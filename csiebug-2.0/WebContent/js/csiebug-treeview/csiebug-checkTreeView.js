/**
 * 開啟關閉樹的節點
 * @param div
 * @param imagePath
 * @return
 * @author csiebug
 * @version 2009/1/30
 */
function showSubCheckListTree(div, imagePath) {
	var vfolder = document.getElementById(div);
	if(vfolder.style.display == "") {
		vfolder.style.display = "none";
		var img2 = document.getElementById(div + "_line");
		if(img2.src.indexOf("ETminus") != -1) {
			img2.src = imagePath + "/ETplus.gif";
		} else if(img2.src.indexOf("Lminus") != -1) {
			img2.src = imagePath + "/Lplus.gif"; 
		} else if(img2.src.indexOf("Eminus") != -1) {
			img2.src = imagePath + "/Eplus.gif";
		} else {
			img2.src = imagePath + "/LTplus.gif";
		}
	} else {
		vfolder.style.display = "";
		var img2 = document.getElementById(div + "_line");
		if(img2.src.indexOf("ETplus") != -1) {
			img2.src = imagePath + "/ETminus.gif";
		} else if(img2.src.indexOf("Lplus") != -1) {
			img2.src = imagePath + "/Lminus.gif";
		} else if(img2.src.indexOf("Eplus") != -1) {
			img2.src = imagePath + "/Eminus.gif";
		} else {
			img2.src = imagePath + "/LTminus.gif";
		}
	}
}

/**
 * 移除選擇的值
 * @param hiddenId
 * @param id
 * @param name
 * @return
 * @author csiebug
 * @version 2009/7/1
 */
function removeValue(hiddenId, id, name) {
	var selected = document.getElementById(hiddenId);
	
	if(selected.value.indexOf(id + " - " + name) != -1) {
		if (selected.value.indexOf(";" + id + " - " + name) != -1) {
			selected.value = selected.value.replace(";" + id + " - " + name, "");
		} else {
			selected.value = selected.value.replace(id + " - " + name, "");
		}
	}
}

/**
 * 加入新的值
 * @param hiddenId
 * @param id
 * @param name
 * @return
 * @author csiebug
 * @version 2009/7/1
 */
function addValue(hiddenId, id, name) {
	var selected = document.getElementById(hiddenId);
	
	if (selected.value.indexOf(id + " - " + name) == -1) {
		if (selected.value != "") {
			selected.value = selected.value + ";" + id + " - " + name;
		} else {
			selected.value = id + " - " + name;
		}
	}
}

/**
 * 勾選child node
 * @param id
 * @param name
 * @param parentId
 * @param parentName
 * @param hiddenId
 * @param linked
 * @return
 * @author csiebug
 * @version 2009/7/1
 */
function selectNode(id, name, parentId, parentName, hiddenId, linked) {
	var checkbox = document.getElementById(id + "_checkbox");
	
	if (parentId != "") {
		if (checkbox.checked != true) {
			if (linked == true) {
				var parentCheckbox = document.getElementById(parentId + "_checkbox");
				
				if (parentCheckbox.checked == true) {
					parentCheckbox.click();
				}
			}
			
			removeValue(hiddenId, id, name);
		} else {
			addValue(hiddenId, id, name);
		}
	} else {
		if (checkbox.checked != true) {
			removeValue(hiddenId, id, name);
		} else {
			addValue(hiddenId, id, name);
		}
	}
}

/**
 * 勾選Parent node
 * @param id
 * @param name
 * @param parentId
 * @param parentName
 * @param hiddenId
 * @return
 * @author csiebug
 * @version 2009/7/1
 */
function selectNodeAndSubNodes(id, name, parentId, parentName, hiddenId) {
	var checkbox = document.getElementById(id + "_checkbox");
	var subtreeCheckboxs = document.getElementById(id).getElementsByTagName("input");
	
	if (parentId != "") {
		if (checkbox.checked != true) {
			var parentCheckbox = document.getElementById(parentId + "_checkbox");
			
			if (parentCheckbox.checked == true) {
				parentCheckbox.click();
			}
			
			for (var i = 0; i < subtreeCheckboxs.length; i++) {
				if (subtreeCheckboxs[i].checked == true) {
					subtreeCheckboxs[i].click();
				}
			}
			
			removeValue(hiddenId, id, name);
		} else {
			for (var i = 0; i < subtreeCheckboxs.length; i++) {
				if (subtreeCheckboxs[i].checked == false) {
					subtreeCheckboxs[i].click();
				}
			}
			
			addValue(hiddenId, id, name);
		}
	} else {
		if (checkbox.checked != true) {
			for (var i = 0; i < subtreeCheckboxs.length; i++) {
				if (subtreeCheckboxs[i].checked == true) {
					subtreeCheckboxs[i].click();
				}
			}
			
			removeValue(hiddenId, id, name);
		} else {
			for (var i = 0; i < subtreeCheckboxs.length; i++) {
				if (subtreeCheckboxs[i].checked == false) {
					subtreeCheckboxs[i].click();
				}
			}
			
			addValue(hiddenId, id, name);
		}
	}
}

/**
 * 單選
 * @param id
 * @param name
 * @param hiddenId
 * @return
 * @author csiebug
 * @version 2009/8/3
 */
function selectNodeForRadio(id, name, hiddenId) {
	var checkbox = document.getElementById(id + "_checkbox");
	
	if (checkbox.checked != true) {
		removeValue(hiddenId, id, name);
	} else {
		addValue(hiddenId, id, name);
		unSelectOtherNode(id);
	}
}

/**
 * 取消選取
 * @param id
 * @return
 * @author csiebug
 * @version 2009/8/3
 */
function unSelectOtherNode(id) {
	var checkboxs = document.getElementsByTagName("input");
	
	for(var i = 0; i < checkboxs.length; i++) {
		if(checkboxs[i].id.substring(checkboxs[i].id.length - 9, checkboxs[i].id.length) == "_checkbox") {
			if(checkboxs[i].id != id + "_checkbox" && checkboxs[i].checked == true) {
				checkboxs[i].click();
				break;
			}
		}
	}
}