package csiebug.web.html.timeline;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import csiebug.util.AssertUtility;
import csiebug.util.StringUtility;
import csiebug.util.WebUtility;
import csiebug.web.html.HtmlBuilder;
import csiebug.web.html.HtmlComponentNoBody;
import csiebug.web.html.HtmlRenderException;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;


/**
 * 產生HTML Timeline
 * @author George_Tsai
 * @version 2010/3/18
 */

public class HtmlTimeline extends HtmlComponentNoBody {
	private String timelineId;
	private Integer timelineHeight;
	private String timelineDataPath;
	private String sourceXMLFileName;
	
	private String dataSource;
	
	private boolean openHourBandFlag;
	private Integer hourBandWidthPercentage;
	private boolean openDayBandFlag;
	private Integer dayBandWidthPercentage;
	private boolean openMonthBandFlag;
	private Integer monthBandWidthPercentage;
	private boolean openYearBandFlag;
	private Integer yearBandWidthPercentage;
	
	private List<Map<String, String>> timelineData;
	
	private WebUtility webutil = new WebUtility();
	
	FilenameFilter filter;
	
	private int bandCounter = 0;
	
	/**
	 * timeline建構子
	 * @author George_Tsai
	 * @version 2010/3/18
	 */
	public HtmlTimeline(String id, Integer timelineHeight, String timelineDataPath, boolean openHourBandFlag, Integer hourBandWidthPercentage, boolean openDayBandFlag, Integer dayBandWidthPercentage, boolean openMonthBandFlag, Integer monthBandWidthPercentage, boolean openYearBandFlag, Integer yearBandWidthPercentage, List<Map<String, String>> data, String dataSource) {
		this.timelineId = id;
		this.timelineHeight = timelineHeight;
		this.timelineDataPath = StringUtility.removeStartEndSlash(timelineDataPath);
		this.openHourBandFlag = openHourBandFlag;
		this.hourBandWidthPercentage = hourBandWidthPercentage;
		this.openDayBandFlag = openDayBandFlag;
		this.dayBandWidthPercentage = dayBandWidthPercentage;
		this.openMonthBandFlag = openMonthBandFlag;
		this.monthBandWidthPercentage = monthBandWidthPercentage;
		this.openYearBandFlag = openYearBandFlag;
		this.yearBandWidthPercentage = yearBandWidthPercentage;
		this.timelineData = data;
		this.dataSource = dataSource;
		
		if(dataSource != null && dataSource.trim().equalsIgnoreCase("xml")) {
			//用登入者和session id就可以判斷檔案的有效期限
			sourceXMLFileName = timelineId + "-" + webutil.getLoginUserId() + "-" + webutil.getSession().getId() + ".xml";
			
			//FilenameFilter
			filter = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					try {
						WebUtility webutil = new WebUtility();
						File currFile = new File(dir, name);
						if (currFile.isFile() && name.indexOf(timelineId + "-" + webutil.getLoginUserId() + "-") != -1) {
							return (name.indexOf(sourceXMLFileName) == -1);
						} else {
					    	return false;
					    }
					} catch(Exception ex) {
		        		webutil.writeErrorLog(ex);
						return false;
					}
				}
			};
		}
	}
	
	/**
	 * 刪除過期的XML檔案
	 * @throws Exception
	 */
	private void deleteExpireXMLFile() {
		File xmlDir = getXmlDirInstance();
		
		if(xmlDir.exists() && xmlDir.isDirectory()) {
			File[] xmlFiles = xmlDir.listFiles(filter);
			
			for(int i = xmlFiles.length - 1; i >= 0; i--) {
				if(!xmlFiles[i].delete()) {
					webutil.getLogger(this.getClass().getName()).warn(xmlFiles[i].getName() + " was deleted failed!");
				}
			}
		}
	}
	
	private File getXmlDirInstance() {
		File xmlDir;
		
		if(timelineDataPath != null) {
			xmlDir = new File(webutil.getServletContext().getRealPath("/") + "/" + timelineDataPath);
		} else {
			xmlDir = new File(webutil.getServletContext().getRealPath("/") + "/timeline");
		}
		
		return xmlDir;
	}
	
	/**
	 * 製作本次所需要的XML資料檔案
	 * @throws IOException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalArgumentException 
	 * @throws Exception
	 */
	private void makeXMLFile() throws IOException, IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
		File xmlFile;
		if(timelineDataPath != null) {
			xmlFile = new File(webutil.getServletContext().getRealPath("/") + "/" + timelineDataPath + "/" + sourceXMLFileName);
		} else {
			xmlFile = new File(webutil.getServletContext().getRealPath("/") + "/timeline/" + sourceXMLFileName);
		}
		
		Document document = DocumentHelper.createDocument();
		Element rootElement = document.addElement("data");
		
		addTimelineData(rootElement);
		
		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter writer = new XMLWriter(new FileWriter(xmlFile), format);
		
		writer.write(document);
		writer.close();
	}
	
	private void addTimelineData(Element rootElement) {
		AssertUtility.notNull(rootElement);
		
		if(timelineData != null) {
			for(int i = 0; i < timelineData.size(); i++) {
				Element eventElement = rootElement.addElement("event");
				
				Map<String, String> eventData = timelineData.get(i);
				
				addEventAttribute(eventElement, eventData, "start");
				addEventAttribute(eventElement, eventData, "end");
				addEventAttribute(eventElement, eventData, "isDuration");
				addEventAttribute(eventElement, eventData, "title");
				addEventAttribute(eventElement, eventData, "link");
				addEventAttribute(eventElement, eventData, "image");
				addEventAttribute(eventElement, eventData, "icon");
				addEventAttribute(eventElement, eventData, "color");
				addEventAttribute(eventElement, eventData, "textColor");
				addEventAttribute(eventElement, eventData, "caption");
				
				if(eventData.get("description") != null) {
					eventElement.addText(eventData.get("description"));
				}
			}
		}
	}
	
	private void addEventAttribute(Element eventElement, Map<String, String> data, String attributeName) {
		if(data.get(attributeName) != null) {
			eventElement.addAttribute(attributeName, data.get(attributeName));
		}
	}
	
	private String makeEventBands() {
		StringBuffer script = new StringBuffer();
		
		List<String> bands = new ArrayList<String>();
		List<Integer> widths = new ArrayList<Integer>();
		
		if(openHourBandFlag) {
			bands.add("hour");
			
			if(hourBandWidthPercentage != null) {
				widths.add(hourBandWidthPercentage);
			} else {
				widths.add(null);
			}
		}
		
		if(openDayBandFlag) {
			bands.add("day");
			
			if(dayBandWidthPercentage != null) {
				widths.add(dayBandWidthPercentage);
			} else {
				widths.add(null);
			}
		}
		
		if(openMonthBandFlag) {
			bands.add("month");
			
			if(monthBandWidthPercentage != null) {
				widths.add(monthBandWidthPercentage);
			} else {
				widths.add(null);
			}
		}
		
		if(openYearBandFlag) {
			bands.add("year");
			
			if(yearBandWidthPercentage != null) {
				widths.add(yearBandWidthPercentage);
			} else {
				widths.add(null);
			}
		}
		
		if(containNull(widths)) {
			widths = layoutBands(widths);
		}
		
		bandCounter = bands.size();
		
		for(int i = 0; i < bands.size(); i++) {
			script.append(makeEventBand(bands.get(i), widths.get(i)));
			
			if(i < (bands.size() - 1)) {
				script.append(",");
			}
			script.append("\n");
		}
		
		return script.toString();
	}
	
	private void makeJSONData() {
		StringBuffer jsonData = new StringBuffer();
		jsonData.append("{\n");
		jsonData.append("'events' : [\n");
		
		if(timelineData != null) {
			for(int i = 0; i < timelineData.size(); i++) {
				jsonData.append("{");
				
				Map<String, String> eventData = timelineData.get(i);
				
				StringBuffer temp = new StringBuffer();
				addEventAttribute(temp, eventData, "start");
				addEventAttribute(temp, eventData, "end");
				addEventAttribute(temp, eventData, "isDuration");
				addEventAttribute(temp, eventData, "title");
				addEventAttribute(temp, eventData, "link");
				addEventAttribute(temp, eventData, "image");
				addEventAttribute(temp, eventData, "icon");
				addEventAttribute(temp, eventData, "color");
				addEventAttribute(temp, eventData, "textColor");
				addEventAttribute(temp, eventData, "caption");
				addEventAttribute(temp, eventData, "description");
				
				//去掉最後的逗號
				jsonData.append(temp.toString().subSequence(0, temp.length() - 2));
				if(i < timelineData.size() - 1) {
					jsonData.append("},\n");
				} else {
					jsonData.append("}\n");
				}
			}
		}
		
		jsonData.append("]\n");
		jsonData.append("}\n");
		
		webutil.setSessionAttribute("timelineJSONData", jsonData.toString());
	}
	
	private void addEventAttribute(StringBuffer jsonData, Map<String, String> data, String attributeName) {
		if(data.get(attributeName) != null) {
			jsonData.append("'" + attributeName + "': '" + data.get(attributeName) + "', ");
		}
	}
	
	private List<Integer> layoutBands(List<Integer> list) {
		List<Integer> widths = new ArrayList<Integer>();
		
		if(list.size() == 1) {
			layoutOneBand(widths);
		} else if(list.size() == 2) {
			layoutTwoBands(widths, list);
		} else if(list.size() == 3) {
			layoutThreeBands(widths, list);
		} else if(list.size() == 4) {
			layoutFourBands(widths, list);
		}
		
		return widths;
	}
	
	private void layoutOneBand(List<Integer> widths) {
		widths.add(100);
	}
	
	private void layoutTwoBands(List<Integer> widths, List<Integer> list) {
		if(isAllNull(list)) {
			widths.add(64);
			widths.add(36);
		} else {
			Integer w0 = list.get(0);
			Integer w1 = list.get(1);
			
			if(w0 == null) {
				widths.add(100 - w1);
				widths.add(w1);
			} else {
				widths.add(w0);
				widths.add(100 - w0);
			}
		}
	}
	
	private void layoutThreeBands(List<Integer> widths, List<Integer> list) {
		if(isAllNull(list)) {
			widths.add(64);
			widths.add(18);
			widths.add(18);
		} else {
			Integer w0 = list.get(0);
			Integer w1 = list.get(1);
			Integer w2 = list.get(2);
			
			if(nullCount(list) == 1) {
				if(w0 == null) {
					widths.add(100 - w1 - w2);
					widths.add(w1);
					widths.add(w2);
				} else if(w1 == null) {
					widths.add(w0);
					widths.add(100 - w0 - w2);
					widths.add(w2);
				} else {
					widths.add(w0);
					widths.add(w1);
					widths.add(100 - w0 - w1);
				}
			} else {
				if(w0 != null) {
					widths.add(w0);
					widths.add((100 - w0) / 2);
					widths.add((100 - w0) / 2);
				} else if(w1 != null) {
					widths.add((100 - w1) * 2 / 3);
					widths.add(w1);
					widths.add((100 - w1) / 3);
				} else {
					widths.add((100 - w2) * 2 / 3);
					widths.add((100 - w2) / 3);
					widths.add(w2);
				}
			}
		}
	}
	
	private void layoutFourBands(List<Integer> widths, List<Integer> list) {
		if(isAllNull(list)) {
			widths.add(64);
			widths.add(12);
			widths.add(12);
			widths.add(12);
		} else {
			Integer w0 = list.get(0);
			Integer w1 = list.get(1);
			Integer w2 = list.get(2);
			Integer w3 = list.get(3);
			
			if(nullCount(list) == 1) {
				if(w0 == null) {
					widths.add(100 - w1 - w2 - w3);
					widths.add(w1);
					widths.add(w2);
					widths.add(w3);
				} else if(w1 == null) {
					widths.add(w0);
					widths.add(100 - w0 - w2 - w3);
					widths.add(w2);
					widths.add(w3);
				} else if(w2 == null) {
					widths.add(w0);
					widths.add(w1);
					widths.add(100 - w0 - w1 - w3);
					widths.add(w3);
				} else {
					widths.add(w0);
					widths.add(w1);
					widths.add(w2);
					widths.add(100 - w0 - w1 - w2);
				}
			} else if(nullCount(list) == 3) {
				if(w0 != null) {
					widths.add(w0);
					widths.add((100 - w0) / 3);
					widths.add((100 - w0) / 3);
					widths.add((100 - w0) / 3);
				} else if(w1 != null) {
					widths.add((100 - w1) / 2);
					widths.add(w1);
					widths.add((100 - w1) / 4);
					widths.add((100 - w1) / 4);
				} else if(w2 != null) {
					widths.add((100 - w2) / 2);
					widths.add((100 - w2) / 4);
					widths.add(w2);
					widths.add((100 - w2) / 4);
				} else {
					widths.add((100 - w3) / 2);
					widths.add((100 - w3) / 4);
					widths.add((100 - w3) / 4);
					widths.add(w3);
				}
			} else {
				if(w0 != null) {
					if(w1 != null) {
						widths.add(w0);
						widths.add(w1);
						widths.add((100 - w0 - w1) / 2);
						widths.add((100 - w0 - w1) / 2);
					} else if(w2 != null) {
						widths.add(w0);
						widths.add((100 - w0 - w2) / 2);
						widths.add(w2);
						widths.add((100 - w0 - w2) / 2);
					} else {
						widths.add(w0);
						widths.add((100 - w0 - w3) / 2);
						widths.add((100 - w0 - w3) / 2);
						widths.add(w3);
					}
				} else {
					if(w1 == null) {
						widths.add((100 - w2 - w3) * 2 / 3);
						widths.add((100 - w2 - w3) / 3);
						widths.add(w2);
						widths.add(w3);
					} else if(w2 == null) {
						widths.add((100 - w1 - w3) * 2 / 3);
						widths.add(w1);
						widths.add((100 - w1 - w3) / 3);
						widths.add(w3);
					} else {
						widths.add((100 - w1 - w2) * 2 / 3);
						widths.add(w1);
						widths.add(w2);
						widths.add((100 - w1 - w2) / 3);
					}
				}
			}
		}
	}
	
	private boolean containNull(List<Integer> list) {
		boolean flag = false;
		
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i) == null) {
				return true;
			}
		}
		
		return flag;
	}
	
	private boolean isAllNull(List<Integer> list) {
		boolean flag = true;
		
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i) != null) {
				return false;
			}
		}
		
		return flag;
	}
	
	private int nullCount(List<Integer> list) {
		int counter = 0;
		
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i) == null) {
				counter++;
			}
		}
		
		return counter;
	}
	
	private String makeEventBand(String type, int width) {
		AssertUtility.notNullAndNotSpace(type);
		
		StringBuffer script = new StringBuffer();
		
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		int day = now.get(Calendar.DAY_OF_MONTH);
		int hour = now.get(Calendar.HOUR_OF_DAY);
		
		if(type.trim().equalsIgnoreCase("hour") ||
		   type.trim().equalsIgnoreCase("day") ||
		   type.trim().equalsIgnoreCase("month")) {
			script.append("Timeline.createBandInfo({\n");
			script.append("eventSource:    eventSource,\n");
			script.append("date:           \"" + year + "/" + month + "/" +  day + " " + hour + ":00:00 GMT\",\n");
			script.append("width:          \"" + width + "%\", \n");
			script.append("intervalUnit:   Timeline.DateTime." + type.toUpperCase() + ",\n");
			script.append("intervalPixels: 100");
			script.append("})");
		} else if(type.trim().equalsIgnoreCase("year")) {
			script.append("Timeline.createBandInfo({\n");
			script.append("eventSource:    eventSource,\n");
			script.append("date:           \"" + year + "/" + month + "/" +  day + " " + hour + ":00:00 GMT\",\n");
			script.append("width:          \"" + width + "%\", \n");
			script.append("intervalUnit:   Timeline.DateTime." + type.toUpperCase() + ",\n");
			script.append("intervalPixels: 200");
			script.append("})");
		}
		
		return script.toString();
	}
	
	private void makeOnLoadScript() throws NamingException {
		StringBuffer script = new StringBuffer();
		script.append("var eventSource = new Timeline.DefaultEventSource();\n");
		script.append("var bandInfos = [\n");
		script.append(makeEventBands());
		script.append("];\n");
		
		if(bandCounter > 1) {
			for(int i = 0; i < bandCounter - 1; i++) {
				script.append("bandInfos[" + (i + 1) + "].syncWith = " + i + ";\n");
				script.append("bandInfos[" + (i + 1) + "].highlight = true;\n");
			}
		}
		
		script.append("tl = Timeline.create(document.getElementById(\"" + timelineId + "\"), bandInfos);\n");
		
		if(timelineData != null) {
			if(dataSource != null && dataSource.trim().equalsIgnoreCase("xml")) {
				script.append("var url = '" + webutil.getBasePathForHTML() + "';\n");
				if(timelineDataPath != null) {
					script.append("Timeline.loadXML(\"" + webutil.getBasePathForHTML() + timelineDataPath + "/" + sourceXMLFileName + "\", function(xml, url) { eventSource.loadXML(xml, url); });\n");
				} else {
					script.append("Timeline.loadXML(\"" + webutil.getBasePathForHTML() + "timeline/" + sourceXMLFileName + "\", function(xml, url) { eventSource.loadXML(xml, url); });\n");
				}
			} else {
				if(timelineDataPath != null) {
					script.append("Timeline.loadJSON(\"" + webutil.getBasePathForHTML() + timelineDataPath + "timelineJSONData.jsp?\"+ (new Date().getTime()), function(json, url) {\n");
				} else {
					script.append("Timeline.loadJSON(\"" + webutil.getBasePathForHTML() + "timeline/timelineJSONData.jsp?\"+ (new Date().getTime()), function(json, url) {\n");
				}
	            script.append("    eventSource.loadJSON(json, url);\n");
	            script.append("});\n");
			}
		}
		
		webutil.addPageLoadScript(script.toString());
	}
	
	public String renderStart() throws HtmlRenderException {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		try {
			if(dataSource != null && dataSource.trim().equalsIgnoreCase("xml")) {
				deleteExpireXMLFile();
				if(timelineData != null) {
					makeXMLFile();
				}
			} else {
				makeJSONData();
			}
			makeOnLoadScript();
			
			if(timelineHeight != null) {
				htmlBuilder.divStart().id(timelineId).style("height: " + timelineHeight + "px; border: 1px solid #aaa");
			} else {
				htmlBuilder.divStart().id(timelineId).style("height: 500px; border: 1px solid #aaa");
			}
			
			htmlBuilder.tagClose();
		} catch (IllegalArgumentException e) {
			throw new HtmlRenderException(e);
		} catch (IOException e) {
			throw new HtmlRenderException(e);
		} catch (ClassNotFoundException e) {
			throw new HtmlRenderException(e);
		} catch (IllegalAccessException e) {
			throw new HtmlRenderException(e);
		} catch (InvocationTargetException e) {
			throw new HtmlRenderException(e);
		} catch (NamingException e) {
			throw new HtmlRenderException(e);
		}
		
		return htmlBuilder.toString();
	}
	
	public String renderEnd() {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		htmlBuilder.divEnd();
		
		return htmlBuilder.toString();
	}
}
