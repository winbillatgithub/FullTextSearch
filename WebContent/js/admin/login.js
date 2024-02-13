	//获取验证码
	function getOperationCheckCode() {
		$("#OperationCheckCode").attr("src", ctx+"/OperationCheckCode?nocache="+new Date().getTime());
	}

$(function (){
    //光标
	$("#username").focus();

    //回车登录
    $(document).keydown(function (e) {
        if (e.keyCode == 13) {
            dologin();
        }
    });

    //点击按钮登录
    $("#btnLogin").click(function () {
    	alert("登录");
        dologin();
    });
    
    
    //点击按钮重置
    $("#btnReset").click(function () {
    	alert("重置");
    	doReset();
    });   
    
    function doReset(){
    	$(".inp-txt").removeClass("inp-error");
    	$("#username").val("");
    	$("#password").val("");
    	$("#username").focus("");
    }
    
    
	//登录
    function dologin() {
		$(".inp-txt").removeClass("inp-error");
		if(!$("#username").val()) {
			$("#username").addClass("inp-error");
			$("#username").focus();
			$("#errorTxt").html("账号不能为空 !");
			$("#errorTxt").addClass("errorTxt");
			return false;
		}
		if(!$("#password").val()) {
			$("#password").addClass("inp-error");
			$("#password").focus();
			$("#errorTxt").html("密码不能为空!");
			$("#errorTxt").addClass("errorTxt");
			return false;
		}
		if(!$("#randCheckCode").val()) {
			$("#randCheckCode").addClass("inp-error");
			$("#randCheckCode").focus();
			$("#errorTxt").html("验证码不能为空!");
			$("#errorTxt").addClass("errorTxt");
			return false;
		}

        $.ajax({
            type: 'post', 
            cache: false, 
            dataType: 'json',
            url: ctx+'/admin/login/loginAction',
            data: {
                'username' : $("#username").val(),
                'password' : $("#password").val(),
                'randCheckCode' : $("#randCheckCode").val()
            },
            success: function (data) {
            	if(data.success) {
            		/*if(data.msg == 1) {
            			window.location.href=ctx+"/admin/user/portalUser/firstEditPassword.do?random="+data.text;
            		} else {
            			window.location.href=ctx+"/admin/user/portalUser/main.do";
            		}*/
            		window.location.href=ctx+"/admin/adminIndex/index";
            	} else {
            		alert(data.msg);
            		//重新生成密钥和验证码
            		getOperationCheckCode();
  					$("#btnLogin").val("登  录");
  					$("#btnLogin").removeAttr("disabled");
            		if(data.msg == "1") {
            			$("#username").addClass("inp-error");
    					$("#errorTxt").html("验证码错误!");
    					$("#errorTxt").addClass("errorTxt");
    					$("#randCheckCode").focus();
    					$("#randCheckCode").val("");
            		}else if(data.msg == "2") {
            			$("#password").addClass("inp-error");
    					$("#errorTxt").html("用户不存在!");
    					$("#errorTxt").addClass("errorTxt");
    					$("#username").val("");
    					$("#username").focus();
    					$("#password").val("");
    					$("#randCheckCode").val("");
            		}else if(data.msg == "3") {
            			$("#randCheckCode").addClass("inp-error");
    					$("#errorTxt").html("密码错误!");
    					$("#errorTxt").addClass("errorTxt");
    					$("#password").val("");
    					$("#password").focus();
    					$("#randCheckCode").val("");
            		}else if(data.msg == "4") {
            			$("#randCheckCode").addClass("inp-error");
    					$("#errorTxt").html("用户被禁用!");
    					$("#errorTxt").addClass("errorTxt");
    					$("#username").val("");
    					$("#username").focus();
    					$("#password").val("");
    					$("#randCheckCode").val("");
            		}else if(data.msg == "5") {
            			$("#randCheckCode").addClass("inp-error");
    					$("#errorTxt").html("不是管理员用户!");
    					$("#errorTxt").addClass("errorTxt");
    					$("#username").val("");
    					$("#username").focus();
    					$("#password").val("");
    					$("#randCheckCode").val("");
            		}else{                    			
            			$("#errorTxt").html(data.msg);
            			$("#errorTxt").addClass("errorTxt");
            		}
            	}
            },
            error : function(data){ 
            	
            	$("#btnLogin").val("登  录");
            	$("#btnLogin").removeAttr("disabled");
            },
            beforeSend: function () {
            	/*alert("登录失败");*/
            	$(".inp-txt").removeClass("inp-error");
            	$("#errorTxt").html("");
            	$("#errorTxt").removeClass("errorTxt");
            	$("#btnLogin").val("登  录  中...");
            	$("#btnLogin").attr({"disabled":"disabled"});
            }
        });
    }
});