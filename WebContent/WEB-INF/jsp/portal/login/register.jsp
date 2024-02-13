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
<link rel="stylesheet" type="text/css"
	href="${ctx}/css/portal/login/register.css">
<script type="text/javascript" src="${ctx}/js/common/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx}/js/portal/register.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$("#username").focus(); 
	});
</script>
<style type="text/css">
.reg .regSuc {
	height: 125px;
	padding: 40px 100px 0;
	text-align: center;
	font-size: 14px;
}

.reg .regSuc .btn122 {
	margin-top: 20px;
}

.errorTxt {
	margin-left: 160px;
	color: #f00;
}

.successTxt {
	margin-left: 160px;
	color: #0AEE55;
	margin-top: 0px;
}
</style>
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
				<fmt:message key="register.h1.RegisterTitle" />
			</h1>
			<div id="register-container" class="register-container">
				<form action="" name="form" method="post">
					<div class="item">
						<div id="errorTxt"></div>
					</div>
					<div class="item">
						<span class="label"><fmt:message key="register.label.UserName" /></span>
						<div class="item-bd">
							<input class="" type="text" id="username" value="" />
							<span id="userNameCheckTxt" class="checkTxt"></span>
							<input id="hiddenCheckUserNameNotNull" type="hidden" value="<fmt:message key="register.checkUserName.UserNameNotNullTxt" />" />
							<input id="hiddenCheckUserNameWrongEmail" type="hidden" value="<fmt:message key="register.checkUserName.WrongMailFormat" />" />
							<input id="hiddenCheckUserNameTrueResult" type="hidden" value="<fmt:message key="register.checkUserName.CheckTrueTxt" />" />
							<input id="hiddenCheckUserNameFalseResult" type="hidden" value="<fmt:message key="register.checkUserName.CheckFalseTxt" />" />
						</div>
					</div>
					<div class="item">
						<span class="label"><fmt:message key="register.label.Password" /></span>
						<div class="item-bd">
							<input class="" type="password" id="password1" value="" />	
							<span id="password1CheckTxt" class="checkTxt"></span>
							<input id="hiddenCheckPassword1NotNull" type="hidden" value="<fmt:message key="register.checkPassword1.NotNul" />"/>
												
						</div>
					</div>
					<div class="item">
						<span class="label"><fmt:message key="register.label.Password" /></span>
						<div class="item-bd">
							<input class="" type="password" id="password2" value="" />
							<span id="password2CheckTxt" class="checkTxt"></span>
							<input id="hiddenCheckPassword2NotNull" type="hidden" value="<fmt:message key="register.checkPassword2.NotNul" />" />
							<input id="hiddenCheckPassword2NotTheSame" type="hidden" value="<fmt:message key="register.checkPassword2.NotTheSameAsPassword1" />" />
						</div>
					</div>
					<div class="item">
						<input type="checkbox" id="reedAgreement" />
						<fmt:message key="register.checkRead.PreContext" />
						<a href="terms.html" target="_blank"><fmt:message key="register.checkRead.AppendixLink" /></a>
						<span id="agreementCheckTxt" class="checkTxt"></span>
						<input id="hiddenCheckBoxAgree" type="hidden" value="<fmt:message key="register.checkSiteAgreement.NotAgree" />" />
						
					</div>
					<div class="item registerButton">
					<!-- disabled="disabled" -->
						<input type="button" id="btnRegister"  
							value="<fmt:message key="register.button.Register" />" />
						<input id="btnRegisteringTxt" type="hidden"
						value="<fmt:message key="register.button.RegisteringText" />" /> 
						<input id="btnRegisterCompleteTxt" type="hidden"
						value="<fmt:message key="register.button.RegisterCompleteText" />" />
					</div>

					<div class="item turnToLogin">
						<fmt:message key="register.turnToLogin.PreContext" />
						<a href="login.html"><fmt:message
								key="register.turnToLogin.AppendixLink" /></a>
					</div>
				</form>
			</div>
			<!-- 注册成功 -->
			<div id="regSuccess" class="regSuc" style="display: none;">
				<fmt:message key="register.turnToLogin.RegisterCompleteText" />
			</div>
		</div>
	</div>
</body>
</html>
