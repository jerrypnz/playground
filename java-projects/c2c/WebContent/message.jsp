<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><c:out value="${title}"></c:out></title>
<link href="css/color.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.dottedLine {
	border-top-style: none;
	border-right-style: none;
	border-bottom-style: dotted;
	border-left-style: none;
	border-bottom-color: #FF9900;
	border-top-width: thin;
	border-right-width: thin;
	border-bottom-width: thin;
	border-left-width: thin;
}
-->
</style>
</head>

<body>
<table width="700" border="0" align="center">
	<tr>
		<td><!--头部表格 -->
		<table width="700" border="0">
			<tr>
				<td width="57">
				<div align="center"><img src="image/logo.jpg" alt="Java商城" /></div>
				</td>
				<td><img src="image/385.gif" width="468" height="60" /></td>
				<td width="65" colspan="2" valign="top">
				<p><a class=link1
					href="javascript:window.external.addFavorite('http://localhost:8080/c2c','Java商城')">加入收藏</A>
				<br />
				<a
					onclick="this.style.behavior='url(#default#homepage)';this.sethomepage('http://localhost:8080/c2c');return false;"
					href="http://localhost:8080/c2c">设为主页</a> <br />
				<a href="mailto:c_jerry@126.com">联系我们</a></p>
				</td>
			</tr>
			<tr>
				<td colspan="4">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="90">&nbsp;</td>
						<td width="80" align="center" valign="bottom" class="navBar"><a
							href="#">买东西</a></td>
						<td width="80" align="center" valign="bottom" class="navBar"><a
							href="#">卖东西</a></td>
						<td width="80" align="center" valign="bottom" class="navBar"><a
							href="#">店铺</a></td>
						<td width="80" align="center" valign="bottom" class="navBar"><a
							href="#">我的帐号</a></td>
						<td width="296" align="right"><span class="promo"><html:link
							action="index">首页</html:link> | <a href="#">搜索</a> | <html:link
							action="register">注册</html:link> | <html:link action="shopReg">申请开店</html:link></span></td>
					</tr>
					<tr>
						<td height="20" colspan="6" align="left" bgcolor="#FF9900">
						<table width="100%" border="0">
							<tr>
								<td width="18%" align="right">宝贝搜索：</td>
								<td width="26%"><input type="text" name="textfield3" /></td>
								<td width="10%"><input type="image" name="imageField"
									src="image/search.jpg" /></td>
								<td width="46%">热门关键字：为爱而生 | Moto E2 | XBox360 | 考研</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		<!--头部表格结束 --></td>
	</tr>

	<tr>
		<td>
		<table width="100%" border="0" class="table">
			<tr class="thead">
				<td>
				<p><strong><img src="image/head_6.gif" width="6"
					height="7" /><c:out value="${message_title}"></c:out></strong></p>
				</td>
			</tr>
			<tr>
				<td><c:out value="${message_content}"></c:out></td>
			</tr>
			<tr>
				<td><html:errors /></td>
			</tr>
		</table>
		</td>
	</tr>

	<tr>
		<td>
		<div align="center">
		<hr align="center" width="600" noshade="noshade" color="#666666" />
		<p class="small">首页 | 浏览商品 | 浏览商店 | 搜索 | 消息中心 | 登陆 | 注册 | 申请开店 |
		我的空间 <br />
		Copyright © 2007 JStudio</p>
		</div>

		</td>
	</tr>
</table>
</body>
</html:html>
