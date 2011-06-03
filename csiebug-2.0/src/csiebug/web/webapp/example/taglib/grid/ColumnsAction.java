package csiebug.web.webapp.example.taglib.grid;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import csiebug.service.ServiceException;
import csiebug.util.AssertUtility;
import csiebug.web.webapp.BasicAction;

public class ColumnsAction extends BasicAction {
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
				setRequestAttribute("FunctionName", getMessage("taglibdemo.ColumnsDoc.FunctionName"));
			}
		}
		
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("field1", "10");
		map.put("field2", "200");
		map.put("field3", "3000");
		
		list.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("field1", "400");
		map.put("field2", "50");
		map.put("field3", "600");
		
		list.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("field1", "7000");
		map.put("field2", "800");
		map.put("field3", "90");
		
		list.add(map);
		
		setRequestAttribute("gridData", list);
		
		List<Map<String, String>> list2 = new ArrayList<Map<String,String>>();
		
		map = new LinkedHashMap<String, String>();
		map.put("fieldname", "field2");
		map.put("dataType", "number");
		map.put("sortable", "true");
		map.put("title", "title2");
		
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("fieldname", "field3");
		map.put("dataType", "currency");
		map.put("title", "title3");
		
		list2.add(map);
		
		setRequestAttribute("columns", list2);
	}
	
	//畫面控制項函數區結束
	
	//邏輯函數區
	//頁面動作所對應的處理method,business logic,請歸類到下面
	
	//邏輯函數區結束
}
