<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="admin_taglib.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="${ctx}/css/admin/style-admin-header.css"
	type="text/css" />
<title></title>
</head>
<body>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#nav-toggle").click(function() {
				$('#nav-primary-menu-container').toggle();
			});
		});
	</script>

	<header id="header-container">
		<!-- 网站左上角的Logo -->
		<div id="site-logo-container" class="site-logo-container">
			<a title="FileSearch" href="${ctx}/admin/adminIndex/index"> <img id="site-logo"
				alt="FileSearch" src="${ctx}/images/logo.png">
				<!-- <h1 id="site-title" style="float: left;">后台管理</h1> -->
			</a>
		</div>
		<!-- 网站的导航栏 -->
		<nav id="nav-primary-container">
			<!-- 当放缩过小的时候展开 -->
			<button id="nav-toggle">
				<i class=""></i>
				<fmt:message key="header.button.buttonText" />
			</button>
			<div id="nav-primary-menu-container">
				<ul id="ul-menu-list">
					<li><a href="${ctx}/admin/login/index"><fmt:message
								key="admin_header.a.LogOut" /></a></li>
				</ul>
			</div>
		</nav>

	</header>
</body>
</html>