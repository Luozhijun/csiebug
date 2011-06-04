<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/csiebug-ui" %>
<%@ taglib prefix="cc" uri="/csiebug-jqPlotChart" %>

<%@page import="csiebug.util.WebUtility"%>

<%WebUtility webutil = new WebUtility();%>


<html>
	<head>
		<%@ include file="../../../pub/common_css.jsp" %>
		<%=webutil.getImportCSSFileLink() %>
		<link rel="stylesheet" type="text/css" href="<%=webutil.getBasePathForHTML()%>js/jqplot.0.9.7/jquery.jqplot.min.css" />
		
		<link rel="shortcut icon" href="<%=webutil.getBasePathForHTML()%>images/favicon.ico" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title><%=webutil.getRequestAttribute("FunctionName").toString()%></title>
	</head>
	<body>
		<form id="jqPlotChart" name="jqPlotChart" action="<%=webutil.getBasePathForHTML()%>example/jqPlotChart" method="POST">
			<!-- 程式標頭 -->
			<table width="100%">
				<tr>
					<td class="PageHeader"><%=webutil.getRequestAttribute("FunctionName").toString()%></td>
				</tr>
			</table>
			<%@ include file="../../../pub/common_html.jsp" %>
			
			<!-- 以下為頁面編輯區 -->
			<h4>JQPlotChart</h4>
			
			須引用以下js檔:<br>
			<c:code>
				<script language="javascript" type="text/javascript" src="<%=webutil.getBasePathForHTML()%>js/jqplot.0.9.7/jquery.jqplot.min.js"></script>
			</c:code>
			
			可選用jqplot.0.9.7/plugins底下的js檔與以下這個css檔:<br>
			<c:code>
				<link rel="stylesheet" type="text/css" href="<%=webutil.getBasePathForHTML()%>js/jqplot.0.9.7/jquery.jqplot.min.css" />
			</c:code>
			
			<br>
			
			<hr>
			
			<a href="jqPlotChart?ActFlag=basic">Basic chart example</a> <br>
			<a href="jqPlotChart?ActFlag=axisAutoScale">Axis auto scale example</a> <br>
			<a href="jqPlotChart?ActFlag=axisLabel">Axis label example</a> <br>
			<a href="jqPlotChart?ActFlag=pie">Pie chart example</a> <br>
			<a href="jqPlotChart?ActFlag=logAxes">Logarithmic axes example</a> <br>
			<a href="jqPlotChart?ActFlag=dateAxes">Date axes example</a> <br>
			<a href="jqPlotChart?ActFlag=dataPointHighlight">Data point highlight example</a> <br>
			<a href="jqPlotChart?ActFlag=candlestick">Open Hi Low Close and Candlestick Charts example</a> <br>
			<a href="jqPlotChart?ActFlag=zooming">Zooming example</a> <br>
			<a href="jqPlotChart?ActFlag=zoomingByRemoteControl">Zooming by remote control example</a><br>
			<a href="jqPlotChart?ActFlag=cursorLineDataTracking">Cursor line and data tracking example</a><br>
			<a href="jqPlotChart?ActFlag=bar">Bar chart example</a><br>
			<a href="jqPlotChart?ActFlag=fillToZero">Fill to zero example</a><br>
			<a href="jqPlotChart?ActFlag=dragableAndTrendLine">Dragable and trend line example</a><br>
			<a href="jqPlotChart?ActFlag=stacked">Stacked example</a><br>
			<a href="jqPlotChart?ActFlag=dataPointLabel">Data point label example</a><br>
			<a href="jqPlotChart?ActFlag=rotatedAxisText">Rotated axis text example</a><br>
			
			<br>
			<br>
			<br>
			<br>
			<br>
			
			<!-- 頁面編輯區結束 -->
			
			<!-- Javascript區 -->
			<%=webutil.getImportJSFileLink() %>
			<script language="javascript" type="text/javascript" src="<%=webutil.getBasePathForHTML()%>js/jqplot.0.9.7/jquery.jqplot.min.js"></script>
			<%=webutil.getAllJSFileLink("js/jqplot.0.9.7/plugins", false) %>
			
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

