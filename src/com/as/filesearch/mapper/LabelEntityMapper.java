package com.as.filesearch.mapper;

import java.util.List;
import java.util.Map;

import com.as.filesearch.base.mapper.BaseMapper;
import com.as.filesearch.entity.LabelEntity;

public interface LabelEntityMapper extends BaseMapper<LabelEntity, Integer> {
	int selectAllCount(Map<String, Object> param);
	List<LabelEntity> selectByName(Map<String, Object> param);
	List<LabelEntity> selectAllByPagination (Map<String, Object> param);
}