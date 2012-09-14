<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<SCRIPT language=JavaScript type="text/javascript">
<!--
	function PopIt(label, msg){  
        // Set up Page Colors & Table  
        var s0 = "<html><head><link href='css/color.css' rel='stylesheet' type='text/css' />"
        var s1 = "<TITLE>" + label + "</TITLE></head>" +    
        "<BODY><table width='100%' border='0' class='table' cellpadding='0' cellspacing='0'>" + 
        "<tr class='thead'><td><p><strong><img src='image/head_6.gif' width='6' height='7' />查看消息" +
        "</strong></p></td></tr>" +
        "<tr><TD VALIGN=TOP ALIGN=LEFT>" +        
        "<FONT SIZE=2>"    
        var s2 = "<FONT SIZE=3 COLOR='FF6600'><B>" + label + "</B></FONT><P>"   
        var s3 = "<p><FORM><INPUT TYPE='BUTTON' VALUE='关闭'" +                   
        "onClick='self.close()'>"  +    
        "</FORM></TD></tr></TABLE></BODY></HTML>"   
        popup = window.open("","popDialog","height=160,width=300,scrollbars=yes")  
        popup.document.write(s0+s1+s2+msg+s3)  
        popup.document.close()
}
//-->
</SCRIPT>

<table width="100%" border="0" cellpadding="0" cellspacing="0"
	class="table">
	<tr class="thead">
		<td width="30%"><strong><img src="image/head_6.gif"
			width="6" height="7" />消息标题</strong></td>
		<td width="30%"><strong><img src="image/head_6.gif"
			width="6" height="7" />发信人</strong></td>
		<td width="40%"><strong><img src="image/head_6.gif"
			width="6" height="7" />发送时间</strong></td>
	</tr>
	<c:forEach items="${msg_list}" var="currentMsg">
		<tr>
			<td><A
				href="javascript:PopIt('${currentMsg.title}','${currentMsg.content}');"><c:out
				value="${currentMsg.title}" /></A></td>
			<td><c:out value="${currentMsg.from.name}" />(<html:link
				action="writeMsg?default_receiver=${currentMsg.from.name}">回复</html:link>)</td>
			<td><c:out value="${currentMsg.sendTime}"></c:out></td>
		</tr>
	</c:forEach>
</table>
