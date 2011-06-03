package csiebug.util;

/**
 * String Parse Exception
 * @author George_Tsai
 * @version 2011/5/26
 *
 */
public class StringParseException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public StringParseException(String message, Exception root) {
		super(message, root);
	}
	
	public StringParseException(String message) {
		super(message);
	}
	
	public StringParseException(Throwable cause) {
		super(cause);
	}
}
