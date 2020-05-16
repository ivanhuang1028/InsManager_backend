package com.hl.insmanager.service;

import com.hl.insmanager.mapper.UserCollectMapper;
import com.hl.insmanager.vo.usercollect.UserCollectVO;

import java.util.List;

public interface UserCollectService<T> extends BaseService<T>, UserCollectMapper<T> {

    List<UserCollectVO> collects(String loginerId);
}
