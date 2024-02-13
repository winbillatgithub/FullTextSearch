package com.as.filesearch.mapper;

import java.util.List;
import java.util.Map;

import com.as.filesearch.base.mapper.BaseMapper;
import com.as.filesearch.entity.NewsInfoEntity;

public interface NewsInfoEntityMapper extends BaseMapper<NewsInfoEntity, Long> {
	
	int selectAllCountByPagination_Portal(Map<String,Object> param);
	List<NewsInfoEntity> selectAllByPagination_Portal(Map<String,Object> param);
}