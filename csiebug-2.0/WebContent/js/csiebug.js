//此變數為系統預設採用的日期格式
var defaultDateFormat = 23;

//此變數為系統金額最大長度,預設13位
var defaultMaxCurrencyLength = 13;

//此變數為系統預設採用的密碼規則
var defaultPasswordRule = "win2003";

//此變數為系統密碼最大長度(預設不限長度)
var defaultPasswordMaxLength = 0;

//此變數為系統alert訊息自動關閉的時間
var defaultAlertMessageTimeout = 2000;

//此變數為自訂alert dialog的html,要共用,做變數替換,故用全域變數記住
var alertDialogHtml = "";

//此變數為自訂confirm dialog的html,要共用,做變數替換,故用全域變數記住
var confirmDialogHtml = "";

//此變數為自訂block ui的html,要共用,作變數替換,故用全域變數記住
var loadingBlockPageHtml = "";

//此變數記錄所有需要填值的control id
var requiredControls = "";

//此變數用來判斷history.back發生的時候用
var alreadyLoad = false;

// Print the properties of an object in an increassing order
function propertyPrint(obj, objName){
	var property = new Array();
	var k=0, prop;
	for (prop in obj)
		property[k++]=prop;
	property.sort();
	for (i=0; i<property.length; i++)
		document.writeln(objName+".<font color=red>"+property[i]+"</font> = <font color=green>"+obj[property[i]]+"</font><br>");
}

// Print the source of an object
function sourcePrint(obj) {
	document.writeln("<xmp class=code>");
	document.writeln(obj.outerHTML);
	document.writeln("</xmp>");
}

//幫String物件加上trim()的function
String.prototype.trim = function() {
  return this.replace(/(^\s*)|(\s*$)/g,'');
};

//幫String物件加上startsWith()的function
String.prototype.startsWith = function(prefix) {
    return this.indexOf(prefix) === 0;
};

//幫String物件加上endsWith()的function
String.prototype.endsWith = function(suffix) {
    return this.match(suffix+"$") == suffix;
};

/**
 * 字串取代
 * @param value
 * @param find
 * @param replace
 * @return
 * @author csiebug
 * @version 2008/5/23
 */
function replaceAll(value,find,replace){
	while(value.indexOf(find) != -1) {
 		value = value.replace(find, replace);
 	}
 	return value;
}

/**
 * 去掉開頭空白
 * @param value
 * @returns
 * @author csiebug
 * @version 2011/6/7
 */
function ltrim(value) {
	var strValue = value;
	
	while(strValue.startsWith(" ") || strValue.startsWith("\t") || strValue.startsWith("\n")) {
		if(strValue.startsWith(" ")) {
			strValue = strValue.replace(" ", "");
		} else if(strValue.startsWith("\t")) {
			strValue = strValue.replace("\t", "");
		} else if(strValue.startsWith("\n")) {
			strValue = strValue.replace("\n", "");
		}
	}
	
	return strValue;
}

/**
 * 用來檢查value是否存在array裏面
 * @param array
 * @param value
 * @returns {Boolean}
 * @author csiebug
 * @version 2011/4/21
 */
function isInArray(array, value) {
	for(var i = 0; i < array.length; i++) {
		if(array[i] == value) {
			return true;
		}
	}
	
	return false;
}

/**
 * 頁面submit之前檢查有無必填欄位
 * @param formId
 * @param warning
 * @param message
 * @param ok
 * @return
 * @author csiebug
 * @version 2009/6/17
 */
function checkRequired(formId, warning, message, ok) {
	var finalMessage = "";
	var radioIds = new Array();
	
	var flag = true;
	var objAll = eval("document." + formId + ".elements");
	for(var i = 0; i < objAll.length; i++) {
		var obj = objAll[i];
		if(obj.getAttribute("isRequired") == "true") {
			if(obj.getAttribute("type") == "radio") {
				var radios = document.getElementsByName(obj.name);
				
				if(!isInArray(radioIds, radios[0].id)) {
					radioIds[radioIds.length] = radios[0].id;
					
					var flag2 = false;
					for(var j = 0; j < radios.length; j++) {
						if(radios[j].checked == true) {
							flag2 = true;
							break;
						}
					}
					
					if(!flag2) {
						flag = false;
						
						if(finalMessage != "") {
							finalMessage = finalMessage + "<br>";
						}
						
						if (document.getElementById(obj.id + "_header") != null) {
							finalMessage = finalMessage + message.replace('{0}', document.getElementById(obj.id + "_header").innerHTML.replace('<font color="red">＊</font>', '').replace('：', ''));
						} else {
							finalMessage = finalMessage + message.replace('{0}', obj.id);
						}
					}
				}
			} else if(obj.value.trim() == "") {
				flag = false;
				
				if(finalMessage != "") {
					finalMessage = finalMessage + "<br>";
				}
				
				if (document.getElementById(obj.id + "_header") != null) {
					finalMessage = finalMessage + message.replace('{0}', document.getElementById(obj.id + "_header").innerHTML.replace('<font color="red">＊</font>', '').replace('：', ''));
				} else {
					finalMessage = finalMessage + message.replace('{0}', obj.id);
				}
			}
		}
	}
	
	if (finalMessage != "") {
		openAlertDialog(null, warning, finalMessage, ok, '', null, 0);
	}
	
	return flag;
}

/**
 * 頁面submit之前檢查有無必填欄位
 * @param formId
 * @param warning
 * @param message
 * @param ok
 * @return
 * @author csiebug
 * @version 2009/3/13
 */
function checkRequiredWithUniteMessage(formId, warning, message, ok) {
	var flag = true;
	var objAll = eval("document." + formId + ".elements");
	var radioIds = new Array();
	
	for(var i = 0; i < objAll.length; i++) {
		var obj = objAll[i];
		if(obj.getAttribute("isRequired") == "true") {
			if(obj.getAttribute("type") == "radio") {
				var radios = document.getElementsByName(obj.name);
				
				if(!isInArray(radioIds, radios[0].id)) {
					radioIds[radioIds.length] = radios[0].id;
					
					var flag2 = false;
					for(var j = 0; j < radios.length; j++) {
						if(radios[j].checked == true) {
							flag2 = true;
							break;
						}
					}
					
					if(!flag2) {
						//alert(message);
						//obj.focus();
						openAlertDialog(obj, warning, message, ok, '', obj, 0);
						flag = false;
						break;
					}
				}
			} else if(obj.value.trim() == "") {
				//alert(message);
				//obj.value = "";
				//obj.focus();
				openAlertDialog(obj, warning, message, ok, '', obj, 0);
				flag = false;
				break;
			}
		}
	}
	
	return flag;
}

/**
 * 頁面submit之前檢查有無必填欄位(不show訊息，由PG根據requiredControl自訂訊息)
 * @param formId
 * @return
 * @author csiebug
 * @version 2009/6/17
 */
function checkRequiredWithNoMessage(formId) {
	requiredControls = "";
	var radioIds = new Array();
	
	var flag = true;
	var objAll = eval("document." + formId + ".elements");
	for(var i = 0; i < objAll.length; i++) {
		var obj = objAll[i];
		if(obj.getAttribute("isRequired") == "true") {
			if(obj.getAttribute("type") == "radio") {
				var radios = document.getElementsByName(obj.name);
				
				if(!isInArray(radioIds, radios[0].id)) {
					radioIds[radioIds.length] = radios[0].id;
					
					var flag2 = false;
					for(var j = 0; j < radios.length; j++) {
						if(radios[j].checked == true) {
							flag2 = true;
							break;
						}
					}
					
					if(!flag2) {
						flag = false;
						
						if(requiredControls != "") {
							requiredControls = requiredControls + ",";
						}
						requiredControls = requiredControls + obj.id;
					}
				}
			} else if(obj.value.trim() == "") {
				flag = false;
				
				if(requiredControls != "") {
					requiredControls = requiredControls + ",";
				}
				requiredControls = requiredControls + obj.id;
			}
		}
	}
	
	return flag;
}

/**
 * 頁面submit之前,將currency資料都去掉逗號後再submit
 * @param formId
 * @return
 * @author csiebug
 * @version 2008/7/4
 */
function removeComma(formId) {
	var objAll = eval("document." + formId + ".elements");
	for(var i = 0; i < objAll.length; i++) {
		var obj = objAll[i];
		
		if(obj.getAttribute("dataType") == "currency") {
			obj.value = replaceAll(obj.value, ',', '');
		}
	}
}

/**
 * 如果是currency欄位,有可能會輸入逗號,所以要動態改變maxLength長度
 * @param obj
 * @param maxlength
 * @return
 * @author csiebug
 * @version 2008/8/7
 */
function checkComma(obj, maxlength) {
	if(isValid(obj.value)) {
		if(maxlength == 0) {
			maxlength = defaultMaxCurrencyLength;
		}
		
		if(obj.value.indexOf(",") != -1) {
			if(obj.value.length >= maxlength) {
				var newMaxLength = maxlength + (obj.value.length - replaceAll(obj.value, ',', '').length);
				
				if(obj.value.indexOf(".") != -1) {
					newMaxLength = newMaxLength + 1;
				}
				
				if(obj.value.indexOf("-") != -1) {
					newMaxLength = newMaxLength + 1;
				}
				
				obj.setAttribute("maxLength", newMaxLength);
			}
		} else if(obj.value.indexOf(".") != -1) {
			if(obj.value.length >= maxlength) {
				var newMaxLength = maxlength + (obj.value.length - replaceAll(obj.value, '.', '').length) + 1;
				obj.setAttribute("maxLength", newMaxLength);
			}
		} else if(obj.value.indexOf("-") != -1) {
			if(obj.value.length >= maxlength) {
				var newMaxLength = maxlength + (obj.value.length - replaceAll(obj.value, '-', '').length) + 1;
				obj.setAttribute("maxLength", newMaxLength);
			}
		} else {
			if(obj.getAttribute("maxLength") != maxlength) {
				obj.setAttribute("maxLength", maxlength);
			}
		}
	}
}

/**
 * 將數字加或減一個step
 * @param obj
 * @param e
 * @param step
 * @param max
 * @param min
 * @author csiebug
 * @version 2011/5/12
 */
function stepNumber(obj, e, step, max, min) {
	//使用者用上下鍵改變數值
	var key = window.event ? e.keyCode : e.which;
	
	if(key == 38) { //上
		addStep(obj, step, max);
	} else if(key == 40) { //下
		subtractStep(obj, step, min);
	}
}

/**
 * 數字加一個step
 * @param obj
 * @param step
 * @param max
 * @author csiebug
 * @version 2011/5/12
 */
function addStep(obj, step, max) {
	var isCurrency = false;
	var originalValue;
	
	if(obj.value == "") {
		obj.value = "0";
	}
	
	if(obj.value.indexOf(",") != -1) {
		isCurrency = true;
		originalValue = parseInt(replaceAll(obj.value, ',', ''), 10);
	} else {
		originalValue = parseInt(obj.value, 10);
	}
	
	if(max != -1) {
		if(max >= originalValue + step) {
			obj.value = originalValue + step;
		} else {
			obj.value = max;
		}
	} else {
		obj.value = originalValue + step;
	}
	
	if(isCurrency) {
		obj.value = getCurrency(obj.value);
	}
}

/**
 * 數字減一個step
 * @param obj
 * @param step
 * @param min
 */
function subtractStep(obj, step, min) {
	var isCurrency = false;
	var originalValue;
	
	if(obj.value == "") {
		obj.value = "0";
	}
	
	if(obj.value.indexOf(",") != -1) {
		isCurrency = true;
		originalValue = parseInt(replaceAll(obj.value, ',', ''), 10);
	} else {
		originalValue = parseInt(obj.value, 10);
	}
	
	if(min != -1) {
		if(min <= originalValue - step) {
			obj.value = originalValue - step;
		} else {
			obj.value = min;
		}
	} else {
		obj.value = originalValue - step;
	}
	
	if(isCurrency) {
		obj.value = getCurrency(obj.value);
	}
}

/**
 * 頁面submit之前檢查欄位資料格式
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
 * @param message13
 * @param message14
 * @return
 * @author csiebug
 * @version 2009/3/13
 */
