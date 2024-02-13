package com.as.filesearch.base.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.as.filesearch.base.entity.Constant;
import com.as.filesearch.base.entity.LoginUser;
import com.as.filesearch.base.helper.ContextHelper;
import com.as.filesearch.entity.PortalUserEntity;

public class AdminInterceptor extends HandlerInterceptorAdapter {
	// 不需要登陆的URL
	private static List<String> noLoginAdminUrls = new ArrayList<String>();
	// 需要登陆的URL
	private static List<String> loginAdminUrls = new ArrayList<String>();

	static {//添加不需要验证的URL
		noLoginAdminUrls.add("/admin/login/index");
		noLoginAdminUrls.add("/admin/login/loginAction");
		
	}

	static {
		// AdminIndexController
		loginAdminUrls.add("/admin/adminIndex/queryMenu");

		// ManageUserController
		loginAdminUrls.add("/admin/user/manageUser/admin_user");
		loginAdminUrls.add("/admin/user/manageUser/query_admin_user");
		loginAdminUrls.add("/admin/user/manageUser/admin_user_detail");
		loginAdminUrls.add("/admin/user/manageUser/saveUser");
		loginAdminUrls.add("/admin/user/manageUser/deleteUser");

		// ManageNewsColumnController
		loginAdminUrls.add("/admin/news/manageNewsColumn/news_column");
		loginAdminUrls.add("/admin/news/manageNewsColumn/query_news_column");
		loginAdminUrls.add("/admin/news/manageNewsColumn/news_column_detail");
		loginAdminUrls.add("/admin/news/manageNewsColumn/saveNewsColumn");
		loginAdminUrls.add("/admin/news/manageNewsColumn/deleteNewsColumn");
		loginAdminUrls.add("/admin/news/manageNewsColumn/getAllDistinctNewsColumns");

		// ManageNewsInfoController
		loginAdminUrls.add("/admin/news/manageNewsInfo/news_Info_draft");
		loginAdminUrls.add("/admin/news/manageNewsInfo/query_news_info_draft");
		loginAdminUrls.add("/admin/news/manageNewsInfo/news_info_draft_detail");
		loginAdminUrls.add("/admin/news/manageNewsInfo/saveNewsInfo");
		loginAdminUrls.add("/admin/news/manageNewsInfo/deleteNewsInfo");
		loginAdminUrls.add("/admin/news/manageNewsInfo/news_info_draft_preview");
		loginAdminUrls.add("/admin/news/manageNewsInfo/approveNewsDraft");
		loginAdminUrls.add("/admin/news/manageNewsInfo/news_Info_under_release");
		loginAdminUrls.add("/admin/news/manageNewsInfo/query_news_info_under_release");
		loginAdminUrls.add("/admin/news/manageNewsInfo/releaseNews");
		loginAdminUrls.add("/admin/news/manageNewsInfo/rejectToDrafts");
		loginAdminUrls.add("/admin/news/manageNewsInfo/news_Info_release");
		loginAdminUrls.add("/admin/news/manageNewsInfo/query_news_info_release");
		loginAdminUrls.add("/admin/news/manageNewsInfo/rejectToUnderRelease");

		// FilesUploadController
		loginAdminUrls.add("/admin/files/fileController/fileView");
		loginAdminUrls.add("/admin/files/fileController/uploadFile");
		loginAdminUrls.add("/admin/files/fileController/getMetaData");
		loginAdminUrls.add("/admin/files/fileController/getCaseType");
		loginAdminUrls.add("/admin/files/fileController/getCaseTypeView");
		loginAdminUrls.add("/admin/files/fileController/getUploadedFileInfoView");
		loginAdminUrls.add("/admin/files/fileController/saveUploadedFileInfo");
		loginAdminUrls.add("/admin/files/fileController/getUploadedFileInfo");

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		String path = request.getContextPath();
		
		// 未登录url直接跳过拦截器
		if (null != uri && noLoginAdminUrls.contains(uri.substring(path.length()))) {
			return super.preHandle(request, response, handler);
		}

		LoginUser currentLoginUserInSession = ContextHelper.getLoginUserFromSession();
		if(null==currentLoginUserInSession){
			response.getWriter().print("<script>top.location.replace('"+path+"/admin/login/index');</script>");//跳转到后台管理登陆页面
			return false;
		}
		// 非管理员用户
		if (!"admin".equals(currentLoginUserInSession.getUsername())) {
			ContextHelper.deleteLoginUserFromSession();
			response.getWriter().print("<script>top.location.replace('"+path+"/admin/login/index');</script>");//跳转到后台管理登陆页面
			return false;
		}

		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		/*if(null != modelAndView) {
	
		}*/
		super.postHandle(request, response, handler, modelAndView);
	}

}
