/**
 * Copyright (c) 2010 George Tsai. All rights reserved.
 * For more information on the George Tsai, please see http://www.pixnet.net/blog/csiebug
 */
package csiebug.util.rss;

/**
 * @author George_Tsai
 * @version 2010/10/18
 */
public class RSSCategory {
	private String domain;
	private String text;
	
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getDomain() {
		return domain;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getText() {
		return text;
	}
}
