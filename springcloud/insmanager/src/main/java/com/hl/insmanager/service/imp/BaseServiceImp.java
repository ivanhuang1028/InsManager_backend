package com.hl.insmanager.service.imp;

import com.hl.common.constants.QueryParam;
import com.hl.insmanager.mapper.BaseMapper;
import com.hl.insmanager.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ivan.huang
 */
@Slf4j
@Mapper
public abstract class BaseServiceImp<T> implements BaseService<T> {

    protected BaseMapper<T> mapper;

    public BaseMapper<T> getMapper() {
        return mapper;
    }

    public void setMapper(BaseMapper<T> mapper) {
        this.mapper = mapper;
    }

    public static final String CACHE_NAME = "web";

    /**
     * 1.  通过主键查询实体
     */
    @Override
    public T selectByPrimaryKey(Object key) {
        try {
            return mapper.selectByPrimaryKey(key);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * 2.  通过实体精准查询实体
     */
    @Override
    public T selectByEqualT(T t) {
        try {
            return mapper.selectByEqualT(t);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * 3. 插入对象数据
     */
    @Override
    public Integer insert(T t) {
        try {
            return mapper.insert(t);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * 4. 对象的更新
     */
    @Override
    public Integer updateByPrimaryKey(T t) {
        try {
            return mapper.updateByPrimaryKey(t);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    /***
     *  5. 简单模糊查询
     */
    @Override
    public List<T> selectByBlurryT(T blurryT) {
        try {
            return mapper.selectByBlurryT(blurryT);
        } catch (Exception e) {
            log.error("", e);

        }
        return new ArrayList<T>();
    }

    /**
     * 6. 简单模糊查询列表总数
     */
    @Override
    public Integer countByBlurryT(T t, QueryParam queryParam) {
        try {

            return mapper.countByBlurryT(t, queryParam);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * 7. 通过id删除对象
     */
    @Override
    public Integer deleteByPrimaryKey(Object key) {
        try {
            return mapper.deleteByPrimaryKey(key);
        } catch (Exception e) {
            log.error("", e);
            return null;
        }

    }

    /**
     * 8. 精准删除对象
     */
    @Override
    public Integer deleteByT(T blurryT) {
        try {
            return mapper.deleteByT(blurryT);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * 9. 模糊删除对象
     */
    @Override
    public Integer deleteByBlurryT(T blurryT) {
        try {
            return mapper.deleteByBlurryT(blurryT);
        } catch (Exception e) {
            log.error("", e);

        }
        return null;
    }

    /**
     * 通过多个id删除对象
     */
    @Override
    public Integer deleteByPrimaryKeys(Object... keys) {
        int i = 0;
        try {
            for (Object key : keys) {
                i += i + getMapper().deleteByPrimaryKey(key);
            }
            return i;
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }
    @Override
    public Integer deleteAll() {
        try {
            return mapper.deleteAll();
        } catch (Exception e) {
            log.error("", e);

        }
        return null;
    }
    @Override
    public List<T> listByModel(T t, QueryParam queryParam) {
        try {
            //t为空则拋错
            return mapper.listByModel(t, queryParam);
        } catch (Exception e) {
            log.error("", e);

        }
        return null;
    }

    @Override
    public List<T> listAll() {
        return listByModel(null, null);
    }
}
