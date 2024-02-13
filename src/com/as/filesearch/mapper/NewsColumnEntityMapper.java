package com.as.filesearch.mapper;

import java.util.List;

import com.as.filesearch.base.mapper.BaseMapper;
import com.as.filesearch.entity.NewsColumnEntity;

public interface NewsColumnEntityMapper extends BaseMapper<NewsColumnEntity, Long> {
	
	List<NewsColumnEntity> selectAllDistinctNewsColumns();
}