package csiebug.web.html.treeview;

import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import csiebug.util.AssertUtility;
import csiebug.util.ListUtility;
import csiebug.util.StringUtility;
import csiebug.util.WebUtility;
import csiebug.web.html.HtmlBuilder;
import csiebug.web.html.HtmlRenderException;
import csiebug.web.html.form.HtmlCheckBox;


/**
 * 產生HTML CheckTreeView
 * @author George_Tsai
 * @version 2009/7/1
 */

public class HtmlCheckTreeView {
	private List<Map<String, String>> listTreeView;
	private String htmlId;
	private String selectedValue;
	private String imagePath;
	private String op = "multi";
	private boolean linked = true;
	
	private static final int FirstNode = 1;
	private static final int LastNode = 2;
	private static final int MiddleNode = 3;
	private static final int OnlyOneNode = 4;
	
	/**
	 * TreeView建構子
	 * @param id
	 * @param 已經被選取的節點
	 * @param 由LinkedHashMap(ParentId, Id, Name, SortOrder)組成的ArrayList
	 * @author George_Tsai
	 * @version 2008/5/20
	 * @throws NamingException 
	 */
	public HtmlCheckTreeView(String id, String value, List<Map<String, String>> list) throws NamingException {
		htmlId = id;
		selectedValue = value;
		listTreeView = list;
		
		WebUtility webutil = new WebUtility();
		imagePath = webutil.getBasePathForHTML() + "images";
	}
	
	/**
	 * TreeView建構子
	 * @param id
	 * @param 已經被選取的節點
	 * @param 由LinkedHashMap(ParentId, Id, Name, SortOrder)組成的ArrayList
	 * @param css的位置
	 * @param 圖檔的位置
	 * @param js的位置
	 * @author George_Tsai
	 * @version 2009/1/19
	 * @throws NamingException 
	 */
	public HtmlCheckTreeView(String id, String value, List<Map<String, String>> list, String imagePath, String op, boolean linked) throws NamingException {
		this.htmlId = id;
		this.selectedValue = value;
		this.listTreeView = list;
		
		WebUtility webutil = new WebUtility();
		if(imagePath != null) {
			this.imagePath = StringUtility.removeStartEndSlash(imagePath);
		} else {
			this.imagePath = webutil.getBasePathForHTML() + "images";
		}
		
		if(op != null && op.trim().equalsIgnoreCase("radio")) {
			this.op = "radio";
		}
		
		this.linked = linked;
	}
	
	/**
	 * 取得TreeView的HTML碼
	 * @return TreeView的HTML碼
	 * @author George_Tsai
	 * @version 2009/1/30
	 * @throws NamingException 
	 * @throws HtmlRenderException 
	 */
	public String getTreeView() throws NamingException, HtmlRenderException {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		htmlBuilder.inputStart().type("hidden").id(htmlId).name(htmlId).value(selectedValue).tagClose();
		
		htmlBuilder.divStart().style("font-size:9pt;padding:0;margin:0;border:none;text-align:left;line-height:0;top:0;bottom:0").tagClose().appendString("\n");
		
		List<Map<String, String>> rootList = ListUtility.getSubListByName(listTreeView, "ParentId", "");
		rootList = (List<Map<String, String>>)ListUtility.sortByName(rootList, "SortOrder");
		
		for(int i = 0; i < rootList.size(); i++) {
			Map<String, String> treeNode = (Map<String, String>)rootList.get(i);
			String strId = treeNode.get("Id").toString();
			String strName = treeNode.get("Name").toString();
			int[] intTabBlank = new int[0];
			
			int dirFlag = ListUtility.getSubListByName(listTreeView, "ParentId", strId).size();
			
			boolean checked = false;
			
			if(dirFlag > 0) { //目錄
				makeFolderWithNodeType(htmlBuilder, i, rootList, "", "", strId, strName, 0, intTabBlank, true, checked);
			} else { //程式頁面
				makeFunctionWithNodeType(htmlBuilder, i, rootList, "", "", strId, strName, true, checked);
			}
		}
		
		htmlBuilder.divEnd().appendString("\n");
		
		return htmlBuilder.toString();
	}
	
