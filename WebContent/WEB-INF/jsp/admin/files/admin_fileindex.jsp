<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="../common/admin_taglib.jsp"%>

<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>文件元数据</title>

<link href="${ctx}/css/admin/formtyle.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/js/portal/search/search.js"></script>

<style type="text/css">
.errorTxt {
	color: #f00;
	font-size: smaller;
}
</style>

<script>
function openLabels(label) {
	//alert("${ctx}");
	openSecondPopupWin("分组标签", "${ctx}/admin/files/labelEntityController/getLabelView?readonly=true&label="+label, 500, 550);
}
function openTags(label) {
	//alert("${ctx}");
	openSecondPopupWin("环助码", "${ctx}/admin/files/labelEntityController/getHzNumberView?readonly=true&label="+label, 500, 550);
}

function validateAndSave(form) {
	if($("#"+form).form('validate')) {
		// validate court
		commonSave('${ctx}/admin/files/uploadedFileInfoController/updateIndexFile', form, this);
	} else {
		return false;
	}
}

</script>
</head>
<body style="width:100%; ">
	<form id="formmeta" name="formmeta" method="post">
		<div style="width:100%; border:1px solid #ddd; overflow:hidden;/* padding:20px; */">
		<input id="id" name="id" value="${fileMetaData.id}" style="display: none;" />
		<table class="form-table" width="100%">
			<tr>
				<th class="form-table-hd" colspan="2"><div class="tit mr20"><strong>分组标签</strong></div></th>
			</tr>
			<tr>
				<th class="label">标签1：</th>
				<td>
					<input id="keywordId1" name="keywordId1" value="" style="display: none;" />
					<input id="keyword1" readonly="readonly" name="keyword1" value="${fileMetaData.keyword1}" class="easyui-validatebox inp-txt w150" maxlength="20"/>
					<input type="button" style="width: 25px;height: 22px;" value="..." onclick="return openLabels('keyword1');"/>
				</td>
				<th class="label">标签2：</th>
				<td>
					<input id="keywordId2" name="keywordId2" value="" style="display: none;" />
					<input id="keyword2" readonly="readonly" name="keyword2" value="${fileMetaData.keyword2}" class="easyui-validatebox inp-txt w150" maxlength="20"/>
					<input type="button" style="width: 25px;height: 22px;" value="..." onclick="return openLabels('keyword2');"/>
				</td>
				<th class="label">标签3：</th>
				<td>
					<input id="keywordId3" name="keywordId3" value="" style="display: none;" />
					<input id="keyword3" readonly="readonly" name="keyword3" value="${fileMetaData.keyword3}" class="easyui-validatebox inp-txt w150" maxlength="20"/>
					<input type="button" style="width: 25px;height: 22px;" value="..." onclick="return openLabels('keyword3');"/>
				</td>
				<th class="label">标签4：</th>
				<td>
					<input id="keywordId4" name="keywordId4" value="" style="display: none;" />
					<input id="keyword4" readonly="readonly" name="keyword4" value="${fileMetaData.keyword4}" class="easyui-validatebox inp-txt w150" maxlength="20"/>
					<input type="button" style="width: 25px;height: 22px;" value="..." onclick="return openLabels('keyword4');"/>
				</td>
			</tr>
			<tr>
				<th class="form-table-hd" colspan="2"><div class="tit mr20"><strong>环助码</strong></div></th>
			</tr>
			<tr>
				<th class="label">环助码1：</th>
				<td>
					<input id="hzNumberId1" name="hzNumberId1" value="" style="display: none;" />
					<input id="hzNumber1" readonly="readonly" name="hzNumber1" value="${fileMetaData.hzNumber1}" class="easyui-validatebox inp-txt w150" maxlength="20"/>
					<input type="button" style="width: 25px;height: 22px;" value="..." onclick="return openTags('hzNumber1');"/>
				</td>
				<th class="label">环助码2：</th>
				<td>
					<input id="hzNumberId2" name="hzNumberId2" value="" style="display: none;" />
					<input id="hzNumber2" readonly="readonly" name="hzNumber2" value="${fileMetaData.hzNumber2}" class="easyui-validatebox inp-txt w150" maxlength="20"/>
					<input type="button" style="width: 25px;height: 22px;" value="..." onclick="return openTags('hzNumber2');"/>
				</td>
				<th class="label">环助码3：</th>
				<td>
					<input id="hzNumberId3" name="hzNumberId3" value="" style="display: none;" />
					<input id="hzNumber3" readonly="readonly" name="hzNumber3" value="${fileMetaData.hzNumber3}" class="easyui-validatebox inp-txt w150" maxlength="20"/>
					<input type="button" style="width: 25px;height: 22px;" value="..." onclick="return openTags('hzNumber3');"/>
				</td>
				<th class="label">环助码4：</th>
				<td>
					<input id="hzNumberId4" name="hzNumberId4" value="" style="display: none;" />
					<input id="hzNumber4" readonly="readonly" name="hzNumber4" value="${fileMetaData.hzNumber4}" class="easyui-validatebox inp-txt w150" maxlength="20"/>
					<input type="button" style="width: 25px;height: 22px;" value="..." onclick="return openTags('hzNumber4');"/>
				</td>
			</tr>
			<tr>
				<th class="label">环助码5：</th>
				<td>
					<input id="hzNumberId5" name="hzNumberId5" value="" style="display: none;" />
					<input id="hzNumber5" readonly="readonly" name="hzNumber5" value="${fileMetaData.hzNumber5}" class="easyui-validatebox inp-txt w150" maxlength="20"/>
					<input type="button" style="width: 25px;height: 22px;" value="..." onclick="return openTags('hzNumber5');"/>
				</td>
				<th class="label">环助码6：</th>
				<td>
					<input id="hzNumberId6" name="hzNumberId6" value="" style="display: none;" />
					<input id="hzNumber6" readonly="readonly" name="hzNumber6" value="${fileMetaData.hzNumber6}" class="easyui-validatebox inp-txt w150" maxlength="20"/>
					<input type="button" style="width: 25px;height: 22px;" value="..." onclick="return openTags('hzNumber6');"/>
				</td>
				<th class="label">环助码7：</th>
				<td>
					<input id="hzNumberId7" name="hzNumberId7" value="" style="display: none;" />
					<input id="hzNumber7" readonly="readonly" name="hzNumber7" value="${fileMetaData.hzNumber7}" class="easyui-validatebox inp-txt w150" maxlength="20"/>
					<input type="button" style="width: 25px;height: 22px;" value="..." onclick="return openTags('hzNumber7');"/>
				</td>
				<th class="label">环助码8：</th>
				<td>
					<input id="hzNumberId8" name="hzNumberId8" value="" style="display: none;" />
					<input id="hzNumber8" readonly="readonly" name="hzNumber8" value="${fileMetaData.hzNumber8}" class="easyui-validatebox inp-txt w150" maxlength="20"/>
					<input type="button" style="width: 25px;height: 22px;" value="..." onclick="return openTags('hzNumber8');"/>
				</td>
			</tr>
		</table>
	</div>
	<br/>
	<div style="text-align:center">
		<input type="button" class="btn122" value="更新" onclick="validateAndSave('formmeta')"/>
	   	<input type="button" class="btn122" value="取消" onclick="closeWin()"/>
	</div>
	</form>
	<span id="spanOutput" class="spanTextDropdown" onmouseout="SetNoColor(this)" onfocus="return specialClickEvent();"></span>
</body>
</jsp>