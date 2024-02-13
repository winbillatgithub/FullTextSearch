package com.as.filesearch.mapper;

import java.util.List;
import java.util.Map;

import com.as.filesearch.base.mapper.BaseMapper;
import com.as.filesearch.entity.CourtEntity;

public interface CourtEntityMapper extends BaseMapper<CourtEntity, Integer> {
	int selectAllCount(Map<String, Object> param);
	List<CourtEntity> selectByFullName(String fullName);
	List<CourtEntity> selectAllByPagination (Map<String, Object> param);
	List<CourtEntity> selectByPartName(String fullName);
}