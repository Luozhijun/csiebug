<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/csiebug-ui" %>

<%@page import="csiebug.util.WebUtility"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.LinkedHashMap"%>

<%WebUtility webutil = new WebUtility();%>

<html>
	<head>
		<%@ include file="../pub/common_css.jsp" %>
		<%=webutil.getImportCSSFileLink() %>
		
		<link rel="shortcut icon" href="<%=webutil.getBasePathForHTML()%>images/favicon.ico" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title><%=webutil.getRequestAttribute("FunctionName").toString()%></title>
	</head>
	<body>
		<form id="taglibDemoList" name="taglibDemoList" action="<%=webutil.getBasePathForHTML()%>example/taglibDemoList" method="POST">
			<!-- 程式標頭 -->
			<table width="100%">
				<tr>
					<td class="PageHeader"><%=webutil.getRequestAttribute("FunctionName").toString()%></td>
				</tr>
			</table>
			<%@ include file="../pub/common_html.jsp" %>
			
			<!-- 以下為頁面編輯區 -->
			
			<table width="90%" height="90%">
				<tr>
					<td valign="top" width="20%" height="100%">
						<c:tree list="exampleDemo"/>
					</td>
					<td valign="top" width="80%" height="100%">
						<iframe id="mainFrame" name="mainFrame" width="100%" height="100%"></iframe>
					</td>
				</tr>
			</table>
			
			<!-- 頁面編輯區結束 -->
			
			<!-- Javascript區 -->
			<%=webutil.getImportJSFileLink() %>
			<script src="<%=webutil.getBasePathForHTML()%>js/csiebug-treeview/csiebug-treeview.js"></script>
			
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

