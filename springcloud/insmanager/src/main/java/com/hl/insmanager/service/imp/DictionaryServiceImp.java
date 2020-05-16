package com.hl.insmanager.service.imp;

import com.hl.insmanager.mapper.BaseMapper;
import com.hl.insmanager.mapper.DictionaryMapper;
import com.hl.insmanager.module.Dictionary;
import com.hl.insmanager.service.DictionaryService;
import com.hl.insmanager.service.imp.BaseServiceImp;
import com.hl.insmanager.vo.dic.DicVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author ivan.huang
 */
@Slf4j
@Service("dictionaryService")
public class DictionaryServiceImp<T> extends BaseServiceImp<T> implements DictionaryService<T> {

    @Override
    public DictionaryMapper<T> getMapper() {
        return (DictionaryMapper<T>) mapper;
    }

    @Override
    @Autowired
    public void setMapper(@Qualifier("dictionaryMapper") BaseMapper<T> mapper) {
        super.setMapper(mapper);
    }


    @Override
    public List<DicVO> queryByName(String dic_name) {
        return getMapper().queryByName(dic_name);
    }

    @Override
    public List<DicVO> queryUserLabels(String user_id) {
        return getMapper().queryUserLabels(user_id);
    }
}
