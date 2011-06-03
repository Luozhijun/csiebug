package csiebug.web.taglib.grid;

import java.util.Map;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import csiebug.web.html.grid.HtmlRow;

/**
 * row tag
 * @author George_Tsai
 * @version 2009/3/19
 */

public class RowTag extends BodyTagSupport implements TryCatchFinally {
	static final long serialVersionUID = 20081217;
	
	private String style;
	private String className;
	private String headerClassName;
	private String headerStyle;
	private String onClick;
	private String onDblClick;
	private String rowType;
	private String selectable;
	private String title;
	
	private TableTag tableTag;
	private HtmlRow row;
	
	public int doStartTag() throws JspException {
		try { 
            JspWriter out = pageContext.getOut(); 
            
            tableTag = (TableTag)getParent();
            
            row = new HtmlRow(tableTag.getTable(), style, className, headerClassName, headerStyle, onClick, onDblClick, rowType, selectable, title);
            
            out.print(row.renderStart());
        } 
        catch(Exception e) {
        	throw new JspException(e);
        } 
        if(tableTag.getCurrentMapIndex() != -1 && rowType != null && rowType.equalsIgnoreCase("header")) {
        	return SKIP_BODY;
        } else {
        	return EVAL_BODY_INCLUDE;
        }
	}
	
	public int doEndTag() throws JspException {
		try { 
            JspWriter out = pageContext.getOut(); 
            
            out.println(row.renderEnd());
        } 
        catch(Exception e) {
        	throw new JspException(e);
        }
        
        return EVAL_PAGE;
	}
	public void doFinally() {
		
	}
	public void doCatch(Throwable e) throws Throwable {
		throw new JspException("RowTag Problem: " + e.getMessage());
    }
	
	public Map<String, String> getCurrentMap() {
		return row.getCurrentMap();
	}
	
//	元件屬性區
	public void setRow(HtmlRow row) {
		this.row = row;
	}
	
	public HtmlRow getRow() {
		return this.row;
	}
	
	public void setCurrentColumnIndex(int value) {
		row.setCurrentColumnIndex(value);
	}
	
	public int getCurrentColumnIndex() {
		return row.getCurrentColumnIndex();
	}
	
	public void setStyle(String value) {
		this.style = value;
	}
	
	public String getStyle() {
		return this.style;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getClassName() {
		return this.className;
	}
	
	public void setHeaderStyle(String value) {
		this.headerStyle = value;
	}
	
	public String getHeaderStyle() {
		return this.headerStyle;
	}
	
	public void setHeaderClassName(String className) {
		this.headerClassName = className;
	}
	
	public String getHeaderClassName() {
		return this.headerClassName;
	}
	
	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}
	
	public String getOnClick() {
		return this.onClick;
	}
	
	public void setOnDblClick(String onDblClick) {
		this.onDblClick = onDblClick;
	}
	
	public String getOnDblClick() {
		return this.onDblClick;
	}
	
	public void setRowType(String rowType) {
		this.rowType = rowType;
	}
	
	public String getRowType() {
		return this.rowType;
	}
	
	public void setSelectable(String checkable) {
		this.selectable = checkable;
	}
	
	public String getSelectable() {
		return this.selectable;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}