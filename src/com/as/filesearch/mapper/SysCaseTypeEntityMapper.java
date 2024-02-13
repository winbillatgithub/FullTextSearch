package com.as.filesearch.mapper;

import java.util.List;
import java.util.Map;

import com.as.filesearch.base.mapper.BaseMapper;
import com.as.filesearch.entity.SysCaseTypeEntity;

public interface SysCaseTypeEntityMapper extends BaseMapper<SysCaseTypeEntity, Integer> {
	int selectAllCount(Map<String, Object> param);
	List<SysCaseTypeEntity> selectByName(String fullName);
	List<SysCaseTypeEntity> selectAllCaseTypeEntity();
}