<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../common/portal_taglib.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="contact.title.ContactUs" /></title>
<link rel='icon' href='${ctx}/images/logo.ico' type='image/x-ico' />
<link rel="stylesheet" type="text/css"
	href="${ctx}/css/portal/login/register.css">
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
				<fmt:message key="contact.h1.CompanyInfo" />
			</h1>
			<div id="register-container" class="register-container">
				<div class="item">
					<span class="label"><fmt:message key="contact.label.DetailInfo" /></span>
<!--
<p>	<strong>律所属性</strong> </p><p>	　　北京环助律师事务所（下称“环助”）是于2010年12月由北京市司法局批准成立的专门从事环境法律业务，并以提供环境法律援助为主要工作任务的公益性律师事务所。 </p><strong>环助设立 </strong><br />　　环助是在中国政法大学污染受害者法律帮助中心的支持和指导下由长期在该中心作为志愿者从事环境法律援助的三位公益律师发起成立的，并得到有关环境公益机构的大力支持和协助。 <br /><strong>环助目标 </strong><br />　　通过提供环境法律援助、提起环境公益诉讼、开展以环境案例为基础的环境法研究和环境法治领域的国际交流与合作，培养高素质的专业环境律师，推动和促进中国环境法治的完善，实现环境正义。 <br />环助使命 <br />　　1. 提提供法律咨询。向国内外的单位和个人提供环境资源法律咨询。 <br />　　2. 代理环境诉讼案件。受当事人委托代理环境污染、生态破坏、资源破坏的诉讼案件。 <br />　　3. 参与非诉讼环境事务。接受委托、聘请或者主动参与环境听证、行政复议、调解、仲裁、谈判、合同或文件的审查等。 <br />　　4. 担任常年法律顾问。受聘作为个人或者单位（不包括非环境友好单位）的常年法律顾问； <br />　　5. 接受有关机构委托进行环境立法、执法和司法问题的课题研究或者法规草案的起草； <br />　　6. 对律师和环境NGOs 进行环境法律实务的公益培训。 <br /><strong>环助团队 </strong><br />　　环助律师团队由专职公益律师和兼职志愿律师共同组成。目前有专职律师9名，兼职律师6名。同时由中国政法大学环境资源法研究所以王灿发教授为代表的9名环境资源法教授、副教授作为顾问团队，中国政法大学的数十名环境资源法研究生和博士生作为志愿者群体。专家掌舵、学者指导、律师办案、志愿者后援是环助律师事务所最鲜明的团队组成特色。 <br />管理模式 <br />　　环助律师事务所实行合伙建所、理事会决策和专业骨干管理的模式。由律师事务所主任、支持单位代表和相关专家组成理事会，对律师事务所的重大事项进行决策，并由理事会选定的专业骨干律师负责对律所进行行政和业务管理。 <br /><br />通讯地址：北京市海淀区学院南路38号智慧大厦304A<br />律所邮箱：bealflawfirm@163.com<br />联系电话：86-10-8222-8715/8725<br /><div>	<br /></div>
 -->
				</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
