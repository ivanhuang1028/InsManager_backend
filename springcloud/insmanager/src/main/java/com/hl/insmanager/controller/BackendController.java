package com.hl.insmanager.controller;

import com.github.pagehelper.PageHelper;
import com.hl.common.constants.Result;
import com.hl.common.constants.ResultCode;
import com.hl.common.util.JwtHelper;
import com.hl.common.util.RequestUtil;
import com.hl.common.util.UUIDGenerator;
import com.hl.insmanager.module.*;
import com.hl.insmanager.properties.ImagePath;
import com.hl.insmanager.properties.JwtConfig;
import com.hl.insmanager.properties.OssConfig;
import com.hl.insmanager.service.*;
import com.hl.insmanager.util.Constants;
import com.hl.insmanager.util.ImageBase64Converter;
import com.hl.insmanager.util.OssUtils;
import com.hl.insmanager.vo.backend.*;
import com.hl.insmanager.vo.dic.DicVO;
import com.hl.insmanager.vo.msg.MsgVO;
import com.hl.insmanager.vo.page.PageVO;
import com.hl.insmanager.vo.page.ResultsPageVO;
import com.hl.insmanager.vo.user.UserInfoVO;
import com.hl.insmanager.vo.user.UserLabelVO;
import com.hl.insmanager.vo.user.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author ivan.huang
 */
@Slf4j
@RestController("backendController")
@Scope("prototype")
public class BackendController extends BaseController {

    @Autowired
    private UserService<User> userService;

    @Autowired
    private ImageService<Image> imageService;

    @Autowired
    private TopicService<Topic> topicService;

    @Autowired
    private TopicCommentService<TopicComment> topicCommentService;

    @Autowired
    private MsgService<Msg> msgService;

    @Autowired
    private SysMsgService<SysMsg> sysMsgService;

    /**
     * 后台管理 首页 1. 首页统计基础数据接口
     * @return
     */
    @RequestMapping(value = "/count/base", method = RequestMethod.GET)
    public Result countBase(HttpServletRequest request){
        CountBaseVO vo = new CountBaseVO();
        Image tmp = new Image();
        Msg tmp1 = new Msg();
        try {
            vo.setUser_num(userService.countByBlurryT(null,null));
            tmp.setImage_type(Constants.TOPIC_TYPE_PIC);
            vo.setPic_num(imageService.countByBlurryT(tmp, null));
            vo.setComment_num(topicCommentService.countByBlurryT(null, null));
            tmp.setImage_type(Constants.TOPIC_TYPE_VIDEO);
            vo.setVideo_num(imageService.countByBlurryT(tmp, null));
            tmp1.setMsg_type(5);
            vo.setTranspond_num(msgService.countByBlurryT(tmp1, null));
        }catch (Exception e){
            return Result.getFailResult(e.getMessage());
        }

        return Result.getSuccResult(vo);
    }

    /**
     * 后台管理 首页 2. 首页-今日数据动态
     * @return
     */
    @RequestMapping(value = "/count/today", method = RequestMethod.GET)
    public Result countToday(HttpServletRequest request, String start_date, String end_date){
        CountTodayVO vo = new CountTodayVO();
        try {
            vo.setUser_num(userService.countToday(start_date, end_date));
            vo.setTopic_num(topicService.countToday(start_date, end_date));
            vo.setComment_num(topicCommentService.countToday(start_date, end_date));
            vo.setTranspond_num(msgService.countToday(start_date, end_date));
        }catch (Exception e){
            return Result.getFailResult(e.getMessage());
        }

        return Result.getSuccResult(vo);
    }

    /**
     * 后台管理 会员 1. 会员用户搜索列表接口
     * @return
     */
    @RequestMapping(value = "/users/backend", method = RequestMethod.GET)
    public Result usersBackend(HttpServletRequest request, PageVO pageVO ){
        List<UserBackendVO> userBackendVO = new ArrayList<>();
        // 分页
        if(pageVO.getOpenPage()){
            PageHelper.startPage(pageVO.getPageIndex(), pageVO.getPageSize());
        }

        userBackendVO = userService.usersBackend(RequestUtil.get(request, "start_date"),
                RequestUtil.get(request, "end_date"), RequestUtil.get(request, "user_gender"),
                RequestUtil.get(request, "user_region"), RequestUtil.get(request, "user_school"),
                RequestUtil.get(request, "key"));

        ResultsPageVO resultsPageVO = ResultsPageVO.init(userBackendVO, pageVO);
        return Result.getSuccResult(resultsPageVO);
    }

    /**
     * 后台管理 会员 2. 会员用户基本资料接口
     * @return
     */
    @RequestMapping(value = "/users/backend/{user_id}", method = RequestMethod.GET)
    public Result usersBackend1(HttpServletRequest request, @PathVariable String user_id){
        UserBackend1VO vo = new UserBackend1VO();
        try {
            vo = userService.usersBackend1(user_id);
        }catch (Exception e){
            return Result.getFailResult(e.getMessage());
        }

        return Result.getSuccResult(vo);
    }

