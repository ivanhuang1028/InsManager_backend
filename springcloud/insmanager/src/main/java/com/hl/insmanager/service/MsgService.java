package com.hl.insmanager.service;

import com.hl.insmanager.mapper.MsgMapper;
import com.hl.insmanager.vo.msg.MsgActionVO;
import com.hl.insmanager.vo.msg.MsgVO;
import com.hl.insmanager.vo.msg.MsgsUsersVO;

import java.util.List;

public interface MsgService<T> extends BaseService<T>, MsgMapper<T> {

    List<MsgVO> hisMess(String loginerId, String msg_user_id, String history_msg_time);

    List<MsgVO> newMess(String loginerId, String msg_user_id, String new_msg_time);

    void updateMsgRead(String loginerId, String msg_user_id);

    List<MsgActionVO> msgsAction(String loginerId);

    List<MsgActionVO> msgsAction1(String loginerId);

    List<MsgActionVO> msgsAction2(String loginerId);

    List<MsgActionVO> msgsAction3(String loginerId);

    List<MsgActionVO> msgsAction4(String loginerId);
}
