<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<table width="0%" border="0" cellpadding="0" cellspacing="0"
	class="table">
	<tr class="thead">
		<td>
		<p><strong><img src="image/head_6.gif" width="6"
			height="7" />上传新商品</strong></p>
		</td>
	</tr>
	<tr>
		<td align="left" valign="top"><html:errors /><html:form
			action="uploadProductAction" method="post"
			enctype="multipart/form-data">
			<table width="100%" border="0">
				<tr>
					<td width="20%" align="right">商品名称：</td>
					<td><html:text property="name"></html:text></td>
				</tr>
				<tr>
					<td width="20%" align="right" valign="top">描述：</td>
					<td><html:textarea property="description" cols="40" rows="15"></html:textarea></td>
				</tr>
				<tr>
					<td width="20%" align="right">分类：</td>
					<td><html:select property="categoryId">
						<c:forEach items="${categories}" var="current">
							<html:option value="${current.key.id}">|<c:out
									value="${current.key.name}" />
							</html:option>
							<c:forEach items="${current.value}" var="child">
								<html:option value="${child.id}">|----<c:out
										value="${child.name}" />
								</html:option>
							</c:forEach>
						</c:forEach>
					</html:select></td>
				</tr>
				<tr>
					<td width="20%" align="right">底价：</td>
					<td><html:text property="basePrice"></html:text></td>
				</tr>
				<tr>
					<td width="20%" align="right">一口价：</td>
					<td><html:text property="tradePrice"></html:text></td>
				</tr>
				<tr>
					<td width="20%" align="right">拍卖天数：</td>
					<td><html:text property="days"></html:text></td>
				</tr>
				<tr>
					<td align="right">图片：</td>
					<td><html:file property="imageFile"></html:file></td>
				</tr>
				<tr>
					<td align="right">&nbsp;</td>
					<td><html:submit value="上传"></html:submit><html:reset
						value="重置"></html:reset></td>
				</tr>
			</table>
		</html:form></td>
	</tr>
</table>
