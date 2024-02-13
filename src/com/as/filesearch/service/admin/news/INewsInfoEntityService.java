package com.as.filesearch.service.admin.news;

import java.util.Map;

import com.as.filesearch.base.service.IBaseService;
import com.as.filesearch.entity.NewsInfoEntity;

public interface INewsInfoEntityService extends IBaseService<NewsInfoEntity, Long>  {
	public Map<String, Object> selectAllByPagination_Portal(Map<String, Object> param) ;
}
