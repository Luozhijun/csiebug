package csiebug.web.html.form;

import java.io.UnsupportedEncodingException;

import javax.naming.NamingException;

import csiebug.util.AssertUtility;
import csiebug.util.NumberFormatUtility;
import csiebug.util.StringUtility;
import csiebug.util.WebUtility;
import csiebug.web.html.HtmlBuilder;
import csiebug.web.html.HtmlComponent;
import csiebug.web.html.HtmlRenderException;


/**
 * 產生可修改的Label控制項
 * @author George_Tsai
 * @version 2010/11/5
 */

public class HtmlEditableLabel extends HtmlComponent {
	private String htmlId;
	private String name;
	private String isReadOnly;
	private String className;
	private String isReturnValue;
	private String defaultValue;
	private String dataType;
	private String isMasked;
	private String onChange;
	private String onKeyDown;
	private String header;
	private String headerClass;
	private String footer;
	private String footerClass;
	private String buttonClass;
	private String isRequired;
	private String userValue;
	private String maxlength;
	private String fixlength;
	private String size;
	private String style;
	private String maxvalue;
	private String minvalue;
	private String maxinteger;
	private String maxdecimal;
	private String imagePath;
	private String typesetting;
	private String blankText;
	
	private WebUtility webutil = new WebUtility();
	
	public HtmlEditableLabel(String htmlId, String name, String isReadOnly, String className, String isReturnValue, String defaultValue, String dataType, String isMasked, String onChange, String onKeyDown, String header, String headerClass, String footer, String footerClass, String buttonClass, String isRequired, String userValue, String maxlength, String fixlength, String size, String style, String maxvalue, String minvalue, String maxinteger, String maxdecimal, String imagePath, String typesetting, String blankText) throws UnsupportedEncodingException, NamingException {
		this.htmlId = htmlId;
		this.name = name;
		this.isReadOnly = isReadOnly;
		this.className = className;
		this.isReturnValue = isReturnValue;
		this.defaultValue = defaultValue;
		this.dataType = dataType;
		this.isMasked = isMasked;
		this.onChange = onChange;
		this.onKeyDown = onKeyDown;
		this.header = header;
		this.headerClass = headerClass;
		this.footer = footer;
		this.footerClass = footerClass;
		this.buttonClass = buttonClass;
		this.isRequired = isRequired;
		this.userValue = userValue;
		this.maxlength = maxlength;
		this.fixlength = fixlength;
		this.size = size;
		this.style = style;
		this.maxvalue = maxvalue;
		this.minvalue = minvalue;
		this.maxinteger = maxinteger;
		this.maxdecimal = maxdecimal;
		this.imagePath = StringUtility.removeStartEndSlash(imagePath);
		this.typesetting = typesetting;
		this.blankText = blankText;
	}
	
	public HtmlEditableLabel() {
		
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
			if(AssertUtility.isNotNullAndNotSpace(htmlId) && AssertUtility.isNotNullAndNotSpace(name)) {
				HtmlBuilder divLabel = new HtmlBuilder();
				HtmlText text = new HtmlText();
				
				divLabel.divStart();
				
				//因為time12會有下拉選單,在這邊不適用,所以都當作time24處理
				if(dataType != null && dataType.equalsIgnoreCase("time12")) {
					dataType = "time24";
				}
				text.setDataType(dataType);
				text.setIsMasked(isMasked);
	            
	            //設定各樣屬性
	            divLabel.id(htmlId + "_textLabel");
	            text.setId(htmlId);
	            
	            divLabel.name(name + "_textLabel");
	            text.setName(name);
	            
	            boolean readOnlyFlag = false;
	            if(htmlId != null && AssertUtility.isTrue(webutil.getRequestAttribute(htmlId + "_IsReadOnly"))) {
	            	readOnlyFlag = true;
	            } else if(AssertUtility.isTrue(isReadOnly)) {
	            	readOnlyFlag = true;
	            }
	            if(readOnlyFlag) {
	            	isReadOnly = "true";
	            } else {
	            	divLabel.onDblClick("showInputText('" + htmlId + "', this);");
	            	divLabel.tabIndex("0");
	            	divLabel.onKeyDown("showInputTextForKeyDown('" + htmlId + "', this, event);");
	            	isReadOnly = "false";
	            }
	            
	            text.setIsReadOnly(isReadOnly);
	            text.setClassName("Text");
	            if(className != null) {
	            	divLabel.className(className);
	            }
	            if(style != null) {
	            	divLabel.style(style);
	            } else if(!readOnlyFlag) {
	            	divLabel.style("cursor:pointer");
	            }
	            divLabel.tagClose();
	            text.setMinvalue(minvalue);
	            text.setMaxvalue(maxvalue);
	            text.setMaxinteger(maxinteger);
	            text.setMaxdecimal(maxdecimal);
	            
	            String strIsRequired = "false";
                if(webutil.getRequestAttribute(htmlId + "_IsRequired") != null) {
    	        	if(webutil.getRequestAttribute(htmlId + "_IsRequired").toString().equals("true")) {
    	        		strIsRequired = "true";
    	        	}
    	        } else {
    	        	if(AssertUtility.isTrue(isRequired)) {
    	            	strIsRequired = "true";
    	            }
    	        }
	            
	            text.setIsRequired(strIsRequired);
	            
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
	            		strValue = webutil.getRequestAttribute(userValue).toString();
	            	}
	            }
	            if(dataType != null) {
	            	if(dataType.equalsIgnoreCase("currency")) {
	            		if(!strValue.equals("")) {
	            			strValue = NumberFormatUtility.getCurrency(strValue);
	            		}
	            	}
	            }
	            text.setDefaultValue(strValue);
	            if(blankText == null) {
	            	blankText = webutil.getMessage("common.BlankText");
	            }
	            if(strValue.equals("")) {
	            	divLabel.text(blankText);
	            } else {
	            	divLabel.text(strValue);
	            }
	            
