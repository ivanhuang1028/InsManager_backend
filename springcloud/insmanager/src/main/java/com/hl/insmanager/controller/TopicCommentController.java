package com.hl.insmanager.controller;

import com.github.pagehelper.PageHelper;
import com.hl.common.constants.Result;
import com.hl.common.constants.ResultCode;
import com.hl.common.util.UUIDGenerator;
import com.hl.insmanager.module.TopicComment;
import com.hl.insmanager.module.TopicLike;
import com.hl.insmanager.service.TopicCommentService;
import com.hl.insmanager.util.Constants;
import com.hl.insmanager.vo.page.PageVO;
import com.hl.insmanager.vo.page.ResultsPageVO;
import com.hl.insmanager.vo.topiccomment.CommentVO;
import com.hl.insmanager.vo.userfocus.UserFocusTosVO;
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
@RestController("topicCommentController")
@Scope("prototype")
public class TopicCommentController extends BaseController {

    @Autowired
    private TopicCommentService<TopicComment> topicCommentService;

    /**
     * 首页 7. 帖子评论列表接口
     * @return
     */
    @RequestMapping(value = "/comments", method = RequestMethod.GET)
    public Result comments(HttpServletRequest request, PageVO pageVO, String topic_id){
        if(StringUtils.isEmpty(topic_id)){
            return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 topic_id");
        }
        List<CommentVO> commentVO = new ArrayList<>();
        // 分页
        if(pageVO.getOpenPage()){
            PageHelper.startPage(pageVO.getPageIndex(), pageVO.getPageSize());
        }

        commentVO = topicCommentService.comments(topic_id);
        ResultsPageVO resultsPageVO = ResultsPageVO.init(commentVO, pageVO);
        return Result.getSuccResult(resultsPageVO);
    }

    /**
     * 首页 11. 帖子评论发布操作
     */
    @RequestMapping(value = "/comments", method = RequestMethod.POST)
    public Result commentsPost(HttpServletRequest request, @RequestBody HashMap<String, String> paramMap) {
        try {
            if(StringUtils.isEmpty(paramMap.get("topic_id"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 topic_id");
            }
            if(StringUtils.isEmpty(paramMap.get("comment"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 comment");
            }

            String logierId = getLoginerId(request);
            TopicComment tc = new TopicComment();
            tc.setComment_id(UUIDGenerator.generate());
            tc.setTopic_id(paramMap.get("topic_id"));
            tc.setUser_id(logierId);
            tc.setComment(paramMap.get("comment"));
            if(!StringUtils.isEmpty(paramMap.get("at_user_id"))){
                tc.setAt_user_id(paramMap.get("at_user_id"));
            }
            tc.setIs_read(0);
            topicCommentService.insert(tc);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.getFalseResult(ResultCode.FAILURE, e.getMessage());
        }
        return Result.getSuccResult();
    }

    /**
     * 动态消息 5. 读-帖子评论操作接口
     * @return
     */
    @RequestMapping(value = "/topics/comment/read", method = RequestMethod.POST)
    public Result read(HttpServletRequest request, @RequestBody HashMap<String, String> paramMap){
        try {
            if(StringUtils.isEmpty(paramMap.get("topic_id"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 topic_id");
            }
            topicCommentService.read(paramMap.get("topic_id"));
        }catch (Exception e){
            log.error(e.getMessage());
            return Result.getFalseResult(ResultCode.FAILURE, e.getMessage());
        }
        return Result.getSuccResult();
    }

}

