/**
 * Copyright (c) 2010 George Tsai. All rights reserved.
 * For more information on the George Tsai, please see http://www.pixnet.net/blog/csiebug
 */
package csiebug.web.webservices;

/**
 * @author George_Tsai
 * @version 2010/11/2
 */
public class TestService extends BasicService {
	@Override  
	protected void onInit() throws javax.xml.rpc.ServiceException {
		super.onInit();
	}
	
	public String testMethod() {
		return "hello world!";
	}
	
	public String testMethodWithPermission(String token) throws WebservicesException {
		if(checkToken(token)) {
			return "hello world!";
		} else {
			throw new WebservicesException("Login failure!!");
		}
	}
}
