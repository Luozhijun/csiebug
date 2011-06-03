package csiebug.web.html;

import csiebug.util.AssertUtility;

/**
 * 產生HTML碼工具
 * @author George_Tsai
 * @version 2009/2/25
 */

public class HtmlBuilder {
	private StringBuffer strHtml = new StringBuffer("");
	
	private String tabString = "&nbsp;&nbsp;&nbsp;&nbsp;";
	/**
	 * 可自訂tab長度(預設長度4)
	 * @param spaceCount
	 * @author George_Tsai
	 * @version 2009/8/6
	 */
	public void setTabLength(int spaceCount) {
		StringBuffer space = new StringBuffer();
		
		for(int i = 0; i < spaceCount; i++) {
			space.append("&nbsp;");
		}
		
		tabString = space.toString();
	}
	
	/**
	 * HTML Tag開頭
	 * @param Tag Name
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/19
	 */
	public HtmlBuilder tagStartForProperty(String name) {
		AssertUtility.notNullAndNotSpace(name);
		
		strHtml.append("<" + name);
		return this;
	}
	
	/**
	 * HTML Tag結尾
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/7/4
	 */
	public HtmlBuilder tagClose() {
		strHtml.append(">");
		return this;
	}
	
	/**
	 * HTML Tag開頭
	 * @param Tag Name
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/19
	 */
	public HtmlBuilder tagStart(String name) {
		AssertUtility.notNullAndNotSpace(name);
		
		strHtml.append("<" + name + ">");
		return this;
	}
	
	/**
	 * HTML Tag結尾
	 * @param Tag Name
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/7/4
	 */
	public HtmlBuilder tagClose(String name) {
		AssertUtility.notNullAndNotSpace(name);
		
		strHtml.append("</" + name + ">");
		return this;
	}
	
	/**
	 * HTML Tag內的屬性
	 * @param Property Name
	 * @param Property Value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/19
	 */
	public HtmlBuilder tagProperty(String name, String value) {
		AssertUtility.notNullAndNotSpace(name);
		AssertUtility.notNull(value);
		
		strHtml.append(" " + name + " = \"" + value + "\"");
		return this;
	}
	
