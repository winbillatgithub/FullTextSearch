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

<link rel="stylesheet" href="${ctx}/css/portal/search/style-search.css"
	type="text/css" />

<link rel="stylesheet"
	href="${ctx}/css/portal/search/style-search-detail-content.css"
	type="text/css" />
<script language="javascript" type="text/javascript" charset="UTF-8"
	src="${ctx}/js/common/kindeditor/kindeditor-min.js"></script>
<script language="javascript" type="text/javascript" charset="UTF-8"
	src="${ctx}/js/common/kindeditor/lang/zh_CN.js"></script>
<script>
	var editor;
	KindEditor.ready(function(K) {
		editor = K.create('textarea[name="content"]', {
			uploadJson : '${ctx}/upload.jsp',
			allowFileManager : true,
			afterBlur : function() {
				this.sync();
				K.ctrl(document, 13, function() {
					K('form[name=form]')[0].submit();
				});
				K.ctrl(this.edit.doc, 13, function() {
					K('form[name=form]')[0].submit();
				});
			}
		});
	});
	
	function logoutPortal(){
		var currentFullUrl = window.location.href;
		var currentContext = ctx;
		var urlSplitArray = currentFullUrl.split(currentContext);
		var urlPrefix = urlSplitArray[0];
		var urlAppendix = urlSplitArray[1];
		var newFullUrl = urlPrefix +"/"+currentContext+"/login.html"+"?from="+urlAppendix ;
		window.location.href = newFullUrl;
	}
	
	function loginPortal(){
		var currentFullUrl = window.location.href;
		var currentContext = ctx;
		var urlSplitArray = currentFullUrl.split(currentContext);
		var urlPrefix = urlSplitArray[0];
		var urlAppendix = urlSplitArray[1];
		var newFullUrl = urlPrefix +"/"+currentContext+"/login.html"+"?from="+urlAppendix ;
		window.location.href = newFullUrl;
	}
	
	function postComment(){
		if (editor.html() == null || editor.html() == '') {
			return false;
		}
		$.ajax({
			type : 'post',
			cache : false,
			dataType : 'json',
			url : ctx + '/portal/searchfile/showDetailFile/comment',
			data : {
				articleId: $("#hiddenArticleId").val(),
				commentContent: editor.html()
			},
			success : function(data) {
				if (data.success) {
					window.location.href = window.location.href;
				}
			}
		});
	}

	function clickLabel(label) {
		// To be continue...
		// Should be opened in new window
		var label = encodeChineseURI(label);
		var newURL = "${ctx}/docList.html?label=" + label;

		window.open(newURL, '_blank');
		return true;
	}
	function clickTag(tag) {
		// To be continue...
		// Should be opened in new window
		var tag = encodeChineseURI(tag);
		var newURL = "${ctx}/docList.html?tag=" + tag;

		window.open(newURL, '_blank');
		return true;
	}
	
	function exportPdf(){
		var fileName = encodeChineseURI($("#hiddenArticleId").val());
		var fileContent = encodeChineseURI($("#search-content-article-detail-page-container").html());
		var postData = {"fileName":fileName,"fileContent":fileContent};
		openPostWindow(ctx + '/portal/searchfile/showDetailFile/exportPDF',postData,"下载PDF");
	}
	
	function openPostWindow(url, data, name)  {
		 var tempForm = document.createElement("form");  
		  tempForm.id="tempForm1";  
		  tempForm.method="post";  
		  tempForm.action=url;  
		  tempForm.target=name;  
		  var hideInput = document.createElement("input");  
		  hideInput.type="hidden";  
		  hideInput.name= "content"
		  hideInput.value= data;
		  tempForm.appendChild(hideInput);   
		 // tempForm.attachEvent("onsubmit",function(){ openWindow(name); });
		  document.body.appendChild(tempForm);  
		 // tempForm.fireEvent("onsubmit");
		  tempForm.submit();
		  document.body.removeChild(tempForm);
	}

</script>
</head>
<!-- oncontextmenu="return false" onselectstart="return false" ondragstart="return false" onbeforecopy="return false" oncopy=document.selection.empty() onselect=document.selection.empty() -->
<body style="background: white;" >
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

		
		<input id="hiddenArticleId" type="hidden" value="${articleId}" />
		<!-- main content -->
