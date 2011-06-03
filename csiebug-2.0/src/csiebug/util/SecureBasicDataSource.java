/**
 * Copyright (c) 2010 George Tsai. All rights reserved.
 * For more information on the George Tsai, please see http://www.pixnet.net/blog/csiebug
 */
package csiebug.util;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * @author George_Tsai
 * @version 2011/3/11
 */
public class SecureBasicDataSource extends BasicDataSource {
	private String key;
	
	public void setKey(String key) {
		this.key = key;
		
		//表示設定檔先設了password,後面才設key
		//所以已經先被呼叫了setPassword那個method
		//此時getPassword拿出來的是沒有解密的密碼
		//所以要重新setPassword,設定解密的密碼
		try {
			if(getPassword() != null) {
				super.setPassword(DesCoder.decryptCode(getPassword(), getKey()));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getKey() {
		return key;
	}
	
	@Override
    public synchronized void setPassword(String password) {
		try {
			//表示設定檔先設了key,此時就可以設定解密的密碼
			if(getKey() != null) {
				super.setPassword(DesCoder.decryptCode(password, getKey()));
			//表示設定檔先設了password,沒有key暫時不能解密
			//先把加密的key設進來,等setKey被呼叫的時候,setKey會再呼叫一次setPassword設定解密的密碼
			} else {
				super.setPassword(password);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}
