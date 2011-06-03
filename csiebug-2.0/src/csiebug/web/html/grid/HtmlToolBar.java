package csiebug.web.html.grid;

import java.io.UnsupportedEncodingException;

import javax.naming.NamingException;

import csiebug.util.AssertUtility;
import csiebug.util.StringUtility;
import csiebug.util.WebUtility;
import csiebug.web.html.HtmlBuilder;
import csiebug.web.html.HtmlComponent;
import csiebug.web.html.HtmlRenderException;

/**
 * 產生HTML ToolBar
 * @author George_Tsai
 * @version 2009/6/17
 */
public class HtmlToolBar extends HtmlComponent {
	private String htmlId;
	private String formId;
	private String data;
	private String xlsName;
	private String imagePath;
	private String downloadPartial;
	private String downloadColumns;
	
	private WebUtility webutil = new WebUtility();
	
	public HtmlToolBar(String htmlId, String formId, String data, String xlsName, String imagePath, String downloadPartial, String downloadColumns) {
		this.htmlId = htmlId;
		this.formId = formId;
		this.data = data;
		this.xlsName = xlsName;
		this.imagePath = StringUtility.removeStartEndSlash(imagePath);
		this.downloadPartial = downloadPartial;
		if(downloadColumns != null) {
			this.downloadColumns = downloadColumns;
		} else {
			this.downloadColumns = "";
		}
	}
	
	public HtmlToolBar() {
		this.downloadColumns = "";
	}
	
	public String renderStart() {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		htmlBuilder.table();
    	htmlBuilder.trStart();
    	htmlBuilder.className("TableStatusBar");
    	htmlBuilder.tagClose();
    	
    	return htmlBuilder.toString();
	}
	
	public String renderEnd() {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		htmlBuilder.trEnd();
        htmlBuilder.tableEnd();
        
        return htmlBuilder.toString();
	}
	
	public String renderDownloadButton() throws NamingException, UnsupportedEncodingException {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		if(htmlId != null) {
			checkXlsName(htmlBuilder, 0);
		}
		
		return htmlBuilder.toString();
	}
	
	public String renderBody(String content) throws HtmlRenderException {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		try {
			if(htmlId != null) {
				htmlBuilder.td();
				
				//將資料放到session中,不透過前端的form來傳送要轉出成excel的html
				if(data != null) {
					webutil.setSessionAttribute(data, webutil.getRequestAttribute(data));
				}
				checkXlsName(htmlBuilder, 1);
				
				htmlBuilder.tdEnd();
			}
		} catch (UnsupportedEncodingException e) {
			throw new HtmlRenderException(e);
		} catch (NamingException e) {
			throw new HtmlRenderException(e);
		}
		
		return htmlBuilder.toString();
	}
	
	private void checkXlsName(HtmlBuilder htmlBuilder, int statusbar) throws NamingException, UnsupportedEncodingException {
		AssertUtility.notNull(htmlBuilder);
		
		if(xlsName != null) {
    		checkImagePathWithXlsName(htmlBuilder, statusbar);
    	} else {
    		checkImagePathWithoutXlsName(htmlBuilder, statusbar);
    	}
	}
	
	private void checkImagePathWithXlsName(HtmlBuilder htmlBuilder, int statusbar) throws NamingException, UnsupportedEncodingException {
		AssertUtility.notNull(htmlBuilder);
		
		htmlBuilder.td();
		if(xlsName.endsWith(".xls")) {
			checkImagePathWithExtensionName(htmlBuilder, statusbar);
		} else {
			checkImagePathWithoutExtensionName(htmlBuilder, statusbar);
		}
		htmlBuilder.tdEnd();
	}
	
