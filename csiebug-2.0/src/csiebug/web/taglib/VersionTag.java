package csiebug.web.taglib;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import csiebug.web.html.HtmlBuilder;

/**
 * 版本tag
 * @author George_Tsai
 * @version 2008/12/16
 */

public class VersionTag extends BodyTagSupport implements TryCatchFinally {
	static final long serialVersionUID = 20081216;
	
	public int doStartTag() throws JspException {
		return SKIP_BODY; 
	}
	
	public int doEndTag() throws JspException {
		try { 
            JspWriter out = pageContext.getOut();
            HtmlBuilder htmlBuilder = new HtmlBuilder();
            
            htmlBuilder.hr();
            htmlBuilder.appendString("<fieldset><legend onClick=\"about();\">關於 csiebug's TagLib UI</legend>");
            htmlBuilder.divStart().id("about").style("display:none").tagClose();
            htmlBuilder.appendString("csiebug's TagLib UI").br();
            htmlBuilder.appendString("Version: 1.0").br().br();
            htmlBuilder.appendString("© 2008 csiebug. All Rights Reserved").br();
            htmlBuilder.divEnd();
            htmlBuilder.appendString("</fieldset>");
            
            out.println(htmlBuilder.toString());
        } 
        catch(Exception e) {
        	throw new JspException(e);
        } 
 
        return EVAL_PAGE; 
	}
	public void doFinally() {
		
	}
	public void doCatch(Throwable e) throws Throwable {
        throw new JspException("VersionTag Problem: " + e.getMessage());
    }
}