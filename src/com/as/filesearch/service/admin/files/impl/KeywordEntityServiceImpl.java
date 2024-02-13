package com.as.filesearch.service.admin.files.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.as.filesearch.base.service.impl.BaseService;
import com.as.filesearch.entity.KeywordEntity;
import com.as.filesearch.mapper.KeywordEntityMapper;
import com.as.filesearch.service.admin.files.IKeywordEntityService;

@Repository(value="KeywordEntityService")
public class KeywordEntityServiceImpl extends BaseService<KeywordEntity, Integer> implements IKeywordEntityService {

	@Autowired
	private KeywordEntityMapper mapper;

	@Autowired
	public void setBaseMapper() {
		// TODO Auto-generated method stub
		super.setBaseMapper(mapper);
	}

	@Override
	public List<KeywordEntity> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int selectAllCount(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return mapper.selectAllCount(paramMap);
	}

	@Override
	public int insert(KeywordEntity record) {
		return mapper.insert(record);
	}

	@Override
	public List<KeywordEntity> selectByName(String name) {
		// TODO Auto-generated method stub
		return mapper.selectByName(name);
	}

	@Override
	public Map<String, Object> selectAllByPagination(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}
}
