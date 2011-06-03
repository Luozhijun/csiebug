package csiebug.web.html.office.project;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import csiebug.util.AssertUtility;
import csiebug.util.ListUtility;
import csiebug.util.NumberFormatUtility;
import csiebug.util.StringUtility;
import csiebug.util.WebUtility;
import csiebug.web.html.HtmlBuilder;
import csiebug.web.html.HtmlComponent;
import csiebug.web.html.HtmlRenderException;
import csiebug.web.html.form.HtmlEditableLabel;


/**
 * 產生HTML Column
 * @author George_Tsai
 * @version 2009/6/17
 */

public class HtmlColumn extends HtmlComponent implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String fieldname;
	private String style;
	private String className;
	private String title;
	private String dataType;
	private String colspan;
	private String hideable;
	private String selAllScript;
	private String selectable;
	private String onClick;
	private String onDblClick;
	private String maxLineLength;
	private String typesettingAlgorithm;
	private String isReadOnly;
	
	private static int defaultMaxLineLength = 50;
	private boolean printFlag = true;
	private HtmlTable table;
	private HtmlRow row;
	private WebUtility webutil = new WebUtility();
	
	public HtmlColumn(HtmlRow row, String fieldname, String style, String className, String title, String dataType, String colspan, String sortable, String selAllScript, String selectable, String onClick, String onDblClick, String maxLineLength, String typesettingAlgorithm, String isReadOnly) {
		this.row = row;
		this.table = row.getTable();
		this.fieldname = fieldname;
		this.style = style;
		this.className = className;
		this.title = title;
		this.dataType = dataType;
		this.colspan = colspan;
		this.hideable = sortable;
		this.selAllScript = selAllScript;
		this.selectable = selectable;
		this.onClick = onClick;
		this.onDblClick = onDblClick;
		this.maxLineLength = maxLineLength;
		this.typesettingAlgorithm = typesettingAlgorithm;
		this.isReadOnly = isReadOnly;
	}
	
	public HtmlColumn() {
		
	}
	
	@SuppressWarnings("unchecked")
	public String renderStart() throws HtmlRenderException {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
        htmlBuilder.tdStart();
        
        //標頭列處理
        if(table.getCurrentMapIndex() == -1) {
        	//如果此欄設定隱藏,則標頭也需要隱藏
        	if(style != null) {
        		if(StringUtility.checkHtmlStyle(style, "display", "none")) {
        			htmlBuilder.style("display:none");
        		} else {
        			if((dataType == null || !dataType.equalsIgnoreCase("excelRowControl")) && !AssertUtility.isFalse(selectable)) {
        				htmlBuilder.style("cursor:pointer");
        			}
        		}
        	} else {
//        		if((dataType == null || !dataType.equalsIgnoreCase("excelRowControl")) && !AssertUtility.isFalse(selectable)) {
        			htmlBuilder.style("cursor:pointer");
//        		}
    		}
        	
        	if(colspan != null) {
        		htmlBuilder.colspan(colspan);
        	}
        	
        	if(hideable == null || AssertUtility.isTrue(hideable)) {
        		htmlBuilder.onClick("hideOrShowColumnForProject('" + table.getId() + "', " + table.getHeaderRowCount() + ", " + (row.getCurrentColumnIndex() + 1) + ", true);");
        		
        		if(title != null) {
        			htmlBuilder.title(title);
        		} else {
        			htmlBuilder.title(fieldname);
        		}
//        	} else {
//        		if((dataType == null || !dataType.equalsIgnoreCase("excelRowControl")) && !AssertUtility.isFalse(selectable)) {
//        			htmlBuilder.onClick("selColumnForProject('" + table.getId() + "', " + table.getHeaderRowCount() + ", " + (row.getCurrentColumnIndex() + 1) + ", event);");
//        		}
        	}
        } else { //資料列處理
        	try {
//	          	設定各樣屬性
        		htmlBuilder.id((table.getHeaderRowCount() + table.getCurrentMapIndex()) + "_" + (row.getCurrentColumnIndex() + 1));
        		
	            if(style != null) {
	            	htmlBuilder.style(style);
	            } else if(dataType != null && dataType.equalsIgnoreCase("excelRowControl")) {
	            	htmlBuilder.style("cursor:pointer");
	            } else if(row.getCurrentColumnIndex() == 0) {
	            	htmlBuilder.style("cursor:pointer");
	            }
	            
	            if(className != null) {
	            	htmlBuilder.className(className);
	            } else {
	            	if(dataType != null && dataType.equalsIgnoreCase("excelRowControl")) {
	            		htmlBuilder.className("TD_EXCEL_CONTROL");
	            	} else {
	            		htmlBuilder.className("TD");
	            	}
	            }
	            
	            //將dataType放入session,供download excel使用
	    		List<String> dataTypes = (List<String>)webutil.getSessionAttribute(table.getId() + "_DownloadDataTypes");
	    		if(dataTypes == null) {
	    			dataTypes = new ArrayList<String>();
	    		}
	            
	            if(dataType != null) {
	            	//啟用group by功能
	            	//第二個column的align right取消功用
	            	if(!(row.getCurrentColumnIndex() == 0) && (dataType.equalsIgnoreCase("number") || dataType.equalsIgnoreCase("currency"))) {
		            	htmlBuilder.align("right");
		            }
	            	
	            	htmlBuilder.appendString(" dataType=\"" + dataType + "\"");
	            	
	            	dataTypes.add(dataType);
	            } else {
	            	htmlBuilder.appendString(" dataType=\"string\"");
	            	
	            	dataTypes.add("string");
	            }
	            
	            webutil.setSessionAttribute(table.getId() + "_DownloadDataTypes", dataTypes);
	            
	            if(colspan != null) {
	        		htmlBuilder.colspan(colspan);
	        	}
	            
	            if(fieldname != null) {
	            	htmlBuilder.id(fieldname);
	            }
	            
	            if(dataType != null && dataType.equalsIgnoreCase("excelRowControl")) {
	        		htmlBuilder.onClick("selOneDataForClickForProject('" + table.getId() + "', " + (table.getHeaderRowCount() + table.getCurrentMapIndex()) + ", 0, " + table.getHeaderRowCount() + ", event);");
	        	} else {
	        		//如果是第二個column(outline level)則提供group收放功能
	        		if(row.getCurrentColumnIndex() == 0) {
						htmlBuilder.onClick("showGroupRowForProject('" + table.getId() + "', this.parentNode, '" + webutil.getBasePathForHTML() + "images/');");
					} else {
		        		//如果row的selectable沒有設定,則column的selectable才有用處
			            if(!AssertUtility.isTrue(row.getSelectable()) && !AssertUtility.isFalse(selectable)) {
			            	//onClick和onDblClick會衝突,以onClick為優先
			            	if(onClick != null) {
			            		htmlBuilder.onClick("selTDForCopyForProject('" + table.getId() + "', this, " + (table.getHeaderRowCount() + table.getCurrentMapIndex()) + ", " + (row.getCurrentColumnIndex() + 1) + ", " + table.getHeaderRowCount() + ", event, '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.confirm.copy") + "', '" + webutil.getMessage("common.ok") + "', '" + webutil.getMessage("common.cancel") + "');" + transScript(onClick));
			            		
			            		if(onDblClick != null) {
				            		htmlBuilder.onDblClick(transScript(onDblClick));
				            	}
			            	} else {
			            		if(onDblClick != null) {
			            			htmlBuilder.onDblClick("selTDForCopyForProject('" + table.getId() + "', this, " + (table.getHeaderRowCount() + table.getCurrentMapIndex()) + ", " + (row.getCurrentColumnIndex() + 1) + ", " + table.getHeaderRowCount() + ", event, '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.confirm.copy") + "', '" + webutil.getMessage("common.ok") + "', '" + webutil.getMessage("common.cancel") + "');" + transScript(onDblClick));
			            		} else {
				            		htmlBuilder.onClick("selTDForCopyForProject('" + table.getId() + "', this, " + (table.getHeaderRowCount() + table.getCurrentMapIndex()) + ", " + (row.getCurrentColumnIndex() + 1) + ", " + table.getHeaderRowCount() + ", event, '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.confirm.copy") + "', '" + webutil.getMessage("common.ok") + "', '" + webutil.getMessage("common.cancel") + "');");
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
		            }
        		}
        	} catch (UnsupportedEncodingException e) {
    			throw new HtmlRenderException(e);
    		} catch (NamingException e) {
    			throw new HtmlRenderException(e);
    		}
        }
        
        htmlBuilder.tagClose();
        
        return htmlBuilder.toString();
    }
	
	@SuppressWarnings("unchecked")
	public String renderBody(String value) throws HtmlRenderException {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		try {
			//標頭列處理
			if(table.getCurrentMapIndex() == -1) {
				
			} else { //資料列處理
				//如果是group by column,在group row的第二個column上附加folder的image(第一個column是excel control row)
				if(row.getCurrentMap() != null) {
		        	Map<String, String> map = row.getCurrentMap();
		        	
		        	if(row.getCurrentColumnIndex() == 0) {
						//進行縮排
			        	if(AssertUtility.isNotNullAndNotSpace(map.get("groupId"))) {
			        		int spaceCount = map.get("groupId").split("_").length - 1;
							
							for(int i = 0; i < spaceCount; i++) {
								htmlBuilder.space();
							}
			        	}
		        	
		        		if(map.get("groupType") != null && !map.get("groupType").equalsIgnoreCase("data")) {
							if(table.getImagePath() != null) {
								htmlBuilder.imgStart().src(webutil.getBasePathForHTML() + StringUtility.removeStartEndSlash(table.getImagePath())  + "/tree_open.gif").align("absmiddle").tagClose().imgEnd().toString();
							} else {
								htmlBuilder.imgStart().src(webutil.getBasePathForHTML() + "images/tree_open.gif").align("absmiddle").tagClose().imgEnd().toString();
							}
						} else {
							if(table.getImagePath() != null) {
								htmlBuilder.imgStart().src(webutil.getBasePathForHTML() + StringUtility.removeStartEndSlash(table.getImagePath())  + "/Blank.gif").align("absmiddle").tagClose().imgEnd().toString();
							} else {
								htmlBuilder.imgStart().src(webutil.getBasePathForHTML() + "images/Blank.gif").align("absmiddle").tagClose().imgEnd().toString();
							}
						}
					}
				}
				
				printFlag = false;
				
				String unTransValue = "";
				List<String> values = null;
				if(table.getCurrentMapIndex() == 0) {
					//將value放入session,供download excel使用
		    		values = (List<String>)webutil.getSessionAttribute(table.getId() + "_DownloadValues");
	        		if(values == null) {
	        			values = new ArrayList<String>();
	        		}
				}
				
				//如果<column></column>中間是空白,則印出欄位值
				if(!AssertUtility.isNotNullAndNotSpace(value)) {
					printFlag = true;
					
					//要檢查table是否設為唯獨,然後再檢查此column是否設為唯獨
					boolean readOnlyFlag = false;
					if(AssertUtility.isTrue(table.getIsReadOnly())) {
						readOnlyFlag = true;
					} else if(AssertUtility.isTrue(isReadOnly)) {
						readOnlyFlag = true;
					}
					
					if(dataType != null) {
						if(dataType.equalsIgnoreCase("selAllCheckBox")) {
							htmlBuilder.pStart().align("center").tagClose().inputStart().id("sel" + table.getId() + (table.getCurrentMapIndex() + 1)).name("sel" + table.getId() + (table.getCurrentMapIndex() + 1)).type("checkbox");
							if(selAllScript == null) {
								htmlBuilder.onClick("selOneDataForCheckBox('" + table.getId() + "', this, " + (table.getHeaderRowCount() + table.getCurrentMapIndex()) + ", 0, " + table.getHeaderRowCount() + ", event);");
							} else {
								//selAllScript中間如果有變數(var.)或(session.)或(request.),就做變數值替換
								htmlBuilder.onClick("selOneDataForCheckBox('" + table.getId() + "', this, " + (table.getHeaderRowCount() + table.getCurrentMapIndex()) + ", 0, " + table.getHeaderRowCount() + ", event);" + transScript(selAllScript));
							}
							htmlBuilder.tagClose();
						} else if(dataType.equalsIgnoreCase("excelRowControl")) {
							htmlBuilder.pStart().align("center").tagClose();
							htmlBuilder.appendString("" + (table.getCurrentMapIndex() + 1));
							htmlBuilder.pEnd();
						} else if(dataType.equalsIgnoreCase("SerialNumber")) {
							htmlBuilder.appendString("" + (table.getCurrentMapIndex() + 1));
							
							unTransValue = "(var.SerialNumber)";
						} else if(dataType.equalsIgnoreCase("currency")) {
							if(readOnlyFlag) {
								htmlBuilder.appendString(NumberFormatUtility.getCurrency(getFieldValue()));
							} else {
								HtmlEditableLabel label = new HtmlEditableLabel();
			            		label.setId(table.getId() + "_" + (table.getCurrentMapIndex() + 1) + "_" + row.getCurrentColumnIndex());
			            		label.setName(table.getId() + "_" + (table.getCurrentMapIndex() + 1) + "_" + row.getCurrentColumnIndex());
			            		label.setDefaultValue(NumberFormatUtility.getCurrency(getFieldValue()));
			            		label.setOnKeyDown("moveToOtherEditableLabel('" + table.getId() + "', " + (table.getCurrentMapIndex() + table.getHeaderRowCount()) + ", " + row.getCurrentColumnIndex() + ", event);");
			            		label.setOnChange("sendAJAXRequestForProjectField('" + table.getFormId() + "', '" + table.getId() + "', this);");
			            		label.setTypesetting("false");
			            		label.setDataType(dataType);
			            		htmlBuilder.appendString(label.render());
							}
		            		
		            		unTransValue = "(var." + fieldname + ")";
		            	} else {
		            		if(readOnlyFlag) {
		            			htmlBuilder.appendString(getFieldValue());
		            		} else {
			            		HtmlEditableLabel label = new HtmlEditableLabel();
			            		label.setId(table.getId() + "_" + (table.getCurrentMapIndex() + 1) + "_" + row.getCurrentColumnIndex());
			            		label.setName(table.getId() + "_" + (table.getCurrentMapIndex() + 1) + "_" + row.getCurrentColumnIndex());
			            		label.setDefaultValue(getFieldValue());
			            		label.setOnKeyDown("moveToOtherEditableLabel('" + table.getId() + "', " + (table.getCurrentMapIndex() + table.getHeaderRowCount()) + ", " + row.getCurrentColumnIndex() + ", event);");
			            		label.setOnChange("sendAJAXRequestForProjectField('" + table.getFormId() + "', '" + table.getId() + "', this);");
			            		label.setTypesetting("false");
			            		label.setDataType(dataType);
			            		htmlBuilder.appendString(label.render());
		            		}
		            		
		            		unTransValue = "(var." + fieldname + ")";
		            	}
					} else {
						if(readOnlyFlag) {
							htmlBuilder.appendString(getFieldValue());
						} else {
							HtmlEditableLabel label = new HtmlEditableLabel();
							label.setId(table.getId() + "_" + (table.getCurrentMapIndex() + 1) + "_" + row.getCurrentColumnIndex());
		            		label.setName(table.getId() + "_" + (table.getCurrentMapIndex() + 1) + "_" + row.getCurrentColumnIndex());
		            		label.setDefaultValue(getFieldValue());
		            		label.setOnKeyDown("moveToOtherEditableLabel('" + table.getId() + "', " + (table.getCurrentMapIndex() + table.getHeaderRowCount()) + ", " + row.getCurrentColumnIndex() + ", event);");
		            		label.setOnChange("sendAJAXRequestForProjectField('" + table.getFormId() + "', '" + table.getId() + "', this);");
		            		label.setTypesetting("false");
		            		htmlBuilder.appendString(label.render());
						}
	            		
	            		unTransValue = "(var." + fieldname + ")";
					}
				} else {
					//body中間如果有變數(var.)或(session.)或(request.),就做變數值替換
					String strHTML = transScript(value);
			        
			        //如果使用csiebug-ui元件,則把元件產出的td去掉,以免影響grid排版
			        strHTML = strHTML.replaceAll("<td class = \"TdHeader\">", "").replaceAll("<td class = \"TdBody\">", "").replaceAll("</td>", "");
			        
			        htmlBuilder.appendString(strHTML);
			        
			        unTransValue = value;
				}
				
				if(values != null) {
					values.add(unTransValue);
					webutil.setSessionAttribute(table.getId() + "_DownloadValues", values);
				}
	        }
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
			row.setCurrentColumnIndex(row.getCurrentColumnIndex() + 1);
			
	        //標頭列處理
	        if(table.getCurrentMapIndex() == -1) {
	        	//標頭置中
	        	htmlBuilder.pStart();
	    		htmlBuilder.align("center");
	    		htmlBuilder.tagClose();
	    		
	    		//將title放入session,供download excel使用
	    		List<String> titles = (List<String>)webutil.getSessionAttribute(table.getId() + "_DownloadTitles");
        		if(titles == null) {
        			titles = new ArrayList<String>();
        		}
	    		
	        	if(title != null) {
	        		//全選按鈕標題
	            	if(dataType != null && dataType.equalsIgnoreCase("selAllCheckBox")) {
	            		htmlBuilder.inputStart().id("sel" + table.getId() + "All").name("sel" + table.getId() + "All").type("checkbox");
	            		if(selAllScript == null) {
	            			htmlBuilder.onClick("selAllData('" + table.getId() + "', " + (table.getHeaderRowCount() - 1) + ", " + row.getCurrentColumnIndex() + ");");
	            		} else {
	            			//selAllScript中間如果有變數(var.)或(session.)或(request.),就做變數值替換
	            			htmlBuilder.onClick("selAllData('" + table.getId() + "', " + (table.getHeaderRowCount() - 1) + ", " + row.getCurrentColumnIndex() + ");" + transScript(selAllScript));
	            		}
	            		
	            		htmlBuilder.tagClose();
	            	}
	            	
	        		htmlBuilder.appendString(title);
	        		
	        		titles.add(title);
	        	} else {
	        		//全選按鈕標題
	            	if(dataType != null && dataType.equalsIgnoreCase("selAllCheckBox")) {
	            		htmlBuilder.inputStart().id("sel" + table.getId() + "All").name("sel" + table.getId() + "All").type("checkbox");
	            		if(selAllScript == null) {
	            			htmlBuilder.onClick("selAllData('" + table.getId() + "', " + (table.getHeaderRowCount() - 1) + ", " + row.getCurrentColumnIndex() + ");");
	            		} else {
	            			//selAllScript中間如果有變數(var.)或(session.)或(request.),就做變數值替換
	            			htmlBuilder.onClick("selAllData('" + table.getId() + "', " + (table.getHeaderRowCount() - 1) + ", " + row.getCurrentColumnIndex() + ");" + transScript(selAllScript));
	            		}
	            		
	            		htmlBuilder.tagClose().appendString(webutil.getMessage("common.SelAll"));
	            		
	            		titles.add(webutil.getMessage("common.SelAll"));
	            	} else if(dataType != null && dataType.equalsIgnoreCase("SerialNumber")) {
	            		htmlBuilder.appendString(webutil.getMessage("common.SerialNumber"));
	            		
	            		titles.add(webutil.getMessage("common.SerialNumber"));
					} else if(fieldname != null) { //用欄位名稱當標題
						htmlBuilder.appendString(fieldname);
						
						titles.add(fieldname);
					} else {
						titles.add("");
					}
	            	
	            	webutil.setSessionAttribute(table.getId() + "_DownloadTitles", titles);
				}
	        } else { //資料列處理
	            //如果是<column />,直接印出欄位值
	            if(printFlag) {
	            	//如果是group by column,在group row的第二個column上附加folder的image(第一個column是excel control row)
	    			if(row.getCurrentMap() != null) {
	    	        	Map<String, String> map = row.getCurrentMap();
	    	        	
	    	        	if(row.getCurrentColumnIndex() == 1) {
	    					//進行縮排
		    	        	if(AssertUtility.isNotNullAndNotSpace(map.get("groupId"))) {
		    	        		int spaceCount = map.get("groupId").split("_").length - 1;
		    					
		    					for(int i = 0; i < spaceCount; i++) {
		    						htmlBuilder.space();
		    					}
		    	        	}
		    	        	
		    	        	if(map.get("groupType") != null && !map.get("groupType").equalsIgnoreCase("data")) {
		    	        		if(table.getImagePath() != null) {
	    							htmlBuilder.imgStart().src(webutil.getBasePathForHTML() + StringUtility.removeStartEndSlash(table.getImagePath())  + "/tree_open.gif").align("absmiddle").tagClose().imgEnd().toString();
	    						} else {
	    							htmlBuilder.imgStart().src(webutil.getBasePathForHTML() + "images/tree_open.gif").align("absmiddle").tagClose().imgEnd().toString();
	    						}
		    				} else {
								if(table.getImagePath() != null) {
									htmlBuilder.imgStart().src(webutil.getBasePathForHTML() + StringUtility.removeStartEndSlash(table.getImagePath())  + "/Blank.gif").align("absmiddle").tagClose().imgEnd().toString();
								} else {
									htmlBuilder.imgStart().src(webutil.getBasePathForHTML() + "images/Blank.gif").align("absmiddle").tagClose().imgEnd().toString();
								}
							}
	    				}
	    			}
	    			
	    			String unTransValue = "";
	    			List<String> values = null;
					if(table.getCurrentMapIndex() == 0) {
						//將value放入session,供download excel使用
			    		values = (List<String>)webutil.getSessionAttribute(table.getId() + "_DownloadValues");
		        		if(values == null) {
		        			values = new ArrayList<String>();
		        		}
					}
	            	
					//要檢查table是否設為唯獨,然後再檢查此column是否設為唯獨
					boolean readOnlyFlag = false;
					if(AssertUtility.isTrue(table.getIsReadOnly())) {
						readOnlyFlag = true;
					} else if(AssertUtility.isTrue(isReadOnly)) {
						readOnlyFlag = true;
					}
					
	            	if(dataType != null) {
	            		if(dataType.equalsIgnoreCase("selAllCheckBox")) {
							htmlBuilder.pStart().align("center").tagClose().inputStart().id("sel" + table.getId() + (table.getCurrentMapIndex() + 1)).name("sel" + table.getId() + (table.getCurrentMapIndex() + 1)).type("checkbox");
							if(selAllScript == null) {
								htmlBuilder.onClick("selOneDataForCheckBox('" + table.getId() + "', this, " + (table.getHeaderRowCount() + table.getCurrentMapIndex()) + ", 0, " + table.getHeaderRowCount() + ", event);");
							} else {
								//selAllScript中間如果有變數(var.)或(session.)或(request.),就做變數值替換
								htmlBuilder.onClick("selOneDataForCheckBox('" + table.getId() + "', this, " + (table.getHeaderRowCount() + table.getCurrentMapIndex()) + ", 0, " + table.getHeaderRowCount() + ", event);" + transScript(selAllScript));
							}
							htmlBuilder.tagClose();
						} else if(dataType.equalsIgnoreCase("excelRowControl")) {
							htmlBuilder.pStart().align("center").tagClose();
							htmlBuilder.appendString("" + (table.getCurrentMapIndex() + 1));
							htmlBuilder.pEnd();
	            		} else if(dataType.equalsIgnoreCase("SerialNumber")) {
							htmlBuilder.appendString("" + (table.getCurrentMapIndex() + 1));
							
							unTransValue = "(var.SerialNumber)";
						} else if(dataType.equalsIgnoreCase("currency")) {
							if(readOnlyFlag) {
								htmlBuilder.appendString(NumberFormatUtility.getCurrency(getFieldValue()));
							} else {
								HtmlEditableLabel label = new HtmlEditableLabel();
								label.setId(table.getId() + "_" + (table.getCurrentMapIndex() + 1) + "_" + row.getCurrentColumnIndex());
			            		label.setName(table.getId() + "_" + (table.getCurrentMapIndex() + 1) + "_" + row.getCurrentColumnIndex());
			            		label.setDefaultValue(NumberFormatUtility.getCurrency(getFieldValue()));
			            		label.setOnKeyDown("moveToOtherEditableLabel('" + table.getId() + "', " + (table.getCurrentMapIndex() + table.getHeaderRowCount()) + ", " + row.getCurrentColumnIndex() + ", event);");
			            		label.setOnChange("sendAJAXRequestForProjectField('" + table.getFormId() + "', '" + table.getId() + "', this);");
			            		label.setTypesetting("false");
			            		label.setDataType(dataType);
			            		htmlBuilder.appendString(label.render());
							}
		            		
		            		unTransValue = "(var." + fieldname + ")";
		            	} else {
		            		if(readOnlyFlag) {
		            			htmlBuilder.appendString(getFieldValue());
		            		} else {
			            		HtmlEditableLabel label = new HtmlEditableLabel();
			            		label.setId(table.getId() + "_" + (table.getCurrentMapIndex() + 1) + "_" + row.getCurrentColumnIndex());
			            		label.setName(table.getId() + "_" + (table.getCurrentMapIndex() + 1) + "_" + row.getCurrentColumnIndex());
			            		label.setDefaultValue(getFieldValue());
			            		label.setOnKeyDown("moveToOtherEditableLabel('" + table.getId() + "', " + (table.getCurrentMapIndex() + table.getHeaderRowCount()) + ", " + row.getCurrentColumnIndex() + ", event);");
			            		label.setOnChange("sendAJAXRequestForProjectField('" + table.getFormId() + "', '" + table.getId() + "', this);");
			            		label.setTypesetting("false");
			            		label.setDataType(dataType);
			            		htmlBuilder.appendString(label.render());
		            		}
		            		
		            		unTransValue = "(var." + fieldname + ")";
		            	}
					} else {
						if(readOnlyFlag) {
							htmlBuilder.appendString(getFieldValue());
						} else {
							HtmlEditableLabel label = new HtmlEditableLabel();
							label.setId(table.getId() + "_" + (table.getCurrentMapIndex() + 1) + "_" + row.getCurrentColumnIndex());
		            		label.setName(table.getId() + "_" + (table.getCurrentMapIndex() + 1) + "_" + row.getCurrentColumnIndex());
		            		label.setDefaultValue(getFieldValue());
		            		label.setOnKeyDown("moveToOtherEditableLabel('" + table.getId() + "', " + (table.getCurrentMapIndex() + table.getHeaderRowCount()) + ", " + row.getCurrentColumnIndex() + ", event);");
		            		label.setOnChange("sendAJAXRequestForProjectField('" + table.getFormId() + "', '" + table.getId() + "', this);");
		            		label.setTypesetting("false");
		            		htmlBuilder.appendString(label.render());
						}
	            		
	            		unTransValue = "(var." + fieldname + ")";
					}
	            	
	            	if(values != null) {
	            		values.add(unTransValue);
						webutil.setSessionAttribute(table.getId() + "_DownloadValues", values);
					}
	            }
	        }
	        
	        htmlBuilder.tdEnd();
		} catch (UnsupportedEncodingException e) {
			throw new HtmlRenderException(e);
		} catch (NamingException e) {
			throw new HtmlRenderException(e);
		}
        
        return htmlBuilder.toString();
    }
	
	//取得map中的欄位值
	private String getFieldValue() {
		String strValue = "";
		
        if(row.getCurrentMap() != null) {
        	Map<String, String> map = row.getCurrentMap();
        	
        	if(fieldname != null) {
        		if(map.get(fieldname) != null) {
        			if(typesettingAlgorithm != null && !typesettingAlgorithm.equalsIgnoreCase("none")) {
        				if(maxLineLength != null && NumberFormatUtility.isValidPositiveInteger(maxLineLength)) {
        					strValue = StringUtility.getTypesettingString(map.get(fieldname).toString(), "<br>", Integer.parseInt(maxLineLength), typesettingAlgorithm);
        				} else {
        					strValue = StringUtility.getTypesettingString(map.get(fieldname).toString(), "<br>", defaultMaxLineLength, typesettingAlgorithm);
        				}
        			} else {
        				strValue = map.get(fieldname).toString();
        			}
        		}
        	}
        }
        
        return strValue;
	}
	
	//如果有變數(var.)或(session.)或(request.),就做變數值替換
	private String transScript(String original) throws UnsupportedEncodingException, NamingException {
		AssertUtility.notNull(original);
		
		//如果有變數(var.)或(sessionVar.)或(requestVar.),就做變數值替換
		String strReturn = original;
		if(row.getCurrentMap() != null) {
        	Map<String, String> map = row.getCurrentMap();
        	strReturn = ListUtility.replaceVariable(map, original);
        }
		
		strReturn = ListUtility.replaceSessionVariable(strReturn);
		strReturn = ListUtility.replaceRequestVariable(strReturn);
        
		//column獨有變數
		//替換column index
		while(strReturn.indexOf("(var.ColumnIndex)") != -1) {
			strReturn = strReturn.substring(0, strReturn.indexOf("(var.ColumnIndex)")) + (row.getCurrentColumnIndex() + 1) + strReturn.substring(strReturn.indexOf("(var.ColumnIndex)") + "(var.ColumnIndex)".length(), strReturn.length());
		}
		
		//row變數
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
	public void setRow(HtmlRow row) {
		AssertUtility.notNull(row);
		
		this.row = row;
		this.table = row.getTable();
	}
	
	public HtmlRow getRow() {
		return this.row;
	}
	
	public void setFieldname(String value) {
		this.fieldname = value;
	}
	
	public String getFieldname() {
		return this.fieldname;
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
	
	public void setTitle(String header) {
		this.title = header;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setDataType(String cellType) {
		this.dataType = cellType;
	}
	
	public String getDataType() {
		return this.dataType;
	}
	
	public void setColspan(String colspan) {
		this.colspan = colspan;
	}
	
	public String getColspan() {
		return this.colspan;
	}
	
	public void setHideable(String sortable) {
		this.hideable = sortable;
	}
	
	public String getHideable() {
		return this.hideable;
	}
	
	public void setSelAllScript(String script) {
		this.selAllScript = script;
	}
	
	public String getSelAllScript() {
		return this.selAllScript;
	}
	
	public void setPrintFlag(boolean flag) {
		this.printFlag = flag;
	}
	
	public boolean getPrintFlag() {
		return this.printFlag;
	}

	public void setSelectable(String selectable) {
		this.selectable = selectable;
	}

	public String getSelectable() {
		return selectable;
	}
	
	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}
	
	public String getOnClick() {
		return onClick;
	}
	
	public void setOnDblClick(String onDblClick) {
		this.onDblClick = onDblClick;
	}
	
	public String getOnDblClick() {
		return onDblClick;
	}

	public void setIsReadOnly(String isReadOnly) {
		this.isReadOnly = isReadOnly;
	}

	public String getIsReadOnly() {
		return isReadOnly;
	}

	public void setMaxLineLength(String maxLineLength) {
		this.maxLineLength = maxLineLength;
	}

	public String getMaxLineLength() {
		return maxLineLength;
	}

	public void setTypesettingAlgorithm(String typesettingAlgorithm) {
		this.typesettingAlgorithm = typesettingAlgorithm;
	}

	public String getTypesettingAlgorithm() {
		return typesettingAlgorithm;
	}
}
