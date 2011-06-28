<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/csiebug-ui" %>

<%@page import="csiebug.util.WebUtility"%>

<%WebUtility webutil = new WebUtility();%>


<html>
	<head>
		<%@ include file="../../../pub/common_css.jsp" %>
		<%=webutil.getImportCSSFileLink() %>
		<link rel="stylesheet" type="text/css" href="<%=webutil.getBasePathForHTML()%>js/farbtastic/farbtastic.css" />
		
		<link rel="shortcut icon" href="<%=webutil.getBasePathForHTML()%>images/favicon.ico" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title><%=webutil.getRequestAttribute("FunctionName").toString()%></title>
	</head>
	<body>
		<form id="textArea" name="textArea" action="<%=webutil.getBasePathForHTML()%>example/textArea" method="POST">
			<!-- 程式標頭 -->
			<table width="100%">
				<tr>
					<td class="PageHeader"><%=webutil.getRequestAttribute("FunctionName").toString()%></td>
				</tr>
			</table>
			<%@ include file="../../../pub/common_html.jsp" %>
			<%@ include file="../../../pub/common_form.jsp" %>
			
			<!-- 以下為頁面編輯區 -->
			
			<h4>TextArea</h4>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('textArea')" style="cursor:pointer">預設(class="Text"; DataType="string"; 有title的td; 輸入長度和資料格式不限制)</legend>
				<div id="textArea_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<textarea id="textArea1" name="textArea1"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<table>
						<tr>
							<c:textarea id="textArea1" name="textArea1"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('className')" style="cursor:pointer">屬性className: 設定textArea所套用的css class</legend>
				<div id="className_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<textarea id="textArea2" name="textArea2" className="TextReadOnly"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<table>
						<tr>
							<c:textarea id="textArea2" name="textArea2" className="TextReadOnly"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('dataType')" style="cursor:pointer">屬性dataType: 會做資料格式的檢查；格式有string(預設)、IDNO(身分證字號)、password、function、email、number、currency、date、time、color、IP</legend>
				<div id="dataType_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<script type="text/javascript">
						function textArea6_onClick() {
							document.getElementById("textArea6").value = 'test';
						}
					</script>
					<table>
						<tr>
							<textarea id="textArea3" name="textArea3" header='<%=webutil.getMessage("common.money") %>' dataType="currency"/>
						</tr>
						<tr>
							<textarea id="textArea4" name="textArea4" header='<%=webutil.getMessage("common.date") %>' dataType="date"/>
						</tr>
						<tr>
							<textarea id="textArea38" name="textArea38" header='<%=webutil.getMessage("common.time") %>' dataType="time12"/>
						</tr>
						<tr>
							<textarea id="textArea39" name="textArea39" header='<%=webutil.getMessage("common.time") %>' dataType="time24"/>
						</tr>
						<tr>
							<textarea id="textArea40" name="textArea40" header='<%=webutil.getMessage("common.datetime") %>' dataType="datetime"/>
						</tr>
						<tr>
							<textarea id="textArea5" name="textArea5" header='<%=webutil.getMessage("common.email") %>' dataType="email"/>
						</tr>
						<tr>
							<textarea id="textArea6" name="textArea6" header='<%=webutil.getMessage("common.function") %>' dataType="function"/>
						</tr>
						<tr>
							<textarea id="textArea7" name="textArea7" header='<%=webutil.getMessage("common.IDNO") %>' dataType="IDNO"/>
						</tr>
						<tr>
							<textarea id="textArea8" name="textArea8" header='<%=webutil.getMessage("common.number") %>' dataType="number"/>
						</tr>
						<tr>
							<textarea id="textArea9" name="textArea9" header='<%=webutil.getMessage("common.password") %>' dataType="password"/>
						</tr>
						<tr>
							<textarea id="textArea35" name="textArea35" header='<%=webutil.getMessage("common.color") %>' dataType="color"/>
						</tr>
						<tr>
							<textarea id="textArea51" name="textArea51" header='<%=webutil.getMessage("common.IP") %>' dataType="IP"/>
						</tr>
						<tr>
							<textarea id="textArea53" name="textArea53" header='<%=webutil.getMessage("common.IP") %>' dataType="IPv6"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<script type="text/javascript">
						function textArea6_onClick() {
							document.getElementById("textArea6").value = 'test';
						}
					</script>
					<table>
						<tr>
							<c:textarea id="textArea3" name="textArea3" header='<%=webutil.getMessage("common.money") %>' dataType="currency"/>
						</tr>
						<tr>
							<c:textarea id="textArea4" name="textArea4" header='<%=webutil.getMessage("common.date") %>' dataType="date"/>
						</tr>
						<tr>
							<c:textarea id="textArea38" name="textArea38" header='<%=webutil.getMessage("common.time") %>' dataType="time12"/>
						</tr>
						<tr>
							<c:textarea id="textArea39" name="textArea39" header='<%=webutil.getMessage("common.time") %>' dataType="time24"/>
						</tr>
						<tr>
							<c:textarea id="textArea40" name="textArea40" header='<%=webutil.getMessage("common.datetime") %>' dataType="datetime"/>
						</tr>
						<tr>
							<c:textarea id="textArea5" name="textArea5" header='<%=webutil.getMessage("common.email") %>' dataType="email"/>
						</tr>
						<tr>
							<c:textarea id="textArea6" name="textArea6" header='<%=webutil.getMessage("common.function") %>' dataType="function"/>
						</tr>
						<tr>
							<c:textarea id="textArea7" name="textArea7" header='<%=webutil.getMessage("common.IDNO") %>' dataType="IDNO"/>
						</tr>
						<tr>
							<c:textarea id="textArea8" name="textArea8" header='<%=webutil.getMessage("common.number") %>' dataType="number"/>
						</tr>
						<tr>
							<c:textarea id="textArea9" name="textArea9" header='<%=webutil.getMessage("common.password") %>' dataType="password"/>
						</tr>
						<tr>
							<c:textarea id="textArea35" name="textArea35" header='<%=webutil.getMessage("common.color") %>' dataType="color"/>
						</tr>
						<tr>
							<c:textarea id="textArea51" name="textArea51" header='<%=webutil.getMessage("common.IP") %>' dataType="IP"/>
						</tr>
						<tr>
							<c:textarea id="textArea53" name="textArea53" header='<%=webutil.getMessage("common.IP") %>' dataType="IPv6"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('op')" style="cursor:pointer">屬性op: 配合屬性dataType="date"或"color"使用；BUTTON(按按鈕出現)、TEXT(按textArea出現)</legend>
				<div id="op_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<textarea id="textArea10" name="textArea10" header='<%=webutil.getMessage("common.date") %>' dataType="date" op="BUTTON"/>
						</tr>
						<tr>
							<textarea id="textArea11" name="textArea11" header='<%=webutil.getMessage("common.date") %>' dataType="date" op="TEXT"/>
						</tr>
						<tr>
							<textarea id="textArea36" name="textArea36" header='<%=webutil.getMessage("common.color") %>' dataType="color" op="BUTTON"/>
						</tr>
						<tr>
							<textarea id="textArea37" name="textArea37" header='<%=webutil.getMessage("common.color") %>' dataType="color" op="TEXT"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textarea id="textArea10" name="textArea10" header='<%=webutil.getMessage("common.date") %>' dataType="date" op="BUTTON"/>
						</tr>
						<tr>
							<c:textarea id="textArea11" name="textArea11" header='<%=webutil.getMessage("common.date") %>' dataType="date" op="TEXT"/>
						</tr>
						<tr>
							<c:textarea id="textArea36" name="textArea36" header='<%=webutil.getMessage("common.color") %>' dataType="color" op="BUTTON"/>
						</tr>
						<tr>
							<c:textarea id="textArea37" name="textArea37" header='<%=webutil.getMessage("common.color") %>' dataType="color" op="TEXT"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('defaultValue')" style="cursor:pointer">屬性defaultValue: 設定預設值；但效力低於isReturnValue="true"(form post帶值回來)、userValue(從request attribute取值)</legend>
				<div id="defaultValue_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<textarea id="textArea12" name="textArea12" defaultValue="test"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textarea id="textArea12" name="textArea12" defaultValue="test"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('fixlength')" style="cursor:pointer">屬性fixlength: 限制輸入長度(不可大於也不可小於)</legend>
				<div id="fixlength_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<textarea id="textArea13" name="textArea13" fixlength="5"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textarea id="textArea13" name="textArea13" fixlength="5"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('footer')" style="cursor:pointer">屬性footer/footerClass: 設定標尾文字/標尾文字套用的css class</legend>
				<div id="footer_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<textarea id="textArea14" name="textArea14" dataType="currency" footer="kg"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textarea id="textArea14" name="textArea14" dataType="currency" footer="kg"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('header')" style="cursor:pointer">屬性header/headerClass: 設定標頭文字/標頭套用的css class</legend>
				<div id="header_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<textarea id="textArea15" name="textArea15" header="title"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textarea id="textArea15" name="textArea15" header="title"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('imagePath')">屬性imagePath: 設定圖檔路徑，預設值="/images"(此屬性是配合dataType="date"或"function"的image button)</legend>
				<div id="imagePath_sidebar" style="display:none">
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('isMasked')" style="cursor:pointer">屬性isMasked: 設定特定格式字串的遮罩(dataType="date"、"time12"、"time24"、"datetime"、"IP")</legend>
				<div id="isMasked_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<textarea id="textArea41" name="textArea41" header='<%=webutil.getMessage("common.date") %>' dataType="date" isMasked="true"/>
						</tr>
						<tr>
							<textarea id="textArea42" name="textArea42" header='<%=webutil.getMessage("common.time") %>' dataType="time12" isMasked="true"/>
						</tr>
						<tr>
							<textarea id="textArea43" name="textArea43" header='<%=webutil.getMessage("common.time") %>' dataType="time24" isMasked="true"/>
						</tr>
						<tr>
							<textarea id="textArea44" name="textArea44" header='<%=webutil.getMessage("common.datetime") %>' dataType="datetime" isMasked="true"/>
						</tr>
						<tr>
							<textarea id="textArea52" name="textArea52" header='<%=webutil.getMessage("common.IP") %>' dataType="IP" isMasked="true"/>
						</tr>
						<tr>
							<textarea id="textArea54" name="textArea54" header='<%=webutil.getMessage("common.IP") %>' dataType="IPv6" isMasked="true"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textarea id="textArea41" name="textArea41" header='<%=webutil.getMessage("common.date") %>' dataType="date" isMasked="true"/>
						</tr>
						<tr>
							<c:textarea id="textArea42" name="textArea42" header='<%=webutil.getMessage("common.time") %>' dataType="time12" isMasked="true"/>
						</tr>
						<tr>
							<c:textarea id="textArea43" name="textArea43" header='<%=webutil.getMessage("common.time") %>' dataType="time24" isMasked="true"/>
						</tr>
						<tr>
							<c:textarea id="textArea44" name="textArea44" header='<%=webutil.getMessage("common.datetime") %>' dataType="datetime" isMasked="true"/>
						</tr>
						<tr>
							<c:textarea id="textArea52" name="textArea52" header='<%=webutil.getMessage("common.IP") %>' dataType="IP" isMasked="true"/>
						</tr>
						<tr>
							<c:textarea id="textArea54" name="textArea54" header='<%=webutil.getMessage("common.IP") %>' dataType="IPv6" isMasked="true"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('isReadOnly')" style="cursor:pointer">屬性isReadOnly: 設定唯讀</legend>
				<div id="isReadOnly_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<textarea id="textArea16" name="textArea16" isReadOnly="true"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textarea id="textArea16" name="textArea16" isReadOnly="true"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend id="textArea17_legend" onClick="openSidebarFolder('isRequired')" style="cursor:pointer">屬性isRequired: 設定必填(配合js function: checkRequired()可達到檢查效果)</legend>
				<div id="isRequired_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<script type="text/javascript">
						function textArea17_submit(form, warning, required, ok) {
							if(checkRequired(form, warning, required, ok)) {
								lockButton();
								document.getElementById("ActFlag").value = "textArea17";
								document.getElementById(form).submit();
							}
						}
					</script>
					<table>
						<tr>
							<textarea id="textArea17" name="textArea17" header="key" isRequired="true"/>
						</tr>
						<tr>
							<td>
								<button type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="textArea17_submit('textArea', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
							</td>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<script type="text/javascript">
						function textArea17_submit(form, warning, required, ok) {
							if(checkRequired(form, warning, required, ok)) {
								lockButton();
								document.getElementById("ActFlag").value = "textArea17";
								document.getElementById(form).submit();
							}
						}
					</script>
					<table>
						<tr>
							<c:textarea id="textArea17" name="textArea17" header="key" isRequired="true"/>
						</tr>
						<tr>
							<td>
								<button type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="textArea17_submit('textArea', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
							</td>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend id="textArea18_legend" onClick="openSidebarFolder('isReturnValue')" style="cursor:pointer">屬性isReturnValue: 設定值(form post帶值回來)；預設是true；但效力低於userValue(從request attribute取值)</legend>
				<div id="isReturnValue_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<script type="text/javascript">
						function textArea18_submit(form, warning, required, ok) {
							document.getElementById("ActFlag").value = "textArea18";
							document.getElementById(form).submit();
						}
					</script>
					<table>
						<tr>
							<textarea id="textArea18" name="textArea18" isReturnValue="false"/>
						</tr>
						<tr>
							<td>
								<button type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="textArea18_submit('textArea', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
							</td>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<script type="text/javascript">
						function textArea18_submit(form, warning, required, ok) {
							document.getElementById("ActFlag").value = "textArea18";
							document.getElementById(form).submit();
						}
					</script>
					<table>
						<tr>
							<c:textarea id="textArea18" name="textArea18" isReturnValue="false"/>
						</tr>
						<tr>
							<td>
								<button type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="textArea18_submit('textArea', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
							</td>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('maxdecimal')" style="cursor:pointer">屬性maxdecimal: 設定小數位數到幾位(要配合dataType="currency"或"number"使用)</legend>
				<div id="maxdecimal_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<textarea id="textArea19" name="textArea19" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxdecimal="2"/>
						</tr>
						<tr>
							<textarea id="textArea20" name="textArea20" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxdecimal="3"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textarea id="textArea19" name="textArea19" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxdecimal="2"/>
						</tr>
						<tr>
							<c:textarea id="textArea20" name="textArea20" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxdecimal="3"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('maxinteger')" style="cursor:pointer">屬性maxinteger: 設定整數位數到幾位(要配合dataType="currency"或"number"使用)</legend>
				<div id="maxinteger_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<textarea id="textArea21" name="textArea21" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxdecimal="2" maxinteger="4"/>
						</tr>
						<tr>
							<textarea id="textArea22" name="textArea22" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxdecimal="3" maxinteger="5"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textarea id="textArea21" name="textArea21" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxdecimal="2" maxinteger="4"/>
						</tr>
						<tr>
							<c:textarea id="textArea22" name="textArea22" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxdecimal="3" maxinteger="5"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('maxlength')" style="cursor:pointer">屬性maxlength: 設定輸入最長長度</legend>
				<div id="maxlength_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<textarea id="textArea23" name="textArea23" maxlength="5"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textarea id="textArea23" name="textArea23" maxlength="5"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('maxvalue')" style="cursor:pointer">屬性maxvalue: 設定輸入值的上限(要配合dataType="currency"或"number"使用)</legend>
				<div id="maxvalue_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<textarea id="textArea24" name="textArea24" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxvalue="1000"/>
						</tr>
						<tr>
							<textarea id="textArea25" name="textArea25" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxvalue="1000"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textarea id="textArea24" name="textArea24" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxvalue="1000"/>
						</tr>
						<tr>
							<c:textarea id="textArea25" name="textArea25" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxvalue="1000"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('minvalue')" style="cursor:pointer">屬性minvalue: 設定輸入值的下限(要配合dataType="currency"或"number"使用)</legend>
				<div id="minvalue_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<textarea id="textArea26" name="textArea26" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxvalue="1000" minvalue="500"/>
						</tr>
						<tr>
							<textarea id="textArea27" name="textArea27" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxvalue="1000" minvalue="500"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textarea id="textArea26" name="textArea26" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxvalue="1000" minvalue="500"/>
						</tr>
						<tr>
							<c:textarea id="textArea27" name="textArea27" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxvalue="1000" minvalue="500"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('numberStep')" style="cursor:pointer">屬性numberStep: 設定數字步進量(按鍵盤上下鍵可以加減的數值)</legend>
				<div id="numberStep_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<textarea id="textarea45" name="textarea45" header='<%=webutil.getMessage("common.number") %>' dataType="number" numberStep="5" maxvalue="50"/>
						</tr>
						<tr>
							<textarea id="textarea46" name="textarea46" header='<%=webutil.getMessage("common.number") %>' dataType="number" numberStep="5" minvalue="5"/>
						</tr>
						<tr>
							<textarea id="textarea47" name="textarea47" header='<%=webutil.getMessage("common.number") %>' dataType="number" numberStep="5" maxvalue="50" minvalue="5"/>
						</tr>
						<tr>
							<textarea id="textarea48" name="textarea48" header='<%=webutil.getMessage("common.money") %>' dataType="currency" numberStep="5000" maxvalue="50000"/>
						</tr>
						<tr>
							<textarea id="textarea49" name="textarea49" header='<%=webutil.getMessage("common.money") %>' dataType="currency" numberStep="5000" minvalue="5000"/>
						</tr>
						<tr>
							<textarea id="textarea50" name="textarea50" header='<%=webutil.getMessage("common.money") %>' dataType="currency" numberStep="5000" maxvalue="50000" minvalue="5000"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textarea id="textarea45" name="textarea45" header='<%=webutil.getMessage("common.number") %>' dataType="number" numberStep="5" maxvalue="50"/>
						</tr>
						<tr>
							<c:textarea id="textarea46" name="textarea46" header='<%=webutil.getMessage("common.number") %>' dataType="number" numberStep="5" minvalue="5"/>
						</tr>
						<tr>
							<c:textarea id="textarea47" name="textarea47" header='<%=webutil.getMessage("common.number") %>' dataType="number" numberStep="5" maxvalue="50" minvalue="5"/>
						</tr>
						<tr>
							<c:textarea id="textarea48" name="textarea48" header='<%=webutil.getMessage("common.money") %>' dataType="currency" numberStep="5000" maxvalue="50000"/>
						</tr>
						<tr>
							<c:textarea id="textarea49" name="textarea49" header='<%=webutil.getMessage("common.money") %>' dataType="currency" numberStep="5000" minvalue="5000"/>
						</tr>
						<tr>
							<c:textarea id="textarea50" name="textarea50" header='<%=webutil.getMessage("common.money") %>' dataType="currency" numberStep="5000" maxvalue="50000" minvalue="5000"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('onBlur')" style="cursor:pointer">屬性onBlur: 設定onBlur事件</legend>
				<div id="onBlur_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<textarea id="textArea28" name="textArea28" onBlur="alert('blur!');"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textarea id="textArea28" name="textArea28" onBlur="alert('blur!');"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('onChange')" style="cursor:pointer">屬性onChange: 設定onChange事件</legend>
				<div id="onChange_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<textarea id="textArea29" name="textArea29" onChange="alert('change!');"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textarea id="textArea29" name="textArea29" onChange="alert('change!');"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('onClick')" style="cursor:pointer">屬性onClick: 設定onClick事件</legend>
				<div id="onClick_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<textarea id="textArea30" name="textArea30" onClick="alert('click!')"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textarea id="textArea30" name="textArea30" onClick="alert('click!')"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('onKeyDown')" style="cursor:pointer">屬性onKeyDown: 設定onKeyDown事件</legend>
				<div id="onKeyDown_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<textarea id="textArea34" name="textArea34" onKeyDown="alert('key down!')"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textarea id="textArea34" name="textArea34" onKeyDown="alert('key down!')"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('rows')" style="cursor:pointer">屬性rows: 設定textArea顯示行數</legend>
				<div id="rows_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<textarea id="textArea33" name="textArea33" rows="10"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textarea id="textArea33" name="textArea33" rows="10"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('size')">屬性size: 設定size(textArea控制項顯示長度)</legend>
				<div id="size_sidebar" style="display:none">
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('style')">屬性style: 設定textArea控制項style</legend>
				<div id="style_sidebar" style="display:none">
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('typesetting')" style="cursor:pointer">屬性typesetting: 設定排版；預設是"true"，產生td與label；反之則沒有td和label，只產生textArea控制項本身</legend>
				<div id="typesetting_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<td>
								title
							</td>
							<td>
								<textarea id="textArea31" name="textArea31" typesetting="false"/>
							</td>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<td>
								title
							</td>
							<td>
								<c:textarea id="textArea31" name="textArea31" typesetting="false"/>
							</td>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('userValue')" style="cursor:pointer">屬性userValue: 設定值(從request attribute取值)</legend>
				<div id="userValue_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					setRequestAttribute("textArea32_requestAttribute", "test");
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<textarea id="textArea32" name="textArea32" userValue="textArea32_requestAttribute"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textarea id="textArea32" name="textArea32" userValue="textArea32_requestAttribute"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<br>
			<br>
			<br>
			<br>
			<br>
			
			<!-- 頁面編輯區結束 -->
			
			<!-- Javascript區 -->
			<%=webutil.getImportJSFileLink() %>
			<script src="<%=webutil.getBasePathForHTML()%>js/csiebug-treeview/csiebug-sidebar.js"></script>
			<script type="text/javascript" src="<%=webutil.getBasePathForHTML()%>js/farbtastic/farbtastic.js"></script>
			
			<script type="text/javascript">
				$(document).ready(function() {
					<% if(webutil.getRequestAttribute("Script") != null) { out.print(webutil.getRequestAttribute("Script").toString()); }%>
					<% if(webutil.getRequestAttribute("Msg") != null) { out.print(webutil.getRequestAttribute("Msg").toString()); }%>
				});
			</script>
			<!-- Javascript區結束 -->
		</form>
	</body>
</html>

