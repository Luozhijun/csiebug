/**
 * Copyright (c) 2010 George Tsai. All rights reserved.
 * For more information on the George Tsai, please see http://www.pixnet.net/blog/csiebug
 */
package csiebug.web.html.rss;

import org.dom4j.Document;
import org.dom4j.Element;

import csiebug.util.AssertUtility;
import csiebug.util.WebUtility;
import csiebug.web.html.HtmlBuilder;
import csiebug.web.html.HtmlComponentOnlyBody;
import csiebug.web.html.HtmlRenderException;

/**
 * @author George_Tsai
 * @version 2010/10/19
 */
public class HtmlRSSChannelImage extends HtmlComponentOnlyBody {
	private String feedId;
	private WebUtility webutil = new WebUtility();
	
	public HtmlRSSChannelImage() {
	}
	
	public HtmlRSSChannelImage(String feedId) {
		this.feedId = feedId;
	}

	/* (non-Javadoc)
	 * @see csiebug.web.html.HtmlComponentOnlyBody#renderBody(java.lang.String)
	 */
	public String renderBody(String content) throws HtmlRenderException {
		AssertUtility.notNullAndNotSpace(feedId);
        HtmlBuilder htmlBuilder = new HtmlBuilder();
		
        Document document = (Document)webutil.getRequestAttribute(feedId);
        Element channelElement = document.getRootElement().element("channel");
        Element imageElement = channelElement.element("image");
        if(imageElement != null && imageElement.element("url") != null) {
        	if(imageElement.element("link") != null) {
        		String link = imageElement.elementText("link");
        		htmlBuilder.aStart().href(link).target("_blank").tagClose();
        	}
        	
        	htmlBuilder.imgStart().src(imageElement.element("url").getText());
        	
        	if(imageElement.element("title") != null) {
        		htmlBuilder.title(imageElement.elementText("title"));
        	}
        	
        	if(imageElement.element("description") != null) {
        		htmlBuilder.alt(imageElement.elementText("description"));
        	}
        	
        	StringBuffer style = new StringBuffer();
        	if(imageElement.element("width") != null) {
        		style.append("width:" + imageElement.elementText("width"));
        	}
        	if(imageElement.element("height") != null) {
        		style.append("height:" + imageElement.elementText("height"));
        	}
        	if(!style.toString().trim().equals("")) {
        		htmlBuilder.style(style.toString());
        	}
        	
        	htmlBuilder.tagClose().imgEnd();
        	
        	if(imageElement.element("link") != null) {
        		htmlBuilder.aEnd();
        	}
        }
        
		return htmlBuilder.toString();
	}
	
	public void setFeedId(String feedId) {
		this.feedId = feedId;
	}

	public String getFeedId() {
		return feedId;
	}
}