	private void checkImagePathWithExtensionName(HtmlBuilder htmlBuilder, int statusbar) throws NamingException, UnsupportedEncodingException {
		AssertUtility.notNull(htmlBuilder);
		
		if(formId == null) {
			formId = "";
		}
		
		if(data == null) {
			data = "";
		}
		
		if(imagePath != null) {
			checkDownloadPartial(htmlBuilder, imagePath + "/office/16/Excel-icon.png", imagePath + "/office/16/ods-16.png",
					"'" + formId + "', '" + htmlId + "', '" + xlsName + "', '" + webutil.getBasePathForHTML() + "', " + statusbar + ", '" + downloadColumns + "', '" + data + "'", 
					"'" + formId + "', '" + htmlId + "', '" + xlsName.substring(0, xlsName.length() - 3) + "csv" + "', '" + webutil.getBasePathForHTML() + "', " + statusbar + ", '" + downloadColumns + "', '" + data + "'",
					"'" + formId + "', '" + htmlId + "', '" + xlsName.substring(0, xlsName.length() - 3) + "ods" + "', '" + webutil.getBasePathForHTML() + "', " + statusbar + ", '" + downloadColumns + "', '" + data + "'");
		} else {
			checkDownloadPartial(htmlBuilder, webutil.getBasePathForHTML() + "images/office/16/Excel-icon.png", webutil.getBasePathForHTML() + "images/office/16/ods-16.png",
					"'" + formId + "', '" + htmlId + "', '" + xlsName + "', '" + webutil.getBasePathForHTML() + "', " + statusbar + ", '" + downloadColumns + "', '" + data + "'", 
					"'" + formId + "', '" + htmlId + "', '" + xlsName.substring(0, xlsName.length() - 3) + "csv" + "', '" + webutil.getBasePathForHTML() + "', " + statusbar + ", '" + downloadColumns + "', '" + data + "'",
					"'" + formId + "', '" + htmlId + "', '" + xlsName.substring(0, xlsName.length() - 3) + "ods" + "', '" + webutil.getBasePathForHTML() + "', " + statusbar + ", '" + downloadColumns + "', '" + data + "'");
		}
	}
	
	private void checkImagePathWithoutExtensionName(HtmlBuilder htmlBuilder, int statusbar) throws NamingException, UnsupportedEncodingException {
		AssertUtility.notNull(htmlBuilder);
		
		if(formId == null) {
			formId = "";
		}
		
		if(data == null) {
			data = "";
		}
		
		if(imagePath != null) {
			checkDownloadPartial(htmlBuilder, imagePath + "/office/16/Excel-icon.png", imagePath + "/office/16/ods-16.png",
					"'" + formId + "', '" + htmlId + "', '" + xlsName + ".xls', '" + webutil.getBasePathForHTML() + "', " + statusbar + ", '" + downloadColumns + "', '" + data + "'", 
					"'" + formId + "', '" + htmlId + "', '" + xlsName + ".csv', '" + webutil.getBasePathForHTML() + "', " + statusbar + ", '" + downloadColumns + "', '" + data + "'",
					"'" + formId + "', '" + htmlId + "', '" + xlsName + ".ods', '" + webutil.getBasePathForHTML() + "', " + statusbar + ", '" + downloadColumns + "', '" + data + "'");
		} else {
			checkDownloadPartial(htmlBuilder, webutil.getBasePathForHTML() + "images/office/16/Excel-icon.png", webutil.getBasePathForHTML() + "images/office/16/ods-16.png",
					"'" + formId + "', '" + htmlId + "', '" + xlsName + ".xls', '" + webutil.getBasePathForHTML() + "', " + statusbar + ", '" + downloadColumns + "', '" + data + "'", 
					"'" + formId + "', '" + htmlId + "', '" + xlsName + ".csv', '" + webutil.getBasePathForHTML() + "', " + statusbar + ", '" + downloadColumns + "', '" + data + "'",
					"'" + formId + "', '" + htmlId + "', '" + xlsName + ".ods', '" + webutil.getBasePathForHTML() + "', " + statusbar + ", '" + downloadColumns + "', '" + data + "'");
		}
	}
	
