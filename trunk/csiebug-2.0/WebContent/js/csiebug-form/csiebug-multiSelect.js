/**
 * 全選
 * @param selId
 * @param func
 * @return
 * @author csiebug
 * @version 2008/12/26
 */
function selAll(selId, func) {
	var hiddenUnSelect = document.getElementById(selId + "_unselect");
	var unSelect = document.getElementById(selId + "_unselect_display");
	var hiddenSelect = document.getElementById(selId);
	var select = document.getElementById(selId + "_display");
	var onChange = false;
	
	for(var i = unSelect.length - 1; i >= 0; i--) {
		onChange = true;
		try { //standard
			select.add(new Option(unSelect[i].text, unSelect[i].value), null);
		} catch(ex) { //just for ie
			select.add(new Option(unSelect[i].text, unSelect[i].value));
		}
		unSelect.remove(i);
	}
	
	setHiddenValue(hiddenUnSelect, unSelect, hiddenSelect, select);
	
	if(onChange) {
		if(func != '') {
			eval(replaceAll(replaceAll(func, 'this', 'document.getElementById(\'' + selId + '\')'), '&quote', '\''));
		}
	}
}

/**
 * 多選
 * @param selId
 * @param func
 * @return
 * @author csiebug
 * @version 2008/12/26
 */
function selMulti(selId, func) {
	var hiddenUnSelect = document.getElementById(selId + "_unselect");
	var unSelect = document.getElementById(selId + "_unselect_display");
	var hiddenSelect = document.getElementById(selId);
	var select = document.getElementById(selId + "_display");
	var onChange = false;
	
	for(var i = unSelect.length - 1; i >= 0; i--) {
		if(unSelect.options[i].selected == true) {
			onChange = true;
			
			try { //standard
				select.add(new Option(unSelect[i].text, unSelect[i].value), null);
			} catch(ex) { //just for ie
				select.add(new Option(unSelect[i].text, unSelect[i].value));
			}
			unSelect.remove(i);
		}
	}
	
	setHiddenValue(hiddenUnSelect, unSelect, hiddenSelect, select);
	
	if(onChange) {
		if(func != '') {
			eval(replaceAll(replaceAll(func, 'this', 'document.getElementById(\'' + selId + '\')'), '&quote', '\''));
		}
	}
}

/**
 * 多不選
 * @param selId
 * @param func
 * @return
 * @author csiebug
 * @version 2008/12/26
 */
function unSelMulti(selId, func) {
	var hiddenUnSelect = document.getElementById(selId + "_unselect");
	var unSelect = document.getElementById(selId + "_unselect_display");
	var hiddenSelect = document.getElementById(selId);
	var select = document.getElementById(selId + "_display");
	var onChange = false;
	
	for(var i = select.length - 1; i >= 0; i--) {
		if(select.options[i].selected == true) {
			onChange = true;
			
			try { //standard
				unSelect.add(new Option(select[i].text, select[i].value), null);
			} catch(ex) { //just for ie
				unSelect.add(new Option(select[i].text, select[i].value));
			}
			select.remove(i);
		}
	}
	
	setHiddenValue(hiddenUnSelect, unSelect, hiddenSelect, select);
	
	if(onChange) {
		if(func != '') {
			eval(replaceAll(replaceAll(func, 'this', 'document.getElementById(\'' + selId + '\')'), '&quote', '\''));
		}
	}
}

/**
 * 全不選
 * @param selId
 * @param func
 * @return
 * @author csiebug
 * @version 2008/12/26
 */
function unSelAll(selId, func) {
	var hiddenUnSelect = document.getElementById(selId + "_unselect");
	var unSelect = document.getElementById(selId + "_unselect_display");
	var hiddenSelect = document.getElementById(selId);
	var select = document.getElementById(selId + "_display");
	var onChange = false;
	
	for(var i = select.length - 1; i >= 0; i--) {
		onChange = true;
		
		try { //standard
			unSelect.add(new Option(select[i].text, select[i].value), null);
		} catch(ex) { //just for ie
			unSelect.add(new Option(select[i].text, select[i].value));
		}
		select.remove(i);
	}
	
	setHiddenValue(hiddenUnSelect, unSelect, hiddenSelect, select);
	
	if(onChange) {
		if(func != '') {
			eval(replaceAll(replaceAll(func, 'this', 'document.getElementById(\'' + selId + '\')'), '&quote', '\''));
		}
	}
}

/**
 * 更新hidden欄位的值
 * @param hiddenUnSelect
 * @param unSelect
 * @param hiddenSelect
 * @param select
 * @return
 * @author csiebug
 * @version 2008/12/25
 */
function setHiddenValue(hiddenUnSelect, unSelect, hiddenSelect, select) {
	hiddenUnSelect.value = "";
	hiddenSelect.value = "";
	
	for(var i = 0; i < unSelect.length; i++) {
		if(hiddenUnSelect.value != "") {
			hiddenUnSelect.value = hiddenUnSelect.value + ",";
		}
		hiddenUnSelect.value = hiddenUnSelect.value + unSelect[i].value;
	}
	
	for(var i = 0; i < select.length; i++) {
		if(hiddenSelect.value != "") {
			hiddenSelect.value = hiddenSelect.value + ",";
		}
		hiddenSelect.value = hiddenSelect.value + select[i].value;
	}
}