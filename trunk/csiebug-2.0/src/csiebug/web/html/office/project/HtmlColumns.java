package csiebug.web.html.office.project;

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
import csiebug.web.html.HtmlComponentOnlyBody;
import csiebug.web.html.HtmlRenderException;


/**
 * 產生HTML Dynamic columns
 * @author George_Tsai
 * @version 2009/6/17
 */

public class HtmlColumns extends HtmlComponentOnlyBody {
	private String dynFields;
	
	private static int defaultMaxLineLength = 50;
	private List<Map<String, String>> dynDatas;
	private HtmlTable table;
	private HtmlRow row;
	private WebUtility webutil = new WebUtility();
	
	public HtmlColumns(HtmlRow row, String dynFields) {
		this.row = row;
		this.table = row.getTable();
		this.dynFields = dynFields;
	}
	
	public HtmlColumns() {
		
	}
	
	@SuppressWarnings("unchecked")
	public String renderBody(String content) throws HtmlRenderException {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
        
        try {
        	if(dynFields != null) {
    			if(webutil.getRequestAttribute(dynFields) != null) {
                	dynDatas = (List<Map<String, String>>)webutil.getRequestAttribute(dynFields);
                }
            }
        	
	        for(int i = 0; i < dynDatas.size(); i++) {
	        	Map<String, String> map = dynDatas.get(i);
	            htmlBuilder.tdStart();
	            
	            //標頭列處理
	            if(table.getCurrentMapIndex() == -1) {
	            	//如果此欄設定隱藏,則標頭也需要隱藏
	            	if(map.get("style") != null) {
	            		if(StringUtility.checkHtmlStyle(map.get("style"), "display", "none")) {
	            			htmlBuilder.style("display:none");
	            		} else {
//	            			if((map.get("dataType") == null || !map.get("dataType").equalsIgnoreCase("excelRowControl")) && AssertUtility.isTrue(map.get("selectable"))) {
	            				htmlBuilder.style("cursor:pointer");
//	            			}
	            		}
	            	} else {
	            		if((map.get("dataType") == null || !map.get("dataType").equalsIgnoreCase("excelRowControl")) && AssertUtility.isTrue(map.get("selectable"))) {
	            			htmlBuilder.style("cursor:pointer");
	            		}
	        		}
	            	
	            	if(map.get("colspan") != null) {
	            		htmlBuilder.colspan(map.get("colspan"));
	            	}
	            	
	            	if(map.get("hideable") == null || AssertUtility.isTrue(map.get("hideable"))) {
	            		htmlBuilder.onClick("hideOrShowColumnForProject('" + table.getId() + "', " + table.getHeaderRowCount() + ", " + (row.getCurrentColumnIndex() + 1) + ", true);");
	            		
	            		if(map.get("title") != null) {
	            			htmlBuilder.title(map.get("title"));
	            		} else {
	            			htmlBuilder.title(map.get("fieldname"));
	            		}
//	            	} else {
//	            		if((map.get("dataType") == null || !map.get("dataType").equalsIgnoreCase("excelRowControl")) && AssertUtility.isTrue(map.get("selectable"))) {
//	            			htmlBuilder.onClick("selColumnForProject('" + table.getId() + "', " + table.getHeaderRowCount() + ", " + (row.getCurrentColumnIndex() + 1) + ", event);");
//	            		}
	            	}
	            	
	            	htmlBuilder.tagClose();
	            	
	            	//標頭置中
	            	htmlBuilder.pStart();
	        		htmlBuilder.align("center");
	        		htmlBuilder.tagClose();
	        		
	        		//將title放入session,供download excel使用
		    		List<String> titles = (List<String>)webutil.getSessionAttribute(table.getId() + "_DownloadTitles");
	        		if(titles == null) {
	        			titles = new ArrayList<String>();
	        		}
	        		
	        		if(map.get("title") != null) {
	        			//全選按鈕標題
		            	if(map.get("dataType") != null && map.get("dataType").equalsIgnoreCase("selAllCheckBox")) {
		            		htmlBuilder.inputStart().id("sel" + table.getId() + "All").name("sel" + table.getId() + "All").type("checkbox").onClick("selAllData('" + table.getId() + "', " + (table.getHeaderRowCount() - 1) + ", " + row.getCurrentColumnIndex() + ")").tagClose();
		            	}
		            	
	            		htmlBuilder.appendString(map.get("title"));
	            		
	            		titles.add(map.get("title"));
	        		} else {
	        			//全選按鈕標題
		            	if(map.get("dataType") != null && map.get("dataType").equalsIgnoreCase("selAllCheckBox")) {
		            		htmlBuilder.inputStart().id("sel" + table.getId() + "All").name("sel" + table.getId() + "All").type("checkbox").onClick("selAllData('" + table.getId() + "', " + (table.getHeaderRowCount() - 1) + ", " + row.getCurrentColumnIndex() + ")").tagClose().appendString(webutil.getMessage("common.SelAll"));
		            		
		            		titles.add(webutil.getMessage("common.SelAll"));
		            	} else if(map.get("dataType") != null && map.get("dataType").equalsIgnoreCase("SerialNumber")) {
							htmlBuilder.appendString(webutil.getMessage("common.SerialNumber"));
							
							titles.add(webutil.getMessage("common.SerialNumber"));
						} else { //用欄位名稱當標題
		            		htmlBuilder.appendString(map.get("fieldname"));
		            		
		            		titles.add(map.get("fieldname"));
		            	}
					}
	        		
	        		webutil.setSessionAttribute(table.getId() + "_DownloadTitles", titles);
	            } else { //資料列處理
	//              設定各樣屬性
	            	
	            	htmlBuilder.id((table.getHeaderRowCount() + table.getCurrentMapIndex()) + "_" + (row.getCurrentColumnIndex() + 1));
	            	
	            	if(map.get("style") != null) {
		            	htmlBuilder.style(map.get("style"));
		            } else if(map.get("dataType") != null && map.get("dataType").equalsIgnoreCase("excelRowControl")) {
		            	htmlBuilder.style("cursor:pointer");
		            }
		            
		            if(map.get("className") != null) {
		            	htmlBuilder.className(map.get("className"));
		            } else {
		            	if(map.get("dataType") != null && map.get("dataType").equalsIgnoreCase("excelRowControl")) {
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
		            
		            if(map.get("dataType") != null) {
		            	//啟用group by功能
		            	//第二個column的align right取消功用
		            	if(!(row.getCurrentColumnIndex() == 0) && map.get("dataType").equalsIgnoreCase("number") || map.get("dataType").equalsIgnoreCase("currency")) {
		            		htmlBuilder.align("right");
		            	}
		            	
		            	htmlBuilder.appendString(" dataType=\"" + map.get("dataType") + "\"");
		            	
		            	dataTypes.add(map.get("dataType"));
		            } else {
		            	htmlBuilder.appendString(" dataType=\"string\"");
		            	
		            	dataTypes.add("string");
		            }
		            
		            webutil.setSessionAttribute(table.getId() + "_DownloadDataTypes", dataTypes);
		            
		            if(map.get("colspan") != null) {
	            		htmlBuilder.colspan(map.get("colspan"));
	            	}
		            
		            if(map.get("fieldname") != null) {
		            	htmlBuilder.id(map.get("fieldname"));
		            }
		            
		            if(map.get("dataType") != null && map.get("dataType").equalsIgnoreCase("excelRowControl")) {
		        		htmlBuilder.onClick("selOneDataForClickForProject('" + table.getId() + "', " + (table.getHeaderRowCount() + table.getCurrentMapIndex()) + ", 0, " + table.getHeaderRowCount() + ", event);");
		        	} else {
			            try {
			            	//如果row的selectable沒有設定,則column的selectable才有用處
				            if(!AssertUtility.isTrue(row.getSelectable()) && AssertUtility.isTrue(map.get("selectable"))) {
				            	//onClick和onDblClick會衝突,以onClick為優先
				            	if(map.get("onClick") != null) {
				            		htmlBuilder.onClick("selTDForCopyForProject('" + table.getId() + "', this, " + (table.getHeaderRowCount() + table.getCurrentMapIndex()) + ", " + (row.getCurrentColumnIndex() + 1) + ", " + table.getHeaderRowCount() + ", event, '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.confirm.copy") + "', '" + webutil.getMessage("common.ok") + "', '" + webutil.getMessage("common.cancel") + "');" + transScript(map.get("onClick")));
				            		
				            		if(map.get("onDblClick") != null) {
					            		htmlBuilder.onDblClick(transScript(map.get("onDblClick")));
					            	}
				            	} else {
				            		if(map.get("onDblClick") != null) {
				            			htmlBuilder.onDblClick("selTDForCopyForProject('" + table.getId() + "', this, " + (table.getHeaderRowCount() + table.getCurrentMapIndex()) + ", " + (row.getCurrentColumnIndex() + 1) + ", " + table.getHeaderRowCount() + ", event, '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.confirm.copy") + "', '" + webutil.getMessage("common.ok") + "', '" + webutil.getMessage("common.cancel") + "');" + transScript(map.get("onDblClick")));
				            		} else {
					            		htmlBuilder.onClick("selTDForCopyForProject('" + table.getId() + "', this, " + (table.getHeaderRowCount() + table.getCurrentMapIndex()) + ", " + (row.getCurrentColumnIndex() + 1) + ", " + table.getHeaderRowCount() + ", event, '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.confirm.copy") + "', '" + webutil.getMessage("common.ok") + "', '" + webutil.getMessage("common.cancel") + "');");
					            	}
				            	}
				            } else {
				            	if(map.get("onClick") != null) {
				            		htmlBuilder.onClick(transScript(map.get("onClick")));
				            	}
				            	
				            	if(map.get("onDblClick") != null) {
				            		htmlBuilder.onDblClick(transScript(map.get("onDblClick")));
				            	}
				            }
			            } catch (UnsupportedEncodingException e) {
			    			throw new HtmlRenderException(e);
			    		} catch (NamingException e) {
			    			throw new HtmlRenderException(e);
			    		}
		        	}
		            
		            htmlBuilder.tagClose();

		            //如果是group by column,在group row的第二個column上附加folder的image(第一個column是excel control row)
					if(row.getCurrentMap() != null) {
			        	Map<String, String> map2 = row.getCurrentMap();
			        	
			        	if(row.getCurrentColumnIndex() == 0) {
							//進行縮排
				        	if(AssertUtility.isNotNullAndNotSpace(map2.get("groupId"))) {
				        		int spaceCount = map2.get("groupId").split("_").length - 1;
								
								for(int j = 0; j < spaceCount; j++) {
									htmlBuilder.space();
								}
				        	}
			        	
			        		if(map2.get("groupType") != null && !map2.get("groupType").equalsIgnoreCase("data")) {
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
		            
		            if(map.get("dataType") != null) {
		            	if(map.get("dataType").equalsIgnoreCase("selAllCheckBox")) {
							htmlBuilder.pStart().align("center").tagClose().inputStart().id("sel" + table.getId() + (table.getCurrentMapIndex() + 1)).name("sel" + table.getId() + (table.getCurrentMapIndex() + 1)).type("checkbox").onClick("selOneDataForCheckBox('" + table.getId() + "', this, " + (table.getHeaderRowCount() + table.getCurrentMapIndex()) + ", 0, " + table.getHeaderRowCount() + ");").tagClose();
						} else if(map.get("dataType").equalsIgnoreCase("excelRowControl")) {
							htmlBuilder.pStart().align("center").tagClose();
							htmlBuilder.appendString("" + (table.getCurrentMapIndex() + 1));
							htmlBuilder.pEnd();
						} else if(map.get("dataType").equalsIgnoreCase("SerialNumber")) {
							htmlBuilder.appendString("" + (table.getCurrentMapIndex() + 1));
							
							unTransValue = "(var.SerialNumber)";
						} else if(map.get("dataType").equalsIgnoreCase("currency")) {
		            		htmlBuilder.appendString(NumberFormatUtility.getCurrency(getFieldValue(map)));
		            		
		            		unTransValue = "(var." + map.get("fieldname") + ")";
		            	} else {
		            		htmlBuilder.appendString(getFieldValue(map));
		            		
		            		unTransValue = "(var." + map.get("fieldname") + ")";
		            	}
					} else {
						htmlBuilder.appendString(getFieldValue(map));
						
						unTransValue = "(var." + map.get("fieldname") + ")";
					}
		            
		            if(values != null) {
		            	values.add(unTransValue);
						webutil.setSessionAttribute(table.getId() + "_DownloadValues", values);
					}
	            }
	            
	            row.setCurrentColumnIndex(row.getCurrentColumnIndex() + 1);
	            
	            htmlBuilder.tdEnd();
	        }
        } catch (UnsupportedEncodingException e) {
			throw new HtmlRenderException(e);
		} catch (NamingException e) {
			throw new HtmlRenderException(e);
		}
        
        return htmlBuilder.toString();
	}
	
	//取得map中的欄位值
	private String getFieldValue(Map<String, String> map) {
		AssertUtility.notNull(map);
		
		String strValue = "";
		
        if(row.getCurrentMap() != null) {
        	Map<String, String> dataMap = row.getCurrentMap();
        	
        	if(map.get("fieldname") != null) {
        		if(dataMap.get(map.get("fieldname")) != null) {
        			if(map.get("typesettingAlgorithm") != null && !map.get("typesettingAlgorithm").equalsIgnoreCase("none")) {
        				if(map.get("maxLineLength") != null && NumberFormatUtility.isValidPositiveInteger(map.get("maxLineLength"))) {
        					strValue = StringUtility.getTypesettingString(dataMap.get(map.get("fieldname")), "<br>", Integer.parseInt(map.get("maxLineLength")), map.get("typesettingAlgorithm"));
        				} else {
        					strValue = StringUtility.getTypesettingString(dataMap.get(map.get("fieldname")), "<br>", defaultMaxLineLength, map.get("typesettingAlgorithm"));
        				}
        			} else {
        				strValue = dataMap.get(map.get("fieldname"));
        			}
        		}
        	}
        }
        
        return strValue;
	}
	
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
	
	public void setDynFields(String value) {
		this.dynFields = value;
	}
	
	public String getDynFields() {
		return this.dynFields;
	}
}
