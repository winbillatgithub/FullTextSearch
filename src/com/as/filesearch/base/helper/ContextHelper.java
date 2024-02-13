package com.as.filesearch.base.helper;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.as.filesearch.base.entity.Constant;
import com.as.filesearch.base.entity.LoginUser;
import com.as.filesearch.entity.SysConfigEntity;
import com.as.filesearch.service.admin.config.ISysConfigEntityService;

public class ContextHelper {

	@Resource(name = "sysConfigEntityService")
	private ISysConfigEntityService sysConfigEntityService;

	public String getContextValueByKey(String keyStr) {
		String returnValString = "";
		List<SysConfigEntity> sysConfigList = sysConfigEntityService
				.selectAllSysConfigEntity();
		for (SysConfigEntity sysConfigItem : sysConfigList) {
			String itemKeyStr = sysConfigItem.getConfigKey();
			if (null != itemKeyStr && itemKeyStr.equals(keyStr)) {
				returnValString = sysConfigItem.getConfigValue();
				break;
			}
		}
		return returnValString;
	}

	public String getSolrUrl() {
		String SOLR_URL = "";
		Properties prop = new Properties();
		InputStream in = SolrClientHelper.class
				.getResourceAsStream("/WebServiceConfig.properties");
		try {
			prop.load(in);
			in.close();
			SOLR_URL = prop.getProperty("SolrWebServiceURL").trim();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SOLR_URL;
	}
	/**
	 * 获取session key值
	 * 
	 * @param loginUser
	 * @return
	 */
	public static String getSessionKey(LoginUser loginUser) {
		if (null != loginUser) {
			return MD5Helper.MD5(loginUser.getUserId() + "#"
					+ loginUser.getUsername() + "#"
					+ System.currentTimeMillis());
		} else {
			return null;
		}
	}

	/**
	 * 获取cookie路径
	 * 
	 * @param request
	 * @return
	 */
	private static String getPath(HttpServletRequest request) {
		String path = request.getContextPath();
		return StringHelper.strIsEmpty(path) ? "/" : path;
	}

	/**
	 * 删除cookie
	 * 
	 * @param request
	 * @param response
	 * @param name
	 */
	public static void delCookie(HttpServletRequest request,
			HttpServletResponse response, String name) {
		// 给cookie从新赋值
		Cookie cookie = new Cookie(name, null);
		cookie.setMaxAge(0);
		cookie.setPath(getPath(request));
		response.addCookie(cookie);
	}

	
	public static void setCookieNameAndValue(HttpServletRequest request,
			HttpServletResponse response,String name, String value, Integer maxAge) {
		// 删除cookie
		delCookie(request, response, name);
		// 创建cookie
		Cookie cookie = new Cookie(name, value);
		if (null != maxAge) {
			cookie.setMaxAge(maxAge);
		}else{
			cookie.setMaxAge(-1);
		}
		cookie.setPath(getPath(request));
		response.addCookie(cookie);
	}

	/**
	 * 创建cookie
	 * 
	 * @param request
	 * @param response
	 * @param value
	 * @param maxAge
	 */
	public static void setCookie(HttpServletRequest request,
			HttpServletResponse response, String value, Integer maxAge) {
		// 获取各个系统cookie的name
		String name = getCookieName(request.getContextPath());
		// 删除cookie
		delCookie(request, response, name);
		// 创建cookie
		Cookie cookie = new Cookie(name, value);
		if (null != maxAge) {
			cookie.setMaxAge(maxAge);
		}else{
			cookie.setMaxAge(-1);
		}
		cookie.setPath(getPath(request));
		response.addCookie(cookie);
	}
	 
	/** 获取各个系统cookie的name
	 * 
	 * @param path 系统上下文
	 * 
	 * @return
	 */
	public static String getCookieName(String path) {
		if (StringHelper.strIsNotEmpty(Constant._COOKIEKEYS.get(path))) {
			return Constant._COOKIEKEYS.get(path);
		} else {
			return Constant._COOKIE_KEY;
		}
	}

	/**
	 * 获取cookie value
	 * 
	 * @param request
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (null != cookies && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(
						getCookieName(request.getContextPath()))) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	/**
	 * 从session中获取用户登录信息
	 * 
	 * @return
	 */
	public static LoginUser getLoginUserFromSession() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		return (LoginUser) session.getValue(Constant._SESSION_KEY+ getCookieValue(request));
	}

	public static Long getCurrentLoginUserId() {
		LoginUser loginUser = getLoginUserFromSession();
		return loginUser.getUserId();
	}

	/**
	 * 添加登录用户信息到session
	 * 
	 * @param sessionId
	 * @param loginUser
	 */
	public static void addLoginUserToSession(HttpSession httpSession,
			String sessionId, LoginUser loginUser) {
		if (null != sessionId && null != loginUser) {
			httpSession.putValue(Constant._SESSION_KEY + sessionId, loginUser);
		}
	}

	/**
	 * 从session中删除登陆用户信息
	 */
	public static void deleteLoginUserFromSession() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		session.removeValue(Constant._SESSION_KEY + getCookieValue(request));
	}
}
