package csiebug.web.html.office.project;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import csiebug.util.AssertUtility;
import csiebug.util.ListException;
import csiebug.util.ListUtility;
import csiebug.util.StringUtility;
import csiebug.util.WebUtility;
import csiebug.web.html.HtmlBuilder;
import csiebug.web.html.HtmlComponentNoBody;
import csiebug.web.html.HtmlRenderException;


/**
 * 產生HTML Table
 * @author George_Tsai
 * @version 2009/6/16
 */

public class HtmlTable extends HtmlComponentNoBody implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String htmlId;
	private String name;
	private String style;
	private String className;
	private String data;
	private String downloadFileName;
	private String imagePath;
	private String formId;
	private String projectId;
	private String downloadPartial;
	private String downloadColumns;
	private String highlight;
	private String isReadOnly;
	private String downloadMPP;
	
	private int currentMapIndex;
	private int headerRowCount;
	private int maxColumnCount;
	private int maxTaskId;
	private WebUtility webutil = new WebUtility();
	
	public HtmlTable(String htmlId, String name, String projectId, String style, String className, String data, String downloadFileName, String imagePath, String formId, String downloadPartial, String downloadColumns, String downloadMPP, String highlight, String isReadOnly) {
		this.htmlId = htmlId;
		this.name = name;
		this.projectId = projectId;
		this.style = style;
		this.className = className;
		this.data = data;
		this.downloadFileName = downloadFileName;
		this.imagePath = StringUtility.removeStartEndSlash(imagePath);
		this.formId = formId;
		this.downloadPartial = downloadPartial;
		this.downloadColumns = downloadColumns;
		this.downloadMPP = downloadMPP;
		this.highlight = highlight;
		this.isReadOnly = isReadOnly;
	}
	
	public HtmlTable() {
		
	}
	
	@SuppressWarnings("unchecked")
	public String renderStart() throws HtmlRenderException {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		try {
			currentMapIndex = -1;
	        maxColumnCount = 0;
	        headerRowCount = 1;
	        maxTaskId = -1;
	        if(htmlId != null) { //Session中存的要for download excel用的資料,在grid重新render的時候都要清除重做
	        	webutil.removeSessionAttribute(htmlId + "_DownloadTitles");
	        	webutil.removeSessionAttribute(htmlId + "_DownloadValues");
	        	webutil.removeSessionAttribute(htmlId + "_DownloadDataTypes");
	        }
	        
	        //使用 Group By 顯示隱藏功能
			//必須先把grid資料做好排序才行
			List<Map<String, String>> list = (List<Map<String, String>>)webutil.getRequestAttribute(data);
			
			//先算出樹狀結構最深層級
			int maxLevel = 1;
			for(int i = 0; i < list.size(); i++) {
				Map<String, String> task = list.get(i);
				
				if(maxLevel < task.get("outlineNumber").split("\\.").length) {
					maxLevel = task.get("outlineNumber").split("\\.").length;
				}
			}
			
			//塞入要做group by的動態column
			//(動態欄位,個數不固定,要看樹狀結構有多少層)
			String[] groupByColumns = new String[maxLevel];
			List<Map<String, String>> dynaData = new ArrayList<Map<String, String>>();
			for(int i = 0; i < maxLevel; i++) {
				Map<String, String> map = new LinkedHashMap<String, String>();
				map.put("fieldname", "level" + i);
				map.put("style", "display:none");
				map.put("dataType", "number");
				
				groupByColumns[i] = "level" + i;
				
				dynaData.add(map);
			}
			webutil.setRequestAttribute(data + "_level", dynaData);
			
			//塞入要做group by的動態column資料
			List<Map<String, String>> list2 = new ArrayList<Map<String,String>>();
			for(int i = 0; i < list.size(); i++) {
				Map<String, String> task = list.get(i);
				Map<String, String> map = new LinkedHashMap<String, String>();
				
				String[] level = task.get("outlineNumber").split("\\.");
				
				//(動態欄位,個數不固定,要看樹狀結構有多少層)
				for(int j = 0; j < maxLevel; j++) {
					if(j < level.length) {
						map.put("level" + j, level[j]);
					} else {
						map.put("level" + j, "");
					}
				}
				
				list2.add(map);
			}
			
			//動態column資料要做join
			list = ListUtility.join(list, list2);
			list = ListUtility.sortByMultipleNameGrouping(list, groupByColumns);
			
			webutil.removeRequestAttribute(data);
			webutil.setRequestAttribute(data, list);
			
	        //建立grid toolbar
	        HtmlToolBar htmlToolbar = new HtmlToolBar(htmlId, formId, data, downloadFileName, imagePath, downloadPartial, downloadColumns, downloadMPP, isReadOnly);
	        htmlBuilder.appendString(htmlToolbar.render());
	        
	        //grid主體開始
	        htmlBuilder.tableStart();
	        
	//      設定各樣屬性
	        if(htmlId != null) {
	        	htmlBuilder.id(htmlId);
	        	
	        	if(!AssertUtility.isTrue(isReadOnly)) {
		        	htmlBuilder.tabIndex("0");
			        htmlBuilder.onKeyDown("projectGridOnKeyDown('" + formId + "', '" + htmlId + "', 1, '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.error.required4") + "', '" + webutil.getMessage("common.ok") + "', '" + webutil.getMessage("common.confirm.copy") + "', '" + webutil.getMessage("common.cancel") + "', event);");
	        	}
	        }
	        
	        if(name != null) {
	        	htmlBuilder.name(name);
	        }
	        
	        if(style != null) {
	        	htmlBuilder.style(style);
	        }
	        
	        if(className != null) {
	        	htmlBuilder.className(className);
	        } else {
	        	htmlBuilder.className("TableRegion");
	        }
	        
	        htmlBuilder.tagClose();
	    } catch (NumberFormatException e) {
			throw new HtmlRenderException(e);
		} catch (NamingException e) {
			throw new HtmlRenderException(e);
		} catch (UnsupportedEncodingException e) {
			throw new HtmlRenderException(e);
		} catch (ListException e) {
			throw new HtmlRenderException(e);
		}
        
		return htmlBuilder.toString();
	}
	
	public String renderEnd() throws HtmlRenderException {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
        
		htmlBuilder.tableEnd();
	    
        if(htmlId != null) {
        	htmlBuilder.inputStart().type("hidden").id(htmlId + "_maxTaskId").name(htmlId + "_maxTaskId").value("" + maxTaskId).tagClose().inputEnd();
        	
        	htmlBuilder.inputStart().type("hidden").id(htmlId + "_projectId").name(htmlId + "_projectId");
        	if(projectId != null) {
        		htmlBuilder.value(projectId);
        	}
        	htmlBuilder.tagClose().inputEnd();
        }
		
        return htmlBuilder.toString();
	}
	
