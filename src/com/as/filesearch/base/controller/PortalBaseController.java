package com.as.filesearch.base.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.as.filesearch.base.entity.Constant;
import com.as.filesearch.base.entity.LoginUser;
import com.as.filesearch.base.entity.SearchArticleEntity;
import com.as.filesearch.base.helper.ContextHelper;
import com.as.filesearch.base.helper.SolrClientHelper;
import com.as.filesearch.base.helper.StringHelper;

public class PortalBaseController {
	protected static Logger baseLogger = LoggerFactory
			.getLogger(AdminBaseController.class);
	
	/**
	 * 获取当前登陆的用户
	 * @return
	 */
	protected  LoginUser getCurrentLoginUser(){
		return ContextHelper.getLoginUserFromSession();
	}	
	
	protected boolean logoutCurrentLoginUser(HttpServletRequest request,
			HttpServletResponse response) {
		try{
		String path = request.getContextPath();
		String contextPathString = StringHelper.strIsEmpty(path) ? "/" : path;
		ContextHelper.delCookie(request, response, Constant._COOKIE_FILESEARCH_USERNAME);
		ContextHelper.delCookie(request, response, Constant._COOKIE_FILESEARCH_PASSWORD);
		ContextHelper.delCookie(request, response, contextPathString);
		ContextHelper.deleteLoginUserFromSession();
		}catch(Exception ex){
			baseLogger.trace(ex.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * 返回json的数据
	 * @param success
	 * @param msg
	 * @return
	 */
	protected Map<String, Object> jsonResult(boolean success, Object msg) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("success", success);
		data.put("msg", msg);
		return data;
	}
	
	protected Map<String, Object> querySearchArticleById(String searchKeyWords,
			String articleId) {
		baseLogger.info("*****************************************");
		baseLogger.debug("*****************************************");
		baseLogger.error("*****************************************");
		baseLogger.trace("*****************************************");
		SolrClientHelper solrClientHelper = new SolrClientHelper();
		Map<String, Object> searArticle = null;
		try {
			searArticle = solrClientHelper.querySolrById(searchKeyWords,
					articleId);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return searArticle;
	}
	
	
	
	/**
	 * 
	 * @param startNumber
	 * @param pageSize
	 * @param searchKeyWords
	 * @return
	 */
	protected Map<String, Object> querySearchArticleEntities(int startNumber,
			int pageSize, Map<String, Object> params) {
		SolrClientHelper solrClientHelper = new SolrClientHelper();
		Map<String, Object> queryResultMap = null;//new HashMap<String, Object>();  // Memory leak?
		try {
			queryResultMap = solrClientHelper.querySolrList(startNumber, pageSize, params);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return queryResultMap;
	}
	

}
