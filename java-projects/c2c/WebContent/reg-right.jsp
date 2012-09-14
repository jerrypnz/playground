<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<font color="#FF0000"> <html:errors /> </font>
<html:form action="/registerAction">
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		class="table">
		<tr class="thead">
			<td colspan="4"><strong><img src="image/head_6.gif"
				width="6" height="7" />注册</strong></td>
		</tr>
		<tr>
			<th align="right">用户名:</th>
			<td align="left"><html:text property="name" size="16"
				maxlength="18" /></td>
		</tr>
		<tr>
			<th align="right">昵称:</th>
			<td align="left"><html:text property="nickName" size="16"
				maxlength="18" /></td>
		</tr>
		<tr>
			<th align="right">密码:</th>
			<td align="left"><html:password property="password" size="16"
				maxlength="18" /></td>
		</tr>
		<tr>
			<th align="right">确认密码:</th>
			<td align="left"><html:password property="passEnsure" size="16"
				maxlength="18" /></td>
		</tr>
		<tr>
			<th align="right">性别:</th>
			<td align="left"><html:select property="sex">
				<html:option value="男"></html:option>
				<html:option value="女"></html:option>
			</html:select></td>
		</tr>
		<tr>
			<th align="right">E-mail:</th>
			<td align="left"><html:text property="email" size="16"
				maxlength="18" /></td>
		</tr>
		<tr>
			<th align="right">地址:</th>
			<td align="left"><html:text property="address" size="16"
				maxlength="18" /></td>
		</tr>
		<tr>
			<th align="right"><html:submit property="Submit" value="提交" />
			</th>
			<td align="left"><html:reset value="重置" /></td>
		</tr>
	</table>
</html:form>
