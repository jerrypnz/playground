<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.zhaonan.forum.vo.*"%>
<%@ page import="com.zhaonan.forum.po.*"%>
<%@ page import="java.util.*"%>
<%
	Board board = (Board)session.getAttribute("current_board");
	if(board == null)
	{
		response.sendRedirect("boards");
	}
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<title><%= board.getName() %></title>
		<link rel="stylesheet" type="text/css" href="css/forum.css" />
		<link rel="stylesheet" type="text/css" href="css/topic.css" />
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	</head>
	<body>
		<div id="container">
			<jsp:include page="nav.jsp"></jsp:include>
			<jsp:include page="header.jsp"></jsp:include>
			<div id="content">
				<div class="navigate">
					<table>
						<tr>
							<td>
								<img src="image/post.gif" alt="发表新帖"
									onclick="window.location='newtopic';" />
							</td>
							<td style="padding-left:10px;">
								<a href="boards">首页</a> → <a href="topics?boardid=<%=board.getId()%>"><%=board.getName() %></a>
							</td>
						</tr>
					</table>
				</div>
				<table class="topicTable" cellspacing="1">
					<thead>
						<tr>
							<th class="title"></th>
							<th class="title" style="width: 550px;">
								主题
							</th>
							<th class="title" style="width: 50px;">
								阅读
							</th>
							<th class="title" style="width: 50px;">
								回复
							</th>
							<th class="title" style="width: 250px;">
								作者
							</th>
						</tr>
					</thead>
					<tbody>
						<%
							List<TopicBean> topicList = (List<TopicBean>) request
									.getAttribute("topics");
							for (TopicBean topic : topicList) {
						%>
						<tr>
							<td class="topic">
								<img src="image/topic_icon.gif" alt="版面图标" />
							</td>
							<td class="topic">
								<a href="topic?topicid=<%=topic.getId()%>"><%=topic.getTitle()%></a>
							</td>
							<td class="otherCol"><%=topic.getReadTimes()%></td>
							<td class="otherCol"><%=topic.getReplyTimes()%></td>
							<td class="otherCol"><%=topic.getAuthorName()%></td>
						</tr>
						<%
							}
						%>
					</tbody>
				</table>

				<div style="text-align: right;">
					共<%=request.getAttribute("total_pages")%>页，<%=request.getAttribute("total_num")%>个主题，当前在第<%=request.getAttribute("current_page")%>页(
					<%
					Integer prev = (Integer) request.getAttribute("prev_page");
					Integer next = (Integer) request.getAttribute("next_page");
					if (prev != null) {
				%><a
						href="topics?page=<%=prev%>&boardid=<%=request.getParameter("boardid")%>">上一页</a>
					<%
						}
						if (next != null) {
					%>
					<a
						href="topics?page=<%=next%>&boardid=<%=request.getParameter("boardid")%>">下一页</a>
					<%
						}
					%>
					)
				</div>

			</div>

			<jsp:include page="footer.jsp"></jsp:include>
		</div>
	</body>
</html>