package com.as.filesearch.service.admin.files;

import java.util.List;
import java.util.Map;

import com.as.filesearch.base.service.IBaseService;
import com.as.filesearch.entity.JudgeEntity;

public interface IJudgeEntityService extends IBaseService<JudgeEntity, Integer> {
	int selectAllCount(Map<String, Object> paramMap);
	List<JudgeEntity> selectByPartName(String type, String name);
}
