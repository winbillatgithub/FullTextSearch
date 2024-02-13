package com.as.filesearch.mapper;

import java.util.List;
import java.util.Map;

import com.as.filesearch.base.mapper.BaseMapper;
import com.as.filesearch.entity.LawyerEntity;

public interface LawyerEntityMapper extends BaseMapper<LawyerEntity, Integer> {
	int selectAllCount(Map<String, Object> param);
	List<LawyerEntity> selectAllByPagination (Map<String, Object> param);
	List<LawyerEntity> selectByPartName(String name);
}