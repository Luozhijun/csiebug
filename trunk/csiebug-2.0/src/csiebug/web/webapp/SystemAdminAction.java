/**
 * Copyright (c) 2010 George Tsai. All rights reserved.
 * For more information on the George Tsai, please see http://www.pixnet.net/blog/csiebug
 */
package csiebug.web.webapp;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;

import csiebug.domain.Code;
import csiebug.domain.Role;
import csiebug.domain.User;
import csiebug.service.CodeService;
import csiebug.service.RoleService;
import csiebug.service.ServiceException;
import csiebug.service.UserService;
import csiebug.util.DateFormatException;
import csiebug.util.ListException;
import csiebug.util.ListUtility;
import csiebug.util.ShaEncoder;
import csiebug.util.StringUtility;
import csiebug.util.ldap.LdapClient;
import csiebug.web.html.HtmlRenderException;
import csiebug.web.html.form.HtmlMultiText;

/**
 * @author George_Tsai
 * @version 2010/9/17
 */
public class SystemAdminAction {
	private PortletAdminAction action;
	
	public SystemAdminAction(PortletAdminAction action) {
		this.action = action;
	}
	
	public void main() throws NamingException, ServiceException, NoSuchAlgorithmException, UnsupportedEncodingException {
		String actFlag = action.getRequestValue("systemAdminActFlag");
		
		if(actFlag.equalsIgnoreCase("systemAdminLockService")) {
			action.lockService();
	    } else if(actFlag.equalsIgnoreCase("systemAdminEnableService")) {
	    	action.enableService();
	    } else if(actFlag.equalsIgnoreCase("systemAdminEnableUser")) {
	    	enableUser();
	    } else if(actFlag.equalsIgnoreCase("systemAdminDisableUser")) {
	    	disableUser();
	    } else if(actFlag.equalsIgnoreCase("systemAdminDeleteUser")) {
	    	deleteUser();
	    } else if(actFlag.equalsIgnoreCase("systemAdminUpdateUserRole")) {
	    	updateUserRole();
	    } else if(actFlag.equalsIgnoreCase("systemAdminSaveRole")) {
	    	saveRole();
	    } else if(actFlag.equalsIgnoreCase("systemAdminDeleteRole")) {
	    	deleteRole();
	    } else if(actFlag.equalsIgnoreCase("systemAdminSaveCode")) {
	    	saveCode();
	    } else if(actFlag.equalsIgnoreCase("systemAdminDeleteCode")) {
	    	deleteCode();
	    }
	}
	
	public void makeControl() throws ServiceException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NamingException, DateFormatException, UnsupportedEncodingException, ListException, HtmlRenderException {
		Map<String, String> tabs = new LinkedHashMap<String, String>();
		tabs.put(action.getMessage("systemAdmin.tab.SystemLock"), "systemLock");
		tabs.put(action.getMessage("systemAdmin.tab.UserManagement"), "userManagement");
		tabs.put(action.getMessage("systemAdmin.tab.RoleManagement"), "roleManagement");
		tabs.put(action.getMessage("systemAdmin.tab.CodeManagement"), "codeManagement");
		action.setRequestAttribute("systemAdminTabs", tabs);
		
		String actFlag = action.getRequestValue("systemAdminActFlag");
		String tabId = "systemLock";
		if(actFlag.equalsIgnoreCase("systemAdminEnableUser")) {
	    	tabId = "userManagement";
	    } else if(actFlag.equalsIgnoreCase("systemAdminDisableUser")) {
	    	tabId = "userManagement";
	    } else if(actFlag.equalsIgnoreCase("systemAdminSearchUser")) {
	    	tabId = "userManagement";
	    } else if(actFlag.equalsIgnoreCase("systemAdminDeleteUser")) {
	    	tabId = "userManagement";
	    } else if(actFlag.equalsIgnoreCase("systemAdminUpdateUserRole")) {
	    	tabId = "userManagement";
	    } else if(actFlag.equalsIgnoreCase("systemAdminSaveRole")) {
	    	tabId = "roleManagement";
	    } else if(actFlag.equalsIgnoreCase("systemAdminDeleteRole")) {
	    	tabId = "roleManagement";
	    } else if(actFlag.equalsIgnoreCase("systemAdminSaveCode")) {
	    	tabId = "codeManagement";
	    } else if(actFlag.equalsIgnoreCase("systemAdminDeleteCode")) {
	    	tabId = "codeManagement";
	    }
		
		action.setRequestAttribute("defaultTab", tabId);
		
		makeSystemLockControl();
		makeUserManagementControl();
		makeRoleManagementControl();
		makeCodeManagementControl();
	}
	
