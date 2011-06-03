package csiebug.util;

/**
 * Cookie Exception
 * @author George_Tsai
 * @version 2009/11/19
 *
 */
public class CookieException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public CookieException(String message, Exception root) {
		super(message, root);
	}
	
	public CookieException(String message) {
		super(message);
	}
	
	public CookieException(Throwable cause) {
		super(cause);
	}
}
