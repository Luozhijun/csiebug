function save(form, warning, required, ok) {
	if(checkRequired(form, warning, required, ok)) {
		lockButton();
		
		document.getElementById("ActFlag").value = "login";
		document.getElementById(form).submit();
	}
}