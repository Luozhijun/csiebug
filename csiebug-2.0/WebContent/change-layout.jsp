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
<%@page import="com.sun.portal.portletcontainer.driver.DriverUtil" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="csiebug" uri="/csiebug-ui" %>

<%@page import="csiebug.util.WebUtility"%>

<%WebUtility webutil = new WebUtility();%>

<!--Load the resource bundle for the page -->
<fmt:setBundle basename="DesktopMessages" />

<form id="portal-content-change-layout" method="POST" action="<%=DriverUtil.getPortletsURL(request)%>" >
  <input type="hidden" id="dashboardId" name="dashboardId" value="<%=webutil.getRequestValue("dashboardId") %>">
  <table>
  	<tr>
	  <csiebug:select id="layout" name="layout" onChange="lockButton();this.form.submit();" userValue="layout" option="layoutOption" blankOptionFlag="false" header='<%=webutil.getMessage("common.layout") %>' />
	</tr>
  </table>
</form>
<NOSCRIPT>
Reloads the page with selected layout
</NOSCRIPT>
