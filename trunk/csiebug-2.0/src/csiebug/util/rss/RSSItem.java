/**
 * Copyright (c) 2010 George Tsai. All rights reserved.
 * For more information on the George Tsai, please see http://www.pixnet.net/blog/csiebug
 */
package csiebug.util.rss;

import java.util.Calendar;

/**
 * @author George_Tsai
 * @version 2010/10/18
 */
public class RSSItem {
	private String title;
	private String description;
	private String link;
	private RSSCategory category;
	private String comments;
	private Calendar pubDate;
	private String guid;
	
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	public void setLink(String link) {
		this.link = link;
		this.guid = link;
	}
	public String getLink() {
		return link;
	}
	public void setCategory(RSSCategory category) {
		this.category = category;
	}
	public RSSCategory getCategory() {
		return category;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getComments() {
		return comments;
	}
	public void setPubDate(Calendar pubDate) {
		this.pubDate = pubDate;
	}
	public Calendar getPubDate() {
		return pubDate;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getGuid() {
		return guid;
	}
}
