/**
 * 切換tab
 * @param li
 * @param tab1
 * @return
 * @author csiebug
 * @version 2009/3/20
 */
function changeTab(li, tab1) {
	var tabs = document.getElementsByTagName("div");
	
	for(var i = 0; i < tabs.length; i++) {
		if(tabs[i].id.indexOf("tab") == 0) {
			if(tabs[i].id != tab1.id) {
				tabs[i].style.display = "none";
			} else {
				tabs[i].style.display = "";
			}
		}
	}
	
	document.getElementById("current").id = "notcurrent";
    li.id = "current";
    
    var objMenu = document.getElementById("menu");
    	
    var top = objMenu.offsetTop;
    
	tab1.style.position = "absolute";
	tab1.style.top = (top + objMenu.offsetHeight) + "px";
	tab1.style.left = 0 + "px";
}

/**
 * 切換tab
 * @param li
 * @param url
 * @param tabId
 * @param frameId
 * @param func
 * @return
 * @author csiebug
 * @version 2009/3/20
 */
function changeTabIframe(li, url, tabId, frameId, func) {
	var ul = document.getElementById(tabId);
	
	var lis = ul.getElementsByTagName("li");
	for(var i = 0; i < lis.length; i++) {
		if(lis[i].id == "current") {
			lis[i].id = "notcurrent";
			break;
		}
	}
	li.id = "current";
	
	document.getElementById(frameId).src = url;
	
	if(func != '') {
		eval(replaceAll(replaceAll(func, 'this', url), '&quote', '\''));
	}
}

/**
 * 切換tab
 * @param li
 * @param divId
 * @param tabId
 * @param func
 * @return
 * @author csiebug
 * @version 2010/1/13
 */
function changeTabDIV(li, divId, tabId, func) {
	var ul = document.getElementById(tabId);
	
	var lis = ul.getElementsByTagName("li");
	for(var i = 0; i < lis.length; i++) {
		if(lis[i].id == "current") {
			lis[i].id = "notcurrent";
			break;
		}
	}
	li.id = "current";
	
	var defaultTabId;
	var inputs = ul.getElementsByTagName("input");
	for(var i = 0; i < inputs.length; i++) {
		if(inputs[i].id == "defaultTabId") {
			defaultTabId = inputs[i];
			break;
		}
	}
	
	if(defaultTabId.value != "" && defaultTabId.value != divId) {
		document.getElementById(defaultTabId.value).style.display = "none";
	}
	
	defaultTabId.value = divId;
	document.getElementById(defaultTabId.value).style.display = "";
	
	if(func != '') {
		eval(replaceAll(replaceAll(func, 'this', divId), '&quote', '\''));
	}
}