/**
 * 
 */
package csiebug.test.web;

/**
 * @author George_Tsai
 * @version 2010/1/18
 */
public abstract class AdminScenario extends BasicCommand {
	public abstract void setUp() throws Exception;
	
	//以admin的角色登入,檢查是否有出現admin可用的功能
	public void testScenario1() throws Exception {
		login("csiebug", "rum5khxm");
		
		seleniumClient.waitForPageToLoad("30000");
		seleniumClient.click("link=主控台");
		seleniumClient.waitForPageToLoad("30000");
		
		//檢查導頁正確
		assertEquals("http://localhost:8080/csiebug-2.0/admin", seleniumClient.getLocation());
		
		//檢查admin的控制項是否有出現
		assertTrue(seleniumClient.isTextPresent("掛載Portlet"));
		assertTrue(seleniumClient.isElementPresent("upload"));
		assertTrue(seleniumClient.isElementPresent("permissionFlag"));
		assertTrue(seleniumClient.isTextPresent("卸載Portlet"));
		assertTrue(seleniumClient.isElementPresent("undeploy-portlet"));
		assertTrue(seleniumClient.isTextPresent("新增Portlet視窗"));
		assertTrue(seleniumClient.isElementPresent("portletList"));
		assertTrue(seleniumClient.isElementPresent("portletWindowName_create"));
		assertTrue(seleniumClient.isElementPresent("createWindowPermissionFlag"));
		
		//檢查一般使用者會有的控制項
		assertTrue(seleniumClient.isTextPresent("修改Dashboard"));
		assertTrue(seleniumClient.isElementPresent("dashboard_display"));
		assertTrue(seleniumClient.isElementPresent("dashboard_valueDisplay"));
		assertTrue(seleniumClient.isElementPresent("dashboardSortOrder"));
		assertTrue(seleniumClient.isTextPresent("修改Portlet視窗"));
		assertTrue(seleniumClient.isElementPresent("portletWindowList"));
		assertTrue(seleniumClient.isElementPresent("dashboardId"));
		assertTrue(seleniumClient.isElementPresent("title"));
		assertTrue(seleniumClient.isElementPresent("widthList"));
		assertTrue(seleniumClient.isElementPresent("portletSortOrder"));
		assertTrue(seleniumClient.isElementPresent("visibleList"));
		
		pause(3000);
		
		seleniumClient.close();
	}
	
	//以一般的角色登入,檢查是否有出現admin可用的功能
	public void testScenario2() throws Exception {
		login("test", "rum5khxm");
		
		seleniumClient.waitForPageToLoad("30000");
		seleniumClient.click("link=主控台");
		seleniumClient.waitForPageToLoad("30000");
		
		//檢查導頁正確
		assertEquals("http://localhost:8080/csiebug-2.0/admin", seleniumClient.getLocation());
		
		//檢查admin的控制項是否有出現
		assertFalse(seleniumClient.isTextPresent("掛載Portlet"));
		assertFalse(seleniumClient.isElementPresent("upload"));
		assertFalse(seleniumClient.isElementPresent("permissionFlag"));
		assertFalse(seleniumClient.isTextPresent("卸載Portlet"));
		assertFalse(seleniumClient.isElementPresent("undeploy-portlet"));
		assertFalse(seleniumClient.isTextPresent("新增Portlet視窗"));
		assertFalse(seleniumClient.isElementPresent("portletList"));
		assertFalse(seleniumClient.isElementPresent("portletWindowName_create"));
		assertFalse(seleniumClient.isElementPresent("createWindowPermissionFlag"));
		
		//檢查一般使用者會有的控制項
		assertTrue(seleniumClient.isTextPresent("修改Dashboard"));
		assertTrue(seleniumClient.isElementPresent("dashboard_display"));
		assertTrue(seleniumClient.isElementPresent("dashboard_valueDisplay"));
		assertTrue(seleniumClient.isElementPresent("dashboardSortOrder"));
		assertTrue(seleniumClient.isTextPresent("修改Portlet視窗"));
		assertTrue(seleniumClient.isElementPresent("portletWindowList"));
		assertTrue(seleniumClient.isElementPresent("dashboardId"));
		assertTrue(seleniumClient.isElementPresent("title"));
		assertTrue(seleniumClient.isElementPresent("widthList"));
		assertTrue(seleniumClient.isElementPresent("portletSortOrder"));
		assertTrue(seleniumClient.isElementPresent("visibleList"));
		
		pause(3000);
		
		seleniumClient.close();
	}
	
	//卸載Portlet
//	public void testScenario3() throws Exception {
//		login("csiebug", "rum5khxm");
//		
//		selenium.waitForPageToLoad("30000");
//		selenium.click("link=主控台");
//		selenium.waitForPageToLoad("30000");
//		
//		assertTrue(selenium.isTextPresent("csiebug-taglib-demo"));
//		selenium.addSelection("undeploy-portlets", "label=csiebug-taglib-demo");
//		selenium.click("undeployButton");
//		selenium.waitForPageToLoad("30000");
//		assertTrue(selenium.isTextPresent("Successfully undeployed"));
//		assertFalse(selenium.isTextPresent("csiebug-taglib-demo"));
//		
//		pause(3000);
//		
//		selenium.close();
//	}
	
