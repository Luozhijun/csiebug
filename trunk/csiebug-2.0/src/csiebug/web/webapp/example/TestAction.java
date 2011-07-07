package csiebug.web.webapp.example;

//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.ServletOutputStream;
//
//import net.sf.mpxj.ProjectFile;
//import net.sf.mpxj.Task;
//import net.sf.mpxj.mpp.MPPReader;
//import net.sf.mpxj.reader.ProjectReader;
//
//import org.apache.poi.hslf.HSLFSlideShow;
//import org.apache.poi.hslf.model.Slide;
//import org.apache.poi.hslf.model.TextBox;
//import org.apache.poi.hslf.model.TextRun;
//import org.apache.poi.hslf.model.TextShape;
//import org.apache.poi.hslf.usermodel.RichTextRun;
//import org.apache.poi.hslf.usermodel.SlideShow;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import csiebug.service.ServiceException;
import csiebug.util.AssertUtility;
//import csiebug.web.html.form.HtmlMultiText;
import csiebug.web.webapp.BasicAction;

public class TestAction extends BasicAction {
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
	private void makeControl() throws Exception {
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
		
//		createPPT();
//		readMPP();
	}
		
	//畫面控制項函數區結束
	
	//邏輯函數區
	//頁面動作所對應的處理method,business logic,請歸類到下面
//	private void readMPP() throws Exception {
//		ProjectReader reader = new MPPReader();
//		
//		ProjectFile project = reader.read("d:\\test.mpp");
//		
//		List<Task> list = project.getAllTasks();
//		List<Map<String, String>> gridData = new ArrayList<Map<String,String>>();
//		List<Map<String, String>> dynaData = new ArrayList<Map<String, String>>();
//		
//		int maxLevel = 1;
//		for(int i = 0; i < list.size(); i++) {
//			Task task = list.get(i);
//			
//			if(maxLevel < task.getOutlineLevel()) {
//				maxLevel = task.getOutlineLevel();
//			}
//		}
//		
//		StringBuffer sb = new StringBuffer();
//		for(int i = 0; i < maxLevel; i++) {
//			Map<String, String> map = new LinkedHashMap<String, String>();
//			map.put("fieldname", "level" + i);
//			map.put("style", "display:none");
//			map.put("dataType", "number");
//			
//			if(i != 0) {
//				sb.append(",");
//			}
//			sb.append("level" + i);
//			
//			dynaData.add(map);
//		}
//		setRequestAttribute("columns", sb.toString());
//		
//		for(int i = 0; i < list.size(); i++) {
//			Map<String, String> map = new LinkedHashMap<String, String>();
//			Task task = list.get(i);
//			
//			map.put("uniqueID", "" + task.getUniqueID());
//			map.put("name", task.getName());
//			map.put("start", task.getStart().toLocaleString());
//			map.put("finish", task.getFinish().toLocaleString());
////			map.put("rollup", "" + task.getRollup());
//			map.put("outlineNumber", task.getOutlineNumber());
////			map.put("outlineLevel", "" + task.getOutlineLevel());
//			map.put("id", "" + task.getID());
////			map.put("recurring", "" + task.getRecurring());
//			map.put("pm", "" + task.getNumber1());
//			map.put("sa", "" + task.getNumber2());
//			map.put("pa", "" + task.getNumber3());
//			map.put("swd", "" + task.getNumber4());
//			map.put("pgr", "" + task.getNumber5());
//			map.put("ase", "" + task.getNumber6());
//			map.put("other", "" + task.getNumber7());
//			
//			Task parentTask = task.getParentTask();
//			if(parentTask != null) {
//				map.put("parentUniqueID", "" + task.getParentTask().getUniqueID());
//				map.put("parentOutlineNumber", task.getParentTask().getOutlineNumber());
//			} else {
//				map.put("parentUniqueID", "");
//				map.put("parentOutlineNumber", "");
//			}
//			
//			String[] level = task.getOutlineNumber().split("\\.");
//			
//			for(int j = 0; j < maxLevel; j++) {
//				if(j < level.length) {
//					map.put("level" + j, level[j]);
//				} else {
//					map.put("level" + j, "");
//				}
//			}
//			
//			gridData.add(map);
//		}
//		
//		setRequestAttribute("grid", gridData);
//		setRequestAttribute("level", dynaData);
//		
//		createExcel();
//	}
//	
//	private void createExcel() throws FileNotFoundException, IOException {
//		HSSFWorkbook excel = new HSSFWorkbook(new FileInputStream("d:\\我的檔案.xls"));
//		HSSFSheet sheet = excel.getSheetAt(4);
//		HSSFRow row = sheet.getRow(3);
//		System.out.println(row.getCell(1).getStringCellValue());
//	}
//	
//	private void createPPT() throws IOException {
//		String newDir = "D:\\doc\\church\\音控\\歌詞庫\\";
//		File dir = new File("D:\\music\\詩歌投影片\\石牌信友堂敬拜團\\敬拜團隊PowerPoint Slide\\");
//		
//		File[] songs = dir.listFiles();
//		
//		for(int i = 0; i < songs.length; i++) {
//			File song = songs[i];
//			
//			if(song.isFile()) {
//				SlideShow templatePPT = new SlideShow(new HSLFSlideShow("D:\\doc\\church\\音控\\敬拜歌詞.ppt"));				
//				String fileName = song.getName();
//				SlideShow oldPPT = new SlideShow(new HSLFSlideShow(song.getPath()));
//				Slide[] oldSlides = oldPPT.getSlides();
//
//				for(int j = 0; j < oldSlides.length; j++) {
//					Slide oldSlide = oldSlides[j];
//					Slide newSlide = templatePPT.createSlide();
//					
//					TextBox newTitle = newSlide.addTitle();
//					newTitle.setText(oldSlide.getTextRuns()[0].getText());
//					newTitle.setAnchor(new java.awt.Rectangle(10, 40, 700, 200));
//					newTitle.setHorizontalAlignment(TextShape.AlignCenter);
//					newTitle.setVerticalAlignment(TextShape.AlignCenter);
//					newTitle.getTextRun().getRichTextRuns()[0].setBold(true);
//				}
//				
//				templatePPT.removeSlide(0);
//				
//				FileOutputStream out = new FileOutputStream(newDir + fileName);
//			    templatePPT.write(out);
//			    out.close();
//			}
//		}
//	}
	
	//邏輯函數區結束
}
