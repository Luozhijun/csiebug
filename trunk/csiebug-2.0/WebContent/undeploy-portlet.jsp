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
                com.sun.portal.portletcontainer.driver.admin.AdminConstants" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="csiebug" uri="/csiebug-ui" %>
<%@page import="csiebug.util.WebUtility"%>

<%WebUtility webutil = new WebUtility();%>

<!--Load the resource bundle for the page -->
<fmt:setBundle basename="DesktopMessages" />

<!-- 程式標頭 -->
<table width="100%">
	<tr>
		<td class="PageHeader"><%=webutil.getMessage("undeploy-portlet.FunctionName") %></td>
	</tr>
</table>

<c:set value="${sessionScope['com.sun.portal.portletcontainer.driver.admin.undeploymentFailed']}" var="msgFail" />
<c:if test="${msgFail != null}" >
    <h2 id="undeploy-failed"><c:out value="${sessionScope['com.sun.portal.portletcontainer.driver.admin.undeploymentFailed']}" escapeXml="false"/></h2>
</c:if>

<c:set value="${sessionScope['com.sun.portal.portletcontainer.driver.admin.undeploymentSucceeded']}" var="msgSuccess" />
<c:if test="${msgSuccess != null}" >
    <h2 id="undeploy-success"><c:out value="${sessionScope['com.sun.portal.portletcontainer.driver.admin.undeploymentSucceeded']}" escapeXml="false"/></h2>
</c:if>

<c:set value="${sessionScope['com.sun.portal.portletcontainer.driver.admin.portletApplications']}" var="list"/> 
<form id="undeploy-portlet" name="undeployForm" action="<%=DriverUtil.getAdminURL(request)%>" method="post" name="undeploy">
    
    <fieldset>
        <table>
            <tr>
                <td><label for="undeploy-portlets"></label>
                    <select id="undeploy-portlets" name="<%=AdminConstants.PORTLETS_TO_UNDEPLOY%>" size="5" multiple>
                        <c:if test="${list != null}"> 
                            <c:forEach items="${list}" var="portlet">
                                <option>${portlet}</option>
                            </c:forEach> 
                        </c:if> 
                </select></td>
            </tr>
            <tr>
                <td colspan="2">
                	<input type="hidden" id="<%=AdminConstants.UNDEPLOY_PORTLET_SUBMIT%>" name="<%=AdminConstants.UNDEPLOY_PORTLET_SUBMIT%>" />
                	<button id="undeployButton" name="undeployButton" type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onclick="loadingBlockPage('', 0);undeployForm.submit();"><%=webutil.getMessage("common.ok") %></button>
                </td>
            </tr>
        </table>
    </fieldset>
    
    
</form>
