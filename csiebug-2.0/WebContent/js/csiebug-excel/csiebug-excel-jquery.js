/**
 * 將focus的row往上移動
 * @author csiebug
 * @version 2010/11/11
 */
function moveUpRowForExcel(formId, tableId, headerRowCount, e, title, message, ok, cancel) {
	if(isSelectOneRow(tableId)) {
		var trs = $("#" + tableId + " tr");
		var focusRow = $(".TR_FOCUS");
		
		var i = trs.index(focusRow);
		if(i != headerRowCount) { //如果已經是第一個row則不需要交換了
			//往上找到第一個不是隱藏的row的index
			var preIndex;
			for(var j = (i - 1); j >= headerRowCount; j--) {
				if(trs[j].style.display != "none") {
					preIndex = j;
					break;
				}
			}
			
			//兩個row開始做交換
			if(preIndex != null) {
				var tr1 = trs[preIndex];
				var tr2 = trs[i];
				
				switchRowForExcel(tableId, preIndex, i, headerRowCount, e, title, message, ok, cancel);
				
				tr2.className = "TR_ODD";
				tr1.className = "TR_FOCUS";
				
				sendAJAXRequestForExcelOrder(formId, tableId, tr1);
				sendAJAXRequestForExcelOrder(formId, tableId, tr2);
				
				//等別的運算累積滿了自動送出,或是由使用者手動決定全部送出
				//sendUpdateOrderPool(formId, tableId);
			}
		}			
	}
}

/**
 * 將focus的row往下移動
 * @author csiebug
 * @version 2010/11/11
 */
function moveDownRowForProject(formId, tableId, headerRowCount, e, title, message, ok, cancel) {
	if(isSelectOneRow(tableId)) {
		var trs = $("#" + tableId + " tr");
		var focusRow = $(".TR_FOCUS");
		
		var i = trs.index(focusRow);
		//往下找到第一個不是隱藏的row的index
		var nextIndex;
		for(var j = (i + 1); j < trs.length; j++) {
			if(trs[j].style.display != "none") {
				nextIndex = j;
				break;
			}
		}
		
		//兩個row開始做交換
		if(nextIndex != null) {
			var tr1 = trs[i];
			var tr2 = trs[nextIndex];
			
			switchRowForExcel(tableId, i, nextIndex, headerRowCount, e, title, message, ok, cancel);
			
			tr1.className = "TR_ODD";
			tr2.className = "TR_FOCUS";
			
			sendAJAXRequestForExcelOrder(formId, tableId, tr1);
			sendAJAXRequestForExcelOrder(formId, tableId, tr2);
			
			//等別的運算累積滿了自動送出,或是由使用者手動決定全部送出
			//sendUpdateOrderPool(formId, tableId);
		}
	}
}

/**
 * 兩列資料交換
 * @author csiebug
 * @version 2010/11/11
 */
function switchRowForExcel(tableId, index1, index2, headerRowCount, e, title, message, ok, cancel) {
	var tr1 = $("#" + tableId + " tr:nth-child(" + (index1 + 1) + ")");
	var tr2 = $("#" + tableId + " tr:nth-child(" + (index2 + 1) + ")");
	var aryTD1 = getDataTD(tr1.get(0));
	var aryTD2 = getDataTD(tr2.get(0));
	
	//複製HTML碼,控制項的值不會一起過去,所以要記錄起來再設回去
	var inputs1 = $("input", tr1);
	var selects1 = $("select", tr1);
	var inputs2 = $("input", tr2);
	var selects2 = $("select", tr2);
	
	var aryControl1 = new Array(inputs1.length + selects1.length);
	
	for(var i = 0; i < aryControl1.length; i++) {
		aryControl1[i] = new Array(2);
	}
	
	var aryControl2 = new Array(inputs2.length + selects2.length);
	
	for(var i = 0; i < aryControl2.length; i++) {
		aryControl2[i] = new Array(2);
	}
	
	for(var i = 0; i < inputs1.length; i++) {
		var obj = inputs1[i];
		
		if(obj.getAttribute("type") == "checkbox") {
			aryControl1[i][0] = obj.id;
			if(obj.checked == true) {
				aryControl1[i][1] = "checked";
			} else {
				aryControl1[i][1] = "unchecked";
			}
		} else if(obj.getAttribute("type") == "radio") {
			aryControl1[i][0] = obj.id;
			if(obj.checked == true) {
				aryControl1[i][1] = "checked";
			} else {
				aryControl1[i][1] = "unchecked";
			}
		} else {
			aryControl1[i][0] = obj.id;
			aryControl1[i][1] = obj.value;
		}
	}
	
	for(var i = 0; i < selects1.length; i++) {
		var obj = selects1[i];
		
		aryControl1[i + inputs1.length][0] = obj.id;
		aryControl1[i + inputs1.length][1] = obj.value;
	}
	
	for(var i = 0; i < inputs2.length; i++) {
		var obj = inputs2[i];
		
		if(obj.getAttribute("type") == "checkbox") {
			aryControl2[i][0] = obj.id;
			if(obj.checked == true) {
				aryControl2[i][1] = "checked";
			} else {
				aryControl2[i][1] = "unchecked";
			}
		} else if(obj.getAttribute("type") == "radio") {
			aryControl2[i][0] = obj.id;
			if(obj.checked == true) {
				aryControl2[i][1] = "checked";
			} else {
				aryControl2[i][1] = "unchecked";
			}
		} else {
			aryControl2[i][0] = obj.id;
			aryControl2[i][1] = obj.value;
		}
	}
	
	for(var i = 0; i < selects2.length; i++) {
		var obj = selects2[i];
		
		aryControl2[i + inputs2.length][0] = obj.id;
		aryControl2[i + inputs2.length][1] = obj.value;
	}
	
	//html碼交換;excel control row不需要交換內容
	for(var i = 1; i < aryTD1.length; i++) {
		var temp = aryTD1[i].innerHTML;
		aryTD1[i].innerHTML = aryTD2[i].innerHTML;
		aryTD2[i].innerHTML = temp;
	}
	
	//控制項值交換
	inputs1 = $("input", tr1);
	selects1 = $("select", tr1);
	inputs2 = $("input", tr2);
	selects2 = $("select", tr2);
	
	for(var i = 0; i < inputs1.length; i++) {
		for(var j = 0; j < aryControl2.length; j++) {
			var obj = inputs1[i];
			
			if(obj.id == aryControl2[j][0]) {
				if(obj.getAttribute("type") == "checkbox") {
					if(aryControl2[j][1] == "checked") {
						obj.checked = true;
					} else {
						obj.checked = false;
					}
				} else if(obj.getAttribute("type") == "radio") {
					if(aryControl2[j][1] == "checked") {
						obj.checked = true;
					} else {
						obj.checked = false;
					}
				} else {
					if(j == 0) { //order不需要交換內容
						obj.value = aryControl1[j][1];
					} else {
						obj.value = aryControl2[j][1];
					}
				}
			}
		}
	}
	
	for(var i = 0; i < selects1.length; i++) {
		for(var j = 0; j < aryControl2.length; j++) {
			var obj = selects1[i];
			
			if(obj.id == aryControl2[j][0]) {
				obj.value = aryControl2[j][1];
			}
		}
	}
	
	for(var i = 0; i < inputs2.length; i++) {
		for(var j = 0; j < aryControl1.length; j++) {
			var obj = inputs2[i];
			
			if(obj.id == aryControl1[j][0]) {
				if(obj.getAttribute("type") == "checkbox") {
					if(aryControl1[j][1] == "checked") {
						obj.checked = true;
					} else {
						obj.checked = false;
					}
				} else if(obj.getAttribute("type") == "radio") {
					if(aryControl1[j][1] == "checked") {
						obj.checked = true;
					} else {
						obj.checked = false;
					}
				} else {
					if(j == 0) { //order不需要交換內容
						obj.value = aryControl2[j][1];
					} else {
						obj.value = aryControl1[j][1];
					}
				}
			}
		}
	}
	
	for(var i = 0; i < selects2.length; i++) {
		for(var j = 0; j < aryControl1.length; j++) {
			var obj = selects2[i];
			
			if(obj.id == aryControl1[j][0]) {
				obj.value = aryControl1[j][1];
			}
		}
	}
	
	var tempTitle = tr1.title;
	tr1.title = tr2.title;
	tr2.title = tempTitle;
	
	//交換的兩列如果有可編輯的cell,也要順便改moveToOtherEditableLabel和selTDForCopyForExcel的參數
	var tds1 = $("td", tr1);
	var tds2 = $("td", tr2);
	for(var i = 1; i < tds1.length; i++) {
		var td1 = tds1[i];
		var td2 = tds2[i];
		
		var text1 = $("input", td1).get(0);
		var text2 = $("input", td2).get(0);
		
		if(text1 != null && text1.getAttribute("onKeyDown") != null) {
			text1.setAttribute("onKeyDown", "moveToOtherEditableLabel('" + tableId + "', " + index1 + ", " + i + ", event);");
			
			if(window.navigator.appName.indexOf("Microsoft") != -1) {
				text1.onkeydown = function(){
					//可能是掛上function的時間點問題
					//直接用i的話,會變成每一個都變成最後一號,然後就會有問題
					//所以直接用當時的td的id去取得columnIndex
					moveToOtherEditableLabel(tableId, index1, parseInt(this.parentNode.id.split("_")[1], 10), e);
				    return true;
				};
			}
		}
		if(text2 != null && text2.getAttribute("onKeyDown") != null) {
			text2.setAttribute("onKeyDown", "moveToOtherEditableLabel('" + tableId + "', " + index2 + ", " + i + ", event);");
			
			if(window.navigator.appName.indexOf("Microsoft") != -1) {
				text2.onkeydown = function(){
					//可能是掛上function的時間點問題
					//直接用i的話,會變成每一個都變成最後一號,然後就會有問題
					//所以直接用當時的td的id去取得columnIndex
					moveToOtherEditableLabel(tableId, index2, parseInt(this.parentNode.id.split("_")[1], 10), e);
				    return true;
				};
			}
		}
		
		if(td1.getAttribute("onClick") != null) {
			//IE不允許用setAttribute方式設定onClick屬性,所以必須用onclick掛上function的方式掛上
			if(window.navigator.appName.indexOf("Microsoft") != -1) {
				td1.onclick = function() {
					//可能是掛上function的時間點問題
					//直接用i的話,會變成每一個都變成最後一號,然後就會有問題
					//所以直接用當時的td的id去取得columnIndex
					selTDForCopyForExcel(tableId, this, index1, parseInt(this.id.split("_")[1], 10), headerRowCount, e, title, message, ok, cancel);
					return true;
				};
			} else {
				td1.setAttribute("onClick", td1.getAttribute("onClick").replace("selTDForCopyForExcel('" + tableId + "', this, " + index2, "selTDForCopyForExcel('" + tableId + "', this, " + index1));
			}
		}
		
		if(td2.getAttribute("onClick") != null) {
			//IE不允許用setAttribute方式設定onClick屬性,所以必須用onclick掛上function的方式掛上
			if(window.navigator.appName.indexOf("Microsoft") != -1) {
				td2.onclick = function() {
					//可能是掛上function的時間點問題
					//直接用i的話,會變成每一個都變成最後一號,然後就會有問題
					//所以直接用當時的td的id去取得columnIndex
					selTDForCopyForExcel(tableId, this, index2, parseInt(this.id.split("_")[1], 10), headerRowCount, e, title, message, ok, cancel);
					return true;
				};
			} else {
				td2.setAttribute("onClick", td2.getAttribute("onClick").replace("selTDForCopyForExcel('" + tableId + "', this, " + index1, "selTDForCopyForExcel('" + tableId + "', this, " + index2));
			}
		}
	}
}

