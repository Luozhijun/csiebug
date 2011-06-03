/**
 * 
 */
package csiebug.test.web;

/**
 * @author George_Tsai
 * @version 2010/1/18
 */
public abstract class LogoutScenario extends BasicCommand {
	public abstract void setUp() throws Exception;
	
	//測試登出
	public void testScenario1() {
		String username = "csiebug";
		String password = "rum5khxm";
		
		login(username, password);
		
		seleniumClient.waitForPageToLoad("30000");
		
		logout(username);
		
		seleniumClient.waitForPageToLoad("30000");

		//檢查登入後導頁正確
		assertEquals("http://localhost:8080/csiebug-2.0/index?ActFlag=logout", seleniumClient.getLocation());
		
		pause(3000);
		
		seleniumClient.close();
	}
}