function checkDataType(obj, warning, ok, messageInterval, message1Start, message1End, message2, message3, message4, message5, message6, message7, message8Start, message8End, message9Start, message9End, message10Start, message10End, message11, message12Start, message12End, message13, message14) {
	var flag = true;
	
	//使用者如果沒有輸入，不當他是資料錯誤
	if(obj.value.trim() != "") {
		var messageType = "";
		var fixLength = parseInt(obj.getAttribute("fixLength"), 10);
		var minValue = parseInt(replaceAll(obj.getAttribute("minValue"), ",", ""), 10);
		var maxValue = parseInt(replaceAll(obj.getAttribute("maxValue"), ",", ""), 10);
		var maxInteger = parseInt(replaceAll(obj.getAttribute("maxInteger"), ",", ""), 10);
		var maxDecimal = parseInt(replaceAll(obj.getAttribute("maxDecimal"), ",", ""), 10);
		
		//檢查資料長度是否有符合
		if(obj.getAttribute("fixLength") != -1) {
			var objLength = 0;
			if(obj.getAttribute("dataType") == "currency") {
				objLength = (replaceAll(replaceAll(replaceAll(obj.value, ",", ""), ".", ""), "-", "")).length;
			} else {
				objLength = (obj.value).length;
			}
			if(objLength != fixLength) {
				flag = false;
				messageType = "fixLength";
			}
		}
		//如果是number,不當它是錯誤,補零即可
		if(obj.getAttribute("dataType") == "number") {
				if(!flag) {
					obj.value = addZero(obj.value, fixLength);
					flag = true;
				}
		}
		/*檢查byte數是否符合
		if(obj.getAttribute("maxLength") != null) {
			var maxBytes = parseInt(obj.getAttribute("maxLength"), 10);
			var objBytes = 0;
			if(obj.getAttribute("dataType") == "currency") {
				objBytes = getBytes(replaceAll(obj.value, ",", ""));
			} else {
				objBytes = getBytes(obj.value);
			}
			
			if(objBytes > maxBytes) {
				flag = false;
				messageType = "maxLength";
			}
		}*/
		//檢查整數和小數位數是否符合
		var strInteger = "";
		var strDecimal = "";
		var strTmp = replaceAll(obj.value, ",", "");
		
		if(obj.value.indexOf(".") != -1) {
			strInteger = strTmp.substring(0, strTmp.indexOf("."));
			strDecimal = strTmp.substring(strTmp.indexOf(".") + 1, strTmp.length);
		} else {
			strInteger = strTmp;
		}
		
		if(obj.getAttribute("maxInteger") != -1) {
			if(strInteger.length > maxInteger) {
				flag = false;
				messageType = "integer";
			}
		}
		if(obj.getAttribute("maxDecimal") != -1) {
			if(strDecimal.length > maxDecimal) {
				flag = false;
				messageType = "decimal";
			}
		}
		
		//檢查欄位資料格式
		if(flag) {
			if(obj.getAttribute("dataType") == "number") {
				flag = isValid(obj.value);
				if(!flag) {
					messageType = "number";
				} else {
					if(obj.getAttribute("minValue") != -1) {
						if(parseInt(obj.value, 10) < minValue) {
							flag =false;
							messageType = "min";
						}
					}
					if(obj.getAttribute("maxValue") != -1) {
						if(parseInt(obj.value, 10) > maxValue) {
							flag =false;
							messageType = "max";
						}
					}
				}
			} else if(obj.getAttribute("dataType") == "currency") {
				flag = isValidCurrency(obj.value);
				
				if(flag) { //將數字正確的三位一撇
					obj.value = getCurrency(obj.value);
				}
				
				if(!flag) {
					messageType = "currency";
				} else {
					if(obj.getAttribute("minValue") != -1) {
						if(parseInt(replaceAll(obj.value, ',', ''), 10) < minValue) {
							flag =false;
							messageType = "min";
						}
					}
					if(obj.getAttribute("maxValue") != -1) {
						if(parseInt(replaceAll(obj.value, ',', ''), 10) > maxValue) {
							flag =false;
							messageType = "max";
						}
					}
				}
			} else if(obj.getAttribute("dataType") == "date") {
				obj.value = autoTransDate(obj.value); //轉換日期格式
				flag = isValidDate(obj.value, defaultDateFormat);
			    messageType = "date";
			    if(flag) { //將日期的月日該補零的補零
			    	obj.value = completeMonthDay(obj.value, defaultDateFormat);
				}
			} else if(obj.getAttribute("dataType") == "time12") {
				flag = isValidTime12(obj.value);
			    messageType = "time";
			    if(flag) { //將時間的時分該補零的補零
			    	obj.value = completeHourMinuteSecond(obj.value);
				}
			} else if(obj.getAttribute("dataType") == "time24") {
				flag = isValidTime24(obj.value);
			    messageType = "time";
			    if(flag) { //將時間的時分該補零的補零
			    	obj.value = completeHourMinuteSecond(obj.value);
				}
			} else if(obj.getAttribute("dataType") == "datetime") {
				var timePart = obj.value.split(" ");
				
				if(timePart.length < 2) {
					flag = false;
					messageType = "time";
				} else {
					timePart[0] = autoTransDate(timePart[0]); //轉換日期格式
					flag = isValidDate(timePart[0], defaultDateFormat);
				    if(flag) { //將日期的月日該補零的補零
				    	timePart[0] = completeMonthDay(timePart[0], defaultDateFormat);
				    	
				    	//去掉中間多餘的空白
				    	for(var i = 1; i < timePart.length; i++) {
				    		if(timePart[i] != "") {
				    			timePart[1] = timePart[i];
				    			break;
				    		}
				    	}
				    	
				    	flag = isValidTime24(timePart[1]);
				        if(flag) { //將時間的時分該補零的補零
				        	timePart[1] = completeHourMinuteSecond(timePart[1]);
				        	
				        	obj.value = timePart[0] + " " + timePart[1];
				    	} else {
				    		messageType = "time";
				    	}
					} else {
						messageType = "date";
				    }
				}
			} else if(obj.getAttribute("dataType") == "idno") {
				flag = isValidIDNO(obj.value);
				messageType = "idno";
			} else if(obj.getAttribute("dataType") == "password") {
				flag = isValidPassword(obj.value, defaultPasswordRule);
				messageType = "password";
			} else if(obj.getAttribute("dataType") == "email") {
				flag = isValidEmailAddress(obj.value);
				messageType = "email";
			} else if(obj.getAttribute("dataType") == "ip") {
				flag = isValidIPAddress(obj.value);
				messageType = "ip";
				if(flag) { //將IP中空白字元去除
					if(obj.getAttribute("Masked") == "true") {
						obj.value = completeIPAddressForMask(obj.value);
					} else {
						obj.value = replaceAll(obj.value, " ", "");
					}
				}
			} else if(obj.getAttribute("dataType") == "ipv6") {
				flag = isValidIPv6Address(obj.value);
				messageType = "ip";
				if(flag) { //將IP中空白字元去除
					if(obj.getAttribute("Masked") == "true") {
						obj.value = completeIPv6AddressForMask(obj.value);
					} else {
						obj.value = replaceAll(obj.value, " ", "");
					}
				}
			}
		}
		
		if(!flag) {
			var message = "";
			if(messageType == "fixLength") {
				message = message1Start + fixLength + message1End;
			} else if(messageType == "maxLength"){
				message = message2;
			} else if(messageType == "number") {
				message = message3;
			} else if(messageType == "currency") {
				message = message3;
			} else if(messageType == "date") {
				message = message4;
			} else if(messageType == "idno") {
				message = message5;
			} else if(messageType == "password") {
				message = message6;
			} else if(messageType == "email") {
				message = message7;
			} else if(messageType == "min") {
				message = message8Start + minValue + message8End;
			} else if(messageType == "max") {
				message = message9Start + maxValue + message9End;
			} else if(messageType == "integer") {
				message = message10Start + maxInteger + message10End;
			} else if(messageType == "decimal") {
				if(maxDecimal == 0) {
					message = message11;
				} else {
					message = message12Start + maxDecimal + message12End;
				}
			} else if(messageType == "time") {
				message = message13;
			} else if(messageType == "ip") {
				message = message14;
			}
			
			//alert(message);
			//obj.value = "";
			//obj.focus();
			openAlertDialog(obj, warning, message, ok, '', obj, 0);
		} else {
			//資料格式若無誤,則檢查是否是interval控制項,檢查範圍是否正確
			if(obj.id.indexOf("_start") != -1) {
				if(typeof(document.getElementById(obj.id.replace("_start", "_end"))) == "object") {
					var end = document.getElementById(obj.id.replace("_start", "_end"));
					
					if(obj.getAttribute("dataType") == "number") {
						if(end.value != "" && compareCurrency(obj.value, end.value)) {
							flag = false;
						}
					} else if(obj.getAttribute("dataType") == "currency") {
						if(end.value != "" && compareCurrency(obj.value, end.value)) {
							flag = false;
						}
					} else if(obj.getAttribute("dataType") == "date") {
		          		if(end.value != "" && compareDate(obj.value, end.value, defaultDateFormat)) {
		          			flag = false;
		          		}
		          	} else if(obj.getAttribute("dataType") == "time12") {
		          		var midStart = document.getElementById(obj.id.replace("_start", "_midStart"));
		          		var midEnd = document.getElementById(obj.id.replace("_start", "_midEnd"));
		          		if(end.value != "" && compareTime12(midStart.value, obj.value, midEnd.value, end.value)) {
		          			flag = false;
		          		}
		          	} else if(obj.getAttribute("dataType") == "time24") {
		          		if(end.value != "" && compareTime(obj.value, end.value)) {
		          			flag = false;
		          		}
		          	} else if(obj.getAttribute("dataType") == "datetime") {
		          		var timePartStart = obj.value.split(" ");
		          		var timePartEnd = end.value.split(" ");
		          		
		          		if(timePartEnd[0] != "" && compareDate(timePartStart[0], timePartEnd[0], defaultDateFormat)) {
		          			flag = false;
		          		} else if(timePartStart[0] == timePartEnd[0] && timePartEnd[1] != "" && compareTime(timePartStart[1], timePartEnd[1])) {
		          			flag = false;
		          		}
		          	} else { //其他只能做單純的字典比對
		          		if(end.value != "" && obj.value > end.value) {
		          			flag = false;
		          		}
		          	}
					
					if(!flag) {
						//alert(messageInterval);
						//obj.value = "";
						//end.value = "";
						//obj.focus();
						openAlertDialog(end, warning, messageInterval, ok, '', obj, 0);
					}
				}
			} else if(obj.id.indexOf("_end") != -1) {
				if(typeof(document.getElementById(obj.id.replace("_end", "_start"))) == "object") {
					var start = document.getElementById(obj.id.replace("_end", "_start"));
					
					if(obj.getAttribute("dataType") == "number") {
						if(start.value != "" && compareCurrency(start.value, obj.value)) {
							flag = false;
						}
					} else if(obj.getAttribute("dataType") == "currency") {
						if(start.value != "" && compareCurrency(start.value, obj.value)) {
							flag = false;
						}
					} else if(obj.getAttribute("dataType") == "date") {
		          		if(start.value != "" && compareDate(start.value, obj.value, defaultDateFormat)) {
		          			flag = false;
		          		}
		          	} else if(obj.getAttribute("dataType") == "time12") {
		          		var midStart = document.getElementById(obj.id.replace("_end", "_midStart"));
		          		var midEnd = document.getElementById(obj.id.replace("_end", "_midEnd"));
		          		if(start.value != "" && compareTime12(midStart.value, start.value, midEnd.value, obj.value)) {
		          			flag = false;
		          		}
		          	} else if(obj.getAttribute("dataType") == "time24") {
		          		if(start.value != "" && compareTime(start.value, obj.value)) {
		          			flag = false;
		          		}
		          	} else if(obj.getAttribute("dataType") == "datetime") {
		          		var timePartStart = start.value.split(" ");
		          		var timePartEnd = obj.value.split(" ");
		          		
		          		if(timePartEnd[0] != "" && compareDate(timePartStart[0], timePartEnd[0], defaultDateFormat)) {
		          			flag = false;
		          		} else if(timePartStart[0] == timePartEnd[0] && timePartEnd[1] != "" && compareTime(timePartStart[1], timePartEnd[1])) {
		          			flag = false;
		          		}
		          	} else { //其他只能做單純的字典比對
		          		if(start.value != "" && start.value > obj.value) {
		          			flag = false;
		          		}
		          	}
					
					if(!flag) {
						//alert(messageInterval);
						//start.value = "";
						//obj.value = "";
						//start.focus();
						openAlertDialog(obj, warning, messageInterval, ok, '', start, 0);
					}
				}
			}
		}
	} else {
		obj.value = "";
	}
	
	return flag;
}

/**
 * 是否是合法正整數
 * @param numString
 * @return
 * @author csiebug
 * @version 2008/5/23
 */
function isValidPositiveInteger(numString) {
	var flagPositiveInteger = true;
		
	var value = numString;
	
	for(var i = 0; i <= 9; i++) {
		value = replaceAll(value, "" + i, "");
	}
	
	if(value.length != 0) {
		flagPositiveInteger = false;
	}
		
	return flagPositiveInteger;
}

/**
 * 是否是合法正數
 * @param numString
 * @return
 * @author csiebug
 * @version 2008/5/23
 */
function isValidPositive(numString) {
	var flagPositive = true;
		
	var value = numString;
		
	//檢查小數點
	if(value.indexOf(".") != -1 && value.indexOf(".") == value.lastIndexOf(".")) {
		value = value.replace(".", "");
	} else if(value.indexOf(".") != -1 && value.indexOf(".") != value.lastIndexOf(".")) {
		//有多個小數點是錯誤的數字
		flagPositive = false;
	}
		
	//去小數點以後，檢查是否是合法數字
	if(flagPositive) {
		flagPositive = isValidPositiveInteger(value);
	}
		
	return flagPositive;
}

/**
 * 是否是合法負數
 * @param numString
 * @return
 * @author csiebug
 * @version 2008/5/23
 */
function isValidNegative(numString) {
		var flagNegative = true;
		
		var value = numString;
		
		//檢查負號
		if(value.indexOf("-") != -1 && value.lastIndexOf("-") == 0) {
			value = value.replace("-", "");
		} else if(value.indexOf("-") != -1 && value.lastIndexOf("-") != 0) {
			//有多個負號或是負號不在開頭，是錯誤的數字
			flagNegative = false;
		} else if(value.indexOf("-") == -1) {
			//不是負數
			flagNegative = false;
		}
		
		//去掉負號後檢查數字是否是合法正數
		if(flagNegative) {
			flagNegative = isValidPositive(value);
		}
		
		return flagNegative;
}

/**
 * 是否是合法數字
 * @param numString
 * @return
 * @author csiebug
 * @version 2008/5/23
 */
function isValid(numString) {
	var flagValid = false;
		
	//合法數字不是合法正數不然就是合法負數
	if(isValidPositive(numString) || isValidNegative(numString)) {
		flagValid = true;
	}
		
	return flagValid;
}

/**
 * 是否是合法的金額格式(逗號點錯可接受為合法)
 * @param numString
 * @return
 * @author csiebug
 * @version 2008/5/23
 */
function isValidCurrency(numString) {
	var flagValid = true;
		
	//去掉逗號
	var value = replaceAll(numString, ",", "");
	//檢查是否是合法數字
	flagValid = isValid(value);
		
	return flagValid;
}

/**
 * 自動轉換日期格式成為系統日期格式
 * @param value
 * @return
 * @author csiebug
 * @version 2009/2/19
 */
function autoTransDate(value) {
	var dateTypes = new Array(1, 2, 3, 4, 5, 10, 11, 12, 23, 101, 102, 103, 104, 105, 110, 111, 112, 1000, 2000, 3000, 4000, 5000, 10000, 11000, 12000, 23000, 101000, 102000, 103000, 104000, 105000, 110000, 111000, 112000);
	
	var type = 0;
	var y = 0;
	var m = 0;
	var d = 0;
	var spliter = "";
	var order = "1";
	var yearType = "1";
	
	//檢查使用者輸入符合哪種日期格式
	for(var i = 0; i < dateTypes.length; i++) {
		if(isValidDate(value, dateTypes[i])) {
			if(defaultDateFormat >= 1000) {
				type = dateTypes[i] * 1000;
			} else {
				type = dateTypes[i];
			}
			
			var parseDate = getParseDate(value, type);
			y = parseInt(parseDate[0], 10);
			m = parseInt(parseDate[1], 10) - 1;
			d = parseInt(parseDate[2], 10);
			order = parseDate[4];
			
			//getDisplayDate只處理西元年,所以民國年或是兩位的西元年都要處理成四位
			if(type < 1000) {//處理西元年
				if(y < 2000) {//兩位的西元年
					if(order == 1) { //有可能會符合好幾種格式,所以一律只將使用者輸入當作ymd來處理
						//考慮輸入生日等情況,會輸入19xx年,避免多加2000年
						if(y < 1000) {
							y = y + 2000;
						}
					}
				}
			} else {//處理民國年
				y = y + 1911;
			}
			
			break;
		}
	}
	
	//將parse出來的年月日,用系統預設日期格式展現出來
	return getDisplayDate(y, m, d, defaultDateFormat);
}

/**
 * 手動轉換日期格式
 * @param value
 * @param type
 * @param targetType
 * @return
 * @author csiebug
 * @version 2008/9/12
 */
function transDate(value, type, targetType) {
	var parseDate = getParseDate(value, type);
	var y = parseInt(parseDate[0], 10);
	var m = parseInt(parseDate[1], 10) - 1;
	var d = parseInt(parseDate[2], 10);
	var yearType = parseDate[5];
	
	if(yearType == "2") {
		y = y + 2000;
	} else if(yearType == "3") {
		y = y + 1911;
	}
	
	//將parse出來的年月日,用指定的日期格式展現出來
	return getDisplayDate(y, m, d, targetType);
}

/**
 * 用固定日期格式解析字串
 * @param value
 * @param type
 * @return
 * @author csiebug
 * @version 2008/9/11
 */
