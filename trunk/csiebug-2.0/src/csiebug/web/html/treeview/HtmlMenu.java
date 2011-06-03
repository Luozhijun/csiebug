package csiebug.web.html.treeview;

import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import csiebug.util.AssertUtility;
import csiebug.util.ListUtility;
import csiebug.util.StringUtility;
import csiebug.util.WebUtility;
import csiebug.web.html.HtmlBuilder;


/**
 * 產生HTML Menu
 * @author George_Tsai
 * @version 2009/1/19
 */

public class HtmlMenu {
	private String htmlId;
	private List<Map<String, String>> listTreeView;
	private String imagePath;
	private String targetFrame = "mainFrame";
	
	private int maxlevel = 0;
	private WebUtility webutil = new WebUtility();
	
	/**
	 * Menu建構子
	 * @param 由LinkedHashMap(ParentId, Id, Name, URL, ImageName, SortOrder)組成的ArrayList
	 * @author George_Tsai
	 * @version 2008/12/30
	 * @throws NamingException 
	 */
	public HtmlMenu(List<Map<String, String>> list) throws NamingException {
		listTreeView = list;
		imagePath = webutil.getBasePathForHTML() + "images";
	}
	
	/**
	 * Menu建構子
	 * @param 由LinkedHashMap(ParentId, Id, Name, URL, ImageName, SortOrder)組成的ArrayList
	 * @param css的位置
	 * @param 圖檔的位置
	 * @param js的位置
	 * @param TreeView的Target Frame
	 * @author George_Tsai
	 * @version 2009/1/19
	 * @throws NamingException 
	 */
	public HtmlMenu(String id, List<Map<String, String>> list, String imagePath, String targetFrame) throws NamingException {
		this.htmlId = id;
		this.listTreeView = list;
		
		if(imagePath != null) {
			this.imagePath = StringUtility.removeStartEndSlash(imagePath);
		} else {
			this.imagePath = webutil.getBasePathForHTML() + "images";
		}
		
		if(targetFrame != null) {
			this.targetFrame = targetFrame;
		}
	}
	
	/**
	 * 取得Menu的HTML碼
	 * @return Menu的HTML碼
	 * @author George_Tsai
	 * @version 2009/1/10
	 */
	public String getMenu() {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		List<Map<String, String>> rootList = ListUtility.getSubListByName(listTreeView, "ParentId", "");
		rootList = ListUtility.sortByName(rootList, "SortOrder");
		
		int maxStringLength = getMaxStringLength(rootList);
		
		for(int i = 0; i < rootList.size(); i++) {
			Map<String, String> treeNode = rootList.get(i);
			String strId = treeNode.get("Id");
			String strName = treeNode.get("Name");
			String strURL = treeNode.get("URL");
			String strImgName = treeNode.get("ImageName");
			
			if(maxStringLength < strName.length()) {
				maxStringLength = strName.length();
			}
			
			if(strURL.equals("")) { //目錄
				htmlBuilder.appendString(getSubMenu(strId, strName, strImgName, 1, maxStringLength) + "\n");
			} else { //程式頁面
				htmlBuilder.appendString(getLeaf(strName, strURL, strImgName, 1) + "\n");
			}
		}
		
		return htmlBuilder.toString().replaceAll("var.maxlevel", "" + maxlevel);
	}
	
	private int getMaxStringLength(List<Map<String, String>> list) {
		AssertUtility.notNull(list);
		
		int maxStringLength = 0;
		
		for(int i = 0; i < list.size(); i++) {
			Map<String, String> treeNode = list.get(i);
			String strName = treeNode.get("Name");
			
			if(maxStringLength < strName.length()) {
				maxStringLength = strName.length();
			}
		}
		
		return maxStringLength;
	}
	
