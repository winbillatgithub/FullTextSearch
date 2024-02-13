package com.as.filesearch.service.admin.news.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.as.filesearch.base.service.impl.BaseService;
import com.as.filesearch.entity.NewsColumnEntity;
import com.as.filesearch.entity.NewsInfoEntity;
import com.as.filesearch.mapper.NewsInfoEntityMapper;
import com.as.filesearch.service.admin.news.INewsInfoEntityService;

@Repository(value="newsInfoService")
public class NewsInfoEntityServiceImpl  extends BaseService<NewsInfoEntity, Long>  implements INewsInfoEntityService {

	@Autowired
	private NewsInfoEntityMapper newsInfoEntityMapper;
	
	@Autowired
	public void setBaseMapper() {
		// TODO Auto-generated method stub
		super.setBaseMapper(newsInfoEntityMapper);
	}

	@Override
	public List<NewsInfoEntity> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> selectAllByPagination(Map<String, Object> param) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		int resultCount = newsInfoEntityMapper.selectAllCountByPagination(param);
		List<NewsInfoEntity> resultList = newsInfoEntityMapper.selectAllByPagination(param);
		returnMap.put("total", resultCount);
		returnMap.put("rows", resultList);
		return returnMap;
	}

	@Override
	public Map<String, Object> selectAllByPagination_Portal(Map<String, Object> param) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		int resultCount = newsInfoEntityMapper.selectAllCountByPagination_Portal(param);
		List<NewsInfoEntity> resultList = newsInfoEntityMapper.selectAllByPagination_Portal(param);
		returnMap.put("total", resultCount);
		returnMap.put("rows", resultList);
		return returnMap;
	}

}
