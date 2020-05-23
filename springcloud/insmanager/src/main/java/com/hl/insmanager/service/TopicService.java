package com.hl.insmanager.service;

import com.hl.insmanager.mapper.TopicMapper;
import com.hl.insmanager.vo.backend.TopicsBackendVO;
import com.hl.insmanager.vo.topic.TopicsCommentsVO;
import com.hl.insmanager.vo.topic.TopicsImagesVO;
import com.hl.insmanager.vo.topic.TopicsVO;
import com.hl.insmanager.vo.topic.TopicsVideoVO;

import java.util.List;

public interface TopicService<T> extends BaseService<T>, TopicMapper<T> {

    public List<TopicsVO> topics(String user_id);

    List<TopicsImagesVO> topicsImagesVO(String topic_id);

    Integer countTopicsVideo(String dic_id);

    List<TopicsVideoVO> topicsVideo(Integer ran, Integer video_num, String dic_id);

    Integer countTopicsPic(String dic_id);

    List<TopicsVideoVO> topicsPic(Integer ran, Integer pic_num, String dic_id);

    List<TopicsVO> topicsUserId(String user_id, String loginerId);

    TopicsVO topicsId(String loginerId, String topic_id);

    void topicAtRead(String topic_id, String loginerId);

    List<TopicsCommentsVO> topicsComments(String topic_id);

    Integer countToday(String start_date, String end_date);

    List<TopicsBackendVO> topicsBackendVO(String start_date, String end_date, String topic_valid, String user_name);
}
