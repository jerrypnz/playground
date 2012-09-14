<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><c:out value="${title}"></c:out></title>
<link href="css/color.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.dottedLine {
	border-top-style: none;
	border-right-style: none;
	border-bottom-style: dotted;
	border-left-style: none;
	border-bottom-color: #FF9900;
	border-top-width: thin;
	border-right-width: thin;
	border-bottom-width: thin;
	border-left-width: thin;
}
-->
</style>
</head>

<body>
<table width="100%" border="0" class="table">
	<tr class="thead">
		<td>
		<p><strong><img src="image/head_6.gif" width="6"
			height="7" />请先登录</strong></p>
		</td>
	</tr>
	<tr>
		<td>
		<ul>
			<li>如果您还没有注册，请<html:link action="register">注册</html:link></li>
			<li>如果您已经注册过，请从<html:link action="index">首页</html:link>登录</li>
		</ul>
		</td>
	</tr>
</table>
</body>
</html:html>