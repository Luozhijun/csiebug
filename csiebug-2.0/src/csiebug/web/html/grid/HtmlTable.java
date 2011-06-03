package csiebug.web.html.grid;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import csiebug.util.AssertUtility;
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
	private int pagination;
	private String toolbar;
	private String xlsName;
	private String imagePath;
	private String formId;
	private String downloadPartial;
	private String downloadColumns;
	private String statusbar;
	private String statusBarPosition;
	private String highlight;
	private Boolean noDataStringFlag;
	private String groupByColumns;
	private String groupOp;
	
	private int currentMapIndex;
	private int headerRowCount;
	private int maxColumnCount;
	private boolean setPagination = false;
	private WebUtility webutil = new WebUtility();
	
	public HtmlTable(String htmlId, String name, String style, String className, String data, int pagination, String toolbar, String xlsName, String imagePath, String formId, String downloadPartial, String downloadColumns, String statusbar, String statusBarPosition, String highlight, Boolean noDataStringFlag, String groupByColumns, String groupOp) {
		this.htmlId = htmlId;
		this.name = name;
		this.style = style;
		this.className = className;
		this.data = data;
		this.pagination = pagination;
		this.toolbar = toolbar;
		if(pagination > 0) {
			this.setPagination = true;
		}
		this.xlsName = xlsName;
		this.imagePath = StringUtility.removeStartEndSlash(imagePath);
		this.formId = formId;
		this.downloadPartial = downloadPartial;
		this.downloadColumns = downloadColumns;
		this.statusbar = statusbar;
		this.statusBarPosition = statusBarPosition;
		this.highlight = highlight;
		this.noDataStringFlag = noDataStringFlag;
		this.groupByColumns = groupByColumns;
		this.groupOp = groupOp;
	}
	
	public HtmlTable() {
		
	}
	
	@SuppressWarnings("unchecked")
	public String renderStart() throws HtmlRenderException {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		try {
			if(!setPagination && webutil.getEnvVariable("defaultPagination") != null) {
				pagination = Integer.parseInt(webutil.getEnvVariable("defaultPagination"));
			}
			
			currentMapIndex = -1;
	        maxColumnCount = 0;
	        setHeaderRowCountByStatusBarPosition();
	        if(htmlId != null) { //Session中存的要for download excel用的資料,在grid重新render的時候都要清除重做
	        	webutil.removeSessionAttribute(htmlId + "_DownloadTitles");
	        	webutil.removeSessionAttribute(htmlId + "_DownloadValues");
	        	webutil.removeSessionAttribute(htmlId + "_DownloadDataTypes");
	        }
	        
	        //沒有資料不show空的grid，而用文字替代用
	        if(noDataStringFlag != null && noDataStringFlag.booleanValue() && data != null && webutil.getRequestAttribute(data) != null && ((List<Map<String, String>>)webutil.getRequestAttribute(data)).size() == 0) {
				htmlBuilder.divStart().tagClose();
				htmlBuilder.appendString(webutil.getMessage("common.query.noData"));
				htmlBuilder.divEnd();
				
				htmlBuilder.divStart().style("display:none").tagClose();
			} else if(AssertUtility.isNotNullAndNotSpace(groupByColumns)) {
				//如果有設groupByColumns,代表要使用 Group By 顯示隱藏功能
				//必須先把grid資料做好排序才行
				List<Map<String, String>> list = (List<Map<String, String>>)webutil.getRequestAttribute(data);
				
				if(groupOp != null && groupOp.equalsIgnoreCase("insertGroupData")) {
					list = ListUtility.sortByMultipleNameWithInsertGroupData(list, groupByColumns.split(","));
				} else {
					list = ListUtility.sortByMultipleNameGrouping(list, groupByColumns.split(","));
				}
				
				webutil.removeRequestAttribute(data);
				webutil.setRequestAttribute(data, list);
				setPagination(0);
			}
	        
	        //建立grid toolbar
	        //如果status bar被設定在grid上面的話，那就不產生獨立的toolbar
	        //把toolbar按鈕整合到status bar上面
	        HtmlToolBar htmlToolbar = new HtmlToolBar(htmlId, formId, data, xlsName, imagePath, downloadPartial, downloadColumns);
	        
	        if(AssertUtility.isTrue(toolbar) && !(statusBarPosition != null && statusBarPosition.equalsIgnoreCase("up"))) {
		        htmlBuilder.appendString(htmlToolbar.render());
	        }
	        
	        //grid主體開始
	        htmlBuilder.tableStart();
	        
	//      設定各樣屬性
	        if(htmlId != null) {
	        	htmlBuilder.id(htmlId);
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
	        
	        if(statusBarPosition != null && statusBarPosition.equalsIgnoreCase("up")) {
	        	//產生Status Bar
	        	//要在table最上面產生status bar,沒有辦法得到最後的column數
	        	//無法計算應該colspan多少，只好給一個極大的數字，讓status bar可以跟table寬度一樣
	        	int maxColumns = 20;
	        	
	            HtmlStatusBar htmlStatusbar;
	            if(AssertUtility.isTrue(toolbar)) {
	            	if(statusbar != null) {
		            	htmlStatusbar = new HtmlStatusBar(statusbar, (List<Map<String, String>>)webutil.getRequestAttribute(data), maxColumns, pagination, htmlId, headerRowCount, htmlToolbar);
		            } else {
		            	htmlStatusbar = new HtmlStatusBar("1", (List<Map<String, String>>)webutil.getRequestAttribute(data), maxColumns, pagination, htmlId, headerRowCount, htmlToolbar);
		            }
	            } else {
		            if(statusbar != null) {
		            	htmlStatusbar = new HtmlStatusBar(statusbar, (List<Map<String, String>>)webutil.getRequestAttribute(data), maxColumns, pagination, htmlId, headerRowCount, null);
		            } else {
		            	htmlStatusbar = new HtmlStatusBar("1", (List<Map<String, String>>)webutil.getRequestAttribute(data), maxColumns, pagination, htmlId, headerRowCount, null);
		            }
	            }
	            htmlBuilder.appendString(htmlStatusbar.render());
	        }
		} catch (NumberFormatException e) {
			throw new HtmlRenderException(e);
		} catch (NamingException e) {
			throw new HtmlRenderException(e);
		} catch (UnsupportedEncodingException e) {
			throw new HtmlRenderException(e);
		}
        
		return htmlBuilder.toString();
	}
	
	@SuppressWarnings("unchecked")
	public String renderEnd() throws HtmlRenderException {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
        
		try {
			if(statusBarPosition == null || !statusBarPosition.equalsIgnoreCase("up")) {
//	        	產生Status Bar
	            HtmlStatusBar htmlStatusbar;
	            
	            if(statusbar != null) {
	            	htmlStatusbar = new HtmlStatusBar(statusbar, (List<Map<String, String>>)webutil.getRequestAttribute(data), getMaxColumnCount(), pagination, htmlId, headerRowCount, null);
	            } else {
	            	htmlStatusbar = new HtmlStatusBar("1", (List<Map<String, String>>)webutil.getRequestAttribute(data), getMaxColumnCount(), pagination, htmlId, headerRowCount, null);
	            }
	            htmlBuilder.appendString(htmlStatusbar.render());
	        }
	        
	        htmlBuilder.tableEnd();
	        
	        if(noDataStringFlag != null && noDataStringFlag.booleanValue() && data != null && webutil.getRequestAttribute(data) != null && ((List<Map<String, String>>)webutil.getRequestAttribute(data)).size() == 0) {
				htmlBuilder.divEnd();
			}
		} catch (NamingException e) {
			throw new HtmlRenderException(e);
		} catch (UnsupportedEncodingException e) {
			throw new HtmlRenderException(e);
		}
        
        return htmlBuilder.toString();
	}
	
	private void setHeaderRowCountByStatusBarPosition() {
		if(statusBarPosition != null && statusBarPosition.equalsIgnoreCase("up")) {
        	if(pagination == 0 && (toolbar == null || toolbar.equalsIgnoreCase("false"))) {
        		headerRowCount = 1;
        	} else {
        		headerRowCount = 2;
        	}
        } else {
        	headerRowCount = 1;
        }
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
	
	public void setPagination(int value) {
		this.pagination = value;
		setPagination = true;
	}
	
	public int getPagination() {
		return this.pagination;
	}
	
	public void setToolbar(String value) {
		this.toolbar = value;
	}
	
	public String getToolbar() {
		return this.toolbar;
	}
	
	public void setXlsName(String value) {
		this.xlsName = value;
	}
	
	public String getXlsName() {
		return this.xlsName;
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
	
	public void setStatusbar(String value) {
		this.statusbar = value;
	}
	
	public String getStatusbar() {
		return this.statusbar;
	}

	public void setStatusBarPosition(String statusBarPosition) {
		this.statusBarPosition = statusBarPosition;
	}

	public String getStatusBarPosition() {
		return statusBarPosition;
	}

	public void setHighlight(String highlight) {
		this.highlight = highlight;
	}

	public String getHighlight() {
		return highlight;
	}

	public void setNoDataStringFlag(Boolean noDataStringFlag) {
		this.noDataStringFlag = noDataStringFlag;
	}

	public Boolean getNoDataStringFlag() {
		return noDataStringFlag;
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

	public void setGroupByColumns(String groupByColumns) {
		this.groupByColumns = groupByColumns;
	}

	public String getGroupByColumns() {
		return groupByColumns;
	}

	public void setGroupOp(String groupOp) {
		this.groupOp = groupOp;
	}

	public String getGroupOp() {
		return groupOp;
	}
}
