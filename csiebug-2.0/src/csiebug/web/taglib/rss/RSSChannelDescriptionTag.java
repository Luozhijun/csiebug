package csiebug.web.taglib.rss;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import csiebug.util.AssertUtility;
import csiebug.web.html.rss.HtmlRSSChannelAttribute;


/**
 * RSS Channel Description tag
 * @author George_Tsai
 * @version 2010/10/19
 */

public class RSSChannelDescriptionTag extends BodyTagSupport implements TryCatchFinally {
	static final long serialVersionUID = 20101019;
	
	private String feedId;
	
	public int doStartTag() throws JspException {
		try {
			AssertUtility.notNullAndNotSpace(feedId);
            JspWriter out = pageContext.getOut();
            HtmlRSSChannelAttribute channelAttribute = new HtmlRSSChannelAttribute(feedId, "description");
            out.print(channelAttribute.render());
        } 
        catch(Exception e) {
        	throw new JspException(e);
        } 
        
        return SKIP_BODY; 
	}
	
	public void doFinally() {
		
	}
	public void doCatch(Throwable e) throws Throwable {
		throw new JspException("RSSChannelDescriptionTag Problem: " + e.getMessage());
    }
	
//	元件屬性區
	public void setFeedId(String feedId) {
		this.feedId = feedId;
	}

	public String getFeedId() {
		return feedId;
	}
}