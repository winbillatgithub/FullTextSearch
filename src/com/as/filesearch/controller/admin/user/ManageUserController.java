package com.as.filesearch.controller.admin.user;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.as.filesearch.base.controller.AdminBaseController;
import com.as.filesearch.base.helper.JsonHelper;
import com.as.filesearch.entity.PortalUserEntity;
import com.as.filesearch.service.admin.user.IPortalUserEntityService;

@Controller
@RequestMapping(value = "/admin/user/manageUser")
public class ManageUserController extends AdminBaseController {

	@Resource(name = "portalUserEntityService")
	private IPortalUserEntityService portalUserEntityService;

	@RequestMapping(value = "/admin_user")
	public ModelAndView returnAdminUserView() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/user/admin_user");
		return modelAndView;
	}

	@RequestMapping(value = "/query_admin_user", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryAllUserJsonStr(HttpServletRequest request, HttpServletResponse response) {
		baseLogger.trace("queryAllUserJsonStr()==>");
		int pageId = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int startNum = pageSize * (pageId - 1);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 起始数据参数
		paramMap.put("startNum", startNum);
		// 分页数据大小
		paramMap.put("pageSize", pageSize);
		// 用户名查询
		String userNamePareString = request.getParameter("username");
		if (null != userNamePareString && !"".equals(userNamePareString)) {
			paramMap.put("userName", userNamePareString);
		}
		// 显示名查询
		String contactNameParaString = request.getParameter("contactName");
		if (null != contactNameParaString && !"".equals(contactNameParaString)) {
			paramMap.put("contactName", contactNameParaString);
		}
		// 性别查询
		String genderSelectParaString = request.getParameter("genderSelect");
		if (null != genderSelectParaString && !"".equals(genderSelectParaString)) {
			paramMap.put("genderSelect", genderSelectParaString);
		}
		// 是否禁用查询
		String isDisableParaString = request.getParameter("isDisable");
		if (null != isDisableParaString && !"".equals(isDisableParaString)) {
			paramMap.put("isDisable", isDisableParaString);
		}
		// 排序字段
		String sortItemString = request.getParameter("sort");
		if("userId".equals(sortItemString)){
			sortItemString = "user_id";
		}else if("username".equals(sortItemString)){
			sortItemString = "username";
		}else if("password".equals(sortItemString)){
			sortItemString = "password";
		}else if("userType".equals(sortItemString)){
			sortItemString = "user_type";
		}else if("isFirstLogin".equals(sortItemString)){
			sortItemString = "is_first_login";
		}else if("createId".equals(sortItemString)){
			sortItemString = "create_id";
		}else if("createTime".equals(sortItemString)){
			sortItemString = "create_time";
		}else if("modifyId".equals(sortItemString)){
			sortItemString = "modify_id";
		}else if("modifyTime".equals(sortItemString)){
			sortItemString = "modify_time";
		}else if("isDisable".equals(sortItemString)){
			sortItemString = "is_disable";
		}else if("isDelete".equals(sortItemString)){
			sortItemString = "is_delete";
		}else if("version".equals(sortItemString)){
			sortItemString = "version";
		}else if("email".equals(sortItemString)){
			sortItemString = "email";
		}else if("gender".equals(sortItemString)){
			sortItemString = "gender";
		}else if("phone".equals(sortItemString)){
			sortItemString = "phone";
		}else if("birthday".equals(sortItemString)){
			sortItemString = "birthday";
		}else if("contactName".equals(sortItemString)){
			sortItemString = "contact_name";
		}else if("imageUrl".equals(sortItemString)){
			sortItemString = "image_url";
		}else if("comment".equals(sortItemString)){
			sortItemString = "comment";
		}
			
		paramMap.put("sortItem", sortItemString);
		// 排序方向
		String sortDirectionString = request.getParameter("order");
		if (null == sortDirectionString || "".equals(sortDirectionString)) {
			sortDirectionString = "asc";
		}
		paramMap.put("sortOrder", sortDirectionString);
		Map<String, Object> paginationMap = portalUserEntityService.selectAllByPagination(paramMap);

		String returnJsonString = JsonHelper.mapToJson(paginationMap,"yyyy-MM-dd");
		baseLogger.trace("queryAllUserJsonStr()<==");
		return paginationMap;
	}

	@RequestMapping(value = "/admin_user_detail")
	public ModelAndView returnAdminUserDetailView(HttpServletRequest request, HttpServletResponse response) {
		baseLogger.trace("returnAdminUserDetailView()==>");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/user/admin_user_detail");
		String userIdString = request.getParameter("userId");
		PortalUserEntity portalUserEntity = new PortalUserEntity();
		if (null != userIdString && !"".equals(userIdString)) {
			Integer userID = Integer.valueOf(userIdString);
			portalUserEntity = portalUserEntityService.selectByPrimaryKey(userID);
			
		}
		modelAndView.addObject(portalUserEntity);
		baseLogger.trace("returnAdminUserDetailView()<==");
		return modelAndView;
	}

	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	@ResponseBody
	public Map saveAdminUserDetail(HttpSession session, HttpServletRequest request, HttpServletResponse response,
			PortalUserEntity portalUser) {
		baseLogger.trace("saveAdminUserDetail()==>");
		try {
			Long userId = portalUser.getUserId();
			if (userId !=null) {
				portalUserEntityService.updateByPrimaryKey(portalUser);
				baseLogger.trace("saveAdminUserDetail()<==保存成功");
				return jsonResult(true, "保存成功");
			} else {
				portalUserEntityService.insert(portalUser);
				baseLogger.trace("saveAdminUserDetail()<==增加成功");
				return jsonResult(true, "增加成功");
			}
		
		} catch (Exception ex) {
			ex.printStackTrace();
			baseLogger.error(ex.getMessage());
			return jsonResult(false, "保存失败");
		}
		
	}
	
	
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePortalUser(Integer[] ids) {
		baseLogger.trace("deletePortalUser()==>");
		try {
			portalUserEntityService.deleteByPrimaryKeys(ids);
			baseLogger.trace("deletePortalUser()<==删除成功");
			return jsonResult(true, "删除成功");
		} catch (Exception e) {
			baseLogger.error(e.getMessage());
			baseLogger.trace("deletePortalUser()<==删除失败");
			return jsonResult(false, "删除失败");
		}
	}
	
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	@ResponseBody
	public Map resetPassword(HttpSession session, HttpServletRequest request, HttpServletResponse response,
			String id) {
		baseLogger.trace("resetPassword()==>");
		try {
			Long userId = Long.parseLong(id);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", userId);
			paramMap.put("password", "123456");
			portalUserEntityService.updateUserPassword(paramMap);
			baseLogger.trace("resetPassword()<==保存成功");
			return jsonResult(true, "保存成功");
		} catch (Exception ex) {
			ex.printStackTrace();
			baseLogger.error(ex.getMessage());
			return jsonResult(false, "保存失败");
		}
	}
}
