<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.zhaonan.forum.vo.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Boards</title>
<link rel="stylesheet" type="text/css" href="css/forum.css" />
<link rel="stylesheet" type="text/css" href="css/board.css" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
</head>
<body>
<div id="container">

	<jsp:include page="nav.jsp"></jsp:include>
	<jsp:include page="header.jsp"></jsp:include>	
			
	<div id="content">
		<table class="boardTable" cellspacing="1">		
			<thead>
				<tr>
					<th class="title"></th>
					<th class="title" style="width:550px;">版面</th>
					<th class="title" style="width:50px;">主题</th>
					<th class="title" style="width:50px;">帖子</th>
					<th class="title" style="width:250px;">最新主题</th>
				</tr>
			</thead>		
			<tbody>
				<%
					List<BoardBean> boards = (List<BoardBean>)request.getAttribute("boards");
					for(BoardBean b:boards)
					{
				%>
				<tr>
					<td class="otherCol"><img src="image/board_icon.gif" alt="版面图标" /></td>
					<td class="board"><a href="topics?boardid=<%=b.getId() %>"><%= b.getName()%></a><p><%= b.getDescription() %></p></td>
					<td class="otherCol"><%=b.getTopicNum() %></td>
					<td class="otherCol"><%=b.getPostNum() %></td>
					<td class="otherCol">
						<%
							if(b.getLatestTopicId()>=0)
							{
						%>
						<a href="topic?topicid=<%=b.getLatestTopicId()%>"><%=b.getLatestTopicTitle() %></a>
						<%
							}
						%>
					</td>
				</tr>	
				<% 
					}
				%>			
			</tbody>			
		</table>	
	</div>
	
	<div id="footer">
		<ul>
			<li><a href="#">我的主题</a></li>
			<li><a href="#">我的帖子</a></li>
			<li><a href="#">个人资料</a></li>
			<li><a href="#">注销</a></li>
			<li class="last"><a href="#">帮助</a></li>
		</ul>
	</div>
	
	<div id="copyright">
		&copy; 2003-2008 Nanday  All rights reserved.
	</div>
	
</div>
</body>
</html>