/**
 * 將新增row塞到grid上(focus row的下方)
 * @author csiebug
 * @version 2010/11/11
 */
function addRowForExcel(formId, tableId, headerRowCount, newTR, copyIndex, beforeFlag, warning, message, ok, message2, cancel, e) {
	var table = $("#" + tableId + " tbody");
	var trs = $("tr", table);
	
	//若有選擇一個row為目標
	if(trs.length > headerRowCount && isSelectOneRow(tableId)) {
		var serialNumber = 1;
		var renewFlag = false;
		var nextIndex = 0;
		
		for(var i = headerRowCount; i < trs.length; i++) {
			var tr = trs[i];
			
			if(tr.className == "TR_FOCUS" && !renewFlag) {
				var newTDs = $("td", newTR);
				
				if(beforeFlag) { //則移動此row到focus row上方
					nextIndex = i;
				} else { //則移動此row到focus row下方
					nextIndex = i + 1;
				}
				
				for(var j = 0; j < newTDs.length; j++) {
					var newTD = newTDs[j];
					if(nextIndex < trs.length) {
						newTD.id = $("td:nth-child(" + (j + 1) + ")", trs[nextIndex]).get(0).id;
					} else {
						var lastId = $("td:nth-child(" + (j + 1) + ")", trs[trs.length - 1]).get(0).id;
						newTD.id = (parseInt(lastId.split("_")[0], 10) + 1) + "_" + lastId.split("_")[1];
					}
					
					if(j == 0) {
						var excelRowControlTD = newTD;
						excelRowControlTD.style.cursor = "pointer";
						
						var serialNumberP = $("p", excelRowControlTD)[0];
						if(nextIndex >= trs.length) {
							var preSerialNumber = $("p", $("td", trs[trs.length - 1])[0])[0].innerHTML;
							serialNumberP.innerHTML = parseInt(preSerialNumber, 10) + 1;
							
							if(excelRowControlTD.getAttribute("onClick") != null) {
								if(window.navigator.appName.indexOf("Microsoft") != -1) {
									excelRowControlTD.onclick = function(){
										//可能是掛上function的時間點問題
										//直接用serialNumber的話,會變成每一個都變成最後一號,然後就會有問題
										//所以直接用當時的td去取innerHTML
										selOneDataForClickForExcel(tableId, parseInt($("p", this)[0].innerHTML, 10), 0, headerRowCount, event);
									    return true;
									};
								} else {
									excelRowControlTD.setAttribute("onClick", "selOneDataForClickForExcel('" + tableId + "', " + (parseInt(preSerialNumber, 10) + 1) + ", 0, " + headerRowCount + ", event);");
								}
							}
						} else {
							var nextSerialNumber = $("p", $("td", trs[nextIndex])[0])[0].innerHTML;
							serialNumberP.innerHTML = nextSerialNumber;
							
							if(excelRowControlTD.getAttribute("onClick") != null) {
								if(window.navigator.appName.indexOf("Microsoft") != -1) {
									excelRowControlTD.onclick = function(){
										//可能是掛上function的時間點問題
										//直接用serialNumber的話,會變成每一個都變成最後一號,然後就會有問題
										//所以直接用當時的td去取innerHTML
										selOneDataForClickForExcel(tableId, parseInt($("p", this)[0].innerHTML, 10), 0, headerRowCount, event);
									    return true;
									};
								} else {
									excelRowControlTD.setAttribute("onClick", "selOneDataForClickForExcel('" + tableId + "', " + nextSerialNumber + ", 0, " + headerRowCount + ", event);");
								}
							}
						}
					}
					
					if(j == 1) {
						var hidden = $("input", newTD)[0];
						hidden.id = tableId + "_" + newTD.id;
						hidden.name = hidden.id;
						
						if(beforeFlag) {
							hidden.value = $("input", $("td", tr)[j])[0].value;
						} else {
							if((i + 1) < trs.length) {
								hidden.value = $("input", $("td", trs[i + 1])[j])[0].value;
							} else {
								hidden.value = parseInt($("input", $("td", tr)[j])[0].value, 10) + 1;
							}
						}
					}
					
					if(j == 2) {
						var maxTaskId = $("#" + tableId + "_maxTaskId");
						var hidden = $("input", newTD)[0];
						hidden.id = tableId + "_" + newTD.id;
						hidden.name = hidden.id;
						hidden.value = parseInt(maxTaskId.val(), 10) + 1;
						maxTaskId.val(hidden.value);
					}
					
					if(j >= 3) {
						if(newTD.getAttribute("onClick") != null) {
							//IE不允許用setAttribute方式設定onClick屬性,所以必須用onclick掛上function的方式掛上
							if(window.navigator.appName.indexOf("Microsoft") != -1) {
								newTD.onclick = function() {
									//可能是掛上function的時間點問題
									//直接用i的話,會變成每一個都變成最後一號,然後就會有問題
									//所以直接用當時的td的id去取得columnIndex
									selTDForCopyForExcel(tableId, this, nextIndex, parseInt(this.id.split("_")[1], 10), headerRowCount, e, warning, message2, ok, cancel);
									return true;
								};
							} else {
								newTD.setAttribute("onClick", newTD.getAttribute("onClick").replace("selTDForCopyForExcel('" + tableId + "', this, " + copyIndex, "selTDForCopyForExcel('" + tableId + "', this, " + nextIndex));
							}
						}
						
						var div = $("div", newTD)[0];
						var text = $("input", newTD)[0];
						var calendar = $("div", newTD)[1];
						if(div != null && text != null) {
							div.id = tableId + "_" + newTD.id + "_textLabel";
							text.id = tableId + "_" + newTD.id;
							text.name = text.id;
							
							if(calendar != null && calendar.id.endsWith("_calendar")) {
								calendar.id = text.id + "_calendar";
							}
							
							//IE不允許用setAttribute方式設定onDblClick屬性,所以必須用ondblclick掛上function的方式掛上
							if(window.navigator.appName.indexOf("Microsoft") != -1) {
								div.ondblclick = function() {
									showInputText(this.id.replace("_textLabel", ""), this);
									return true;
								};
								div.onkeydown = function() {
									showInputTextForKeyDown(this.id.replace("_textLabel", ""), this, event);
									return true;
								};
							} else {
								div.setAttribute("onDblClick", "showInputText('" + text.id + "', this);");
								div.setAttribute("onKeyDown", "showInputTextForKeyDown('" + text.id + "', this, event);");
							}
							
							if(text != null && text.getAttribute("onKeyDown") != null) {
								if(window.navigator.appName.indexOf("Microsoft") != -1) {
									text.onkeydown = function(){
										//可能是掛上function的時間點問題
										//直接用i,j的話,會變成每一個都變成最後一號,然後就會有問題
										//所以直接用當時的td的id去取得rowIndex與columnIndex
										moveToOtherEditableLabel(tableId, parseInt(this.parentNode.id.split("_")[0], 10), parseInt(this.parentNode.id.split("_")[1], 10), e);
									    return true;
									};
								} else {
									text.setAttribute("onKeyDown", text.getAttribute("onKeyDown").replace("moveToOtherEditableLabel('" + tableId + "', " + copyIndex + ", ", "moveToOtherEditableLabel('" + tableId + "', " + nextIndex + ", "));
								}
							}
						}
					}
				}
				
				newTR.className = "TR_ODD";
				
				//正式插入新節點
				if(beforeFlag) { //則移動此row到focus row上方
					$(newTR).insertBefore(tr);
					i--; //跳回處理focus row
				} else { //則移動此row到focus row下方
					$(newTR).insertAfter(tr);
					serialNumber++;
				}
				
				sendAJAXRequestForExcel(formId, tableId, newTR);
				
				renewFlag = true;
			} else if(renewFlag) {
				if(i >= nextIndex) {
					var tds = $("td", tr);
					
					for(var j = 0; j < tds.length; j++) {
						var td = tds[j];
						td.id = (parseInt(td.id.split("_")[0], 10) + 1) + "_" + td.id.split("_")[1];
						
						//更新excel control row
						if(j == 0) {
							var excelControl = td;
							//IE不允許用setAttribute方式設定onClick屬性,所以必須用onclick掛上function的方式掛上
							if(window.navigator.appName.indexOf("Microsoft") != -1) {
								excelControl.onclick = function(){
									//可能是掛上function的時間點問題
									//直接用serialNumber的話,會變成每一個都變成最後一號,然後就會有問題
									//所以直接用當時的td去取innerHTML
									selOneDataForClickForExcel(tableId, parseInt(this.getElementsByTagName("p")[0].innerHTML, 10), 0, headerRowCount, event);
								    return true;
								};
							} else {
								excelControl.setAttribute("onClick", "selOneDataForClickForExcel('" + tableId + "', " + serialNumber + ", 0, " + headerRowCount + ", event);");
							}
							
							var p = $("p", excelControl)[0];
							p.innerHTML = serialNumber;
						}
						
						if(j == 1) {
							var hidden = $("input", td)[0];
							hidden.id = tableId + "_" + td.id;
							hidden.name = hidden.id;
							
							hidden.value = parseInt(hidden.value, 10) + 1;
						}
						
						if(j == 2) {
							var hidden = $("input", td)[0];
							hidden.id = tableId + "_" + td.id;
							hidden.name = hidden.id;
						}
						
						if(j >= 3) {
							if(td.getAttribute("onClick") != null) {
								//IE不允許用setAttribute方式設定onClick屬性,所以必須用onclick掛上function的方式掛上
								if(window.navigator.appName.indexOf("Microsoft") != -1) {
									td.onclick = function() {
										//可能是掛上function的時間點問題
										//直接用i,j的話,會變成每一個都變成最後一號,然後就會有問題
										//所以直接用當時的td的id去取得rowIndex與columnIndex
										selTDForCopyForExcel(tableId, this, i, parseInt(this.id.split("_")[1], 10), headerRowCount, e, warning, message2, ok, cancel);
										return true;
									};
								} else {
									td.setAttribute("onClick", td.getAttribute("onClick").replace("selTDForCopyForExcel('" + tableId + "', this, " + i, "selTDForCopyForExcel('" + tableId + "', this, " + (i + 1)));
								}
							}
							
							var div = $("div", td)[0];
							var text = $("input", td)[0];
							var calendar = $("div", td)[1];
							if(div != null && text != null) {
								div.id = tableId + "_" + td.id + "_textLabel";
								text.id = tableId + "_" + td.id;
								text.name = text.id;
								text.value = div.innerHTML;
								
								if(calendar != null && calendar.id.endsWith("_calendar")) {
									calendar.id = text.id + "_calendar";
								}
								
								//IE不允許用setAttribute方式設定onDblClick屬性,所以必須用ondblclick掛上function的方式掛上
								if(window.navigator.appName.indexOf("Microsoft") != -1) {
									div.ondblclick = function() {
										showInputText(this.id.replace("_textLabel", ""), this);
										return true;
									};
									div.onkeydown = function() {
										showInputTextForKeyDown(this.id.replace("_textLabel", ""), this, event);
										return true;
									};
								} else {
									div.setAttribute("onDblClick", "showInputText('" + text.id + "', this);");
									div.setAttribute("onKeyDown", "showInputTextForKeyDown('" + text.id + "', this, event);");
								}
							}
							
							if(text != null && text.getAttribute("onKeyDown") != null) {
								if(window.navigator.appName.indexOf("Microsoft") != -1) {
									text.onkeydown = function(){
										//可能是掛上function的時間點問題
										//直接用i,j的話,會變成每一個都變成最後一號,然後就會有問題
										//所以直接用當時的td的id去取得rowIndex與columnIndex
										moveToOtherEditableLabel(tableId, parseInt(this.parentNode.id.split("_")[0], 10), parseInt(this.parentNode.id.split("_")[1], 10), e);
									    return true;
									};
								} else {
									text.setAttribute("onKeyDown", text.getAttribute("onKeyDown").replace("moveToOtherEditableLabel('" + tableId + "', " + i + ", ", "moveToOtherEditableLabel('" + tableId + "', " + (i + 1) + ", "));
								}
							}
						}
					}
					
					sendAJAXRequestForExcelOrder(formId, tableId, tr);
				}
			}
			
			serialNumber++;
		}
		
		//等別的運算累積滿了自動送出,或是由使用者手動決定全部送出
		//sendUpdateOrderPool(formId, tableId);
	} else if(trs.length == headerRowCount) {
		table.append(newTR);
	} else {
		openAlertDialog(null, warning, message, ok, '', null, 0);
	}
}

