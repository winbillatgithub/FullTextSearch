package com.as.filesearch.service.admin.config;

import java.util.List;
import java.util.Map;

import com.as.filesearch.base.service.IBaseService;
import com.as.filesearch.entity.ObjType;

public interface IBObjTypeService  extends IBaseService<ObjType, String> 	{
	
	List<ObjType> selectBObjTypeByTypeType(Map<String, Object> param);
}
