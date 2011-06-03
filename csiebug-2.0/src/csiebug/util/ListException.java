package csiebug.util;

/**
 * Date Format Exception
 * @author George_Tsai
 * @version 2009/12/31
 *
 */
public class ListException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public ListException(String message, Exception root) {
		super(message, root);
	}
	
	public ListException(String message) {
		super(message);
	}
	
	public ListException(Throwable cause) {
		super(cause);
	}
}
