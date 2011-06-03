package csiebug.web.webapp.example.taglib.office.project;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.mpp.MPPReader;
import net.sf.mpxj.reader.ProjectReader;

import csiebug.service.ServiceException;
import csiebug.util.AssertUtility;
import csiebug.util.DateFormatException;
import csiebug.web.html.office.project.ProjectUtility;
import csiebug.web.webapp.BasicAction;

public class ProjectGridAction extends BasicAction {
	private static final long serialVersionUID = 1L;

	public String main() throws Exception {
		//以下的if else處理各樣頁面的動作,可自行增加或修改
		if(getActFlag().equalsIgnoreCase("saveProjectRecord")) {
			try {
				String uniqueID = "";
				String outlineNumber = "";
				String taskName = "";
				Enumeration<String> enumeration = getRequest().getParameterNames();
				while(enumeration.hasMoreElements()) {
					String name = enumeration.nextElement();
					
					if(name.endsWith("_2")) {
						outlineNumber = getRequestValue(name);
					} else if(name.endsWith("_3")) {
						uniqueID = getRequestValue(name);
					} else if(name.endsWith("_4")) {
						taskName = getRequestValue(name);
						taskName = new String(taskName.getBytes("ISO-8859-1"), getRequestValue("encode"));
						taskName = taskName.replaceAll("var.openParenthesis", "(");
						taskName = taskName.replaceAll("var.closeParenthesis", ")");
					}
				}
				System.out.println("saveProjectRecord:");
				System.out.println("projectId = " + getRequestValue("projectId"));
				System.out.println("task key = " + uniqueID);
				System.out.println("new outlineNumber = " + outlineNumber);
				System.out.println("new name = " + taskName);
				System.out.println("------------------------------------");
				
				getResponse().getWriter().print("true");
			} catch(Exception ex) {
				getResponse().getWriter().print("false");
			}
			
			return null;
		} else if(getActFlag().equalsIgnoreCase("saveProjectField")) {
			try {
				String[] fields = new String(getRequestValue("fields").getBytes("ISO-8859-1"), getRequestValue("encode")).split(";");
				
				for(int i = 0; i < fields.length; i++) {
					String uniqueID = fields[i].split(",")[0];
					String fieldId = fields[i].split(",")[1];
					String fieldValue = fields[i].split(",")[2];
					
					System.out.println("saveProjectField:");
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
		} else if(getActFlag().equalsIgnoreCase("updateOutlineNumber")) {
			try {
				String[] outlineNumbers = getRequestValue("outlineNumbers").split(";");
				for(int i = 0; i < outlineNumbers.length; i++) {
					String uniqueID = outlineNumbers[i].split(",")[0];
					String outlineNumber = outlineNumbers[i].split(",")[1];
					
					System.out.println("updateOutlineNumber:");
					System.out.println("projectId = " + getRequestValue("projectId"));
					System.out.println("task key = " + uniqueID);
					System.out.println("new outlineNumber = " + outlineNumber);
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
		} else if(getActFlag().equalsIgnoreCase("isReadOnly")) {
			makeControl();
			return "isReadOnly";
		} else if(getActFlag().equalsIgnoreCase("hideable")) {
			makeControl();
			return "hideable";
		} else if(getActFlag().equalsIgnoreCase("columnIsReadOnly")) {
			makeControl();
			return "columnIsReadOnly";
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
	private void makeControl() throws UnsupportedEncodingException, NamingException, MPXJException, DateFormatException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
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
		
		readMPP();
	}
	
	private void readMPP() throws MPXJException, DateFormatException, NamingException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		ProjectReader reader = new MPPReader();
		
		ProjectFile project = reader.read("/home/csiebug/projects/workspace4csiebug/csiebug-2.0/WebContent/example/test.mpp");
		
		List<Map<String, String>> gridData = ProjectUtility.toList(project, Integer.parseInt(getSysDateFormat()));
		
		setRequestAttribute("grid", gridData);
	}
	
	//畫面控制項函數區結束
	
	//邏輯函數區
	//頁面動作所對應的處理method,business logic,請歸類到下面
	
	//邏輯函數區結束
}
