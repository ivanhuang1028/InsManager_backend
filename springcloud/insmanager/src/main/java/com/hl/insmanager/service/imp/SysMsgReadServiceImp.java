package com.hl.insmanager.service.imp;

import com.hl.insmanager.mapper.BaseMapper;
import com.hl.insmanager.mapper.SysMsgReadMapper;
import com.hl.insmanager.service.SysMsgReadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author ivan.huang
 */
@Slf4j
@Service("sysMsgReadService")
public class SysMsgReadServiceImp<T> extends BaseServiceImp<T> implements SysMsgReadService<T> {

    @Override
    public SysMsgReadMapper<T> getMapper() {
        return (SysMsgReadMapper<T>) mapper;
    }

    @Override
    @Autowired
    public void setMapper(@Qualifier("sysMsgReadMapper") BaseMapper<T> mapper) {
        super.setMapper(mapper);
    }


}