function getParseDate(value, type) {
	var parseDate = new Array(6);
	
	var y = "0";
	var m = "0";
	var d = "0";
	
	var spliter = "";
	var order = "1"; /*1:ymd 2:mdy 3: dmy*/
	var yearType = "1"; /*1:四位年 2:兩位年 3:民國年*/
	
	var localYear = false;
	if(type >= 1000) {
		type = type / 1000;
		localYear = true;
	}
	
	if(type == 1) {
		spliter = "/";
		order = "2";
		yearType = "2";
	} else if(type == 2) {
		spliter = ".";
		order = "1";
		yearType = "2";
	} else if(type == 3) {
		spliter = "/";
		order = "3";
		yearType = "2";
	} else if(type == 4) {
		spliter = ".";
		order = "3";
		yearType = "2";
	} else if(type == 5) {
		spliter = "-";
		order = "3";
		yearType = "2";
	} else if(type == 10) {
		spliter = "-";
		order = "2";
		yearType = "2";
	} else if(type == 11) {
		spliter = "/";
		order = "1";
		yearType = "2";
	} else if(type == 12) {
		spliter = "";
		order = "1";
		yearType = "2";
	} else if(type == 23) {
		spliter = "-";
		order = "1";
		yearType = "1";
	} else if(type == 101) {
		spliter = "/";
		order = "2";
		yearType = "1";
	} else if(type == 102) {
		spliter = ".";
		order = "1";
		yearType = "1";
	} else if(type == 103) {
		spliter = "/";
		order = "3";
		yearType = "1";
	} else if(type == 104) {
		spliter = ".";
		order = "3";
		yearType = "1";
	} else if(type == 105) {
		spliter = "-";
		order = "3";
		yearType = "1";
	} else if(type == 110) {
		spliter = "-";
		order = "2";
		yearType = "1";
	} else if(type == 111) {
		spliter = "/";
		order = "1";
		yearType = "1";
	} else if(type == 112) {
		spliter = "";
		order = "1";
		yearType = "1";
	}
	
	if(localYear) {
		type = type * 1000;
		yearType = "3";
	}
	
	if(spliter != "" && order == 1) {
		var tmp = value;
		y = completeYear(tmp.substring(0, tmp.indexOf(spliter)), yearType);
		tmp = tmp.substring(tmp.indexOf(spliter) + 1, tmp.length);
		m = addZero(tmp.substring(0, tmp.indexOf(spliter)), 2);
		d = addZero(tmp.substring(tmp.indexOf(spliter) + 1, tmp.length), 2);
	} else if(spliter != "" && order == 2) {
		var tmp = value;
		m = addZero(tmp.substring(0, tmp.indexOf(spliter)), 2);
		tmp = tmp.substring(tmp.indexOf(spliter) + 1, tmp.length);
		d = addZero(tmp.substring(0, tmp.indexOf(spliter)), 2);
		y = completeYear(tmp.substring(tmp.indexOf(spliter) + 1, tmp.length), yearType);
	} else if(spliter != "" && order == 3) {
		var tmp = value;
		d = addZero(tmp.substring(0, tmp.indexOf(spliter)), 2);
		tmp = tmp.substring(tmp.indexOf(spliter) + 1, tmp.length);
		m = addZero(tmp.substring(0, tmp.indexOf(spliter)), 2);
		y = completeYear(tmp.substring(tmp.indexOf(spliter) + 1, tmp.length), yearType);
	} else if(spliter == "" && yearType == "1") {
		y = completeYear(value.substring(0, 4), yearType);
		m = addZero(value.substring(4, 6), 2);
		d = addZero(value.substring(6, 8), 2);
	} else if(spliter == "" && yearType == "2") {
		y = completeYear(value.substring(0, 2), yearType);
		m = addZero(value.substring(2, 4), 2);
		d = addZero(value.substring(4, 6), 2);
	} else if(spliter == "" && yearType == "3") {
		if(value.length == 6) {
			y = completeYear(value.substring(0, 2), yearType);
			m = addZero(value.substring(2, 4), 2);
			d = addZero(value.substring(4, 6), 2);
		} else if(value.length == 7) {
			y = completeYear(value.substring(0, 3), yearType);
			m = addZero(value.substring(3, 5), 2);
			d = addZero(value.substring(5, 7), 2);
		}
	}
	
	parseDate[0] = y;
	parseDate[1] = m;
	parseDate[2] = d;
	parseDate[3] = spliter;
	parseDate[4] = order;
	parseDate[5] = yearType;
	
	return parseDate;
}

/**
 * 檢查是否是合法日期
 * @param value
 * @param type
 * @return
 * @author csiebug
 * @version 2008/9/11
 */
function isValidDate(value, type) {
	var flag = true;
	
	var parseDate = getParseDate(value, type);
	var spliter = parseDate[3];
	var order = parseDate[4];
	var yearType = parseDate[5];
	
	var y = "0";
	var m = "0";
	var d = "0";
	
	if(spliter != "" && order == 1) {
		//沒有用分隔符號是錯誤的格式
		if(value.indexOf(spliter) == -1) {
			flag = false;
		} else {
			var tmp = value;
			var cnt = 0;
			while(tmp.indexOf(spliter) != -1) {
				if(cnt == 0) {
					y = tmp.substring(0, tmp.indexOf(spliter));
					tmp = tmp.substring(tmp.indexOf(spliter) + 1, tmp.length);
					cnt = cnt + 1;
				} else if(cnt == 1) {
					m = tmp.substring(0, tmp.indexOf(spliter));
					tmp = tmp.substring(tmp.indexOf(spliter) + 1, tmp.length);
					cnt = cnt + 1;
				}
			}
				
			//檢查是否有兩個分隔符號，並且是y/m/d格式
			if(cnt == 2 && tmp.length != 0) {
				d = tmp;
			} else {
				flag = false;
			}
				
			if(flag) {
				flag = isValidDateWithoutFormat(y, m, d, yearType);
			}
		}
	} else if(spliter != "" && order == 2) {
		//沒有用分隔符號是錯誤的格式
		if(value.indexOf(spliter) == -1) {
			flag = false;
		} else {
			var tmp = value;
			var cnt = 0;
			while(tmp.indexOf(spliter) != -1) {
				if(cnt == 0) {
					m = tmp.substring(0, tmp.indexOf(spliter));
					tmp = tmp.substring(tmp.indexOf(spliter) + 1, tmp.length);
					cnt = cnt + 1;
				} else if(cnt == 1) {
					d = tmp.substring(0, tmp.indexOf(spliter));
					tmp = tmp.substring(tmp.indexOf(spliter) + 1, tmp.length);
					cnt = cnt + 1;
				}
			}
				
			//檢查是否有兩個分隔符號，並且是y/m/d格式
			if(cnt == 2 && tmp.length != 0) {
				y = tmp;
			} else {
				flag = false;
			}
				
			if(flag) {
				flag = isValidDateWithoutFormat(y, m, d, yearType);
			}
		}
	} else if(spliter != "" && order == 3) {
		//沒有用分隔符號是錯誤的格式
		if(value.indexOf(spliter) == -1) {
			flag = false;
		} else {
			var tmp = value;
			var cnt = 0;
			while(tmp.indexOf(spliter) != -1) {
				if(cnt == 0) {
					d = tmp.substring(0, tmp.indexOf(spliter));
					tmp = tmp.substring(tmp.indexOf(spliter) + 1, tmp.length);
					cnt = cnt + 1;
				} else if(cnt == 1) {
					m = tmp.substring(0, tmp.indexOf(spliter));
					tmp = tmp.substring(tmp.indexOf(spliter) + 1, tmp.length);
					cnt = cnt + 1;
				}
			}
				
			//檢查是否有兩個分隔符號，並且是y/m/d格式
			if(cnt == 2 && tmp.length != 0) {
				y = tmp;
			} else {
				flag = false;
			}
				
			if(flag) {
				flag = isValidDateWithoutFormat(y, m, d, yearType);
			}
		}
	} else if(spliter == "" && yearType == "1") {
		if(value.length != 8) {
			flag = false;
		} else {
			y = value.substring(0, 4);
			m = value.substring(4, 6);
			d = value.substring(6, 8);	
		
			flag = isValidDateWithoutFormat(y, m, d, yearType);
		}
	} else if(spliter == "" && yearType == "2") {
		if(value.length != 6) {
			flag = false;
		} else {
			y = value.substring(0, 2);
			m = value.substring(2, 4);
			d = value.substring(4, 6);	
		
			flag = isValidDateWithoutFormat(y, m, d, yearType);
		}
	} else if(spliter == "" && yearType == "3") {
		if(value.length != 7) {
			flag = false;
		} else {
			y = value.substring(0, 3);
			m = value.substring(3, 5);
			d = value.substring(5, 7);	
		
			flag = isValidDateWithoutFormat(y, m, d, yearType);
		}
	}
	
	return flag;
}

/**
 * 檢查是否是合法日期
 * @param y
 * @param m
 * @param d
 * @param yearType
 * @return
 * @author csiebug
 * @version 2008/9/10
 */
function isValidDateWithoutFormat(y, m, d, yearType) {
	var flag = true;
	
	var realY = "";
	if(yearType == "2") {
		realY = "" + (parseInt(y, 10) + 2000);
	} else if(yearType == "3") {
		realY = "" + (parseInt(y, 10) + 1911);
	}
	
	//檢查年是否是正整數
	if(!isValidPositiveInteger(realY)) {
		flag = false;
	//檢查月是否是正整數並且月份在1-12之間
	} else if(!isValidPositiveInteger(m) || parseInt(m, 10) > 12 || parseInt(m, 10) == 0) {
		flag = false;
	//檢查日是否是正整數
	} else if(!isValidPositiveInteger(d)) {
		flag = false;
	} else {
		var days_of_month = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
		
		//考慮閏年
		if (parseInt(m, 10) == 2) {
			if (isBissextile(parseInt(realY, 10))) {
				days_of_month[1] = 29;
			}
		}
						
		//檢查日是否在本月份的日期範圍內
		if(parseInt(d, 10) > days_of_month[m - 1] || parseInt(d, 10) == 0) {
			flag = false;
		}
	}
	
	return flag;
}

/**
 * 檢查12小時制時間是否合法
 * @param value
 * @return
 * @author csiebug
 * @version 2011/2/12
 */
function isValidTime12(value) {
	var timePart = value.split(":");
	if(timePart.length == 2) {
		return (parseInt(timePart[0], 10) >= 0 && parseInt(timePart[0], 10) < 12 && parseInt(timePart[1], 10) >= 0 && parseInt(timePart[1], 10) < 60);
	} else if(timePart.length == 3) {
		return (parseInt(timePart[0], 10) >= 0 && parseInt(timePart[0], 10) < 12 && parseInt(timePart[1], 10) >= 0 && parseInt(timePart[1], 10) < 60 && parseInt(timePart[2], 10) >= 0 && parseInt(timePart[2], 10) < 60);
	} else {
		return false;
	}
}

/**
 * 檢查24小時制時間是否合法
 * @param value
 * @return
 * @author csiebug
 * @version 2011/2/12
 */
function isValidTime24(value) {
	var timePart = value.split(":");
	if(timePart.length == 2) {
		return (parseInt(timePart[0], 10) >= 0 && parseInt(timePart[0], 10) < 24 && parseInt(timePart[1], 10) >= 0 && parseInt(timePart[1], 10) < 60);
	} else if(timePart.length == 3) {
		return (parseInt(timePart[0], 10) >= 0 && parseInt(timePart[0], 10) < 24 && parseInt(timePart[1], 10) >= 0 && parseInt(timePart[1], 10) < 60 && parseInt(timePart[2], 10) >= 0 && parseInt(timePart[2], 10) < 60);
	} else {
		return false;
	}
}

/**
* 時和分若是固定兩位,幫忙補零
* @param value
* @param type
* @return
* @author csiebug
* @version 2011/2/12
*/
function completeHourMinuteSecond(value) {
	if(isValidTime12(value) || isValidTime24(value)) {
		var timePart = value.split(":");
		
		if(timePart.length == 2) {
			return addZero(timePart[0], 2) + ":" + addZero(timePart[1], 2);
		} else {
			return addZero(timePart[0], 2) + ":" + addZero(timePart[1], 2) + ":" + addZero(timePart[2], 2);
		}
	} else {
		return value;
	}
}

/**
 * 檢查身分證字號是否合法
 * @param value
 * @return
 * @author csiebug
 * @version 2008/7/4
 */
function isValidIDNO(value) {
	var flag = true;
	
	if(value.length != 10) {
		flag = false;
	} else {
		var checkCode = parseInt(value.substring(value.length - 1, value.length), 10);
		var array = new Array('10','11','12','13','14','15','16','17','34','18','19','20','21','22','35','23','24','25','26','27','28','29','30','31','32','33');
		var zipCode1 = "";
		var zipCode2 = "";
		
		if(value.substring(0, 1).toLowerCase() == 'a') {
			zipCode1 = array[0].substring(0,1);
			zipCode2 = array[0].substring(1,2);
		} else if(value.substring(0, 1).toLowerCase() == 'b') {
			zipCode1 = array[1].substring(0,1);
			zipCode2 = array[1].substring(1,2);
		} else if(value.substring(0, 1).toLowerCase() == 'c') {
			zipCode1 = array[2].substring(0,1);
			zipCode2 = array[2].substring(1,2);
		} else if(value.substring(0, 1).toLowerCase() == 'd') {
			zipCode1 = array[3].substring(0,1);
			zipCode2 = array[3].substring(1,2);
		} else if(value.substring(0, 1).toLowerCase() == 'e') {
			zipCode1 = array[4].substring(0,1);
			zipCode2 = array[4].substring(1,2);
		} else if(value.substring(0, 1).toLowerCase() == 'f') {
			zipCode1 = array[5].substring(0,1);
			zipCode2 = array[5].substring(1,2);
		} else if(value.substring(0, 1).toLowerCase() == 'g') {
			zipCode1 = array[6].substring(0,1);
			zipCode2 = array[6].substring(1,2);
		} else if(value.substring(0, 1).toLowerCase() == 'h') {
			zipCode1 = array[7].substring(0,1);
			zipCode2 = array[7].substring(1,2);
		} else if(value.substring(0, 1).toLowerCase() == 'i') {
			zipCode1 = array[8].substring(0,1);
			zipCode2 = array[8].substring(1,2);
		} else if(value.substring(0, 1).toLowerCase() == 'j') {
			zipCode1 = array[9].substring(0,1);
			zipCode2 = array[9].substring(1,2);
		} else if(value.substring(0, 1).toLowerCase() == 'k') {
			zipCode1 = array[10].substring(0,1);
			zipCode2 = array[10].substring(1,2);
		} else if(value.substring(0, 1).toLowerCase() == 'l') {
			zipCode1 = array[11].substring(0,1);
			zipCode2 = array[11].substring(1,2);
		} else if(value.substring(0, 1).toLowerCase() == 'm') {
			zipCode1 = array[12].substring(0,1);
			zipCode2 = array[12].substring(1,2);
		} else if(value.substring(0, 1).toLowerCase() == 'n') {
			zipCode1 = array[13].substring(0,1);
			zipCode2 = array[13].substring(1,2);
		} else if(value.substring(0, 1).toLowerCase() == 'o') {
			zipCode1 = array[14].substring(0,1);
			zipCode2 = array[14].substring(1,2);
		} else if(value.substring(0, 1).toLowerCase() == 'p') {
			zipCode1 = array[15].substring(0,1);
			zipCode2 = array[15].substring(1,2);
		} else if(value.substring(0, 1).toLowerCase() == 'q') {
			zipCode1 = array[16].substring(0,1);
			zipCode2 = array[16].substring(1,2);
		} else if(value.substring(0, 1).toLowerCase() == 'r') {
			zipCode1 = array[17].substring(0,1);
			zipCode2 = array[17].substring(1,2);
		} else if(value.substring(0, 1).toLowerCase() == 's') {
			zipCode1 = array[18].substring(0,1);
			zipCode2 = array[18].substring(1,2);
		} else if(value.substring(0, 1).toLowerCase() == 't') {
			zipCode1 = array[19].substring(0,1);
			zipCode2 = array[19].substring(1,2);
		} else if(value.substring(0, 1).toLowerCase() == 'u') {
			zipCode1 = array[20].substring(0,1);
			zipCode2 = array[20].substring(1,2);
		} else if(value.substring(0, 1).toLowerCase() == 'v') {
			zipCode1 = array[21].substring(0,1);
			zipCode2 = array[21].substring(1,2);
		} else if(value.substring(0, 1).toLowerCase() == 'w') {
			zipCode1 = array[22].substring(0,1);
			zipCode2 = array[22].substring(1,2);
		} else if(value.substring(0, 1).toLowerCase() == 'x') {
			zipCode1 = array[23].substring(0,1);
			zipCode2 = array[23].substring(1,2);
		} else if(value.substring(0, 1).toLowerCase() == 'y') {
			zipCode1 = array[24].substring(0,1);
			zipCode2 = array[24].substring(1,2);
		} else if(value.substring(0, 1).toLowerCase() == 'z') {
			zipCode1 = array[25].substring(0,1);
			zipCode2 = array[25].substring(1,2);
		} else {
			flag = false;
		}
		
		if(flag) {
			var sum = parseInt(zipCode1, 10) + (parseInt(zipCode2, 10) * 9);
			
			for(var i = 1; i <= 8 ; i++) {
				sum = sum + (parseInt(value.substring(i, i + 1), 10) * (9 - i));
			}
			
			var checkCode2 = sum % 10;
			
			if(checkCode2 != 0) {
				checkCode2 = 10 - checkCode2;
			}
			
			if(checkCode != checkCode2) {
				flag = false;
			}
		}
	}
		
	return flag;
}

