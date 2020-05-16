package com.hl.insmanager.service.imp;

import com.hl.insmanager.mapper.BaseMapper;
import com.hl.insmanager.mapper.TopicAtMapper;
import com.hl.insmanager.service.TopicAtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author ivan.huang
 */
@Slf4j
@Service("topicAtService")
public class TopicAtServiceImp<T> extends BaseServiceImp<T> implements TopicAtService<T> {

    @Override
    public TopicAtMapper<T> getMapper() {
        return (TopicAtMapper<T>) mapper;
    }

    @Override
    @Autowired
    public void setMapper(@Qualifier("topicAtMapper") BaseMapper<T> mapper) {
        super.setMapper(mapper);
    }


}
