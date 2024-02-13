package com.as.filesearch.base.entity;

import java.util.HashMap;
import java.util.Map;

public class Constant {

	public static final Integer _SUCCESS_ = 1;
	public static final Integer _FAILED_= 0;

	public static final String _RESULT_ = "result";
	public static final String _REASON_ = "reason";

	public static final String _PATH_ = "path";

	public static final String _SAVE_SUCCESS_ = "保存成功!";
	public static final String _SAVE_FILE_INFO_FAILED_ = "保存文件信息失败!";
	public static final String _SAVE_META_DATA_FAILED_ = "保存元数据信息失败!";
	public static final String _ADD_KEY_WORDS_FAILED_ = "动态添加字典失败!";

	public static final String _ERROR_OCCURED_ = "发生系统错误!";
	public static final String _NOT_FOUND_COURT_ = "没有找到法庭信息!";
	public static final String _NOT_FOUND_CASE_CAUSE_ = "没有找到案由信息!";
	public static final String _NOT_FOUND_CASE_TYPE_ = "没有找到案件类型信息!";
	/**
	 * 精确查找类型
	 */
	public static final String _CAUSE_ = "cause";
	public static final String _COURT_ = "court";
	public static final String _JUDGE_ = "judge";
	public static final String _LITIGANT_ = "litigant";
	public static final String _EXECUTOR_ = "executor";
	public static final String _EXECUTED_ = "executed";
	public static final String _THIRDPART_ = "thirdpart";
	public static final String _LAWYER_ = "lawyer";
	public static final String _LAWFIRM_ = "lawfirm";
	public static final String _LEGALPERSON_ = "legalPerson";

	/**
	 * 重定向Url，必须和urlrewrite.xml中的同步
	 */
	// 首页
    public static final String _url_index_ = "index.jsp";
    // 文件搜索List
    public static final String _url_docList_ = "/portal/searchfile/searchFile/searchFile";
     // 文件搜索明细
    public static final String _url_docDetail_ = "/portal/searchfile/showDetailFile/showDetailDoc";   
    // 典型案例
    public static final String _url_typical_ = "/portal/searchfile/searchFile/typical";
    // 案由查找
    public static final String _url_cause_ = "/portal/searchfile/searchFile/cause";
    // 当事人查找
    public static final String _url_litigant_ = "/portal/searchfile/searchFile/litigant";
    // 法院、法官查找
    public static final String _url_court_ = "/portal/searchfile/searchFile/court";
    // 新闻列表页面
	public static final String _url_newsList_ = "/portal/news/newsList/index";
	// 新闻页明细页面
	public static final String _url_newsDetail_ = "/portal/news/newsDetail/index";
	// 登陆
	public static final String _url_login_ = "/portal/login/login/loginIndex";
	// 注册
	public static final String _url_register_ = "/portal/login/register/registerIndex";
	// 联系我们  
	public static final String _url_contactus_ = "/portal/contact/contactus/contactusIndex";
	// 自助服务 
	public static final String _url_member_ = "/portal/user/selfservice/personalInfo";
	public static final String _url_member_edit_ = "/portal/user/selfservice/editInfo";
	public static final String _url_password_edit_ = "/portal/user/selfservice/editPassword";
	public static final String _url_logout_ = "/portal/user/selfservice/logout";
	/**
	 * 验证码
	 */
	public static final String _OPERATION_CHECK_CODE = "operationCheckCode";
	
	
	/**
	 * cookie key
	 */
	public static final String _COOKIE_KEY = "COOKIEID";
	
	public static final String _SESSION_KEY = "SESSIONID";
	
	/**
	 * 用户名的Session
	 */
	public static final String 	_SESSION_USERNAME = "sessionUser";
	
	/**
	 * 前台的用户名和密码能够记录的Session时间长度(默认30天)
	 */
	public static final int _SESSION_OUT_OF_DATE_TIME = 60 * 60 * 24 * 30;
	
	/**
	 * 用户名的Session
	 */
	public static final String 	_SESSION_CONTENT_PLAIN = "contentPlain";
	/**
	 * 用户名的Session
	 */
	public static final String 	_SESSION_CONTENT_HTML = "contentHtml";
	
	
	/**
	 * 各个系统对应cookie的Key值
	 */
	public static final String _COOKIE_FILESEARCH_USERNAME = "FileSearch_UserName";
	public static final String _COOKIE_FILESEARCH_PASSWORD= "FileSearch_Password";
	public static final Map<String, String> _COOKIEKEYS = new HashMap<String, String>();
	static{
		//_COOKIEKEYS.put("/admin", _ADMIN_SESSION_PRE_STR);//后台管理
		//_COOKIEKEYS.put("/portal", _PORTAL_SESSION_PRE_STR); //portal
		//_COOKIEKEYS.put("/FileSearch", "/FileSearch");	
	}
}
