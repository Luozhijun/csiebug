package csiebug.web.webapp.example;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import csiebug.service.ServiceException;
import csiebug.util.AssertUtility;
import csiebug.web.webapp.BasicAction;

public class ExampleDemoAction extends BasicAction {
	private static final long serialVersionUID = 1L;

	public String main() throws Exception {
		//以下的if else處理各樣頁面的動作,可自行增加或修改
//		if(getActFlag().equalsIgnoreCase("add")) {	//�?
//			
//		} else if(getActFlag().equalsIgnoreCase("delete")) {	//??
//			
//		} else if(getActFlag().equalsIgnoreCase("save")) {	//�?
//			
//		} else if(getActFlag().equalsIgnoreCase("query")) {	//??
//			
//		}
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
				setRequestAttribute("FunctionName", "");
			}
		}
		
		makeTree();
	}
	
	private void makeTree() throws NamingException {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		makeJavaFolder(list);
		makeTaglibFolder(list);
			
		setRequestAttribute("exampleDemo", list);
	}
	
	private void makeFunction(List<Map<String, String>> list, String parentId, String id, String name, String url, String order) throws NamingException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("ParentId", parentId);
		map.put("Id", id);
		map.put("Name", name);
		map.put("URL", getBasePathForHTML() + "example/" + url);
		map.put("SortOrder", order);
		list.add(map);
	}
	
	private void makeJavaFolder(List<Map<String, String>> list) throws NamingException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("ParentId", "");
		map.put("Id", "java");
		map.put("Name", "java");
		map.put("URL", "");
		map.put("SortOrder", "1");
		list.add(map);
		
		makeAuthenticationImageFolder(list);
		makeQRCodeImageFolder(list);
	}
	
	private void makeTaglibFolder(List<Map<String, String>> list) throws NamingException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("ParentId", "");
		map.put("Id", "taglib");
		map.put("Name", "taglib");
		map.put("URL", "");
		map.put("SortOrder", "2");
		list.add(map);
		
		makeFormFolder(list);
		makeGridFolder(list);
		makeExcelGridFolder(list);
		makeProjectGridFolder(list);
		makeTreeViewFolder(list);
		makeTabFolder(list);
		makeProgressBarFolder(list);
		makeChartFolder(list);
		makeTimelineFolder(list);
		makeMovingBoxFolder(list);
		makeCodeFolder(list);
		makeRSSFolder(list);
	}
	
	private void makeFormFolder(List<Map<String, String>> list) throws NamingException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("ParentId", "taglib");
		map.put("Id", "form");
		map.put("Name", "Form");
		map.put("URL", "");
		map.put("SortOrder", "1");
		list.add(map);
		
		makeFormList(list);
	}
	
	private void makeFormList(List<Map<String, String>> list) throws NamingException {
		makeFunction(list, "form", "text", "Text", "text", "1");
		makeFunction(list, "form", "textArea", "Text Area", "textArea", "2");
		makeFunction(list, "form", "textInterval", "Text Interval", "textInterval", "3");
		makeFunction(list, "form", "multiText", "Multi Text", "multiText", "4");
		makeFunction(list, "form", "editableLabel", "Editable Label", "editableLabel", "5");
		makeFunction(list, "form", "select", "Select", "select", "6");
		makeFunction(list, "form", "multiSelect", "Multi Select", "multiSelect", "7");
		makeFunction(list, "form", "editableSelect", "Editable Select", "editableSelect", "8");
		makeFunction(list, "form", "keyValue", "Key Value Select", "keyValue", "9");
		makeFunction(list, "form", "radio", "Radio", "radio", "10");
		makeFunction(list, "form", "radioGroup", "Radio Group", "radioGroup", "11");
		makeFunction(list, "form", "checkbox", "Checkbox", "checkbox", "12");
		makeFunction(list, "form", "uploadify", "Uploadify", "uploadify", "13");
	}
	
	private void makeGridFolder(List<Map<String, String>> list) throws NamingException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("ParentId", "taglib");
		map.put("Id", "grid");
		map.put("Name", "Grid");
		map.put("URL", "");
		map.put("SortOrder", "2");
		list.add(map);
		
		makeGridList(list);
	}
	
	private void makeGridList(List<Map<String, String>> list) throws NamingException {
		makeFunction(list, "grid", "table", "Table", "table", "1");
		makeFunction(list, "grid", "row", "Row", "row", "2");
		makeFunction(list, "grid", "column", "Column", "column", "3");
		makeFunction(list, "grid", "columns", "Columns", "columns", "4");
	}
	
	private void makeExcelGridFolder(List<Map<String, String>> list) throws NamingException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("ParentId", "taglib");
		map.put("Id", "excelGridFolder");
		map.put("Name", "Excel Grid");
		map.put("URL", "");
		map.put("SortOrder", "3");
		list.add(map);
		
		makeExcelGridList(list);
	}
	
	private void makeExcelGridList(List<Map<String, String>> list) throws NamingException {
		makeFunction(list, "excelGridFolder", "excelGrid", "Excel Grid", "excelGrid", "1");
	}
	
	private void makeProjectGridFolder(List<Map<String, String>> list) throws NamingException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("ParentId", "taglib");
		map.put("Id", "projectGridFolder");
		map.put("Name", "Project Grid");
		map.put("URL", "");
		map.put("SortOrder", "4");
		list.add(map);
		
		makeProjectGridList(list);
	}
	
	private void makeProjectGridList(List<Map<String, String>> list) throws NamingException {
		makeFunction(list, "projectGridFolder", "projectGrid", "Project Grid", "projectGrid", "1");
	}
	
	private void makeTreeViewFolder(List<Map<String, String>> list) throws NamingException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("ParentId", "taglib");
		map.put("Id", "treeViewFolder");
		map.put("Name", "Tree View");
		map.put("URL", "");
		map.put("SortOrder", "5");
		list.add(map);
		
		makeTreeViewList(list);
	}
	
	private void makeTreeViewList(List<Map<String, String>> list) throws NamingException {
		makeFunction(list, "treeViewFolder", "treeView", "Tree View", "treeView", "1");
		makeFunction(list, "treeViewFolder", "checkTreeView", "Check Tree View", "checkTreeView", "2");
		makeFunction(list, "treeViewFolder", "menu", "Menu", "menu", "3");
		makeFunction(list, "treeViewFolder", "sidebar", "Sidebar", "sidebar", "4");
	}
	
	private void makeTabFolder(List<Map<String, String>> list) throws NamingException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("ParentId", "taglib");
		map.put("Id", "tabFolder");
		map.put("Name", "Tab");
		map.put("URL", "");
		map.put("SortOrder", "6");
		list.add(map);
		
		makeTabList(list);
	}
	
	private void makeTabList(List<Map<String, String>> list) throws NamingException {
		makeFunction(list, "tabFolder", "tab", "Tab", "tab", "1");
	}
	
	private void makeProgressBarFolder(List<Map<String, String>> list) throws NamingException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("ParentId", "taglib");
		map.put("Id", "progressBarFolder");
		map.put("Name", "Progress Bar");
		map.put("URL", "");
		map.put("SortOrder", "7");
		list.add(map);
		
		makeProgressBarList(list);
	}
	
	private void makeProgressBarList(List<Map<String, String>> list) throws NamingException {
		makeFunction(list, "progressBarFolder", "progressBar", "Progress Bar", "progressBar", "1");
		makeFunction(list, "progressBarFolder", "jQueryProgressBar", "JQuery Progress Bar", "jQueryProgressBar", "2");
	}
	
	private void makeChartFolder(List<Map<String, String>> list) throws NamingException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("ParentId", "taglib");
		map.put("Id", "chart");
		map.put("Name", "Chart");
		map.put("URL", "");
		map.put("SortOrder", "8");
		list.add(map);
		
		makeChartList(list);
	}
	
	private void makeChartList(List<Map<String, String>> list) throws NamingException {
		makeFunction(list, "chart", "xmlSwfChart", "XML-SWF Chart", "xmlSwfChart", "1");
		makeFunction(list, "chart", "raphaelJSChart", "Raphael JS Chart", "raphaelJSChart", "2");
		makeFunction(list, "chart", "jqPlotChart", "jqPlot Chart", "jqPlotChart", "3");
	}
	
	private void makeTimelineFolder(List<Map<String, String>> list) throws NamingException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("ParentId", "taglib");
		map.put("Id", "timelineFolder");
		map.put("Name", "Timeline");
		map.put("URL", "");
		map.put("SortOrder", "9");
		list.add(map);
		
		makeTimelineList(list);
	}
	
	private void makeTimelineList(List<Map<String, String>> list) throws NamingException {
		makeFunction(list, "timelineFolder", "timeline", "Timeline", "timeline", "1");
	}
	
	private void makeMovingBoxFolder(List<Map<String, String>> list) throws NamingException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("ParentId", "taglib");
		map.put("Id", "movingBoxFolder");
		map.put("Name", "Moving Box");
		map.put("URL", "");
		map.put("SortOrder", "10");
		list.add(map);
		
		makeMovingBoxList(list);
	}
	
	private void makeMovingBoxList(List<Map<String, String>> list) throws NamingException {
		makeFunction(list, "movingBoxFolder", "movingBox", "Moving Box", "movingBox", "1");
	}
	
	private void makeCodeFolder(List<Map<String, String>> list) throws NamingException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("ParentId", "taglib");
		map.put("Id", "codeFolder");
		map.put("Name", "Code");
		map.put("URL", "");
		map.put("SortOrder", "11");
		list.add(map);
		
		makeCodeList(list);
	}
	
	private void makeCodeList(List<Map<String, String>> list) throws NamingException {
		makeFunction(list, "codeFolder", "code", "Code", "code", "1");
	}
	
	private void makeRSSFolder(List<Map<String, String>> list) throws NamingException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("ParentId", "taglib");
		map.put("Id", "rssFolder");
		map.put("Name", "RSS");
		map.put("URL", "");
		map.put("SortOrder", "12");
		list.add(map);
		
		makeRSSList(list);
	}
	
	private void makeRSSList(List<Map<String, String>> list) throws NamingException {
		makeFunction(list, "rssFolder", "rssFeed", "RSS Feed", "rssFeed", "1");
	}
	
	private void makeAuthenticationImageFolder(List<Map<String, String>> list) throws NamingException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("ParentId", "java");
		map.put("Id", "authenticationFolder");
		map.put("Name", "Authentication");
		map.put("URL", "");
		map.put("SortOrder", "13");
		list.add(map);
		
		makeAuthenticationImageList(list);
	}
	
	private void makeAuthenticationImageList(List<Map<String, String>> list) throws NamingException {
		makeFunction(list, "authenticationFolder", "authenticationImage", "Authentication Image", "authenticationImage", "1");
	}
	
	private void makeQRCodeImageFolder(List<Map<String, String>> list) throws NamingException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("ParentId", "java");
		map.put("Id", "qrCodeImageFolder");
		map.put("Name", "QR Code Image");
		map.put("URL", "");
		map.put("SortOrder", "14");
		list.add(map);
		
		makeQRCodeImageList(list);
	}
	
	private void makeQRCodeImageList(List<Map<String, String>> list) throws NamingException {
		makeFunction(list, "qrCodeImageFolder", "qrCodeImage", "QR Code Image", "qrCodeImage", "1");
	}
	
	//畫面控制項函數區結束
	
	//邏輯函數區
	//頁面動作所對應的處理method,business logic,請歸類到下面
	
	//邏輯函數區結束
}
