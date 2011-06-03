/**
 * Copyright (c) 2010 George Tsai. All rights reserved.
 * For more information on the George Tsai, please see http://www.pixnet.net/blog/csiebug
 */
package csiebug.web.html.office.project;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jawin.COMException;
import org.jawin.DispatchPtr;

import net.sf.mpxj.AccrueType;
import net.sf.mpxj.ConstraintType;
import net.sf.mpxj.DateRange;
import net.sf.mpxj.Duration;
import net.sf.mpxj.Priority;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Relation;
import net.sf.mpxj.Task;
import net.sf.mpxj.TaskType;
import net.sf.mpxj.TimeUnit;

import csiebug.util.AssertUtility;
import csiebug.util.DateFormatException;
import csiebug.util.DateFormatUtility;
import csiebug.util.NumberFormatUtility;
import csiebug.util.StringUtility;

/**
 * @author George_Tsai
 * @version 2010/12/24
 */
public class ProjectUtility {
	public static ProjectFile toProjectFile(List<Map<String, String>> tasks, int defaultDateFormat) throws DateFormatException, ParseException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		ProjectFile project = new ProjectFile();
		
		for(int i = 0; i < tasks.size(); i++) {
			Map<String, String> task = tasks.get(i);
			
			Task msProjectTask = project.addTask();

			for(int j = 0; j < Task.class.getMethods().length; j++) {
	        	Method method = Task.class.getMethods()[j];
	        	if(method.getName().startsWith("set") && !method.getName().equalsIgnoreCase("set")) {
	        		if(method.getGenericParameterTypes() != null && method.getGenericParameterTypes().length == 1) {
	        			String key = method.getName().replaceFirst("set", "");
			        	key = key.substring(0, 1).toLowerCase() + key.substring(1, key.length());
			        	String value = task.get(key);
			        	
			        	if(value != null) {
			        		Object[] parameters = null;
				        	Type dataType = method.getGenericParameterTypes()[0];
				        	
			        		if(dataType.equals(Date.class)) {
			        			parameters = new Object[1];
			        			parameters[0] = DateFormatUtility.toDate(value, defaultDateFormat);
			        		} else if(dataType.equals(Boolean.class) || dataType.equals(boolean.class)) {
			        			parameters = new Object[1];
			        			if(value.equals("0")) {
			        				parameters[0] = false;
			        			} else if(value.equals("1")) {
			        				parameters[0] = true;
			        			} else {
			        				parameters[0] = Boolean.parseBoolean(value);
			        			}
			        		} else if(dataType.equals(Integer.class)) {
			        			parameters = new Object[1];
			        			parameters[0] = Integer.parseInt(value);
			        		} else if(dataType.equals(Number.class)) {
			        			parameters = new Object[1];
			        			parameters[0] = Double.parseDouble(value);
			        		} else if(StringUtility.isInArray(key, ProjectUtility.getDurationToDurationFormatKeys())) {
			        			if(task.get(key + "Format") != null) {
				        			parameters = new Object[1];
				        			int format = Integer.parseInt(task.get(key + "Format"));
				        			parameters[0] = Duration.getInstance(Double.parseDouble(value), TimeUnit.getInstance(format));
			        			}
			        		} else if(dataType.equals(Duration.class)) {
			        			Double duration = null;
			        			TimeUnit unit = null;
			        			for(int k = 0; k < value.length(); k++) {
			        				String checkString = value.substring(0, value.length() - k);
			        				if(NumberFormatUtility.isValid(checkString)) {
			        					duration = Double.parseDouble(checkString);
			        					TimeUnit[] units = TimeUnit.values();
			        					for(int l = 0; l < units.length; l++) {
			        						if(units[l].getName().equalsIgnoreCase(value.substring(value.length() - k, value.length()))) {
			        							unit = units[l];
			        							break;
			        						}
			        					}
			        					break;
			        				}
			        			}
			        			
			        			if(duration != null && unit != null) {
			        				parameters = new Object[1];
			        				parameters[0] = Duration.getInstance(duration, unit);
			        			}
			        		} else if(dataType.equals(Priority.class)) {
			        			parameters = new Object[1];
			        			parameters[0] = Priority.getInstance(Integer.parseInt(value));
	        				} else if(dataType.equals(TaskType.class)) {
		        				parameters = new Object[1];
			        			parameters[0] = TaskType.getInstance(Integer.parseInt(value));
	        				} else if(dataType.equals(AccrueType.class)) {
	        					parameters = new Object[1];
			        			parameters[0] = AccrueType.getInstance(Integer.parseInt(value));
	        				} else if(dataType.equals(ConstraintType.class)) {
	        					parameters = new Object[1];
	        					if(value.equalsIgnoreCase("ASAP")) {
	        						parameters[0] = ConstraintType.AS_SOON_AS_POSSIBLE;
	        					} else if(value.equalsIgnoreCase("ALAP")) {
	        						parameters[0] = ConstraintType.AS_LATE_AS_POSSIBLE;
	        					} else if(value.equalsIgnoreCase("MSO")) {
	        						parameters[0] = ConstraintType.MUST_START_ON;
	        					} else if(value.equalsIgnoreCase("MFO")) {
	        						parameters[0] = ConstraintType.MUST_FINISH_ON;
	        					} else if(value.equalsIgnoreCase("SNET")) {
	        						parameters[0] = ConstraintType.START_NO_EARLIER_THAN;
	        					} else if(value.equalsIgnoreCase("SNLT")) {
	        						parameters[0] = ConstraintType.START_NO_LATER_THAN;
	        					} else if(value.equalsIgnoreCase("FNET")) {
	        						parameters[0] = ConstraintType.FINISH_NO_EARLIER_THAN;
	        					} else if(value.equalsIgnoreCase("FNLT")) {
	        						parameters[0] = ConstraintType.FINISH_NO_LATER_THAN;
	        					}
	        				} else if(key.equalsIgnoreCase("parentTask")) {
	        					//TODO: 因不確定要塞哪個值,所以List<Map>裡面暫時沒塞值
		        			} else if(key.equalsIgnoreCase("childTasks")) {
		        				//TODO: 因不確定要塞哪個值,所以List<Map>裡面暫時沒塞值
		        			} else if(key.equalsIgnoreCase("resourceAssignments")) {
		        				//TODO: 因不確定要塞哪個值,所以List<Map>裡面暫時沒塞值
		        			} else if(key.equalsIgnoreCase("recurringTask")) {
		        				//TODO: 因不確定要塞哪個值,所以List<Map>裡面暫時沒塞值
		        			} else if(key.equalsIgnoreCase("successors")) {
	        					//TODO: 因不確定要塞哪個值,所以List<Map>裡面暫時沒塞值
	        				} else if(key.equalsIgnoreCase("predecessors")) {
	        					//TODO: 因不確定要塞哪個值,所以List<Map>裡面暫時沒塞值
	        				} else if(key.equalsIgnoreCase("splits")) {
	        					//TODO: 因不確定要塞哪個值,所以List<Map>裡面暫時沒塞值
	        				} else {
	        					parameters = new Object[1];
	        					parameters[0] = value;
	        				}
			        		
			        		if(parameters != null) {
			        			method.invoke(msProjectTask, parameters);
			        		}
			        	}
		        	}
	        	}
	        }
		}
		
