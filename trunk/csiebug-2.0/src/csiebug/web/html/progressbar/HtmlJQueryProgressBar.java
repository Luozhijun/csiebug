package csiebug.web.html.progressbar;

import java.util.Enumeration;

import csiebug.util.StringUtility;
import csiebug.util.WebUtility;
import csiebug.web.html.HtmlBuilder;
import csiebug.web.html.HtmlComponent;


/**
 * 產生HTML JQuery ProgressBar
 * @author George_Tsai
 * @version 2009/6/10
 */

public class HtmlJQueryProgressBar extends HtmlComponent {
	private float uncomplete;
	
	/**
	 * ProgressBar建構子
	 * @param uncomplete 完成進度百分比 * 100
	 * @author George_Tsai
	 * @version 2009/6/10
	 */
	public HtmlJQueryProgressBar(float uncomplete) {
		this.uncomplete = uncomplete;
	}
	
	public String renderStart() {
		WebUtility webutil = new WebUtility();
		
		String uncompleteString = Float.toString(uncomplete);
		String integer = uncompleteString.substring(0, uncompleteString.indexOf("."));
		String decimal = uncompleteString.substring(uncompleteString.indexOf(".") + 1, uncompleteString.length());
		
		//設定idPrefix(讓grid有機會用純文字字典方式做排序)
		String idPrefix = "progressbar_" + StringUtility.addZero(integer, 3) + decimal + "_";
		int serial = 0;
		Enumeration<String> attributeNames = webutil.getRequest().getAttributeNames();
		
		while(attributeNames.hasMoreElements()) {
			String attributeName = attributeNames.nextElement();
			
			if(attributeName.equalsIgnoreCase(idPrefix + serial)) {
				serial++;
			}
		}
		
		String id = idPrefix + serial;
		
		webutil.setRequestAttribute(id, uncomplete);
		
		//產生HTML碼
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		htmlBuilder.divStart().id(idPrefix + serial);
		htmlBuilder.style("height: 15px");
		htmlBuilder.className("ui-progressbar progressbar ui-widget ui-widget-content ui-corner-all");
		htmlBuilder.tagClose();
		
		return htmlBuilder.toString();
	}
	
	public String renderBody(String content) {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		htmlBuilder.divStart();
		if(uncomplete < 100) {
			htmlBuilder.style("width: " + uncomplete + "%");
			htmlBuilder.className("ui-progressbar-value progressbar_uncompleted ui-corner-left");
		} else if(uncomplete == 100){
			htmlBuilder.style("width: " + uncomplete + "%");
			htmlBuilder.className("ui-progressbar-value progressbar_completed ui-corner-left ui-corner-right");
		} else {
			htmlBuilder.style("width: 100%");
			htmlBuilder.className("ui-progressbar-value progressbar_exceeded ui-corner-left ui-corner-right");
		}
		htmlBuilder.tagClose();
		
		htmlBuilder.text(uncomplete + "%");
		
		htmlBuilder.divEnd();
		
		return htmlBuilder.toString();
	}
	
	public String renderEnd() {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		htmlBuilder.divEnd();
		
		return htmlBuilder.toString();
	}
}
