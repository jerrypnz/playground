<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<c:choose>

	<c:when test="${user==null}">
		<table width="0%" border="0" cellpadding="0" cellspacing="0"
			class="table">
			<tr class="thead">
				<td>
				<p><strong><img src="image/head_6.gif" width="6"
					height="7" />会员登录</strong></p>
				</td>
			</tr>
			<tr>
				<td align="left" valign="top"><html:form action="/loginAction">
					<table width="100%" border="0">
						<tr>
							<td width="32%" align="right">用户名：</td>
							<td width="68%"><html:text property="username" size="16"
								maxlength="18" styleClass="loginInput" /></td>
						</tr>
						<tr>
							<td align="right">密码：</td>
							<td><html:password property="password" size="16"
								maxlength="18" styleClass="loginInput" /></td>
						</tr>
						<tr>
							<td colspan="2" align="center"><html:submit
								property="Submit" value="登录" /><html:reset value="重设" /></td>
						</tr>
					</table>
				</html:form></td>
			</tr>
		</table>
	</c:when>

	<c:otherwise>
		<table width="0%" border="0" cellpadding="0" cellspacing="0"
			class="table">
			<tr class="thead">
				<td>
				<p><strong><img src="image/head_6.gif" width="6"
					height="7" />你好,<c:out value="${user.name}" /></strong></p>
				</td>
			</tr>
			<tr>
				<td>
				<ul>
					<li><html:link action="receiveMsgAction">收消息</html:link></li>
					<li><html:link action="writeMsg">发消息</html:link></li>
					<li>我的出价</li>
					<li>我购买的宝贝</li>
					<c:if test="${!empty user_shop}">
						<li><html:link action="myShop">管理我的店铺</html:link></li>
					</c:if>
					<li><html:link action="logOffAction">注销</html:link></li>
				</ul>
				</td>
			</tr>
		</table>
	</c:otherwise>

</c:choose>

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

<table border="0" cellpadding="0" cellspacing="0" class="table">
	<tr class="thead">
		<td>
		<p><strong><img src="image/head_6.gif" width="6"
			height="7" />新手指南</strong></p>
		</td>
	</tr>
	<tr>
		<td>
		<table width="100%" border="0">
			<tr>
				<td class="dottedLine" width="42%"><img
					src="image/free_join.gif" width="77" height="41" /></td>
				<td class="dottedLine" width="58%">免费注册成为Java交易网的会员</td>
			</tr>
			<tr>
				<td class="dottedLine"><img src="image/free_rz.gif" width="77"
					height="41" /></td>
				<td class="dottedLine">免费认证成为卖家</td>
			</tr>
			<tr>
				<td><img src="image/free_shop.gif" width="77" height="41" /></td>
				<td>免费开店，即刻上传您的商品，开始拍卖</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<table width="0%" border="0" cellpadding="0" cellspacing="0"
	class="table">
	<tr class="thead">
		<td>
		<p><strong><img src="image/head_6.gif" width="6"
			height="7" />新开店铺</strong></p>
		</td>
	</tr>
	<tr>
		<td align="left" valign="top">
		<ul>
			<li><a href="#">Jerry's Java Shop Store </a></li>
			<li><a href="#">流行数码馆</a></li>
			<li><a href="#">手机专卖铺</a></li>
			<li><a href="#">DIY配件商城</a></li>
		</ul>
		</td>
	</tr>
</table>
