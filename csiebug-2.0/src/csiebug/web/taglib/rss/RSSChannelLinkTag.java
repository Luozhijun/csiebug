package csiebug.web.taglib.rss;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import csiebug.util.AssertUtility;
import csiebug.web.html.rss.HtmlRSSChannelLink;


/**
 * RSS Channel Link tag
 * @author George_Tsai
 * @version 2010/10/19
 */

public class RSSChannelLinkTag extends BodyTagSupport implements TryCatchFinally {
	static final long serialVersionUID = 20101019;
	
	private String feedId;
	private String asLink;
	
	public int doStartTag() throws JspException {
		try {
			AssertUtility.notNullAndNotSpace(feedId);
            JspWriter out = pageContext.getOut();
            HtmlRSSChannelLink channelLink;
            if(asLink == null) {
            	channelLink = new HtmlRSSChannelLink(feedId, null);
            } else {
            	channelLink = new HtmlRSSChannelLink(feedId, Boolean.parseBoolean(asLink));
            }
            out.print(channelLink.render());
        } 
        catch(Exception e) {
        	throw new JspException(e);
        } 
        
        return SKIP_BODY; 
	}
	
	public void doFinally() {
		
	}
	public void doCatch(Throwable e) throws Throwable {
		throw new JspException("RSSChannelLinkTag Problem: " + e.getMessage());
    }
	
//	元件屬性區
	public void setFeedId(String feedId) {
		this.feedId = feedId;
	}

	public String getFeedId() {
		return feedId;
	}

	public void setAsLink(String asLink) {
		this.asLink = asLink;
	}

	public String getAsLink() {
		return asLink;
	}
}