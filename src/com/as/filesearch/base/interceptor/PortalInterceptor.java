package com.as.filesearch.base.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.as.filesearch.base.entity.Constant;
import com.as.filesearch.base.entity.LoginUser;
import com.as.filesearch.base.helper.ContextHelper;

public class PortalInterceptor extends HandlerInterceptorAdapter  {
	// 不需要登陆的URL
	private static List<String> noLoginPortalUrls = new ArrayList<String>();
	// 需要登陆的URL
	private static List<String> loginPortalUrls = new ArrayList<String>();
	
	static{
		//登陆
		noLoginPortalUrls.add("/portal/login/login/loginIndex");
		noLoginPortalUrls.add("/portal/login/login/loginAction");
		//注册
		noLoginPortalUrls.add("/portal/login/register/registerIndex");
		noLoginPortalUrls.add("/portal/login/register/checkUserName");
		noLoginPortalUrls.add("/portal/login/register/registerUser");
		noLoginPortalUrls.add("/portal/login/register/terms");
		//主页
		noLoginPortalUrls.add("/portal/index/index");
		//新闻
		noLoginPortalUrls.add("/portal/news/newsDetail/index");
		noLoginPortalUrls.add("/portal/news/newsList/index");
		//文件搜索
//		noLoginPortalUrls.add("/portal/searchfile/searchFile/searchFile");
//		noLoginPortalUrls.add("/portal/searchfile/showDetailFile/showDetailDoc");
		///FileSearch/portal/searchfile/searchFile/typical
		//联系我们
		noLoginPortalUrls.add("/portal/contact/contactus/contactusIndex");
//		//用户自服务--个人信息
//		noLoginPortalUrls.add("/portal/user/selfservice/personalInfo");
//		//用户自服务--个人设置
//		noLoginPortalUrls.add("/portal/user/selfservice/editInfo");
//		//用户自服务--修改密码
//		noLoginPortalUrls.add("/portal/user/selfservice/editPassword");
//		//用户自服务--注销
//		noLoginPortalUrls.add("/portal/user/selfservice/logout");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		String path = request.getContextPath();
		
		String requestLanguage = request.getParameter("language");
		if(null!=requestLanguage && !"".equals(requestLanguage)){
			if(requestLanguage.startsWith("zh")){
				 Locale locale = new Locale("zh", "CN"); 
				 request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale); 
			}else if(requestLanguage.startsWith("en")){
				 Locale locale = new Locale("en", "US"); 
				 request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale); 
			}else{
				 Locale locale = new Locale("zh", "CN"); 
				 request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale); 
			}
			
		}

		// 未登录url直接跳过拦截器
		String preUri = uri.substring(path.length());
		if (null != uri && noLoginPortalUrls.contains(preUri)) {
			return super.preHandle(request, response, handler);
		}

		LoginUser currentLoginUserInSession = ContextHelper.getLoginUserFromSession();
		if(null==currentLoginUserInSession){
			String fromUrl = matchRewriteUrl(preUri);
			response.getWriter().print("<script>top.location.replace('"+path+"/login.html"+fromUrl+"');</script>");//跳转到后台管理登陆页面
			return false;
		}
		

		return super.preHandle(request, response, handler);
	}
	/*
	 * Match request uri to html
	 */
	private String matchRewriteUrl(String url) {
		String from = "?from=";
		if (Constant._url_index_.equals(url)) {
			from += "index.html";
		} else if (Constant._url_docList_.equals(url)) {
			from += "index.html";//"docList.html";
		} else if (Constant._url_docDetail_.equals(url)) {
			from += "index.html";//docDetail.html";
		} else if (Constant._url_typical_.equals(url)) {
			from += "typical.html";
		} else if (Constant._url_cause_.equals(url)) {
			from += "cause.html";
		} else if (Constant._url_litigant_.equals(url)) {
			from += "litigant.html";
		} else if (Constant._url_court_.equals(url)) {
			from += "court.html";
		} else if (Constant._url_newsList_.equals(url)) {
			from += "newsList.html";
		} else if (Constant._url_newsDetail_.equals(url)) {
			from += "newsDetail.html";
		} else if (Constant._url_login_.equals(url)) {
			from += "login.html";
		} else if (Constant._url_register_.equals(url)) {
			from += "register.html";
		} else if (Constant._url_contactus_.equals(url)) {
			from += "contactus.html";
		} else if (Constant._url_member_.equals(url)) {
			from += "member.html";
		} else if (Constant._url_member_edit_.equals(url)) {
			from += "member_edit.html";
		} else if (Constant._url_password_edit_.equals(url)) {
			from += "password_edit.html";
		} else if (Constant._url_logout_.equals(url)) {
			from += "logout.html";
		} else {
			from = "";
		}
		return from;
	}
}
