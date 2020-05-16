package com.hl.insmanager.service.imp;

import com.hl.insmanager.mapper.BaseMapper;
import com.hl.insmanager.mapper.SysMsgMapper;
import com.hl.insmanager.module.SysMsg;
import com.hl.insmanager.service.SysMsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ivan.huang
 */
@Slf4j
@Service("sysMsgService")
public class SysMsgServiceImp<T> extends BaseServiceImp<T> implements SysMsgService<T> {

    @Override
    public SysMsgMapper<T> getMapper() {
        return (SysMsgMapper<T>) mapper;
    }

    @Override
    @Autowired
    public void setMapper(@Qualifier("sysMsgMapper") BaseMapper<T> mapper) {
        super.setMapper(mapper);
    }


    @Override
    public Integer sysMsgsCount(String user_id) {
        return getMapper().sysMsgsCount(user_id);
    }

    @Override
    public Integer countAll() {
        return getMapper().countAll();
    }

    @Override
    public SysMsg getFirst() {
        return getMapper().getFirst();
    }
}