    /**
     * 后台管理 会员 3. 用户加入黑名单操作接口
     */
    @RequestMapping(value = "/users/black", method = RequestMethod.POST)
    public Result userBlack(HttpServletRequest request, @RequestBody HashMap<String, String> paramMap) {
        try {
            if(StringUtils.isEmpty(paramMap.get("user_ids"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 user_ids");
            }

            List<String> user_ids = Arrays.asList(paramMap.get("user_ids").split(","));
            userService.userBlack(user_ids);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.getFalseResult(ResultCode.FAILURE, e.getMessage());
        }
        return Result.getSuccResult();
    }

    /**
     * 后台管理 会员 4. 用户取消黑名单操作接口
     */
    @RequestMapping(value = "/users/unblack", method = RequestMethod.POST)
    public Result userUnBlack(HttpServletRequest request, @RequestBody HashMap<String, String> paramMap) {
        try {
            if(StringUtils.isEmpty(paramMap.get("user_ids"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 user_ids");
            }

            List<String> user_ids = Arrays.asList(paramMap.get("user_ids").split(","));
            userService.userUnBlack(user_ids);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.getFalseResult(ResultCode.FAILURE, e.getMessage());
        }
        return Result.getSuccResult();
    }

    /**
     * 后台管理 作品 1. 会员作品搜索列表接口
     * @return
     */
    @RequestMapping(value = "/topics/backend", method = RequestMethod.GET)
    public Result topicsBackend(HttpServletRequest request, PageVO pageVO){
        List<TopicsBackendVO> topicsBackendVO = new ArrayList<>();
        // 分页
        if(pageVO.getOpenPage()){
            PageHelper.startPage(pageVO.getPageIndex(), pageVO.getPageSize());
        }

        topicsBackendVO = topicService.topicsBackendVO(RequestUtil.get(request, "start_date"),
                RequestUtil.get(request, "end_date"), RequestUtil.get(request, "topic_valid"),
                RequestUtil.get(request, "user_name"));

        for(TopicsBackendVO vo : topicsBackendVO){
            vo.setImages(topicService.topicsImagesVO(vo.getTopic_id()));
        }

        ResultsPageVO resultsPageVO = ResultsPageVO.init(topicsBackendVO, pageVO);
        return Result.getSuccResult(resultsPageVO);
    }

    /**
     * 后台管理 作品 2. 帖子作品屏蔽操作接口
     */
    @RequestMapping(value = "/topics/shield", method = RequestMethod.POST)
    public Result topicsShield(HttpServletRequest request, @RequestBody HashMap<String, String> paramMap) {
        try {
            if(StringUtils.isEmpty(paramMap.get("topic_ids"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 topic_ids");
            }

            List<String> topic_ids = Arrays.asList(paramMap.get("topic_ids").split(","));
            topicService.topicsShield(topic_ids);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.getFalseResult(ResultCode.FAILURE, e.getMessage());
        }
        return Result.getSuccResult();
    }

    /**
     * 后台管理 作品 3. 帖子作品取消屏蔽操作接口
     */
    @RequestMapping(value = "/topics/unshield", method = RequestMethod.POST)
    public Result topicsUnShield(HttpServletRequest request, @RequestBody HashMap<String, String> paramMap) {
        try {
            if(StringUtils.isEmpty(paramMap.get("topic_ids"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 topic_ids");
            }

            List<String> topic_ids = Arrays.asList(paramMap.get("topic_ids").split(","));
            topicService.topicsUnShield(topic_ids);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.getFalseResult(ResultCode.FAILURE, e.getMessage());
        }
        return Result.getSuccResult();
    }

    /**
     * 后台管理 作品 4. 帖子作品推荐操作接口
     */
    @RequestMapping(value = "/topics/recommend", method = RequestMethod.POST)
    public Result topicsRecommend(HttpServletRequest request, @RequestBody HashMap<String, String> paramMap) {
        try {
            if(StringUtils.isEmpty(paramMap.get("topic_ids"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 topic_ids");
            }

            List<String> topic_ids = Arrays.asList(paramMap.get("topic_ids").split(","));
            topicService.topicsRecommend(topic_ids);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.getFalseResult(ResultCode.FAILURE, e.getMessage());
        }
        return Result.getSuccResult();
    }

    /**
     * 后台管理 作品 5. 帖子作品取消推荐操作接口
     */
    @RequestMapping(value = "/topics/unrecommend", method = RequestMethod.POST)
    public Result topicsUnRecommend(HttpServletRequest request, @RequestBody HashMap<String, String> paramMap) {
        try {
            if(StringUtils.isEmpty(paramMap.get("topic_ids"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 topic_ids");
            }

            List<String> topic_ids = Arrays.asList(paramMap.get("topic_ids").split(","));
            topicService.topicsUnRecommend(topic_ids);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.getFalseResult(ResultCode.FAILURE, e.getMessage());
        }
        return Result.getSuccResult();
    }

    /**
     * 后台管理 作品 6. 帖子作品删除操作接口
     */
    @RequestMapping(value = "/topics", method = RequestMethod.DELETE)
    public Result topicsDelete(HttpServletRequest request, @RequestBody HashMap<String, String> paramMap) {
        try {
            if(StringUtils.isEmpty(paramMap.get("topic_ids"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 topic_ids");
            }

            List<String> topic_ids = Arrays.asList(paramMap.get("topic_ids").split(","));
            topicService.topicsDelete(topic_ids);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.getFalseResult(ResultCode.FAILURE, e.getMessage());
        }
        return Result.getSuccResult();
    }


    /**
     * 后台管理 评论 1. 评论搜索列表接口
     * @return
     */
    @RequestMapping(value = "/comments/backend", method = RequestMethod.GET)
    public Result commentsBackend(HttpServletRequest request, PageVO pageVO){
        List<CommentBackendVO> commentBackendVO = new ArrayList<>();
        // 分页
        if(pageVO.getOpenPage()){
            PageHelper.startPage(pageVO.getPageIndex(), pageVO.getPageSize());
        }

        commentBackendVO = topicCommentService.commentBackendVO(RequestUtil.get(request, "start_date"),
                RequestUtil.get(request, "end_date"), RequestUtil.get(request, "user_name"));

        ResultsPageVO resultsPageVO = ResultsPageVO.init(commentBackendVO, pageVO);
        return Result.getSuccResult(resultsPageVO);
    }

    /**
     * 后台管理 评论 2. 评论屏蔽操作接口
     */
    @RequestMapping(value = "/comments/shield", method = RequestMethod.POST)
    public Result commentsShield(HttpServletRequest request, @RequestBody HashMap<String, String> paramMap) {
        try {
            if(StringUtils.isEmpty(paramMap.get("comment_ids"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 comment_ids");
            }

            List<String> comment_ids = Arrays.asList(paramMap.get("comment_ids").split(","));
            topicCommentService.commentsShield(comment_ids);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.getFalseResult(ResultCode.FAILURE, e.getMessage());
        }
        return Result.getSuccResult();
    }

    /**
     * 后台管理 评论 3. 评论取消屏蔽操作接口
     */
    @RequestMapping(value = "/comments/unshield", method = RequestMethod.POST)
    public Result commentsUnShield(HttpServletRequest request, @RequestBody HashMap<String, String> paramMap) {
        try {
            if(StringUtils.isEmpty(paramMap.get("comment_ids"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 comment_ids");
            }

            List<String> comment_ids = Arrays.asList(paramMap.get("comment_ids").split(","));
            topicCommentService.commentsUnShield(comment_ids);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.getFalseResult(ResultCode.FAILURE, e.getMessage());
        }
        return Result.getSuccResult();
    }

    /**
     * 后台管理 系统 1. 系统消息列表接口
     * @return
     */
    @RequestMapping(value = "/sysmsgs/backend", method = RequestMethod.GET)
    public Result sysmsgsBackend(HttpServletRequest request, PageVO pageVO){
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
     * 后台管理 系统 2. 系统消息新增接口
     */
    @RequestMapping(value = "/sysmsgs/backend", method = RequestMethod.POST)
    public Result sysmsgsBackendPost(HttpServletRequest request, @RequestBody HashMap<String, String> paramMap) {
        try {
            if(StringUtils.isEmpty(paramMap.get("sys_msg_topic"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 sys_msg_topic");
            }
            if(StringUtils.isEmpty(paramMap.get("sys_msg_content"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 sys_msg_content");
            }

            SysMsg sysMsg = new SysMsg();
            sysMsg.setSys_msg_id(UUIDGenerator.generate());
            sysMsg.setSys_msg_topic(paramMap.get("sys_msg_topic"));
            sysMsg.setSys_msg_content(paramMap.get("sys_msg_content"));
            if(!StringUtils.isEmpty(paramMap.get("sys_msg_link"))){
                sysMsg.setSys_msg_link(paramMap.get("sys_msg_link"));
            }
            sysMsgService.insert(sysMsg);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.getFalseResult(ResultCode.FAILURE, e.getMessage());
        }
        return Result.getSuccResult();
    }

    /**
     * 后台管理 系统 3. 系统消息删除操作接口
     */
    @RequestMapping(value = "/sysmsgs/backend", method = RequestMethod.DELETE)
    public Result sysmsgsBackendDelete(HttpServletRequest request, @RequestBody HashMap<String, String> paramMap) {
        try {
            if(StringUtils.isEmpty(paramMap.get("sys_msg_ids"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 sys_msg_ids");
            }

            List<String> sys_msg_ids = Arrays.asList(paramMap.get("sys_msg_ids").split(","));
            sysMsgService.sysmsgsDelete(sys_msg_ids);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.getFalseResult(ResultCode.FAILURE, e.getMessage());
        }
        return Result.getSuccResult();
    }

}

