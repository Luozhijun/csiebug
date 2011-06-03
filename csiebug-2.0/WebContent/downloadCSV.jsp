<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/csiebug-ui" %>

<%@page import="csiebug.util.WebUtility"%>

<%WebUtility webutil = new WebUtility();%>

<html>
	<head>
		<%@ include file="pub/common_css.jsp" %>
		<%=webutil.getImportCSSFileLink() %>
		
		<base target="_self">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<link rel="shortcut icon" href="<%=webutil.getBasePathForHTML()%>images/favicon.ico" />
		<title><%=webutil.getRequestAttribute("FunctionName").toString()%></title>
	</head>
	<body>
		<form id="downloadCSV" name="downloadCSV" action="<%=webutil.getBasePathForHTML()%>index" method="POST" target="_self">
			<%@ include file="pub/common_html.jsp" %>
			<%@ include file="pub/common_form.jsp" %>
			
			<!-- 以下為頁面編輯區 -->
			
			<!-- 頁面編輯區結束 -->
			
			<!-- Javascript區 -->
			<%=webutil.getImportJSFileLink() %>
			
			<script type="text/javascript">
				$(document).ready(function() {
					<% if(webutil.getRequestAttribute("Script") != null) { out.print(webutil.getRequestAttribute("Script").toString()); }%>
					<% if(webutil.getRequestAttribute("Msg") != null) { out.print(webutil.getRequestAttribute("Msg").toString()); }%>
					
					document.getElementById("ActFlag").value = "downloadCSV";
					document.getElementById("DownloadFileName").value = parent.document.getElementById("DownloadFileName").value;
					document.getElementById("DownloadTableId").value = parent.document.getElementById("DownloadTableId").value;
					document.getElementById("GridHTML").value = parent.document.getElementById("GridHTML").value;
					document.getElementById("DownloadRows").value = parent.document.getElementById("DownloadRows").value;
					document.getElementById("DownloadColumns").value = replaceAll(replaceAll(parent.document.getElementById("DownloadColumns").value, "(", "var.openParenthesis"), ")", "var.closeParenthesis");
					
					document.getElementById("downloadCSV").submit();
				});
			</script>
			<!-- Javascript區結束 -->
		</form>
	</body>
</html>

