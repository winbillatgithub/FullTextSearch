package com.as.filesearch.service.admin.menu.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.as.filesearch.base.service.impl.BaseService;
import com.as.filesearch.entity.SysMenuEntity;
import com.as.filesearch.mapper.SysMenuEntityMapper;
import com.as.filesearch.service.admin.menu.ISysMenuEntityService;

@Repository(value="sysMenuEntityService")
public class SysMenuEntityServiceImpl extends BaseService<SysMenuEntity, Integer>  implements ISysMenuEntityService{

	@Autowired
	private SysMenuEntityMapper sysMenuEntityMapper;
	
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(sysMenuEntityMapper);
	}


	@Override
	public List<SysMenuEntity> selectAll() {
		List<SysMenuEntity> sysMenuList = sysMenuEntityMapper.selectAll();
		return sysMenuList;
	}

	@Override
	public Map<String, Object> selectAllByPagination(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}


	

}
