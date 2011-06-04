<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/csiebug-ui" %>

<%@page import="csiebug.util.WebUtility"%>

<%WebUtility webutil = new WebUtility();%>


<html>
	<head>
		<%@ include file="../../../pub/common_css.jsp" %>
		<%=webutil.getImportCSSFileLink() %>
		
		<link rel="shortcut icon" href="<%=webutil.getBasePathForHTML()%>images/favicon.ico" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title><%=webutil.getRequestAttribute("FunctionName").toString()%></title>
	</head>
	<body>
		<form id="progressBar" name="progressBar" action="<%=webutil.getBasePathForHTML()%>example/progressBar" method="POST">
			<!-- 程式標頭 -->
			<table width="100%">
				<tr>
					<td class="PageHeader"><%=webutil.getRequestAttribute("FunctionName").toString()%></td>
				</tr>
			</table>
			<%@ include file="../../../pub/common_html.jsp" %>
			
			<!-- 以下為頁面編輯區 -->
			<h4>ProgressBar</h4>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('uncomplete')" style="cursor:pointer">屬性uncomplete: 已完成百分比</legend>
				<div id="uncomplete_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<progressbar uncomplete="30"/><br>
					<progressbar uncomplete="70"/><br>
					<progressbar uncomplete="100"/><br>
					<progressbar uncomplete="120"/><br>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:progressbar uncomplete="30"/><br>
					<c:progressbar uncomplete="70"/><br>
					<c:progressbar uncomplete="100"/><br>
					<c:progressbar uncomplete="120"/><br>
					
				</div>
			</fieldset>
			
			<br>
			<br>
			<br>
			<br>
			<br>
			
			<!-- 頁面編輯區結束 -->
			
			<!-- Javascript區 -->
			<%=webutil.getImportJSFileLink() %>
			<script src="<%=webutil.getBasePathForHTML()%>js/csiebug-treeview/csiebug-sidebar.js"></script>
			
			<script type="text/javascript">
				$(document).ready(function() {
					<% if(webutil.getRequestAttribute("Script") != null) { out.print(webutil.getRequestAttribute("Script").toString()); }%>
					<% if(webutil.getRequestAttribute("Msg") != null) { out.print(webutil.getRequestAttribute("Msg").toString()); }%>
				});
			</script>
			<!-- Javascript區結束 -->
			
		</form>
	</body>
</html>

