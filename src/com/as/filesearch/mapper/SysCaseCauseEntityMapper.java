package com.as.filesearch.mapper;

import java.util.List;
import java.util.Map;

import com.as.filesearch.base.mapper.BaseMapper;
import com.as.filesearch.entity.SysCaseCauseEntity;

public interface SysCaseCauseEntityMapper extends BaseMapper<SysCaseCauseEntity, Integer> {
	int selectAllCount(Map<String, Object> param);
	List<SysCaseCauseEntity> selectByName(String fullName);
	List<SysCaseCauseEntity> selectByPartName(String fullName);
}