package csiebug.web.webapp.example.taglib.movingbox;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.naming.NamingException;

import csiebug.service.ServiceException;
import csiebug.util.AssertUtility;
import csiebug.web.webapp.BasicAction;

public class MovingBoxAction extends BasicAction {
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
				setRequestAttribute("FunctionName", getMessage("taglibdemo.MovingBoxDoc.FunctionName"));
			}
		}
		
		makeMovingBox();
	}
	
	//畫面控制項函數區結束
	
	//邏輯函數區
	//頁面動作所對應的處理method,business logic,請歸類到下面
	private void makeMovingBox() {
		ArrayList<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("imageURL", "http://css-tricks.com/examples/MovingBoxes/images/1.jpg");
		map.put("title", "test title");
		map.put("description", "fdasfas;j;fas");
		list.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("imageURL", "http://css-tricks.com/examples/MovingBoxes/images/2.jpg");
		map.put("title", "test title2");
		map.put("description", "hfdsgd");
		list.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("imageURL", "http://css-tricks.com/examples/MovingBoxes/images/3.jpg");
		map.put("title", "test title3");
		map.put("description", "hfdsgd");
		list.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("imageURL", "http://css-tricks.com/examples/MovingBoxes/images/4.jpg");
		map.put("title", "test title4");
		map.put("description", "hfdsgd");
		list.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("imageURL", "http://css-tricks.com/examples/MovingBoxes/images/5.jpg");
		map.put("title", "test title5");
		map.put("description", "hfdsgd");
		list.add(map);
		
		setRequestAttribute("testMovingBoxData", list);
	}
	//邏輯函數區結束
}
