package com.as.filesearch.mapper;

import java.util.List;
import java.util.Map;

import com.as.filesearch.base.mapper.BaseMapper;
import com.as.filesearch.entity.ObjType;

public interface ObjTypeMapper extends BaseMapper<ObjType, String> {
	
	List<ObjType> selectBObjTypeByTypeType(Map<String,Object> param);
}