package csiebug.web.taglib.office.excel;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import csiebug.web.html.office.excel.HtmlColumns;

/**
 * dynamic columns tag
 * @author George_Tsai
 * @version 2009/2/20
 */

public class ColumnsTag extends BodyTagSupport implements TryCatchFinally {
	static final long serialVersionUID = 20081217;
	
	private String dynFields;
	
	private RowTag rowTag;
	
	public int doStartTag() throws JspException {
		try {
            JspWriter out = pageContext.getOut(); 
            
            rowTag = (RowTag)getParent();
            
            HtmlColumns columns = new HtmlColumns(rowTag.getRow(), dynFields);

            out.println(columns.render());
        } 
        catch(Exception e) {
        	throw new JspException(e);
        } 
        
        return SKIP_BODY; 
	}
	
	public void doFinally() {
		
	}
	public void doCatch(Throwable e) throws Throwable {
		throw new JspException("ColumnsTag Problem: " + e.getMessage());
    }
	
//	元件屬性區
	public void setDynFields(String value) {
		this.dynFields = value;
	}
	
	public String getDynFields() {
		return this.dynFields;
	}
	
}