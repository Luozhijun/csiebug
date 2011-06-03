<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/csiebug-ui" %>

<%@page import="csiebug.util.WebUtility"%>

<%WebUtility webutil = new WebUtility();%>

		<form id="dashboardForm" name="dashboardForm" action="<%=webutil.getBasePathForHTML()%>admin" method="POST">
			<!-- 程式標頭 -->
			<table width="100%">
				<tr>
					<td class="PageHeader"><%=webutil.getMessage("modify-dashboard.FunctionName")%></td>
				</tr>
			</table>
			
			<!-- 以下為頁面編輯區 -->
			<input type="hidden" id="modifyDashboardActFlag" name="modifyDashboardActFlag"></input>
			
			<table>
				<tr>
					<c:keyValue id="dashboard" name="dashboard" option="modifyDashboardOption" op="2" isRequired="true" valueReadOnly="false" header='<%=webutil.getMessage("modify-dashboard.DashboardIdName") %>' onChange="refreshValue(this);loadDashboardProperties();" />
				</tr>
				<tr>
					<c:text id="dashboardSortOrder" name="dashboardSortOrder" dataType="number" header='<%=webutil.getMessage("common.sort") %>' isReturnValue="false" />
				</tr>
				<tr>
					<td>
						<script src="<%=webutil.getBasePathForHTML()%>modify-dashboard.js"></script>
						<input type="hidden" id="ModifyDashboardSubmit" name="ModifyDashboardSubmit" />
                		<input id="saveDashboardButton" name="saveDashboardButton" type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="saveDashboard('dashboardForm', this, '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');" value="<%=webutil.getMessage("common.save")%>">
                		<input id="deleteDashboardButton" name="deleteDashboardButton" type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="deleteDashboard('dashboardForm', this, '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');" value="<%=webutil.getMessage("common.delete")%>">
					</td>
				</tr>
			</table>
			
			<!-- 頁面編輯區結束 -->
			
		</form>

