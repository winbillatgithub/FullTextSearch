<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/admin_taglib.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>

<script type="text/javascript" src="${ctx}/js/files/vendor/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${ctx}/js/files/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${ctx}/js/files/jquery.fileupload.js"></script>

<!-- bootstrap just to have good looking page -->
<script type="text/javascript" src="${ctx}/js/files/bootstrap.min.js"></script>
<link href="${ctx}/css/files/bootstrap.css" type="text/css" rel="stylesheet" />

<link rel="stylesheet" type="text/css" href="${ctx}/css/files/jquery.fileupload-ui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/files/jquery.fileupload.css">

<!-- we code these -->
<link href="${ctx}/css/files/dropzone.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/js/files/myuploadfunction.js"></script>

<script>
function openMetaData() {
//	alert("${ctx}");
	openWin("元数据", "${ctx}/admin/files/fileController/getMetaData?fileId=1233", 1200, 650);
}
</script>
</head>
<body>

<h1 style="text-align:center">法律文书上传<br/></h1> 


<div style="width:700px;padding:20px;">

	<input id="fileupload" type="file" name="files[]" data-url="${ctx}/admin/files/fileController/uploadFile" single />
	
	<div id="dropzone" class="fade well">Drop files here</div>
	
	<div id="progress" class="progress">
    	<div class="bar" style="width: 0%;"></div>
	</div>
	
	<input type="button" class="btn122" value="元数据" onclick="openMetaData();"/>
	
	<h5 style="text-align:center"><i style="color:#ccc"><small>Max File Size: 2 Mb - Display last 20 files</small></i></h5>

</div>


</body>
</html>