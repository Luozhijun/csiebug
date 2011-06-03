/**
 * Copyright (c) 2010 George Tsai. All rights reserved.
 * For more information on the George Tsai, please see http://www.pixnet.net/blog/csiebug
 */
package csiebug.web.webapp;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;

import csiebug.domain.Code;
import csiebug.domain.DomainObjectFactory;
import csiebug.domain.User;
import csiebug.domain.UserEmail;
import csiebug.service.ServiceException;
import csiebug.util.DateFormatException;
import csiebug.util.DateFormatUtility;
import csiebug.util.EmailFormatException;
import csiebug.util.ShaEncoder;
import csiebug.util.StringUtility;
import csiebug.util.ldap.LdapClient;

/**
 * @author George_Tsai
 * @version 2010/9/17
 */
public class PersonalSettingAction {
	private PortletAdminAction action;
	
	public PersonalSettingAction(PortletAdminAction action) {
		this.action = action;
	}
	
	public void main() throws NamingException, ServiceException, NoSuchAlgorithmException, UnsupportedEncodingException, EmailFormatException {
		String actFlag = action.getRequestValue("personalSettingActFlag");
		
		if(actFlag.equalsIgnoreCase("personalSettingUserSave")) {
			saveUser();
	    } else if(actFlag.equalsIgnoreCase("personalSettingModifyPassword")) {
	    	modifyPassword();
	    }
	}
	
	public void makeControl() throws ServiceException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NamingException, DateFormatException {
		User loginUser = action.getLoginUser();
		action.setRequestAttribute("nickname", loginUser.getNickname());
		action.setRequestAttribute("registerDate", DateFormatUtility.getDisplayDate(loginUser.getCreateDate(), Integer.parseInt(action.getEnvVariable("defaultDateFormat"))));
		action.setRequestAttribute("lastLoginDate", DateFormatUtility.getDisplayDate(loginUser.getModifyDate(), Integer.parseInt(action.getEnvVariable("defaultDateFormat"))));
		
		Iterator<UserEmail> iterator = loginUser.getUserEmails().iterator();
		Map<String, String> emailOption = new LinkedHashMap<String, String>();
		StringBuffer emailList = new StringBuffer();
		while(iterator.hasNext()) {
			UserEmail email = iterator.next();
			emailOption.put(email.toString(), email.toString());
			
			if(emailList.length() != 0) {
				emailList.append(";");
			}
			emailList.append(email.toString());
		}
		action.setRequestAttribute("emailOption", emailOption);
		action.setRequestAttribute("majorEmail", loginUser.getMajorEmail());
		action.setRequestAttribute("emailList", emailList.toString());
		
		Code voObj = action.getDomainObjectFactory().createCode();
		voObj.setCodeType("locale");
		voObj.setEnabled(true);
		action.setRequestAttribute("localeOption", action.getCodeService().getCodeMap(voObj, Integer.parseInt(action.getEnvVariable("defaultDateFormat"))));
		action.setRequestAttribute("userLocale", loginUser.getLocale());
	}
	
	private void saveUser() throws ServiceException, NamingException, EmailFormatException {
		User loginUser = action.getLoginUser();
		loginUser.setNickname(action.getRequestValue("nickname"));
		loginUser.setLocale(action.getRequestValue("locale"));
		if(!action.getRequestValue("locale").trim().equals("")) {
			action.setLoginUserLocale(loginUser.getLocale());
		}
		loginUser.setModifyUserId(loginUser.getId());
		loginUser.setModifyDate(Calendar.getInstance());
		
		Set<UserEmail> emailSet = null;
		String[] emails = action.getRequestValue("emails").split(";");
		String majorEmail = action.getRequestValue("majorEmail");
		if(emails.length > 1 || (emails.length == 1 && !emails[0].trim().equals(""))) {
			emailSet = new HashSet<UserEmail>();
		}
		
		Object[] originalEmails = loginUser.getUserEmails().toArray();
		for(int i = 0; i < originalEmails.length; i++) {
			UserEmail email = (UserEmail)originalEmails[i];
			loginUser.removeUserEmail(email);
			action.getUserService().deleteUserEmail(email);
		}
		
		DomainObjectFactory domainObjectFactory = action.getDomainObjectFactory();
		for(int i = 0; i < emails.length; i++) {
			String[] emailPart = StringUtility.parseEmail(emails[i]);
			UserEmail email = domainObjectFactory.createUserEmail();
			email.setUserId(loginUser.getId());
			email.setEmailAccount(emailPart[0]);
			email.setEmailDomain(emailPart[1]);
			email.setCreateUserId(loginUser.getId());
			email.setCreateDate(Calendar.getInstance());
			email.setMajorFlag(majorEmail.equalsIgnoreCase(emails[i]));
			
			emailSet.add(email);
		}
		loginUser.setUserEmails(emailSet);
		
		action.getUserService().saveUser(loginUser);
	}
	
	private void modifyPassword() throws ServiceException, NoSuchAlgorithmException, UnsupportedEncodingException, NamingException {
		try {
			modifyPassword2LDAP();
			modifyPassword2DB();
		} catch(Exception ex) {
			rollback();
		}
	}
	
	@SuppressWarnings("deprecation")
	private void modifyPassword2LDAP() throws NamingException, NoSuchAlgorithmException, UnsupportedEncodingException {
		LdapClient client = new LdapClient(action.getEnvVariable("ldapURL"), action.getEnvVariable("ldapAdmin"), action.getEnvVariable("ldapPassword"));
		Attributes attributes = new BasicAttributes();
		
		attributes.put("userpassword", "{SHA}" + ShaEncoder.getSHA1Base64(action.getRequestValue("password")));
		
		client.modifyAttributes("uid=" + action.getLoginUserId() + ",ou=csiebug", attributes);
	}
	
	private void modifyPassword2DB() throws NamingException, NoSuchAlgorithmException, UnsupportedEncodingException, ServiceException {
		User loginUser = action.getLoginUser();
		loginUser.setPassword(ShaEncoder.getSHA256String(action.getRequestValue("password")));
		loginUser.setModifyUserId(loginUser.getId());
		loginUser.setModifyDate(Calendar.getInstance());
		
		action.getUserService().saveUser(loginUser);
	}
	
	private void rollback() throws NamingException, NoSuchAlgorithmException, UnsupportedEncodingException {
		LdapClient client = new LdapClient(action.getEnvVariable("ldapURL"), action.getEnvVariable("ldapAdmin"), action.getEnvVariable("ldapPassword"));
		client.deleteUser("ou=csiebug", action.getLoginUserId());
	}
}
