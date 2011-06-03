/**
 * Copyright (c) 2010 George Tsai. All rights reserved.
 * For more information on the George Tsai, please see http://www.pixnet.net/blog/csiebug
 */
package csiebug.web.html.rss;

import org.dom4j.Document;
import org.dom4j.Element;

import csiebug.util.AssertUtility;
import csiebug.util.WebUtility;
import csiebug.web.html.HtmlComponentOnlyBody;
import csiebug.web.html.HtmlRenderException;

/**
 * @author George_Tsai
 * @version 2010/10/19
 */
public class HtmlRSSChannelAttribute extends HtmlComponentOnlyBody {
	private String feedId;
	private String attributeId;
	private WebUtility webutil = new WebUtility();
	
	public HtmlRSSChannelAttribute() {
	}
	
	public HtmlRSSChannelAttribute(String feedId, String attributedId) {
		this.feedId = feedId;
		this.attributeId = attributedId;
	}

	/* (non-Javadoc)
	 * @see csiebug.web.html.HtmlComponentOnlyBody#renderBody(java.lang.String)
	 */
	public String renderBody(String content) throws HtmlRenderException {
		AssertUtility.notNullAndNotSpace(feedId);
        AssertUtility.notNullAndNotSpace(attributeId);
        
		Document document = (Document)webutil.getRequestAttribute(feedId);
        Element channelElement = document.getRootElement().element("channel");
        String attributeValue = "";
        if(channelElement.element(attributeId) != null) {
        	attributeValue = channelElement.elementText(attributeId);
        }
        
		return attributeValue;
	}
	
	public void setFeedId(String feedId) {
		this.feedId = feedId;
	}

	public String getFeedId() {
		return feedId;
	}

	public void setAttributeId(String attributeId) {
		this.attributeId = attributeId;
	}

	public String getAttributeId() {
		return attributeId;
	}
}