/**
 * 刪除row
 * @author csiebug
 * @version 2010/11/11
 */
function deleteRowForExcel(formId, tableId, headerRowCount, warning, message, ok, message2, cancel, e) {
	if(isSelectRows(tableId)) {
		var table = $("#" + tableId + " tbody");
		var trs = $("tr", table);
		var moveUpCounter = 0;
		
		var firstIndexToDelete = headerRowCount;
		var deleteIds = "";
		for(var i = trs.length - 1; i >= headerRowCount; i--) {
			var tr = trs[i];
			if(tr.className == "TR_FOCUS") {
				firstIndexToDelete = i;
				
				if(deleteIds != "") {
					deleteIds = deleteIds + ",";
				}
				
				deleteIds = deleteIds + $("input", $("td", tr)[2])[0].value;
				
				//刪除自己
				$(tr).remove();
			}
		}
		
		sendAJAXRequestForExcelDelete(formId, tableId, deleteIds);
		
		trs = $("tr", table);
		
		for(var i = firstIndexToDelete; i < trs.length; i++) {
			var tr = trs[i];
			var tds = $("td", tr);
			
			for(var j = 0; j < tds.length; j++) {
				var td = tds[j];
				
				td.id = i + "_" + td.id.split("_")[1];
				
				if(j == 0) {
					var excelControl = td;
					//IE不允許用setAttribute方式設定onClick屬性,所以必須用onclick掛上function的方式掛上
					if(window.navigator.appName.indexOf("Microsoft") != -1) {
						excelControl.onclick = function() {
							//可能是掛上function的時間點問題
							//直接用i的話,會變成每一個都變成最後一號,然後就會有問題
							//所以直接用當時的td的id去取得rowIndex
							selOneDataForClickForExcel(tableId, parseInt(this.id.split("_")[0], 10), 0, headerRowCount, e);
							return true;
						};
					} else {
						excelControl.setAttribute("onClick", "selOneDataForClickForExcel('" + tableId + "', " + i + ", 0, " + headerRowCount + ", event);");
					}
					
					var p = $("p", excelControl)[0];
					p.innerHTML = i + 1 - headerRowCount;
				}
				
				if(j == 1 || j == 2) {
					var hidden = $("input", td)[0];
					hidden.id = tableId + "_" + td.id;
					hidden.name = hidden.id;
				}
				
				if(j >= 3) {
					if(td.getAttribute("onClick") != null) {
						//IE不允許用setAttribute方式設定onClick屬性,所以必須用onclick掛上function的方式掛上
						if(window.navigator.appName.indexOf("Microsoft") != -1) {
							td.onclick = function() {
								//可能是掛上function的時間點問題
								//直接用i的話,會變成每一個都變成最後一號,然後就會有問題
								//所以直接用當時的td的id去取得rowIndex
								selTDForCopyForExcel(tableId, this, parseInt(this.id.split("_")[0], 10), parseInt(this.id.split("_")[1], 10), headerRowCount, e, warning, message2, ok, cancel);
								return true;
							};
						} else {
							td.setAttribute("onClick", "selTDForCopyForExcel('" + tableId + "', this, " + i + ", " + j + ", " + headerRowCount + ", event, '" + warning + "', '" + message2 + "', '" + ok + "', '" + cancel + "')");
						}
					}
					
					var div = $("div", td)[0];
					var text = $("input", td)[0];
					var calendar = $("div", td)[1];
					if(div != null && text != null) {
						div.id = tableId + "_" + td.id + "_textLabel";
						text.id = tableId + "_" + td.id;
						text.name = text.id;
						
						if(calendar != null && calendar.id.endsWith("_calendar")) {
							calendar.id = text.id + "_calendar";
						}
						
						//IE不允許用setAttribute方式設定onDblClick屬性,所以必須用ondblclick掛上function的方式掛上
						if(window.navigator.appName.indexOf("Microsoft") != -1) {
							div.ondblclick = function() {
								showInputText(this.id.replace("_textLabel", ""), this);
								return true;
							};
							div.onkeydown = function() {
								showInputTextForKeyDown(this.id.replace("_textLabel", ""), this, event);
								return true;
							};
						} else {
							div.setAttribute("onDblClick", "showInputText('" + text.id + "', this);");
							div.setAttribute("onKeyDown", "showInputTextForKeyDown('" + text.id + "', this, event);");
						}
					}
					if(text != null && text.getAttribute("onKeyDown") != null) {
						if(window.navigator.appName.indexOf("Microsoft") != -1) {
							text.onkeydown = function(){
								//可能是掛上function的時間點問題
								//直接用i,j的話,會變成每一個都變成最後一號,然後就會有問題
								//所以直接用當時的td的id去取得rowIndex與columnIndex
								moveToOtherEditableLabel(tableId, parseInt(this.parentNode.id.split("_")[0], 10), parseInt(this.parentNode.id.split("_")[1], 10), e);
							    return true;
							};
						} else {
							text.setAttribute("onKeyDown", "moveToOtherEditableLabel('" + tableId + "', " + i + ", " + j + ", event);");
						}
					}
				}
			}
		}
		
		//等別的運算累積滿了自動送出,或是由使用者手動決定全部送出
		//sendUpdateOrderPool(formId, tableId);
	} else {
		openAlertDialog(null, warning, message, ok, '', null, 0);
	}
}

