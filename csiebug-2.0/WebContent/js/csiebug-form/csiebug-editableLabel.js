/**
 * EditableLabel需要用到的function
 * @param id
 * @param label
 * @return
 * @author csiebug
 * @version 2010/11/5
 */
function showInputText(id, label) {
	var inputText = document.getElementById(id);
	inputText.style.display = "";
	inputText.focus();
	var calendarButton = document.getElementById(id + "_button");
	if(calendarButton != null) {
		calendarButton.style.display = "";
	}
	label.style.display = "none";
}

/**
 * 顯示text控制項以供修改Label
 * @param id
 * @param label
 * @param e
 * @return
 * @author csiebug
 * @version 2010/11/5
 */
function showInputTextForKeyDown(id, label, e) {
	var key = window.event ? e.keyCode : e.which;
	
	//按下可視字元或enter才提供這功能
	if(!e.ctrlKey && (
	   key == 13 || //enter
	   key == 32 || //space
	   (key >= 48 && key <= 57) || //數字
	   key == 59 || //;:
	   (key >= 65 && key <= 90) || //英文單字
	   key == 107 || //=+
	   key == 109 || //-_
	   key == 188 || //,<
	   (key >= 190 && key <= 192) || //.> /? `~
	   (key >= 219 && key <= 222))) { //[{ \| ]} '"
		var inputText = document.getElementById(id);
		inputText.value = "";
		inputText.style.display = "";
		inputText.focus();
		var calendarButton = document.getElementById(id + "_button");
		if(calendarButton != null) {
			calendarButton.style.display = "";
		}
		label.style.display = "none";
	}
}

/**
 * 輸入Text後onBlur自動修改Label
 * @param blankText
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
 * @version 2010/11/5
 */
function modifyLabel(blankText, obj, warning, ok, messageInterval, message1Start, message1End, message2, message3, message4, message5, message6, message7, message8Start, message8End, message9Start, message9End, message10Start, message10End, message11, message12Start, message12End, message13, message14) {
	if(checkDataType(obj, warning, ok, messageInterval, message1Start, message1End, message2, message3, message4, message5, message6, message7, message8Start, message8End, message9Start, message9End, message10Start, message10End, message11, message12Start, message12End, message13, message14)) {
		var divLabel = document.getElementById(obj.id + "_textLabel");
		var divLabelHTML = divLabel.innerHTML;
		var calendar = document.getElementById(obj.id + "_calendar");
		
		if(calendar == null || calendar.style.display == "none") {
			if(obj.value != "") {
				divLabel.innerHTML = obj.value;
			} else {
				divLabel.innerHTML = blankText;
			}
			obj.style.display = "none";
			var calendarButton = document.getElementById(obj.id + "_button");
			if(calendarButton != null) {
				calendarButton.style.display = "none";
			}
			divLabel.style.display = "";
		}
	}
}