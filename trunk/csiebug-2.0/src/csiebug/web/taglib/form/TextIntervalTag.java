package csiebug.web.taglib.form;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import csiebug.util.StringUtility;
import csiebug.web.html.form.OpEnum;
import csiebug.web.html.form.HtmlTextInterval;

/**
 * TextInterval tag
 * @author George_Tsai
 * @version 2009/2/15
 */
public class TextIntervalTag extends BodyTagSupport implements TryCatchFinally {
	static final long serialVersionUID = 20090104;
	
	private String htmlId;
	private String name;
	private String isReadOnly;
	private String className;
	private String isReturnValue;
	private String defaultValueStart;
	private String defaultValueEnd;
	private String dataType;
	private String isMasked;
	private String onChange;
	private String onBlur;
	private String onClick;
	private String onKeyDown;
	private String header;
	private String headerClass;
	private String footer;
	private String footerClass;
	private String buttonClass;
	private String isRequired;
	private String userValueStart;
	private String userValueEnd;
	private String op;
	private String maxlength;
	private String fixlength;
	private String size;
	private String style;
	private String maxvalue;
	private String minvalue;
	private String maxinteger;
	private String maxdecimal;
	private String numberStep;
	private String imagePath;
	private String typesetting;
	
	public int doStartTag() throws JspException {
		try { 
            JspWriter out = pageContext.getOut();
            
            OpEnum opEnum = OpEnum.BUTTON;
            if(op != null && op.equalsIgnoreCase(OpEnum.TEXT.toString())) {
            	opEnum = OpEnum.TEXT;
            }
            
            HtmlTextInterval textInterval = new HtmlTextInterval(htmlId, name, isReadOnly, className, isReturnValue, defaultValueStart, defaultValueEnd, dataType, isMasked, onChange, onBlur, onClick, onKeyDown, header, headerClass, footer, footerClass, buttonClass, isRequired, userValueStart, userValueEnd, opEnum, maxlength, fixlength, size, style, maxvalue, minvalue, maxinteger, maxdecimal, numberStep, imagePath, typesetting);
            
            out.println(textInterval.render());
        } 
        catch(Exception e) {
        	throw new JspException(e);
        } 
 
        return SKIP_BODY; 
	}
	public void doFinally() {
		
	}
	public void doCatch(Throwable e) throws Throwable {
        throw new JspException("TextIntervalTag Problem: " + e.getMessage());
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
	
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	public String getDataType() {
		return this.dataType;
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
	
	public void setButtonClass(String buttonClass) {
		this.buttonClass = buttonClass;
	}
	
	public String getButtonClass() {
		return this.buttonClass;
	}
	
	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}
	
	public String getIsRequired() {
		return this.isRequired;
	}
	
	public void setDefaultValueStart(String defaultValue) {
		this.defaultValueStart = defaultValue;
	}
	
	public String getDefaultValueStart() {
		return this.defaultValueStart;
	}
	
	public void setDefaultValueEnd(String defaultValue) {
		this.defaultValueEnd = defaultValue;
	}
	
	public String getDefaultValueEnd() {
		return this.defaultValueEnd;
	}
	
	public void setUserValueStart(String value) {
		this.userValueStart = value;
	}
	
	public String getUserValueStart() {
		return this.userValueStart;
	}
	
	public void setUserValueEnd(String value) {
		this.userValueEnd = value;
	}
	
	public String getUserValueEnd() {
		return this.userValueEnd;
	}
	
	public void setOp(String value) {
		this.op = value;
	}
	
	public String getOp() {
		return this.op;
	}
	
	public void setMaxlength(String value) {
		this.maxlength = value;
	}
	
	public String getMaxlength() {
		return this.maxlength;
	}
	
	public void setFixlength(String value) {
		this.fixlength = value;
	}
	
	public String getFixlength() {
		return this.fixlength;
	}
	
	public void setSize(String value) {
		this.size = value;
	}
	
	public String getSize() {
		return this.size;
	}
	
	public void setStyle(String value) {
		this.style = value;
	}
	
	public String getStyle() {
		return this.style;
	}
	
	public void setMaxvalue(String value) {
		this.maxvalue = value;
	}
	
	public String getMaxvalue() {
		return this.maxvalue;
	}
	
	public void setMinvalue(String value) {
		this.minvalue = value;
	}
	
	public String getMinvalue() {
		return this.minvalue;
	}
	
	public void setMaxinteger(String value) {
		this.maxinteger = value;
	}
	
	public String getMaxinteger() {
		return this.maxinteger;
	}
	
	public void setMaxdecimal(String value) {
		this.maxdecimal = value;
	}
	
	public String getMaxdecimal() {
		return this.maxdecimal;
	}
	
	public void setImagePath(String value) {
		this.imagePath = StringUtility.removeStartEndSlash(value);
	}
	
	public String getImagePath() {
		return this.imagePath;
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
	public void setOnKeyDown(String onKeyDown) {
		this.onKeyDown = onKeyDown;
	}
	public String getOnKeyDown() {
		return onKeyDown;
	}
	public void setIsMasked(String isMasked) {
		this.isMasked = isMasked;
	}
	public String getIsMasked() {
		return isMasked;
	}
	public void setNumberStep(String numberStep) {
		this.numberStep = numberStep;
	}
	public String getNumberStep() {
		return numberStep;
	}
}