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
import com.hl.insmanager.vo.backend.CountBaseVO;
import com.hl.insmanager.vo.backend.CountTodayVO;
import com.hl.insmanager.vo.backend.UserBackendVO;
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
    private DictionaryService<Dictionary> dictionaryService;

    @Autowired
    private UserLabelService<UserLabel> userLabelService;

    @Autowired
    private BackendAccountService<BackendAccount> backendAccountService;

    @Autowired
    private ImagePath imagePath;

    @Autowired
    private OssConfig ossConfig;

    @Autowired
    private JwtConfig jwtConfig;

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


}

