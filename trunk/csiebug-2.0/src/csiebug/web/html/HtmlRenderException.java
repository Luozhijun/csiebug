/**
 * Copyright (c) 2010 George Tsai. All rights reserved.
 * For more information on the George Tsai, please see http://www.pixnet.net/blog/csiebug
 */
package csiebug.web.html;

/**
 * @author George_Tsai
 * @version 2010/6/10
 */
public class HtmlRenderException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public HtmlRenderException(String message, Exception root) {
		super(message, root);
	}
	
	public HtmlRenderException(String message) {
		super(message);
	}
	
	public HtmlRenderException(Throwable cause) {
		super(cause);
	}
}
