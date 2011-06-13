/**
 * 
 */
package csiebug.test.web;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.DecoderException;

import csiebug.util.DesCoder;

/**
 * @author George_Tsai
 * @version 2010/1/18
 */
public abstract class LogoutScenario extends BasicCommand {
	public abstract void setUp() throws Exception;
	
	//測試登出
	public void testScenario1() throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, DecoderException {
		String username = "csiebug";
		String key = "123456789012345678901234123456789012345678901234";
		String encryptPassword = "e9be64df86350eb2";
		
		login(username, DesCoder.decryptCode(encryptPassword, key));
		
		seleniumClient.waitForPageToLoad("30000");
		
		logout(username);
		
		seleniumClient.waitForPageToLoad("30000");

		//檢查登入後導頁正確
		assertEquals("http://localhost:8080/csiebug-2.0/index?ActFlag=logout", seleniumClient.getLocation());
		
		pause(3000);
		
		seleniumClient.close();
	}
}
