<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/admin_taglib.jsp"%>

<html>
<head>
<meta http-equiv="Content-filetype" content="text/html; charset=UTF-8" />
<title></title>

<link href="${ctx}/css/admin/formtyle.css" type="text/css" rel="stylesheet" />
<%-- <link href="${ctx}/css/files/bootstrap.css" type="text/css" rel="stylesheet" /> --%>

<script type="text/javascript">
	var tab;
	var editcount = 0; //当前正在编辑的数据条数
	$(function() {
		//初始化地区代码
		$.ajax({
			type : 'POST',
			async : false,
			url : ctx
					+ '/admin/files/areaEntityController/queryAreaCode',
			dataType : 'json',
			success : function(e) {
				dataList = e;
				document.getElementById("hidden_arealist").value = dataList;
			}
		});
		//绑定数据
		tab = $('#dataTable').datagrid({
			url : "${ctx}/admin/files/courtEntityController/getCourtInfo",
			method : 'POST',
			loadMsg : '数据加载中......',
			fit : true, //自动补全  
			striped : true, //奇偶行颜色不同  
			pagination : true,//显示分页  
			fitColumns : true,//自动调整各列  
			rownumbers : true,//是否显示行号  
			pageSize : 15,//每页显示的记录条数，默认为15
			pageList : [ 5, 10, 15, 20, 50 ],//可以设置每页记录条数的列表 
			toolbar : '#admin_court_toolbar',
			columns : [ [ {
				field : 'ck',
				checkbox : true,
				width : 80
			}, {
				field : 'id',
				title : 'ID',
				editor:{ filetype:'validatebox',options:{required:true} } ,
				width : 100,
				hidden : true
			}, {
				field : 'areaId',
				title : '地区名称',
				align : 'left',
				editor : {
					type : 'combobox',
					options : {
						data : eval(dataList),
						valueField : "id",
						textField : "name",
						required:true,
						editable : false
					}
				},
				formatter : areaNameFormatter,
				width : 100
			}, {
				field : 'name',
				title : '法院简称',
				editor : {
					type : 'text',
					options : {
						required:true,
						validType : 'length[0, 200]'
					}
				},
				align : 'left',
				width : 100
			}, {
				field : 'fullName',
				title : '法院全称',
				editor : {
					type : 'text',
					options : {
						required:true,
						validType : 'length[0, 200]'
					}
				},
				align : 'left',
				width : 100
			}, {
				field : 'isActive',
				title : '启用标志',
				editor : {
					type : 'text',
					options : {
						required:true,
						validType : 'length[0, 1]'
					}
				},
				width : 100
			}, {
				field : 'createTime',
				title : '上传时间',
				editor:{ createdate:'validatebox',options:{required:true} } ,
				width : 100,
				hidden : true
			} ] ],
			onLoadSuccess : function() {
				tab.datagrid('clearSelections');
				editIndex = undefined;
			},
			onLoadError : function(data) {
				if (data.responseText) {
					parent.show(data.responseText);
				}
			},
			onBeforeEdit:function(index,row){
                row.editing = true;
                tab.datagrid('refreshRow', index);
                editcount++;
            },
            onAfterEdit:function(index,row){
                row.editing = false;
                tab.datagrid('refreshRow', index);
                editcount--;
            },
            onCancelEdit:function(index,row){
                row.editing = false;
                tab.datagrid('refreshRow', index);
                editcount--;
            },
			onClickCell : function(rowIndex, field, value) {
				beginEditing(rowIndex, field, value);
			}
		});
	});
	//格式化地区嗲吗
	function areaNameFormatter(value, rowData, rowIndex) {
/* 		if (value == 0) {
			return;
		} */
		var dataListJson = eval(dataList);
		for (var i = 0; i < dataListJson.length; i++) {
			if (dataListJson[i].id == value) {
				return dataListJson[i].name;
			}
		}
	}
	var editIndex = undefined;
	var editCol = undefined;
	function endEditing() {
		if (editIndex == undefined) {
			return true
		}
		if ($('#dataTable').datagrid('validateRow', editIndex)) {
			$('#dataTable').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}

	var beginEditing = function(rowIndex, field, value) {
		if (editIndex != rowIndex) {
			if (endEditing()) {
				$('#dataTable').datagrid('selectRow', rowIndex).datagrid('beginEdit', rowIndex);
				var currentEditor = $('#dataTable').datagrid('getEditor', { index: rowIndex, field: field });
				if(currentEditor != null && $(currentEditor.target)){
					$(currentEditor.target).select();
				}
				editIndex = rowIndex;
			} else {
				$('#dataTable').datagrid('selectRow', editIndex);
			}
		}
	}
	function removeit() {
		var selectIndex = $('#dataTable').datagrid('getRowIndex',
				$("#dataTable").datagrid('getSelected'))

		if (selectIndex == -1) {
			alert("请选择要删除的数据");
			return false;
		}
		
		confirm('确认要删除吗?',
			function() {
				$('#dataTable').datagrid('cancelEdit', selectIndex).datagrid(
						'deleteRow', selectIndex);
				editIndex = undefined;
				checkAndSave();
			}
		);
	}
	// 弹出窗口关闭时的回调函数
	function append() {

		var areaId = '';
		var name = '';
		var fullName = '';
		var isActive = '1';

		$('#dataTable').datagrid('appendRow', {
			'areaId' : areaId,
			'name' : name,
			'fullName' : fullName,
			'isActive' : isActive
		});
		editIndex = $('#dataTable').datagrid('getRows').length - 1;
		$('#dataTable').datagrid('selectRow', editIndex).datagrid('beginEdit',
				editIndex);
	}

	function checkAndSave() {
		endEditing();
		return saveAllData('${ctx}/admin/files/courtEntityController/saveData');
	}

	function searchFiles() {
		//alert('onclicked');
		tab.datagrid("load",{
			"name" : $("#name").val()
		});
	};

</script>
</head>

<body class="easyui-layout" id="easyui-layout">
	<div region="north" border="false" split="true" title="查询条件" iconCls="icon-search" style="height: 90px;padding-top: 10px;">
		<input id="hidden_arealist" name="hidden_arealist" type="hidden" />
		<form action="" id="queryForm">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td>
                    	法院名称：
                    </td>
                    <td>
                        <input name="name" id="name" style="width: 150px;"/>
                    </td>
                    <td>
						<input id="queryBton" type="button" class="btn122" value="确定" onclick="searchFiles();"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>

	<div region="center" border="false" split="false" style="padding: 1px;">
		<table id="dataTable"></table>
	</div>
	<div region="south" border="false" split="false"
		style="padding: 1px; align: center">
		<input type="button" class="btn122" value="添加" onclick="append()" />
		<input type="button" class="btn122" value="删除" onclick="removeit()" />
		<input type="button" class="btn122" value="保存" onclick="return checkAndSave();" />
	</div>
</body>
</html>