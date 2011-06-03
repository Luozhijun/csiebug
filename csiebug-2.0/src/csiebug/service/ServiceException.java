package csiebug.service;

/**
 * Service Layer Exception
 * @author George_Tsai
 * @version 2009/9/9
 *
 */
public class ServiceException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public ServiceException(String message, Exception root) {
		super(message, root);
	}
	
	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(Throwable cause) {
		super(cause);
	}
}
