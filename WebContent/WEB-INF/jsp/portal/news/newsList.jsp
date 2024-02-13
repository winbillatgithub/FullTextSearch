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
	href="${ctx}/css/portal/news/style-news-list-content.css">
<link rel="stylesheet"
	href="${ctx}/css/portal/search/style-search-paging-content.css" type="text/css" />
<script type="text/javascript">
	$(document).ready(function() {
		initPagination();
	});

	function initPagination() {
		var getParaStr = "?pageId=__id__";
		//总条数
		var articleTotalSize = $("#pagination_totalSize").val();
		var pageSize = $("#pagination_pageSize").val();
		var current_page = $("#pagination_pageNumber").val();
		var keyword = $("#key-word-input").val();
		if (keyword) {
			getParaStr = getParaStr + "&keyword=" + keyword;
		}

		$("#news-list-pagination-container").pagination(articleTotalSize, {
			current_page : current_page,
			items_per_page : pageSize,
			prev_text : "<",
			next_text:">",
			link_to : getParaStr,
			callback : pageselectCallback
		});
	}

	function pageselectCallback(page_index, jq) {
		return true;
	}
</script>

</head>
<body style="background: white;">
	<input id="pagination_pageSize" type="hidden" value="${pageSize}" />
	<input id="pagination_pageNumber" type="hidden" value="${currentPageNum}" />
	<input id="pagination_totalSize" type="hidden" value="${totalNewsSize}" />
	<input id="search_columnId" type="hidden" value="${columnId}"/>
	<%-- 	<input id="pagination_newsColumn type="hidden" value="${columnId}" /> --%>
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
				<c:if test="${searchKeyWord != null }">
					><span class="searchWords"><fmt:message key='newsList.search.Title' />"${searchKeyWord}"</span>
				</c:if>
				<c:if test="${columnName !=null }">
					><span class="searchWords">${columnName}</span>
				</c:if>
			</div>
			<div id="news-container">
				<div id="news-list-container">
					<div id="news-list-content-container">
						<c:forEach var="item" items="${newsList}" varStatus="status">
							<article class="news-article">
							<h3>
								<a href="newsDetail.html?newsId=${item.newsId}" target="_blank">${item.title}</a>
							</h3>
							<ul>
								<li><fmt:message key="newsList.ul.li.Date" /><fmt:formatDate value="${item.releaseTime}"
										pattern="yyyy-MM-dd HH:mm:ss" />
								</li>
								<%-- <li>日期:${item.releaseTime}</li> --%>
								<li><fmt:message key="newsList.ul.li.Source" />${item.source}</li>
								<li><fmt:message key="newsList.ul.li.Type" />${item.columnName}</li>
							</ul>
							<br />
							<p>${item.newsIntroduction}</p>
							<c:if test="${status.last==false}">
								<hr>
							</c:if> </article>
						</c:forEach>
					</div>
					<div id="news-list-pagination-container"></div>
					<br style="clear: both;" />
				</div>
				<div id="search-and-newscolumn">
					<div id="search-container">
						<form action="">
							<input type="search" class="input-search" placeholder="<fmt:message key='newsList.search.Placeholder' />"
								value="${searchKeyWord}" name="keyword" title="<fmt:message key='newsList.search.Title' />"  />
							<input type="submit" style="display: none;" class="search-submit"
								value="<fmt:message key='newsList.search.button' />" />

						</form>
					</div>
					<div id="newscolumn-container">
						<div id="newscolumn-title"><fmt:message key='newsList.news.Type' /></div>
						<div id="newscolumn-ul">
							<ul>
								<c:forEach var="item" items="${newsColumnList}" varStatus="status">
									<li><span> <a href="?columnId=${item.columnId}">
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
