function lockService(form, button) {
	//lockButton();
	//loading(button);
	loadingBlockPage('', 0);
	
	document.getElementById("systemAdminActFlag").value = "systemAdminLockService";
	document.getElementById(form).submit();
}

function enableService(form, button) {
	//lockButton();
	//loading(button);
	loadingBlockPage('', 0);
	
	document.getElementById("systemAdminActFlag").value = "systemAdminEnableService";
	document.getElementById(form).submit();
}

function searchUser(form, button) {
	//lockButton();
	//loading(button);
	loadingBlockPage('', 0);
	
	document.getElementById("systemAdminActFlag").value = "systemAdminSearchUser";
	document.getElementById(form).submit();
}

function enableUser(form, button, id, warning, password, message, ok) {
	if(document.getElementById("newPassword_" + id).value != "") {
		//lockButton();
		//loading(button);
		loadingBlockPage('', 0);
		
		document.getElementById("users").value = id;
		document.getElementById("systemAdminActFlag").value = "systemAdminEnableUser";
		document.getElementById(form).submit();
	} else {
		var comboMessage = replaceAll(message, "{0}", password);
		openAlertDialog(null, warning, comboMessage, ok, '', document.getElementById("newPassword_" + id), 0);
	}
}

function disableUser(warning, message1, message2, ok, cancel) {
	var trs = document.getElementById("userGrid").getElementsByTagName("tr");
	
	var users = "";
	for(var i = 1; i < trs.length - 1; i++) {
		var tr = trs[i];
		var tds = tr.getElementsByTagName("td");
		var chk = tds[0].getElementsByTagName("input");
		var userId = replaceAll(tds[2].innerHTML, "\n", "");
						
		if(chk[0] != null && chk[0].checked == true) {
			if(users != "") {
				users = users + ",";
			}
			
			users = users + userId; 
		}
	}
	
	if(users != "") {
		document.getElementById("users").value = users;
		openConfirmDialog(null, warning, message1, ok, cancel, 'disableUser2()', '', null);
	} else {
		openAlertDialog(null, warning, message2, ok, '', null, 0);
	}
}

function disableUser2() {
	document.getElementById("systemAdminActFlag").value = "systemAdminDisableUser";
	document.getElementById("systemAdminForm").submit();
}

function deleteUser(warning, message1, message2, ok, cancel) {
	var trs = document.getElementById("userGrid").getElementsByTagName("tr");
	
	var users = "";
	for(var i = 1; i < trs.length - 1; i++) {
		var tr = trs[i];
		var tds = tr.getElementsByTagName("td");
		var chk = tds[0].getElementsByTagName("input");
		var userId = replaceAll(tds[2].innerHTML, "\n", "");
						
		if(chk[0] != null && chk[0].checked == true) {
			if(users != "") {
				users = users + ",";
			}
			
			users = users + userId; 
		}
	}
	
	if(users != "") {
		document.getElementById("users").value = users;
		openConfirmDialog(null, warning, message1, ok, cancel, 'deleteUser2()', '', null);
	} else {
		openAlertDialog(null, warning, message2, ok, '', null, 0);
	}
}

function deleteUser2() {
	document.getElementById("systemAdminActFlag").value = "systemAdminDeleteUser";
	document.getElementById("systemAdminForm").submit();
}

function updateUserRole(form, button, id) {
	//lockButton();
	//loading(button);
	loadingBlockPage('', 0);
	
	document.getElementById("users").value = id;
	document.getElementById("systemAdminActFlag").value = "systemAdminUpdateUserRole";
	document.getElementById(form).submit();
}

function selectToEditForRole(roleId, roleName) {
	var roleIdControl = document.getElementById("roleId");
	var roleNameControl = document.getElementById("roleName");
	roleIdControl.className = "TextReadOnly";
	roleIdControl.readOnly = true;
	
	if(document.getElementById("roleId").value == roleId) {
		closeRoleEditor();
	} else {
		showRoleEditor();
		
		roleIdControl.value = roleId;
		roleNameControl.value = roleName;
	}
}

function clearRoleEditor() {
	document.getElementById("roleId").value = "";
	document.getElementById("roleName").value = "";
}

