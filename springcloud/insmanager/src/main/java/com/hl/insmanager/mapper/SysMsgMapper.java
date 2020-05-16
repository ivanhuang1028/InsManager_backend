package com.hl.insmanager.mapper;

import com.hl.insmanager.module.SysMsg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ivan.huang
 */
@Mapper
public interface SysMsgMapper<T> extends BaseMapper<T> {

    Integer sysMsgsCount(@Param("user_id") String user_id);

    Integer countAll();

    SysMsg getFirst();


}
