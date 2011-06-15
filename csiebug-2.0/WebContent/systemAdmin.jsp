<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/csiebug-ui" %>

<%@page import="csiebug.util.WebUtility"%>

<%WebUtility webutil = new WebUtility();%>

		<form id="systemAdminForm" name="systemAdminForm" action="<%=webutil.getBasePathForHTML()%>admin" method="POST">
			<!-- 程式標頭 -->
			<table width="100%">
				<tr>
					<td class="PageHeader"><%=webutil.getMessage("systemAdmin.FunctionName")%></td>
				</tr>
			</table>
			
			<!-- 以下為頁面編輯區 -->
			<input type="hidden" id="systemAdminActFlag" name="systemAdminActFlag"></input>
			<c:tab id="systemAdmin" name="systemAdmin" op="div" tabs="systemAdminTabs" defaultTab='<%=(String)webutil.getRequestAttribute("defaultTab")%>' />						
						
			<div id="systemLock" <%if(!((String)webutil.getRequestAttribute("defaultTab")).equalsIgnoreCase("systemLock")) {out.print("style=\"display:none\"");} %>>
				<% if(((String)webutil.getRequestAttribute("isServiceLock")).trim().equalsIgnoreCase("false")) { %>
					<input type="button" id="lockServiceButton" name="lockServiceButton" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="lockService('systemAdminForm', this);" value="<%=webutil.getMessage("systemAdmin.LockService") %>">
				<% } else { %>
					<input type="button" id="enableServiceButton" name="enableServiceButton" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="enableService('systemAdminForm', this);" value="<%=webutil.getMessage("systemAdmin.EnableService") %>">
				<% } %>
			</div>
			
			<div id="userManagement" <%if(!((String)webutil.getRequestAttribute("defaultTab")).equalsIgnoreCase("userManagement")) {out.print("style=\"display:none\"");} %>>
				<br></br>
				<table>
					<tr>
						<c:text id="username" name="username" header='<%=webutil.getMessage("common.account")%>' maxlength="50"></c:text>
						<td>
							<input id="searchButton" name="searchButton" type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="searchUser('systemAdminForm', this);" value="<%=webutil.getMessage("common.query")%>">
						</td>
					</tr>
				</table>
				
				<hr></hr>
				
				<input id="disableUserButton" name="disableUserButton" type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="disableUser('<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.confirm.disable") %>', '<%=webutil.getMessage("common.error.required4")%>', '<%=webutil.getMessage("common.ok")%>', '<%=webutil.getMessage("common.cancel") %>');" value="<%=webutil.getMessage("common.disable")%>">
				<input id="deleteUserButton" name="deleteUserButton" type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="deleteUser('<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.confirm.delete") %>', '<%=webutil.getMessage("common.error.required4")%>', '<%=webutil.getMessage("common.ok")%>', '<%=webutil.getMessage("common.cancel") %>');" value="<%=webutil.getMessage("common.delete")%>">
				<input type="hidden" id="users" name="users"></input>
				
				<c:table id="userGrid" name="userGrid" data="users" noDataStringFlag="true">
					<c:row>
						<c:column dataType="selAllCheckBox" />
						<c:column title='<%=webutil.getMessage("common.enable")%>'>
							<c:text id="newPassword_(var.id)" name="newPassword_(var.id)" dataType="password" maxlength="70" typesetting="false"></c:text><br></br>
							<input id="enableButton_(var.id)" name="enableButton_(var.id)" type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="enableUser('systemAdminForm', this, '(var.id)', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.password")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');" value="<%=webutil.getMessage("common.enable")%>">
						</c:column>
						<c:column fieldname="id" title='<%=webutil.getMessage("common.account")%>' sortable="true" />
						<c:column fieldname="enabled" title='<%=webutil.getMessage("common.enable")%>' sortable="true" />
						<c:column title='<%=webutil.getMessage("personalSetting.MajorEmail") %>'>
							<a href="mailto:(var.majorEmail)">(var.majorEmail)</a>
						</c:column>
						<c:column fieldname="createDate" title='<%=webutil.getMessage("systemAdmin.RegisterDate")%>' sortable="true" />
						<c:column fieldname="modifyDate" title='<%=webutil.getMessage("systemAdmin.LastLoginDate")%>' sortable="true" />
						<c:column fieldname="roleList" title='<%=webutil.getMessage("common.role") %>'>
							(var.roleList)<br>
							<input id="updateUserRoleButton_(var.id)" name="updateUserRoleButton_(var.id)" type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="updateUserRole('systemAdminForm', this, '(var.id)');" value="<%=webutil.getMessage("common.save")%>">
						</c:column>
						<c:column fieldname="resourceList" title='<%=webutil.getMessage("systemAdmin.availableResources") %>'>
							(var.resourceList)<br>
							<input id="updateUserResourceButton_(var.id)" name="updateUserResourceButton_(var.id)" type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="updateUserResource('systemAdminForm', this, '(var.id)');" value="<%=webutil.getMessage("common.save")%>">
						</c:column>
					</c:row>
				</c:table>
			</div>
			
			<div id="roleManagement" <%if(!((String)webutil.getRequestAttribute("defaultTab")).equalsIgnoreCase("roleManagement")) {out.print("style=\"display:none\"");} %>>
				<br></br>
				<table id="roleEditor" style="display:none">
					<tr>
						<c:text id="roleId" name="roleId" header='<%=webutil.getMessage("systemAdmin.RoleId") %>' isRequired="true" isReadOnly="true" isReturnValue="false"></c:text>
					</tr>
					<tr>
						<c:text id="roleName" name="roleName" header='<%=webutil.getMessage("systemAdmin.RoleName") %>' isReturnValue="false"></c:text>
					</tr>
					<tr>
						<c:multiselect id="roleResources" name="roleResources" header='<%=webutil.getMessage("systemAdmin.availableResources") %>' totalOption="resources" />
					</tr>
					<tr>
						<td>
							<input id="saveRoleButton" name="saveRoleButton" type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="saveRole('systemAdminForm', this, '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');" value="<%=webutil.getMessage("common.save")%>">
						</td>
					</tr>
				</table>
				
				<hr></hr>
				
				<input id="addRoleButton" name="addRoleButton" type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="addRole();" value="<%=webutil.getMessage("systemAdmin.AddRole")%>">
				<input id="cancelEditRoleButton" name="cancelEditRoleButton" type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="closeRoleEditor();" value="<%=webutil.getMessage("systemAdmin.CancelEditRole")%>">
				<input id="deleteRoleButton" name="deleteRoleButton" type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="deleteRole('<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.confirm.delete") %>', '<%=webutil.getMessage("common.error.required4")%>', '<%=webutil.getMessage("systemAdmin.cannotDeleteThisRole") %>', '<%=webutil.getMessage("common.ok")%>', '<%=webutil.getMessage("common.cancel") %>');" value="<%=webutil.getMessage("common.delete")%>">
				<input type="hidden" id="roles" name="roles"></input>
				
				<c:table id="roleGrid" name="roleGrid" data="roles" noDataStringFlag="true">
					<c:row selectable="true" onDblClick='<%=webutil.getRequestAttribute("onDblClickForRole").toString() %>'>
						<c:column dataType="selAllCheckBox" title='<%=webutil.getMessage("common.delete") %>' />
						<c:column fieldname="id" title='<%=webutil.getMessage("systemAdmin.RoleId") %>'></c:column>
						<c:column fieldname="roleName" title='<%=webutil.getMessage("systemAdmin.RoleName") %>'></c:column>
					</c:row>
				</c:table>
			</div>
			
			<div id="codeManagement" <%if(!((String)webutil.getRequestAttribute("defaultTab")).equalsIgnoreCase("codeManagement")) {out.print("style=\"display:none\"");} %>>
				<br></br>
				<table id="codeEditor" style="display:none">
					<tr>
						<c:text id="codeId" name="codeId" header='<%=webutil.getMessage("systemAdmin.CodeId") %>' isRequired="true" isReadOnly="true" isReturnValue="false"></c:text>
					</tr>
					<tr>
						<input type="hidden" id="codeTypeHidden" name="codeTypeHidden" isRequired="true"></input>
						<c:select id="codeType" name="codeType" header='<%=webutil.getMessage("systemAdmin.CodeType") %>' isRequired="true" option="codeTypeOption" isReadOnly="true"  isReturnValue="false" onChange="refreshCodeTypeHidden();" />
					</tr>
					<tr>
						<c:text id="codeValue" name="codeValue" header='<%=webutil.getMessage("systemAdmin.CodeValue") %>' isRequired="true" isReturnValue="false"></c:text>
					</tr>
					<tr>
						<c:textarea id="codeDescription" name="codeDescription" header='<%=webutil.getMessage("common.note") %>' isReturnValue="false"></c:textarea>
					</tr>
					<tr>
						<c:radiogroup id="codeEnabled" name="codeEnabled" header='<%=webutil.getMessage("common.enable") %>' option="codeEnableOption" isRequired="true" isReturnValue="false"/>
					</tr>
					<tr>
						<td>
							<input id="saveCodeButton" name="saveCodeButton" type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="saveCode('systemAdminForm', this, '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');" value="<%=webutil.getMessage("common.save")%>">
						</td>
					</tr>
				</table>
				
				<hr></hr>
				
				<input id="addCodeTypeButton" name="addCodeTypeButton" type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="addCodeType('<%=webutil.getRequestAttribute("codeTypeText") %>');" value="<%=webutil.getMessage("systemAdmin.AddCodeType")%>">
				<input id="addCodeButton" name="addCodeButton" type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="addCode();" value="<%=webutil.getMessage("systemAdmin.AddCode")%>">
				<input id="cancelEditCodeButton" name="cancelEditCodeButton" type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="closeCodeEditor();" value="<%=webutil.getMessage("systemAdmin.CancelEditCode")%>">
				<input id="deleteCodeButton" name="deleteCodeButton" type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="deleteCode('<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.confirm.delete") %>', '<%=webutil.getMessage("common.error.required4")%>', '<%=webutil.getMessage("common.ok")%>', '<%=webutil.getMessage("common.cancel") %>');" value="<%=webutil.getMessage("common.delete")%>">
				<input type="hidden" id="codes" name="codes"></input>
				
				<c:table id="codeGrid" name="codeGrid" data="codes" noDataStringFlag="true">
					<c:row selectable="true" onDblClick='<%=webutil.getRequestAttribute("onDblClickForCode").toString() %>'>
						<c:column dataType="selAllCheckBox" title='<%=webutil.getMessage("common.delete") %>' />
						<c:column fieldname="codeId" title='<%=webutil.getMessage("systemAdmin.CodeId") %>'></c:column>
						<c:column fieldname="codeType" style="display:none" />
						<c:column fieldname="codeTypeText" title='<%=webutil.getMessage("systemAdmin.CodeType") %>'></c:column>
						<c:column fieldname="codeValue" title='<%=webutil.getMessage("systemAdmin.CodeValue") %>'></c:column>
						<c:column fieldname="codeDescription" title='<%=webutil.getMessage("common.note") %>'></c:column>
						<c:column fieldname="enabled" title='<%=webutil.getMessage("common.enable") %>'></c:column>
					</c:row>
				</c:table>
			</div>
	 	 				
			<script src="<%=webutil.getBasePathForHTML()%>systemAdmin.js"></script>
			<!-- 頁面編輯區結束 -->
			
		</form>

