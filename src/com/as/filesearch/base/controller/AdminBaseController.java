package com.as.filesearch.base.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.as.filesearch.base.entity.LoginUser;
import com.as.filesearch.base.entity.SearchArticleEntity;
import com.as.filesearch.base.helper.ContextHelper;
import com.as.filesearch.base.helper.SolrClientHelper;
import com.as.filesearch.entity.SysConfigEntity;
import com.as.filesearch.service.admin.config.ISysConfigEntityService;

public class AdminBaseController {
	protected static Logger baseLogger = LoggerFactory
			.getLogger(AdminBaseController.class);
	
	@Resource(name = "sysConfigEntityService")
	private  ISysConfigEntityService sysConfigEntityService;

	/**
	 * 获取当前登陆的用户
	 * @return
	 */
	protected  LoginUser getCurrentLoginUser(){
		return ContextHelper.getLoginUserFromSession();
	}	

	
	/**
	 * 返回字符串
	 * @param response
	 * @param json
	 */
/*	public void outJsonString(HttpServletResponse response, Object json) { 
		response.setContentType("text/javascript;charset=UTF-8");
		response.setHeader("Cache-Control","no-store max-age=0 no-cache must-revalidate"); 
		response.addHeader("Cache-Control","post-check=0 pre-check=0");
		response.setHeader("Pragma","no-cache"); 

		try {
			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
			out.close(); 
		} catch (IOException e) {
			baseLogger.error("json返回字符串错误", e);
		}
	} */
	
	
	protected  String getContextValueByKey(String keyStr){
		String returnValString = "";
		List<SysConfigEntity> sysConfigList = sysConfigEntityService.selectAllSysConfigEntity();
		for(SysConfigEntity sysConfigItem :sysConfigList){
			String itemKeyStr = sysConfigItem.getConfigKey();
			if(null!=itemKeyStr && itemKeyStr.equals(keyStr)){
				returnValString = sysConfigItem.getConfigValue();
				break;
			}
		}
		return returnValString;
	}
	
	
	/**
	 * 返回json的数据
	 * @param success
	 * @param msg
	 * @return
	 */
	public Map<String, Object> jsonResult(boolean success, Object msg) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("success", success);
		data.put("msg", msg);
		return data;
	}
	
}
