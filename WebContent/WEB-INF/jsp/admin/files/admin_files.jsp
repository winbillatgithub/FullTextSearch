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
			url : "${ctx}/admin/files/fileController/getUploadedFileInfo",
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
			toolbar : '#admin_file_toolbar',
			columns : [ [ {
				field : 'ck',
				checkbox : true,
				width : 80
			}, {
				field : 'id',
				title : 'id',
				width : 110
			}, {
				field : 'filename',
				title : '文件名',
				editor:{ filetype:'validatebox',options:{required:true} } ,
				width : 250
			}, {
				field : 'filesize',
				title : '文件大小',
				editor:{ filetype:'validatebox',options:{required:true} } ,
				width : 30
			}, {
				field : 'filetype',
				title : '类型',
				editor:{ filetype:'validatebox',options:{required:true} } ,
				width : 50
			}, {
				field : 'createdate',
				title : '上传时间',
				editor:{ createdate:'validatebox',options:{required:true} } ,
				width : 50
			}, {
				field:'edit',title:'操作',align:'center',formatter:operateIndexData
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
	function operateIndexData(value,row,index) {
		 return '<input type="button" class="btn50" id="'+row.id+'" value="更新索引" onclick="operateRowIndex(this)"/>';
	}
	function openDataIndex(id) {
//		alert("${ctx}");
		openWin("更新索引", "${ctx}/admin/files/uploadedFileInfoController/indexView?articleId=" + id, 1200, 300);
	}
	function operateRowIndex(obj) {
		var id = $(obj).attr("id");
		openDataIndex(id);
	}

	$("#reBton").click(function(){
		document.getElementById("queryForm").reset();
		$("#queryBton").click();		
	});
	
	function refresh() {
		return searchFiles();
	}

	function searchFiles() {
		tab.datagrid("load",{
			"id" : $("#id").val(),
			"fileName" : $("#filename").val(),
			"fileType" : $("#filetype").val()
		});
	};
	function endEditing() {
		return true;
	}
	function removeit() {
		var selectIndex = $('#dataTable').datagrid('getRowIndex',
				$("#dataTable").datagrid('getSelected'))

		if (selectIndex == -1) {
			alert("请选择要删除的数据");
			return false;
		}
		
		var id = $('#dataTable').datagrid('getRows')[selectIndex]['id'];

		confirm('会将法律文书库中的索引信息一并删除，确认要删除吗?',
			function() {
				$('#dataTable').datagrid('cancelEdit', selectIndex).datagrid('deleteRow', selectIndex);
				checkAndSave();
				deleteIndexFile(id);
			}
		);
	}

	function checkAndSave() {
		//endEditing();
		return saveAllData('${ctx}/admin/files/uploadedFileInfoController/saveData');
	}
	function deleteIndexFile(id) {
		
		var url = '${ctx}/admin/files/uploadedFileInfoController/deleteIndexFile';
		var ret = false;
		$.ajax({
	        type: 'post', 
	        cache: false, 
	        dataType: 'json',
	        async: false,
	        url: url,
	        data: {'id':id},
	        success: function (data) {
				if (data.success) {
					ret = true;
				}
				alert(data.msg);
	        },
	        error : function(data){
	        	//alert('message:' + data.responseText);
	        },
	        beforeSend: function () {
	        }
	    });
		return ret;
	}

</script>
</head>

<body class="easyui-layout" id="easyui-layout">
	<div region="north" border="false" split="true" title="查询条件" iconCls="icon-search" style="height: 90px;padding-top: 10px;">
		<form action="" id="queryForm">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td>文件编号：<input name="id" id="id" style="width: 150px;"/></td>
                    <td>文件名称：<input name="filename" id="filename" style="width: 150px;"/></td>
                    <td>文件类型：<input name="filetype" id="filetype" style="width: 150px;"/></td>
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
		<input type="button" id="remove" name="remove" class="btn122" value="删除" onclick="removeit()" />
	</div>

</body>
</html>