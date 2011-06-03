package csiebug.web.webapp.example.taglib.chart;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import csiebug.service.ServiceException;
import csiebug.util.AssertUtility;
import csiebug.web.webapp.BasicAction;

public class RaphaelJSChartAction extends BasicAction {
	private static final long serialVersionUID = 1L;

	public String main() throws Exception {
		//以下的if else處理各樣頁面的動作,可自行增加或修改
//		if(getActFlag().equalsIgnoreCase("add")) {	//增
//		
//		} else if(getActFlag().equalsIgnoreCase("delete")) {	//刪
//		
//		} else if(getActFlag().equalsIgnoreCase("save")) {	//存
//		
//		} else if(getActFlag().equalsIgnoreCase("query")) {	//查
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
			}
		}
		
		makePieChartData();
		makeBarChartData();
		makeLineChartData();
	}
	
	private void makePieChartData() {
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("amount", "50");
		map.put("legend", "%%.%% Enterprise Users");
		map.put("href", "http://localhost:8080/csiebug-2.0");
		data.add(map);
		
		map = new HashMap<String, String>();
		map.put("amount", "20");
		map.put("legend", "IE Users");
		map.put("href", "http://www.kimo.com.tw");
		data.add(map);
		
		map = new HashMap<String, String>();
		map.put("amount", "13");
		data.add(map);
		
		map = new HashMap<String, String>();
		map.put("amount", "32");
		data.add(map);
		
		map = new HashMap<String, String>();
		map.put("amount", "5");
		data.add(map);
		
		map = new HashMap<String, String>();
		map.put("amount", "1");
		data.add(map);
		
		map = new HashMap<String, String>();
		map.put("amount", "2");
		data.add(map);
		
		map = new HashMap<String, String>();
		map.put("amount", "10");
		data.add(map);
		
		setRequestAttribute("pie", data);
	}
	
	private void makeBarChartData() {
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "55");
		map.put("2", "20");
		map.put("3", "13");
		map.put("4", "32");
		map.put("5", "5");
		map.put("6", "1");
		map.put("7", "2");
		map.put("8", "10");
		data.add(map);
		
		map = new HashMap<String, String>();
		map.put("1", "10");
		map.put("2", "2");
		map.put("3", "1");
		map.put("4", "5");
		map.put("5", "32");
		map.put("6", "13");
		map.put("7", "20");
		map.put("8", "55");
		data.add(map);
		
		map = new HashMap<String, String>();
		map.put("1", "12");
		map.put("2", "20");
		map.put("3", "30");
		data.add(map);
		
		setRequestAttribute("bar", data);
	}
	
	private void makeLineChartData() {
		Map<String, List<Map<String, String>>> data = new LinkedHashMap<String, List<Map<String,String>>>();
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("1", "1");
		map.put("2", "2");
		map.put("3", "3");
		map.put("4", "4");
		map.put("5", "5");
		map.put("6", "6");
		map.put("7", "7");
		list.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("1", "3.5");
		map.put("2", "4.5");
		map.put("3", "5.5");
		map.put("4", "6.5");
		map.put("5", "7");
		map.put("6", "8");
		list.add(map);
		data.put("x", list);
		
		list = new ArrayList<Map<String,String>>();
		map = new LinkedHashMap<String, String>();
		map.put("1", "12");
		map.put("2", "32");
		map.put("3", "23");
		map.put("4", "15");
		map.put("5", "17");
		map.put("6", "27");
		map.put("7", "22");
		list.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("1", "10");
		map.put("2", "20");
		map.put("3", "30");
		map.put("4", "25");
		map.put("5", "15");
		map.put("6", "28");
		list.add(map);
		data.put("y", list);
		
		setRequestAttribute("line", data);
	}
	
	//畫面控制項函數區結束
	
	//邏輯函數區
	//頁面動作所對應的處理method,business logic,請歸類到下面
	
	//邏輯函數區結束
}
