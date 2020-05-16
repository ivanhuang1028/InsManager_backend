package com.hl.insmanager.service;

import com.hl.insmanager.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BaseService<T> extends BaseMapper<T> {

     Integer deleteByPrimaryKeys(Object... keys) throws Exception;  //通过多个id删除对象

}
