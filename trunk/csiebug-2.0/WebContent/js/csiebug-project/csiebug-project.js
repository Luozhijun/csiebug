/**
 * 將focus的row往上移動
 * @author csiebug
 * @version 2010/11/11
 */
function moveUpRowForProject(formId, tableId, headerRowCount, e, title, message, ok, cancel) {
	if(isSelectOneRow(tableId)) {
		var trs = document.getElementById(tableId).getElementsByTagName("tr");
		
		for(var i = headerRowCount; i < trs.length; i++) {
			if(trs[i].className == "TR_FOCUS") {
				if(i != headerRowCount) {//如果已經是第一個row則不需要交換了
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
						
						switchRowForProject(tableId, preIndex, i, headerRowCount, e, title, message, ok, cancel);
						
						tr2.className = "TR_ODD";
						tr1.className = "TR_FOCUS";
						
						sendAJAXRequestForProjectOutlineNumber(formId, tableId, tr1);
						sendAJAXRequestForProjectOutlineNumber(formId, tableId, tr2);
						
						//等別的運算累積滿了自動送出,或是由使用者手動決定全部送出
						//sendUpdateOutlineNumberPool(formId, tableId);
					}
				}
				
				break;
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
		var trs = document.getElementById(tableId).getElementsByTagName("tr");
		
		for(var i = headerRowCount; i < trs.length; i++) {
			if(trs[i].className == "TR_FOCUS") {
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
					
					switchRowForProject(tableId, i, nextIndex, headerRowCount, e, title, message, ok, cancel);
					
					tr1.className = "TR_ODD";
					tr2.className = "TR_FOCUS";
					
					sendAJAXRequestForProjectOutlineNumber(formId, tableId, tr1);
					sendAJAXRequestForProjectOutlineNumber(formId, tableId, tr2);
					
					//等別的運算累積滿了自動送出,或是由使用者手動決定全部送出
					//sendUpdateOutlineNumberPool(formId, tableId);
				}
				
				break;
			}
		}
	}
}

/**
 * 兩列資料交換
 * @author csiebug
 * @version 2010/11/11
 */
function switchRowForProject(tableId, index1, index2, headerRowCount, e, title, message, ok, cancel) {
	var table = document.getElementById(tableId);
	var tr1 = table.getElementsByTagName("tr")[index1];
	var tr2 = table.getElementsByTagName("tr")[index2];
	var aryTD1 = getDataTD(tr1);
	var aryTD2 = getDataTD(tr2);
	
	//複製HTML碼,控制項的值不會一起過去,所以要記錄起來再設回去
	var inputs1 = tr1.getElementsByTagName("input");
	var selects1 = tr1.getElementsByTagName("select");
	var inputs2 = tr2.getElementsByTagName("input");
	var selects2 = tr2.getElementsByTagName("select");
	
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
	
	//html碼交換;excel control row和outline number不需要交換內容
	for(var i = 2; i < aryTD1.length; i++) {
		var temp = aryTD1[i].innerHTML;
		aryTD1[i].innerHTML = aryTD2[i].innerHTML;
		aryTD2[i].innerHTML = temp;
	}
	
	//控制項值交換
	var newinputs1 = tr1.getElementsByTagName("input");
	var newselects1 = tr1.getElementsByTagName("select");
	var newinputs2 = tr2.getElementsByTagName("input");
	var newselects2 = tr2.getElementsByTagName("select");
	
	for(var i = 0; i < newinputs1.length; i++) {
		for(var j = 0; j < aryControl2.length; j++) {
			var obj = newinputs1[i];
			
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
					if(j == 0) { //outline number不需要交換內容
						obj.value = aryControl1[j][1];
					} else {
						obj.value = aryControl2[j][1];
					}
				}
			}
		}
	}
	
	for(var i = 0; i < newselects1.length; i++) {
		for(var j = 0; j < aryControl2.length; j++) {
			var obj = newselects1[i];
			
			if(obj.id == aryControl2[j][0]) {
				obj.value = aryControl2[j][1];
			}
		}
	}
	
	for(var i = 0; i < newinputs2.length; i++) {
		for(var j = 0; j < aryControl1.length; j++) {
			var obj = newinputs2[i];
			
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
					if(j == 0) { //outline number不需要交換內容
						obj.value = aryControl2[j][1];
					} else {
						obj.value = aryControl1[j][1];
					}
				}
			}
		}
	}
	
	for(var i = 0; i < newselects2.length; i++) {
		for(var j = 0; j < aryControl1.length; j++) {
			var obj = newselects2[i];
			
			if(obj.id == aryControl1[j][0]) {
				obj.value = aryControl1[j][1];
			}
		}
	}
	
	var tempTitle = tr1.title;
	tr1.title = tr2.title;
	tr2.title = tempTitle;
	
	//交換的兩列如果有可編輯的cell,也要順便改moveToOtherEditableLabel和selTDForCopyForProject的參數
	var tds1 = tr1.getElementsByTagName("td");
	var tds2 = tr2.getElementsByTagName("td");
	for(var i = 2; i < tds1.length; i++) {
		var td1 = tds1[i];
		var td2 = tds2[i];
		
		var text1 = td1.getElementsByTagName("input")[0];
		var text2 = td2.getElementsByTagName("input")[0];
		
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
					selTDForCopyForProject(tableId, this, index1, parseInt(this.id.split("_")[1], 10), headerRowCount, e, title, message, ok, cancel);
					return true;
				};
			} else {
				td1.setAttribute("onClick", td1.getAttribute("onClick").replace("selTDForCopyForProject('" + tableId + "', this, " + index2, "selTDForCopyForProject('" + tableId + "', this, " + index1));
			}
		}
		
		if(td2.getAttribute("onClick") != null) {
			//IE不允許用setAttribute方式設定onClick屬性,所以必須用onclick掛上function的方式掛上
			if(window.navigator.appName.indexOf("Microsoft") != -1) {
				td2.onclick = function() {
					//可能是掛上function的時間點問題
					//直接用i的話,會變成每一個都變成最後一號,然後就會有問題
					//所以直接用當時的td的id去取得columnIndex
					selTDForCopyForProject(tableId, this, index2, parseInt(this.id.split("_")[1], 10), headerRowCount, e, title, message, ok, cancel);
					return true;
				};
			} else {
				td2.setAttribute("onClick", td2.getAttribute("onClick").replace("selTDForCopyForProject('" + tableId + "', this, " + index1, "selTDForCopyForProject('" + tableId + "', this, " + index2));
			}
		}
	}
}

/**
 * 取得某個row的所有第一層子節點的index
 * @author csiebug
 * @version 2010/11/18
 */
function getFirstLevelChildIndex(tableId, index) {
	var firstLevelChildIndex = new Array();
	
	var table = document.getElementById(tableId).getElementsByTagName("tbody")[0];
	var trs = table.getElementsByTagName("tr");
	var prefix = trs[index].getElementsByTagName("td")[1].getElementsByTagName("span")[0].innerHTML;
	
	for(var i = (index + 1); i < trs.length; i++) {
		var tr = trs[i];
		var td = tr.getElementsByTagName("td")[1];
		var outlineNumber = td.getElementsByTagName("span")[0].innerHTML;
		
		if(outlineNumber.startsWith(prefix) && (prefix == "" || outlineNumber.startsWith(prefix + "."))) {
			var postfix = outlineNumber.replace(prefix + ".", "");
			
			if(postfix.indexOf(".") == -1) {
				firstLevelChildIndex[firstLevelChildIndex.length] = i;
			}
		}
	}
	
	return firstLevelChildIndex;
}

/**
 * 取得此節點的所有子節點最後的index
 * @author csiebug
 * @version2010/11/18
 */
function getLastIndexForFolder(tableId, index) {
	var lastIndex = index;
	
	var table = document.getElementById(tableId).getElementsByTagName("tbody")[0];
	var trs = table.getElementsByTagName("tr");
	var prefix = trs[index].getElementsByTagName("td")[1].getElementsByTagName("span")[0].innerHTML;
	
	for(var i = (index + 1); i < trs.length; i++) {
		var tr = trs[i];
		var td = tr.getElementsByTagName("td")[1];
		
		var outlineNumber = td.getElementsByTagName("span")[0].innerHTML;
		
		if(outlineNumber.startsWith(prefix) && (outlineNumber == prefix || outlineNumber.startsWith(prefix + "."))) {
			lastIndex = i;
		}
	}
	
	return lastIndex;
}

/**
 * 取得那個row的outlineNumber
 * @author csiebug
 * @version 2010/11/11
 */
function getOutlineNumber(tableId, index) {
	var table = document.getElementById(tableId).getElementsByTagName("tbody")[0];
	var trs = table.getElementsByTagName("tr");
	
	return trs[index].getElementsByTagName("td")[1].getElementsByTagName("span")[0].innerHTML;
}

/**
 * 將新增row塞到grid上(focus row的下方)
 * @author csiebug
 * @version 2010/11/11
 */
