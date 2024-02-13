<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="../common/admin_taglib.jsp"%>

<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>文件元数据</title>

<link href="${ctx}/css/admin/formtyle.css" type="text/css" rel="stylesheet" />

<style type="text/css">
.errorTxt {
	color: #f00;
	font-size: smaller;
}
</style>
<script type="text/javascript">

	$(document).ready(function() {
		initMenuContent();
	});

	//转化树的节点内容
	function convert(rows) {
		function exists(rows, parentId) {
			for (var i = 0; i < rows.length; i++) {
				if (rows[i].id == parentId)
					return true;
			}
			return false;
		}

		var nodes = [];
		// get the top level nodes
		for (var i = 0; i < rows.length; i++) {
			var row = rows[i];
			if (!exists(rows, row.parentId)) {
				nodes.push({
					id : row.id,
					text : row.name,
					attributes : {
						//requestUrl : row.requestUrl
					}
				});
			}
		}

		var toDo = [];
		for (var i = 0; i < nodes.length; i++) {
			toDo.push(nodes[i]);
		}
		while (toDo.length) {
			var node = toDo.shift(); // the parent node
			// get the children nodes
			for (var i = 0; i < rows.length; i++) {
				var row = rows[i];
				if (row.parentId == node.id) {
					var child = {
						id : row.id,
						text : row.name,
						attributes : {
							//requestUrl : row.requestUrl
						}
					};
					if (node.children) {
						node.children.push(child);
					} else {
						node.children = [ child ];
					}
					toDo.push(child);
				}
			}
		}
		return nodes;
	}
	//加载左侧的菜单树
	function initMenuContent() {
		$("#cause-menu-ul")
			.tree(
					{
						url : "${ctx}/admin/files/fileController/getCaseCause",
						method : 'post',
						lines : true,
						loadFilter : function(rows) {
							return convert(rows);
						},
						formatter : function(node) {
							var s = node.text;
							if (node.children) {
								s += '&nbsp;<span style=\'color:blue\'>('
										+ node.children.length
										+ ')</span>';
							}
							return s;
						},
						onClick : function(node) {
							//当前点击的项的名称
							var currentClickItemText = node.text;
							//判断当前节点是否打开
							parent.document.getElementById("popupReturnValue").value = currentClickItemText;
							//alert(parent.document.getElementById("popupReturnValue").value);
							if ($("cause-menu-ul").tree("isLeaf",node.target)) {
								//alert('isLeaf');
								//var loadItemUrl = node.attributes.requestUrl;
						}
					}
				});
	}
	// Compatible with IE/Chrome
	function getFrame(_id) {
		var Oframe = parent.document.getElementById(_id).contentDocument || parent.document.frames[_id].document;
		return Oframe;
	}
	function saveCause() {
		var cause = parent.document.getElementById("popupReturnValue").value;
		if (cause == null || cause == '') {
			alert('请选择案由!');
			return false;
		}
		var frame = getFrame('winSrc');
		alert(frame);
		frame.getElementById("cause").value = parent.document.getElementById("popupReturnValue").value;
		closeWinPopup();
	}
</script>

</head>
<body>
	<form id="formcause" name="formcause" method="post">
		<div style="width:465px;height:450px; overflow:hidden; border:1px solid #ddd; padding:5px;">
			<div title="案件类型" style="width:100%;height:100%;overflow-x:hidden;overflow-y:scroll" >
				<ul id="cause-menu-ul"></ul>
			</div>
		</div>
		<div align=center>
			<input type="button" class="btn122" value="确定" onclick="saveCause();"/>
			<input type="button" class="btn122" value="关闭" onclick="closeWinPopup();"/>
		</div>
	</form>
</body>
</html>