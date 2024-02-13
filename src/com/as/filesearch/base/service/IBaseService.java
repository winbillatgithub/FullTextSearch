package com.as.filesearch.base.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.as.filesearch.base.entity.PaginationEntity;

/**
 * DAO类接口
 * 
 * @author fengfeng_sun
 *
 * @param <T>
 * @param <K>
 */
public interface IBaseService<T, ID extends Serializable> {

	void setBaseMapper();

	int deleteByPrimaryKey(ID id);
	
	int deleteByPrimaryKeys(ID[] ids);

	int insert(T record);

	int insertSelective(T record);

	T selectByPrimaryKey(ID id);

	int updateByPrimaryKeySelective(T record);

	int updateByPrimaryKeyWithBLOBs(T record);

	int updateByPrimaryKey(T record);
	
	List<T> selectAll();
	
    Map<String,Object> selectAllByPagination(Map<String, Object> param);
}
