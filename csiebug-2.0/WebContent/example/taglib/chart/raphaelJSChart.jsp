<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/csiebug-ui" %>
<%@ taglib prefix="cc" uri="/csiebug-raphaelJSChart" %>

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
		<form id="raphaelJSChart" name="raphaelJSChart" action="<%=webutil.getBasePathForHTML()%>example/raphaelJSChart" method="POST">
			<!-- 程式標頭 -->
			<table width="100%">
				<tr>
					<td class="PageHeader"><%=webutil.getRequestAttribute("FunctionName").toString()%></td>
				</tr>
			</table>
			<%@ include file="../../../pub/common_html.jsp" %>
			
			<!-- 以下為頁面編輯區 -->
			<h4>RaphaelJSChart</h4>
			
			欲引用RaphaelJSChart時，須視情況引用以下js檔:<br>
			<c:code>
				<script src="<%=webutil.getBasePathForHTML()%>js/raphael/raphael.js" type="text/javascript"></script><br>
				<script src="<%=webutil.getBasePathForHTML()%>js/raphael/g.raphael-min.js" type="text/javascript"></script><br>
				<script src="<%=webutil.getBasePathForHTML()%>js/raphael/g.pie-min.js" type="text/javascript"></script><br>
				<script src="<%=webutil.getBasePathForHTML()%>js/raphael/g.bar-min.js" type="text/javascript"></script><br>
				<script src="<%=webutil.getBasePathForHTML()%>js/raphael/g.line-min.js" type="text/javascript"></script><br>
			</c:code>
			<br>
			
			<hr>
			
			<h5>JSP 程式碼:</h5>
			
			<c:code>
			<RaphaelJSChart chartId="chart1" chartType="pie" list="pie" chartTitleText="靜態圓餅圖"/>
			</c:code>
			
			<hr>
			
			<h5>頁面結果:</h5>
			
			<cc:RaphaelJSChart chartId="chart1" chartType="pie" list="pie" chartTitleText="靜態圓餅圖" />
			
			<hr>
			
			<h5>JSP 程式碼:</h5>
			
			<c:code>
			<RaphaelJSChart chartId="chart2" chartType="pie" list="pie" chartTitleText="動態圓餅圖" chartInteractive="true"/>
			</c:code>
			
			<hr>
			
			<h5>頁面結果:</h5>
			
			<cc:RaphaelJSChart chartId="chart2" chartType="pie" list="pie" chartTitleText="動態圓餅圖" chartInteractive="true"/>	
			
			<hr>
			
			<h5>JSP 程式碼:</h5>
			
			<c:code>
			<RaphaelJSChart chartId="chart3" chartType="bar" list="bar" chartTitleText="靜態直條圖"/>
			</c:code>
			
			<hr>
			
			<h5>頁面結果:</h5>
			
			<cc:RaphaelJSChart chartId="chart3" chartType="bar" list="bar" chartTitleText="靜態直條圖"/>
			
			<hr>
			
			<h5>JSP 程式碼:</h5>
			
			<c:code>
			<RaphaelJSChart chartId="chart4" chartType="bar" list="bar" chartTitleText="靜態堆疊直條圖" chartBarStacked="true"/>
			</c:code>
			
			<hr>
			
			<h5>頁面結果:</h5>
			
			<cc:RaphaelJSChart chartId="chart4" chartType="bar" list="bar" chartTitleText="靜態堆疊直條圖" chartBarStacked="true"/>
			
			<hr>
			
			<h5>JSP 程式碼:</h5>
			
			<c:code>
			<RaphaelJSChart chartId="chart5" chartType="bar" list="bar" chartTitleText="靜態堆疊直條圖(round)" chartBarStacked="true" chartBarType="round"/>
			</c:code>
			
			<hr>
			
			<h5>頁面結果:</h5>
			
			<cc:RaphaelJSChart chartId="chart5" chartType="bar" list="bar" chartTitleText="靜態堆疊直條圖(round)" chartBarStacked="true" chartBarType="round"/>
			
			<hr>
			
			<h5>JSP 程式碼:</h5>
			
			<c:code>
			<RaphaelJSChart chartId="chart6" chartType="bar" list="bar" chartTitleText="動態橫式直條圖" chartInteractive="true" chartBarHorizontal="true"/>
			</c:code>
			
			<hr>
			
			<h5>頁面結果:</h5>
			
			<cc:RaphaelJSChart chartId="chart6" chartType="bar" list="bar" chartTitleText="動態橫式直條圖" chartInteractive="true" chartBarHorizontal="true"/>
			
			<hr>
			
			<h5>JSP 程式碼:</h5>
			
			<c:code>
			<RaphaelJSChart chartId="chart7" chartType="bar" list="bar" chartTitleText="動態橫式堆疊直條圖" chartInteractive="true" chartBarStacked="true" chartBarHorizontal="true"/>
			</c:code>
			
			<hr>
			
			<h5>頁面結果:</h5>
			
			<cc:RaphaelJSChart chartId="chart7" chartType="bar" list="bar" chartTitleText="動態橫式堆疊直條圖" chartInteractive="true" chartBarStacked="true" chartBarHorizontal="true"/>
			
			<hr>
			
			<h5>JSP 程式碼:</h5>
			
			<c:code>
			<RaphaelJSChart chartId="chart8" chartType="bar" list="bar" chartTitleText="動態橫式堆疊直條圖(soft & column hover)" chartInteractive="true" chartBarStacked="true" chartBarHorizontal="true" chartBarType="soft" chartBarHoverType="2"/>
			</c:code>
			
			<hr>
			
			<h5>頁面結果:</h5>
			
			<cc:RaphaelJSChart chartId="chart8" chartType="bar" list="bar" chartTitleText="動態橫式堆疊直條圖(soft & column hover)" chartInteractive="true" chartBarStacked="true" chartBarHorizontal="true" chartBarType="soft" chartBarHoverType="2"/>
			
			<hr>
			
			<h5>JSP 程式碼:</h5>
			
			<c:code>
			<RaphaelJSChart chartId="chart9" chartType="line" list="line" chartTitleText="靜態折線圖" />
			</c:code>
			
			<hr>
			
			<h5>頁面結果:</h5>
			
			<cc:RaphaelJSChart chartId="chart9" chartType="line" list="line" chartTitleText="靜態折線圖" />
			
			<hr>
			
			<h5>JSP 程式碼:</h5>
			
			<c:code>
			<RaphaelJSChart chartId="chart10" chartType="line" list="line" chartTitleText="靜態折線圖(shade)" chartLineShade="true" />
			</c:code>
			
			<hr>
			
			<h5>頁面結果:</h5>
			
			<cc:RaphaelJSChart chartId="chart10" chartType="line" list="line" chartTitleText="靜態折線圖(shade)" chartLineShade="true"/>
			
			<hr>
			
			<h5>JSP 程式碼:</h5>
			
			<c:code>
			<RaphaelJSChart chartId="chart11" chartType="line" list="line" chartTitleText="靜態折線圖(shade & nostroke)" chartLineShade="true" chartLineNoStroke="true"/>
			</c:code>
			
			<hr>
			
			<h5>頁面結果:</h5>
			
			<cc:RaphaelJSChart chartId="chart11" chartType="line" list="line" chartTitleText="靜態折線圖(shade & nostroke)" chartLineShade="true" chartLineNoStroke="true"/>
			
			<hr>
			
			<h5>JSP 程式碼:</h5>
			
			<c:code>
			<RaphaelJSChart chartId="chart12" chartType="line" list="line" chartTitleText="動態座標軸折線圖"  chartInteractive="true" chartLineAxis="0 0 1 1"/>
			</c:code>
			
			<hr>
			
			<h5>頁面結果:</h5>
			
			<cc:RaphaelJSChart chartId="chart12" chartType="line" list="line" chartTitleText="動態座標軸折線圖" chartInteractive="true" chartLineAxis="0 0 1 1"/>
			
			<br>
			<br>
			<br>
			<br>
			<br>
			
			<!-- 頁面編輯區結束 -->
			
			<!-- Javascript區 -->
			<%=webutil.getImportJSFileLink() %>
			<script src="<%=webutil.getBasePathForHTML()%>js/raphael/raphael.js" type="text/javascript"></script>
			<script src="<%=webutil.getBasePathForHTML()%>js/raphael/g.raphael-min.js" type="text/javascript"></script>
			<script src="<%=webutil.getBasePathForHTML()%>js/raphael/g.pie-min.js" type="text/javascript"></script>
			<script src="<%=webutil.getBasePathForHTML()%>js/raphael/g.bar-min.js" type="text/javascript"></script>
			<script src="<%=webutil.getBasePathForHTML()%>js/raphael/g.line-min.js" type="text/javascript"></script>
			
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