		return project;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public static List<Map<String, String>> toList(ProjectFile project, int defaultDateFormat) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, DateFormatException  {
		AssertUtility.notNull(project);
		
		List<Task> list = project.getAllTasks();
		
		String[] excludeMethod = new String[]{"getClass", "getParentFile"};
		
		List<Map<String, String>> finalList = new ArrayList<Map<String, String>>();
		
		Iterator<Task> iterator = list.iterator();
		
        while (iterator.hasNext()) {
        	Map<String, String> map = new LinkedHashMap<String, String>();
        	
        	Task task = iterator.next();
	        
        	if(!task.getOutlineNumber().equals("0")) {
		        for(int i = 0; i < Task.class.getMethods().length; i++) {
		        	Method method = Task.class.getMethods()[i];
		        	if(method.getName().startsWith("get") && !StringUtility.isInArray(method.getName(), excludeMethod)) {
		        		String key = method.getName().replaceFirst("get", "");
			        	key = key.substring(0, 1).toLowerCase() + key.substring(1, key.length());
			        	
			        	//需要傳參數的get method先濾掉
			        	if(method.getGenericParameterTypes() == null || method.getGenericParameterTypes().length == 0) {
			        		Object value = method.invoke(task, (Object[])null);
			        		
			        		if(value == null) {
		        				map.put(key, null);
		        			} else {
//		        				if(key.equalsIgnoreCase("finishSlack")) {
//		        					System.out.println(value.getClass().getName());
//		        				}
		        				
		        				
		        				if(method.getReturnType().equals(Date.class)) {
		        					String timeString = ((Date)value).toLocaleString();
		        					timeString = timeString.substring(0, timeString.indexOf(" "));
		        					map.put(key, DateFormatUtility.transDate(timeString, 111, defaultDateFormat));
		        				} else if(StringUtility.isInArray(key, getDurationToDurationFormatKeys())) {
		        					Duration duration = (Duration)value;
		        					String doubleValue = "" + duration.getDuration();
		        					map.put(key, doubleValue);
		        					map.put(key + "Format", "" + duration.getUnits().getValue());
		        				} else if(key.equalsIgnoreCase("priority")) {
		        					Priority priority = (Priority)value;
		        					map.put(key, "" + priority.getValue());
		        				} else if(key.equalsIgnoreCase("parentTask")) {
		        					//TODO: 有這個key,但不確定要塞哪個值
//			        				Task parentTask = (Task)value;
//			        				map.put(key, "" + parentTask.getID());
//			        				map.put(key, "" + parentTask.getUniqueID());
//			        				map.put(key, parentTask.getName());
		        				} else if(key.equalsIgnoreCase("childTasks")) {
		        					//TODO: 有這個key,但這是集合物件,不確定要怎麼塞值
		        					//[[Task id=155 uniqueID=516 name=測試], [Task id=156 uniqueID=517 name=修正程式]]
		        					LinkedList<Task> childTasks = (LinkedList<Task>)value;
		        					
		        					if(childTasks.size() != 0) {
		        						
		        					}
		        				} else if(key.equalsIgnoreCase("resourceAssignments")) {
		        					//TODO: 有這個key,但這是集合物件,不確定要怎麼塞值
		        					//[]
		        					@SuppressWarnings("rawtypes")
									LinkedList resourceAssignments = (LinkedList)value;
		        					
		        					if(resourceAssignments.size() != 0) {
		        						
		        					}
		        				} else if(key.equalsIgnoreCase("recurringTask")) {
		        					//TODO: 有這個key,但不確定要塞哪個值
//		        					RecurringTask recurringTask = (RecurringTask)value;
//		        					
//		        					map.put(key, "" + recurringTask.getDailyFrequency());
//		        					map.put(key, "" + recurringTask.getDailyWorkday());
//		        					map.put(key, "" + recurringTask.getDuration());
//		        					map.put(key, "" + recurringTask.getFinishDate());
//		        					map.put(key, "" + recurringTask.getMonthlyAbsoluteDay());
//		        					map.put(key, "" + recurringTask.getMonthlyAbsoluteFrequency());
//		        					map.put(key, "" + recurringTask.getMonthlyRelative());
//		        					map.put(key, "" + recurringTask.getMonthlyRelativeDay());
//		        					map.put(key, "" + recurringTask.getMonthlyRelativeFrequency());
//		        					map.put(key, "" + recurringTask.getMonthlyRelativeOrdinal());
//		        					map.put(key, "" + recurringTask.getOccurrences());
//		        					map.put(key, "" + recurringTask.getStartDate());
//		        					map.put(key, "" + recurringTask.getUseEndDate());
//		        					map.put(key, "" + recurringTask.getWeeklyDays());
//		        					map.put(key, "" + recurringTask.getWeeklyFrequency());
//		        					map.put(key, "" + recurringTask.getYearlyRelativeMonth());
//		        					map.put(key, "" + recurringTask.getYearlyRelativeOrdinal());
//		        					map.put(key, "" + recurringTask.getYearlyAbsolute());
//		        					map.put(key, "" + recurringTask.getYearlyAbsoluteDate());
//		        					map.put(key, "" + recurringTask.getYearlyRelativeDay());
		        				} else if(key.equalsIgnoreCase("type")) {
		        					//在ms project裡面,是"任務類型(Y)"的下拉選單,存檔的值是0,1,2
		        					TaskType type = (TaskType)value;
		        					if(type == TaskType.FIXED_UNITS) {
		        						map.put(key, "0");
		        					} else if(type == TaskType.FIXED_DURATION) {
		        						map.put(key, "1");
		        					} else if(type == TaskType.FIXED_WORK) {
		        						map.put(key, "2");
		        					}
		        				} else if(key.equalsIgnoreCase("fixedCostAccrual")) {
		        					//在ms project裡面,選檢視(V)->表格(B)->成本(C),可以叫出"固定成本累算"這個欄位,有三種下拉選項可以選,存檔的值是1,2,3
		        					AccrueType type = (AccrueType)value;
		        					if(type == AccrueType.START) {
		        						map.put(key, "1");
		        					} else if(type == AccrueType.END) {
		        						map.put(key, "2");
		        					} else if(type == AccrueType.PRORATED) {
		        						map.put(key, "3");
		        					}
		        				} else if(key.equalsIgnoreCase("constraintType")) {
		        					//在ms project裡面,是"限制式類型(P)"的下拉選單
		        					ConstraintType type = (ConstraintType)value;
		        					if(type == ConstraintType.AS_SOON_AS_POSSIBLE) {
		        						map.put(key, "ASAP");
		        					} else if(type == ConstraintType.AS_LATE_AS_POSSIBLE) {
		        						map.put(key, "ALAP");
		        					} else if(type == ConstraintType.MUST_START_ON) {
		        						map.put(key, "MSO");
		        					} else if(type == ConstraintType.MUST_FINISH_ON) {
		        						map.put(key, "MFO");
		        					} else if(type == ConstraintType.START_NO_EARLIER_THAN) {
		        						map.put(key, "SNET");
		        					} else if(type == ConstraintType.START_NO_LATER_THAN) {
		        						map.put(key, "SNLT");
		        					} else if(type == ConstraintType.FINISH_NO_EARLIER_THAN) {
		        						map.put(key, "FNET");
		        					} else if(type == ConstraintType.FINISH_NO_LATER_THAN) {
		        						map.put(key, "FNLT");
		        					}
		        				} else if(key.equalsIgnoreCase("successors")) {
		        					//TODO: 有這個key,但這是集合物件,不確定要怎麼塞值
		        					//[[Relation [Task id=218 uniqueID=456 name=訓練教材製作] -> [Task id=223 uniqueID=147 name=教育訓練執行]]]
		        					LinkedList<Relation> relations = (LinkedList<Relation>)value;
		        					
		        					if(relations.size() != 0) {
		        						
		        					}
		        				} else if(key.equalsIgnoreCase("predecessors")) {
		        					//TODO: 有這個key,但這是集合物件,不確定要怎麼塞值
		        					//[[Relation [Task id=219 uniqueID=457 name=製作] -> [Task id=209 uniqueID=893 name=CodeReview]]]
		        					LinkedList<Relation> relations = (LinkedList<Relation>)value;
		        					
		        					if(relations.size() != 0) {
		        						
		        					}
		        				} else if(key.equalsIgnoreCase("splits")) {
		        					//TODO: 有這個key,但這是集合物件,不確定要怎麼塞值
		        					//[[DateRange start=Fri Jul 31 08:00:00 CST 2009 end=Mon Aug 03 14:12:00 CST 2009], [DateRange start=Mon Aug 03 14:12:00 CST 2009 end=Wed Aug 05 12:00:00 CST 2009], [DateRange start=Wed Aug 05 13:00:00 CST 2009 end=Thu Aug 06 16:52:47 CST 2009]]
		        					LinkedList<DateRange> ranges = (LinkedList<DateRange>)value;
		        					
		        					if(ranges.size() != 0) {
		        						
		        					}
//		        				} else if(key.equalsIgnoreCase("actualCost")) {
//		        					TODO: 在ms project裡面,選檢視(V)->表格(B)->成本(C),可以叫出"實際"這個欄位,目前還無法確認如何存入,存入"0.0"會有exception
//		        					Double cost = (Double)value;
//		        					map.put(key, cost.toString());
		        				} else {
		        					map.put(key, value.toString());
		        				}
		        			}
			        	}
		        	}
		        }
		        
		        finalList.add(map);
	        }
        }
		
		return finalList;
	}
	
