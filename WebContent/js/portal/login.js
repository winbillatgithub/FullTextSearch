function setCookie() {	
	if ($.cookie(ctx) != undefined) {
		$("#rememberMe").attr("checked",'true');
	}
	$("#username").val($.cookie('FileSearch_UserName'));
	$("#password").val($.cookie('FileSearch_Password'));
};

$(function() {

	// 回车登录
	$(document).keydown(function(e) {
		if (e.keyCode == 13) {
			dologin();
		}
	});

	// 点击按钮登录
	$("#btnLogin").click(function() {
		dologin();
	});

	/*
	 * function writeCookie(){
	 * if($.cookie('FileSearch_Portal_Remember')!=undefined){
	 * $("#username").val($.cookie('FileSearch_Portal_UserName'));
	 * $("#password").val($.cookie('FileSearch_Portal_Password')); } }
	 */

	function dologin() {
		if (!$("#username").val()) {
			$("#username").focus();
			$("#errorTxt").html("账号不能为空 !");
			return false;
		}
		if (!$("#password").val()) {
			$("#password").focus();
			$("#errorTxt").html("密码不能为空 !");
			return false;
		}

		$.ajax({
			type : 'post',
			cache : false,
			dataType : 'json',
			url : ctx + '/portal/login/login/loginAction',
			data : {
				'username' : $("#username").val(),
				'password' : $("#password").val(),
				"rememberMe" : $('#rememberMe').is(":checked")
			},
			success : function(data) {
				if (data.success) {
					/* writeCookie(); */
					var currentFullUrl = window.location.href;
					//alert(currentFullUrl);
					var fromArray = currentFullUrl.split("?from=");
					var fromUrl = "/";
					if(fromArray!=null && fromArray[1]!=null){
						fromUrl += fromArray[1];
						window.location.href = ctx+fromUrl;
					}else{
						window.location.href = ctx + "/index.html";
					}
				} else {
					$("#btnLogin").val($("#btnLoginCompleteText").val());
					$("#btnLogin").removeAttr("disabled");

					if (data.msg == "1") {
						$("#errorTxt").html("用户名不存在!");
						$("#username").focus();
						$("#username").val("");
						$("#password").val("");
					} else if (data.msg == "2") {
						$("#errorTxt").html("密码错误!");
						$("#password").focus();
						$("#password").val("");
					} else if (data.msg == "3") {
						$("#errorTxt").html("该用户已被禁用!");
						$("#username").focus();
						$("#username").val("");
						$("#password").val("");
					} else {
						$("#errorTxt").html(data.msg);
						$("#username").focus();
						$("#username").val("");
						$("#password").val("");
					}
				}
			},
			error : function(data) {
				$("#btnLogin").val($("#btnLoginCompleteText").val());
				$("#btnLogin").removeAttr("disabled");
			},
			beforeSend : function() {
				$("#errorTxt").html("");
				$("#btnLogin").val($("#btnLoginingText").val());
				$("#btnLogin").attr({
					"disabled" : "disabled"
				});
			}
		});
	}
});