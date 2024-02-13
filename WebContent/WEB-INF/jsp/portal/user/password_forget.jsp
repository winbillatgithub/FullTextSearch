<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<html>
<head>
<meta charset="utf-8">
<title>忘记密码</title>
<link rel='icon' href='${ctx}/images/logo.ico' type='image/x-ico' />
<jsp:include page="../common/portal_taglib.jsp"></jsp:include>
<link href="${ctx }/css/common.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/css/style.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.reg .regSuc {
	height: 125px;
	padding: 40px 100px 0;
	text-align: center;
	font-size: 14px;
}

.reg .regSuc .btn122 {
	margin-top: 20px;
}

.errorTxt {
	margin-left: 160px;
	color: #f00;
}

.successTxt {
	margin-left: 160px;
	color: #0AEE55;
	margin-top: 0px;
}
</style>
<script type="text/javascript">
        $(function (){
        	
        	//getDictionaryList('${ctx}', 'cardType', 'cardType');
        	
        	//单击刷新验证码
	        $("img").click(function () {
            	$("#OperationCheckCode").attr("src", "${ctx }/OperationCheckCode?nocache="+new Date().getTime());
		 	});
		 	
            //看不清,换一个
	        $("#flash").click(function () {
            	$("#OperationCheckCode").attr("src", "${ctx }/OperationCheckCode?nocache="+new Date().getTime());
		 	});
            
	        $('#username').blur(function(){
				if($('#username').val()){
					$.ajax({
			            type: 'post', 
			            cache: false, 
			            dataType: 'json',
			            url: '${ctx}/personal/user/portalUser/getPortalUserByName.do',
			            data: {
		                    'username' : $("#username").val()
	                    },
			            success: function (data) {
							if (!data.success) {
								$('#username').focus();
								$('#errorTxt').html("该用户不存在");
								/* $('#successTxt').html(null); */
					        } else {
					        	$('#errorTxt').html(null);
					        	$("#name").val(data.msg.name);
					        	$("#mobileNum").val(data.msg.mobile);
					        	/* $('#successTxt').html(data.msg); */
					        }
			            }
			        });
				}
			}); 
	        
	        $('#confirmcode').blur(function(){
		        //alert($('#code').val() != $('#confirmcode').val());
				if($('#code').val() != $('#confirmcode').val()){
					$('#code').focus();
					$('#errorTxt').html("两次密码不一致！");
				}else{
					$('#errorTxt').html(null);
				}
			}); 
        });

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
        
        function step2(){
        	if ($("#form1").form('validate')) {
        		$('#countInfo').attr('style','display: none');
            	$('#IDInfo').attr('style','display: block');
            	$('#step2').addClass("pass");
            	$('#errorTxt').html(null);
            	$('#phoneCodeInput').focus();
			}
        }
        function step3(){
        	if ($("#form2").form('validate')) {
        		$('#errorTxt').html(null);
        		
	       		$.ajax({
	                type: 'post', 
	                cache: false, 
	                dataType: 'json',
	                url: '${ctx}/personal/user/portalUser/checkPhoneCode.do',
	                data: {
	                	 'contactName' : $("#name").val(),
		                 'contactMobile' : $("#mobileNum").val(),
		                 'postCode' : $("#phoneCodeInput").val()
                    },
	                success: function (data) {
	                	if (data.success) {
		                	if(data.msg == 1) {
		                		$('#IDInfo').attr('style','display: none');
		                    	$('#relation').attr('style','display: block');
		                    	$('#step3').addClass("pass");
		            		} 
	                	}else{
		                	var emsg;
	                		if(data.msg == 2) {
								emsg = "手机验证码失效！";
	                		}else {
	                			emsg = "验证码错误！";
	                		}
							alert(emsg);
							$("#phoneCodeInput").val(null);
							$("#phoneCodeInput").focus();
		                }
	                },
	                error : function(data){   
	                	alert(data.msg);//显示失败提示信息 
	                }
	            });
			}
        }
        function step4(obj){
        	if ($("#form3").form('validate')) {
        		$('#errorTxt').html(null);
	       		$.ajax({
	                type: 'post', 
	                cache: false, 
	                dataType: 'json',
	                url: '${ctx}/personal/user/portalUser/saveForgetPwd.do',
	                data: $("#form1").serialize()+"&"+$("#form2").serialize()+"&"+$("#form3").serialize(),
	                success: function (data) {
	                	if (data.success) {
		                	if(data.msg == 1) {
		                		$('#relation').attr('style','display: none');
		                		$('#noReg').attr('style','display: none');
		                		$('#step4').addClass("pass");
		                		$('#regSuccess').attr('style','display: block');
		            		} 
	                	}
	                },
	                error : function(data){   
	                	alert(data.msg);//显示失败提示信息 
	                },
	                beforeSend: function () {
	                	if(obj) {
	                		obj.disabled=true;
	                	}
	                },
	                complete: function () {
	                	if(obj) {
	                		obj.disabled=false;
	                	}
	                }
	            });
        	}
       	}

        $(function () { 
        	$('#msgBtn').click(function () { 

        		$.ajax({
		            type: 'post', 
		            cache: false, 
		            dataType: 'json',
		            url: '${ctx}/personal/user/portalUser/getPhoneCode.do',
		            data: {
	                    'contactName' : $("#name").val(),
	                    'contactMobile' : $("#mobileNum").val()
                    },
		            success: function (data) {
    		            /*
						if (data.success) {
							alert("手机验证码发送成功，请查看您的手机！");
				        } else {
				        	alert("手机验证码发送失败，请重试");
				        }*/
		            }
		        });
        		
	            var count = 120; 
	            var countdown = setInterval(CountDown, 1000); 
		
		       	function CountDown() { 
		        	$("#msgBtn").attr("disabled", true); 
		        	$("#msgBtn").val(count + "秒后失效"); 
		        	if (count == 0) { 
		        		$("#msgBtn").val("获取短信验证码").removeAttr("disabled"); 
		        		clearInterval(countdown); 
		        	} 
		        	count--; 
		        } 
		  });

      }); 
