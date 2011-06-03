<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/csiebug-ui" %>

<%@page import="csiebug.util.WebUtility"%>

<%WebUtility webutil = new WebUtility();%>

<html>
	<head>
		<%@ include file="pub/common_css.jsp" %>
		<%=webutil.getImportCSSFileLink() %>
		
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<link rel="shortcut icon" href="<%=webutil.getBasePathForHTML()%>images/favicon.ico" />
		<title><%=webutil.getRequestAttribute("FunctionName").toString()%></title>
	</head>
	<body>
		<form id="index" name="index" action="<%=webutil.getBasePathForHTML()%>j_spring_security_check" method="POST">
			<!-- 程式標頭 -->
			<table width="100%">
				<tr>
					<td class="PageHeader"><%=webutil.getRequestAttribute("FunctionName").toString()%></td>
				</tr>
			</table>
			
			<%@ include file="pub/common_html.jsp" %>
			<%@ include file="pub/common_form.jsp" %>
			
			<!-- 以下為頁面編輯區 -->
			<table id="loginTable">
			<tr>
				<c:text id="j_username" name="j_username" header='<%=webutil.getMessage("common.account")%>' maxlength="50" isRequired="true"/>
			</tr>
			<tr>
				<c:text id="j_password" name="j_password" header='<%=webutil.getMessage("common.password")%>' maxlength="70" isRequired="true" dataType="password" onKeyDown='<%=webutil.getRequestAttribute("enterSubmitScript").toString() %>'/>
			</tr>
			<tr>
				<td>
					<input id="loginButton" name="loginButton" type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="login('index', this, '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');" value="<%=webutil.getMessage("common.ok")%>">
				</td>
				<td>
					<c:checkbox id="j_rememberme" name="j_rememberme" displayName="RememberMe" value="_spring_security_remember_me" typesetting="false" />
				</td>
			</tr>
			<tr>
				<td><a href="signIn">[sign in]</a></td>
				<td></td>
			</tr>
			</table>
			<!-- 頁面編輯區結束 -->
			
		</form>
		
		<!-- Javascript區 -->
		<%=webutil.getImportJSFileLink() %>
		
		<script src="<%=webutil.getBasePathForHTML()%>login.js"></script>
		
		<script type="text/javascript">
			$(document).ready(function() {
				<% if(webutil.getRequestAttribute("Script") != null) { out.print(webutil.getRequestAttribute("Script").toString()); }%>
				<% if(webutil.getRequestAttribute("Msg") != null) { out.print(webutil.getRequestAttribute("Msg").toString()); }%>

				document.getElementById("j_username").focus();
			});
			
			//---禁回此頁 Begin--- 
			function forward() { 
			    setTimeout("forward()",100); 
			    history.forward(); 
			} 
			if (document.all) 
			    history.forward();  //for ie 
			else     
			    setTimeout("forward()",100);  //for firefox 
			//---禁回此頁 End---
		</script>
		<!-- Javascript區結束 -->
	</body>
</html>
