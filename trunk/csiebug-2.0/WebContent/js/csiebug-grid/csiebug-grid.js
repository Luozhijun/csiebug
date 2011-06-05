//如果grid開啟highlight功能的話
//需要此變數記錄原本的class,滑鼠移開才有辦法設回來
var currentRowClass = "";

//以下變數用來記錄grid目前focus的row(用來配合shift鍵來做起迄的多選)
var rowStatus = new Array();

//以下變數用來記錄grid目前focus的column(用來配合shift鍵來做起迄的多選)
var columnStatus = new Array();

/**
 * 取得某table row目前的狀態
 * @author csiebug
 * @version 2010/11/11
 */
function getRowStatus(tableId) {
	for(var i = 0; i < rowStatus.length; i++) {
		if(rowStatus[i][0] == tableId) {
			return rowStatus[i];
		}
	}
	
	var table = new Array();
	table[0] = tableId;
	//startFocusRow
	table[1] = -1;
	//endFocusRow
	table[2] = -1;
	//originalEndFocusRow
	table[3] = -1;
	
	rowStatus[rowStatus.length] = table;
	
	return table;
}

/**
 * 修改某table row目前的狀態
 * @author csiebug
 * @version 2010/11/11
 */
function setRowStatus(tableId, index, value) {
	for(var i = 0; i < rowStatus.length; i++) {
		if(rowStatus[i][0] == tableId) {
			if(index == "startFocusRow") {
				rowStatus[i][1] = value;
			} else if(index == "endFocusRow") {
				rowStatus[i][2] = value;
			} else if(index == "originalEndFocusRow") {
				rowStatus[i][3] = value;
			}
			
			break;
		}
	}
}

/**
 * 取得某table column目前的狀態
 * @author csiebug
 * @version 2010/11/11
 */
function getColumnStatus(tableId) {
	for(var i = 0; i < columnStatus.length; i++) {
		if(columnStatus[i][0] == tableId) {
			return columnStatus[i];
		}
	}
	
	var table = new Array();
	table[0] = tableId;
	//startFocusColumn
	table[1] = -1;
	//endFocusColumn
	table[2] = -1;
	//originalEndFocusColumn
	table[3] = -1;
	//focusColumn
	table[4] = -1;
	
	columnStatus[columnStatus.length] = table;
	
	return table;
}

/**
 * 修改某table column目前的狀態
 * @author csiebug
 * @version 2010/11/11
 */
function setColumnStatus(tableId, index, value) {
	for(var i = 0; i < columnStatus.length; i++) {
		if(columnStatus[i][0] == tableId) {
			if(index == "startFocusColumn") {
				columnStatus[i][1] = value;
			} else if(index == "endFocusColumn") {
				columnStatus[i][2] = value;
			} else if(index == "originalEndFocusColumn") {
				columnStatus[i][3] = value;
			} else if(index == "focusColumn") {
				columnStatus[i][4] = value;
			}
			
			break;
		}
	}
}

/**
 * 自行決定跳的頁數
 * @param tableId
 * @param pagination
 * @param totalPages
 * @param targetControl
 * @param headerRowCount
 * @param prePage
 * @param nextPage
 * @return
 * @author csiebug
 * @version 2011/1/24
 */
function goFreePage(tableId, pagination, totalPages, targetControl, headerRowCount, prePage, nextPage) {
	if(targetControl.value.trim() != "" && totalPages >= parseInt(targetControl.value, 10)) {
		toPage(tableId, pagination, totalPages, parseInt(targetControl.value, 10), headerRowCount, prePage, nextPage);
	} else {
		targetControl.value = "";
	}
}

/**
 * grid換頁
 * @param tableId
 * @param pagination
 * @param totalPages
 * @param targetPage
 * @param headerRowCount
 * @param prePage
 * @param nextPage
 * @return
 * @author csiebug
 * @version 2009/3/13
 */
