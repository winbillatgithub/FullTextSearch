package com.as.filesearch.controller.portal.user;

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
import com.as.filesearch.base.controller.PortalBaseController;
import com.as.filesearch.base.entity.LoginUser;
import com.as.filesearch.base.helper.ContextHelper;
import com.as.filesearch.base.helper.JsonHelper;
import com.as.filesearch.entity.PortalUserEntity;
import com.as.filesearch.service.admin.user.IPortalUserEntityService;

@Controller
@RequestMapping(value = "/portal/user/selfservice")
public class SelfServiceUserController extends PortalBaseController {

	@Resource(name = "portalUserEntityService")
	private IPortalUserEntityService portalUserEntityService;

	@RequestMapping(value = "/personalInfo")
	public ModelAndView returnPersonalInfoView(HttpServletRequest request, HttpServletResponse response) {
		baseLogger.trace("returnPersonalInfoView()==>");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("portal/user/user_info");
		LoginUser loginUser = ContextHelper.getLoginUserFromSession();
		PortalUserEntity portalUserEntity = new PortalUserEntity();
		if (null != loginUser.getUserId() && !"".equals(loginUser.getUserId())) {
			Long userID = Long.valueOf(loginUser.getUserId());
			portalUserEntity = portalUserEntityService.selectByPrimaryKey(userID.intValue());
		}
//		modelAndView.addAttribute("portalUserEntity", portalUserEntity);
		modelAndView.addObject(portalUserEntity);
		baseLogger.trace("returnPersonalInfoView()<==");
		return modelAndView;
	}

	@RequestMapping(value = "/editInfo")
	public ModelAndView returnEditInfoView(HttpServletRequest request, HttpServletResponse response) {
		baseLogger.trace("returnEditInfoView()==>");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("portal/user/user_info_edit");
		LoginUser loginUser = ContextHelper.getLoginUserFromSession();
		PortalUserEntity portalUserEntity = new PortalUserEntity();
		if (null != loginUser.getUserId() && !"".equals(loginUser.getUserId())) {
			Long userID = Long.valueOf(loginUser.getUserId());
			portalUserEntity = portalUserEntityService.selectByPrimaryKey(userID.intValue());
		}
		modelAndView.addObject(portalUserEntity);
		baseLogger.trace("returnEditInfoView()<==");
		return modelAndView;
	}

	@RequestMapping(value = "/editPassword")
	public ModelAndView returnEditPasswordView(HttpServletRequest request, HttpServletResponse response) {
		baseLogger.trace("returnEditPasswordView()==>");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("portal/user/password_edit");
		LoginUser loginUser = ContextHelper.getLoginUserFromSession();
		PortalUserEntity portalUserEntity = new PortalUserEntity();
		if (null != loginUser.getUserId() && !"".equals(loginUser.getUserId())) {
			Long userID = Long.valueOf(loginUser.getUserId());
			portalUserEntity = portalUserEntityService.selectByPrimaryKey(userID.intValue());
		}
		modelAndView.addObject(portalUserEntity);
		baseLogger.trace("returnEditPasswordView()<==");
		return modelAndView;
	}

	@RequestMapping(value = "/logout")
	public ModelAndView returnLogoutView(HttpServletRequest request, HttpServletResponse response) {
		baseLogger.trace("returnLogoutView()==>");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("portal/login/login");
		ContextHelper.deleteLoginUserFromSession();
		baseLogger.trace("returnLogoutView()<==");
		return modelAndView;
	}

	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	@ResponseBody
	public Map saveUserDetail(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		baseLogger.trace("saveUserDetail()==>");
		try {
			String userName = request.getParameter("userName");
			Long userId = Long.parseLong(request.getParameter("userId"));
			String phone = request.getParameter("phone");
			String gender = request.getParameter("gender");
			String comment = request.getParameter("comment");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", userId);
			paramMap.put("userName", userName);
			paramMap.put("phone", phone);
			paramMap.put("gender", gender);
			paramMap.put("comment", comment);

			portalUserEntityService.updateUserDetail(paramMap);
			baseLogger.trace("saveUserDetail()<==保存成功");
			return jsonResult(true, "保存成功");

		} catch (Exception ex) {
			ex.printStackTrace();
			baseLogger.error(ex.getMessage());
			return jsonResult(false, "保存失败");
		}
	}

	@RequestMapping(value = "/saveEditPassword", method = RequestMethod.POST)
	@ResponseBody
	public Map saveEditPassword(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		baseLogger.trace("saveEditPassword()==>");
		try {
			String password = request.getParameter("password");
			Long userId = Long.parseLong(request.getParameter("userId"));
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", userId);
			paramMap.put("password", password);

			portalUserEntityService.updateUserPassword(paramMap);
			baseLogger.trace("saveEditPassword()<==保存成功");
			return jsonResult(true, "保存成功");

		} catch (Exception ex) {
			ex.printStackTrace();
			baseLogger.error(ex.getMessage());
			return jsonResult(false, "保存失败");
		}
	}

	@RequestMapping(value = "/checkUserName", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkUserName(HttpServletRequest request, HttpServletResponse response) {
		baseLogger.trace("checkUserName()==>");
		try {
			String userName = request.getParameter("userName");
			String userId = request.getParameter("userId");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", userId);
			paramMap.put("userName", userName);
			PortalUserEntity userEntity = portalUserEntityService.checkUserName(paramMap);
			if (null != userEntity) {
				baseLogger.trace("checkUserName()<== ng，已存在.");
				return jsonResult(false, "用户已经存在");
			}
			baseLogger.trace("checkUserName()<== ok");
			return jsonResult(true, "可以使用");
		} catch (Exception e) {
			baseLogger.trace("deletePortalUser()<==删除失败，原因：" + e.getMessage());
			return jsonResult(false, e.getMessage());
		}
	}

	@RequestMapping(value = "/validatePwd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> validatePwd(HttpServletRequest request, HttpServletResponse response) {
		baseLogger.trace("validatePwd()==>");
		try {
			String password = request.getParameter("password");
			String userId = request.getParameter("userId");
			PortalUserEntity userEntity = portalUserEntityService.selectByPrimaryKey(Integer.parseInt(userId));

			if (null == userEntity) {
				baseLogger.trace("validatePwd()<== User not exists:" + userId);
				return jsonResult(false, "用户不存在");				
			} else if (userEntity.getPassword().equals(password)) {
				baseLogger.trace("validatePwd()<== Current password is ok.");
				return jsonResult(true, "密码正确");
			}
			baseLogger.trace("validatePwd()<== Current password is not correct.");
			return jsonResult(false, "旧密码不正确");
		} catch (Exception e) {
			baseLogger.trace("validatePwd()<==删除失败，原因：" + e.getMessage());
			return jsonResult(false, e.getMessage());
		}
	}

}
