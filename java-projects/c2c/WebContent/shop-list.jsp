<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<table width="0%" border="0" cellpadding="0" cellspacing="0"
	class="table">
	<tr class="thead">
		<td align="center" width="25%"><strong><img
			src="image/head_6.gif" width="6" height="7" />店铺名</strong></td>
		<td align="center" width="25%"><strong><img
			src="image/head_6.gif" width="6" height="7" />店主</strong></td>
		<td align="center" width="25%"><strong><img
			src="image/head_6.gif" width="6" height="7" />信誉度</strong></td>
		<td align="center" width="25%"><strong><img
			src="image/head_6.gif" width="6" height="7" />访问量</strong></td>
	</tr>
	<c:forEach var="currentShop" items="${shop_list}">
		<tr>
			<td align="center" valign="top"><c:out
				value="${currentShop.name}"></c:out></td>
			<td align="center" valign="top"><c:out
				value="${currentShop.owner.name}"></c:out></td>
			<td align="center" valign="top"><c:out
				value="${currentShop.credit}"></c:out></td>
			<td align="center" valign="top"><c:out
				value="${currentShop.visitTimes}"></c:out></td>
		</tr>
	</c:forEach>
</table>
