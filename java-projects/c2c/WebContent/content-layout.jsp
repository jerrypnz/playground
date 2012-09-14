<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<table width="700" border="0">
	<tr>
		<td  width="30%" align="left" valign="top"><tiles:insert attribute="content-left" />
		</td>
		<td align="left" valign="top"><tiles:insert attribute="content-right" />
		</td>
	</tr>
</table>
