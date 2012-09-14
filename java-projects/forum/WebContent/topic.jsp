<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<%@ page import="com.zhaonan.forum.vo.*"%>
<%@ page import="com.zhaonan.forum.po.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%
	Topic topic = (Topic) request.getAttribute("topic");
	List<PostBean> posts = (List<PostBean>) request
			.getAttribute("posts");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><%=topic.getTitle()%></title>
<link rel="stylesheet" type="text/css" href="css/forum.css" />
<link rel="stylesheet" type="text/css" href="css/post.css" />
<link rel="stylesheet" type="text/css" href="css/thumb.css" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
</head>
<body>
<div id="container">
<jsp:include page="nav.jsp" />
<jsp:include page="header.jsp" />

<div id="content">
<div class="navigate">
<table>
	<tr>
		<td><img src="image/post.gif" alt="发表新帖"
			onclick="window.location='newtopic';"
		/> <img src="image/reply.gif" alt="发表回复"
			onclick="window.location='newreply?topicid=<%=topic.getId()%>&current_page=<%=request.getAttribute("current_page") %>';"
		/></td>
		<%
			Board board = (Board) session.getAttribute("current_board");
			if (board == null)
			{
				response.sendRedirect("boards");
			}
		%>
		<td style="padding-left: 10px;"><a href="boards">首页</a> → <a
			href="topics?boardid=<%=board.getId()%>"
		><%=board.getName()%></a> → <%=topic.getTitle()%></td>
	</tr>
</table>

</div>
<table class="postTable" cellspacing="1">
	<thead>
		<tr>
			<th colspan="2" class="title">主题：<%=topic.getTitle()%></th>
		</tr>
		<tr>
			<th class="header" style="width: 170px;">作者</th>
			<th class="header" style="width: 730px;">正文</th>
		</tr>
	</thead>
	<tbody>
		<%
			for (PostBean postBean : posts)
			{
				Post p = postBean.getPost();
				User u = postBean.getUser();
				String time = new SimpleDateFormat("yyyy-MM-dd : HH:mm:ss")
						.format(p.getPostTime());
		%>
		<tr>
			<td class="user_profile">
			<ul>
				<li class="user_name"><%=u.getNickName()%></li>
				<li>等级：<%=u.getRank()%></li>
				<li>积分：<%=u.getMark()%></li>
				<li>
					<div class="thumb">
						<a href="#">
							<img src="usericon.jpg?userid=<%= u.getId() %>" 
								alt="User Icon" />
						</a>
					</div>
				</li>
			</ul>
			</td>
			<td class="post_body">
			<div class="post_time">时间：<%=time%></div>
			<div class="post_text">
			<p><%=p.getContent()%></p>
			</div>
			</td>
		</tr>

		<tr>
			<td colspan="2" class="separator"></td>
		</tr>

		<%
			}
		%>

	</tbody>
</table>

<div style="text-align: right;">共<%=request.getAttribute("total_pages")%>页，<%=request.getAttribute("total_num")%>个贴子，当前在第<%=request.getAttribute("current_page")%>页(
<%
	Integer prev = (Integer) request.getAttribute("prev_page");
	Integer next = (Integer) request.getAttribute("next_page");
	if (prev != null)
	{
%><a
	href="topic?page=<%=prev%>&topicid=<%=request.getParameter("topicid")%>"
>上一页</a> <%
 	}
 	if (next != null)
 	{
 %> <a
	href="topic?page=<%=next%>&topicid=<%=request.getParameter("topicid")%>"
>下一页</a> <%
 	}
 %> )</div>

<div id="quickreply">
<form action="savereply" method="post">
<div style="margin-bottom: 10px;"><input type="hidden"
	name="topicid" value="<%= topic.getId() %>"
/> <input type="hidden" name="current_page"
	value="<%= request.getAttribute("current_page") %>"
/> <input type="submit" value="提交" class="button" /></div>
<textarea name="content" rows="8" cols="40"
	style="border: 1px solid #BDBDBD; padding: 1px 1px 1px 1px;"
></textarea></form>
</div>

</div>
<jsp:include page="footer.jsp"></jsp:include></div>
</body>
</html>