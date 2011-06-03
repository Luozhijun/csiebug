/**
 * Copyright (c) 2010 George Tsai. All rights reserved.
 * For more information on the George Tsai, please see http://www.pixnet.net/blog/csiebug
 */
package csiebug.web.html;

/**
 * @author George_Tsai
 * @version 2010/6/10
 */
public abstract class HtmlComponent {
	public String render() throws HtmlRenderException {
		return render("");
	}
	
	public String render(String content) throws HtmlRenderException {
		return renderStart() + renderBody(content) + renderEnd();
	}
	
	abstract public String renderStart() throws HtmlRenderException;
	abstract public String renderBody(String content) throws HtmlRenderException;
	abstract public String renderEnd() throws HtmlRenderException;
}
