package com.as.filesearch.service.admin.files.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.as.filesearch.base.service.impl.BaseService;
import com.as.filesearch.entity.ClientEntity;
import com.as.filesearch.mapper.ClientEntityMapper;
import com.as.filesearch.service.admin.files.IClientEntityService;

@Repository(value="ClientEntityService")
public class ClientEntityServiceImpl extends BaseService<ClientEntity, Integer> implements IClientEntityService {

	@Autowired
	private ClientEntityMapper mapper;

	@Autowired
	public void setBaseMapper() {
		// TODO Auto-generated method stub
		super.setBaseMapper(mapper);
	}

	@Override
	public List<ClientEntity> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> selectAllByPagination(Map<String, Object> param) {
		// TODO Auto-generated method stub
		Map<String, Object> returnMap = new HashMap<String, Object>();
		int resultCount = mapper.selectAllCount(param);
		List<ClientEntity> resultList = mapper.selectAllByPagination(param);
		returnMap.put("total", resultCount);
		returnMap.put("rows", resultList);
		return returnMap;
	}

	@Override
	public List<ClientEntity> selectByPartName(String type, String name) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("type", type);
		paramMap.put("name", name);
		return mapper.selectByPartName(paramMap);
	}
	@Override
	public int selectAllCount(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return mapper.selectAllCount(paramMap);
	}

	@Override
	public int insert(ClientEntity record) {
		return mapper.insert(record);
	}
}
