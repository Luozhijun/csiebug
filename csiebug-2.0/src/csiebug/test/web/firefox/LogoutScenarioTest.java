/**
 * 
 */
package csiebug.test.web.firefox;

import csiebug.test.web.LogoutScenario;

/**
 * @author George_Tsai
 * @version 2010/3/15
 */
public class LogoutScenarioTest extends LogoutScenario {
	public void setUp() throws Exception {
//		setUp("http://localhost:8080/", "*firefox");
		setUp("*firefox");
	}
}
