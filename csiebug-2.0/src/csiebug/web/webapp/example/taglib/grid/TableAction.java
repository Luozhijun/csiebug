package csiebug.web.webapp.example.taglib.grid;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Task;
import net.sf.mpxj.mpp.MPPReader;
import net.sf.mpxj.reader.ProjectReader;

import csiebug.service.ServiceException;
import csiebug.util.AssertUtility;
import csiebug.web.webapp.BasicAction;

public class TableAction extends BasicAction {
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
	private void makeControl() throws UnsupportedEncodingException, NamingException, MPXJException {
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
		map.put("field1", "1");
		map.put("field2", "2");
		map.put("field3", "3");
		
		list.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("field1", "4");
		map.put("field2", "5");
		map.put("field3", "6");
		
		list.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("field1", "7");
		map.put("field2", "8");
		map.put("field3", "9");
		
		list.add(map);
		
		setRequestAttribute("gridData", list);
		
		setSessionAttribute("gridData", list);
		setSessionAttribute("gridDataPage", 1);
		
		setRequestAttribute("gridData2", list);
		
		List<Map<String, String>> list2 = new ArrayList<Map<String,String>>();
		
		setRequestAttribute("noData", list2);
		
		readMPP();
	}
	
	private void readMPP() throws MPXJException {
		ProjectReader reader = new MPPReader();
		
		ProjectFile project = reader.read("/home/csiebug/projects/workspace4csiebug/csiebug-2.0/WebContent/example/test.mpp");
		
		List<Task> list = project.getAllTasks();
		List<Map<String, String>> gridData = new ArrayList<Map<String,String>>();
		List<Map<String, String>> dynaData = new ArrayList<Map<String, String>>();
		
		int maxLevel = 1;
		for(int i = 0; i < list.size(); i++) {
			Task task = list.get(i);
			
			if(maxLevel < task.getOutlineLevel()) {
				maxLevel = task.getOutlineLevel();
			}
		}
		
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < maxLevel; i++) {
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("fieldname", "level" + i);
			map.put("style", "display:none");
			
			if(i != 0) {
				sb.append(",");
			}
			sb.append("level" + i);
			
			dynaData.add(map);
		}
		setRequestAttribute("columns", sb.toString());
		
		for(int i = 0; i < list.size(); i++) {
			Map<String, String> map = new LinkedHashMap<String, String>();
			Task task = list.get(i);
			
			map.put("uniqueID", "" + task.getUniqueID());
			map.put("name", task.getName());
			map.put("start", task.getStart().toString());
			map.put("finish", task.getFinish().toString());
			map.put("rollup", "" + task.getRollup());
			map.put("outlineNumber", task.getOutlineNumber());
			map.put("outlineLevel", "" + task.getOutlineLevel());
			map.put("id", "" + task.getID());
			map.put("recurring", "" + task.getRecurring());
			map.put("pm", "" + task.getNumber1());
			map.put("sa", "" + task.getNumber2());
			map.put("pa", "" + task.getNumber3());
			map.put("swd", "" + task.getNumber4());
			map.put("pgr", "" + task.getNumber5());
			map.put("ase", "" + task.getNumber6());
			
			String[] level = task.getOutlineNumber().split("\\.");
			
			for(int j = 0; j < maxLevel; j++) {
				if(j < level.length) {
					map.put("level" + j, level[j]);
				} else {
					map.put("level" + j, "");
				}
			}
			
			gridData.add(map);
		}
		
		setRequestAttribute("gridData3", gridData);
		setRequestAttribute("level", dynaData);
	}
	
	//畫面控制項函數區結束
	
	//邏輯函數區
	//頁面動作所對應的處理method,business logic,請歸類到下面
	
	//邏輯函數區結束
}
