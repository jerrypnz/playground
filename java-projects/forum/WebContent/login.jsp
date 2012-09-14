<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<title>Login</title>
		<link rel="stylesheet" type="text/css" href="css/forum.css" />
		<link rel="stylesheet" type="text/css" href="css/register.css" />
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	</head>
	<body>
		<div id="container">

			<jsp:include page="header.jsp"></jsp:include>

			<div id="content">

				<div id="sidebar">
					<h1>
						Tips：
					</h1>
					<p>
						欢迎注册javaeye的会员。注册成为会员，注册成功，帐号即时开通，马上能够下载文件，读站内短信，能够修改个人资料和密码，邀请朋友和推荐文章。
					</p>
					<p>
						注册满三天以后，您将拥有全部权限，包括在论坛发技术贴和招聘贴，对别人的帖子进行评分，写博客，发站内短信，申请专栏和发起圈子等。
					</p>
				</div>

				<div id="mainpart">
					<span style="margin-left: 50px;"><img
							src="image/user_login.gif" alt="用户登录" /> </span>
					<div id="errors" style="margin-left: 100px;">
						<%
							String[] errors = (String[]) request.getAttribute("errors");
							if (errors != null) {
								for (String error : errors) {
						%>
						<span class="highlight" style="font-size: 12px"><p>
								*<%=error%></p> </span>
						<%
							}
							}
						%>
					</div>
					<form action="loginAction" method="post">
						<table class="regTable" cellspacing="1" cellpadding="2">
							<tr>
								<td style="width: 150px;"></td>
								<td style="width: 230px;"></td>
							</tr>
							<tr>
								<td class="label">
									<span class="highlight">*</span>用户名
								</td>
								<td class="form">
									<input name="username" type="text" />
								</td>
							</tr>
							<tr>
								<td class="label">
									<span class="highlight">*</span>密码
								</td>
								<td class="form">
									<input name="password" type="password" />
								</td>
							</tr>
							<tr>
								<td class="label"></td>
								<td class="form">
									<a href="register">注册</a>
								</td>
							</tr>
							<tr>
								<td class="label"></td>
								<td class="form">
									<input class="button" type="submit" value="登录" />
								</td>
							</tr>
						</table>
					</form>
				</div>

			</div>

			<jsp:include page="footer.jsp"></jsp:include>
		</div>
	</body>
</html>