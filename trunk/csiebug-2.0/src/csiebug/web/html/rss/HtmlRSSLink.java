/**
 * Copyright (c) 2010 George Tsai. All rights reserved.
 * For more information on the George Tsai, please see http://www.pixnet.net/blog/csiebug
 */
package csiebug.web.html.rss;

import csiebug.util.AssertUtility;
import csiebug.web.html.HtmlBuilder;
import csiebug.web.html.HtmlComponentOnlyBody;
import csiebug.web.html.HtmlRenderException;

/**
 * @author George_Tsai
 * @version 2010/10/19
 */
public class HtmlRSSLink extends HtmlComponentOnlyBody {
	private String feedId;
	private String attributeId;
	private Boolean asLink;
	
	public HtmlRSSLink() {
	}
	
	public HtmlRSSLink(String feedId, String attributeId, Boolean asLink) {
		this.feedId = feedId;
		this.attributeId = attributeId;
		this.asLink = asLink;
	}

	/* (non-Javadoc)
	 * @see csiebug.web.html.HtmlComponentOnlyBody#renderBody(java.lang.String)
	 */
	public String renderBody(String content) throws HtmlRenderException {
		AssertUtility.notNullAndNotSpace(feedId);
		AssertUtility.notNullAndNotSpace(attributeId);
        HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		HtmlRSSChannelAttribute channelLink = new HtmlRSSChannelAttribute(feedId, attributeId);
		String attributeValue = channelLink.render();
        
		if(asLink == null || asLink.booleanValue()) {
			htmlBuilder.aStart().href(attributeValue).target("_blank").tagClose().text(attributeValue).aEnd();
		} else {
			htmlBuilder.appendString(attributeValue);
		}
		
		return htmlBuilder.toString();
	}
	
	public void setFeedId(String feedId) {
		this.feedId = feedId;
	}

	public String getFeedId() {
		return feedId;
	}

	public void setAsLink(Boolean asLink) {
		this.asLink = asLink;
	}

	public Boolean getAsLink() {
		return asLink;
	}

	public void setAttributeId(String attributeId) {
		this.attributeId = attributeId;
	}

	public String getAttributeId() {
		return attributeId;
	}
}
