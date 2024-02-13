package com.as.filesearch.service.admin.files;

import java.util.List;

import com.as.filesearch.base.service.IBaseService;
import com.as.filesearch.entity.CourtEntity;
import com.as.filesearch.entity.UploadedFileInfoEntity;

public interface ICourtEntityService extends IBaseService<CourtEntity, Integer> {
	List<CourtEntity> selectByFullName(String name);
	List<CourtEntity> selectByPartName(String name);
}
