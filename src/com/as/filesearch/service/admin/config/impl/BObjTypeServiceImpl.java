package com.as.filesearch.service.admin.config.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.as.filesearch.base.service.impl.BaseService;
import com.as.filesearch.entity.ObjType;
import com.as.filesearch.mapper.ObjTypeMapper;
import com.as.filesearch.service.admin.config.IBObjTypeService;

@Repository(value="bObjTypeServiceImpl")
public class  BObjTypeServiceImpl extends BaseService<ObjType, String>  implements IBObjTypeService {

	@Autowired
	private ObjTypeMapper bObjTypeMapper;
	
	@Autowired
	@Override
	public void setBaseMapper() {
		// TODO Auto-generated method stub
		super.setBaseMapper(bObjTypeMapper);
	}

	@Override
	//@SystemServiceSelectLog(description = "查询基础数据")
	public List<ObjType> selectBObjTypeByTypeType(Map<String, Object> param) {
		return bObjTypeMapper.selectBObjTypeByTypeType(param);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByPrimaryKeys(String[] ids) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(ObjType record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(ObjType record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ObjType selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(ObjType record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(ObjType record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(ObjType record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ObjType> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> selectAllByPagination(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

}
