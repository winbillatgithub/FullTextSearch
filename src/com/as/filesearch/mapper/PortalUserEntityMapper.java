package com.as.filesearch.mapper;

import java.util.Map;

import com.as.filesearch.base.mapper.BaseMapper;
import com.as.filesearch.entity.PortalUserEntity;

public interface PortalUserEntityMapper extends BaseMapper<PortalUserEntity, Integer> {
	
	PortalUserEntity selectByUserName(String loginUserName);
	
	PortalUserEntity loginWithUserNameOrEmail(String loginUserName);
	
	int registerUser(PortalUserEntity record);

	PortalUserEntity checkUserName(Map<String, Object> param);

	int updateUserDetail(Map<String, Object> param);

	int updateUserPassword(Map<String, Object> param);
}