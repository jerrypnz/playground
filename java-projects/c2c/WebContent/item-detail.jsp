<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<table width="0%" border="0" cellpadding="0" cellspacing="0"
	class="table">
	<tr class="thead">
		<td>
		<p><strong><img src="image/head_6.gif" width="6"
			height="7" />商品详情</strong></p>
		</td>
	</tr>
	<tr>
		<td align="left" valign="top">
		<table width="100%" border="0">
			<tr>
				<td>
				<table width="100%" border="0">
					<tr>
						<td width="100" align="left" valign="top" class="dottedLine"><img
							src="${base_dir}/item_image_${product.id }.jpg" width="100"
							height="100" alt="${product.name}" /></td>
						<td class="dottedLine">
						<table width="100%" border="0">
							<tr>
								<td>商品名称：<c:out value="${product.name}"></c:out></td>
							</tr>
							<tr>
								<td>发货方式：上门送货</td>
							</tr>
							<tr>
								<td>上架时间：<c:out value="${product.createTime}"></c:out></td>
							</tr>
							<tr>
								<td>结束时间：<c:out value="${product.endTime}"></c:out></td>
							</tr>
							<tr>
								<td>发货期：3天内发货</td>
							</tr>
							<tr>
								<td>底价：<span class="productPrice">￥<c:out
									value="${product.basePrice}"></c:out></span></td>
							</tr>
							<tr>
								<td>一口价：<span class="productPrice">￥<c:out
									value="${product.tradePrice}"></c:out></span></td>
							</tr>
							<tr>
								<td><html:link action="buyItemAction?itemId=${product.id}"><img style="border:0px;" src="image/sale.gif" alt="购买" width="84"
									height="24" /></html:link></td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td class="dottedLine">
				<p class="productDetailHeader">商品细节：
				<div>${product.description}</div>
				</td>
			</tr>
			<tr>
				<td align="left">
				<p class="productDetailHeader">顾客评论：</p>
				<c:forEach var="currentComment" items="${product_comments}">
					<table width="90%" border="0" align="center" cellpadding="0"
						cellspacing="0" class="commentTable">
						<tr>
							<td width="30%" class="commentLine">留言者: <c:out
								value="${currentComment.maker.name}"></c:out></td>
							<td class="commentLine">留言时间: <c:out
								value="${currentComment.time}"></c:out></td>
						</tr>
						<tr>
							<td colspan="2"><c:out value="${currentComment.content}"></c:out>
							</td>
						</tr>
					</table>
				</c:forEach> <c:choose>
					<c:when test="${!empty user}">
						<table width="90%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<html:form action="itemCommentAction?productId=${product.id}">
								<tr>
									<td align="center" valign="top"><html:textarea
										property="content" cols="40" rows="15" /></td>
								</tr>

								<tr>
									<td align="center"><html:submit value="发表评论" /> <html:reset
										value="重新填写" /></td>
								</tr>
							</html:form>
						</table>
					</c:when>
					<c:otherwise>
						<table width="90%" border="0" align="center" cellpadding="0"
							cellspacing="0" class="commentTable">
							<tr>
								<td>
								<p>要发表评论，请先登录</p>
								</td>
							</tr>
							<tr>
								<td>
								<ul>
									<li>如果您还没有注册，请<html:link action="register">注册</html:link></li>
									<li>如果您已经注册过，请从<html:link action="index">首页</html:link>登录</li>
								</ul>
								</td>
							</tr>
						</table>
					</c:otherwise>
				</c:choose></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
