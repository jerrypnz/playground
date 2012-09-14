<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<table width="0%" border="0" cellpadding="0" cellspacing="0"
	class="table">
	<tr class="thead">
		<td>
		<p><strong><img src="image/head_6.gif" width="6"
			height="7" />店铺管理</strong></p>
		</td>
	</tr>
	<tr>
		<td align="left" valign="top">
		<ul>
			<li><html:link action="newProduct">上传新商品</html:link></li>
			<li><html:link action="listUnsoldItemAction">浏览在售商品</html:link></li>
			<li>浏览已售宝贝</li>
			<li>店铺留言</li>
		</ul>
		</td>
	</tr>
</table>
