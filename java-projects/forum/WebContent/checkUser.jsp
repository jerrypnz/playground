<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.zhaonan.forum.po.*" %>
<%
	User user = (User)session.getAttribute("user");
	if(user == null)
	{
		request.setAttribute("errors",new String[]{
				"请先登录再使用本功能"
			});
		request.getRequestDispatcher("login.jsp").forward(request,response);
	}
%>