	public static void putDispatchPtr(DispatchPtr mstasks, List<Map<String, String>> list, int defaultDateFormat) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, DateFormatException, COMException, ParseException  {
		AssertUtility.notNull(list);
		
		String[] excludeKey = getExcludeKeys();
		
		Iterator<Map<String, String>> iterator = list.iterator();
		
		while (iterator.hasNext()) {
        	Map<String, String> task = iterator.next();
        	Iterator<Entry<String, String>> iterator2 = task.entrySet().iterator();
        	
        	DispatchPtr msProjectTask = (DispatchPtr)mstasks.invoke("Add");
	        
	        while(iterator2.hasNext()) {
	        	Entry<String, String> taskFields = iterator2.next();
	        	
	        	try {
	        		if(taskFields.getValue() != null) {
	        			if(!StringUtility.isInArray(taskFields.getKey(), excludeKey)) {
	        				if(StringUtility.isInArray(taskFields.getKey(), getBooleanKeys())) {
	        					//TODO: 等找到這些key值寫入mpp該寫入何值,才能實作轉換的code
	        					if(Boolean.parseBoolean(taskFields.getValue())) {
	        					} else {
	        					}
	        				} else if(StringUtility.isInArray(taskFields.getKey(), getIntegerKeys())) {
	        					//TODO: 等找到這些key值寫入mpp該寫入何值,才能實作轉換的code
//	        					msProjectTask.put(taskFields.getKey(), Integer.parseInt(taskFields.getValue()));
	        				} else if(StringUtility.isInArray(taskFields.getKey(), getDoubleKeys())) {
	        					//TODO: 等找到這些key值寫入mpp該寫入何值,才能實作轉換的code
//	        					msProjectTask.put(taskFields.getKey(), Double.parseDouble(taskFields.getValue()));
	        				} else if(StringUtility.isInArray(taskFields.getKey(), getDateKeys())) {
	        					//TODO: 等找到這些key值寫入mpp該寫入何值,才能實作轉換的code
	        				} else if(StringUtility.isInArray(taskFields.getKey(), getUUIDKeys())) {
	        					//TODO: 等找到這些key值寫入mpp該寫入何值,才能實作轉換的code
	        				} else if(StringUtility.isInArray(taskFields.getKey(), getDurationKeys())) {
	        					//TODO: 等找到這些key值寫入mpp該寫入何值,才能實作轉換的code
	        				} else if(StringUtility.isInArray(taskFields.getKey(), getDurationToDurationFormatKeys())) {
	        					//TODO: 等找到這些key值寫入mpp該寫入何值,才能實作轉換的code
//	        					msProjectTask.put(taskFields.getKey(), taskFields.getValue());
//	        					msProjectTask.put(taskFields.getKey(), Double.parseDouble(taskFields.getValue()));
//	        					msProjectTask.put(taskFields.getKey(), Integer.parseInt(taskFields.getValue()));
	        				} else if(StringUtility.isInArray(taskFields.getKey(), getBooleanTo01Keys())) {
	        					if(Boolean.parseBoolean(taskFields.getValue())) {
	        						msProjectTask.put(taskFields.getKey(), "1");
	        					} else {
	        						msProjectTask.put(taskFields.getKey(), "0");
	        					}
	        				} else if(StringUtility.isInArray(taskFields.getKey(), getDoubleToDoubleObjectKeys())) {
	        					msProjectTask.put(taskFields.getKey(), Double.parseDouble(taskFields.getValue()));
	        				} else {
	        					msProjectTask.put(taskFields.getKey(), taskFields.getValue());
	        				}
	        			}
	        		}
	        	} catch(org.jawin.COMException ex) {
        			System.out.println(taskFields.getKey() + ":" + taskFields.getValue());
	        		String message = ex.getMessage();
//	        		try {
	        			System.out.println(message);
//						System.out.println(new String(message.getBytes("UTF-8"), "ISO-8859-1"));
//						System.out.println(new String(message.getBytes("UTF-8"), "big5"));
//						System.out.println(new String(message.getBytes("ISO-8859-1"), "UTF-8"));
//						System.out.println(new String(message.getBytes("ISO-8859-1"), "big5"));
//						System.out.println(new String(message.getBytes("big5"), "UTF-8"));
//						System.out.println(new String(message.getBytes("big5"), "ISO-8859-1"));
						System.out.println("*************************************************");
//					} catch (UnsupportedEncodingException e) {
//						e.printStackTrace();
//					}
	        	}
	        }
	    }
	}
	