function toPage(tableId, pagination, totalPages, targetPage, headerRowCount, prePage, nextPage) {
	var trs = document.getElementById(tableId).getElementsByTagName("tr");
	var startTR = (parseInt(pagination, 10) * (parseInt(targetPage, 10) - 1)) + parseInt(headerRowCount, 10);
	var endTR = startTR + parseInt(pagination, 10) - 1;
	
	//更新grid資料
	for(var i = 1; i < trs.length; i++) {
		var tr = trs[i];
		
		if(tr.className == "TR_ODD" || tr.className == "TR_EVEN" || tr.className == "TR_FOCUS" || tr.className == "TR_HIDDEN") {
			//先全部隱藏
			tr.style.display = "none";
			
			//找到該顯示的那頁資料,顯示出來
			if(i >= startTR && i <= endTR) {
				//如果是選取的row就不改變狀態,否則要設定他是單數還是雙數列
				if(tr.className != "TR_FOCUS") {
					if((i - startTR + 1) % 2 == 1) {
						tr.className = "TR_ODD";
					} else {
						tr.className = "TR_EVEN";
					}
				}
				tr.style.display = "";
			}
		}
	}
	
	//更新status bar
	var prepageLink = document.getElementById("prepage_" + tableId);
	var nextpageLink = document.getElementById("nextpage_" + tableId);
	
	if(parseInt(totalPages, 10) == 1) {
		prepageLink.className = "disabled";
		nextpageLink.className = "disabled";
		
		prepageLink.innerHTML = prePage;
		nextpageLink.innerHTML = nextPage;
	} else {
		if(parseInt(targetPage, 10) == 1) {
			prepageLink.className = "disabled";
			nextpageLink.className = "";
			
			prepageLink.innerHTML = prePage;
			nextpageLink.innerHTML = "<a href=\"JavaScript:toPage('" + tableId + "', '" + pagination + "', '" + totalPages + "', '" + (parseInt(targetPage, 10) + 1) + "', '" + headerRowCount + "', '" + prePage + "', '" + nextPage + "');\">" + nextPage + "</a>";
		} else if(parseInt(targetPage, 10) == parseInt(totalPages, 10)) {
			prepageLink.className = "";
			nextpageLink.className = "disabled";
			
			prepageLink.innerHTML = "<a href=\"JavaScript:toPage('" + tableId + "', '" + pagination + "', '" + totalPages + "', '" + (parseInt(targetPage, 10) - 1) + "', '" + headerRowCount + "', '" + prePage + "', '" + nextPage + "');\">" + prePage + "</a>";
			nextpageLink.innerHTML = nextPage;
		} else {
			prepageLink.className = "";
			nextpageLink.className = "";
			
			prepageLink.innerHTML = "<a href=\"JavaScript:toPage('" + tableId + "', '" + pagination + "', '" + totalPages + "', '" + (parseInt(targetPage, 10) - 1) + "', '" + headerRowCount + "', '" + prePage + "', '" + nextPage + "');\">" + prePage + "</a>";
			nextpageLink.innerHTML = "<a href=\"JavaScript:toPage('" + tableId + "', '" + pagination + "', '" + totalPages + "', '" + (parseInt(targetPage, 10) + 1) + "', '" + headerRowCount + "', '" + prePage + "', '" + nextPage + "');\">" + nextPage + "</a>";
		}
	}
	
	var page1Link = document.getElementById("page1_" + tableId);
	var page2Link = document.getElementById("page2_" + tableId);
	var page3Link = document.getElementById("page3_" + tableId);
	
	if(parseInt(targetPage, 10) - 1 > 0) {
		if(parseInt(targetPage, 10) == parseInt(totalPages, 10)) {
			if(parseInt(targetPage, 10) >= 3) {
				page1Link.className = "";
				page2Link.className = "";
				page3Link.className = "current";
				
				page1Link.innerHTML = "<a href=\"JavaScript:toPage('" + tableId + "', '" + pagination + "', '" + totalPages + "', '" + (parseInt(targetPage, 10) - 2) + "', '" + headerRowCount + "', '" + prePage + "', '" + nextPage + "');\">" + (parseInt(targetPage, 10) - 2) + "</a>";
				page2Link.innerHTML = "<a href=\"JavaScript:toPage('" + tableId + "', '" + pagination + "', '" + totalPages + "', '" + (parseInt(targetPage, 10) - 1) + "', '" + headerRowCount + "', '" + prePage + "', '" + nextPage + "');\">" + (parseInt(targetPage, 10) - 1) + "</a>";
				page3Link.innerHTML = "[" + targetPage + "]";
			} else {
				page1Link.className = "";
				page2Link.className = "current";
				
				page1Link.innerHTML = "<a href=\"JavaScript:toPage('" + tableId + "', '" + pagination + "', '" + totalPages + "', '" + (parseInt(targetPage, 10) - 1) + "', '" + headerRowCount + "', '" + prePage + "', '" + nextPage + "');\">" + (parseInt(targetPage, 10) - 1) + "</a>";
				page2Link.innerHTML = "[" + targetPage + "]";
			}
		} else {
			page1Link.className = "";
			page2Link.className = "current";
			page3Link.className = "";
			
			page1Link.innerHTML = "<a href=\"JavaScript:toPage('" + tableId + "', '" + pagination + "', '" + totalPages + "', '" + (parseInt(targetPage, 10) - 1) + "', '" + headerRowCount + "', '" + prePage + "', '" + nextPage + "');\">" + (parseInt(targetPage, 10) - 1) + "</a>";
			page2Link.innerHTML = "[" + targetPage + "]";
			page3Link.innerHTML = "<a href=\"JavaScript:toPage('" + tableId + "', '" + pagination + "', '" + totalPages + "', '" + (parseInt(targetPage, 10) + 1) + "', '" + headerRowCount + "', '" + prePage + "', '" + nextPage + "');\">" + (parseInt(targetPage, 10) + 1) + "</a>";
		}
	} else {
		page1Link.className = "current";
		page1Link.innerHTML = "[" + targetPage + "]";
		
		if(parseInt(targetPage, 10) + 1 <= parseInt(totalPages, 10)) {
			page2Link.className = "";
			page2Link.innerHTML = "<a href=\"JavaScript:toPage('" + tableId + "', '" + pagination + "', '" + totalPages + "', '" + (parseInt(targetPage, 10) + 1) + "', '" + headerRowCount + "', '" + prePage + "', '" + nextPage + "');\">" + (parseInt(targetPage, 10) + 1) + "</a>";
		}
		
		if(parseInt(targetPage, 10) + 2 <= parseInt(totalPages, 10)) {
			page3Link.className = "";
			page3Link.innerHTML = "<a href=\"JavaScript:toPage('" + tableId + "', '" + pagination + "', '" + totalPages + "', '" + (parseInt(targetPage, 10) + 2) + "', '" + headerRowCount + "', '" + prePage + "', '" + nextPage + "');\">" + (parseInt(targetPage, 10) + 2) + "</a>";
		}
	}
	
}

/**
 * 顯示全部grid資料
 * @param tableId
 * @return
 * @author csiebug
 * @version 2008/12/4
 */
function showAllData(tableId) {
	var trs = document.getElementById(tableId).getElementsByTagName("tr");
	
	for(var i = 0; i < trs.length; i++) {
		var tr = trs[i];
		
		if(tr.className == "TR_ODD" || tr.className == "TR_EVEN" || tr.className == "TR_FOCUS" || tr.className == "TR_HIDDEN") {
			if(tr.style.display == "none") {
				tr.style.display = "";
			}
		}
	}
}

/**
 * grid的全選
 * @param tableId
 * @param rowIndex 全選的checkbox所處的row
 * @param columnIndex 全選的checkbox所處的column
 * @return
 * @author csiebug
 * @version 2008/12/9
 */
function selAllData(tableId, rowIndex, columnIndex) {
	var trs = document.getElementById(tableId).getElementsByTagName("tr");
	var selAll = trs[rowIndex].getElementsByTagName("td")[columnIndex].getElementsByTagName("input")[0];
	
	var firstRowIndex = 0;
				
	for(var i = rowIndex + 1; i < trs.length; i++) {
		var tr = trs[i];
		var chk = tr.getElementsByTagName("td")[columnIndex].getElementsByTagName("input");
						
		if(chk[0] != null) {
			if(chk[0].disabled != true) {
				if(selAll.checked == true) {
					chk[0].checked = true;
					tr.className = "TR_FOCUS";
				} else {
					chk[0].checked = false;
					
					//取消全選,要把本來單數或雙數列設回來
					if(tr.style.display != "none") {
						if(firstRowIndex == 0) {
							firstRowIndex = i;
						}
						
						if((i - firstRowIndex + 1) % 2 == 1) {
							tr.className = "TR_ODD";
						} else {
							tr.className = "TR_EVEN";
						}
					} else {
						tr.className = "TR_HIDDEN";
					}
				}
			}
		}
	}
}

/**
 * grid全不選取
 * @param tableId
 * @param headerRowCount
 * @return
 * @author csiebug
 * @version 2009/3/25
 */
function unSelAllDataForClick(tableId, headerRowCount) {
	var trs = document.getElementById(tableId).getElementsByTagName("tr");
	
	//取消選取,要把本來單數或雙數列設回來
	for(var i = 0; i < trs.length; i++) {
		var tr = trs[i];
		
		if(tr.className == "TR_FOCUS") {
			if((i - headerRowCount + 1) % 2 == 1) {
				tr.className = "TR_ODD";
			} else {
				tr.className = "TR_EVEN";
			}
		}
		
	}
}

/**
 * grid單選(使用checkbox),改變style
 * @param tableId
 * @param checkbox
 * @param rowIndex
 * @param pagination
 * @param headerRowCount
 * @param e
 * @return
 * @author csiebug
 * @version 2008/12/17
 */
