<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../common/portal_taglib.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="search.title.HomePage" /></title>
<link rel='icon' href='${ctx}/images/logo.ico' type='image/x-ico' />
<link rel="stylesheet" type="text/css"
	href="${ctx}/css/portal/news/style-news-common-content.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/css/portal/news/style-news-detail-content.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/css/portal/news/style-news-detail-comment-form.css">
<script language="javascript" type="text/javascript" charset="UTF-8"
	src="${ctx}/js/common/kindeditor/kindeditor-min.js"></script>
<script language="javascript" type="text/javascript" charset="UTF-8"
	src="${ctx}/js/common/kindeditor/lang/zh_CN.js"></script>

<script>
	var editor;
	var limitTxtLenght = 100;

	KindEditor.ready(function(K) {
		editor = K.create('textarea[id="commentContent"]', {
			allowFileManager : true,
			afterBlur : function() {
				this.sync();
				K.ctrl(document, 13, function() {
					K('form[name=form]')[0].submit();
				});
				K.ctrl(this.edit.doc, 13, function() {
					K('form[name=form]')[0].submit();
				});
			},
			afterChange : function() {
				if (this.count('text') > limitTxtLenght) {
					var strValue = editor.text();
					strValue = strValue.substring(0, limitTxtLenght);
					editor.text(strValue);
				}
			}
		});
	});
</script>
<script type="text/javascript">
	$(document).ready(function() {

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
		<div id="center-container">
			<div id="current-location">
				<a href="index.html"><fmt:message key="global.txt.Home" /></a> > <a href="newsList.html"><fmt:message key="newsList.title.HomePage" /></a>
			</div>
			<div id="news-container">
				<div id="news-detail-container">
					<c:if test="${newsDetail.newsId != null}">
						<input id="hiddenNewsDetailId" type="hidden" value="${newsDetail.newsId}" />
						<article class="news-article">
						<h3>${newsDetail.title}</h3>
						<ul>
							<li><fmt:message key="newsList.ul.li.Date" /><fmt:formatDate value="${newsDetail.releaseTime}"
									pattern="yyyy-MM-dd HH:mm:ss" />
							</li>
							<li><fmt:message key="newsList.ul.li.Source" />${newsDetail.source}</li>
							<li><fmt:message key="newsList.ul.li.Type" />${newsDetail.columnName}</li>
						</ul>
						<br />
						<p>${newsDetail.content}</p>
						<hr>
						</article>
					</c:if>

				</div>
				<div id="search-and-newscolumn">
					<div id="search-container">
						<form action="newsList.html">
							<input type="search" class="input-search" placeholder="<fmt:message key='newsList.search.Placeholder' />"
								value="${searchKeyWord}" name="keyword" title="<fmt:message key='newsList.search.Title' />" required="required" />
							<input type="submit" style="display: none;" class="search-submit"
								value="<fmt:message key='newsList.search.button' />" />

						</form>
					</div>
					<div id="newscolumn-container">
						<div id="newscolumn-title"><fmt:message key='newsList.news.Type' /></div>
						<div id="newscolumn-ul">
							<ul>
								<c:forEach var="item" items="${newsColumnList}" varStatus="status">
									<li><span> <a
											href="newsList.html?columnId=${item.columnId}">
												${item.columnName}</a></span></li>
									<c:if test="${status.last==false}">
										<hr>
									</c:if>

								</c:forEach>

							</ul>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>
</body>
</html>
