<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/csiebug-ui" %>
<%@ taglib prefix="ct" uri="/csiebug-timeline" %>

<%@page import="csiebug.util.WebUtility"%>

<%WebUtility webutil = new WebUtility();%>


<html>
	<head>
		<%@ include file="../../../pub/common_css.jsp" %>
		<%=webutil.getImportCSSFileLink() %>
		
		<link rel="shortcut icon" href="<%=webutil.getBasePathForHTML()%>images/favicon.ico" />
		<!-- timeline的js要放在head裡面才可以 -->
		<script src="<%=webutil.getBasePathForHTML()%>js/timeline_2.3.0/timeline_js/timeline-api.js?bundle=true" type="text/javascript"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title><%=webutil.getRequestAttribute("FunctionName").toString()%></title>
	</head>
	<body onload="onResize();">
		<form id="timeline" name="timeline" action="<%=webutil.getBasePathForHTML()%>example/timeline" method="POST">
			<!-- 程式標頭 -->
			<table width="100%">
				<tr>
					<td class="PageHeader"><%=webutil.getRequestAttribute("FunctionName").toString()%></td>
				</tr>
			</table>
			<%@ include file="../../../pub/common_html.jsp" %>
			
			<!-- 以下為頁面編輯區 -->
			<h4>Timeline</h4>
			
			欲引用timeline時，須在HTML HEAD裡面引用此js檔:<br>
			<c:code>
				<script src="<%=webutil.getBasePathForHTML()%>js/timeline_2.3.0/timeline_js/timeline-api.js?bundle=true" type="text/javascript"></script>
			</c:code>
			<br>
			<hr>
	
	Event屬性說明:<br>			
	* start - in full date format (e.g. "May 20 1961 00:00:00 GMT-0600"). <br>
    * end - same date format as start<br>
    * isDuration - "true" or "false". Only applies to events with start/end times.<br>
          o true -- the event occurs over a time duration. No icon. The event will be drawn as a dark blue tape. The tape color is set with the color attribute. Default color is #58A0DC<br>
          o false -- the event is focused on a specific "instant" (shown with the icon). The event will be drawn as a blue dot icon (default) with a pale blue tape. The tape is the default color (or color attribute color), with opacity set to 20. To change the opacity, change the theme's instant: {impreciseOpacity: 20} value. Maximum 100.<br> 
    * title - text title that goes next to the tape in the Timeline. Also shown in the bubble. The title attribute is optional. Leave it out if you want just an icon or icon and tape. The description will be shown in the bubble when the icon or tape is clicked. <br>
	* icon - url. This image will appear next to the title text in the timeline if (no end date) or (durationEvent = false). If a start and end date are supplied, and durationEvent is true, the icon is not shown. If icon attribute is not set, a default icon from the theme is used.<br>
    * image - url to an image that will be displayed in the bubble<br>
    * link - url. The bubble's title text be a hyper-link to this address.<br>
    * color - color of the text and tape (duration events) to display in the timeline. If the event has durationEvent = false, then the bar's opacity will be applied (default 20%). See durationEvent, above.<br>
    * textColor - color of the label text on the timeline. If not set, then the color attribute will be used.<br>
    * caption - additional event information shown when mouse is hovered over the Timeline tape or label. Uses the html title property. Looks like a tooltip. Plain text only. See the cubism example.<br>
    * description - will be displayed inside the bubble with the event's title and image.<br>
    
    <hr>
			
			<h5>JAVA 程式碼:</h5>
			
			<c:code>
			ArrayList<Map<String, String>> list = new ArrayList<Map<String,String>>();
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("start", "<%=webutil.getRequestAttribute("today") %> 00:00:00 GMT");
			map.put("title", "test title");
			list.add(map);
			
			map = new LinkedHashMap<String, String>();
			map.put("start", "<%=webutil.getRequestAttribute("today") %> 00:00:00 GMT");
			map.put("end", "<%=webutil.getRequestAttribute("afterFiveDay") %> 00:00:00 GMT");
			map.put("title", "test title2");
			list.add(map);
			
			map = new LinkedHashMap<String, String>();
			map.put("start", "<%=webutil.getRequestAttribute("today") %> 00:00:00 GMT");
			map.put("end", "<%=webutil.getRequestAttribute("afterFiveDay") %> 00:00:00 GMT");
			map.put("isDuration", "true");
			map.put("title", "test title3");
			list.add(map);
			
			map = new LinkedHashMap<String, String>();
			map.put("start", "<%=webutil.getRequestAttribute("today") %> 00:00:00 GMT");
			map.put("link", "http://localhost:8080<%=webutil.getBasePathForHTML()%>example/timeline");
			map.put("title", "test title4");
			list.add(map);
			
			setRequestAttribute("testTimelineData", list);
			</c:code>
			
			<hr>
			
			<h5>JSP 程式碼:</h5>
			
			<c:code>
			<timeline timelineId="testTimeline" list="testTimelineData" timelineHeight="500" openDayBandFlag="true" openMonthBandFlag="true" openYearBandFlag="true" />
			</c:code>
			
			<hr>
			
			<h5>頁面結果:</h5>
			
			<ct:timeline timelineId="testTimeline" list="testTimelineData" timelineHeight="500" openDayBandFlag="true" openMonthBandFlag="true" openYearBandFlag="true" />
			
			<br>
			<br>
			<br>
			<br>
			<br>
			
			<!-- 頁面編輯區結束 -->
			
			<!-- Javascript區 -->
			<%=webutil.getImportJSFileLink() %>
			
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