/**
 * grid多選(使用onClick,或onDblClick),改變style
 * @param tableId
 * @param rowIndex
 * @param pagination
 * @param headerRowCount
 * @return
 * @author csiebug
 * @version 2009/3/25
 */
function selOneDataForClickMultiForExcel(tableId, rowIndex, pagination, headerRowCount) {
	var tr = $("#" + tableId + " tr:nth-child(" + (rowIndex + 1) + ")");
	
	if(tr.attr("class") == "TR_ODD") {
		tr.attr("class", "TR_FOCUS");
	} else {
		tr.attr("class", "TR_ODD");
	}
}

/**
 * grid單選(使用onClick,或onDblClick),改變style
 * @param tableId
 * @param rowIndex
 * @param pagination
 * @param headerRowCount
 * @param e
 * @return
 * @author csiebug
 * @version 2009/3/25
 */
function selOneDataForClickForExcel(tableId, rowIndex, pagination, headerRowCount, e) {
	var table = getRowStatus(tableId);
	var startFocusRow = table[1];
	var endFocusRow = table[2];
	var originalEndFocusRow = table[3];
	
	clearAllSelTD(tableId);
	selOneDataForClickMultiForExcel(tableId, rowIndex, pagination, headerRowCount);
	
	//如果使用者有合併ctrl或shift按鍵,則要當作是複選
	if(e.shiftKey) {
		if(endFocusRow != -1) {
			setRowStatus(tableId, "originalEndFocusRow", endFocusRow);
		}
		setRowStatus(tableId, "endFocusRow", rowIndex);
		endFocusRow = rowIndex;
		unSelDataFromTo(tableId, headerRowCount);
		selDataFromTo(tableId);
	} else if(e.ctrlKey) {
		setRowStatus(tableId, "startFocusRow", rowIndex);
		setRowStatus(tableId, "endFocusRow", -1);
		setRowStatus(tableId, "originalEndFocusRow", -1);
	} else {
		setRowStatus(tableId, "startFocusRow", rowIndex);
		setRowStatus(tableId, "endFocusRow", -1);
		setRowStatus(tableId, "originalEndFocusRow", -1);
		
		var trs = $("#" + tableId + " tr");
		//取消其他的選取
		for(var i = headerRowCount; i < trs.length; i++) {
			var tr2 = trs[i];
			
			if(tr2.className == "TR_FOCUS" && i != rowIndex) {
				tr2.className = "TR_ODD";
			}
		}
	}
}

/**
 * 選取column
 * @author csiebug
 * @version 2010/11/8
 */
