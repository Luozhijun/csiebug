/**
 * 
 */
package csiebug.test.web.safari;

import csiebug.test.web.AdminScenario;

/**
 * @author George_Tsai
 * @version 2010/3/15
 */
public class AdminScenarioTest extends AdminScenario {
	public void setUp() throws Exception {
//		setUp("http://localhost:8080/", "*iexplore");
		setUp("*iexplore");
	}
}
