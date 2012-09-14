<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Login</title>
<link rel="stylesheet" type="text/css" href="css/forum.css" />
<link rel="stylesheet" type="text/css" href="css/register.css" />
<link rel="stylesheet" type="text/css" href="css/msg.css" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
</head>
<body>
<div id="container">

	<jsp:include page="header.jsp"></jsp:include>

	<jsp:include page="nav.jsp"></jsp:include>


	<div id="content">
	
		<div id="sidebar">
			<h1>Tips：</h1>
			<p>
				欢迎注册javaeye的会员。注册成为会员，注册成功，帐号即时开通，马上能够下载文件，读站内短信，能够修改个人资料和密码，邀请朋友和推荐文章。
			</p>
			<p>
				注册满三天以后，您将拥有全部权限，包括在论坛发技术贴和招聘贴，对别人的帖子进行评分，写博客，发站内短信，申请专栏和发起圈子等。
			</p>
		</div>
		
		<div id="mainpart">
			<img class="msg_image" src="image/msg.png" />
			<h1 class="msg_title"><%= request.getAttribute("msg_title") %></h1>
			<%
				String[] msgs = (String[])request.getAttribute("msgs");
				if(msgs!=null)
				{
					for(String msg:msgs)
					{					
			%>
			<p class="msg_body"><%= msg %></p>
			<%
					}
				}
			%>
		</div>
		
		
	</div>
	
	<jsp:include page="footer.jsp"></jsp:include>
</div>
</body>
</html>