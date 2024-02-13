package com.as.filesearch.service.admin.files.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.as.filesearch.base.service.impl.BaseService;
import com.as.filesearch.entity.UploadedFileInfoEntity;
import com.as.filesearch.service.admin.files.IUploadedFileInfoEntityService;
import com.as.filesearch.mapper.UploadedFileInfoEntityMapper;

@Repository(value="UploadedFileInfoEntityService")
public class UploadedFileInfoServiceImpl extends BaseService<UploadedFileInfoEntity, String>  implements IUploadedFileInfoEntityService{

	@Autowired
	private UploadedFileInfoEntityMapper fileInfoEntityMapper;

	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(fileInfoEntityMapper);
	}

	@Override
	public List<UploadedFileInfoEntity> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> selectAllByPagination(Map<String, Object> param) {
		// TODO Auto-generated method stub
		Map<String, Object> returnMap = new HashMap<String, Object>();
		int resultCount = fileInfoEntityMapper.selectAllCount(param);
		List<UploadedFileInfoEntity> resultList = fileInfoEntityMapper.selectAllByPagination(param);
		returnMap.put("total", resultCount);
		returnMap.put("rows", resultList);
		return returnMap;
	}

	@Override
	public List<UploadedFileInfoEntity> selectAllFileInfoEntity() {
		// TODO Auto-generated method stub
		List<UploadedFileInfoEntity> sysMenuList = fileInfoEntityMapper.selectAll();
		return sysMenuList;
	}

	@Override
	public int selectAllCount(Map<String, Object> paramMap) {
		return fileInfoEntityMapper.selectAllCount(paramMap);
	}
}
