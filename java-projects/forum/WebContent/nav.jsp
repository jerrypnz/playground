<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.zhaonan.forum.po.*" %>

<div id="loginLinks">
	<%
		User user = (User)session.getAttribute("user");
		if(user == null)
		{
	%>
	<ul>
		<li><a href="register">注册</a></li>
		<li class="last"><a href="login">登录</a></li>
	</ul>
	<%
		}
		else
		{
	%>
	<ul>
		<li>
			<span class="highlight"><img style="margin-right: 5px;"
					src="image/profile.gif" />欢迎<%=user.getNickName() %>!</span>
		</li>
		<li>
			<a href="#">我的主题</a>
		</li>
		<li>
			<a href="#">我的帖子</a>
		</li>
		<li>
			<a href="settings">个人资料</a>
		</li>
		<li>
			<a href="#">注销</a>
		</li>
		<li class="last">
			<a href="#">帮助</a>
		</li>
	</ul>
	<%
		}
	%>
</div>