	/**
	 * 取得程式連結
	 * @param 程式名稱
	 * @param 程式連結
	 * @param icon名稱
	 * @param 層次
	 * @return HTML碼
	 * @author George_Tsai
	 * @version 2009/1/10
	 */
	private String getLeaf(String strName, String strURL, String strImgName, int level) {
		AssertUtility.notNull(strName);
		AssertUtility.notNull(strURL);
		AssertUtility.notNull(strImgName);
		
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		setMaxLevel(level);
		
		makeStart(htmlBuilder, level);
		
		//程式圖檔
		if(strImgName.trim().equals("")) {
			htmlBuilder.imgStart().src(imagePath + "/Blank.gif").title(strName).style("CURSOR:pointer").align("absmiddle").onClick("parent." + targetFrame + ".location.href='" + strURL + "'").onMouseOver("moveMenuOver('', " + level + ", var.maxlevel);").tagClose().appendString("\n");
		} else {
			htmlBuilder.imgStart().src(imagePath + "/" + strImgName).title(strName).style("CURSOR:pointer").align("absmiddle").onClick("parent." + targetFrame + ".location.href='" + strURL + "'").onMouseOver("moveMenuOver('', " + level + ", var.maxlevel)").tagClose().appendString("\n");
		}
		
		//程式hyperlink
		htmlBuilder.aStart().href(strURL).target(targetFrame).onMouseOver("moveMenuOver('', " + level + ", var.maxlevel)").tagClose().text(strName).aEnd().space().appendString("\n");
		
		makeEnd(htmlBuilder, level);
		
		return htmlBuilder.toString();
	}
	
	/**
	 * 取得子Menu
	 * @param root id
	 * @param root name
	 * @param icon名稱
	 * @param 層次
	 * @return HTML碼
	 * @author George_Tsai
	 * @version 2009/1/10
	 */
	private String getSubMenu(String strId, String strName, String strImgName, int level, int maxStringLength) {
		AssertUtility.notNull(strId);
		AssertUtility.notNull(strName);
		AssertUtility.notNull(strImgName);
		
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		setMaxLevel(level);
		
		makeStart(htmlBuilder, level);
		
		makeSubMenuBody(htmlBuilder, level, strImgName, strId, strName, maxStringLength);
		
		makeEnd(htmlBuilder, level);
		
		return htmlBuilder.toString();
	}
	
	private void setMaxLevel(int level) {
		if(level > maxlevel) {
			maxlevel = level;
		}
	}
	
	private void makeStart(HtmlBuilder htmlBuilder, int level) {
		AssertUtility.notNull(htmlBuilder);
		
		if(level == 1) {
			htmlBuilder.spanStart().className("menubar").onMouseOver("this.className='menubarhover'").onMouseOut("this.className='menubar'").tagClose().appendString("\n");
		} else {
			htmlBuilder.divStart().className("menu").onMouseMove("this.className='menuhover'").onMouseOut("this.className='menu'").tagClose().appendString("\n");
		}
	}
	
	private void makeSubMenuBody(HtmlBuilder htmlBuilder, int level, String strImgName, String strId, String strName, int maxStringLength) {
		AssertUtility.notNull(htmlBuilder);
		AssertUtility.notNull(strImgName);
		AssertUtility.notNull(strId);
		AssertUtility.notNull(strName);
		
		makeSubMenuStart(htmlBuilder, level, strImgName, strId, strName, maxStringLength);
		
		List<Map<String, String>> subList = (List<Map<String, String>>)ListUtility.getSubListByName(listTreeView, "ParentId", strId);
		subList = (List<Map<String, String>>)ListUtility.sortByName(subList, "SortOrder");
		
		int childMaxStringLength = getMaxStringLength(subList);
		for(int i = 0; i < subList.size(); i++) {
			Map<String, String> treeNode = subList.get(i);
			String strChildId = treeNode.get("Id");
			String strChildName = treeNode.get("Name");
			String strChildURL = treeNode.get("URL");
			String strChildImgName = treeNode.get("ImageName");
			
			if(strChildURL.equals("")) { //目錄
				htmlBuilder.appendString(getSubMenu(strChildId, strChildName, strChildImgName, level + 1, childMaxStringLength) + "\n");
			} else { //程式頁面
				htmlBuilder.appendString(getLeaf(strChildName, strChildURL, strChildImgName, level + 1) + "\n");
			}
		}
		
		makeSubMenuBodyEnd(htmlBuilder);
	}
	