function addRowForProject(formId, tableId, headerRowCount, newTR, copyIndex, addSon, warning, message, ok, message2, cancel, e) {
	var table = document.getElementById(tableId).getElementsByTagName("tbody")[0];
	var trs = table.getElementsByTagName("tr");
	
	//若有選擇一個row
	if(isSelectOneRow(tableId)) {
		var serialNumber = 1;
		var renewFlag = false;
		var nextIndex = 0;
		var prefix = "";
		var currentNumber = "";
		var currentPrefix = "";
		var currentReplace = "";
		
		for(var i = headerRowCount; i < trs.length; i++) {
			var tr = trs[i];
			
			if(tr.className == "TR_FOCUS") {
				if(addSon) { //則移動此row到focus row下方(加長子)
					//更改focus row為開啟狀態
					var td = tr.getElementsByTagName("td")[1];
					var img = td.getElementsByTagName("img")[0];
					img.src = img.src.replace("tree_open.gif", "tree_close.gif");
					img.src = img.src.replace("Blank.gif", "tree_close.gif");
					//第一層子節點,全部顯現
					var firstLevelChildIndex = getFirstLevelChildIndex(tableId, i);
					for(var j = 0; j < firstLevelChildIndex.length; j++) {
						trs[firstLevelChildIndex[j]].style.display = "";
					}
					
					nextIndex = i + 1;
				} else { //則移動到最後一個子節點的下方(加弟弟)
					nextIndex = getLastIndexForFolder(tableId, i) + 1;
				}
				
				//將欲插入的新row的outlineNumber準備好
				var html = "<img src = \"/csiebug-2.0/images/Blank.gif\" align = \"absmiddle\"></img>\n";
				
				var outlineNumber = "";
				
				//新增的outlineNumber就取下一個的outlineNumber來用
				//但如果focus row是獨子,則加弟弟的時候取得的nextIndex不能拿來用(因為那是叔叔節點了)
				if(!addSon && (nextIndex >= trs.length || getOutlineNumber(tableId, i).split(".").length != getOutlineNumber(tableId, nextIndex).split(".").length)) {
					var splitFocusOutlineNumber = getOutlineNumber(tableId, i).split(".");
					for(var j = 0; j < splitFocusOutlineNumber.length; j++) {
						if(outlineNumber != "") {
							outlineNumber = outlineNumber + ".";
						}
						
						if(j == splitFocusOutlineNumber.length - 1) {
							outlineNumber = outlineNumber + (parseInt(splitFocusOutlineNumber[j], 10) + 1);
						} else {
							outlineNumber = outlineNumber + splitFocusOutlineNumber[j];
						}
					}
				} else if(addSon && (nextIndex >= trs.length)) {
					outlineNumber = getOutlineNumber(tableId, trs.length - 1) + ".1";
				} else if(addSon && (getOutlineNumber(tableId, i).split(".").length == getOutlineNumber(tableId, nextIndex).split(".").length)) {
					outlineNumber = getOutlineNumber(tableId, i) + ".1";
				} else {
					outlineNumber = getOutlineNumber(tableId, nextIndex);
				}
				
				var splitOutlineNumber = outlineNumber.split(".");
				currentNumber = parseInt(splitOutlineNumber[splitOutlineNumber.length - 1], 10) - 1;
				for(var j = 0; j < splitOutlineNumber.length - 1; j++) {
					if(prefix != "") {
						prefix = prefix + ".";
					}
					
					prefix = prefix + splitOutlineNumber[j];
				}
				
				for(var j = 0; j < outlineNumber.split(".").length - 1; j++) {
					html = "&nbsp;" + html;
				}
				
				var newTDs = newTR.getElementsByTagName("td");
				
				var excelRowControlTD = newTDs[0];
				excelRowControlTD.style.cursor = "pointer";
				
				var outlineNumberTD = newTDs[1];
				outlineNumberTD.innerHTML = html + "<span>" + outlineNumber + "</span>";
				
				for(var j = 0; j < newTDs.length; j++) {
					var newTD = newTDs[j];
					if(nextIndex < trs.length) {
						newTD.id = trs[nextIndex].getElementsByTagName("td")[j].id;
					} else {
						var lastId = trs[trs.length - 1].getElementsByTagName("td")[j].id;
						newTD.id = (parseInt(lastId.split("_")[0], 10) + 1) + "_" + lastId.split("_")[1];
					}
					
					if(j == 2) {
						var hidden = newTD.getElementsByTagName("input")[0];
						hidden.id = tableId + "_" + newTD.id;
						hidden.name = hidden.id;
						hidden.value = outlineNumber;
					}
					
					if(j == 3) {
						var maxTaskId = document.getElementById(tableId + "_maxTaskId");
						var hidden = newTD.getElementsByTagName("input")[0];
						hidden.id = tableId + "_" + newTD.id;
						hidden.name = hidden.id;
						hidden.value = parseInt(maxTaskId.value, 10) + 1;
						maxTaskId.val(hidden.value);
					}
					
					if(j >= 4) {
						if(newTD.getAttribute("onClick") != null) {
							//IE不允許用setAttribute方式設定onClick屬性,所以必須用onclick掛上function的方式掛上
							if(window.navigator.appName.indexOf("Microsoft") != -1) {
								newTD.onclick = function() {
									//可能是掛上function的時間點問題
									//直接用i的話,會變成每一個都變成最後一號,然後就會有問題
									//所以直接用當時的td的id去取得columnIndex
									selTDForCopyForProject(tableId, this, nextIndex, parseInt(this.id.split("_")[1], 10), headerRowCount, e, warning, message2, ok, cancel);
									return true;
								};
							} else {
								newTD.setAttribute("onClick", newTD.getAttribute("onClick").replace("selTDForCopyForProject('" + tableId + "', this, " + copyIndex, "selTDForCopyForProject('" + tableId + "', this, " + nextIndex));
							}
						}
						
						var div = newTD.getElementsByTagName("div")[0];
						var text = newTD.getElementsByTagName("input")[0];
						var calendar = newTD.getElementsByTagName("div")[1];
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
				
				newTR.id = replaceAll(outlineNumber, ".", "_");
				newTR.className = "TR_ODD";
				
				//正式插入新節點
				if(nextIndex >= trs.length) {
					table.appendChild(newTR);
				} else {
					table.insertBefore(newTR, trs[nextIndex]);
				}
				
				sendAJAXRequestForProject(formId, tableId, newTR);
				
				renewFlag = true;
			} else if(renewFlag) {
				var tds = tr.getElementsByTagName("td");
				
				//更新excel control row
				var excelControl = tds[0];
				//IE不允許用setAttribute方式設定onClick屬性,所以必須用onclick掛上function的方式掛上
				if(window.navigator.appName.indexOf("Microsoft") != -1) {
					excelControl.onclick = function(){
						//可能是掛上function的時間點問題
						//直接用serialNumber的話,會變成每一個都變成最後一號,然後就會有問題
						//所以直接用當時的td去取innerHTML
						selOneDataForClickForProject(tableId, parseInt(this.getElementsByTagName("p")[0].innerHTML, 10), 0, headerRowCount, event);
					    return true;
					};
				} else {
					excelControl.setAttribute("onClick", "selOneDataForClickForProject('" + tableId + "', " + serialNumber + ", 0, " + headerRowCount + ", event);");
				}
				
				var p = excelControl.getElementsByTagName("p")[0];
				p.innerHTML = serialNumber;
				
				//更新outlineNumber
				if(i >= nextIndex) {
					var tdOutlineNumber = tds[1];
					var originalOutlineNumber = tdOutlineNumber.getElementsByTagName("span")[0].innerHTML;
					var renewOutlineNumberFlag = originalOutlineNumber.startsWith(prefix) && (prefix == "" || originalOutlineNumber.startsWith(prefix + "."));
					if(renewOutlineNumberFlag) {
						if((prefix == "" && originalOutlineNumber.split(".").length == 1) ||
						   (prefix != "" && (originalOutlineNumber.split(".").length == (prefix.split(".").length + 1)))) {
							currentNumber++;
							
							if(prefix != "") {
								currentPrefix = prefix + "." + currentNumber;
							} else {
								currentPrefix = prefix + currentNumber;
							}
							
							currentReplace = originalOutlineNumber;
							
							tdOutlineNumber.innerHTML = tdOutlineNumber.innerHTML.replace("<span>" + originalOutlineNumber, "<span>" + currentPrefix);
							tdOutlineNumber.innerHTML = tdOutlineNumber.innerHTML.replace("<SPAN>" + originalOutlineNumber, "<SPAN>" + currentPrefix);
							tr.id = tr.id.replace(replaceAll(originalOutlineNumber, ".", "_"), replaceAll(currentPrefix, ".", "_"));
						} else {
							tdOutlineNumber.innerHTML = tdOutlineNumber.innerHTML.replace("<span>" + currentReplace, "<span>" + currentPrefix);
							tdOutlineNumber.innerHTML = tdOutlineNumber.innerHTML.replace("<SPAN>" + currentReplace, "<SPAN>" + currentPrefix);
							tr.id = tr.id.replace(replaceAll(currentReplace, ".", "_"), replaceAll(currentPrefix, ".", "_"));
						}
					}
					
					if(i > nextIndex) {
						for(var j = 0; j < tds.length; j++) {
							var td = tds[j];
							td.id = (parseInt(td.id.split("_")[0], 10) + 1) + "_" + td.id.split("_")[1];
							
							if(j == 2) {
								var hidden = td.getElementsByTagName("input")[0];
								hidden.id = tableId + "_" + td.id;
								hidden.name = hidden.id;
								hidden.value = replaceAll(tr.id, "_", ".");
							}
							
							if(j == 3) {
								var hidden = td.getElementsByTagName("input")[0];
								hidden.id = tableId + "_" + td.id;
								hidden.name = hidden.id;
							}
							
							if(j >= 4) {
								if(td.getAttribute("onClick") != null) {
									//IE不允許用setAttribute方式設定onClick屬性,所以必須用onclick掛上function的方式掛上
									if(window.navigator.appName.indexOf("Microsoft") != -1) {
										td.onclick = function() {
											//可能是掛上function的時間點問題
											//直接用i,j的話,會變成每一個都變成最後一號,然後就會有問題
											//所以直接用當時的td的id去取得rowIndex與columnIndex
											selTDForCopyForProject(tableId, this, i, parseInt(this.id.split("_")[1], 10), headerRowCount, e, warning, message2, ok, cancel);
											return true;
										};
									} else {
										td.setAttribute("onClick", td.getAttribute("onClick").replace("selTDForCopyForProject('" + tableId + "', this, " + (i - 1), "selTDForCopyForProject('" + tableId + "', this, " + i));
									}
								}
								
								var div = td.getElementsByTagName("div")[0];
								var text = td.getElementsByTagName("input")[0];
								var calendar = td.getElementsByTagName("div")[1];
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
										text.setAttribute("onKeyDown", text.getAttribute("onKeyDown").replace("moveToOtherEditableLabel('" + tableId + "', " + (i - 1) + ", ", "moveToOtherEditableLabel('" + tableId + "', " + i + ", "));
									}
								}
							}
						}
						
						if(renewOutlineNumberFlag) {
							sendAJAXRequestForProjectOutlineNumber(formId, tableId, tr);
						}
					}
				}
			}
			
			serialNumber++;
		}
		
		//等別的運算累積滿了自動送出,或是由使用者手動決定全部送出
		//sendUpdateOutlineNumberPool(formId, tableId);
	} else {
		openAlertDialog(null, warning, message, ok, '', null, 0);
	}
}

/**
 * 刪除row
 * @author csiebug
 * @version 2010/11/11
 */