/**
 * 檢查密碼是否符合密碼規則
 * @param value
 * @param type
 * @return
 * @author csiebug
 * @version 2008/9/12
 */
function isValidPassword(value, type) {
	var flag = true;
	
	if(type == "none") { //不限制密碼規則
	} else if(type == "win2003") { //文字,數字,特殊字元三種取兩種以上組合,長度大於等於6碼(windows2003密碼規則)
		flag = (passwordRule1(value, 1, 1, 1, 6, defaultPasswordMaxLength) || passwordRule2(value, 1, 1, 6, 0, defaultPasswordMaxLength) || passwordRule3(value, 1, 1, 6, defaultPasswordMaxLength) || passwordRule4(value, 1, 1, 6, defaultPasswordMaxLength));
	} else if(type == "TDM") { //文字,數字,特殊字元三種要有兩種超過兩碼,長度大於等於8碼(國科會專案的密碼規則)
		flag = (passwordRule1(value, 2, 2, 2, 8, defaultPasswordMaxLength) || passwordRule1(value, 1, 2, 2, 8, defaultPasswordMaxLength) || passwordRule1(value, 2, 1, 2, 8, defaultPasswordMaxLength) || passwordRule1(value, 2, 2, 1, 8, defaultPasswordMaxLength) || passwordRule2(value, 2, 2, 8, defaultPasswordMaxLength) || passwordRule3(value, 2, 2, 8, defaultPasswordMaxLength) || passwordRule4(value, 2, 2, 8, defaultPasswordMaxLength));
	}
	
	return flag;
}

/**
 * 檢查e-mail格式
 * @param value
 * @return
 * @author csiebug
 * @version 2008/9/21
 */
function isValidEmailAddress(value) {
	var flag = true;
	
	if(value.indexOf("@") == -1 || value.indexOf(".") == -1 || value.indexOf(" ") != -1) {
		flag = false;
	} else {
		var userid = value.substring(0, value.indexOf("@"));
		
		if(userid.trim() == "") {
			flag = false;
		}
		
		var domain = value.substring(value.indexOf("@") + 1, value.length);
		if(domain.indexOf("..") != -1) {
			flag = false;
		}
	}
	
	return flag;
}

/**
 * 檢查IP格式
 * @param value
 * @return
 * @author csiebug
 * @version 2011/6/1
 */
function isValidIPAddress(value) {
	var flag = true;
	
	//先將中間的空白去除
	var temp = replaceAll(value, " ", "");
	
	//去掉.之後看是不是純數字
	if(!isValid(replaceAll(temp, ".", ""))) {
		flag = false; 
	} else {
		var ipPart = temp.split(".");
		if(ipPart.length != 4) { //看看是否是四個數字組成
			flag = false;
		} else {
			for(var i = 0; i < ipPart.length; i++) {
				if(ipPart[i].trim() == "") { //如果某欄位是空白也是錯的
					flag = false;
					break;
				} else {
					var number = parseInt(ipPart[i].trim(), 10);
					if(number < 0 || number > 255) { //如果某欄位超過範圍也是錯的0~255
						flag = false;
						break;
					}
				}
			}
		}
	}
	
	return flag;
}

/**
 * 將IP中間空白字元去除,並排列整齊
 * @param value
 * @author csiebug
 * @version 2011/6/1
 */
function completeIPAddressForMask(value) {
	var result = "";
	
	var temp = replaceAll(value, " ", "");
	var ipPart = temp.split(".");
	
	for(var i = 0; i < ipPart.length; i++) {
		if(i != 0) {
			result = result + ".";
		}
		
		if(ipPart[i].length == 3) {
			result = result + ipPart[i];
		} else if(ipPart[i].length == 2) {
			result = result + " " + ipPart[i];
		} else if(ipPart[i].length == 1) {
			result = result + "  " + ipPart[i];
		}
	}
	
	return result;
}

//Address Format
//
//		IPv6 addresses have two logical parts: a 64-bit network prefix, and a 64-bit host address part. (The host address is often automatically generated from the interface MAC address.[34]) An IPv6 address is represented by 8 groups of 16-bit hexadecimal values separated by colons (:) shown as follows:
//
//		A typical example of an IPv6 address is
//
//		    2001:0db8:85a3:0000:0000:8a2e:0370:7334
//
//		The hexadecimal digits are case-insensitive.
//
//		The 128-bit IPv6 address can be abbreviated with the following rules:
//
//		    Rule one: Leading zeroes within a 16-bit value may be omitted. For example, the address fe80:0000:0000:0000:0202:b3ff:fe1e:8329 may be written as fe80:0:0:0:202:b3ff:fe1e:8329
//		    Rule two: A single occurrence of consecutive groups of zeroes within an address may be replaced by a double colon. For example, fe80:0:0:0:202:b3ff:fe1e:8329 becomes fe80::202:b3ff:fe1e:8329
//
//		A single IPv6 address can be represented in several different ways, such as 2001:db8::1:0:0:1 and 2001:0DB8:0:0:1::1. RFC 5952 recommends a canonical textual representation.
	
/**
 * 檢查IPv6格式
 * @param value
 * @return
 * @author csiebug
 * @version 2011/6/1
 */
function isValidIPv6Address(value) {
	var flag = true;
	var ipPart = value.toLowerCase().split(":");
	
	if(ipPart.length < 3 && ipPart.length > 8) {
		flag = false;
	} else {
		for(var i = 0; i < ipPart.length; i++) {
			if(ipPart[i].length <= 4) {
				for(var j = 0; j < ipPart[i].length; j++) {
					var char = ipPart[i].substring(j, j + 1);
					if(char != ' ') {
						var char16 = parseInt(ipPart[i].substring(j, j + 1), 16);
						
						if(!(char16 >= 0 && char16 <= 15)) {
							flag = false;
							break;
						}
					}
				}
			} else {
				flag = false;
				break;
			}
		}
	}
	
	return flag;
}

/**
 * 將IPv6中間空白字元去除,並排列整齊
 * @param value
 * @author csiebug
 * @version 2011/6/1
 */
function completeIPv6AddressForMask(value) {
	var result = "";
	
	var temp = replaceAll(value, " ", "");
	var ipPart = temp.split(":");
	
	for(var i = 0; i < ipPart.length; i++) {
		if(i != 0) {
			result = result + ":";
		}
		
		if(ipPart[i].length == 4) {
			result = result + ipPart[i];
		} else if(ipPart[i].length == 3) {
			result = result + " " + ipPart[i];
		} else if(ipPart[i].length == 2) {
			result = result + "  " + ipPart[i];
		} else if(ipPart[i].length == 1) {
			result = result + "   " + ipPart[i];
		}
	}
	
	return result;
}

/**
 * 月和日若是固定兩位,幫忙補零
 * @param value
 * @param type
 * @return
 * @author csiebug
 * @version 2008/9/11
 */
function completeMonthDay(value, type) {
	var parseDate = getParseDate(value, type);
	var y = parseDate[0];
	var m = parseDate[1];
	var d = parseDate[2];
	
	var spliter = parseDate[3];
	var order = parseDate[4]; 
	var yearType = parseDate[5]; 
	
	var strComplete = "";
	y = completeYear(y, yearType);
	m = addZero(m, 2);
	d = addZero(d, 2);
	
	if(spliter != "" && order == 1) {
		strComplete = y + spliter + m + spliter + d;
	} else if(spliter != "" && order == 2) {
		strComplete = m + spliter + d + spliter + y;
	} else if(spliter != "" && order == 3) {
		strComplete = d + spliter + m + spliter + y;
	} else if(spliter == "" && yearType == "1") {
		strComplete = y + spliter + m + spliter + d;
	} else if(spliter == "" && yearType == "2") {
		strComplete = y + spliter + m + spliter + d;
	} else if(spliter == "" && yearType == "3") {
		strComplete = y + spliter + m + spliter + d;
	}
	
	return strComplete;
}

/**
 * 年如果只有兩位,也需要幫忙補零
 * @param value
 * @param type
 * @return
 * @author csiebug
 * @version 2008/9/10
 */
function completeYear(value, type) {
	var strComplete = "";
	
	if(type == "2") {
		strComplete = addZero(value, 2);
	} else if(type == "3") {
		strComplete = addZero(value, 3);
	} else {
		strComplete = value;
	}
	
	return strComplete;
}

/**
 * 數字前面補零
 * @param value
 * @param fixLength
 * @return
 * @author csiebug
 * @version2008/7/9
 */
function addZero(value, fixLength) {
	var strValue = "" + value;
	var objLength = strValue.length;
	
	for(var i = 0; i < (fixLength - objLength); i++) {
		strValue = "0" + strValue;
	}
	
	return strValue;
}

/**
 * 數字比較v1>v2:true;v1<=v2:false
 * @param value1
 * @param value2
 * @return
 * @author csiebug
 * @version 2008/5/29
 */
function compareCurrency(value1, value2) {
	var flag = true;

	var v1 = replaceAll(value1, ",", "");
	var v2 = replaceAll(value2, ",", "");
	var v11 = "0";
	var v21 = "0";
	
	if(v1.indexOf(".") != -1) {
		v11 = v1.substring(v1.indexOf(".") + 1, v1.length);
		v1 = v1.substring(0, v1.indexOf("."));
	}
	if(v2.indexOf(".") != -1) {
		v21 = v2.substring(v2.indexOf(".") + 1, v2.length);
		v2 = v2.substring(0, v2.indexOf("."));
	}
	
	if(parseInt(v1, 10) < parseInt(v2, 10)) {
		flag = false;
	} else if(parseInt(v1, 10) == parseInt(v2, 10)) {
		if(v11 <= v21) {
			flag = false;
		}
	}
	
	return flag;
}

/**
 * 時間比較v1>v2:true;v1<=v2:false
 * @param value1
 * @param value2
 * @return
 * @author csiebug
 * @version 2011/2/18
 */
function compareTime12(mid1, value1, mid2, value2) {
	var v1 = value1;
	var v2 = value2;
	if(mid1 == "pm") {
		var timePart = value1.split(":");
		
		if(timePart.length == 2) {
			v1 = (parseInt(timePart[0], 10) + 12) + ":" + timePart[1];
		} else if(timePart.length == 3) {
			v1 = (parseInt(timePart[0], 10) + 12) + ":" + timePart[1] + ":" + timePart[2];
		}
	}
	if(mid2 == "pm") {
		var timePart = value2.split(":");
		
		if(timePart.length == 2) {
			v2 = (parseInt(timePart[0], 10) + 12) + ":" + timePart[1];
		} else if(timePart.length == 3) {
			v2 = (parseInt(timePart[0], 10) + 12) + ":" + timePart[1] + ":" + timePart[2];
		}
	}
	
	return compareTime(v1, v2);
}

/**
 * 時間比較v1>v2:true;v1<=v2:false
 * @param value1
 * @param value2
 * @return
 * @author csiebug
 * @version 2011/2/18
 */
function compareTime(value1, value2) {
	var v1 = completeHourMinuteSecond(value1);
	var v2 = completeHourMinuteSecond(value2);
	
	return v1 > v2;
}

/**
 * 日期比較v1>v2:true;v1<=v2:false
 * @param value1
 * @param value2
 * @param type
 * @return
 * @author csiebug
 * @version 2008/5/29
 */
function compareDate(value1, value2, type) {
	var flag = true;

	var y1 = "0";
	var y2 = "0";
	var m1 = "0";
	var m2 = "0";
	var d1 = "0";
	var d2 = "0";
	
	var spliter = "";
	var order;
	
	if(type >= 1000) {
		type = type / 1000;
	}
	
	if(type == 1) {
		spliter = "/";
		order = "2";
	} else if(type == 2) {
		spliter = ".";
		order = "1";
	} else if(type == 3) {
		spliter = "/";
		order = "3";
	} else if(type == 4) {
		spliter = ".";
		order = "3";
	} else if(type == 5) {
		spliter = "-";
		order = "3";
	} else if(type == 10) {
		spliter = "-";
		order = "2";
	} else if(type == 11) {
		spliter = "/";
		order = "1";
	} else if(type == 12) {
		spliter = "";
		order = "1";
	} else if(type == 23) {
		spliter = "-";
		order = "1";
	} else if(type == 101) {
		spliter = "/";
		order = "2";
	} else if(type == 102) {
		spliter = ".";
		order = "1";
	} else if(type == 103) {
		spliter = "/";
		order = "3";
	} else if(type == 104) {
		spliter = ".";
		order = "3";
	} else if(type == 105) {
		spliter = "-";
		order = "3";
	} else if(type == 110) {
		spliter = "-";
		order = "2";
	} else if(type == 111) {
		spliter = "/";
		order = "1";
	} else if(type == 112) {
		spliter = "";
		order = "1";
	}
	
	if(spliter != "" && order == 1) {
		y1 = value1.substring(0, value1.indexOf(spliter));
		var tmp = value1.substring(value1.indexOf(spliter) + 1, value1.length);
		m1 = tmp.substring(0, tmp.indexOf(spliter));
		d1 = tmp.substring(tmp.indexOf(spliter) + 1, tmp.length);
		y2 = value2.substring(0, value2.indexOf(spliter));
		tmp = value2.substring(value2.indexOf(spliter) + 1, value2.length);
		m2 = tmp.substring(0, tmp.indexOf(spliter));
		d2 = tmp.substring(tmp.indexOf(spliter) + 1, tmp.length);
	} else if(spliter != "" && order == 2) {
		m1 = value1.substring(0, value1.indexOf(spliter));
		var tmp = value1.substring(value1.indexOf(spliter) + 1, value1.length);
		d1 = tmp.substring(0, tmp.indexOf(spliter));
		y1 = tmp.substring(tmp.indexOf(spliter) + 1, tmp.length);
		m2 = value2.substring(0, value2.indexOf(spliter));
		tmp = value2.substring(value2.indexOf(spliter) + 1, value2.length);
		d2 = tmp.substring(0, tmp.indexOf(spliter));
		y2 = tmp.substring(tmp.indexOf(spliter) + 1, tmp.length);
	} else if(spliter != "" && order == 3) {
		d1 = value1.substring(0, value1.indexOf(spliter));
		var tmp = value1.substring(value1.indexOf(spliter) + 1, value1.length);
		m1 = tmp.substring(0, tmp.indexOf(spliter));
		y1 = tmp.substring(tmp.indexOf(spliter) + 1, tmp.length);
		d2 = value2.substring(0, value2.indexOf(spliter));
		tmp = value2.substring(value2.indexOf(spliter) + 1, value2.length);
		m2 = tmp.substring(0, tmp.indexOf(spliter));
		y2 = tmp.substring(tmp.indexOf(spliter) + 1, tmp.length);
	} else if(spliter == "" && order == 1) {
		y1 = value1.substring(0, 3);
		var tmp = value1.substring(3, value1.length);
		m1 = tmp.substring(0, 2);
		d1 = tmp.substring(2, tmp.length);
		y2 = value2.substring(0, 3);
		tmp = value2.substring(3, value2.length);
		m2 = tmp.substring(0, 2);
		d2 = tmp.substring(2, tmp.length);
	} else if(spliter == "" && order == 2) {
		m1 = value1.substring(0, 3);
		var tmp = value1.substring(3, value1.length);
		d1 = tmp.substring(0, 2);
		y1 = tmp.substring(2, tmp.length);
		m2 = value2.substring(0, 3);
		tmp = value2.substring(3, value2.length);
		d2 = tmp.substring(0, 2);
		y2 = tmp.substring(2, tmp.length);
	} else if(spliter == "" && order == 3) {
		d1 = value1.substring(0, 3);
		var tmp = value1.substring(3, value1.length);
		m1 = tmp.substring(0, 2);
		y1 = tmp.substring(2, tmp.length);
		d2 = value2.substring(0, 3);
		tmp = value2.substring(3, value2.length);
		m2 = tmp.substring(0, 2);
		y2 = tmp.substring(2, tmp.length);
	}
	
	if(parseInt(y1, 10) < parseInt(y2, 10)) {
		flag = false;
	} else if(parseInt(y1, 10) == parseInt(y2, 10)) {
		if(parseInt(m1, 10) < parseInt(m2, 10)) {
			flag = false;
		} else if(parseInt(d1, 10) <= parseInt(d2, 10)) {
			flag = false;
		}
	}
	
	return flag;
}