	private void makeSystemLockControl() {
		action.setRequestAttribute("isServiceLock", "" + action.isServiceLock());
	}
	
	private void makeUserManagementControl() throws ServiceException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NamingException, DateFormatException, ListException, HtmlRenderException {
		User voObj = action.getDomainObjectFactory().createUser();
		
		if(action.getRequestValue("systemAdminActFlag").equalsIgnoreCase("systemAdminSearchUser")) {
			voObj.setId(action.getRequestValue("username"));
		}
		
		List<User> users = action.getUserService().searchUsers(voObj);
		//轉型
		List<Map<String, String>> list = ListUtility.castList(ListUtility.toList(users, User.class), Integer.parseInt(action.getEnvVariable("defaultDateFormat")));
		
		//join role data
		List<Map<String, String>> roleList = new ArrayList<Map<String,String>>();
		for(int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("id", user.getId());
			
			StringBuffer roleId = new StringBuffer();
			Iterator<Role> iterator = user.getAuthorities().iterator();
			while(iterator.hasNext()) {
				Role role = iterator.next();
				
				if(!roleId.toString().equals("")) {
					roleId.append(";");
				}
				
				roleId.append(role.getId());
			}
			
			HtmlMultiText multiText = new HtmlMultiText();
			multiText.setId("roles_" + user.getId());
			multiText.setName("roles_" + user.getId());
			multiText.setTypesetting("false");
			action.setRequestAttribute("roleList_" + user.getId(), roleId.toString());
			multiText.setUserValue("roleList_" + user.getId());
			
			map.put("roleList", multiText.render());
			
			roleList.add(map);
		}
		list = ListUtility.leftJoin(list, roleList, new String[]{"id"});
		
		action.setRequestAttribute("users", list);
	}
	
	private void makeRoleManagementControl() throws ServiceException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NamingException, DateFormatException, ListException, HtmlRenderException {
		Role voObj = action.getDomainObjectFactory().createRole();
		List<Role> roles = action.getRoleService().searchRoles(voObj);
		//轉型
		List<Map<String, String>> list = ListUtility.castList(ListUtility.toList(roles, Role.class), Integer.parseInt(action.getEnvVariable("defaultDateFormat")));
		//過濾掉不可編輯刪除的role
		list = ListUtility.getSubListByNameExcludeValue(list, "id", new String[]{"ROLE_USER", "admin"});
		action.setRequestAttribute("roles", list);
		
		action.setRequestAttribute("onDblClickForRole", "selectToEditForRole('(var.id)', '(var.roleName)');");
		
	}
	
	private void makeCodeManagementControl() throws ServiceException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NamingException, DateFormatException, UnsupportedEncodingException {
		Map<String, String> codeTypeOption = action.getCodeService().getCodeTypes(Integer.parseInt(action.getEnvVariable("defaultDateFormat")));
		action.setRequestAttribute("codeTypeOption", codeTypeOption);
		
		Map<String, String> codeEnableOption = new LinkedHashMap<String, String>();
		codeEnableOption.put("true", action.getMessage("common.true"));
		codeEnableOption.put("false", action.getMessage("common.false"));
		action.setRequestAttribute("codeEnableOption", codeEnableOption);
		
		Code voObj = action.getDomainObjectFactory().createCode();
		List<Code> codes = action.getCodeService().searchCodes(voObj);
		//轉型
		List<Map<String, String>> list = ListUtility.castList(ListUtility.toList(codes, Code.class), Integer.parseInt(action.getEnvVariable("defaultDateFormat")));
		//過濾掉不可編輯刪除的code
		list = ListUtility.getSubListByNameExcludeValue(list, "codeId", "CodeType");
		//複製欄位
		list = ListUtility.copyField(list, "codeType", "codeTypeText");
		//取代欄位值
		list = ListUtility.replaceField(list, "codeTypeText", codeTypeOption);
		//排序
		String[] fieldNames = new String[]{"codeType", "codeId"};
		list = ListUtility.sortByMultipleName(list, fieldNames);
		action.setRequestAttribute("codes", list);
		
		voObj = action.getDomainObjectFactory().createCode();
		voObj.setCodeId("CodeType");
		voObj.setCodeType("CodeType");
		Code codeType = action.getCodeService().searchCodes(voObj).get(0);
		action.setRequestAttribute("codeTypeText", codeType.getCodeValue());
		action.setRequestAttribute("onDblClickForCode", "selectToEditForCode('" + codeType.getCodeValue() + "', '(var.codeId)', '(var.codeType)', '(var.codeValue)', '(var.codeDescription)', '(var.enabled)');");
	}
	