<!-- 
		<div id="main-container" class="main-container">
			<h1 id="main-page-header-title">
				<c:if test="${guideCase != null && '' != guideCase && guideCase == 'true'}">
					<fmt:message key="search.h1.InstructionCase" />
				</c:if>
				<c:if test="${guideCase == null || '' == guideCase || 'false' == guideCase}">
					<fmt:message key="search.h1.FileSearchTitle" />
				</c:if>
			</h1>
			<div id="main-search-container">
				<form method="get" id="main-search-file-form" action="searchFile">
					<input id="key-word-input" type="text" value="${searchKeyWord}"
						name="keyword"
						placeholder="<fmt:message key="search.input.PlaceHolder" />"
						maxlength="45" /> <input type="submit"
						id="key-word-search-button"
						value="<fmt:message key="search.input.SearchButton" />" />
				</form>
			</div>
		</div>
 -->
		<!-- 显示搜索的关键字 -->
		<div id="search-keyword-container">
			<div id="search-keyword-content-container">
				<fmt:message key="global.txt.CurrentLocation" />
				<a href="${ctx}/index.html"><fmt:message key="header.li.MainPage" /></a> / <fmt:message key="search.detail.header.Article" /> / ${articleTitle}
			</div>
			
		</div>
		<!-- 搜索出来的内容 -->
		<div id="search-content-container">
		    <div><input id="exportPdf" type="button" value="导出pdf" onclick="exportPdf();" /></div>
			<!-- 显示左侧的文章明细 -->
			<div id="search-content-article-detail-container">
				<div id="search-content-article-detail-page-container">
					${articleContentHtml}</div>
				<hr>
				<div id="search-content-article-detail-comment-container">
					<div class="commentList">
						<c:forEach var="item" items="${commentList}" varStatus="status">
							<article>
							<div class="commentUserImage">
								<image src="${ctx}/images/search/defaultUserImg.jpg"
									alt="<fmt:message key='search.detail.img.default' />" />
							</div>

							<div class="commentInfo">
								<div class="commentUserName">${item.commentBy}:</div>
								<div class="commentContent">${item.commentContent}</div>
							</div>
							</article>
							<c:if test="${status.last==false}">
								<hr>
							</c:if>
						</c:forEach>
					</div>
					<div class="commentContainer">
					  <h3><fmt:message key="search.detail.comment.h3" /></h3>
					  <div id="currentCommentUser">
					   <c:if test="${currentLoginUserName !=null && '' != currentLoginUserName}">
					    <div class="currentLoginUser">
					    	<fmt:message key="search.detail.div.user" />:${currentLoginUserName}
					    	<!-- <a href="javascript:void(0)" onclick="logoutPortal()" ><fmt:message key="search.detail.a.logout" /></a> -->
					    </div>
					   </c:if>
					   <c:if test="${currentLoginUserName ==null || '' == currentLoginUserName}">
					    <div class="toLogin">
					    	<fmt:message key="search.detail.div.loginClick" /><a href="javascript:void(0)" onclick="loginPortal()"><fmt:message key="search.detail.div.login" /></a>	
					  	</div>
					   </c:if>
					  	
					  </div>
						<textarea  rows="20" cols="80" name="content"></textarea>
						<div id="postCommentButton-container">
							 <input id="postCommentButton" type="button" onclick="postComment()" value="<fmt:message key='search.detail.button.PostComment' />" />
						</div>
					</div>
				</div>
			</div>
			<!-- 显示右侧的分类列表 -->
			<div id="search-content-type-container">
				<div class="search-article-tag type-item">
					<h4>
						<fmt:message key="search.h4.tag" />
					</h4>
					<ul>
						<c:forEach var="item" items="${articleTagList}"
							varStatus="status">
							<li class="article-type-li"><a href="javascript:void(0)"
								onclick="clickLabel('${item.label}')">${item.label} <%-- (${item.labelNum}) --%></a>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div style="display: none;" id="search-content-type-container">
				<div class="search-article-label type-item">
					<h4>
						<fmt:message key="search.h4.Label" />
					</h4>
					<ul>
						<c:forEach var="item" items="${articleLabelList}"
							varStatus="status">
							<li class="article-type-li"><a href="javascript:void(0)"
								onclick="clickLabel('${item.label}')">${item.label} <%-- (${item.labelNum}) --%></a>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div id="search-content-type-container">
				<div class="search-article-type type-item">
					<h4>
						<fmt:message key="search.h4.ArticleType" />
					</h4>
					<ul id="article-type-group-ul">
						<li class="article-type-li"><fmt:message key="search.detail.li.CaseNo" />${articleCaseNO}</li>
						<li class="article-type-li"><fmt:message key="search.detail.li.Court" />${articleCourt}</li>
						<li class="article-type-li"><fmt:message key="search.detail.li.JudgeDate" />${articleJudgeDate}</li>
						<li class="article-type-li"><fmt:message key="search.detail.li.Cause" />${articleReason}</li>
						<li class="article-type-li"><fmt:message key="search.detail.li.Type" />${articleType}</li>
						<li class="article-type-li"><fmt:message key="search.detail.li.Step" />${articleStep}</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer -->
		<%-- <jsp:include page="../common/footer.jsp"></jsp:include> --%>
	</div>
</body>
</html>