	private void makeFolderWithNodeType(HtmlBuilder htmlBuilder, int i, List<Map<String, String>> list, String strId, String strName, String strChildId, String strChildName, int intTabCnt, int[] intTabBlank, boolean rootFlag, boolean parentChecked) throws NamingException, HtmlRenderException {
		AssertUtility.notNull(htmlBuilder);
		AssertUtility.notNull(list);
		AssertUtility.notNull(strId);
		AssertUtility.notNull(strName);
		AssertUtility.notNull(strChildId);
		AssertUtility.notNull(strChildName);
		AssertUtility.notNull(intTabBlank);
		
		boolean checked = (op.equals("multi") && linked && ((AssertUtility.isNotNullAndNotSpace(selectedValue) && selectedValue.indexOf(strChildId + " - " + strChildName) != -1) || parentChecked));
		if(rootFlag && list.size() == 1) {
			htmlBuilder.appendString(getSubTreeView(strId, strName, strChildId, strChildName, intTabCnt, OnlyOneNode, intTabBlank, checked) + "\n");
		} else {
			if(rootFlag && i == 0) { //如果第一個是目錄
				htmlBuilder.appendString(getSubTreeView(strId, strName, strChildId, strChildName, intTabCnt, FirstNode, intTabBlank, checked) + "\n");
			} else if(i == list.size() - 1) { //如果最後一個是目錄
				htmlBuilder.appendString(getSubTreeView(strId, strName, strChildId, strChildName, intTabCnt, LastNode, intTabBlank, checked) + "\n");
			} else { //中間的目錄
				htmlBuilder.appendString(getSubTreeView(strId, strName, strChildId, strChildName, intTabCnt, MiddleNode, intTabBlank, checked) + "\n");
			}
		}
	}
	
	private void makeFunctionWithNodeType(HtmlBuilder htmlBuilder, int i, List<Map<String, String>> list, String strId, String strName, String strChildId, String strChildName, boolean rootFlag, boolean parentChecked) throws NamingException, HtmlRenderException {
		AssertUtility.notNull(htmlBuilder);
		AssertUtility.notNull(list);
		AssertUtility.notNull(strId);
		AssertUtility.notNull(strName);
		AssertUtility.notNull(strChildId);
		AssertUtility.notNull(strChildName);
		
		boolean checked = (op.equals("multi") && linked && ((AssertUtility.isNotNullAndNotSpace(selectedValue) && selectedValue.indexOf(strChildId + " - " + strChildName) != -1) || parentChecked));
		if(rootFlag && list.size() == 1) {
			htmlBuilder.appendString(getTreeLeaf(strId, strName, strChildId, strChildName, OnlyOneNode, checked) + "\n");
		} else {
			if(rootFlag && i == 0) { //如果第一個是程式
				htmlBuilder.appendString(getTreeLeaf(strId, strName, strChildId, strChildName, FirstNode, checked) + "\n");
			} else if(i == list.size() - 1) { //如果最後一個是程式
				htmlBuilder.appendString(getTreeLeaf(strId, strName, strChildId, strChildName, LastNode, checked) + "\n");
			} else { //中間的程式
				htmlBuilder.appendString(getTreeLeaf(strId, strName, strChildId, strChildName, MiddleNode, checked) + "\n");
			}
		}
	}
	
