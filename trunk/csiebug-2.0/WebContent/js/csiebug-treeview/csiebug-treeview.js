/**
 * 開啟關閉樹的節點
 * @param div
 * @param imagePath
 * @return
 * @author csiebug
 * @version 2009/1/30
 */
function showSubTree(div, imagePath) {
	var vfolder = document.getElementById(div);
	if(vfolder.style.display == "") {
		vfolder.style.display = "none";
		var img1 = document.getElementById(div + "_folder");
		img1.src = imagePath + "/Cfolder.gif";
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
		var img1 = document.getElementById(div + "_folder");
		img1.src = imagePath + "/Ofolder.gif";
		var img2 = document.getElementById(div + "_line");
		if(img2.src.indexOf("ETplus") != -1) {
			img2.src = imagePath + "/ETminus.gif"
		} else if(img2.src.indexOf("Lplus") != -1) {
			img2.src = imagePath + "/Lminus.gif";
		} else if(img2.src.indexOf("Eplus") != -1) {
			img2.src = imagePath + "/Eminus.gif";
		} else {
			img2.src = imagePath + "/LTminus.gif";
		}
	}
}