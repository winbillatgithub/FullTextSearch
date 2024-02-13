package com.as.filesearch.mapper;

import java.util.List;
import java.util.Map;

import com.as.filesearch.base.mapper.BaseMapper;
import com.as.filesearch.entity.JudgeEntity;

public interface JudgeEntityMapper extends BaseMapper<JudgeEntity, Integer> {
	int selectAllCount(Map<String, Object> param);
	List<JudgeEntity> selectAllByPagination (Map<String, Object> param);
	List<JudgeEntity> selectByPartName (Map<String, Object> param);
}