package com.as.filesearch.service.admin.files.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.as.filesearch.base.service.impl.BaseService;
import com.as.filesearch.entity.AdminAreaEntity;
import com.as.filesearch.mapper.AdminAreaEntityMapper;
import com.as.filesearch.service.admin.files.IAreaEntityService;

@Repository(value="areaEntityService")
public class AreaEntityServiceImpl extends BaseService<AdminAreaEntity, Integer>  implements IAreaEntityService {

	@Autowired
	private AdminAreaEntityMapper mapper;
	
	@Autowired
	public void setBaseMapper() {
		// TODO Auto-generated method stub
		super.setBaseMapper(mapper);
	}
	
	@Override
	public AdminAreaEntity selectByPrimaryKey(Integer id){
		return mapper.selectByPrimaryKey(id);
	}


	@Override
	public int updateByPrimaryKey(AdminAreaEntity record) {
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int insert(AdminAreaEntity record) {
		// TODO Auto-generated method stub
		return mapper.insert(record);
	}

	@Override
	public List<AdminAreaEntity> selectAll() {
		// TODO Auto-generated method stub
		return mapper.selectAll();
	}

	@Override
	public Map<String, Object> selectAllByPagination(Map<String, Object> param) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		int resultCount = mapper.selectAllCount(param);
		List<AdminAreaEntity> resultList = mapper.selectAllByPagination(param);
		returnMap.put("total", resultCount);
		returnMap.put("rows", resultList);
		return returnMap;
	}
	public List<AdminAreaEntity> selectByFullName(String name) {
		return null;
	}
	public List<AdminAreaEntity> selectByPartName(String name) {
		return null;
	}
}
