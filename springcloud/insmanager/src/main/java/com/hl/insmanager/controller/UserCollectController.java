package com.hl.insmanager.controller;

import com.github.pagehelper.PageHelper;
import com.hl.common.constants.Result;
import com.hl.common.constants.ResultCode;
import com.hl.common.util.UUIDGenerator;
import com.hl.insmanager.module.UserCollect;
import com.hl.insmanager.service.UserCollectService;
import com.hl.insmanager.vo.page.PageVO;
import com.hl.insmanager.vo.page.ResultsPageVO;
import com.hl.insmanager.vo.usercollect.UserCollectVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author ivan.huang
 */
@Slf4j
@RestController("userCollectController")
@Scope("prototype")
public class UserCollectController extends BaseController {

    @Autowired
    private UserCollectService<UserCollect> userCollectService;

    /**
     * 首页 5. 帖子收藏操作接口
     */
    @RequestMapping(value = "/collects", method = RequestMethod.POST)
    public Result collects(HttpServletRequest request, @RequestBody HashMap<String, String> paramMap) {
        try {
            if(StringUtils.isEmpty(paramMap.get("topic_id"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 topic_id");
            }

            String logierId = getLoginerId(request);
            UserCollect tmp = new UserCollect();
            tmp.setUser_id(logierId);
            tmp.setTopic_id(paramMap.get("topic_id"));
            List<UserCollect> currents = userCollectService.selectByBlurryT(tmp);
            if(currents.size() > 0){
                return Result.getFalseResult(ResultCode.FAILURE, "已收藏");
            }else{
                UserCollect userCollect = new UserCollect();
                userCollect.setUc_id(UUIDGenerator.generate());
                userCollect.setTopic_id(paramMap.get("topic_id"));
                userCollect.setUser_id(logierId);
                userCollect.setCollecttime(new Date());
                userCollectService.insert(userCollect);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.getFalseResult(ResultCode.FAILURE, e.getMessage());
        }
        return Result.getSuccResult();
    }

    /**
     * 首页 6. 帖子取消收藏操作接口
     * @return
     */
    @RequestMapping(value = "/collects", method = RequestMethod.DELETE)
    public Result uncollects(HttpServletRequest request, @RequestBody HashMap<String, String> paramMap) {
        try {
            if(StringUtils.isEmpty(paramMap.get("topic_id"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 topic_id");
            }

            String logierId = getLoginerId(request);
            UserCollect tmp = new UserCollect();
            tmp.setUser_id(logierId);
            tmp.setTopic_id(paramMap.get("topic_id"));
            userCollectService.deleteByBlurryT(tmp);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.getFalseResult(ResultCode.FAILURE, e.getMessage());
        }
        return Result.getSuccResult();
    }

    /**
     * 个人主页 9. 个人收藏帖子列表接口
     * @return
     */
    @RequestMapping(value = "/collects", method = RequestMethod.GET)
    public Result collects(HttpServletRequest request, PageVO pageVO, String key){
        List<UserCollectVO> collectVO = new ArrayList<>();
        // 分页
        if(pageVO.getOpenPage()){
            PageHelper.startPage(pageVO.getPageIndex(), pageVO.getPageSize());
        }
        collectVO = userCollectService.collects(getLoginerId(request));

        ResultsPageVO resultsPageVO = ResultsPageVO.init(collectVO, pageVO);

        return Result.getSuccResult(resultsPageVO);
    }

}

