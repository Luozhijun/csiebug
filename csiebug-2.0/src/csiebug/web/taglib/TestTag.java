package csiebug.web.taglib;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

/**
 * 測試tag
 * @author George_Tsai
 * @version 2008/12/16
 */

public class TestTag extends BodyTagSupport implements TryCatchFinally {
	static final long serialVersionUID = 20081216;
	
	public int doStartTag() throws JspException {
		return SKIP_BODY; 
	}
	
	public int doEndTag() throws JspException {
		try { 
            JspWriter out = pageContext.getOut();
            out.println("Hello World");
        } 
        catch(Exception e) {
        	throw new JspException(e);
        } 
 
        return EVAL_PAGE; 
	}
	public void doFinally() {
		
	}
	public void doCatch(Throwable e) throws Throwable {
        throw new JspException("TestTag Problem: " + e.getMessage());
    }
}