	/**
	 * 這些key值是boolean值,但寫到mpp檔裡面要寫成0或1
	 * @return
	 */
	private static String[] getBooleanTo01Keys() {
		List<String> keys = new ArrayList<String>();
		
		keys.add("subprojectReadOnly");
		keys.add("levelingCanSplit");
		keys.add("milestone");
		keys.add("hideBar");
		keys.add("marked");
		keys.add("rollup");
		keys.add("estimated");
		
		for(int i = 1; i <= 20; i++) {
			keys.add("flag" + i);
		}
		
		String[] keysArray = new String[keys.size()];
		for(int i = 0; i < keys.size(); i++) {
			keysArray[i] = keys.get(i);
		}
		
		return keysArray;
	}
	
	/**
	 * 這些key值是boolean值,但尚未找到寫到mpp檔裡面的值要如何轉換
	 * @return
	 */
	private static String[] getBooleanKeys() {
		List<String> keys = new ArrayList<String>();
		
		keys.add("null"); //可能是isNull
		keys.add("resumeValid");		
		keys.add("levelAssignments");
		keys.add("expanded");
		keys.add("ignoreResourceCalendar");
		keys.add("externalTask");
		keys.add("summary");
		keys.add("confirmed");
		keys.add("critical");
		keys.add("linkedFields");
		keys.add("updateNeeded");
		keys.add("recurring");
		keys.add("effortDriven");
		keys.add("overAllocated");
		
		String[] keysArray = new String[keys.size()];
		for(int i = 0; i < keys.size(); i++) {
			keysArray[i] = keys.get(i);
		}
		
		return keysArray;
	}
	
