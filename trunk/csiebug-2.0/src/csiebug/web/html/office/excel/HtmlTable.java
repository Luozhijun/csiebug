package csiebug.web.html.office.excel;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

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
	
	private int currentMapIndex;
	private int headerRowCount;
	private int maxColumnCount;
	private int maxTaskId;
	private WebUtility webutil = new WebUtility();
	
	public HtmlTable(String htmlId, String name, String projectId, String style, String className, String data, String downloadFileName, String imagePath, String formId, String downloadPartial, String downloadColumns, String highlight) {
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
		this.highlight = highlight;
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
	        
	        List<Map<String, String>> list = (List<Map<String, String>>)webutil.getRequestAttribute(data);
	        list = ListUtility.sortByName(list, "order");
			
			webutil.removeRequestAttribute(data);
			webutil.setRequestAttribute(data, list);
			
	        //建立grid toolbar
	        HtmlToolBar htmlToolbar = new HtmlToolBar(htmlId, formId, data, downloadFileName, imagePath, downloadPartial, downloadColumns);
	        htmlBuilder.appendString(htmlToolbar.render());
	        
	        //grid主體開始
	        htmlBuilder.tableStart();
	        
	//      設定各樣屬性
	        if(htmlId != null) {
	        	htmlBuilder.id(htmlId);
	        	
	        	htmlBuilder.tabIndex("0");
			    htmlBuilder.onKeyDown("excelGridOnKeyDown('" + formId + "', '" + htmlId + "', 1, '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.error.required4") + "', '" + webutil.getMessage("common.ok") + "', '" + webutil.getMessage("common.confirm.copy") + "', '" + webutil.getMessage("common.cancel") + "', event);");
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

}
