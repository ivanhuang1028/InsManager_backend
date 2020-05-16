package com.hl.insmanager.controller;

import com.github.pagehelper.PageHelper;
import com.hl.common.constants.QueryParam;
import com.hl.common.constants.Result;
import com.hl.common.constants.ResultCode;
import com.hl.common.util.DateUtil;
import com.hl.common.util.DateUtils;
import com.hl.common.util.UUIDGenerator;
import com.hl.insmanager.module.*;
import com.hl.insmanager.properties.ImagePath;
import com.hl.insmanager.properties.OssConfig;
import com.hl.insmanager.service.*;
import com.hl.insmanager.util.Constants;
import com.hl.insmanager.util.ImageBase64Converter;
import com.hl.insmanager.util.OssUtils;
import com.hl.insmanager.vo.msg.MsgActionVO;
import com.hl.insmanager.vo.msg.MsgVO;
import com.hl.insmanager.vo.msg.MsgsUsersVO;
import com.hl.insmanager.vo.page.PageVO;
import com.hl.insmanager.vo.page.ResultsPageVO;
import com.hl.insmanager.vo.topiccomment.CommentVO;
import com.hl.insmanager.vo.user.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * @author ivan.huang
 */
@Slf4j
@RestController("msgController")
@Scope("prototype")
public class MsgController extends BaseController {

    @Autowired
    private MsgService<Msg> msgService;

    @Autowired
    private MsgSessionService<MsgSession> msgSessionService;

    @Autowired
    private UserService<User> userService;

    @Autowired
    private ImageService<Image> imageService;

    @Autowired
    private SysMsgService<SysMsg> sysMsgService;

    @Autowired
    private SysMsgReadService<SysMsgRead> sysMsgReadService;

    @Autowired
    private ImagePath imagePath;

    @Autowired
    private OssConfig ossConfig;

