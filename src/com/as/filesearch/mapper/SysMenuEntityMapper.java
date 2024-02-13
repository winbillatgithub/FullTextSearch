package com.as.filesearch.mapper;

import java.util.List;

import com.as.filesearch.base.mapper.BaseMapper;
import com.as.filesearch.entity.SysMenuEntity;

public interface SysMenuEntityMapper extends BaseMapper<SysMenuEntity, Integer> {

	List<SysMenuEntity> selectAll();
}