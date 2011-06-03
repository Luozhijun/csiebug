package csiebug.web.taglib.rss;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import org.dom4j.Element;

import csiebug.web.html.rss.HtmlRSSItemAttribute;


/**
 * RSS Item Description tag
 * @author George_Tsai
 * @version 2010/10/19
 */

public class RSSItemDescriptionTag extends BodyTagSupport implements TryCatchFinally {
	static final long serialVersionUID = 20101019;
	
	public int doStartTag() throws JspException {
		try {
	        JspWriter out = pageContext.getOut();
	        RSSItemsTag rssItemsTag = (RSSItemsTag)getParent();
            
            Element item = rssItemsTag.getCurrentItem();
            
            HtmlRSSItemAttribute itemAttribute = new HtmlRSSItemAttribute(item, "description");
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
		throw new JspException("RSSItemDescriptionTag Problem: " + e.getMessage());
    }
	
//	元件屬性區
}