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