function selColumnForExcel(tableId, headerRowCount, columnIndex, e) {
	var table = getColumnStatus(tableId);
	var startFocusColumn = table[1];
	var endFocusColumn = table[2];
	var originalEndFocusColumn = table[3];
	var focusColumn = table[4];
	
	var trs = $("#" + tableId + " tr");
	
	if(focusColumn == -1) {
		clearAllSelTDAndTRForExcel(tableId, headerRowCount);
		
		for(var i = headerRowCount; i < trs.length; i++) {
			var tr = trs[i];
			var td = $("td:nth-child(" + (columnIndex + 1) + ")", tr);
			
			td.attr("class", "TD_FOCUS");
		}
		
		setColumnStatus(tableId, "focusColumn", columnIndex);
		
		setColumnStatus(tableId, "startFocusColumn", columnIndex);
		setColumnStatus(tableId, "endFocusColumn", -1);
		setColumnStatus(tableId, "originalEndFocusColumn", -1);
	} else {
		if(focusColumn == columnIndex) {
			clearAllSelTDAndTRForExcel(tableId, headerRowCount);
			setColumnStatus(tableId, "focusColumn", -1);
			
			setColumnStatus(tableId, "startFocusColumn", -1);
			setColumnStatus(tableId, "endFocusColumn", -1);
			setColumnStatus(tableId, "originalEndFocusColumn", -1);
		} else {
			if(e.shiftKey) {
				if(endFocusColumn != -1) {
					setColumnStatus(tableId, "originalEndFocusColumn", endFocusColumn);
				}
				setColumnStatus(tableId, "endFocusColumn", columnIndex);
				unSelColumnFromTo(tableId);
				selColumnFromTo(tableId, headerRowCount);
			} else if(e.ctrlKey) {
				setColumnStatus(tableId, "startFocusColumn", columnIndex);
				setColumnStatus(tableId, "endFocusColumn", -1);
				setColumnStatus(tableId, "originalEndFocusColumn", -1);
				
				var selFlag = false;
				for(var i = headerRowCount; i < trs.length; i++) {
					var td = $("td:nth-child(" + (columnIndex + 1) + ")", trs[i]);
					
					if(td.attr("class") == "TD") {
						selFlag = true;
						break;
					}
				}
				
				for(var i = headerRowCount; i < trs.length; i++) {
					var td = $("td:nth-child(" + (columnIndex + 1) + ")", trs[i]);
					
					if(selFlag) {
						td.attr("class", "TD_FOCUS");
					} else {
						td.attr("class", "TD");
					}
				}
			} else {
				setColumnStatus(tableId, "startFocusColumn", columnIndex);
				setColumnStatus(tableId, "endFocusColumn", -1);
				setColumnStatus(tableId, "originalEndFocusColumn", -1);
				
				clearAllSelTDAndTRForExcel(tableId, headerRowCount);
				
				for(var i = headerRowCount; i < trs.length; i++) {
					var td = $("td:nth-child(" + (columnIndex + 1) + ")", trs[i]);
					
					td.attr("class", "TD_FOCUS");
				}
			}
			
			setColumnStatus(tableId, "focusColumn", columnIndex);
		}
	}
}

/**
 * 清除所有選取的TD和TR
 * @author csiebug
 * @version 2010/11/9
 */
function clearAllSelTDAndTRForExcel(tableId, headerRowCount) {
	$("td[class=TD_FOCUS]", $("#" + tableId)).attr("class", "TD");
	
	focusColumn = -1;
	
	$("tr:gt(" + (headerRowCount - 1) + ")", $("#" + tableId)).attr("class", "TR_ODD");
}

/**
 * 選取grid的cell
 * @author csiebug
 * @version 2010/11/5
 */
function selTDForCopyForExcel(tableId, td, rowIndex, columnIndex, headerRowCount, e, title, message, ok, cancel) {
	var table = getRowStatus(tableId);
	var startFocusRow = table[1];
	var endFocusRow = table[2];
	var originalEndFocusRow = table[3];
	
	//如果使用者有合併ctrl或shift按鍵,則要當作是複選
	if(e.shiftKey) {
		if(endFocusRow != -1) {
			setRowStatus(tableId, "originalEndFocusRow", endFocusRow);
		}
		setRowStatus(tableId, "endFocusRow", rowIndex);
		
		selColumnDataFromToForCopy(tableId, rowIndex, columnIndex, title, message, ok, cancel);
	} else if(e.ctrlKey) {
		setRowStatus(tableId, "startFocusRow", rowIndex);
		setRowStatus(tableId, "endFocusRow", -1);
		setRowStatus(tableId, "originalEndFocusRow", -1);
		
		if(td.className == "TD") {
			td.className = "TD_FOCUS";
		} else {
			td.className = "TD";
		}
	} else {
		setRowStatus(tableId, "startFocusRow", rowIndex);
		setRowStatus(tableId, "endFocusRow", -1);
		setRowStatus(tableId, "originalEndFocusRow", -1);
		
		var originalClassName = td.className;
		
		clearAllSelTDAndTRForExcel(tableId, headerRowCount);
		
		if(originalClassName == "TD") {
			td.className = "TD_FOCUS";
		} else {
			td.className = "TD";
		}
	}
	
	setColumnStatus(tableId, "startFocusColumn", columnIndex);
	
	if(getColumnStatus(tableId)[1] == -1) {
		setColumnStatus(tableId, "startFocusColumn", columnIndex);
	}
}

function moveToOtherEditableLabel(tableId, rowIndex, columnIndex, e) {
	var trs = $("#" + tableId + " tr");
	
	//使用者用上下鍵改變目前編輯的cell
	var key = window.event ? e.keyCode : e.which;
	
	if(key == 38 || key == 40) { //上/下
		var tdCurrent = $("td", trs[rowIndex])[columnIndex];
		var txtCurrent = $("input", tdCurrent)[0];
		tdCurrent.className = "TD";
		txtCurrent.blur();
		
		var newIndex = rowIndex;
		if(key == 38) { //上
			for(var i = rowIndex - 1; i > 0; i--) {
				if(trs[i].style.display != "none") {
					newIndex = i;
					break;
				}
			}
		} else { //下
			for(var i = rowIndex + 1; i < trs.length; i++) {
				if(trs[i].style.display != "none") {
					newIndex = i;
					break;
				}
			}
		}
		
		var tdNew = $("td", trs[newIndex])[columnIndex];
		tdNew.className = "TD_FOCUS";
		var labelNew = $("div", tdNew)[0];
		var txtNew = $("input", tdNew)[0];
		txtNew.style.display = "";
		txtNew.focus();
		labelNew.style.display = "none";
	} else if(e.ctrlKey && (key == 37 || key == 39)) { //因為左右用來移動游標編輯,所以不能用左右來跳不同的text編輯,所以加上ctrl作複合鍵
		var tds = $("td", trs[rowIndex]);
		var tdCurrent = tds[columnIndex];
		var txtCurrent = $("input", tdCurrent)[0];
		tdCurrent.className = "TD";
		txtCurrent.blur();
		
		var newIndex = columnIndex;
		if(key == 37) { //左
			for(var i = columnIndex - 1; i > 0; i--) {
				var td = tds[i];
				
				//要先判斷那個column是否被藏起來了,沒有才可能移動過去
				var divHide = $("div", td)[0];
				if(divHide != null) {
					var divChild = $("div", divHide)[0];
					var textChild = $("input", divHide)[0];
					if(divHide.style.display != "none" || divChild == null || textChild == null) {
						var div = $("div", td)[0];
						var text = $("input", td)[0];
						
						if(td.style.display != "none" && div != null && text != null) {
							newIndex = i;
							break;
						}
					}
				}
			}
		} else if(key == 39) { //右
			for(var i = columnIndex + 1; i < tds.length; i++) {
				var td = tds[i];
				
				//要先判斷那個column是否被藏起來了,沒有才可能移動過去
				var divHide = $("div", td)[0];
				if(divHide != null) {
					var divChild = $("div", divHide)[0];
					var textChild = $("input", divHide)[0];
					if(divHide.style.display != "none" || divChild == null || textChild == null) {
						var div = $("div", td)[0];
						var text = $("input", td)[0];
						
						if(td.style.display != "none" && div != null && text != null) {
							newIndex = i;
							break;
						}
					}
				}
			}
		}
		
		var tdNew = tds[newIndex];
		tdNew.className = "TD_FOCUS";
		var labelNew = $("div", tdNew)[0];
		var txtNew = $("input", tdNew)[0];
		txtNew.style.display = "";
		txtNew.focus();
		labelNew.style.display = "none";
	}
}

/**
 *  將cell的虛線去掉
 * @author csiebug
 */
function cancelPreCopy(tableId, headerRowCount) {
	var trs = $("#" + tableId + " tr");
	
	for(var i = headerRowCount; i < trs.length; i++) {
		var tds = $("td", trs[i]);
		
		for(var j = 1; j < tds.length; j++) {
			var td = tds[j];
			
			if(td.getAttribute("style") != null && ("" + td.getAttribute("style")).startsWith("border-")) {
				if(j == 1) {
					td.setAttribute("style", "cursor:pointer");
				} else {
					td.setAttribute("style", "");
				}
			}
		}
	}
}

