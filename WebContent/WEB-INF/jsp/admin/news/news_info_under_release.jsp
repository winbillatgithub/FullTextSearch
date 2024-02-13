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
									+ "/admin/news/manageNewsInfo/query_news_info_under_release", //数据来源 
							striped : true, //奇偶行颜色不同  
							idField : "newsId",//主键字段
							method : "post", //提交方式
							singleSelect : false, //是否单选  
							pagination : true, //是否分页  
							rownumbers : true, //是否显示行号  
							fitColumns : true, //自动调整各列  
							fit : true, //自适应宽度
							sortName : 'newsId', //排序名称
							sortOrder : 'asc', //排序顺序
							pageSize : 10,//分页信息
							pageList : [ 5, 10, 20, 50, 100 ],//每页显示条目下拉菜单
							onClickRow : onClickRow,//单击事件
							columns : [ [
									{
										checkbox : true
									},
									{
										field : 'newsId',
										title : 'ID',
										width : 50,
										sortable : true
									},
									{
										field : 'columnName',
										title : '新闻栏目',
										width : 120,
										sortable : true
									},
									{
										field : 'title',
										title : '新闻标题',
										width : 120,
										sortable : true
									},
									{
										field : 'source',
										title : '新闻来源',
										width : 120,
										sortable : true
									},
									{
										field : 'status',
										title : '状态',
										width : 120,
										sortable : true,
										formatter : function(val, rec) {
											if (val == 1) {
												return '草稿';
											} else if (val == 2) {
												return '待发布';
											} else if (val == 3) {
												return '已发布';
											}
										}
									},
									{
										field : 'createTime',
										title : '创建时间',
										width : 120,
										sortable : true
									},
									{
										field : 'releaseTime',
										title : '发布时间',
										width : 120,
										sortable : true
									},
									{
										field : 'remakes',
										title : '备注',
										width : 120,
										sortable : true
									},
									{
										field : 'action',
										title : '操作',
										width : 150,
										formatter : function(value, row, index) {

											return "<a style='text-decoration:underline;cursor:pointer;' onclick='release_u("
													+ row.newsId
													+ ");'>发布</a>||"
													+ "<a style='text-decoration:underline;cursor:pointer;' onclick='preview_u("
													+ row.newsId
													+ ");'>查看</a>||"
													+ "<a style='text-decoration:underline;cursor:pointer;' onclick='jeject_u("
													+ row.newsId
													+ ");'>驳回到草稿</a>";

										}
									}

							] ],
							
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

		//状态combobox
		var statusjson = [ {
			"id" : '1',
			"text" : "是"
		}, {
			"id" : '2',
			"text" : "否"
		} ];
		$("#status_u").combobox({
			data : statusjson,
			editable : false,
			valueField : 'id',
			textField : 'text',
			panelHeight : 'auto'
		});

		//公告栏目
		$('#columnId')
				.combobox(
						{
							url : ctx
									+ '/admin/news/manageNewsColumn/getAllDistinctNewsColumns',
							editable : false,
							valueField : 'columnId',
							textField : 'columnName'
						});

		$("#queryBton").click(function() {
			tab.datagrid("load", {
				"createTime_start" : $("input[name=c_start]").val(),
				"createTime_end" : $("input[name=c_end]").val(),
				"releaseTime_start" : $("input[name=r_start]").val(),
				"releaseTime_end" : $("input[name=r_end]").val(),
				"columnId" : $("#columnId").combobox('getValue'),
				"infoName" : $("#infoName").val(),
				"infoTitle" : $("#infoTitle").val()
			});
		});

		$("#reBton").click(function() {
			document.getElementById("queryForm").reset();
			//combobox必须如此赋值才能把值置为空
			$("#status_u").combobox('setValues', '');
			$("#columnId").combobox('setValues', '');
			$("#queryBton").click();
		});

	});
	
	function release_u(id) {
		$.messager.confirm('确认', '您是否要发布？', function(r) {
			if (r) {
				$.ajax({
					url : '${ctx}/admin/news/manageNewsInfo/releaseNews',
					method : 'POST',
					data : {
						newsId : id
					},
					dataType : 'json',
					success : function(data) {
						if (data.success) {
							$('#dataTable').datagrid('load');
						}
						$.messager.show({
							title : '提示',
							msg : data.msg
						});
					}
				});
			}

		});

	}
	
	//
	function preview_u(id) {
		openWin("新闻预览",
				"${ctx}/admin/news/manageNewsInfo/news_info_draft_preview?infoId="
						+ id, 800, 500);
	}

	
	function jeject_u(id) {
		$.messager.confirm('确认', '您是否要驳回到草稿？', function(r) {
			if (r) {
				$.ajax({
					url : '${ctx}/admin/news/manageNewsInfo/rejectToDrafts',
					method : 'POST',
					data : {
						newsId : id
					},
					dataType : 'json',
					success : function(data) {
						if (data.success) {
							$('#dataTable').datagrid('load');
						}
						$.messager.show({
							title : '提示',
							msg : data.msg
						});
					}
				});
			}

		});
	}

</script>
</head>
<body class="easyui-layout" id="easyui-layout">
	<div region="north" border="false" split="false"
		style="height: 100px; padding: 5px 0px 5px 5px">
		<form action="" id="queryForm">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<th>创建时间：</th>
					<td><input class="easyui-datebox" id="c_start" name="c_start"
						style="width: 90px" />- <input class="easyui-datebox" id="c_end"
						name="c_end" style="width: 90px" /></td>
					<th>发布时间：</th>
					<td><input class="easyui-datebox" id="r_start" name="r_start"
						style="width: 90px" />- <input class="easyui-datebox" id="r_end"
						name="r_end" style="width: 90px" /></td>

					<!-- <th>状态：</th>
					<td><input id="status_u" name="status_u"
						style="width: 100px; height: 25px;" /></td> -->
					<th>公告栏目：</th>
					<td><input id="columnId" name="columnId"
						style="width: 100px; height: 25px;" /></td>

				</tr>
				<tr>
					<th>新闻名称：</th>
					<td><input id="infoName" name="infoName"
						style="width: 190px; height: 25px;" /></td>
					<th>新闻标题：</th>
					<td><input id="infoTitle" name="infoTitle"
						style="width: 190px; height: 25px;" /></td>
					<td><a id="queryBton" class="easyui-linkbutton"
						data-options="iconCls:'icon-search'">搜索</a> <a id="reBton"
						class="easyui-linkbutton" data-options="iconCls:'icon-reload'">重置</a></td>
				</tr>
			</table>
		</form>
	</div>

	<div region="center" border="false" split="false">
		<table id="dataTable"></table>
	</div>
	<span id="spanOutput" class="spanTextDropdown" onmouseout="SetNoColor(this)"></span>
</body>
</html>