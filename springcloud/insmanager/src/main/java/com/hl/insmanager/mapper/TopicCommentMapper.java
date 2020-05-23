package com.hl.insmanager.mapper;

import com.hl.insmanager.vo.backend.CommentBackendVO;
import com.hl.insmanager.vo.topiccomment.CommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ivan.huang
 */
@Mapper
public interface TopicCommentMapper<T> extends BaseMapper<T> {

    List<CommentVO> comments(@Param("topic_id") String topic_id);

    void read(@Param("topic_id") String topic_id);

    Integer countToday(@Param("start_date") String start_date, @Param("end_date") String end_date);

    List<CommentBackendVO> commentBackendVO(@Param("start_date") String start_date, @Param("end_date") String end_date, @Param("user_name") String user_name);

    void commentsShield(@Param("comment_ids") List<String> comment_ids);

    void commentsUnShield(@Param("comment_ids") List<String> comment_ids);

}
