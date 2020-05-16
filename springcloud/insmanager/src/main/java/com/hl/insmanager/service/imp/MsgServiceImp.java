package com.hl.insmanager.service.imp;

import com.hl.insmanager.mapper.BaseMapper;
import com.hl.insmanager.mapper.MsgMapper;
import com.hl.insmanager.service.MsgService;
import com.hl.insmanager.vo.msg.MsgActionVO;
import com.hl.insmanager.vo.msg.MsgVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ivan.huang
 */
@Slf4j
@Service("msgService")
public class MsgServiceImp<T> extends BaseServiceImp<T> implements MsgService<T> {

    @Override
    public MsgMapper<T> getMapper() {
        return (MsgMapper<T>) mapper;
    }

    @Override
    @Autowired
    public void setMapper(@Qualifier("msgMapper") BaseMapper<T> mapper) {
        super.setMapper(mapper);
    }


    @Override
    public List<MsgVO> hisMess(String loginerId, String msg_user_id, String history_msg_time) {
        return getMapper().hisMess(loginerId, msg_user_id, history_msg_time);
    }

    @Override
    public List<MsgVO> newMess(String loginerId, String msg_user_id, String new_msg_time) {
        return getMapper().newMess(loginerId, msg_user_id, new_msg_time);
    }

    @Override
    public void updateMsgRead(String loginerId, String msg_user_id) {
        getMapper().updateMsgRead(loginerId, msg_user_id);
    }

    @Override
    public List<MsgActionVO> msgsAction(String loginerId) {
        return getMapper().msgsAction(loginerId);
    }

    @Override
    public List<MsgActionVO> msgsAction1(String loginerId) {
        return getMapper().msgsAction1(loginerId);
    }

    @Override
    public List<MsgActionVO> msgsAction2(String loginerId) {
        return getMapper().msgsAction2(loginerId);
    }

    @Override
    public List<MsgActionVO> msgsAction3(String loginerId) {
        return getMapper().msgsAction3(loginerId);
    }

    @Override
    public List<MsgActionVO> msgsAction4(String loginerId) {
        return getMapper().msgsAction4(loginerId);
    }
}
