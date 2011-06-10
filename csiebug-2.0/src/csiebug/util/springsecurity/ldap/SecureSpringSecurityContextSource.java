package csiebug.util.springsecurity.ldap;

import org.springframework.security.ldap.DefaultSpringSecurityContextSource;

import csiebug.util.DesCoder;

public class SecureSpringSecurityContextSource extends DefaultSpringSecurityContextSource {
	private String key;
	private String password;
	private String dn;
	
	public SecureSpringSecurityContextSource(String url, String dn) {
		super(url);
		super.setUserDn(dn);
	}
	
	public void setKey(String key) {
		this.key = key;
		
		//表示設定檔先設了password,後面才設key
		//所以已經先被呼叫了setPassword那個method
		//此時getPassword拿出來的是沒有解密的密碼
		//所以要重新setPassword,設定解密的密碼
		try {
			if(this.password != null) {
				super.setPassword(DesCoder.decryptCode(this.password, getKey()));
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
				this.password = password;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
	
	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}
}
