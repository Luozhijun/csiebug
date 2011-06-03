package csiebug.dao;

/**
 * DAO Layer Exception
 * @author George_Tsai
 * @version 2009/9/9
 *
 */
public class DAOException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public DAOException(String message, Exception root) {
		super(message, root);
	}
	
	public DAOException(String message) {
		super(message);
	}
	
	public DAOException(Throwable cause) {
		super(cause);
	}
}
