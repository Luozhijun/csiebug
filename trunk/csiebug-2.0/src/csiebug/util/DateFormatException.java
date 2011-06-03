package csiebug.util;

/**
 * Date Format Exception
 * @author George_Tsai
 * @version 2009/12/31
 *
 */
public class DateFormatException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public DateFormatException(String message, Exception root) {
		super(message, root);
	}
	
	public DateFormatException(String message) {
		super(message);
	}
	
	public DateFormatException(Throwable cause) {
		super(cause);
	}
}
