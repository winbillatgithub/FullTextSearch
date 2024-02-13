package com.as.filesearch.service.admin.files;

import java.util.List;
import java.util.Map;

import com.as.filesearch.base.service.IBaseService;
import com.as.filesearch.entity.LawFirmEntity;

public interface ILawFirmEntityService extends IBaseService<LawFirmEntity, Integer> {
	int selectAllCount(Map<String, Object> paramMap);
	List<LawFirmEntity> selectByName(String name);
	List<LawFirmEntity> selectByPartName(String name);
}