	            divLabel.divEnd();
	            
	            text.setMaxlength(maxlength);
	            text.setFixlength(fixlength);
	            text.setSize(size);
	            text.setStyle("display:none");
	            
	            if(onChange != null) {
	            	text.setOnChange("modifyLabel('" + blankText + "', this, '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.ok") + "', '" + webutil.getMessage("common.error.interval") + "', '" + webutil.getMessage("common.error.DataType1Start") + "', '" + webutil.getMessage("common.error.DataType1End") + "', '" + webutil.getMessage("common.error.DataType2") + "', '" + webutil.getMessage("common.error.DataType3") + "', '" + webutil.getMessage("common.error.DataType4") + "', '" + webutil.getMessage("common.error.DataType5") + "', '" + webutil.getMessage("common.error.DataType6") + "', '" + webutil.getMessage("common.error.DataType7") + "', '" + webutil.getMessage("common.error.DataType8Start") + "', '" + webutil.getMessage("common.error.DataType8End") + "', '" + webutil.getMessage("common.error.DataType9Start") + "', '" + webutil.getMessage("common.error.DataType9End") + "', '" + webutil.getMessage("common.error.DataType10Start") + "', '" + webutil.getMessage("common.error.DataType10End") + "', '" + webutil.getMessage("common.error.DataType11") + "', '" + webutil.getMessage("common.error.DataType12Start") + "', '" + webutil.getMessage("common.error.DataType12End") + "', '" + webutil.getMessage("common.error.DataType13") + "', '" + webutil.getMessage("common.error.DataType14") + "');" + onChange);
	            } else {
	            	text.setOnChange("modifyLabel('" + blankText + "', this, '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.ok") + "', '" + webutil.getMessage("common.error.interval") + "', '" + webutil.getMessage("common.error.DataType1Start") + "', '" + webutil.getMessage("common.error.DataType1End") + "', '" + webutil.getMessage("common.error.DataType2") + "', '" + webutil.getMessage("common.error.DataType3") + "', '" + webutil.getMessage("common.error.DataType4") + "', '" + webutil.getMessage("common.error.DataType5") + "', '" + webutil.getMessage("common.error.DataType6") + "', '" + webutil.getMessage("common.error.DataType7") + "', '" + webutil.getMessage("common.error.DataType8Start") + "', '" + webutil.getMessage("common.error.DataType8End") + "', '" + webutil.getMessage("common.error.DataType9Start") + "', '" + webutil.getMessage("common.error.DataType9End") + "', '" + webutil.getMessage("common.error.DataType10Start") + "', '" + webutil.getMessage("common.error.DataType10End") + "', '" + webutil.getMessage("common.error.DataType11") + "', '" + webutil.getMessage("common.error.DataType12Start") + "', '" + webutil.getMessage("common.error.DataType12End") + "', '" + webutil.getMessage("common.error.DataType13") + "', '" + webutil.getMessage("common.error.DataType14") + "');");
	            }
	            
	            if(onKeyDown != null) {
	            	text.setOnKeyDown(onKeyDown);
	            }
	            
	            text.setOnBlur("modifyLabel('" + blankText + "', this, '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.ok") + "', '" + webutil.getMessage("common.error.interval") + "', '" + webutil.getMessage("common.error.DataType1Start") + "', '" + webutil.getMessage("common.error.DataType1End") + "', '" + webutil.getMessage("common.error.DataType2") + "', '" + webutil.getMessage("common.error.DataType3") + "', '" + webutil.getMessage("common.error.DataType4") + "', '" + webutil.getMessage("common.error.DataType5") + "', '" + webutil.getMessage("common.error.DataType6") + "', '" + webutil.getMessage("common.error.DataType7") + "', '" + webutil.getMessage("common.error.DataType8Start") + "', '" + webutil.getMessage("common.error.DataType8End") + "', '" + webutil.getMessage("common.error.DataType9Start") + "', '" + webutil.getMessage("common.error.DataType9End") + "', '" + webutil.getMessage("common.error.DataType10Start") + "', '" + webutil.getMessage("common.error.DataType10End") + "', '" + webutil.getMessage("common.error.DataType11") + "', '" + webutil.getMessage("common.error.DataType12Start") + "', '" + webutil.getMessage("common.error.DataType12End") + "', '" + webutil.getMessage("common.error.DataType13") + "', '" + webutil.getMessage("common.error.DataType14") + "');");
	            
	            text.setOp(OpEnum.TEXT);
	            text.setImagePath(imagePath);
	            
	            text.setButtonClass(buttonClass);
	            text.setTypesetting("false");
	            
				htmlBuilder.appendString(divLabel.toString() + "\n");
				htmlBuilder.appendString(text.render());
			}
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

	public void setBlankText(String blankText) {
		this.blankText = blankText;
	}

	public String getBlankText() {
		return blankText;
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
}
