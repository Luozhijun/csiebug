<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/csiebug-ui" %>

<%@page import="csiebug.util.WebUtility"%>

<%WebUtility webutil = new WebUtility();%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<link rel="shortcut icon" href="<%=webutil.getBasePathForHTML()%>images/favicon.ico" />
		<title>Access Denied!</title>
	</head>
	<body>
			<!-- 以下為頁面編輯區 -->
			
			
			
			<!-- 頁面編輯區結束 -->
			
			<!-- Javascript區 -->
			<script type="text/javascript" src="js/jquery-1.3.2.js"></script>
		
			<script type="text/javascript">
				$(document).ready(function() {
					parent.parent.parent.location.href = "<%=webutil.getBasePathForHTML()%>index?ActFlag=nopermission";
				});
			</script>
			<!-- Javascript區結束 -->
	</body>
</html>

