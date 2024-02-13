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
	//var dataList = '[{"id":0,"name":"分组标签"},{"id":1,"name":"环助码"}]';
	var dataList = [ {
		"id" : '0',
		"name" : "分组标签"
	}, {
		"id" : '1',
		"name" : "环助码"
	} ];

	$(function() {
		var readonly = $("#readonly").val();
		if (readonly == 'true') {
			$("#append").hide();
			$("#remove").hide();
			$("#save").hide();
		} else {
			$("#saveData").hide();
			$("#close").hide();
		}
		//绑定数据
		tab = $('#dataTable').datagrid({
			url : "${ctx}/admin/files/labelEntityController/getLabelInfo",
            queryParams : {
    			"type" : '1'
			},
			method : 'POST',
			loadMsg : '数据加载中......',
			fit : true, //自动补全  
			striped : true, //奇偶行颜色不同  
			pagination : true,//显示分页  
			fitColumns : true,//自动调整各列  
			rownumbers : true,//是否显示行号  
			singleSelect : true,
			pageSize : 15,//每页显示的记录条数，默认为15
			pageList : [ 5, 10, 15, 20, 50 ],//可以设置每页记录条数的列表 
			toolbar : '#admin_label_toolbar',
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
				field : 'type',
				title : '分类(分组标签/环助码)',
				hidden : true
			}, {
				field : 'name',
				title : '标签',
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
				if (readonly != 'true') {
					beginEditing(rowIndex, field, value);
				}
			}
		});
		//类型
		$("#typeName").combobox({
			data : dataList,
			editable : false,
			valueField : 'id',
			textField : 'name',
			panelHeight : 'auto',
			onLoadSuccess : function() {
				$('#typeName').combobox('select', '1');
			}
		});
	});
	//格式化地区嗲吗
	function typeFormatter(value, rowData, rowIndex) {
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

		var type = '1';
		var name = '';
		var isActive = '1';

		$('#dataTable').datagrid('appendRow', {
			'type' : type,
			'name' : name,
			'isActive' : isActive
		});
		editIndex = $('#dataTable').datagrid('getRows').length - 1;
		$('#dataTable').datagrid('selectRow', editIndex).datagrid('beginEdit',
				editIndex);
	}

	function checkAndSave() {
		endEditing();
		return saveAllData('${ctx}/admin/files/labelEntityController/saveData');
	}

	function refresh() {
		return searchFiles();
	};

	function searchFiles() {
		//alert('onclicked');
		tab.datagrid("load", {
			"type" : $('#typeName').combobox('getValue'),
			"name" : $("#name").val()
		});
	};
	// Compatible with IE/Chrome
	function getFrame(_id) {
		var Oframe = parent.document.getElementById(_id).contentDocument || parent.document.frames[_id].document;
		return Oframe;
	}
	function saveData() {
		var row = $("#dataTable").datagrid('getSelected');
		if (row == null || row == '') {
			alert('没有选中行!');
			return false;
		}
		var frame = getFrame('winSrc');
		var label = $("#label").val();
		frame.getElementById(label).value = row.name;
		closeWinPopup();
	}

</script>
</head>

<body class="easyui-layout" id="easyui-layout">
	<div region="north" border="false" split="true" title="查询条件" iconCls="icon-search" style="height: 90px;padding-top: 10px;">
		<input id="hidden_arealist" name="hidden_arealist" type="hidden" />
		<input id="readonly" name="readonly" type="hidden" value="${readonly}" />
		<input id="label" name="label" type="hidden" value="${label}" />
		<form action="" id="queryForm">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr style='display:none'>
					<td>分类：<input name="typeName" id="typeName" style="width: 120px;" /></td>
				</tr>
				<tr>
                    <td>
                    	环助码： <input name="name" id="name" style="width: 150px;"/>
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
	<div region="south" border="false" split="false" style="padding: 1px; align: center">
		<input type="button" id="append" name="append" class="btn122" value="添加" onclick="append()" />
		<input type="button" id="remove" name="remove" class="btn122" value="删除" onclick="removeit()" />
		<input type="button" id="save" name="save" class="btn122" value="保存" onclick="return checkAndSave();" />
		<input type="button" id="saveData" name="saveData" class="btn122"  value="确定" onclick="saveData();"/>
	   	<input type="button" id="close" name="close" class="btn122"  value="取消" onclick="closeWinPopup();"/>
	</div>
</body>
</html>