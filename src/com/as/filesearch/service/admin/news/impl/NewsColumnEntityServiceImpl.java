package com.as.filesearch.service.admin.news.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.as.filesearch.base.service.impl.BaseService;
import com.as.filesearch.entity.NewsColumnEntity;
import com.as.filesearch.entity.PortalUserEntity;
import com.as.filesearch.mapper.NewsColumnEntityMapper;
import com.as.filesearch.service.admin.news.INewsColumnEntityService;

@Repository(value="newsColumnEntityService")
public class NewsColumnEntityServiceImpl extends BaseService<NewsColumnEntity, Long>  implements INewsColumnEntityService {

	@Autowired
	private NewsColumnEntityMapper newsColumnEntityMapper;
	
	@Autowired
	public void setBaseMapper() {
		// TODO Auto-generated method stub
		super.setBaseMapper(newsColumnEntityMapper);
	}

	@Override
	public List<NewsColumnEntity> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> selectAllByPagination(Map<String, Object> param) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		int resultCount = newsColumnEntityMapper.selectAllCountByPagination(param);
		List<NewsColumnEntity> resultList = newsColumnEntityMapper.selectAllByPagination(param);
		returnMap.put("total", resultCount);
		returnMap.put("rows", resultList);
		return returnMap;
	}

	@Override
	public List<NewsColumnEntity> selectAllDistinctNewsColumns() {
		return newsColumnEntityMapper.selectAllDistinctNewsColumns();
	}

}
