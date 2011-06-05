/**
 * 開啟或關閉選單
 * @param selId
 * @return
 * @author csiebug
 * @version 2009/1/8
 */
function openOption(selId) {
	var alertDialog = document.getElementById("alertDialog");
	
	//如果有自訂alert視窗彈出,則要鎖定選單功能
	if (alertDialog.style.display == "none") {
		var selText = document.getElementById(selId + "_display");
		var selOption = document.getElementById(selId + "_option");
		
		//先看看目標選單的狀態是開啟還是關閉
		var openFlag = false;
		if(selOption.style.display == "none") {
			openFlag = true;
		}
		
		//先把所有的選單收起來(可能同個畫面已經開了其他選單)
		closeAllOption();
		
		//因為全部都關閉了包括目標選單,所以要根據之前記起來的狀態來開啟或關閉目標選單
		if (openFlag) {
			//因為relative雖然可以設定位置,但是他佔空間,所以無法達到z-index可以做到重疊的效果 
			//取得relative(相對座標系統)的絕對座標
			selText.style.position = "relative";
			
			var top = selText.offsetTop;
			var left = selText.offsetLeft;
			var width = selText.offsetWidth;
			
			//再把它改為absolute(絕對座標系統),並把座標設上
			selOption.style.position = "absolute";
			selOption.style.top = (top + selText.offsetHeight) + "px";
			selOption.style.left = left + "px";
			selOption.style.width = width;
			
			selOption.style.display = "";
		}
	}
}

/**
 * 編輯中出現提示選項
 * @param selId
 * @param caseSensitive
 * @param op
 * @param e
 * @param warning
 * @param message
 * @param ok
 * @return
 * @author csiebug
 * @version 2009/3/13
 */