	/**
	 * 這些key值是Double值,寫到mpp檔要傳入Double物件
	 * @return
	 */
	private static String[] getDoubleToDoubleObjectKeys() {
		List<String> keys = new ArrayList<String>();
		
		keys.add("fixedCost");
		keys.add("cost");
		keys.add("baselineCost");
		for(int i = 1; i <= 20; i++) {
			keys.add("number" + i);
			
			if(i <= 10) {
				keys.add("cost" + i);
			}
		}
		
		String[] keysArray = new String[keys.size()];
		for(int i = 0; i < keys.size(); i++) {
			keysArray[i] = keys.get(i);
		}
		
		return keysArray;
	}
	
	/**
	 * 這些key值是Double值,但尚未找到寫到mpp檔裡面的值要如何轉換
	 * @return
	 */
	private static String[] getDoubleKeys() {
		List<String> keys = new ArrayList<String>();
		
		keys.add("actualCost");
		keys.add("percentageComplete");
		keys.add("percentageWorkComplete");
		keys.add("costVariance");
		keys.add("cV");
		keys.add("remainingCost");
		keys.add("overtimeCost");
		keys.add("actualOvertimeCost");
		keys.add("remainingOvertimeCost");
		
		String[] keysArray = new String[keys.size()];
		for(int i = 0; i < keys.size(); i++) {
			keysArray[i] = keys.get(i);
		}
		
		return keysArray;
	}
	
