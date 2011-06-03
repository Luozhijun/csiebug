package csiebug.web.taglib.treeview;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import csiebug.util.StringUtility;
import csiebug.web.html.treeview.HtmlSidebar;


/**
 * Sidebar Tag
 * @author George_Tsai
 * @version 2009/8/7
 */

public class SidebarTag extends BodyTagSupport implements TryCatchFinally {
	static final long serialVersionUID = 20090110;
	
	private String htmlId;
	private String list;
	private String imagePath;
	private String targetFrame;
	
	private ArrayList<Map<String, String>> treelist;
	
	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {
		try { 
            JspWriter out = pageContext.getOut();
            String strHtml = "";
            
            if(list != null && pageContext.getRequest().getAttribute(list) != null) {
            	treelist = (ArrayList<Map<String, String>>)pageContext.getRequest().getAttribute(list);
            		
            	HtmlSidebar htmlSidebar = new HtmlSidebar(htmlId, treelist, imagePath, targetFrame);
            		
            	strHtml = htmlSidebar.getSidebar();
            }
            
            out.println(strHtml);
        } 
        catch(Exception e) {
        	throw new JspException(e);
        } 
 
        return SKIP_BODY; 
	}
	public void doFinally() {
		
	}
	public void doCatch(Throwable e) throws Throwable {
        throw new JspException("SidebarTag Problem: " + e.getMessage());
    }
	
	//元件屬性區
	public void setList(String list) {
		this.list = list;
	}
	
	public String getList() {
		return this.list;
	}
	
	public void setImagePath(String imagePath) {
		this.imagePath = StringUtility.removeStartEndSlash(imagePath);
	}
	
	public String getImagePath() {
		return this.imagePath;
	}
	
	public void setTargetFrame(String targetFrame) {
		this.targetFrame = targetFrame;
	}
	
	public String getTargetFrame() {
		return this.targetFrame;
	}
	public void setId(String htmlId) {
		this.htmlId = htmlId;
	}
	public String getId() {
		return htmlId;
	}
}