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
		
		<style type="text/css">
		#chart3 .jqplot-point-label {
		  border: 1.5px solid #aaaaaa;
		  padding: 1px 3px;
		  background-color: #eeccdd;
		}
		</style>
		
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
				<script type="text/javascript" src="<%=webutil.getBasePathForHTML()%>js/jqplot.0.9.7/plugins/jqplot.barRenderer.min.js"></script>
				<script type="text/javascript" src="<%=webutil.getBasePathForHTML()%>js/jqplot.0.9.7/plugins/jqplot.categoryAxisRenderer.min.js"></script>
				<script type="text/javascript" src="<%=webutil.getBasePathForHTML()%>js/jqplot.0.9.7/plugins/jqplot.pointLabels.min.js"></script>
			</c:code>
			
			可選用以下這個css檔:<br>
			<c:code>
				<link rel="stylesheet" type="text/css" href="<%=webutil.getBasePathForHTML()%>js/jqplot.0.9.7/jquery.jqplot.min.css" />
			</c:code>
			
			<br>
			
			<hr>
			
			<h4>Data point label example</h4>
			
			<h5>JSP 程式碼:</h5>
			
			<c:code>
				<JQPlotChart chartId="chart1" list="lineData6" chartTitleText="Chart with Point Labels" chartSeriesList="seriesList6" chartXaxisPad="1.3" chartYaxisPad="1.3" />
				<JQPlotChart chartId="chart2" list="lineData11" chartTitleText="Point Labels From Extra Series Data" chartSeriesList="seriesList12" chartYaxisPad="1.3" />
				<style type="text/css">
				#chart3 .jqplot-point-label {
				  border: 1.5px solid #aaaaaa;
				  padding: 1px 3px;
				  background-color: #eeccdd;
				}
				</style>
				<JQPlotChart chartId="chart3" list="lineData6" chartTitleText="Bar Chart with Point Labels" chartSeriesList="seriesList13" chartXaxisRenderer="$.jqplot.CategoryAxisRenderer" chartYaxisPad="1.3" />
				<JQPlotChart chartId="chart4" list="barData7" chartTitleText="Stacked Bar Chart with Cumulative Point Labels" stackSeries="true" chartSeriesList="seriesList7" chartXaxisRenderer="$.jqplot.CategoryAxisRenderer" chartYaxisTicks="yticks3" />
			</c:code>
			
			<hr>
			
			<h5>頁面結果:</h5>
			
			<cc:JQPlotChart chartId="chart1" list="lineData6" chartTitleText="Chart with Point Labels" chartSeriesList="seriesList6" chartXaxisPad="1.3" chartYaxisPad="1.3" />
			<cc:JQPlotChart chartId="chart2" list="lineData11" chartTitleText="Point Labels From Extra Series Data" chartSeriesList="seriesList12" chartYaxisPad="1.3" />
			<cc:JQPlotChart chartId="chart3" list="lineData6" chartTitleText="Bar Chart with Point Labels" chartSeriesList="seriesList13" chartXaxisRenderer="$.jqplot.CategoryAxisRenderer" chartYaxisPad="1.3" />
			<cc:JQPlotChart chartId="chart4" list="barData7" chartTitleText="Stacked Bar Chart with Cumulative Point Labels" stackSeries="true" chartSeriesList="seriesList7" chartXaxisRenderer="$.jqplot.CategoryAxisRenderer" chartYaxisTicks="yticks3" />
			
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
			<script type="text/javascript" src="<%=webutil.getBasePathForHTML()%>js/jqplot.0.9.7/plugins/jqplot.barRenderer.min.js"></script>
			<script type="text/javascript" src="<%=webutil.getBasePathForHTML()%>js/jqplot.0.9.7/plugins/jqplot.categoryAxisRenderer.min.js"></script>
			<script type="text/javascript" src="<%=webutil.getBasePathForHTML()%>js/jqplot.0.9.7/plugins/jqplot.pointLabels.min.js"></script>
			
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

