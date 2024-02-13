package com.as.filesearch.service.admin.user.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.as.filesearch.base.service.impl.BaseService;
import com.as.filesearch.entity.PortalUserEntity;
import com.as.filesearch.mapper.PortalUserEntityMapper;
import com.as.filesearch.service.admin.user.IPortalUserEntityService;

@Repository(value="portalUserEntityService")
public class PortalUserEntityServiceImpl extends BaseService<PortalUserEntity, Integer>  implements IPortalUserEntityService {

	@Autowired
	private PortalUserEntityMapper portalUserEntityMapper;
	
	@Autowired
	public void setBaseMapper() {
		// TODO Auto-generated method stub
		super.setBaseMapper(portalUserEntityMapper);
	}
	
	@Override
	public PortalUserEntity selectByPrimaryKey(Integer id){
		return portalUserEntityMapper.selectByPrimaryKey(id);
	}


	@Override
	public int updateByPrimaryKey(PortalUserEntity record) {
		return portalUserEntityMapper.updateByPrimaryKey(record);
	}

	@Override
	public int insert(PortalUserEntity record) {
		// TODO Auto-generated method stub
		return portalUserEntityMapper.insert(record);
	}

	@Override
	public List<PortalUserEntity> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> selectAllByPagination(Map<String, Object> param) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		int resultCount = portalUserEntityMapper.selectAllCountByPagination(param);
		List<PortalUserEntity> resultList = portalUserEntityMapper.selectAllByPagination(param);
		returnMap.put("total", resultCount);
		returnMap.put("rows", resultList);
		return returnMap;
	}

	@Override
	public PortalUserEntity selectByUserName(String loginUserName) {
		
		return portalUserEntityMapper.selectByUserName(loginUserName);
	}

	@Override
	public PortalUserEntity loginWithUserNameOrEmail(String loginUserName) {
		return portalUserEntityMapper.loginWithUserNameOrEmail(loginUserName);
	}

	@Override
	public int registerUser(PortalUserEntity record) {
		return portalUserEntityMapper.registerUser(record);
	}

	@Override
	public PortalUserEntity checkUserName(Map<String, Object> param){
		return portalUserEntityMapper.checkUserName(param);
	}

	@Override
	public int updateUserDetail(Map<String, Object> param){
		return portalUserEntityMapper.updateUserDetail(param);
	}
	
	@Override
	public int updateUserPassword(Map<String, Object> param) {
		return portalUserEntityMapper.updateUserPassword(param);
	}
}