/**
 * 計算日期差多少天
 * @param startValue
 * @param endValue
 * @returns
 * @author csiebug
 * @version 2011/4/15
 */
function dateIntervalSize(startValue, endValue) {
	var startDate;
	var endDate;
	
	var flag = compareDate(startValue, endValue, defaultDateFormat);
	if(flag) {
		startDate = getParseDate(endValue, defaultDateFormat);
		endDate = getParseDate(startValue, defaultDateFormat);
	} else {
		startDate = getParseDate(startValue, defaultDateFormat);
		endDate = getParseDate(endValue, defaultDateFormat);
	}
	
	var startY = parseInt(startDate[0], 10);
	var startM = parseInt(startDate[1], 10);
	var startD = parseInt(startDate[2], 10);
	var endY = parseInt(endDate[0], 10);
	var endM = parseInt(endDate[1], 10);
	var endD = parseInt(endDate[2], 10);
	
	var days;
	if((endY - startY) == 0) {
		days = dateIntervalSizeWithSameYear(startY, startM, startD, endM, endD);
	} else if((endY - startY) == 1) {
		days = dateIntervalSizeWithSameYear(startY, startM, startD, 12, 31) + dateIntervalSizeWithSameYear(endY, 1, 1, endM, endD);
	} else {
		days = dateIntervalSizeWithSameYear(startY, startM, startD, 12, 31) + dateIntervalSizeWithSameYear(endY, 1, 1, endM, endD);
		
		for(var y = startY + 1; y < endY; y++) {
			if(isBissextile(y)) {
				days = days + 366;
			} else {
				days = days + 365;
			}
		}
	}
	
	if(flag) {
		return 0 - days;
	} else {
		return days;
	}
}

/**
 * 計算同年日期差多少天
 * @param year
 * @param startM
 * @param startD
 * @param endM
 * @param endD
 * @returns
 * @author csiebug
 * @version 2011/4/15
 */
function dateIntervalSizeWithSameYear(year, startM, startD, endM, endD) {
	if((endM - startM) == 0) {
		return dateIntervalSizeWithSameYearMonth(startD, endD);
	} else if((endM - startM) == 1) {
		return dateIntervalSizeWithSameYearMonth(startD, getMaxDate(year, startM)) + endD;
	} else {
		var days = dateIntervalSizeWithSameYearMonth(startD, getMaxDate(year, startM)) + endD;
		
		for(var m = startM + 1; m < endM; m++) {
			days = days + getMaxDate(year, m);
		}
		
		return days;
	}
}

/**
 * 計算同年同月日期差多少天
 * @param startD
 * @param endD
 * @returns {Number}
 * @author csiebug
 * @version 2011/4/15
 */
function dateIntervalSizeWithSameYearMonth(startD, endD) {
	return endD - startD;
}

/**
 * 取得currency
 * @param numString
 * @return
 * @author csiebug
 * @version 2008/5/29
 */
function getCurrency(numString) {
	var flagValid = isValidCurrency(numString);
	var value;
		
	if(flagValid) {
		value = replaceAll(numString, ",", "");
			
		// 如果是負數的話,先取出負號,再做下面的處理
		var negative = "";
		if (value.indexOf("-") != -1) {
			negative = "-";
			value = value.substring(value.indexOf("-") + 1, value.length);
		}
	
		// 小數如果是0,要去掉小數點和小數
		var decimal = "";
		if (value.indexOf(".") != -1) {
			decimal = value.substring(value.indexOf(".") + 1, value.length);
			value = value.substring(0, value.indexOf("."));
	
			if (parseInt(decimal, 10) != 0) {
				decimal = "." + decimal;
			} else {
				decimal = "";
			}
		}
	
		// 處理數字三位一個逗號
		value = replaceAll(value, ",", "");
		var value2 = "";
		for(var i = 0; i < value.length; i++) {
			if((i % 3) == 2) {
				value2 = "," + value.substring(value.length - 1 - i, value.length - i) + value2;
			} else {
				value2 = value.substring(value.length - 1 - i, value.length - i) + value2;
			}
		}
		if(value2.indexOf(",") == 0) {
			value2 = value2.substring(1, value2.length);
		}
	
		value = value2;
				
		value = negative + value + decimal;
	}

	return value;
}

/**
 * 取得字串的byte數
 */
