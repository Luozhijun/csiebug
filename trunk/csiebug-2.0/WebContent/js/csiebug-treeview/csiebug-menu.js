var flagOpenMenu = false;

/**
 * 開啟選單
 * @param div
 * @param level
 * @return
 * @author csiebug
 * @version 2008/12/30
 */
function openMenu(div, level) {
	var obj = document.getElementById(div + "_folderMenu");
	var menu = document.getElementById(div + "_" + level + "_menu");
	
	if(flagOpenMenu) {
		flagOpenMenu = false;
	} else {
		flagOpenMenu = true;
	}
	
	if(flagOpenMenu) {
		menu.style.display = "";
		menu.style.border = "1px solid";
		menu.style.background = "#FFF"
		
		//因為relative雖然可以設定位置,但是他佔空間,所以無法達到z-index可以做到重疊的效果 
		//取得relative(相對座標系統)的絕對座標
		var top = obj.offsetTop;
		var left = obj.offsetLeft;
		
		//再把它改為absolute(絕對座標系統),並把座標設上
		menu.style.position = "absolute";
		if(level == 1) {
			menu.style.top = (top + obj.offsetHeight) + "px";
			menu.style.left = left + "px";
		} else {
			menu.style.top = top + "px";
			menu.style.left = (left + obj.offsetWidth - 5) + "px";
		}
		
	} else {
		closeAllMenu();
	}
}

/**
 * 移到選單
 * @param div
 * @param level
 * @param maxlevel
 * @return
 * @author csiebug
 * @version 2008/12/30
 */
function moveMenuOver(div, level, maxlevel) {
	var obj = document.getElementById(div + "_folderMenu");
	var menu = document.getElementById(div + "_" + level + "_menu");
	
	for(var i = level; i <= maxlevel; i++) {
		closeLevelMenu(i);
	}
	
	if(div != "") {
		if(menu.style.display == "none" && flagOpenMenu == true) {
			menu.style.display = "";
			menu.style.border = "1px solid";
			menu.style.background = "#FFF"
			
			//因為relative雖然可以設定位置,但是他佔空間,所以無法達到z-index可以做到重疊的效果 
			//取得relative(相對座標系統)的絕對座標
			var top = obj.offsetTop;
			var left = obj.offsetLeft;
			
			//再把它改為absolute(絕對座標系統),並把座標設上
			menu.style.position = "absolute";
			if(level == 1) {
				menu.style.top = (top + obj.offsetHeight) + "px";
				menu.style.left = left + "px";
			} else {
				menu.style.top = top + "px";
				menu.style.left = (left + obj.offsetWidth - 5) + "px";
			}
		}
	}
}

/**
 * 把選單收起來
 * @param obj
 * @return
 * @author csiebug
 * @version 2008/9/12
 */
function closeMenu(obj) {
	document.getElementById(obj.id).style.display = "none";
}

/**
 * 把所有的選單收起來
 * @return
 * @author csiebug
 * @version 2008/12/30
 */
function closeAllMenu() {
	var divs = document.getElementsByTagName("div");
	
	for(var i = 0; i < divs.length; i++) {
		var div = divs[i];
		
		if(div.id.substring(div.id.length - 5, div.id.length) == "_menu") {
			div.style.display = "none";
		}
	}
}

/**
 * 把同一層的選單收起來
 * @param level
 * @return
 * @author csiebug
 * @version 2008/12/30
 */
function closeLevelMenu(level) {
	var divs = document.getElementsByTagName("div");
	
	for(var i = 0; i < divs.length; i++) {
		var div = divs[i];
		
		if(div.id.substring(div.id.length - (6 + ('' + level).length), div.id.length) == "_" + level + "_menu") {
			div.style.display = "none";
		}
	}
}
