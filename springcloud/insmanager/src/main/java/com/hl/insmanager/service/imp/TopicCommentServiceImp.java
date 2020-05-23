package com.hl.insmanager.service.imp;

import com.hl.insmanager.mapper.BaseMapper;
import com.hl.insmanager.mapper.TopicCommentMapper;
import com.hl.insmanager.service.TopicCommentService;
import com.hl.insmanager.vo.backend.CommentBackendVO;
import com.hl.insmanager.vo.topiccomment.CommentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ivan.huang
 */
@Slf4j
@Service("topicCommentService")
public class TopicCommentServiceImp<T> extends BaseServiceImp<T> implements TopicCommentService<T> {

    @Override
    public TopicCommentMapper<T> getMapper() {
        return (TopicCommentMapper<T>) mapper;
    }

    @Override
    @Autowired
    public void setMapper(@Qualifier("topicCommentMapper") BaseMapper<T> mapper) {
        super.setMapper(mapper);
    }


    @Override
    public List<CommentVO> comments(String topic_id) {
        return getMapper().comments(topic_id);
    }

    @Override
    public void read(String topic_id) {
        getMapper().read(topic_id);
    }

    @Override
    public Integer countToday(String start_date, String end_date) {
        return getMapper().countToday(start_date, end_date);
    }

    @Override
    public List<CommentBackendVO> commentBackendVO(String start_date, String end_date, String user_name) {
        return getMapper().commentBackendVO(start_date, end_date, user_name);
    }

    @Override
    public void commentsShield(List<String> comment_ids) {
        getMapper().commentsShield(comment_ids);
    }

    @Override
    public void commentsUnShield(List<String> comment_ids) {
        getMapper().commentsUnShield(comment_ids);
    }
}
