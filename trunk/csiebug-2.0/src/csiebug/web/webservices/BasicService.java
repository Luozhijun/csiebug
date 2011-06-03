/**
 * Copyright (c) 2010 George Tsai. All rights reserved.
 * For more information on the George Tsai, please see http://www.pixnet.net/blog/csiebug
 */
package csiebug.web.webservices;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Calendar;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.naming.NamingException;

import org.apache.commons.codec.DecoderException;
import org.springframework.context.ApplicationContext;
import org.springframework.remoting.jaxrpc.ServletEndpointSupport;

import csiebug.domain.DomainObjectFactory;
import csiebug.domain.User;
import csiebug.domain.WebservicesChannel;
import csiebug.service.ServiceException;
import csiebug.service.UserService;
import csiebug.util.AssertUtility;
import csiebug.util.DesCoder;
import csiebug.util.StringUtility;
import csiebug.util.WebUtility;
import csiebug.util.ldap.LdapClient;

/**
 * @author George_Tsai
 * @version 2010/11/2
 */
public class BasicService extends ServletEndpointSupport {
	private ApplicationContext applicationContext; 
	
	private DomainObjectFactory domainObjectFactory;
	private UserService userService;
	
	@Override  
	protected void onInit() throws javax.xml.rpc.ServiceException {
		applicationContext = super.getApplicationContext();
		domainObjectFactory = (DomainObjectFactory)applicationContext.getBean("domainObjectFactory");
		userService = (UserService)applicationContext.getBean("userService");
	}
	
	private boolean checkLogin(String userId, String password) {
		AssertUtility.notNullAndNotSpace(userId);
		
		try {
			WebUtility webutil = new WebUtility();
			new LdapClient(webutil.getEnvVariable("ldapURL"), "uid=" + userId + ",ou=csiebug", password);
			
			return true;
		} catch (NamingException e) {
			return false;
		}
	}
	
	/**
	 * 執行所有的webservice method都需要先執行過login取得token
	 * @param userId
	 * @param password
	 * @return
	 * @throws WebservicesException
	 */
	public String login(String userId, String password) throws WebservicesException {
		AssertUtility.notNullAndNotSpace(userId);
		
		if(checkLogin(userId, password)) {
			try {
				User voObj = domainObjectFactory.createUser();
				voObj.setId(userId);
				voObj.setEnabled(true);
				User user = userService.searchUsers(voObj).get(0);
				
				String serviceKey = StringUtility.makeRandomNumberKey(48);
				String channelId = user.getAvailableWebservicesChannelId();
				String token = userId + "&" + DesCoder.encryptCode(password, serviceKey) + "&" + channelId;
				
				WebservicesChannel webservicesChannel = domainObjectFactory.createWebservicesChannel();
	        	webservicesChannel.setUserId(user.getId());
	        	webservicesChannel.setChannelId(channelId);
	        	webservicesChannel.setServiceKey(serviceKey);
	        	webservicesChannel.setLastUsed(Calendar.getInstance());
	        	user.addWebservicesChannel(webservicesChannel);
		        
		        userService.saveUser(user);
				
				return token;
			} catch (ServiceException e) {
				throw new WebservicesException(e);
			} catch (InvalidKeyException e) {
				throw new WebservicesException(e);
			} catch (InvalidKeySpecException e) {
				throw new WebservicesException(e);
			} catch (NoSuchAlgorithmException e) {
				throw new WebservicesException(e);
			} catch (NoSuchPaddingException e) {
				throw new WebservicesException(e);
			} catch (InvalidAlgorithmParameterException e) {
				throw new WebservicesException(e);
			} catch (UnsupportedEncodingException e) {
				throw new WebservicesException(e);
			} catch (IllegalBlockSizeException e) {
				throw new WebservicesException(e);
			} catch (BadPaddingException e) {
				throw new WebservicesException(e);
			} catch (DecoderException e) {
				throw new WebservicesException(e);
			}
		} else {
			throw new WebservicesException("Login failure!!");
		}
	}
	
	/**
	 * 每個method執行前都需要檢查token是否合法(是否有登入成功)
	 * @param token
	 * @return
	 */
	protected boolean checkToken(String token) {
		try {
			AssertUtility.notNullAndNotSpace(token);
			
			String[] tokenPart = token.split("&");
			
			if(tokenPart.length == 3) {
				String userId = tokenPart[0];
				String encryptPassword = tokenPart[1];
				String channelId = tokenPart[2];
				
				User voObj = domainObjectFactory.createUser();
				voObj.setId(userId);
				voObj.setEnabled(true);
				User user = userService.searchUsers(voObj).get(0);
				
				WebservicesChannel webservicesChannel = user.getWebservicesChannel(channelId);
				if(webservicesChannel != null) {
					String password = DesCoder.decryptCode(encryptPassword, webservicesChannel.getServiceKey());
					return checkLogin(userId, password);
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (ServiceException e) {
			return false;
		} catch (InvalidKeyException e) {
			return false;
		} catch (InvalidKeySpecException e) {
			return false;
		} catch (NoSuchAlgorithmException e) {
			return false;
		} catch (NoSuchPaddingException e) {
			return false;
		} catch (InvalidAlgorithmParameterException e) {
			return false;
		} catch (IllegalBlockSizeException e) {
			return false;
		} catch (BadPaddingException e) {
			return false;
		} catch (UnsupportedEncodingException e) {
			return false;
		} catch (DecoderException e) {
			return false;
		}
	}
	
	/**
	 * client端webservice不用的時候,記得呼叫logout,把token失效
	 * @param token
	 * @return
	 */
	public boolean logout(String token) {
		AssertUtility.notNullAndNotSpace(token);
		
		if(checkToken(token)) {
			try {
				String[] tokenPart = token.split("&");
				String userId = tokenPart[0];
				String channelId = tokenPart[2];
				
				User voObj = domainObjectFactory.createUser();
				voObj.setId(userId);
				voObj.setEnabled(true);
				User user = userService.searchUsers(voObj).get(0);
				
				WebservicesChannel channel = user.getWebservicesChannel(channelId);
				user.removeWebservicesChannel(channel);
				
				userService.deleteWebservicesChannel(channel);
				
				return true;
			} catch (ServiceException e) {
				return false;
			}
		} else {
			return false;
		}
	}
}
