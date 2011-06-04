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
				<script language="javascript" type="text/javascript" src="<%=webutil.getBasePathForHTML()%>js/jqplot.0.9.7/plugins/jqplot.categoryAxisRenderer.min.js"></script>
				<script language="javascript" type="text/javascript" src="<%=webutil.getBasePathForHTML()%>js/jqplot.0.9.7/plugins/jqplot.barRenderer.min.js"></script>
			</c:code>
			
			可選用以下這個css檔:<br>
			<c:code>
				<link rel="stylesheet" type="text/css" href="<%=webutil.getBasePathForHTML()%>js/jqplot.0.9.7/jquery.jqplot.min.css" />
			</c:code>
			
			<br>
			
			<hr>
			
			<h4>Bar chart example</h4>
			
			<h5>JSP 程式碼:</h5>
			
			<c:code>
				<JQPlotChart chartId="chart1" list="barData1" chartTitleText="Bar Chart" chartLegendShow="true" chartLegendLocation="ne" chartSeriesList="seriesList1" chartXaxisRenderer="$.jqplot.CategoryAxisRenderer" chartYaxisMin="0" />
				<JQPlotChart chartId="chart2" list="barData2" chartTitleText="Bar Chart With Options" chartLegendShow="true" chartLegendLocation="ne" chartLegendXoffset="55" chartSeriesList="seriesList2" chartXaxisRenderer="$.jqplot.CategoryAxisRenderer" chartXaxisTicks="xticks" chartYaxisMin="0" />
				<JQPlotChart chartId="chart3" list="barData3" chartTitleText="Horizontally Oriented Bar Chart" chartLegendShow="true" chartLegendLocation="ne" chartSeriesList="seriesList3" chartYaxisRenderer="$.jqplot.CategoryAxisRenderer" chartYaxisTicks="yticks" chartXaxisMin="0" />
			</c:code>
			
			<hr>
			
			<h5>頁面結果:</h5>
			
			<cc:JQPlotChart chartId="chart1" list="barData1" chartTitleText="Bar Chart" chartLegendShow="true" chartLegendLocation="ne" chartSeriesList="seriesList1" chartXaxisRenderer="$.jqplot.CategoryAxisRenderer" chartYaxisMin="0" />
			<cc:JQPlotChart chartId="chart2" list="barData2" chartTitleText="Bar Chart With Options" chartLegendShow="true" chartLegendLocation="ne" chartLegendXoffset="55" chartSeriesList="seriesList2" chartXaxisRenderer="$.jqplot.CategoryAxisRenderer" chartXaxisTicks="xticks" chartYaxisMin="0" />
			<cc:JQPlotChart chartId="chart3" list="barData3" chartTitleText="Horizontally Oriented Bar Chart" chartLegendShow="true" chartLegendLocation="ne" chartSeriesList="seriesList3" chartYaxisRenderer="$.jqplot.CategoryAxisRenderer" chartYaxisTicks="yticks" chartXaxisMin="0" />
			
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
			<script language="javascript" type="text/javascript" src="<%=webutil.getBasePathForHTML()%>js/jqplot.0.9.7/plugins/jqplot.categoryAxisRenderer.min.js"></script>
			<script language="javascript" type="text/javascript" src="<%=webutil.getBasePathForHTML()%>js/jqplot.0.9.7/plugins/jqplot.barRenderer.min.js"></script>
			
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

