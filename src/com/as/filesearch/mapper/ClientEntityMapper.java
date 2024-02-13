package com.as.filesearch.mapper;

import java.util.List;
import java.util.Map;

import com.as.filesearch.base.mapper.BaseMapper;
import com.as.filesearch.entity.ClientEntity;

public interface ClientEntityMapper extends BaseMapper<ClientEntity, Integer> {
	int selectAllCount(Map<String, Object> param);
	List<ClientEntity> selectAllByPagination (Map<String, Object> param);
	List<ClientEntity> selectByPartName (Map<String, Object> param);
}