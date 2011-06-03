function submitEnter(e, form, warning, required, ok) {
	var key;
	var key = window.event ? e.keyCode : e.which;
	
	if (key == 13) {
		var button = document.getElementById("loginButton");
		login(form, button, warning, required, ok);
	}
}

function login(form, button, warning, required, ok) {
	if(checkRequired(form, warning, required, ok)) {
		//lockButton();
		//loading(button);
		loadingBlockPage('', 0);
		
		document.getElementById("ActFlag").value = "login";
		createCookie('createCookie', document.getElementById("j_rememberme").checked + '&' + document.getElementById("j_username").value + '&' + document.getElementById("j_password").value);
		document.getElementById(form).submit();
	}
}

function createCookie(name, value) {
	return document.cookie = name + "=" + value;
}