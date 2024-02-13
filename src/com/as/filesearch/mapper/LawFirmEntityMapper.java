package com.as.filesearch.mapper;

import java.util.List;
import java.util.Map;

import com.as.filesearch.base.mapper.BaseMapper;
import com.as.filesearch.entity.LawFirmEntity;

public interface LawFirmEntityMapper extends BaseMapper<LawFirmEntity, Integer> {
	int selectAllCount(Map<String, Object> param);
	List<LawFirmEntity> selectByName(String fullName);
	List<LawFirmEntity> selectAllByPagination (Map<String, Object> param);
	List<LawFirmEntity> selectByPartName(String name);
}