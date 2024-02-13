package com.as.filesearch.mapper;

import java.util.Map;

import com.as.filesearch.base.mapper.BaseMapper;
import com.as.filesearch.entity.AdminAreaEntity;

public interface AdminAreaEntityMapper extends BaseMapper<AdminAreaEntity, Integer> {
	int selectAllCount(Map<String, Object> param);
}