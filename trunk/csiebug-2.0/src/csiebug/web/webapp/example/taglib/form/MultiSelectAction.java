package csiebug.web.webapp.example.taglib.form;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.naming.NamingException;

import csiebug.service.ServiceException;
import csiebug.util.AssertUtility;
import csiebug.web.webapp.BasicAction;

public class MultiSelectAction extends BasicAction {
	private static final long serialVersionUID = 1L;

	public String main() throws Exception {
		//以下的if else處理各樣頁面的動作,可自行增加或修改
		if(getActFlag().startsWith("multiSelect")) {
			StringBuffer script = new StringBuffer();
			script.append("document.getElementById(\"" + getActFlag() + "_legend\").click();\n");
			script.append("document.getElementById(\"" + getActFlag() + "_unselect_display\").focus();\n");
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
				setRequestAttribute("FunctionName", getMessage("taglibdemo.MultiSelectDoc.FunctionName"));
			}
		}
		
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("test", "測試");
		map.put("test2", "測試2");
		setRequestAttribute("option", map);
		
		setRequestAttribute("multiSelect10_requestAttribute", "test2");
	}
	
	
	//畫面控制項函數區結束
	
	//邏輯函數區
	//頁面動作所對應的處理method,business logic,請歸類到下面
	
	//邏輯函數區結束
}
