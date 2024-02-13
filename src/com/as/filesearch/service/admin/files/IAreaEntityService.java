package com.as.filesearch.service.admin.files;

import java.util.List;

import com.as.filesearch.base.service.IBaseService;
import com.as.filesearch.entity.AdminAreaEntity;

public interface IAreaEntityService extends IBaseService<AdminAreaEntity, Integer> {
	List<AdminAreaEntity> selectByFullName(String name);
	List<AdminAreaEntity> selectByPartName(String name);
}
