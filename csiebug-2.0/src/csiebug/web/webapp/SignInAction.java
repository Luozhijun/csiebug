package csiebug.web.webapp;

import java.awt.Color;
import java.awt.Font;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.List;

import javax.naming.NamingException;

import csiebug.domain.Role;
import csiebug.domain.User;
import csiebug.service.RoleService;
import csiebug.service.ServiceException;
import csiebug.service.UserService;
import csiebug.util.AssertUtility;
import csiebug.util.ShaEncoder;
import csiebug.util.ldap.LdapClient;


public class SignInAction extends BasicAction {
	private static final long serialVersionUID = 1L;
	
	private UserService userService;
	private RoleService roleService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	public String main() throws Exception {
		//以下的if else處理各樣頁面的動作,可自行增加或修改
		if(getActFlag().equalsIgnoreCase("signIn")) {
			makeControl();
			return signIn();
		} else if(getActFlag().equalsIgnoreCase("authenticationImage")) {
			makeAuthenticationImageFile("Mix", 6, 100, 30, Color.WHITE, true, Color.BLACK, Color.BLACK, "Arial", Font.ITALIC, 24, 2, 24, 150);
			return null;
		}
		//頁面動作處理結束
		
		//頁面控制項需要的資料
		makeControl();
		
		//若沒有導到特定處理頁面,則預設都導到formLoad頁
		return FORMLOAD;
	}
	
	//畫面控制項函數區
	private void makeControl() throws UnsupportedEncodingException, NamingException {
		//畫面控制項
		try {
			setRequestAttribute("FunctionName", getFunctionName());
		} catch(ServiceException se) {
			writeErrorLog(se);
			
			if(AssertUtility.isNotNullAndNotSpace(getFunctionId())) {
				setRequestAttribute("FunctionName", getFunctionId());
			} else {
				setRequestAttribute("FunctionName", getMessage("Login.FunctionName"));
			}
		}
	}
	
	//畫面控制項函數區結束
	 
	//邏輯函數區
	//頁面動作所對應的處理method,business logic,請歸類到下面
	
	private String signIn() throws Exception {
		if(checkPictureContent()) {
			if(checkUserValid()) {
				try {
					createUser2LDAP();
					createUser2DB();
				} catch(Exception ex) {
					rollback();
					return FORMLOAD;
				}
				
				setRequestAttribute("enterSubmitScript", "submitEnter(event, 'index', '" + getMessage("common.warning") + "', '" + getMessage("common.error.required5") + "', '" + getMessage("common.ok") + "');");
				
				StringBuffer script = new StringBuffer("document.getElementById(\"j_username\").value = \"" + getRequestValue("j_username") + "\";\n");
				script.append("document.getElementById(\"j_password\").value = \"" + getRequestValue("j_password") + "\";\n");
				script.append("document.getElementById(\"loginButton\").click();");
				
				addPageLoadScript(script.toString());
				
				return SUCCESS;
			} else {
				String script = "document.getElementById(\"j_username\").value = \"\";";
				addPageLoadScript(script);
				addPageLoadWarningDialog(getMessage("common.UserExist"));
				return FORMLOAD;
			}
		} else {
			String script = "document.getElementById(\"pictureContent\").value = \"\";";
			addPageLoadScript(script);
			addPageLoadWarningDialog(getMessage("common.error.PictureContent"));
			return FORMLOAD;
		}
	}
	
	private boolean checkPictureContent() throws NamingException {
		return (getRequestValue("pictureContent").equals((String)getSessionAttribute("AuthenticationImageNumber")));
	}
	
	private boolean checkUserValid() throws NamingException, ServiceException {
		User voObj = getDomainObjectFactory().createUser();
		voObj.setId(getRequestValue("j_username"));
		
		List<User> list = userService.searchUsers(voObj);
		
		return (list.size() == 0);
	}
	
	private void createUser2LDAP() throws NamingException, NoSuchAlgorithmException, UnsupportedEncodingException {
		LdapClient client = new LdapClient(getEnvVariable("ldapURL"), getEnvVariable("ldapAdmin"), getEnvVariable("ldapPassword"));
		client.createUser("ou=csiebug", getRequestValue("j_username"), getRequestValue("j_username"), getRequestValue("j_username"), getRequestValue("j_username"), getRequestValue("j_password"));
	}
	
	private void createUser2DB() throws NamingException, NoSuchAlgorithmException, UnsupportedEncodingException, ServiceException {
		User user = getDomainObjectFactory().createUser();
		user.setId(getRequestValue("j_username"));
		user.setPassword(ShaEncoder.getSHA256String(getRequestValue("j_password")));
		user.setEnabled(true);
		user.setCreateUserId(getRequestValue("j_username"));
		user.setCreateDate(Calendar.getInstance());
		
		Role voObj = getDomainObjectFactory().createRole();
		voObj.setId("ROLE_USER");
		Role role = roleService.searchRoles(voObj).get(0);
		
		user.addAuthority(role);
		
		userService.saveUser(user);
	}
	
	private void rollback() throws NamingException, NoSuchAlgorithmException, UnsupportedEncodingException {
		LdapClient client = new LdapClient(getEnvVariable("ldapURL"), getEnvVariable("ldapAdmin"), getEnvVariable("ldapPassword"));
		client.deleteUser("ou=csiebug", getRequestValue("j_username"));
	}
	
	//邏輯函數區結束
}
