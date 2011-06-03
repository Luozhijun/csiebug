package csiebug.web.taglib.code;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import csiebug.web.html.code.HtmlCode;


/**
 * code tag
 * @author George_Tsai
 * @version 2009/8/5
 */

public class CodeTag extends BodyTagSupport implements TryCatchFinally {
	static final long serialVersionUID = 20081223;
	
	private int tabLength = 4;
	
	private HtmlCode htmlCode;
	
	public int doStartTag() throws JspException {
		try { 
            JspWriter out = pageContext.getOut(); 
            
            htmlCode = new HtmlCode(tabLength);
            
            out.println(htmlCode.renderStart());
        } 
        catch(Exception e) {
        	throw new JspException(e);
        } 
        
        return EVAL_BODY_BUFFERED; 
	}
	
	
	public int doAfterBody() throws JspException {
		try { 
            JspWriter out = getPreviousOut();
            
            out.println(htmlCode.renderBody(getBodyContent().getString()));
        } 
        catch(Exception e) {
        	throw new JspException(e);
        } 
        
		return SKIP_BODY;
	}
	
	public int doEndTag() throws JspException {
		try { 
            JspWriter out = pageContext.getOut(); 
            
            out.println(htmlCode.renderEnd());
        } 
        catch(Exception e) {
        	throw new JspException(e);
        } 
 
        return EVAL_PAGE; 
	}
	public void doFinally() {
		
	}
	public void doCatch(Throwable e) throws Throwable {
		throw new JspException("CodeTag Problem: " + e.getMessage());
    }
	
//	元件屬性區
	public void setTabLength(int spaceCount) {
		tabLength = spaceCount;
	}
}