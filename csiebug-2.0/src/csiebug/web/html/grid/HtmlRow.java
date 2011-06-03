package csiebug.web.html.grid;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import csiebug.util.AssertUtility;
import csiebug.util.ListUtility;
import csiebug.util.WebUtility;
import csiebug.web.html.HtmlBuilder;
import csiebug.web.html.HtmlComponentNoBody;
import csiebug.web.html.HtmlRenderException;


/**
 * 產生HTML Row
 * @author George_Tsai
 * @version 2009/6/17
 */

public class HtmlRow extends HtmlComponentNoBody implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String style;
	private String className;
	private String headerClassName;
	private String headerStyle;
	private String onClick;
	private String onDblClick;
	private String rowType;
	private String selectable;
	private String title;
	
	private Map<String, String> currentMap;
	private List<Map<String, String>> list;
	private HtmlTable table;
	private int currentColumnIndex;
	private WebUtility webutil = new WebUtility();
	
	public HtmlRow(HtmlTable table, String style, String className, String headerClassName, String headerStyle, String onClick, String onDblClick, String rowType, String selectable, String title) {
		this.table = table;
		this.style = style;
		this.className = className;
		this.headerClassName = headerClassName;
		this.headerStyle = headerStyle;
		this.onClick = onClick;
		this.onDblClick = onDblClick;
		this.rowType = rowType;
		this.selectable = selectable;
		this.title = title;
	}
	
	public HtmlRow() {
		
	}
	
	@SuppressWarnings("unchecked")
	public String renderStart() throws HtmlRenderException {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
        
        try {
        	currentColumnIndex = -1;
        	
	        htmlBuilder.trStart();
	        
	        //標頭列處理
	        if(table.getCurrentMapIndex() == -1) {
	        	//自訂複合式標頭,需要計算標頭的列數(為了使換頁的script可以正常運作)
	        	if(rowType != null && rowType.equalsIgnoreCase("header")) {
	        		table.setHeaderRowCount(table.getHeaderRowCount() + 1);
	        	}
	        	
	        	if(headerStyle != null) {
	        		htmlBuilder.style(style);
	        	}
	        	
	        	if(headerClassName != null) {
	        		htmlBuilder.className(headerClassName);
	        	} else {
	        		htmlBuilder.className("TableHeader");
	        	}
	        } else { //資料列處理
	//          設定各樣屬性
	        	boolean showGroupRow = false;
	            if(table.getData() != null && webutil.getRequestAttribute(table.getData()) != null) {
	            	list = (List<Map<String, String>>)webutil.getRequestAttribute(table.getData());
	            	currentMap = list.get(table.getCurrentMapIndex());
	            	
	            	//附加變數
	            	currentMap.put("SerialNumber", "" + (table.getCurrentMapIndex() + 1));
	            	currentMap.put("TableId", table.getId());
	            	currentMap.put("FormId", table.getFormId());
	            	currentMap.put("RowIndex", "" + (table.getHeaderRowCount() + table.getCurrentMapIndex()));
	            	currentMap.put("pagination", "" + table.getPagination());
	            	currentMap.put("HeaderRowCount", "" + table.getHeaderRowCount());
	            	
	            	if(AssertUtility.isNotNullAndNotSpace(table.getGroupByColumns())) {
	            		htmlBuilder.id(currentMap.get("groupId"));
	            		
	            		if(currentMap.get("groupId").indexOf("_") != -1) {
	            			htmlBuilder.style("display:none");
	            		}
	            		
	            		showGroupRow = true;
	            	}
	            }
	            
	            if(table.getPagination() == 0 || table.getCurrentMapIndex() < table.getPagination()) {
		            if(style != null) {
		            	htmlBuilder.style(style);
		            }
	            } else {
	            	htmlBuilder.style("display:none");
	            }
	            
	            if(className != null) {
	            	htmlBuilder.className(className);
	            } else {
	            	if(showGroupRow) {
	//            		if(currentMap.get("groupType").equalsIgnoreCase("data")) {
	            			htmlBuilder.className("TR_ODD");
	//	            	} else {
	//	            		htmlBuilder.className("TR_EVEN");
	//	            	}
	            	} else {
		            	if((table.getCurrentMapIndex() % 2) == 0) {
		            		htmlBuilder.className("TR_ODD");
		            	} else {
		            		htmlBuilder.className("TR_EVEN");
		            	}
	            	}
	            }
	            
	            if(AssertUtility.isTrue(selectable)) {
	            	//onClick和onDblClick會衝突,以onClick為優先
	            	if(onClick != null) {
	            		if(showGroupRow) {
	            			htmlBuilder.onClick("selOneDataForClick('" + table.getId() + "', " + (table.getHeaderRowCount() + table.getCurrentMapIndex()) + ", " + table.getPagination() + ", " + table.getHeaderRowCount() + ", event);showGroupRow('" + table.getId() + "', this, '" + webutil.getBasePathForHTML() + "images/');" + transScript(onClick));
	            		} else {
	            			htmlBuilder.onClick("selOneDataForClick('" + table.getId() + "', " + (table.getHeaderRowCount() + table.getCurrentMapIndex()) + ", " + table.getPagination() + ", " + table.getHeaderRowCount() + ", event);" + transScript(onClick));
	            		}
	            		if(onDblClick != null) {
		            		htmlBuilder.onDblClick(transScript(onDblClick));
		            	}
	            	} else {
	            		if(onDblClick != null) {
	            			if(showGroupRow) {
	            				htmlBuilder.onDblClick("selOneDataForClick('" + table.getId() + "', " + (table.getHeaderRowCount() + table.getCurrentMapIndex()) + ", " + table.getPagination() + ", " + table.getHeaderRowCount() + ", event);showGroupRow('" + table.getId() + "', this, '" + webutil.getBasePathForHTML() + "images/');" + transScript(onDblClick));
	            			} else {
	            				htmlBuilder.onDblClick("selOneDataForClick('" + table.getId() + "', " + (table.getHeaderRowCount() + table.getCurrentMapIndex()) + ", " + table.getPagination() + ", " + table.getHeaderRowCount() + ", event);" + transScript(onDblClick));
	            			}
		            	} else {
		            		if(showGroupRow) {
		            			htmlBuilder.onClick("selOneDataForClick('" + table.getId() + "', " + (table.getHeaderRowCount() + table.getCurrentMapIndex()) + ", " + table.getPagination() + ", " + table.getHeaderRowCount() + ", event);showGroupRow('" + table.getId() + "', this, '" + webutil.getBasePathForHTML() + "images/');");
		            		} else {
		            			htmlBuilder.onClick("selOneDataForClick('" + table.getId() + "', " + (table.getHeaderRowCount() + table.getCurrentMapIndex()) + ", " + table.getPagination() + ", " + table.getHeaderRowCount() + ", event);");
		            		}
		            	}
	            	}
	            } else {
	            	if(onClick != null) {
	            		if(showGroupRow) {
	            			htmlBuilder.onClick("showGroupRow('" + table.getId() + "', this, '" + webutil.getBasePathForHTML() + "images/');" + transScript(onClick));
	            		} else {
	            			htmlBuilder.onClick(transScript(onClick));
	            		}
	            	} else if(showGroupRow){
	            		htmlBuilder.onClick("showGroupRow('" + table.getId() + "', this, '" + webutil.getBasePathForHTML() + "images/');");
	            	}
	            	
	            	if(onDblClick != null) {
	            		htmlBuilder.onDblClick(transScript(onDblClick));
	            	}
	            }
	            
	            if(AssertUtility.isTrue(table.getHighlight())) {
	            	htmlBuilder.onMouseMove("mousemoveRow(this);");
	            	htmlBuilder.onMouseOut("mouseoutRow(this);");
	            }
	            
	            if(AssertUtility.isNotNullAndNotSpace(title)) {
	            	htmlBuilder.title(transScript(title));
	            }
	        }
	        
	        htmlBuilder.tagClose();
        } catch (UnsupportedEncodingException e) {
			throw new HtmlRenderException(e);
		} catch (NamingException e) {
			throw new HtmlRenderException(e);
		}
        
        if(!(table.getCurrentMapIndex() != -1 && rowType != null && rowType.equalsIgnoreCase("header"))) {
        	return htmlBuilder.toString();
        } else {
        	return "";
        }
    }
	
	public String renderEnd() {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
        htmlBuilder.trEnd();
        
        if(!(table.getCurrentMapIndex() != -1 && rowType != null && rowType.equalsIgnoreCase("header"))) {
        	//計算出此table的column數
        	if((table.getMaxColumnCount() - 1) < currentColumnIndex) {
        		table.setMaxColumnCount(currentColumnIndex + 1);
        	}
        	return htmlBuilder.toString();
        } else {
        	return "";
        }
    }
	
	public Map<String, String> getCurrentMap() {
		return this.currentMap;
	}
	
	private String transScript(String original) throws UnsupportedEncodingException, NamingException {
		AssertUtility.notNull(original);
		
		//如果有變數(var.)或(sessionVar.)或(requestVar.),就做變數值替換
		String strReturn = original;
		if(getCurrentMap() != null) {
        	Map<String, String> map = getCurrentMap();
        	strReturn = ListUtility.replaceVariable(map, original);
        }
		
		strReturn = ListUtility.replaceSessionVariable(strReturn);
		strReturn = ListUtility.replaceRequestVariable(strReturn);
        
		//row獨有變數
		//替換序號變數
        while(strReturn.indexOf("(var.SerialNumber)") != -1) {
        	strReturn = strReturn.substring(0, strReturn.indexOf("(var.SerialNumber)")) + (table.getCurrentMapIndex() + 1) + strReturn.substring(strReturn.indexOf("(var.SerialNumber)") + "(var.SerialNumber)".length(), strReturn.length());
        }
        
        //table變數
        //替換FormId變數
        while(strReturn.indexOf("(var.FormId)") != -1) {
        	strReturn = strReturn.substring(0, strReturn.indexOf("(var.FormId)")) + table.getFormId() + strReturn.substring(strReturn.indexOf("(var.FormId)") + "(var.FormId)".length(), strReturn.length());
        }
        
        //替換TableId變數
        while(strReturn.indexOf("(var.TableId)") != -1) {
        	strReturn = strReturn.substring(0, strReturn.indexOf("(var.TableId)")) + table.getId() + strReturn.substring(strReturn.indexOf("(var.TableId)") + "(var.TableId)".length(), strReturn.length());
        }
        
        //替換RowIndex變數
        while(strReturn.indexOf("(var.RowIndex)") != -1) {
        	strReturn = strReturn.substring(0, strReturn.indexOf("(var.RowIndex)")) + (table.getHeaderRowCount() + table.getCurrentMapIndex()) + strReturn.substring(strReturn.indexOf("(var.RowIndex)") + "(var.RowIndex)".length(), strReturn.length());
        }
        
        //替換pagination變數
        while(strReturn.indexOf("(var.pagination)") != -1) {
        	strReturn = strReturn.substring(0, strReturn.indexOf("(var.pagination)")) + table.getPagination() + strReturn.substring(strReturn.indexOf("(var.pagination)") + "(var.pagination)".length(), strReturn.length());
        }
        
        //替換HeaderRowCount變數
        while(strReturn.indexOf("(var.HeaderRowCount)") != -1) {
        	strReturn = strReturn.substring(0, strReturn.indexOf("(var.HeaderRowCount)")) + table.getHeaderRowCount() + strReturn.substring(strReturn.indexOf("(var.HeaderRowCount)") + "(var.HeaderRowCount)".length(), strReturn.length());
        }
        
        return strReturn;
	}
	
//	元件屬性區
	public void setTable(HtmlTable table) {
		this.table = table;
	}
	
	public HtmlTable getTable() {
		return this.table;
	}
	
	public void setCurrentColumnIndex(int value) {
		this.currentColumnIndex = value;
	}
	
	public int getCurrentColumnIndex() {
		return this.currentColumnIndex;
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
	
	public void setHeaderStyle(String value) {
		this.headerStyle = value;
	}
	
	public String getHeaderStyle() {
		return this.headerStyle;
	}
	
	public void setHeaderClassName(String className) {
		this.headerClassName = className;
	}
	
	public String getHeaderClassName() {
		return this.headerClassName;
	}
	
	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}
	
	public String getOnClick() {
		return this.onClick;
	}
	
	public void setOnDblClick(String onDblClick) {
		this.onDblClick = onDblClick;
	}
	
	public String getOnDblClick() {
		return this.onDblClick;
	}
	
	public void setRowType(String rowType) {
		this.rowType = rowType;
	}
	
	public String getRowType() {
		return this.rowType;
	}
	
	public void setSelectable(String checkable) {
		this.selectable = checkable;
	}
	
	public String getSelectable() {
		return this.selectable;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
