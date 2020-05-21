package com.hl.insmanager.mapper;

import com.hl.insmanager.vo.backend.UserBackendVO;
import com.hl.insmanager.vo.user.UserDetailInfoVO;
import com.hl.insmanager.vo.user.UserInfoVO;
import com.hl.insmanager.vo.user.UserLabelVO;
import com.hl.insmanager.vo.user.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ivan.huang
 */
@Mapper
public interface UserMapper<T> extends BaseMapper<T> {

    Integer isBefore(@Param("logierId") String logierId, @Param("user_id") String user_id);

    List<UserVO> focusTos(@Param("logierId") String logierId, @Param("key") String key);

    List<UserVO> users(@Param("logierId") String logierId, @Param("key") String key);

    List<UserVO> usersLabel(@Param("logierId") String logierId, @Param("labelIds") List<String> labelIds);

    List<UserVO> usersRegion(@Param("logierId") String loginerId, @Param("key") String key);

    UserInfoVO usersInfo(@Param("user_id") String user_id);

    UserDetailInfoVO usersDetailInfo(@Param("logierId") String loginerId);

    List<UserLabelVO> usersLabels(@Param("logierId") String loginerId);

    List<UserVO> usersRecommend(@Param("logierId") String loginerId);

    Integer countToday(@Param("start_date") String start_date, @Param("end_date") String end_date);

    List<UserBackendVO> usersBackend(@Param("start_date") String start_date, @Param("end_date") String end_date,
                                     @Param("user_gender") String user_gender, @Param("user_region") String user_region,
                                     @Param("user_school") String user_school, @Param("key") String key);

}
