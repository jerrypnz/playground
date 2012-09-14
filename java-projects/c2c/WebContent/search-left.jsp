<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<table width="0%" border="0" cellpadding="0" cellspacing="0"
	class="table">
	<tr class="thead">
		<td>
		<p><strong><img src="image/head_6.gif" width="6"
			height="7" />搜索</strong></p>
		</td>
	</tr>
	<tr>
		<td align="left" valign="top"><html:form
			action="searchItemAction">
			<table width="100%" border="0">
				<tr>
					<td width="30%" align="right">关键字：</td>
					<td><html:text property="keyword" size="12" /></td>
				</tr>
				<tr>
					<td align="right">价格：</td>
					<td><input name="lowprice" type="text" size="2" /> 至 <input
						name="highprice" type="text" size="2" /></td>
				</tr>
				<tr>
					<td align="right">时间：</td>
					<td><select name="time">
						<option value="0">近三天</option>
						<option value="1">近一周</option>
						<option value="2">近一个月</option>
						<option value="3">超过一个月</option>
					</select></td>
				</tr>
				<tr>
					<td align="center" colspan="2"><input type="submit"
						name="Submit" value="Lix搜" /> <input type="reset" name="Reset"
						value="重新填写" /></td>
				</tr>
			</table>
		</html:form></td>
	</tr>
</table>

<table width="0%" border="0" cellpadding="0" cellspacing="0"
	class="table">
	<tr class="thead">
		<td>
		<p><strong><img src="image/head_6.gif" width="6"
			height="7" />商品分类</strong></p>
		</td>
	</tr>
	<tr>
		<td align="left" valign="top">
		<table width="100%" border="0">
			<c:forEach items="${categories}" var="current">
				<tr>
					<td><html:link
						action="viewCategoryAction?categId=${current.key.id}">
						<strong><c:out value="${current.key.name}"></c:out></strong>
					</html:link><br />
					<c:forEach items="${current.value}" var="child">
						<html:link action="viewCategoryAction?categId=${child.id}">
							<c:out value="${child.name}"></c:out>
						</html:link> |
					</c:forEach></td>
				</tr>
			</c:forEach>
		</table>
		</td>
	</tr>
</table>