	private void checkImagePathWithoutXlsName(HtmlBuilder htmlBuilder, int statusbar) throws NamingException, UnsupportedEncodingException {
		AssertUtility.notNull(htmlBuilder);
		
		if(formId == null) {
			formId = "";
		}
		
		if(data == null) {
			data = "";
		}
		
		if(imagePath != null) {
			checkDownloadPartial(htmlBuilder, imagePath + "/office/16/Excel-icon.png", imagePath + "/office/16/ods-16.png",
					"'" + formId + "', '" + htmlId + "', '" + htmlId + ".xls', '" + webutil.getBasePathForHTML() + "', " + statusbar + ", '" + downloadColumns + "', '" + data + "'", 
					"'" + formId + "', '" + htmlId + "', '" + htmlId + ".csv', '" + webutil.getBasePathForHTML() + "', " + statusbar + ", '" + downloadColumns + "', '" + data + "'",
					"'" + formId + "', '" + htmlId + "', '" + htmlId + ".ods', '" + webutil.getBasePathForHTML() + "', " + statusbar + ", '" + downloadColumns + "', '" + data + "'");
		} else {
			checkDownloadPartial(htmlBuilder, webutil.getBasePathForHTML() + "images/office/16/Excel-icon.png", webutil.getBasePathForHTML() + "images/office/16/ods-16.png",
					"'" + formId + "', '" + htmlId + "', '" + htmlId + ".xls', '" + webutil.getBasePathForHTML() + "', " + statusbar + ", '" + downloadColumns + "', '" + data + "'", 
					"'" + formId + "', '" + htmlId + "', '" + htmlId + ".csv', '" + webutil.getBasePathForHTML() + "', " + statusbar + ", '" + downloadColumns + "', '" + data + "'",
					"'" + formId + "', '" + htmlId + "', '" + htmlId + ".ods', '" + webutil.getBasePathForHTML() + "', " + statusbar + ", '" + downloadColumns + "', '" + data + "'");
		}
	}
	
	private void checkDownloadPartial(HtmlBuilder htmlBuilder, String srcExcel, String srcODS, String onClickParameterForExcel, String onClickParameterForCSV, String onClickParameterForODS) throws UnsupportedEncodingException, NamingException {
		AssertUtility.notNull(htmlBuilder);
		AssertUtility.notNull(srcExcel);
		AssertUtility.notNull(srcODS);
		AssertUtility.notNullAndNotSpace(onClickParameterForExcel);
		AssertUtility.notNullAndNotSpace(onClickParameterForCSV);
		
		if(AssertUtility.isTrue(downloadPartial)) {
			htmlBuilder.aStart().href("#").onClick("downloadPartialCSV(" + onClickParameterForCSV + ");").style("cursor:pointer;").tagClose();
		} else {
			htmlBuilder.aStart().href("#").onClick("downloadCSV(" + onClickParameterForCSV + ");").style("cursor:pointer;").tagClose();
		}
		
		htmlBuilder.imageStart().src(srcExcel).style("cursor:pointer;").tagClose();
		
		htmlBuilder.appendString(webutil.getMessage("grid.downloadCSV"));
		
		htmlBuilder.aEnd();
		
		if(AssertUtility.isTrue(downloadPartial)) {
			htmlBuilder.aStart().href("#").onClick("downloadPartialExcel(" + onClickParameterForExcel + ");").style("cursor:pointer;").tagClose();
		} else {
			htmlBuilder.aStart().href("#").onClick("downloadExcel(" + onClickParameterForExcel + ");").style("cursor:pointer;").tagClose();
		}
		
		htmlBuilder.imageStart().src(srcExcel).style("cursor:pointer;").tagClose();
		
		htmlBuilder.appendString(webutil.getMessage("grid.downloadExcel"));
		
		htmlBuilder.aEnd();
		
		if(AssertUtility.isTrue(downloadPartial)) {
			htmlBuilder.aStart().href("#").onClick("downloadPartialODS(" + onClickParameterForODS + ");").style("cursor:pointer;").tagClose();
		} else {
			htmlBuilder.aStart().href("#").onClick("downloadODS(" + onClickParameterForODS + ");").style("cursor:pointer;").tagClose();
		}
		
		htmlBuilder.imageStart().src(srcODS).style("cursor:pointer;").tagClose();
		
		htmlBuilder.appendString(webutil.getMessage("grid.downloadODS"));
		
		htmlBuilder.aEnd();
	}
	
	//元件屬性區
	public void setId(String id) {
		this.htmlId = id;
	}
	
	public String getId() {
		return this.htmlId;
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
}
