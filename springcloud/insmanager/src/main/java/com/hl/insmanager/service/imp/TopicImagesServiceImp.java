package com.hl.insmanager.service.imp;

import com.hl.insmanager.mapper.BaseMapper;
import com.hl.insmanager.mapper.TopicImagesMapper;
import com.hl.insmanager.service.TopicImagesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author ivan.huang
 */
@Slf4j
@Service("topicImagesService")
public class TopicImagesServiceImp<T> extends BaseServiceImp<T> implements TopicImagesService<T> {

    @Override
    public TopicImagesMapper<T> getMapper() {
        return (TopicImagesMapper<T>) mapper;
    }

    @Override
    @Autowired
    public void setMapper(@Qualifier("topicImagesMapper") BaseMapper<T> mapper) {
        super.setMapper(mapper);
    }


}