	/**
	 * 非HTML的純文字部份
	 * @param Property Value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/19
	 */
	public HtmlBuilder text(String value) {
		AssertUtility.notNull(value);
		
		strHtml.append(value.replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>").replaceAll("\t", tabString));
		return this;
	}
	
	/**
	 * append任意字串
	 * @param Property Value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/7/3
	 */
	public HtmlBuilder appendString(String value) {
		AssertUtility.notNull(value);
		
		strHtml.append(value);
		return this;
	}
	
	/**
	 * 插入空白
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/21
	 */
	public HtmlBuilder space() {
		strHtml.append("&nbsp;");
		return this;
	}
	
	/**
	 * 插入縮排
	 * @param 縮排空白數
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/21
	 */
	public HtmlBuilder tab(int cnt) {
		for(int i = 0; i < cnt; i++) {
			strHtml.append("&nbsp;");
		}
		return this;
	}
	
	/**
	 * 插入縮排(預設縮排8個空白)
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/21
	 */
	public HtmlBuilder tab() {
		return tab(8);
	}
	
	/**
	 * 輸出HTML碼
	 * @return HTML碼
	 * @author George_Tsai
	 * @version 2008/5/19
	 */
	public String toString() {
		return strHtml.toString();
	}
	
	//以下為擴充功能可由基本功能完成
	
	//HTML各式Tag區
	/**
	 * br tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/19
	 */
	public HtmlBuilder br() {
		this.tagStart("br");
		return this;
	}
	
	/**
	 * br tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2010/10/5
	 */
	public HtmlBuilder brStart() {
		this.tagStartForProperty("br");
		return this;
	}
	
	/**
	 * br tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2010/10/5
	 */
	public HtmlBuilder brEnd() {
		this.tagClose("br");
		return this;
	}
	
	/**
	 * a tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/19
	 */
	public HtmlBuilder aStart() {
		this.tagStartForProperty("a");
		return this;
	}
	
	/**
	 * a tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/19
	 */
	public HtmlBuilder aEnd() {
		this.tagClose("a");
		return this;
	}
	
	/**
	 * hr tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/19
	 */
	public HtmlBuilder hr() {
		this.tagStart("hr");
		return this;
	}
	
	/**
	 * p tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/19
	 */
	public HtmlBuilder p() {
		this.tagStart("p");
		return this;
	}
	
	/**
	 * p tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/19
	 */
	public HtmlBuilder pStart() {
		this.tagStartForProperty("p");
		return this;
	}
	
	/**
	 * p tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/12/3
	 */
	public HtmlBuilder pEnd() {
		this.tagClose("p");
		return this;
	}
	
	/**
	 * input tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/20
	 */
	public HtmlBuilder inputStart() {
		this.tagStartForProperty("input");
		return this;
	}
	
	/**
	 * input tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/20
	 */
	public HtmlBuilder inputEnd() {
		this.tagClose("input");
		return this;
	}
	
	/**
	 * select tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/20
	 */
	public HtmlBuilder selectStart() {
		this.tagStartForProperty("select");
		return this;
	}
	
	/**
	 * select tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/20
	 */
	public HtmlBuilder selectEnd() {
		this.tagClose("select");
		return this;
	}
	
	/**
	 * option tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2010/10/8
	 */
	public HtmlBuilder optionStart() {
		this.tagStartForProperty("option");
		return this;
	}
	
	/**
	 * option tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2010/10/8
	 */
	public HtmlBuilder optionEnd() {
		this.tagClose("option");
		return this;
	}
	
	/**
	 * option tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/20
	 */
	public HtmlBuilder option(String value, String name) {
		AssertUtility.notNull(value);
		AssertUtility.notNull(name);
		
		this.optionStart().value(value).tagClose().text(name).optionEnd();
		return this;
	}
	
	/**
	 * option tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/6/11
	 */
	public HtmlBuilder optionSelected(String value, String name) {
		AssertUtility.notNull(value);
		AssertUtility.notNull(name);
		
		this.optionStart().value(value).selected().tagClose().text(name).optionEnd();
		return this;
	}
	
	/**
	 * option tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2010/10/8
	 */
	public HtmlBuilder option(String value, String name, String className) {
		AssertUtility.notNull(value);
		AssertUtility.notNull(name);
		
		this.optionStart().className(className).value(value).tagClose().text(name).optionEnd();
		return this;
	}
	
	/**
	 * option tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2010/10/8
	 */
	public HtmlBuilder optionSelected(String value, String name, String className) {
		AssertUtility.notNull(value);
		AssertUtility.notNull(name);
		
		this.optionStart().className(className).value(value).selected().tagClose().text(name).optionEnd();
		return this;
	}
	
	/**
	 * option tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2009/3/9
	 */
	public HtmlBuilder optionDisabled(String value, String name) {
		AssertUtility.notNull(value);
		AssertUtility.notNull(name);
		
		this.tagStartForProperty("option").value(value).disabled().tagClose().text(name).tagClose("option");
		return this;
	}
	
	/**
	 * textarea tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/20
	 */
	public HtmlBuilder textareaStart() {
		this.tagStartForProperty("textarea");
		return this;
	}
	
	/**
	 * textarea tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/20
	 */
	public HtmlBuilder textareaEnd() {
		this.tagClose("textarea");
		return this;
	}
	
	/**
	 * button tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/20
	 */
	public HtmlBuilder buttonStart() {
		this.tagStartForProperty("button");
		return this;
	}
	
	/**
	 * button tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/20
	 */
	public HtmlBuilder buttonEnd() {
		this.tagClose("button");
		return this;
	}
	
	/**
	 * label tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/21
	 */
	public HtmlBuilder labelStart() {
		this.tagStartForProperty("label");
		return this;
	}
	
	/**
	 * label tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/21
	 */
	public HtmlBuilder labelEnd() {
		this.tagClose("label");
		return this;
	}
	
	/**
	 * font tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/22
	 */
	public HtmlBuilder fontStart() {
		this.tagStartForProperty("font");
		return this;
	}
	
	/**
	 * font tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/22
	 */
	public HtmlBuilder fontEnd() {
		this.tagClose("font");
		return this;
	}
	
	/**
	 * table tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/22
	 */
	public HtmlBuilder table() {
		this.tagStart("table");
		return this;
	}
	
	/**
	 * table tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/22
	 */
	public HtmlBuilder tableStart() {
		this.tagStartForProperty("table");
		return this;
	}
	
	/**
	 * table tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/22
	 */
	public HtmlBuilder tableEnd() {
		this.tagClose("table");
		return this;
	}
	
	/**
	 * tr tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/22
	 */
	public HtmlBuilder tr() {
		this.tagStart("tr");
		return this;
	}
	
	/**
	 * tr tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/22
	 */
	public HtmlBuilder trStart() {
		this.tagStartForProperty("tr");
		return this;
	}
	
	/**
	 * tr tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/22
	 */
	public HtmlBuilder trEnd() {
		this.tagClose("tr");
		return this;
	}
	
	/**
	 * td tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/22
	 */
	public HtmlBuilder td() {
		this.tagStart("td");
		return this;
	}
	
	/**
	 * td tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/22
	 */
	public HtmlBuilder tdStart() {
		this.tagStartForProperty("td");
		return this;
	}
	
	/**
	 * td tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/22
	 */
	public HtmlBuilder tdEnd() {
		this.tagClose("td");
		return this;
	}
	
	/**
	 * image tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/22
	 */
	public HtmlBuilder imageStart() {
		this.tagStartForProperty("image");
		return this;
	}
	
	/**
	 * image tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/22
	 */
	public HtmlBuilder imageEnd() {
		this.tagClose("image");
		return this;
	}
	
	/**
	 * img tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2009/1/10
	 */
	public HtmlBuilder imgStart() {
		this.tagStartForProperty("img");
		return this;
	}
	
	/**
	 * img tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2009/1/10
	 */
	public HtmlBuilder imgEnd() {
		this.tagClose("img");
		return this;
	}
	
	/**
	 * div tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/26
	 */
	public HtmlBuilder divStart() {
		this.tagStartForProperty("div");
		return this;
	}
	
	/**
	 * div tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/26
	 */
	public HtmlBuilder divEnd() {
		this.tagClose("div");
		return this;
	}
	
	/**
	 * span tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/12/3
	 */
	public HtmlBuilder spanStart() {
		this.tagStartForProperty("span");
		return this;
	}
	
	/**
	 * span tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/12/3
	 */
	public HtmlBuilder spanEnd() {
		this.tagClose("span");
		return this;
	}
	
	/**
	 * link tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2009/1/10
	 */
	public HtmlBuilder linkStart() {
		this.tagStartForProperty("link");
		return this;
	}
	
	/**
	 * link tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2009/1/10
	 */
	public HtmlBuilder linkEnd() {
		this.tagClose("link");
		return this;
	}
	
	/**
	 * script tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2009/1/10
	 */
	public HtmlBuilder scriptStart() {
		this.tagStartForProperty("script");
		return this;
	}
	
	/**
	 * script tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2009/1/10
	 */
	public HtmlBuilder scriptEnd() {
		this.tagClose("script");
		return this;
	}
	
	/**
	 * fieldset tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2009/2/25
	 */
	public HtmlBuilder fieldset() {
		this.tagStart("fieldset");
		return this;
	}
	
	/**
	 * fieldset tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2009/2/25
	 */
	public HtmlBuilder fieldsetStart() {
		this.tagStartForProperty("fieldset");
		return this;
	}
	
	/**
	 * fieldset tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2009/2/25
	 */
	public HtmlBuilder fieldsetEnd() {
		this.tagClose("fieldset");
		return this;
	}
	
	/**
	 * legend tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2009/2/25
	 */
	public HtmlBuilder legend() {
		this.tagStart("legend");
		return this;
	}
	
	/**
	 * legend tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2009/2/25
	 */
	public HtmlBuilder legendStart() {
		this.tagStartForProperty("legend");
		return this;
	}
	
	/**
	 * legend tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2009/2/25
	 */
	public HtmlBuilder legendEnd() {
		this.tagClose("legend");
		return this;
	}
	
	/**
	 * ul tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2009/5/20
	 */
	public HtmlBuilder ul() {
		this.tagStart("ul");
		return this;
	}
	
	/**
	 * ul tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2009/5/20
	 */
	public HtmlBuilder ulStart() {
		this.tagStartForProperty("ul");
		return this;
	}
	
	/**
	 * ul tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2009/5/20
	 */
	public HtmlBuilder ulEnd() {
		this.tagClose("ul");
		return this;
	}
	
	/**
	 * li tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2009/5/20
	 */
	public HtmlBuilder li() {
		this.tagStart("li");
		return this;
	}
	
	/**
	 * li tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2009/5/20
	 */
	public HtmlBuilder liStart() {
		this.tagStartForProperty("li");
		return this;
	}
	
	/**
	 * li tag
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2009/5/20
	 */
	public HtmlBuilder liEnd() {
		this.tagClose("li");
		return this;
	}
	
	/**
	 * iframe tag
	 * @return
	 * @author George_Tsai
	 * @version 2009/6/24
	 */
	public HtmlBuilder iframeStart() {
		this.tagStartForProperty("iframe");
		return this;
	}
	
	/**
	 * iframe tag
	 * @return
	 * @author George_Tsai
	 * @version 2009/6/24
	 */
	public HtmlBuilder iframeEnd() {
		this.tagClose("iframe");
		return this;
	}
	
	/**
	 * pre tag
	 * @return
	 * @author George_Tsai
	 * @version 2009/8/5
	 */
	public HtmlBuilder pre() {
		this.tagStart("pre");
		return this;
	}
	
	/**
	 * pre tag
	 * @return
	 * @author George_Tsai
	 * @version 2009/8/5
	 */
	public HtmlBuilder preEnd() {
		this.tagClose("pre");
		return this;
	}
	
	/**
	 * map tag
	 * @return
	 * @author George_Tsai
	 * @version 2011/2/8
	 */
	public HtmlBuilder mapStart() {
		this.tagStartForProperty("map");
		return this;
	}
	
	/**
	 * map tag
	 * @return
	 * @author George_Tsai
	 * @version 2011/2/8
	 */
	public HtmlBuilder mapEnd() {
		this.tagClose("map");
		return this;
	}
	
	/**
	 * area tag
	 * @return
	 * @author George_Tsai
	 * @version 2011/2/8
	 */
	public HtmlBuilder areaStart() {
		this.tagStartForProperty("area");
		return this;
	}
	
	/**
	 * area tag
	 * @return
	 * @author George_Tsai
	 * @version 2011/2/8
	 */
	public HtmlBuilder areaEnd() {
		this.tagClose("area");
		return this;
	}
	
	//HTML Tag屬性區
	/**
	 * Id屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/19
	 */
	public HtmlBuilder id(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("id", value);
		return this;
	}
	
	/**
	 * Name屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/19
	 */
	public HtmlBuilder name(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("name", value);
		return this;
	}
	
	/**
	 * align屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/19
	 */
	public HtmlBuilder align(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("align", value);
		return this;
	}
	
	/**
	 * href屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/20
	 */
	public HtmlBuilder href(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("href", value);
		return this;
	}
	
	/**
	 * target屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/20
	 */
	public HtmlBuilder target(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("target", value);
		return this;
	}
	
	/**
	 * style屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/20
	 */
	public HtmlBuilder style(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("style", value);
		return this;
	}
	
	/**
	 * tabIndex屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/20
	 */
	public HtmlBuilder tabIndex(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("tabIndex", value);
		return this;
	}
	
	/**
	 * className屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/20
	 */
	public HtmlBuilder className(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("class", value);
		return this;
	}
	
	/**
	 * title屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/20
	 */
	public HtmlBuilder title(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("title", value);
		return this;
	}
	
	/**
	 * color屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/20
	 */
	public HtmlBuilder color(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("color", value);
		return this;
	}
	
	/**
	 * size屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/20
	 */
	public HtmlBuilder size(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("size", value);
		return this;
	}
	
	/**
	 * width屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/20
	 */
	public HtmlBuilder width(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("width", value);
		return this;
	}
	
	/**
	 * height屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2009/2/2
	 */
	public HtmlBuilder height(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("height", value);
		return this;
	}
	
	/**
	 * value屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/20
	 */
	public HtmlBuilder value(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("value", value);
		return this;
	}
	
	/**
	 * checked屬性
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/22
	 */
	public HtmlBuilder checked() {
		strHtml.append(" checked");
		return this;
	}
	
	/**
	 * selected屬性
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/6/11
	 */
	public HtmlBuilder selected() {
		strHtml.append(" selected");
		return this;
	}
	
	/**
	 * disabled屬性
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/22
	 */
	public HtmlBuilder disabled() {
		strHtml.append(" disabled");
		return this;
	}
	
	/**
	 * length屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/20
	 */
	public HtmlBuilder length(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("length", value);
		return this;
	}
	
	/**
	 * readOnly屬性
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/21
	 */
	public HtmlBuilder readOnly() {
		strHtml.append(" readonly");
		return this;
	}
	
	/**
	 * alt屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/20
	 */
	public HtmlBuilder alt(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("alt", value);
		return this;
	}
	
	/**
	 * maxLength屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/20
	 */
	public HtmlBuilder maxLength(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("maxLength", value);
		return this;
	}
	
	/**
	 * type屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/20
	 */
	public HtmlBuilder type(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("type", value);
		return this;
	}
	
	/**
	 * rows屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/6/13
	 */
	public HtmlBuilder rows(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("rows", value);
		return this;
	}
	
	/**
	 * src屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/7/4
	 */
	public HtmlBuilder src(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("src", value);
		return this;
	}
	
	/**
	 * colspan屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/12/7
	 */
	public HtmlBuilder colspan(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("colspan", value);
		return this;
	}
	
	/**
	 * rowspan屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2009/2/25
	 */
	public HtmlBuilder rowspan(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("rowspan", value);
		return this;
	}
	
	/**
	 * rel屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2009/1/10
	 */
	public HtmlBuilder rel(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("rel", value);
		return this;
	}
	
	/**
	 * language屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2009/1/10
	 */
	public HtmlBuilder language(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("language", value);
		return this;
	}
	
	/**
	 * cellspacing屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2009/6/10
	 */
	public HtmlBuilder cellspacing(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("cellspacing", value);
		return this;
	}
	
	/**
	 * cellpadding屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2009/6/10
	 */
	public HtmlBuilder cellpadding(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("cellpadding", value);
		return this;
	}
	
	/**
	 * border屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2009/6/10
	 */
	public HtmlBuilder border(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("border", value);
		return this;
	}
	
	/**
	 * bgcolor屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2009/6/10
	 */
	public HtmlBuilder bgcolor(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("bgcolor", value);
		return this;
	}
	
	/**
	 * valign屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2009/8/5
	 */
	public HtmlBuilder valign(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("valign", value);
		return this;
	}
	
	//事件處理屬性
	/**
	 * onChange屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/21
	 */
	public HtmlBuilder onChange(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("onChange", value);
		return this;
	}
	
	/**
	 * onClick屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/21
	 */
	public HtmlBuilder onClick(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("onClick", value);
		return this;
	}
	
	/**
	 * onDblClick屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/21
	 */
	public HtmlBuilder onDblClick(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("onDblClick", value);
		return this;
	}
	
	/**
	 * onBlur屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/22
	 */
	public HtmlBuilder onBlur(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("onBlur", value);
		return this;
	}
	
	/**
	 * onKeyDown屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2008/5/27
	 */
	public HtmlBuilder onKeyDown(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("onKeyDown", value);
		return this;
	}
	
	/**
	 * onFocus屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2009/1/5
	 */
	public HtmlBuilder onFocus(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("onFocus", value);
		return this;
	}
	
	/**
	 * onKeyUp屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2009/1/8
	 */
	public HtmlBuilder onKeyUp(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("onKeyUp", value);
		return this;
	}
	
	/**
	 * onMouseMove屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2009/1/8
	 */
	public HtmlBuilder onMouseMove(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("onMouseMove", value);
		return this;
	}
	
	/**
	 * onMouseOut屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2009/1/8
	 */
	public HtmlBuilder onMouseOut(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("onMouseOut", value);
		return this;
	}
	
	/**
	 * onMouseOver屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2009/1/10
	 */
	public HtmlBuilder onMouseOver(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("onMouseOver", value);
		return this;
	}
	
	/**
	 * onKeyPress屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2010/2/2
	 */
	public HtmlBuilder onKeyPress(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("onKeyPress", value);
		return this;
	}
	
	/**
	 * shape屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2011/2/8
	 */
	public HtmlBuilder shape(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("shape", value);
		return this;
	}
	
	/**
	 * coords屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2011/2/8
	 */
	public HtmlBuilder coords(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("coords", value);
		return this;
	}
	
	/**
	 * usemap屬性
	 * @param value
	 * @return HtmlBuilder
	 * @author George_Tsai
	 * @version 2011/2/8
	 */
	public HtmlBuilder usemap(String value) {
		AssertUtility.notNull(value);
		
		this.tagProperty("usemap", value);
		return this;
	}
}
