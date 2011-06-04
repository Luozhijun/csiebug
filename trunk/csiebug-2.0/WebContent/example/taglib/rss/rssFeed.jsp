<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/csiebug-ui" %>
<%@ taglib prefix="rss" uri="/csiebug-rss" %>

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
		<form id="rssFeed" name="rssFeed" action="<%=webutil.getBasePathForHTML()%>example/rssFeed" method="POST">
			<!-- 程式標頭 -->
			<table width="100%">
				<tr>
					<td class="PageHeader"><%=webutil.getRequestAttribute("FunctionName").toString()%></td>
				</tr>
			</table>
			<%@ include file="../../../pub/common_html.jsp" %>
			<%@ include file="../../../pub/common_form.jsp" %>
			
			<!-- 以下為頁面編輯區 -->
			
			<h5>JSP 程式碼:</h5>
			
			<c:code>
				<feed url="http://www.javaworld.com.tw/jute/rss/2.0/all.xml" feedId="javaworld" />
				<table>
					<tr><td>Channel Image:</td><td><channelImage feedId="javaworld"/></td></tr>
					<tr><td>Channel Title:</td><td><channelTitle feedId="javaworld"/></td></tr>
					<tr><td>Channel Link:</td><td><channelLink feedId="javaworld"/></td></tr>
					<tr><td>Channel Description:</td><td><channelDescription feedId="javaworld"/></td></tr>
					<tr><td>Channel Copyright:</td><td><channelCopyright feedId="javaworld"/></td></tr>
					<tr><td>Channel Docs:</td><td><channelDocs feedId="javaworld"/></td></tr>
					<tr><td>Channel Generator:</td><td><channelGenerator feedId="javaworld"/></td></tr>
					<tr><td>Channel Language:</td><td><channelLanguage feedId="javaworld"/></td></tr>
					<tr><td>Channel Last Build Date:</td><td><channelLastBuildDate feedId="javaworld"/></td></tr>
					<tr><td>Channel Managing Editor:</td><td><channelManagingEditor feedId="javaworld"/></td></tr>
					<tr><td>Channel PubDate:</td><td><channelPubDate feedId="javaworld"/></td></tr>
				</table>
				<items feedId="javaworld">
					<fieldset style="background:lightblue">
						<legend><itemTitle /></legend>
						<div>
							itemDescription:<itemDescription /><br>
							itemComments:<itemComments /><br>
							itemPubDate:<itemPubDate /><br>
							itemLink:<itemLink /><br>
						</div>
					</fieldset>
				</items>		
			</c:code>
			
			<h5>頁面結果:</h5>
			
			<rss:feed url="http://www.javaworld.com.tw/jute/rss/2.0/all.xml" feedId="javaworld" />
				<table>
					<tr><td>Channel Image:</td><td><rss:channelImage feedId="javaworld"/></td></tr>
					<tr><td>Channel Title:</td><td><rss:channelTitle feedId="javaworld"/></td></tr>
					<tr><td>Channel Link:</td><td><rss:channelLink feedId="javaworld"/></td></tr>
					<tr><td>Channel Description:</td><td><rss:channelDescription feedId="javaworld"/></td></tr>
					<tr><td>Channel Copyright:</td><td><rss:channelCopyright feedId="javaworld"/></td></tr>
					<tr><td>Channel Docs:</td><td><rss:channelDocs feedId="javaworld"/></td></tr>
					<tr><td>Channel Generator:</td><td><rss:channelGenerator feedId="javaworld"/></td></tr>
					<tr><td>Channel Language:</td><td><rss:channelLanguage feedId="javaworld"/></td></tr>
					<tr><td>Channel Last Build Date:</td><td><rss:channelLastBuildDate feedId="javaworld"/></td></tr>
					<tr><td>Channel Managing Editor:</td><td><rss:channelManagingEditor feedId="javaworld"/></td></tr>
					<tr><td>Channel PubDate:</td><td><rss:channelPubDate feedId="javaworld"/></td></tr>
				</table>
				<rss:items feedId="javaworld">
					<fieldset style="background:lightblue">
						<legend><rss:itemTitle /></legend>
						<table>
							<tr><td>itemDescription:</td><td><rss:itemDescription /></td></tr>
							<tr><td>itemComments:</td><td><rss:itemComments /></td></tr>
							<tr><td>itemPubDate:</td><td><rss:itemPubDate /></td></tr>
							<tr><td>itemLink:</td><td><rss:itemLink /></td></tr>
						</table>
					</fieldset>
				</rss:items>
			
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

