package csiebug.util;

/**
 * Invalid Input Exception
 * @author George_Tsai
 * @version 2009/11/19
 *
 */
public class InvalidInputException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public InvalidInputException(String message, Exception root) {
		super(message, root);
	}
	
	public InvalidInputException(String message) {
		super(message);
	}
	
	public InvalidInputException(Throwable cause) {
		super(cause);
	}
}
