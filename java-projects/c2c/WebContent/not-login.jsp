<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<table width="100%" border="0" class="table">
	<tr class="thead">
		<td>
		<p><strong><img src="image/head_6.gif" width="6"
			height="7" />请先登录</strong></p>
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
