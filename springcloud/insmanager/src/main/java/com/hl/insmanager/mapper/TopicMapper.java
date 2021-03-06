package com.hl.insmanager.mapper;

import com.hl.insmanager.vo.backend.TopicsBackendVO;
import com.hl.insmanager.vo.topic.TopicsCommentsVO;
import com.hl.insmanager.vo.topic.TopicsImagesVO;
import com.hl.insmanager.vo.topic.TopicsVO;
import com.hl.insmanager.vo.topic.TopicsVideoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ivan.huang
 */
@Mapper
public interface TopicMapper<T> extends BaseMapper<T> {

    List<TopicsVO> topics(@Param("user_id") String user_id);

    List<TopicsImagesVO> topicsImagesVO(@Param("topic_id") String topic_id);

    Integer countTopicsVideo(@Param("dic_id") String dic_id);

    List<TopicsVideoVO> topicsVideo(@Param("ran")Integer ran, @Param("video_num") Integer video_num, @Param("dic_id") String dic_id);

    Integer countTopicsPic(@Param("dic_id") String dic_id);

    List<TopicsVideoVO> topicsPic(@Param("ran") Integer ran, @Param("pic_num") Integer pic_num, @Param("dic_id") String dic_id);

    List<TopicsVO> topicsUserId(@Param("user_id") String user_id, @Param("loginerId") String loginerId);

    TopicsVO topicsId(@Param("loginerId") String loginerId, @Param("topic_id") String topic_id);

    void topicAtRead(@Param("topic_id") String topic_id, @Param("loginerId") String loginerId);

    List<TopicsCommentsVO> topicsComments(@Param("topic_id") String topic_id);

    Integer countToday(@Param("start_date") String start_date, @Param("end_date") String end_date);

    List<TopicsBackendVO> topicsBackendVO(@Param("start_date") String start_date, @Param("end_date") String end_date,
                                          @Param("topic_valid") String topic_valid, @Param("user_name") String user_name);

    void topicsShield(@Param("topic_ids") List<String> topic_ids);

    void topicsUnShield(@Param("topic_ids") List<String> topic_ids);

    void topicsRecommend(@Param("topic_ids") List<String> topic_ids);

    void topicsUnRecommend(@Param("topic_ids") List<String> topic_ids);

    void topicsDelete(@Param("topic_ids") List<String> topic_ids);
}
