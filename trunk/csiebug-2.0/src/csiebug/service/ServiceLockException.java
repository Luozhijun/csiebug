package csiebug.service;

/**
 * Service Layer Exception
 * @author George_Tsai
 * @version 2009/9/9
 *
 */
public class ServiceLockException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public ServiceLockException(String message, Exception root) {
		super(message, root);
	}
	
	public ServiceLockException(String message) {
		super(message);
	}
	
	public ServiceLockException(Throwable cause) {
		super(cause);
	}
}