</script>
</head>
<body>
<!--头部 开始-->
<div class="header">
	<div class="wrap">
		<h1 class="logo">健康365</h1>
		<p class="topLink"><a href="${ctx}/personal/user/portalUser/getReg.do" class="reg-txt">注册</a><span class="line">|</span><a href="${ctx }/portal/person.jsp">登录</a></p>
		<ul class="nav">
			<li><a href="${ctx}/index.jsp">首页</a></li>
			<li class="list">
            	<a href="#nogo">关于365</a>
                <ul class="down">
                    <li><a href="#nogo">公司介绍</a></li>
                    <li><a href="#nogo">发展历程</a></li>
                    <li><a href="#nogo">公司荣誉</a></li>
                    <li class="last"><a href="#nogo">领导寄语</a></li>
                </ul>
            </li>
			<li><a href="#nogo">365动态</a></li>
			<li><a href="#nogo">365服务</a></li>
			<li><a href="#nogo">客户服务</a></li>
			<li><a href="#nogo">联系我们</a></li>
		</ul>
        <script>
        $(".nav .list").hover(function(){
			$(this).toggleClass("open");
		});
        </script>
	</div>
</div>
<!--头部 结束-->

<!--中部 开始-->
<div class="wrap content clearfix">
	<div class="boxBorder">
		<h2>找回密码</h2>
		<!--进度条 开始-->
		<div class="step">
			<ul>
				<li id="step1" class="item1 pass">1<span>填写用户名</span></li>
				<li id="step2" class="item2">2<span>身份认证</span></li>
				<li id="step3" class="item3">3<span>设置新密码</span></li>
				<li id="step4" class="item4">4<span>完成</span></li>
			</ul>
		</div>
		<!--进度条 结束-->
		
		<div id="regForm" class="reg clearfix" >
			<div class="item">
				<div id="errorTxt" class="item errorTxt"></div>
				<!-- <div id="successTxt" class="item successTxt"></div> -->
			</div>
			<!--注册表单 开始-->
			<!-- 填写账户信息 -->
			<div id="countInfo">
			<form id="form1" name="form1" method="post">
				<div class="formList">
					<div class="item">
						<span class="label">用户名：</span>
						<div class="item-bd"><input id="username" name="username" class="easyui-validatebox inp-txt w230 defaultVal" required="true" validType="loginName" value="" type="text" style="width:204px;" /></div>
					</div>
					<div class="item">
						<span class="label">验证码：</span>
						<div class="item-bd">
							<div class="code">
								<img src="${ctx }/OperationCheckCode" id="OperationCheckCode" title="单击刷新验证码" style="vertical-align:middle; width: 100px;height: 30px;" class="codeImg"/>
								<a href="javascript:void(0)" id="flash" class="refresh" title="单击刷新验证码">刷新</a>
								<input class="easyui-validatebox inp-txt" style="width: 80px;vertical-align:middle;margin-left: 0px;" name="randCheckCode" value="" type="text" maxlength="2" required="true" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" />
							</div>
						</div>
					</div>
					<div class="item">
						<div class="item-bd">
							<div>
								<input type="button" style="border: 0;" value="下一步"  class="btn122" onclick="step2()"/>
							</div>
						</div>
					</div>
				</div>
			</form>
			</div>
			
			<!-- 填写身份信息 -->
			<div id="IDInfo" style="display: none;">
			<form id="form2" name="form2" method="post">
				<div class="formList">
					<div class="item">
						<span class="label">真实姓名：</span>
						<div class="item-bd" ><input id="name" name="name" value="" class="easyui-validatebox inp-txt w230" required="true" maxlength="30" disabled="disabled" style="width:175px;"/></div>
					</div>
					<div class="item">
						<span class="label">手机号码：</span>
						<div class="item-bd"><input id="mobileNum"  name="mobile" value="" class="easyui-validatebox inp-txt w230" required="true" validType="mobile" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="11" disabled="disabled"  style="width:175px;"/></div>
					</div>
					<div class="item">
						<span class="label">短信验证码：</span>
						<div class="item-bd">
						    <!-- <a href="#" class="btn122w" id="msgBtn">获取短信验证码</a> -->
						    <input type="button" style="border: 0;" value="获取短信验证码"  class="btn122w" id="msgBtn" />
							<input id="phoneCodeInput" name="mobileCode" value="" class="easyui-validatebox inp-txt w230" required="true" validType="NUM" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="6" style="width: 50px;vertical-align:middle;"/>
							</div>
					</div>
					<div class="item">
						<div class="item-bd">
							<div>
								<input type="button" style="border: 0;" value="下一步"  class="btn122" onclick="step3()"/>
							</div>
						</div>
					</div>
				</div>
			</form>
			</div>
			
			<!-- 设置关联用户 -->
			<div id="relation" style="display: none;">
			<form id="form3" name="form3" method="post">
				<div class="formList">
					<div class="item">
						<span class="label">设置新密码：</span>
						<div class="item-bd"><input id="code" name="code" value="" class="easyui-validatebox inp-txt w230" required="true" validType="safepass" value="" type="password" style="width:204px;" onkeydown='fnckeystop(event)' onpaste='return false' oncontextmenu='return false'/></div>
					</div>
					<div class="item">
						<span class="label">确认新密码：</span>
						<div class="item-bd"><input id="confirmcode" name="password" value="" class="easyui-validatebox inp-txt w230" required="true" validType="equalTo['#code']" value="" type="password" style="width:204px;" onkeydown='fnckeystop(event)' onpaste='return false' oncontextmenu='return false'/></div>
					</div>
					
					<div class="item">
						<div class="item-bd">
							<div>
								<input id="commitb" type="button" style="border: 0px;" value="保存" class="btn122" onclick="step4(this)"/>
							</div>
						</div>
					</div>
				</div>
			</form>
			</div>
			
			<!-- 注册成功 -->
			<div id="regSuccess" class="regSuc" style="display: none;">
				恭喜您！密码已成功找回！<br>
				现在您可以直接登录了！<br>
				<a href="${ctx }/portal/person.jsp" class="btn122">直接登录</a>
			</div>
			
			<!--注册表单 结束-->
			<div id="noReg" class="noReg">
				如果您是已注册用户，您可以<br><a href="${ctx }/portal/person.jsp" class="btn122w">直接登录</a>
			</div>
		</div>
	</div>
