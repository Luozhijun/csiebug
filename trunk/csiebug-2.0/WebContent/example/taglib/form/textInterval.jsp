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
		<form id="textInterval" name="textInterval" action="<%=webutil.getBasePathForHTML()%>example/textInterval" method="POST">
			<!-- 程式標頭 -->
			<table width="100%">
				<tr>
					<td class="PageHeader"><%=webutil.getRequestAttribute("FunctionName").toString()%></td>
				</tr>
			</table>
			<%@ include file="../../../pub/common_html.jsp" %>
			<%@ include file="../../../pub/common_form.jsp" %>
			
			<!-- 以下為頁面編輯區 -->
			
			<h4>Text Interval (通常用在dataType="currency"或"date"或"number"上)</h4>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('textInterval')" style="cursor:pointer">預設(class="Text"; DataType="string"; 有title的td; 輸入長度和資料格式不限制)</legend>
				<div id="textInterval_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<textinterval id="textInterval1" name="textInterval1"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<table>
						<tr>
							<c:textinterval id="textInterval1" name="textInterval1"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('className')" style="cursor:pointer">屬性className: 設定textInterval所套用的css class</legend>
				<div id="className_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<textinterval id="textInterval2" name="textInterval2" className="TextReadOnly"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<table>
						<tr>
							<c:textinterval id="textInterval2" name="textInterval2" className="TextReadOnly"/>
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
						function textInterval6_start_onClick() {
							document.getElementById("textInterval6_start").value = 'start';
						}
						function textInterval6_end_onClick() {
							document.getElementById("textInterval6_end").value = 'end';
						}
					</script>
					<table>
						<tr>
							<textinterval id="textInterval3" name="textInterval3" header='<%=webutil.getMessage("common.money") %>' dataType="currency"/>
						</tr>
						<tr>
							<textinterval id="textInterval4" name="textInterval4" header='<%=webutil.getMessage("common.date") %>' dataType="date"/>
						</tr>
						<tr>
							<textinterval id="textInterval37" name="textInterval37" header='<%=webutil.getMessage("common.time") %>' dataType="time12"/>
						</tr>
						<tr>
							<textinterval id="textInterval38" name="textInterval38" header='<%=webutil.getMessage("common.time") %>' dataType="time24"/>
						</tr>
						<tr>
							<textinterval id="textInterval39" name="textInterval39" header='<%=webutil.getMessage("common.datetime") %>' dataType="datetime"/>
						</tr>
						<tr>
							<textinterval id="textInterval5" name="textInterval5" header='<%=webutil.getMessage("common.email") %>' dataType="email"/>
						</tr>
						<tr>
							<textinterval id="textInterval6" name="textInterval6" header='<%=webutil.getMessage("common.function") %>' dataType="function"/>
						</tr>
						<tr>
							<textinterval id="textInterval7" name="textInterval7" header='<%=webutil.getMessage("common.IDNO") %>' dataType="IDNO"/>
						</tr>
						<tr>
							<textinterval id="textInterval8" name="textInterval8" header='<%=webutil.getMessage("common.number") %>' dataType="number"/>
						</tr>
						<tr>
							<textinterval id="textInterval9" name="textInterval9" header='<%=webutil.getMessage("common.password") %>' dataType="password"/>
						</tr>
						<tr>
							<textinterval id="textInterval34" name="textInterval34" header='<%=webutil.getMessage("common.color") %>' dataType="color"/>
						</tr>
						<tr>
							<textinterval id="textInterval50" name="textInterval50" header='<%=webutil.getMessage("common.IP") %>' dataType="IP"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<script type="text/javascript">
						function textInterval6_start_onClick() {
							document.getElementById("textInterval6_start").value = 'start';
						}
						function textInterval6_end_onClick() {
							document.getElementById("textInterval6_end").value = 'end';
						}
					</script>
					<table>
						<tr>
							<c:textinterval id="textInterval3" name="textInterval3" header='<%=webutil.getMessage("common.money") %>' dataType="currency"/>
						</tr>
						<tr>
							<c:textinterval id="textInterval4" name="textInterval4" header='<%=webutil.getMessage("common.date") %>' dataType="date"/>
						</tr>
						<tr>
							<c:textinterval id="textInterval37" name="textInterval37" header='<%=webutil.getMessage("common.time") %>' dataType="time12"/>
						</tr>
						<tr>
							<c:textinterval id="textInterval38" name="textInterval38" header='<%=webutil.getMessage("common.time") %>' dataType="time24"/>
						</tr>
						<tr>
							<c:textinterval id="textInterval39" name="textInterval39" header='<%=webutil.getMessage("common.datetime") %>' dataType="datetime"/>
						</tr>
						<tr>
							<c:textinterval id="textInterval5" name="textInterval5" header='<%=webutil.getMessage("common.email") %>' dataType="email"/>
						</tr>
						<tr>
							<c:textinterval id="textInterval6" name="textInterval6" header='<%=webutil.getMessage("common.function") %>' dataType="function"/>
						</tr>
						<tr>
							<c:textinterval id="textInterval7" name="textInterval7" header='<%=webutil.getMessage("common.IDNO") %>' dataType="IDNO"/>
						</tr>
						<tr>
							<c:textinterval id="textInterval8" name="textInterval8" header='<%=webutil.getMessage("common.number") %>' dataType="number"/>
						</tr>
						<tr>
							<c:textinterval id="textInterval9" name="textInterval9" header='<%=webutil.getMessage("common.password") %>' dataType="password"/>
						</tr>
						<tr>
							<c:textinterval id="textInterval34" name="textInterval34" header='<%=webutil.getMessage("common.color") %>' dataType="color"/>
						</tr>
						<tr>
							<c:textinterval id="textInterval50" name="textInterval50" header='<%=webutil.getMessage("common.IP") %>' dataType="IP"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('op')" style="cursor:pointer">屬性op: 配合屬性dataType="date"或"color"使用；BUTTON(按按鈕出現)、TEXT(按textInterval出現)</legend>
				<div id="op_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<textinterval id="textInterval10" name="textInterval10" header='<%=webutil.getMessage("common.date") %>' dataType="date" op="BUTTON"/>
						</tr>
						<tr>
							<textinterval id="textInterval11" name="textInterval11" header='<%=webutil.getMessage("common.date") %>' dataType="date" op="TEXT"/>
						</tr>
						<tr>
							<textinterval id="textInterval35" name="textInterval35" header='<%=webutil.getMessage("common.color") %>' dataType="color" op="BUTTON"/>
						</tr>
						<tr>
							<textinterval id="textInterval36" name="textInterval36" header='<%=webutil.getMessage("common.color") %>' dataType="color" op="TEXT"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textinterval id="textInterval10" name="textInterval10" header='<%=webutil.getMessage("common.date") %>' dataType="date" op="BUTTON"/>
						</tr>
						<tr>
							<c:textinterval id="textInterval11" name="textInterval11" header='<%=webutil.getMessage("common.date") %>' dataType="date" op="TEXT"/>
						</tr>
						<tr>
							<c:textinterval id="textInterval35" name="textInterval35" header='<%=webutil.getMessage("common.color") %>' dataType="color" op="BUTTON"/>
						</tr>
						<tr>
							<c:textinterval id="textInterval36" name="textInterval36" header='<%=webutil.getMessage("common.color") %>' dataType="color" op="TEXT"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('defaultValue')" style="cursor:pointer">屬性defaultValueStart/defaultValueEnd: 設定預設值；但效力低於isReturnValue="true"(form post帶值回來)、userValue(從request attribute取值)</legend>
				<div id="defaultValue_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<textinterval id="textInterval12" name="textInterval12" defaultValueStart="100" defaultValueEnd="500"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textinterval id="textInterval12" name="textInterval12" defaultValueStart="100" defaultValueEnd="500"/>
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
							<textinterval id="textInterval13" name="textInterval13" fixlength="5"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textinterval id="textInterval13" name="textInterval13" fixlength="5"/>
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
							<textinterval id="textInterval14" name="textInterval14" dataType="currency" footer="kg"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textinterval id="textInterval14" name="textInterval14" dataType="currency" footer="kg"/>
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
							<textinterval id="textInterval15" name="textInterval15" header="title"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textinterval id="textInterval15" name="textInterval15" header="title"/>
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
							<textinterval id="textInterval40" name="textInterval40" header='<%=webutil.getMessage("common.date") %>' dataType="date" isMasked="true"/>
						</tr>
						<tr>
							<textinterval id="textInterval41" name="textInterval41" header='<%=webutil.getMessage("common.time") %>' dataType="time12" isMasked="true"/>
						</tr>
						<tr>
							<textinterval id="textInterval42" name="textInterval42" header='<%=webutil.getMessage("common.time") %>' dataType="time24" isMasked="true"/>
						</tr>
						<tr>
							<textinterval id="textInterval43" name="textInterval43" header='<%=webutil.getMessage("common.datetime") %>' dataType="datetime" isMasked="true"/>
						</tr>
						<tr>
							<textinterval id="textInterval51" name="textInterval51" header='<%=webutil.getMessage("common.IP") %>' dataType="IP" isMasked="true"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textinterval id="textInterval40" name="textInterval40" header='<%=webutil.getMessage("common.date") %>' dataType="date" isMasked="true"/>
						</tr>
						<tr>
							<c:textinterval id="textInterval41" name="textInterval41" header='<%=webutil.getMessage("common.time") %>' dataType="time12" isMasked="true"/>
						</tr>
						<tr>
							<c:textinterval id="textInterval42" name="textInterval42" header='<%=webutil.getMessage("common.time") %>' dataType="time24" isMasked="true"/>
						</tr>
						<tr>
							<c:textinterval id="textInterval43" name="textInterval43" header='<%=webutil.getMessage("common.datetime") %>' dataType="datetime" isMasked="true"/>
						</tr>
						<tr>
							<c:textinterval id="textInterval51" name="textInterval51" header='<%=webutil.getMessage("common.IP") %>' dataType="IP" isMasked="true"/>
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
							<textinterval id="textInterval16" name="textInterval16" isReadOnly="true"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textinterval id="textInterval16" name="textInterval16" isReadOnly="true"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend id="textInterval17_legend" onClick="openSidebarFolder('isRequired')" style="cursor:pointer">屬性isRequired: 設定必填(配合js function: checkRequired()可達到檢查效果)</legend>
				<div id="isRequired_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<script type="text/javascript">
						function textInterval17_submit(form, warning, required, ok) {
							if(checkRequired(form, warning, required, ok)) {
								lockButton();
								document.getElementById("ActFlag").value = "textInterval17";
								document.getElementById(form).submit();
							}
						}
					</script>
					<table>
						<tr>
							<textinterval id="textInterval17" name="textInterval17" header="key" isRequired="true"/>
						</tr>
						<tr>
							<td>
								<button type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="textInterval17_submit('textInterval', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
							</td>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<script type="text/javascript">
						function textInterval17_submit(form, warning, required, ok) {
							if(checkRequired(form, warning, required, ok)) {
								lockButton();
								document.getElementById("ActFlag").value = "textInterval17";
								document.getElementById(form).submit();
							}
						}
					</script>
					<table>
						<tr>
							<c:textinterval id="textInterval17" name="textInterval17" header="key" isRequired="true"/>
						</tr>
						<tr>
							<td>
								<button type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="textInterval17_submit('textInterval', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
							</td>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend id="textInterval18_legend" onClick="openSidebarFolder('isReturnValue')" style="cursor:pointer">屬性isReturnValue: 設定值(form post帶值回來)；預設是true；但效力低於userValue(從request attribute取值)</legend>
				<div id="isReturnValue_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<script type="text/javascript">
						function textInterval18_submit(form, warning, required, ok) {
							document.getElementById("ActFlag").value = "textInterval18";
							document.getElementById(form).submit();
						}
					</script>
					<table>
						<tr>
							<textinterval id="textInterval18" name="textInterval18" isReturnValue="false"/>
						</tr>
						<tr>
							<td>
								<button type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="textInterval18_submit('textInterval', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
							</td>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<script type="text/javascript">
						function textInterval18_submit(form, warning, required, ok) {
							document.getElementById("ActFlag").value = "textInterval18";
							document.getElementById(form).submit();
						}
					</script>
					<table>
						<tr>
							<c:textinterval id="textInterval18" name="textInterval18" isReturnValue="false"/>
						</tr>
						<tr>
							<td>
								<button type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="textInterval18_submit('textInterval', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
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
							<textinterval id="textInterval19" name="textInterval19" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxdecimal="2"/>
						</tr>
						<tr>
							<textinterval id="textInterval20" name="textInterval20" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxdecimal="3"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textinterval id="textInterval19" name="textInterval19" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxdecimal="2"/>
						</tr>
						<tr>
							<c:textinterval id="textInterval20" name="textInterval20" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxdecimal="3"/>
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
							<textinterval id="textInterval21" name="textInterval21" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxdecimal="2" maxinteger="4"/>
						</tr>
						<tr>
							<textinterval id="textInterval22" name="textInterval22" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxdecimal="3" maxinteger="5"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textinterval id="textInterval21" name="textInterval21" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxdecimal="2" maxinteger="4"/>
						</tr>
						<tr>
							<c:textinterval id="textInterval22" name="textInterval22" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxdecimal="3" maxinteger="5"/>
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
							<textinterval id="textInterval23" name="textInterval23" maxlength="5"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textinterval id="textInterval23" name="textInterval23" maxlength="5"/>
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
							<textinterval id="textInterval24" name="textInterval24" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxvalue="1000"/>
						</tr>
						<tr>
							<textinterval id="textInterval25" name="textInterval25" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxvalue="1000"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textinterval id="textInterval24" name="textInterval24" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxvalue="1000"/>
						</tr>
						<tr>
							<c:textinterval id="textInterval25" name="textInterval25" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxvalue="1000"/>
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
							<textinterval id="textInterval26" name="textInterval26" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxvalue="1000" minvalue="500"/>
						</tr>
						<tr>
							<textinterval id="textInterval27" name="textInterval27" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxvalue="1000" minvalue="500"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textinterval id="textInterval26" name="textInterval26" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxvalue="1000" minvalue="500"/>
						</tr>
						<tr>
							<c:textinterval id="textInterval27" name="textInterval27" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxvalue="1000" minvalue="500"/>
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
							<textinterval id="textinterval44" name="textinterval44" header='<%=webutil.getMessage("common.number") %>' dataType="number" numberStep="5" maxvalue="50"/>
						</tr>
						<tr>
							<textinterval id="textinterval45" name="textinterval45" header='<%=webutil.getMessage("common.number") %>' dataType="number" numberStep="5" minvalue="5"/>
						</tr>
						<tr>
							<textinterval id="textinterval46" name="textinterval46" header='<%=webutil.getMessage("common.number") %>' dataType="number" numberStep="5" maxvalue="50" minvalue="5"/>
						</tr>
						<tr>
							<textinterval id="textinterval47" name="textinterval47" header='<%=webutil.getMessage("common.money") %>' dataType="currency" numberStep="5000" maxvalue="50000"/>
						</tr>
						<tr>
							<textinterval id="textinterval48" name="textinterval48" header='<%=webutil.getMessage("common.money") %>' dataType="currency" numberStep="5000" minvalue="5000"/>
						</tr>
						<tr>
							<textinterval id="textinterval49" name="textinterval49" header='<%=webutil.getMessage("common.money") %>' dataType="currency" numberStep="5000" maxvalue="50000" minvalue="5000"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textinterval id="textinterval44" name="textinterval44" header='<%=webutil.getMessage("common.number") %>' dataType="number" numberStep="5" maxvalue="50"/>
						</tr>
						<tr>
							<c:textinterval id="textinterval45" name="textinterval45" header='<%=webutil.getMessage("common.number") %>' dataType="number" numberStep="5" minvalue="5"/>
						</tr>
						<tr>
							<c:textinterval id="textinterval46" name="textinterval46" header='<%=webutil.getMessage("common.number") %>' dataType="number" numberStep="5" maxvalue="50" minvalue="5"/>
						</tr>
						<tr>
							<c:textinterval id="textinterval47" name="textinterval47" header='<%=webutil.getMessage("common.money") %>' dataType="currency" numberStep="5000" maxvalue="50000"/>
						</tr>
						<tr>
							<c:textinterval id="textinterval48" name="textinterval48" header='<%=webutil.getMessage("common.money") %>' dataType="currency" numberStep="5000" minvalue="5000"/>
						</tr>
						<tr>
							<c:textinterval id="textinterval49" name="textinterval49" header='<%=webutil.getMessage("common.money") %>' dataType="currency" numberStep="5000" maxvalue="50000" minvalue="5000"/>
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
							<textinterval id="textInterval28" name="textInterval28" onBlur="alert('blur!');"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textinterval id="textInterval28" name="textInterval28" onBlur="alert('blur!');"/>
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
							<textinterval id="textInterval29" name="textInterval29" onChange="alert('change!');"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textinterval id="textInterval29" name="textInterval29" onChange="alert('change!');"/>
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
							<textinterval id="textInterval30" name="textInterval30" onClick="alert('click!')"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textinterval id="textInterval30" name="textInterval30" onClick="alert('click!')"/>
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
							<textinterval id="textInterval33" name="textInterval33" onKeyDown="alert('key down!')"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textinterval id="textInterval33" name="textInterval33" onKeyDown="alert('key down!')"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('size')">屬性size: 設定size(textInterval控制項顯示長度)</legend>
				<div id="size_sidebar" style="display:none">
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('style')">屬性style: 設定textInterval控制項style</legend>
				<div id="style_sidebar" style="display:none">
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('typesetting')" style="cursor:pointer">屬性typesetting: 設定排版；預設是"true"，產生td與label；反之則沒有td和label，只產生textInterval控制項本身</legend>
				<div id="typesetting_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<td>
								title
							</td>
							<td>
								<textinterval id="textInterval31" name="textInterval31" typesetting="false"/>
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
								<c:textinterval id="textInterval31" name="textInterval31" typesetting="false"/>
							</td>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('userValue')" style="cursor:pointer">屬性userValueStart/userValueEnd: 設定值(從request attribute取值)</legend>
				<div id="userValue_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					setRequestAttribute("textInterval32_start_requestAttribute", "start");
					setRequestAttribute("textInterval32_end_requestAttribute", "end");
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<textinterval id="textInterval32" name="textInterval32" userValueStart="textInterval32_start_requestAttribute" userValueEnd="textInterval32_end_requestAttribute"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:textinterval id="textInterval32" name="textInterval32" userValueStart="textInterval32_start_requestAttribute" userValueEnd="textInterval32_end_requestAttribute"/>
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

