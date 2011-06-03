/**
 * Copyright (c) 2010 George Tsai. All rights reserved.
 * For more information on the George Tsai, please see http://www.pixnet.net/blog/csiebug
 */
package csiebug.util.rss;

/**
 * @author George_Tsai
 * @version 2010/10/18
 */
public class RSSImage {
	private String url;
	private String title;
	private String link;
	private String description;
	private String width;
	private String height;
	
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl() {
		return url;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getLink() {
		return link;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getWidth() {
		return width;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getHeight() {
		return height;
	}
}