	@SuppressWarnings("deprecation")
	private void enableUser() throws NamingException, ServiceException, NoSuchAlgorithmException, UnsupportedEncodingException {
		String[] users = action.getRequestValue("users").split(",");
		
		for(int i = 0; i < users.length; i++) {
			UserService userService = action.getUserService();
			User voObj = action.getDomainObjectFactory().createUser();
			voObj.setId(users[i]);
			
			User user = userService.searchUsers(voObj).get(0);
			user.setEnabled(Boolean.TRUE);
			
			userService.saveUser(user);
			
			LdapClient client = new LdapClient(action.getEnvVariable("ldapURL"), action.getEnvVariable("ldapAdmin"), action.getEnvVariable("ldapPassword"));
			Attributes attributes = new BasicAttributes();
			
			//TODO 這邊應該是把密碼(被disable後,系統給的random密碼)從LDAP取出後,用e-mail寄給那個使用者
			//但是沒有mail server可以寄信,所以還是讓系統管理者輸入密碼
			attributes.put("userpassword", "{SHA}" + ShaEncoder.getSHA1Base64(action.getRequestValue("newPassword_" + user.getId())));
			
			client.modifyAttributes("uid=" + users[i] + ",ou=csiebug", attributes);
		}
	}
	
	private void disableUser() throws NamingException, ServiceException {
		String[] users = action.getRequestValue("users").split(",");
		
		for(int i = 0; i < users.length; i++) {
			UserService userService = action.getUserService();
			User voObj = action.getDomainObjectFactory().createUser();
			voObj.setId(users[i]);
			
			User user = userService.searchUsers(voObj).get(0);
			user.setEnabled(Boolean.FALSE);
			
			userService.saveUser(user);
			
			LdapClient client = new LdapClient(action.getEnvVariable("ldapURL"), action.getEnvVariable("ldapAdmin"), action.getEnvVariable("ldapPassword"));
			Attributes attributes = new BasicAttributes();
			
			//TODO 因為LDAP在disable user上沒有統一的做法,AD有disable user的功能,但其他LDAP server不一定有
			//所以這邊用程式塞random的密碼,來取代disable的功能
			attributes.put("userpassword", StringUtility.makeRandomMixKey(8));
			
			client.modifyAttributes("uid=" + users[i] + ",ou=csiebug", attributes);
		}
	}
	
	private void deleteUser() throws NamingException, ServiceException, NoSuchAlgorithmException, UnsupportedEncodingException {
		String[] users = action.getRequestValue("users").split(",");
		
		for(int i = 0; i < users.length; i++) {
			UserService userService = action.getUserService();
			User voObj = action.getDomainObjectFactory().createUser();
			voObj.setId(users[i]);
			
			User user = userService.searchUsers(voObj).get(0);
			userService.deleteUser(user);
			
			LdapClient client = new LdapClient(action.getEnvVariable("ldapURL"), action.getEnvVariable("ldapAdmin"), action.getEnvVariable("ldapPassword"));
			client.deleteUser("ou=csiebug", users[i]);
		}
	}
	
