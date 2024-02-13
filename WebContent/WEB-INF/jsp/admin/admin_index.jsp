<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="common/admin_taglib.jsp"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>后台管理</title>
<link rel="stylesheet" href="${ctx}/css/admin/index/style-admin-index.css"
	type="text/css" />
</head>
<body class="easyui-layout" id="easyui-layout">
	<div class="easyui-layout" style="width: 100%; height: 100%;">
		<div data-options="region:'north'" style="height: 102px;">
			<!-- header -->
			<jsp:include page="common/admin_header.jsp"></jsp:include>
		</div>
		<div data-options="region:'west',split:true" title="导航菜单"
			style="width: 200px;">
			<ul id="left-menu-ul">
			</ul>
		</div>
		<div data-options="region:'center',title:'主页',iconCls:'icon-ok'">
			<div id="center-content-tabs"></div>
		</div>
	</div>
	<!-- 弹出窗口 -->
	<div id="dataForm" style="width:100%; height:100%;overflow:hidden;font-size:10px;" title="功能编辑">
		<div id="load" style="display:none;position:absolute;top:50%;left:50%;margin:-40px 0 0 -50px;width:100px;height:100px;border:0px solid #008800;">
			<img id="loadImg" src="${ctx }/images/load.gif"/>
		</div>
		<iframe id="winSrc" frameborder="0" style="width:100%; height:100%; overflow:hidden;"></iframe>
	</div>
	<!-- 弹出窗口上的弹出窗口 -->
	<div id="dataFormPopup" style="overflow:hidden;font-size:10px;" title="功能编辑">
		<div id="loadPopup" style="display:none;position:absolute;top:50%;left:50%;margin:-40px 0 0 -50px;width:100px;height:100px;border:0px solid #008800;">
			<img id="loadImg" src="${ctx }/images/load.gif"/>
		</div>
		<iframe id="winSrcPopup" frameborder="0" width="100%" height="100%"></iframe>
	</div>
	<input id="popupReturnValue" type="hidden" value="" />
	<!-- 弹出窗口关闭后是否重新加载数据的标识 -->
	<input id="isReloadCurrentData" type="hidden" value="false" />
	<script type="text/javascript">
		$(document).ready(function() {
			initLeftMenuContent();
			initCenterContent();
		});

		//自动调节tabs的高度和宽度
		$(window).resize(function() {
			$('#center-content-tabs').tabs({
				width : $("#center-content-tabs").parent().width(),
				height : $("#center-content-tabs").parent().height()
			});
		});

		function addCollapseListener() {
			//左侧菜单栏的隐藏按钮
			$(".layout-button-left").click(function() {
				$('#center-content-tabs').tabs({
					width : $("#center-content-tabs").parent().width()
				});
			});
		}

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
							requestUrl : row.requestUrl
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
								requestUrl : row.requestUrl
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
		function initLeftMenuContent() {
			$("#left-menu-ul")
					.tree(
							{
								url : "${ctx}/admin/adminIndex/queryMenu",
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
									var centerTabs = $("#center-content-tabs")
											.tabs("tabs");
									var centerTabsLenght = centerTabs.length;
									if ($("left-menu-ul").tree("isLeaf",node.target)) {
										var loadItemUrl = node.attributes.requestUrl;
										addTab(currentClickItemText, "${ctx}"
												+ loadItemUrl);
								}
								}
							});
		}
		
		//检验是否已经打开，如果已经打开，则直接显示，否则加载
		function addTab(currentClickItemText, loadItemUrl) {
			if ($('#center-content-tabs').tabs('exists', currentClickItemText)) {
				$('#center-content-tabs').tabs('select', currentClickItemText);
			} else {
				var content = '<iframe scrolling="auto" frameborder="0"  src="'
						+ loadItemUrl
						+ '" style="width:100%;height:100%;"></iframe>';

				$("#center-content-tabs").tabs('add', {
					title : currentClickItemText,
					closable : true,
					cache : true,
					content : content
				});
			}
		}

		//初始化中间的主容器
		function initCenterContent() {
			$("#center-content-tabs").tabs({
				width : $("#center-content-tabs").parent().width(),
				height : $("#center-content-tabs").parent().height()
			});
		}
	</script>
</body>
</html>
