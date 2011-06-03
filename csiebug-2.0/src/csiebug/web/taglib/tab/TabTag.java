package csiebug.web.taglib.tab;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import csiebug.web.html.tab.HtmlTab;


/**
 * Tab tag
 * @author George_Tsai
 * @version 2009/6/24
 */
public class TabTag extends BodyTagSupport implements TryCatchFinally {
	static final long serialVersionUID = 20090610;
	
	private String htmlId;
	private String name;
	private String defaultTab;
	private String tabs;
	private String op;
	private String onClick;
	
	public int doStartTag() throws JspException {
		try { 
            JspWriter out = pageContext.getOut();
            
            HtmlTab htmlTab = new HtmlTab(htmlId, name, defaultTab, tabs, op, onClick);
            
            out.println(htmlTab.render());
        } 
        catch(Exception e) {
        	throw new JspException(e);
        } 
 
        return SKIP_BODY; 
	}
	public void doFinally() {
		
	}
	public void doCatch(Throwable e) throws Throwable {
        throw new JspException("TabTag Problem: " + e.getMessage());
    }
	
	//元件屬性區
	public void setId(String id) {
		this.htmlId = id;
	}
	
	public String getId() {
		return this.htmlId;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setTabs(String value) {
		this.tabs = value;
	}
	
	public String getTabs() {
		return this.tabs;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getOp() {
		return op;
	}

	public void setDefaultTab(String defultTab) {
		this.defaultTab = defultTab;
	}

	public String getDefaultTab() {
		return defaultTab;
	}
	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}
	public String getOnClick() {
		return onClick;
	}
	
}