	private void updateUserRole() throws NamingException, ServiceException {
		String[] users = action.getRequestValue("users").split(",");
		
		for(int i = 0; i < users.length; i++) {
			UserService userService = action.getUserService();
			User voUserObj = action.getDomainObjectFactory().createUser();
			voUserObj.setId(users[i]);
			
			String[] roles = action.getRequestValue("roles_" + users[i]).split(";");
			
			User user = userService.searchUsers(voUserObj).get(0);
			Object[] originalRoles = user.getAuthorities().toArray();
			for(int j = 0; j < originalRoles.length; j++) {
				Role role = (Role)originalRoles[j];
				
				if(!role.getId().equalsIgnoreCase("ROLE_USER")) {
					user.removeAuthority(role);
					role.removeAuthority(user);
				}
			}
			
			userService.saveUser(user);
			
			for(int j = 0; j < roles.length; j++) {
				Role voRoleObj = action.getDomainObjectFactory().createRole();
				voRoleObj.setId(roles[j]);
				
				if(!roles[j].equalsIgnoreCase("ROLE_USER")) {
					List<Role> list = action.getRoleService().searchRoles(voRoleObj);
					if(list.size() == 1) {
						Role role = list.get(0);
						user.addAuthority(role);
					}
				}
			}
			
			userService.saveUser(user);
		}
	}
	
	private void saveRole() throws NamingException, ServiceException {
		String roleId = action.getRequestValue("roleId");
		String roleName = action.getRequestValue("roleName");
		
		Role voObj = action.getDomainObjectFactory().createRole();
		voObj.setId(roleId);
		voObj.setRoleName(roleName);
		List<Role> list = action.getRoleService().searchRoles(voObj);
		
		Role role;
		if(list.size() > 0) {
			role = list.get(0);
			role.setModifyUserId(action.getLoginUserId());
			role.setModifyDate(Calendar.getInstance());
		} else {
			role = action.getDomainObjectFactory().createRole();
			role.setId(roleId);
			role.setRoleName(roleName);
			role.setCreateUserId(action.getLoginUserId());
			role.setCreateDate(Calendar.getInstance());
		}
		
		action.getRoleService().saveRole(role);
	}
	
	private void deleteRole() throws NamingException, ServiceException, NoSuchAlgorithmException, UnsupportedEncodingException {
		String[] roles = action.getRequestValue("roles").split(",");
		
		for(int i = 0; i < roles.length; i++) {
			RoleService roleService = action.getRoleService();
			Role voObj = action.getDomainObjectFactory().createRole();
			voObj.setId(roles[i]);
			
			Role role = roleService.searchRoles(voObj).get(0);
			roleService.deleteRole(role);
		}
	}
	
	private void saveCode() throws NamingException, ServiceException {
		String codeId = action.getRequestValue("codeId");
		String codeType = "";
		if(action.getRequestValue("codeType").trim().equals("")) {
			codeType = action.getRequestValue("codeTypeHidden");
		} else {
			codeType = action.getRequestValue("codeType");
		}
		
		Code voObj = action.getDomainObjectFactory().createCode();
		voObj.setCodeId(codeId);
		voObj.setCodeType(codeType);
		List<Code> list = action.getCodeService().searchCodes(voObj);
		
		Code code;
		if(list.size() > 0) {
			code = list.get(0);
			code.setModifyUserId(action.getLoginUserId());
			code.setModifyDate(Calendar.getInstance());
		} else {
			code = action.getDomainObjectFactory().createCode();
			code.setCodeId(codeId);
			code.setCodeType(codeType);
			code.setCreateUserId(action.getLoginUserId());
			code.setCreateDate(Calendar.getInstance());
		}
		code.setCodeValue(action.getRequestValue("codeValue"));
		code.setCodeDescription(action.getRequestValue("codeDescription"));
		code.setEnabled(Boolean.parseBoolean(action.getRequestValue("codeEnabled")));
		
		action.getCodeService().saveCode(code);
	}
	
	private void deleteCode() throws NamingException, ServiceException, NoSuchAlgorithmException, UnsupportedEncodingException {
		String[] codes = action.getRequestValue("codes").split(",");
		
		for(int i = 0; i < codes.length; i++) {
			CodeService codeService = action.getCodeService();
			Code voObj = action.getDomainObjectFactory().createCode();
			voObj.setCodeId(codes[i].split(";")[0]);
			voObj.setCodeType(codes[i].split(";")[1]);
			
			Code code = codeService.searchCodes(voObj).get(0);
			codeService.deleteCode(code);
		}
	}
}
