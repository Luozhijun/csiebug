/**
 * Copyright (c) 2010 George Tsai. All rights reserved.
 * For more information on the George Tsai, please see http://www.pixnet.net/blog/csiebug
 */
package csiebug.web.html.rss;

/**
 * @author George_Tsai
 * @version 2010/10/19
 */
public class HtmlRSSChannelLink extends HtmlRSSLink {
	public HtmlRSSChannelLink(String feedId, Boolean asLink) {
		super(feedId, "link", asLink);
	}
}
