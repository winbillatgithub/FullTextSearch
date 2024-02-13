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
					<li><a href="${ctx}/member_edit.html"><fmt:message key="user.li.PersonalSetting" /></a></li>
					<li><a href="${ctx}/password_edit.html" class="current"><fmt:message key="user.li.PasswordEdit" /></a></li>
					<li><a href="${ctx}/logout.html"><fmt:message key="user.li.Logout" /></a></li>
				</ul>
			</div>
			<!--左侧导航 结束-->
			<!--右侧主体 开始-->
			<div class="maindiv">
            	<form id="form1" name="form1" method="post">
					<input id="userId" name="userId" type="hidden" value="${portalUserEntity.userId}" />
					<div class="item">
						<span class="label"><fmt:message key="user.label.OldPassword" /></span>
						<div class="item-bd">
						<input id="oldPassword" name="oldPassword" value="" class="easyui-validatebox inp-txt" required="true"  value="" type="password" style="width:204px;"/>
						<span id="oldPasswordCheckTxt" class="checkTxt"></span>
						<input id="oldPasswordNotCorrect" type="hidden" value="<fmt:message key='user.checkOldPassword.NotCorrect' />">
						<input id="oldPasswordCanNotBeNull" type="hidden" value="<fmt:message key='user.checkOldPassword.Null' />">
						</div>
					</div>
	                <div class="item">
						<span class="label"><fmt:message key="user.label.NewPassword" /></span>
						<div class="item-bd">
						<input id="password" name="password" value="" class="easyui-validatebox inp-txt w230" required="true" validType="safepass" type="password" style="width:204px;" onkeydown='fnckeystop(event)' onpaste='return false' oncontextmenu='return false'/>
						<span id="newPasswordCheckTxt" class="checkTxt"></span>
						<input id="canNotBeSame" type="hidden" value="<fmt:message key='user.checkNewPassword.Same' />" />
						<input id="canNotBeNull" type="hidden" value="<fmt:message key='user.checkNewPassword.Null' />" />
						</div>
	                </div>
					<div class="item">
						<span class="label"><fmt:message key="user.label.ConfirmNewPassword" /></span>
						<div class="item-bd">
						<input id="confirmPassword" name="confirmPassword" value="" class="easyui-validatebox inp-txt w230" required="true" validType="equalTo['#password']" type="password" style="width:204px;" onkeydown='fnckeystop(event)' onpaste='return false' oncontextmenu='return false'/>
						<span id="confirmPasswordCheckTxt" class="checkTxt"></span>
						<input id="notSame" type="hidden" value="<fmt:message key='user.checkNewPassword.NotSame' />" />
						</div>
					</div>
					<div class="item updateButton">
					<!-- disabled="disabled" -->
						<input type="button" id="conButton" class="btnUpdate"
							value="<fmt:message key="user.button.Update" />" />
						<input id="btnUpdatingTxt" type="hidden"
						value="<fmt:message key="user.button.UpdatingText" />" /> 
						<input id="btnUpdateCompleteTxt" type="hidden"
						value="<fmt:message key="user.button.UpdateCompleteText" />" />
					</div>
					<!-- 修改成功 -->
					<div id="pwdSuccess" class="uptSuc" style="display: none;">
						<fmt:message key="user.modify.PasswordCompleteText" />
					</div>
                </form>
			</div>
		</div>
	</div>
</div>
</body>
</html>