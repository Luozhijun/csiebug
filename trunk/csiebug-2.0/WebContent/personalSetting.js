function refreshMajorEmailOption() {
	var majorEmailSelect = document.getElementById("majorEmail");
	var options = document.getElementById("emails").value.split(";");
	
	var selectedOption = majorEmailSelect.value;
	
	for(var i = majorEmailSelect.length - 1; i > 0; i--) {
		majorEmailSelect.remove(i);
	}
	
	var selectedFlag = false;
	for(var i = 0; i < options.length; i++) {
		try { //standard
			majorEmailSelect.add(new Option(options[i], options[i]), null);
		} catch(ex) { //just for ie
			majorEmailSelect.add(new Option(options[i], options[i]));
		}
		
		if(selectedOption != "" && selectedOption == options[i]) {
			selectedFlag = true;
		}
	}
	
	if(selectedFlag) {
		majorEmailSelect.value = selectedOption;
	}
}

function saveUser() {
	document.getElementById("personalSettingActFlag").value = "personalSettingUserSave";
	document.getElementById("personalSettingForm").submit();
}

function modifyPassword(form, button, buttonText1, buttonText2, warning, required, ok, authenticationFailure) {
	if(document.getElementById("modifyPassword").style.display == "none") {
		document.getElementById("modifyPassword").style.display = "";
		button.value = buttonText2;
	} else {
		if(checkRequired(form, warning, required, ok)) {
			if(!checkPassword()) {
				document.getElementById("password").value = "";
				document.getElementById("confirm_password").value = "";
				
				openAlertDialog(null, warning, authenticationFailure, ok, '', null, 0);
			} else {
				//lockButton();
				//loading(button);
				loadingBlockPage('', 0);
				
				document.getElementById("personalSettingActFlag").value = "personalSettingModifyPassword";
				document.getElementById(form).submit();
			}
		}
	}
}

function checkPassword() {
	if(document.getElementById("password").value == document.getElementById("confirm_password").value) {
		return true;
	} else {
		return false;
	}
}