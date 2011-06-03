package csiebug.web.webapp.example;

import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.mpp.MPPReader;
import net.sf.mpxj.reader.ProjectReader;

import csiebug.service.ServiceException;
import csiebug.util.AssertUtility;
import csiebug.web.webapp.BasicAction;

public class MPPAction extends BasicAction {
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
	private void makeControl() throws MPXJException {
		//畫面控制項
		try {
			setRequestAttribute("FunctionName", getFunctionName());
		} catch(ServiceException se) {
			writeErrorLog(se);
			
			if(AssertUtility.isNotNullAndNotSpace(getFunctionId())) {
				setRequestAttribute("FunctionName", getFunctionId());
			} else {
				setRequestAttribute("FunctionName", "頁面標題");
			}
		}
		
		readMPP();
	}
		
	//畫面控制項函數區結束
	
	//邏輯函數區
	//頁面動作所對應的處理method,business logic,請歸類到下面
	private void readMPP() throws MPXJException {
		ProjectReader reader = new MPPReader();
		
		ProjectFile project = reader.read("d:\\test.mpp");
		
		System.out.println(project.getTaskByID(0).getName());
	}
	
	//邏輯函數區結束
}
