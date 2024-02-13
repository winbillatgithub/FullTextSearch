package com.as.filesearch.service.admin.files;

import java.util.List;

import com.as.filesearch.base.service.IBaseService;
import com.as.filesearch.entity.SysCaseCauseEntity;

public interface ISysCaseCauseEntityService extends IBaseService<SysCaseCauseEntity, Integer> {
	List<SysCaseCauseEntity> selectByName(String name);
	List<SysCaseCauseEntity> selectByPartName(String name);
	List<SysCaseCauseEntity> selectAllCaseCauseEntity();
}
