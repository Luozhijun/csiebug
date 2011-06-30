<%--
  CDDL HEADER START
  The contents of this file are subject to the terms
  of the Common Development and Distribution License
  (the License). You may not use this file except in
  compliance with the License.

  You can obtain a copy of the License at
  http://www.sun.com/cddl/cddl.html and legal/CDDLv1.0.txt
  See the License for the specific language governing
  permission and limitations under the License.

  When distributing Covered Code, include this CDDL
  Header Notice in each file and include the License file
  at legal/CDDLv1.0.txt.
  If applicable, add the following below the CDDL Header,
  with the fields enclosed by brackets [] replaced by
  your own identifying information:
  "Portions Copyrighted [year] [name of copyright owner]"

  Copyright 2006 Sun Microsystems Inc. All Rights Reserved
  CDDL HEADER END
--%>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="csiebug.util.WebUtility"%>

<%WebUtility webutil = new WebUtility();%>

<jsp:include page="header.jsp" flush="true"/>
  
<jsp:include page="tabs.jsp" flush="true">
  <jsp:param name="selectedTab" value="admin" />
</jsp:include>
  
<div id="portal-content">
	<sec:authorize ifAllGranted="admin">
	  <jsp:include page="systemAdmin.jsp" flush="true" />
	</sec:authorize>
	  <jsp:include page="personalSetting.jsp" flush="true" />
	<sec:authorize ifAllGranted="admin">
	  <jsp:include page="deploy-portlet.jsp" flush="true" />
	  <jsp:include page="undeploy-portlet.jsp" flush="true" />
	  <jsp:include page="create-portlet.jsp" flush="true" />
	</sec:authorize>
	<jsp:include page="modify-dashboard.jsp" flush="true" />
	<jsp:include page="modify-window.jsp" flush="true" />
</div> <!-- closes portal-content -->

</div> <!-- closes portal-page -->
<%=webutil.getImportJSFileLink() %>
<script src="<%=webutil.getBasePathForHTML()%>js/csiebug-tab/csiebug-tab.js"></script>
<script src="<%=webutil.getBasePathForHTML()%>js/csiebug-form/csiebug-editableSelect.js"></script>
<script src="<%=webutil.getBasePathForHTML()%>js/csiebug-form/csiebug-keyValue.js"></script>
<script src="<%=webutil.getBasePathForHTML()%>js/csiebug-form/csiebug-multiText.js"></script>
<script src="<%=webutil.getBasePathForHTML()%>js/csiebug-form/csiebug-multiSelect.js"></script>
<script src="<%=webutil.getBasePathForHTML()%>js/csiebug-grid/csiebug-grid.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		<% if(webutil.getRequestAttribute("Script") != null) { out.print(webutil.getRequestAttribute("Script").toString()); }%>
		<% if(webutil.getRequestAttribute("Msg") != null) { out.print(webutil.getRequestAttribute("Msg").toString()); }%>
	});
	
	//---禁回此頁 Begin--- 
	function forward() { 
	    setTimeout("forward()",100); 
	    history.forward(); 
	} 
	if (document.all) 
	    history.forward();  //for ie 
	else     
	    setTimeout("forward()",100);  //for firefox 
	//---禁回此頁 End---
</script>
</body>
</html>