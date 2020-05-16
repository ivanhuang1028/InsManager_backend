package com.hl.insmanager.service.imp;

import com.hl.insmanager.mapper.BaseMapper;
import com.hl.insmanager.mapper.TopicLikeMapper;
import com.hl.insmanager.service.TopicLikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author ivan.huang
 */
@Slf4j
@Service("topicLikeService")
public class TopicLikeServiceImp<T> extends BaseServiceImp<T> implements TopicLikeService<T> {

    @Override
    public TopicLikeMapper<T> getMapper() {
        return (TopicLikeMapper<T>) mapper;
    }

    @Override
    @Autowired
    public void setMapper(@Qualifier("topicLikeMapper") BaseMapper<T> mapper) {
        super.setMapper(mapper);
    }


}
