package com.hl.insmanager.mapper;

import com.hl.insmanager.vo.usercollect.UserCollectVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ivan.huang
 */
@Mapper
public interface UserCollectMapper<T> extends BaseMapper<T> {

    List<UserCollectVO> collects(@Param("loginerId") String loginerId);

}