/**
 * 複製選取cell的值或複製選取的row,準備之後貼上用
 * @author csiebug
 * @version 2010/11/25
 */
function copyForExcel(tableId, headerRowCount) {
	var copyField = $("#" + tableId + "_copyField");
	var copyRow = $("#" + tableId + "_copyRow");
	var pasteButton = $("#" + tableId + "_pasteButton");
	var pasteBeforeButton = $("#" + tableId + "_pasteBeforeButton");
	var pasteAfterButton = $("#" + tableId + "_pasteAfterButton");
	var trs = $("#" + tableId + " tr");
	
	var selRows = "";
	
	if(isSelectRows(tableId)) {
		cancelPreCopy(tableId, headerRowCount);
	}
	
	for(var i = headerRowCount; i < trs.length; i++) {
		var tr = trs[i];
		
		if(tr.className == "TR_FOCUS") {
			if(selRows != "") {
				selRows = selRows + ",";
			}
			selRows = selRows + i;
			
			//加上虛線表示被複製
			var tds = $("td", tr);
			var findLastFlag = false;
			var basicStyle = "border-top-style:dashed;border-top-width:thin;border-bottom-style:dashed;border-bottom-width:thin;";
			for(var j = tds.length - 1; j > 0; j--) {
				var td = tds[j];
				
				if(!findLastFlag && td.style.display != "none") {
					td.setAttribute("style", basicStyle + "border-right-style:dashed;border-right-width:thin;");
					
					findLastFlag = true;
				} else if(j == 3) {
					td.setAttribute("style", basicStyle + "border-left-style:dashed;border-left-width:thin;cursor:pointer");
				} else if(td.style.display != "none") {
					td.setAttribute("style", basicStyle);
				}
			}
		}
	}
	copyRow.val(selRows);
	
	if(copyRow.val() != "") { //複製row
		pasteBeforeButton.attr("style", "display:");
		pasteAfterButton.attr("style", "display:");
		
		copyField.val("");
		pasteButton.attr("style", "display:none");
	} else { //複製cell text
		var startFocusRow = getRowStatus(tableId)[1];
		var startFocusColumn = getColumnStatus(tableId)[1];
		
		var td = $("td", trs[startFocusRow])[startFocusColumn];
		var div = $("div", td)[0];
		var text = $("input", td)[0];
		
		if(div != null && text != null) {
			copyField.val(div.innerHTML);
		} else {
			copyField.val(td.innerHTML);
		}
		
		if(copyField.val() != "") {
			//加上虛線表示被複製
			cancelPreCopy(tableId, headerRowCount);
			td.setAttribute("style", "border-style:dashed;border-width:thin;");
			
			pasteButton.attr("style", "display:");
			
			copyRow.val("");
			pasteBeforeButton.attr("style", "display:none");
			pasteAfterButton.attr("style", "display:none");
		}
	}
}

/**
 * 將複製的值貼到所選的cell裡面
 * @author csiebug
 * @version 2010/11/25
 */
function pasteFieldForExcel(tableId, headerRowCount) {
	var copyField = $("#" + tableId + "_copyField");
	
	if(copyField.val() != "") {
		var trs = $("#" + tableId + " tr");
		
		for(var i = headerRowCount; i < trs.length; i++) {
			var tr = trs[i];
			var tds = $("td", tr);
			
			for(var j = 3; j < tds.length; j++) {
				var td = tds[j];
				
				if(td.className == "TD_FOCUS") {
					var div = $("div", td)[0];
					var text = $("input", td)[0];
					
					if(div != null && text != null) {
						div.innerHTML = copyField.val();
						text.value = copyField.val();
					}
				}
			}
		}
	}
}

/**
 * 將複製的row貼到所選的row子節點或是同層節點
 * @author csiebug
 * @version 2010/11/25
 */
function pasteRowsForExcel(formId, tableId, headerRowCount, beforeFlag, warning, message, ok, message2, cancel, e) {
	var copyRow = $("#" + tableId + "_copyRow");
	var pasteBeforeButton = $("#" + tableId + "_pasteBeforeButton");
	var pasteAfterButton = $("#" + tableId + "_pasteAfterButton");
	
	if(copyRow.val() != "") {
		var rowsIndex = copyRow.val().split(",");
		
		for(var i = rowsIndex.length - 1; i >= 0; i--) {
			var index = rowsIndex[i];
			pasteRowForExcel(formId, tableId, headerRowCount, beforeFlag, warning, message, ok, message2, cancel, e, index);
		}
	}
	
	copyRow.val("");
	pasteBeforeButton.attr("style", "display:none");
	pasteAfterButton.attr("style", "display:none");
}

function pasteRowForExcel(formId, tableId, headerRowCount, beforeFlag, warning, message, ok, message2, cancel, e, index) {
	var newTR = document.createElement("tr");
	var trs = $("#" + tableId + " tbody tr");
	
	var copyTR = trs[index];
	var tds = $("td", copyTR);
	
	newTR.title = copyTR.title;
	
	for(var i = 0; i < tds.length; i++) {
		var newTD = document.createElement("td");
		newTR.appendChild(newTD);
		newTD.innerHTML = tds[i].innerHTML;
		newTD.className = tds[i].className;
		
		//IE不允許用setAttribute方式設定onClick屬性,所以必須用onclick掛上function的方式掛上
		if(window.navigator.appName.indexOf("Microsoft") != -1) {
			newTD.onclick = tds[i].getAttribute("onClick");
		} else {
			newTD.setAttribute("onClick", tds[i].getAttribute("onClick"));
		}
		
		if(tds[i].style.display == "none") {
			newTD.style.display = "none";
		}
		
		//將原本標虛線的row,虛線拿掉
		var basicStyle = "border-top-style";
		if(("" + tds[i].getAttribute("style")).startsWith(basicStyle)) {
			if(i == 1) {
				tds[i].setAttribute("style", "cursor:pointer");
			} else {
				tds[i].setAttribute("style", "");
			}
		}
	}
	
	//這裡才是將新增列塞進grid的動作
	addRowForExcel(formId, tableId, headerRowCount, newTR, index, beforeFlag, warning, message, ok, message2, cancel, e);
}

/**
 * 把不想看的column藏起來/把藏起來的column顯現出來
 * @author csiebug
 * @version 2010/11/29
 */
function hideOrShowColumnForExcel(tableId, headerRowCount, columnIndex, hideFlag) {
	var trs = $("#" + tableId + " tr");
	
	for(var i = 0; i < headerRowCount; i++) {
		var tr = trs[i];
		var td = $("td", tr)[columnIndex];
		
		if(hideFlag) {
			//IE不允許用setAttribute方式設定onClick屬性,所以必須用onclick掛上function的方式掛上
			if(window.navigator.appName.indexOf("Microsoft") != -1) {
				td.onclick = function() {
					hideOrShowColumnForExcel(tableId, headerRowCount, columnIndex, false);
					return true;
				};
			} else {
				td.setAttribute("onClick", "hideOrShowColumnForExcel('" + tableId + "', " + headerRowCount + ", " + columnIndex + ", false)");
			}
			
			var hideDiv = document.createElement("div");
			hideDiv.innerHTML = td.innerHTML;
			hideDiv.style.display = "none";
			td.innerHTML = "&nbsp;";
			td.appendChild(hideDiv);
		} else {
			//IE不允許用setAttribute方式設定onClick屬性,所以必須用onclick掛上function的方式掛上
			if(window.navigator.appName.indexOf("Microsoft") != -1) {
				td.onclick = function() {
					hideOrShowColumnForExcel(tableId, headerRowCount, columnIndex, true);
					return true;
				};
			} else {
				td.setAttribute("onClick", "hideOrShowColumnForExcel('" + tableId + "', " + headerRowCount + ", " + columnIndex + ", true)");
			}
			
			td.innerHTML = $("div", td)[0].innerHTML;
		}
	}
	
	for(var i = headerRowCount; i < trs.length; i++) {
		var td = $("td", trs[i])[columnIndex];
		
		if(hideFlag) {
			var hideDiv = document.createElement("div");
			hideDiv.innerHTML = td.innerHTML;
			hideDiv.style.display = "none";
			td.innerHTML = "";
			td.appendChild(hideDiv);
		} else {
			td.innerHTML = $("div", td)[0].innerHTML;
		}
	}
}

