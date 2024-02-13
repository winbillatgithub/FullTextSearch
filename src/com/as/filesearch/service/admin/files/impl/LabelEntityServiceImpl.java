package com.as.filesearch.service.admin.files.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.as.filesearch.base.service.impl.BaseService;
import com.as.filesearch.entity.LabelEntity;
import com.as.filesearch.mapper.LabelEntityMapper;
import com.as.filesearch.service.admin.files.ILabelEntityService;

@Repository(value="LabelEntityService")
public class LabelEntityServiceImpl extends BaseService<LabelEntity, Integer> implements ILabelEntityService {

	@Autowired
	private LabelEntityMapper mapper;

	@Autowired
	public void setBaseMapper() {
		// TODO Auto-generated method stub
		super.setBaseMapper(mapper);
	}

	@Override
	public List<LabelEntity> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int selectAllCount(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return mapper.selectAllCount(paramMap);
	}

	@Override
	public int insert(LabelEntity record) {
		return mapper.insert(record);
	}

	@Override
	public List<LabelEntity> selectByName(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.selectByName(param);
	}

	@Override
	public Map<String, Object> selectAllByPagination(Map<String, Object> param) {
		// TODO Auto-generated method stub
		Map<String, Object> returnMap = new HashMap<String, Object>();
		int resultCount = mapper.selectAllCount(param);
		List<LabelEntity> resultList = mapper.selectAllByPagination(param);
		returnMap.put("total", resultCount);
		returnMap.put("rows", resultList);
		return returnMap;
	}
}