function saveRole(form, button, warning, required, ok) {
	document.getElementById("roleId").setAttribute("isRequired", "true");
	document.getElementById("codeId").setAttribute("isRequired", "false");
	document.getElementById("codeTypeHidden").setAttribute("isRequired", "false");
	document.getElementById("codeType").setAttribute("isRequired", "false");
	document.getElementById("codeValue").setAttribute("isRequired", "false");
	document.getElementsByName("codeEnabled")[0].setAttribute("isRequired", "false");
	document.getElementsByName("codeEnabled")[1].setAttribute("isRequired", "false");
	
	if(checkRequired(form, warning, required, ok)) {
		//lockButton();
		//loading(button);
		loadingBlockPage('', 0);
		
		document.getElementById("systemAdminActFlag").value = "systemAdminSaveRole";
		document.getElementById(form).submit();
	}
}

function addRole() {
	cancelFocusForRole();
	clearRoleEditor();
	showRoleEditor();
	
	var roleIdControl = document.getElementById("roleId");
	var roleNameControl = document.getElementById("roleName");
	
	roleIdControl.className = "Text";
	roleIdControl.readOnly = false;
}

function showRoleEditor() {
	document.getElementById("roleEditor").style.display = "";
}

function closeRoleEditor() {
	cancelFocusForRole();
	clearRoleEditor();
	document.getElementById("roleEditor").style.display = "none";
}

function deleteRole(warning, message1, message2, ok, cancel) {
	var trs = document.getElementById("roleGrid").getElementsByTagName("tr");
	
	var roles = "";
	for(var i = 1; i < trs.length - 1; i++) {
		var tr = trs[i];
		var tds = tr.getElementsByTagName("td");
		var chk = tds[0].getElementsByTagName("input");
		var roleId = replaceAll(tds[1].innerHTML, "\n", "");
						
		if(chk[0] != null && chk[0].checked == true) {
			if(roles != "") {
				roles = roles + ",";
			}
			
			roles = roles + roleId; 
		}
	}
	
	if(roles != "") {
		document.getElementById("roles").value = roles;
		openConfirmDialog(null, warning, message1, ok, cancel, 'deleteRole2()', '', null);
	} else {
		openAlertDialog(null, warning, message2, ok, '', null, 0);
	}
}

function deleteRole2() {
	document.getElementById("systemAdminActFlag").value = "systemAdminDeleteRole";
	document.getElementById("systemAdminForm").submit();
}

function cancelFocusForRole() {
	var trs = document.getElementById("roleGrid").getElementsByTagName("tr");
	
	//取消選取,要把本來單數或雙數列設回來
	for(var i = 0; i < trs.length; i++) {
		var tr = trs[i];
		
		if(tr.className == "TR_FOCUS") {
			if((i - 1 + 1) % 2 == 1) {
				tr.className = "TR_ODD";
			} else {
				tr.className = "TR_EVEN";
			}
		}
	}
}

function selectToEditForCode(codeTypeText, codeId, codeType, codeValue, codeDescription, codeEnable) {
	var codeIdControl = document.getElementById("codeId");
	var codeTypeControl = document.getElementById("codeType");
	codeIdControl.className = "TextReadOnly";
	codeIdControl.readOnly = true;
	codeTypeControl.className = "SelectReadOnly";
	codeTypeControl.disabled = true;
	
	if(document.getElementById("codeId").value == codeId) {
		closeCodeEditor();
	} else {
		showCodeEditor();
		
		codeIdControl.value = codeId;
		document.getElementById("codeTypeHidden").value = codeType;
		if(codeType == "CodeType") {
			try { //standard
				codeTypeControl.add(new Option(codeTypeText, "CodeType"), null);
			} catch(ex) { //just for ie
				codeTypeControl.add(new Option(codeTypeText, "CodeType"));
			}
		}
		codeTypeControl.value = codeType;
		document.getElementById("codeValue").value = codeValue;
		document.getElementById("codeDescription").value = codeDescription;
		
		var radios = document.getElementsByName("codeEnabled");
		if(codeEnable == 'true') {
			radios[0].checked = true;
			radios[1].checked = false;
		} else if(codeEnable == 'false') {
			radios[0].checked = false;
			radios[1].checked = true;
		}
	}
}

function clearCodeEditor() {
	document.getElementById("codeId").value = "";
	document.getElementById("codeTypeHidden").value = "";
	document.getElementById("codeType").value = "";
	document.getElementById("codeValue").value = "";
	document.getElementById("codeDescription").value = "";
	
	var radios = document.getElementsByName("codeEnabled");
	radios[0].checked = false;
	radios[1].checked = false;
}

