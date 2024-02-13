<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/admin_taglib.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<script type="text/javascript">
	var tab;
	$(function() {
		//加载列表信息
		tab = $('#dataTable')
				.datagrid(
						{
							url : ctx
									+ "/admin/news/manageNewsColumn/query_news_column", //数据来源 
							striped : true, //奇偶行颜色不同  
							idField : "columnId",//主键字段
							method : "post", //提交方式
							singleSelect : false, //是否单选  
							pagination : true, //是否分页  
							rownumbers : true, //是否显示行号  
							fitColumns : true, //自动调整各列  
							fit : true, //自适应宽度
							sortName : 'columnId', //排序名称
							sortOrder : 'asc', //排序顺序
							pageSize : 10,//分页信息
							pageList : [ 5, 10, 20, 50, 100 ],//每页显示条目下拉菜单
							onClickRow : onClickRow,//单击事件
							columns : [ [ {
								checkbox : true
							}, {
								field : 'columnId',
								title : 'ID',
								width : 50,
								sortable : true
							}, {
								field : 'columnName',
								title : '栏目名称',
								width : 120,
								sortable : true
							}, {
								field : 'status',
								title : '状态',
								width : 120,
								sortable : true,
								formatter : formatterNewsColumnStatus
							}, {
								field : 'createId',
								title : '创建人',
								width : 50,
								sortable : true
							}, {
								field : 'createTime',
								title : '创建时间',
								width : 120,
								sortable : true
							}, {
								field : 'modifyId',
								title : '修改人',
								width : 120,
								sortable : true
							}, {
								field : 'modifyTime',
								title : '修改时间',
								width : 120,
								sortable : true
							} ] ],
							toolbar : [
									{
										text : '新增',
										iconCls : 'icon-add',
										handler : function() {
											openWin(
													"新增",
													ctx
															+ "/admin/news/manageNewsColumn/news_column_detail",
													800, 500);
										}
									},
									'-',
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
											var selectedColumnId = row[0].columnId;
											openWin(
													"修改",
													ctx
															+ "/admin/news/manageNewsColumn/news_column_detail?columnId="
															+ selectedColumnId,
													800, 500);
										}
									},
									'-',
									{
										text : '删除',
										iconCls : 'icon-remove',
										handler : function() {
											var row = $('#dataTable').datagrid('getSelections');
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
																					+ "/admin/news/manageNewsColumn/deleteNewsColumn",
																			deleteIdsArray(
																					row,
																					'columnId'),
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
				"columnName" : $("#columnName").val(),
				"columnStatus" : $("#columnStatus").val()
			});
		});
		
		$("#reBton").click(function() {
			document.getElementById("queryForm").reset();
			$("#queryBton").click();
		});

	});
	

</script>
</head>
<body class="easyui-layout" id="easyui-layout">
	<div region="north" border="false" split="false"
		style="height: 40px; padding: 5px 0px 5px 5px">
		<form action="" id="queryForm">
			栏目名称： <input id="columnName" class="input-h25"
				style="width: 80px; height: 25px;" /> 状态： <input id="columnStatus"
				class="input-h25" style="width: 80px; height: 25px;" /> 
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