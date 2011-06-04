<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/csiebug-ui" %>
<%@ taglib prefix="cmb" uri="/csiebug-movingBox" %>

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
		<form id="movingBox" name="movingBox" action="<%=webutil.getBasePathForHTML()%>example/movingBox" method="POST">
			<!-- 程式標頭 -->
			<table width="100%">
				<tr>
					<td class="PageHeader"><%=webutil.getRequestAttribute("FunctionName").toString()%></td>
				</tr>
			</table>
			<%@ include file="../../../pub/common_html.jsp" %>
			
			<!-- 以下為頁面編輯區 -->
			<h4>MovingBox</h4>
			
			欲引用MovingBox時，須引用此js檔:<br>
			<c:code>
				<script src="<%=webutil.getBasePathForHTML()%>js/movingBox/slider.js" type="text/javascript"></script>
			</c:code>
			<br>
			
			<h5>JAVA 程式碼:</h5>
			
			<c:code>
			ArrayList<Map<String, String>> list = new ArrayList<Map<String,String>>();
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("imageURL", "http://css-tricks.com/examples/MovingBoxes/images/1.jpg");
			map.put("title", "test title");
			map.put("description", "fdasfas;j;fas");
			list.add(map);
			
			...
			
			setRequestAttribute("testMovingBoxData", list);
			</c:code>
			
			<hr>
			
			<h5>JSP 程式碼:</h5>
			
			<c:code>
			<movingBox movingBoxId="testMovingBox" list="testMovingBoxData" />
			</c:code>
			
			<hr>
			
			<h5>頁面結果:</h5>
			<cmb:movingBox movingBoxId="testMovingBox" list="testMovingBoxData" />
			
			<br>
			<br>
			<br>
			<br>
			<br>
			
			<!-- 頁面編輯區結束 -->
			
			<!-- Javascript區 -->
			<%=webutil.getImportJSFileLink() %>
			
			<script src="<%=webutil.getBasePathForHTML()%>js/movingBox/slider.js" type="text/javascript"></script>
			
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

