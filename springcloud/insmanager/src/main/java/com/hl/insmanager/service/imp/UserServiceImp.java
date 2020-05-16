package com.hl.insmanager.service.imp;

import com.hl.insmanager.mapper.BaseMapper;
import com.hl.insmanager.mapper.UserMapper;
import com.hl.insmanager.service.UserService;
import com.hl.insmanager.vo.user.UserDetailInfoVO;
import com.hl.insmanager.vo.user.UserInfoVO;
import com.hl.insmanager.vo.user.UserLabelVO;
import com.hl.insmanager.vo.user.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ivan.huang
 */
@Slf4j
@Service("userService")
public class UserServiceImp<T> extends BaseServiceImp<T> implements UserService<T> {

    @Override
    public UserMapper<T> getMapper() {
        return (UserMapper<T>) mapper;
    }

    @Override
    @Autowired
    public void setMapper(@Qualifier("userMapper") BaseMapper<T> mapper) {
        super.setMapper(mapper);
    }


    @Override
    public Integer isBefore(String logierId, String user_id) {
        return getMapper().isBefore(logierId, user_id);
    }

    @Override
    public List<UserVO> focusTos(String loginerId, String key) {
        return getMapper().focusTos(loginerId, key);
    }

    @Override
    public List<UserVO> users(String loginerId, String key) {
        return getMapper().users(loginerId, key);
    }

    @Override
    public List<UserVO> usersLabel(String loginerId, List<String> labelIds) {
        return getMapper().usersLabel(loginerId, labelIds);
    }

    @Override
    public List<UserVO> usersRegion(String loginerId, String key) {
        return getMapper().usersRegion(loginerId, key);
    }

    @Override
    public UserInfoVO usersInfo(String user_id) {
        return getMapper().usersInfo(user_id);
    }

    @Override
    public UserDetailInfoVO usersDetailInfo(String loginerId) {
        return getMapper().usersDetailInfo(loginerId);
    }

    @Override
    public List<UserLabelVO> usersLabels(String loginerId) {
        return getMapper().usersLabels(loginerId);
    }

    @Override
    public List<UserVO> usersRecommend(String loginerId) {
        return getMapper().usersRecommend(loginerId);
    }
}
