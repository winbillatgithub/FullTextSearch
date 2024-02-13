<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/../common/taglib.jsp"%>

<script type="text/javascript">
	$(document).ready(function() {
		$("#nav-toggle").click(function() {
			$('#nav-primary-menu-container').toggle();
		});
	});
</script>

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

	<!-- <div class="clearBoth"></div> -->
</div>