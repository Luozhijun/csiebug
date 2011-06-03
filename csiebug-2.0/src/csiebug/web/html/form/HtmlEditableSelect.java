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
 * 產生HTML EditableSelect
 * @author George_Tsai
 * @version 2009/6/14
 */

public class HtmlEditableSelect extends HtmlComponent {
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
	private String typesetting;
	private WebUtility webutil = new WebUtility();
	
	private boolean keyValueFlag = false;
	
	public HtmlEditableSelect(String htmlId, String name, String isReadOnly, String className, String isReturnValue, String defaultValue, String onChange, String header, String headerClass, String footer, String footerClass, String isRequired, String userValue, String style, String option, boolean caseSensitive, String op, String typesetting) {
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
		this.typesetting = typesetting;
	}
	
	public HtmlEditableSelect() {
		
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
        try {
        	HtmlBuilder selectText = new HtmlBuilder();
    		HtmlBuilder selectButton = new HtmlBuilder();
    		HtmlBuilder selectOption = new HtmlBuilder();
    		HtmlBuilder selectValue = new HtmlBuilder();
    		
    		selectText.inputStart().type("text");
    		selectButton.buttonStart().type("button").className("Button").onMouseOut("className='Button'").onMouseOver("className='ButtonHover'");
    		selectValue.inputStart().type("hidden");
    		selectOption.divStart();
            
            //設定各樣屬性
            if(htmlId != null) {
            	selectText.id(htmlId + "_display");
            	selectButton.id(htmlId + "_button");
            	selectButton.onClick("openOption('" + htmlId + "');");
            	selectValue.id(htmlId);
            	selectOption.id(htmlId + "_option");
            }
            
            if(name != null) {
            	selectText.name(name + "_display");
            	selectValue.name(name);
            	selectOption.name(name + "_option");
            }
            
            if(style != null) {
            	selectText.style(style);
            	selectOption.style(style + ";display:none;border:1px solid;background:white;z-index:5;height:200;overflow-y:scroll;");
            } else {
            	selectOption.style("display:none;border:1px solid;background:white;z-index:5;height:200;overflow-y:scroll;");
            }
            
            boolean readOnlyFlag = false;
            if(htmlId != null && AssertUtility.isTrue(webutil.getRequestAttribute(htmlId + "_IsReadOnly"))) {
            	readOnlyFlag = true;
            } else if(AssertUtility.isTrue(isReadOnly)) {
            	readOnlyFlag = true;
            }
            if(readOnlyFlag) {
            	selectText.disabled();
        		selectButton.disabled();
            	isReadOnly = "true";
            } else {
            	isReadOnly = "false";
            }
            
            if(className != null) {
            	selectText.className(className);
            	selectOption.className(className + "_Option");
            } else {
            	if(isReadOnly.equalsIgnoreCase("true")) {
                	selectText.className("TextReadOnly");
                } else {
                	selectText.className("Text");
                }
            	selectOption.className("EditableSelectOption");
            }
            
            selectOption.tagClose();
            
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
            
            selectValue.tagProperty("isRequired", strIsRequired);
            
            String strValue = "";
            if(defaultValue != null) {
            	strValue = defaultValue;
            }
            if(isReturnValue == null || isReturnValue.equalsIgnoreCase("true")) {
                if(webutil.getRequest().getParameter(name) != null) {
                	strValue = StringUtility.cleanXSSPattern(webutil.getRequest().getParameter(name));
                }
            }
            if(userValue != null) {
            	if(webutil.getRequestAttribute(userValue) != null) {
            		strValue = (String)webutil.getRequestAttribute(userValue);
            	}
            }
            selectValue.value(strValue);
            
            //建立option
            if(option != null) {
            	Map<String, String> optionMap = null;
    	        if(webutil.getRequestAttribute(option) != null) {
    	        	optionMap = (Map<String, String>)webutil.getRequestAttribute(option);
    	        	Object[] aryKey = optionMap.keySet().toArray();
    	        	for(int i = 0; i < aryKey.length; i++) {
    	        		selectOption.appendString("\t");
    	        		selectOption.divStart();
    	        		
    	        		//如果是複合控制項keyValue,則選項和控制項的值與一般狀況不同
    	        		if(keyValueFlag) {
            				if(strValue.equalsIgnoreCase(aryKey[i].toString())) {
	    	        			selectText.value(strValue);
	    	        		}
	    	        		
	    	        		if(htmlId != null) {
	    	        			if(onChange != null) {
	    	        				selectOption.onClick("selOption('" + htmlId + "', '" + aryKey[i].toString() + "', '" + optionMap.get(aryKey[i]) + "', '" + onChange + "');");
	    	        			} else {
	    	        				selectOption.onClick("selOption('" + htmlId + "', '" + aryKey[i].toString() + "', '" + optionMap.get(aryKey[i]) + "', '');");
	    	        			}
	    	        		}
	    	        		selectOption.value(aryKey[i].toString());
	    	        		selectOption.tagProperty("text", optionMap.get(aryKey[i]));
	    	        		selectOption.onMouseMove("mousemoveOption(this);");
	            			selectOption.onMouseOut("mouseoutOption(this);");
	            			selectOption.tagClose().appendString(aryKey[i].toString()).divEnd();
	            		} else {
	            		//一般使用的情況
	            			if(strValue.equalsIgnoreCase(aryKey[i].toString())) {
	    	        			selectText.value(optionMap.get(aryKey[i]));
	    	        		}
	    	        		
	    	        		if(htmlId != null) {
	    	        			if(onChange != null) {
	    	        				selectOption.onClick("selOption('" + htmlId + "', '" + optionMap.get(aryKey[i]) + "', '" + aryKey[i].toString() + "', '" + onChange + "');");
	    	        			} else {
	    	        				selectOption.onClick("selOption('" + htmlId + "', '" + optionMap.get(aryKey[i]) + "', '" + aryKey[i].toString() + "', '');");
	    	        			}
	    	        		}
	    	        		selectOption.value(aryKey[i].toString());
	    	        		selectOption.onMouseMove("mousemoveOption(this);");
	            			selectOption.onMouseOut("mouseoutOption(this);");
	            			selectOption.tagClose().appendString(optionMap.get(aryKey[i])).divEnd();
	            		}
    	        	}
    	        }
            }
            selectOption.divEnd();
            
            if(onChange != null) {
            	onChange = onChange.replaceAll("&quote", "'");
            	if(htmlId != null) {
            		if(op != null) {
            			selectText.onChange("checkSelect('" + htmlId + "', " + Boolean.toString(caseSensitive) + " , " + op + ", '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.error.NoOption") + "', '" + webutil.getMessage("common.ok") + "');" + onChange);
            		} else {
            			selectText.onChange("checkSelect('" + htmlId + "', " + Boolean.toString(caseSensitive) + ", 1, '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.error.NoOption") + "', '" + webutil.getMessage("common.ok") + "');" + onChange);
            		}
            	} else {
            		selectText.onChange(onChange);
            	}
            } else {
            	if(htmlId != null) {
            		if(op != null) {
            			selectText.onChange("checkSelect('" + htmlId + "', " + Boolean.toString(caseSensitive) + ", " + op + ", '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.error.NoOption") + "', '" + webutil.getMessage("common.ok") + "');");
            		} else {
            			selectText.onChange("checkSelect('" + htmlId + "', " + Boolean.toString(caseSensitive) + ", 1, '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.error.NoOption") + "', '" + webutil.getMessage("common.ok") + "');");
            		}
            	}
            }
            
            if(htmlId != null) {
            	if(op != null) {
            		selectText.onKeyUp("editSelect('" + htmlId + "', " + Boolean.toString(caseSensitive) + ", " + op + ", event, '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.error.NoOption") + "', '" + webutil.getMessage("common.ok") + "');");
            	} else {
            		selectText.onKeyUp("editSelect('" + htmlId + "', " + Boolean.toString(caseSensitive) + ", 1, event, '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.error.NoOption") + "', '" + webutil.getMessage("common.ok") + "');");
            	}
            	selectText.onBlur("closeOption('" + htmlId + "');");
            	selectText.onClick("closeOption('" + htmlId + "');");
            }
        	
            
            selectText.tagClose();
            selectButton.tagClose().text("▼").buttonEnd();
            selectValue.tagClose();
            
            htmlBuilder.appendString(selectText.toString()).appendString(selectButton.toString()).appendString(selectOption.toString()).appendString(selectValue.toString());
		} catch (UnsupportedEncodingException e) {
			throw new HtmlRenderException(e);
		} catch (NamingException e) {
			throw new HtmlRenderException(e);
		}
        
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

	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	public boolean getCaseSensitive() {
		return caseSensitive;
	}

	public void setKeyValueFlag(boolean keyValueFlag) {
		this.keyValueFlag = keyValueFlag;
	}

	public boolean isKeyValueFlag() {
		return keyValueFlag;
	}
}
