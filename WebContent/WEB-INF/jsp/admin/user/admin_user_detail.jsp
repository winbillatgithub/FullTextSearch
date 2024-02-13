<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/admin_taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户明细</title>
<script type="text/javascript">
	$(function() {

		//用户类型
		var userTypejson = [ {
			"id" : '0',
			"text" : "普通用户"
		}, {
			"id" : '1',
			"text" : "管理员"
		} ];
		$("#userType").combobox(
				{
					data : userTypejson,
					editable : false,
					valueField : 'id',
					textField : 'text',
					panelHeight : 'auto',
					onLoadSuccess : function() {
						$('#userType').combobox('select',
								$("#hiddenUserTypeValue").val());
					}
				});

		//性别查询
		var genderjson = [ {
			"id" : '0',
			"text" : "女"
		}, {
			"id" : '1',
			"text" : "男"
		} ];
		$("#gender").combobox(
				{
					data : genderjson,
					editable : false,
					valueField : 'id',
					textField : 'text',
					panelHeight : 'auto',
					onLoadSuccess : function() {
						$('#gender').combobox('select',
								$("#hiddenUserGenderValue").val());
					}
				});
		//是否禁用查询
		var isDisablejson = [ {
			"id" : '0',
			"text" : "否"
		}, {
			"id" : '1',
			"text" : "是"
		} ];
		$("#isDisable").combobox(
				{
					data : isDisablejson,
					editable : false,
					valueField : 'id',
					textField : 'text',
					panelHeight : 'auto',
					onLoadSuccess : function() {
						$('#isDisable').combobox('select',$("#hiddenIsDisableValue").val());
					}
				});
		
	
	});

	
</script>
</head>
<body class="easyui-layout">
	<form id="form" name="form" method="post">
		<div region="center" border="false" split="false"
			style="width: 100%; border: 1px solid #ddd; padding: 20px;">
			<input id="userId" name="userId" type="hidden"
				value="${portalUserEntity.userId}" />
			<table class="form-table" width="100%">
				<tr>
					<th class="form-table-hd" colspan="2"><div class="tit mr20">
							<strong>用户信息</strong>
						</div></th>
				</tr>
				<tr>
					<th>登陆名称</th>
					<td><input id="username" name="username" value="${portalUserEntity.username}"
						class="easyui-validatebox inp-txt" required="true" maxlength="30" /></td>
				</tr>
				<tr>
					<th>用户姓名</th>
					<td><input id="contactName" name="contactName" class="input-h25"
						value="${portalUserEntity.contactName} " required="true" /></td>
					<th>邮箱</th>
					<td><input id="email" name="email" value="${portalUserEntity.email}"
						class="inp-txt easyui-validatebox" validType="email" /></td>
				</tr>

				<tr>
					<th>用户类型</th>
					<td><input id="userType" name="userType"
						value="${portalUserEntity.userType} " class="easyui-combobox w230"
						style="height: 25px;" /> <input type="hidden" id="hiddenUserTypeValue"
						value="${portalUserEntity.userType}"/ ></td>
					<th>生日</th>
					<td><input id="birthday" name="birthday" missingMessage="日期必须填写"
						class="easyui-datebox w230" editable="false"
						value="${portalUserEntity.birthday}" required="true" />
					<input id="hiddeBirthdayValue" type="hidden" value="${portalUserEntity.birthday}"  /></td>
				</tr>
				<tr>
					<th>是否禁用</th>
					<td><input id="isDisable" name="isDisable" class="easyui-combobox w230" style="height: 25px;" /> 
					<input type="hidden" id="hiddenIsDisableValue" value="${portalUserEntity.isDisable}"/ ></td>
					<th>性别</th>
					<td><input id="gender" name="gender" class="easyui-combobox w230"
						style="height: 25px;" /> <input type="hidden" id="hiddenUserGenderValue"
						value="${portalUserEntity.gender}"/ ></td>
				</tr>
				<tr>
					<th>电&nbsp&nbsp&nbsp&nbsp话</th>
					<td><input id="phone" name="phone" class="easyui-validatebox inp-txt"
						validType="phone_mobile" maxlength="15" value="${portalUserEntity.phone} "
						required="true" /></td>
						<th>备注</th>
					<td><input id="comment" name="comment" class="input-h25"
						value="${portalUserEntity.comment} " required="true" /></td>
				</tr>
				<tr>
					<td colspan="4" align="center"><input type="button" class="btn122" value="保存"
						onclick="commonSave('${ctx}/admin/user/manageUser/saveUser', 'form', this)" />

						<input type="button" class="btn122" value="取消" onclick="closeWin()" /></td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>