package csiebug.web.html.office.project;

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
	private String downloadFileName;
	private String imagePath;
	private String downloadPartial;
	private String downloadColumns;
	private String downloadMPP;
	private String isReadOnly;
	
	private WebUtility webutil = new WebUtility();
	
	public HtmlToolBar(String htmlId, String formId, String data, String downloadFileName, String imagePath, String downloadPartial, String downloadColumns, String downloadMPP, String isReadOnly) {
		this.htmlId = htmlId;
		this.formId = formId;
		this.data = data;
		this.downloadFileName = downloadFileName;
		this.imagePath = StringUtility.removeStartEndSlash(imagePath);
		this.downloadPartial = downloadPartial;
		if(downloadColumns != null) {
			this.downloadColumns = downloadColumns;
		} else {
			this.downloadColumns = "";
		}
		this.downloadMPP = downloadMPP;
		this.isReadOnly = isReadOnly;
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
	
	public String renderBody(String content) throws HtmlRenderException {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		try {
			if(htmlId != null) {
				String baseImagePath = "";
				if(imagePath != null) {
					baseImagePath = imagePath;
				} else {
					baseImagePath = webutil.getBasePathForHTML();
				}
				
				if(formId == null) {
					formId = "";
				}
				
				htmlBuilder.td();
				
				//Table不是唯讀才有以下按鈕可以使用
				if(!AssertUtility.isTrue(isReadOnly)) {
					renderArrowButtons(htmlBuilder, baseImagePath);
					
					renderVertline(htmlBuilder, baseImagePath);
					
					renderAddDeleteButtons(htmlBuilder, baseImagePath);
					
					renderVertline(htmlBuilder, baseImagePath);
					
					renderCopyPasteButtons(htmlBuilder, baseImagePath);
					
					renderVertline(htmlBuilder, baseImagePath);
					
					makeHiddenField(htmlBuilder);
				}
				
				renderDownloadButton(htmlBuilder, 1);
				
				htmlBuilder.tdEnd();

			}
		} catch (UnsupportedEncodingException e) {
			throw new HtmlRenderException(e);
		} catch (NamingException e) {
			throw new HtmlRenderException(e);
		}
		
		return htmlBuilder.toString();
	}
	
	private void renderVertline(HtmlBuilder htmlBuilder, String baseImagePath) {
		htmlBuilder.imgStart().align("absmiddle").src(baseImagePath + "/images/Vertline.gif").tagClose().imgEnd();
	}
	
	private void renderArrowButtons(HtmlBuilder htmlBuilder, String baseImagePath) throws UnsupportedEncodingException, NamingException {
		htmlBuilder.imgStart().id(htmlId + "_upButton").align("absmiddle").src(baseImagePath + "/images/arrow-up.png").onClick("moveUpRowForProject('" + formId + "', '" + htmlId + "', 1, event, '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.confirm.copy") + "', '" + webutil.getMessage("common.ok") + "', '" + webutil.getMessage("common.cancel") + "')").title(webutil.getMessage("projectGrid.up")).tagClose().imgEnd();
		htmlBuilder.imgStart().id(htmlId + "_downButton").align("absmiddle").src(baseImagePath + "/images/arrow-down.png").onClick("moveDownRowForProject('" + formId + "', '" + htmlId + "', 1, event, '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.confirm.copy") + "', '" + webutil.getMessage("common.ok") + "', '" + webutil.getMessage("common.cancel") + "')").title(webutil.getMessage("projectGrid.down")).tagClose().imgEnd();
		htmlBuilder.imgStart().id(htmlId + "_leftButton").align("absmiddle").src(baseImagePath + "/images/arrow-left.png").onClick("moveLeft('" + formId + "', '" + htmlId + "', 1)").title(webutil.getMessage("projectGrid.left")).tagClose().imgEnd();
		htmlBuilder.imgStart().id(htmlId + "_rightButton").align("absmiddle").src(baseImagePath + "/images/arrow-right.png").onClick("moveRight('" + formId + "', '" + htmlId + "', 1)").title(webutil.getMessage("projectGrid.right")).tagClose().imgEnd();
	}
	
	private void renderAddDeleteButtons(HtmlBuilder htmlBuilder, String baseImagePath) throws UnsupportedEncodingException, NamingException {
		htmlBuilder.imgStart().id(htmlId + "_addChildButton").align("absmiddle").src(baseImagePath + "/images/Add-icon.png").onClick("createNewRowForProject('" + formId + "', '" + htmlId + "', 1, true, '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.error.required4") + "', '" + webutil.getMessage("common.ok") + "', '" + webutil.getMessage("common.confirm.copy") + "', '" + webutil.getMessage("common.cancel") + "', event, '" + webutil.getMessage("common.BlankText") + "', '" + webutil.getMessage("common.error.interval") + "', '" + webutil.getMessage("common.error.DataType1Start") + "', '" + webutil.getMessage("common.error.DataType1End") + "', '" + webutil.getMessage("common.error.DataType2") + "', '" + webutil.getMessage("common.error.DataType3") + "', '" + webutil.getMessage("common.error.DataType4") + "', '" + webutil.getMessage("common.error.DataType5") + "', '" + webutil.getMessage("common.error.DataType6") + "', '" + webutil.getMessage("common.error.DataType7") + "', '" + webutil.getMessage("common.error.DataType8Start") + "', '" + webutil.getMessage("common.error.DataType8End") + "', '" + webutil.getMessage("common.error.DataType9Start") + "', '" + webutil.getMessage("common.error.DataType9End") + "', '" + webutil.getMessage("common.error.DataType10Start") + "', '" + webutil.getMessage("common.error.DataType10End") + "', '" + webutil.getMessage("common.error.DataType11") + "', '" + webutil.getMessage("common.error.DataType12Start") + "', '" + webutil.getMessage("common.error.DataType12End") + "', '" + webutil.getMessage("common.error.DataType13") + "', '" + webutil.getMessage("common.error.DataType14") + "')").title(webutil.getMessage("projectGrid.addChild")).tagClose().imgEnd();
		htmlBuilder.imgStart().id(htmlId + "_addBrotherButton").align("absmiddle").src(baseImagePath + "/images/Add-icon.png").onClick("createNewRowForProject('" + formId + "', '" + htmlId + "', 1, false, '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.error.required4") + "', '" + webutil.getMessage("common.ok") + "', '" + webutil.getMessage("common.confirm.copy") + "', '" + webutil.getMessage("common.cancel") + "', event, '" + webutil.getMessage("common.BlankText") + "', '" + webutil.getMessage("common.error.interval") + "', '" + webutil.getMessage("common.error.DataType1Start") + "', '" + webutil.getMessage("common.error.DataType1End") + "', '" + webutil.getMessage("common.error.DataType2") + "', '" + webutil.getMessage("common.error.DataType3") + "', '" + webutil.getMessage("common.error.DataType4") + "', '" + webutil.getMessage("common.error.DataType5") + "', '" + webutil.getMessage("common.error.DataType6") + "', '" + webutil.getMessage("common.error.DataType7") + "', '" + webutil.getMessage("common.error.DataType8Start") + "', '" + webutil.getMessage("common.error.DataType8End") + "', '" + webutil.getMessage("common.error.DataType9Start") + "', '" + webutil.getMessage("common.error.DataType9End") + "', '" + webutil.getMessage("common.error.DataType10Start") + "', '" + webutil.getMessage("common.error.DataType10End") + "', '" + webutil.getMessage("common.error.DataType11") + "', '" + webutil.getMessage("common.error.DataType12Start") + "', '" + webutil.getMessage("common.error.DataType12End") + "', '" + webutil.getMessage("common.error.DataType13") + "', '" + webutil.getMessage("common.error.DataType14") + "')").title(webutil.getMessage("projectGrid.addBrother")).tagClose().imgEnd();
		htmlBuilder.imgStart().id(htmlId + "_deleteButton").align("absmiddle").src(baseImagePath + "/images/Delete-icon.png").onClick("deleteRowForProject('" + formId + "', '" + htmlId + "', 1, '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.error.required4") + "', '" + webutil.getMessage("common.ok") + "', '" + webutil.getMessage("common.confirm.copy") + "', '" + webutil.getMessage("common.cancel") + "', event)").title(webutil.getMessage("projectGrid.delete")).tagClose().imgEnd();
	}
	
	private void renderCopyPasteButtons(HtmlBuilder htmlBuilder, String baseImagePath) throws UnsupportedEncodingException, NamingException {
		htmlBuilder.imgStart().id(htmlId + "_copyButton").align("absmiddle").src(baseImagePath + "/images/copy-icon.png").onClick("copyForProject('" + htmlId + "', 1)").title(webutil.getMessage("projectGrid.copy")).tagClose().imgEnd();
		htmlBuilder.imgStart().id(htmlId + "_pasteButton").align("absmiddle").src(baseImagePath + "/images/paste-icon.png").onClick("pasteFieldForProject('" + htmlId + "', 1)").style("display:none").title(webutil.getMessage("projectGrid.pasteCellText")).tagClose().imgEnd();
		htmlBuilder.imgStart().id(htmlId + "_pasteChildButton").align("absmiddle").src(baseImagePath + "/images/paste-icon.png").onClick("pasteRowsForProject('" + formId + "', '" + htmlId + "', 1, true, '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.error.required4") + "', '" + webutil.getMessage("common.ok") + "', '" + webutil.getMessage("common.confirm.copy") + "', '" + webutil.getMessage("common.cancel") + "', event)").style("display:none").title(webutil.getMessage("projectGrid.pasteChildren")).tagClose().imgEnd();
		htmlBuilder.imgStart().id(htmlId + "_pasteBrotherButton").align("absmiddle").src(baseImagePath + "/images/paste-icon.png").onClick("pasteRowsForProject('" + formId + "', '" + htmlId + "', 1, false, '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.error.required4") + "', '" + webutil.getMessage("common.ok") + "', '" + webutil.getMessage("common.confirm.copy") + "', '" + webutil.getMessage("common.cancel") + "', event)").style("display:none").title(webutil.getMessage("projectGrid.pasteBrothers")).tagClose().imgEnd();
	}
	
	private void makeHiddenField(HtmlBuilder htmlBuilder) {
		htmlBuilder.inputStart().type("hidden").id(htmlId + "_copyRow").name(htmlId + "_copyRow").tagClose().inputEnd();
		htmlBuilder.inputStart().type("hidden").id(htmlId + "_copyField").name(htmlId + "_copyField").tagClose().inputEnd();
		htmlBuilder.inputStart().type("hidden").id(htmlId + "_updateStatus").name(htmlId + "_updateStatus").tagClose().inputEnd();
	}
	
	private void renderDownloadButton(HtmlBuilder htmlBuilder, int statusbar) throws NamingException, UnsupportedEncodingException {
		AssertUtility.notNull(htmlBuilder);
		
		//將資料放到session中,不透過前端的form來傳送要轉出成excel的html
		if(data != null) {
			webutil.setSessionAttribute(data, webutil.getRequestAttribute(data));
		}
		
		if(downloadFileName != null) {
			checkImagePath(htmlBuilder, statusbar);
    	} else {
    		checkImagePathWithoutFileName(htmlBuilder, statusbar);
    	}
	}
	
	private void checkImagePath(HtmlBuilder htmlBuilder, int statusbar) throws NamingException, UnsupportedEncodingException {
		AssertUtility.notNull(htmlBuilder);
		
		if(formId == null) {
			formId = "";
		}
		
		if(data == null) {
			data = "";
		}
		
		if(imagePath != null) {
			renderDownloadExcelButton(htmlBuilder, imagePath + "/office/16/Excel-icon.png", imagePath + "/office/16/ods-16.png",
					"'" + formId + "', '" + htmlId + "', '" + downloadFileName + ".xls', '" + webutil.getBasePathForHTML() + "', " + statusbar + ", '" + downloadColumns + "', '" + data + "'",
					"'" + formId + "', '" + htmlId + "', '" + downloadFileName + ".csv', '" + webutil.getBasePathForHTML() + "', " + statusbar + ", '" + downloadColumns + "', '" + data + "'",
					"'" + formId + "', '" + htmlId + "', '" + downloadFileName + ".ods', '" + webutil.getBasePathForHTML() + "', " + statusbar + ", '" + downloadColumns + "', '" + data + "'");
			if(AssertUtility.isTrue(downloadMPP)) {
				renderDownloadProjectButton(htmlBuilder, imagePath + "/office/16/Project-icon.png", "'" + htmlId + "', '" + downloadFileName + ".mpp', '" + webutil.getBasePathForHTML() + "', '" + data + "', 'MPP'");
			}
		} else {
			renderDownloadExcelButton(htmlBuilder, webutil.getBasePathForHTML() + "images/office/16/Excel-icon.png", webutil.getBasePathForHTML() + "images/office/16/ods-16.png",
					"'" + formId + "', '" + htmlId + "', '" + downloadFileName + ".xls', '" + webutil.getBasePathForHTML() + "', " + statusbar + ", '" + downloadColumns + "', '" + data + "'",
					"'" + formId + "', '" + htmlId + "', '" + downloadFileName + ".csv', '" + webutil.getBasePathForHTML() + "', " + statusbar + ", '" + downloadColumns + "', '" + data + "'",
					"'" + formId + "', '" + htmlId + "', '" + downloadFileName + ".ods', '" + webutil.getBasePathForHTML() + "', " + statusbar + ", '" + downloadColumns + "', '" + data + "'");
			if(AssertUtility.isTrue(downloadMPP)) {
				renderDownloadProjectButton(htmlBuilder, webutil.getBasePathForHTML() + "images/office/16/Project-icon.png", "'" + htmlId + "', '" + downloadFileName + ".mpp', '" + webutil.getBasePathForHTML() + "', '" + data + "', 'MPP'");
			}
		}
	}
	
	private void checkImagePathWithoutFileName(HtmlBuilder htmlBuilder, int statusbar) throws NamingException, UnsupportedEncodingException {
		AssertUtility.notNull(htmlBuilder);
		
		if(formId == null) {
			formId = "";
		}
		
		if(data == null) {
			data = "";
		}
		
		if(imagePath != null) {
			renderDownloadExcelButton(htmlBuilder, imagePath + "/office/16/Excel-icon.png", imagePath + "/office/16/ods-16.png",
					"'" + formId + "', '" + htmlId + "', '" + htmlId + ".xls', '" + webutil.getBasePathForHTML() + "', " + statusbar + ", '" + downloadColumns + "', '" + data + "'",
					"'" + formId + "', '" + htmlId + "', '" + htmlId + ".csv', '" + webutil.getBasePathForHTML() + "', " + statusbar + ", '" + downloadColumns + "', '" + data + "'",
					"'" + formId + "', '" + htmlId + "', '" + htmlId + ".ods', '" + webutil.getBasePathForHTML() + "', " + statusbar + ", '" + downloadColumns + "', '" + data + "'");
			if(AssertUtility.isTrue(downloadMPP)) {
				renderDownloadProjectButton(htmlBuilder, imagePath + "/office/16/Project-icon.png", "'" + htmlId + "', '" + htmlId + ".mpp', '" + webutil.getBasePathForHTML() + "', '" + data + "', 'MPP'");
			}
		} else {
			renderDownloadExcelButton(htmlBuilder, webutil.getBasePathForHTML() + "images/office/16/Excel-icon.png", webutil.getBasePathForHTML() + "images/office/16/ods-16.png",
					"'" + formId + "', '" + htmlId + "', '" + htmlId + ".xls', '" + webutil.getBasePathForHTML() + "', " + statusbar + ", '" + downloadColumns + "', '" + data + "'",
					"'" + formId + "', '" + htmlId + "', '" + htmlId + ".csv', '" + webutil.getBasePathForHTML() + "', " + statusbar + ", '" + downloadColumns + "', '" + data + "'",
					"'" + formId + "', '" + htmlId + "', '" + htmlId + ".ods', '" + webutil.getBasePathForHTML() + "', " + statusbar + ", '" + downloadColumns + "', '" + data + "'");
			if(AssertUtility.isTrue(downloadMPP)) {
				renderDownloadProjectButton(htmlBuilder, webutil.getBasePathForHTML() + "images/office/16/Project-icon.png", "'" + htmlId + "', '" + htmlId + ".mpp', '" + webutil.getBasePathForHTML() + "', '" + data + "', 'MPP'");
			}
		}
	}
	
	private void renderDownloadExcelButton(HtmlBuilder htmlBuilder, String srcExcel, String srcODS, String onClickParameterForExcel, String onClickParameterForCSV, String onClickParameterForODS) throws UnsupportedEncodingException, NamingException {
		AssertUtility.notNull(htmlBuilder);
		AssertUtility.notNull(srcExcel);
		AssertUtility.notNull(srcODS);
		AssertUtility.notNullAndNotSpace(onClickParameterForExcel);
		AssertUtility.notNullAndNotSpace(onClickParameterForCSV);
		
		htmlBuilder.imageStart().align("absmiddle").src(srcExcel).style("cursor:pointer;").title(webutil.getMessage("grid.downloadCSV"));
		
		if(AssertUtility.isTrue(downloadPartial)) {
			htmlBuilder.onClick("downloadPartialCSV(" + onClickParameterForCSV + ");");
			
		} else {
			htmlBuilder.onClick("downloadCSV(" + onClickParameterForCSV + ");");
		}
		
		htmlBuilder.tagClose();
		htmlBuilder.imgEnd();
		
		htmlBuilder.imageStart().align("absmiddle").src(srcExcel).style("cursor:pointer;").title(webutil.getMessage("grid.downloadExcel"));
		
		if(AssertUtility.isTrue(downloadPartial)) {
			htmlBuilder.onClick("downloadPartialExcel(" + onClickParameterForExcel + ");");
			
		} else {
			htmlBuilder.onClick("downloadExcel(" + onClickParameterForExcel + ");");
		}
		
		htmlBuilder.tagClose();
		htmlBuilder.imgEnd();
		
		htmlBuilder.imageStart().align("absmiddle").src(srcODS).style("cursor:pointer;").title(webutil.getMessage("grid.downloadODS"));
		
		if(AssertUtility.isTrue(downloadPartial)) {
			htmlBuilder.onClick("downloadPartialODS(" + onClickParameterForODS + ");");
			
		} else {
			htmlBuilder.onClick("downloadODS(" + onClickParameterForODS + ");");
		}
		
		htmlBuilder.tagClose();
		htmlBuilder.imgEnd();
	}
	
	private void renderDownloadProjectButton(HtmlBuilder htmlBuilder, String src, String onClickParameter) throws UnsupportedEncodingException, NamingException {
		AssertUtility.notNull(htmlBuilder);
		AssertUtility.notNull(src);
		AssertUtility.notNullAndNotSpace(onClickParameter);
		
		htmlBuilder.imageStart().align("absmiddle").src(src).style("cursor:pointer;").title(webutil.getMessage("common.download"));
		
		htmlBuilder.onClick("downloadProject(" + onClickParameter + ");");
		
		htmlBuilder.tagClose();
		htmlBuilder.imgEnd();
	}
	
	//元件屬性區
	public void setId(String id) {
		this.htmlId = id;
	}
	
	public String getId() {
		return this.htmlId;
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
