package csiebug.web.taglib.form;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import csiebug.web.html.form.HtmlCheckBox;


/**
 * CheckBox tag
 * @author George_Tsai
 * @version 2009/2/14
 */
public class CheckBoxTag extends BodyTagSupport implements TryCatchFinally {
	static final long serialVersionUID = 20081222;
	
	private String htmlId;
	private String name;
	private String className;
	private String isReadOnly;
	private String isReturnValue;
	private String checked;
	private String onChange;
	private String onClick;
	private String header;
	private String headerClass;
	private String footer;
	private String footerClass;
	private String displayName;
	private String userValue;
	private String value;
	private String style;
	private String typesetting;
	
	public int doStartTag() throws JspException {
		try { 
            JspWriter out = pageContext.getOut();
            
            HtmlCheckBox checkbox = new HtmlCheckBox(htmlId, name, className, isReadOnly, isReturnValue, checked, onChange, onClick, header, headerClass, footer, footerClass, displayName, userValue, value, style, typesetting);
            
            out.println(checkbox.render());
        } 
        catch(Exception e) {
        	throw new JspException(e);
        } 
 
        return SKIP_BODY; 
	}
	public void doFinally() {
		
	}
	public void doCatch(Throwable e) throws Throwable {
        throw new JspException("CheckBoxTag Problem: " + e.getMessage());
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
	
	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}
	
	public String getOnClick() {
		return this.onClick;
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
	
	public void setFooter(String footer) {
		this.footer = footer;
	}
	
	public String getFooter() {
		return this.footer;
	}
	
	public void setFooterClass(String footerClass) {
		this.footerClass = footerClass;
	}
	
	public String getFooterClass() {
		return this.footerClass;
	}
	
	public void setChecked(String defaultValue) {
		this.checked = defaultValue;
	}
	
	public String getChecked() {
		return this.checked;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getDisplayName() {
		return this.displayName;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getClassName() {
		return this.className;
	}
	
	public void setUserValue(String value) {
		this.userValue = value;
	}
	
	public String getUserValue() {
		return this.userValue;
	}
	
	public void setIsReadOnly(String value) {
		this.isReadOnly = value;
	}
	
	public String getIsReadOnly() {
		return this.isReadOnly;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public void setStyle(String value) {
		this.style = value;
	}
	
	public String getStyle() {
		return this.style;
	}
	
	public void setTypesetting(String value) {
		this.typesetting = value;
	}
	
	public String getTypesetting() {
		return this.typesetting;
	}
}