package com.as.filesearch.service.admin.files;

import java.util.List;
import java.util.Map;

import com.as.filesearch.base.service.IBaseService;
import com.as.filesearch.entity.LabelEntity;

public interface ILabelEntityService extends IBaseService<LabelEntity, Integer> {
	int selectAllCount(Map<String, Object> paramMap);
	List<LabelEntity> selectByName(Map<String, Object> param);
	Map<String, Object> selectAllByPagination(Map<String, Object> param);
}
