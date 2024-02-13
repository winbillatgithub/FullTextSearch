package com.as.filesearch.service.admin.files.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.as.filesearch.base.service.impl.BaseService;
import com.as.filesearch.entity.SysCaseTypeEntity;
import com.as.filesearch.mapper.SysCaseTypeEntityMapper;
import com.as.filesearch.service.admin.files.ISysCaseTypeEntityService;

@Repository(value="SysCaseTypeEntityService")
public class CaseTypeEntityServiceImpl extends BaseService<SysCaseTypeEntity, Integer>  implements ISysCaseTypeEntityService {

	@Autowired
	private SysCaseTypeEntityMapper caseTypeEntityMapper;
	
	@Autowired
	public void setBaseMapper() {
		// TODO Auto-generated method stub
		super.setBaseMapper(caseTypeEntityMapper);
	}
	
	@Override
	public SysCaseTypeEntity selectByPrimaryKey(Integer id){
		return caseTypeEntityMapper.selectByPrimaryKey(id);
	}


	@Override
	public int updateByPrimaryKey(SysCaseTypeEntity record) {
		return caseTypeEntityMapper.updateByPrimaryKey(record);
	}

	@Override
	public int insert(SysCaseTypeEntity record) {
		// TODO Auto-generated method stub
		return caseTypeEntityMapper.insert(record);
	}

	@Override
	public List<SysCaseTypeEntity> selectAll() {
		// TODO Auto-generated method stub
		return caseTypeEntityMapper.selectAll();
	}

	@Override
	public Map<String, Object> selectAllByPagination(Map<String, Object> param) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		int resultCount = caseTypeEntityMapper.selectAllCountByPagination(param);
		List<SysCaseTypeEntity> resultList = caseTypeEntityMapper.selectAllByPagination(param);
		returnMap.put("total", resultCount);
		returnMap.put("rows", resultList);
		return returnMap;
	}
	public List<SysCaseTypeEntity> selectByName(String name) {
		return caseTypeEntityMapper.selectByName(name);
	}

	@Override
	public List<SysCaseTypeEntity> selectAllCaseTypeEntity() {
		// TODO Auto-generated method stub
		return caseTypeEntityMapper.selectAllCaseTypeEntity();
	}
}
