
function removePara(currentFullUrl, paramName) {
	var urlArray = currentFullUrl.split("?");
	var urlPre, urlParas, urlParaArray, newURL = "";
	if (urlArray.length >= 2) {
		urlPre = urlArray[0];
		newURL = urlPre + "?";
		urlParas = urlArray[1];
		urlParaArray = urlParas.split("&");
		var bAdded = false;
		for (var i = 0; i < urlParaArray.length; i++) {
			var paramPair = urlParaArray[i].split("=");
			if (paramPair[0] != paramName) {
				if (bAdded == false) {
					newURL = newURL + urlParaArray[i];
					bAdded = true;
				} else {
					newURL = newURL + "&" + urlParaArray[i];
				}
			}
		}
	}
	return newURL;
}
function addPara(currentFullUrl, type, val) {
	if (val == null || '' == val) {
		return currentFullUrl;
	}
	var urlArray = currentFullUrl.split("?");
	var urlPre, urlParas, urlParaArray, newURL = "";
	if (urlArray.length >= 2) {
		urlPre = urlArray[0];
		newURL = urlPre + "?";
		urlParas = urlArray[1];
		urlParaArray = urlParas.split("&");
		var bFound = false;
		for (var i = 0; i < urlParaArray.length; i++) {
			var paramPair = urlParaArray[i].split("=");
			if (paramPair[0] == type) {
				bFound = true;
				break;
			}
		}
		if (bFound == false) {
			currentFullUrl += ("&" + type + "=" + val);
		}
	} else {
		currentFullUrl += ("?" + type + "=" + val);
	}
	return currentFullUrl;
}

/* 智能输入框 开始*/
//以上设置一些全局变量
var specialClicked = false;
function specialClickEvent(){
    specialClicked = true;
    // 特殊事件的操作 
    return false;
};

function onblurEvent(code, h_id, t_id){
    // 适用所有浏览器，延迟100ms
	// 目前延迟方式存在bug，提示框会自动消失
    //setTimeout(function(){onblurEventDelay(code, h_id, t_id);}, 500);
	SetText(code, h_id, t_id);
	HideTheBox(code);
};

function onblurEventDelay(code, h_id, t_id){
    if (!specialClicked) {
        // 这里做真正的验证操作
		SetText(code, h_id, t_id);
		HideTheBox(code);
    } else {
    	$("#"+t_id).focus();
    	specialClicked = false;
    }
};

//调用函数
function getList(code, h_id, t_id){
  	var intKey = -1;
	if(window.event){
		intKey = event.keyCode;
		theTextBox = event.srcElement;//获得事件源
	}

	if(intKey == 13){//按回车键			
		SetText(code, h_id, t_id);
		theTextBox.blur();
		return false;
	}else if(intKey == 38){//按向上键	
		MoveHighlight(-1);
		return false;
	}else if(intKey == 40){	//按向下键	
		MoveHighlight(1);
		return false;
	}

	//dwr调用方法
	if ($("#"+t_id).val()) {
		$.ajax({
			type : 'post',
			cache : false,
			async : true,
			dataType : 'json',
			url : ctx + '/portal/searchfile/dictionary/selectByPartName',
			data : {
				'type' : code,
				'val' : $("#"+t_id).val()
			},
			success : function(data) {
				if(data != '') {
					arrOptions = data;
					Bulid(code, h_id, t_id);
					$('#' +t_id+'Error').html(null);
					document.getElementById("spanOutput").title = code;
				} else {
					$('#'+h_id).val('');
					$('#' +t_id+'Error').html($("#NoData").val());
					//$('#' +t_id).focus(); // This will cause infinite loop of calling getList in Chrome
					HideTheBox(code);
				}
			},
			error : function(data) {
				$('#' +t_id+'Error').html(("#ErrorMessage").val());
			}
		});
		/*
		userController.getDictionaryItems(code, $("#"+t_id).val(), function(data) {
			if(data != '') {	
				arrOptions = data;
				Bulid(code, h_id, t_id);
				$('#' +t_id+'Error').html(null);
			} else {
				$('#'+h_id).val('');
				$('#' +t_id+'Error').html("您所输入的数据不存在！");
				$('#' +t_id).focus();
				HideTheBox(code);
			}
		});*/
	}
	
	if (!theTextBox || !theTextBox.value || theTextBox.value.length == 0) {
		HideTheBox(code);
		return false;
	}
}
/* 智能输入框 结束*/