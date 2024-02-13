package com.as.filesearch.service.admin.config.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.as.filesearch.base.entity.PaginationEntity;
import com.as.filesearch.base.service.impl.BaseService;
import com.as.filesearch.entity.SysConfigEntity;
import com.as.filesearch.entity.SysMenuEntity;
import com.as.filesearch.mapper.SysConfigEntityMapper;
import com.as.filesearch.mapper.SysMenuEntityMapper;
import com.as.filesearch.service.admin.config.ISysConfigEntityService;
import com.as.filesearch.service.admin.menu.ISysMenuEntityService;

@Repository(value="sysConfigEntityService")
public class SysConfigEntityServiceImpl extends BaseService<SysConfigEntity, Integer>  implements ISysConfigEntityService {

	@Autowired
	private SysConfigEntityMapper sysConfigEntityMapper;
	
	@Override
	public void setBaseMapper() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int insert(SysConfigEntity record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(SysConfigEntity record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKeySelective(SysConfigEntity record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(SysConfigEntity record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(SysConfigEntity record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<SysConfigEntity> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public SysConfigEntity selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SysConfigEntity> selectAllSysConfigEntity() {
		List<SysConfigEntity> sysConfigList = sysConfigEntityMapper.selectAll();
		return sysConfigList;
	}

	@Override
	public Map<String, Object> selectAllByPagination(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

}
