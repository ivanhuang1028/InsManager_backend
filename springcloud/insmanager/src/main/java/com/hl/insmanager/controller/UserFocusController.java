package com.hl.insmanager.controller;

import com.github.pagehelper.PageHelper;
import com.hl.common.constants.Result;
import com.hl.common.constants.ResultCode;
import com.hl.common.util.UUIDGenerator;
import com.hl.insmanager.module.Dictionary;
import com.hl.insmanager.module.UserFocus;
import com.hl.insmanager.module.UserLabel;
import com.hl.insmanager.service.UserFocusService;
import com.hl.insmanager.vo.page.PageVO;
import com.hl.insmanager.vo.page.ResultsPageVO;
import com.hl.insmanager.vo.userfocus.UserFocusTosVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.plexus.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author ivan.huang
 */
@Slf4j
@RestController("userFocusController")
@Scope("prototype")
public class UserFocusController extends BaseController {

    @Autowired
    private UserFocusService<UserFocus> userFocusService;

    /**
     * 首页 1. 被关注人列表接口
     * @return
     */
    @RequestMapping(value = "/focus_tos", method = RequestMethod.GET)
    public Result focusTos(HttpServletRequest request, PageVO pageVO){
        List<UserFocusTosVO> userFocusTosVOList = new ArrayList<>();
        // 分页
        if(pageVO.getOpenPage()){
            PageHelper.startPage(pageVO.getPageIndex(), pageVO.getPageSize());
        }
        userFocusTosVOList = userFocusService.focusTos(getLoginerId(request));
        ResultsPageVO resultsPageVO = ResultsPageVO.init(userFocusTosVOList, pageVO);
        return Result.getSuccResult(resultsPageVO);
    }

    /**
     * 个人主页 7. 用户关注操作接口
     * @return
     */
    @RequestMapping(value = "/focuss", method = RequestMethod.POST)
    public Result focuss(HttpServletRequest request, @RequestBody HashMap<String, String> paramMap){
        try {
            if(StringUtils.isEmpty(paramMap.get("user_id"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 user_id");
            }

            String loginerId = this.getLoginerId(request);

            UserFocus tmp = new UserFocus();
            tmp.setFocus_from(loginerId);
            tmp.setFocus_to(paramMap.get("user_id"));
            List<UserFocus> list = userFocusService.selectByBlurryT(tmp);
            if(!ArrayUtils.isEmpty(list.toArray())){
                return Result.getFalseResult(ResultCode.FAILURE, "已关注");
            }
            tmp.setFocus_id(UUIDGenerator.generate());
            tmp.setFocus_time(new Date(System.currentTimeMillis()));
            tmp.setRead_time(new Date(System.currentTimeMillis()));
            tmp.setIs_read(0);
            userFocusService.insert(tmp);

        }catch (Exception e){
            log.error(e.getMessage());
            return Result.getFalseResult(ResultCode.FAILURE, e.getMessage());
        }
        return Result.getSuccResult();
    }

    /**
     * 个人主页 8. 用户取消关注操作接口
     * @return
     */
    @RequestMapping(value = "/unfocuss", method = RequestMethod.POST)
    public Result unfocuss(HttpServletRequest request, @RequestBody HashMap<String, String> paramMap){
        try {
            if(StringUtils.isEmpty(paramMap.get("user_id"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 user_id");
            }

            String loginerId = this.getLoginerId(request);

            UserFocus tmp = new UserFocus();
            tmp.setFocus_from(loginerId);
            tmp.setFocus_to(paramMap.get("user_id"));
            userFocusService.deleteByBlurryT(tmp);

        }catch (Exception e){
            log.error(e.getMessage());
            return Result.getFalseResult(ResultCode.FAILURE, e.getMessage());
        }
        return Result.getSuccResult();
    }

    /**
     * 动态消息 2. 读-被用户关注操作接口
     * @return
     */
    @RequestMapping(value = "/focuss/read", method = RequestMethod.POST)
    public Result focussRead(HttpServletRequest request, @RequestBody HashMap<String, String> paramMap){
        try {
            if(StringUtils.isEmpty(paramMap.get("user_id"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 user_id");
            }
            userFocusService.read(paramMap.get("user_id"), this.getLoginerId(request));
        }catch (Exception e){
            log.error(e.getMessage());
            return Result.getFalseResult(ResultCode.FAILURE, e.getMessage());
        }
        return Result.getSuccResult();
    }

    /**
     * 动态消息 3. 读-关注用户帖子操作接口
     * @return
     */
    @RequestMapping(value = "/topics/read", method = RequestMethod.POST)
    public Result topicRead(HttpServletRequest request, @RequestBody HashMap<String, String> paramMap){
        try {
            if(StringUtils.isEmpty(paramMap.get("user_id"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 user_id");
            }
            userFocusService.topicRead(this.getLoginerId(request), paramMap.get("user_id"));
        }catch (Exception e){
            log.error(e.getMessage());
            return Result.getFalseResult(ResultCode.FAILURE, e.getMessage());
        }
        return Result.getSuccResult();
    }

}

