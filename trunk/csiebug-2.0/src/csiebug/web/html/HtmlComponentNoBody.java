/**
 * Copyright (c) 2010 George Tsai. All rights reserved.
 * For more information on the George Tsai, please see http://www.pixnet.net/blog/csiebug
 */
package csiebug.web.html;

/**
 * @author George_Tsai
 * @version 2010/6/10
 */
public abstract class HtmlComponentNoBody extends HtmlComponent {
	public String render(String content) throws HtmlRenderException {
		return renderStart() + renderEnd();
	}
	
	abstract public String renderStart() throws HtmlRenderException;
	
	public String renderBody(String content) {
		return "";
	}
	
	abstract public String renderEnd() throws HtmlRenderException;
}
