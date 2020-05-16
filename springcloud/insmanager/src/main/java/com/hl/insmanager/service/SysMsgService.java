package com.hl.insmanager.service;

import com.hl.insmanager.mapper.SysMsgMapper;
import com.hl.insmanager.module.SysMsg;

public interface SysMsgService<T> extends BaseService<T>, SysMsgMapper<T> {

    Integer sysMsgsCount(String user_id);

    Integer countAll();

    SysMsg getFirst();
}