function getBytes(str) {
	var re = /[\w!'\(\)\*\-\.~]/;	/*不會被編碼的字元*/
	var j = 0;	/*不會被encodeURIComponent編碼的字元數*2*/
	for(var i = 0; i < str.length; i++){
  		if(re.test(str.charAt(i))){
  			j += 2;	/*計算有多少個字不會被編碼*/
  		}
	}
	
	return (encodeURIComponent(str).length + j) / 3;
}

/**
 * AJAX呼叫
 * 傳中文時IE與其他瀏覽器會有不一樣的結果
 * IE把中文字當作big5,然後post back時轉碼為ISO-8859-1
 * 其他瀏覽器把中文字當作UTF-8,然後post back時轉碼為ISO-8859-1
 * 透過AJAX連,似乎不受server.xml的filter處理
 * 所以當server端接收到中文的request時,必須把字串由ISO-8859-1轉回big5/UTF-8
 * 所以此函數會多傳一個參數encode到server端,透過這個來轉中文字編碼
 * @param pageUrl
 * @return
 * @author csiebug
 * @version 2009/3/6
 */
function sendAJAXRequest(pageUrl) {
	// 初始化 XmlHttpRequest 物件
	var xmlRequest, e;
	
	try {
		xmlRequest = new XMLHttpRequest();
	} catch(e) {
	    try {
	      xmlRequest = new ActiveXObject("Microsoft.XMLHTTP");
	    } catch(e) {
	    }
	}
	
	// 準備 POST 同步請求
	xmlRequest.open("POST", pageUrl, false);
	xmlRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	//判斷哪種瀏覽器,給定server端需要轉換的charset
	var encodeType = "";
	if(window.navigator.appName.indexOf("Microsoft") != -1) {
		encodeType = "big5";
	} else if(window.navigator.appName.indexOf("Netscape") != -1) {
		encodeType = "UTF-8";
	} else {
		encodeType = "UTF-8";
	}
	xmlRequest.send("encode=" + encodeType);
	
	return xmlRequest.responseText;
}

/**
 * 四捨五入到第幾位
 * @param value
 * @param num
 * @return
 */
function roundToPoint(value, num){
  return parseInt(value * Math.pow(10, num) + 0.5, 10) / Math.pow(10, num);
}

/**
 * 自訂alert訊息視窗(timeout=0,套用系統預設timeout; timeout<0,則不設定timeout
 * @param obj
 * @param title
 * @param message
 * @param button
 * @param func
 * @param focus
 * @param timeout
 * @return
 * @author csiebug
 * @version 2009/12/8
 */
function openAlertDialog(obj, title, message, button, func, focus, timeout) {
	var alertDialog = document.getElementById("alertDialog");
	
	//要共用此html,做變數的替換
	if(alertDialogHtml == "") {
		alertDialogHtml = alertDialog.innerHTML;
	}
	
	$("#alertDialog").dialog( 'destroy' );
	
	//變數替換
	alertDialog.title = title;
	if(obj != null && focus != null) {
		alertDialog.innerHTML = replaceAll(replaceAll(replaceAll(replaceAll(replaceAll(alertDialogHtml, "var.message", decodeURIComponent(encodeURIComponent(message))), "var.button", button), "var.func", func), "var.objId", obj.id), "var.focusId", focus.id);
		
		$("#alertDialog").dialog({
			bgiframe: true,
			modal: true,
			close: function(ev, ui) { closeAlertDialogForCloseImageButton(obj.id, focus.id, func); }
		});
	} else if(obj == null && focus == null){
		alertDialog.innerHTML = replaceAll(replaceAll(replaceAll(replaceAll(replaceAll(alertDialogHtml, "var.message", decodeURIComponent(encodeURIComponent(message))), "var.button", button), "var.func", func), "var.objId", ""), "var.focusId", "");
		
		$("#alertDialog").dialog({
			bgiframe: true,
			modal: true,
			close: function(ev, ui) { closeAlertDialogForCloseImageButton('', '', func); }
		});
	} else if(obj == null) {
		alertDialog.innerHTML = replaceAll(replaceAll(replaceAll(replaceAll(replaceAll(alertDialogHtml, "var.message", decodeURIComponent(encodeURIComponent(message))), "var.button", button), "var.func", func), "var.objId", ""), "var.focusId", focus.id);
		
		$("#alertDialog").dialog({
			bgiframe: true,
			modal: true,
			close: function(ev, ui) { closeAlertDialogForCloseImageButton('', focus.id, func); }
		});
	} else {
		alertDialog.innerHTML = replaceAll(replaceAll(replaceAll(replaceAll(replaceAll(alertDialogHtml, "var.message", decodeURIComponent(encodeURIComponent(message))), "var.button", button), "var.func", func), "var.objId", obj.id), "var.focusId", "");
		
		$("#alertDialog").dialog({
			bgiframe: true,
			modal: true,
			close: function(ev, ui) { closeAlertDialogForCloseImageButton(obj.id, '', func); }
		});
	}
	
	alertDialog.style.display = "";
	
	$("#alertDialog").dialog('open');
	
	if(timeout > 0) {
		setTimeout("closeAlertDialog()", timeout);
	} else if(timeout == 0 && defaultAlertMessageTimeout > 0) {
		setTimeout("closeAlertDialog()", defaultAlertMessageTimeout);
	}
}

/**
 * 設定alertDialog的寬度
 * @param width
 * @return
 * @author csiebug
 * @version 2009/12/8
 */
function setAlertDialogWidth(width) {
	$("#alertDialog").dialog('option', 'width', width);
}

/**
 * 關閉自訂alert訊息視窗
 * @return
 * @author csiebug
 * @version 2009/12/8
 */
function closeAlertDialog() {
	$("#alertDialog").dialog('close');
}

/**
 * 關閉自訂alert訊息視窗
 * @param objId
 * @param focusId
 * @param func
 * @return
 * @author csiebug
 * @version 2009/12/8
 */
function closeAlertDialogForCloseImageButton(objId, focusId, func) {
	var alertDialog = document.getElementById("alertDialog");
	alertDialog.innerHTML = "";
	alertDialog.style.display = "none";
	
	if(objId != "") {
		document.getElementById(objId).value = "";
	}
	if(focusId != "") {
		var focusObj = document.getElementById(focusId);
		//為了ie的控制項focus問題,需要做以下這些判斷
		if(focusObj.disabled == false && focusObj.getAttribute("type") != "hidden" && focusObj.style.display != "none") {
			document.getElementById(focusId).focus();
		}
	}
	
	if(func != '') {
		if (objId != "") {
			eval(replaceAll(replaceAll(func, 'this', 'document.getElementById(\'' + objId + '\')'), '&quote', '\''));
		} else {
			eval(replaceAll(func, '&quote', '\''));
		}
	}
}

/**
 * 自訂confirm視窗
 * @param obj
 * @param title
 * @param message
 * @param ok
 * @param cancel
 * @param okFunc
 * @param cancelFunc
 * @param focus
 * @return
 * @author csiebug
 * @version 2009/12/8
 */
function openConfirmDialog(obj, title, message, ok, cancel, okFunc, cancelFunc, focus) {
	var confirmDialog = document.getElementById("confirmDialog");
	
	//要共用此html,做變數的替換
	if(confirmDialogHtml == "") {
		confirmDialogHtml = confirmDialog.innerHTML;
	}
	
	$("#confirmDialog").dialog( 'destroy' );
	
	//變數替換
	confirmDialog.title = title;
	if(obj != null && focus != null) {
		confirmDialog.innerHTML = replaceAll(replaceAll(replaceAll(replaceAll(replaceAll(replaceAll(replaceAll(confirmDialogHtml, "var.message", message), "var.ok", ok), "var.cancel", cancel), "var.funcOK", replaceAll(okFunc, "\'", "&quote")), "var.funcCancel", replaceAll(cancelFunc, "\'", "&quote")), "var.objId", obj.id), "var.focusId", focus.id);
		
		$("#confirmDialog").dialog({
			bgiframe: true,
			modal: true,
			close: function(ev, ui) { closeConfirmDialogForCloseImageButton(obj.id, focus.id, false, okFunc, cancelFunc); }
		});
	} else if(obj == null && focus == null){
		confirmDialog.innerHTML = replaceAll(replaceAll(replaceAll(replaceAll(replaceAll(replaceAll(replaceAll(confirmDialogHtml, "var.message", message), "var.ok", ok), "var.cancel", cancel), "var.funcOK", replaceAll(okFunc, "\'", "&quote")), "var.funcCancel", replaceAll(cancelFunc, "\'", "&quote")), "var.objId", ""), "var.focusId", "");
		
		$("#confirmDialog").dialog({
			bgiframe: true,
			modal: true,
			close: function(ev, ui) { closeConfirmDialogForCloseImageButton('', '', false, okFunc, cancelFunc); }
		});
	} else if(obj == null) {
		confirmDialog.innerHTML = replaceAll(replaceAll(replaceAll(replaceAll(replaceAll(replaceAll(replaceAll(confirmDialogHtml, "var.message", message), "var.ok", ok), "var.cancel", cancel), "var.funcOK", replaceAll(okFunc, "\'", "&quote")), "var.funcCancel", replaceAll(cancelFunc, "\'", "&quote")), "var.objId", ""), "var.focusId", focus.id);
		
		$("#confirmDialog").dialog({
			bgiframe: true,
			modal: true,
			close: function(ev, ui) { closeConfirmDialogForCloseImageButton('', focus.id, false, okFunc, cancelFunc); }
		});
	} else {
		confirmDialog.innerHTML = replaceAll(replaceAll(replaceAll(replaceAll(replaceAll(replaceAll(replaceAll(confirmDialogHtml, "var.message", message), "var.ok", ok), "var.cancel", cancel), "var.funcOK", replaceAll(okFunc, "\'", "&quote")), "var.funcCancel", replaceAll(cancelFunc, "\'", "&quote")), "var.objId", obj.id), "var.focusId", "");
		
		$("#confirmDialog").dialog({
			bgiframe: true,
			modal: true,
			close: function(ev, ui) { closeConfirmDialogForCloseImageButton(obj.id, '', false, okFunc, cancelFunc); }
		});
	}
	
	confirmDialog.style.display = "";
	
	$("#confirmDialog").dialog('open');
}

function setConfirmDialogWidth(width) {
	$("#confirmDialog").dialog('option', 'width', width);
}

/**
 * 關閉自訂confirm視窗
 * @param objId
 * @param focusId
 * @param flag
 * @param okFunc
 * @param cancelFunc
 * @return
 * @author csiebug
 * @version 2009/12/8
 */
function closeConfirmDialog(objId, focusId, flag, okFunc, cancelFunc) {
	closeConfirmDialogForCloseImageButton(objId, focusId, flag, okFunc, cancelFunc);
	
	$("#confirmDialog").dialog('close');
}

//closeEvent似乎會觸發此method執行兩次,所以需要避免錯誤
var closeDialogEventCount = 0;
/**
 * 關閉自訂confirm視窗
 * @param objId
 * @param focusId
 * @param flag
 * @param okFunc
 * @param cancelFunc
 * @return
 * @author csiebug
 * @version 2009/12/8
 */
function closeConfirmDialogForCloseImageButton(objId, focusId, flag, okFunc, cancelFunc) {
	if(closeDialogEventCount == 0) {
		closeDialogEventCount = 1;
		var confirmDialog = document.getElementById("confirmDialog");
		confirmDialog.innerHTML = "";
		confirmDialog.style.display = "none";
		
		//取消清空的動作
		//if(objId != "") {
		//	document.getElementById(objId).value = "";
		//}
		if(focusId != "") {
			var focusObj = document.getElementById(focusId);
			//為了ie的控制項focus問題,需要做以下這些判斷
			if(focusObj.disabled == false && focusObj.getAttribute("type") != "hidden" && focusObj.style.display != "none") {
				document.getElementById(focusId).focus();
			}
		}
		
		if(flag) {
			if(okFunc != '') {
				if (objId != "") {
					eval(replaceAll(replaceAll(okFunc, 'this', 'document.getElementById(\'' + objId + '\')'), '&quote', '\''));
				} else {
					eval(replaceAll(okFunc, '&quote', '\''));
				}
			}
		} else {
			if(cancelFunc != '') {
				if (objId != "") {
					eval(replaceAll(replaceAll(cancelFunc, 'this', 'document.getElementById(\'' + objId + '\')'), '&quote', '\''));
				} else {
					eval(replaceAll(cancelFunc, '&quote', '\''));
				}
			}
		}
	} else {
		closeDialogEventCount = 0;
	}
}

/**
 * 增加按下button有loading動畫的效果
 * @param button
 * @return
 * @author csiebug
 * @version 2009/12/7
 */
function loading(button) {
	button.style.position = "absolute";
	var waitButton = document.getElementById("waitButton");
	
	waitButton.style.position = "absolute";
	waitButton.style.top = button.offsetTop + "px";
	waitButton.style.left = button.offsetLeft + "px";
	
	button.style.display = "none";
	waitButton.style.display = "";
}

/**
 * loading block整頁
 * @author csiebug
 * @version 2010/11/26
 */
function loadingBlockPage(message, timeout) {
	if(loadingBlockPageHtml == "") {
		loadingBlockPageHtml = document.getElementById("waitingBlock").innerHTML;
	} else {
		document.getElementById("waitingBlock").innerHTML = loadingBlockPageHtml;
	}
	
	if(message.trim() != "") {
		document.getElementById("waitingBlock").innerHTML = message;
	}
	
	$.blockUI({ 
        message: $('#waitingBlock')
    });
	
	if(timeout > 0) {
		setTimeout($.unblockUI, timeout);
	}
}

/**
 * 網頁遮罩
 * @return
 */
function putMask() {
	mask = document.createElement("div");
    mask.id = "maskWindow";
    mask.style.display = "";
    mask.style.position = "absolute";
    mask.style.zIndex = "888";
    mask.style.filter = "alpha(opacity=40)";
    mask.style.MozOpacity = "0.4";
    mask.style.opacity = "0.4";
    mask.style.backgroundColor = "#333333";
    mask.style.top = 0 + "px";
    mask.style.left = 0 + "px";
    mask.style.height = window.screen.availHeight;
    mask.style.width = window.screen.availWidth;

    document.body.appendChild(mask);
}

/**
 * 移除網頁遮罩
 * @return
 */
function removeMask() {
    var tgtMask = document.getElementById("maskWindow");
    if (tgtMask) document.body.removeChild(tgtMask);
}

/**
 * 當form被submit的時候,可以使用來讓使用者無法重複submit
 * @return
 * @author csiebug
 * @version 2009/9/17
 */
function lockButton() {
//	putMask();
	
	var objInput = document.getElementsByTagName("input");
	var objButton = document.getElementsByTagName("button");
	var objImage = document.getElementsByTagName("image");
	var objImg = document.getElementsByTagName("img");
	
	for(var i = 0; i < objInput.length; i++) {
		var obj = objInput[i];
		
		if(obj.getAttribute("type") == "button") {
			obj.disabled = true;
		}
	}
	
	for(var i = 0; i < objButton.length; i++) {
		var obj = objButton[i];
		
		if(obj.id == null || obj.id == "") {
			obj.id = "button" + i;
		}
		
		if(obj.id != "alertDialogButton" && obj.id != "confirmDialogOK" && obj.id != "confirmDialogCancel") {
			obj.disabled = true;
		}
	}
	
	for(var i = 0; i < objImage.length; i++) {
		var obj = objImage[i];
		
		obj.disabled = true;
	}
	
	for(var i = 0; i < objImg.length; i++) {
		var obj = objImg[i];
		
		obj.disabled = true;
	}
}

/**
 * 當使用者用browser上面的回上一頁的時候，有可能會因為submit的時候使用lockButton而讓頁面無法操作
 * 所以要使用這個method解開button
 * 但這會把所有的button通通解開，有可能會有誤(有的頁面，可能因為權限關係，本來就需要disabled某些button)
 * @return
 * @author csiebug
 * @version 2010/5/17
 */
function unlockAllButton() {
//	putMask();
	
	var objInput = document.getElementsByTagName("input");
	var objButton = document.getElementsByTagName("button");
	var objImage = document.getElementsByTagName("image");
	var objImg = document.getElementsByTagName("img");
	
	for(var i = 0; i < objInput.length; i++) {
		var obj = objInput[i];
		
		if(obj.getAttribute("type") == "button") {
			obj.disabled = false;
		}
	}
	
	for(var i = 0; i < objButton.length; i++) {
		var obj = objButton[i];
		
		if(obj.id == null || obj.id == "") {
			obj.id = "button" + i;
		}
		
		if(obj.id != "alertDialogButton" && obj.id != "confirmDialogOK" && obj.id != "confirmDialogCancel") {
			obj.disabled = false;
		}
	}
	
	for(var i = 0; i < objImage.length; i++) {
		var obj = objImage[i];
		
		obj.disabled = false;
	}
	
	for(var i = 0; i < objImg.length; i++) {
		var obj = objImg[i];
		
		obj.disabled = false;
	}
}

/**
 * 解除某個button lock
 * @param id
 * @return
 * @author csiebug
 */
function unlockButton(id) {
	document.getElementById(id).disabled = false;
}

/**
 * 讓portlet window取得內容
 * @param portletWindowId
 * @param portletWindowMode
 * @return
 * @author csiebug
 * @version 2010/4/21
 */
function renderPortletContent(portletWindowId, portletWindowMode) {
	var portletContent = document.getElementById(portletWindowId + "_content");
	
	portletContent.innerHTML = sendAJAXRequest("dt?ActFlag=renderPortlet&PortletWindowMode=" + portletWindowMode + "&PortletWindowId=" + portletWindowId);
	
	//用ajax取回來的html內容,js和css的部分需要寫script動態載入
	//因為browser載入js和css的動作已經完成了
	var head = document.getElementsByTagName("head")[0];
	
	var portletJs = portletContent.getElementsByTagName("script");
	var portletCss = portletContent.getElementsByTagName("link");
	
	for(var i = 0; i < portletJs.length; i++) {
		var script = document.createElement("script");
	  	script.setAttribute('language', 'javascript');
	  	script.setAttribute('type', 'text/javascript');
	  	script.setAttribute('src', portletJs[i].getAttribute("src"));
	  	
	  	var done = false;
	  	script.onload = script.onreadystatechange = function(){
	  		if ( !done && (!this.readyState || this.readyState == "loaded" || this.readyState == "complete") ) {
	    		done = true;
	      		if(typeof callback === 'function')
	      			callback();
	      		if(this.tagName.toLowerCase() == 'script')
	      			document.getElementsByTagName('head')[0].removeChild(this);
	    	}
	  	};
		
		head.appendChild(script);
	}
	
	for(var i = 0; i < portletCss.length; i++) {
		var css = document.createElement("link");
	  	css.setAttribute('type', 'text/css');
		css.setAttribute('rel', 'stylesheet');
	  	css.setAttribute('href', portletCss[i].getAttribute("href"));
	  	
	  	var done = false;
	  	css.onload = css.onreadystatechange = function(){
	  		if ( !done && (!this.readyState || this.readyState == "loaded" || this.readyState == "complete") ) {
	    		done = true;
	      		if(typeof callback === 'function')
	      			callback();
	      		if(this.tagName.toLowerCase() == 'script')
	      			document.getElementsByTagName('head')[0].removeChild(this);
	    	}
	  	};
		
		head.appendChild(css);
	}
}

/**
 * portlet縮小與還原
 * @param portletId
 * @param minimizeMessage
 * @param unminimizeMessage
 * @return
 * @author csiebug
 * @version 2010/4/21
 */
function portletMinimized(portletId, minimizeMessage, unminimizeMessage) {
	var imageButton = document.getElementById(portletId + "_minimizeButton");
	var contentHTML = document.getElementById(portletId + "_minimizeContent");
	var content = document.getElementById(portletId + "_content");
	
	if(imageButton.getAttribute("alt") == minimizeMessage) {
		contentHTML.value = content.innerHTML;
		content.innerHTML = "";
		imageButton.setAttribute("alt", unminimizeMessage);
		imageButton.setAttribute("title", unminimizeMessage);
		imageButton.src = "images/unminimize_button.gif";
	} else {
		content.innerHTML = contentHTML.value;
		contentHTML.value = "";
		imageButton.setAttribute("alt", minimizeMessage);
		imageButton.setAttribute("title", minimizeMessage);
		imageButton.src = "images/minimize_button.gif";
	}
}

/**
 * portlet放大與還原
 * @param portletId
 * @param maximizeMessage
 * @param unmaximizeMessage
 * @return
 * @author csiebug
 * @version 2010/4/21
 */
function portletMaximized(portletId, maximizeMessage, unmaximizeMessage) {
	var imageButton = document.getElementById(portletId + "_maximizeButton");
	var portletWindow = document.getElementById(portletId);
	
	if(imageButton.getAttribute("alt") == maximizeMessage) {
		portletWindow.parentNode.style.width = "100%";
		imageButton.setAttribute("alt", unmaximizeMessage);
		imageButton.setAttribute("title", unmaximizeMessage);
		imageButton.src = "images/unmaximize_button.gif";
		
		var divs = document.getElementsByTagName("div");
		for(var i = 0; i < divs.length; i++) {
			var div = divs[i];
			if(div.className == "portlet" && div.id != portletId) {
				div.style.display = "none";
			}
		}
	} else {
		portletWindow.parentNode.style.width = "";
		imageButton.setAttribute("alt", maximizeMessage);
		imageButton.setAttribute("title", maximizeMessage);
		imageButton.src = "images/maximize_button.gif";
		
		var divs = document.getElementsByTagName("div");
		for(var i = 0; i < divs.length; i++) {
			var div = divs[i];
			if(div.className == "portlet") {
				div.style.display = "";
			}
		}
	}
}

/**
 * 開啟小月曆
 * @param obj
 * @param type
 * @param func
 * @param imagePath
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
 * @version 2009/3/13
 */
function openCalendar(obj, type, func, imagePath, warning, ok, messageInterval, message1Start, message1End, message2, message3, message4, message5, message6, message7, message8Start, message8End, message9Start, message9End, message10Start, message10End, message11, message12Start, message12End, message13, message14) {
	var alertDialog = document.getElementById("alertDialog");
	
	//如果有自訂alert視窗彈出,則要鎖定小月曆功能
	if(alertDialog.style.display == "none") {
		var calendar = document.getElementById(obj.id + "_calendar");
		
		//先看看目標小月曆的狀態是開啟還是關閉
		var openFlag = false;
		if(calendar.style.display == "none") {
			openFlag = true;
		}
		
		//先把所有的小月曆收起來(可能同個畫面已經開了其他小月曆)
		closeAllCalendar();
		
		//因為全部都關閉了包括目標小月曆,所以要根據之前記起來的狀態來開啟或關閉目標小月曆
		if(openFlag) {
			obj.style.position = "relative";
			
			calendar.style.display = "";
			//calendar.style.background = "lightgrey"
			calendar.style.width = 160;
			calendar.style.height = 170;
			
			//因為relative雖然可以設定位置,但是他佔空間,所以無法達到z-index可以做到重疊的效果 
			//取得relative(相對座標系統)的絕對座標
			var top = obj.offsetTop;
			var left = obj.offsetLeft;
			
			//再把它改為absolute(絕對座標系統),並把座標設上
			calendar.style.position = "absolute";
			calendar.style.top = (top + obj.offsetHeight) + "px";
			calendar.style.left = left + "px";
		
			var today = new Date();
			getCalendar(obj, today.getFullYear(), today.getMonth(), type, func, imagePath, warning, ok, messageInterval, message1Start, message1End, message2, message3, message4, message5, message6, message7, message8Start, message8End, message9Start, message9End, message10Start, message10End, message11, message12Start, message12End, message13, message14);
		}
	}
}

/**
 * 判斷是否是閏年
 * @param year
 * @return
 * @author csiebug
 * @version 2008/5/27
 */
function isBissextile(year) {
	var flag = false;
	
	if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
		flag = true;
	}
			
	return flag;
}

/**
 * 取得民國年
 * @param year
 * @return
 * @author csiebug
 * @version 2008/5/26
 */
function getLocalYear(year) {
	return (year - 1911);
}

/**
 * 取得顯示的年
 * @param year
 * @param type
 * @return
 * @author csiebug
 * @version 2008/7/4
 */
