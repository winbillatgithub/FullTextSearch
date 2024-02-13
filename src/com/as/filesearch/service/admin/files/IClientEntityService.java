package com.as.filesearch.service.admin.files;

import java.util.List;
import java.util.Map;

import com.as.filesearch.base.service.IBaseService;
import com.as.filesearch.entity.ClientEntity;

public interface IClientEntityService extends IBaseService<ClientEntity, Integer> {
	int selectAllCount(Map<String, Object> paramMap);
	List<ClientEntity> selectByPartName(String type, String name);
}
