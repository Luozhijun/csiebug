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
		<form id="text" name="text" action="<%=webutil.getBasePathForHTML()%>example/text" method="POST">
			<!-- 程式標頭 -->
			<table width="100%">
				<tr>
					<td class="PageHeader"><%=webutil.getRequestAttribute("FunctionName").toString()%></td>
				</tr>
			</table>
			<%@ include file="../../../pub/common_html.jsp" %>
			<%@ include file="../../../pub/common_form.jsp" %>
			
			<!-- 以下為頁面編輯區 -->
			
			<h4>Text</h4>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('text')" style="cursor:pointer">預設(class="Text"; DataType="string"; 有title的td; 輸入長度和資料格式不限制)</legend>
				<div id="text_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<text id="text1" name="text1"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<table>
						<tr>
							<c:text id="text1" name="text1"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('className')" style="cursor:pointer">屬性className: 設定text所套用的css class</legend>
				<div id="className_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<text id="text2" name="text2" className="TextReadOnly"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<table>
						<tr>
							<c:text id="text2" name="text2" className="TextReadOnly"/>
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
						function text6_onClick() {
							document.getElementById("text6").value = 'test';
						}
					</script>
					<table>
						<tr>
							<text id="text3" name="text3" header='<%=webutil.getMessage("common.money") %>' dataType="currency"/>
						</tr>
						<tr>
							<text id="text4" name="text4" header='<%=webutil.getMessage("common.date") %>' dataType="date"/>
						</tr>
						<tr>
							<text id="text37" name="text37" header='<%=webutil.getMessage("common.time") %>' dataType="time12"/>
						</tr>
						<tr>
							<text id="text38" name="text38" header='<%=webutil.getMessage("common.time") %>' dataType="time24"/>
						</tr>
						<tr>
							<text id="text39" name="text39" header='<%=webutil.getMessage("common.datetime") %>' dataType="datetime"/>
						</tr>
						<tr>
							<text id="text5" name="text5" header='<%=webutil.getMessage("common.email") %>' dataType="email"/>
						</tr>
						<tr>
							<text id="text6" name="text6" header='<%=webutil.getMessage("common.function") %>' dataType="function"/>
						</tr>
						<tr>
							<text id="text7" name="text7" header='<%=webutil.getMessage("common.IDNO") %>' dataType="IDNO"/>
						</tr>
						<tr>
							<text id="text8" name="text8" header='<%=webutil.getMessage("common.number") %>' dataType="number"/>
						</tr>
						<tr>
							<text id="text9" name="text9" header='<%=webutil.getMessage("common.password") %>' dataType="password"/>
						</tr>
						<tr>
							<text id="text34" name="text34" header='<%=webutil.getMessage("common.color") %>' dataType="color"/>
						</tr>
						<tr>
							<text id="text50" name="text50" header='<%=webutil.getMessage("common.IP") %>' dataType="IP"/>
						</tr>
						<tr>
							<text id="text52" name="text52" header='<%=webutil.getMessage("common.IP") %>' dataType="IPv6"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<script type="text/javascript">
						function text6_onClick() {
							document.getElementById("text6").value = 'test';
						}
					</script>
					<table>
						<tr>
							<c:text id="text3" name="text3" header='<%=webutil.getMessage("common.money") %>' dataType="currency"/>
						</tr>
						<tr>
							<c:text id="text4" name="text4" header='<%=webutil.getMessage("common.date") %>' dataType="date"/>
						</tr>
						<tr>
							<c:text id="text37" name="text37" header='<%=webutil.getMessage("common.time") %>' dataType="time12"/>
						</tr>
						<tr>
							<c:text id="text38" name="text38" header='<%=webutil.getMessage("common.time") %>' dataType="time24"/>
						</tr>
						<tr>
							<c:text id="text39" name="text39" header='<%=webutil.getMessage("common.datetime") %>' dataType="datetime"/>
						</tr>
						<tr>
							<c:text id="text5" name="text5" header='<%=webutil.getMessage("common.email") %>' dataType="email"/>
						</tr>
						<tr>
							<c:text id="text6" name="text6" header='<%=webutil.getMessage("common.function") %>' dataType="function"/>
						</tr>
						<tr>
							<c:text id="text7" name="text7" header='<%=webutil.getMessage("common.IDNO") %>' dataType="IDNO"/>
						</tr>
						<tr>
							<c:text id="text8" name="text8" header='<%=webutil.getMessage("common.number") %>' dataType="number"/>
						</tr>
						<tr>
							<c:text id="text9" name="text9" header='<%=webutil.getMessage("common.password") %>' dataType="password"/>
						</tr>
						<tr>
							<c:text id="text34" name="text34" header='<%=webutil.getMessage("common.color") %>' dataType="color"/>
						</tr>
						<tr>
							<c:text id="text50" name="text50" header='<%=webutil.getMessage("common.IP") %>' dataType="IP"/>
						</tr>
						<tr>
							<c:text id="text52" name="text52" header='<%=webutil.getMessage("common.IP") %>' dataType="IPv6"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('op')" style="cursor:pointer">屬性op: 配合屬性dataType="date"或"color"使用；BUTTON(按按鈕出現)、TEXT(按text出現)</legend>
				<div id="op_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<text id="text10" name="text10" header='<%=webutil.getMessage("common.date") %>' dataType="date" op="BUTTON"/>
						</tr>
						<tr>
							<text id="text11" name="text11" header='<%=webutil.getMessage("common.date") %>' dataType="date" op="TEXT"/>
						</tr>
						<tr>
							<text id="text35" name="text35" header='<%=webutil.getMessage("common.color") %>' dataType="color" op="BUTTON"/>
						</tr>
						<tr>
							<text id="text36" name="text36" header='<%=webutil.getMessage("common.color") %>' dataType="color" op="TEXT"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:text id="text10" name="text10" header='<%=webutil.getMessage("common.date") %>' dataType="date" op="BUTTON"/>
						</tr>
						<tr>
							<c:text id="text11" name="text11" header='<%=webutil.getMessage("common.date") %>' dataType="date" op="TEXT"/>
						</tr>
						<tr>
							<c:text id="text35" name="text35" header='<%=webutil.getMessage("common.color") %>' dataType="color" op="BUTTON"/>
						</tr>
						<tr>
							<c:text id="text36" name="text36" header='<%=webutil.getMessage("common.color") %>' dataType="color" op="TEXT"/>
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
							<text id="text12" name="text12" defaultValue="test"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:text id="text12" name="text12" defaultValue="test"/>
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
							<text id="text13" name="text13" fixlength="5"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:text id="text13" name="text13" fixlength="5"/>
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
							<text id="text14" name="text14" dataType="currency" footer="kg"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:text id="text14" name="text14" dataType="currency" footer="kg"/>
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
							<text id="text15" name="text15" header="title"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:text id="text15" name="text15" header="title"/>
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
							<text id="text40" name="text40" header='<%=webutil.getMessage("common.date") %>' dataType="date" isMasked="true"/>
						</tr>
						<tr>
							<text id="text41" name="text41" header='<%=webutil.getMessage("common.time") %>' dataType="time12" isMasked="true"/>
						</tr>
						<tr>
							<text id="text42" name="text42" header='<%=webutil.getMessage("common.time") %>' dataType="time24" isMasked="true"/>
						</tr>
						<tr>
							<text id="text43" name="text43" header='<%=webutil.getMessage("common.datetime") %>' dataType="datetime" isMasked="true"/>
						</tr>
						<tr>
							<text id="text51" name="text51" header='<%=webutil.getMessage("common.IP") %>' dataType="ip" isMasked="true"/>
						</tr>
						<tr>
							<text id="text53" name="text53" header='<%=webutil.getMessage("common.IP") %>' dataType="IPv6" isMasked="true"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:text id="text40" name="text40" header='<%=webutil.getMessage("common.date") %>' dataType="date" isMasked="true"/>
						</tr>
						<tr>
							<c:text id="text41" name="text41" header='<%=webutil.getMessage("common.time") %>' dataType="time12" isMasked="true"/>
						</tr>
						<tr>
							<c:text id="text42" name="text42" header='<%=webutil.getMessage("common.time") %>' dataType="time24" isMasked="true"/>
						</tr>
						<tr>
							<c:text id="text43" name="text43" header='<%=webutil.getMessage("common.datetime") %>' dataType="datetime" isMasked="true"/>
						</tr>
						<tr>
							<c:text id="text51" name="text51" header='<%=webutil.getMessage("common.IP") %>' dataType="ip" isMasked="true"/>
						</tr>
						<tr>
							<c:text id="text53" name="text53" header='<%=webutil.getMessage("common.IP") %>' dataType="IPv6" isMasked="true"/>
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
							<text id="text16" name="text16" isReadOnly="true"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:text id="text16" name="text16" isReadOnly="true"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend id="text17_legend" onClick="openSidebarFolder('isRequired')" style="cursor:pointer">屬性isRequired: 設定必填(配合js function: checkRequired()可達到檢查效果)</legend>
				<div id="isRequired_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<script type="text/javascript">
						function text17_submit(form, warning, required, ok) {
							if(checkRequired(form, warning, required, ok)) {
								lockButton();
								document.getElementById("ActFlag").value = "text17";
								document.getElementById(form).submit();
							}
						}
					</script>
					<table>
						<tr>
							<text id="text17" name="text17" header="key" isRequired="true"/>
						</tr>
						<tr>
							<td>
								<button type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="text17_submit('text', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
							</td>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<script type="text/javascript">
						function text17_submit(form, warning, required, ok) {
							if(checkRequired(form, warning, required, ok)) {
								lockButton();
								document.getElementById("ActFlag").value = "text17";
								document.getElementById(form).submit();
							}
						}
					</script>
					<table>
						<tr>
							<c:text id="text17" name="text17" header="key" isRequired="true"/>
						</tr>
						<tr>
							<td>
								<button type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="text17_submit('text', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
							</td>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend id="text18_legend" onClick="openSidebarFolder('isReturnValue')" style="cursor:pointer">屬性isReturnValue: 設定值(form post帶值回來)；預設是true；但效力低於userValue(從request attribute取值)</legend>
				<div id="isReturnValue_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<script type="text/javascript">
						function text18_submit(form, warning, required, ok) {
							document.getElementById("ActFlag").value = "text18";
							document.getElementById(form).submit();
						}
					</script>
					<table>
						<tr>
							<text id="text18" name="text18" isReturnValue="false"/>
						</tr>
						<tr>
							<td>
								<button type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="text18_submit('text', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
							</td>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<script type="text/javascript">
						function text18_submit(form, warning, required, ok) {
							document.getElementById("ActFlag").value = "text18";
							document.getElementById(form).submit();
						}
					</script>
					<table>
						<tr>
							<c:text id="text18" name="text18" isReturnValue="false"/>
						</tr>
						<tr>
							<td>
								<button type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="text18_submit('text', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
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
							<text id="text19" name="text19" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxdecimal="2"/>
						</tr>
						<tr>
							<text id="text20" name="text20" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxdecimal="3"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:text id="text19" name="text19" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxdecimal="2"/>
						</tr>
						<tr>
							<c:text id="text20" name="text20" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxdecimal="3"/>
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
							<text id="text21" name="text21" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxdecimal="2" maxinteger="4"/>
						</tr>
						<tr>
							<text id="text22" name="text22" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxdecimal="3" maxinteger="5"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:text id="text21" name="text21" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxdecimal="2" maxinteger="4"/>
						</tr>
						<tr>
							<c:text id="text22" name="text22" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxdecimal="3" maxinteger="5"/>
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
							<text id="text23" name="text23" maxlength="5"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:text id="text23" name="text23" maxlength="5"/>
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
							<text id="text24" name="text24" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxvalue="1000"/>
						</tr>
						<tr>
							<text id="text25" name="text25" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxvalue="1000"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:text id="text24" name="text24" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxvalue="1000"/>
						</tr>
						<tr>
							<c:text id="text25" name="text25" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxvalue="1000"/>
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
							<text id="text26" name="text26" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxvalue="1000" minvalue="500"/>
						</tr>
						<tr>
							<text id="text27" name="text27" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxvalue="1000" minvalue="500"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:text id="text26" name="text26" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxvalue="1000" minvalue="500"/>
						</tr>
						<tr>
							<c:text id="text27" name="text27" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxvalue="1000" minvalue="500"/>
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
							<text id="text44" name="text44" header='<%=webutil.getMessage("common.number") %>' dataType="number" numberStep="5" maxvalue="50"/>
						</tr>
						<tr>
							<text id="text45" name="text45" header='<%=webutil.getMessage("common.number") %>' dataType="number" numberStep="5" minvalue="5"/>
						</tr>
						<tr>
							<text id="text46" name="text46" header='<%=webutil.getMessage("common.number") %>' dataType="number" numberStep="5" maxvalue="50" minvalue="5"/>
						</tr>
						<tr>
							<text id="text47" name="text47" header='<%=webutil.getMessage("common.money") %>' dataType="currency" numberStep="5000" maxvalue="50000"/>
						</tr>
						<tr>
							<text id="text48" name="text48" header='<%=webutil.getMessage("common.money") %>' dataType="currency" numberStep="5000" minvalue="5000"/>
						</tr>
						<tr>
							<text id="text49" name="text49" header='<%=webutil.getMessage("common.money") %>' dataType="currency" numberStep="5000" maxvalue="50000" minvalue="5000"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:text id="text44" name="text44" header='<%=webutil.getMessage("common.number") %>' dataType="number" numberStep="5" maxvalue="50"/>
						</tr>
						<tr>
							<c:text id="text45" name="text45" header='<%=webutil.getMessage("common.number") %>' dataType="number" numberStep="5" minvalue="5"/>
						</tr>
						<tr>
							<c:text id="text46" name="text46" header='<%=webutil.getMessage("common.number") %>' dataType="number" numberStep="5" maxvalue="50" minvalue="5"/>
						</tr>
						<tr>
							<c:text id="text47" name="text47" header='<%=webutil.getMessage("common.money") %>' dataType="currency" numberStep="5000" maxvalue="50000"/>
						</tr>
						<tr>
							<c:text id="text48" name="text48" header='<%=webutil.getMessage("common.money") %>' dataType="currency" numberStep="5000" minvalue="5000"/>
						</tr>
						<tr>
							<c:text id="text49" name="text49" header='<%=webutil.getMessage("common.money") %>' dataType="currency" numberStep="5000" maxvalue="50000" minvalue="5000"/>
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
							<text id="text28" name="text28" onBlur="alert('blur!');"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:text id="text28" name="text28" onBlur="alert('blur!');"/>
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
							<text id="text29" name="text29" onChange="alert('change!');"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:text id="text29" name="text29" onChange="alert('change!');"/>
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
							<text id="text30" name="text30" onClick="alert('click!')"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:text id="text30" name="text30" onClick="alert('click!')"/>
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
							<text id="text33" name="text33" onKeyDown="alert('key down!')"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:text id="text33" name="text33" onKeyDown="alert('key down!')"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('size')">屬性size: 設定size(text控制項顯示長度)</legend>
				<div id="size_sidebar" style="display:none">
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('style')">屬性style: 設定text控制項style</legend>
				<div id="style_sidebar" style="display:none">
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('typesetting')" style="cursor:pointer">屬性typesetting: 設定排版；預設是"true"，產生td與label；反之則沒有td和label，只產生text控制項本身</legend>
				<div id="typesetting_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<td>
								title
							</td>
							<td>
								<text id="text31" name="text31" typesetting="false"/>
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
								<c:text id="text31" name="text31" typesetting="false"/>
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
					setRequestAttribute("text32_requestAttribute", "test");
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<text id="text32" name="text32" userValue="text32_requestAttribute"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:text id="text32" name="text32" userValue="text32_requestAttribute"/>
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

