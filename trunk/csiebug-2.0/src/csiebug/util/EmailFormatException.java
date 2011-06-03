package csiebug.util;

/**
 * Email Format Exception
 * @author George_Tsai
 * @version 2010/10/7
 *
 */
public class EmailFormatException extends StringParseException {
	private static final long serialVersionUID = 1L;
	
	public EmailFormatException(String message, Exception root) {
		super(message, root);
	}
	
	public EmailFormatException(String message) {
		super(message);
	}
	
	public EmailFormatException(Throwable cause) {
		super(cause);
	}
}