/**
 * 用ajax的方式刪除資料
 * @author csiebug
 * @version 2010/12/6
 */
function sendAJAXRequestForExcelDelete(formId, tableId, deleteIds) {
	var form = $("#" + formId);
	
	if(form != null) {
		var url = form.attr("action") + "?ActFlag=deleteProjectRecord&projectId=" + $("#" + tableId + "_projectId").val() + "&uniqueIDs=" + deleteIds;
		
		$("#" + tableId + "_updateStatus").val(sendAJAXRequest(url));
	}
}

//改善ajax次數的公用變數
var defaultPoolSize = 20;
var updateOrderPoolCounter = 0;
var updateOrders = "";
var updateFieldPoolCounter = 0;
var updateFields = "";

/**
 * 用ajax的方式處理order更新
 * 但因為此method常包在迴圈內執行,故用pool的方式批次執行,減少ajax request的次數
 * @author csiebug
 * @version 2010/12/3
 */
function sendAJAXRequestForExcelOrder(formId, tableId, tr) {
	var form = $("#" + formId);
	if(form != null) {
		var uniqueID = $("input", $("td", tr)[3])[0].value;
		var outlineNumber = replaceAll(tr.id, "_", ".");
		updateOrderPoolCounter = updateOrderPoolCounter + 1;
		
		if(updateOrderPoolCounter < defaultPoolSize) {
			if(updateOrders != "") {
				updateOrders = updateOrders + ";";
			}
			
			updateOrders = updateOrders + uniqueID + "," + outlineNumber;
		} else {
			sendUpdateOrderPool(formId, tableId);
		}
	}
}

/**
 * 把pool內資料倒出去送出ajax request
 * pool累積滿了會自動被呼叫,但若沒有滿,還是要有時間點要呼叫此function送出
 * @author csiebug
 * @version 2010/12/3
 */
function sendUpdateOrderPool(formId, tableId) {
	var form = $("#" + formId);
	if(form != null && updateOrders != "") {
		updateOrderPoolCounter = 0;
		
		var url = form.attr("action") + "?ActFlag=updateOrder&projectId=" + $("#" + tableId + "_projectId").val() + "&orders=" + updateOrders;
		
		$("#" + tableId + "_updateStatus").val(sendAJAXRequest(url));
		
		updateOrders = "";
	}
}

/**
 * 用ajax的方式處理cell更新
 * 怕使用者操作cell太頻繁,所以也是利用pool來控制ajax request數量
 * @author csiebug
 * @version 2010/12/6
 */
function sendAJAXRequestForExcelField(formId, tableId, field) {
	var form = $("#" + formId);
	if(form != null) {
		var tr = field.parentNode.parentNode;
		var uniqueID = $("input", $("td", tr)[3])[0].value;
		var outlineNumber = replaceAll(tr.id, "_", ".");
		updateFieldPoolCounter = updateFieldPoolCounter + 1;
		
		if(updateFieldPoolCounter < defaultPoolSize) {
			if(updateFields != "") {
				updateFields = updateFields + ";";
			}
			
			if(field.value.indexOf("(") != -1) {
				updateFields = updateFields + uniqueID + "," + field.id + "," + replaceAll(replaceAll(field.value, "(", "var.openParenthesis"), ")", "var.closeParenthesis");
			} else {
				updateFields = updateFields + uniqueID + "," + field.id + "," + field.value;
			}
		} else {
			sendUpdateFieldPool(formId, tableId);
		}
	}
}

/**
 * 把pool內資料倒出去送出ajax request
 * pool累積滿了會自動被呼叫,但若沒有滿,還是要有時間點要呼叫此function送出
 * @author csiebug
 * @version 2010/12/3
 */
function sendUpdateFieldPool(formId, tableId) {
	var form = $("#" + formId);
	if(form != null) {
		updateFieldPoolCounter = 0;
		
		var url = form.attr("action") + "?ActFlag=saveExcelField&projectId=" + $("#" + tableId + "_projectId").val() + "&fields=" + updateFields;
		
		$("#" + tableId + "_updateStatus").val(sendAJAXRequest(url));
		
		updateFields = "";
	}
}

/**
 * 用ajax的方式一次處理一個row的資料
 * @author csiebug
 * @version 2010/12/3
 */
function sendAJAXRequestForExcel(formId, tableId, tr) {
	var form = $("#" + formId);
	if(form != null) {
		var inputs = $("input", tr);
		var selects = $("select", tr);
		
		var url = form.attr("action") + "?ActFlag=saveExcelRecord&projectId=" + $("#" + tableId + "_projectId").val();
		for(var i = 0; i < inputs.length; i++) {
			var obj = inputs[i];
			
			if(obj.getAttribute("type") == "checkbox") {
				url = url + "&" + obj.id + "=";
				if(obj.checked == true) {
					url = url + "checked";
				} else {
					url = url + "unchecked";
				}
			} else if(obj.getAttribute("type") == "radio") {
				url = url + "&" + obj.id + "=";
				if(obj.checked == true) {
					url = url + "checked";
				} else {
					url = url + "unchecked";
				}
			} else {
				url = url + "&" + obj.id + "=";
				if(obj.value.indexOf("(") != -1) {
					url = url + replaceAll(replaceAll(obj.value, "(", "var.openParenthesis"), ")", "var.closeParenthesis");
				} else {
					url = url + obj.value;
				}
			}
		}
		
		for(var i = 0; i < selects.length; i++) {
			var obj = selects[i];
			
			url = url + "&" + obj.id + "=";
			url = url + obj.value;
		}
		
		$("#" + tableId + "_updateStatus").val(sendAJAXRequest(url));
	}
}

/**
 * excel table也可使用快速鍵執行複製貼上刪除等動作
 * @author csiebug
 * @version 2010/12/9 
 */
function excelGridOnKeyDown(formId, tableId, headerRowCount, warning, message, ok, message2, cancel, e) {
	var key = window.event ? e.keyCode : e.which;
	
	if(e.ctrlKey && (key == 67 || key == 86 || key == 88)) {
		if(key == 67) { //ctrl-c 複製
			copyForExcel(tableId, headerRowCount);
		} else if(key == 86) { //ctrl-v 貼上
			var copyField = $("#" + tableId + "_copyField");
			var copyRow = $("#" + tableId + "_copyRow");
			if(copyRow.val() != "") { //貼上row
				pasteRowsForExcel(formId, tableId, headerRowCount, false, warning, message, ok, message2, cancel, e);
			} else if(copyField.value != "") { //貼上cell text
				pasteFieldForExcel(tableId, 1);
			}
		} else if(key == 88) { //ctrl-x 剪下
			//TODO: 暫不實作
		}
	} else if(key == 46) {
		deleteRowForExcel(formId, tableId, headerRowCount, warning, message, ok, message2, cancel, e);
	}
}

/**
 * 建立一個新的row好插入到grid
 * @param formId
 * @param tableId
 * @param headerRowCount
 * @param addSon
 * @param warning
 * @param message
 * @param ok
 * @param message2
 * @param cancel
 * @param e
 * @param blankText
 * @param messageInterval
 * @param message1Start
 * @param message1End
 * @param message2
 * @param message3
 * @param message4
 * @param message5
 * @param message6
 * @param message7
 * @param message8Start
 * @param message8End
 * @param message9Start
 * @param message9End
 * @param message10Start
 * @param message10End
 * @param message11
 * @param message12Start
 * @param message12End
 * @return
 * @author csiebug
 * @version 2010/12/20
 */
