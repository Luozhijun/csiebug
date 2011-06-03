package csiebug.web.taglib;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import csiebug.web.html.HtmlBuilder;

/**
 * 作者資訊tag
 * @author George_Tsai
 * @version 2008/12/16
 */

public class AuthorTag extends BodyTagSupport implements TryCatchFinally {
	static final long serialVersionUID = 20081216;
	
	public int doStartTag() throws JspException {
		return SKIP_BODY; 
	}
	
	public int doEndTag() throws JspException {
		try { 
            JspWriter out = pageContext.getOut();
            HtmlBuilder htmlBuilder = new HtmlBuilder();
            
            htmlBuilder.br();
            htmlBuilder.appendString("<fieldset><legend onClick=\"authorInfo();\">Author Info</legend>");
            htmlBuilder.divStart().id("authorInfo").style("display:none").tagClose();
            htmlBuilder.appendString("Author: Tsai Cheng-Chi").br();
            htmlBuilder.appendString("NetId: csiebug").br();
            htmlBuilder.appendString("Email: ").aStart().href("mailto://csiebug@alumni.csie.ncu.edu.tw").tagClose().appendString("csiebug@alumni.csie.ncu.edu.tw").aEnd().br();
            htmlBuilder.appendString("Blog: ").aStart().href("http://csiebug.pixnet.net/blog").target("_blank").tagClose().appendString("http://csiebug.pixnet.net/blog").aEnd().br();
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
        throw new JspException("AuthorTag Problem: " + e.getMessage());
    }
}