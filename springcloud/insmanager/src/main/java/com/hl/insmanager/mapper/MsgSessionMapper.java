package com.hl.insmanager.mapper;

import com.hl.insmanager.vo.msg.MsgsUsersVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @author ivan.huang
 */
@Mapper
public interface MsgSessionMapper<T> extends BaseMapper<T> {

    List<MsgsUsersVO> msgsusers(@Param("user_id") String user_id);

    void updateUserIds(@Param("userIds") String userIds);

}
