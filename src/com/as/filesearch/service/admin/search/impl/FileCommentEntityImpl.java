package com.as.filesearch.service.admin.search.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.as.filesearch.base.service.impl.BaseService;
import com.as.filesearch.entity.FileCommentEntity;
import com.as.filesearch.mapper.FileCommentEntityMapper;
import com.as.filesearch.service.admin.search.IFileCommentEntityService;

@Repository(value="fileCommentEntityService")
public class FileCommentEntityImpl extends BaseService<FileCommentEntity, Long>  implements IFileCommentEntityService {

	@Autowired
	private FileCommentEntityMapper fileCommentEntityMapper;
	
	@Autowired
	public void setBaseMapper() {
		// TODO Auto-generated method stub
		super.setBaseMapper(fileCommentEntityMapper);
	}

	@Override
	public List<FileCommentEntity> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> selectAllByPagination(Map<String, Object> param) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		int resultCount = fileCommentEntityMapper.selectAllCountByPagination(param);
		List<FileCommentEntity> resultList = fileCommentEntityMapper.selectAllByPagination(param);
		returnMap.put("total", resultCount);
		returnMap.put("rows", resultList);
		return returnMap;
	}

}
