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
		<form id="signIn" name="signIn" action="<%=webutil.getBasePathForHTML()%>signIn" method="POST">
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
				<c:text id="j_password" name="j_password" header='<%=webutil.getMessage("common.password")%>' maxlength="70" isRequired="true" dataType="password" />
			</tr>
			<tr>
				<c:text id="confirm_password" name="confirm_password" header='<%=webutil.getMessage("common.confirmPassword")%>' maxlength="70" isRequired="true" dataType="password" />
			</tr>
			<tr>
				<c:text id="pictureContent" name="pictureContent" header='<%=webutil.getMessage("common.PictureContent") %>' fixlength="6" isRequired="true"></c:text>
				<td>
					<img src="<%=webutil.getBasePathForHTML()%>signIn?ActFlag=authenticationImage" border="1">
				</td>
			</tr>
			<tr>
				<td>
					<input id="loginButton" name="loginButton" type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="login('signIn', this, '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>', '<%=webutil.getMessage("common.AuthenticationFailure") %>');" value="<%=webutil.getMessage("common.ok")%>">
				</td>
			</tr>

			</table>
			<!-- 頁面編輯區結束 -->
			
		</form>
		
		<!-- Javascript區 -->
		<%=webutil.getImportJSFileLink() %>
		
		<script src="<%=webutil.getBasePathForHTML()%>signIn.js"></script>
		
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
