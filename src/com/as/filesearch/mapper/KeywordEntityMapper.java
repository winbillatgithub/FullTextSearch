package com.as.filesearch.mapper;

import java.util.List;
import java.util.Map;

import com.as.filesearch.base.mapper.BaseMapper;
import com.as.filesearch.entity.KeywordEntity;

public interface KeywordEntityMapper  extends BaseMapper<KeywordEntity, Integer> {
	int selectAllCount(Map<String, Object> param);
	List<KeywordEntity> selectByName(String fullName);
}