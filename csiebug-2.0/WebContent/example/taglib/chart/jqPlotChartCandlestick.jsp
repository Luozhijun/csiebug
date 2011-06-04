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
				<script language="javascript" type="text/javascript" src="<%=webutil.getBasePathForHTML()%>js/jqplot.0.9.7/plugins/jqplot.ohlcRenderer.min.js"></script>
				<script language="javascript" type="text/javascript" src="<%=webutil.getBasePathForHTML()%>js/jqplot.0.9.7/plugins/jqplot.dateAxisRenderer.min.js"></script>
				<script language="javascript" type="text/javascript" src="<%=webutil.getBasePathForHTML()%>js/jqplot.0.9.7/plugins/jqplot.highlighter.min.js"></script>
			</c:code>
			
			可選用以下這個css檔:<br>
			<c:code>
				<link rel="stylesheet" type="text/css" href="<%=webutil.getBasePathForHTML()%>js/jqplot.0.9.7/jquery.jqplot.min.css" />
			</c:code>
			
			<br>
			
			<hr>
			
			<h4>Open Hi Low Close and Candlestick Charts example</h4>
			
			<h5>JSP 程式碼:</h5>
			
			<c:code>
			<JQPlotChart chartId="chart1" chartTitleText="Apple Stock Price" list="lineData9" chartSeriesList="seriesList9" chartXaxisRenderer="$.jqplot.DateAxisRenderer" chartXTickOptionsFormatString="%Y.%m" chartYTickOptionsFormatString="$%.2f" chartHighlightShowMarker="false" chartHighlightYvalues="4" />
			<JQPlotChart chartId="chart2" chartTitleText="Apple Stock Price" list="lineData9" chartSeriesList="seriesList10" chartXaxisRenderer="$.jqplot.DateAxisRenderer" chartXTickOptionsFormatString="%Y.%m" chartYTickOptionsFormatString="$%.2f" chartHighlightShowMarker="false" chartHighlightTooltipAxes="y" chartHighlightTooltipLocation="nw" chartHighlightYvalues="4" chartHighlightFormatString="<table class=jqplot-highlighter><tr><td>open:</td><td>%s</td></tr><tr><td>hi:</td><td>%s</td></tr><tr><td>low:</td><td>%s</td></tr><tr><td>close:</td><td>%s</td></tr></table>" />
			</c:code>
			
			<hr>
			
			<h5>頁面結果:</h5>
			
			<cc:JQPlotChart chartId="chart1" chartTitleText="Apple Stock Price" list="lineData9" chartSeriesList="seriesList9" chartXaxisRenderer="$.jqplot.DateAxisRenderer" chartXTickOptionsFormatString="%Y.%m" chartYTickOptionsFormatString="$%.2f" chartHighlightShowMarker="false" chartHighlightYvalues="4" />
			<cc:JQPlotChart chartId="chart2" chartTitleText="Apple Stock Price" list="lineData9" chartSeriesList="seriesList10" chartXaxisRenderer="$.jqplot.DateAxisRenderer" chartXTickOptionsFormatString="%Y.%m" chartYTickOptionsFormatString="$%.2f" chartHighlightShowMarker="false" chartHighlightTooltipAxes="y" chartHighlightTooltipLocation="nw" chartHighlightYvalues="4" chartHighlightFormatString="<table class=jqplot-highlighter><tr><td>open:</td><td>%s</td></tr><tr><td>hi:</td><td>%s</td></tr><tr><td>low:</td><td>%s</td></tr><tr><td>close:</td><td>%s</td></tr></table>" />
			
			<hr>
			
			<br>
			<br>
			<br>
			<br>
			<br>
			
			<!-- 頁面編輯區結束 -->
			
			<!-- Javascript區 -->
			<%=webutil.getImportJSFileLink() %>
			<script language="javascript" type="text/javascript" src="<%=webutil.getBasePathForHTML()%>js/jqplot.0.9.7/jquery.jqplot.min.js"></script>
			<script language="javascript" type="text/javascript" src="<%=webutil.getBasePathForHTML()%>js/jqplot.0.9.7/plugins/jqplot.ohlcRenderer.min.js"></script>
			<script language="javascript" type="text/javascript" src="<%=webutil.getBasePathForHTML()%>js/jqplot.0.9.7/plugins/jqplot.dateAxisRenderer.min.js"></script>
			<script language="javascript" type="text/javascript" src="<%=webutil.getBasePathForHTML()%>js/jqplot.0.9.7/plugins/jqplot.highlighter.min.js"></script>
			
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

