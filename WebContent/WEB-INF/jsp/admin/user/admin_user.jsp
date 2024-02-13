<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/admin_taglib.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<script type="text/javascript">
	var tab;

	// 用户管理--是否删除
	var formatterIsDelete = function(value, row, index) {
		if (value == 1) {
			return "否";
		} else if (value == 0) {
			return "是";
		} else {
			return "否";
		}
	};
	$(function() {
		//加载列表信息
		tab = $('#dataTable').datagrid(
		{
			//title:'数据列表', //标题  
			//iconCls:'icon-ok', //图标
			url : ctx + "/admin/user/manageUser/query_admin_user", //数据来源 
			striped : true, //奇偶行颜色不同  
			idField : "userId",//主键字段
			method : "post", //提交方式
			singleSelect : false, //是否单选  
			pagination : true, //是否分页  
			rownumbers : true, //是否显示行号  
			fitColumns : true, //自动调整各列  
			fit : true, //自适应宽度
			sortName : 'userId', //排序名称
			sortOrder : 'asc', //排序顺序
			pageSize : 15,//分页信息
			pageList : [ 5, 15, 20, 50, 100 ],//每页显示条目下拉菜单
			onClickRow : onClickRow,//单击事件
			//onDblClickRow: onDblClickRow,//双击事件

			columns : [ [ {
				checkbox : true
			}, {
				field : 'userId',
				title : 'ID',
				width : 50,
				sortable : true
			}, {
				field : 'username',
				title : '用户账号',
				width : 120,
				sortable : true
			}, {
				field : 'contactName',
				title : '用户姓名',
				width : 120,
				sortable : true
			}, {
				field : 'gender',
				title : '性别',
				width : 50,
				sortable : true,
				formatter : formatterSex
			}, {
				field : 'userType',
				title : '用户类型',
				width : 120,
				sortable : true,
				formatter : formatterUserType
			}, {
				field : 'email',
				title : '邮箱',
				width : 120,
				sortable : true
			}, {
				field : 'birthday',
				title : '生日',
				width : 120,
				sortable : true
			}, {
				field : 'phone',
				title : '电话',
				width : 180,
				sortable : true
			}, {
				field : 'isDisable',
				title : '是否禁用',
				width : 80,
				sortable : true,
				formatter : formatterIsDisable
			}, {
				field : 'isDelete',
				title : '是否删除',
				width : 80,
				sortable : true,
				formatter : formatterIsDelete
			}, {
				field : 'comment',
				title : '备注',
				width : 120,
				sortable : true
			}, {
				field:'edit',title:'操作',align:'center',formatter:operateRoleUser
			}, ] ],
			toolbar : [
					{
						text : '新增',
						iconCls : 'icon-add',
						handler : function() {
							openWin(
									"新增",
									ctx
											+ "/admin/user/manageUser/admin_user_detail",
									800, 500);
						}
					},
					/* '-',
					{
						text : '修改',
						iconCls : 'icon-edit',
						handler : function() {
							var row = $('#dataTable').datagrid(
									'getSelections');
							if (row.length == 0) {
								alert("请选择要修改的数据");
								return false;
							}
							if (row.length > 1) {
								alert("只能选择一条数据进行修改");
								return false;
							}
							var selectedUserId = row[0].userId;
							openWin(
									"修改",
									ctx
											+ "/admin/user/manageUser/admin_user_detail?userId="
											+ selectedUserId,
									800, 500);
						}
					}, */
					'-',
					{
						text : '删除',
						iconCls : 'icon-remove',
						handler : function() {
							var row = $('#dataTable').datagrid(
									'getSelections');
							if (row.length == 0) {
								alert("请选择要删除的数据");
								return false;
							} else {
								confirm(
										'您确认要删除吗?',
										function() {
											$
													.post(
															ctx
																	+ "/admin/user/manageUser/deleteUser",
															deleteIdsArray(
																	row,
																	'userId'),
															function(
																	data) {
																parent
																		.show(data.msg);
																if (data.success) {
																	tab
																			.datagrid("reload");
																	tab
																			.datagrid('clearSelections');
																}
															},
															"json");
										});
							}
						}
					} ],
			onLoadSuccess : function() {
				tab.datagrid('clearSelections');
				var arr = $("[class='datagrid-btable']").find(
						'[field="mobile"]');
				var arrStr = "";
				$.each(arr, function(i, item) {
					if (arrStr.length > 0)
						arrStr += ",";
					arrStr += $(item).find("div").html();
				});

				var addr = $("[class='datagrid-btable']").find(
						'[field="address"]');
				var addrStr = "";
				$.each(addr, function(i, item) {
					if (addrStr.length > 0)
						addrStr += ",";
					addrStr += $(item).find("div").html();
				});
			},
			onLoadError : function(data) {
				if (data.responseText) {
					parent.show(data.responseText);
				}
			}
		});
		$("#queryBton").click(function() {

			tab.datagrid("load", {
				"username" : $("#username").val(),
				"contactName" : $("#contactName").val(),
				"genderSelect" : $("#genderSelect").combobox('getValue'),
				"isDisable" : $("#isDisable").combobox('getValue')
			});
		});

		$("#reBton").click(function() {
			document.getElementById("queryForm").reset();
			$("#queryBton").click();
		});

		//性别查询
		var genderjson = [ {
			"id" : '0',
			"text" : "女"
		}, {
			"id" : '1',
			"text" : "男"
		} ];
		$("#genderSelect").combobox({
			data : genderjson,
			editable : false,
			valueField : 'id',
			textField : 'text',
			panelHeight : 'auto'
		});
		//是否禁用查询
		var isDisablejson = [ {
			"id" : '0',
			"text" : "否"
		}, {
			"id" : '1',
			"text" : "是"
		} ];
		$("#isDisable").combobox({
			data : isDisablejson,
			editable : false,
			valueField : 'id',
			textField : 'text',
			panelHeight : 'auto'
		});
	});
	function operateRoleUser(value,row,index) {
		 return '<input type="button" class="btn50" id="'+row.userId+'" value="重置密码" onclick="operateUser(this)"/>';
	}
	function operateUser(obj) {
		var userId = $(obj).attr("id");
		confirm('您确认要重置该用户的密码吗?',
		function() {
			$.ajax({
				type : 'POST',
				async : false,
				type : "post",
				url : ctx + '/admin/user/manageUser/resetPassword',
				data : {
					"id" : userId
				},
				dataType : "json",
				success : function(e) {
					if (e.success) {
						alert("修改成功");
						endEditing();
					} else {
						alert("修改失败");
						endEditing();
					}
				},
				error : function(e) {
					alert("系统异常，请联系管理员");
				}
			});
		});
	}
</script>
</head>
<body class="easyui-layout" id="easyui-layout">
	<div region="north" border="false" split="false"
		style="height: 40px; padding: 5px 0px 5px 5px">
		<form action="" id="queryForm">
			用户账号： <input id="username" class="input-h25" style="width: 80px; height: 25px;" /> 
			用户姓名： <input id="contactName" class="input-h25" style="width: 80px; height: 25px;" /> 
			性别: <input id="genderSelect" style="width: 80px; height: 25px;" /> 
			是否禁用： <input id="isDisable" style="width: 80px; height: 25px;" /> 
			<a id="queryBton" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
			<a id="reBton"   class="easyui-linkbutton" data-options="iconCls:'icon-reload'">重置</a>

		</form>
	</div>

	<div region="center" border="false" split="false">
		<table id="dataTable"></table>
	</div>
	<span id="spanOutput" class="spanTextDropdown" onmouseout="SetNoColor(this)"></span>
</body>
</html>