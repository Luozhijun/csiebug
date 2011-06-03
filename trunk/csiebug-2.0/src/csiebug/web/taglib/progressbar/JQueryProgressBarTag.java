package csiebug.web.taglib.progressbar;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import csiebug.web.html.progressbar.HtmlJQueryProgressBar;


/**
 * ProgressBar tag
 * @author George_Tsai
 * @version 2009/6/10
 */
public class JQueryProgressBarTag extends BodyTagSupport implements TryCatchFinally {
	static final long serialVersionUID = 20090610;
	
	private String uncomplete;
	
	public int doStartTag() throws JspException {
		try { 
            JspWriter out = pageContext.getOut();
            
            HtmlJQueryProgressBar htmlProgressBar;
            
            if(uncomplete != null) {
            	htmlProgressBar = new HtmlJQueryProgressBar(Float.parseFloat(uncomplete));
            } else {
            	htmlProgressBar = new HtmlJQueryProgressBar(0);
            }
            
            out.println(htmlProgressBar.render());
            
        } 
        catch(Exception e) {
        	throw new JspException(e);
        } 
 
        return SKIP_BODY; 
	}
	public void doFinally() {
		
	}
	public void doCatch(Throwable e) throws Throwable {
        throw new JspException("ProgressBarTag Problem: " + e.getMessage());
    }
	
	//元件屬性區
	public void setUncomplete(String value) {
		this.uncomplete = value;
	}
	
	public String getUncomplete() {
		return this.uncomplete;
	}
	
}