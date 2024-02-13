package com.as.filesearch.controller.admin.files;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.as.filesearch.base.controller.AdminBaseController;
import com.as.filesearch.base.helper.JsonHelper;
import com.as.filesearch.entity.AdminAreaEntity;
import com.as.filesearch.service.admin.files.IAreaEntityService;

@Controller
@RequestMapping(value = "/admin/files/areaEntityController")
public class AreaEntityController  extends AdminBaseController {

	@Resource(name = "areaEntityService")
	private IAreaEntityService service;


	@RequestMapping(value = "/queryAreaCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryAreaCode() {
		List<AdminAreaEntity> list = service.selectAll();
		return JsonHelper.listToJson(list);
	}
/*	@RequestMapping(value = "saveData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveData(HttpServletRequest request, HttpServletResponse response) {
		baseLogger.trace("saveData() ==>");

		//String pDate = request.getParameter("pDate");
		String inserted = request.getParameter("inserted");
		String updated = request.getParameter("updated");
		String deleted = request.getParameter("deleted");
		List<AdminAreaEntity> riInserted = null;
		List<AdminAreaEntity> riUpdated = null;
		List<AdminAreaEntity> riDeleted = null;

		String userId = "0";
		Date currentDate = new Date();

		try {
			if (inserted != null) {
				riInserted = JsonHelper.jsonToBeanList(String.valueOf(inserted), AdminAreaEntity.class);
				// 调用插入方法
				for (int index = 0; index < riInserted.size(); index++) {
					AdminAreaEntity ri = riInserted.get(index);
					ri.
					unitMainTenanceService.insertSelective(ri);
				}
			}
			if (updated != null) {
				riUpdated = JsonHelper.jsonToBeanList(String.valueOf(updated), AdminAreaEntity.class);
				// 调用更新方法
				for (int index = 0; index < riUpdated.size(); index++) {
					AdminAreaEntity ri = riUpdated.get(index);
					ri.setCorpId(corpId);
					ri.setCreateDate(currentDate);
					ri.setModifyDate(currentDate);
					ri.setOperator(userId);
					unitMainTenanceService.updateByPrimaryKey(ri);
				}
			}
			if (deleted != null) {
				riDeleted = JsonHelper.jsonToBeanList(String.valueOf(deleted), AdminAreaEntity.class);
				// 调用删除方法
				for (int index = 0; index < riDeleted.size(); index++) {
					AdminAreaEntity ri = riDeleted.get(index);
					ri.setCorpId(corpId);
					unitMainTenanceService.deleteByPrimaryKey(ri.getId());
				}
			}
		} catch (Exception e) {
			return jsonResult(false, "错误信息:" + e.getMessage());
		}
		baseLogger.trace("saveData() <==");
		return jsonResult(true, "保存成功");
	}*/
}