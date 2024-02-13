<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/admin_taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新闻明细</title>
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
</script>

<script type="text/javascript">

	$(function() {
		$('#columnId')
				.combobox(
						{
							url : '${ctx}/admin/news/manageNewsColumn/getAllDistinctNewsColumns',
							editable : false,
							valueField : 'columnId',
							textField : 'columnName',
							onSelect : function(record) {
								//刷新数据，重新读品牌下的型号
								$('#hiddenColumnName').val(record.columnName);
							}, 
							onLoadSuccess : function() {
								$('#columnId').combobox('select',
										$("#hiddenColumnId").val());
							}
						});
	});

	function delete_img(id) {
		var img_div = document.getElementById("img_div" + id);
		img_div.parentNode.removeChild(img_div);
	}

	//重写当前窗口的鼠标点击事件，如果在别的地方点击就隐藏面板
	$(document).click(function() {
		var myDateSpanHide = $("#title").attr("myDateSpanHide");
		if (myDateSpanHide == "false")
			$("#title").val($.trim($("#title").val()));
		var mysource = $("#source").attr("mysource");
		if (mysource == "false")
			$("#source").val($.trim($("#source").val()));
	});
	$(parent.window.document).click(function() {
		var myDateSpanHide = $("#title").attr("myDateSpanHide");
		if (myDateSpanHide == "false")
			$("#title").val($.trim($("#title").val()));
		var mysource = $("#source").attr("mysource");
		if (mysource == "false")
			$("#source").val($.trim($("#source").val()));
	});
</script>
</head>
<body class="easyui-layout">
	<form id="form" name="form" method="post">
		<table align="center" width="100%" border="0" cellpadding="0" cellspacing="0">
			<input type="hidden" id="newsId" name="newsId" value="${newsInfoEntity.newsId}" />
			<tr>
				<th align="right" style="padding-top: 10px;">新闻栏目：</th>
				<td style="padding-top: 10px;">
				<select id="columnId" name="columnId" class="easyui-combobox" required="true" style="width: 150px; height: 30px;"></select>
				<input id="hiddenColumnId" type="hidden" value="${newsInfoEntity.columnId}"/> 
				<input id="hiddenColumnName" name="columnName" type="hidden" value="${newsInfoEntity.columnName}"/> 
				</td>
			</tr>
			
			<tr>
				<th align="right" style="padding-top: 10px;">新闻标题：</th>
				<td style="padding-top: 10px;"><input id="title" name="title"
					onmouseout='$(this).attr("myDateSpanHide",false);'
					onmouseover='$(this).attr("myDateSpanHide",true);'
					value="${newsInfoEntity.title}" class="easyui-validatebox"
					validType="CHS_lften" required="true" style="height: 150px; height: 25px;" /></td>
			</tr>
			<tr>
				<th align="right" style="padding-top: 10px;">新闻简介：</th>
				<td style="padding-top: 10px;"><textarea id="title" name="newsIntroduction"
					  class="easyui-validatebox" required="true" style="width: 600px; height: 50px;" >${newsInfoEntity.newsIntroduction}</textarea></td>
			</tr>
			<tr>
				<th align="right" style="padding-top: 10px;">新闻内容：</th>
				<td style="padding-top: 10px;"><textarea rows="5" cols="50"
						name="content" style="width: 670px; height: 200px">
				${newsInfoEntity.content}
				</textarea></td>
			</tr>
			<tr>
				<th align="right" style="padding-top: 10px;">来源：</th>
				<td style="padding-top: 10px;"><input id="source" name="source"
					maxlength="10" onmouseout='$(this).attr("mysource",false);'
					onmouseover='$(this).attr("mysource",true);'
					value="${newsInfoEntity.source}" class="easyui-validatebox"
					validType="CHS_lten" required="true" style="height: 150px; height: 25px;" /></td>
			</tr>
			<tr>
				<th align="right" style="padding-top: 10px;">备注：</th>
				<td style="padding-top: 10px;"><input id="remakes" name="remakes"
					maxlength="10" onmouseout='$(this).attr("mysource",false);'
					onmouseover='$(this).attr("mysource",true);'
					value="${newsInfoEntity.remakes}"
					validType="CHS_lten" style="height: 150px; height: 25px;" /></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="button" class="btn122"
					value="保存"
					onclick="commonSave('${ctx}/admin/news/manageNewsInfo/saveNewsInfo', 'form', this)" />
					<input type="reset" class="btn122"
					value="取消" onclick="parent.parent.closeWin()" /></td>
			</tr>
		</table>
	</form>
</body>
</html>