package csiebug.web.taglib.rss;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import org.dom4j.Element;

import csiebug.web.html.rss.HtmlRSSItemAttribute;


/**
 * RSS Item Comments tag
 * @author George_Tsai
 * @version 2010/10/19
 */

public class RSSItemCommentsTag extends BodyTagSupport implements TryCatchFinally {
	static final long serialVersionUID = 20101019;
	
	public int doStartTag() throws JspException {
		try {
	        JspWriter out = pageContext.getOut();
	        RSSItemsTag rssItemsTag = (RSSItemsTag)getParent();
            
            Element item = rssItemsTag.getCurrentItem();
            
            HtmlRSSItemAttribute itemAttribute = new HtmlRSSItemAttribute(item, "comments");
            out.print(itemAttribute.render());
        } 
        catch(Exception e) {
        	throw new JspException(e);
        }
        
        return SKIP_BODY;
    }
	
	public void doFinally() {
		
	}
	public void doCatch(Throwable e) throws Throwable {
		throw new JspException("RSSItemCommentsTag Problem: " + e.getMessage());
    }
	
//	元件屬性區
}