	/**
	 * 取得leaf node
	 * @param parentId
	 * @param id
	 * @param 顯示名稱
	 * @param 節點型別
	 * @return HTML碼
	 * @author George_Tsai
	 * @version 2009/7/1
	 * @throws NamingException 
	 * @throws HtmlRenderException 
	 */
	private String getTreeLeaf(String strParentId, String strParentName, String strId, String strName, int intLeafType, boolean parentChecked) throws NamingException, HtmlRenderException {
		AssertUtility.notNull(strParentId);
		AssertUtility.notNull(strParentName);
		AssertUtility.notNull(strId);
		AssertUtility.notNull(strName);
		
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		htmlBuilder.spanStart().style("line-height:normal; white-space:nowrap").tagClose().appendString("\n");
		
		makeLeafPic(htmlBuilder, intLeafType);
		
		boolean checked = (op.equals("multi") && linked && ((AssertUtility.isNotNullAndNotSpace(selectedValue) && selectedValue.indexOf(strId + " - " + strName) != -1) || parentChecked));
		
		makeCheckboxForNode(htmlBuilder, strId, strName, strParentId, strParentName, checked);
		
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
	 * @throws NamingException 
	 * @throws HtmlRenderException 
	 */
	private String getSubTreeView(String strParentId, String strParentName, String strId, String strName, int intTabCnt, int intFolderType, int[] intTabBlank, boolean parentChecked) throws NamingException, HtmlRenderException {
		AssertUtility.notNull(strParentId);
		AssertUtility.notNull(strParentName);
		AssertUtility.notNull(strId);
		AssertUtility.notNull(strName);
		AssertUtility.notNull(intTabBlank);
		
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		htmlBuilder.spanStart().style("line-height:normal; white-space:nowrap").tagClose().appendString("\n");
		
		htmlBuilder.appendString(getTab(intTabCnt, intTabBlank));
		
		boolean checked = (op.equals("multi") && linked && ((AssertUtility.isNotNullAndNotSpace(selectedValue) && selectedValue.indexOf(strId + " - " + strName) != -1) || parentChecked));
		
		makeFolderPic(htmlBuilder, intFolderType, strId, checked);
		
		makeCheckboxForFolder(htmlBuilder, strId, strName, strParentId, strParentName, checked);
		
		//對應的div(子節點)
		htmlBuilder.divStart().id(strId).name(strId);
		
		//有勾取的就展開子樹
		if(checked) {
			htmlBuilder.style("line-height:0");
		} else {
			htmlBuilder.style("display:none;line-height:0");
		}
		
		htmlBuilder.tagClose().appendString("\n");
		
		List<Map<String, String>> subList = ListUtility.getSubListByName(listTreeView, "ParentId", strId);
		subList = ListUtility.sortByName(subList, "SortOrder");
		
		for(int i = 0; i < subList.size(); i++) {
			Map<String, String> treeNode = subList.get(i);
			String strChildId = treeNode.get("Id");
			String strChildName = treeNode.get("Name");
			int[] intTabBlank2;
			
			if(intFolderType == LastNode) { //如果本目錄是本層的最後一個目錄,需記錄下來,本目錄的子節點縮排時才可以產生空白
				intTabBlank2 = new int[intTabBlank.length + 1];
				intTabBlank2[intTabBlank2.length - 1] = intTabCnt;
			} else {
				intTabBlank2 = intTabBlank;
			}
			
			int dirFlag = ListUtility.getSubListByName(listTreeView, "ParentId", strChildId).size();
			
			if(dirFlag > 0) { //目錄
				makeFolderWithNodeType(htmlBuilder, i, subList, strId, strName, strChildId, strChildName, intTabCnt + 1, intTabBlank2, false, checked);
			} else { //程式頁面
				htmlBuilder.appendString(getTab(intTabCnt + 1, intTabBlank2));
				makeFunctionWithNodeType(htmlBuilder, i, subList, strId, strName, strChildId, strChildName, false, checked);
			}
		}
		
		htmlBuilder.divEnd().appendString("\n");
		htmlBuilder.spanEnd().br().appendString("\n");
		
		return htmlBuilder.toString();
	}
	
	/**
	 * 產生folder圖檔
	 * @param htmlBuilder
	 * @param intFolderType
	 * @param strId
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/7/29
	 */
	private void makeFolderPic(HtmlBuilder htmlBuilder, int intFolderType, String strId, boolean parentChecked) {
		AssertUtility.notNull(htmlBuilder);
		AssertUtility.notNull(strId);
		
		String imgSuffix;
		if(parentChecked) {
			imgSuffix = "minus";
		} else {
			imgSuffix = "plus";
		}
		
		if(intFolderType == FirstNode) { // 第一個folder對應的圖檔
			htmlBuilder.imgStart().id(strId + "_line").name(strId + "_line").src(imagePath + "/ET" + imgSuffix + ".gif").style("CURSOR:pointer").onClick("showSubCheckListTree('" + strId + "', '" + imagePath + "')").align("absmiddle").tagClose().imgEnd().appendString("\n");
		} else if(intFolderType == LastNode) { //最後一個folder對應的圖檔
			htmlBuilder.imgStart().id(strId + "_line").name(strId + "_line").src(imagePath + "/L" + imgSuffix + ".gif").style("CURSOR:pointer").onClick("showSubCheckListTree('" + strId + "', '" + imagePath + "')").align("absmiddle").tagClose().imgEnd().appendString("\n");
		} else if(intFolderType == MiddleNode){ //中間其他的folder對應的圖檔
			htmlBuilder.imgStart().id(strId + "_line").name(strId + "_line").src(imagePath + "/E" + imgSuffix + ".gif").style("CURSOR:pointer").onClick("showSubCheckListTree('" + strId + "', '" + imagePath + "')").align("absmiddle").tagClose().imgEnd().appendString("\n");
		} else {
			htmlBuilder.imgStart().id(strId + "_line").name(strId + "_line").src(imagePath + "/LT" + imgSuffix + ".gif").style("CURSOR:pointer").onClick("showSubCheckListTree('" + strId + "', '" + imagePath + "')").align("absmiddle").tagClose().imgEnd().appendString("\n");
		}
	}
	
	private void makeLeafPic(HtmlBuilder htmlBuilder, int intLeafType) {
		AssertUtility.notNull(htmlBuilder);
		
		if(intLeafType == FirstNode) { //第一個node
			htmlBuilder.imgStart().src(imagePath + "/ET.gif").align("absmiddle").tagClose().imgEnd().appendString("\n");
		} else if(intLeafType == LastNode) { //最後一個node
			htmlBuilder.imgStart().src(imagePath + "/L.gif").align("absmiddle").tagClose().imgEnd().appendString("\n");
		} else if(intLeafType == MiddleNode){ //中間的node
			htmlBuilder.imgStart().src(imagePath + "/E.gif").align("absmiddle").tagClose().imgEnd().appendString("\n");
		} else {
			htmlBuilder.imgStart().src(imagePath + "/LT.gif").align("absmiddle").tagClose().imgEnd().appendString("\n");
		}
	}
	
	private void makeCheckboxForFolder(HtmlBuilder htmlBuilder, String strId, String strName, String strParentId, String strParentName, boolean parentChecked) throws NamingException, HtmlRenderException {
		AssertUtility.notNull(htmlBuilder);
		AssertUtility.notNull(strId);
		AssertUtility.notNull(strName);
		AssertUtility.notNull(strParentId);
		AssertUtility.notNull(strParentName);
		
		HtmlCheckBox checkbox = new HtmlCheckBox();
		checkbox.setId(strId + "_checkbox");
		checkbox.setName(strId + "_checkbox");
		checkbox.setDisplayName(strName);
		checkbox.setIsReturnValue("false");
		
		if(op.equals("multi")) {
			if(linked) {
				checkbox.setOnClick("selectNodeAndSubNodes('" + strId + "', '" + strName + "', '" + strParentId + "', '" + strParentName + "', '" + htmlId + "');");
			} else {
				checkbox.setOnClick("selectNode('" + strId + "', '" + strName + "', '" + strParentId + "', '" + strParentName + "', '" + htmlId + "', false);");
			}
		} else {
			checkbox.setOnClick("selectNodeForRadio('" + strId + "', '" + strName + "', '" + htmlId + "');");
		}
		
		boolean checked = (op.equals("multi") && linked && ((AssertUtility.isNotNullAndNotSpace(selectedValue) && selectedValue.indexOf(strId + " - " + strName) != -1) || parentChecked));
		if(checked) {
			checkbox.setChecked("true");
		}
		
		checkbox.setTypesetting("false");
		
		htmlBuilder.appendString(checkbox.render());
	}
	
	private void makeCheckboxForNode(HtmlBuilder htmlBuilder, String strId, String strName, String strParentId, String strParentName, boolean parentChecked) throws NamingException, HtmlRenderException {
		AssertUtility.notNull(htmlBuilder);
		AssertUtility.notNull(strId);
		AssertUtility.notNull(strName);
		AssertUtility.notNull(strParentId);
		AssertUtility.notNull(strParentName);
		
		HtmlCheckBox checkbox = new HtmlCheckBox();
		checkbox.setId(strId + "_checkbox");
		checkbox.setName(strId + "_checkbox");
		checkbox.setDisplayName(strName);
		checkbox.setIsReturnValue("false");
		
		if(op.equals("multi")) {
			checkbox.setOnClick("selectNode('" + strId + "', '" + strName + "', '" + strParentId + "', '" + strParentName + "', '" + htmlId + "', " + Boolean.toString(linked) + ");");
		} else {
			checkbox.setOnClick("selectNodeForRadio('" + strId + "', '" + strName + "', '" + htmlId + "');");
		}
		
		boolean checked = (op.equals("multi") && linked && ((AssertUtility.isNotNullAndNotSpace(selectedValue) && selectedValue.indexOf(strId + " - " + strName) != -1) || parentChecked));
		if(checked) {
			checkbox.setChecked("true");
		}
		
		checkbox.setTypesetting("false");
		
		htmlBuilder.appendString(checkbox.render());
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
