package csiebug.test.webservices;

import static org.junit.Assert.*;

import java.net.URL;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import csiebug.util.DesCoder;

public class TestServiceTest  {
	private String nameSpaceUri = "http://localhost:8080/csiebug-2.0/services/TestService";  
	private String wsdlUrl = nameSpaceUri + "?wsdl";
	
	private Service service;  
	private Call call;
	
	private String token;
	
	@Before
	public void login() throws Exception {
		service = new Service();  
	    call = (Call)service.createCall();  
	    call.setOperationName(new QName(nameSpaceUri, "login"));  
	    call.setTargetEndpointAddress(new URL(wsdlUrl));
	    String key = "123456789012345678901234123456789012345678901234";
		String encryptPassword = "e9be64df86350eb2";
		token = (String)call.invoke(new Object[] {"csiebug", DesCoder.decryptCode(encryptPassword, key)});
	}
	
	@After
	public void logout() throws Exception {
		call = (Call)service.createCall();  
	    call.setOperationName(new QName(nameSpaceUri, "logout"));  
	    call.setTargetEndpointAddress(new URL(wsdlUrl));
	    call.invoke(new Object[] {token});
	}
	
	@Test
	/*
	 * 先logout出去,測試不綁登入的method
	 */
	public void testMethod() throws Exception {
		logout();
		
		call = (Call)service.createCall();  
	    call.setOperationName(new QName(nameSpaceUri, "testMethod"));  
	    call.setTargetEndpointAddress(new URL(wsdlUrl));
	    String result = (String)call.invoke(new Object[] {});
	    
		assertEquals("hello world!", result);
	}
	
	@Test
	public void testMethodWithPermission() throws Exception {
		call = (Call)service.createCall();  
	    call.setOperationName(new QName(nameSpaceUri, "testMethodWithPermission"));  
	    call.setTargetEndpointAddress(new URL(wsdlUrl));
	    String result = (String)call.invoke(new Object[] {token});
	    
		assertEquals("hello world!", result);
	}
	
	@Test
	/*
	 * 登入成功後,用同一個token連續執行一些method
	 */
	public void testMethodWithPermission2() throws Exception {
		call = (Call)service.createCall();  
	    call.setOperationName(new QName(nameSpaceUri, "testMethodWithPermission"));  
	    call.setTargetEndpointAddress(new URL(wsdlUrl));
	    String result = (String)call.invoke(new Object[] {token});
	    
		assertEquals("hello world!", result);
		
		call = (Call)service.createCall();  
	    call.setOperationName(new QName(nameSpaceUri, "testMethodWithPermission"));  
	    call.setTargetEndpointAddress(new URL(wsdlUrl));
	    result = (String)call.invoke(new Object[] {token});
	    
	    assertEquals("hello world!", result);
	}
	
	@Test
	/*
	 * 中間logout出去,繼續用同一個token,會發現沒辦法繼續使用webservice了,會發生exception
	 */
	public void testMethodWithPermission3() throws Exception {
		call = (Call)service.createCall();  
	    call.setOperationName(new QName(nameSpaceUri, "testMethodWithPermission"));  
	    call.setTargetEndpointAddress(new URL(wsdlUrl));
	    String result = (String)call.invoke(new Object[] {token});
	    
		assertEquals("hello world!", result);
		
		logout();
		
		call = (Call)service.createCall();  
	    call.setOperationName(new QName(nameSpaceUri, "testMethodWithPermission"));  
	    call.setTargetEndpointAddress(new URL(wsdlUrl));
	    
	    try {
	    	call.invoke(new Object[] {token});
	    	assertEquals(false, true);
	    } catch(Exception ex) {
	    	assertEquals(true, true);
	    }
	}
}
