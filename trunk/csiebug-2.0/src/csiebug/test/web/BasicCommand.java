/**
 * 
 */
package csiebug.test.web;

import java.net.URL;

import org.openqa.selenium.SeleneseCommandExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.thoughtworks.selenium.SeleneseTestCase;
import com.thoughtworks.selenium.Selenium;

/**
 * @author George_Tsai
 * @version 2010/3/11
 */
public class BasicCommand extends SeleneseTestCase {
	Selenium seleniumClient;
	
	public void setUp(String browserString) throws Exception {
		String baseUrl = "http://localhost:8080/";
		WebDriver driver;
		
		if(browserString.equalsIgnoreCase("*firefox")) {
			driver = new FirefoxDriver();
		} else if(browserString.equalsIgnoreCase("*chrome")) {
			driver = new ChromeDriver();
		} else if(browserString.equalsIgnoreCase("*safari")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setBrowserName("safari");
			CommandExecutor executor = new SeleneseCommandExecutor(new URL("http", "localhost", 4444, ""), new URL("http", "localhost", 8080, ""), capabilities);
			driver = new RemoteWebDriver(executor, capabilities);
//		} else if(browserString.equalsIgnoreCase("*iexplore")) {
//			driver = new InternetExplorerDriver();
		} else {
			driver = new InternetExplorerDriver();
		}
		
		seleniumClient = new WebDriverBackedSelenium(driver, baseUrl);
	}
	
	protected void login(String username, String password) {
		seleniumClient.open("/csiebug-2.0/index");
		seleniumClient.windowMaximize();
		seleniumClient.type("j_username", username);
		pause(1000);
		seleniumClient.type("j_password", password);
		pause(1000);
		seleniumClient.click("loginButton");
	}
	
	protected void clickAlertDialogButton() {
		pause(3000);
		seleniumClient.click("alertDialogButton");
	}
	
	protected void clickConfirmDialogOK() {
		pause(3000);
		seleniumClient.click("confirmDialogOK");
	}
	
	protected void clickConfirmDialogCancel() {
		pause(3000);
		seleniumClient.click("confirmDialogCancel");
	}
	
	protected void logout(String username) {
		seleniumClient.click("link=登出 " + username);
	}
}
