/**
 * Copyright (c) 2010 George Tsai. All rights reserved.
 * For more information on the George Tsai, please see http://www.pixnet.net/blog/csiebug
 */
package csiebug.web.webapp;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;

import org.apache.commons.codec.DecoderException;

import csiebug.domain.Code;
import csiebug.domain.Resource;
import csiebug.domain.Role;
import csiebug.domain.User;
import csiebug.service.CodeService;
import csiebug.service.ResourceService;
import csiebug.service.RoleService;
import csiebug.service.ServiceException;
import csiebug.service.UserService;
import csiebug.util.DateFormatException;
import csiebug.util.DesCoder;
import csiebug.util.ListException;
import csiebug.util.ListUtility;
import csiebug.util.ShaEncoder;
import csiebug.util.StringUtility;
import csiebug.util.ldap.LdapClient;
import csiebug.web.html.HtmlRenderException;
import csiebug.web.html.form.HtmlMultiSelect;
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
	
	public void main() throws NamingException, ServiceException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, DecoderException {
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
	    	deleteUsers();
	    } else if(actFlag.equalsIgnoreCase("systemAdminUpdateUserRole")) {
	    	updateUserRole();
	    } else if(actFlag.equalsIgnoreCase("systemAdminUpdateUserResource")) {
	    	updateUserResource();
	    } else if(actFlag.equalsIgnoreCase("systemAdminSaveRole")) {
	    	saveRole();
	    } else if(actFlag.equalsIgnoreCase("systemAdminDeleteRole")) {
	    	deleteRoles();
	    } else if(actFlag.equalsIgnoreCase("systemAdminUpdateResourceRole")) {
	    	updateResourceRole();
	    } else if(actFlag.equalsIgnoreCase("systemAdminUpdateResourceUser")) {
	    	updateResourceUser();
	    } else if(actFlag.equalsIgnoreCase("systemAdminSaveCode")) {
	    	saveCode();
	    } else if(actFlag.equalsIgnoreCase("systemAdminDeleteCode")) {
	    	deleteCodes();
	    }
	}
	
	public void makeControl() throws ServiceException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NamingException, DateFormatException, UnsupportedEncodingException, ListException, HtmlRenderException {
		Map<String, String> tabs = new LinkedHashMap<String, String>();
		tabs.put(action.getMessage("systemAdmin.tab.SystemLock"), "systemLock");
		tabs.put(action.getMessage("systemAdmin.tab.UserManagement"), "userManagement");
		tabs.put(action.getMessage("systemAdmin.tab.RoleManagement"), "roleManagement");
		tabs.put(action.getMessage("systemAdmin.tab.ResourceManagement"), "resourceManagement");
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
	    } else if(actFlag.equalsIgnoreCase("systemAdminUpdateUserResource")) {
	    	tabId = "userManagement";
	    } else if(actFlag.equalsIgnoreCase("systemAdminSaveRole")) {
	    	tabId = "roleManagement";
	    } else if(actFlag.equalsIgnoreCase("systemAdminDeleteRole")) {
	    	tabId = "roleManagement";
	    } else if(actFlag.equalsIgnoreCase("systemAdminUpdateResourceRole")) {
	    	tabId = "resourceManagement";
	    } else if(actFlag.equalsIgnoreCase("systemAdminUpdateResourceUser")) {
	    	tabId = "resourceManagement";
	    } else if(actFlag.equalsIgnoreCase("systemAdminSaveCode")) {
	    	tabId = "codeManagement";
	    } else if(actFlag.equalsIgnoreCase("systemAdminDeleteCode")) {
	    	tabId = "codeManagement";
	    }
		
		action.setRequestAttribute("defaultTab", tabId);
		
		makeSystemLockControl();
		makeTotalResourceOption();
		makeTotalRoleOption();
		makeTotalUserOption();
		makeUserManagementControl();
		makeRoleManagementControl();
		makeResourceManagementControl();
		makeCodeManagementControl();
	}
	
	private void makeSystemLockControl() {
		action.setRequestAttribute("isServiceLock", "" + action.isServiceLock());
	}
	
	private void makeTotalResourceOption() throws ServiceException, NumberFormatException, IllegalArgumentException, DateFormatException, IllegalAccessException, InvocationTargetException, NamingException {
		Resource voResource = action.getDomainObjectFactory().createResource();
		List<Resource> resources = action.getResourceService().searchResources(voResource);
		//轉型
		List<Map<String, String>> resourceList = ListUtility.castList(ListUtility.toList(resources, Resource.class), Integer.parseInt(action.getEnvVariable("defaultDateFormat")));
		action.setRequestAttribute("totalResourceOption", ListUtility.toMap(resourceList, "id", "id"));
	}
	
	private void makeTotalRoleOption() throws ServiceException, NumberFormatException, IllegalArgumentException, DateFormatException, IllegalAccessException, InvocationTargetException, NamingException {
		Role voRole = action.getDomainObjectFactory().createRole();
		List<Role> roles = action.getRoleService().searchRoles(voRole);
		//轉型
		List<Map<String, String>> roleList = ListUtility.castList(ListUtility.toList(roles, Role.class), Integer.parseInt(action.getEnvVariable("defaultDateFormat")));
		action.setRequestAttribute("totalRoleOption", ListUtility.toMap(roleList, "id", "id"));
	}
	
	private void makeTotalUserOption() throws ServiceException, NumberFormatException, IllegalArgumentException, DateFormatException, IllegalAccessException, InvocationTargetException, NamingException {
		User voUser = action.getDomainObjectFactory().createUser();
		List<User> users = action.getUserService().searchUsers(voUser);
		//轉型
		List<Map<String, String>> userList = ListUtility.castList(ListUtility.toList(users, User.class), Integer.parseInt(action.getEnvVariable("defaultDateFormat")));
		action.setRequestAttribute("totalUserOption", ListUtility.toMap(userList, "id", "id"));
	}
	
	private void makeUserManagementControl() throws ServiceException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NamingException, DateFormatException, ListException, HtmlRenderException {
		User voObj = action.getDomainObjectFactory().createUser();
		
		if(action.getRequestValue("systemAdminActFlag").equalsIgnoreCase("systemAdminSearchUser")) {
			voObj.setId(action.getRequestValue("username"));
		}
		
		List<User> users = action.getUserService().searchUsers(voObj);
		//轉型
		List<Map<String, String>> list = ListUtility.castList(ListUtility.toList(users, User.class), Integer.parseInt(action.getEnvVariable("defaultDateFormat")));
		
		//join role and resource data
		List<Map<String, String>> roleResourceList = new ArrayList<Map<String,String>>();
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
			
			StringBuffer resourceId = new StringBuffer();
			Iterator<Resource> iteratorResource = user.getUserResources().iterator();
			while(iteratorResource.hasNext()) {
				if(!resourceId.toString().equals("")) {
					resourceId.append(",");
				}
				
				resourceId.append(iteratorResource.next().getId());
			}
			
			HtmlMultiSelect multiSelect = new HtmlMultiSelect();
			multiSelect.setId("resource_" + user.getId());
			multiSelect.setName("resource_" + user.getId());
			multiSelect.setTypesetting("false");
			multiSelect.setTotalOption("totalResourceOption");
			action.setRequestAttribute("resourceList_" + user.getId(), resourceId.toString());
			multiSelect.setUserValue("resourceList_" + user.getId());
			
			map.put("resourceList", multiSelect.render());
			
			roleResourceList.add(map);
		}
		list = ListUtility.leftJoin(list, roleResourceList, new String[]{"id"});
		
		action.setRequestAttribute("users", list);
	}
	
	private void makeRoleManagementControl() throws ServiceException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NamingException, DateFormatException, ListException, HtmlRenderException {
		Role voRole = action.getDomainObjectFactory().createRole();
		List<Role> roles = action.getRoleService().searchRoles(voRole);
		List<Map<String, String>> roleResourceList = new ArrayList<Map<String,String>>();
		for(int i = 0; i < roles.size(); i++) {
			Role role = roles.get(i);
			
			Iterator<Resource> iterator = role.getAuthorityResources().iterator();
			StringBuffer roleResources = new StringBuffer();
			int j = 0;
			while(iterator.hasNext()) {
				if(j != 0) {
					roleResources.append(",");
				} else {
					j = 1;
				}
				
				roleResources.append(iterator.next().getId());
			}
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("resources", roleResources.toString());
			roleResourceList.add(map);
		}
		//轉型
		List<Map<String, String>> roleList = ListUtility.castList(ListUtility.toList(roles, Role.class), Integer.parseInt(action.getEnvVariable("defaultDateFormat")));
		//join角色的資源
		roleList = ListUtility.join(roleList, roleResourceList);
		//過濾掉不可編輯刪除的role
		//roleList = ListUtility.getSubListByNameExcludeValue(roleList, "id", new String[]{"ROLE_USER", "admin"});
		action.setRequestAttribute("roles", roleList);
		
		action.setRequestAttribute("onDblClickForRole", "selectToEditForRole('(var.id)', '(var.roleName)', '(var.resources)');");
	}
	
	private void makeResourceManagementControl() throws ServiceException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NamingException, DateFormatException, ListException, HtmlRenderException {
		Resource voObj = action.getDomainObjectFactory().createResource();
		List<Resource> resources = action.getResourceService().searchResources(voObj);
		//轉型
		List<Map<String, String>> list = ListUtility.castList(ListUtility.toList(resources, Resource.class), Integer.parseInt(action.getEnvVariable("defaultDateFormat")));
		
		//join role and user data
		List<Map<String, String>> roleUserList = new ArrayList<Map<String,String>>();
		for(int i = 0; i < resources.size(); i++) {
			Resource resource = resources.get(i);
			
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("id", resource.getId());
			map.put("resourceType", resource.getResourceType().toString());
			
			StringBuffer roleId = new StringBuffer();
			Iterator<Role> iterator = resource.getAuthorityResources().iterator();
			while(iterator.hasNext()) {
				Role role = iterator.next();
				
				if(!roleId.toString().equals("")) {
					roleId.append(",");
				}
				
				roleId.append(role.getId());
			}
			
			HtmlMultiSelect multiSelectRole = new HtmlMultiSelect();
			multiSelectRole.setId("roles_" + resource.getId());
			multiSelectRole.setName("roles_" + resource.getId());
			multiSelectRole.setTypesetting("false");
			multiSelectRole.setTotalOption("totalRoleOption");
			action.setRequestAttribute("roleList_" + resource.getId(), roleId.toString());
			multiSelectRole.setUserValue("roleList_" + resource.getId());
			
			map.put("roleList", multiSelectRole.render());
			
			StringBuffer userId = new StringBuffer();
			Iterator<User> iteratorUser = resource.getUserResources().iterator();
			while(iteratorUser.hasNext()) {
				if(!userId.toString().equals("")) {
					userId.append(",");
				}
				
				userId.append(iteratorUser.next().getId());
			}
			
			HtmlMultiSelect multiSelectUser = new HtmlMultiSelect();
			multiSelectUser.setId("users_" + resource.getId());
			multiSelectUser.setName("users_" + resource.getId());
			multiSelectUser.setTypesetting("false");
			multiSelectUser.setTotalOption("totalUserOption");
			action.setRequestAttribute("userList_" + resource.getId(), userId.toString());
			multiSelectUser.setUserValue("userList_" + resource.getId());
			
			map.put("userList", multiSelectUser.render());
			
			roleUserList.add(map);
		}
		list = ListUtility.leftJoin(list, roleUserList, new String[]{"id"});
		
		action.setRequestAttribute("resourceGrid", list);
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
	private void enableUser() throws NamingException, ServiceException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, DecoderException {
		String[] users = action.getRequestValue("users").split(",");
		
		for(int i = 0; i < users.length; i++) {
			UserService userService = action.getUserService();
			User voObj = action.getDomainObjectFactory().createUser();
			voObj.setId(users[i]);
			
			User user = userService.searchUsers(voObj).get(0);
			user.setEnabled(Boolean.TRUE);
			
			userService.saveUser(user);
			
			LdapClient client = new LdapClient(action.getEnvVariable("ldapURL"), action.getEnvVariable("ldapAdmin"), DesCoder.decryptCode(action.getEnvVariable("ldapEncryptPassword"), action.getEnvVariable("ldapKey")));
			Attributes attributes = new BasicAttributes();
			
			//TODO 這邊應該是把密碼(被disable後,系統給的random密碼)從LDAP取出後,用e-mail寄給那個使用者
			//但是沒有mail server可以寄信,所以還是讓系統管理者輸入密碼
			attributes.put("userpassword", "{SHA}" + ShaEncoder.getSHA1Base64(action.getRequestValue("newPassword_" + user.getId())));
			
			client.modifyAttributes("uid=" + users[i] + ",ou=csiebug", attributes);
		}
	}
	
	private void disableUser() throws NamingException, ServiceException, InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, DecoderException {
		String[] users = action.getRequestValue("users").split(",");
		
		for(int i = 0; i < users.length; i++) {
			UserService userService = action.getUserService();
			User voObj = action.getDomainObjectFactory().createUser();
			voObj.setId(users[i]);
			
			User user = userService.searchUsers(voObj).get(0);
			user.setEnabled(Boolean.FALSE);
			
			userService.saveUser(user);
			
			LdapClient client = new LdapClient(action.getEnvVariable("ldapURL"), action.getEnvVariable("ldapAdmin"), DesCoder.decryptCode(action.getEnvVariable("ldapEncryptPassword"), action.getEnvVariable("ldapKey")));
			Attributes attributes = new BasicAttributes();
			
			//TODO 因為LDAP在disable user上沒有統一的做法,AD有disable user的功能,但其他LDAP server不一定有
			//所以這邊用程式塞random的密碼,來取代disable的功能
			attributes.put("userpassword", StringUtility.makeRandomMixKey(8));
			
			client.modifyAttributes("uid=" + users[i] + ",ou=csiebug", attributes);
		}
	}
	
	private void deleteUsers() throws NamingException, ServiceException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, DecoderException {
		String[] users = action.getRequestValue("users").split(",");
		
		for(int i = 0; i < users.length; i++) {
			UserService userService = action.getUserService();
			User voObj = action.getDomainObjectFactory().createUser();
			voObj.setId(users[i]);
			
			User user = userService.searchUsers(voObj).get(0);
			userService.deleteUser(user);
			
			LdapClient client = new LdapClient(action.getEnvVariable("ldapURL"), action.getEnvVariable("ldapAdmin"), DesCoder.decryptCode(action.getEnvVariable("ldapEncryptPassword"), action.getEnvVariable("ldapKey")));
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
	
	private void updateUserResource() throws NamingException, ServiceException {
		String[] users = action.getRequestValue("users").split(",");
		
		for(int i = 0; i < users.length; i++) {
			UserService userService = action.getUserService();
			User voUserObj = action.getDomainObjectFactory().createUser();
			voUserObj.setId(users[i]);
			
			String[] resources = action.getRequestValue("resource_" + users[i]).split(",");
			
			User user = userService.searchUsers(voUserObj).get(0);
			Object[] originalResources = user.getUserResources().toArray();
			for(int j = 0; j < originalResources.length; j++) {
				Resource resource = (Resource)originalResources[j];
				
				user.removeUserResource(resource);
				resource.removeUserResource(user);
			}
			
			userService.saveUser(user);
			
			for(int j = 0; j < resources.length; j++) {
				Resource voResourceObj = action.getDomainObjectFactory().createResource();
				voResourceObj.setId(resources[j]);
				
				List<Resource> list = action.getResourceService().searchResources(voResourceObj);
				if(list.size() == 1) {
					Resource resource = list.get(0);
					user.addUserResource(resource);
				}
			}
			
			userService.saveUser(user);
		}
	}
	
	private void saveRole() throws NamingException, ServiceException {
		String roleId = action.getRequestValue("roleId");
		String roleName = action.getRequestValue("roleName");
		String[] unSelectResources = action.getRequestValue("roleResources_unselect").split(",");
		String[] selectResources = action.getRequestValue("roleResources").split(",");
		
		Role voRole = action.getDomainObjectFactory().createRole();
		voRole.setId(roleId);
		List<Role> list = action.getRoleService().searchRoles(voRole);
		
		Role role;
		if(list.size() > 0) {
			role = list.get(0);
			role.setRoleName(roleName);
			role.setModifyUserId(action.getLoginUserId());
			role.setModifyDate(Calendar.getInstance());
			
			List<Resource> addResources = new ArrayList<Resource>();
			List<Resource> removeResources = new ArrayList<Resource>();
			Resource voResource = action.getDomainObjectFactory().createResource();
			List<Resource> allResources = action.getResourceService().searchResources(voResource);
			for(int i = 0; i < allResources.size(); i++) {
				Resource resource = allResources.get(i);
				
				if(StringUtility.isInArray(resource.getId(), selectResources)) {
					addResources.add(resource);
				}
				
				if(StringUtility.isInArray(resource.getId(), unSelectResources)) {
					removeResources.add(resource);
				}
			}
			
			for(int i = 0; i < addResources.size(); i++) {
				role.addAuthorityResource(addResources.get(i));
			}
			
			for(int i = 0; i < removeResources.size(); i++) {
				role.removeAuthorityResource(removeResources.get(i));
			}
		} else {
			role = action.getDomainObjectFactory().createRole();
			role.setId(roleId);
			role.setRoleName(roleName);
			role.setCreateUserId(action.getLoginUserId());
			role.setCreateDate(Calendar.getInstance());
			
			List<Resource> addResources = new ArrayList<Resource>();
			Resource voResource = action.getDomainObjectFactory().createResource();
			List<Resource> allResources = action.getResourceService().searchResources(voResource);
			for(int i = 0; i < allResources.size(); i++) {
				Resource resource = allResources.get(i);
				
				if(StringUtility.isInArray(resource.getId(), selectResources)) {
					addResources.add(resource);
				}
			}
			
			for(int i = 0; i < addResources.size(); i++) {
				role.addAuthorityResource(addResources.get(i));
			}
		}
		
		action.getRoleService().saveRole(role);
	}
	
	private void deleteRoles() throws NamingException, ServiceException, NoSuchAlgorithmException, UnsupportedEncodingException {
		String[] roles = action.getRequestValue("roles").split(",");
		RoleService roleService = action.getRoleService();
		
		List<Role> deleteRoles = new ArrayList<Role>();
		for(int i = 0; i < roles.length; i++) {
			if(!roles[i].equalsIgnoreCase("admin") && !roles[i].equalsIgnoreCase("ROLE_USER")) {
				Role voObj = action.getDomainObjectFactory().createRole();
				voObj.setId(roles[i]);
				
				Role role = roleService.searchRoles(voObj).get(0);
				deleteRoles.add(role);
			}
		}
		
		roleService.deleteRoles(deleteRoles);
	}
	
	private void updateResourceRole() throws NamingException, ServiceException {
		String[] resources = action.getRequestValue("resources").split(",");
		
		for(int i = 0; i < resources.length; i++) {
			ResourceService resourceService = action.getResourceService();
			Resource voResourceObj = action.getDomainObjectFactory().createResource();
			voResourceObj.setId(resources[i]);
			
			String[] roles = action.getRequestValue("roles_" + resources[i]).split(",");
			
			Resource resource = resourceService.searchResources(voResourceObj).get(0);
			Object[] originalRoles = resource.getAuthorityResources().toArray();
			for(int j = 0; j < originalRoles.length; j++) {
				Role role = (Role)originalRoles[j];
				
				resource.removeAuthorityResource(role);
				role.removeAuthorityResource(resource);
			}
			
			resourceService.saveResource(resource);
			
			for(int j = 0; j < roles.length; j++) {
				Role voRoleObj = action.getDomainObjectFactory().createRole();
				voRoleObj.setId(roles[j]);
				
				List<Role> list = action.getRoleService().searchRoles(voRoleObj);
				if(list.size() == 1) {
					Role role = list.get(0);
					resource.addAuthorityResource(role);
				}
			}
			
			resourceService.saveResource(resource);
		}
	}
	
	private void updateResourceUser() throws NamingException, ServiceException {
		String[] resources = action.getRequestValue("resources").split(",");
		
		for(int i = 0; i < resources.length; i++) {
			ResourceService resourceService = action.getResourceService();
			Resource voResourceObj = action.getDomainObjectFactory().createResource();
			voResourceObj.setId(resources[i]);
			
			String[] users = action.getRequestValue("users_" + resources[i]).split(",");
			
			Resource resource = resourceService.searchResources(voResourceObj).get(0);
			Object[] originalUsers = resource.getUserResources().toArray();
			for(int j = 0; j < originalUsers.length; j++) {
				User user = (User)originalUsers[j];
				
				resource.removeUserResource(user);
				user.removeUserResource(resource);
			}
			
			resourceService.saveResource(resource);
			
			for(int j = 0; j < users.length; j++) {
				User voUserObj = action.getDomainObjectFactory().createUser();
				voUserObj.setId(users[j]);
				
				List<User> list = action.getUserService().searchUsers(voUserObj);
				if(list.size() == 1) {
					User user = list.get(0);
					resource.addUserResource(user);
				}
			}
			
			resourceService.saveResource(resource);
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
	
	private void deleteCodes() throws NamingException, ServiceException, NoSuchAlgorithmException, UnsupportedEncodingException {
		String[] codes = action.getRequestValue("codes").split(",");
		CodeService codeService = action.getCodeService();
		List<Code> deleteCodes = new ArrayList<Code>();
		for(int i = 0; i < codes.length; i++) {
			Code voObj = action.getDomainObjectFactory().createCode();
			voObj.setCodeId(codes[i].split(";")[0]);
			voObj.setCodeType(codes[i].split(";")[1]);
			
			Code code = codeService.searchCodes(voObj).get(0);
			deleteCodes.add(code);
		}
		
		codeService.deleteCodes(deleteCodes);
	}
}
