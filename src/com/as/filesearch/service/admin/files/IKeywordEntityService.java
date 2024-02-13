package com.as.filesearch.service.admin.files;

import java.util.List;
import java.util.Map;

import com.as.filesearch.base.service.IBaseService;
import com.as.filesearch.entity.KeywordEntity;

public interface IKeywordEntityService extends IBaseService<KeywordEntity, Integer> {
	int selectAllCount(Map<String, Object> paramMap);
	List<KeywordEntity> selectByName(String fullName);
}
