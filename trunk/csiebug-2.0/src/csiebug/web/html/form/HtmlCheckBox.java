package csiebug.web.html.form;

import csiebug.util.AssertUtility;
import csiebug.util.StringUtility;
import csiebug.util.WebUtility;
import csiebug.web.html.HtmlBuilder;
import csiebug.web.html.HtmlComponent;


/**
 * 產生HTML CheckBox
 * @author George_Tsai
 * @version 2009/6/14
 */

public class HtmlCheckBox extends HtmlComponent {
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
	private WebUtility webutil = new WebUtility();
	
	public HtmlCheckBox(String htmlId, String name, String className, String isReadOnly, String isReturnValue, String checked, String onChange, String onClick, String header, String headerClass, String footer, String footerClass, String displayName, String userValue, String value, String style, String typesetting) {
		this.htmlId = htmlId;
		this.name = name;
		this.className = className;
		this.isReadOnly = isReadOnly;
		this.isReturnValue = isReturnValue;
		this.checked = checked;
		this.onChange = onChange;
		this.onClick = onClick;
		this.header = header;
		this.headerClass = headerClass;
		this.footer = footer;
		this.footerClass = footerClass;
		this.displayName = displayName;
		this.userValue = userValue;
		this.value = value;
		this.style = style;
		this.typesetting = typesetting;
	}
	
	public HtmlCheckBox() {
		
	}
	
	public String renderStart() {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
        
        //多一個hidden欄位來判斷到底是form load還是submit
        //因為checkbox若不勾選而submit的話,request是不傳的,所以和form load的狀況會分不清,設值的判斷會有問題
        if(htmlId != null && name != null) {
        	htmlBuilder.inputStart().type("hidden").id(htmlId + "_hidden").name(name + "_hidden").value("on").tagClose();
        }
        
        if(typesetting == null || !typesetting.equalsIgnoreCase("false")) {
            htmlBuilder.tdStart().className("TdHeader").tagClose();
            
            //建立header
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
                if(header != null) {
                	htmlBuilder.text(header + "：");
                }
                htmlBuilder.labelEnd();
                htmlBuilder.text("  ");
            }
            
            htmlBuilder.tdEnd();
            
            htmlBuilder.tdStart().className("TdBody").tagClose();
        }
        
        return htmlBuilder.toString();
	}
	
	public String renderBody(String bodyHtml) {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
        
		htmlBuilder.inputStart().type("checkbox");
        
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
        
        String strValue = "";
        
        if(AssertUtility.isTrue(checked)) {
    		if(value != null) {
    			strValue = value;
    		} else {
    			strValue = "on";
    		}
        }
        if(isReturnValue == null || isReturnValue.equalsIgnoreCase("true")) {
        	//這個判斷只能判斷出request有沒有傳,若有傳一定就是有勾選而submit
        	//但是無法判斷是form load,還是不勾選而submit
        	//因為checkbox若不勾選而submit的話,request是不傳的
        	//所以還要多判斷開頭加的那個hidden欄位
            if(webutil.getRequest().getParameter(name) != null) {
            	strValue = StringUtility.cleanXSSPattern(webutil.getRequest().getParameter(name));
            } else if(webutil.getRequest().getParameter(name + "_hidden") != null) {
            	strValue = "";
            }
        }
        if(userValue != null) {
        	if(webutil.getRequestAttribute(userValue) != null) {
        		strValue = webutil.getRequestAttribute(userValue).toString();
        	}
        }
        
        if(value != null) {
        	htmlBuilder.value(value);
        	if(strValue.equalsIgnoreCase(value)) {
	        	htmlBuilder.checked();
	        }
        } else {
	        if(strValue.equalsIgnoreCase("on")) {
	        	htmlBuilder.checked();
	        }
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
        
        if(displayName != null) {
        	htmlBuilder.labelStart();
        	if(htmlId != null) {
        		htmlBuilder.id(htmlId + "_displayName");
        	}
        	if(className != null) {
        		htmlBuilder.className(className);
        	} else {
            	htmlBuilder.className("Label");
            }
        	htmlBuilder.tagClose();
        	htmlBuilder.text(displayName);
        	htmlBuilder.labelEnd();
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
