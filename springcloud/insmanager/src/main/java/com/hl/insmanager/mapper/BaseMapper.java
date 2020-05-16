package com.hl.insmanager.mapper;

import com.hl.common.constants.QueryParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Mapper基本接口，定义公用接口，其他Mapper继承
 *
 * @author ivan.huang
 */
@Mapper
public interface BaseMapper<T> {

    T selectByPrimaryKey(Object key) throws Exception;  // 1. 通过id获取对象

    T selectByEqualT(@Param(value = "t") T t) throws Exception;  //2. 通过多个字段匹配获取一个对象

    Integer insert(T t) throws Exception;    //3. 插入对象数据

    Integer updateByPrimaryKey(T t) throws Exception;  //4. 对象的更新

    List<T> selectByBlurryT(@Param(value = "t") T blurryT);  //5. 简单模糊查询

    Integer countByBlurryT(@Param(value = "t") T blurryT) throws Exception;  //6. 简单模糊查询列表总数

    Integer deleteByPrimaryKey(Object key) throws Exception;  //7. 通过id删除对象

    Integer deleteByT(@Param(value = "t") T t) throws Exception;   //8. 精准删除对象

    Integer deleteByBlurryT(@Param(value = "t") T t) throws Exception;   //9. 模糊删除对象

    Integer deleteAll() throws Exception;

    List<T> listByModel(@Param(value = "t") T t, @Param(value = "queryParam") QueryParam queryParam) throws Exception;

    List<T> listAll();
}
