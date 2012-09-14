<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><tiles:getAsString name="title" /></title>
<link href="css/color.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.dottedLine {
	border-bottom-style: dotted;
	border-bottom-color: #FF9900;
	border-bottom-width: 1px;
}
-->
</style>
</head>

<body>
<table width="700" border="0" align="center">
	<tr>
		<td><tiles:insert attribute="header"></tiles:insert></td>
	</tr>

	<c:choose>
		<c:when test="${user!=null}">
			<td><tiles:insert attribute="middle"></tiles:insert></td>
		</c:when>
		<c:otherwise>
			<td><tiles:insert attribute="not-login"/></td>
		</c:otherwise>
	</c:choose>
	
	<tr>
		<td><tiles:insert attribute="footer"></tiles:insert></td>
	</tr>
</table>
</body>
</html:html>
