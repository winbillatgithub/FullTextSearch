<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
</head>
<body>

  <%
      String path = request.getContextPath();
  	System.out.println("*****"+path);
      response.sendRedirect(path + "/index.html");
  %>

</body>
</html>