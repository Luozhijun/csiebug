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
 * 產生HTML MultiSelect
 * @author George_Tsai
 * @version 2009/6/14
 */

public class HtmlMultiSelect extends HtmlComponent {
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
	private WebUtility webutil = new WebUtility();
	
	public HtmlMultiSelect(	String htmlId, String name, String isReadOnly, String className, String isReturnValue, String defaultValue, String onChange, String header, String headerClass, String isRequired, String userValue, String style, String totalOption, String size, String typesetting) {
		this.htmlId = htmlId;
		this.name = name;
		this.isReadOnly = isReadOnly;
		this.className = className;
		this.isReturnValue = isReturnValue;
		this.defaultValue = defaultValue;
		this.onChange = onChange;
		this.header = header;
		this.headerClass = headerClass;
		this.isRequired = isRequired;
		this.userValue = userValue;
		this.style = style; 
		this.totalOption = totalOption;
		this.size = size;
		this.typesetting = typesetting;
	}
	
	public HtmlMultiSelect() {
		
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
	
	public String renderBody(String content) throws HtmlRenderException {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
        
        //建立主體
        try {
        	htmlBuilder.table().tr().td();
    		renderBodyIndividual(htmlBuilder, 1);
    		htmlBuilder.tdEnd();
    		
    		htmlBuilder.td();
    		
    		for(int i = 0; i < 4; i++) {
    			htmlBuilder.buttonStart().type("button").className("Button").onMouseOut("className='Button'").onMouseOver("className='ButtonHover'").style("width:75px");
    			
    			boolean readOnlyFlag = false;
    	        if(htmlId != null && AssertUtility.isTrue(webutil.getRequestAttribute(htmlId + "_IsReadOnly"))) {
    	        	readOnlyFlag = true;
    	        } else if(AssertUtility.isTrue(isReadOnly)) {
    	        	readOnlyFlag = true;
    	        }
    	        if(readOnlyFlag) {
    	        	htmlBuilder.disabled();
    	        	isReadOnly = "true";
    	        }
    			
    			if(i == 0) {
    				if(htmlId != null) {
    					htmlBuilder.id(htmlId + "_selAll");
    				}
    				if(onChange != null) {
    					htmlBuilder.onClick("selAll('" + htmlId + "', '" + onChange.replaceAll("'", "&quote") + "');").tagClose().text("全    選");
    				} else {
    					htmlBuilder.onClick("selAll('" + htmlId + "', '');").tagClose().text("全    選");
    				}
    			} else if(i == 1) {
    				if(htmlId != null) {
    					htmlBuilder.id(htmlId + "_selMulti");
    				}
    				if(onChange != null) {
    					htmlBuilder.onClick("selMulti('" + htmlId + "', '" + onChange.replaceAll("'", "&quote") + "');").tagClose().text("選    擇");
    				} else {
    					htmlBuilder.onClick("selMulti('" + htmlId + "', '');").tagClose().text("選    擇");
    				}
    			} else if(i == 2) {
    				if(htmlId != null) {
    					htmlBuilder.id(htmlId + "_unSelMulti");
    				}
    				if(onChange != null) {
    					htmlBuilder.onClick("unSelMulti('" + htmlId + "', '" + onChange.replaceAll("'", "&quote") + "');").tagClose().text("取    消");
    				} else {
    					htmlBuilder.onClick("unSelMulti('" + htmlId + "', '');").tagClose().text("取    消");
    				}
    			} else {
    				if(htmlId != null) {
    					htmlBuilder.id(htmlId + "_unSelAll");
    				}
    				if(onChange != null) {
    					htmlBuilder.onClick("unSelAll('" + htmlId + "', '" + onChange.replaceAll("'", "&quote") + "');").tagClose().text("全取消");
    				} else {
    					htmlBuilder.onClick("unSelAll('" + htmlId + "', '');").tagClose().text("全取消");
    				}
    			}
    			
    			htmlBuilder.buttonEnd().br();
    		}
    		htmlBuilder.tdEnd();
    		
    		htmlBuilder.td();
    		renderBodyIndividual(htmlBuilder, 2);
    		htmlBuilder.tdEnd().trEnd().tableEnd();
		} catch (NamingException e) {
			throw new HtmlRenderException(e);
		}
        
        return htmlBuilder.toString();
	}
	
	public String renderEnd() {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
        
        if(typesetting == null || !typesetting.equalsIgnoreCase("false")) {
        	htmlBuilder.tdEnd();
        }
        
        return htmlBuilder.toString();
	}
	
	@SuppressWarnings("unchecked")
	private void renderBodyIndividual(HtmlBuilder htmlBuilder, int type) throws NamingException {
		AssertUtility.notNull(htmlBuilder);
		
		HtmlBuilder hiddenSelect = new HtmlBuilder();
		hiddenSelect.inputStart().type("hidden");
		htmlBuilder.selectStart();
		htmlBuilder.tagProperty("multiple", "multiple");
        
        //設定各樣屬性
        if(htmlId != null) {
        	if(type == 1) {
        		hiddenSelect.id(htmlId + "_unselect");
        		htmlBuilder.id(htmlId + "_unselect_display");
        	} else {
        		hiddenSelect.id(htmlId);
        		htmlBuilder.id(htmlId + "_display");
        	}
        }
        
        if(name != null) {
        	if(type == 1) {
        		hiddenSelect.name(name + "_unselect");
        		htmlBuilder.name(name + "_unselect_display");
        	} else {
        		hiddenSelect.name(name);
        		htmlBuilder.name(name + "_display");
        	}
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
        	htmlBuilder.className("MultiSel");
        }
        
        if(type == 2) {
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
    		
	        hiddenSelect.tagProperty("isRequired", strIsRequired);
        }
        
        if(style != null) {
        	htmlBuilder.style(style);
        }
        
        if(size != null) {
        	htmlBuilder.size(size);
        }
        
        htmlBuilder.tagClose();
    
		String strValue = "";
		StringBuffer strUnSelectValue = new StringBuffer();
		if(defaultValue != null) {
	        strValue = defaultValue;
	    }
        if((isReturnValue == null || isReturnValue.equalsIgnoreCase("true")) && webutil.getRequest().getParameter(name) != null) {
            strValue = StringUtility.cleanXSSPattern(webutil.getRequest().getParameter(name));
        }
        if(userValue != null && webutil.getRequestAttribute(userValue) != null) {
        	strValue = (String)webutil.getRequestAttribute(userValue);
        }
        
        String[] arySelectKey = strValue.split(",");
        if(totalOption != null) {
        	Map<String, String> optionMap = null;
        	if(webutil.getRequestAttribute(totalOption) != null) {
    	        optionMap = (Map<String, String>)webutil.getRequestAttribute(totalOption);
    	        
    	        Object[] aryKey = optionMap.keySet().toArray();
    	        
    	        for(int i = 0; i < aryKey.length; i++) {
    	        	boolean flagUnSelect = true;
    	        	
    	        	for(int j = 0; j < arySelectKey.length; j++) {
    	        		if(aryKey[i].equals(arySelectKey[j])) {
    	        			flagUnSelect = false;
    	        			break;
    	        		}
    	        	}
    	        	
    	        	if(flagUnSelect) {
    	        		if(!strUnSelectValue.toString().equals("")) {
    	        			strUnSelectValue.append(",");
    	        		}
    	        		strUnSelectValue.append(aryKey[i]);
    	        	}
    	        }
        	}
        }
        
        String[] aryUnSelectKey = strUnSelectValue.toString().split(",");
        
        if(type == 1) {
            hiddenSelect.value(strUnSelectValue.toString());
        } else {
        	hiddenSelect.value(strValue);
        }
        hiddenSelect.tagClose();
        
        //建立option
        if(totalOption != null) {
        	Map<String, String> optionMap = null;
	        if(webutil.getRequestAttribute(totalOption) != null) {
	        	optionMap = (Map<String, String>)webutil.getRequestAttribute(totalOption);
	        	
	        	if(type == 1) {
		        	for(int i = 0; i < aryUnSelectKey.length; i++) {
		        		if(optionMap.get(aryUnSelectKey[i]) != null) {
			        		htmlBuilder.appendString("\t");
			        		htmlBuilder.option(aryUnSelectKey[i], optionMap.get(aryUnSelectKey[i]));
		        		}
		        	}
	        	} else {
	        		for(int i = 0; i < arySelectKey.length; i++) {
	        			if(optionMap.get(arySelectKey[i]) != null) {
			        		htmlBuilder.appendString("\t");
			        		htmlBuilder.option(arySelectKey[i], optionMap.get(arySelectKey[i]));
	        			}
		        	}
	        	}
	        }
	    }
        
        htmlBuilder.selectEnd();
        htmlBuilder.appendString(hiddenSelect.toString());
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
