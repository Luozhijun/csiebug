<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/csiebug-ui" %>
<%@ taglib prefix="excel" uri="/csiebug-excel" %>

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
		<%@ include file="../../../pub/common_html.jsp" %>
		
		<form id="excelGrid" name="excelGrid" action="<%=webutil.getBasePathForHTML()%>example/excelGrid" method="POST">
			<!-- 程式標頭 -->
			<table width="100%">
				<tr>
					<td class="PageHeader"><%=webutil.getRequestAttribute("FunctionName").toString()%></td>
				</tr>
			</table>
			<%@ include file="../../../pub/common_form.jsp" %>
			
			<!-- 以下為頁面編輯區 -->
			<!-- 
			<div class="contextMenu" id="myMenu1">
		      <ul>
		
		        <li id="copy"><img id = "mpp_copyButton" align = "absmiddle" src = "/csiebug-2.0//images/copy-icon.png" title = "複製"></img></li>
		
		      </ul>
		
		    </div>
		    -->
		    
		    <h5>JSP 程式碼:</h5>
		    
		    <c:code>
		    <table id="test" data="gridData" projectId="templateProject" formId="excelGrid" downloadFileName="testExcel" downloadColumns="3,4,5">
				<row >
					<column fieldname="field1" />
					<column fieldname="field2" />
					<column fieldname="field3" />
				</row>
			</table>
		    </c:code>
		    
		    <h5>頁面結果:</h5>
		    
			<excel:table id="test" data="gridData" projectId="templateProject" formId="excelGrid" downloadFileName="testExcel" downloadColumns="3,4,5">
				<excel:row >
					<excel:column fieldname="field1" />
					<excel:column fieldname="field2" />
					<excel:column fieldname="field3" />
				</excel:row>
			</excel:table>
			
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
			<script src="<%=webutil.getBasePathForHTML()%>js/csiebug-form/csiebug-editableLabel.js"></script>
			<script src="<%=webutil.getBasePathForHTML()%>js/csiebug-excel/csiebug-excel-jquery.js"></script>
			<script src="<%=webutil.getBasePathForHTML()%>example/taglib/excelGrid/excelGrid.js"></script>
			<!-- <script src="<%=webutil.getBasePathForHTML()%>js/jquery-contextmenu/jquery.contextmenu.r2.packed.js"></script>-->
			
			<script type="text/javascript">
				$(document).ready(function() {
					<% if(webutil.getRequestAttribute("Script") != null) { out.print(webutil.getRequestAttribute("Script").toString()); }%>
					<% if(webutil.getRequestAttribute("Msg") != null) { out.print(webutil.getRequestAttribute("Msg").toString()); }%>
/*
					$('div[id$="_textLabel"]').contextMenu('myMenu1', {
					      bindings: {

					        'copy': function(t) {

					          //alert('Trigger was '+t.id+'\nAction was Open');
					        	copyForProject('mpp', 1);

					        },

					      }

					    });*/
				});
			</script>
			<!-- Javascript區結束 -->
			
		</form>
	</body>
</html>

