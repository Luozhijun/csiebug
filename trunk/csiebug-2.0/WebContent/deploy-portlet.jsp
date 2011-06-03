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
<%@page import="com.sun.portal.portletcontainer.driver.admin.AdminConstants" %>

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
		<td class="PageHeader"><%=webutil.getMessage("deploy-portlet.FunctionName") %></td>
	</tr>
</table>

<c:set value="${sessionScope['com.sun.portal.portletcontainer.driver.admin.deploymentFailed']}" var="msgFail" />
<c:if test="${msgFail != null}" >
  <h2 id="deploy-failed"><c:out value="${sessionScope['com.sun.portal.portletcontainer.driver.admin.deploymentFailed']}" escapeXml="false"/></h2>
</c:if>

<c:set value="${sessionScope['com.sun.portal.portletcontainer.driver.admin.deploymentSucceeded']}" var="msgSuccess" />
<c:if test="${msgSuccess != null}" >
  <h2 id="deploy-success"><c:out value="${sessionScope['com.sun.portal.portletcontainer.driver.admin.deploymentSucceeded']}" escapeXml="false"/></h2>
</c:if>

<form id="deployForm" METHOD="POST" name="deployForm" enctype="multipart/form-data"  action="upload" >
  <fieldset>
  	<table>
  		<tr>
  			<td class = "TdHeader">
		    	<label id="upload_header" name="upload_header" class = "LabelHeader"><%=webutil.getMessage("deploy-portlet.WarFile") %>：</label>
		    </td>
		    <td class = "TdBody">
		    	<input id="upload" type="file" name="upload"  size="50" />
		    </td>
    	</tr>
    	<tr>
    		<csiebug:checkbox id="permissionFlag" name="permissionFlag" header='<%=webutil.getMessage("deploy-portlet.openPermission") %>' isReturnValue="false" />
    	</tr>
    	<tr>
    		<td>
    			<input id="deployButton" name="deployButton" type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onclick="loadingBlockPage('', 0);deployForm.submit();" value="<%=webutil.getMessage("common.ok") %>">
    		</td>
    	</tr>
    </table>  
  </fieldset>
</form>