function refreshCodeTypeHidden() {
	document.getElementById("codeTypeHidden").value = document.getElementById("codeType").value;
}

function saveCode(form, button, warning, required, ok) {
	document.getElementById("roleId").setAttribute("isRequired", "false");
	document.getElementById("codeId").setAttribute("isRequired", "true");
	document.getElementById("codeTypeHidden").setAttribute("isRequired", "true");
	document.getElementById("codeType").setAttribute("isRequired", "true");
	document.getElementById("codeValue").setAttribute("isRequired", "true");
	document.getElementsByName("codeEnabled")[0].setAttribute("isRequired", "true");
	document.getElementsByName("codeEnabled")[1].setAttribute("isRequired", "true");
	
	if(checkRequired(form, warning, required, ok)) {
		//lockButton();
		//loading(button);
		loadingBlockPage('', 0);
		
		document.getElementById("systemAdminActFlag").value = "systemAdminSaveCode";
		document.getElementById(form).submit();
	}
}

function addCode() {
	cancelFocusForCode();
	clearCodeEditor();
	showCodeEditor();
	
	var codeIdControl = document.getElementById("codeId");
	var codeTypeControl = document.getElementById("codeType");
	
	codeIdControl.className = "Text";
	codeIdControl.readOnly = false;
	codeTypeControl.className = "Select";
	codeTypeControl.disabled = false;
	
	for(var i = codeTypeControl.length - 1; i > 0; i--) {
		if(codeTypeControl[i].value == "CodeType") {
			codeTypeControl.remove(i);
		}
	}
}

function addCodeType(codeTypeText) {
	cancelFocusForCode();
	clearCodeEditor();
	showCodeEditor();
	
	var codeIdControl = document.getElementById("codeId");
	var codeTypeControl = document.getElementById("codeType");
	
	codeIdControl.className = "Text";
	codeIdControl.readOnly = false;
	codeTypeControl.disabled = true;
	try { //standard
		codeTypeControl.add(new Option(codeTypeText, "CodeType"), null);
	} catch(ex) { //just for ie
		codeTypeControl.add(new Option(codeTypeText, "CodeType"));
	}
	codeTypeControl.value = "CodeType";
	document.getElementById("codeTypeHidden").value = "CodeType";
}

function showCodeEditor() {
	document.getElementById("codeEditor").style.display = "";
}

function closeCodeEditor() {
	cancelFocusForCode();
	clearCodeEditor();
	document.getElementById("codeEditor").style.display = "none";
}

function deleteCode(warning, message1, message2, ok, cancel) {
	var trs = document.getElementById("codeGrid").getElementsByTagName("tr");
	
	var codes = "";
	for(var i = 1; i < trs.length - 1; i++) {
		var tr = trs[i];
		var tds = tr.getElementsByTagName("td");
		var chk = tds[0].getElementsByTagName("input");
		var codeId = replaceAll(tds[1].innerHTML, "\n", "");
		var codeType = replaceAll(tds[2].innerHTML, "\n", "");
						
		if(chk[0] != null && chk[0].checked == true) {
			if(codes != "") {
				codes = codes + ",";
			}
			
			codes = codes + codeId + ";" + codeType; 
		}
	}
	
	if(codes != "") {
		document.getElementById("codes").value = codes;
		openConfirmDialog(null, warning, message1, ok, cancel, 'deleteCode2()', '', null);
	} else {
		openAlertDialog(null, warning, message2, ok, '', null, 0);
	}
}

function deleteCode2() {
	document.getElementById("systemAdminActFlag").value = "systemAdminDeleteCode";
	document.getElementById("systemAdminForm").submit();
}

function cancelFocusForCode() {
	var trs = document.getElementById("codeGrid").getElementsByTagName("tr");
	
	//取消選取,要把本來單數或雙數列設回來
	for(var i = 0; i < trs.length; i++) {
		var tr = trs[i];
		
		if(tr.className == "TR_FOCUS") {
			if((i - 1 + 1) % 2 == 1) {
				tr.className = "TR_ODD";
			} else {
				tr.className = "TR_EVEN";
			}
		}
	}
}
