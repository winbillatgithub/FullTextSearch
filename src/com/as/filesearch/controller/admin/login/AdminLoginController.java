/**
 * File description
 */
package com.as.filesearch.controller.admin.login;

import java.util.Date;
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
import com.as.filesearch.base.entity.Constant;
import com.as.filesearch.base.entity.LoginUser;
import com.as.filesearch.base.helper.ContextHelper;
import com.as.filesearch.entity.PortalUserEntity;
import com.as.filesearch.service.admin.user.IPortalUserEntityService;

@Controller
@RequestMapping(value = "/admin/login")
public class AdminLoginController extends AdminBaseController {

	@Resource(name = "portalUserEntityService")
	private IPortalUserEntityService portalUserEntityService;

	@RequestMapping(value = "/index")
	public ModelAndView loginAndView() {
		baseLogger.trace("loginAndView()==>进入登陆");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/login/login");
		return modelAndView;
	}

	@RequestMapping(value = "/loginAction", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			PortalUserEntity portalUser, Integer randCheckCode, String clientIp ) {

		Map<String, Object> returnResult = new HashMap<String, Object>();
		try {
			String loginUserIpString  = this.getIpAddr(request);
			baseLogger.info("\r\n用户名：" + portalUser.getUsername() + "\r\n密码：" + portalUser.getPassword() + "\r\n验证码："
					+ randCheckCode + "\r\n客户端ip：" + loginUserIpString );

			// 判断验证码是否正确
			if (randCheckCode.equals(session.getAttribute(Constant._OPERATION_CHECK_CODE))) {
				// 验证码验证成功清除session中的数据
				session.removeAttribute(Constant._OPERATION_CHECK_CODE);
			} else {
				return jsonResult(false, "1"); //验证码错误
			}

			// 验证用户名是否存在
			PortalUserEntity sqlPortalUser = portalUserEntityService.selectByUserName(portalUser.getUsername());
			if (null == sqlPortalUser) {
				return jsonResult(false, "2");//用户不存在
			}
			//验证密码是否正确
		    if (!portalUser.getPassword().equals(sqlPortalUser.getPassword())) {
		    	return jsonResult(false, "3");//密码错误
		    }
			
		    //用户被禁用
		    if ("1".equals(sqlPortalUser.getIsDisable())){
		    	return jsonResult(false, "4");//用户被禁用
		    }
		    
		    if ("0".equals(sqlPortalUser.getUserType())){
		    	return jsonResult(false, "5");//用户非系统管理员
		    }
            
		    //存取当前登陆成功的用户的Session
			LoginUser loginUser = new LoginUser();
			loginUser.setUserId(sqlPortalUser.getUserId());
			loginUser.setUsername(sqlPortalUser.getUsername());
			loginUser.setLoginDate(new Date());
			loginUser.setClientIp(clientIp);
			String sessionId = ContextHelper.getSessionKey(loginUser);
			loginUser.setSessionId(sessionId);
			loginUser.setUserType(sqlPortalUser.getUserType());
			//创建cookie
			ContextHelper.setCookie(request, response, sessionId, null);
			//存session
			ContextHelper.addLoginUserToSession(session, sessionId, loginUser);
		    
		    return jsonResult(true, "登陆成功");
		} catch (Exception ex) {
			baseLogger.error(ex.getMessage());
		}
		return returnResult;
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
