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
<%@page import="com.sun.portal.portletcontainer.driver.DriverUtil, 
                java.util.ArrayList, 
                java.util.HashMap" %> 

<%@page import="csiebug.util.WebUtility"%>

<%WebUtility webutil = new WebUtility();%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!--Load the resource bundle for the page -->
<fmt:setBundle basename="DesktopMessages" />

<c:if test="${selectedTab==null}">
  <c:set var="selectedTab" value="dt" scope="session" />
</c:if>
<c:if test="${param.selectedTab!=null}">
  <c:set var="selectedTab" value="${param.selectedTab}" scope="session" />
</c:if>

<div id="portal-tabs">
<ul id="portal-tablist">
<c:forEach var='tab' items='${tabs}' varStatus="status">
  <c:set var="id" value='${tab["id"]}' />
  <li><a href="<c:out value='${tab["url"]}'/>" <c:if test="${id==selectedTab}">id="selected"</c:if>><c:out value='${tab["title"]}'/></a></li>
</c:forEach>
</ul>
</div> <!-- closes portal-tabs -->