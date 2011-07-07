package csiebug.web.html.form;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.naming.NamingException;

import csiebug.util.AssertUtility;
import csiebug.util.StringUtility;
import csiebug.util.WebUtility;
import csiebug.web.html.HtmlBuilder;
import csiebug.web.html.HtmlComponent;
import csiebug.web.html.HtmlRenderException;


/**
 * 產生HTML Select
 * @author George_Tsai
 * @version 2009/6/15
 */

public class HtmlSelect extends HtmlComponent {
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
	private WebUtility webutil = new WebUtility();
	
	public HtmlSelect(String htmlId, String name, String isReadOnly, String className, String isReturnValue, String defaultValue, String onChange, String onBlur, String onClick, String header, String headerClass, String footer, String footerClass, String isRequired, String userValue, String style, String option, String optionClass, String blankOptionFlag, String blankOptionText, String typesetting, String size) {
		this.htmlId = htmlId;
		this.name = name;
		this.isReadOnly = isReadOnly;
		this.className = className;
		this.isReturnValue = isReturnValue;
		this.defaultValue = defaultValue;
		this.onChange = onChange;
		this.onBlur = onBlur;
		this.onClick = onClick;
		this.header = header;
		this.headerClass = headerClass;
		this.footer = footer;
		this.footerClass = footerClass;
		this.isRequired = isRequired;
		this.userValue = userValue;
		this.style = style;
		this.option = option;
		this.optionClass = optionClass;
		this.blankOptionFlag = blankOptionFlag;
		this.blankOptionText = blankOptionText;
		this.typesetting = typesetting;
		this.size = size;
	}
	
	public HtmlSelect() {
		
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
	
	public String renderBodyStart() {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
        
        //建立主體
		htmlBuilder.selectStart();
        
        //設定各樣屬性
        if(htmlId != null) {
        	htmlBuilder.id(htmlId);
        }
        
        if(name != null) {
        	htmlBuilder.name(name);
        }
        
        boolean readOnlyFlag = false;
        if(htmlId != null && AssertUtility.isTrue(webutil.getRequestAttribute(htmlId + "_IsReadOnly"))) {
        	readOnlyFlag = true;
        } else if(AssertUtility.isTrue(isReadOnly)) {
        	readOnlyFlag = true;
        }
        if(readOnlyFlag) {
        	htmlBuilder.disabled();
        	isReadOnly = "true";
        } else {
        	isReadOnly = "false";
        }
        
        if(className != null) {
        	htmlBuilder.className(className);
        } else {
        	if(isReadOnly.equalsIgnoreCase("true")) {
            	htmlBuilder.className("SelectReadOnly");
            } else {
            	htmlBuilder.className("Select");
            }
        }
        
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
        
        htmlBuilder.tagProperty("isRequired", strIsRequired);
        
        String strValue = "";
        if(defaultValue != null) {
        	strValue = defaultValue;
        }
        if((isReturnValue == null || isReturnValue.equalsIgnoreCase("true")) && webutil.getRequest().getParameter(name) != null) {
            strValue = StringUtility.cleanXSSPattern(webutil.getRequest().getParameter(name));
        }
        if(userValue != null && webutil.getRequestAttribute(userValue) != null) {
        	strValue = (String)webutil.getRequestAttribute(userValue);
        }
        htmlBuilder.value(strValue);
        
        if(style != null) {
        	htmlBuilder.style(style);
        }
        
        if(size != null) {
        	htmlBuilder.size(size);
        }
        
        if(onChange != null) {
        	htmlBuilder.onChange(onChange);
        }
        
        if(onBlur != null) {
        	htmlBuilder.onBlur(onBlur);
        }
    	
        if(onClick != null) {
            htmlBuilder.onClick(onClick);
        }
        
        htmlBuilder.tagClose();
		
		return htmlBuilder.toString();
	}
	
	@SuppressWarnings("unchecked")
	public String renderBodyEnd() throws HtmlRenderException {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		try {
			String strValue = "";
	        if(defaultValue != null) {
	        	strValue = defaultValue;
	        }
	        if((isReturnValue == null || isReturnValue.equalsIgnoreCase("true")) && webutil.getRequest().getParameter(name) != null) {
	            strValue = StringUtility.cleanXSSPattern(webutil.getRequest().getParameter(name));
	        }
	        if(userValue != null && webutil.getRequestAttribute(userValue) != null) {
	        	strValue = (String)webutil.getRequestAttribute(userValue);
	        }
	        
	        //如果有設定空白選項則加入一個空白選項
	        if(blankOptionFlag == null || !blankOptionFlag.equalsIgnoreCase("false")) {
	        	if(blankOptionText == null) {
	        		blankOptionText = webutil.getMessage("common.BlankOptionText");
	        	}
	        	
	        	htmlBuilder.appendString("\t");
	    		if(strValue.equalsIgnoreCase("")) {
	    			if(optionClass != null) {
	    				htmlBuilder.optionSelected("", blankOptionText, optionClass);
	    			} else {
	    				htmlBuilder.optionSelected("", blankOptionText);
	    			}
	    		} else {
	    			if(optionClass != null) {
	    				htmlBuilder.option("", blankOptionText, optionClass);
	    			} else {
	    				htmlBuilder.option("", blankOptionText);
	    			}
	    		}
	        }
	        
	        //建立option
	        if(option != null) {
	        	Map<String, String> optionMap = null;
		        if(webutil.getRequestAttribute(option) != null) {
		        	optionMap = (Map<String, String>)webutil.getRequestAttribute(option);
		        	
		        	Object[] aryKey = optionMap.keySet().toArray();
		        	
		        	for(int i = 0; i < aryKey.length; i++) {
		        		htmlBuilder.appendString("\t");
		        		if(strValue.equalsIgnoreCase(aryKey[i].toString())) {
		        			if(optionClass != null) {
		        				htmlBuilder.optionSelected(aryKey[i].toString(), optionMap.get(aryKey[i]), optionClass);
		        			} else {
		        				htmlBuilder.optionSelected(aryKey[i].toString(), optionMap.get(aryKey[i]));
		        			}
		        		} else {
		        			if(optionClass != null) {
		        				htmlBuilder.option(aryKey[i].toString(), optionMap.get(aryKey[i]), optionClass);
		        			} else {
		        				htmlBuilder.option(aryKey[i].toString(), optionMap.get(aryKey[i]));
		        			}
		        		}
		        	}
		        }
		    }
	        
	        htmlBuilder.selectEnd();
		} catch (UnsupportedEncodingException e) {
			throw new HtmlRenderException(e);
		} catch (NamingException e) {
			throw new HtmlRenderException(e);
		}
		
        return htmlBuilder.toString();
	}
	
	public String renderBody(String content) throws HtmlRenderException {
		return renderBodyStart() + content + renderBodyEnd();
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
	
	public void setOptionClass(String value) {
		this.optionClass = value;
	}
	
	public String getOptionClass() {
		return this.optionClass;
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
