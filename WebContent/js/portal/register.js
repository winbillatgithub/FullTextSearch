function setCookie() {
	if ($.cookie('FileSearch_Portal_Remember') != undefined) {
		$("#rememberMe").attr("checked", 'true');
		$("#username").val($.cookie('FileSearch_Portal_UserName'));
		$("#password").val($.cookie('FileSearch_Portal_Password'));
	}
};

function checkIsEmail(email) {
	var patten = new RegExp(/^[\w-]+(\.[\w-]+)*@([\w-]+\.)+(com|cn)$/);
	return patten.test(email);
}

function checkDuplicatedEmail(userNameVal, bAsync) {
	// Search from portal_user table
	var bRet = false;
	$.ajax({
		type : 'post',
		cache : false,
		async : bAsync,
		dataType : 'json',
		url : ctx + '/portal/login/register/checkUserName',
		data : {
			'userName' : userNameVal
		},
		success : function(data) {
			if (!data.success) {
				$("#userNameCheckTxt").html(
						$("#hiddenCheckUserNameFalseResult")
								.val());
				$("#username").focus();
			} else {
				bRet = true;
			}
		},
		error : function(data) {
			$("#userNameCheckTxt").html(data.msg);
		}
	});
	return bRet;
}

$(function() {
	// 回车登录
	$(document).keydown(function(e) {
		if (e.keyCode == 13) {
			doRegister();
		}
	});

	// 点击按钮注册
	$("#btnRegister").click(function() {
		doRegister();
	});

	$("#username").blur(
			function() {
				var userNameVal = $("#username").val();
				if (!userNameVal) { // 当用户名不存在，则清空任何用户名的提示信息
					$("#userNameCheckTxt").html(
							$("#hiddenCheckUserNameNotNull").val());
					$("#username").focus();
				} else if (checkIsEmail(userNameVal)) {// 用户名聚焦，存在，则去系统检验
					// Check duplicate
					$("#userNameCheckTxt").html("");
					if (checkDuplicatedEmail(userNameVal, true)) {
						$("#userNameCheckTxt").html("");
						//$("#userNameCheckTxt").html($("#hiddenCheckUserNameTrueResult") .val());
						$("#password1").focus();
					}
				} else {
					$("#userNameCheckTxt").html(
							$("#hiddenCheckUserNameWrongEmail").val());
					$("#username").focus();
				}
			});

	/*
	 * $("#password1").blur(function(){ if(!$("#password1").val()){//第一个密码不存在 }
	 * });
	 */

	$("#reedAgreement").change(function(e) {
		if($(this).prop("checked")){
			$("#agreementCheckTxt").html('');
		}else{
			$("#agreementCheckTxt").html($("#hiddenCheckBoxAgree").val());
		}
	});

	// 注册
	function doRegister() {
		if(!$("#reedAgreement").prop("checked")){
			$("#agreementCheckTxt").html($("#hiddenCheckBoxAgree").val());
			return false;
		}else{
			$("#agreementCheckTxt").html('');
		}
		
		var userNameVal = $("#username").val();
		if (!userNameVal) { // 当用户名不存在，则清空任何用户名的提示信息
			$("#userNameCheckTxt").html($("#hiddenCheckUserNameNotNull").val());
			$("#username").focus();
			return false;
		} else if (!checkIsEmail(userNameVal)) {
			$("#userNameCheckTxt").html(
					$("#hiddenCheckUserNameWrongEmail").val());
			$("#username").focus();
			return false;
		} else if (!checkDuplicatedEmail(userNameVal, false)) {
			$("#userNameCheckTxt").html(
					$("#hiddenCheckUserNameFalseResult").val());
			$("#username").focus();
			return false;
		}

		if (!$("#password1").val()) {
			$("#password1").focus();
			$("#password1CheckTxt").html(
					$("#hiddenCheckPassword1NotNull").val());
			return false;
		}

		if (!$("#password2").val()) {
			$("#password2").focus();
			$("#password2CheckTxt").html(
					$("#hiddenCheckPassword1NotNull").val());
			return false;
		} else if ($("#password1").val() != $("#password2").val()) {
			$("#password2").focus();
			$("#password2CheckTxt").html(
					$("#hiddenCheckPassword2NotTheSame").val());
			return false;
		}
		
		$.ajax({
			type : 'post',
			cache : false,
			dataType : 'json',
			url : ctx + '/portal/login/register/registerUser',
			data : {
				'userName' : userNameVal,
				'password':$("#password1").val()
			},
			success : function(data) {
				//alert(data.msg);
				$('#regSuccess').attr('style','display: block');
			},
			error : function(data) {
				alert(data.msg);
			}
		});
	}

});
