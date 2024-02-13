package com.as.filesearch.service.admin.files.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.as.filesearch.base.service.impl.BaseService;
import com.as.filesearch.entity.SysCaseCauseEntity;
import com.as.filesearch.service.admin.files.ISysCaseCauseEntityService;
import com.as.filesearch.mapper.SysCaseCauseEntityMapper;

@Repository(value="SysCaseCauseEntityService")
public class SysCaseCauseServiceImpl extends BaseService<SysCaseCauseEntity, Integer>  implements ISysCaseCauseEntityService{

	@Autowired
	private SysCaseCauseEntityMapper sysCaseCauseEntityMapper;

	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(sysCaseCauseEntityMapper);
	}

	@Override
	public List<SysCaseCauseEntity> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SysCaseCauseEntity> selectAllCaseCauseEntity() {
		// TODO Auto-generated method stub
		List<SysCaseCauseEntity> sysMenuList = sysCaseCauseEntityMapper.selectAll();
		return sysMenuList;
	}

	@Override
	public Map<String, Object> selectAllByPagination(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<SysCaseCauseEntity> selectByName(String name) {
		return sysCaseCauseEntityMapper.selectByName(name);
	}

	public List<SysCaseCauseEntity> selectByPartName(String name) {
		return sysCaseCauseEntityMapper.selectByPartName(name);
	}
}
