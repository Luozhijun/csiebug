<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/csiebug-ui" %>

<%@page import="csiebug.util.WebUtility"%>

<%WebUtility webutil = new WebUtility();%>

		<form id="personalSettingForm" name="personalSettingForm" action="<%=webutil.getBasePathForHTML()%>admin" method="POST">
			<!-- 程式標頭 -->
			<table width="100%">
				<tr>
					<td class="PageHeader"><%=webutil.getMessage("personalSetting.FunctionName")%></td>
				</tr>
			</table>
			
			<!-- 以下為頁面編輯區 -->
			<input type="hidden" id="personalSettingActFlag" name="personalSettingActFlag"></input>
			<table>
				<tr>
					<td class="TdHeader"><label class = "LabelHeader"><%=webutil.getMessage("common.account")%>：</label>
					&nbsp;&nbsp;</td>
					<td class="TdBody"><label class = "LabelHeader"><%=webutil.getLoginUserId()%></label></td>
					<c:text id="nickname" name="nickname" header='<%=webutil.getMessage("common.UserName") %>' userValue="nickname" />
				</tr>
				<tr>
					<td class="TdHeader"><label class = "LabelHeader"><%=webutil.getMessage("systemAdmin.RegisterDate")%>：</label>
					&nbsp;&nbsp;</td>
					<td class="TdBody"><label class = "LabelHeader"><%=webutil.getRequestAttribute("registerDate")%></label></td>
					<td class="TdHeader"><label class = "LabelHeader"><%=webutil.getMessage("systemAdmin.LastLoginDate")%>：</label>
					&nbsp;&nbsp;</td>
					<td class="TdBody"><label class = "LabelHeader"><%=webutil.getRequestAttribute("lastLoginDate")%></label></td>
				</tr>
				<tr>
					<c:select id="majorEmail" name="majorEmail" header='<%=webutil.getMessage("personalSetting.MajorEmail") %>' option="emailOption" userValue="majorEmail" />
					<c:multitext id="emails" name="emails" dataType="email" header='<%=webutil.getMessage("common.email") %>' userValue="emailList" onChange="refreshMajorEmailOption();" />
				</tr>
				<tr>
					<c:select id="locale" name="locale" header='<%=webutil.getMessage("common.locale") %>' option="localeOption" userValue="userLocale" optionClass="flag" />
				</tr>
				<tr id="modifyPassword" style="display:none">
					<c:text id="password" name="password" header='<%=webutil.getMessage("common.password")%>' maxlength="70" isRequired="true" dataType="password" />
					<c:text id="confirm_password" name="confirm_password" header='<%=webutil.getMessage("common.confirmPassword")%>' maxlength="70" isRequired="true" dataType="password" />
				</tr>
				<tr>
					<td>
						<input id="saveUserButton" name="saveUserButton" type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="saveUser();" value="<%=webutil.getMessage("common.save")%>">
						<input id="modifyPasswordButton" name="modifyPasswordButton" type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="modifyPassword('personalSettingForm', this, '<%=webutil.getMessage("personalSetting.ModifyPassword") %>', '<%=webutil.getMessage("personalSetting.SavePassword") %>', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>', '<%=webutil.getMessage("common.AuthenticationFailure") %>');" value="<%=webutil.getMessage("personalSetting.ModifyPassword")%>">
					</td>
				</tr>
			</table>
			
			<script src="<%=webutil.getBasePathForHTML()%>personalSetting.js"></script>
			<!-- 頁面編輯區結束 -->
			
		</form>

