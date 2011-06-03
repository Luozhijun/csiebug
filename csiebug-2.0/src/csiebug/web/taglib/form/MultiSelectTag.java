package csiebug.web.taglib.form;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import csiebug.web.html.form.HtmlMultiSelect;

/**
 * MultiSelect tag
 * @author George_Tsai
 * @version 2009/2/14
 */
public class MultiSelectTag extends BodyTagSupport implements TryCatchFinally {
	static final long serialVersionUID = 20081226;
	
	private String htmlId;
	private String name;
	private String isReadOnly;
	private String className;
	private String isReturnValue;
	private String defaultValue;
	private String onChange;
	private String header;
	private String headerClass;
	private String isRequired;
	private String userValue;
	private String style;
	private String totalOption;
	private String size;
	private String typesetting;
	
	public int doStartTag() throws JspException {
		try { 
            JspWriter out = pageContext.getOut();
            
            HtmlMultiSelect multiSelect = new HtmlMultiSelect(htmlId, name, isReadOnly, className, isReturnValue, defaultValue, onChange, header, headerClass, isRequired, userValue, style, totalOption, size, typesetting);
			
            out.println(multiSelect.render());
            
        } 
        catch(Exception e) {
        	throw new JspException(e);
        } 
 
        return SKIP_BODY; 
	}
	
	public void doFinally() {
		
	}
	public void doCatch(Throwable e) throws Throwable {
		throw new JspException("MultiSelectTag Problem: " + e.getMessage());
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
	
	public void setIsReadOnly(String readOnly) {
		this.isReadOnly = readOnly;
	}
	
	public String getIsReadOnly() {
		return this.isReadOnly;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getClassName() {
		return this.className;
	}
	
	public void setIsReturnValue(String returnValue) {
		this.isReturnValue = returnValue;
	}
	
	public String getIsReturnValue() {
		return this.isReturnValue;
	}
	
	public void setOnChange(String onChange) {
		this.onChange = onChange;
	}
	
	public String getOnChange() {
		return this.onChange;
	}
	
	public void setHeader(String header) {
		this.header = header;
	}
	
	public String getHeader() {
		return this.header;
	}
	
	public void setHeaderClass(String headerClass) {
		this.headerClass = headerClass;
	}
	
	public String getHeaderClass() {
		return this.headerClass;
	}
	
	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}
	
	public String getIsRequired() {
		return this.isRequired;
	}
	
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	public String getDefaultValue() {
		return this.defaultValue;
	}
	
	public void setUserValue(String value) {
		this.userValue = value;
	}
	
	public String getUserValue() {
		return this.userValue;
	}
	
	public void setStyle(String value) {
		this.style = value;
	}
	
	public String getStyle() {
		return this.style;
	}
	
	public void setTotalOption(String value) {
		this.totalOption = value;
	}
	
	public String getTotalOption() {
		return this.totalOption;
	}
	
	public void setSize(String value) {
		this.size = value;
	}
	
	public String getSize() {
		return this.size;
	}
	
	public void setTypesetting(String value) {
		this.typesetting = value;
	}
	
	public String getTypesetting() {
		return this.typesetting;
	}
}