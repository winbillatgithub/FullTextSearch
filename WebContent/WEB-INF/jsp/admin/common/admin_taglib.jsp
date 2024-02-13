<!-- 引用admin模块的公有css/js文件 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="language"
	value="${not empty param.language ? param.language : pageContext.request.locale}"
	scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="internationalization" />

<script>var ctx = '${ctx}';</script>

<link rel="stylesheet" type="text/css" href="${ctx}/js/common/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/common/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/admin/formtyle.css">
<script src="${ctx}/js/common/jquery-1.11.2.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/js/common/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/common/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/js/common/common.js"></script>	
<script type="text/javascript" src="${ctx}/js/admin/formatter.js"></script>	
<script type="text/javascript" src="${ctx}/js/common/datagrid.js"></script>	