    /**
     * 首页 9. 帖子转发操作接口
     */
    @RequestMapping(value = "/msgs/topic", method = RequestMethod.POST)
    public Result likes(HttpServletRequest request, @RequestBody HashMap<String, String> paramMap) {
        try {
            if(StringUtils.isEmpty(paramMap.get("topic_id"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 topic_id");
            }
            if(StringUtils.isEmpty(paramMap.get("user_ids"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 user_ids");
            }

            String logierId = getLoginerId(request);
            List<String> user_ids = Arrays.asList(paramMap.get("user_ids").split(","));
            for (String user_id : user_ids){
                Msg msg = new Msg();
                msg.setMsg_id(UUIDGenerator.generate());
                msg.setUser_from(logierId);
                msg.setUser_to(user_id);
                msg.setMsg_type(Constants.MSG_TYPE_TOPIC);
                msg.setTopic_id(paramMap.get("topic_id"));
                msg.setMsg_time(new Date(System.currentTimeMillis()));
                msgService.insert(msg);

                if(!StringUtils.isEmpty(paramMap.get("msg_content"))){
                    Msg msgChar = new Msg();
                    msgChar.setMsg_id(UUIDGenerator.generate());
                    msgChar.setUser_from(logierId);
                    msgChar.setUser_to(user_id);
                    msgChar.setMsg_type(Constants.MSG_TYPE_CHARACTER);
                    msgChar.setMsg_content(paramMap.get("msg_content"));
                    msgChar.setMsg_time(new Date(System.currentTimeMillis()));
                    msgService.insert(msgChar);
                }

                //更新会话时间
                String userIds = getUserIds(logierId, user_id);
                MsgSession msTmp = new MsgSession();
                msTmp.setUser_ids(userIds);
                if(msgSessionService.selectByEqualT(msTmp) == null){
                    MsgSession ms = new MsgSession();
                    ms.setMs_id(UUIDGenerator.generate());
                    ms.setUser_ids(userIds);
                    ms.setSession_time(new Date(System.currentTimeMillis()));
                    msgSessionService.insert(ms);
                }else{
                    msgSessionService.updateUserIds(userIds);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.getFalseResult(ResultCode.FAILURE, e.getMessage());
        }
        return Result.getSuccResult();
    }

    private String getUserIds(String logierId, String user_id) throws Exception{
        Integer is_before = userService.isBefore(logierId, user_id);
        if(is_before > 0){
            return logierId + "," + user_id;
        }else {
            return user_id + "," + logierId;
        }
    }

    /**
     * 私信 1. 私信详情接口
     */
    @RequestMapping(value = "/mess", method = RequestMethod.GET)
    public Result mess(HttpServletRequest request, PageVO pageVO, String msg_user_id,
                       String msg_time, int is_before) {
        List<MsgVO> msgVO = new ArrayList<>();
        // 分页
        if(pageVO.getOpenPage()){
            PageHelper.startPage(pageVO.getPageIndex(), pageVO.getPageSize());
        }

        if(is_before == 1){
            msgVO = msgService.hisMess(getLoginerId(request), msg_user_id, msg_time);
        }
        if(is_before == 0){
            msgVO = msgService.newMess(getLoginerId(request), msg_user_id, msg_time);
        }

        ResultsPageVO resultsPageVO = ResultsPageVO.init(msgVO, pageVO);
        return Result.getSuccResult(resultsPageVO);
    }

    /**
     * 私信 2. 私信读操作接口
     */
    @RequestMapping(value = "/msgs/read", method = RequestMethod.POST)
    public Result msgsRead(HttpServletRequest request, @RequestBody HashMap<String, String> paramMap) {
        if(StringUtils.isEmpty(paramMap.get("msg_user_id"))){
            return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 msg_user_id");
        }
        msgService.updateMsgRead(this.getLoginerId(request), paramMap.get("msg_user_id"));
        return Result.getSuccResult();
    }

    /**
     * 私信 3. 私信写操作接口
     */
    @RequestMapping(value = "/msgs/write", method = RequestMethod.POST)
    public Result msgsWrite(HttpServletRequest request, @RequestBody HashMap<String, String> paramMap) {
        try {
            if(StringUtils.isEmpty(paramMap.get("msg_user_id"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 msg_user_id");
            }
            if(StringUtils.isEmpty(paramMap.get("msg_type"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 msg_type");
            }
            if(StringUtils.isEmpty(paramMap.get("msg_content"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 msg_content");
            }

            String logierId = getLoginerId(request);

            Msg msg = new Msg();
            msg.setMsg_id(UUIDGenerator.generate());
            msg.setMsg_type(Integer.valueOf(paramMap.get("msg_type")));
            msg.setUser_from(logierId);
            msg.setUser_to(paramMap.get("msg_user_id"));
            if(Constants.MSG_TYPE_CHARACTER == Integer.valueOf(paramMap.get("msg_type")) ){
                msg.setMsg_content(paramMap.get("msg_content"));
            }
            if(Constants.MSG_TYPE_FACE == Integer.valueOf(paramMap.get("msg_type"))){
                msg.setFace_content(paramMap.get("msg_content"));
            }


            if(Constants.MSG_TYPE_PIC == Integer.valueOf(paramMap.get("msg_type")) ||
                    Constants.MSG_TYPE_VIDEO == Integer.valueOf(paramMap.get("msg_type")) ){
                if(StringUtils.isEmpty(paramMap.get("msg_suffix"))){
                    return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 msg_suffix");
                }

                //先保存在本地，再上传到OSS，保存数据库数据
                // 保存在本地
                String imagePath = this.imagePath.getLocalPicPath();
                String imageNameLocal = msg.getMsg_id() + "_image." + paramMap.get("msg_suffix");
                ImageBase64Converter.convertBase64ToFile(paramMap.get("msg_content"), imagePath, imageNameLocal);
                // 上传到OSS
                String imageName = "image." + paramMap.get("msg_suffix");
                String image_remote_path = ossConfig.getMsgPath() + msg.getMsg_id() + "/" + imageName;
                String image_remote_link =  OssUtils.uploadOssReturnUrl(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret(),
                        ossConfig.getBucketName(), imagePath + "/" + imageNameLocal, image_remote_path);
                if(StringUtils.isEmpty(image_remote_link)){
                    return Result.getFalseResult(ResultCode.FAILURE, "文件发送失败");
                }
                FileUtils.forceDelete(new File(imagePath + "/" + imageNameLocal));
                // 保存数据库
                //保存 image
                Image image = new Image();
                image.setImage_id(UUIDGenerator.generate());
                image.setImage_user(logierId);
                if(Constants.MSG_TYPE_PIC == Integer.valueOf(paramMap.get("msg_type"))){
                    image.setImage_type(Constants.TOPIC_TYPE_PIC);
                }
                if(Constants.MSG_TYPE_VIDEO == Integer.valueOf(paramMap.get("msg_type"))){
                    image.setImage_type(Constants.TOPIC_TYPE_VIDEO);
                }
                image.setImage_name(imageName);
                image.setImage_remote_path(image_remote_path);
                image.setImage_remote_link(image_remote_link);
                image.setImage_uploadtime(new Date(System.currentTimeMillis()));
                imageService.insert(image);

                msg.setImage_id(image.getImage_id());
            }
            msg.setMsg_time(new Date(System.currentTimeMillis()));
            msgService.insert(msg);

            //更新会话时间
            String userIds = getUserIds(logierId, paramMap.get("msg_user_id"));
            MsgSession msTmp = new MsgSession();
            msTmp.setUser_ids(userIds);
            if(msgSessionService.selectByEqualT(msTmp) == null){
                MsgSession ms = new MsgSession();
                ms.setMs_id(UUIDGenerator.generate());
                ms.setUser_ids(userIds);
                ms.setSession_time(new Date(System.currentTimeMillis()));
                msgSessionService.insert(ms);
            }else{
                msgSessionService.updateUserIds(userIds);
            }


        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.getFalseResult(ResultCode.FAILURE, e.getMessage());
        }
        return Result.getSuccResult();
    }

    /**
     * 动态消息 1. 动态消息列表接口
     */
    @RequestMapping(value = "/msgs/action", method = RequestMethod.GET)
    public Result msgsAction(HttpServletRequest request, PageVO pageVO, String action_type) {
        List<MsgActionVO> msgActionVO = new ArrayList<>();
        // 分页
        if(pageVO.getOpenPage()){
            PageHelper.startPage(pageVO.getPageIndex(), pageVO.getPageSize());
        }

        if(Integer.valueOf(action_type) == 0){
            msgActionVO = msgService.msgsAction(this.getLoginerId(request));
        }
        if(Integer.valueOf(action_type) == 1) {
            msgActionVO = msgService.msgsAction1(this.getLoginerId(request));
        }
        if(Integer.valueOf(action_type) == 2) {
            msgActionVO = msgService.msgsAction2(this.getLoginerId(request));
        }
        if(Integer.valueOf(action_type) == 3) {
            msgActionVO = msgService.msgsAction3(this.getLoginerId(request));
        }
        if(Integer.valueOf(action_type) == 4) {
            msgActionVO = msgService.msgsAction4(this.getLoginerId(request));
        }

        ResultsPageVO resultsPageVO = ResultsPageVO.init(msgActionVO, pageVO);
        return Result.getSuccResult(resultsPageVO);
    }

    /**
     * 动态消息 6. 未读系统消息数量接口
     */
    @RequestMapping(value = "/sysmsgs/count", method = RequestMethod.GET)
    public Result sysMsgsCount(HttpServletRequest request) {
        try {
            String user_id = this.getLoginerId(request);

            SysMsgRead sysMsgReadTmp = new SysMsgRead();
            sysMsgReadTmp.setUser_id(user_id);
            SysMsgRead sysMsgReadCurrent = sysMsgReadService.selectByEqualT(sysMsgReadTmp);
            if(sysMsgReadCurrent == null || StringUtils.isEmpty(sysMsgReadCurrent.getSmr_id())){
                return Result.getSuccResult(sysMsgService.countAll());
            }
            return Result.getSuccResult(sysMsgService.sysMsgsCount(user_id));
        }catch (Exception e){
            return Result.getFalseResult(ResultCode.FAILURE, e.getMessage());
        }

    }

    /**
     * 动态消息 7. 第一条系统消息内容接口
     */
    @RequestMapping(value = "/sysmsgs/first", method = RequestMethod.GET)
    public Result sysMsgsFirst(HttpServletRequest request) {
        SysMsg sysMsg = sysMsgService.getFirst();
        return Result.getSuccResult(sysMsg);

    }

    /**
     * 动态消息 8. 系统消息列表接口
     */
    @RequestMapping(value = "/sysmsgs", method = RequestMethod.GET)
    public Result sysmsgs(HttpServletRequest request, PageVO pageVO) {
        List<SysMsg> sysMsgs = new ArrayList<>();
        // 分页
        if(pageVO.getOpenPage()){
            PageHelper.startPage(pageVO.getPageIndex(), pageVO.getPageSize());
        }

        sysMsgs = sysMsgService.selectByBlurryT(null);
        ResultsPageVO resultsPageVO = ResultsPageVO.init(sysMsgs, pageVO);
        return Result.getSuccResult(resultsPageVO);
    }

    /**
     * 动态消息 9. 系统消息读参照接口
     */
    @RequestMapping(value = "/sysmsgs/read", method = RequestMethod.POST)
    public Result sysMsgsRead(HttpServletRequest request) {
        try {
            SysMsgRead sysMsgReadTmp = new SysMsgRead();
            sysMsgReadTmp.setUser_id(this.getLoginerId(request));
            SysMsgRead sysMsgReadCurrent = sysMsgReadService.selectByEqualT(sysMsgReadTmp);
            if(sysMsgReadCurrent == null || StringUtils.isEmpty(sysMsgReadCurrent.getSmr_id())){
                sysMsgReadTmp.setSmr_id(UUIDGenerator.generate());
                sysMsgReadService.insert(sysMsgReadTmp);
            }else {
                sysMsgReadCurrent.setRead_time(new Date(System.currentTimeMillis()));
                sysMsgReadService.updateByPrimaryKey(sysMsgReadCurrent);
            }
        }catch (Exception e){
            return Result.getFalseResult(ResultCode.FAILURE, e.getMessage());
        }
        return Result.getSuccResult();
    }

}

