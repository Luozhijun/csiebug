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
		<form id="code" name="code" action="<%=webutil.getBasePathForHTML()%>example/code" method="POST">
			<!-- 程式標頭 -->
			<table width="100%">
				<tr>
					<td class="PageHeader"><%=webutil.getRequestAttribute("FunctionName").toString()%></td>
				</tr>
			</table>
			<%@ include file="../../../pub/common_html.jsp" %>
			<%@ include file="../../../pub/common_form.jsp" %>
			
			<!-- 以下為頁面編輯區 -->
			
			<h4>將程式碼輸出加上行號，以方便展示使用</h4>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('code')" style="cursor:pointer">預設(預設tab長度等於4個空白字元)</legend>
				<div id="code_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
						<code>
							//這是測試單行註解
							/*
							這是測試多行註解
							*/
							System.out.print("Hello");
							System.out.print(" ");
							if(UserId.equals("George_Tsai")) {
								System.out.print("admin");
							} else {
								System.out.print("guest");
							}
						</code>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:code>
						//這是測試單行註解
						/*
						這是測試多行註解
						*/
						System.out.print("Hello");
						System.out.print(" ");
						if(UserId.equals("George_Tsai")) {
							System.out.print("admin");
						} else {
							System.out.print("guest");
						}
					</c:code>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('tabLength')" style="cursor:pointer">屬性tabLength: 將tab換為指定的空白數目</legend>
				<div id="tabLength_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
						<code tabLength="6">
							//這是測試單行註解
							/*
							這是測試多行註解
							*/
							System.out.print("Hello");
							System.out.print(" ");
							if(UserId.equals("George_Tsai")) {
								System.out.print("admin");
							} else {
								System.out.print("guest");
							}
						</code>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:code tabLength="6">
						//這是測試單行註解
						/*
						這是測試多行註解
						*/
						System.out.print("Hello");
						System.out.print(" ");
						if(UserId.equals("George_Tsai")) {
							System.out.print("admin");
						} else {
							System.out.print("guest");
						}
					</c:code>
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

