<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../common/portal_taglib.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="terms.title.terms" /></title>
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
				<fmt:message key="terms.h1.polices" />
			</h1>
			<div id="register-container" class="register-container">
				<div class="item">
					<span class="label">
<span></span>一．服务内容<br />
　　环境诉讼法律文书网的具体服务内容由<span>环境诉讼法律文书</span>网根据实际情况提供，例如法律文书搜索、个人信息、个人分享信息以及评论，在线交流等。<span>环境诉讼法律文书网</span>保留变更、中断或终止部分网络服务的权利。<br />
<span></span><br />
二．注册义务<br />
为了能使用本服务，您同意以下事项：依本服务注册提示请您填写正确的注册邮箱，并确保今后更新的登陆邮箱、名号、头像等资料的有效性和合法性。若您提供任何违法、不道德或<span>环境诉讼法律文书网</span>认为不适合在<span>环境诉讼法律文书网</span>上展示的资料；或者<span>环境诉讼法律文书网</span>有理由怀疑你的资料属于程序或恶意操作，<span>环境诉讼法律文书网</span>有权暂停或终止您的帐号，并拒绝您于现在和未来使用本服务之全部或任何部分。<br />
<br />
三．内容使用权<br />
用户在<span>环境诉讼法律文书网</span>网发表的内容（包含但不限于<span>环境诉讼法律文书网</span>网目前各产品功能里的内容）仅表明其个人的立场和观点，并不代表<span>环境诉讼法律文书网</span>网的立场或观点。作为内容的发表者，需自行对所发表内容负责，因所发表内容引发的一切纠纷，由该内容的发表者承担全部法律及连带责任。<span>环境诉讼法律文书网</span>网不承担任何法律及连带责任。用户在<span>环境诉讼法律文书网</span>发布侵犯他人知识产权或其他合法权益的内容，<span>环境诉讼法律文书网</span>有权予以删除，<span>环境诉讼法律文书网</span>不承担任何法律及连带责任，并保留移交司法机关处理的权利。　　<br />
用户不得使用<span>环境诉讼法律文书网</span>服务发送或传播敏感信息和违反国家法律制度的信息，包括但不限于下列信息：<br />
1. 反对宪法所确定的基本原则的；<br />
2. 危害国家安全，泄露国家秘密，颠覆国家政权，破坏国家统一的；<br />
3. 损害国家荣誉和利益的；<br />
4. 煽动民族仇恨、民族歧视，破坏民族团结的；<br />
5. 破坏国家宗教政策，宣扬邪教和封建迷信的；<br />
6. 散布谣言，扰乱社会秩序，破坏社会稳定的；<br />
7. 散布淫秽、色情、赌博、暴力、凶杀、恐怖或者教唆犯罪的；<br />
8. 侮辱或者诽谤他人，侵害他人合法权益的；<br />
9. 含有法律、行政法规禁止的其他内容的。
<br />
<p>
	用户承诺发表言论要：爱国、守法、自律、真实、文明。不传输任何非法的、骚扰性的、中伤他人的、辱骂性的、恐吓性的、伤害性的、庸俗的，淫秽的、危害国家安全的、泄露国家机密的、破坏国家宗教政策和民族团结的以及其它违反法律法规及政策的内容。 若用户的行为不符合以上提到的服务条款，环境诉讼法律文书网将作出独立判断立即取消用户服务帐号。用户需对自己在网上的行为承担法律责任，环境诉讼法律文书网不承担任何法律及连带责任。
</p>
<br />
四．隐私保护<br />
<p>
	&nbsp; &nbsp; 保护用户隐私是<span>环境诉讼法律文书网</span>的重点原则，<span>环境诉讼法律文书网</span>通过技术手段、提供隐私保护服务功能、强化内部管理等办法充分保护用户的个人资料安全。
</p>
	本隐私声明适用于<span>环境诉讼法律文书网</span>的所有相关服务。<br />
　　<span>环境诉讼法律文书网</span>严格保护您个人信息的安全。我们使用各种安全技术和程序来保护您的个人信息不被未经授权的访问、使用或泄漏。<br />
<br />
五．免责声明<br />
　　一旦您注册成为用户即表示您与<span>环境诉讼法律文书网</span>达成协议，完全接受本服务条款项下的全部条款。对免责声明的解释、修改及更新权均属于<span>环境诉讼法律文书网</span><span></span>所有。<br />
1. 由于您将用户密码告知他人或与他人共享注册帐户，由此导致的任何个人信息的泄漏，或其他非因本网站原因导致的个人信息的泄漏，<span>环境诉讼法律文书网</span>不承担任何法律责任；<br />
2. 任何第三方根据<span>环境诉讼法律文书网</span>各服务条款及声明中所列明的情况使用您的个人信息，由此所产生的纠纷，<span>环境诉讼法律文书网</span>不承认任何法律责任；<br />
3. 任何由于黑客攻击、电脑病毒侵入或政府管制而造成的暂时性网站关闭，<span>环境诉讼法律文书网</span>不承担任何法律责任；<br />
4. 我们鼓励用户充分利用<span>环境诉讼法律文书网</span>平台自由地张贴和共享自己的信息。<br />
5. 用户在<span>环境诉讼法律文书网</span>发布侵犯他人知识产权或其他合法权益的内容，<span>环境诉讼法律文书网</span>有权予以删除，并保留移交司法机关处理的权利。<span></span><br />
</p>
					</span>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