function editSelect(selId, caseSensitive, op, e, warning, message, ok) {
	var selText = document.getElementById(selId + "_display");
	var selOption = document.getElementById(selId + "_option");
	var selOptions = selOption.getElementsByTagName("div");
	
	if(selOption.style.display == "none") {
		selOption.style.display = "";
	}
	
	//使用者輸入中,show出"目前"前幾個字有符合的選項
	var fitFlag = false;
	for(var i = 0; i < selOptions.length; i++) {
		selOptions[i].style.display = "none";
		
		if(selText.value != "") {
			if (caseSensitive) {
				if (selText.value == selOptions[i].innerHTML.substring(0, selText.value.length)) {
					selOptions[i].style.display = "";
					fitFlag = true;
				}
			} else {
				if (selText.value.toLowerCase() == selOptions[i].innerHTML.substring(0, selText.value.length).toLowerCase()) {
					selOptions[i].style.display = "";
					fitFlag = true;
				}
			}
		}
	}
	
	if(!fitFlag) {
		closeOption(selId);
	} else {
		//因為relative雖然可以設定位置,但是他佔空間,所以無法達到z-index可以做到重疊的效果 
		//取得relative(相對座標系統)的絕對座標
		selText.style.position = "relative";
		
		var top = selText.offsetTop;
		var left = selText.offsetLeft;
		var width = selText.offsetWidth;
				
		//再把它改為absolute(絕對座標系統),並把座標設上
		selOption.style.position = "absolute";
		selOption.style.top = (top + selText.offsetHeight) + "px";
		selOption.style.left = left + "px";
		selOption.style.width = width;
		
		//如果使用者用上下鍵移動選擇選項時
		var key = window.event ? e.keyCode : e.which;
		
		//firefox中沒辦法使用background == "blue"來判斷是否選了選項,要自設attribute才行
	  	if(key == 38) { //上
	  		if(fitFlag) {
	  			var selFlag = false;
	  			var firstIndex = -1;
	  			for(var i = 0; i < selOptions.length; i++) {
	  				if(firstIndex == -1 && selOptions[i].style.display == "") {
	  					firstIndex = i;
	  				}
	  				
	  				if(selOptions[i].getAttribute("selected") == "true" && selFlag == false) {
	  					if(i != firstIndex) {
	  						for(var j = i - 1; j >= 0; j++) {
	  							if(selOptions[j].style.display == "") {
			  						selOptions[j].style.background = "blue";
			  						selOptions[j].style.color = "white";
			  						selOptions[j].setAttribute("selected", "true");
			  						
			  						break;
		  						}
	  						}
	  					}
	  					selOptions[i].style.background = "white";
	  					selOptions[i].style.color = "black";
	  					selOptions[i].setAttribute("selected", "false");
	  					
	  					selFlag = true;
	  				}
	  			}
	  		}
	  	} else if(key == 40) { //下
	  		if(fitFlag) {
	  			var selFlag = false;
	  			var firstIndex = -1;
	  			var lastIndex = - 1;
	  			for(var i = 0; i < selOptions.length; i++) {
	  				if(firstIndex == -1 && selOptions[i].style.display == "") {
	  					firstIndex = i;
	  				}
	  				if(lastIndex == -1 && selOptions[selOptions.length - 1 - i].style.display == "") {
	  					lastIndex = selOptions.length - 1 - i;
	  				}
	  				
	  				if(selOptions[i].getAttribute("selected") == "true" && selFlag == false) {
	  					if(i != lastIndex) {
	  						for(var j = i + 1; j < selOptions.length; j++) {
		  						if(selOptions[j].style.display == "") {
		  							selOptions[j].style.background = "blue";
			  						selOptions[j].style.color = "white";
			  						selOptions[j].setAttribute("selected", "true");
			  						
			  						break;
		  						}
	  						}
	  						selOptions[i].style.background = "white";
	  						selOptions[i].style.color = "black";
	  						selOptions[i].setAttribute("selected", "false");
	  					}
	  					selFlag = true;
	  				}
	  			}
	  			
	  			if(!selFlag) {
	  				selOptions[firstIndex].style.background = "blue";
			  		selOptions[firstIndex].style.color = "white";
			  		selOptions[firstIndex].setAttribute("selected", "true");
				}
	  		}
	  	} else if(key == 13) { //enter
	  		//firefox按下enter其實會先觸發text的onblur事件,也就是會先執行checkSelect()
	  		//然後才會觸發onkeyup事件,才會執行到本處的程式碼
	  		//如果checkSelect中有dialog出現,則永遠不會執行到此處
	  		if(selText.value != "") {
	  			for(var i = 0; i < selOptions.length; i++) {
	  				if(selOptions[i].getAttribute("selected") == "true") {
	  					selText.value = selOptions[i].innerHTML;
	  					selOptions[i].style.background = "white";
	  					selOptions[i].style.color = "black";
	  					selOptions[i].setAttribute("selected", "false");
	  					break;
	  				}
	  			}
	  			
	  			checkSelect(selId, op, warning, message, ok);
	  		}
	  	} else if(e.keyCode == 27) { //esc
	  		closeOption(selId);
	  	}
	}
}

/**
 * 判斷是否輸入正確,然後設值
 * @param selId
 * @param caseSensitive
 * @param op
 * @param warning
 * @param message
 * @param ok
 * @return
 * @author csiebug
 * @version 2009/3/13
 */
