package com.as.filesearch.controller.admin.files;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.as.filesearch.base.controller.AdminBaseController;
import com.as.filesearch.base.entity.Constant;
import com.as.filesearch.base.helper.JsonHelper;
import com.as.filesearch.entity.CourtEntity;
import com.as.filesearch.service.admin.files.ICourtEntityService;

@Controller
@RequestMapping(value = "/admin/files/courtEntityController")
public class CourtEntityController  extends AdminBaseController {

	@Resource(name = "CourtEntityService")
	private ICourtEntityService service;


	@RequestMapping(value = "getCourtInfo", method = RequestMethod.POST )
	@ResponseBody
	public Map<String,Object> getCourtInfo(HttpServletRequest request, HttpServletResponse response) {
		AdminBaseController.baseLogger.trace("getCourtInfo() ==>");
		int pageId = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		String name = request.getParameter("name");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		int startNum = pageSize * (pageId - 1);
		// 起始数据参数
		paramMap.put("startNum", startNum);
		// 分页数据大小
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortItem", "area_id,name");
		paramMap.put("sortOrder", "asc");
		paramMap.put("name", name);
		Map<String,Object> paginationMap = service.selectAllByPagination(paramMap);
		String returnJsonString = JsonHelper.mapToJson(paginationMap, "yyyy-MM-dd HH:mm:ss");
		AdminBaseController.baseLogger.trace("getCourtInfo() <== returns=" + returnJsonString);
		return paginationMap;
	}

	@RequestMapping(value = "saveData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveData(HttpServletRequest request, HttpServletResponse response) {
		baseLogger.trace("saveData() ==>");

		//String pDate = request.getParameter("pDate");
		String inserted = request.getParameter("inserted");
		String updated = request.getParameter("updated");
		String deleted = request.getParameter("deleted");
		List<CourtEntity> riInserted = null;
		List<CourtEntity> riUpdated = null;
		List<CourtEntity> riDeleted = null;

		int userId = 0;
		Date currentDate = new Date();

		try {
			if (inserted != null) {
				riInserted = JsonHelper.jsonToBeanList(String.valueOf(inserted), CourtEntity.class);
				// 调用插入方法
				for (int index = 0; index < riInserted.size(); index++) {
					CourtEntity ri = riInserted.get(index);
					ri.setCreateId(userId);
					ri.setCreateTime(currentDate);
					service.insertSelective(ri);
				}
			}
			if (updated != null) {
				riUpdated = JsonHelper.jsonToBeanList(String.valueOf(updated), CourtEntity.class);
				// 调用更新方法
				for (int index = 0; index < riUpdated.size(); index++) {
					CourtEntity ri = riUpdated.get(index);
					ri.setCreateId(userId);
					ri.setCreateTime(currentDate);
					service.updateByPrimaryKey(ri);
				}
			}
			if (deleted != null) {
				riDeleted = JsonHelper.jsonToBeanList(String.valueOf(deleted), CourtEntity.class);
				// 调用删除方法
				for (int index = 0; index < riDeleted.size(); index++) {
					CourtEntity ri = riDeleted.get(index);
					service.deleteByPrimaryKey(ri.getId());
				}
			}
		} catch (Exception e) {
			return jsonResult(false, "错误信息:" + e.getMessage());
		}
		baseLogger.trace("saveData() <==");
		return jsonResult(true, "保存成功");
	}
}