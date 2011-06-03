function login(form, button, warning, required, ok, authenticationFailure) {
	if(checkRequired(form, warning, required, ok)) {
		if(!checkPassword()) {
			document.getElementById("j_password").value = "";
			document.getElementById("confirm_password").value = "";
			
			openAlertDialog(null, warning, authenticationFailure, ok, '', null, 0);
		} else {
			//lockButton();
			//loading(button);
			loadingBlockPage('', 0);
			
			document.getElementById("ActFlag").value = "signIn";
			document.getElementById(form).submit();
		}
	}
}

function checkPassword() {
	if(document.getElementById("j_password").value == document.getElementById("confirm_password").value) {
		return true;
	} else {
		return false;
	}
}