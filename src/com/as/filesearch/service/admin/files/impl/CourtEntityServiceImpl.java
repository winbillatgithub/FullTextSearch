package com.as.filesearch.service.admin.files.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.as.filesearch.base.service.impl.BaseService;
import com.as.filesearch.entity.CourtEntity;
import com.as.filesearch.mapper.CourtEntityMapper;
import com.as.filesearch.service.admin.files.ICourtEntityService;

@Repository(value="CourtEntityService")
public class CourtEntityServiceImpl extends BaseService<CourtEntity, Integer>  implements ICourtEntityService {

	@Autowired
	private CourtEntityMapper courtEntityMapper;
	
	@Autowired
	public void setBaseMapper() {
		// TODO Auto-generated method stub
		super.setBaseMapper(courtEntityMapper);
	}
	
	@Override
	public CourtEntity selectByPrimaryKey(Integer id){
		return courtEntityMapper.selectByPrimaryKey(id);
	}


	@Override
	public int updateByPrimaryKey(CourtEntity record) {
		return courtEntityMapper.updateByPrimaryKey(record);
	}

	@Override
	public int insert(CourtEntity record) {
		// TODO Auto-generated method stub
		return courtEntityMapper.insert(record);
	}

	@Override
	public List<CourtEntity> selectAll() {
		// TODO Auto-generated method stub
		return courtEntityMapper.selectAll();
	}

	@Override
	public Map<String, Object> selectAllByPagination(Map<String, Object> param) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		int resultCount = courtEntityMapper.selectAllCount(param);
		List<CourtEntity> resultList = courtEntityMapper.selectAllByPagination(param);
		returnMap.put("total", resultCount);
		returnMap.put("rows", resultList);
		return returnMap;
	}
	public List<CourtEntity> selectByFullName(String name) {
		return courtEntityMapper.selectByFullName(name);
	}
	public List<CourtEntity> selectByPartName(String name) {
		return courtEntityMapper.selectByPartName(name);
	}
}
