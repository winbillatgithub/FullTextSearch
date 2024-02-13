package com.as.filesearch.service.admin.files;

import java.util.List;

import com.as.filesearch.base.service.IBaseService;
import com.as.filesearch.entity.SysCaseTypeEntity;

public interface ISysCaseTypeEntityService extends IBaseService<SysCaseTypeEntity, Integer> {
	List<SysCaseTypeEntity> selectByName(String name);
	List<SysCaseTypeEntity> selectAllCaseTypeEntity();
}
