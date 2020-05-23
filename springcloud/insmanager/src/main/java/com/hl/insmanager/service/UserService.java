package com.hl.insmanager.service;

import com.hl.insmanager.mapper.UserMapper;
import com.hl.insmanager.vo.backend.UserBackend1VO;
import com.hl.insmanager.vo.backend.UserBackendVO;
import com.hl.insmanager.vo.user.UserDetailInfoVO;
import com.hl.insmanager.vo.user.UserInfoVO;
import com.hl.insmanager.vo.user.UserLabelVO;
import com.hl.insmanager.vo.user.UserVO;

import java.util.List;

public interface UserService<T> extends BaseService<T>, UserMapper<T> {

    Integer isBefore(String logierId, String user_id);

    List<UserVO> focusTos(String loginerId, String key);

    List<UserVO> users(String loginerId, String key);

    List<UserVO> usersLabel(String loginerId, List<String> labelIds);

    List<UserVO> usersRegion(String loginerId, String key);

    UserInfoVO usersInfo(String user_id);

    UserDetailInfoVO usersDetailInfo(String loginerId);

    List<UserLabelVO> usersLabels(String loginerId);

    List<UserVO> usersRecommend(String loginerId);

    Integer countToday(String start_date, String end_date);

    List<UserBackendVO> usersBackend(String start_date, String end_date, String user_gender, String user_region, String user_school, String key);

    UserBackend1VO usersBackend1(String user_id);

    void userBlack(List<String> user_ids);

    void userUnBlack(List<String> user_ids);
}
