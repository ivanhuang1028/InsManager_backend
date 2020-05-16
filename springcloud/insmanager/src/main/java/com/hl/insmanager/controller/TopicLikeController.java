package com.hl.insmanager.controller;

import com.github.pagehelper.PageHelper;
import com.hl.common.constants.Result;
import com.hl.common.constants.ResultCode;
import com.hl.common.util.UUIDGenerator;
import com.hl.insmanager.module.Dictionary;
import com.hl.insmanager.module.TopicLike;
import com.hl.insmanager.service.DictionaryService;
import com.hl.insmanager.service.TopicLikeService;
import com.hl.insmanager.util.Constants;
import com.hl.insmanager.util.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author ivan.huang
 */
@Slf4j
@RestController("topicLikeController")
@Scope("prototype")
public class TopicLikeController extends BaseController {

    @Autowired
    private TopicLikeService<TopicLike> topicLikeService;

    /**
     * 首页 3. 帖子点赞操作接口
     */
    @RequestMapping(value = "/likes", method = RequestMethod.POST)
    public Result likes(HttpServletRequest request, @RequestBody HashMap<String, String> paramMap) {
        try {
            if(StringUtils.isEmpty(paramMap.get("topic_id"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 topic_id");
            }

            String logierId = getLoginerId(request);
            TopicLike tmp = new TopicLike();
            tmp.setUser_id(logierId);
            tmp.setTopic_id(paramMap.get("topic_id"));
            List<TopicLike> currents = topicLikeService.selectByBlurryT(tmp);
            if(currents.size() > 0){
                return Result.getFalseResult(ResultCode.FAILURE, "已关注");
            }else{
                TopicLike topicLike = new TopicLike();
                topicLike.setLike_id(UUIDGenerator.generate());
                topicLike.setTopic_id(paramMap.get("topic_id"));
                topicLike.setUser_id(logierId);
                topicLike.setCreatetime(new Date());
                topicLike.setIs_read(Constants.NO_READ);
                topicLikeService.insert(topicLike);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.getFalseResult(ResultCode.FAILURE, e.getMessage());
        }
        return Result.getSuccResult();
    }

    /**
     * 首页 4. 帖子取消点赞操作
     * @return
     */
    @RequestMapping(value = "/likes", method = RequestMethod.DELETE)
    public Result unlikes(HttpServletRequest request, @RequestBody HashMap<String, String> paramMap) {
        try {
            if(StringUtils.isEmpty(paramMap.get("topic_id"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 topic_id");
            }

            String logierId = getLoginerId(request);
            TopicLike tmp = new TopicLike();
            tmp.setUser_id(logierId);
            tmp.setTopic_id(paramMap.get("topic_id"));
            topicLikeService.deleteByBlurryT(tmp);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.getFalseResult(ResultCode.FAILURE, e.getMessage());
        }
        return Result.getSuccResult();
    }

}

