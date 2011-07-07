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
 * 產生HTML RadioGroup
 * @author George_Tsai
 * @version 2009/6/14
 */

public class HtmlRadioGroup extends HtmlComponent {
	private String htmlId;
	private String name;
	private String defaultValue;
	private String userValue;
	private String className;
	private String isReturnValue;
	private String onChange;
	private String onClick;
	private String header;
	private String headerClass;
	private String footer;
	private String footerClass;
	private String isRequired;
	private String isReadOnly;
	private String style;
	private String option;
	private String typesetting;
	private String newLineFlag;
	private WebUtility webutil = new WebUtility();
	
	public HtmlRadioGroup(String htmlId, String name, String defaultValue, String userValue, String className, String isReturnValue, String onChange, String onClick, String header, String headerClass, String footer, String footerClass, String isRequired, String isReadOnly, String style, String option, String typesetting, String newLineFlag) {
		this.htmlId = htmlId;
		this.name = name;
		this.defaultValue = defaultValue;
		this.userValue = userValue;
		this.className = className;
		this.isReturnValue = isReturnValue;
		this.onChange = onChange;
		this.onClick = onClick;
		this.header = header;
		this.headerClass = headerClass;
		this.footer = footer;
		this.footerClass = footerClass;
		this.isRequired = isRequired;
		this.isReadOnly = isReadOnly;
		this.style = style;
		this.option = option;
		this.typesetting = typesetting;
		this.newLineFlag = newLineFlag;
	}
	
	public HtmlRadioGroup() {
		
	}
	
	/**
	 * 取得RadioGroup所選取的value值
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getRadioGroupAnswerValue() {
		String value = "";
		
		if(option != null && webutil.getRequestAttribute(option) != null) {
			Map<String, String> map = (Map<String, String>)webutil.getRequestAttribute(option);
			
			Object[] key = map.keySet().toArray();
			
			for(int i = 0; i < key.length; i++) {
				String strValue = "";
		        
		        if(defaultValue != null) {
		        	strValue = defaultValue;
		        }
		        if((isReturnValue == null || isReturnValue.equalsIgnoreCase("true")) && webutil.getRequest().getParameter(name) != null) {
		            strValue = StringUtility.cleanXSSPattern(webutil.getRequest().getParameter(name));
		        }
		        if(userValue != null && webutil.getRequestAttribute(userValue) != null) {
		        	strValue = webutil.getRequestAttribute(userValue).toString();
		        }
		        
		        if(strValue.equalsIgnoreCase((String)key[i])) {
		        	return strValue;
			    }
			}
		}
		
		return value;
	}
	
	/**
	 * 取得RadioGroup所選取的選項文字
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getRadioGroupAnswerText() {
		String value = "";
		
		if(option != null && webutil.getRequestAttribute(option) != null) {
			Map<String, String> map = (Map<String, String>)webutil.getRequestAttribute(option);
			
			Object[] key = map.keySet().toArray();
			
			for(int i = 0; i < key.length; i++) {
				String strValue = "";
		        
		        if(defaultValue != null) {
		        	strValue = defaultValue;
		        }
		        if((isReturnValue == null || isReturnValue.equalsIgnoreCase("true")) && webutil.getRequest().getParameter(name) != null) {
		            strValue = StringUtility.cleanXSSPattern(webutil.getRequest().getParameter(name));
		        }
		        if(userValue != null && webutil.getRequestAttribute(userValue) != null) {
		        	strValue = webutil.getRequestAttribute(userValue).toString();
		        }
		        
		        if(strValue.equalsIgnoreCase((String)key[i])) {
		        	return map.get(key[i]);
			    }
			}
		}
		
		return value;
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
        		
        		if(header != null) {
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
	public String renderBody(String content) {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
        
        //建立主體
		if(option != null && webutil.getRequestAttribute(option) != null) {
			Map<String, String> map = (Map<String, String>)webutil.getRequestAttribute(option);
			
			Object[] key = map.keySet().toArray();
			
			for(int i = 0; i < key.length; i++) {
				//強迫換行
				if(AssertUtility.isTrue(newLineFlag) && i != 0) {
					htmlBuilder.br();
				}
				
				htmlBuilder.inputStart().type("radio");
		        
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
		        	strValue = webutil.getRequestAttribute(userValue).toString();
		        }
		        
		        htmlBuilder.value((String)key[i]);
		        if(strValue.equalsIgnoreCase((String)key[i])) {
			       	htmlBuilder.checked();
			    }
		        
		        if(style != null) {
		        	htmlBuilder.style(style);
		        }
		        
		        if(onChange != null) {
		        	htmlBuilder.onChange(onChange);
		        }
		        
		        if(onClick != null) {
		        	htmlBuilder.onClick(onClick);
		        }
		        
		        htmlBuilder.tagClose();
		        htmlBuilder.inputEnd();
		        
		        htmlBuilder.labelStart();
		        if(htmlId != null) {
		        	htmlBuilder.id(htmlId + "_" + i + "_displayName");
	        	}
		        if(className != null) {
		        	htmlBuilder.className(className);
		        } else {
		           	htmlBuilder.className("Label");
		        }
		        htmlBuilder.tagClose();
		        htmlBuilder.text(map.get(key[i]));
		        htmlBuilder.labelEnd();
		    }
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
	
	public void setUserValue(String userValue) {
		this.userValue = userValue;
	}
	
	public String getUserValue() {
		return this.userValue;
	}
	
	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}
	
	public String getIsRequired() {
		return this.isRequired;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getClassName() {
		return this.className;
	}
	
	public void setIsReadOnly(String isReadOnly) {
		this.isReadOnly = isReadOnly;
	}
	
	public String getIsReadOnly() {
		return this.isReadOnly;
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
	
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	
	public void setTypesetting(String value) {
		this.typesetting = value;
	}
	
	public String getTypesetting() {
		return this.typesetting;
	}

	public void setNewLineFlag(String newLineFlag) {
		this.newLineFlag = newLineFlag;
	}

	public String getNewLineFlag() {
		return newLineFlag;
	}
}
