package csiebug.web.webservices;

/**
 * Webservices Exception
 * @author George_Tsai
 * @version 2009/11/19
 *
 */
public class WebservicesException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public WebservicesException(String message, Exception root) {
		super(message, root);
	}
	
	public WebservicesException(String message) {
		super(message);
	}
	
	public WebservicesException(Throwable cause) {
		super(cause);
	}
}
