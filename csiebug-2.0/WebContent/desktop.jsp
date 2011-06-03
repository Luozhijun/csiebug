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

<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="csiebug.util.WebUtility"%>

<%WebUtility webutil = new WebUtility();%>

<jsp:include page="header.jsp" flush="true" />

<jsp:include page="tabs.jsp" flush="true">
  <jsp:param name="selectedTab" value="dt" />
</jsp:include>
    
<div id="portal-content">

  <c:if test="${layout==null}">
      <c:set var="layout" value="1" scope="session" />
  </c:if>
  <c:if test="${param.layout!=null}">
    <c:set var="layout" value="${param.layout}" scope="session" />
  </c:if>
   
  <jsp:include page="change-layout.jsp" flush="true" />
  
  <c:choose>
    <c:when test='${layout == "1"}'>
      <jsp:include page="layout-1.jsp" flush="true"/>
    </c:when>
    <c:when test='${layout == "2"}'>
      <jsp:include page="layout-2.jsp" flush="true"/>
    </c:when>
    <c:otherwise>
      <jsp:include page="layout-3.jsp" flush="true"/>
    </c:otherwise>
  </c:choose>

</div> <!-- closes portal-content -->
  
</div> <!-- closes portal-page -->
<%=webutil.getImportJSFileLink() %>
<%=webutil.getAllJSFileLink("js/csiebug-excel", true) %>
<%=webutil.getAllJSFileLink("js/csiebug-form", true) %>
<%=webutil.getAllJSFileLink("js/csiebug-grid", true) %>
<%=webutil.getAllJSFileLink("js/csiebug-project", true) %>
<%=webutil.getAllJSFileLink("js/csiebug-tab", true) %>
<%=webutil.getAllJSFileLink("js/csiebug-treeview", true) %>
<%=webutil.getAllJSFileLink("js/farbtastic", true) %>
<%=webutil.getAllJSFileLink("js/jqplot.0.9.7", true) %>
<%=webutil.getAllJSFileLink("js/jquery-contextmenu", true) %>

<script type="text/javascript">
	$(document).ready(function() {
		<% if(webutil.getRequestAttribute("Script") != null) { out.print(webutil.getRequestAttribute("Script").toString()); }%>
		<% if(webutil.getRequestAttribute("Msg") != null) { out.print(webutil.getRequestAttribute("Msg").toString()); }%>

		<c:choose>
			<c:when test='${layout == "1"}'>
				$("#layout-2-thick").sortable({
					handle : '.portlet-header', 
					connectWith:["#layout-2-thin"],
					activate:function(event, ui) {
						document.getElementById("layout-2-thick").setAttribute("style", "border-style:dashed;border-width:1px;");
					},
					deactivate:function(e, ui) {
						document.getElementById("layout-2-thick").setAttribute("style", "");
						sendAJAXRequest("admin?ActFlag=saveWindowOrder&dashboardId=<%=webutil.getRequestValue("dashboardId") %>&thick=" + $("#layout-2-thick").sortable("toArray"));
					}
				});
				$("#layout-2-thin").sortable({
					handle : '.portlet-header', 
					connectWith:["#layout-2-thick"],
					activate:function(event, ui) {
						document.getElementById("layout-2-thin").setAttribute("style", "border-style:dashed;border-width:1px;");
					},
					deactivate:function(e, ui) {
						document.getElementById("layout-2-thin").setAttribute("style", "");
						sendAJAXRequest("admin?ActFlag=saveWindowOrder&dashboardId=<%=webutil.getRequestValue("dashboardId") %>&thin=" + $("#layout-2-thin").sortable("toArray"));
					}
				});
		    </c:when>
		    <c:when test='${layout == "2"}'>
			    $("#layout-1-thick").sortable({
			    	handle : '.portlet-header', 
					connectWith:["#layout-1-thin"],
					activate:function(event, ui) {
						document.getElementById("layout-1-thick").setAttribute("style", "border-style:dashed;border-width:1px;");
					},
					deactivate:function(e, ui) {
						document.getElementById("layout-1-thick").setAttribute("style", "");
						sendAJAXRequest("admin?ActFlag=saveWindowOrder&dashboardId=<%=webutil.getRequestValue("dashboardId") %>&thick=" + $("#layout-1-thick").sortable("toArray"));
					}
				});
				$("#layout-1-thin").sortable({
					handle : '.portlet-header',
					connectWith:["#layout-1-thick"],
					activate:function(event, ui) {
						document.getElementById("layout-1-thin").setAttribute("style", "border-style:dashed;border-width:1px;");
					},
					deactivate:function(e, ui) {
						document.getElementById("layout-1-thin").setAttribute("style", "");
						sendAJAXRequest("admin?ActFlag=saveWindowOrder&dashboardId=<%=webutil.getRequestValue("dashboardId") %>&thin=" + $("#layout-1-thin").sortable("toArray"));
					}
				});
			</c:when>
		</c:choose>
	});
</script>
</body>
</html>