	//掛載Portlet
//	public void testScenario4() throws Exception {
//		login("csiebug", "rum5khxm");
//		
//		selenium.waitForPageToLoad("30000");
//		selenium.click("link=主控台");
//		selenium.waitForPageToLoad("30000");
//		
//		selenium.type("upload", "C:\\Users\\csiebug\\Desktop\\csiebug-taglib-demo.war");
//		selenium.click("permissionFlag");
//		selenium.click("deployButton");
//		
//		selenium.waitForPageToLoad("30000");
//		
//		assertTrue(selenium.isTextPresent("Successfully Deployed and a Portlet Window created."));
//		assertTrue(selenium.isTextPresent("csiebug-taglib-demo"));
//		
//		pause(3000);
//		
//		selenium.close();
//	}
	
	//以admin的角色登入,新增portlet window
//	public void testScenario5() throws Exception {
//		login("csiebug", "rum5khxm");
//		
//		selenium.waitForPageToLoad("30000");
//		selenium.click("link=主控台");
//		selenium.waitForPageToLoad("30000");
//		
//		selenium.select("portletList", "label=csiebug-taglib-demo.csiebug-taglib-demo");
//		selenium.type("portletWindowName_create", "test2");
//		selenium.click("createWindowPermissionFlag");
//		selenium.waitForPageToLoad("30000");
//		
//		assertTrue(selenium.isTextPresent("test2"));
//		
//		pause(3000);
//		
//		selenium.close();
//	}
	
	//以一般的角色登入,新增一個dashboard,再刪除
	public void testScenario6() throws Exception {
		login("test", "rum5khxm");
		
		seleniumClient.waitForPageToLoad("30000");
		seleniumClient.click("link=主控台");
		seleniumClient.waitForPageToLoad("30000");
		
		//一開始沒有這個dashboard
		assertFalse(seleniumClient.isTextPresent("測試"));
		
		seleniumClient.type("dashboard_display", "test");
		pause(1000);
		seleniumClient.type("dashboard_valueDisplay", "測試");
		seleniumClient.click("saveDashboardButton");
		
		seleniumClient.waitForPageToLoad("30000");
		
		//新增完應該有這個dashboard
		assertTrue(seleniumClient.isTextPresent("測試"));
		
		pause(3000);
		
		seleniumClient.type("dashboard_display", "test");
		pause(1000);
		seleniumClient.click("deleteDashboardButton");
		
		seleniumClient.waitForPageToLoad("30000");
		
		//刪除完沒有這個dashboard
		assertFalse(seleniumClient.isTextPresent("測試"));
		
		pause(3000);
		
		seleniumClient.close();
	}
	
	//修改portlet
	public void testScenario7() throws Exception {
		login("csiebug", "rum5khxm");
		
		seleniumClient.waitForPageToLoad("30000");
		seleniumClient.click("link=主控台");
		seleniumClient.waitForPageToLoad("30000");
		
		seleniumClient.select("portletWindowList", "label=csiebug-taglib-demo.csiebug-taglib-demo");
		seleniumClient.select("dashboardId", "label=測試");
		seleniumClient.click("modifyWindowButton");
		seleniumClient.waitForPageToLoad("30000");
		
		seleniumClient.click("link=桌面");
		seleniumClient.waitForPageToLoad("30000");
		
		//新增完這個桌面應該沒有這個portlet
		assertFalse(seleniumClient.isTextPresent("taglib"));
		
		pause(1000);
		
		seleniumClient.click("link=測試");
		seleniumClient.waitForPageToLoad("30000");
		
		//新增完這個dashboard應該有這個portlet
		assertTrue(seleniumClient.isTextPresent("taglib"));
		
		pause(1000);
		
		seleniumClient.click("link=主控台");
		seleniumClient.waitForPageToLoad("30000");
		
		seleniumClient.select("portletWindowList", "label=csiebug-taglib-demo.csiebug-taglib-demo");
		seleniumClient.type("title", "測試portlet");
		seleniumClient.click("modifyWindowButton");
		seleniumClient.waitForPageToLoad("30000");
		
		seleniumClient.click("link=測試");
		seleniumClient.waitForPageToLoad("30000");
		
		//修改完這個portlet的標題改變
		assertFalse(seleniumClient.isTextPresent("taglib"));
		assertTrue(seleniumClient.isTextPresent("測試portlet"));
		
		seleniumClient.click("link=主控台");
		seleniumClient.waitForPageToLoad("30000");
		
		seleniumClient.select("portletWindowList", "label=csiebug-taglib-demo.csiebug-taglib-demo");
		seleniumClient.click("//input[@id='visibleList' and @name='visibleList' and @value='false']");
		seleniumClient.click("modifyWindowButton");
		seleniumClient.waitForPageToLoad("30000");
		
		seleniumClient.click("link=測試");
		seleniumClient.waitForPageToLoad("30000");
		
		//修改完這個portlet不顯現
		assertFalse(seleniumClient.isTextPresent("taglib"));
		assertFalse(seleniumClient.isTextPresent("測試portlet"));
		
		
		//復原
		seleniumClient.click("link=主控台");
		seleniumClient.waitForPageToLoad("30000");
		
		seleniumClient.select("portletWindowList", "label=csiebug-taglib-demo.csiebug-taglib-demo");
		seleniumClient.select("dashboardId", "label=桌面");
		seleniumClient.type("title", "taglib");
		seleniumClient.click("//input[@id='visibleList' and @name='visibleList' and @value='true']");
		seleniumClient.click("modifyWindowButton");
		seleniumClient.waitForPageToLoad("30000");
		
		pause(3000);
		
		seleniumClient.close();
	}
}
