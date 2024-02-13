package com.as.filesearch.service.admin.user;

import java.util.Map;

import com.as.filesearch.base.service.IBaseService;
import com.as.filesearch.entity.PortalUserEntity;

public interface IPortalUserEntityService extends IBaseService<PortalUserEntity, Integer>  {

	PortalUserEntity selectByUserName(String loginUserName);
	
	PortalUserEntity loginWithUserNameOrEmail(String loginUserName);
	
	int registerUser(PortalUserEntity record);

	PortalUserEntity checkUserName(Map<String, Object> param);

	int updateUserDetail(Map<String, Object> param);

	int updateUserPassword(Map<String, Object> param);
}