function deleteRowForProject(formId, tableId, headerRowCount, warning, message, ok, message2, cancel, e) {
	if(isSelectRows(tableId)) {
		var table = document.getElementById(tableId);
		var trs = table.getElementsByTagName("tr");
		var moveUpCounter = 0;
		
		var firstIndexToDelete = headerRowCount;
		var deleteIds = "";
		for(var i = trs.length - 1; i >= headerRowCount; i--) {
			var tr = trs[i];
			if(tr.className == "TR_FOCUS") {
				firstIndexToDelete = i;
				
				var tbody = table.getElementsByTagName("tbody")[0];
				var outlineNumber = tr.getElementsByTagName("td")[1].getElementsByTagName("span")[0].innerHTML;
				var prefix = "";
				var splitOutlineNumber = outlineNumber.split(".");
				for(var j = 0; j < splitOutlineNumber.length - 1; j++) {
					if(prefix != "") {
						prefix = prefix + ".";
					}
					
					prefix = prefix + splitOutlineNumber[j];
				}
				
				//刪除自己並自己底下的所有子節點
				var lastChildIndex = getLastIndexForFolder(tableId, i);
				for(var j = lastChildIndex; j >= i; j--) {
					if(deleteIds != "") {
						deleteIds = deleteIds + ",";
					}
					
					deleteIds = deleteIds + trs[j].getElementsByTagName("td")[3].getElementsByTagName("input")[0].value;
					
					tbody.removeChild(trs[j]);
				}
				
				//更新自己後面的兄弟節點的outlineNumber
				var currentNumber = "";
				var currentPrefix = "";
				var currentReplace = "";
				for(var j = i; j < trs.length; j++) {
					var td = trs[j].getElementsByTagName("td")[1];
					var originalOutlineNumber = td.getElementsByTagName("span")[0].innerHTML;
					
					if(originalOutlineNumber.startsWith(prefix) && (prefix == "" || originalOutlineNumber.startsWith(prefix + "."))) {
						if((prefix == "" && originalOutlineNumber.split(".").length == 1) ||
						   (prefix != "" && (originalOutlineNumber.split(".").length == (prefix.split(".").length + 1)))) {
							currentNumber = parseInt(originalOutlineNumber.split(".")[originalOutlineNumber.split(".").length - 1], 10) - 1;
							
							if(prefix != "") {
								currentPrefix = prefix + "." + currentNumber;
							} else {
								currentPrefix = prefix + currentNumber;
							}
							
							currentReplace = originalOutlineNumber;
							
							td.innerHTML = td.innerHTML.replace("<span>" + originalOutlineNumber, "<span>" + currentPrefix);
							td.innerHTML = td.innerHTML.replace("<SPAN>" + originalOutlineNumber, "<SPAN>" + currentPrefix);
							trs[j].id = trs[j].id.replace(replaceAll(originalOutlineNumber, ".", "_"), replaceAll(currentPrefix, ".", "_"));
						} else {
							td.innerHTML = td.innerHTML.replace("<span>" + currentReplace, "<span>" + currentPrefix);
							td.innerHTML = td.innerHTML.replace("<SPAN>" + currentReplace, "<SPAN>" + currentPrefix);
							trs[j].id = trs[j].id.replace(replaceAll(currentReplace, ".", "_"), replaceAll(currentPrefix, ".", "_"));
						}
					}
				}
			}
		}
		
		sendAJAXRequestForProjectDelete(formId, tableId, deleteIds);
		
		for(var i = firstIndexToDelete; i < trs.length; i++) {
			var tr = trs[i];
			var tds = tr.getElementsByTagName("td");
			
			var excelControl = tds[0];
			//IE不允許用setAttribute方式設定onClick屬性,所以必須用onclick掛上function的方式掛上
			if(window.navigator.appName.indexOf("Microsoft") != -1) {
				excelControl.onclick = function() {
					//可能是掛上function的時間點問題
					//直接用i的話,會變成每一個都變成最後一號,然後就會有問題
					//所以直接用當時的td的id去取得rowIndex
					selOneDataForClickForProject(tableId, parseInt(this.id.split("_")[0], 10), 0, headerRowCount, e);
					return true;
				};
			} else {
				excelControl.setAttribute("onClick", "selOneDataForClickForProject('" + tableId + "', " + i + ", 0, " + headerRowCount + ", event);");
			}
			
			var p = excelControl.getElementsByTagName("p")[0];
			p.innerHTML = i + 1 - headerRowCount;
			
			for(var j = 0; j < tds.length; j++) {
				var td = tds[j];
				
				td.id = i + "_" + td.id.split("_")[1];
				
				if(j == 2) {
					var hidden = td.getElementsByTagName("input")[0];
					hidden.id = tableId + "_" + td.id;
					hidden.name = hidden.id;
					hidden.value = replaceAll(tr.id, "_", ".");
				}
				
				if(j == 3) {
					var hidden = td.getElementsByTagName("input")[0];
					hidden.id = tableId + "_" + td.id;
					hidden.name = hidden.id;
				}
				
				if(j >= 4) {
					if(td.getAttribute("onClick") != null) {
						//IE不允許用setAttribute方式設定onClick屬性,所以必須用onclick掛上function的方式掛上
						if(window.navigator.appName.indexOf("Microsoft") != -1) {
							td.onclick = function() {
								//可能是掛上function的時間點問題
								//直接用i的話,會變成每一個都變成最後一號,然後就會有問題
								//所以直接用當時的td的id去取得rowIndex
								selTDForCopyForProject(tableId, this, parseInt(this.id.split("_")[0], 10), parseInt(this.id.split("_")[1], 10), headerRowCount, e, warning, message2, ok, cancel);
								return true;
							};
						} else {
							td.setAttribute("onClick", "selTDForCopyForProject('" + tableId + "', this, " + i + ", " + j + ", " + headerRowCount + ", event, '" + warning + "', '" + message2 + "', '" + ok + "', '" + cancel + "')");
						}
					}
					
					var div = td.getElementsByTagName("div")[0];
					var text = td.getElementsByTagName("input")[0];
					var calendar = td.getElementsByTagName("div")[1];
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
			
			sendAJAXRequestForProjectOutlineNumber(formId, tableId, tr);
		}
		
		//等別的運算累積滿了自動送出,或是由使用者手動決定全部送出
		//sendUpdateOutlineNumberPool(formId, tableId);
	} else {
		openAlertDialog(null, warning, message, ok, '', null, 0);
	}
}

/**
 * 點選show出group row
 * @author csiebug
 * @version 2010/2/11
 */
function showGroupRowForProject(tableId, tr, imagePath) {
	var table = document.getElementById(tableId);
	var trs = table.getElementsByTagName("tr");
	
	if(tr.getElementsByTagName("td")[1].getElementsByTagName("img").length > 0) {
		if(tr.getElementsByTagName("td")[1].getElementsByTagName("img")[0].src.indexOf("/tree_open.gif") != -1) {
			tr.getElementsByTagName("td")[1].getElementsByTagName("img")[0].src = imagePath + "/tree_close.gif";
		}
	}
	var groupIdPrefix = tr.id + "_";
	
	for(var i = 0; i < trs.length; i++) {
		if(trs[i].id.indexOf(groupIdPrefix) == 0) {
			//只show下層節點
			if(trs[i].style.display == "none") {
				if(trs[i].id.substring(groupIdPrefix.length, trs[i].id.length).indexOf("_") == -1) {
					trs[i].style.display = "";
					if(trs[i].getElementsByTagName("td")[1].getElementsByTagName("img").length > 0) {
						if(trs[i].getElementsByTagName("td")[1].getElementsByTagName("img")[0].src.indexOf("/tree_open.gif") != -1 ||
						   trs[i].getElementsByTagName("td")[1].getElementsByTagName("img")[0].src.indexOf("/tree_close.gif") != -1) {
							trs[i].getElementsByTagName("td")[1].getElementsByTagName("img")[0].src = imagePath + "/tree_open.gif";
						}
					}
				}
			//全部子節點都收起來
			} else {
				trs[i].style.display = "none";
				if(tr.getElementsByTagName("td")[1].getElementsByTagName("img").length > 0) {
					if(tr.getElementsByTagName("td")[1].getElementsByTagName("img")[0].src.indexOf("/tree_close.gif") != -1) {
						tr.getElementsByTagName("td")[1].getElementsByTagName("img")[0].src = imagePath + "/tree_open.gif";
					}
				}
			}
		}
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
function selOneDataForClickMultiForProject(tableId, rowIndex, pagination, headerRowCount) {
	var trs = document.getElementById(tableId).getElementsByTagName("tr");
	var tr = trs[rowIndex];
	
	if(tr.className == "TR_ODD") {
		tr.className = "TR_FOCUS";
	} else {
		tr.className = "TR_ODD";
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
function selOneDataForClickForProject(tableId, rowIndex, pagination, headerRowCount, e) {
	var table = getRowStatus(tableId);
	var startFocusRow = table[1];
	var endFocusRow = table[2];
	var originalEndFocusRow = table[3];
	
	clearAllSelTD(tableId);
	selOneDataForClickMultiForProject(tableId, rowIndex, pagination, headerRowCount);
	
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
		
		var trs = document.getElementById(tableId).getElementsByTagName("tr");
		//取消其他的選取,要把本來單數或雙數列設回來
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
function selColumnForProject(tableId, headerRowCount, columnIndex, e) {
	var table = getColumnStatus(tableId);
	var startFocusColumn = table[1];
	var endFocusColumn = table[2];
	var originalEndFocusColumn = table[3];
	var focusColumn = table[4];
	
	var trs = document.getElementById(tableId).getElementsByTagName("tr");
	
	if(focusColumn == -1) {
		clearAllSelTDAndTRForProject(tableId, headerRowCount);
		
		for(var i = headerRowCount; i < trs.length; i++) {
			var tr = trs[i];
			var td = tr.getElementsByTagName("td")[columnIndex];
			
			td.className = "TD_FOCUS";
		}
		
		setColumnStatus(tableId, "focusColumn", columnIndex);
		
		setColumnStatus(tableId, "startFocusColumn", columnIndex);
		setColumnStatus(tableId, "endFocusColumn", -1);
		setColumnStatus(tableId, "originalEndFocusColumn", -1);
	} else {
		if(focusColumn == columnIndex) {
			clearAllSelTDAndTRForProject(tableId, headerRowCount);
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
					var tr = trs[i];
					var td = tr.getElementsByTagName("td")[columnIndex];
					
					if(td.className == "TD") {
						selFlag = true;
						break;
					}
				}
				
				for(var i = headerRowCount; i < trs.length; i++) {
					var tr = trs[i];
					var td = tr.getElementsByTagName("td")[columnIndex];
					
					if(selFlag) {
						td.className = "TD_FOCUS";
					} else {
						td.className = "TD";
					}
				}
			} else {
				setColumnStatus(tableId, "startFocusColumn", columnIndex);
				setColumnStatus(tableId, "endFocusColumn", -1);
				setColumnStatus(tableId, "originalEndFocusColumn", -1);
				
				clearAllSelTDAndTRForProject(tableId, headerRowCount);
				
				for(var i = headerRowCount; i < trs.length; i++) {
					var tr = trs[i];
					var td = tr.getElementsByTagName("td")[columnIndex];
					
					td.className = "TD_FOCUS";
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
function clearAllSelTDAndTRForProject(tableId, headerRowCount) {
	var tds = document.getElementById(tableId).getElementsByTagName("td");
	for(var i = 0; i < tds.length; i++) {
		if(tds[i].className == "TD_FOCUS") {
			tds[i].className = "TD";
		}
	}
	
	focusColumn = -1;
	
	var trs = document.getElementById(tableId).getElementsByTagName("tr");
	for(var i = headerRowCount ; i < trs.length; i++) {
		trs[i].className = "TR_ODD";
	}
}

/**
 * 選取grid的cell
 * @author csiebug
 * @version 2010/11/5
 */
function selTDForCopyForProject(tableId, td, rowIndex, columnIndex, headerRowCount, e, title, message, ok, cancel) {
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
		
		clearAllSelTDAndTRForProject(tableId, headerRowCount);
		
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

/**
 * 計算層次,根為0,依序為1,2,3
 * @author csiebug
 * @version 2010/11/16
 */
function countSpace(txt) {
	var counter = 0;
	var tmpString = txt;
	
	while(tmpString.indexOf("&nbsp;") != -1) {
		counter++;
		tmpString = tmpString.replace("&nbsp;");
	}
	
	return counter;
}

/**
 * 判斷此節點可否凸排
 * @author csiebug
 * @version 2010/11/16
 */
function canMoveLeft(td) {
	return (countSpace(td.innerHTML) != 0);
}

/**
 * 凸排
 * @author csiebug
 * @version 2010/11/16
 */
function moveLeft(formId, tableId, headerRowCount) {
	var table = document.getElementById(tableId).getElementsByTagName("tbody")[0];
	var trs = table.getElementsByTagName("tr");
	if(isSelectOneRow(tableId)) {
		for(var i = headerRowCount; i < trs.length; i++) {
			var tr = trs[i];
			if(tr.className == "TR_FOCUS") {
				var td = tr.getElementsByTagName("td")[1];
				
				if(canMoveLeft(td)) {
					//更新自己(縮排和outlineNumber)
					var originalOutlineNumber = td.getElementsByTagName("span")[0].innerHTML;
					var outlineNumber = "";
					var html = td.innerHTML;
					
					html = html.replace("&nbsp;", "");
					
					var splitOutlineNumber = originalOutlineNumber.split(".");
					splitOutlineNumber[splitOutlineNumber.length - 2] = parseInt(splitOutlineNumber[splitOutlineNumber.length - 2], 10) + 1;
					for(var j = 0; j < (splitOutlineNumber.length - 1); j++) {
						if(outlineNumber != "") {
							outlineNumber = outlineNumber + ".";
						}
						outlineNumber = outlineNumber + splitOutlineNumber[j];
					}
					
					//表示自己是原本父節點的第一個兒子
					//所以原本的父節點的所有子節點會變成自己的子節點,原本的父節點將變成leaf
					if(splitOutlineNumber[splitOutlineNumber.length - 1] == "1") {
						var parentTR = trs[i - 1];
						var parentTD = parentTR.getElementsByTagName("td")[1];
						var parentImg = parentTD.getElementsByTagName("img")[0];
						parentImg.src = parentImg.src.replace("tree_close.gif", "Blank.gif");
					}
					
					html = html.replace("<span>" + originalOutlineNumber, "<span>" + outlineNumber);
					html = html.replace("<SPAN>" + originalOutlineNumber, "<SPAN>" + outlineNumber);
					
					//變成開啟狀態(因為原本是兄弟的節點都顯現了,但如今會變成自己的子節點,所以要變成開啟目錄)
					html = html.replace("tree_open.gif", "tree_close.gif");
					
					td.innerHTML = html;
					
					tr.getElementsByTagName("td")[2].getElementsByTagName("input")[0].value = outlineNumber;
					
					tr.id = replaceAll(outlineNumber, ".", "_");
					
					sendAJAXRequestForProjectOutlineNumber(formId, tableId, tr);
					
					//更新原本就是自己的子節點(更新縮排和outlineNumber)
					var next = refreshChildForMoveLeft(formId, tableId, i, originalOutlineNumber, outlineNumber);
					
					//更新後面原本是自己的兄弟節點,變成自己的子節點(縮排不變,但要更新outlineNumber(接在原本自己的孩子後面))
					var prefix = "";
					var splitOriginalOutlineNumber = originalOutlineNumber.split(".");
					for(var j = 0; j < splitOriginalOutlineNumber.length - 1; j++) {
						if(prefix != "") {
							prefix = prefix + ".";
						}
						
						prefix = prefix + splitOriginalOutlineNumber[j];
					}
					var nextIndex = refreshOriginalBrotherToChildForMoveLeft(formId, tableId, next[0], prefix, outlineNumber, next[1]);
					
					//表示有本來是兄弟,但現在變成自己的子節點,則自己要變成目錄
					if(nextIndex > next[0]) {
						var img = td.getElementsByTagName("img")[0];
						img.src = img.src.replace("Blank.gif", "tree_close.gif");
					}
					
					//更新後面原本是自己的叔叔節點,變成自己的兄弟節點(縮排不變,但要更新outlineNumber)
					prefix = "";
					splitOriginalOutlineNumber = outlineNumber.split(".");
					splitOriginalOutlineNumber[splitOriginalOutlineNumber.length - 1] = parseInt(splitOriginalOutlineNumber[splitOriginalOutlineNumber.length - 1], 10) + 1;
					for(var j = 0; j < splitOriginalOutlineNumber.length - 1; j++) {
						if(prefix != "") {
							prefix = prefix + ".";
						}
						
						prefix = prefix + splitOriginalOutlineNumber[j];
					}
					
					refreshOriginalUncleToBrotherForMoveLeft(formId, tableId, nextIndex, prefix, splitOriginalOutlineNumber[splitOriginalOutlineNumber.length - 1]);
				}
				break;
			}
		}
		
		//等別的運算累積滿了自動送出,或是由使用者手動決定全部送出
		//sendUpdateOutlineNumberPool(formId, tableId);
	}
}

/**
 * 將index以下的節點找出是原本自己子孫的節點,將凸排一格,並將outlinNumber更新
 * @author csiebug
 * @version 2010/11/18
 */
function refreshChildForMoveLeft(formId, tableId, index, originalOutlineNumber, outlineNumber) {
	var table = document.getElementById(tableId).getElementsByTagName("tbody")[0];
	var trs = table.getElementsByTagName("tr");
	
	var returnArray = new Array();
	var nextIndex = index + 1;
	var nextNumber = 1;
	
	for(var i = (index + 1); i < trs.length; i++) {
		var tr = trs[i];
		var td = tr.getElementsByTagName("td")[1];
		var html = td.innerHTML;
		
		var tdOutlineNumber = td.getElementsByTagName("span")[0].innerHTML;
		
		if(tdOutlineNumber.startsWith(originalOutlineNumber) && (originalOutlineNumber == "" || tdOutlineNumber.startsWith(originalOutlineNumber + "."))) {
			html = html.replace("&nbsp;", "");
			
			html = html.replace("<span>" + originalOutlineNumber, "<span>" + outlineNumber);
			html = html.replace("<SPAN>" + originalOutlineNumber, "<SPAN>" + outlineNumber);
			
			td.innerHTML = html;
			
			tr.getElementsByTagName("td")[2].getElementsByTagName("input")[0].value = outlineNumber;
			
			//如果是第一層子節點,則讓它顯現(因為原本與父節點為兄弟的那些節點是顯現的,如今全部要變成自己的兄弟了)
			var postfix = tdOutlineNumber.replace(originalOutlineNumber, "");
			if(postfix.startsWith(".")) {
				postfix = postfix.replace(".", "");
			}
			if(postfix.split(".").length == 1) {
				tr.style.display = "";
			}
			tr.id = tr.id.replace(replaceAll(originalOutlineNumber, ".", "_"), replaceAll(outlineNumber, ".", "_"));
			
			sendAJAXRequestForProjectOutlineNumber(formId, tableId, tr);
			
			nextIndex = i + 1;
			var level = outlineNumber.split(".").length + 1;
			nextNumber = parseInt(tdOutlineNumber.split(".")[level], 10) + 1;
		}
	}
	
	//等別的運算累積滿了自動送出,或是由使用者手動決定全部送出
	//sendUpdateOutlineNumberPool(formId, tableId);
	
	returnArray[0] = nextIndex;
	returnArray[1] = nextNumber;
	
	return returnArray;
}

/**
 * 將index之後的子節點outlineNumber更新
 * @author csiebug
 * @version 2010/11/18
 */
function refreshChildOutlineNumber(formId, tableId, index, originalOutlineNumber, outlineNumber) {
	var table = document.getElementById(tableId).getElementsByTagName("tbody")[0];
	var trs = table.getElementsByTagName("tr");
	
	var nextIndex = index + 1;
	for(var i = (index + 1); i < trs.length; i++) {
		var tr = trs[i];
		var td = tr.getElementsByTagName("td")[1];
		var html = td.innerHTML;
		
		var tdOutlineNumber = td.getElementsByTagName("span")[0].innerHTML;
		
		if(tdOutlineNumber.startsWith(originalOutlineNumber) && (originalOutlineNumber == "" || tdOutlineNumber.startsWith(originalOutlineNumber + "."))) {
			html = html.replace("<span>" + originalOutlineNumber, "<span>" + outlineNumber);
			html = html.replace("<SPAN>" + originalOutlineNumber, "<SPAN>" + outlineNumber);
			
			td.innerHTML = html;
			
			tr.getElementsByTagName("td")[2].getElementsByTagName("input")[0].value = outlineNumber;
			
			tr.id = tr.id.replace(replaceAll(originalOutlineNumber, ".", "_"), replaceAll(outlineNumber, ".", "_"));
			
			sendAJAXRequestForProjectOutlineNumber(formId, tableId, tr);
			
			nextIndex = i + 1;
		}
	}
	
	//等別的運算累積滿了自動送出,或是由使用者手動決定全部送出
	//sendUpdateOutlineNumberPool(formId, tableId);
	
	return nextIndex;
}

/**
 * 將index之後,原本是兄弟節點的節點變成自己的子節點
 * @author csiebug
 * @version 2010/11/18
 */
function refreshOriginalBrotherToChildForMoveLeft(formId, tableId, index, prefix, parentOutlineNumber, number) {
	var nextIndex = index;
	var table = document.getElementById(tableId).getElementsByTagName("tbody")[0];
	var trs = table.getElementsByTagName("tr");
	
	var currentNumber = number;
	
	for(var i = index; i < trs.length; i++) {
		var tr = trs[i];
		var td = tr.getElementsByTagName("td")[1];
		var originalOutlineNumber = td.getElementsByTagName("span")[0].innerHTML;
		var outlineNumber = parentOutlineNumber + "." + currentNumber;
		var html = td.innerHTML;
		
		if(originalOutlineNumber.startsWith(prefix) && (prefix == "" || originalOutlineNumber.startsWith(prefix + "."))) {
			html = html.replace("<span>" + originalOutlineNumber, "<span>" + outlineNumber);
			html = html.replace("<SPAN>" + originalOutlineNumber, "<SPAN>" + outlineNumber);
			
			td.innerHTML = html;
			
			tr.getElementsByTagName("td")[2].getElementsByTagName("input")[0].value = outlineNumber;
			
			tr.id = tr.id.replace(replaceAll(originalOutlineNumber, ".", "_"), replaceAll(outlineNumber, ".", "_"));
			
			sendAJAXRequestForProjectOutlineNumber(formId, tableId, tr);
			
			var currentIndex = i;
			
			i = refreshChildOutlineNumber(formId, tableId, i, originalOutlineNumber, outlineNumber) - 1;
			
			currentNumber++;
			
			if(i > 0) {
				nextIndex = i + 1;
			} else {
				nextIndex = currentIndex + 1;
				break;
			}
		}
	}
	
	//等別的運算累積滿了自動送出,或是由使用者手動決定全部送出
	//sendUpdateOutlineNumberPool(formId, tableId);
	
	return nextIndex;
}

/**
 * 將index之後,原本是自己的叔叔節點,變成自己的兄弟節點(更新outlineNumber)
 * @author csiebug
 * @version 2010/11/18
 */
function refreshOriginalUncleToBrotherForMoveLeft(formId, tableId, index, prefix, number) {
	var table = document.getElementById(tableId).getElementsByTagName("tbody")[0];
	var trs = table.getElementsByTagName("tr");
	
	var currentNumber = number;
	var currentPrefix = prefix;
	
	for(var i = index; i < trs.length; i++) {
		var tr = trs[i];
		var td = tr.getElementsByTagName("td")[1];
		var originalOutlineNumber = td.getElementsByTagName("span")[0].innerHTML;
		var outlineNumber;
		if(prefix != "") {
			outlineNumber = prefix + "." + currentNumber;
		} else {
			outlineNumber = prefix + currentNumber;
			currentPrefix = parseInt(outlineNumber, 10) - 1;
		}
		var html = td.innerHTML;
		
		if(originalOutlineNumber.startsWith(currentPrefix)) {
			html = html.replace("<span>" + originalOutlineNumber, "<span>" + outlineNumber);
			html = html.replace("<SPAN>" + originalOutlineNumber, "<SPAN>" + outlineNumber);
			
			td.innerHTML = html;
			
			tr.getElementsByTagName("td")[2].getElementsByTagName("input")[0].value = outlineNumber;
			
			tr.id = tr.id.replace(replaceAll(originalOutlineNumber, ".", "_"), replaceAll(outlineNumber, ".", "_"));
			
			sendAJAXRequestForProjectOutlineNumber(formId, tableId, tr);
			
			i = refreshChildOutlineNumber(formId, tableId, i, originalOutlineNumber, outlineNumber) - 1;
			
			currentNumber++;
			
			if(i <= 0) {
				break;
			}
		}
	}
	
	//等別的運算累積滿了自動送出,或是由使用者手動決定全部送出
	//sendUpdateOutlineNumberPool(formId, tableId);
}

/**
 * 縮排
 * @author csiebug
 * @version 2010/11/16
 */
function moveRight(formId, tableId, headerRowCount) {
	var table = document.getElementById(tableId).getElementsByTagName("tbody")[0];
	var trs = table.getElementsByTagName("tr");
	if(isSelectOneRow(tableId)) {
		for(var i = headerRowCount; i < trs.length; i++) {
			var tr = trs[i];
			if(tr.className == "TR_FOCUS") {
				var td = tr.getElementsByTagName("td")[1];
				
				var originalOutlineNumber = td.getElementsByTagName("span")[0].innerHTML;
				var outlineNumber = "";
				var html = td.innerHTML;
				
				var splitOutlineNumber = originalOutlineNumber.split(".");
				
				//表示自己不是原本父節點的第一個兒子,才有辦法縮排依附在自己的哥哥節點
				if(splitOutlineNumber[splitOutlineNumber.length - 1] != "1") {
					//更新自己(縮排和outlineNumber)
					splitOutlineNumber[splitOutlineNumber.length - 1] = parseInt(splitOutlineNumber[splitOutlineNumber.length - 1], 10) - 1;
					for(var j = 0; j < splitOutlineNumber.length; j++) {
						if(outlineNumber != "") {
							outlineNumber = outlineNumber + ".";
						}
						outlineNumber = outlineNumber + splitOutlineNumber[j];
					}
					
					openParentDir(tableId, headerRowCount, outlineNumber);
					
					var preOutlineNumber = trs[i - 1].getElementsByTagName("td")[1].getElementsByTagName("span")[0].innerHTML;
					
					//表示自己的哥哥原本是leaf,如今自己依附哥哥,哥哥要變成folder
					if(outlineNumber == preOutlineNumber) {
						var parentTR = trs[i - 1];
						var parentTD = parentTR.getElementsByTagName("td")[1];
						var parentImg = parentTD.getElementsByTagName("img")[0];
						parentImg.src = parentImg.src.replace("Blank.gif", "tree_close.gif");
						outlineNumber = outlineNumber + ".1";
					} else {
						var splitPreOutlineNumber = preOutlineNumber.replace(outlineNumber + ".", "").split(".");
						outlineNumber = outlineNumber + "." + (parseInt(splitPreOutlineNumber[0], 10) + 1);
					}
					
					html = "&nbsp;" + html;
					html = html.replace("\n", "");
					
					html = html.replace("<span>" + originalOutlineNumber, "<span>" + outlineNumber);
					html = html.replace("<SPAN>" + originalOutlineNumber, "<SPAN>" + outlineNumber);
					
					td.innerHTML = html;
					
					tr.getElementsByTagName("td")[2].getElementsByTagName("input")[0].value = outlineNumber;
					
					tr.id = replaceAll(outlineNumber, ".", "_");
					
					sendAJAXRequestForProjectOutlineNumber(formId, tableId, tr);
					
					//更新原本就是自己的子節點(更新縮排和outlineNumber)
					var next = refreshChildForMoveRight(formId, tableId, i, originalOutlineNumber, outlineNumber);
					
					//更新後面原本是自己的兄弟節點(縮排不變,但要更新outlineNumber減1)
					refreshOriginalBrotherForMoveRight(formId, tableId, next, originalOutlineNumber);
				}
				
				break;
			}
		}
		
		//等別的運算累積滿了自動送出,或是由使用者手動決定全部送出
		//sendUpdateOutlineNumberPool(formId, tableId);
	}
}

/**
 * 將目錄打開
 * @author csiebug
 * @version 2010/11/18
 */
function openParentDir(tableId, headerRowCount, outlineNumber) {
	var table = document.getElementById(tableId).getElementsByTagName("tbody")[0];
	var trs = table.getElementsByTagName("tr");
	
	for(var i = headerRowCount; i < trs.length; i++) {
		var tr = trs[i];
		var td = tr.getElementsByTagName("td")[1];
		var originalOutlineNumber = td.getElementsByTagName("span")[0].innerHTML;
		
		if(originalOutlineNumber.startsWith(outlineNumber) && (outlineNumber == "" || originalOutlineNumber.startsWith(outlineNumber + "."))) {
			if(originalOutlineNumber == outlineNumber) {
				//目錄改為開啟狀態
				td.innerHTML = td.innerHTML.replace("tree_open.gif", "tree_close.gif");
			} else {
				//把目錄的第一層子節點,隱藏的改為顯現
				if(originalOutlineNumber.replace(outlineNumber + ".", "").split(".").length == 1) {
					tr.style.display = "";
				}
			}
		}
	}	
}

/**
 * 將index以下的節點找出是原本自己子孫的節點,將縮排一格,並將outlinNumber更新
 * @author csiebug
 * @version 2010/11/18
 */
function refreshChildForMoveRight(formId, tableId, index, originalOutlineNumber, outlineNumber) {
	var table = document.getElementById(tableId).getElementsByTagName("tbody")[0];
	var trs = table.getElementsByTagName("tr");
	
	var nextIndex = index + 1;
	
	for(var i = (index + 1); i < trs.length; i++) {
		var tr = trs[i];
		var td = tr.getElementsByTagName("td")[1];
		var html = td.innerHTML;
		
		var tdOutlineNumber = td.getElementsByTagName("span")[0].innerHTML;
		
		if(tdOutlineNumber.startsWith(originalOutlineNumber) && (originalOutlineNumber == "" || tdOutlineNumber.startsWith(originalOutlineNumber + "."))) {
			html = "&nbsp;" + html;
			html = html.replace("\n", "");
			
			html = html.replace("<span>" + originalOutlineNumber, "<span>" + outlineNumber);
			html = html.replace("<SPAN>" + originalOutlineNumber, "<SPAN>" + outlineNumber);
			
			td.innerHTML = html;
			
			tr.getElementsByTagName("td")[2].getElementsByTagName("input")[0].value = outlineNumber;
			
			tr.id = tr.id.replace(replaceAll(originalOutlineNumber, ".", "_"), replaceAll(outlineNumber, ".", "_"));
			
			sendAJAXRequestForProjectOutlineNumber(formId, tableId, tr);
			
			nextIndex = i + 1;
		}
	}
	
	//等別的運算累積滿了自動送出,或是由使用者手動決定全部送出
	//sendUpdateOutlineNumberPool(formId, tableId);
	
	return nextIndex;
}

/**
 * 將index之後,原本是兄弟節點的節點outlineNumber要減1
 * @author csiebug
 * @version 2010/11/18
 */
function refreshOriginalBrotherForMoveRight(formId, tableId, index, movedOutlineNumber) {
	var table = document.getElementById(tableId).getElementsByTagName("tbody")[0];
	var trs = table.getElementsByTagName("tr");
	
	var splitOutlineNumber = movedOutlineNumber.split(".");
	var prefix = "";
	for(var i = 0; i < splitOutlineNumber.length - 1; i++) {
		if(prefix != "") {
			prefix = prefix + ".";
		}
		
		prefix = prefix + splitOutlineNumber[i];
	}
	
	var currentNumber = parseInt(splitOutlineNumber[splitOutlineNumber.length - 1], 10);
	
	for(var i = index; i < trs.length; i++) {
		var tr = trs[i];
		var td = tr.getElementsByTagName("td")[1];
		var originalOutlineNumber = td.getElementsByTagName("span")[0].innerHTML;
		var outlineNumber = "";
		if(prefix != "") {
			outlineNumber = prefix + "." + currentNumber;
		} else {
			outlineNumber = prefix + "" + currentNumber;
		}
		var html = td.innerHTML;
		
		if(originalOutlineNumber.startsWith(prefix) && (prefix == "" || originalOutlineNumber.startsWith(prefix + "."))) {
			html = html.replace("<span>" + originalOutlineNumber, "<span>" + outlineNumber);
			html = html.replace("<SPAN>" + originalOutlineNumber, "<SPAN>" + outlineNumber);
			
			td.innerHTML = html;
			
			tr.getElementsByTagName("td")[2].getElementsByTagName("input")[0].value = outlineNumber;
			
			tr.id = tr.id.replace(replaceAll(originalOutlineNumber, ".", "_"), replaceAll(outlineNumber, ".", "_"));
			
			sendAJAXRequestForProjectOutlineNumber(formId, tableId, tr);
			
			var currentIndex = i;
			
			i = refreshChildOutlineNumber(formId, tableId, i, originalOutlineNumber, outlineNumber) - 1;
			
			currentNumber++;
			
			if(i <= 0) {
				break;
			}
		}
	}
	
	//等別的運算累積滿了自動送出,或是由使用者手動決定全部送出
	//sendUpdateOutlineNumberPool(formId, tableId);
}

function moveToOtherEditableLabel(tableId, rowIndex, columnIndex, e) {
	var table = document.getElementById(tableId);
	var trs = table.getElementsByTagName("tr");
	
	//使用者用上下鍵改變目前編輯的cell
	var key = window.event ? e.keyCode : e.which;
	
	if(key == 38 || key == 40) { //上/下
		var tdCurrent = trs[rowIndex].getElementsByTagName("td")[columnIndex];
		var txtCurrent = tdCurrent.getElementsByTagName("input")[0];
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
		
		var tdNew = trs[newIndex].getElementsByTagName("td")[columnIndex];
		tdNew.className = "TD_FOCUS";
		var labelNew = tdNew.getElementsByTagName("div")[0];
		var txtNew = tdNew.getElementsByTagName("input")[0];
		txtNew.style.display = "";
		txtNew.focus();
		labelNew.style.display = "none";
	} else if(e.ctrlKey && (key == 37 || key == 39)) { //因為左右用來移動游標編輯,所以不能用左右來跳不同的text編輯,所以加上ctrl作複合鍵
		var tds = trs[rowIndex].getElementsByTagName("td");
		var tdCurrent = tds[columnIndex];
		var txtCurrent = tdCurrent.getElementsByTagName("input")[0];
		tdCurrent.className = "TD";
		txtCurrent.blur();
		
		var newIndex = columnIndex;
		if(key == 37) { //左
			for(var i = columnIndex - 1; i > 0; i--) {
				var td = tds[i];
				
				//要先判斷那個column是否被藏起來了,沒有才可能移動過去
				var divHide = td.getElementsByTagName("div")[0];
				if(divHide != null) {
					var divChild = divHide.getElementsByTagName("div")[0];
					var textChild = divHide.getElementsByTagName("input")[0];
					if(divHide.style.display != "none" || divChild == null || textChild == null) {
						var div = td.getElementsByTagName("div")[0];
						var text = td.getElementsByTagName("input")[0];
						
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
				var divHide = td.getElementsByTagName("div")[0];
				if(divHide != null) {
					var divChild = divHide.getElementsByTagName("div")[0];
					var textChild = divHide.getElementsByTagName("input")[0];
					if(divHide.style.display != "none" || divChild == null || textChild == null) {
						var div = td.getElementsByTagName("div")[0];
						var text = td.getElementsByTagName("input")[0];
						
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
		var labelNew = tdNew.getElementsByTagName("div")[0];
		var txtNew = tdNew.getElementsByTagName("input")[0];
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
	var trs = document.getElementById(tableId).getElementsByTagName("tr");
	
	for(var i = headerRowCount; i < trs.length; i++) {
		var tr = trs[i];
		var tds = tr.getElementsByTagName("td");
		
		for(var j = 1; j < tds.length; j++) {
			var td = tds[j];
			
			if(td.getAttribute("style") != null && td.getAttribute("style").startsWith("border-")) {
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
function copyForProject(tableId, headerRowCount) {
	var copyField = document.getElementById(tableId + "_copyField");
	var copyRow = document.getElementById(tableId + "_copyRow");
	var pasteButton = document.getElementById(tableId + "_pasteButton");
	var pasteChildButton = document.getElementById(tableId + "_pasteChildButton");
	var pasteBrotherButton = document.getElementById(tableId + "_pasteBrotherButton");
	var trs = document.getElementById(tableId).getElementsByTagName("tr");
	
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
			var tds = tr.getElementsByTagName("td");
			var findLastFlag = false;
			var basicStyle = "border-top-style:dashed;border-top-width:thin;border-bottom-style:dashed;border-bottom-width:thin;";
			for(var j = tds.length - 1; j > 0; j--) {
				var td = tds[j];
				
				if(!findLastFlag && td.style.display != "none") {
					td.setAttribute("style", basicStyle + "border-right-style:dashed;border-right-width:thin;");
					
					findLastFlag = true;
				} else if(j == 1) {
					td.setAttribute("style", basicStyle + "border-left-style:dashed;border-left-width:thin;cursor:pointer");
				} else if(td.style.display != "none") {
					td.setAttribute("style", basicStyle);
				}
			}
		}
	}
	copyRow.value = selRows;
	
	if(copyRow.value != "") { //複製row
		pasteChildButton.style.display = "";
		pasteBrotherButton.style.display = "";
		
		copyField.value = "";
		pasteButton.style.display = "none";
	} else { //複製cell text
		var startFocusRow = getRowStatus(tableId)[1];
		var startFocusColumn = getColumnStatus(tableId)[1];
		
		var td = trs[startFocusRow].getElementsByTagName("td")[startFocusColumn];
		var div = td.getElementsByTagName("div")[0];
		var text = td.getElementsByTagName("input")[0];
		
		if(div != null && text != null) {
			copyField.value = div.innerHTML;
		} else {
			copyField.value = td.innerHTML;
		}
		
		if(copyField.value != "") {
			//加上虛線表示被複製
			cancelPreCopy(tableId, headerRowCount);
			td.setAttribute("style", "border-style:dashed;border-width:thin;");
			
			pasteButton.style.display = "";
			
			copyRow.value = "";
			pasteChildButton.style.display = "none";
			pasteBrotherButton.style.display = "none";
		}
	}
}

/**
 * 將複製的值貼到所選的cell裡面
 * @author csiebug
 * @version 2010/11/25
 */
function pasteFieldForProject(tableId, headerRowCount) {
	var copyField = document.getElementById(tableId + "_copyField");
	var trs = document.getElementById(tableId).getElementsByTagName("tr");
	
	if(copyField.value != "") {
		for(var i = headerRowCount; i < trs.length; i++) {
			var tr = trs[i];
			var tds = tr.getElementsByTagName("td");
			
			var modifyTitleFlag = false;
			for(var j = 2; j < tds.length; j++) {
				var td = tds[j];
				
				if(td.className == "TD_FOCUS") {
					var div = td.getElementsByTagName("div")[0];
					var text = td.getElementsByTagName("input")[0];
					
					if(div != null && text != null) {
						div.innerHTML = copyField.value;
						text.value = copyField.value;
						
						if(j == 2) {
							modifyTitleFlag = true;
						}
					}
				}
			}
			
			if(modifyTitleFlag) {
				tr.title = copyField.value;
			}	
		}
	}
}

/**
 * 將複製的row貼到所選的row子節點或是同層節點
 * @author csiebug
 * @version 2010/11/25
 */
function pasteRowsForProject(formId, tableId, headerRowCount, addSon, warning, message, ok, message2, cancel, e) {
	var copyRow = document.getElementById(tableId + "_copyRow");
	var pasteChildButton = document.getElementById(tableId + "_pasteChildButton");
	var pasteBrotherButton = document.getElementById(tableId + "_pasteBrotherButton");
	
	if(copyRow.value != "") {
		var rowsIndex = copyRow.value.split(",");
		
		for(var i = rowsIndex.length - 1; i >= 0; i--) {
			var index = rowsIndex[i];
			pasteRowForProject(formId, tableId, headerRowCount, addSon, warning, message, ok, message2, cancel, e, index);
		}
	}
	
	copyRow.value = "";
	pasteChildButton.style.display = "none";
	pasteBrotherButton.style.display = "none";
}

function pasteRowForProject(formId, tableId, headerRowCount, addSon, warning, message, ok, message2, cancel, e, index) {
	var table = document.getElementById(tableId).getElementsByTagName("tbody")[0];
	var newTR = document.createElement("tr");
	var trs = table.getElementsByTagName("tr");
	
	var copyTR = trs[index];
	var tds = copyTR.getElementsByTagName("td");
	
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
		if(tds[i].getAttribute("style").startsWith(basicStyle)) {
			if(i == 1) {
				tds[i].setAttribute("style", "cursor:pointer");
			} else {
				tds[i].setAttribute("style", "");
			}
		}
	}
	
	//這裡才是將新增列塞進grid的動作
	addRowForProject(formId, tableId, headerRowCount, newTR, index, addSon, warning, message, ok, message2, cancel, e);
}

/**
 * 把不想看的column藏起來/把藏起來的column顯現出來
 * @author csiebug
 * @version 2010/11/29
 */
function hideOrShowColumnForProject(tableId, headerRowCount, columnIndex, hideFlag) {
	var trs = document.getElementById(tableId).getElementsByTagName("tr");
	
	for(var i = 0; i < headerRowCount; i++) {
		var tr = trs[i];
		var td = tr.getElementsByTagName("td")[columnIndex];
		
		if(hideFlag) {
			//IE不允許用setAttribute方式設定onClick屬性,所以必須用onclick掛上function的方式掛上
			if(window.navigator.appName.indexOf("Microsoft") != -1) {
				td.onclick = function() {
					hideOrShowColumnForProject(tableId, headerRowCount, columnIndex, false);
					return true;
				};
			} else {
				td.setAttribute("onClick", "hideOrShowColumnForProject('" + tableId + "', " + headerRowCount + ", " + columnIndex + ", false)");
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
					hideOrShowColumnForProject(tableId, headerRowCount, columnIndex, true);
					return true;
				};
			} else {
				td.setAttribute("onClick", "hideOrShowColumnForProject('" + tableId + "', " + headerRowCount + ", " + columnIndex + ", true)");
			}
			
			var hideValue = td.getElementsByTagName("div")[0].innerHTML;
			td.innerHTML = hideValue;
		}
	}
	
	for(var i = headerRowCount; i < trs.length; i++) {
		var tr = trs[i];
		var td = tr.getElementsByTagName("td")[columnIndex];
		
		if(hideFlag) {
			var hideDiv = document.createElement("div");
			hideDiv.innerHTML = td.innerHTML;
			hideDiv.style.display = "none";
			td.innerHTML = "";
			td.appendChild(hideDiv);
		} else {
			var hideValue = td.getElementsByTagName("div")[0].innerHTML;
			td.innerHTML = hideValue;
		}
	}
}

/**
 * 用ajax的方式刪除資料
 * @author csiebug
 * @version 2010/12/6
 */
function sendAJAXRequestForProjectDelete(formId, tableId, deleteIds) {
	var form = document.getElementById(formId);
	if(form != null) {
		var url = form.action + "?ActFlag=deleteProjectRecord&projectId=" + document.getElementById(tableId + "_projectId").value + "&uniqueIDs=" + deleteIds;
		
		document.getElementById(tableId + "_updateStatus").value = sendAJAXRequest(url);
	}
}

//改善ajax次數的公用變數
var defaultPoolSize = 20;
var updateOutlineNumberPoolCounter = 0;
var updateOutlineNumbers = "";
var updateFieldPoolCounter = 0;
var updateFields = "";

/**
 * 用ajax的方式處理outlineNumber更新
 * 但因為此method常包在迴圈內執行,故用pool的方式批次執行,減少ajax request的次數
 * @author csiebug
 * @version 2010/12/3
 */
function sendAJAXRequestForProjectOutlineNumber(formId, tableId, tr) {
	var form = document.getElementById(formId);
	if(form != null) {
		var uniqueID = tr.getElementsByTagName("td")[3].getElementsByTagName("input")[0].value;
		var outlineNumber = replaceAll(tr.id, "_", ".");
		updateOutlineNumberPoolCounter = updateOutlineNumberPoolCounter + 1;
		
		if(updateOutlineNumberPoolCounter < defaultPoolSize) {
			if(updateOutlineNumbers != "") {
				updateOutlineNumbers = updateOutlineNumbers + ";";
			}
			
			updateOutlineNumbers = updateOutlineNumbers + uniqueID + "," + outlineNumber;
		} else {
			sendUpdateOutlineNumberPool(formId, tableId);
		}
	}
}

/**
 * 把pool內資料倒出去送出ajax request
 * pool累積滿了會自動被呼叫,但若沒有滿,還是要有時間點要呼叫此function送出
 * @author csiebug
 * @version 2010/12/3
 */
function sendUpdateOutlineNumberPool(formId, tableId) {
	var form = document.getElementById(formId);
	if(form != null && updateOutlineNumbers != "") {
		updateOutlineNumberPoolCounter = 0;
		
		var url = form.action + "?ActFlag=updateOutlineNumber&projectId=" + document.getElementById(tableId + "_projectId").value + "&outlineNumbers=" + updateOutlineNumbers;
		
		document.getElementById(tableId + "_updateStatus").value = sendAJAXRequest(url);
		
		updateOutlineNumbers = "";
	}
}

/**
 * 用ajax的方式處理cell更新
 * 怕使用者操作cell太頻繁,所以也是利用pool來控制ajax request數量
 * @author csiebug
 * @version 2010/12/6
 */
function sendAJAXRequestForProjectField(formId, tableId, field) {
	var form = document.getElementById(formId);
	if(form != null) {
		var tr = field.parentNode.parentNode;
		var uniqueID = tr.getElementsByTagName("td")[3].getElementsByTagName("input")[0].value;
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
	var form = document.getElementById(formId);
	if(form != null) {
		updateFieldPoolCounter = 0;
		
		var url = form.action + "?ActFlag=saveProjectField&projectId=" + document.getElementById(tableId + "_projectId").value + "&fields=" + updateFields;
		
		document.getElementById(tableId + "_updateStatus").value = sendAJAXRequest(url);
		
		updateFields = "";
	}
}

/**
 * 用ajax的方式一次處理一個row的資料
 * @author csiebug
 * @version 2010/12/3
 */
function sendAJAXRequestForProject(formId, tableId, tr) {
	var form = document.getElementById(formId);
	if(form != null) {
		var inputs = tr.getElementsByTagName("input");
		var selects = tr.getElementsByTagName("select");
		
		var url = form.action + "?ActFlag=saveProjectRecord&projectId=" + document.getElementById(tableId + "_projectId").value;
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
		
		document.getElementById(tableId + "_updateStatus").value = sendAJAXRequest(url);
	}
}

/**
 * project table也可使用快速鍵執行複製貼上刪除等動作
 * @author csiebug
 * @version 2010/12/9 
 */
function projectGridOnKeyDown(formId, tableId, headerRowCount, warning, message, ok, message2, cancel, e) {
	var key = window.event ? e.keyCode : e.which;
	
	if(e.ctrlKey && (key == 67 || key == 86 || key == 88)) {
		if(key == 67) { //ctrl-c 複製
			copyForProject(tableId, headerRowCount);
		} else if(key == 86) { //ctrl-v 貼上
			var copyField = document.getElementById(tableId + "_copyField");
			var copyRow = document.getElementById(tableId + "_copyRow");
			if(copyRow.value != "") { //貼上row
				pasteRowsForProject(formId, tableId, headerRowCount, true, warning, message, ok, message2, cancel, e);
			} else if(copyField.value != "") { //貼上cell text
				pasteFieldForProject(tableId, 1);
			}
		} else if(key == 88) { //ctrl-x 剪下
			//TODO: 暫不實作
		}
	} else if(key == 46) {
		deleteRowForProject(formId, tableId, headerRowCount, warning, message, ok, message2, cancel, e);
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
function createNewRowForProject(formId, tableId, headerRowCount, addSon, warning, message, ok, message2ForAdd, cancel, e, blankText, messageInterval, message1Start, message1End, message2, message3, message4, message5, message6, message7, message8Start, message8End, message9Start, message9End, message10Start, message10End, message11, message12Start, message12End, message13, message14) {
	var table = document.getElementById(tableId).getElementsByTagName("tbody")[0];
	var newTR = document.createElement("tr");
	var trs = table.getElementsByTagName("tr");
	
	var lastTR = trs[trs.length - 1];
	var tds = lastTR.getElementsByTagName("td");
	
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
					selOneDataForClickForProject(tableId, headerRowCount, 0, headerRowCount, event);
					return true;
				};
			} else {
				newTD.setAttribute("onClick", "selOneDataForClickForProject('" + tableId + "', " + headerRowCount + ", 0, " + headerRowCount + ", event)");
			}
		} else if(i == 1) {
			newTD.style.cursor = "pointer";
			newTD.innerHTML = "<img src = \"/csiebug-2.0/images/Blank.gif\" align = \"absmiddle\"></img><span>1</span>";
			//IE不允許用setAttribute方式設定onClick屬性,所以必須用onclick掛上function的方式掛上
			if(window.navigator.appName.indexOf("Microsoft") != -1) {
				newTD.onclick = function() {
					showGroupRowForProject(tableId, this.parentNode, '/csiebug-2.0/images/');
					return true;
				};
			} else {
				newTD.setAttribute("onClick", "showGroupRowForProject('" + tableId + "', this.parentNode, '/csiebug-2.0/images/')");
			}
		} else if(i == 2 || i == 3) {
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
					selTDForCopyForProject(tableId, this, parseInt(this.id.split("_")[0], 10), parseInt(this.id.split("_")[1], 10), headerRowCount, e, warning, message2ForAdd, ok, cancel);
					return true;
				};
			} else {
				newTD.setAttribute("onClick", "selTDForCopyForProject('" + tableId + "', this, " + headerRowCount + ", " + i + ", " + headerRowCount + ", event, '" + warning + "', '" + message2ForAdd + "', '" + ok + "', '" + cancel + "')");
			}
			
			//TODO: 自己要撰寫產生新row的程式碼,因為每個grid的新增定義不一樣
			userDefineNewRow(newTD, i, formId, tableId, headerRowCount, blankText, warning, ok, messageInterval, message1Start, message1End, message2, message3, message4, message5, message6, message7, message8Start, message8End, message9Start, message9End, message10Start, message10End, message11, message12Start, message12End, message13, message14);
		}
	}
	
	//這裡才是將新增列塞進grid的動作
	addRowForProject(formId, tableId, headerRowCount, newTR, headerRowCount, addSon, warning, message, ok, message2ForAdd, cancel, e);
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
	var inputHTML = "<input type = \"text\" id = \"" + tableId + "_" + headerRowCount + "_" + columnIndex + "\" name = \"" + tableId + "_" + headerRowCount + "_" + columnIndex + "\" class = \"Text\" DataType = \"" + dataType + "\" minValue = \"-1\" maxValue = \"-1\" maxInteger = \"-1\" maxDecimal = \"-1\" isRequired = \"false\" value = \"\" fixLength = \"-1\" style = \"display:none\" onChange = \"modifyLabel('" + blankText + "', this, '" + warning + "', '" + ok + "', '" + messageInterval + "', '" + message1Start + "', '" + message1End + "', '" + message2 + "', '" + message3 + "', '" + message4 + "', '" + message5 + "', '" + message6 + "', '" + message7 + "', '" + message8Start + "', '" + message8End + "', '" + message9Start + "', '" + message9End + "', '" + message10Start + "', '" + message10End + "', '" + message11 + "', '" + message12Start + "', '" + message12End + "', '" + message13 + "', '" + message14 + "');sendAJAXRequestForProjectField('" + formId + "', '" + tableId + "', this);\" onBlur = \"modifyLabel('" + blankText + "', this, '" + warning + "', '" + ok + "', '" + messageInterval + "', '" + message1Start + "', '" + message1End + "', '" + message2 + "', '" + message3 + "', '" + message4 + "', '" + message5 + "', '" + message6 + "', '" + message7 + "', '" + message8Start + "', '" + message8End + "', '" + message9Start + "', '" + message9End + "', '" + message10Start + "', '" + message10End + "', '" + message11 + "', '" + message12Start + "', '" + message12End + "', '" + message13 + "', '" + message14 + "');\" onKeyDown = \"moveToOtherEditableLabel('" + tableId + "', 1, " + columnIndex + ", event);\"></input>";
	
	if(dataType == "date") {
		var inputOnClick = "onClick = \"openCalendar(this, 23, 'modifyLabel(&quote" + blankText + "&quote, this, &quote" + warning + "&quote, &quote" + ok + "&quote, &quote" + messageInterval + "&quote, &quote" + message1Start + "&quote, &quote" + message1End + "&quote, &quote" + message2 + "&quote, &quote" + message3 + "&quote, &quote" + message4 + "&quote, &quote" + message5 + "&quote, &quote" + message6 + "&quote, &quote" + message7 + "&quote, &quote" + message8Start + "&quote, &quote" + message8End + "&quote, &quote" + message9Start + "&quote, &quote" + message9End + "&quote, &quote" + message10Start + "&quote, &quote" + message10End + "&quote, &quote" + message11 + "&quote, &quote" + message12Start + "&quote, &quote" + message12End + "&quote, &quote" + message13 + "&quote, &quote" + message14 + "&quote);sendAJAXRequestForProjectField(&quoteprojectGrid&quote, &quotempp&quote, this);', '/csiebug-2.0/images', '" + warning + "', '" + ok + "', '" + messageInterval + "', '" + message1Start + "', '" + message1End + "', '" + message2 + "', '" + message3 + "', '" + message4 + "', '" + message5 + "', '" + message6 + "', '" + message7 + "', '" + message8Start + "', '" + message8End + "', '" + message9Start + "', '" + message9End + "', '" + message10Start + "', '" + message10End + "', '" + message11 + "', '" + message12Start + "', '" + message12End + "', '" + message13 + "', '" + message14 + "');\"";
		inputHTML = inputHTML.replace("></input>", inputOnClick + "></input>");
		var inputCalendar = "<div id = \"" + tableId + "_" + headerRowCount + "_" + columnIndex + "_calendar\" class = \"calendar\" style = \"position:absolute;left:0;top:0; z-index:5;display:none;\"></div>";
		
		return divHTML + inputHTML + inputCalendar;
	} else {
		return divHTML + inputHTML;
	}
}

///**
//* 下載excel
//* @param formId
//* @param tableId
//* @param filename
//* @param basePath
//* @param statusbar
//* @param columns
//* @return
//* @author csiebug
//* @version 2008/12/15
//*/
//function downloadExcelForProject(formId, tableId, filename, basePath, statusbar, columns, data) {
//	sendUpdateOutlineNumberPool(formId, tableId);
//	sendUpdateFieldPool(formId, tableId);
//	
//	var trs = document.getElementById(tableId).getElementsByTagName("tr");
//	
//	var columnFlag = false;
//	if(columns.trim() != "") {
//		columnFlag = true;
//	}
//	
//	var tableHtml = "<html>";
//	tableHtml = tableHtml + "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /></head>"
//	tableHtml = tableHtml + "<body><table>";
//	
//	for(var i = 0; i < (trs.length - statusbar); i++) {
//		var tr = trs[i];
//		var tds = tr.getElementsByTagName("td");
//		
//		tableHtml = tableHtml + "<tr>";
//		
//		for(var j = 4; j < tds.length; j++) {
//			var value = tds[j].innerHTML;
//			var div = $("div", tds[j])[0];
//			var text = $("input", tds[j])[0];
//			
//			if(div != null && text != null) {
//				value = div.innerHTML;
//			}
//			
//			if(!columnFlag) {
//				var td = tds[j];
//				if (td.getAttribute("dataType") == "number" || td.getAttribute("dataType") == "currency") {
//					tableHtml = tableHtml + "<td style=\"mso-number-format:\@\"><p align=\"right\">" + value + "</p></td>";
//				} else {
//					tableHtml = tableHtml + "<td>" + value + "</td>";
//				}
//			} else {
//				for(var k = 0; k < columns.split(",").length; k++) {
//					if(j == parseInt(columns.split(",")[k], 10)) {
//						var td = tds[j];
//						if (td.getAttribute("dataType") == "number" || td.getAttribute("dataType") == "currency") {
//							tableHtml = tableHtml + "<td style=\"mso-number-format:\@\"><p align=\"right\">" + value + "</p></td>";
//						} else {
//							tableHtml = tableHtml + "<td>" + value + "</td>";
//						}
//						break;
//					}
//				}
//			}
//		}
//		
//		tableHtml = tableHtml + "</tr>";
//	}
//	
//	tableHtml = tableHtml + "</table></body></html>";
//	
//	//grid元件加上去的資料自行濾除
//	tableHtml = replaceAll(tableHtml, "▲", "");
//	tableHtml = replaceAll(tableHtml, "▼", "");
//	
//	document.getElementById("DownloadFileName").value = filename;
//	document.getElementById("GridHTML").value = tableHtml;
//	
//	document.getElementById("downloadExcelIframe").src = basePath + "index?ActFlag=downloadExcelView";
//}
//
///**
//* 下載excel(grid中有選取的資料)
//* @param formId
//* @param tableId
//* @param filename
//* @param basePath
//* @param statusbar
//* @param columns
//* @return
//* @author csiebug
//* @version 2008/12/15
//*/
//function downloadPartialExcelForProject(formId, tableId, filename, basePath, statusbar, columns, data) {
//	sendUpdateOutlineNumberPool(formId, tableId);
//	sendUpdateFieldPool(formId, tableId);
//	
//	var trs = document.getElementById(tableId).getElementsByTagName("tr");
//	
//	var columnFlag = false;
//	if(columns.trim() != "") {
//		columnFlag = true;
//	}
//	
//	var tableHtml = "<html>";
//	tableHtml = tableHtml + "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /></head>"
//	tableHtml = tableHtml + "<body><table>";
//	
//	for(var i = 0; i < (trs.length - statusbar); i++) {
//		var tr = trs[i];
//		
//		if(tr.className == "TD_EXCEL_CONTROL" || tr.className == "TR_FOCUS") {
//			var tds = tr.getElementsByTagName("td");
//			
//			tableHtml = tableHtml + "<tr>";
//			
//			for(var j = 4; j < tds.length; j++) {
//				var value = tds[j].innerHTML;
//				var div = $("div", tds[j])[0];
//				var text = $("input", tds[j])[0];
//				
//				if(div != null && text != null) {
//					value = div.innerHTML;
//				}
//				
//				if(!columnFlag) {
//					var td = tds[j];
//					if (td.getAttribute("dataType") == "number" || td.getAttribute("dataType") == "currency") {
//						tableHtml = tableHtml + "<td style=\"mso-number-format:\@\"><p align=\"right\">" + value + "</p></td>";
//					} else {
//						tableHtml = tableHtml + "<td>" + value + "</td>";
//					}
//				} else {
//					for(var k = 0; k < columns.split(",").length; k++) {
//						if(j == parseInt(columns.split(",")[k], 10)) {
//							var td = tds[j];
//							if (td.getAttribute("dataType") == "number" || td.getAttribute("dataType") == "currency") {
//								tableHtml = tableHtml + "<td style=\"mso-number-format:\@\"><p align=\"right\">" + value + "</p></td>";
//							} else {
//								tableHtml = tableHtml + "<td>" + value + "</td>";
//							}
//							break;
//						}
//					}
//				}
//			}
//			
//			tableHtml = tableHtml + "</tr>";
//		}
//	}
//	
//	tableHtml = tableHtml + "</table></body></html>";
//	
//	//grid元件加上去的資料自行濾除
//	tableHtml = replaceAll(tableHtml, "▲", "");
//	tableHtml = replaceAll(tableHtml, "▼", "");
//	
//	document.getElementById("DownloadFileName").value = filename;
//	document.getElementById("GridHTML").value = tableHtml;
//	
//	document.getElementById("downloadExcelIframe").src = basePath + "index?ActFlag=downloadExcelView";
//}

/**
 * 下載project
 * @param formId
 * @param tableId
 * @param filename
 * @param basePath
 * @param data
 * @return
 * @author csiebug
 * @version 2010/12/21
 */
function downloadProject(tableId, filename, basePath, data, type) {
	document.getElementById("DownloadFileName").value = filename;
	document.getElementById("DownloadTableId").value = tableId;
	document.getElementById("GridHTML").value = data;
	
	document.getElementById("downloadExcelIframe").src = basePath + "index?ActFlag=download" + type.toUpperCase() + "View";
}
