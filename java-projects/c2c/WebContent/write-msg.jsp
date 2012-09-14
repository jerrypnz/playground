<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<table width="0%" border="0" cellpadding="0" cellspacing="0"
	class="table">
	<tr class="thead">
		<td>
		<p><strong><img src="image/head_6.gif" width="6"
			height="7" />发消息</strong></p>
		</td>
	</tr>
	<tr>
		<td align="left" valign="top"><html:form action="sendMsgAction">
			<table width="100%" border="0">
				<tr>
					<th width="15%" align="right">收信人：</th>
					<td><html:text property="receiver" maxlength="20"
						value="${param.default_receiver}"></html:text> (请填写收件人用户名)</td>
				</tr>
				<tr>
					<th width="15%" align="right">标题：</th>
					<td><html:text property="title" maxlength="40"></html:text></td>
				</tr>
				<tr>
					<th width="15%" align="right" valign="top">正文：</th>
					<td><html:textarea property="content" rows="15" cols="45"></html:textarea>
					</td>
				</tr>
				<tr>
					<th width="15%" align="right">&nbsp;</th>
					<td><html:submit value="发送" /> <html:reset value="重写" /></td>
				</tr>
			</table>
		</html:form></td>
	</tr>
</table>
