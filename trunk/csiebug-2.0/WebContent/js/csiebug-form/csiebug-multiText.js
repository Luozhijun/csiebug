/**
 * MultiText需要用到的function
 * @param id
 * @param button
 * @return
 * @author csiebug
 * @version 2010/10/6
 */
function showInputTextForMultiText(id, button) {
	var inputText = document.getElementById(id);
	inputText.style.display = "";
	inputText.focus();
	var calendarButton = document.getElementById(id + "_button");
	if(calendarButton != null) {
		calendarButton.style.display = "";
	}
	button.style.display = "none";
}

/**
 * 輸入Text後onBlur自動將內容加至List
 * @param onChange
 * @param obj
 * @param warning
 * @param ok
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
 * @version 2010/10/6
 */
function addToList(onChange, obj, warning, ok, messageInterval, message1Start, message1End, message2, message3, message4, message5, message6, message7, message8Start, message8End, message9Start, message9End, message10Start, message10End, message11, message12Start, message12End, message13, message14) {
	if(checkDataType(obj, warning, ok, messageInterval, message1Start, message1End, message2, message3, message4, message5, message6, message7, message8Start, message8End, message9Start, message9End, message10Start, message10End, message11, message12Start, message12End, message13, message14)) {
		var controlId = replaceAll(obj.id, "_textInput", "");
		var divList = document.getElementById(controlId + "_textList");
		var divListHTML = divList.innerHTML;
		var button = document.getElementById(controlId + "_button");
		
		if(obj.value.trim() != "") {
			var addHTML = "";
			
			if(divListHTML.trim() != "") {
				addHTML = "<br id='" + obj.value + "_divElementBr'>";
			}
			
			if(onChange != '') {
				onChange = replaceAll(onChange, '&quote', '\'');
			}
			addHTML = addHTML + "<label id='" + obj.value + "_divElementLabel' class = \"DivList\">" + obj.value + "</label>\n<a id='" + obj.value + "_divElementButton' style=\"cursor:pointer\" onClick = \"removeFromList('" + controlId + "', '" + obj.value + "');" + onChange + "\">[X]</a>";
			
			divList.innerHTML = divListHTML + addHTML;
			
			refreshDivList(controlId);
		}
		
		obj.value = "";
		obj.style.display = "none";
		var calendarButton = document.getElementById(obj.id + "_button");
		if(calendarButton != null) {
			calendarButton.style.display = "none";
		}
		button.style.display = "";
	}
}

/**
 * 從List中移除一個資料
 * @param controlId
 * @param value
 * @return
 * @author csiebug
 * @version 2010/10/6
 */
function removeFromList(controlId, value) {
	var divList = document.getElementById(controlId + "_textList");
	var divElements = divList.getElementsByTagName("label");
	var divElement;
	for(var i = 0; i < divElements.length; i++) {
		if(divElements[i].id == value + "_divElementLabel") {
			divElement = divElements[i];
			break;
		}
	}
	
	var divButtons = divList.getElementsByTagName("a");
	var divButton;
	for(var i = 0; i < divButtons.length; i++) {
		if(divButtons[i].id == value + "_divElementButton") {
			divButton = divButtons[i];
			break;
		}
	}
	
	var divBrs = divList.getElementsByTagName("br");
	var divBr;
	for(var i = 0; i < divBrs.length; i++) {
		if(divBrs[i].id == value + "_divElementBr") {
			divBr = divBrs[i];
			break;
		}
	}
	
	divList.removeChild(divElement);
	divList.removeChild(divButton);
	
	if(divBr != null) {
		divList.removeChild(divBr);
	}
	
	removeFirstBr(controlId);
	
	refreshDivList(controlId);
}

/**
 * 對List的任何新增和刪除動作,都要記得更新hidden欄位的值
 * @param controlId
 * @return
 * @author csiebug
 * @version 2010/10/6
 */
function refreshDivList(controlId) {
	var divList = document.getElementById(controlId + "_textList");
	var hidden = document.getElementById(controlId);
	var divElements = divList.getElementsByTagName("label");
	
	var listValue = "";
	for(var i = 0; i < divElements.length; i++) {
		if(i != 0) {
			listValue = listValue + ";";
		}
		
		listValue = listValue + divElements[i].innerHTML;
	}
	
	hidden.value = listValue;
}

/**
 * 移除資料可能會殘留BR使版面變奇怪,要移除第一個BR
 * @param controlId
 * @return
 * @author csiebug
 * @version 2010/10/6
 */
function removeFirstBr(controlId) {
	var divList = document.getElementById(controlId + "_textList");
	var divElements = divList.getElementsByTagName("label");
	var divBrs = divList.getElementsByTagName("br");
	
	if(divBrs[0] != null) {
		if(divElements[0] != null) {
			var id1 = replaceAll(divElements[0].id, "_divElementLabel", "");
			var id2 = replaceAll(divBrs[0].id, "_divElementBr", "");
			
			if(id1 == id2) {
				divList.removeChild(divBrs[0]);
			}
		} else {
			divList.removeChild(divBrs[0]);
		}
	}
}