function createNewRowForExcel(formId, tableId, headerRowCount, beforeFlag, warning, message, ok, message2ForAdd, cancel, e, blankText, messageInterval, message1Start, message1End, message2, message3, message4, message5, message6, message7, message8Start, message8End, message9Start, message9End, message10Start, message10End, message11, message12Start, message12End, message13, message14) {
	var tds = $("td", $("tr:last-child", $("#" + tableId + " tbody")));
	
	var newTR = document.createElement("tr");
	newTR.id = "1";
	newTR.className = "TR_ODD";
	newTR.title = blankText;
	
	for(var i = 0; i < tds.length; i++) {
		var newTD = document.createElement("td");
		newTR.appendChild(newTD);
		
		newTD.id = headerRowCount + "_" + i;
		newTD.className = "TD";
		
		//預設欄位建立
		if(i == 0) {
			newTD.setAttribute("style", "cursor:pointer");
			newTD.className = "TD_EXCEL_CONTROL";
			newTD.innerHTML = "<p>1</p>";
			//IE不允許用setAttribute方式設定onClick屬性,所以必須用onclick掛上function的方式掛上
			if(window.navigator.appName.indexOf("Microsoft") != -1) {
				newTD.onclick = function() {
					selOneDataForClickForExcel(tableId, headerRowCount, 0, headerRowCount, event);
					return true;
				};
			} else {
				newTD.setAttribute("onClick", "selOneDataForClickForExcel('" + tableId + "', " + headerRowCount + ", 0, " + headerRowCount + ", event)");
			}
		} else if(i == 1) {
			newTD.style.display = "none";
			newTD.innerHTML = "<input type=\"hidden\" id=\"" + tableId + "_" + headerRowCount + "_" + i + "\" name=\"" + tableId + "_" + headerRowCount + "_" + i + "\" value=\"0\"></input>";
		} else if(i == 2) {
			newTD.style.display = "none";
			newTD.innerHTML = "<input type=\"hidden\" id=\"" + tableId + "_" + headerRowCount + "_" + i + "\" name=\"" + tableId + "_" + headerRowCount + "_" + i + "\" value=\"1\"></input>";
		} else {
			if(tds[i].style.display == "none") {
				newTD.style.display = "none";
			}
			
			//IE不允許用setAttribute方式設定onClick屬性,所以必須用onclick掛上function的方式掛上
			if(window.navigator.appName.indexOf("Microsoft") != -1) {
				newTD.onclick = function() {
					//可能是掛上function的時間點問題
					//直接用i的話,會變成每一個都變成最後一號,然後就會有問題
					//所以直接用當時的td的id去取得rowIndex
					selTDForCopyForExcel(tableId, this, parseInt(this.id.split("_")[0], 10), parseInt(this.id.split("_")[1], 10), headerRowCount, e, warning, message2ForAdd, ok, cancel);
					return true;
				};
			} else {
				newTD.setAttribute("onClick", "selTDForCopyForExcel('" + tableId + "', this, " + headerRowCount + ", " + i + ", " + headerRowCount + ", event, '" + warning + "', '" + message2ForAdd + "', '" + ok + "', '" + cancel + "')");
			}
			
			//TODO: 自己要撰寫產生新row的程式碼,因為每個grid的新增定義不一樣
			userDefineNewRow(newTD, i, formId, tableId, headerRowCount, blankText, warning, ok, messageInterval, message1Start, message1End, message2, message3, message4, message5, message6, message7, message8Start, message8End, message9Start, message9End, message10Start, message10End, message11, message12Start, message12End, message13, message14);
		}
	}
	
	//這裡才是將新增列塞進grid的動作
	addRowForExcel(formId, tableId, headerRowCount, newTR, headerRowCount, beforeFlag, warning, message, ok, message2ForAdd, cancel, e);
}

/**
 * 根據dataType建立一個新的cell
 * @param formId
 * @param tableId
 * @param headerRowCount
 * @param columnIndex
 * @param dataType
 * @return
 * @author csiebug
 * @version 2010/12/20
 */
function getEditableLabelHTML(formId, tableId, headerRowCount, columnIndex, dataType, blankText, warning, ok, messageInterval, message1Start, message1End, message2, message3, message4, message5, message6, message7, message8Start, message8End, message9Start, message9End, message10Start, message10End, message11, message12Start, message12End, message13, message14) {
	var divHTML = "<div id = \"" + tableId + "_" + headerRowCount + "_" + columnIndex + "_textLabel\" name = \"" + tableId + "_" + headerRowCount + "_" + columnIndex + "_textLabel\" onDblClick = \"showInputText('" + tableId + "_" + headerRowCount + "_" + columnIndex + "', this);\" tabIndex = \"0\" onKeyDown = \"showInputTextForKeyDown('" + tableId + "_" + headerRowCount + "_" + columnIndex + "', this, event);\" style = \"cursor:pointer\">" + blankText + "</div>";
	var inputHTML = "<input type = \"text\" id = \"" + tableId + "_" + headerRowCount + "_" + columnIndex + "\" name = \"" + tableId + "_" + headerRowCount + "_" + columnIndex + "\" class = \"Text\" DataType = \"" + dataType + "\" minValue = \"-1\" maxValue = \"-1\" maxInteger = \"-1\" maxDecimal = \"-1\" isRequired = \"false\" value = \"\" fixLength = \"-1\" style = \"display:none\" onChange = \"modifyLabel('" + blankText + "', this, '" + warning + "', '" + ok + "', '" + messageInterval + "', '" + message1Start + "', '" + message1End + "', '" + message2 + "', '" + message3 + "', '" + message4 + "', '" + message5 + "', '" + message6 + "', '" + message7 + "', '" + message8Start + "', '" + message8End + "', '" + message9Start + "', '" + message9End + "', '" + message10Start + "', '" + message10End + "', '" + message11 + "', '" + message12Start + "', '" + message12End + "', '" + message13 + "', '" + message14 + "');sendAJAXRequestForExcelField('" + formId + "', '" + tableId + "', this);\" onBlur = \"modifyLabel('" + blankText + "', this, '" + warning + "', '" + ok + "', '" + messageInterval + "', '" + message1Start + "', '" + message1End + "', '" + message2 + "', '" + message3 + "', '" + message4 + "', '" + message5 + "', '" + message6 + "', '" + message7 + "', '" + message8Start + "', '" + message8End + "', '" + message9Start + "', '" + message9End + "', '" + message10Start + "', '" + message10End + "', '" + message11 + "', '" + message12Start + "', '" + message12End + "', '" + message13 + "', '" + message14 + "');\" onKeyDown = \"moveToOtherEditableLabel('" + tableId + "', 1, " + columnIndex + ", event);\"></input>";
	
	if(dataType == "date") {
		var inputOnClick = "onClick = \"openCalendar(this, 23, 'modifyLabel(&quote" + blankText + "&quote, this, &quote" + warning + "&quote, &quote" + ok + "&quote, &quote" + messageInterval + "&quote, &quote" + message1Start + "&quote, &quote" + message1End + "&quote, &quote" + message2 + "&quote, &quote" + message3 + "&quote, &quote" + message4 + "&quote, &quote" + message5 + "&quote, &quote" + message6 + "&quote, &quote" + message7 + "&quote, &quote" + message8Start + "&quote, &quote" + message8End + "&quote, &quote" + message9Start + "&quote, &quote" + message9End + "&quote, &quote" + message10Start + "&quote, &quote" + message10End + "&quote, &quote" + message11 + "&quote, &quote" + message12Start + "&quote, &quote" + message12End + "&quote, &quote" + message13 + "&quote, &quote" + message14 + "&quote);sendAJAXRequestForExcelField(&quoteprojectGrid&quote, &quotempp&quote, this);', '/csiebug-2.0/images', '" + warning + "', '" + ok + "', '" + messageInterval + "', '" + message1Start + "', '" + message1End + "', '" + message2 + "', '" + message3 + "', '" + message4 + "', '" + message5 + "', '" + message6 + "', '" + message7 + "', '" + message8Start + "', '" + message8End + "', '" + message9Start + "', '" + message9End + "', '" + message10Start + "', '" + message10End + "', '" + message11 + "', '" + message12Start + "', '" + message12End + "', '" + message13 + "', '" + message14 + "');\"";
		inputHTML = inputHTML.replace("></input>", inputOnClick + "></input>");
		var inputCalendar = "<div id = \"" + tableId + "_" + headerRowCount + "_" + columnIndex + "_calendar\" class = \"calendar\" style = \"position:absolute;left:0;top:0; z-index:5;display:none;\"></div>";
		
		return divHTML + inputHTML + inputCalendar;
	} else {
		return divHTML + inputHTML;
	}
}
