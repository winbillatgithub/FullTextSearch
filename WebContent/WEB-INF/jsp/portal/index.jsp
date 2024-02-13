<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="common/portal_taglib.jsp"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="index.title.HomePage" /></title>
<link rel='icon' href='${ctx}/images/logo.ico' type='image/x-ico' />
<link rel="stylesheet" href="${ctx}/css/portal/index/style-index.css" type="text/css" />

</head>
<body>
	<div id="wap-container" class="wap-container">
		<!-- header -->
		<!-- 因国际化问题，注销一下内容，并直接嵌入 -->
	<%-- 	<jsp:include page="WEB-INF/jsp/portal/common/header.jsp"></jsp:include> --%>
       <%--  <jsp:include page="common/header.jsp"></jsp:include> --%>
		<div id="header-container">
			<!-- 网站左上角的Logo -->
			<div id="site-logo-container" class="site-logo-container">
				<a title="FileSearch" href="${ctx}/index.html"> <img id="site-logo"
					alt="FileSearch" src="${ctx}/images/logo.png">
					<!-- <h1 id="site-title" style="float: left;">FileSearch</h1>  -->
				</a>
			</div>
			<div id="site-language-switch-display">
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




       
       
		<!-- main content -->
		<div id="main-container" class="main-container">
			<h1 id="main-page-header-title">
				<fmt:message key="index.h1.FileSearchTitle" />
			</h1>
			<br/>
			<div id="main-search-container">
				<form method="get" id="main-search-file-form" action="${ctx}/docList.html">
					<input id="key-word-input" type="text" value="" name="keyword"
						placeholder="<fmt:message key="index.input.PlaceHolder" />"
						maxlength="45" /> <input type="submit"
						id="key-work-search-button"
						value="<fmt:message key="index.input.SearchButton" />" />
				</form>
			</div>
		</div>

		<!-- footer -->
		<jsp:include page="common/footer.jsp"></jsp:include>
          <%--  <jsp:include page="jsp/portal/common/footer.jsp"></jsp:include> --%>



	</div>
</body>
</html>
