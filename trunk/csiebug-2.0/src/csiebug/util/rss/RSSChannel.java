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
public class RSSChannel {
	private String title;
	private String description;
	private String link;
	private String copyright;
	private RSSCategory category;
    
    private String docs;
    private String language;
    private Calendar lastBuildDate;
    private String managingEditor;
    private Calendar pubDate;
    private String webMaster;
    
    private String generator;
    private RSSImage image;
	
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
	}
	public String getLink() {
		return link;
	}
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	public String getCopyright() {
		return copyright;
	}
	public void setCategory(RSSCategory category) {
		this.category = category;
	}
	public RSSCategory getCategory() {
		return category;
	}
	public void setDocs(String docs) {
		this.docs = docs;
	}
	public String getDocs() {
		return docs;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getLanguage() {
		return language;
	}
	public void setLastBuildDate(Calendar lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}
	public Calendar getLastBuildDate() {
		return lastBuildDate;
	}
	public void setManagingEditor(String managingEditor) {
		this.managingEditor = managingEditor;
	}
	public String getManagingEditor() {
		return managingEditor;
	}
	public void setPubDate(Calendar pubDate) {
		this.pubDate = pubDate;
	}
	public Calendar getPubDate() {
		return pubDate;
	}
	public void setWebMaster(String webMaster) {
		this.webMaster = webMaster;
	}
	public String getWebMaster() {
		return webMaster;
	}
	public void setGenerator(String generator) {
		this.generator = generator;
	}
	public String getGenerator() {
		return generator;
	}
	public void setImage(RSSImage image) {
		this.image = image;
	}
	public RSSImage getImage() {
		return image;
	}
}
