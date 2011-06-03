/**
 * Copyright (c) 2010 George Tsai. All rights reserved.
 * For more information on the George Tsai, please see http://www.pixnet.net/blog/csiebug
 */
package csiebug.web.html.rss;

import org.dom4j.Element;

import csiebug.util.AssertUtility;
import csiebug.web.html.HtmlComponentOnlyBody;
import csiebug.web.html.HtmlRenderException;

/**
 * @author George_Tsai
 * @version 2010/10/19
 */
public class HtmlRSSItemAttribute extends HtmlComponentOnlyBody {
	private Element item;
	private String attributeId;
	
	public HtmlRSSItemAttribute() {
	}
	
	public HtmlRSSItemAttribute(Element item, String attributedId) {
		this.item = item;
		this.attributeId = attributedId;
	}

	/* (non-Javadoc)
	 * @see csiebug.web.html.HtmlComponentOnlyBody#renderBody(java.lang.String)
	 */
	public String renderBody(String content) throws HtmlRenderException {
		AssertUtility.notNull(item);
        AssertUtility.notNullAndNotSpace(attributeId);
        
		String attributeValue = "";
        if(item.element(attributeId) != null) {
        	attributeValue = item.elementText(attributeId);
        }
        
		return attributeValue;
	}
	
	public void setItem(Element item) {
		this.item = item;
	}

	public Element getItem() {
		return item;
	}

	public void setAttributeId(String attributeId) {
		this.attributeId = attributeId;
	}

	public String getAttributeId() {
		return attributeId;
	}
}
