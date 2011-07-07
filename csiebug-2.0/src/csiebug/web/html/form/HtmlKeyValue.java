package csiebug.web.html.form;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.naming.NamingException;

import csiebug.util.AssertUtility;
import csiebug.util.WebUtility;
import csiebug.web.html.HtmlBuilder;
import csiebug.web.html.HtmlComponent;
import csiebug.web.html.HtmlRenderException;


/**
 * 產生HTML ValueName Text
 * @author George_Tsai
 * @version 2009/8/28
 */

public class HtmlKeyValue extends HtmlComponent {
	private String htmlId;
	private String name;
	private String isReadOnly;
	private String className;
	private String isReturnValue;
	private String defaultValue;
	private String onChange;
	private String header;
	private String headerClass;
	private String footer;
	private String footerClass;
	private String isRequired;
	private String userValue;
	private String style;
	private String option;
	private boolean caseSensitive;
	private String op;
	private String valueReadOnly;
	private String typesetting;
	private WebUtility webutil = new WebUtility();
	
	public HtmlKeyValue(String htmlId, String name, String isReadOnly, String className, String isReturnValue, String defaultValue, String onChange, String header, String headerClass, String footer, String footerClass, String isRequired, String userValue, String style, String option, boolean caseSensitive, String op, String valueReadOnly, String typesetting) {
		this.htmlId = htmlId;
		this.name = name;
		this.isReadOnly = isReadOnly;
		this.className = className;
		this.isReturnValue = isReturnValue;
		this.defaultValue = defaultValue;
		this.onChange = onChange;
		this.header = header;
		this.headerClass = headerClass;
		this.footer = footer;
		this.footerClass = footerClass;
		this.isRequired = isRequired;
		this.userValue = userValue;
		this.style = style;
		this.option = option;
		this.caseSensitive = caseSensitive;
		this.op = op;
		this.valueReadOnly = valueReadOnly;
		this.typesetting = typesetting;
	}
	
	public HtmlKeyValue() {
		
	}
	
	public String renderStart() throws HtmlRenderException {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
        
		try {
	        if(typesetting == null || !typesetting.equalsIgnoreCase("false")) {
	            htmlBuilder.tdStart().className("TdHeader").tagClose();
	            
	            //建立header
            	String strIsRequired = "false";
        		if(htmlId != null) {
        	        if(webutil.getRequestAttribute(htmlId + "_IsRequired") != null) {
        	        	if(webutil.getRequestAttribute(htmlId + "_IsRequired").toString().equals("true")) {
        	        		strIsRequired = "true";
        	        	}
        	        } else {
        	        	if(AssertUtility.isTrue(isRequired)) {
        	            	strIsRequired = "true";
        	            }
        	        }
                } else {
                	if(AssertUtility.isTrue(isRequired)) {
                    	strIsRequired = "true";
                    }
                }
        		
        		if(header != null || strIsRequired.equalsIgnoreCase("true")) {
                    htmlBuilder.labelStart();
                    if(htmlId != null) {
                    	htmlBuilder.id(htmlId + "_header");
                    }
                    if(name != null) {
                    	htmlBuilder.name(name + "_header");
                    }
                    if(headerClass != null) {
                    	htmlBuilder.className(headerClass);
                    } else {
                    	htmlBuilder.className("LabelHeader");
                    }
                    htmlBuilder.tagClose();
                    if(strIsRequired.equalsIgnoreCase("true")) {
                    	htmlBuilder.appendString(webutil.getMessage("common.star"));
                    }
                    if(header != null) {
                    	htmlBuilder.text(header + "：");
                    }
                    htmlBuilder.labelEnd();
                    htmlBuilder.text("  ");
                }
			
	            htmlBuilder.tdEnd();
	            
	            htmlBuilder.tdStart().className("TdBody").tagClose();
	        }
		} catch (UnsupportedEncodingException e) {
			throw new HtmlRenderException(e);
		} catch (NamingException e) {
			throw new HtmlRenderException(e);
		}
        
        return htmlBuilder.toString();
	}
	
	@SuppressWarnings("unchecked")
	public String renderBody(String content) throws HtmlRenderException {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
        
        //建立主體
    	//建立key控制項(EditableSelect)
		//建立option
		Map<String, String> optionMap = null;
		String keyDefaultValue = null;
		if(option != null && webutil.getRequestAttribute(option) != null) {
        	optionMap = (Map<String, String>)webutil.getRequestAttribute(option);
        	
        	if(defaultValue != null && optionMap.get(defaultValue) != null) {
	        	keyDefaultValue = optionMap.get(defaultValue);
        	}
        	
        	if(userValue != null && optionMap.get(webutil.getRequestAttribute(userValue)) != null) {
        		String keyUserValue = optionMap.get(webutil.getRequestAttribute(userValue));
        		webutil.setRequestAttribute(userValue + "_displayText", keyUserValue);
        	}
	    }
        
		if(onChange == null && htmlId != null) {
			onChange = "refreshValue(this);";
		}
		
		HtmlEditableSelect htmlEditableSelect = new HtmlEditableSelect(htmlId, name, isReadOnly, className, isReturnValue, defaultValue, onChange, null, null, null, null, isRequired, userValue, style, option, caseSensitive, op, "false");
		htmlEditableSelect.setKeyValueFlag(true);
		
        //建立value控制項
        HtmlText text = new HtmlText();
        if(htmlId != null) {
        	text.setId(htmlId + "_valueDisplay");
        }
        if(name != null) {
        	text.setName(name + "_valueDisplay");
        }
        if(valueReadOnly != null) {
        	text.setIsReadOnly(valueReadOnly);
        } else {
        	text.setIsReadOnly("true");
        }
        if(isRequired != null) {
        	text.setIsRequired(isRequired);
        } else {
        	text.setIsRequired("false");
        }
        if(isReturnValue != null) {
        	text.setIsReturnValue(isReturnValue);
        } else {
        	text.setIsReturnValue("true");
        }
        text.setTypesetting("false");
        
        if(optionMap != null) {
        	text.setDefaultValue(keyDefaultValue);
        	text.setUserValue(userValue + "_displayText");
        }
        
        htmlBuilder.appendString(htmlEditableSelect.render());
        htmlBuilder.space();
        htmlBuilder.appendString(text.render());
		
        return htmlBuilder.toString();
	}
	
	public String renderEnd() {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
        
        //建立footer
		if(footer != null) {
            htmlBuilder.labelStart();
            if(htmlId != null) {
            	htmlBuilder.id(htmlId + "_footer");
            }
            if(name != null) {
            	htmlBuilder.name(name + "_footer");
            }
            if(footerClass != null) {
            	htmlBuilder.className(footerClass);
            } else {
            	htmlBuilder.className("LabelFooter");
            }
            htmlBuilder.tagClose();
            htmlBuilder.text(footer);
            htmlBuilder.labelEnd();
        }
        
        if(typesetting == null || !typesetting.equalsIgnoreCase("false")) {
        	htmlBuilder.tdEnd();
        }
        
        return htmlBuilder.toString();
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
	
	public void setOp(String value) {
		this.op = value;
	}
	
	public String getOp() {
		return this.op;
	}
	
	public void setTypesetting(String value) {
		this.typesetting = value;
	}
	
	public String getTypesetting() {
		return this.typesetting;
	}

	public void setValueReadOnly(String valueReadOnly) {
		this.valueReadOnly = valueReadOnly;
	}

	public String getValueReadOnly() {
		return valueReadOnly;
	}
}
