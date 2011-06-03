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
 * 產生HTML TreeView
 * @author George_Tsai
 * @version 2009/1/30
 */

public class HtmlTreeView {
	private List<Map<String, String>> listTreeView;
	private String htmlId;
	private String imagePath;
	private String targetFrame = "mainFrame";
	
	private WebUtility webutil = new WebUtility();
	
	private static final int FirstNode = 1;
	private static final int LastNode = 2;
	private static final int MiddleNode = 3;
	private static final int OnlyOneNode = 4;
	
	/**
	 * TreeView建構子
	 * @param 由LinkedHashMap(ParentId, Id, Name, URL, SortOrder)組成的ArrayList
	 * @author George_Tsai
	 * @version 2008/5/20
	 * @throws NamingException 
	 */
	public HtmlTreeView(List<Map<String, String>> list) throws NamingException {
		listTreeView = list;
		imagePath = webutil.getBasePathForHTML() + "images";
	}
	
	/**
	 * TreeView建構子
	 * @param 由LinkedHashMap(ParentId, Id, Name, URL, SortOrder)組成的ArrayList
	 * @param 圖檔的位置
	 * @param TreeView的Target Frame
	 * @author George_Tsai
	 * @version 2009/1/19
	 * @throws NamingException 
	 */
	public HtmlTreeView(String id, List<Map<String, String>> list, String imagePath, String targetFrame) throws NamingException {
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
	 * 取得TreeView的HTML碼
	 * @return TreeView的HTML碼
	 * @author George_Tsai
	 * @version 2009/1/30
	 */
	public String getTreeView() {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		htmlBuilder.divStart().style("font-size:9pt;padding:0;margin:0;border:none;text-align:left;line-height:0;top:0;bottom:0").tagClose().appendString("\n");
		
		List<Map<String, String>> rootList = ListUtility.getSubListByName(listTreeView, "ParentId", "");
		rootList = ListUtility.sortByName(rootList, "SortOrder");
		
		for(int i = 0; i < rootList.size(); i++) {
			Map<String, String> treeNode = rootList.get(i);
			String strId = treeNode.get("Id");
			String strName = treeNode.get("Name");
			String strURL = treeNode.get("URL");
			int[] intTabBlank = new int[0];
			
			if(strURL.equals("")) { //目錄
				makeFolderWithNodeType(htmlBuilder, rootList, i, strId, strName, 0, intTabBlank, true);
			} else { //程式頁面
				makeFunctionWithNodeType(htmlBuilder, rootList, i, strURL, strName, true);
			}
		}
		
		htmlBuilder.divEnd().appendString("\n");
		
		return htmlBuilder.toString();
	}
	
	private void makeFolderWithNodeType(HtmlBuilder htmlBuilder, List<Map<String, String>> list, int i, String strId, String strName, int intTabCnt, int[] intTabBlank, boolean rootFlag) {
		AssertUtility.notNull(htmlBuilder);
		AssertUtility.notNull(list);
		AssertUtility.notNull(strId);
		AssertUtility.notNull(strName);
		AssertUtility.notNull(intTabBlank);
		
		if(rootFlag && list.size() == 1) {
			htmlBuilder.appendString(getSubTreeView(strId, strName, intTabCnt, OnlyOneNode, intTabBlank) + "\n");
		} else {
			if(rootFlag && i == 0) { //如果第一個是目錄
				htmlBuilder.appendString(getSubTreeView(strId, strName, intTabCnt, FirstNode, intTabBlank) + "\n");
			} else if(i == list.size() - 1) { //如果最後一個是目錄
				htmlBuilder.appendString(getSubTreeView(strId, strName, intTabCnt, LastNode, intTabBlank) + "\n");
			} else { //中間的目錄
				htmlBuilder.appendString(getSubTreeView(strId, strName, intTabCnt, MiddleNode, intTabBlank) + "\n");
			}
		}
	}
	
	private void makeFunctionWithNodeType(HtmlBuilder htmlBuilder, List<Map<String, String>> list, int i, String strURL, String strName, boolean rootFlag) {
		AssertUtility.notNull(htmlBuilder);
		AssertUtility.notNull(list);
		AssertUtility.notNull(strURL);
		AssertUtility.notNull(strName);
		
		if(rootFlag && list.size() == 1) {
			htmlBuilder.appendString(getTreeLeaf(strName, strURL, OnlyOneNode) + "\n");
		} else {
			if(rootFlag && i == 0) { //如果第一個是程式
				htmlBuilder.appendString(getTreeLeaf(strName, strURL, FirstNode) + "\n");
			} else if(i == list.size() - 1) { //如果最後一個是程式
				htmlBuilder.appendString(getTreeLeaf(strName, strURL, LastNode) + "\n");
			} else { //中間的程式
				htmlBuilder.appendString(getTreeLeaf(strName, strURL, MiddleNode) + "\n");
			}
		}
	}
	
	/**
	 * 取得程式連結
	 * @param 程式名稱
	 * @param 程式連結
	 * @param 節點型別
	 * @return HTML碼
	 * @author George_Tsai
	 * @version 2009/1/30
	 */
	private String getTreeLeaf(String strName, String strURL, int intLeafType) {
		AssertUtility.notNull(strName);
		AssertUtility.notNull(strURL);
		
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		htmlBuilder.spanStart().style("line-height:normal; white-space:nowrap").tagClose().appendString("\n");
		if(intLeafType == FirstNode) { //第一個程式
			htmlBuilder.imgStart().src(imagePath + "/ET.gif").align("absmiddle").tagClose().imgEnd().appendString("\n");
		} else if(intLeafType == LastNode) { //最後一個程式
			htmlBuilder.imgStart().src(imagePath + "/L.gif").align("absmiddle").tagClose().imgEnd().appendString("\n");
		} else if(intLeafType == MiddleNode){ //中間的程式
			htmlBuilder.imgStart().src(imagePath + "/E.gif").align("absmiddle").tagClose().imgEnd().appendString("\n");
		} else {
			htmlBuilder.imgStart().src(imagePath + "/LT.gif").align("absmiddle").tagClose().imgEnd().appendString("\n");
		}
		
		//程式圖檔
		htmlBuilder.imgStart().src(imagePath + "/Leaf.gif").title(strName).style("CURSOR:pointer").align("absmiddle").onClick("parent." + targetFrame + ".location.href='" + strURL + "'").tagClose().appendString("\n");
		
		//程式hyperlink
		htmlBuilder.aStart().href(strURL).target(targetFrame).tagClose().text(strName).aEnd().appendString("\n");
		htmlBuilder.spanEnd().br().appendString("\n");
		
		return htmlBuilder.toString();
	}
	
	/**
	 * 取得子樹
	 * @param root id
	 * @param root name
	 * @param 縮排層次
	 * @param 節點型別
	 * @param 紀錄子節點的縮排哪幾層該給空白
	 * @return HTML碼
	 * @author George_Tsai
	 * @version 2009/1/30
	 */
	private String getSubTreeView(String strId, String strName, int intTabCnt, int intFolderType, int[] intTabBlank) {
		AssertUtility.notNull(strId);
		AssertUtility.notNull(strName);
		AssertUtility.notNull(intTabBlank);
		
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		htmlBuilder.spanStart().style("line-height:normal; white-space:nowrap").tagClose().appendString("\n");
		
		htmlBuilder.appendString(getTab(intTabCnt, intTabBlank));
		makeFolderPic(htmlBuilder, intFolderType, strId, strName);
		
		//對應的div(子節點)
		if(htmlId != null) {
			htmlBuilder.divStart().id(strId + "_" + htmlId).name(strId + "_" + htmlId).style("display:none;line-height:0").tagClose().appendString("\n");	
		} else {
			htmlBuilder.divStart().id(strId).name(strId).style("display:none;line-height:0").tagClose().appendString("\n");
		}
		
		List<Map<String, String>> subList = ListUtility.getSubListByName(listTreeView, "ParentId", strId);
		subList = ListUtility.sortByName(subList, "SortOrder");
		
		for(int i = 0; i < subList.size(); i++) {
			Map<String, String> treeNode = subList.get(i);
			String strChildId = treeNode.get("Id");
			String strChildName = treeNode.get("Name");
			String strChildURL = treeNode.get("URL");
			int[] intTabBlank2;
			
			if(intFolderType == LastNode) { //如果本目錄是本層的最後一個目錄,需記錄下來,本目錄的子節點縮排時才可以產生空白
				intTabBlank2 = new int[intTabBlank.length + 1];
				intTabBlank2[intTabBlank2.length - 1] = intTabCnt;
			} else {
				intTabBlank2 = intTabBlank;
			}
			
			if(strChildURL.equals("")) { //目錄
				makeFolderWithNodeType(htmlBuilder, subList, i, strChildId, strChildName, intTabCnt + 1, intTabBlank2, false);
			} else { //程式頁面
				htmlBuilder.appendString(getTab(intTabCnt + 1, intTabBlank2));
				makeFunctionWithNodeType(htmlBuilder, subList, i, strChildURL, strChildName, false);
			}
		}
		
		htmlBuilder.divEnd().appendString("\n");
		htmlBuilder.spanEnd().br().appendString("\n");
		
		return htmlBuilder.toString();
	}
	
	private void makeFolderPic(HtmlBuilder htmlBuilder, int intFolderType, String strId, String strName) {
		AssertUtility.notNull(htmlBuilder);
		AssertUtility.notNull(strId);
		AssertUtility.notNull(strName);
		
		if(htmlId != null) {
			strId = strId + "_" + htmlId; 
		}
		
		if(intFolderType == FirstNode) { // 第一個folder對應的圖檔
			htmlBuilder.imgStart().id(strId + "_line").name(strId + "_line").src(imagePath + "/ETplus.gif").style("CURSOR:pointer").onClick("showSubTree('" + strId + "', '" + imagePath + "')").align("absmiddle").tagClose().imgEnd().appendString("\n");
		} else if(intFolderType == LastNode) { //最後一個folder對應的圖檔
			htmlBuilder.imgStart().id(strId + "_line").name(strId + "_line").src(imagePath + "/Lplus.gif").style("CURSOR:pointer").onClick("showSubTree('" + strId + "', '" + imagePath + "')").align("absmiddle").tagClose().imgEnd().appendString("\n");
		} else if(intFolderType == MiddleNode){ //中間其他的folder對應的圖檔
			htmlBuilder.imgStart().id(strId + "_line").name(strId + "_line").src(imagePath + "/Eplus.gif").style("CURSOR:pointer").onClick("showSubTree('" + strId + "', '" + imagePath + "')").align("absmiddle").tagClose().imgEnd().appendString("\n");
		} else {
			htmlBuilder.imgStart().id(strId + "_line").name(strId + "_line").src(imagePath + "/LTplus.gif").style("CURSOR:pointer").onClick("showSubTree('" + strId + "', '" + imagePath + "')").align("absmiddle").tagClose().imgEnd().appendString("\n");
		}
		
		//folder圖檔
		htmlBuilder.imgStart().id(strId + "_folder").name(strId + "_folder").src(imagePath + "/Cfolder.gif").title(strName).style("CURSOR:pointer").onClick("showSubTree('" + strId + "', '" + imagePath + "')").align("absmiddle").tagClose().appendString("\n").spanStart().title(strName).style("CURSOR:pointer").onClick("showSubTree('" + strId + "', '" + imagePath + "')").tagClose().text(strName).spanEnd().appendString("\n");
	}
	
	/**
	 * 取得縮排
	 * @param 縮排層次
	 * @param 哪幾層是空白
	 * @return HTML碼
	 * @author George_Tsai
	 * @version 2008/5/21
	 */
	private String getTab(int intTabCnt, int[] intTabBlank) {
		AssertUtility.notNull(intTabBlank);
		
		StringBuffer strTab = new StringBuffer("");
		
		for(int i = 0; i < intTabCnt; i++) {
			boolean flagBlank = false;
			
			for(int j = 0; j < intTabBlank.length; j++) {
				if(i == intTabBlank[j]) {
					flagBlank = true;
					break;
				} else if(i > intTabBlank[j]) {
					break;
				}
			}
			
			if(flagBlank) {
				strTab.append(getBlank());
			} else {
				strTab.append(getVertline());
			}
		}
		
		return strTab.toString();
	}
	
	/**
	 * 取得縮排
	 * @return HTML碼
	 * @author George_Tsai
	 * @version 2009/1/10
	 */
	private String getVertline() {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		htmlBuilder.imgStart().src(imagePath + "/Vertline.gif").align("absmiddle").tagClose().imgEnd();
		
		return htmlBuilder.toString();
	}
	
	/**
	 * 取得縮排
	 * @return HTML碼
	 * @author George_Tsai
	 * @version 2009/1/10
	 */
	private String getBlank() {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		htmlBuilder.imgStart().src(imagePath + "/Blank.gif").align("absmiddle").tagClose().imgEnd();
		
		return htmlBuilder.toString();
	}
}
