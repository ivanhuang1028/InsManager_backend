package com.hl.insmanager.service.imp;

import com.hl.insmanager.mapper.BaseMapper;
import com.hl.insmanager.mapper.TopicMapper;
import com.hl.insmanager.service.TopicService;
import com.hl.insmanager.vo.topic.TopicsCommentsVO;
import com.hl.insmanager.vo.topic.TopicsImagesVO;
import com.hl.insmanager.vo.topic.TopicsVO;
import com.hl.insmanager.vo.topic.TopicsVideoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ivan.huang
 */
@Slf4j
@Service("topicService")
public class TopicServiceImp<T> extends BaseServiceImp<T> implements TopicService<T> {

    @Override
    public TopicMapper<T> getMapper() {
        return (TopicMapper<T>) mapper;
    }

    @Override
    @Autowired
    public void setMapper(@Qualifier("topicMapper") BaseMapper<T> mapper) {
        super.setMapper(mapper);
    }

    @Override
    public List<TopicsVO> topics(String user_id) {
        return getMapper().topics(user_id);
    }

    @Override
    public List<TopicsImagesVO> topicsImagesVO(String topic_id) {
        return getMapper().topicsImagesVO(topic_id);
    }

    @Override
    public Integer countTopicsVideo(String dic_id) {
        return getMapper().countTopicsVideo(dic_id);
    }

    @Override
    public List<TopicsVideoVO> topicsVideo(Integer ran, Integer video_num, String dic_id) {
        return getMapper().topicsVideo(ran, video_num, dic_id);
    }

    @Override
    public Integer countTopicsPic(String dic_id) {
        return getMapper().countTopicsPic(dic_id);
    }

    @Override
    public List<TopicsVideoVO> topicsPic(Integer ran, Integer pic_num, String dic_id) {
        return getMapper().topicsPic(ran, pic_num, dic_id);
    }

    @Override
    public List<TopicsVO> topicsUserId(String user_id, String loginerId) {
        return getMapper().topicsUserId(user_id, loginerId);
    }

    @Override
    public TopicsVO topicsId(String loginerId, String topic_id) {
        return getMapper().topicsId(loginerId, topic_id);
    }

    @Override
    public void topicAtRead(String topic_id, String loginerId) {
        getMapper().topicAtRead(topic_id, loginerId);
    }

    @Override
    public List<TopicsCommentsVO> topicsComments(String topic_id) {
        return getMapper().topicsComments(topic_id);
    }

}
