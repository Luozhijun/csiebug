function createWindow(form, button, warning, required, ok) {
	if(checkRequired(form, warning, required, ok)) {
		//lockButton();
		//loading(button);
		loadingBlockPage('', 0);
		
		document.getElementById("CreatePortletSubmit").value = "createFormSubmit";
		document.getElementById(form).submit();
	}
}
