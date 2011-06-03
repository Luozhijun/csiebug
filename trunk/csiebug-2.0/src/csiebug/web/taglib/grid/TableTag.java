package csiebug.web.taglib.grid;

import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import csiebug.util.StringUtility;
import csiebug.web.html.grid.HtmlTable;


/**
 * table tag
 * @author George_Tsai
 * @version 2009/3/13
 */

public class TableTag extends BodyTagSupport implements TryCatchFinally {
	static final long serialVersionUID = 20081223;
	
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
	private String noDataStringFlag;
	private String groupByColumns;
	private String groupOp;
	
	private HtmlTable table;
	private boolean setPagination = false;
	
	public int doStartTag() throws JspException {
		try { 
            JspWriter out = pageContext.getOut(); 
            
            table = new HtmlTable(htmlId, name, style, className, data, pagination, toolbar, xlsName, imagePath, formId, downloadPartial, downloadColumns, statusbar, statusBarPosition, highlight, Boolean.parseBoolean(noDataStringFlag), groupByColumns, groupOp);
            
            if(setPagination) {
            	table.setPagination(pagination);
            }
            
            out.println(table.renderStart());
        } 
        catch(Exception e) {
        	throw new JspException(e);
        } 
        
        return EVAL_BODY_INCLUDE; 
	}
	
	@SuppressWarnings("unchecked")
	public int doAfterBody() throws JspException {
		List<Map<String, String>> list = (List<Map<String, String>>)pageContext.getRequest().getAttribute(data);
		
		if(list != null && table.getCurrentMapIndex() + 1 < list.size()) {
        	table.setCurrentMapIndex(table.getCurrentMapIndex() + 1);
        	return EVAL_BODY_AGAIN;
        } else {
        	return SKIP_BODY;
        }  
	}
	
	public int doEndTag() throws JspException {
		try { 
            JspWriter out = pageContext.getOut(); 
            
            out.println(table.renderEnd());
        } 
        catch(Exception e) {
        	throw new JspException(e);
        } 
 
        return EVAL_PAGE; 
	}
	public void doFinally() {
		
	}
	public void doCatch(Throwable e) throws Throwable {
		throw new JspException("TableTag Problem: " + e.getMessage());
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
	
	public HtmlTable getTable() {
		return table;
	}
	
	public void setCurrentMapIndex(int value) {
		table.setCurrentMapIndex(value);
	}
	
	public int getCurrentMapIndex() {
		return table.getCurrentMapIndex();
	}
	
	public void setHeaderRowCount(int value) {
		table.setHeaderRowCount(value);
	}
	
	public int getHeaderRowCount() {
		return table.getHeaderRowCount();
	}
	
	public void setMaxColumnCount(int value) {
		table.setMaxColumnCount(value);
	}
	
	public int getMaxColumnCount() {
		return table.getMaxColumnCount();
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

	public void setNoDataStringFlag(String noDataStringFlag) {
		this.noDataStringFlag = noDataStringFlag;
	}

	public String getNoDataStringFlag() {
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