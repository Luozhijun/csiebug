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
		<title><%=webutil.getMessage("common.NoPermission")%></title>
	</head>
	<body>
			<%@ include file="pub/common_html.jsp" %>
			
			<!-- 以下為頁面編輯區 -->
			
			
			
			<!-- 頁面編輯區結束 -->
			
			<!-- Javascript區 -->	
			<%=webutil.getImportJSFileLink() %>
			
			<script type="text/javascript">
				$(document).ready(function() {
					<% if(webutil.getRequestAttribute("Script") != null) { out.print(webutil.getRequestAttribute("Script").toString()); }%>
					<% if(webutil.getRequestAttribute("Msg") != null) { out.print(webutil.getRequestAttribute("Msg").toString()); }%>
				});
			</script>
			<!-- Javascript區結束 -->
	</body>
</html>

