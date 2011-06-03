<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/csiebug-ui" %>
<%@ taglib prefix="upload" uri="/csiebug-uploadify" %>

<%@page import="csiebug.util.WebUtility"%>

<%WebUtility webutil = new WebUtility();%>

<!-- 若是portlet則不需要html,head和body的部分 -->

<%@page import="csiebug.web.html.form.HtmlEditableLabel"%><html>
	<head>
		<%@ include file="../pub/common_css.jsp" %>
		<%=webutil.getImportCSSFileLink() %>
		<link rel="stylesheet" type="text/css" href="/csiebug-2.0/js/jqplot.0.9.7/jquery.jqplot.css" />
		<link rel="stylesheet" type="text/css" href="/csiebug-2.0/js/jquery.uploadify-v2.1.4/uploadify.css" />
		
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<link rel="shortcut icon" href="<%=webutil.getBasePathForHTML()%>images/favicon.ico" />
		<title><%=webutil.getRequestAttribute("FunctionName").toString()%></title>
	</head>
	<body>
		<form id="test" name="test" action="<%=webutil.getBasePathForHTML()%>example/test" method="POST">
			<!-- 程式標頭 -->
			<table width="100%">
				<tr>
					<td class="PageHeader"><%=webutil.getRequestAttribute("FunctionName").toString()%></td>
				</tr>
			</table>
			<%@ include file="../pub/common_html.jsp" %>
			<%@ include file="../pub/common_form.jsp" %>
			
			<!-- 以下為頁面編輯區 -->
			<!-- 登出 -->
			<a href="<%=webutil.getBasePathForHTML()%>j_spring_security_logout">登出</a>
			
			<br></br>
			
			<upload:uploadify uploadId="testUpload" queueID="testQueue"/>
			
			<!-- 頁面編輯區結束 -->
			
			<!-- Javascript區 -->
			<!-- 若是portlet則不需要import共用的js -->
			<%=webutil.getImportJSFileLink() %>
			
			<!-- 若是portlet則不需要import共用的js -->
			<!-- 額外import區 -->
			
			<!-- 額外import區 -->
			<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/swfobject/2.2/swfobject.js"></script>
			<script type="text/javascript" src="/csiebug-2.0/js/jquery.uploadify-v2.1.4/jquery.uploadify.v2.1.4.min.js"></script>
			
			
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

