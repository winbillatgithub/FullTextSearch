package com.as.filesearch.service.admin.config;

import java.util.List;

import com.as.filesearch.base.service.IBaseService;
import com.as.filesearch.entity.SysConfigEntity;

public interface ISysConfigEntityService  extends IBaseService<SysConfigEntity, Integer>{
	List<SysConfigEntity> selectAllSysConfigEntity();
}
