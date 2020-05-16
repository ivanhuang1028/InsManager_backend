package com.hl.insmanager.service;

import com.hl.insmanager.mapper.MsgSessionMapper;
import com.hl.insmanager.vo.msg.MsgsUsersVO;

import java.util.List;

public interface MsgSessionService<T> extends BaseService<T>, MsgSessionMapper<T> {

    List<MsgsUsersVO> msgsusers(String user_id);

    void updateUserIds(String userIds);
}