//	元件屬性區
	public void setId(String id) {
		this.htmlId = id;
	}
	
	public String getId() {
		return this.htmlId;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setStyle(String value) {
		this.style = value;
	}
	
	public String getStyle() {
		return this.style;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getClassName() {
		return this.className;
	}
	
	public void setData(String value) {
		this.data = value;
	}
	
	public String getData() {
		return this.data;
	}
	
	public void setCurrentMapIndex(int value) {
		this.currentMapIndex = value;
	}
	
	public int getCurrentMapIndex() {
		return this.currentMapIndex;
	}
	
	public void setHeaderRowCount(int value) {
		this.headerRowCount = value;
	}
	
	public int getHeaderRowCount() {
		return this.headerRowCount;
	}
	
	public void setMaxColumnCount(int value) {
		this.maxColumnCount = value;
	}
	
	public int getMaxColumnCount() {
		return this.maxColumnCount;
	}
	
	public void setDownloadFileName(String value) {
		this.downloadFileName = value;
	}
	
	public String getDownloadFileName() {
		return this.downloadFileName;
	}
	
	public void setImagePath(String value) {
		this.imagePath = StringUtility.removeStartEndSlash(value);
	}
	
	public String getImagePath() {
		return this.imagePath;
	}
	
	public void setDownloadPartial(String value) {
		this.downloadPartial = value;
	}
	
	public String getDownloadPartial() {
		return this.downloadPartial;
	}
	
	public void setHighlight(String highlight) {
		this.highlight = highlight;
	}

	public String getHighlight() {
		return highlight;
	}

	public void setDownloadColumns(String downloadColumns) {
		this.downloadColumns = downloadColumns;
	}

	public String getDownloadColumns() {
		return downloadColumns;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getFormId() {
		return formId;
	}

	public void setMaxTaskId(int maxTaskId) {
		this.maxTaskId = maxTaskId;
	}

	public int getMaxTaskId() {
		return maxTaskId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setIsReadOnly(String isReadOnly) {
		this.isReadOnly = isReadOnly;
	}

	public String getIsReadOnly() {
		return isReadOnly;
	}

	public void setDownloadMPP(String downloadMPP) {
		this.downloadMPP = downloadMPP;
	}

	public String getDownloadMPP() {
		return downloadMPP;
	}
}
