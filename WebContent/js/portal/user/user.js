
function fnckeystop(evt){
    if(! window.event){
        var    keycode=evt.keycode;
       var key=String.fromcharcode(keycode).toLowercase();
       if(evt.ctrlkey&&key=="v"){
                evt.preventDefault();
               evt.stopPropagation();
        }
    }
}

function checkUserName(userName) {
	var patten = new RegExp(/^(\w+)|([\u0391-\uFFE5]+)$/);
	return patten.test(userName);
}

function checkDuplicatedUserName(userId, userNameVal, bAsync) {
	// Search from portal_user table
	var bRet = false;
	$.ajax({
		type : 'post',
		cache : false,
		async : bAsync,
		dataType : 'json',
		url : ctx + '/portal/user/selfservice/checkUserName',
		data : {
			'userName' : userNameVal,
			'userId' : userId
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

function ValidatePwd(bAsync) {
	var bRet = false;
	if ($('#oldPassword').val() == null || $('#oldPassword').val() == '') {
		$("#oldPasswordCheckTxt").html($("#oldPasswordCanNotBeNull").val());
		$("#oldPassword").focus();
		return false;
	}

	$.ajax({
        type: 'post', 
        cache: false, 
		async : bAsync,
        dataType: 'json', 
        url : ctx + '/portal/user/selfservice/validatePwd',
        data: {
        	'userId'   : $("#userId").val(),
            'password' : $("#oldPassword").val()
        },
        success: function (data) {
        	if(!data.success) {
				$("#oldPasswordCheckTxt").html($("#oldPasswordNotCorrect").val());
				$('#oldPassword').val("");
				$('#oldPassword').focus();
        	} else {
				$("#oldPasswordCheckTxt").html("");
				bRet = true;
        	}
        }
    });
	return bRet;
}

$(function() {
	// 回车修改
	$(document).keydown(function(e) {
		if (e.keyCode == 13) {
			doUpdate();
		}
	});

	// 点击修改注册
	$("#btnUpdate").click(function() {
		doUpdate();
	});

	$("#username").blur(
		function() {
			var userNameVal = $("#username").val();
			var userId = $("#userId").val();

			var userId = $("#userId").val();
			if (userNameVal == null || userNameVal == '') { // 当用户名不存在，则清空任何用户名的提示信息
				$("#userNameCheckTxt").html(
						$("#hiddenCheckUserNameNotNull").val());
				$("#username").focus();
			} else if (!checkUserName(userNameVal)) {
				$("#userNameCheckTxt").html(
						$("#hiddenCheckUserNameWrongFormat").val());
				$("#username").focus();
				return false;
			} else if (checkDuplicatedUserName(userId, userNameVal, true)) {// 用户名聚焦，存在，则去系统检验
				// Check duplicate
				$("#userNameCheckTxt").html("");
				//$("#userNameCheckTxt").html($("#hiddenCheckUserNameTrueResult") .val());
			}
		});

	// 注册
	function doUpdate() {
		$("#userNameCheckTxt").html("");
		var userNameVal = $("#username").val();
		var userId = $("#userId").val();
		var gender = $('input:radio[name="gender"]:checked').val();

		if (userNameVal == null || userNameVal == '') { // 当用户名不存在，则清空任何用户名的提示信息
			$("#userNameCheckTxt").html($("#hiddenCheckUserNameNotNull").val());
			$("#username").focus();
			return false;
		} else if (!checkUserName(userNameVal)) {
			$("#userNameCheckTxt").html(
					$("#hiddenCheckUserNameWrongFormat").val());
			$("#username").focus();
			return false;
		} else if (!checkDuplicatedUserName(userId, userNameVal, false)) {
			$("#userNameCheckTxt").html(
					$("#hiddenCheckUserNameFalseResult").val());
			$("#username").focus();
			alert("userid=" + userId);
			return false;
		}

		$.ajax({
			type : 'post',
			cache : false,
			dataType : 'json',
			url : ctx + '/portal/user/selfservice/saveUser',
			data : {
				'userId': userId,
				'userName' : userNameVal,
				'phone':$("#mobile").val(),
				'gender': gender,
				'comment':$("#comment").val()
			},
			success : function(data) {
				$('#uptSuccess').attr('style','display: block');
				$("#userNameCheckTxt").html("");
			},
			error : function(data) {
				alert(data.msg);
			}
		});
	}

	$('#oldPassword').blur(function(){
		if ($('#oldPassword').val() == null || $('#oldPassword').val() == '') {
			$("#oldPasswordCheckTxt").html($("#oldPasswordCanNotBeNull").val());
			$("#oldPassword").focus();
			return false;
		}
		/*var bRet = ValidatePwd(false);
		if (bRet == true) {
			$("#password").focus();
		} else {
			$("#oldPassword").focus();
		}
		return bRet;*/
		$("#oldPasswordCheckTxt").html("");
		return true;
	});

	$('#password').blur(function(){
		// Input old password first
		if ($('#oldPassword').val() == null || $('#oldPassword').val() == '') {
			$("#oldPasswordCheckTxt").html($("#oldPasswordCanNotBeNull").val());
			$("#oldPassword").focus();
			return false;
		}

		if ($('#password').val() == null || $('#password').val() == '') {
			$("#newPasswordCheckTxt").html($("#canNotBeNull").val());
			$("#password").focus();
			return false;
		}
		$("#newPasswordCheckTxt").html("");
		$("#confirmPassword").focus();
		return true;
	});

	$('#confirmPassword').blur(function(){
		// Input old password first
		if ($('#oldPassword').val() == null || $('#oldPassword').val() == '') {
			$("#oldPasswordCheckTxt").html($("#oldPasswordCanNotBeNull").val());
			$("#oldPassword").focus();
			return false;
		}

		if($('#confirmPassword').val() != $('#password').val()){
			$("#confirmPasswordCheckTxt").html($("#notSame").val());
			//$("#confirmPassword").focus();
			return false;
		}
		$("#confirmPasswordCheckTxt").html("");
		return true;
	}); 	

	$('#conButton').click(function(){
		//if ($("#form1").form('validate')) {
		// 隐藏成功修改标志
		$('#pwdSuccess').attr('style','display: none');
		
		if (ValidatePwd(false) == false) {
			return false;
		}
		// 新密码不能为空
		if ($('#password').val() == null || $('#password').val() == '') {
			$("#newPasswordCheckTxt").html($("#canNotBeNull").val());
			$("#password").focus();
			return false;
		}
		// 确认密码不能为空
		if($('#confirmPassword').val() != $('#password').val()){
			$("#confirmPasswordCheckTxt").html($("#notSame").val());
			$("#confirmPassword").focus();
			return false;
		}
		// 确认密码不等于新密码
		if($('#confirmPassword').val() != $('#password').val()){
			$("#confirmPasswordCheckTxt").html($("#notSame").val());
			$("#confirmPassword").focus();
			return false;
		}
		// 新旧密码一致
		if($('#password').val() == $('#oldPassword').val()){
			$("#newPasswordCheckTxt").html($("#canNotBeSame").val());
			$("#password").focus();
//				alert("新密码不能与原始密码相同,请重新输入!");
//				$('#password').val("");
//            	$('#confirmPassword').val("");
        	$('#password').focus();
        	return false;
		}
		$.ajax({
            type: 'post', 
            cache: false, 
            dataType: 'json',
            url: ctx + '/portal/user/selfservice/saveEditPassword',
            data: {
            	'userId'   : $("#userId").val(),
                'password' : $("#password").val()
            },
            success: function (data) {
            	if(data.success) {
					$('#pwdSuccess').attr('style','display: block');
	            	$('#oldPassword').val("");
	            	$('#password').val("");
	            	$('#confirmPassword').val("");
	        		$("#oldPasswordCheckTxt").html("");
	        		$("#newPasswordCheckTxt").html("");
	        		$("#confirmPasswordCheckTxt").html("");
            	}else{
	            	alert(data.msg);
					$('#oldPassword').val("");
					$('#oldPassword').focus();
            	}
            }
        });
		
	}); 	

});
