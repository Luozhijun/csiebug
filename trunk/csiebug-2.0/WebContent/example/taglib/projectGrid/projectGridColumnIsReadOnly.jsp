<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/csiebug-ui" %>
<%@ taglib prefix="project" uri="/csiebug-project" %>

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
		
		<form id="projectGrid" name="projectGrid" action="<%=webutil.getBasePathForHTML()%>example/projectGrid" method="POST">
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
		    <table id="mpp" data="grid" projectId="templateProject" formId="projectGrid" isReadOnly="true" downloadColumns="4,5,6,7,8,9,10,11,12,13">
				<row title="(var.name)" >
					<column fieldname="start" title="開始時間(start)預計開始日" dataType="date"  isReadOnly="true"/>
					<column fieldname="finish" title="完成時間(finish)預計結束日"  dataType="date"/>
					<column fieldname="number1" title="pm" dataType="number" />
					<column fieldname="number2" title="sa" dataType="number" />
					<column fieldname="number3" title="pa" dataType="number" />
					<column fieldname="number4" title="swd" dataType="number" />
					<column fieldname="number5" title="pgr" dataType="number" />
					<column fieldname="number6" title="ase" dataType="number" />
					<column fieldname="number7" title="other" dataType="number" />
				</row>
			</table>
		    </c:code>
		    
		    <h5>頁面結果:</h5>
		    
			<project:table id="mpp" data="grid" projectId="templateProject" formId="projectGrid" downloadColumns="4,5,6,7,8,9,10,11,12,13">
				<project:row title="(var.name)" >
					<project:column fieldname="start" title="開始時間(start)預計開始日" dataType="date" isReadOnly="true"/>
					<project:column fieldname="finish" title="完成時間(finish)預計結束日"  dataType="date"/>
					<project:column fieldname="number1" title="pm" dataType="number" />
					<project:column fieldname="number2" title="sa" dataType="number" />
					<project:column fieldname="number3" title="pa" dataType="number" />
					<project:column fieldname="number4" title="swd" dataType="number" />
					<project:column fieldname="number5" title="pgr" dataType="number" />
					<project:column fieldname="number6" title="ase" dataType="number" />
					<project:column fieldname="number7" title="other" dataType="number" />
				</project:row>
			</project:table>
			
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
			<!-- <script src="<%=webutil.getBasePathForHTML()%>js/csiebug-project/csiebug-project.js"></script> -->
			<script src="<%=webutil.getBasePathForHTML()%>js/csiebug-project/csiebug-project-jquery.js"></script>
			<script src="<%=webutil.getBasePathForHTML()%>example/taglib/projectGrid/projectGrid.js"></script>
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

