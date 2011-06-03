package csiebug.web.taglib.form;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import csiebug.web.html.form.HtmlSelect;

/**
 * SelectField tag
 * @author George_Tsai
 * @version 2009/2/15
 */
public class SelectTag extends BodyTagSupport implements TryCatchFinally {
	static final long serialVersionUID = 20081223;
	
	private String htmlId;
	private String name;
	private String isReadOnly;
	private String className;
	private String isReturnValue;
	private String defaultValue;
	private String onChange;
	private String onBlur;
	private String onClick;
	private String header;
	private String headerClass;
	private String footer;
	private String footerClass;
	private String isRequired;
	private String userValue;
	private String style;
	private String option;
	private String optionClass;
	private String blankOptionFlag;
	private String blankOptionText;
	private String typesetting;
	private String size;
	
	public int doStartTag() throws JspException {
		try { 
            JspWriter out = pageContext.getOut();
            
            HtmlSelect select = new HtmlSelect(htmlId, name, isReadOnly, className, isReturnValue, defaultValue, onChange, onBlur, onClick, header, headerClass, footer, footerClass, isRequired, userValue, style, option, optionClass, blankOptionFlag, blankOptionText, typesetting, size);
            
            out.println(select.renderStart());
            out.println(select.renderBodyStart());
        } 
        catch(Exception e) {
        	throw new JspException(e);
        } 
 
        return EVAL_BODY_INCLUDE; 
	}
	
	public int doAfterBodyTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			
			out.println(getBodyContent().getString());
           
		} catch(Exception e) {
			throw new JspException(e);
		}
		
		return SKIP_BODY;
	}
	
	public int doEndTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			 
			HtmlSelect select = new HtmlSelect(htmlId, name, isReadOnly, className, isReturnValue, defaultValue, onChange, onBlur, onClick, header, headerClass, footer, footerClass, isRequired, userValue, style, option, optionClass, blankOptionFlag, blankOptionText, typesetting, size);
	        
			out.println(select.renderBodyEnd());
	        out.println(select.renderEnd());
		} catch(Exception e) {
			throw new JspException(e);
		}
		
		return EVAL_PAGE;
	}
	
	public void doFinally() {
		
	}
	public void doCatch(Throwable e) throws Throwable {
        throw new JspException("SelectTag Problem: " + e.getMessage());
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
	
	public void setOption(String value) {
		this.option = value;
	}
	
	public String getOption() {
		return this.option;
	}
	
	public void setOptionClass(String optionClass) {
		this.optionClass = optionClass;
	}

	public String getOptionClass() {
		return optionClass;
	}
	
	public void setBlankOptionFlag(String value) {
		this.blankOptionFlag = value;
	}
	
	public String getBlankOptionFlag() {
		return this.blankOptionFlag;
	}
	
	public void setBlankOptionText(String value) {
		this.blankOptionText = value;
	}
	
	public String getBlankOptionText() {
		return this.blankOptionText;
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

	public void setOnBlur(String onBlur) {
		this.onBlur = onBlur;
	}

	public String getOnBlur() {
		return onBlur;
	}
}