	/**
	 * 這些key值是Date值,但尚未找到寫到mpp檔裡面的值要如何轉換
	 * @return
	 */
	private static String[] getDateKeys() {
		List<String> keys = new ArrayList<String>();
		
		keys.add("createDate");
		keys.add("lateStart");
		keys.add("lateFinish");
		keys.add("earlyFinish");
		keys.add("earlyStart");
		keys.add("resume");
		keys.add("completeThrough");
		keys.add("summaryProgress");
		keys.add("splitCompleteDuration");
		
		String[] keysArray = new String[keys.size()];
		for(int i = 0; i < keys.size(); i++) {
			keysArray[i] = keys.get(i);
		}
		
		return keysArray;
	}
	
	/**
	 * 這些key值是Duration值,但尚未找到寫到mpp檔裡面的值要如何轉換
	 * @return
	 */
	private static String[] getDurationToDurationFormatKeys() {
		List<String> keys = new ArrayList<String>();
		
		keys.add("levelingDelay");
		
		String[] keysArray = new String[keys.size()];
		for(int i = 0; i < keys.size(); i++) {
			keysArray[i] = keys.get(i);
		}
		
		return keysArray;
	}
	
	/**
	 * 這些key值是Duration值,但尚未找到寫到mpp檔裡面的值要如何轉換
	 * @return
	 */
	private static String[] getDurationKeys() {
		List<String> keys = new ArrayList<String>();
		
		keys.add("startVariance");
		keys.add("finishVariance");
		keys.add("durationVariance");
		keys.add("freeSlack");
		keys.add("totalSlack");
		keys.add("workVariance");
		keys.add("actualOvertimeWork");
		keys.add("remainingOvertimeWork");
		keys.add("startSlack");
		keys.add("finishSlack");
		
		String[] keysArray = new String[keys.size()];
		for(int i = 0; i < keys.size(); i++) {
			keysArray[i] = keys.get(i);
		}
		
		return keysArray;
	}
	
