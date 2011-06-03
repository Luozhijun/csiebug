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
                com.sun.portal.portletcontainer.driver.admin.AdminConstants, 
                com.sun.portal.portletcontainer.admin.registry.PortletRegistryConstants" %>

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
		<td class="PageHeader"><%=webutil.getMessage("modify-window.FunctionName") %></td>
	</tr>
</table>

<c:set value="${sessionScope['com.sun.portal.portletcontainer.driver.admin.modifyFailed']}" var="msgFail" />
<c:if test="${msgFail != null}" >
    <h2 id="modify-failed"><c:out value="${sessionScope['com.sun.portal.portletcontainer.driver.admin.modifyFailed']}" escapeXml="false"/></h2>
</c:if>

<c:set value="${sessionScope['com.sun.portal.portletcontainer.driver.admin.modifySucceeded']}" var="msgSuccess" />
<c:if test="${msgSuccess != null}" >
    <h2 id="modify-success"><c:out value="${sessionScope['com.sun.portal.portletcontainer.driver.admin.modifySucceeded']}" escapeXml="false"/></h2>
</c:if>

<form id="modifyForm" name="modifyForm" method="post" action="<%=DriverUtil.getAdminURL(request)%>" >
	<fieldset>
        <table>
        	<tr>
            	<csiebug:select id="portletWindowList" name="portletWindowList" onChange="loadPortletProperties();" header='<%=webutil.getMessage("common.portlet.window")%>' option="portletWindowOption" isRequired="true"/>
            </tr>
            <tr>
            	<csiebug:select id="dashboardId" name="dashboardId" userValue="dashboardId" header='<%=webutil.getMessage("common.dashboard")%>' option="dashboardOption" blankOptionFlag="false" isRequired="true" />
            </tr>
            <tr>
            	<csiebug:text id="<%=AdminConstants.PORTLET_WINDOW_TITLE%>" name="<%=AdminConstants.PORTLET_WINDOW_TITLE%>" maxlength="50" userValue="portlet.title" header='<%=webutil.getMessage("common.portlet.title") %>' />
            </tr>
            <tr>
            	<csiebug:select id="<%=AdminConstants.WIDTH_LIST%>" name="<%=AdminConstants.WIDTH_LIST%>" userValue="columnName" header='<%=webutil.getMessage("modify-window.ColumnName")%>' option="columnNameOption" blankOptionFlag="false" />
            	<csiebug:text id="portletSortOrder" name="portletSortOrder" dataType="number" userValue="portletSortOrder" header='<%=webutil.getMessage("common.sort") %>' />
            </tr>
            <tr>
            	<csiebug:radiogroup id="<%=AdminConstants.VISIBLE_LIST%>" name="<%=AdminConstants.VISIBLE_LIST%>" userValue="visible" header='<%=webutil.getMessage("modify-window.ShowHide") %>' option="visibleOption" newLineFlag="false" isRequired="true"/>
            </tr>
            <!-- <tr>
                <td>
                    <label for="widthList"><fmt:message key="selectWidth"/></label>
                    <select id="widthList" name="<%=AdminConstants.WIDTH_LIST%>" >
                        <option ${sessionScope['com.sun.portal.portletcontainer.driver.admin.thickWindow']} value="<%=PortletRegistryConstants.WIDTH_THICK%>">
                            <fmt:message key="thick"/>
                        </option>
                        <option ${sessionScope['com.sun.portal.portletcontainer.driver.admin.thinWindow']} value="<%=PortletRegistryConstants.WIDTH_THIN%>" >
                            <fmt:message key="thin"/>
                        </option>
                    </select>
                </td>
            </tr> -->
            <tr>
                <td>
                	<script type="text/javascript" src="<%=webutil.getBasePathForHTML() %>modify-window.js"></script>
                	<input type="hidden" id="<%=AdminConstants.MODIFY_PORTLET_WINDOW_SUBMIT%>" name="<%=AdminConstants.MODIFY_PORTLET_WINDOW_SUBMIT%>" />
                	<input id="modifyWindowButton" name="modifyWindowButton" type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="modifyWindow('modifyForm', this, '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');" value="<%=webutil.getMessage("common.ok")%>">
                </td>
            </tr>
        </table>
    </fieldset>
</form>
