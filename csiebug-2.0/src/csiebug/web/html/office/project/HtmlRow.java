package csiebug.web.html.office.project;

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
	        		htmlBuilder.className("TD_EXCEL_CONTROL");
	        	}
	        } else { //資料列處理
//	        	設定各樣屬性
	            if(table.getData() != null && webutil.getRequestAttribute(table.getData()) != null) {
	            	list = (List<Map<String, String>>)webutil.getRequestAttribute(table.getData());
	            	currentMap = list.get(table.getCurrentMapIndex());
	            	
	            	if(table.getMaxTaskId() == -1 || table.getMaxTaskId() < Integer.parseInt(currentMap.get("uniqueID"))) {
	            		table.setMaxTaskId(Integer.parseInt(currentMap.get("uniqueID")));
	            	}
	            	
	            	//附加變數
	            	currentMap.put("SerialNumber", "" + (table.getCurrentMapIndex() + 1));
	            	currentMap.put("TableId", table.getId());
	            	currentMap.put("FormId", table.getFormId());
	            	currentMap.put("ProjectId", table.getProjectId());
	            	currentMap.put("RowIndex", "" + (table.getHeaderRowCount() + table.getCurrentMapIndex()));
	            	currentMap.put("HeaderRowCount", "" + table.getHeaderRowCount());
	            	
            		htmlBuilder.id(currentMap.get("groupId"));
            		
            		if(currentMap.get("groupId").indexOf("_") != -1) {
            			htmlBuilder.style("display:none");
            		}
            	}
	            
	            if(style != null) {
		           	htmlBuilder.style(style);
		        }
	            
	            if(className != null) {
	            	htmlBuilder.className(className);
	            } else {
	            	htmlBuilder.className("TR_ODD");
	            }
	            
	            if(AssertUtility.isTrue(selectable)) {
	            	//onClick和onDblClick會衝突,以onClick為優先
	            	if(onClick != null) {
	            		htmlBuilder.onClick("selOneDataForClickForProject('" + table.getId() + "', " + (table.getHeaderRowCount() + table.getCurrentMapIndex()) + ", 0, " + table.getHeaderRowCount() + ", event);" + transScript(onClick));
	            		if(onDblClick != null) {
		            		htmlBuilder.onDblClick(transScript(onDblClick));
		            	}
	            	} else {
	            		if(onDblClick != null) {
	            			htmlBuilder.onDblClick("selOneDataForClickForProject('" + table.getId() + "', " + (table.getHeaderRowCount() + table.getCurrentMapIndex()) + ", 0, " + table.getHeaderRowCount() + ", event);" + transScript(onDblClick));
	            		} else {
		            		htmlBuilder.onClick("selOneDataForClickForProject('" + table.getId() + "', " + (table.getHeaderRowCount() + table.getCurrentMapIndex()) + ", 0, " + table.getHeaderRowCount() + ", event);");
		            	}
	            	}
	            } else {
	            	if(onClick != null) {
	            		htmlBuilder.onClick(transScript(onClick));
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
	        
	        //一定保留的column(developer不需寫column tag指定)
	        //Excel Row Control
	        HtmlColumn excelRowControl = new HtmlColumn();
	        excelRowControl.setRow(this);
	        excelRowControl.setDataType("excelRowControl");
	        excelRowControl.setHideable("false");
	        htmlBuilder.appendString(excelRowControl.renderStart() + excelRowControl.renderEnd());
	        
	        //outlineNumber
	        HtmlColumn outlineNumber = new HtmlColumn();
	        outlineNumber.setRow(this);
	        outlineNumber.setFieldname("outlineNumber");
	        outlineNumber.setTitle(webutil.getMessage("projectGrid.outlineNumber"));
	        outlineNumber.setHideable("false");
	        htmlBuilder.appendString(outlineNumber.render("<span>(var.outlineNumber)</span>"));
	        
	        HtmlColumn outlineNumberHidden = new HtmlColumn();
	        outlineNumberHidden.setRow(this);
	        outlineNumberHidden.setFieldname("outlineNumber");
	        outlineNumberHidden.setStyle("display:none");
	        htmlBuilder.appendString(outlineNumberHidden.render("<input type=\"hidden\" id=\"(var.TableId)_(var.SerialNumber)_(var.ColumnIndex)\" name=\"(var.TableId)_(var.SerialNumber)_(var.ColumnIndex)\" value=\"(var.outlineNumber)\"></input>"));
	        
	        //task id
	        HtmlColumn uniqueID = new HtmlColumn();
	        uniqueID.setRow(this);
	        uniqueID.setFieldname("uniqueID");
	        uniqueID.setStyle("display:none");
	        htmlBuilder.appendString(uniqueID.render("<input type=\"hidden\" id=\"(var.TableId)_(var.SerialNumber)_(var.ColumnIndex)\" name=\"(var.TableId)_(var.SerialNumber)_(var.ColumnIndex)\" value=\"(var.uniqueID)\"></input>"));
	        
	        //task name
	        HtmlColumn taskName = new HtmlColumn();
	        taskName.setRow(this);
	        taskName.setFieldname("name");
	        taskName.setTitle(webutil.getMessage("projectGrid.name"));
	        taskName.setSelectable("true");
	        htmlBuilder.appendString(taskName.renderStart() + taskName.renderEnd());
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
	
	public String renderEnd() throws HtmlRenderException {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		//一定保留的column(developer不需寫column tag指定)
		HtmlColumns levels = new HtmlColumns();
		levels.setRow(this);
		levels.setDynFields(table.getData() + "_level");
		htmlBuilder.appendString(levels.render());
		
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
        //替換ProjectId變數
        while(strReturn.indexOf("(var.ProjectId)") != -1) {
        	strReturn = strReturn.substring(0, strReturn.indexOf("(var.ProjectId)")) + table.getProjectId() + strReturn.substring(strReturn.indexOf("(var.ProjectId)") + "(var.ProjectId)".length(), strReturn.length());
        }
        
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