</div>
<!--中部 结束-->

<!--底部 开始-->
<div class="footer">
	<div class="wrap">
		<ul class="ft-link">
			<li>
				<h3>关于365</h3>
				<p><a href="#nogo">民政部</a></p>
				<p><a href="#nogo">老龄办</a></p>
                <p><a href="#nogo">Dell</a></p>
			</li>
			<li>
				<h3>合作伙伴</h3>
				<p><a href="#nogo">法律声明</a></p>
                <p><a href="#nogo">意见建议</a></p>
				<p><a href="#nogo">网站地图</a></p>
			</li>
            <li>
				<h3>联系我们</h3>
				<p><a href="#nogo">联系我们</a></p>
				<h3>招聘信息</h3>
				<p><a href="#nogo">招聘信息</a></p>
			</li>
			<li>
				<h3>网站帮助</h3>
				<p><a href="#nogo">法律声明</a></p>
				<p><a href="#nogo">意见建议</a></p>
				<p><a href="#nogo">网站地图</a></p>
			</li>
		</ul>
		<p class="copyright"><span>Copyright © 2013-2014 Beijing Acttek Technology Ltd. All Rights Reserved. </span><span>北京艾科泰克科技有限公司版权所有</span><span>京ICP备13050124号</span></p>
	</div>
</div>
<!--底部 结束-->
</body>
</html>
