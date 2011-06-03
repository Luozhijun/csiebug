package csiebug.web.webapp.example.taglib.chart;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import csiebug.service.ServiceException;
import csiebug.util.AssertUtility;
import csiebug.web.webapp.BasicAction;

public class XmlSwfChartAction extends BasicAction {
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
		
		makeChartData();
		makePieChartData();
		makeFloatingChartData();
		makeCandlestickChartData();
		makeScatterChartData();
		makeBubbleChartData();
		makeChartDataWithNote();
		makeChartDataWithTooltip();
	}
	
	private void makeChartData() {
		List<List<Map<String, String>>> list = new ArrayList<List<Map<String,String>>>();
		
		List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "2005");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "2006");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "2007");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "2008");
		list2.add(map);
		
		list.add(list2);
		
		list2 = new ArrayList<Map<String, String>>();
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "Region A");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "5");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "10");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "30");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "63");
		list2.add(map);
		
		list.add(list2);
		
		list2 = new ArrayList<Map<String, String>>();
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "Region B");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "100");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "20");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "65");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "55");
		list2.add(map);
		
		list.add(list2);
		
		list2 = new ArrayList<Map<String, String>>();
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "Region C");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "56");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "21");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "5");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "90");
		list2.add(map);
		
		list.add(list2);
		
		setRequestAttribute("chartData", list);
	}
	
	/**
	 * In the case of pie charts, only one row of values is required, and all values must be positive
	 */
	private void makePieChartData() {
		List<List<Map<String, String>>> list = new ArrayList<List<Map<String,String>>>();
		
		List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "2005");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "2006");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "2007");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "2008");
		list2.add(map);
		
		list.add(list2);
		
		list2 = new ArrayList<Map<String, String>>();
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "Region A");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "5");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "10");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "30");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "63");
		list2.add(map);
		
		list.add(list2);
		
		setRequestAttribute("pieChartData", list);
	}
	
	/**
	 * In floating column and floating bar charts, the columns and bars aren't anchored to the zero line. They can start from any value. Each column or bar have two values. The low value determines where the column or bar starts. The high value determines where the column or bar ends.
	 * Floating charts require two rows of values that must be organized in this order: high, low.
	 */
	private void makeFloatingChartData() {
		List<List<Map<String, String>>> list = new ArrayList<List<Map<String,String>>>();
		
		List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "2006");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "2007");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "2008");
		list2.add(map);
		
		list.add(list2);
		
		list2 = new ArrayList<Map<String, String>>();
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "high");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "10");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "15");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "16");
		list2.add(map);
		
		list.add(list2);
		
		list2 = new ArrayList<Map<String, String>>();
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "low");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "1");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "5");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "3");
		list2.add(map);
		
		list.add(list2);
		
		setRequestAttribute("floatingChartData", list);
	}
	
	/**
	 * In candlestick charts, each candle represents a period of time during which a price fluctuates. The price the period starts with is the open price. The price the period ends with is the close price. The highest and lowest price during this period are the max and min. See chart_pref for more details.
	 * Candlestick charts require four rows of values that must be organized in this order: max, min, open, close.
	 */
	private void makeCandlestickChartData() {
		List<List<Map<String, String>>> list = new ArrayList<List<Map<String,String>>>();
		
		List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "mon");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "tue");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "wed");
		list2.add(map);
		
		list.add(list2);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "thu");
		list2.add(map);
		
		list.add(list2);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "fri");
		list2.add(map);
		
		list.add(list2);
		
		list2 = new ArrayList<Map<String, String>>();
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "max");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "4");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "5");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "6");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "8");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "10");
		list2.add(map);
		
		list.add(list2);
		
		list2 = new ArrayList<Map<String, String>>();
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "min");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "1");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "1");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "3");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", ".5");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "2");
		list2.add(map);
		
		list.add(list2);
		
		list2 = new ArrayList<Map<String, String>>();
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "open");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "3");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "4.5");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "5");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "6");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "5");
		list2.add(map);
		
		list.add(list2);
		
		list2 = new ArrayList<Map<String, String>>();
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "close");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "3.5");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "2");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "3");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "7");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "8");
		list2.add(map);
		
		list.add(list2);
		
		setRequestAttribute("candlestickChartData", list);
	}
	
	/**
	 * In scatter charts, the category axis is calculated just like the value axis. Scatter charts require a pair of x and y values for each data point. The x represents the horizontal position of the point, and the y represents the vertical position. The x and y values must be organized in column pairs like this:
	 */
	private void makeScatterChartData() {
		List<List<Map<String, String>>> list = new ArrayList<List<Map<String,String>>>();
		
		List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "");
		list2.add(map);
		
		for(int i = 0; i < 5; i++) {
			map = new LinkedHashMap<String, String>();
			map.put("DataType", "string");
			map.put("Value", "x");
			list2.add(map);
			
			map = new LinkedHashMap<String, String>();
			map.put("DataType", "string");
			map.put("Value", "y");
			list2.add(map);
		}
		
		list.add(list2);
		
		list2 = new ArrayList<Map<String, String>>();
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "region a");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", ".5");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "5");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "2");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "8");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "3");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "7");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "1.5");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "6.5");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "2.5");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "2.5");
		list2.add(map);
		
		list.add(list2);
		
		list2 = new ArrayList<Map<String, String>>();
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "region b");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "1");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "1");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "3");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "5");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "2");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "7");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "3.7");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "10.5");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "3.5");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "2.5");
		list2.add(map);
		
		list.add(list2);
		
		setRequestAttribute("scatterChartData", list);
	}
	
	/**
	 * In bubble charts, the category axis is calculated just like the value axis. Bubble charts require x, y, and z values for each bubble. The x represents the horizontal position of the bubble, y represents the vertical position, and z represents the diameter of the bubble in pixels (which may be scaled up or down in the XML source code). The x, y, and z values must be organized like this:
	 */
	private void makeBubbleChartData() {
		List<List<Map<String, String>>> list = new ArrayList<List<Map<String,String>>>();
		
		List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "");
		list2.add(map);
		
		for(int i = 0; i < 3; i++) {
			map = new LinkedHashMap<String, String>();
			map.put("DataType", "string");
			map.put("Value", "x");
			list2.add(map);
			
			map = new LinkedHashMap<String, String>();
			map.put("DataType", "string");
			map.put("Value", "y");
			list2.add(map);
			
			map = new LinkedHashMap<String, String>();
			map.put("DataType", "string");
			map.put("Value", "z");
			list2.add(map);
		}
		
		list.add(list2);
		
		list2 = new ArrayList<Map<String, String>>();
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "region a");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "55");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "65");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "100");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "68");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "63");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "50");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "63");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "66");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "25");
		list2.add(map);
		
		list.add(list2);
		
		list2 = new ArrayList<Map<String, String>>();
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "region b");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "59");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "66");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "50");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "65");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "62");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "40");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "63");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "70");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "20");
		list2.add(map);
		
		list.add(list2);
		
		setRequestAttribute("bubbleChartData", list);
	}
	
	private void makeChartDataWithNote() {
		List<List<Map<String, String>>> list = new ArrayList<List<Map<String,String>>>();
		
		List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "2006");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "2007");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "2008");
		list2.add(map);
		
		list.add(list2);
		
		list2 = new ArrayList<Map<String, String>>();
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "Region A");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "40");
		map.put("note", "Lowest Value");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "45");
		map.put("note", "note_type=balloon \r test");
		map.put("note_type", "balloon");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "63");
		map.put("note", "Highest Value");
		map.put("note_type", "arrow");
		map.put("note_x", "-20");
		list2.add(map);
		
		list.add(list2);
			
		setRequestAttribute("chartDataWithNote", list);
	}
	
	private void makeChartDataWithTooltip() {
		List<List<Map<String, String>>> list = new ArrayList<List<Map<String,String>>>();
		
		List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "2005");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "2006");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "2007");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "2008");
		list2.add(map);
		
		list.add(list2);
		
		list2 = new ArrayList<Map<String, String>>();
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "Region A");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "5");
		map.put("tooltip", "5");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "10");
		map.put("tooltip", "10");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "30");
		map.put("tooltip", "30");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "63");
		map.put("tooltip", "63");
		list2.add(map);
		
		list.add(list2);
		
		list2 = new ArrayList<Map<String, String>>();
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "Region B");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "100");
		map.put("tooltip", "100");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "20");
		map.put("tooltip", "20");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "65");
		map.put("tooltip", "65");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "55");
		map.put("tooltip", "55");
		list2.add(map);
		
		list.add(list2);
		
		list2 = new ArrayList<Map<String, String>>();
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "Region C");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "56");
		map.put("tooltip", "56");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "21");
		map.put("tooltip", "21");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "5");
		map.put("tooltip", "5");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "90");
		map.put("tooltip", "90");
		list2.add(map);
		
		list.add(list2);
		
		setRequestAttribute("chartDataWithTooltip", list);
	}
	//畫面控制項函數區結束
	
	//邏輯函數區
	//頁面動作所對應的處理method,business logic,請歸類到下面
	
	//邏輯函數區結束
}
