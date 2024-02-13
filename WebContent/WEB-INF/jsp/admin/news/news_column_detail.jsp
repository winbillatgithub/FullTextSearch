<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/admin_taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新闻栏目明细</title>
<script type="text/javascript">
	$(function() {

		//栏目状态json
		var statusjson = [ {
			"id" : '0',
			"text" : "启用"
		}, {
			"id" : '1',
			"text" : "禁用"
		} ];
		$("#status").combobox(
				{
					data : statusjson,
					editable : false,
					valueField : 'id',
					textField : 'text',
					panelHeight : 'auto',
					onLoadSuccess : function() {
						$('#status').combobox('select',
								$("#hiddenStatusValue").val());
					}
				});

	
	});

	
</script>
</head>
<body class="easyui-layout">
	<form id="form" name="form" method="post">
		<div region="center" border="false" split="false"
			style="width: 100%; border: 1px solid #ddd; padding: 20px;">
			<input id="columnId" name="columnId" type="hidden" value="${newsColumnEntity.columnId}" /> 
			<table class="form-table" width="100%">
				<tr>
					<th class="form-table-hd" colspan="2"><div class="tit mr20">
							<strong>新闻栏目信息</strong>
						</div></th>
				</tr>
				<tr>
					<th>栏目名称</th>
					<td><input id="columnName" name="columnName" value="${newsColumnEntity.columnName}"
						class="easyui-validatebox inp-txt" required="true" maxlength="30" /></td>
					<th>栏目状态</th>
					<td>
						<input id="status" name="status" class="easyui-combobox w230" style="height: 25px;" /> 
						<input type="hidden" id="hiddenStatusValue" value="${newsColumnEntity.status}"/ >
					</td>
				 </td>
				</tr>
				
				<tr>
					<td colspan="4" align="center"><input type="button" class="btn122"
						value="保存"
						onclick="commonSave('${ctx}/admin/news/manageNewsColumn/saveNewsColumn', 'form', this)" />

						<input type="button" class="btn122"
						value="取消" onclick="closeWin()" /></td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>