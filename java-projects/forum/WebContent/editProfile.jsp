<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.zhaonan.forum.po.*" %>
<%
	User u = (User)session.getAttribute("user");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<title>Register</title>
		<link rel="stylesheet" type="text/css" href="css/forum.css" />
		<link rel="stylesheet" type="text/css" href="css/register.css" />
		<link rel="stylesheet" type="text/css" href="css/thumb.css" />
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	</head>
	<body>
		<div id="container">
			<jsp:include page="nav.jsp"></jsp:include>
			<jsp:include page="header.jsp"></jsp:include>
			<jsp:include page="checkUser.jsp"></jsp:include>
			<div id="content">
				<div class="navigate">
						<table>
							<tr>
								<td style="padding-left:10px;">
									<a href="boards">首页</a> → 用户设置
								</td>
							</tr>
						</table>
				</div>

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
							src="image/user_register.gif" alt="用户设置" /> </span>
					<div id="errors" style="margin-left: 100px;">
						<%
							List<String> errors = (List<String>) request.getAttribute("errors");
							if (errors != null) {
								for (String error : errors) {
						%>
						<span class="highlight" style="font-size: 12px">*<%=error%></span>
						<%
							}
							}
						%>
					</div>
					<form action="updateuser" method="post">
						<table class="regTable" cellspacing="1" cellpadding="2">
							<tr>
								<td style="width: 150px;"></td>
								<td style="width: 230px;"></td>
								<td style="width: 320px;"></td>
							</tr>
							<tr>
								<td class="label">
									用户名
								</td>
								<td class="form">
									<span><%= u.getLoginName() %></span>
								</td>
								<td class="descrip">
								</td>
							</tr>
							<tr>
								<td class="label">
									昵称
								</td>
								<td class="form">
									<span><%= u.getNickName() %></span>
								</td>
								<td class="descrip">
								</td>
							</tr>
							<tr>
								<td class="label">
									邮箱
								</td>
								<td class="form">
									<input name="email" type="text"
										value="<%= u.getEmail() %>" />
								</td>
								<td class="descrip">
									<span class="highlight">*</span>确保可用
								</td>
							</tr>
							<tr>
								<td class="label">
									密码
								</td>
								<td class="form">
									<input name="pass1" type="password" />
								</td>
								<td class="descrip">
									<span class="highlight">*</span>4-20个字符
								</td>
							</tr>
							<tr>
								<td class="label">
									密码确认
								</td>
								<td class="form">
									<input name="pass2" type="password" />
								</td>
								<td class="descrip">
									<span class="highlight">*</span>
								</td>
							</tr>
							<tr>
								<td colspan="3">
									<hr />
								</td>
							</tr>
							<tr>
								<td class="label">
									QQ
								</td>
								<td class="form">
									<input name="qq" type="text"
										value="<%= u.getQq() %>" />
								</td>
								<td class="descrip"></td>
							</tr>
							<tr>
								<td class="label">
									个人主页
								</td>
								<td class="form">
									<input name="homepage" type="text"
										value="<%= u.getHomepage() %>" />
								</td>
								<td class="descrip"></td>
							</tr>
							<tr>
								<td colspan="3">
									<hr />
								</td>
							</tr>
							<tr>
								<td class="label">
								</td>
								<td class="form">
									<input class="button" type="submit" value="修改" />
								</td>
								<td class="descrip"></td>
							</tr>
						</table>
					</form>
					
					<form action="uploadpic" method="post" enctype="multipart/form-data">
						<table class="regTable" cellspacing="1" cellpadding="2" style="margin-top:50px;">
								<tr>
									<td style="width: 175px;"></td>
									<td style="width: 300px;"></td>
									<td style="width: 225px;"></td>
								</tr>
								<tr>
									<td class="label">当前头像</td>
									<td class="form">
										<div class="thumb">
											<a href="#">
												<img src="usericon.jpg?userid=<%= u.getId() %>" 
													alt="User Icon" />
											</a>
										</div>
									</td>
									<td class="descrip"></td>
								</tr>
								<tr>
									<td class="label">修改头像</td>
									<td class="form">
										<input type="file" name="userpic" />
									</td>
									<td class="descrip"></td>
								</tr>
								<tr>
									<td colspan="3">
										<hr />
									</td>
								</tr>
								<tr>
									<td class="label"></td>
									<td class="form">
										<input style="width:100px;" class="button" type="submit" value="上传" />
									</td>
									<td class="descrip"></td>
								</tr>
						</table>
					</form>
				</div>


			</div>

			<jsp:include page="footer.jsp"></jsp:include>
		</div>
	</body>
</html>