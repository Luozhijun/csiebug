package csiebug.web.webapp.example.taglib.timeline;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.naming.NamingException;

import csiebug.service.ServiceException;
import csiebug.util.AssertUtility;
import csiebug.util.DateFormatException;
import csiebug.util.DateFormatUtility;
import csiebug.web.webapp.BasicAction;

public class TimelineAction extends BasicAction {
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
	private void makeControl() throws UnsupportedEncodingException, NamingException, DateFormatException {
		//畫面控制項
		try {
			setRequestAttribute("FunctionName", getFunctionName());
		} catch(ServiceException se) {
			writeErrorLog(se);
			
			if(AssertUtility.isNotNullAndNotSpace(getFunctionId())) {
				setRequestAttribute("FunctionName", getFunctionId());
			} else {
				setRequestAttribute("FunctionName", getMessage("taglibdemo.TimelineDoc.FunctionName"));
			}
		}
		
		makeTimeline();
	}
	
	//畫面控制項函數區結束
	
	//邏輯函數區
	//頁面動作所對應的處理method,business logic,請歸類到下面
	private void makeTimeline() throws DateFormatException {
		Calendar date = Calendar.getInstance();
		String formatToday = DateFormatUtility.getDisplayDate(date.get(Calendar.YEAR), date.get(Calendar.MONTH) + 1, date.get(Calendar.DAY_OF_MONTH), 111);
		setRequestAttribute("today", formatToday);
		
		date.add(Calendar.DAY_OF_MONTH, 5);
		String formatAfterFiveDay = DateFormatUtility.getDisplayDate(date.get(Calendar.YEAR), date.get(Calendar.MONTH) + 1, date.get(Calendar.DAY_OF_MONTH), 111);
		setRequestAttribute("afterFiveDay", formatAfterFiveDay);
		
		ArrayList<Map<String, String>> list = new ArrayList<Map<String,String>>();
		
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("start", formatToday + " 00:00:00 GMT");
		map.put("title", "test title");
		list.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("start", formatToday + " 00:00:00 GMT");
		map.put("end", formatAfterFiveDay + " 00:00:00 GMT");
		map.put("title", "test title2");
		list.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("start", formatToday + " 00:00:00 GMT");
		map.put("end", formatAfterFiveDay + " 00:00:00 GMT");
		map.put("isDuration", "true");
		map.put("title", "test title3");
		list.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("start", formatToday + " 00:00:00 GMT");
		map.put("link", "http://localhost:8080/csiebug-2.0/example/timeline");
		map.put("title", "test title4");
		list.add(map);
		
		setRequestAttribute("testTimelineData", list);
	}
	
	//邏輯函數區結束
}
