/**
 * Copyright (c) 2010 George Tsai. All rights reserved.
 * For more information on the George Tsai, please see http://www.pixnet.net/blog/csiebug
 */
package csiebug.web.html;

/**
 * @author George_Tsai
 * @version 2010/6/10
 */
public abstract class HtmlComponentOnlyBody extends HtmlComponent {
	public String render(String content) throws HtmlRenderException {
		return renderBody(content);
	}
	
	public String renderStart() {
		return "";
	}
	
	abstract public String renderBody(String content) throws HtmlRenderException;
	
	public String renderEnd() {
		return "";
	}
}
