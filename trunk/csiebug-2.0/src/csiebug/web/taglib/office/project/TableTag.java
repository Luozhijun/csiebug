package csiebug.web.taglib.office.project;

import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import csiebug.util.StringUtility;
import csiebug.web.html.office.project.HtmlTable;


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
	private String downloadFileName;
	private String imagePath;
	private String formId;
	private String projectId;
	private String downloadPartial;
	private String downloadColumns;
	private String highlight;
	private String isReadOnly;
	private String downloadMPP;
	
	private HtmlTable table;
	
	public int doStartTag() throws JspException {
		try { 
            JspWriter out = pageContext.getOut(); 
            
            table = new HtmlTable(htmlId, name, projectId, style, className, data, downloadFileName, imagePath, formId, downloadPartial, downloadColumns, downloadMPP, highlight, isReadOnly);
            
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