<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<table width="0%" border="0" cellpadding="0" cellspacing="0"
	class="table">
	<tr class="thead">
		<td>
		<p><strong><img src="image/head_6.gif" width="6"
			height="7" />欢迎申请开店，<c:out value="${user.name}" /></strong></p>
		</td>
	</tr>
	<tr>
		<td align="left" valign="top"><html:form action="/shopRegAction">
			<table width="100%" border="0">
				<tr>
					<td width="30%" align="right">店铺名：</td>
					<td><html:text property="name"></html:text></td>
				</tr>
				<tr>
					<td width="30%" align="right" valign="top">描述：</td>
					<td><html:textarea property="description" rows="15" cols="40"></html:textarea></td>
				</tr>
				<tr>
					<td width="30%" align="right">分类：</td>
					<td><html:select property="category">
						<c:forEach items="${categories}" var="current">
							<html:option value="${current.key.id}">|<c:out value="${current.key.name}"/></html:option>
							<c:forEach items="${current.value}" var="child">
								<html:option value="${child.id}">|----<c:out value="${child.name}"/></html:option>
							</c:forEach>
						</c:forEach>
					</html:select>
					</td>
				</tr>
				<tr>
					<td width="30%" align="right">&nbsp;</td>
					<td><html:submit value="提交" /> <html:reset value="重置" /></td>
				</tr>
			</table>
		</html:form></td>
	</tr>
</table>
