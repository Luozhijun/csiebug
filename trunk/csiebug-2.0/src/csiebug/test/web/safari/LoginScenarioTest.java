/**
 * 
 */
package csiebug.test.web.safari;

import csiebug.test.web.LoginScenario;

/**
 * @author George_Tsai
 * @version 2010/3/15
 */
public class LoginScenarioTest extends LoginScenario {
	public void setUp() throws Exception {
//		setUp("http://localhost:8080/", "*iexplore");
		setUp("*iexplore");
	}
}
