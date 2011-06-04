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
		<form id="columns" name="columns" action="<%=webutil.getBasePathForHTML()%>example/columns" method="POST">
			<!-- 程式標頭 -->
			<table width="100%">
				<tr>
					<td class="PageHeader"><%=webutil.getRequestAttribute("FunctionName").toString()%></td>
				</tr>
			</table>
			<%@ include file="../../../pub/common_html.jsp" %>
			
			<!-- 以下為頁面編輯區 -->
			<h4>Dynamic Columns</h4>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('dynFields')" style="cursor:pointer">屬性dynFields: 從request attribute取出動態column的資料</legend>
				<div id="dynFields_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					<!-- pseudo code
					//準備grid資料
					ArrayList<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String,String>>();
					
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("field1", "10");
					map.put("field2", "200");
					map.put("field3", "3000");
					
					list.add(map);
					
					...
					
					setRequestAttribute("gridData", list);
					
					//設定dynamic columns資料
					//所有column的欄位都可以拿來設定，詳細請參考column的說明
					ArrayList<LinkedHashMap<String, String>> list2 = new ArrayList<LinkedHashMap<String,String>>();
					
					map = new LinkedHashMap<String, String>();
					map.put("fieldname", "field2");
					map.put("dataType", "number");
					map.put("sortable", "true");
					map.put("title", "title2");
					
					list2.add(map);
					
					map = new LinkedHashMap<String, String>();
					map.put("fieldname", "field3");
					map.put("dataType", "currency");
					map.put("title", "title3");
					
					list2.add(map);
					
					setRequestAttribute("columns", list2);
					-->
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table id="table1" name="table1" data="gridData">
						<row rowType="header">
							<column title="title1" colspan="2"/>
							<column title="title2" colspan="1"/>
						</row>
						<row>
							<column fieldname="field1"/>
							<column fieldname="field2"/>
							<columns dynFields="columns"/>
							<column fieldname="field3"/>
						</row>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:table id="table1" name="table1" data="gridData">
						<c:row>
							<c:column fieldname="field1"/>
							<c:column fieldname="field2"/>
							<c:columns dynFields="columns"/>
							<c:column fieldname="field3"/>
						</c:row>
					</c:table>
					
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
			<script src="<%=webutil.getBasePathForHTML()%>js/csiebug-grid/csiebug-grid.js"></script>
			
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

