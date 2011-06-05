/**
 * 將key對應的value帶到唯讀的text欄位
 * @param selText
 * @return
 * @author csiebug
 * @version 2009/8/28
 */
function refreshValue(selText) {
	var selId = selText.id.replace("_display", "");
	var selHidden = document.getElementById(selId);
	var selReadOnly = document.getElementById(selId + "_valueDisplay");
	
	selReadOnly.value = selHidden.value;
	selHidden.value = selText.value;
}