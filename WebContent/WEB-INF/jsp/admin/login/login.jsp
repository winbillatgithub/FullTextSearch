<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.Date" pageEncoding="UTF-8"%>
<%@ include file="../common/admin_taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>后台管理登录</title>
<meta http-equiv="X-UA-Compatible" content="IE=8,IE=9,IE=10" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<style>
html, body {
	height: 100%;
	width: 100%;
	margin: 0;
	padding: 0;
	background-color: #48a176;
}
</style>
<link type="text/css" rel="stylesheet" href="${ctx}/css/admin/login/login.css" />
<script type="text/javascript" src="${ctx}/js/admin/login.js"></script>
<script type="text/javascript">


</script>
</head>
<body>
	<div class="login">
		<div class="blank"></div>
		<div class="left">
			<img src="${ctx }/images/login/logo06.png" width="212" height="130" />
		</div>

		<div class="formList">
			<form id="form" action="" name="form" method="post">
				   <div class="item">
						<div id="errorTxt"></div>
					</div>
					<div class="item">
						<span class="label">用户名：</span>
						<div class="item-bd"><input class="inp-txt w230" type="text" id="username" value=""/></div>
					</div>
					<div class="item">
						<span class="label">密&nbsp;&nbsp;&nbsp;码：</span>
						<div class="item-bd"><input class="inp-txt w230" type="password" id="password" value=""/></div>
					</div>
					<div class="item">
						<span class="label">验证码：</span>
						<div class="item-bd">
							<div class="code">
								<input type="hidden" name="token" id="token" />
								<input type="hidden" name="modulus" id="modulus" />
								<input class="inp-txt" id="randCheckCode" style="width: 80px;vertical-align:middle;" size="2" maxlength="2" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
								<img src="${ctx }/OperationCheckCode?nocache=<%=new Date().getTime()%>" id="OperationCheckCode" title="单击刷新验证码" style="vertical-align:middle;" width="100" height="30" class="codeImg" onclick="getOperationCheckCode()"/>
								<a href="javascript:void(0)" class="refresh" title="单击刷新验证码" onclick="getOperationCheckCode()">刷新</a>
							</div>
						</div>
					</div>
					<div class="item loginItem">
						<input type="button" class="btn242" style="border: 0;" id="btnLogin" value="登&nbsp;&nbsp;录"/>
						<input type="button" class="btn242" style="border: 0;" id="btnReset" value="重&nbsp;&nbsp;置"/>
					</div>
			</form>
		</div>
	</div>

</body>
</html>