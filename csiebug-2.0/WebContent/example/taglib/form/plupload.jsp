<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/csiebug-ui" %>
<%@ taglib prefix="upload" uri="/csiebug-plupload" %>

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
		<form id="plupload" name="plupload" action="<%=webutil.getBasePathForHTML()%>example/plupload" method="POST">
			<!-- 程式標頭 -->
			<table width="100%">
				<tr>
					<td class="PageHeader"><%=webutil.getRequestAttribute("FunctionName").toString()%></td>
				</tr>
			</table>
			<%@ include file="../../../pub/common_html.jsp" %>
			<%@ include file="../../../pub/common_form.jsp" %>
			
			<!-- 以下為頁面編輯區 -->
			
			<h4>PlUpload</h4>
			
			欲引用PlUpload時，除了jquery以外還須引用下列js檔:<br>
			<c:code>
				<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/swfobject/2.2/swfobject.js"></script>
				<script type="text/javascript" src="/csiebug-2.0/js/jquery.uploadify-v2.1.4/jquery.uploadify.v2.1.4.min.js"></script>
			</c:code>
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('uploadify')" style="cursor:pointer">預設: cancelImg,checkScript,script,uploader都帶預設值</legend>
				<div id="uploadify_sidebar" style="display:none"> -->
					<h4>預設: cancelImg,checkScript,script,uploader都帶預設值</h4>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<plupload uploadId="uploadify1"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<upload:plupload uploadId="uploadify1" />
			<!-- 	</div>
			</fieldset> -->
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('buttonImg')">屬性buttonImg: 設定選取檔案按鈕的圖檔</legend>
				<div id="buttonImg_sidebar" style="display:none">
				</div>
			</fieldset> -->
				<h4>屬性buttonImg: 設定選取檔案按鈕的圖檔</h4>
			
			<br>
			<br>
			<br>
			<br>
			<br>
			
			<!-- 頁面編輯區結束 -->
			
			<!-- Javascript區 -->
			<%=webutil.getImportJSFileLink() %>
			<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/swfobject/2.2/swfobject.js"></script>
			<script type="text/javascript" src="<%=webutil.getBasePathForHTML()%>js/jquery.uploadify-v2.1.4/jquery.uploadify.v2.1.4.min.js"></script>
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

