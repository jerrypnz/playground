<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<table width="0%" border="0" cellpadding="0" cellspacing="0"
	class="table">
	<tr class="thead">
		<td>
		<p><strong><img src="image/head_6.gif" width="6"
			height="7" /><c:out value="${user_shop.name}"></c:out></strong></p>
		</td>
	</tr>
	<tr>
		<td align="left" valign="top">
		<table width="100%" border="0">
			<tr>
				<td width="50%"><strong>店铺名：</strong><c:out
					value="${user_shop.name}" /></td>
				<td width="50%"><strong>店铺分类：</strong><c:out
					value="${user_shop.category.name}" /></td>
			</tr>
			<tr>
				<td><strong>创建日期：</strong><c:out
					value="${user_shop.createTime}" /></td>
			</tr>
			<tr>
				<td colspan="2"><strong>店铺描述：</strong><c:out
					value="${user_shop.description}" /></td>
			</tr>
			<tr>
				<td colspan="2"><strong>管理店铺：</strong><span class="promo">
				<html:link action="newProduct">上传新商品</html:link> | <html:link
					action="listUnsoldItemAction">浏览在售商品</html:link> | <a href="#">浏览已售宝贝</a>
				| <a href="#">店铺留言</a></span></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
