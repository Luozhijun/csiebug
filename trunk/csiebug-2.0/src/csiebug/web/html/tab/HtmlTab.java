package csiebug.web.html.tab;

import java.util.LinkedHashMap;
import java.util.Map;

import csiebug.util.AssertUtility;
import csiebug.util.WebUtility;
import csiebug.web.html.HtmlBuilder;
import csiebug.web.html.HtmlComponentOnlyBody;


/**
 * 產生HTML Tab
 * @author George_Tsai
 * @version 2009/6/24
 */

public class HtmlTab extends HtmlComponentOnlyBody {
	private String htmlId;
	private String name;
	private String defaultTab;
	private String tabs;
	private String op;
	private String onClick;
	private WebUtility webutil = new WebUtility();
	
	public HtmlTab(String htmlId, String name, String defaultTab, String tabs, String op, String onClick) {
		this.htmlId = htmlId;
		this.name = name;
		this.defaultTab = defaultTab;
		this.tabs = tabs;
		this.op = op;
		this.onClick = onClick;
	}
	
	public HtmlTab() {
		
	}
	
	public String renderBody(String content) {
        if(op == null || !op.equalsIgnoreCase("div")) {
            return renderTabUsingIFrame();
        } else {
        	return renderTabUsingDIV();
        }
	}
	
	@SuppressWarnings("unchecked")
	private String renderTabUsingIFrame() {
		String defaultURL = "";
		
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		htmlBuilder.divStart().id("menu").tagClose();
		htmlBuilder.ulStart();
		
		if(htmlId != null) {
			htmlBuilder.id(htmlId);
		}
		
		if(name != null) {
			htmlBuilder.name(name);
		}
		
		htmlBuilder.tagClose();
		
		if(tabs != null && webutil.getRequestAttribute(tabs) != null) {
	    	Map<String, String> tabMap = (LinkedHashMap<String, String>)webutil.getRequestAttribute(tabs);
        	
        	Object[] aryKey = tabMap.keySet().toArray();
        	
        	defaultURL = renderTabsUsingIFrame(htmlBuilder, tabMap, aryKey);
        }
		
		htmlBuilder.ulEnd();
		htmlBuilder.divEnd();
		
		htmlBuilder.iframeStart();
		
		if(htmlId != null) {
			htmlBuilder.id(htmlId + "_iframe");
		}
		
		if(name != null) {
			htmlBuilder.name(name + "_iframe");
		}
		
		htmlBuilder.src(defaultURL);
		htmlBuilder.width("100%").tagClose().iframeEnd();
		
		return htmlBuilder.toString();
	}
	
	private String renderTabsUsingIFrame(HtmlBuilder htmlBuilder, Map<String, String> tabMap, Object[] aryKey) {
		AssertUtility.notNull(htmlBuilder);
		AssertUtility.notNull(tabMap);
		AssertUtility.notNull(aryKey);
		
		String defaultURL = "";
		
		for(int i = 0; i < aryKey.length; i++) {
			htmlBuilder.liStart();
			htmlBuilder.style("cursor:pointer");
			
			if(defaultTab != null && defaultTab.equalsIgnoreCase(aryKey[i].toString())) {
				htmlBuilder.id("current");
				defaultURL = tabMap.get(aryKey[i]).toString();
			} else if(defaultTab == null && i == 0) {
				htmlBuilder.id("current");
				defaultURL = tabMap.get(aryKey[i]).toString();
			} else {
				htmlBuilder.id("notcurrent");
			}
			if(htmlId != null) {
				if(!AssertUtility.isNotNullAndNotSpace(onClick)) {
					onClick = "";
				}
				
				htmlBuilder.onClick("changeTabIframe(this, '" + tabMap.get(aryKey[i]).toString() + "', '" + htmlId + "', '" + htmlId + "_iframe', '" + onClick.replaceAll("'", "&quote") + "')");
			}
			htmlBuilder.tagClose();
			
			htmlBuilder.aStart().tagClose().divStart().tagClose();
			htmlBuilder.appendString(aryKey[i].toString());
			htmlBuilder.divEnd().aEnd().liEnd();
		}
		
		return defaultURL;
	}
	
	@SuppressWarnings("unchecked")
	private String renderTabUsingDIV() {
		String defaultTabId = "";
		
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		htmlBuilder.divStart().id("menu").tagClose();
		htmlBuilder.ulStart();
		
		if(htmlId != null) {
			htmlBuilder.id(htmlId);
		}
		
		if(name != null) {
			htmlBuilder.name(name);
		}
		
		htmlBuilder.tagClose();
		
		if(tabs != null && webutil.getRequestAttribute(tabs) != null) {
	    	Map<String, String> tabMap = (LinkedHashMap<String, String>)webutil.getRequestAttribute(tabs);
        	
        	Object[] aryKey = tabMap.keySet().toArray();
        	
        	defaultTabId = renderTabsUsingDIV(htmlBuilder, tabMap, aryKey);
        }
		
		htmlBuilder.inputStart().type("hidden").id("defaultTabId").name("defaultTabId").value(defaultTabId).tagClose();
		
		htmlBuilder.ulEnd();
		htmlBuilder.divEnd();
		
		return htmlBuilder.toString();
	}
	
	private String renderTabsUsingDIV(HtmlBuilder htmlBuilder, Map<String, String> tabMap, Object[] aryKey) {
		AssertUtility.notNull(htmlBuilder);
		AssertUtility.notNull(tabMap);
		AssertUtility.notNull(aryKey);
		
		String defaultTabId = "";
		
		for(int i = 0; i < aryKey.length; i++) {
			htmlBuilder.liStart();
			htmlBuilder.style("cursor:pointer");
			
			if(defaultTab != null && defaultTab.equalsIgnoreCase(tabMap.get(aryKey[i]).toString())) {
				htmlBuilder.id("current");
				defaultTabId = tabMap.get(aryKey[i]).toString();
			} else if(defaultTab == null && i == 0) {
				htmlBuilder.id("current");
				defaultTabId = tabMap.get(aryKey[i]).toString();
			} else {
				htmlBuilder.id("notcurrent");
			}
			if(htmlId != null) {
				if(!AssertUtility.isNotNullAndNotSpace(onClick)) {
					onClick = "";
				}
				
				htmlBuilder.onClick("changeTabDIV(this, '" + tabMap.get(aryKey[i]).toString() + "', '" + htmlId + "', '" + onClick.replaceAll("'", "&quote") + "')");
			}
			htmlBuilder.tagClose();
			
			htmlBuilder.aStart().tagClose().divStart().tagClose();
			htmlBuilder.appendString(aryKey[i].toString());
			htmlBuilder.divEnd().aEnd().liEnd();
		}
		
		return defaultTabId;
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
	
	public void setTabs(String value) {
		this.tabs = value;
	}
	
	public String getTabs() {
		return this.tabs;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getOp() {
		return op;
	}

	public void setDefaultTab(String defultTab) {
		this.defaultTab = defultTab;
	}

	public String getDefaultTab() {
		return defaultTab;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}

	public String getOnClick() {
		return onClick;
	}
}
