<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/csiebug-ui" %>

<%@page import="csiebug.util.WebUtility"%>

<%WebUtility webutil = new WebUtility();%>

<!-- 若是portlet則不需要html,head和body的部分 -->
<html>
	<head>
		<%@ include file="pub/common_css.jsp" %>
		<%=webutil.getImportCSSFileLink() %>
		
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<link rel="shortcut icon" href="<%=webutil.getBasePathForHTML()%>images/favicon.ico" />
		<title><%=webutil.getRequestAttribute("FunctionName").toString()%></title>
	</head>
	<body>
		<form id="template" name="template" action="<%=webutil.getBasePathForHTML()%>template" method="POST">
			<!-- 程式標頭 -->
			<table width="100%">
				<tr>
					<td class="PageHeader"><%=webutil.getRequestAttribute("FunctionName").toString()%></td>
				</tr>
			</table>
			<%@ include file="pub/common_html.jsp" %>
			<%@ include file="pub/common_form.jsp" %>
			
			<!-- 以下為頁面編輯區 -->
			
			<!-- 頁面編輯區結束 -->
			
			<!-- Javascript區 -->
			<!-- 若是portlet則不需要import共用的js -->
			<%=webutil.getImportJSFileLink() %>
			<!-- 若是portlet則不需要import共用的js -->
			
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

