function modifyWindow(form, button, warning, required, ok) {
	if(checkRequired(form, warning, required, ok)) {
		//lockButton();
		//loading(button);
		loadingBlockPage('', 0);
		
		document.getElementById("ModifyPortletSubmit").value = "modifyFormSubmit";
		document.getElementById(form).submit();
	}
}

function loadPortletProperties() {
	var url = "admin?ModifyPortletSubmit=modifyFormSubmit&ActFlag=loadPortletProperties&portletWindowList=" + document.getElementById("portletWindowList").value;
	var properties = sendAJAXRequest(url).split(";");
	
	document.getElementById("dashboardId").value = properties[0];
	document.getElementById("title").value = properties[1];
	document.getElementById("widthList").value = properties[2];
	document.getElementById("portletSortOrder").value = properties[3];
	if (properties[4] == "true") {
		document.getElementsByName("visibleList")[0].checked = true;
	} else if(properties[4] == "false") {
		document.getElementsByName("visibleList")[1].checked = true;
	} else {
		document.getElementsByName("visibleList")[0].checked = false;
		document.getElementsByName("visibleList")[0].checked = false;
	}
}
