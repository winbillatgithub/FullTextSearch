package com.as.filesearch.mapper;

import java.util.List;
import java.util.Map;

import com.as.filesearch.base.mapper.BaseMapper;
import com.as.filesearch.entity.UploadedFileInfoEntity;

public interface UploadedFileInfoEntityMapper extends BaseMapper<UploadedFileInfoEntity, String> {
	int selectAllCount(Map<String, Object> param);
	List<UploadedFileInfoEntity> selectAllByPagination (Map<String, Object> param);
}