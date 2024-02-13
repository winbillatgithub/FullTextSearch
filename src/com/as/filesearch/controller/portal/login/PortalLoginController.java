package com.as.filesearch.controller.portal.login;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.as.filesearch.base.controller.PortalBaseController;
import com.as.filesearch.base.entity.Constant;
import com.as.filesearch.base.entity.LoginUser;
import com.as.filesearch.base.helper.ContextHelper;
import com.as.filesearch.entity.PortalUserEntity;
import com.as.filesearch.service.admin.user.IPortalUserEntityService;

@Controller
@RequestMapping(value = "/portal/login/login")
public class PortalLoginController extends PortalBaseController {

	@Resource(name = "portalUserEntityService")
	private IPortalUserEntityService portalUserEntityService;

	@RequestMapping(value = "/loginIndex")
	public ModelAndView loginModelAndView() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("portal/login/login");
		ContextHelper.deleteLoginUserFromSession();
		return modelAndView;
	}

	@RequestMapping(value = "/loginAction", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			PortalUserEntity portalUser, String clientIp, Boolean rememberMe) {
		/* Map<String, Object> returnResult = new HashMap<String, Object>(); */

		try {
			String loginUserIpString = this.getIpAddr(request);
			baseLogger.info("\r\n用户名：" + portalUser.getUsername() + "\r\n密码："
					+ portalUser.getPassword() + "\r\n客户端ip："
					+ loginUserIpString);

			// 验证用户名是否存在
			PortalUserEntity sqlPortalUser = portalUserEntityService
					.selectByUserName(portalUser.getUsername());
			if (null == sqlPortalUser) {
				return jsonResult(false, "1");// 用户不存在
			}

			// 验证密码是否正确
			if (!sqlPortalUser.getPassword().equals(portalUser.getPassword())) {
				return jsonResult(false, "2");// 密码错误
			}

			// 用户被禁用
			if ("1".equals(sqlPortalUser.getIsDisable())) {
				return jsonResult(false, "3");// 用户被禁用
			}

			// 存取当前登陆成功的用户的Session
			LoginUser loginUser = new LoginUser();
			loginUser.setUserId(sqlPortalUser.getUserId());
			loginUser.setUsername(sqlPortalUser.getUsername());
			loginUser.setLoginDate(new Date());
			loginUser.setClientIp(clientIp);
			String sessionId = ContextHelper.getSessionKey(loginUser);
			loginUser.setSessionId(sessionId);
			loginUser.setUserType(sqlPortalUser.getUserType());
			// 当选择记住我,则把cookie都写入
			if (null != rememberMe && rememberMe) {
				// 记录Session ID
				Constant._COOKIEKEYS.put(request.getContextPath(),
						request.getContextPath());
				// 若选中，则需要把cookie的时间设置为30天
				ContextHelper.setCookie(request, response, sessionId,
						Constant._SESSION_OUT_OF_DATE_TIME);
				// 创建cookie(UserName)
				ContextHelper.setCookieNameAndValue(request, response,
						Constant._COOKIE_FILESEARCH_USERNAME,
						portalUser.getUsername(),
						Constant._SESSION_OUT_OF_DATE_TIME);
				// 创建cookie(Password)
				ContextHelper.setCookieNameAndValue(request, response,
						Constant._COOKIE_FILESEARCH_PASSWORD,
						portalUser.getPassword(),
						Constant._SESSION_OUT_OF_DATE_TIME);
			} else {// 否则，cookie只在浏览器期间才保存
				ContextHelper.setCookie(request, response, sessionId, null);
				// 创建cookie(UserName)
				ContextHelper.setCookieNameAndValue(request, response,
						Constant._COOKIE_FILESEARCH_USERNAME,
						portalUser.getUsername(), null);
				// 创建cookie(Password)
				ContextHelper.setCookieNameAndValue(request, response,
						Constant._COOKIE_FILESEARCH_PASSWORD,
						portalUser.getPassword(), null);
			}
			
			ContextHelper.addLoginUserToSession(session, sessionId, loginUser);
			return jsonResult(true, "登陆成功");
		} catch (Exception ex) {
			ex.printStackTrace();
			baseLogger.error(ex.getMessage());
			return jsonResult(false, ex.getMessage());
		}
	}


	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("PRoxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
