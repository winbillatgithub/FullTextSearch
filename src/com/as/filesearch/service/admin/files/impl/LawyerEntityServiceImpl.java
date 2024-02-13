package com.as.filesearch.service.admin.files.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.as.filesearch.base.service.impl.BaseService;
import com.as.filesearch.entity.LawyerEntity;
import com.as.filesearch.mapper.LawyerEntityMapper;
import com.as.filesearch.service.admin.files.ILawyerEntityService;

@Repository(value="LawyerEntityService")
public class LawyerEntityServiceImpl extends BaseService<LawyerEntity, Integer> implements ILawyerEntityService {

	@Autowired
	private LawyerEntityMapper mapper;

	@Autowired
	public void setBaseMapper() {
		// TODO Auto-generated method stub
		super.setBaseMapper(mapper);
	}

	@Override
	public List<LawyerEntity> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> selectAllByPagination(Map<String, Object> param) {
		// TODO Auto-generated method stub
		Map<String, Object> returnMap = new HashMap<String, Object>();
		int resultCount = mapper.selectAllCount(param);
		List<LawyerEntity> resultList = mapper.selectAllByPagination(param);
		returnMap.put("total", resultCount);
		returnMap.put("rows", resultList);
		return returnMap;
	}

	@Override
	public List<LawyerEntity> selectByPartName(String name) {
		return mapper.selectByPartName(name);
	}
	@Override
	public int selectAllCount(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return mapper.selectAllCount(paramMap);
	}

	@Override
	public int insert(LawyerEntity record) {
		return mapper.insert(record);
	}
}
