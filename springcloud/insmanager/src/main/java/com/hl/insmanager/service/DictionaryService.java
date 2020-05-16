package com.hl.insmanager.service;

import com.hl.insmanager.mapper.DictionaryMapper;
import com.hl.insmanager.vo.dic.DicVO;

import java.util.List;

public interface DictionaryService<T> extends BaseService<T>, DictionaryMapper<T> {

    List<DicVO> queryByName(String dic_name);

    List<DicVO> queryUserLabels(String user_id);
}
