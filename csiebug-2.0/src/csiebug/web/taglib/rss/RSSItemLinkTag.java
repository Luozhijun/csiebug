package csiebug.web.taglib.rss;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import org.dom4j.Element;

import csiebug.web.html.rss.HtmlRSSItemLink;


/**
 * RSS Channel Link tag
 * @author George_Tsai
 * @version 2010/10/19
 */

public class RSSItemLinkTag extends BodyTagSupport implements TryCatchFinally {
	static final long serialVersionUID = 20101019;
	
	private String asLink;
	
	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
            RSSItemsTag rssItemsTag = (RSSItemsTag)getParent();
            
            Element item = rssItemsTag.getCurrentItem();
            
            HtmlRSSItemLink itemLink;
            if(asLink == null) {
            	itemLink = new HtmlRSSItemLink(item, null);
            } else {
            	itemLink = new HtmlRSSItemLink(item, Boolean.parseBoolean(asLink));
            }
            out.print(itemLink.render());
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
	public void setAsLink(String asLink) {
		this.asLink = asLink;
	}

	public String getAsLink() {
		return asLink;
	}
}