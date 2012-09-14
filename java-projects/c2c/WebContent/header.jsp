<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<!--头部表格 -->
<table width="700" border="0">
	<tr>
		<td width="57">
		<div align="center"><img src="image/logo.jpg" alt="Java商城" /></div>
		</td>
		<td><img src="image/385.gif" width="468" height="60" /></td>
		<td width="65" colspan="2" valign="top">
		<p><A class=link1
			href="javascript:window.external.addFavorite('http://localhost:8080/c2c','Java商城')">加入收藏</A>
		<br />
		<a
			onclick="this.style.behavior='url(#default#homepage)';this.sethomepage('http://localhost:8080/c2c');return false;"
			href="http://localhost:8080/c2c">设为主页</a> <br />
		<a href="mailto:c_jerry@126.com">联系我们</a></p>
		</td>
	</tr>
	<tr>
		<td colspan="4" height="40">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="90">&nbsp;</td>
				<td width="80" align="center" valign="bottom" class="navBar"><html:link
					action="index">买东西</html:link></td>
				<td width="80" align="center" valign="bottom" class="navBar"><html:link
					action="listShopsAction">买东西</html:link></td>
				<td width="80" align="center" valign="bottom" class="navBar"><html:link
					action="receiveMsgAction">我的帐号</html:link></td>
				<td width="296" align="right"><span class="promo"><html:link
					action="index">首页</html:link> | <a href="#">搜索</a> | <html:link
					action="register">注册</html:link> | <html:link action="shopReg">申请开店</html:link></span></td>
			</tr>
			<tr height="24">
				<td height="20" colspan="6" align="left" bgcolor="#FF9900">
				<table width="100%" border="0">
					<tr>
						<td width="18%" align="right">宝贝搜索：</td>
						<html:form action="searchItemAction">
							<td width="26%"><html:text property="keyword" /></td>
							<td width="10%"><html:image src="image/search.jpg" /></td>
						</html:form>
						<td width="46%">热门关键字：为爱而生 | Moto E2 | XBox360 | 考研</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<!--头部表格结束 -->