function selOneDataForCheckBox(tableId, checkbox, rowIndex, pagination, headerRowCount, e) {
	var table = getRowStatus(tableId);
	var startFocusRow = table[1];
	var endFocusRow = table[2];
	var originalEndFocusRow = table[3];
	
	//如果使用者有合併shift按鍵,則啟用連續範圍選擇
	if(e.shiftKey) {
		if(endFocusRow != -1) {
			setRowStatus(tableId, "originalEndFocusRow", endFocusRow);
		}
		setRowStatus(tableId, "endFocusRow", rowIndex);
		unSelDataFromTo(tableId, headerRowCount);
		selDataFromTo(tableId);
	} else {
		setRowStatus(tableId, "startFocusRow", rowIndex);
		setRowStatus(tableId, "endFocusRow", -1);
		setRowStatus(tableId, "originalEndFocusRow", -1);
		
		var trs = document.getElementById(tableId).getElementsByTagName("tr");
		var tr = trs[rowIndex];
		
		if(checkbox.disabled != true) {
			if(checkbox.checked == true) {
				tr.className = "TR_FOCUS";
			} else {
				//把全選按鈕取消
				var selAll = document.getElementById("sel" + tableId + "All");
				
				if(selAll != null && selAll.checked == true) {
					selAll.checked = false;
				}
				
				if(pagination <= 0) {
					if((rowIndex - headerRowCount + 1) % 2 == 1) {
						tr.className = "TR_ODD";
					} else {
						tr.className = "TR_EVEN";
					}
				} else {
					if(((rowIndex - headerRowCount + 1) % pagination == 0) && (pagination % 2 == 1)) {
						tr.className = "TR_ODD";
					} else {
						if(((rowIndex - headerRowCount + 1) % pagination) % 2 == 1) {
							tr.className = "TR_ODD";
						} else {
							tr.className = "TR_EVEN";
						}
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
function selOneDataForClickMulti(tableId, rowIndex, pagination, headerRowCount) {
	var trs = document.getElementById(tableId).getElementsByTagName("tr");
	var tr = trs[rowIndex];
	
	if(tr.className == "TR_ODD" || tr.className == "TR_EVEN") {
		tr.className = "TR_FOCUS";
	} else {
		if(pagination <= 0) {
			if((rowIndex - headerRowCount + 1) % 2 == 1) {
				tr.className = "TR_ODD";
			} else {
				tr.className = "TR_EVEN";
			}
		} else {
			if(((rowIndex - headerRowCount + 1) % pagination == 0) && (pagination % 2 == 1)) {
				tr.className = "TR_ODD";
			} else {
				if(((rowIndex - headerRowCount + 1) % pagination) % 2 == 1) {
					tr.className = "TR_ODD";
				} else {
					tr.className = "TR_EVEN";
				}
			}
		}
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
function selOneDataForClick(tableId, rowIndex, pagination, headerRowCount, e) {
	var table = getRowStatus(tableId);
	var startFocusRow = table[1];
	var endFocusRow = table[2];
	var originalEndFocusRow = table[3];
	
	clearAllSelTD(tableId);
	selOneDataForClickMulti(tableId, rowIndex, pagination, headerRowCount);
	
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
		for(var i = 0; i < trs.length; i++) {
			var tr2 = trs[i];
			
			if(tr2.className == "TR_FOCUS" && i != rowIndex) {
				if((i - headerRowCount + 1) % 2 == 1) {
					tr2.className = "TR_ODD";
				} else {
					tr2.className = "TR_EVEN";
				}
			}
			
		}
	}
}

/**
 * 選取startFocusRow到endFocusRow範圍
 * @author csiebug
 * @version 2010/11/5
 */
function selDataFromTo(tableId) {
	var table = getRowStatus(tableId);
	var startFocusRow = table[1];
	var endFocusRow = table[2];
	var originalEndFocusRow = table[3];
	
	var trs = document.getElementById(tableId).getElementsByTagName("tr");
	var s = -1;
	var e = -1;
	
	if(startFocusRow > endFocusRow) {
		s = endFocusRow;
		e = startFocusRow;
	} else {
		s = startFocusRow;
		e = endFocusRow;
	}
	
	for(var i = 0; i < trs.length; i++) {
		if(i >= s && i <= e) {
			var tr = trs[i];
			
			if(tr.style.display != "none") {
				tr.className = "TR_FOCUS";
				
				var checkbox = getTRCheckbox(tableId, tr);
				if(checkbox != null) {
					checkbox.checked = true;
				}
			}
		} else if(i > e) {
			break;
		}
	}
}

/**
 * 取消選取startFocusRow到originalEndFocusRow範圍
 * @author csiebug
 * @version 2010/11/5
 */
function unSelDataFromTo(tableId, headerRowCount) {
	var table = getRowStatus(tableId);
	var startFocusRow = table[1];
	var endFocusRow = table[2];
	var originalEndFocusRow = table[3];
	
	if(originalEndFocusRow != -1) {
		var trs = document.getElementById(tableId).getElementsByTagName("tr");
		var s = -1;
		var e = -1;
		
		if(startFocusRow > originalEndFocusRow) {
			s = originalEndFocusRow;
			e = startFocusRow;
		} else {
			s = startFocusRow;
			e = originalEndFocusRow;
		}
		
		for(var i = 0; i < trs.length; i++) {
			if(i >= s && i <= e) {
				var tr = trs[i];
				
				if(tr.className == "TR_FOCUS") {
					if((i - headerRowCount + 1) % 2 == 1) {
						tr.className = "TR_ODD";
					} else {
						tr.className = "TR_EVEN";
					}
					
					var checkbox = getTRCheckbox(tableId, tr);
					if(checkbox != null) {
						checkbox.checked = false;
					}
				}
			} else if(i > e) {
				break;
			}
		}
	}
}

/**
 * 取得checkbox
 * @param tableId
 * @param tr
 * @return
 * @author csiebug
 * @version 2010/11/5
 */
function getTRCheckbox(tableId, tr) {
	var inputs = tr.getElementsByTagName("input");
	
	for(var i = 0; i < inputs.length; i++) {
		var obj = inputs[i];
		
		if((obj.getAttribute("type") == "checkbox") && (obj.id.startsWith("sel" + tableId))) {
			return obj;
		}
	}
	
	return null;
}

/**
 * 取得資料列的TR array
 * @param tableId
 * @return
 * @author csiebug
 * @version 2009/6/18
 */
function getDataTR(tableId) {
	var trs = document.getElementById(tableId).getElementsByTagName("tr");
	
	var trlength = 0;
	for(var i = 0; i < trs.length; i++) {
		var tr = trs[i];
		if(tr.className != null && tr.className != "") {
			if(tr.className.length > 3) {
				if(tr.className.substring(0, 3) == "TR_") {
					trlength = trlength + 1;
				}
			}
		}
	}
	
	var aryTR = new Array(trlength);
	
	var i = 0;
	var j = 0;
	while((i < aryTR.length) && (j < trs.length)) {
		var tr = trs[j];
		if(tr.className != null && tr.className != "") {
			if(tr.className.length > 3) {
				if(tr.className.substring(0, 3) == "TR_") {
					aryTR[i] = tr;
					i = i + 1;
				}
			}
		}
		
		j = j + 1;
	}
	
	return aryTR;
}

/**
 * 選取column
 * @author csiebug
 * @version 2010/11/8
 */
function selColumn(tableId, headerRowCount, columnIndex, e) {
	var table = getColumnStatus(tableId);
	var startFocusColumn = table[1];
	var endFocusColumn = table[2];
	var originalEndFocusColumn = table[3];
	var focusColumn = table[4];
	
	var trs = document.getElementById(tableId).getElementsByTagName("tr");
	
	if(focusColumn == -1) {
		clearAllSelTDAndTR(tableId, headerRowCount);
		
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
			clearAllSelTDAndTR(tableId, headerRowCount);
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
				
				clearAllSelTDAndTR(tableId, headerRowCount);
				
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
 * 選取startFocusColumn到endFocusColumn範圍
 * @author csiebug
 * @version 2010/11/5
 */
function selColumnFromTo(tableId, headerRowCount) {
	var table = getColumnStatus(tableId);
	var startFocusColumn = table[1];
	var endFocusColumn = table[2];
	var originalEndFocusColumn = table[3];
	var focusColumn = table[4];
	
	var trs = document.getElementById(tableId).getElementsByTagName("tr");
	var s = -1;
	var e = -1;
	
	if(startFocusColumn > endFocusColumn) {
		s = endFocusColumn;
		e = startFocusColumn;
	} else {
		s = startFocusColumn;
		e = endFocusColumn;
	}
	
	for(var i = headerRowCount; i < trs.length; i++) {
		var tds = trs[i].getElementsByTagName("td");
		
		for(var j = 0; j < tds.length; j++) {
			if(j >= s && j <= e) {
				var td = tds[j];
				td.className = "TD_FOCUS";
			} else if(j > e) {
				break;
			}
		}
	}
}

/**
 * 取消選取startFocusColumn到originalEndFocusColumn範圍
 * @author csiebug
 * @version 2010/11/5
 */
function unSelColumnFromTo(tableId) {
	var table = getColumnStatus(tableId);
	var startFocusColumn = table[1];
	var endFocusColumn = table[2];
	var originalEndFocusColumn = table[3];
	var focusColumn = table[4];
	
	if(originalEndFocusColumn != -1) {
		var trs = document.getElementById(tableId).getElementsByTagName("tr");
		var s = -1;
		var e = -1;
		
		if(startFocusColumn > originalEndFocusColumn) {
			s = originalEndFocusColumn;
			e = startFocusColumn;
		} else {
			s = startFocusColumn;
			e = originalEndFocusColumn;
		}
		
		for(var i = 0; i < trs.length; i++) {
			var tds = trs[i].getElementsByTagName("td");
			
			for(var j = 0; j < tds.length; j++) {
				if(j >= s && j <= e) {
					var td = tds[j];
					
					if(td.className == "TD_FOCUS") {
						td.className = "TD";
					}
				} else if(j > e) {
					break;
				}
			}
		}
	}
}

/**
 * 選取startFocusRow到endFocusRow範圍的TD
 * @author csiebug
 * @version 2010/11/5
 */
function selColumnDataFromTo(tableId, columnIndex) {
	var table = getRowStatus(tableId);
	var startFocusRow = table[1];
	var endFocusRow = table[2];
	var originalEndFocusRow = table[3];
	
	var trs = document.getElementById(tableId).getElementsByTagName("tr");
	var s = -1;
	var e = -1;
	
	if(startFocusRow > endFocusRow) {
		s = endFocusRow;
		e = startFocusRow;
	} else {
		s = startFocusRow;
		e = endFocusRow;
	}
	
	for(var i = 0; i < trs.length; i++) {
		if(i >= s && i <= e) {
			var tr = trs[i];
			var td = tr.getElementsByTagName("td")[columnIndex];
			
			td.className = "TD_FOCUS";
		} else if(i > e) {
			break;
		}
	}
}

/**
 * 清除所有選取的TD
 * @author csiebug
 * @version 2010/11/9
 */
function clearAllSelTD(tableId) {
	var tds = document.getElementById(tableId).getElementsByTagName("td");
	for(var i = 0; i < tds.length; i++) {
		if(tds[i].className != "TD_EXCEL_CONTROL") {
			tds[i].className = "TD";
		}
	}
	
	focusColumn = -1;
}

/**
 * 清除所有選取的TD和TR
 * @author csiebug
 * @version 2010/11/9
 */
function clearAllSelTDAndTR(tableId, headerRowCount) {
	var tds = document.getElementById(tableId).getElementsByTagName("td");
	for(var i = 0; i < tds.length; i++) {
		if(tds[i].className != "TD_EXCEL_CONTROL") {
			tds[i].className = "TD";
		}
	}
	
	focusColumn = -1;
	
	var trs = document.getElementById(tableId).getElementsByTagName("tr");
	for(var i = headerRowCount ; i < trs.length; i++) {
		if((i - headerRowCount + 1) % 2 == 1) {
			trs[i].className = "TR_ODD";
		} else {
			trs[i].className = "TR_EVEN";
		}
	}
}

/**
 * 選取grid的cell
 * @author csiebug
 * @version 2010/11/5
 */
function selTD(tableId, td, rowIndex, columnIndex, headerRowCount, e) {
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
		
		selColumnDataFromTo(tableId, columnIndex);
		
		setRowStatus(tableId, "startFocusRow", rowIndex);
		setRowStatus(tableId, "endFocusRow", -1);
		setRowStatus(tableId, "originalEndFocusRow", -1);
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
		
		clearAllSelTDAndTR(tableId, headerRowCount);
		
		if(originalClassName == "TD") {
			td.className = "TD_FOCUS";
		} else {
			td.className = "TD";
		}
	}
}

/**
 * 選取startFocusRow到endFocusRow範圍的TD(跳出confirm確認是否使用copy paste功能)
 * @author csiebug
 * @version 2010/11/5
 */
function selColumnDataFromToForCopy(tableId, rowIndex, columnIndex, title, message, ok, cancel) {
	var table = getRowStatus(tableId);
	var startFocusRow = table[1];
	var endFocusRow = table[2];
	var originalEndFocusRow = table[3];
	
	var trs = document.getElementById(tableId).getElementsByTagName("tr");
	var copyLabel = trs[startFocusRow].getElementsByTagName("td")[columnIndex].getElementsByTagName("div")[0];
	
	if(copyLabel != null) {
		openConfirmDialog(null, title, message, ok, cancel, "selColumnDataFromToForCopy2('" + tableId + "', " + rowIndex + ", " + columnIndex + ", true)", "selColumnDataFromToForCopy2('" + tableId + "', " + rowIndex + ", " + columnIndex + ", false)", null);
	} else {
		selColumnDataFromToForCopy2(tableId, rowIndex, columnIndex, true);
	}
}

/**
 * 選取startFocusRow到endFocusRow範圍的TD(連續選取則附加copy paste value的功能)
 * @author csiebug
 * @version 2010/11/5
 */
function selColumnDataFromToForCopy2(tableId, rowIndex, columnIndex, confirmFlag) {
	var table = getRowStatus(tableId);
	var startFocusRow = table[1];
	var endFocusRow = table[2];
	var originalEndFocusRow = table[3];
	
	var trs = document.getElementById(tableId).getElementsByTagName("tr");
	var s = -1;
	var e = -1;
	var copyLabel = trs[startFocusRow].getElementsByTagName("td")[columnIndex].getElementsByTagName("div")[0];
	var copyFlag = false;
	var copyValue = "";
	
	if(copyLabel != null) {
		if(confirmFlag) {
			copyFlag = true;
			copyValue = copyLabel.innerHTML;
		}
	}
	
	if(startFocusRow > endFocusRow) {
		s = endFocusRow;
		e = startFocusRow;
	} else {
		s = startFocusRow;
		e = endFocusRow;
	}
	
	for(var i = 0; i < trs.length; i++) {
		if(i >= s && i <= e) {
			var tr = trs[i];
			
			if(tr.style.display != "none") {
				var td = tr.getElementsByTagName("td")[columnIndex];
				
				td.className = "TD_FOCUS";
				
				if(copyFlag) {
					var pasteLabel = td.getElementsByTagName("div")[0];
					pasteLabel.innerHTML = copyValue;
					var pasteInputText = td.getElementsByTagName("input")[0];
					pasteInputText.value = copyValue;
				}
			}
		} else if(i > e) {
			break;
		}
	}
	
	setRowStatus(tableId, "startFocusRow", rowIndex);
	setRowStatus(tableId, "endFocusRow", -1);
	setRowStatus(tableId, "originalEndFocusRow", -1);
}

/**
 * 選取grid的cell
 * @author csiebug
 * @version 2010/11/5
 */
function selTDForCopy(tableId, td, rowIndex, columnIndex, headerRowCount, e, title, message, ok, cancel) {
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
		
		clearAllSelTDAndTR(tableId, headerRowCount);
		
		if(originalClassName == "TD") {
			td.className = "TD_FOCUS";
		} else {
			td.className = "TD";
		}
	}
	
	setColumnStatus(tableId, "startFocusColumn", columnIndex);
}

/**
 * 取得某筆資料列的TD array
 * @param tr
 * @return
 * @author csiebug
 * @version 2009/6/18
 */
function getDataTD(tr) {
	var tds = tr.getElementsByTagName("td");
	
	var tdlength = tds.length - notGridTDCount(tr);

	var aryTD = new Array(tdlength);
	
	var i = 0;
	var j = 0;
	while((j < tds.length) && (i < tdlength)) {
		aryTD[i] = tds[j];
		
		i = i + 1;
		j = j + 1 + tds[j].getElementsByTagName("td").length;
	}
	
	return aryTD;
}

/**
 * 取得非資料列的TD數目
 * 為了要扣除grid資料本身也是html table的情況
 * 如果用getElementsByTagName，會抓錯資料
 * @param tr
 * @return
 * @author csiebug
 * @version 2009/6/18
 */
function notGridTDCount(tr) {
	var count = 0;
	
	var tds = tr.getElementsByTagName("td");
	
	for(var i = 0; i < tds.length; i++) {
		count = count + tds[i].getElementsByTagName("td").length;
	}
	
	return count;
}

/**
 * 把grid上的控制項記錄起來
 * @param tableId
 * @return
 * @author csiebug
 * @version 2009/6/18
 */
function getAllGridControl(tableId) {
	var inputs = document.getElementById(tableId).getElementsByTagName("input");
	var selects = document.getElementById(tableId).getElementsByTagName("select");
	
	var aryControl = new Array(inputs.length + selects.length);
	
	for(var i = 0; i < aryControl.length; i++) {
		aryControl[i] = new Array(2);
	}
	
	for(var i = 0; i < inputs.length; i++) {
		var obj = inputs[i];
		
		if(obj.getAttribute("type") == "checkbox") {
			aryControl[i][0] = obj.id;
			if(obj.checked == true) {
				aryControl[i][1] = "checked";
			} else {
				aryControl[i][1] = "unchecked";
			}
		} else if(obj.getAttribute("type") == "radio") {
			aryControl[i][0] = obj.id;
			if(obj.checked == true) {
				aryControl[i][1] = "checked";
			} else {
				aryControl[i][1] = "unchecked";
			}
		} else {
			aryControl[i][0] = obj.id;
			aryControl[i][1] = obj.value;
		}
	}
	
	for(var i = 0; i < selects.length; i++) {
		var obj = selects[i];
		
		aryControl[i + inputs.length][0] = obj.id;
		aryControl[i + inputs.length][1] = obj.value;
	}
	
	return aryControl;
}

/**
 * grid排序
 * @param tableId
 * @param rowIndex
 * @param columnIndex
 * @param isCurrency
 * @return
 * @author csiebug
 * @version 2009/6/18
 */
function sortGrid(tableId, rowIndex, columnIndex, isCurrency) {
	var aryTR = getDataTR(tableId);
	
	//使用innerHTML來做排序，grid上面的控制項上的值，沒有辦法跟著排序帶上來
	//所以要先記住所有控制項的值，排完序之後要帶回來
	var aryControl = getAllGridControl(tableId);
	
	if(aryTR.length > 0) {
		var tmpTable = new Array(aryTR.length);
		
		var trs = document.getElementById(tableId).getElementsByTagName("tr");
		var tds = trs[rowIndex].getElementsByTagName("td");
		var sortTitle = tds[columnIndex];
		var sortFlag = "";
		
		for(var i = 0; i < tds.length; i++) {
			var td = tds[i];
			
			if(i != columnIndex) {
//				if(td.innerHTML.indexOf("▼") != -1) {
//					td.innerHTML = td.innerHTML.replace("▼", "◆");
//					break;
//				} else if(td.innerHTML.indexOf("▲") != -1) {
//					td.innerHTML = td.innerHTML.replace("▲", "◆");
//					break;
//				}
				if(td.innerHTML.indexOf("icon-d-green") != -1) {
					td.innerHTML = td.innerHTML.replace("icon-d-green", "icon-n");
					break;
				} else if(td.innerHTML.indexOf("icon-a-green") != -1) {
					td.innerHTML = td.innerHTML.replace("icon-a-green", "icon-n");
					break;
				}
			}
		}
		
//		if(sortTitle.innerHTML.indexOf("▼") != -1) {
//			sortFlag = "desc";
//		}
		if(sortTitle.innerHTML.indexOf("icon-d-green") != -1) {
			sortFlag = "desc";
		}
		
		//改變title的箭頭,以指示使用者按下標頭以後是遞增還是遞減排序
		if(sortFlag == "desc") {
			//sortTitle.innerHTML = replaceAll(sortTitle.innerHTML, "▼", "▲");
//			sortTitle.innerHTML = sortTitle.innerHTML.replace("▼", "▲").replace("◆", "▲");
			sortTitle.innerHTML = sortTitle.innerHTML.replace("icon-d-green", "icon-a-green").replace("icon-n", "icon-a-green");
		} else {
			//sortTitle.innerHTML = replaceAll(sortTitle.innerHTML, "▲", "▼");
//			sortTitle.innerHTML = sortTitle.innerHTML.replace("▲", "▼").replace("◆", "▼");
			sortTitle.innerHTML = sortTitle.innerHTML.replace("icon-a-green", "icon-d-green").replace("icon-n", "icon-d-green");
		}
		
		//取出整個grid資料
		for(var i = 0; i < aryTR.length; i++) {
			var tr = aryTR[i];
			var aryTD = getDataTD(tr);
			tmpTable[i] = new Array(aryTD.length + 1);
			
			for(var j = 0; j < aryTD.length; j++) {
				tmpTable[i][j] = aryTD[j].innerHTML;
			}
			tmpTable[i][aryTD.length] = tr.className;
		}
		
		//開始排序
		if(sortFlag == "desc") {
			var sortByFieldDesc = function(ary1, ary2) {
				if(isCurrency == "true") {
					//if(parseInt(replaceAll(ary1[columnIndex], ",", ""), 10) > parseInt(replaceAll(ary2[columnIndex], ",", ""), 10)) {
					if(compareCurrency(ary1[columnIndex], ary2[columnIndex])) {
						return 1;
					//} else if(parseInt(replaceAll(ary1[columnIndex], ",", ""), 10) == parseInt(replaceAll(ary2[columnIndex], ",", ""), 10)) {
					} else if(ary1[columnIndex] == ary2[columnIndex]) {
						return 0;
					} else {
						return -1;
					}
				} else {
					if(ary1[columnIndex] > ary2[columnIndex]) {
						return 1;
					} else if(ary1[columnIndex] == ary2[columnIndex]) {
						return 0;
					} else {
						return -1;
					}
				}
			}
			
			tmpTable = tmpTable.sort(sortByFieldDesc);
				
			sortFlag = "";
		} else {
			var sortByField = function(ary1, ary2) {
				if(isCurrency == "true") {
					//if(parseInt(replaceAll(ary1[columnIndex], ",", ""), 10) < parseInt(replaceAll(ary2[columnIndex], ",", ""), 10)) {
					if(compareCurrency(ary2[columnIndex], ary1[columnIndex])) {
						return 1;
					//} else if(parseInt(replaceAll(ary1[columnIndex], ",", ""), 10) == parseInt(replaceAll(ary2[columnIndex], ",", ""), 10)) {
					} else if(ary1[columnIndex] == ary2[columnIndex]) {
						return 0;
					} else {
						return -1;
					}
				} else {
					if(ary1[columnIndex] < ary2[columnIndex]) {
						return 1;
					} else if(ary1[columnIndex] == ary2[columnIndex]) {
						return 0;
					} else {
						return -1;
					}
				}
			}
			
			tmpTable = tmpTable.sort(sortByField);
			
			sortFlag = "desc";
		}
		
		unSelAllDataForClick(tableId, rowIndex + 1);
		
		//將排完序的結果寫回grid
		for(var i = 0; i < aryTR.length; i++) {
			var tr = aryTR[i];
			var aryTD = getDataTD(tr);
			
			for(var j = 0; j < aryTD.length; j++) {
				var td = aryTD[j];
				
				//選取的js function,其中第三個參數是行數,排序後行數會重排,所以需要將參數換成新的
				if(tmpTable[i][j].indexOf("selOneDataForCheckBox(") != -1) {
					var strRegex = tmpTable[i][j];
					strRegex = strRegex.substring(strRegex.indexOf("selOneDataForCheckBox("), strRegex.length);
					strRegex = strRegex.substring(0, strRegex.indexOf(");"));
					
					var strReplacement;
					for(var k = 0; k < strRegex.split(",").length; k++) {
						if(k != 0) {
							if(k == 2) {
								strReplacement = strReplacement + ", " + (i + rowIndex + 1);
							} else {
								strReplacement = strReplacement + "," + strRegex.split(",")[k];
							}
						} else {
							strReplacement = strRegex.split(",")[0];
						}
					}
					
					//td.innerHTML = replaceAll(tmpTable[i][j], strRegex, strReplacement);
					td.innerHTML = tmpTable[i][j].replace(strRegex, strReplacement);
				} else {
					td.innerHTML = tmpTable[i][j];
				}
			}
			
			if(tmpTable[i][aryTD.length] == "TR_FOCUS") {
				tr.className = "TR_FOCUS";
			}
		}
		
		//innerHTML排完序，把控制項的值帶回
		var newinputs = document.getElementById(tableId).getElementsByTagName("input");
		var newselects = document.getElementById(tableId).getElementsByTagName("select");
		
		for(var i = 0; i < newinputs.length; i++) {
			for(var j = 0; j < aryControl.length; j++) {
				var obj = newinputs[i];
				
				if(obj.id == aryControl[j][0]) {
					if(obj.getAttribute("type") == "checkbox") {
						if(aryControl[j][1] == "checked") {
							obj.checked = true;
						} else {
							obj.checked = false;
						}
					} else if(obj.getAttribute("type") == "radio") {
						if(aryControl[j][1] == "checked") {
							obj.checked = true;
						} else {
							obj.checked = false;
						}
					} else {
						obj.value = aryControl[j][1];
					}
				}
			}
		}
		
		for(var i = 0; i < newselects.length; i++) {
			for(var j = 0; j < aryControl.length; j++) {
				var obj = newselects[i];
				
				if(obj.id == aryControl[j][0]) {
					obj.value = aryControl[j][1];
				}
			}
		}
	}
}

///**
// * 下載excel
// * @param formId
// * @param tableId
// * @param filename
// * @param basePath
// * @param statusbar
// * @param columns
// * @return
// * @author csiebug
// * @version 2008/12/15
// */
//function downloadExcel(formId, tableId, filename, basePath, statusbar, columns) {
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
//		for(var j = 0; j < tds.length; j++) {
//			if(!columnFlag) {
//				var td = tds[j];
//				if (td.getAttribute("dataType") == "number" || td.getAttribute("dataType") == "currency") {
//					tableHtml = tableHtml + "<td style=\"mso-number-format:\@\"><p align=\"right\">" + td.innerHTML + "</p></td>";
//				} else {
//					//一律加上單引號,避免有0開頭的資料被excel自作聰明的去掉
//					tableHtml = tableHtml + "<td>'" + td.innerHTML + "</td>";
//				}
//			} else {
//				for(var k = 0; k < columns.split(",").length; k++) {
//					if(j == parseInt(columns.split(",")[k], 10)) {
//						var td = tds[j];
//						if (td.getAttribute("dataType") == "number" || td.getAttribute("dataType") == "currency") {
//							tableHtml = tableHtml + "<td style=\"mso-number-format:\@\"><p align=\"right\">" + td.innerHTML + "</p></td>";
//						} else {
//							//一律加上單引號,避免有0開頭的資料被excel自作聰明的去掉
//							tableHtml = tableHtml + "<td>'" + td.innerHTML + "</td>";
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
// * 下載excel(grid中有選取的資料)
// * @param formId
// * @param tableId
// * @param filename
// * @param basePath
// * @param statusbar
// * @param columns
// * @return
// * @author csiebug
// * @version 2008/12/15
// */
//function downloadPartialExcel(formId, tableId, filename, basePath, statusbar, columns) {
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
//		if(tr.className == "TableHeader" || tr.className == "TR_FOCUS") {
//			var tds = tr.getElementsByTagName("td");
//			
//			tableHtml = tableHtml + "<tr>";
//			
//			for(var j = 0; j < tds.length; j++) {
//				if(!columnFlag) {
//					var td = tds[j];
//					if (td.getAttribute("dataType") == "number" || td.getAttribute("dataType") == "currency") {
//						tableHtml = tableHtml + "<td style=\"mso-number-format:\@\"><p align=\"right\">" + td.innerHTML + "</p></td>";
//					} else {
//						//一律加上單引號,避免有0開頭的資料被excel自作聰明的去掉
//						tableHtml = tableHtml + "<td>'" + td.innerHTML + "</td>";
//					}
//				} else {
//					for(var k = 0; k < columns.split(",").length; k++) {
//						if(j == parseInt(columns.split(",")[k], 10)) {
//							var td = tds[j];
//							if (td.getAttribute("dataType") == "number" || td.getAttribute("dataType") == "currency") {
//								tableHtml = tableHtml + "<td style=\"mso-number-format:\@\"><p align=\"right\">" + td.innerHTML + "</p></td>";
//							} else {
//								//一律加上單引號,避免有0開頭的資料被excel自作聰明的去掉
//								tableHtml = tableHtml + "<td>'" + td.innerHTML + "</td>";
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
 * 下載csv
 * @param formId
 * @param tableId
 * @param filename
 * @param basePath
 * @param statusbar
 * @param columns
 * @param data
 * @return
 * @author csiebug
 * @version 2010/12/21
 */
function downloadCSV(formId, tableId, filename, basePath, statusbar, columns, data) {
	document.getElementById("DownloadFileName").value = filename;
	document.getElementById("DownloadTableId").value = tableId;
	document.getElementById("GridHTML").value = data;
	document.getElementById("DownloadRows").value = "all";
	document.getElementById("DownloadColumns").value = columns;
	
	document.getElementById("downloadExcelIframe").src = basePath + "index?ActFlag=downloadCSVView";
}

/**
 * 下載csv(grid中有選取的資料)
 * @param formId
 * @param tableId
 * @param filename
 * @param basePath
 * @param statusbar
 * @param columns
 * @return
 * @author csiebug
 * @version 2008/12/15
 */
function downloadPartialCSV(formId, tableId, filename, basePath, statusbar, columns, data) {
	var trs = document.getElementById(tableId).getElementsByTagName("tr");
	
	var downloadRows = "";
	var headerRowCount = 0;
	for(var i = 0; i < (trs.length - statusbar); i++) {
		var tr = trs[i];
		
		if(tr.className == "TableHeader") {
			headerRowCount++;
		} else if(tr.className == "TR_FOCUS") {
			if(downloadRows != "") {
				downloadRows = downloadRows + ",";
			}
			
			downloadRows = downloadRows + (i - headerRowCount);
		}
	}
	
	document.getElementById("DownloadFileName").value = filename;
	document.getElementById("DownloadTableId").value = tableId;
	document.getElementById("GridHTML").value = data;
	document.getElementById("DownloadRows").value = downloadRows;
	document.getElementById("DownloadColumns").value = columns;
	
	document.getElementById("downloadExcelIframe").src = basePath + "index?ActFlag=downloadCSVView";
}

/**
 * 下載excel
 * @param formId
 * @param tableId
 * @param filename
 * @param basePath
 * @param statusbar
 * @param columns
 * @param data
 * @return
 * @author csiebug
 * @version 2010/12/21
 */
function downloadExcel(formId, tableId, filename, basePath, statusbar, columns, data) {
	document.getElementById("DownloadFileName").value = filename;
	document.getElementById("DownloadTableId").value = tableId;
	document.getElementById("GridHTML").value = data;
	document.getElementById("DownloadRows").value = "all";
	document.getElementById("DownloadColumns").value = columns;
	
	document.getElementById("downloadExcelIframe").src = basePath + "index?ActFlag=downloadExcelView";
}

/**
 * 下載excel(grid中有選取的資料)
 * @param formId
 * @param tableId
 * @param filename
 * @param basePath
 * @param statusbar
 * @param columns
 * @return
 * @author csiebug
 * @version 2008/12/15
 */
function downloadPartialExcel(formId, tableId, filename, basePath, statusbar, columns, data) {
	var trs = document.getElementById(tableId).getElementsByTagName("tr");
	
	var downloadRows = "";
	var headerRowCount = 0;
	for(var i = 0; i < (trs.length - statusbar); i++) {
		var tr = trs[i];
		
		if(tr.className == "TableHeader") {
			headerRowCount++;
		} else if(tr.className == "TR_FOCUS") {
			if(downloadRows != "") {
				downloadRows = downloadRows + ",";
			}
			
			downloadRows = downloadRows + (i - headerRowCount);
		}
	}
	
	document.getElementById("DownloadFileName").value = filename;
	document.getElementById("DownloadTableId").value = tableId;
	document.getElementById("GridHTML").value = data;
	document.getElementById("DownloadRows").value = downloadRows;
	document.getElementById("DownloadColumns").value = columns;
	
	document.getElementById("downloadExcelIframe").src = basePath + "index?ActFlag=downloadExcelView";
}

/**
 * 下載ods
 * @param formId
 * @param tableId
 * @param filename
 * @param basePath
 * @param statusbar
 * @param columns
 * @param data
 * @return
 * @author csiebug
 * @version 2010/12/21
 */
function downloadODS(formId, tableId, filename, basePath, statusbar, columns, data) {
	document.getElementById("DownloadFileName").value = filename;
	document.getElementById("DownloadTableId").value = tableId;
	document.getElementById("GridHTML").value = data;
	document.getElementById("DownloadRows").value = "all";
	document.getElementById("DownloadColumns").value = columns;
	
	document.getElementById("downloadExcelIframe").src = basePath + "index?ActFlag=downloadODSView";
}

/**
 * 下載ods(grid中有選取的資料)
 * @param formId
 * @param tableId
 * @param filename
 * @param basePath
 * @param statusbar
 * @param columns
 * @return
 * @author csiebug
 * @version 2008/12/15
 */
function downloadPartialODS(formId, tableId, filename, basePath, statusbar, columns, data) {
	var trs = document.getElementById(tableId).getElementsByTagName("tr");
	
	var downloadRows = "";
	var headerRowCount = 0;
	for(var i = 0; i < (trs.length - statusbar); i++) {
		var tr = trs[i];
		
		if(tr.className == "TableHeader") {
			headerRowCount++;
		} else if(tr.className == "TR_FOCUS") {
			if(downloadRows != "") {
				downloadRows = downloadRows + ",";
			}
			
			downloadRows = downloadRows + (i - headerRowCount);
		}
	}
	
	document.getElementById("DownloadFileName").value = filename;
	document.getElementById("DownloadTableId").value = tableId;
	document.getElementById("GridHTML").value = data;
	document.getElementById("DownloadRows").value = downloadRows;
	document.getElementById("DownloadColumns").value = columns;
	
	document.getElementById("downloadExcelIframe").src = basePath + "index?ActFlag=downloadODSView";
}

/**
 * 滑鼠移到資料列上
 * @param row
 * @return
 * @author csiebug
 * @version 2009/9/21
 */
function mousemoveRow(row) {
	if(row.className != "TR_HIGHLIGHT") {
		currentRowClass = row.className;
	}
	
	row.className = "TR_HIGHLIGHT";
}

/**
 * 滑鼠移開資料列
 * @param row
 * @return
 * @author csiebug
 * @version 2009/9/21
 */
function mouseoutRow(row) {
	row.className = currentRowClass;
}

/**
 * 點選show出group row
 * @param tableId
 * @param tr
 * @param imagePath
 * @return
 * @author csiebug
 * @version 2010/2/11
 */
function showGroupRow(tableId, tr, imagePath) {
	var table = document.getElementById(tableId);
	var trs = table.getElementsByTagName("tr");
	
	if(tr.getElementsByTagName("td")[0].getElementsByTagName("img").length > 0) {
		if(tr.getElementsByTagName("td")[0].getElementsByTagName("img")[0].src.indexOf("/Cfolder.gif") != -1) {
			tr.getElementsByTagName("td")[0].getElementsByTagName("img")[0].src = imagePath + "/Ofolder.gif";
		}
	}
	var groupIdPrefix = tr.id + "_";
	
	for(var i = 0; i < trs.length; i++) {
		if(trs[i].id.indexOf(groupIdPrefix) == 0) {
			//只show下層節點
			if(trs[i].style.display == "none") {
				if(trs[i].id.substring(groupIdPrefix.length, trs[i].id.length).indexOf("_") == -1) {
					trs[i].style.display = "";
					if(trs[i].getElementsByTagName("td")[0].getElementsByTagName("img").length > 0) {
						if(trs[i].getElementsByTagName("td")[0].getElementsByTagName("img")[0].src.indexOf("/Cfolder.gif") != -1 ||
						   trs[i].getElementsByTagName("td")[0].getElementsByTagName("img")[0].src.indexOf("/Ofolder.gif") != -1) {
							trs[i].getElementsByTagName("td")[0].getElementsByTagName("img")[0].src = imagePath + "/Cfolder.gif";
						}
					}
				}
			//全部子節點都收起來
			} else {
				trs[i].style.display = "none";
				if(tr.getElementsByTagName("td")[0].getElementsByTagName("img").length > 0) {
					if(tr.getElementsByTagName("td")[0].getElementsByTagName("img")[0].src.indexOf("/Ofolder.gif") != -1) {
						tr.getElementsByTagName("td")[0].getElementsByTagName("img")[0].src = imagePath + "/Cfolder.gif";
					}
				}
			}
		}
	}
}

/**
 * 檢查是否有選擇一筆row
 * @author csiebug
 * @version 2010/11/11
 */
function isSelectOneRow(tableId) {
	var flag = false;
	
	var trs = document.getElementById(tableId).getElementsByTagName("tr");
	
	for(var i = 0; i < trs.length; i++) {
		//查到第一個focus row,繼續往下查有沒有複選
		if(!flag && trs[i].className == "TR_FOCUS") {
			flag = true;
		//查到有多筆被focus,則不可移動
		} else if(flag && trs[i].className == "TR_FOCUS") {
			return false;
		}
	}
	
	return flag;
}

/**
 * 檢查是否有選擇一筆以上row
 * @author csiebug
 * @version 2010/12/10
 */
function isSelectRows(tableId) {
	var flag = false;
	
	var trs = document.getElementById(tableId).getElementsByTagName("tr");
	
	for(var i = 0; i < trs.length; i++) {
		if(trs[i].className == "TR_FOCUS") {
			flag = true;
			break;
		}
	}
	
	return flag;
}

/**
 * 取得選取的row個數
 * @author csiebug
 * @version 2010/11/11
 */
function getFocusRowCount(tableId) {
	var count = 0;
	var trs = document.getElementById(tableId).getElementsByTagName("tr");
	
	for(var i = 0; i < trs.length; i++) {
		if(trs[i].className == "TR_FOCUS") {
			count++;
		}
	}
	
	return count;
}

/**
 * 取得目前被focus row的index(單選)
 * @author csiebug
 * @version 2010/11/18
 */
function getSelectOneFocusRowIndex(tableId) {
	var index = 0;
	var trs = document.getElementById(tableId).getElementsByTagName("tr");
	
	for(var i = 0; i < trs.length; i++) {
		if(trs[i].className == "TR_FOCUS") {
			return i;
		}
	}
	
	return index;
}

/**
 * 取得所有被focus row的index(複選)
 * @author csiebug
 * @version 2010/11/18
 */
function getFocusRowIndex(tableId) {
	var focusRowIndex = new Array();
	
	var trs = document.getElementById(tableId).getElementsByTagName("tr");
	
	for(var i = 0; i < trs.length; i++) {
		if(trs[i].className == "TR_FOCUS") {
			focusRowIndex[focusRowIndex.length] = i;
		}
	}
	
	return focusRowIndex;
}
