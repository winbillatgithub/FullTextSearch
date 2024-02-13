//js字符串为空处理
function isUndefined(str){
	if(str){
		return str;
	}else{
		return "";
	}
}

//处理键盘事件 禁止后退键（Backspace）密码或单行、多行文本框除外
function banBackSpace(e){
    var ev = e || window.event;//获取event对象
    var obj = ev.target || ev.srcElement;//获取事件源
    var t = obj.type || obj.getAttribute('type');//获取事件源类型
    //获取作为判断条件的事件类型
    var vReadOnly = obj.readOnly;
    var vDisabled = obj.disabled;
    //处理undefined值情况
    vReadOnly = (vReadOnly == undefined) ? false : vReadOnly;
    vDisabled = (vDisabled == undefined) ? true : vDisabled;
    //当敲Backspace键时，事件源类型为密码或单行、多行文本的，
    //并且readOnly属性为true或disabled属性为true的，则退格键失效
    var flag1= ev.keyCode == 8 && (t=="password" || t=="text" || t=="textarea")&& (vReadOnly==true || vDisabled==true);
    //当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效
    var flag2= ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea" ;
    //判断
    if(flag2 || flag1)return false;
}
//禁止退格键 作用于Firefox、Opera
document.onkeypress=banBackSpace;
//禁止退格键 作用于IE、Chrome
document.onkeydown=banBackSpace;


//日期格式化
function dateFormatter(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}

//日期格式化
function timeFormatter(value){
	var datetime = new Date();
    datetime.setTime(value);
    var year=datetime.getFullYear();
    //月份重0开始，所以要加1，当小于10月时，为了显示2位的月份，所以补0
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
    var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
    var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
    return year + "-" + month + "-" + date+" " + hour + ":" + minute + ":" + second;
}

//打开新窗口
function openNewWin(url,w,h){
	var awidth=1050; //窗口宽度,需要设置
	if(w){
		awidth = w;
	}
	var aheight=500; //窗口高度,需要设置 
	if(h) {
		aheight = h;
	}
	var atop=(screen.availHeight - aheight)/2; //窗口顶部位置,一般不需要改
	var aleft=(screen.availWidth - awidth)/2; //窗口放中央,一般不需要改
	var param0="toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no"; //新窗口的参数
	//新窗口的左部位置，顶部位置，宽度，高度
	var params="top=" + atop + ",left=" + aleft + ",width=" + awidth + ",height=" + aheight + "," + param0 ;
	var win=window.open(url, 'newWin', params); //打开新窗口
	win.focus(); //新窗口获得焦点
}

//接收数据
function acceptData(list) {
	for(var i=0; i<list.length; i++) {
		if($("#"+list[i].split("=")[0])) {
			$("#"+list[i].split("=")[0]).val(list[i].split("=")[1]);
		}
	}
}

