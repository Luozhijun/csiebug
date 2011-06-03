package csiebug.web.taglib.treeview;

import java.util.ArrayList;
import java.util.Map;

import javax.naming.NamingException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import csiebug.util.StringUtility;
import csiebug.util.WebUtility;
import csiebug.web.html.treeview.HtmlCheckTreeView;


/**
 * CheckTreeView Tag
 * @author George_Tsai
 * @version 2009/7/1
 */

public class CheckTreeViewTag extends BodyTagSupport implements TryCatchFinally {
	static final long serialVersionUID = 20090110;
	
	private String htmlId;
	private String defaultValue;
	private String isReturnValue;
	private String userValue;
	private String list;
	private String imagePath;
	private String op = "multi";
	private String linked = "true";
	
	private ArrayList<Map<String, String>> treelist;
	
	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {
		try { 
            JspWriter out = pageContext.getOut();
            String strHtml = "";
            
            if(list != null && pageContext.getRequest().getAttribute(list) != null) {
            	treelist = (ArrayList<Map<String, String>>)pageContext.getRequest().getAttribute(list);
            	
            	HtmlCheckTreeView htmlTreeView = new HtmlCheckTreeView(htmlId, getValue(), treelist, imagePath, op, Boolean.parseBoolean(linked));
            		
            	strHtml = htmlTreeView.getTreeView();
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
        throw new JspException("CheckTreeViewTag Problem: " + e.getMessage());
    }
	
	private String getValue() throws NamingException {
		WebUtility webutil = new WebUtility();
    	String strValue = "";
    	if(defaultValue != null) {
           	strValue = defaultValue;
        }
        if((isReturnValue == null || isReturnValue.equalsIgnoreCase("true")) && webutil.getRequest().getParameter(htmlId) != null) {
        	strValue = StringUtility.cleanXSSPattern(webutil.getRequest().getParameter(htmlId));
        }
        if(userValue != null && webutil.getRequestAttribute(userValue) != null) {
        	strValue = webutil.getRequestAttribute(userValue).toString();
        }
        
        return strValue;
	}
	
	//元件屬性區
	public void setId(String id) {
		this.htmlId = id;
	}
	
	public String getId() {
		return this.htmlId;
	}
	
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
	
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setIsReturnValue(String isReturnValue) {
		this.isReturnValue = isReturnValue;
	}
	public String getIsReturnValue() {
		return isReturnValue;
	}
	public void setUserValue(String userValue) {
		this.userValue = userValue;
	}
	public String getUserValue() {
		return userValue;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public String getOp() {
		return op;
	}
	public void setLinked(String linked) {
		this.linked = linked;
	}
	public String getLinked() {
		return linked;
	}
}