	/**
	 * 這些key值是Integer值,但尚未找到寫到mpp檔裡面的值要如何轉換
	 * @return
	 */
	private static String[] getIntegerKeys() {
		List<String> keys = new ArrayList<String>();
		
		keys.add("subprojectTaskUniqueID");
		keys.add("subprojectTaskID");
		keys.add("subprojectTasksUniqueIDOffset");
		keys.add("iD");
		keys.add("uniqueID"); //可能是UID
		
		String[] keysArray = new String[keys.size()];
		for(int i = 0; i < keys.size(); i++) {
			keysArray[i] = keys.get(i);
		}
		
		return keysArray;
	}
	
	/**
	 * 這些key值是UUID值,但尚未找到寫到mpp檔裡面的值要如何轉換
	 * @return
	 */
	private static String[] getUUIDKeys() {
		List<String> keys = new ArrayList<String>();
		
		keys.add("gUID");
		
		String[] keysArray = new String[keys.size()];
		for(int i = 0; i < keys.size(); i++) {
			keysArray[i] = keys.get(i);
		}
		
		return keysArray;
	}
	
	private static String[] getExcludeKeys() {
		List<String> keys = new ArrayList<String>();
		
		//無效的項目 (grid元件加進去的欄位)
		keys.add("FormId");
		keys.add("TableId");
		keys.add("ProjectId");
		keys.add("SerialNumber");
		keys.add("RowIndex");
		keys.add("HeaderRowCount");
		keys.add("groupType");
		keys.add("groupId");
		
		//層次應該是不至於超過20,所以先用20來當作magic number
		int maxLevel = 20;
		for(int i = 0; i <= maxLevel; i++) {
			keys.add("level" + i);
		}
		
		//TODO: 8002000e: 無?滌???
		keys.add("outlineNumber");
		
		String[] keysArray = new String[keys.size()];
		for(int i = 0; i < keys.size(); i++) {
			keysArray[i] = keys.get(i);
		}
		
		return keysArray;
	}
}
