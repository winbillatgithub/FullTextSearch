<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/admin_taglib.jsp"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新闻预览</title>
</head>

<div class="main" style="height: 100%; width: 96.5%; overflow: hidden;">
	<div class="info-box">
		<div class="titB">${newsInfoEntity.columnName }</div>
		<h1>${newsInfoEntity.title }</h1>
		<div class="info-hd">
			<span class="form">公告来源：${newsInfoEntity.source }</span> <span class="time">发布日期：<fmt:formatDate
					value="${newsInfoEntity.releaseTime }" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></span>
		</div>
		<div class="info-bd">
			<p style="word-break: break-all">${newsInfoEntity.content }</p>
		</div>
		<div class="info-ft"></div>
	</div>
	<c:if test="${newsInfoEntity.status==1||newsInfoEntity.status==6}">
		<form id="form" name="form" method="post">
			<table align="center" width="100%" border="0" cellpadding="0" cellspacing="0">
			</table>
		</form>
	</c:if>
</div>

</body>

</html>