function getDisplayYear(year, type) {
	var strValue = "";
	
	if(type == 1 || type == 2 || type == 3 || type == 4 || type == 5 || type == 10 || type == 11) {
		strValue = ("" + year).substring(2, 4) + yearText;
	} else {
		if(type >= 1000) {
			strValue = getLocalYear(year) + yearText;
		} else {
			strValue = year + yearText;
		}
	}
	
	return strValue;
}

/**
 * 取得顯示的月
 * @param month
 * @param type
 * @return
 * @author csiebug
 * @version 2008/7/4
 */
function getDisplayMonth(month, type) {
	var strValue = "";
	
	if(month < 10) {
		strValue = "0";
	}
	strValue = strValue + month + monthText;
	
	return strValue;
}

/**
 * 取得顯示的年月組合
 * @param year
 * @param month
 * @param type
 * @return
 * @author csiebug
 * @version 2008/7/4
 */
function getDisplayYearMonth(year, month, type) {
	var strValue = "";
	
	strValue = getDisplayYear(year, type) + getDisplayMonth(month, type);
	
	return strValue;
}

/**
 * 根據type傳回不同日期格式字串
 * @param year
 * @param month
 * @param day
 * @param type
 * @return
 * @author csiebug
 * @version 2008/9/10
 */
function getDisplayDate(year, month, day, type) {
	var strValue = "";
	
	var localYear = false;
	if(type >= 1000) {
		type = type / 1000;
		localYear = true;
	}
	
	if(type == 1) {
		if(localYear) {
			strValue = (month + 1) + "/" + day + "/" + getLocalYear(year);
		} else {
			strValue = (month + 1) + "/" + day + "/" + ("" + year).substring(2, 4);
		}
	} else if(type == 2) {
		if(localYear) {
			strValue = getLocalYear(year) + "." + (month + 1) + "." + day;
		} else {
			strValue = ("" + year).substring(2, 4) + "." + (month + 1) + "." + day;
		}
	} else if(type == 3) {
		if(localYear) {
			strValue = day + "/" + (month + 1) + "/" + getLocalYear(year);
		} else {
			strValue = day + "/" + (month + 1) + "/" + ("" + year).substring(2, 4);
		}
	} else if(type == 4) {
		if(localYear) {
			strValue = day + "." + (month + 1) + "." + getLocalYear(year);
		} else {
			strValue = day + "." + (month + 1) + "." + ("" + year).substring(2, 4);
		}
	} else if(type == 5) {
		if(localYear) {
			strValue = day + "-" + (month + 1) + "-" + getLocalYear(year);
		} else {
			strValue = day + "-" + (month + 1) + "-" + ("" + year).substring(2, 4);
		}
	} else if(type == 10) {
		if(localYear) {
			strValue = (month + 1) + "-" + day + "-" + getLocalYear(year);
		} else {
			strValue = (month + 1) + "-" + day + "-" + ("" + year).substring(2, 4);
		}
	} else if(type == 11) {
		if(localYear) {
			strValue = getLocalYear(year) + "/" + (month + 1) + "/" + day;
		} else {
			strValue = ("" + year).substring(2, 4) + "/" + (month + 1) + "/" + day;
		}
	} else if(type == 12) {
		if(localYear) {
			strValue = getLocalYear(year) + "" + addZero(month + 1, 2) + "" + addZero(day, 2);
		} else {
			strValue = ("" + year).substring(2, 4) + "" + addZero(month + 1, 2) + "" + addZero(day, 2);
		}
	} else if(type == 23) {
		if(localYear) {
			strValue = getLocalYear(year) + "-" + (month + 1) + "-" + day;
		} else {
			strValue = year + "-" + (month + 1) + "-" + day;
		}
	} else if(type == 101) {
		if(localYear) {
			strValue = (month + 1) + "/" + day  + "/" + getLocalYear(year);
		} else {
			strValue = (month + 1) + "/" + day  + "/" + year;
		}
	} else if(type == 102) {
		if(localYear) {
			strValue = getLocalYear(year) + "." + (month + 1) + "." + day;
		} else {
			strValue = year + "." + (month + 1) + "." + day;
		}
	} else if(type == 103) {
		if(localYear) {
			strValue = day + "/" + (month + 1) + "/" + getLocalYear(year);
		} else {
			strValue = day + "/" + (month + 1) + "/" + year;
		}
	} else if(type == 104) {
		if(localYear) {
			strValue = day + "." + (month + 1) + "." + getLocalYear(year);
		} else {
			strValue = day + "." + (month + 1) + "." + year;
		}
	} else if(type == 105) {
		if(localYear) {
			strValue = day + "-" + (month + 1) + "-" + getLocalYear(year);
		} else {
			strValue = day + "-" + (month + 1) + "-" + year;
		}
	} else if(type == 110) {
		if(localYear) {
			strValue = (month + 1) + "-" + day  + "-" + getLocalYear(year);
		} else {
			strValue = (month + 1) + "-" + day  + "-" + year;
		}
	} else if(type == 111) {
		if(localYear) {
			strValue = getLocalYear(year) + "/" + (month + 1) + "/" + day;
		} else {
			strValue = year + "/" + (month + 1) + "/" + day;
		}
	} else if(type == 112) {
		if(localYear) {
			strValue = getLocalYear(year) + "" + addZero(month + 1, 2) + "" + addZero(day, 2);
		} else {
			strValue = year + "" + addZero(month + 1, 2) + "" + addZero(day, 2);
		}
	}
	
	if(localYear) {
		type = type * 1000;
	}
	
	strValue = completeMonthDay(strValue, type);
	
	return strValue;
}

/**
 * 產生小月曆HTML
 * @param obj
 * @param year
 * @param month
 * @param type
 * @param func
 * @param imagePath
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
 * @version 2009/3/13
 */
function getCalendar(obj, year, month, type, func, imagePath, warning, ok, messageInterval, message1Start, message1End, message2, message3, message4, message5, message6, message7, message8Start, message8End, message9Start, message9End, message10Start, message10End, message11, message12Start, message12End, message13, message14) {
	//處理跨年問題
	if(month == -1) {
		year = year - 1;
		month = 11;
	} else if(month == 12) {
		year = year + 1;
		month = 0;
	}
	
	var todayDisplayFlag = false;
	var today = new Date();
	var day = today.getDate();
	if(today.getFullYear() == year && today.getMonth() == month) {
		todayDisplayFlag = true;
	}
	
	//清除之前的小月曆
	var calendar = document.getElementById(obj.id + "_calendar");
	calendar.innerHTML = "";
	
	var strHtml = "";
	
	var firstday = new Date(year, month, 1);
	
	var displayDay = new Array('日','一','二','三','四','五','六');
	
	//產生小月曆主體
	strHtml = strHtml + "<p align='center'>";
	strHtml = strHtml + "<table>";
	strHtml = strHtml + "<tr>";
	strHtml = strHtml + "<td colspan='7' align='center'>";
	
	//月和年可以個別跳耀
	/* 使用圖檔來做跳躍年月的箭頭
	strHtml = strHtml + "<img src=\"" + imagePath + "/calendar_pre.gif\" onClick=\"getCalendar(document.getElementById('" + obj.id + "')," + (year - 1) + "," + month + ", " + type + ", '" + func + "', '" + imagePath + "', '" + warning + "', '" + ok + "', '" + messageInterval + "', '" + message1Start + "', '" + message1End + "', '" + message2 + "', '" + message3 + "', '" + message4 + "', '" + message5 + "', '" + message6 + "', '" + message7 + "', '" + message8Start + "', '" + message8End + "', '" + message9Start + "', '" + message9End + "', '" + message10Start + "', '" + message10End + "', '" + message11 + "', '" + message12Start + "', '" + message12End + "', '" + message13 + "', '" + message14 + "');\">";
	strHtml = strHtml + "  " + getDisplayYear(year, type);
	strHtml = strHtml + "<img src=\"" + imagePath + "/calendar_next.gif\" onClick=\"getCalendar(document.getElementById('" + obj.id + "')," + (year + 1) + "," + month + ", " + type + ", '" + func + "', '" + imagePath + "', '" + warning + "', '" + ok + "', '" + messageInterval + "', '" + message1Start + "', '" + message1End + "', '" + message2 + "', '" + message3 + "', '" + message4 + "', '" + message5 + "', '" + message6 + "', '" + message7 + "', '" + message8Start + "', '" + message8End + "', '" + message9Start + "', '" + message9End + "', '" + message10Start + "', '" + message10End + "', '" + message11 + "', '" + message12Start + "', '" + message12End + "', '" + message13 + "', '" + message14 + "');\">";
	strHtml = strHtml + "  ";
	strHtml = strHtml + "<img src=\"" + imagePath + "/calendar_pre.gif\" onClick=\"getCalendar(document.getElementById('" + obj.id + "')," + year + "," + (month - 1) + ", " + type + ", '" + func + "', '" + imagePath + "', '" + warning + "', '" + ok + "', '" + messageInterval + "', '" + message1Start + "', '" + message1End + "', '" + message2 + "', '" + message3 + "', '" + message4 + "', '" + message5 + "', '" + message6 + "', '" + message7 + "', '" + message8Start + "', '" + message8End + "', '" + message9Start + "', '" + message9End + "', '" + message10Start + "', '" + message10End + "', '" + message11 + "', '" + message12Start + "', '" + message12End + "', '" + message13 + "', '" + message14 + "');\">";
	strHtml = strHtml + "  <li>" + getDisplayMonth(month + 1, type) + "</li>  ";
	strHtml = strHtml + "<img src=\"" + imagePath + "/calendar_next.gif\" onClick=\"getCalendar(document.getElementById('" + obj.id + "')," + year + "," + (month + 1) + ", " + type + ", '" + func + "', '" + imagePath + "', '" + warning + "', '" + ok + "', '" + messageInterval + "', '" + message1Start + "', '" + message1End + "', '" + message2 + "', '" + message3 + "', '" + message4 + "', '" + message5 + "', '" + message6 + "', '" + message7 + "', '" + message8Start + "', '" + message8End + "', '" + message9Start + "', '" + message9End + "', '" + message10Start + "', '" + message10End + "', '" + message11 + "', '" + message12Start + "', '" + message12End + "', '" + message13 + "', '" + message14 + "');\">";
	*/
	//使用css來做跳躍年月的箭頭
	strHtml = strHtml + "<ul class=\"pager\"><li class=\"prev\" title=\"Previous Page\" onClick=\"getCalendar(document.getElementById('" + obj.id + "')," + (year - 1) + "," + month + ", " + type + ", '" + func + "', '" + imagePath + "', '" + warning + "', '" + ok + "', '" + messageInterval + "', '" + message1Start + "', '" + message1End + "', '" + message2 + "', '" + message3 + "', '" + message4 + "', '" + message5 + "', '" + message6 + "', '" + message7 + "', '" + message8Start + "', '" + message8End + "', '" + message9Start + "', '" + message9End + "', '" + message10Start + "', '" + message10End + "', '" + message11 + "', '" + message12Start + "', '" + message12End + "', '" + message13 + "', '" + message14 + "');\"><div class=\"l-arrow\"><div class=\"inner\"></div></div></li>";
	strHtml = strHtml + "  <li>" + getDisplayYear(year, type) + "</li>  ";
	strHtml = strHtml + "<li class=\"prev\" title=\"Next Page\" onClick=\"getCalendar(document.getElementById('" + obj.id + "')," + (year + 1) + "," + month + ", " + type + ", '" + func + "', '" + imagePath + "', '" + warning + "', '" + ok + "', '" + messageInterval + "', '" + message1Start + "', '" + message1End + "', '" + message2 + "', '" + message3 + "', '" + message4 + "', '" + message5 + "', '" + message6 + "', '" + message7 + "', '" + message8Start + "', '" + message8End + "', '" + message9Start + "', '" + message9End + "', '" + message10Start + "', '" + message10End + "', '" + message11 + "', '" + message12Start + "', '" + message12End + "', '" + message13 + "', '" + message14 + "');\"><div class=\"r-arrow\"><div class=\"inner\"></div></div></li>";
	strHtml = strHtml + "  ";
	strHtml = strHtml + "<li class=\"prev\" title=\"Previous Page\" onClick=\"getCalendar(document.getElementById('" + obj.id + "')," + year + "," + (month - 1) + ", " + type + ", '" + func + "', '" + imagePath + "', '" + warning + "', '" + ok + "', '" + messageInterval + "', '" + message1Start + "', '" + message1End + "', '" + message2 + "', '" + message3 + "', '" + message4 + "', '" + message5 + "', '" + message6 + "', '" + message7 + "', '" + message8Start + "', '" + message8End + "', '" + message9Start + "', '" + message9End + "', '" + message10Start + "', '" + message10End + "', '" + message11 + "', '" + message12Start + "', '" + message12End + "', '" + message13 + "', '" + message14 + "');\"><div class=\"l-arrow\"><div class=\"inner\"></div></div></li>";
	strHtml = strHtml + "  <li>" + getDisplayMonth(month + 1, type) + "</li>  ";
	strHtml = strHtml + "<li class=\"prev\" title=\"Next Page\" onClick=\"getCalendar(document.getElementById('" + obj.id + "')," + year + "," + (month + 1) + ", " + type + ", '" + func + "', '" + imagePath + "', '" + warning + "', '" + ok + "', '" + messageInterval + "', '" + message1Start + "', '" + message1End + "', '" + message2 + "', '" + message3 + "', '" + message4 + "', '" + message5 + "', '" + message6 + "', '" + message7 + "', '" + message8Start + "', '" + message8End + "', '" + message9Start + "', '" + message9End + "', '" + message10Start + "', '" + message10End + "', '" + message11 + "', '" + message12Start + "', '" + message12End + "', '" + message13 + "', '" + message14 + "');\"><div class=\"r-arrow\"><div class=\"inner\"></div></div></li></ul>";
	
	strHtml = strHtml + "</td>";
	strHtml = strHtml + "</tr>";

	//產生星期
	strHtml = strHtml + "<tr>";
	for(var i = 0; i < 7; i++) {
		strHtml = strHtml + "<td>";
		
		if(i == 0) {
			strHtml = strHtml + "<font color='red'>";
		}
		if(type >= 1000) {
			strHtml = strHtml + displayDay[i];
		} else {
			strHtml = strHtml + displayDayForLocale[i];
		}
		if(i == 0) {
			strHtml = strHtml + "</font>";
		}
		
		strHtml = strHtml + "</td>";
	}
	strHtml = strHtml + "</tr>";
	
	var cntLine = 5;
	var calendar = document.getElementById(obj.id + "_calendar");
	
	if((firstday.getDay() + getMaxDate(year, month)) > 35) {
		cntLine = 6;
		calendar.style.height = 190;
	} else if((firstday.getDay() + getMaxDate(year, month)) > 28) {
		cntLine = 5;
		calendar.style.height = 170;
	} else {
		cntLine = 4;
		calendar.style.height = 150;
	}
	
	var currentDay = 1;
	for(var i = 0; i < cntLine; i++) {
		strHtml = strHtml + "<tr>";
		
		for(var j = 0; j < 7; j++) {
			strHtml = strHtml + "<td>";
			
			if(i == 0) {
				if(firstday.getDay() == j) {
					strHtml = strHtml + "<a href=\"JavaScript:clickDate(document.getElementById('" + obj.id + "'), '" + getDisplayDate(year, month, currentDay, type) + "', '" + func + "', '" + warning + "', '" + ok + "', '" + messageInterval + "', '" + message1Start + "', '" + message1End + "', '" + message2 + "', '" + message3 + "', '" + message4 + "', '" + message5 + "', '" + message6 + "', '" + message7 + "', '" + message8Start + "', '" + message8End + "', '" + message9Start + "', '" + message9End + "', '" + message10Start + "', '" + message10End + "', '" + message11 + "', '" + message12Start + "', '" + message12End + "', '" + message13 + "', '" + message14 + "');\">";
					if(todayDisplayFlag && currentDay == day) {
						strHtml = strHtml + "<font color=\"red\">";
					}
					strHtml = strHtml + currentDay;
					if(todayDisplayFlag && currentDay == day) {
						strHtml = strHtml + "</font>";
					}
					strHtml = strHtml + "</a>";
					currentDay = currentDay + 1;
				} else if(currentDay > 1) {
					strHtml = strHtml + "<a href=\"JavaScript:clickDate(document.getElementById('" + obj.id + "'), '" + getDisplayDate(year, month, currentDay, type) + "', '" + func + "', '" + warning + "', '" + ok + "', '" + messageInterval + "', '" + message1Start + "', '" + message1End + "', '" + message2 + "', '" + message3 + "', '" + message4 + "', '" + message5 + "', '" + message6 + "', '" + message7 + "', '" + message8Start + "', '" + message8End + "', '" + message9Start + "', '" + message9End + "', '" + message10Start + "', '" + message10End + "', '" + message11 + "', '" + message12Start + "', '" + message12End + "', '" + message13 + "', '" + message14 + "');\">";
					if(todayDisplayFlag && currentDay == day) {
						strHtml = strHtml + "<font color=\"red\">";
					}
					strHtml = strHtml + currentDay;
					if(todayDisplayFlag && currentDay == day) {
						strHtml = strHtml + "</font>";
					}
					strHtml = strHtml + "</a>";
					currentDay = currentDay + 1;
				}
			} else {
				if(currentDay <= getMaxDate(year, month)) {
					strHtml = strHtml + "<a href=\"JavaScript:clickDate(document.getElementById('" + obj.id + "'), '" + getDisplayDate(year, month, currentDay, type) + "', '" + func + "', '" + warning + "', '" + ok + "', '" + messageInterval + "', '" + message1Start + "', '" + message1End + "', '" + message2 + "', '" + message3 + "', '" + message4 + "', '" + message5 + "', '" + message6 + "', '" + message7 + "', '" + message8Start + "', '" + message8End + "', '" + message9Start + "', '" + message9End + "', '" + message10Start + "', '" + message10End + "', '" + message11 + "', '" + message12Start + "', '" + message12End + "', '" + message13 + "', '" + message14 + "');\">";
					if(todayDisplayFlag && currentDay == day) {
						strHtml = strHtml + "<font color=\"red\">";
					}
					strHtml = strHtml + currentDay;
					if(todayDisplayFlag && currentDay == day) {
						strHtml = strHtml + "</font>";
					}
					strHtml = strHtml + "</a>";
					currentDay = currentDay + 1;
				}
			}
			
			strHtml = strHtml + "</td>";
		}
		
		strHtml = strHtml + "</tr>";
	}
	
	strHtml = strHtml + "</table>";
	
	calendar.innerHTML = strHtml;
}

