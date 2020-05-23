package com.hl.insmanager.service;

import com.hl.insmanager.mapper.SysMsgMapper;
import com.hl.insmanager.module.SysMsg;

import java.util.List;

public interface SysMsgService<T> extends BaseService<T>, SysMsgMapper<T> {

    Integer sysMsgsCount(String user_id);

    Integer countAll();

    SysMsg getFirst();

    void sysmsgsDelete(List<String> sys_msg_ids);
}
