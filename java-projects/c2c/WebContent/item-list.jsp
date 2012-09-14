<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<table width="0%" border="0" cellpadding="0" cellspacing="0"
	class="table">
	<tr class="thead">
		<td align="center" width="15%">
		<p><strong><img src="image/head_6.gif" width="6"
			height="7" />图片</strong></p>
		</td>
		<td align="center" width="30%"><strong><img
			src="image/head_6.gif" width="6" height="7" />商品名称</strong></td>
		<td align="center" width="30%"><strong><img
			src="image/head_6.gif" width="6" height="7" />分类</strong></td>
		<td width="25%"><strong><img src="image/head_6.gif"
			width="6" height="7" />价格</strong></td>
	</tr>
	<c:forEach items="${item_list}" var="currentItem">
		<tr>
			<td align="center" valign="middle" class="dottedLine"><html:link
				action="showItemAction?productId=${currentItem.id}">
				<img src="${base_dir}/item_image_${currentItem.id}.jpg" width="50"
					height="50" />
			</html:link></td>
			<td align="center" valign="middle" class="dottedLine"><html:link
				action="showItemAction?productId=${currentItem.id}">
				<c:out value="${currentItem.name}" />
			</html:link></td>
			<td align="center" valign="middle" class="dottedLine"><c:out
				value="${currentItem.category.name}" /></td>
			<td valign="middle" class="dottedLine">底价:<span
				class="productPrice">￥<c:out value="${currentItem.basePrice}" /></span><br />
			一口价:<span class="productPrice">￥<c:out
				value="${currentItem.tradePrice}" /></span></td>
		</tr>
	</c:forEach>
</table>
