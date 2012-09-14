<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/FCKeditor.tld" prefix="fck"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>FCKeditor Sample</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	</head>

	<body>
		<p>下面的是FCKeditor的一个实例</p>
		<form action="result.jsp">
			<fck:editor id="content" basePath="/forum/" width="700"
				height="500" skinPath="/forum/editor/skins/silver/"
				toolbarSet="Basic">
			</fck:editor>
			<input type="submit" value="Submit">
		</form>
	</body>
</html>
