/**
 * 
 */
package csiebug.test.web;

import junit.framework.AssertionFailedError;

/**
 * @author George_Tsai
 * @version 2010/1/18
 */
public abstract class LoginScenario extends BasicCommand {
	public abstract void setUp() throws Exception;
	
	//測試都不輸入
	public void testScenario1() {
		String username = "";
		String password = "";
		
		login(username, password);
		
		pause(1000);
		
		//這邊是因為javascript的關係,replace掉星號有差別
		//才會造成assertion必須隨browser不同而implement
		//與selenium command跨browser無關
		try {
			assertTrue(seleniumClient.isTextPresent("「帳號」欄位不得為空白!!"));
			assertTrue(seleniumClient.isTextPresent("「密碼」欄位不得為空白!!"));
		} catch(AssertionFailedError ex) {
			assertTrue(seleniumClient.isTextPresent("「＊帳號」欄位不得為空白!!"));
			assertTrue(seleniumClient.isTextPresent("「＊密碼」欄位不得為空白!!"));
		}
		
		clickAlertDialogButton();
		
		seleniumClient.close();
	}
	
	//測試只輸入帳號
	public void testScenario2() {
		String username = "csiebug";
		String password = "";
		
		login(username, password);
		
		pause(1000);
		
		//這邊是因為javascript的關係,replace掉星號有差別
		//才會造成assertion必須隨browser不同而implement
		//與selenium command跨browser無關
		try {
			assertFalse(seleniumClient.isTextPresent("「帳號」欄位不得為空白!!"));
			assertTrue(seleniumClient.isTextPresent("「密碼」欄位不得為空白!!"));
		} catch(AssertionFailedError ex) {
			assertFalse(seleniumClient.isTextPresent("「＊帳號」欄位不得為空白!!"));
			assertTrue(seleniumClient.isTextPresent("「＊密碼」欄位不得為空白!!"));
		}
		
		clickAlertDialogButton();
		
		seleniumClient.close();
	}
	
	//測試只輸入密碼
	public void testScenario3() {
		String username = "";
		String password = "rum5khxm";
		
		login(username, password);
		
		pause(1000);
		
		//這邊是因為javascript的關係,replace掉星號有差別
		//才會造成assertion必須隨browser不同而implement
		//與selenium command跨browser無關
		try {
			assertTrue(seleniumClient.isTextPresent("「帳號」欄位不得為空白!!"));
			assertFalse(seleniumClient.isTextPresent("「密碼」欄位不得為空白!!"));
		} catch(AssertionFailedError ex) {
			assertTrue(seleniumClient.isTextPresent("「＊帳號」欄位不得為空白!!"));
			assertFalse(seleniumClient.isTextPresent("「＊密碼」欄位不得為空白!!"));
		}
		
		clickAlertDialogButton();
		
		seleniumClient.close();
	}
	
	//測試輸入不符密碼規則的密碼
	public void testScenario4() {
		String username = "csiebug";
		String password = "password";
		
		seleniumClient.open("/csiebug-2.0/index");
		seleniumClient.windowMaximize();
		seleniumClient.type("j_username", username);
		pause(1000);
		seleniumClient.type("j_password", password);
		pause(1000);
		//按下tab(產生onBlur)
		seleniumClient.type("j_password", "\\9");
		pause(1000);
		
		assertTrue(seleniumClient.isTextPresent("密碼格式錯誤!!"));
		
		clickAlertDialogButton();
		
		seleniumClient.close();
	}
	
	//測試輸入錯誤的密碼
	public void testScenario5() {
		String username = "csiebug";
		String password = "mxhk5mu";
		
		login(username, password);
		
		seleniumClient.waitForPageToLoad("30000");
		
		//檢查導頁
		assertEquals("http://localhost:8080/csiebug-2.0/index?ActFlag=authenticationFailure", seleniumClient.getLocation());
		//檢查錯誤訊息
		assertTrue(seleniumClient.isTextPresent("密碼錯誤!!"));
		
		clickAlertDialogButton();
		
		seleniumClient.close();
	}
	
	//測試csiebug登入正確的情況
	public void testScenario6() {
		String username = "csiebug";
		String password = "rum5khxm";
		
		login(username, password);
		
		seleniumClient.waitForPageToLoad("30000");

		//檢查登入後導頁正確
		assertEquals("http://localhost:8080/csiebug-2.0/dt", seleniumClient.getLocation());
		//檢查登入者帳號正確
		assertTrue(seleniumClient.isTextPresent("登出 " + username));
		//檢查是不是有出現應該出現的dashboard
		assertTrue(seleniumClient.isTextPresent("測試"));
		//檢查是不是有出現應該出現的portlet window
		assertTrue(seleniumClient.isTextPresent("test1"));
		assertTrue(seleniumClient.isTextPresent("taglib"));
		
		pause(3000);
		
		seleniumClient.close();
	}
	
	//測試test登入正確的情況
	public void testScenario7() {
		String username = "test";
		String password = "rum5khxm";
		
		login(username, password);
		
		seleniumClient.waitForPageToLoad("30000");

		//檢查登入後導頁正確
		assertEquals("http://localhost:8080/csiebug-2.0/dt", seleniumClient.getLocation());
		//檢查登入者帳號正確
		assertTrue(seleniumClient.isTextPresent("登出 " + username));
		//檢查是不是有出現應該出現的dashboard
		assertFalse(seleniumClient.isTextPresent("測試"));
		//檢查是不是有出現應該出現的portlet window
		assertFalse(seleniumClient.isTextPresent("test1"));
		assertFalse(seleniumClient.isTextPresent("taglib"));
		
		pause(3000);
		
		seleniumClient.close();
	}
}
