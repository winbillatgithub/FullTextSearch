<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../common/portal_taglib.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="user.title.Member" /></title>
<link rel='icon' href='${ctx}/images/logo.ico' type='image/x-ico' />
<link type="text/css" rel="stylesheet" href="${ctx}/css/admin/formtyle.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/css/portal/user/user.css">
<script type="text/javascript" src="${ctx}/js/portal/user/user.js"></script>

</head>
<body style="background: white;">
<div id="wap-container" class="wap-container">
	<!-- header -->
	<%-- <jsp:include page="../common/header.jsp"></jsp:include> --%>
				<div id="header-container">
			<!-- 网站左上角的Logo -->
			<div id="site-logo-container" class="site-logo-container">
				<a title="FileSearch" href="${ctx}/index.html"> <img id="site-logo"
					alt="FileSearch" src="${ctx}/images/logo.png">
					<!-- <h1 id="site-title" style="float: left;">FileSearch</h1>  -->
				</a>
			</div>
			<div id="site-language-switch">
				 <a href="?language=zh_CN">中</a>|<a href="?language=en_US">EN</a>
			</div>
			<!-- 网站的导航栏 -->
			<div id="nav-primary-container">
				<!-- 当放缩过小的时候展开 -->
				<button id="nav-toggle">
					<i class=""></i><fmt:message
									key="header.button.buttonText" />
				</button>
				<div id="nav-primary-menu-container">
					<ul id="ul-menu-list">
						<li><a href="${ctx}/index.html"><fmt:message
									key="header.li.MainPage" /></a></li>
						<li><a href="${ctx}/typical.html"><fmt:message
									key="header.li.InstructionCase" /></a></li>
		
						<li><a href="${ctx}/cause.html"><fmt:message
									key="header.li.CaseCauseSearch" /></a></li>
						<li><a href="${ctx}/litigant.html"><fmt:message
									key="header.li.ClientSearch" /></a></li>
						<li><a href="${ctx}/court.html"><fmt:message
									key="header.li.CourtSearch" /></a></li>
		
						<li><a href="${ctx}/newsList.html"><fmt:message
									key="header.li.News" /></a></li>
						<li><a href="${ctx}/contactus.html"><fmt:message
									key="header.li.Forum" /></a></li>
						<li><a href="${ctx}/member.html"><fmt:message
									key="header.li.Member" /></a></li>
					</ul>
				</div>
			</div>
		</div>
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	<!-- 中间容器div -->
	<div id="center-container">
		<div id="current-location">
			<a href="index.html"><fmt:message key="global.txt.Home" /></a> > <a href="newsList.html"><fmt:message key="user.nav.Member" /></a> 
		</div>
		<div id="user-container">
			<!--左侧导航 开始-->
			 <div class="sideBar">
				<ul class="sideNav">
					<li><a href="${ctx}/member.html"><fmt:message key="user.li.PersonalInfo" /></a></li>
					<li><a href="${ctx}/member_edit.html" class="current"><fmt:message key="user.li.PersonalSetting" /></a></li>
					<li><a href="${ctx}/password_edit.html"><fmt:message key="user.li.PasswordEdit" /></a></li>
					<li><a href="${ctx}/logout.html"><fmt:message key="user.li.Logout" /></a></li>
				</ul>
			</div>
			<!--左侧导航 结束-->
			<!--右侧主体 开始-->
			<div class="maindiv">
            	<form id="form1" name="form1" method="post">
					<div class="item">
						<span class="label"><fmt:message key="user.label.UserName" /></span>
						<div class="item-bd">
	                    <input id="username" class="easyui-validatebox inp-txt w230" required="true" value="${portalUserEntity.username }" maxlength="100" />
						<input id="userId" type="hidden" value="${portalUserEntity.userId}" />
						<span id="userNameCheckTxt" class="checkTxt"></span>
						<input id="hiddenCheckUserNameNotNull" type="hidden" value="<fmt:message key='user.checkUserName.UserNameNotNullTxt' />" />
						<input id="hiddenCheckUserNameWrongFormat" type="hidden" value="<fmt:message key='user.checkUserName.WrongFormat' />" />
						<input id="hiddenCheckUserNameTrueResult" type="hidden" value="<fmt:message key='user.checkUserName.CheckTrueTxt' />" />
						<input id="hiddenCheckUserNameFalseResult" type="hidden" value="<fmt:message key='user.checkUserName.CheckFalseTxt' />" />
						</div>
					</div>
	                <div class="item">
	                    <span class="label"><fmt:message key="user.label.CellPhone" /></span>
	                    <div class="item-bd">
	                    	<input id="mobile" class="easyui-validatebox inp-txt" value="${portalUserEntity.phone}" maxlength="20"/>
	                    </div>
	                </div>
					<div class="item">
						<span class="label"><fmt:message key="user.label.email" /></span>
	                    <div class="item-bd"><input id="email" readonly="readonly" value="${portalUserEntity.email}" class="inp-txt easyui-validatebox w230"  validType="email" onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5\@\.\_]/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="20"/></div>
					</div>
	                <div class="item">
						<span class="label"><fmt:message key="user.label.Gender" /></span>
	                    <div class="item-bd"><span class="mr20"><input name="gender" value="1" type="radio" <c:if test="${portalUserEntity.gender == '1' }">checked="checked"</c:if> /><label><fmt:message key="global.txt.Male" /></label></span><span><input name="gender" value="0" <c:if test="${portalUserEntity.gender == '0' }">checked="checked"</c:if> type="radio" /><label><fmt:message key="global.txt.Female" /></label></span></div>
	                </div>
					<div class="item">
						<span class="label"><fmt:message key="user.label.Comment" /></span>
						<div class="item-bd">
	                    <input id="comment" class="easyui-validatebox inp-txt w230 defaultVal" value="${portalUserEntity.comment }" maxlength="100" />
						</div>
					</div>
					<div class="item updateButton">
					<!-- disabled="disabled" -->
						<input type="button" id="btnUpdate" class="btnUpdate"
							value="<fmt:message key="user.button.Update" />" />
						<input id="btnUpdatingTxt" type="hidden"
						value="<fmt:message key="user.button.UpdatingText" />" /> 
						<input id="btnUpdateCompleteTxt" type="hidden"
						value="<fmt:message key="user.button.UpdateCompleteText" />" />
					</div>
					<!-- 注册成功 -->
					<div id="uptSuccess" class="uptSuc" style="display: none;">
						<fmt:message key="user.modify.UpdateCompleteText" />
					</div>
                </form>
			</div>
		</div>
	</div>
</div>
</body>
</html>
