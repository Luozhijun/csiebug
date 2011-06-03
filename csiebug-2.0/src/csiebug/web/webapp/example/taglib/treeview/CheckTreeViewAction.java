package csiebug.web.webapp.example.taglib.treeview;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import csiebug.service.ServiceException;
import csiebug.util.AssertUtility;
import csiebug.web.webapp.BasicAction;

public class CheckTreeViewAction extends BasicAction {
	private static final long serialVersionUID = 1L;

	public String main() throws Exception {
		//以下的if else處理各樣頁面的動作,可自行增加或修改
		if(getActFlag().startsWith("checkTreeView")) {
			StringBuffer script = new StringBuffer();
			script.append("document.getElementById(\"" + getActFlag() + "_legend\").click();\n");
			script.append("document.getElementById(\"" + getActFlag() + "Button\").focus();\n");
			addPageLoadScript(script.toString());
		}
		//頁面動作處理結束
		
		//頁面控制項需要的資料
		makeControl();
		
		//若沒有導到特定處理頁面,則預設都導到formLoad頁
		return FORMLOAD;
	}
	
	//畫面控制項函數區
	private void makeControl() throws UnsupportedEncodingException, NamingException {
		//畫面控制項
		try {
			setRequestAttribute("FunctionName", getFunctionName());
		} catch(ServiceException se) {
			writeErrorLog(se);
			
			if(AssertUtility.isNotNullAndNotSpace(getFunctionId())) {
				setRequestAttribute("FunctionName", getFunctionId());
			} else {
				setRequestAttribute("FunctionName", getMessage("taglibdemo.CheckTreeViewDoc.FunctionName"));
			}
		}
		
		makeTree(1);
		makeTree(2);
		makeTree(3);
		makeTree(4);
		makeTree(5);
		makeTree(6);
		
		setRequestAttribute("checkTreeViewValue6", "tab6 - Tab;chart6 - Chart");
	}
	
	private void makeTree(int index) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		makeFormFolder(list, index);
		makeGridFolder(list, index);
		makeTreeViewFolder(list, index);
		makeTabFolder(list, index);
		makeProgressBarFolder(list, index);
		makeChartFolder(list, index);
		
		setRequestAttribute("checkTreeView" + index, list);
	}
	
	private void makeDoc(List<Map<String, String>> list, String parentId, String id, String name, String url, String order) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("ParentId", parentId);
		map.put("Id", id);
		map.put("Name", name);
		map.put("SortOrder", order);
		map.put("ImageName", "");
		list.add(map);
	}
	
	private void makeFormFolder(List<Map<String, String>> list, int index) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("ParentId", "");
		map.put("Id", "form" + index);
		map.put("Name", "Form");
		map.put("SortOrder", "1");
		map.put("ImageName", "");
		list.add(map);
		
		makeFormDocList(list, index);
	}
	
	private void makeFormDocList(List<Map<String, String>> list, int index) {
		makeDoc(list, "form" + index, "text" + index, "Text", "", "1");
		makeDoc(list, "form" + index, "textArea" + index, "Text Area", "textAreaAction", "2");
		makeDoc(list, "form" + index, "textInterval" + index, "Text Interval", "textIntervalAction", "3");
		makeDoc(list, "form" + index, "select" + index, "Select", "selectAction", "4");
		makeDoc(list, "form" + index, "multiSelect" + index, "Multi Select", "multiSelectAction", "5");
		makeDoc(list, "form" + index, "editableSelect" + index, "Editable Select", "editableSelectAction", "6");
		makeDoc(list, "form" + index, "radio" + index, "Radio", "radioAction", "7");
		makeDoc(list, "form" + index, "radioGroup" + index, "Radio Group", "radioGroupAction", "8");
		makeDoc(list, "form" + index, "checkbox" + index, "Checkbox", "checkboxAction", "9");
	}
	
	private void makeGridFolder(List<Map<String, String>> list, int index) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("ParentId", "");
		map.put("Id", "grid" + index);
		map.put("Name", "Grid");
		map.put("SortOrder", "2");
		map.put("ImageName", "");
		list.add(map);
		
		makeGridDocList(list, index);
	}
	
	private void makeGridDocList(List<Map<String, String>> list, int index) {
		makeDoc(list, "grid" + index, "table" + index, "Table", "tableAction", "1");
		makeDoc(list, "grid" + index, "row" + index, "Row", "rowAction", "2");
		makeDoc(list, "grid" + index, "column" + index, "Column", "columnAction", "3");
		makeDoc(list, "grid" + index, "columns" + index, "Columns", "columnsAction", "4");
	}
	
	private void makeTreeViewFolder(List<Map<String, String>> list, int index) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("ParentId", "");
		map.put("Id", "treeViewFolder" + index);
		map.put("Name", "Tree View");
		map.put("SortOrder", "3");
		map.put("ImageName", "");
		list.add(map);
		
		makeTreeViewDocList(list, index);
	}
	
	private void makeTreeViewDocList(List<Map<String, String>> list, int index) {
		makeDoc(list, "treeViewFolder" + index, "treeView" + index, "Tree View", "treeViewAction", "1");
		makeDoc(list, "treeViewFolder" + index, "checkTreeView" + index, "Check Tree View", "checkTreeViewAreaAction", "2");
		makeDoc(list, "text" + index, "menu" + index, "Menu", "menuAction", "3");
	}
	
	private void makeTabFolder(List<Map<String, String>> list, int index) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("ParentId", "");
		map.put("Id", "tabFolder" + index);
		map.put("Name", "Tab");
		map.put("SortOrder", "4");
		map.put("ImageName", "");
		list.add(map);
		
		makeTabDocList(list, index);
	}
	
	private void makeTabDocList(List<Map<String, String>> list, int index) {
		makeDoc(list, "tabFolder" + index, "tab" + index, "Tab", "tabAction", "1");
	}
	
	private void makeProgressBarFolder(List<Map<String, String>> list, int index) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("ParentId", "");
		map.put("Id", "progressBarFolder" + index);
		map.put("Name", "Progress Bar");
		map.put("SortOrder", "5");
		map.put("ImageName", "");
		list.add(map);
		
		makeProgressBarDocList(list, index);
	}
	
	private void makeProgressBarDocList(List<Map<String, String>> list, int index) {
		makeDoc(list, "progressBarFolder" + index, "progressBar" + index, "Progress Bar", "progressBarAction", "1");
	}
	
	private void makeChartFolder(List<Map<String, String>> list, int index) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("ParentId", "");
		map.put("Id", "chart" + index);
		map.put("Name", "Chart");
		map.put("SortOrder", "6");
		map.put("ImageName", "");
		list.add(map);
		
		makeChartDocList(list, index);
	}
	
	private void makeChartDocList(List<Map<String, String>> list, int index) {
		makeDoc(list, "chart" + index, "xmlSwfChart" + index, "XML-SWF Chart", "xmlSwfChartAction", "1");
	}
	//畫面控制項函數區結束
	
	//邏輯函數區
	//頁面動作所對應的處理method,business logic,請歸類到下面
	
	//邏輯函數區結束
}