//load
function load() {
	$("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
	$("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 210) / 2,top:($(window).height() - 0) / 2});
}

//hidden load
function dispalyLoad() {
    $(".datagrid-mask").remove();
    $(".datagrid-mask-msg").remove();
}

//弹出框页面form提交方法
//url   提交到后台的url地址  如：${ctx}/rule/saveRuleReviewTips.do
//form  form表单的id
//obj 	当前按钮
function commonSave(url, form, obj) {
	if($("#"+form).form('validate')) {
		$.ajax({
            type: 'post', 
            cache: false, 
            dataType: 'json',
            url: url,
            data: $("#"+form).serializeArray(),
            success: function (data) {
            	//alert("success" + data.msg);
            	parent.parent.show(data.msg);//显示提示信息
				if (data.success) {
					//如果是点击右上角的修改个人信息，则刷新main
					if (data.msg == 1) {
	            		parent.window.location.href = ctx + "/admin/user/portalUser/main.do";
					} else {
						reloadData();//刷新datagrid
					}
					parent.parent.closeWin();//关闭window窗口
		        } else {
		        	dispalyLoad();
		        	if(obj) {
	            		obj.disabled=false;
	            	}
				}
            },
            error : function(data){   
            	//alert('message:' + data.responseText);
            	parent.parent.show(data.responseText);//显示失败提示信息
            	dispalyLoad();
            	if(obj) {
            		obj.disabled=false;
            	}
            },
            beforeSend: function () {
            	load();
            	if(obj) {
            		obj.disabled=true;
            	}
            }
        });
	}
}

//非弹出框页面form提交方法
//url   提交到后台的url地址  如：${ctx}/rule/saveRuleReviewTips.do
//form  form表单的id
//obj 	当前按钮
function commonSaveForm(url, form, obj) {
	if($("#"+form).form('validate')) {
		$.ajax({
			type: 'post', 
			cache: false, 
			dataType: 'json',
			url: url,
			data: $("#"+form).serializeArray(),
			success: function (data) {
          		parent.show(data.msg);//显示提示信息
          		dispalyLoad();
          		if(obj) {
          			obj.disabled=false;
          		}
			},
			error : function(data){   
				parent.show(data.responseText);//显示失败提示信息
				dispalyLoad();
				if(obj) {
					obj.disabled=false;
				}
			},
			beforeSend: function () {
				load();
				if(obj) {
					obj.disabled=true;
				}
			}
		});
	}
}

//打开单个窗口
function openWin(title, url, width, height) {
	parent.parent.dialog = "dataForm";
	
	parent.$("#load").show();//显示
	parent.$("#winSrc").attr("src", url);
	
	var iframe = parent.document.getElementById("winSrc");
	if(iframe.attachEvent){  //ie浏览器
	    iframe.attachEvent("onreadystatechange", function() {  
	        //有时候会比较怪异 readyState状态会跳过 complete 所以我们loaded状态也要判断  
	        if (iframe.readyState === "complete" || iframe.readyState == "loaded") {  
	        	parent.$("#load").hide();//隐藏  
	        }  
	    });  
	}else{  //其他浏览器（Firefox,Opera,chrome等 ）
	    iframe.addEventListener("load", function() {  
	    	parent.$("#load").hide();//隐藏   
	    }, false);  
	} 
	
	parent.$('#dataForm').dialog({
		modal : true,
		iconCls:'icon-save',//图标
		title:title,//标题
		width:width,//宽度
		height:height,//高度
		cache:false,
		onClose:function(){
			parent.$("#winSrc").attr("src", "");
			//直接刷新数据，不论是否有修改
			//$('#dataTable').datagrid('reload');
			
			var isReloadCurrentData = parent.document.getElementById("isReloadCurrentData");
			var isReloadVal = $(isReloadCurrentData).val();
			if(isReloadVal && isReloadVal ==="true"){
				$('#dataTable').datagrid('reload');
			}
			//刷新完成之后，再次把改值置为false
			$(isReloadCurrentData).val("false");
		}
	});
	
	parent.$('#dataForm').dialog("open");
}

//打开单个窗口
function openSecondPopupWin(title, url, width, height) {
	//parent.parent.dialog = "dataFormPopup";

	parent.$("#loadPopup").show();//显示
	parent.$("#winSrcPopup").attr("src", url);

	var iframe = parent.document.getElementById("winSrcPopup");
	if(iframe.attachEvent){  //ie浏览器
	    iframe.attachEvent("onreadystatechange", function() {  
	        //有时候会比较怪异 readyState状态会跳过 complete 所以我们loaded状态也要判断  
	        if (iframe.readyState === "complete" || iframe.readyState == "loaded") {  
	        	parent.$("#loadPopup").hide();//隐藏  
	        }  
	    });  
	}else{  //其他浏览器（Firefox,Opera,chrome等 ）
	    iframe.addEventListener("load", function() {  
	    	parent.$("#loadPopup").hide();//隐藏   
	    }, false);  
	} 

	parent.$('#dataFormPopup').dialog({
		modal : true,
		iconCls:'icon-save',//图标
		title:title,//标题
		width:width,//宽度
		height:height,//高度
		cache:false,
		onClose:function(){
			//alert('onClose');
			parent.$("#winSrcPopup").attr("src", "");
		}
	});
	
	parent.$('#dataFormPopup').dialog("open");
}

//创建页签
function creFrame(url) {
	return '<iframe scrolling="yes" frameborder="0" src="'+url+'" style="width:100%;height:100%;"></iframe>';
}

//打开多个个页签的窗口
function openTabsWin(title, urls, width, height, winTitles) {
	parent.parent.dialog = "dataFormTabs";
	
	if(!winTitles) {
		winTitles = new Array();
		winTitles.push("基本信息");
		winTitles.push("意见信息");
		winTitles.push("流程记录");
	}
	
	for(var i=0; i<urls.length; i++) {	
		parent.$('#tabs').tabs('add',{
			title:winTitles[i],
			content:creFrame(urls[i]),
			cache:false
		});
	}
	
	parent.$("#tabs").tabs("select", 0);
	
	parent.$('#dataFormTabs').dialog({
		modal : true,
		iconCls:'icon-save',//图标
		title:title,//标题
		width:width,//宽度
		height:height,//高度
		onClose:function(){
			for(var i=0; i<winTitles.length; i++) {	
				parent.$('#tabs').tabs('close', winTitles[i]);
			}
		}
	});
	
	parent.$('#dataFormTabs').dialog("open");
}

//刷新iframe
function refreshTab(obj,url) {
    var refresh_tab = obj;
    if (refresh_tab && refresh_tab.find('iframe').length > 0) {
    	var refresh_iframe = refresh_tab.find('iframe')[0];
        refresh_iframe.contentWindow.location.href = url;
    }
}

//关闭窗口
function closeWin() {
	//alert(parent.parent.dialog);
	if(parent.parent.dialog == "dataForm") {
		if(parent.$('#dataForm')) {
			parent.$('#dataForm').dialog("close");
		}
	}else if(parent.parent.dialog == "dataFormTabs") {
		if($("#dataFormTabs")) {
			$("#dataFormTabs").dialog("close");
		}
	}
}

//关闭弹出窗口上的弹出窗口
function closeWinPopup() {
	if(parent.$('#dataFormPopup')) {
		parent.$('#dataFormPopup').dialog("close");
	}
}

//刷新datagrid数据
function reloadData(id) {
	//刷新  datagrid
	var topWin = window.top.document.getElementById("center-content-tabs");
	if(id) {
		var win = topWin.document.getElementById(id).contentWindow;
		win.tab.datagrid('reload');//刷新datagrid
		win.tab.datagrid('clearSelections');//清理选中
	}else{
		//重新加载IFrame
		//var tabsHtml = parent.document.getElementById("center-content-tabs");
		//var currentIframe = $(tabsHtml).find('.tabs-panels > .panel:visible > .panel-body > iframe');
		//currentIframe.get(0).contentDocument.location.reload(true);
		
		var isReloadCurrentData = parent.document.getElementById("isReloadCurrentData");
		var test = $(isReloadCurrentData).val();
		$(isReloadCurrentData).val("true");

	}
	
}


//批量删除
function deleteIdsArray(rows, id) {
	return getIdsArray(rows, id);
}

//批量操作时，获得IDs字符串
function getIdsArray(rows, id) {
	var ids = new Array();
    for (var i = 0; i < rows.length; i++) {
    	ids.push('ids=' + eval('rows['+i+'].'+id));
    }
    return ids.join('&');
}

//批量操作
function barchIdsArray(rows, id) {
	var ids = new Array();
    for (var i = 0; i < rows.length; i++) {
    	ids.push(id + "s=" + eval('rows['+i+'].'+id));
    }
    return ids.join('&');
}

//单击事件
function onClickRow(index,row){
	$('#dataTable').datagrid('unselectAll');
	$('#dataTable').datagrid('selectRow',index);
}

//自动隐藏头部和查询框 
//$(document).ready(function(){
	//$('#easyui-layout').layout();
	//$("#easyui-layout").layout("collapse", "north");
//});

//操作提示
function alert(msg) {
	$.messager.alert('操作提示', msg, 'info');  
}

//错误提示
function error(msg) {
	$.messager.alert('错误提示', msg, 'error');
}

//确认提示
function confirm(msg, callback) {
    $.messager.confirm('确认提示', msg, function(r) {
        if(r) {
        	callback();
        }
    });            
}

//操作提示
function show(msg) {
	$.messager.show({title:'操作提示', msg:msg, showType:'show'});
}

/**
 * 根据字典类别编码+字典项key获取字典项value值
 * 用法：<div class="dictionary" id="bloodABOType_0"></div>
 * class="dictionary" 固定写法
 * id="bloodABOType_0" 类别编码_字典项key
 */
$(function(){
	var dictionarys = $(".dictionary");
	if(dictionarys) {
		for(var i=0; i<dictionarys.length; i++){
			var code = dictionarys[i].id.split("_")[0];
			var key = dictionarys[i].id.split("_")[1];
			var keyArr = key.split(",");
			var msg = "";
			for(var j=0; j<keyArr.length; j++){
				if(j>0){
					msg+="，";
				}
				msg+=formatterDictionary(code, keyArr[j]);
			}
			$(dictionarys[i]).html(isUndefined(msg));
		}
	}
});

/**
 * 获取数据字典项
 * @param categoryCode 数据字典类别编码
 */
function getDictionaryItems(categoryCode) {
	if(categoryCode) {
		for (var code in dictionary){
			if(dictionary[code].code == categoryCode) {
				return dictionary[code].dictionaryItems;
			}
		}
	}
}

/**
 * 获取数据字典项下拉框, 输入框不可编辑
 * @param ctx
 * @param id 页面下拉框id
 * @param categoryCode 数据字典类别编码
 */
function getDictionaryList(ctx, id, categoryCode) {
	if (id && categoryCode) {
		if ($('#'+id).val() != undefined) {
			$('#'+id).combobox({  
				data:getDictionaryItems(categoryCode),//从js文件中获取字典项数据
				valueField:'key',
			    textField:'value',
			    panelHeight:'auto',
			    editable:false,
			    onChange:function(record){
			    	$('#'+id).val(record);
			    }
			});
		} else {
			alert("input id= " +id+ " 未定义！");
		}
	} 
}
/**
 * 获取数据字典项下拉框
 * @param ctx
 * @param id 页面下拉框id
 * @param categoryCode 数据字典类别编码
 * @param editable 是否可编辑：boolean值
 * @param panelHeight 下拉高度
 * @param changeFunction 下拉change事件
 */
function getDictionaryListSetHeigth(ctx, id, categoryCode,editable,panelHeight,changeFunction) {
	if (id && categoryCode) {
		if ($('#'+id).val() != undefined) {
			if(!editable)editable=false;
			if(!panelHeight)panelHeight='auto';
			$('#'+id).combobox({  
				data:getDictionaryItems(categoryCode),//从js文件中获取字典项数据
				valueField:'key',
				textField:'value',
				panelHeight:panelHeight,
				editable:editable,
				onChange:function(record){
					$('#'+id).val(record);
					if(changeFunction)changeFunction();
				}
			});
		} else {
			alert("input id= " +id+ " 未定义！");
		}
	} 
}

/**
 * 获取数据字典项下拉框, 输入框不可编辑
 * @param ctx
 * @param id 页面下拉框id
 * @param categoryCode 数据字典类别编码
 */
function getDictionaryList2(ctx, id, categoryCode) {
	if (id && categoryCode) {
		if ($('#'+id).val() != undefined) {
			$('#'+id).combobox({  
				data:getDictionaryItems(categoryCode),//从js文件中获取字典项数据
				valueField:'key',
			    textField:'value',
			    panelHeight:'auto',
			    editable:false,
			    onChange:function(record){
			    	$('#'+id).val(record);
			    }
			});
		} else {
			alert("input id= " +id+ " 未定义！");
		}
	} 
}

/**
 * 获取省列表
 */
var provinceData = function() {
	return baseRegion;
};
/**
 * 获取市列表
 * @param regionCode 省编码
 */
var cityData = function(regionCode) {
	if(regionCode) {
		for (var code in baseRegion){
			if(baseRegion[code].regionCode == regionCode) {
				return baseRegion[code].citys;
			}
		}
	}else{
		return {};
	}
};
/**
 * 获取县列表
 * @param regionCode 市编码
 */
var divisionData = function(province, regionCode) {
	if(province && regionCode) {
		var citys = cityData(province);
		for (var code in citys){
			if(citys[code].regionCode == regionCode) {
				return citys[code].divisions;
			}
		}
	}else{
		return {};
	}
};

//省市
function region1(ctx){
	var city = $('#city').combobox({
        valueField:'regionCode',
        textField:'regionName',
        editable:false
    });
	
	$('#province').combobox({  
		data:provinceData(),
	    valueField:'regionCode',  
	    textField:'regionName',
	    editable:false,
	    onChange:function(value){
	    	city.combobox("clear").combobox('loadData',cityData(value));
		}
	});
}

//省市县
function region2(ctx, p, c, d){
	var mprovince = "province";
	var mcity = "city";
	var mdivision = "division";
	if(p) mprovince = p;
	if(c) mcity = c;
	if(d) mdivision = d;
	
	var division = $("#"+mdivision).combobox({
		valueField:'regionCode',
    	textField:'regionName',
    	editable:false
    });
	
	var city = $("#"+mcity).combobox({
        valueField:'regionCode',
        textField:'regionName',
        editable:false,
        onChange:function(value){
        	if($("[name='"+mprovince+"']").val()) {
        		division.combobox("clear").combobox('loadData',divisionData($("[name='"+mprovince+"']").val(), value));
        	}
        }
    });
	
	$("#"+mprovince).combobox({
    	data:provinceData(),
	    valueField:'regionCode',
	    textField:'regionName',
	    editable:false,
	    onChange:function(value){
	    	city.combobox("clear").combobox('loadData',cityData(value));
	  		division.combobox("clear").combobox('loadData',{});
	    },
		onLoadSuccess:function(){
			if($("[name='"+mprovince+"']").val()) {
				city.combobox('loadData',cityData($("[name='"+mprovince+"']").val()));
				if($("[name='"+mcity+"']").val()) {
					division.combobox('loadData',divisionData($("[name='"+mprovince+"']").val(), $("[name='"+mcity+"']").val()));
				}
			}
		}
	});
}

//根据regionCode，获得regionName
function getRegionName(regionCode) {
	if(regionCode) {
		for (var province in baseRegion){
			//省
			if(baseRegion[province].regionCode == regionCode) {
				return baseRegion[province].regionName;
			}
			//市
			var citys = baseRegion[province].citys;
			for (var city in citys){
				if(citys[city].regionCode == regionCode) {
					return citys[city].regionName;
				}
				//县
				var divisions = citys[city].divisions;
				for (var division in divisions){
					if(divisions[division].regionCode == regionCode) {
						return divisions[division].regionName;
					}
				}
			}
		}
	} else {
		return '';
	}
}

//返回当前 日期 时分秒，格式：yyyy-MM-dd HH:mm:ss
function getNowFormatDate() {
	var day = new Date();
	var Year = 0;
	var Month = 0;
	var Day = 0;
	var CurrentDate = "";
	//初始化时间
	//Year= day.getYear();//有火狐下2008年显示108的bug
	Year= day.getFullYear();//ie火狐下都可以
	Month= day.getMonth()+1;
	Day = day.getDate();
	Hour = day.getHours();
	Minute = day.getMinutes();
	Second = day.getSeconds();
	CurrentDate += Year + "-";
	CurrentDate += Month >= 10 ? Month + "-": "0" + Month + "-";
	CurrentDate += Day >= 10 ? Day + " ": "0" + Day + " ";
	CurrentDate += Hour >= 10 ? + Hour : "0" + Hour ;
	CurrentDate += Minute >= 10 ? ':' + Minute : ":0" + Minute ;
	CurrentDate += Second >= 10 ? ':' + Second : ":0" + Second ; 
	return CurrentDate;
} 

//设置iframe的高度为自适应
//参数obj：需设置高度为自适应的iframe
function SetWinHeight(obj) { 
	var win=obj; 
	if (document.getElementById){
		if (win && !window.opera){ 
			if (win.contentDocument && win.contentDocument.body.offsetHeight) 
			win.height = win.contentDocument.body.offsetHeight; 
			else if(win.Document && win.Document.body.scrollHeight) 
			win.height = win.Document.body.scrollHeight; 
		} 
	} 
}

//创建select的option项
/**
 * selectId   	： 下拉的id
 * code       	： 元数据的code
 * arrVal     	： 当前值
 * selDefault 	： 默认选中  (-1)默认选中最后一个   (-2)默认选中第一个
 */
function createDiceionOptions(selectId,code,arrVal,selDefault){
	var items = getDictionaryItems(code);
	var html = "";
	var bl = true;
	$.each(items, function(i, item){
		if($.trim(item.key)==$.trim(arrVal)){
			bl=false;
			html+='<option value="'+item.key+'" selected="selected">'+item.value+'</option>';
		}else{
			html+='<option value="'+item.key+'">'+item.value+'</option>';
		}
	});
	var obj = document.getElementById(selectId);
	$(obj).append(html);
	if(bl){
		if(selDefault==-1){//默认选中最后一个
			$(obj.options[obj.options.length-1]).attr("selected","selected"); 
		}else if(selDefault==-2){//默认选中第一个
			$(obj.options[0]).attr("selected","selected"); 
		}
	}
}

//是否是IE浏览器
var isMSIE = navigator.userAgent.indexOf("MSIE")>0;
//禁止滚动条
function windowBorScrollDisable(){
	if(isMSIE){
		document.getElementsByTagName('body')[0].style.overflow = 'hidden';
		document.getElementsByTagName('html')[0].style.overflow = 'hidden';
		//$(document.body).css("overflow","hidden");
		//$(document.body).attr("scroll","no");//针对IE
	}else{
		$(document.body).attr("style","overflow-x:hidden;overflow-y:hidden");
	}
}
//启用滚动条
function windowBorScrollEnable(){
	if(isMSIE){
		document.getElementsByTagName('html')[0].style.overflow = 'auto';
		document.getElementsByTagName('body')[0].style.overflow = 'auto';
		//$(document.body).css("overflow","auto");
	}else{
		$(document.body).attr("style","overflow-x:auto;overflow-y:auto");
	}
}

/* 智能输入框 开始*/
var arrOptions = new Array();
var theTextBox;
var currentValueSelected = -1;//表当前选中的某项
function setSelectedKey(event,obj){
	if(obj){ 
      	if (event.keyCode==38){
    	  	MoveHighlight(-1);
  			return false;
      	}else if (event.keyCode==40){
      		MoveHighlight(1);
    		return false;   
      	}else if (event.keyCode==13){
      		SetText(document.getElementById("spanOutput").title, h_id, t_id);
    		theTextBox.blur();
    		return false;
      	}
  	}
}

//建立下拉框  
function Bulid(code, h_id, t_id){
	if(arrOptions && h_id && t_id){
	    var inner = "";
		SetElementPosition(t_id);//设置下拉框出现的位置
	 	for(var i=0; i<arrOptions.length; i++){
	 		inner+="<span id=arr_"+i+" class='spanNormalElement' onmouseover='SetHighColor(this)' onclick='SetText(\""+code+"\",\""+h_id+"\",\""+t_id+"\")'>"+arrOptions[i].split("_")[1]+"</span>";
		}
	   	document.getElementById("spanOutput").innerHTML = inner;
	   	if(inner != "") {   
	   		document.getElementById("arr_0").className ="spanHighElement";//设置第一个顶为默认选中
	   		currentValueSelected=0;
	   	}else{
			HideTheBox(code);//隐藏下拉框
			currentValueSelected=-1;
	   	}
	}
}
//设置下拉框出现的位置
function SetElementPosition(id){
	var selectedPosX = 0;
	var selectedPosY = 0;
	var theElement = document.getElementById(id);

	if (!theElement) return;
	var theElemHeight = $('#'+id).outerHeight();
	var theElemWidth = $('#'+id).outerWidth();

	while(theElement != null){
		selectedPosX += theElement.offsetLeft;
		selectedPosY += theElement.offsetTop;
		theElement = theElement.offsetParent;
	}
	xPosElement = document.getElementById("spanOutput");
	xPosElement.style.left = selectedPosX + "px";
	xPosElement.style.width = theElemWidth + "px";
	xPosElement.style.top = selectedPosY + theElemHeight + "px";
	xPosElement.style.display = "block";
	xPosElement.style.overflow = "auto";
}
//设置上下移动键
function MoveHighlight(xDir){
	if(arrOptions && arrOptions.length > 0){
		var currnum=currentValueSelected+xDir;
		if(currnum >= 0 && currnum<arrOptions.length ){
			if(currentValueSelected == -1) {
				currentValueSelected = 0;
			}
	    	document.getElementById("arr_"+currentValueSelected).className = "spanNormalElement";
	       	document.getElementById("arr_"+currnum).className = "spanHighElement";
	       	currentValueSelected=currnum;
	  	}else if(currnum==arrOptions.length){
	   		document.getElementById("arr_"+currentValueSelected).className = "spanNormalElement";
	        currentValueSelected=0;
	        document.getElementById("arr_"+currentValueSelected).className = "spanHighElement";
		}else if(currnum==-1){
	   		document.getElementById("arr_"+currentValueSelected).className = "spanNormalElement";
	        currentValueSelected=arrOptions.length-1;
	        document.getElementById("arr_"+currentValueSelected).className = "spanHighElement";
		}
	}
}
//隐藏下拉框
function HideTheBox(code){
	if (code != document.getElementById("spanOutput").title) {
		return false;
	}
	document.getElementById("spanOutput").style.display = "none";
	document.getElementById("spanOutput").title = "";
	currentValueSelected = -1;
}

//选中下拉框中的某个值
function SetText(code, h_id, t_id){
	
	//alert(currentValueSelected);
	if (code != document.getElementById("spanOutput").title) {
		return false;
	}
	if(arrOptions.length == 0 &&currentValueSelected==-1){
		$('#'+h_id).val("");
		$('#'+t_id).val("");
	} else if(arrOptions.length > 0 && currentValueSelected >= 0) {
		$('#'+h_id).val(arrOptions[currentValueSelected].split("_")[0]);
		$('#'+t_id).val(arrOptions[currentValueSelected].split("_")[1]);
		HideTheBox(code);//隐藏下拉框
	}	/*if(currentValueSelected==-1){
		$('#'+h_id).val("");
		$('#'+t_id).val("");
	}*/
}
//当鼠标划过变为选中状态	
function SetHighColor(theTextBox){
	if(theTextBox){
		currentValueSelected = theTextBox.id.slice(theTextBox.id.indexOf("_")+1, theTextBox.id.length);
	}
	for(var i = 0; i < arrOptions.length; i++){
		document.getElementById('arr_' + i).className ='spanNormalElement';
	}
	document.getElementById('arr_'+currentValueSelected).className = 'spanHighElement';
}
//当鼠标移除变为选中状态	
function SetNoColor(theTextBox){
	currentValueSelected = -1;
	for(var i = 0; i < arrOptions.length; i++){
		document.getElementById('arr_' + i).className ='spanNormalElement';
	}
}
/* 智能输入框 结束*/

/* 页面下拉框js赋值 */
var myjson=[{
    "id":'0',
    "text":"否"
},{
    "id":'1',
    "text":"是"
}];
function setSelectComboBox(id,str){
	if(id) {
		if (str) {
			var arr = str.split(",");
			myjson = new Array();
			for (var i = 0; i < arr.length; i++) {
				var row = {};
				row.id = arr[i].split("-")[0];
				row.text = arr[i].split("-")[1];
				myjson.push(row);
			}
		}

		$('#'+id).combobox({
			data:myjson,
			editable : false,
			valueField : 'id',
			textField : 'text',
			panelHeight:'auto'
		});	
	}
}

function encodeChineseURI(uri) {
	return encodeURI(encodeURI(uri));
}