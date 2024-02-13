<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../common/portal_taglib.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="search.title.HomePage" /></title>
<link rel='icon' href='${ctx}/images/logo.ico' type='image/x-ico' />

<link rel="stylesheet" href="${ctx}/css/portal/search/style-search.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/portal/search/style-search-paging-content.css" />
<link rel="stylesheet" href="${ctx}/css/portal/search/style-smart-input.css" type="text/css" />
<script type="text/javascript" src="${ctx}/js/portal/search/search.js"></script>
<!-- 法院、法官智能输入框 -->
<script type="text/javascript">

window.onload = function(){
	$("#input-court").keyup(function(){
		getList('court', 'input-courtId', 'input-court');
	}).blur(function(){
		onblurEvent('court', 'input-courtId', 'input-court');
	}).focus(function(){
		getList('court', 'input-courtId', 'input-court');
	});

	$("#input-judge").keyup(function(){
		getList('judge', 'input-judgeId', 'input-judge');
	}).blur(function(){
		onblurEvent('judge', 'input-judgeId', 'input-judge');
	}).focus(function(){
		getList('judge', 'input-judgeId', 'input-judge');
	});
};

</script>
<script type="text/javascript">
	// When document is ready, initialize pagination
	$(document).ready(function() {
		$("#key-word-input").val($("#hiddenSearchKeyWords").val());

		$("#search-keyword-content-container .closeIco").click(function() {
			var dc = $(this).attr("data-control");
			var currentFullUrl = window.location.href;
			currentFullUrl = removePara(currentFullUrl, 'pageId');
			currentFullUrl = addPara(currentFullUrl, 'pageId', '0');
			window.location.href = removePara(currentFullUrl, dc);
		});
		// Initialize pagination
		initPagination();

		$("#input-court").focus();
	});

	function initPagination() {
		//总条数
		var articleTotalSize = $("#articleTotalSize").val();
		var keyword = $("#key-word-input").val();
		var pageSize = $("#pageSize").val();
		var current_page = ${currentPageNum}; // Modified by winbill from currentPageNum to current_page
		$("#pageId").attr('value', current_page);

		var court = $("#articleCourt").val();
		var judge = $("#judge").val();
		var articleType = $("#articleType").val();
		var judgeYear = $("#judgeYear").val();
		var label= $("#label").val();
		var tag= $("#tag").val();
		// 拼接URI
		// 
		var link = '?pageId=__id__';
		link = addPara(link, 'keyword', encodeChineseURI(keyword));
		link = addPara(link, 'articleType', encodeChineseURI(articleType));
		link = addPara(link, 'articleCourt', encodeChineseURI(court));
		link = addPara(link, 'judge', encodeChineseURI(judge));
		link = addPara(link, 'judgeYear', encodeChineseURI(judgeYear));
		link = addPara(link, 'label', encodeChineseURI(label));
		link = addPara(link, 'tag', encodeChineseURI(tag));

		/* var prePageTxt = eval("<fmt:message key="search.button.PreviousPage" />");
		 var nextPageTxt = eval("<fmt:message key="search.button.NextPage" />"); */
		if (articleTotalSize > 0) {
			$("#Pagination").pagination(articleTotalSize, {
				current_page : current_page, // Modified from current_page to currentPageNum
				items_per_page : pageSize,
				prev_text : "<",
				next_text:">",
				link_to : link,
				callback : pageselectCallback
			});
		}
	}

	function pageselectCallback(page_index, jq) {
		$("#pageId").attr('value', page_index);
		return true;
	}

	function clickKeyWords() {
		/* var keyWords = encodeChineseURI($("#hiddenSearchKeyWords").val());
		window.location.href = "${ctx}/portal/searchfile/searchFile/searchFile?keyword="
				+ keyWords;
		return true; */
		return false;
	}

	function clickItemArticle(articleID) {
		// To be continue...
		// Should be opened in new window
		var guideCase= $("#guideCase").val();
		var keyWords = encodeChineseURI($("#hiddenSearchKeyWords").val());
		var newURL = "${ctx}/docDetail.html?articleId=" + articleID
				+ "&keyword=" + keyWords;
		if (guideCase != null && guideCase != '') {
			newURL = newURL + "&guideCase=" + guideCase;
		}
		window.open(newURL, '_blank');
		return true;
//		return addPara('articleId', articleID);
	}
	function clickArticleType(typeName) {
		currentFullUrl = window.location.href;
		currentFullUrl = removePara(currentFullUrl, 'pageId');
		currentFullUrl = addPara(currentFullUrl, 'pageId', '0');
		currentFullUrl = addPara(currentFullUrl, 'articleType', encodeChineseURI(typeName));
		window.location.href = currentFullUrl;
		return true;
	}
	function clickArticleCourt(courtName) {
		currentFullUrl = window.location.href;
		currentFullUrl = removePara(currentFullUrl, 'pageId');
		currentFullUrl = addPara(currentFullUrl, 'pageId', '0');
		currentFullUrl = addPara(currentFullUrl, 'articleCourt', encodeChineseURI(courtName));
		window.location.href = currentFullUrl;
		return true;
	}
	function clickArticleYear(judgeYear) {
		currentFullUrl = window.location.href;
		currentFullUrl = removePara(currentFullUrl, 'pageId');
		currentFullUrl = addPara(currentFullUrl, 'pageId', '0');
		currentFullUrl = addPara(currentFullUrl, 'judgeYear', encodeChineseURI(judgeYear));
		window.location.href = currentFullUrl;
		return true;
	}
	function clickSearchCourt() {
		var court = $("#input-court").val();
		var judge = $("#input-judge").val();
		if ((court == null || court == '') && (judge == null || judge == '')) {
			return false;
		}
		var oldCourt = $("#articleCourt").val();
		var oldJudge = $("#judge").val();
		//
		currentFullUrl = window.location.href;
		currentFullUrl = removePara(currentFullUrl, 'pageId');
		currentFullUrl = addPara(currentFullUrl, 'pageId', '0');
		if (court != null && court != '') {
			currentFullUrl = removePara(currentFullUrl, 'articleCourt');
		}
		if (judge != null && judge != '') {
			currentFullUrl = removePara(currentFullUrl, 'judge');
		}
		currentFullUrl = addPara(currentFullUrl, 'articleCourt', encodeChineseURI(court));
		currentFullUrl = addPara(currentFullUrl, 'judge', encodeChineseURI(judge));
		window.location.href = currentFullUrl;
		return true;
	}
	function clickLabel(label) {
		currentFullUrl = window.location.href;
		currentFullUrl = removePara(currentFullUrl, 'pageId');
		currentFullUrl = addPara(currentFullUrl, 'pageId', '0');
		currentFullUrl = removePara(currentFullUrl, 'label');
		currentFullUrl = addPara(currentFullUrl, 'label', encodeChineseURI(label));
		window.location.href = currentFullUrl;
		return true;
	}
	function clickTag(tag) {
		currentFullUrl = window.location.href;
		currentFullUrl = removePara(currentFullUrl, 'pageId');
		currentFullUrl = addPara(currentFullUrl, 'pageId', '0');
		currentFullUrl = removePara(currentFullUrl, 'tag');
		currentFullUrl = addPara(currentFullUrl, 'tag', encodeChineseURI(tag));
		window.location.href = currentFullUrl;
		return true;
	}
	function removeName(item) {
		var val = $("#"+item).val();
		if (val == null || '' == val) {
			$("#"+item).removeAttr('name');
		}
		return true;
	}
	function clickSearchButton() {
		// If the following input text is null, do NOT submit these values
		removeName('judge');
		removeName('guideCase');
		removeName('articleType');
		removeName('articleCourt');
		removeName('judgeYear');
		removeName('label');
		removeName('tag');

		return true;
	}
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

		<input id="hiddenSearchKeyWords" type="hidden" value="${searchKeyWord}" />
		<input id="articleTotalSize" type="hidden" value="${articleTotalSize}" />
		<input id="pageSize" type="hidden" value="${pageSize}" />
		
		<!-- main content -->
		<div id="main-container" class="main-container">
			<h1 id="main-page-header-title">
				<c:if test="${guideCase != null && '' != guideCase && guideCase == 'true'}">
					<fmt:message key="search.h1.InstructionCase" />
				</c:if>
				<c:if test="${guideCase == null || '' == guideCase || 'false' == guideCase}">
					<fmt:message key="search.court.h1.FileSearchTitle" />
				</c:if>
			</h1>
			<div id="main-search-container">
				<form method="get" id="main-search-file-form"
					action="${ctx}/court.html">
					<input id="key-word-input" type="text" name="keyword"
						placeholder="<fmt:message key="search.input.PlaceHolder" />"
						maxlength="45" /> <input type="submit"
						id="key-word-search-button"
						onclick="return clickSearchButton();"
						value="<fmt:message key="search.input.SearchButton" />" />
					<input id="guideCase" name="guideCase" type="hidden" value="${guideCase}" />
					<input id="articleType" name="articleType" type="hidden" value="${articleType}" />
					<input id="articleCourt" name="articleCourt" type="hidden" value="${articleCourt}" />
					<input id="judgeYear" name="judgeYear" type="hidden" value="${judgeYear}" />
					<input id="judge" name="judge" type="hidden" value="${judge}" />
					<input id="label" name="label" type="hidden" value="${label}" />
					<input id="tag" name="tag" type="hidden" value="${tag}" />
					<input id="pageId" name="pageId" type="hidden" value="0" />
				</form>
			</div>
		</div>
		<!-- 显示搜索的关键字 -->
		<div id="search-keyword-container">
			<div id="search-keyword-content-container">
				<fmt:message key="search.div_text.CurrentSearchWords" />
				<span class="searchItem"> 
					<c:if test="${guideCase != null && '' != guideCase}">
						<a href="javascript:void(0)" onclick="" style="display:none;"> 
						${guideCase}<i data-control="guideCase" class="closeIco"></i>
						</a>
					</c:if>
					<c:if test="${searchKeyWord != null && '' != searchKeyWord}">
						<a href="javascript:void(0)" onclick="clickKeyWords()"> ${searchKeyWord} <i
							data-control="keyword" class="closeIco"></i>
						</a>
					</c:if>
					<c:if test="${judge != null && '' != judge}">
						<a href="javascript:void(0)" onclick=""> ${judge} <i
							data-control="judge" class="closeIco"></i>
						</a>
					</c:if>
					<c:if test="${articleType != null && '' != articleType}">
						<a href="javascript:void(0)" onclick=""> ${articleType} <i
							data-control="articleType" class="closeIco"></i>
						</a>
					</c:if>
					<c:if test="${articleCourt != null && '' != articleCourt}">
						<a href="javascript:void(0)" onclick=""> ${articleCourt} <i
							data-control="articleCourt" class="closeIco"></i>
						</a>
					</c:if>
					<c:if test="${judgeYear != null && '' != judgeYear}">
						<c:if test="${judgeYear != null && '' != judgeYear && '0' == judgeYear}">
							<a href="javascript:void(0)" onclick=""> <fmt:message key="search.h4.JudgeYear" />(N/A) <i data-control="judgeYear" class="closeIco"></i></a>
						</c:if>
						<c:if test="${judgeYear != null && '' != judgeYear && '0' != judgeYear}">
							<a href="javascript:void(0)" onclick=""> ${judgeYear} <i data-control="judgeYear" class="closeIco"></i></a>
						</c:if>
					</c:if>
					<c:if test="${label != null && '' != label}">
						<a href="javascript:void(0)" onclick=""> ${label} <i
							data-control="label" class="closeIco"></i>
						</a>
					</c:if>
					<c:if test="${tag != null && '' != tag}">
						<a href="javascript:void(0)" onclick=""> ${tag} <i
							data-control="tag" class="closeIco"></i>
						</a>
					</c:if>
				</span>
			</div>
		</div>
		<!-- 显示法院、法官搜索部分 -->
		<div id="search-casecause-container">
			<div id="search-casecause-content-container">
				<!--<fmt:message key="search.court.div_text.Litigant" />-->
				<span class="inputItem">
					<input id="input-courtId" name="input-courtId" value="" style="display: none;" />
					<input id="input-judgeId" name="input-judgeId" value="" style="display: none;" />
					<input id="input-court" type="text" name="input-court"
						placeholder="<fmt:message key="search.court.input.PlaceHolder" />"
						maxlength="45" />
					<input id="input-judge" type="text" name="input-judge"
						placeholder="<fmt:message key="search.judge.input.PlaceHolder" />"
						maxlength="45" />
					<input type="submit"
						id="cause-search-button"
						onclick="clickSearchCourt()"
						value="<fmt:message key="search.input.SearchButton" />" />
					<div id="input-courtError" style="color: red;"></div>
					<div id="input-judgeError" style="color: red;"></div>
					<input id="NoData" type="hidden" value="<fmt:message key='search.input.DataNotExist' />" />
					<input id="ErrorMessage" type="hidden" value="<fmt:message key='search.input.Exception' />" />
					
				</span>
			</div>
		</div>
		<!-- 搜索出来的内容 -->
		<div id="search-content-container">
			<!-- 显示左侧的文章列表 -->
			<div id="search-content-article-container">
				<div id="search-content-foreach-container">
					<c:forEach var="item" items="${searchArticleList}"
						varStatus="status">
						<article class="search-result-article">
						<h3 class="search-article-title-h3">
							<a href="javascript:void(0)" onclick="clickItemArticle('${item.articleId}')">${item.articleTitle}</a>
						</h3>
						<p>${item.articleContent}</p>
						<ul class="search-article-info-ul">
							<li class="search-article-info-date-li"><span><fmt:message
										key="search.li.FileDate" /></span>${item.articleJudgeDate}</li>
							<li class="search-article-info-source-li"><span><fmt:message
										key="search.li.FileSource" /></span>${item.articleCourt}</li>
							<li class="search-article-info-type-li"><span><fmt:message
										key="search.li.FileType" /></span>${item.articleType}</li>
							<li class="search-article-info-number-li"><span><fmt:message
										key="search.li.FileNumber" /></span>${item.articleCaseNO}</li>
						</ul>
						<br />

						<hr>
						</article>


					</c:forEach>
				</div>
				<div id="Pagination"></div>
				<br style="clear: both;" />
			</div>

			<!-- 显示右侧的分类列表 -->
			<div id="search-content-type-container">
				<div class="search-article-tag type-item">
					<h4><fmt:message key="search.h4.tag" /></h4>
					<ul>
						<c:forEach var="item" items="${articleTagList}"
							varStatus="status">
							<li class="article-type-li"><a href="javascript:void(0)"
								onclick="clickTag('${item.label}')">${item.label} (${item.labelNum})</a>
							</li>
						</c:forEach>
					</ul>
				</div>
				<div style="display: none;" class="search-article-label type-item">
					<h4><fmt:message key="search.h4.Label" /></h4>
					<ul>
						<c:forEach var="item" items="${articleLabelList}"
							varStatus="status">
							<li class="article-type-li"><a href="javascript:void(0)"
								onclick="clickLabel('${item.label}')">${item.label} (${item.labelNum})</a>
							</li>
						</c:forEach>
					</ul>
				</div>
				<div class="search-article-type type-item">
					<h4>
						<fmt:message key="search.h4.ArticleType" />
					</h4>
					<ul id="article-type-group-ul">
						<c:forEach var="item" items="${articleTypeList}"
							varStatus="status">
							<li class="article-type-li"><a href="javascript:void(0)"
								onclick="clickArticleType('${item.articleType}')">
									${item.articleType} (${item.articleTypeNum}) </a></li>
						</c:forEach>
					</ul>
				</div>
				<div class="search-article-court type-item">
					<h4><fmt:message key="search.h4.Court" /></h4>
					<ul>
						<c:forEach var="item" items="${articleCourtList}"
							varStatus="status">
							<li class="article-type-li"><a href="javascript:void(0)"
								onclick="clickArticleCourt('${item.courtName}')">${item.courtName} (${item.courtCount})</a>
							</li>
						</c:forEach>
					</ul>
				</div>
				<div class="search-article-date type-item">
					<h4><fmt:message key="search.h4.JudgeYear" /></h4>
					<ul>
						<c:forEach var="item" items="${articleDateList}"
							varStatus="status">
							<c:if test="${item.articleDateStr != null && '' != item.articleDateStr && '0' == item.articleDateStr}">
								<li class="article-type-li"><a href="javascript:void(0)"
									onclick="clickArticleYear('${item.articleDateStr}')">N/A (${item.articleDateCount})</a>
								</li>
							</c:if>
							<c:if test="${item.articleDateStr != null && '' != item.articleDateStr && '0' != item.articleDateStr}">
								<li class="article-type-li"><a href="javascript:void(0)"
									onclick="clickArticleYear('${item.articleDateStr}')">${item.articleDateStr} (${item.articleDateCount})</a>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer -->
		<%-- <jsp:include page="../common/footer.jsp"></jsp:include> --%>
	</div>
	<span id="spanOutput" class="spanTextDropdown" onmouseout="SetNoColor(this)" onfocus="return specialClickEvent();"></span>
</body>
</html>
