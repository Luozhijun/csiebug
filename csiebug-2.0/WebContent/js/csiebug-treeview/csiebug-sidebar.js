/**
 * 打開/關閉Folder
 * @param div
 * @return
 * @author csiebug
 */
function openSidebarFolder(div) {
	var vfolder = document.getElementById(div + "_sidebar");
	
	if (vfolder.style.display == "") {
		vfolder.style.display = "none";
	} else {
		vfolder.style.display = "";
	}
}
