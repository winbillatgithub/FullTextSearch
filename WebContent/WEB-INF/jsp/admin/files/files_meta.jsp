<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="../common/admin_taglib.jsp"%>

<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>文件元数据</title>

<link href="${ctx}/css/admin/formtyle.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" href="${ctx}/css/portal/search/style-smart-input.css" type="text/css" />
<script type="text/javascript" src="${ctx}/js/portal/search/search.js"></script>

<style type="text/css">
.errorTxt {
	color: #f00;
	font-size: smaller;
}
</style>
<!-- 案由智能输入框 -->
<script type="text/javascript">

window.onload = function(){
	$("#cause").keyup(function(){
		getList('cause', 'causeId', 'cause');
	}).blur(function(){
		onblurEvent('cause', 'causeId', 'cause');
	}).focus(function(){
		getList('cause', 'causeId', 'cause');
	});
};

</script>

<script>
function openCaseCause() {
	//alert("${ctx}");
	openSecondPopupWin("案由", "${ctx}/admin/files/fileController/getCaseCauseView", 500, 550);
}
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
		var val = $("#court").val();
		if (validateItem('${ctx}/admin/files/fileController/validateCourt', val) == false) {
			return false;
		}
		// validate case cause
		var val = $("#cause").val();
		if (validateItem('${ctx}/admin/files/fileController/validateCaseCause', val) == false) {
			return false;
		}
		// validate case type
		var val = $("#type").val();
		if (validateItem('${ctx}/admin/files/fileController/validateCaseType', val) == false) {
			return false;
		}
		// validate court
		commonSave('${ctx}/admin/files/fileController/saveUploadedFileInfo', form, this);
	} else {
		return false;
	}
}
function validateItem(url, val) {
	var ret = false;
	$.ajax({
        type: 'post', 
        cache: false, 
        dataType: 'json',
        async: false,
        url: url,
        data: {'val':val},
        success: function (data) {
        	//alert("success:" + data.success);
			if (data.success) {
				ret = true;
	        } else {
	        	parent.parent.show(data.msg);//显示提示信息
			}
        },
        error : function(data){
        	//alert('message:' + data.responseText);
        	parent.parent.show("错误:" + data.responseText);//显示失败提示信息
        	
        },
        beforeSend: function () {
        	
        }
    });
	return ret;
}
</script>
</head>
<body style="width:100%; ">
	<form id="formmeta" name="formmeta" method="post">
		<div style="width:100%; border:1px solid #ddd; overflow:hidden;/* padding:20px; */">
		<table class="form-table" width="100%">
			<tr>
				<th class="form-table-hd" colspan="2"><div class="tit mr20"><strong>文书信息</strong></div></th>
			</tr>
			<tr style='display:none'>
				<c:if test="${empty fileMetaData.id}">
				<th  class="label" width="150">用户名：</th>
				<td>
					<input id="username" name="username" value="${fileMetaData.id}" class="inp-txt w200 easyui-validatebox" validType="loginName" maxlength="20" required="true"/>
					<div id="errorTxt" class="item errorTxt"></div>
				</td>
				</c:if>
			</tr>
			<input type="hidden" name="id" value="${fileMetaData.id}"/>
			<tr>
				<th class="label">典型案例：</th>
				<td>
					<input name="guideCase" style="width: 20px;" type="radio" value="1" <c:if test="${fileMetaData.guideCase == true }">checked="checked"</c:if><c:if test="${empty fileMetaData.guideCase }">checked="checked"</c:if>/>是
					<input name="guideCase" style="width: 20px;" type="radio" value="0" <c:if test="${fileMetaData.guideCase == false }">checked="checked"</c:if>/>否
				</td>
				<th class="label">标题：</th>
				<td colspan=5><input id="title" name="title" value="${fileMetaData.title}" class="easyui-validatebox inp-txt w550" required="true" maxlength="255"/></td>
			</tr>
			<tr>
				<th class="label">案号：</th>
				<td><input id="caseNo" name="caseNo" value="${fileMetaData.caseNo}" class="easyui-validatebox inp-txt w200" editable="true"/></td>
				<th class="label">案由：</th>
				<td>
				<input id="causeId" name="causeId" value="" style="display: none;" />
				<input id="cause" name="cause" type="text" value="${fileMetaData.cause}" class="easyui-validatebox inp-txt w200" maxlength="255"/>
				<input type="button" style="width: 30px;height: 22px;display:none" value="..." onclick="openCaseCause();"/>
				<div id="causeError" style="color: red;"></div>
				<input id="NoData" type="hidden" value="<fmt:message key='search.input.DataNotExist' />" />
				<input id="ErrorMessage" type="hidden" value="<fmt:message key='search.input.Exception' />" />
				</td>
				<th class="label">类型：</th>
				<td><input id="type" name="type" value="${fileMetaData.type}" class="easyui-validatebox inp-txt w200" required="true" maxlength="255"/></td>
			</tr>
			<tr>
				<th class="label">法院：</th>
				<td><input id="court" name="court" value="${fileMetaData.court}" class="easyui-validatebox inp-txt w200" maxlength="255"/></td>
				<th class="label">当事人原告：</th>
				<td><input id="excutor" name="excutor" value="${fileMetaData.excutor}" class="easyui-validatebox inp-txt w200" maxlength="255"/></td>
				<th class="label">当事人被告：</th>
				<td><input id="excuted" name="excuted" value="${fileMetaData.excuted}" class="easyui-validatebox inp-txt w200" maxlength="255"/></td>
			</tr>
			<tr>
				<th class="label">审判长：</th>
				<td><input name="chiefJudge" value="${fileMetaData.chiefJudge}" class="easyui-validatebox inp-txt w200" maxlength="50"/></td>
				<th class="label">审判员：</th>
				<td><input id="judge" name="judge" value="${fileMetaData.judge}" class="easyui-validatebox inp-txt w200" maxlength="255"/></td>
				<th class="label">书记员：</th>
				<td><input id="clerk" name="clerk" value="${fileMetaData.clerk}" class="easyui-validatebox inp-txt w200" maxlength="255"/></td>
			</tr>
			<tr>
				<th class="label">律师：</th>
				<td><input id="lawyers" name="lawyers" value="${fileMetaData.lawyers}" class="easyui-validatebox inp-txt w200" maxlength="255"/></td>
				<th class="label">律所：</th>
				<td><input id="lawFirm" name="lawFirm" value="${fileMetaData.lawFirm}" class="easyui-validatebox inp-txt w200" maxlength="512"/></td>
				<th class="label">程序：</th>
				<td><input id="step" name="step" value="${fileMetaData.step}" class="easyui-validatebox inp-txt w200" maxlength="255"/></td>
			</tr>
			<tr>
				<th class="label">第三人：</th>
				<td><input id="thirdPart" name="thirdPart" value="${fileMetaData.thirdPart}" class="easyui-validatebox inp-txt w200" maxlength="255"/></td>
				<th class="label">法定代表人：</th>
				<td><input id="legalPerson" name="legalPerson" value="${fileMetaData.legalPerson}" class="easyui-validatebox inp-txt w200" maxlength="255"/></td>
				<th class="label">宣判日期：</th>
				<td><input id="judgeDate" name="judgeDate" value="${fileMetaData.judgeDate}" class="easyui-datebox inp-txt w200" editable="true" /></td>
			</tr>
		</table>
		<table class="form-table" width="100%">
			<tr>
				<th class="form-table-hd" colspan="3"><div class="tit mr20"><strong>文件元数据</strong></div></th>
			</tr>
			<tr>
				<th class="label">文件名称：</th>
				<td colspan="3"><input id="fileName" name="fileName" value="${fileMetaData.fileName}" class="easyui-validatebox inp-txt w450" required="true" maxlength="255"/></td>
				<th class="label">文件大小：</th>
				<td><input id="fileSize" name="fileSize" value="${fileMetaData.fileSize}" class="easyui-validatebox inp-txt w150" required="true" maxlength="255"/></td>
				<th class="label">文件类型：</th>
				<td><input id="fileType" name="fileType" value="${fileMetaData.fileType}" class="easyui-validatebox inp-txt w150" required="true" maxlength="255"/></td>
			</tr>
			<tr style='display:none'>
				<th class="label">上传日期：</th>
				<td><input id="uploadDate" name="uploadDate" value="${fileMetaData.uploadDate}" class="easyui-validatebox inp-txt w150" required="true" maxlength="255"/></td>
				<td><input id="createDate" name="createDate" type="hidden" value="${fileMetaData.createDate}" class="easyui-validatebox inp-txt w150" maxlength="255"/></td>
				<td><input id="modifyDate" name="modifyDate" type="hidden" value="${fileMetaData.modifyDate}" class="easyui-validatebox inp-txt w150" maxlength="255"/></td>
				<td><input id="judgeYear" name="judgeYear" type="hidden" value="${fileMetaData.judgeYear}" class="easyui-validatebox inp-txt w150" maxlength="255"/></td>
			</tr>
			<tr>
				<th class="form-table-hd" colspan="2"><div class="tit mr20"><strong>分组标签</strong></div></th>
			</tr>
			<tr>
				<th class="label">标签1：</th>
				<td>
					<input id="keywordId1" name="keywordId1" value="" style="display: none;" />
					<input id="keyword1" name="keyword1" value="${fileMetaData.keyword1}" class="easyui-validatebox inp-txt w150" maxlength="20"/>
					<input type="button" style="width: 25px;height: 22px;" value="..." onclick="return openLabels('keyword1');"/>
				</td>
				<th class="label">标签2：</th>
				<td>
					<input id="keywordId2" name="keywordId2" value="" style="display: none;" />
					<input id="keyword2" name="keyword2" value="${fileMetaData.keyword2}" class="easyui-validatebox inp-txt w150" maxlength="20"/>
					<input type="button" style="width: 25px;height: 22px;" value="..." onclick="return openLabels('keyword2');"/>
				</td>
				<th class="label">标签3：</th>
				<td>
					<input id="keywordId3" name="keywordId3" value="" style="display: none;" />
					<input id="keyword3" name="keyword3" value="${fileMetaData.keyword3}" class="easyui-validatebox inp-txt w150" maxlength="20"/>
					<input type="button" style="width: 25px;height: 22px;" value="..." onclick="return openLabels('keyword3');"/>
				</td>
				<th class="label">标签4：</th>
				<td>
					<input id="keywordId4" name="keywordId4" value="" style="display: none;" />
					<input id="keyword4" name="keyword4" value="${fileMetaData.keyword4}" class="easyui-validatebox inp-txt w150" maxlength="20"/>
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
					<input id="hzNumber1" name="hzNumber1" readonly="readonly" value="${fileMetaData.hzNumber1}" class="easyui-validatebox inp-txt w150" maxlength="20"/>
					<input type="button" style="width: 25px;height: 22px;" value="..." onclick="return openTags('hzNumber1');"/>
				</td>
				<th class="label">环助码2：</th>
				<td>
					<input id="hzNumberId2" name="hzNumberId2" value="" style="display: none;" />
					<input id="hzNumber2" name="hzNumber2" readonly="readonly" value="${fileMetaData.hzNumber2}" class="easyui-validatebox inp-txt w150" maxlength="20"/>
					<input type="button" style="width: 25px;height: 22px;" value="..." onclick="return openTags('hzNumber2');"/>
				</td>
				<th class="label">环助码3：</th>
				<td>
					<input id="hzNumberId3" name="hzNumberId3" value="" style="display: none;" />
					<input id="hzNumber3" name="hzNumber3" readonly="readonly" value="${fileMetaData.hzNumber3}" class="easyui-validatebox inp-txt w150" maxlength="20"/>
					<input type="button" style="width: 25px;height: 22px;" value="..." onclick="return openTags('hzNumber3');"/>
				</td>
				<th class="label">环助码4：</th>
				<td>
					<input id="hzNumberId4" name="hzNumberId4" value="" style="display: none;" />
					<input id="hzNumber4" name="hzNumber4" readonly="readonly" value="${fileMetaData.hzNumber4}" class="easyui-validatebox inp-txt w150" maxlength="20"/>
					<input type="button" style="width: 25px;height: 22px;" value="..." onclick="return openTags('hzNumber4');"/>
				</td>
			</tr>
			<tr>
				<th class="label">环助码5：</th>
				<td>
					<input id="hzNumberId5" name="hzNumberId5" value="" style="display: none;" />
					<input id="hzNumber5" name="hzNumber5" readonly="readonly" value="${fileMetaData.hzNumber5}" class="easyui-validatebox inp-txt w150" maxlength="20"/>
					<input type="button" style="width: 25px;height: 22px;" value="..." onclick="return openTags('hzNumber5');"/>
				</td>
				<th class="label">环助码6：</th>
				<td>
					<input id="hzNumberId6" name="hzNumberId6" value="" style="display: none;" />
					<input id="hzNumber6" name="hzNumber6" readonly="readonly" value="${fileMetaData.hzNumber6}" class="easyui-validatebox inp-txt w150" maxlength="20"/>
					<input type="button" style="width: 25px;height: 22px;" value="..." onclick="return openTags('hzNumber6');"/>
				</td>
				<th class="label">环助码7：</th>
				<td>
					<input id="hzNumberId7" name="hzNumberId7" value="" style="display: none;" />
					<input id="hzNumber7" name="hzNumber7" readonly="readonly" value="${fileMetaData.hzNumber7}" class="easyui-validatebox inp-txt w150" maxlength="20"/>
					<input type="button" style="width: 25px;height: 22px;" value="..." onclick="return openTags('hzNumber7');"/>
				</td>
				<th class="label">环助码8：</th>
				<td>
					<input id="hzNumberId8" name="hzNumberId8" value="" style="display: none;" />
					<input id="hzNumber8" name="hzNumber8" readonly="readonly" value="${fileMetaData.hzNumber8}" class="easyui-validatebox inp-txt w150" maxlength="20"/>
					<input type="button" style="width: 25px;height: 22px;" value="..." onclick="return openTags('hzNumber8');"/>
				</td>
			</tr>
		</table>
	</div>
	<br/>
	<div style="text-align:center">
		<input type="button" class="btn122" value="保存" onclick="validateAndSave('formmeta')"/>
	   	<input type="button" class="btn122" value="取消" onclick="closeWin()"/>
	</div>
	</form>
	<span id="spanOutput" class="spanTextDropdown" onmouseout="SetNoColor(this)" onfocus="return specialClickEvent();"></span>
</body>
</jsp>