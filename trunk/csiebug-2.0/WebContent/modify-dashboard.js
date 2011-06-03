function saveDashboard(form, button, warning, required, ok) {
	if(checkRequired(form, warning, required, ok)) {
		//lockButton();
		//loading(button);
		loadingBlockPage('', 0);
		
		document.getElementById("ModifyDashboardSubmit").value = "modifyDashboardSubmit";
		document.getElementById("modifyDashboardActFlag").value = "save";
		document.getElementById(form).submit();
	}
}

function deleteDashboard(form, button, warning, required, ok) {
	if(checkRequired(form, warning, required, ok)) {
		lockButton();
		loading(button);
		
		document.getElementById("ModifyDashboardSubmit").value = "modifyDashboardSubmit";
		document.getElementById("modifyDashboardActFlag").value = "delete";
		document.getElementById(form).submit();
	}
}

function loadDashboardProperties() {
	var url = "admin?modifyDashboardActFlag=loadDashboardProperties&ModifyDashboardSubmit=modifyDashboardSubmit&dashboard=" + document.getElementById("dashboard").value;
	document.getElementById("dashboardSortOrder").value = sendAJAXRequest(url);
}
