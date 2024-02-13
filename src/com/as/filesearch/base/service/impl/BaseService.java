package com.as.filesearch.base.service.impl;

import java.io.Serializable;

import com.as.filesearch.base.mapper.BaseMapper;
import com.as.filesearch.base.service.IBaseService;

public abstract class BaseService<T, ID extends Serializable> implements IBaseService<T, ID> {

	private BaseMapper<T, ID> baseMapper;

	
	public void setBaseMapper(BaseMapper<T, ID> baseMapper) {
		this.baseMapper = baseMapper;
	}

	@Override
	public int deleteByPrimaryKey(ID id) {
		return baseMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public int deleteByPrimaryKeys(ID[] ids){
		return baseMapper.deleteByPrimaryKeys(ids);
	}

	@Override
	public int insertSelective(T record) {
		return baseMapper.insertSelective(record);
	}

	@Override
	public T selectByPrimaryKey(ID id) {
		return baseMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(T record) {
		return baseMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(T record) {
		return baseMapper.updateByPrimaryKeyWithBLOBs(record);
	}

	@Override
	public int updateByPrimaryKey(T record) {
		return baseMapper.updateByPrimaryKey(record);
	}

	@Override
	public int insert(T record) {
		return baseMapper.insert(record);
	}

}
