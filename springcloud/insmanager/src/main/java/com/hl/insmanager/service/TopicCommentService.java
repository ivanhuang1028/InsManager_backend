package com.hl.insmanager.service;

import com.hl.insmanager.mapper.TopicCommentMapper;
import com.hl.insmanager.vo.topiccomment.CommentVO;

import java.util.List;

public interface TopicCommentService<T> extends BaseService<T>, TopicCommentMapper<T> {

    List<CommentVO> comments(String topic_id);

    void read(String topic_id);
}
