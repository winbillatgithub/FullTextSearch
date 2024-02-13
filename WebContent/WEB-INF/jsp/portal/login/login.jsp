<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../common/portal_taglib.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="login.title.Login" /></title>
<link rel='icon' href='${ctx}/images/logo.ico' type='image/x-ico' />
<link rel="stylesheet" type="text/css" href="${ctx}/css/portal/login/login.css">
<script type="text/javascript" src="${ctx}/js/common/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx}/js/portal/login.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		setCookie();
		$("#username").focus();
	});
</script>

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
		<div id="main-div">

			<h1 id="h1-login-title">
				<fmt:message key="login.h1.LoginTitle" />
			</h1>
			<div id="login-container" class="login-container">
				<form action="" name="form" method="post">
					<div class="item">
						<div id="errorTxt"></div>
					</div>
					<div class="item">
						<span class="label"><fmt:message key="login.label.UserName" /></span>
						<div class="item-bd">
							<input class="" type="text" id="username" value="" />
						</div>
					</div>
					<div class="item">
						<span class="label"><fmt:message key="login.label.Password" /></span>
						<div class="item-bd">
							<input class="" type="password" id="password" value="" />

						</div>
					</div>
					<div class="item sessionAndRegister">
						<input type="checkbox" id="rememberMe" /> <label><fmt:message
								key="login.label.RememberMe" /></label><%--  &emsp;|&emsp; <span></span> <a href=""><fmt:message
								key="login.a.ForgetPassword" /></a> --%>
					</div>
					<div class="item loginButton">
						<input type="button" id="btnLogin"
							value="<fmt:message key="login.button.Login" />" /> <input
							id="btnLoginingText" type="hidden"
							value="<fmt:message key="login.button.LoginingText" />" /> <input
							id="btnLoginCompleteText" type="hidden"
							value="<fmt:message key="login.button.LoginCompleteText" />" />
					</div>
					<dir class="item registerItem">
						<fmt:message key="login.turnToRegister.PreContext" />
						<a id="registerLink" href="register.html"><fmt:message
								key="login.turnToRegister.AppendixLink" /></a>
					</dir>
				</form>
			</div>

		</div>
	</div>
</body>
</html>
