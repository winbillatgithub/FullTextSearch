<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/admin_taglib.jsp"%>

<html>
<head>
<meta http-equiv="Content-filetype" content="text/html; charset=UTF-8" />
<title></title>

<link href="${ctx}/css/admin/formtyle.css" type="text/css" rel="stylesheet" />

<script type="text/javascript">
	var tab;
	var editcount = 0; //当前正在编辑的数据条数
	$(function() {
		//绑定数据
		tab = $('#dataTable').datagrid({
			url : "${ctx}/admin/files/fileController/getClientInfo",
			method : 'POST',
			loadMsg : '数据加载中......',
			fit : true, //自动补全  
			striped : true, //奇偶行颜色不同  
			pagination : true,//显示分页  
			fitColumns : true,//自动调整各列  
			rownumbers : true,//是否显示行号  
			pageSize : 15,//每页显示的记录条数，默认为15
			pageList : [ 5, 10, 15, 20, 50 ],//可以设置每页记录条数的列表 
			toolbar : '#admin_client_toolbar',
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
				hidden : true
			}, {
				field : 'typeName',
				title : '当事人类型',
				editor:{ filetype:'validatebox',options:{required:true} } ,
				width : 100
			}, {
				field : 'name',
				title : '当事人名称',
				editor:{ filetype:'validatebox',options:{required:true} } ,
				width : 100
			}, {
				field : 'isActive',
				title : '启用标志',
				editor:{ filetype:'validatebox',options:{required:true} } ,
				width : 100
			}, {
				field : 'createTime',
				hidden : true
			} ] ],
			onLoadSuccess : function() {
				tab.datagrid('clearSelections');
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
            }
		});

	});

	$("#reBton").click(function(){
		document.getElementById("queryForm").reset();
		$("#queryBton").click();		
	});

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
		<form action="" id="queryForm">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td>
                    	当事人名称：
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

</body>
</html>