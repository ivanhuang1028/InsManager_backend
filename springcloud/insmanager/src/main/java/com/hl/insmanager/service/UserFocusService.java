package com.hl.insmanager.service;

import com.hl.insmanager.mapper.UserFocusMapper;
import com.hl.insmanager.vo.userfocus.UserFocusTosVO;
import java.util.List;

public interface UserFocusService<T> extends BaseService<T>, UserFocusMapper<T> {

    public List<UserFocusTosVO> focusTos(String focus_from);

    void read(String user_id, String loginerId);

    void topicRead(String loginerId, String user_id);
}