	private void makeSubMenuStart(HtmlBuilder htmlBuilder, int level, String strImgName, String strId, String strName, int maxStringLength) {
		AssertUtility.notNull(htmlBuilder);
		AssertUtility.notNull(strImgName);
		AssertUtility.notNull(strId);
		AssertUtility.notNull(strName);
		
		if(htmlId != null) {
			strId = strId + "_" + htmlId;
		}
		
		if(level == 1) {
			//folder圖檔
			if(strImgName.trim().equals("")) {
				htmlBuilder.imgStart().id(strId + "_folderMenu").name(strId + "_folderMenu").src(imagePath + "/Blank.gif").title(strName).style("CURSOR:pointer;position:relative;").onClick("openMenu('" + strId + "', " + level + ")").onMouseOver("moveMenuOver('" + strId + "', " + level + ", var.maxlevel)").align("absmiddle").tagClose().appendString("\n");
			} else {
				htmlBuilder.imgStart().id(strId + "_folderMenu").name(strId + "_folderMenu").src(imagePath + "/" + strImgName).title(strName).style("CURSOR:pointer;position:absolute;").onClick("openMenu('" + strId + "', " + level + ")").onMouseOver("moveMenuOver('" + strId + "', " + level + ", var.maxlevel)").align("absmiddle").tagClose().appendString("\n");
			}
			htmlBuilder.spanStart().title(strName).style("CURSOR:pointer").onClick("openMenu('" + strId + "', " + level + ")").onMouseOver("moveMenuOver('" + strId + "', " + level + ", var.maxlevel)").tagClose().text(strName).spanEnd().space().appendString("\n");
			
			//對應的div(子節點)
			htmlBuilder.divStart().id(strId + "_" + level + "_menu").name(strId + "_" + level + "_menu").style("display:none;line-height:0;position:absolute;").tagClose().appendString("\n");
		} else {
			//folder圖檔
			if(strImgName.trim().equals("")) {
				htmlBuilder.imgStart().src(imagePath + "/Blank.gif").title(strName).style("CURSOR:pointer").onMouseOver("moveMenuOver('" + strId + "', " + level + ", var.maxlevel)").align("absmiddle").tagClose().appendString("\n");
			} else {
				htmlBuilder.imgStart().src(imagePath + "/" + strImgName).title(strName).style("CURSOR:pointer").onMouseOver("moveMenuOver('" + strId + "', " + level + ", var.maxlevel)").align("absmiddle").tagClose().appendString("\n");
			}
			htmlBuilder.spanStart().id(strId + "_folderMenu").name(strId + "_folderMenu").title(strName).style("CURSOR:pointer").onMouseOver("moveMenuOver('" + strId + "', " + level + ", var.maxlevel)").tagClose().text(strName).space().text(getSpace(maxStringLength, strName.length())).imgStart().src(imagePath + "/right_direct.gif").align("absmiddle").tagClose().spanEnd().appendString("\n");
			
			//對應的div(子節點)
			htmlBuilder.divStart().id(strId + "_" + level + "_menu").name(strId + "_" + level + "_menu").style("display:none;line-height:0").tagClose().appendString("\n");
		}
	}
	
	private String getSpace(int maxStringLength, int currentStringLength) {
		StringBuffer strSpace = new StringBuffer();
		
		for(int i = 0; i < (maxStringLength - currentStringLength) + 1; i++) {
			strSpace.append("&nbsp;");
		}
			
		return strSpace.toString();
	}
	
	private void makeSubMenuBodyEnd(HtmlBuilder htmlBuilder) {
		AssertUtility.notNull(htmlBuilder);
		
		htmlBuilder.divEnd().appendString("\n");
	}
	
	private void makeEnd(HtmlBuilder htmlBuilder, int level) {
		AssertUtility.notNull(htmlBuilder);
		
		if(level == 1) {
			htmlBuilder.spanEnd().appendString("\n");
		} else {
			htmlBuilder.divEnd().appendString("\n");
		}
	}
}
