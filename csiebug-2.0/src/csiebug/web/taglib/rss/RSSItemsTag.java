package csiebug.web.taglib.rss;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import org.dom4j.Document;
import org.dom4j.Element;

import csiebug.util.AssertUtility;
import csiebug.util.WebUtility;

/**
 * RSS Items tag
 * @author George_Tsai
 * @version 2010/10/19
 */

public class RSSItemsTag extends BodyTagSupport implements TryCatchFinally {
	static final long serialVersionUID = 20101019;
	
	private String feedId;
	
	private WebUtility webutil = new WebUtility();
	private List<Element> items;
	private int currentItemIndex = 0;
	
	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {
		AssertUtility.notNullAndNotSpace(feedId);
		
		if(items == null) {
			Document document = (Document)webutil.getRequestAttribute(feedId);
			items = document.getRootElement().element("channel").elements("item");
		}
		
		return EVAL_BODY_INCLUDE; 
	}
	
	public int doAfterBody() throws JspException {
		try { 
			currentItemIndex++;
			
			if(hasNext()) {
	        	return SKIP_BODY;
	        } else {
	        	return EVAL_BODY_AGAIN;
	        }
		} 
        catch(Exception e) {
        	throw new JspException(e);
        }
	}
	
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
	public void doFinally() {
		
	}
	public void doCatch(Throwable e) throws Throwable {
		throw new JspException("RSSItemsTag Problem: " + e.getMessage());
    }
	
	public Element getCurrentItem() {
		return items.get(currentItemIndex);
	}
	
	public boolean hasNext() {
		return currentItemIndex == items.size();
	}
	
//	元件屬性區
	public void setFeedId(String feedId) {
		this.feedId = feedId;
	}

	public String getFeedId() {
		return feedId;
	} 
}