package com.hl.insmanager.service;

import com.hl.insmanager.mapper.TopicCommentMapper;
import com.hl.insmanager.vo.backend.CommentBackendVO;
import com.hl.insmanager.vo.topiccomment.CommentVO;

import java.util.List;

public interface TopicCommentService<T> extends BaseService<T>, TopicCommentMapper<T> {

    List<CommentVO> comments(String topic_id);

    void read(String topic_id);

    Integer countToday(String start_date, String end_date);

    List<CommentBackendVO> commentBackendVO(String start_date, String end_date, String user_name);

    void commentsShield(List<String> comment_ids);

    void commentsUnShield(List<String> comment_ids);
}
