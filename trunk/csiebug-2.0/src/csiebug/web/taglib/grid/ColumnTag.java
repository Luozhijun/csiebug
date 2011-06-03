package csiebug.web.taglib.grid;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import csiebug.web.html.grid.HtmlColumn;

/**
 * column tag
 * @author George_Tsai
 * @version 2009/2/20
 */

public class ColumnTag extends BodyTagSupport implements TryCatchFinally {
	static final long serialVersionUID = 20081217;
	
	private String fieldname;
	private String style;
	private String className;
	private String title;
	private String dataType;
	private String colspan;
	private String sortable;
	private String selAllScript;
	private String selectable;
	private String onClick;
	private String onDblClick;
	private String maxLineLength;
	private String typesettingAlgorithm;
	
	private RowTag rowTag;
	private HtmlColumn column;
	
	public int doStartTag() throws JspException {
		try {
            JspWriter out = pageContext.getOut(); 
            
            rowTag = (RowTag)getParent();
            
            column = new HtmlColumn(rowTag.getRow(), fieldname, style, className, title, dataType, colspan, sortable, selAllScript, selectable, onClick, onDblClick, maxLineLength, typesettingAlgorithm);

            out.println(column.renderStart());
        } 
        catch(Exception e) {
        	throw new JspException(e);
        } 
        
        return EVAL_BODY_BUFFERED; 
	}
	
	public int doAfterBody() throws JspException {
		try {
			JspWriter out = bodyContent.getEnclosingWriter();
			
			out.println(column.renderBody(bodyContent.getString()));
        } 
        catch(Exception e) {
        	throw new JspException(e);
        } 
 
        return SKIP_BODY; 
	}
	
	public int doEndTag() throws JspException {
		try { 
            JspWriter out = pageContext.getOut(); 
            
            out.println(column.renderEnd());
        } 
        catch(Exception e) {
        	throw new JspException(e);
        } 
 
        return EVAL_PAGE; 
	}
	public void doFinally() {
		
	}
	public void doCatch(Throwable e) throws Throwable {
		throw new JspException("ColumnTag Problem: " + e.getMessage());
    }
	
//	元件屬性區
	public void setFieldname(String value) {
		this.fieldname = value;
	}
	
	public String getFieldname() {
		return this.fieldname;
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
	
	public void setTitle(String header) {
		this.title = header;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setDataType(String cellType) {
		this.dataType = cellType;
	}
	
	public String getDataType() {
		return this.dataType;
	}
	
	public void setColspan(String colspan) {
		this.colspan = colspan;
	}
	
	public String getColspan() {
		return this.colspan;
	}
	
	public void setSortable(String sortable) {
		this.sortable = sortable;
	}
	
	public String getSortable() {
		return this.sortable;
	}
	
	public void setSelAllScript(String script) {
		this.selAllScript = script;
	}
	
	public String getSelAllScript() {
		return this.selAllScript;
	}

	public void setSelectable(String selectable) {
		this.selectable = selectable;
	}

	public String getSelectable() {
		return selectable;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}

	public String getOnClick() {
		return onClick;
	}

	public void setOnDblClick(String onDblClick) {
		this.onDblClick = onDblClick;
	}

	public String getOnDblClick() {
		return onDblClick;
	}

	public void setMaxLineLength(String maxLineLength) {
		this.maxLineLength = maxLineLength;
	}

	public String getMaxLineLength() {
		return maxLineLength;
	}

	public void setTypesettingAlgorithm(String typesettingAlgorithm) {
		this.typesettingAlgorithm = typesettingAlgorithm;
	}

	public String getTypesettingAlgorithm() {
		return typesettingAlgorithm;
	}
}