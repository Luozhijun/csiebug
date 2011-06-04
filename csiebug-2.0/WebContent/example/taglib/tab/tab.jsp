<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/csiebug-ui" %>

<%@page import="csiebug.util.WebUtility"%>

<%WebUtility webutil = new WebUtility();%>


<html>
	<head>
		<%@ include file="../../../css/tab/tab.css" %>
		<%@ include file="../../../pub/common_css.jsp" %>
		<%=webutil.getImportCSSFileLink() %>
		
		<link rel="shortcut icon" href="<%=webutil.getBasePathForHTML()%>images/favicon.ico" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title><%=webutil.getRequestAttribute("FunctionName").toString()%></title>
	</head>
	<body>
		<form id="tab" name="tab" action="<%=webutil.getBasePathForHTML()%>example/tab" method="POST">
			<!-- 程式標頭 -->
			<table width="100%">
				<tr>
					<td class="PageHeader"><%=webutil.getRequestAttribute("FunctionName").toString()%></td>
				</tr>
			</table>
			<%@ include file="../../../pub/common_html.jsp" %>
			
			<!-- 以下為頁面編輯區 -->
			<h4>Tab</h4>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('tabs')" style="cursor:pointer">屬性tabs: 從request attribute中取出tab資料</legend>
				<div id="tabs_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					LinkedHashMap<String, String> tabMap = new LinkedHashMap<String, String>();
					tabMap.put("Text", "text");
					tabMap.put("Menu", "menu");
					tabMap.put("Code", "code");
					
					setRequestAttribute("tabMap1", tabMap);
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<tab id="tab1" name="tab1" tabs="tabMap1"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:tab id="tab1" name="tab1" tabs="tabMap1" op="div" />
					<div id="textDIV">
						text
					</div>
					
					<div id="menuDIV" style="display:none">
						menu
					</div>
					
					<div id="codeDIV" style="display:none">
						code
					</div>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('defaultTab')" style="cursor:pointer">屬性defaultTab: 設定form load預設展示的tab</legend>
				<div id="defaultTab_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					LinkedHashMap<String, String> tabMap = new LinkedHashMap<String, String>();
					tabMap.put("Text", "example/text");
					tabMap.put("Menu", "example/menu");
					tabMap.put("Code", "example/code");
					
					setRequestAttribute("tabMap2", tabMap);
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<tab id="tab2" name="tab2" tabs="tabMap2" defaultTab="Menu"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:tab id="tab2" name="tab2" tabs="tabMap2" defaultTab="Menu"/>
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
			<script src="<%=webutil.getBasePathForHTML()%>js/csiebug-tab/csiebug-tab.js"></script>
			
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

