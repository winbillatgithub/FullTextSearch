package com.as.filesearch.service.admin.news;

import java.util.List;

import com.as.filesearch.base.service.IBaseService;
import com.as.filesearch.entity.NewsColumnEntity;

public interface INewsColumnEntityService extends IBaseService<NewsColumnEntity, Long>  {
	List<NewsColumnEntity> selectAllDistinctNewsColumns();
}
