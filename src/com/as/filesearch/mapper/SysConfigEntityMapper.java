package com.as.filesearch.mapper;

import java.util.List;

import com.as.filesearch.base.mapper.BaseMapper;
import com.as.filesearch.entity.SysConfigEntity;

public interface SysConfigEntityMapper extends BaseMapper<SysConfigEntity, Integer> {
	  List<SysConfigEntity> selectAll();
}