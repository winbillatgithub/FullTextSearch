//保存按钮,多条数据一起提交
function saveAllData(url) {
	if (endEditing()) {
		var rows = $("#dataTable").datagrid('getRows');
		var pDate = '';
		var pDateBox = parent.$("#pDate");
		if(null != pDateBox && pDateBox.size() >0){
			var pDate = parent.$("#pDate").datebox('getValue');
		}
		
		// DataGrid的更改行不为0
		if ($("#dataTable").datagrid('getChanges').length) {
			// 获取插入更改的行的集合
			var inserted = $("#dataTable").datagrid('getChanges', "inserted");
			// 获取删除更改的行的集合
			var deleted = $("#dataTable").datagrid('getChanges', "deleted");
			// 获取更新更改的行的集合
			var updated = $("#dataTable").datagrid('getChanges', "updated");

			var effectRow = new Object();
			if (inserted.length) {
				effectRow["inserted"] = JSON.stringify(inserted);
				//effectRow["inserted"] = inserted;
			}
			if (deleted.length) {
				effectRow["deleted"] = JSON.stringify(deleted);
				//effectRow["deleted"] = deleted;
			}
			if (updated.length) {
				effectRow["updated"] = JSON.stringify(updated);
				//effectRow["updated"] = updated;
			}

			$.ajax({
	            type: 'post', 
	            cache: false, 
	            dataType: 'json',
	            url: url,
	            data: {'inserted' : effectRow["inserted"],
	            	'updated' : effectRow["updated"],
	            	'deleted' : effectRow["deleted"],
	            	'pDate' : pDate},
	            success: function (data) {
	            	//alert("success" + data.msg);
	            	parent.show(data.msg);//显示提示信息
					if (data.success) {
						//如果是点击右上角的修改个人信息，则刷新main
						//reloadData();//刷新datagrid
						dispalyLoad();
						refresh();
			        } else {
			        	dispalyLoad();
					}
	            },
	            error : function(data){   
	            	//alert('message:' + data.responseText);
	            	parent.show(data.responseText);//显示失败提示信息
	            	dispalyLoad();
	            	refresh();
	            },
	            beforeSend: function () {
	            	load();
	            }
	        });
			effectRow = null;
		}
	}
}

//保存按钮,多条数据一起提交
function saveAllDataInSelf(url) {
	if (endEditing()) {
		var rows = $("#dataTable").datagrid('getRows');
		var pDate = '';
		var pDateBox = $("#pDate");
		if(null != pDateBox && pDateBox.size() >0){
		   pDate = $("#pDate").datebox('getValue');
		}
		
		// DataGrid的更改行不为0
		if ($("#dataTable").datagrid('getChanges').length) {
			// 获取插入更改的行的集合
			var inserted = $("#dataTable").datagrid('getChanges', "inserted");
			// 获取删除更改的行的集合
			var deleted = $("#dataTable").datagrid('getChanges', "deleted");
			// 获取更新更改的行的集合
			var updated = $("#dataTable").datagrid('getChanges', "updated");

			var effectRow = new Object();
			if (inserted.length) {
				effectRow["inserted"] = JSON.stringify(inserted);
			}
			if (deleted.length) {
				effectRow["deleted"] = JSON.stringify(deleted);
			}
			if (updated.length) {
				effectRow["updated"] = JSON.stringify(updated);
			}
		
			$.ajax({
	            type: 'post', 
	            cache: false, 
	            dataType: 'json',
	            url: url,
	            data: {'inserted' : effectRow["inserted"],
	            	'updated' : effectRow["updated"],
	            	'deleted' : effectRow["deleted"],
	            	'pDate' : pDate},
	            success: function (data) {
	            	//alert("success" + data.msg);
	            	show(data.msg);//显示提示信息
					if (data.success) {
						//如果是点击右上角的修改个人信息，则刷新main
						//reloadData();//刷新datagrid
						dispalyLoad();
						refresh();
			        } else {
			        	dispalyLoad();
					}
	            },
	            error : function(data){   
	            	//alert('message:' + data.responseText);
	            	show(data.responseText);//显示失败提示信息
	            	dispalyLoad();
	            	refresh();
	            },
	            beforeSend: function () {
	            	load();
	            }
	        });
			effectRow = null;
		}
	}
}

//导出Excel模板
function downloadExcel(requestUrl){
	window.open(requestUrl);
	/*$.ajax({
		 type: 'post', 
         cache: false, 
         url:requestUrl,
         success:function(e){
        	 if(e.success){
        		 alert("成功");
        	 }else{
        		 alert("下载excel模板错误,原因:"+e.msg); 
        	 }
        	 
         },
         error:function(e){
        	 alert("下载excel模板错误,原因:"+e.msg);
         }
	});*/
}

//导入Excel模板
function uploadExcel(requestUrl){
	
}



// 撤销按钮
function reject() {
	$('#dataTable').datagrid('rejectChanges');
	editIndex = undefined;
}

function scrollShow(datagrid) {  
    datagrid.prev(".datagrid-view2").children(".datagrid-body").html("<div style='width:" + datagrid.prev(".datagrid-view2").find(".datagrid-header-row").width() + "px;border:solid 0px;height:1px;'></div>");  
} 