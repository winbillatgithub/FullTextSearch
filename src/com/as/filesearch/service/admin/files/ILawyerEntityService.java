package com.as.filesearch.service.admin.files;

import java.util.List;
import java.util.Map;

import com.as.filesearch.base.service.IBaseService;
import com.as.filesearch.entity.LawyerEntity;

public interface ILawyerEntityService extends IBaseService<LawyerEntity, Integer> {
	int selectAllCount(Map<String, Object> paramMap);
	List<LawyerEntity> selectByPartName(String name);
}
