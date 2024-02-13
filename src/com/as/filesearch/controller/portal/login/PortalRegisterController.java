package com.as.filesearch.controller.portal.login;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.as.filesearch.base.controller.PortalBaseController;
import com.as.filesearch.entity.PortalUserEntity;
import com.as.filesearch.service.admin.user.IPortalUserEntityService;

@Controller
@RequestMapping(value = "/portal/login/register")
public class PortalRegisterController extends PortalBaseController {
	@Resource(name = "portalUserEntityService")
	private IPortalUserEntityService portalUserEntityService;

	@RequestMapping(value = "/registerIndex")
	public ModelAndView loginModelAndView() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("portal/login/register");
		return modelAndView;
	}
	@RequestMapping(value = "/terms")
	public ModelAndView termsModelAndView() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("portal/login/terms");
		return modelAndView;
	}
	@RequestMapping(value = "/checkUserName", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkRegisterUserExist(String userName) {
		try {
			PortalUserEntity userEntity = portalUserEntityService.loginWithUserNameOrEmail(userName);
			if (null != userEntity) {
				return jsonResult(false, "用户已经存在");
			}
			return jsonResult(true, "可以注册");
		} catch (Exception ex) {
			ex.printStackTrace();
			baseLogger.error(ex.getMessage());
			return jsonResult(false, ex.getMessage());
		}
	}

	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> registerUser(HttpServletRequest request, HttpServletResponse response, String userName,
			String password) {
		try {
			PortalUserEntity portalUserEntity = new PortalUserEntity();
			portalUserEntity.setEmail(userName); //注册的时候是用户名呢邮箱相同
			portalUserEntity.setUsername(userName);
			portalUserEntity.setPassword(password);
			portalUserEntity.setCreateTime(new Date());
			portalUserEntity.setModifyTime(new Date());
			
			portalUserEntityService.insertSelective(portalUserEntity);
			return jsonResult(true, "注册成功");
		} catch (Exception ex) {
			baseLogger.error(ex.getMessage());
			return jsonResult(false, ex.getMessage());
		}
	}
}
