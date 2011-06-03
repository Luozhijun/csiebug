/**
 * Copyright (c) 2010 George Tsai. All rights reserved.
 * For more information on the George Tsai, please see http://www.pixnet.net/blog/csiebug
 */
package csiebug.web.html.rss;

import org.dom4j.Element;

import csiebug.util.AssertUtility;
import csiebug.web.html.HtmlBuilder;
import csiebug.web.html.HtmlComponentOnlyBody;
import csiebug.web.html.HtmlRenderException;

/**
 * @author George_Tsai
 * @version 2010/10/19
 */
public class HtmlRSSItemLink extends HtmlComponentOnlyBody {
	private Element item;
	private Boolean asLink;
	
	public HtmlRSSItemLink() {
	}
	
	public HtmlRSSItemLink(Element item, Boolean asLink) {
		this.item = item;
		this.asLink = asLink;
	}

	/* (non-Javadoc)
	 * @see csiebug.web.html.HtmlComponentOnlyBody#renderBody(java.lang.String)
	 */
	public String renderBody(String content) throws HtmlRenderException {
		AssertUtility.notNull(item);
        HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		HtmlRSSItemAttribute itemLink = new HtmlRSSItemAttribute(item, "link");
		String attributeValue = itemLink.render();
        
		if(asLink == null || asLink.booleanValue()) {
			htmlBuilder.aStart().href(attributeValue).target("_blank").tagClose().text(attributeValue).aEnd();
		} else {
			htmlBuilder.appendString(attributeValue);
		}
		
		return htmlBuilder.toString();
	}
	
	public void setItem(Element item) {
		this.item = item;
	}

	public Element getItem() {
		return item;
	}

	public void setAsLink(Boolean asLink) {
		this.asLink = asLink;
	}

	public Boolean getAsLink() {
		return asLink;
	}
}