function checkSelect(selId, caseSensitive, op, warning, message, ok) {
	var checkResult = true;
	
	var sel = document.getElementById(selId);
	var selText = document.getElementById(selId + "_display");
	var selOption = document.getElementById(selId + "_option");
	var selOptions = selOption.getElementsByTagName("div");
	
	var clickOption = false;
	
	for(var i = 0; i < selOptions.length; i++) {
		if(selOptions[i].getAttribute("selected") == "true") {
			clickOption = true;
			selOptions[i].onclick();
			break;
		}
	}
	
	if(!clickOption) {
		sel.value = "";
		var partialMatch;
		
		//使用者自行輸入,故尋找option裡面是否有符合
		if (selText.value != "") {
			for (var i = 0; i < selOptions.length; i++) {
				if (caseSensitive) {
					if (selText.value == selOptions[i].innerHTML) {
						//keyValue複合控制項才會有text屬性,因為keyValue控制項與一般使用不同
						if(selOptions[i].getAttribute("text") != null) {
							sel.value = selOptions[i].getAttribute("text");
						} else {
							sel.value = selOptions[i].getAttribute("value");
						}
						break;
					}
					else {
						if (selText.value == selOptions[i].innerHTML.substring(0, selText.value.length)) {
							if (partialMatch == null) {
								partialMatch = selOptions[i];
							}
						}
					}
				} else {
					if (selText.value.toLowerCase() == selOptions[i].innerHTML.toLowerCase()) {
						//keyValue複合控制項才會有text屬性,因為keyValue控制項與一般使用不同
						if(selOptions[i].getAttribute("text") != null) {
							sel.value = selOptions[i].getAttribute("text");
						} else {
							sel.value = selOptions[i].getAttribute("value");
						}
						break;
					}
					else {
						if (selText.value.toLowerCase() == selOptions[i].innerHTML.substring(0, selText.value.length).toLowerCase()) {
							if (partialMatch == null) {
								partialMatch = selOptions[i];
							}
						}
					}
				}
			}
		}
		
		//option之中沒有符合的
		if(sel.value == "") {
			if(op == 1) { //預設使用者輸入必須是選項之一
				if(selText.value != "") {
					openAlertDialog(selText, warning, message, ok, '', selText, 0);
					selText.value = "";
					checkResult = false;
				}
			} else if(op == 2) { //使用者可以任意輸入,傳回程式的值等於使用者輸入值(option的value和text相同的意思)
				sel.value = selText.value;
			} else if(op == 3) { //沒有符合,自動選取第一個符合的選項
				if(selText.value != "") {
					if(partialMatch != null) {
						selText.value = partialMatch.innerHTML;
						//keyValue複合控制項才會有text屬性,因為keyValue控制項與一般使用不同
						if(partialMatch.getAttribute("text") != null) {
							sel.value = partialMatch.getAttribute("text");
						} else {
							sel.value = partialMatch.getAttribute("value");
						}
					} else { //若沒有部分符合的選項,就直接用使用者輸入的值
						openAlertDialog(selText, warning, message, ok, '', selText, 0);
						selText.value = "";
						checkResult = false;
					}
				}
			}
		}
		
		closeOption(selId);
	}
	
	return checkResult;
}

/**
 * 選擇選項
 * @param selId
 * @param n
 * @param v
 * @param func
 * @return
 * @author csiebug
 * @version 2009/1/8
 */
function selOption(selId, n, v, func) {
	var selText = document.getElementById(selId + "_display");
	var sel = document.getElementById(selId);
	var selOption = document.getElementById(selId + "_option");
	
	var changeFlag = false;
	if(selText.value != n && sel.value != v) {
		changeFlag = true;
	}
	
	selText.value = n;
	sel.value = v;
	
	closeOption(selId);
	
	if(func != '' && changeFlag) {
		eval(replaceAll(replaceAll(func, 'this', 'document.getElementById(\'' + selId + '_display\')'), '&quote', '\''));
	}
	
	selText.select();
}

/**
 * 把所有的選單收起來
 * @return
 * @author csiebug
 * @version 2009/6/19
 */
function closeAllOption() {
	var divs = document.getElementsByTagName("div");
	
	for(var i = 0; i < divs.length; i++) {
		var div = divs[i];
		
		if(div.id.substring(div.id.length - 7, div.id.length) == "_option") {
			div.style.display = "none";
			
			var selText = document.getElementById(div.id.substring(0, div.id.length - 7) + "_display");
			selText.style.position = "static";
		}
	}
}

/**
 * 關閉選單
 * @param selId
 * @return
 * @author csiebug
 * @version 2009/1/8
 */
function closeOption(selId) {
	var selText = document.getElementById(selId + "_display");
	var selOption = document.getElementById(selId + "_option");
	var selOptions = selOption.getElementsByTagName("div");
	
	selOption.style.display = "none";
	
	for(var i = 0; i < selOptions.length; i++) {
		selOptions[i].style.display = "";
		selOptions[i].style.background = "white";
		selOptions[i].style.color = "black";
		selOptions[i].setAttribute("selected", "false");
	}
	
	selText.style.position = "static";
}

/**
 * 滑鼠移到選項上
 * @param obj
 * @return
 * @author csiebug
 * @version 2009/1/8
 */
function mousemoveOption(obj) {
	obj.style.background = "blue";
	obj.style.color = "white";
	obj.setAttribute("selected", "true");
}

/**
 * 滑鼠移開選項
 * @param obj
 * @return
 * @author csiebug
 * @version 2009/1/8
 */
function mouseoutOption(obj) {
	obj.style.background = "white";
	obj.style.color = "black";
	obj.setAttribute("selected", "false");
}