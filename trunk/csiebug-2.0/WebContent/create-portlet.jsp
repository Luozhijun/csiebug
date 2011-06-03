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
		<td class="PageHeader"><%=webutil.getMessage("create-portlet.FunctionName") %></td>
	</tr>
</table>

<c:set value="${sessionScope['com.sun.portal.portletcontainer.driver.admin.creationFailed']}" var="msgFail" />
<c:if test="${msgFail != null}" >
    <h2 id="create-failed"><c:out value="${sessionScope['com.sun.portal.portletcontainer.driver.admin.creationFailed']}" escapeXml="false"/></h2>
</c:if>

<c:set value="${sessionScope['com.sun.portal.portletcontainer.driver.admin.creationSucceeded']}" var="msgSuccess" />
<c:if test="${msgSuccess != null}" >
    <h2 id="create-success"><c:out value="${sessionScope['com.sun.portal.portletcontainer.driver.admin.creationSucceeded']}" escapeXml="false"/></h2>
</c:if>

<form id="createForm" name="createForm" method="post" action="<%=DriverUtil.getAdminURL(request)%>" >
    <fieldset>
    	<table>
    		<tr>
            	<csiebug:select id="<%=AdminConstants.PORTLET_LIST%>" name="<%=AdminConstants.PORTLET_LIST%>" header='<%=webutil.getMessage("common.portlet")%>' option="portletOption" isRequired="true" isReturnValue="false"/>
            </tr>
            <tr>
            	<csiebug:text id='<%=AdminConstants.PORTLET_WINDOW_NAME + "_create"%>' name='<%=AdminConstants.PORTLET_WINDOW_NAME + "_create"%>' maxlength="255" isRequired="true" header='<%=webutil.getMessage("common.portlet.window") %>' isReturnValue="false"/>
            </tr>
            <tr>
	    		<csiebug:checkbox id="createWindowPermissionFlag" name="createWindowPermissionFlag" header='<%=webutil.getMessage("deploy-portlet.openPermission") %>' isReturnValue="false" />
	    	</tr>
            <tr>
        		<td>
        			<script type="text/javascript" src="<%=webutil.getBasePathForHTML() %>create-portlet.js"></script>
        			<input type="hidden" id='<%=AdminConstants.PORTLET_WINDOW_TITLE + "_create"%>' name='<%=AdminConstants.PORTLET_WINDOW_TITLE + "_create"%>' value="title">
                	<input type="hidden" id="<%=AdminConstants.CREATE_PORTLET_WINDOW_SUBMIT%>" name="<%=AdminConstants.CREATE_PORTLET_WINDOW_SUBMIT%>" />
                	<input type="button" id="createWindowButton" name="createWindowButton" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="createWindow('createForm', this, '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');" value="<%=webutil.getMessage("common.ok")%>">
        		</td>
        	</tr>
        </table>
    </fieldset>
</form>