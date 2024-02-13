<!-- 引用admin模块的公有css/js文件 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script>
var ctx = '${ctx}';

</script>

<!-- portal的公有样式设置 -->
<link rel="stylesheet" href="${ctx}/css/common/style-common.css" type="text/css" />
<!-- headder的样式 -->
<link rel="stylesheet" href="${ctx}/css/portal/common/style-header.css" type="text/css" />
<!-- footer的样式 -->
<link rel="stylesheet" href="${ctx}/css/portal/common/style-footer.css" type="text/css" />
<!-- portal的版权信息 -->
<link rel="stylesheet" href="${ctx}/css/portal/common/style-copyright.css" type="text/css" />
<!-- 分页的jQuery UI CSS -->
<link rel="stylesheet" href="${ctx}/js/common/paging/pagination.css"
	type="text/css" />
<script src="${ctx}/js/common/jquery-1.9.0.js" type="text/javascript"></script> 
<!-- 分页的jQuery UI js -->
<script type="text/javascript" src="${ctx}/js/common/paging/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/js/common/common.js"></script>	
