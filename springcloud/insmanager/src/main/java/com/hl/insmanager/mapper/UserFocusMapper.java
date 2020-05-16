package com.hl.insmanager.mapper;

import com.hl.insmanager.vo.userfocus.UserFocusTosVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @author ivan.huang
 */
@Mapper
public interface UserFocusMapper<T> extends BaseMapper<T> {

    List<UserFocusTosVO> focusTos(@Param("focus_from") String focus_from);

    void read(@Param("user_id") String user_id, @Param("loginerId") String loginerId);

    void topicRead(@Param("loginerId") String loginerId, @Param("user_id")String user_id);

}
