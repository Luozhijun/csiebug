package csiebug.web.webapp.example.taglib.office.excel;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import csiebug.service.ServiceException;
import csiebug.util.AssertUtility;
import csiebug.web.webapp.BasicAction;

public class ExcelGridAction extends BasicAction {
	private static final long serialVersionUID = 1L;

	public String main() throws Exception {
		//以下的if else處理各樣頁面的動作,可自行增加或修改
		if(getActFlag().equalsIgnoreCase("saveExcelRecord")) {
			try {
				String uniqueID = "";
				String order = "";
				Enumeration<String> enumeration = getRequest().getParameterNames();
				while(enumeration.hasMoreElements()) {
					String name = enumeration.nextElement();
					
					if(name.endsWith("_1")) {
						order = getRequestValue(name);
					} else if(name.endsWith("_2")) {
						uniqueID = getRequestValue(name);
					}
				}
				System.out.println("saveExcelRecord:");
				System.out.println("projectId = " + getRequestValue("projectId"));
				System.out.println("task key = " + uniqueID);
				System.out.println("new order = " + order);
				System.out.println("------------------------------------");
				
				getResponse().getWriter().print("true");
			} catch(Exception ex) {
				getResponse().getWriter().print("false");
			}
			
			return null;
		} else if(getActFlag().equalsIgnoreCase("saveExcelField")) {
			try {
				String[] fields = new String(getRequestValue("fields").getBytes("ISO-8859-1"), getRequestValue("encode")).split(";");
				
				for(int i = 0; i < fields.length; i++) {
					String uniqueID = fields[i].split(",")[0];
					String fieldId = fields[i].split(",")[1];
					String fieldValue = fields[i].split(",")[2];
					
					System.out.println("saveExcelField:");
					System.out.println("projectId = " + getRequestValue("projectId"));
					System.out.println("task key = " + uniqueID);
					
					if(fieldId.endsWith("_4")) {
						System.out.println("name = " + fieldValue);
					} else if(fieldId.endsWith("_5")) {
						System.out.println("start = " + fieldValue);
					} else if(fieldId.endsWith("_6")) {
						System.out.println("finish = " + fieldValue);
					} else if(fieldId.endsWith("_7")) {
						System.out.println("pm = " + fieldValue);
					} else if(fieldId.endsWith("_8")) {
						System.out.println("sa = " + fieldValue);
					} else if(fieldId.endsWith("_9")) {
						System.out.println("pa = " + fieldValue);
					} else if(fieldId.endsWith("_10")) {
						System.out.println("swd = " + fieldValue);
					} else if(fieldId.endsWith("_11")) {
						System.out.println("pgr = " + fieldValue);
					} else if(fieldId.endsWith("_12")) {
						System.out.println("ase = " + fieldValue);
					} else if(fieldId.endsWith("_13")) {
						System.out.println("other = " + fieldValue);
					}
					
					System.out.println("------------------------------------");
				}
				
				getResponse().getWriter().print("true");
			} catch(Exception ex) {
				ex.printStackTrace();
				getResponse().getWriter().print("false");
			}
			
			return null;
		} else if(getActFlag().equalsIgnoreCase("updateOrder")) {
			try {
				String[] orders = getRequestValue("orders").split(";");
				for(int i = 0; i < orders.length; i++) {
					String uniqueID = orders[i].split(",")[0];
					String order = orders[i].split(",")[1];
					
					System.out.println("updateOrder:");
					System.out.println("projectId = " + getRequestValue("projectId"));
					System.out.println("task key = " + uniqueID);
					System.out.println("new order = " + order);
					System.out.println("------------------------------------");
				}
				
				getResponse().getWriter().print("true");
			} catch(Exception ex) {
				getResponse().getWriter().print("false");
			}
			
			return null;
		} else if(getActFlag().equalsIgnoreCase("deleteProjectRecord")) {
			try {
				String[] uniqueIDs = getRequestValue("uniqueIDs").split(",");
				
				for(int i = 0; i < uniqueIDs.length; i++) {
					System.out.println("delete:");
					System.out.println("projectId = " + getRequestValue("projectId"));
					System.out.println("task key = " + uniqueIDs[i]);
					System.out.println("------------------------------------");
				}
				
				getResponse().getWriter().print("true");
			} catch(Exception ex) {
				getResponse().getWriter().print("false");
			}
			
			return null;
		} else if(getActFlag().equalsIgnoreCase("downloadFileName")) {
			makeControl();
			return "downloadFileName";
		} else if(getActFlag().equalsIgnoreCase("hideable")) {
			makeControl();
			return "hideable";
		} else if(getActFlag().equalsIgnoreCase("selectable")) {
			makeControl();
			return "selectable";
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
				setRequestAttribute("FunctionName", getMessage("taglibdemo.TableDoc.FunctionName"));
			}
		}
		
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("uniqueID", "1");
		map.put("order", "2");
		map.put("field1", "1");
		map.put("field2", "2");
		map.put("field3", "3");
		
		list.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("uniqueID", "4");
		map.put("order", "1");
		map.put("field1", "4");
		map.put("field2", "5");
		map.put("field3", "6");
		
		list.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("uniqueID", "7");
		map.put("order", "0");
		map.put("field1", "7");
		map.put("field2", "8");
		map.put("field3", "9");
		
		list.add(map);
		
		setRequestAttribute("gridData", list);
	}
	
	//畫面控制項函數區結束
	
	//邏輯函數區
	//頁面動作所對應的處理method,business logic,請歸類到下面
	
	//邏輯函數區結束
}
