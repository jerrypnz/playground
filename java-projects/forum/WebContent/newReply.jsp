<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<jsp:include page="checkUser.jsp"></jsp:include>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="/WEB-INF/FCKeditor.tld" prefix="fck"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>发表新帖</title>
<link rel="stylesheet" type="text/css" href="css/forum.css" />
<link rel="stylesheet" type="text/css" href="css/editor.css" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
</head>
<body>
<div id="container">
<jsp:include page="nav.jsp"></jsp:include>
<jsp:include page="header.jsp"></jsp:include>

<div id="content">
<form action="savereply" method="post">
<table class="editorTable" cellspacing="1">
	<thead>
		<tr>
			<th colspan="2" class="title" style="width: 900px;">发表回复</th>
		</tr>
	</thead>

	<tbody>
		<tr>
			<td class="editor" colspan="2">
				<input name="topicid" type="hidden" value="<%= request.getParameter("topicid") %>" />
				<input name="current_page" type="hidden" value="<%= request.getParameter("current_page") %>" />
				<fck:editor id="content"
					basePath="/forum/" width="700" height="500"
					skinPath="/forum/editor/skins/silver/" toolbarSet="Basic">
				</fck:editor>
			</td>
		</tr>
		<tr>
			<td class="control" colspan="2" style="text-align: center;">
				<input class="button" value="提交" type="submit"/>
			</td>
		</tr>
	</tbody>
</table>
</form>
</div>
<jsp:include page="footer.jsp"></jsp:include></div>
</body>
</html>