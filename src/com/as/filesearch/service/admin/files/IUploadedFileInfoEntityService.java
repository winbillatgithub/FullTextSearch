package com.as.filesearch.service.admin.files;

import java.util.List;
import java.util.Map;

import com.as.filesearch.base.service.IBaseService;
import com.as.filesearch.entity.UploadedFileInfoEntity;

public interface IUploadedFileInfoEntityService extends IBaseService<UploadedFileInfoEntity, String> {

	List<UploadedFileInfoEntity> selectAllFileInfoEntity();
	int selectAllCount(Map<String, Object> paramMap);
}
