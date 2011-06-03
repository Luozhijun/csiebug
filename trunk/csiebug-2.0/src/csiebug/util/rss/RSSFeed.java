/**
 * Copyright (c) 2010 George Tsai. All rights reserved.
 * For more information on the George Tsai, please see http://www.pixnet.net/blog/csiebug
 */
package csiebug.util.rss;

/**
 * @author George_Tsai
 * @version 2010/10/18
 */
public class RSSFeed {
	private RSSChannel channel;

	public void setChannel(RSSChannel channel) {
		this.channel = channel;
	}

	public RSSChannel getChannel() {
		return channel;
	}
}