/**
 * 按下某個日期將結果傳回控制項
 * @param obj
 * @param dateValue
 * @param func
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
 * @version 2009/3/13
 */
function clickDate(obj, dateValue, func, warning, ok, messageInterval, message1Start, message1End, message2, message3, message4, message5, message6, message7, message8Start, message8End, message9Start, message9End, message10Start, message10End, message11, message12Start, message12End, message13, message14) {
	obj.value = dateValue;
	closeCalendar(obj);
	checkDataType(obj, warning, ok, messageInterval, message1Start, message1End, message2, message3, message4, message5, message6, message7, message8Start, message8End, message9Start, message9End, message10Start, message10End, message11, message12Start, message12End, message13, message14);
	
	if(func != '') {
		eval(replaceAll(replaceAll(func, 'this', 'document.getElementById(\'' + obj.id + '\')'), '&quote', '\''));
	}
}

/**
 * 把小月曆收起來
 * @param obj
 * @return
 * @author csiebug
 * @version 2008/5/27
 */
function closeCalendar(obj) {
	var calendar = document.getElementById(obj.id + "_calendar");
	calendar.innerHTML = "";
	calendar.style.display = "none";
	obj.style.position = "static";
}

/**
 * 把所有的小月曆收起來
 * @return
 * @author csiebug
 * @version 2008/12/16
 */
function closeAllCalendar() {
	var divs = document.getElementsByTagName("div");
	
	for(var i = 0; i < divs.length; i++) {
		var div = divs[i];
		
		if(div.id.substring(div.id.length - 9, div.id.length) == "_calendar") {
			div.innerHTML = "";
			div.style.display = "none";
			
			var text = document.getElementById(div.id.substring(0, div.id.length - 9));
			text.style.position = "static";
		}
	}
}

/**
 * 取得當月最大天數
 * @param year
 * @param month
 * @return
 * @author csiebug
 * @version 2008/8/11
 */
function getMaxDate(year, month) {
	var days_of_month = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
	
	//考慮閏年
	if (month == 1) {
		if (isBissextile(year)) {
			return 29;
		} else {
			return 28;
		}
	} else {
		return days_of_month[month];
	}
}

/**
 * 顯示或隱藏color picker
 * @param controlId
 * @return
 * @author csiebug
 * @version 2011/1/27
 */
function showColorPicker(controlId) {
	var alertDialog = document.getElementById("alertDialog");
	
	//如果有自訂alert視窗彈出,則要鎖定色盤功能
	if(alertDialog.style.display == "none") {
		$('#' + controlId + '_colorPicker').farbtastic('#' + controlId);
		
		var colorPicker = document.getElementById(controlId + "_colorPicker");
		
		//先看看目標色盤的狀態是開啟還是關閉
		var openFlag = false;
		if(colorPicker.style.display == "none") {
			openFlag = true;
		}
		
		//先把所有的色盤收起來(可能同個畫面已經開了其他色盤)
		hideAllColorPicker();
		
		//因為全部都關閉了包括目標色盤,所以要根據之前記起來的狀態來開啟或關閉目標色盤
		if(openFlag) {
			var control = document.getElementById(controlId);
			control.style.position = "relative";
			
			//因為relative雖然可以設定位置,但是他佔空間,所以無法達到z-index可以做到重疊的效果 
			//取得relative(相對座標系統)的絕對座標
			var top = control.offsetTop;
			var left = control.offsetLeft;
			
			//再把它改為absolute(絕對座標系統),並把座標設上
			colorPicker.style.position = "absolute";
			colorPicker.style.top = (top + control.offsetHeight) + "px";
			colorPicker.style.left = left + "px";
			
			$('#' + controlId + "_colorPicker").show();
		}
	}
}

/**
 * 隱藏color picker
 * @param controlId
 * @return
 * @author csiebug
 * @version 2011/1/27
 */
function hideColorPicker(controlId) {
	$('#' + controlId + '_colorPicker').farbtastic('#' + controlId);
	$('#' + controlId + "_colorPicker").hide();
}

/**
 * 把所有的色盤收起來
 * @return
 * @author csiebug
 * @version 2010/1/27
 */
function hideAllColorPicker() {
	var divs = document.getElementsByTagName("div");
	
	for(var i = 0; i < divs.length; i++) {
		var div = divs[i];
		
		if(div.id.substring(div.id.length - 12, div.id.length) == "_colorPicker") {
			div.style.display = "none";
			
			var text = document.getElementById(div.id.substring(0, div.id.length - 12));
			text.style.position = "static";
		}
	}
}

//密碼規則
//數字需要count1以上,文字需要count2以上,特殊字元需要count3以上,最小長度需要minlength以上,最大長度maxlength(0為不限制)以下
//author: csiebug
//version: 2008/9/12
function passwordRule1(value, count1, count2, count3, minlength, maxlength) {
	var flag = true;
	
	var c1 = 0;
	var c2 = 0;
	var c3 = 0;

	for(var i = 0; i < value.length; i++){
		var charx = value.charAt(i);

		if(/[0-9]/.test(charx)){
			c1++;
		}else if(/[a-zA-Z]/.test(charx)){
			c2++;
		}else if(/\W|_/.test(charx)){
			c3++;
		}
	}

	if(c1 < count1){
		flag = false;
	} else if(c2 < count2){
		flag = false;
	} else if(c3 < count3){
		flag = false;
	}
	
	if((c1 + c2 + c3) < minlength){
		flag = false;
	}
	
	if(maxlength != 0 && (c1 + c2 + c3) > maxlength) {
		flag = false;
	}
	
	return flag;
}

//不能有數字,文字需要count2以上,特殊字元需要count3以上,最小長度需要minlength以上,最大長度maxlength(0為不限制)以下
//author: csiebug
//version: 2008/9/12
function passwordRule2(value, count2, count3, minlength, maxlength) {
	var flag = true;
	
	var c1 = 0;
	var c2 = 0;
	var c3 = 0;

	for(var i = 0; i < value.length; i++){
		var charx = value.charAt(i);

		if(/[0-9]/.test(charx)){
			c1++;
		}else if(/[a-zA-Z]/.test(charx)){
			c2++;
		}else if(/\W|_/.test(charx)){
			c3++;
		}
	}

	if(c1 > 0){
		flag = false;
	} else if(c2 < count2){
		flag = false;
	} else if(c3 < count3){
		flag = false;
	}
	
	if((c1 + c2 + c3) < minlength){
		flag = false;
	}
	
	if(maxlength != 0 && (c1 + c2 + c3) > maxlength) {
		flag = false;
	}
	
	return flag;
}

//不能有文字,數字需要count1以上,特殊字元需要count3以上,最小長度需要minlength以上,最大長度maxlength(0為不限制)以下
//author: csiebug
//version: 2008/9/12
function passwordRule3(value, count1, count3, minlength, maxlength) {
	var flag = true;
	
	var c1 = 0;
	var c2 = 0;
	var c3 = 0;

	for(var i = 0; i < value.length; i++){
		var charx = value.charAt(i);

		if(/[0-9]/.test(charx)){
			c1++;
		}else if(/[a-zA-Z]/.test(charx)){
			c2++;
		}else if(/\W|_/.test(charx)){
			c3++;
		}
	}

	if(c1 < count1){
		flag = false;
	} else if(c2 > 0){
		flag = false;
	} else if(c3 < count3){
		flag = false;
	}
	
	if((c1 + c2 + c3) < minlength){
		flag = false;
	}
	
	if(maxlength != 0 && (c1 + c2 + c3) > maxlength) {
		flag = false;
	}
	
	return flag;
}

//不能有特殊字元,數字需要count1以上,文字需要count2以上,最小長度需要minlength以上,最大長度maxlength(0為不限制)以下
//author: csiebug
//version: 2008/9/12
function passwordRule4(value, count1, count2, minlength, maxlength) {
	var flag = true;
	
	var c1 = 0;
	var c2 = 0;
	var c3 = 0;

	for(var i = 0; i < value.length; i++){
		var charx = value.charAt(i);

		if(/[0-9]/.test(charx)){
			c1++;
		}else if(/[a-zA-Z]/.test(charx)){
			c2++;
		}else if(/\W|_/.test(charx)){
			c3++;
		}
	}

	if(c1 < count1){
		flag = false;
	} else if(c2 < count2){
		flag = false;
	} else if(c3 > 0){
		flag = false;
	}
	
	if((c1 + c2 + c3) < minlength){
		flag = false;
	}
	
	if(maxlength != 0 && (c1 + c2 + c3) > maxlength) {
		flag = false;
	}
	
	return flag;
}

//只允許數字密碼,最小長度需要minlength以上,最大長度maxlength(0為不限制)以下
//author: csiebug
//version: 2008/9/12
function passwordRule5(value, minlength, maxlength) {
	var flag = true;
	
	var c1 = 0;
	var c2 = 0;
	var c3 = 0;

	for(var i = 0; i < value.length; i++){
		var charx = value.charAt(i);

		if(/[0-9]/.test(charx)){
			c1++;
		}else if(/[a-zA-Z]/.test(charx)){
			c2++;
		}else if(/\W|_/.test(charx)){
			c3++;
		}
	}

	if(c2 > 0){
		flag = false;
	} else if(c3 > 0){
		flag = false;
	}
	
	if((c1 + c2 + c3) < minlength){
		flag = false;
	}
	
	if(maxlength != 0 && (c1 + c2 + c3) > maxlength) {
		flag = false;
	}
	
	return flag;
}

//只允許文字密碼,最小長度需要minlength以上,最大長度maxlength(0為不限制)以下
//author: csiebug
//version: 2008/9/12
function passwordRule6(value, minlength, maxlength) {
	var flag = true;
	
	var c1 = 0;
	var c2 = 0;
	var c3 = 0;

	for(var i = 0; i < value.length; i++){
		var charx = value.charAt(i);

		if(/[0-9]/.test(charx)){
			c1++;
		}else if(/[a-zA-Z]/.test(charx)){
			c2++;
		}else if(/\W|_/.test(charx)){
			c3++;
		}
	}

	if(c1 > 0){
		flag = false;
	} else if(c3 > 0){
		flag = false;
	}
	
	if((c1 + c2 + c3) < minlength){
		flag = false;
	}
	
	if(maxlength != 0 && (c1 + c2 + c3) > maxlength) {
		flag = false;
	}
	
	return flag;
}

//只允許特殊字元密碼,最小長度需要minlength以上,最大長度maxlength(0為不限制)以下
//author: csiebug
//version: 2008/9/12
function passwordRule7(value, minlength, maxlength) {
	var flag = true;
	
	var c1 = 0;
	var c2 = 0;
	var c3 = 0;

	for(var i = 0; i < value.length; i++){
		var charx = value.charAt(i);

		if(/[0-9]/.test(charx)){
			c1++;
		}else if(/[a-zA-Z]/.test(charx)){
			c2++;
		}else if(/\W|_/.test(charx)){
			c3++;
		}
	}

	if(c1 > 0){
		flag = false;
	} else if(c2 > 0){
		flag = false;
	}
	
	if((c1 + c2 + c3) < minlength){
		flag = false;
	}
	
	if(maxlength != 0 && (c1 + c2 + c3) > maxlength) {
		flag = false;
	}
	
	return flag;
}

//登出系統
//author: csiebug
//version: 2009/2/17
function logout(strURL) {
	window.top.location = strURL + "?ActFlag=logout";
}

/**
 * TextArea沒有maxLength屬性
 * @param textArea
 * @param maxLength
 * @param warning
 * @param message
 * @param ok
 * @return
 * @author csiebug
 * @version 2009/11/21
 */
function checkLength(textArea, maxLength, warning, message, ok) {
	if(textArea.value.length > maxLength) {
		openAlertDialog(textArea, warning, message, ok, '', textArea, 0);
	}
}

//開啟或隱藏功能樹frame
//author: csiebug
//version: 2009/1/23
function hiddenFunList() {
	var framesets = window.top.document.getElementsByTagName("FRAMESET");
	
	if(typeof(framesets[0])=='object') {
		if (framesets[0].cols == '0,*') {
			framesets[0].cols = '210,*';
		}
		else{
			framesets[0].cols = '0,*';
		}
	}
}

//timeline需要用到的function
var t1;
var resizeTimerID = null;
/**
 * 用來Resize timeline
 * @return
 */
function onResize() {
	if (resizeTimerID == null) {
    	resizeTimerID = window.setTimeout(function() {
        	resizeTimerID = null;
        	tl.layout();
    	}, 500);
	}
}
