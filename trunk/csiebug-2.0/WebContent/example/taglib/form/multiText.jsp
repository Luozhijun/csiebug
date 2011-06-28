<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/csiebug-ui" %>

<%@page import="csiebug.util.WebUtility"%>

<%WebUtility webutil = new WebUtility();%>


<html>
	<head>
		<%@ include file="../../../pub/common_css.jsp" %>
		<%=webutil.getImportCSSFileLink() %>
		
		<link rel="shortcut icon" href="<%=webutil.getBasePathForHTML()%>images/favicon.ico" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title><%=webutil.getRequestAttribute("FunctionName").toString()%></title>
	</head>
	<body>
		<form id="multiText" name="multiText" action="<%=webutil.getBasePathForHTML()%>example/multiText" method="POST">
			<!-- 程式標頭 -->
			<table width="100%">
				<tr>
					<td class="PageHeader"><%=webutil.getRequestAttribute("FunctionName").toString()%></td>
				</tr>
			</table>
			<%@ include file="../../../pub/common_html.jsp" %>
			<%@ include file="../../../pub/common_form.jsp" %>
			
			<!-- 以下為頁面編輯區 -->
			
			<h4>MultiText</h4>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('multiText')" style="cursor:pointer">預設(class="Text"; DataType="string"; 有title的td; 輸入長度和資料格式不限制)</legend>
				<div id="multiText_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<multitext id="multiText1" name="multiText1"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<table>
						<tr>
							<c:multitext id="multiText1" name="multiText1"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('className')" style="cursor:pointer">屬性className: 設定multiText所套用的css class</legend>
				<div id="className_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<multitext id="multiText2" name="multiText2" className="TextReadOnly"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<table>
						<tr>
							<c:multitext id="multiText2" name="multiText2" className="TextReadOnly"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('dataType')" style="cursor:pointer">屬性dataType: 會做資料格式的檢查；格式有string(預設)、IDNO(身分證字號)、password、function、email、number、currency、date、color、IP</legend>
				<div id="dataType_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<script type="text/javascript">
						function multiText6_textInput_onClick() {
							document.getElementById("multiText6_textInput").value = 'test';
						}
					</script>
					<table>
						<tr>
							<multitext id="multiText3" name="multiText3" header='<%=webutil.getMessage("common.money") %>' dataType="currency"/>
						</tr>
						<tr>
							<multitext id="multiText4" name="multiText4" header='<%=webutil.getMessage("common.date") %>' dataType="date"/>
						</tr>
						<tr>
							<multitext id="multiText33" name="multiText33" header='<%=webutil.getMessage("common.time") %>' dataType="time24"/>
						</tr>
						<tr>
							<multitext id="multiText34" name="multiText34" header='<%=webutil.getMessage("common.datetime") %>' dataType="datetime"/>
						</tr>
						<tr>
							<multitext id="multiText5" name="multiText5" header='<%=webutil.getMessage("common.email") %>' dataType="email"/>
						</tr>
						<tr>
							<multitext id="multiText6" name="multiText6" header='<%=webutil.getMessage("common.function") %>' dataType="function"/>
						</tr>
						<tr>
							<multitext id="multiText7" name="multiText7" header='<%=webutil.getMessage("common.IDNO") %>' dataType="IDNO"/>
						</tr>
						<tr>
							<multitext id="multiText8" name="multiText8" header='<%=webutil.getMessage("common.number") %>' dataType="number"/>
						</tr>
						<tr>
							<multitext id="multiText9" name="multiText9" header='<%=webutil.getMessage("common.password") %>' dataType="password"/>
						</tr>
						<tr>
							<multitext id="multiText35" name="multiText35" header='<%=webutil.getMessage("common.color") %>' dataType="color"/>
						</tr>
						<tr>
							<multitext id="multiText39" name="multiText39" header='<%=webutil.getMessage("common.IP") %>' dataType="IP"/>
						</tr>
						<tr>
							<multitext id="multiText41" name="multiText41" header='<%=webutil.getMessage("common.IP") %>' dataType="IPv6"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<script type="text/javascript">
						function multiText6_textInput_onClick() {
							document.getElementById("multiText6_textInput").value = 'test';
						}
					</script>
					<table>
						<tr>
							<c:multitext id="multiText3" name="multiText3" header='<%=webutil.getMessage("common.money") %>' dataType="currency"/>
						</tr>
						<tr>
							<c:multitext id="multiText4" name="multiText4" header='<%=webutil.getMessage("common.date") %>' dataType="date"/>
						</tr>
						<tr>
							<c:multitext id="multiText33" name="multiText33" header='<%=webutil.getMessage("common.time") %>' dataType="time24"/>
						</tr>
						<tr>
							<c:multitext id="multiText34" name="multiText34" header='<%=webutil.getMessage("common.datetime") %>' dataType="datetime"/>
						</tr>
						<tr>
							<c:multitext id="multiText5" name="multiText5" header='<%=webutil.getMessage("common.email") %>' dataType="email"/>
						</tr>
						<tr>
							<c:multitext id="multiText6" name="multiText6" header='<%=webutil.getMessage("common.function") %>' dataType="function"/>
						</tr>
						<tr>
							<c:multitext id="multiText7" name="multiText7" header='<%=webutil.getMessage("common.IDNO") %>' dataType="IDNO"/>
						</tr>
						<tr>
							<c:multitext id="multiText8" name="multiText8" header='<%=webutil.getMessage("common.number") %>' dataType="number"/>
						</tr>
						<tr>
							<c:multitext id="multiText9" name="multiText9" header='<%=webutil.getMessage("common.password") %>' dataType="password"/>
						</tr>
						<tr>
							<c:multitext id="multiText35" name="multiText35" header='<%=webutil.getMessage("common.color") %>' dataType="color"/>
						</tr>
						<tr>
							<c:multitext id="multiText39" name="multiText39" header='<%=webutil.getMessage("common.IP") %>' dataType="IP"/>
						</tr>
						<tr>
							<c:multitext id="multiText41" name="multiText41" header='<%=webutil.getMessage("common.IP") %>' dataType="IPv6"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('defaultValue')" style="cursor:pointer">屬性defaultValue: 設定預設值(可設定多筆,用分號隔開)；但效力低於isReturnValue="true"(form post帶值回來)、userValue(從request attribute取值)</legend>
				<div id="defaultValue_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<multitext id="multiText12" name="multiText12" defaultValue="123;4567"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:multitext id="multiText12" name="multiText12" defaultValue="123;4567"/>
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
							<multitext id="multiText13" name="multiText13" fixlength="5"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:multitext id="multiText13" name="multiText13" fixlength="5"/>
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
							<multitext id="multiText14" name="multiText14" dataType="currency" footer="kg"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:multitext id="multiText14" name="multiText14" dataType="currency" footer="kg"/>
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
							<multitext id="multiText15" name="multiText15" header="title"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:multitext id="multiText15" name="multiText15" header="title"/>
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
							<multitext id="multiText36" name="multiText36" header='<%=webutil.getMessage("common.date") %>' dataType="date" isMasked="true"/>
						</tr>
						<tr>
							<multitext id="multiText37" name="multiText37" header='<%=webutil.getMessage("common.time") %>' dataType="time24" isMasked="true"/>
						</tr>
						<tr>
							<multitext id="multiText38" name="multiText38" header='<%=webutil.getMessage("common.datetime") %>' dataType="datetime" isMasked="true"/>
						</tr>
						<tr>
							<multitext id="multiText40" name="multiText40" header='<%=webutil.getMessage("common.IP") %>' dataType="IP" isMasked="true"/>
						</tr>
						<tr>
							<multitext id="multiText42" name="multiText42" header='<%=webutil.getMessage("common.IP") %>' dataType="IPv6" isMasked="true"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:multitext id="multiText36" name="multiText36" header='<%=webutil.getMessage("common.date") %>' dataType="date" isMasked="true"/>
						</tr>
						<tr>
							<c:multitext id="multiText37" name="multiText37" header='<%=webutil.getMessage("common.time") %>' dataType="time24" isMasked="true"/>
						</tr>
						<tr>
							<c:multitext id="multiText38" name="multiText38" header='<%=webutil.getMessage("common.datetime") %>' dataType="datetime" isMasked="true"/>
						</tr>
						<tr>
							<c:multitext id="multiText40" name="multiText40" header='<%=webutil.getMessage("common.IP") %>' dataType="IP" isMasked="true"/>
						</tr>
						<tr>
							<c:multitext id="multiText42" name="multiText42" header='<%=webutil.getMessage("common.IP") %>' dataType="IPv6" isMasked="true"/>
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
							<multitext id="multiText16" name="multiText16" isReadOnly="true" defaultValue="123;4567"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:multitext id="multiText16" name="multiText16" isReadOnly="true" defaultValue="123;4567"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend id="multiText17_legend" onClick="openSidebarFolder('isRequired')" style="cursor:pointer">屬性isRequired: 設定必填(配合js function: checkRequired()可達到檢查效果)</legend>
				<div id="isRequired_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<script type="text/javascript">
						function multiText17_textInput_submit(form, warning, required, ok) {
							if(checkRequired(form, warning, required, ok)) {
								lockButton();
								document.getElementById("ActFlag").value = "multiText17";
								document.getElementById(form).submit();
							}
						}
					</script>
					<table>
						<tr>
							<multitext id="multiText17" name="multiText17" header="key" isRequired="true"/>
						</tr>
						<tr>
							<td>
								<button type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="multiText17_textInput_submit('multiText', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
							</td>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<script type="text/javascript">
						function multiText17_textInput_submit(form, warning, required, ok) {
							if(checkRequired(form, warning, required, ok)) {
								lockButton();
								document.getElementById("ActFlag").value = "multiText17";
								document.getElementById(form).submit();
							}
						}
					</script>
					<table>
						<tr>
							<c:multitext id="multiText17" name="multiText17" header="key" isRequired="true"/>
						</tr>
						<tr>
							<td>
								<button type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="multiText17_textInput_submit('multiText', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
							</td>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend id="multiText18_legend" onClick="openSidebarFolder('isReturnValue')" style="cursor:pointer">屬性isReturnValue: 設定值(form post帶值回來)；預設是true；但效力低於userValue(從request attribute取值)</legend>
				<div id="isReturnValue_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<script type="text/javascript">
						function multiText18_textInput_submit(form, warning, required, ok) {
							document.getElementById("ActFlag").value = "multiText18";
							document.getElementById(form).submit();
						}
					</script>
					<table>
						<tr>
							<multitext id="multiText18" name="multiText18" isReturnValue="false"/>
						</tr>
						<tr>
							<td>
								<button type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="multiText18_textInput_submit('multiText', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
							</td>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<script type="text/javascript">
						function multiText18_textInput_submit(form, warning, required, ok) {
							document.getElementById("ActFlag").value = "multiText18";
							document.getElementById(form).submit();
						}
					</script>
					<table>
						<tr>
							<c:multitext id="multiText18" name="multiText18" isReturnValue="false"/>
						</tr>
						<tr>
							<td>
								<button type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="multiText18_textInput_submit('multiText', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
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
							<multitext id="multiText19" name="multiText19" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxdecimal="2"/>
						</tr>
						<tr>
							<multitext id="multiText20" name="multiText20" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxdecimal="3"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:multitext id="multiText19" name="multiText19" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxdecimal="2"/>
						</tr>
						<tr>
							<c:multitext id="multiText20" name="multiText20" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxdecimal="3"/>
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
							<multitext id="multiText21" name="multiText21" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxdecimal="2" maxinteger="4"/>
						</tr>
						<tr>
							<multitext id="multiText22" name="multiText22" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxdecimal="3" maxinteger="5"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:multitext id="multiText21" name="multiText21" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxdecimal="2" maxinteger="4"/>
						</tr>
						<tr>
							<c:multitext id="multiText22" name="multiText22" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxdecimal="3" maxinteger="5"/>
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
							<multitext id="multiText23" name="multiText23" maxlength="5"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:multitext id="multiText23" name="multiText23" maxlength="5"/>
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
							<multitext id="multiText24" name="multiText24" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxvalue="1000"/>
						</tr>
						<tr>
							<multitext id="multiText25" name="multiText25" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxvalue="1000"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:multitext id="multiText24" name="multiText24" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxvalue="1000"/>
						</tr>
						<tr>
							<c:multitext id="multiText25" name="multiText25" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxvalue="1000"/>
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
							<multitext id="multiText26" name="multiText26" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxvalue="1000" minvalue="500"/>
						</tr>
						<tr>
							<multitext id="multiText27" name="multiText27" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxvalue="1000" minvalue="500"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:multitext id="multiText26" name="multiText26" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxvalue="1000" minvalue="500"/>
						</tr>
						<tr>
							<c:multitext id="multiText27" name="multiText27" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxvalue="1000" minvalue="500"/>
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
							<multitext id="multiText29" name="multiText29" onChange="alert('change!');"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:multitext id="multiText29" name="multiText29" onChange="alert('change!');"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('size')">屬性size: 設定size(multiText控制項顯示長度)</legend>
				<div id="size_sidebar" style="display:none">
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('style')">屬性style: 設定multiText控制項style</legend>
				<div id="style_sidebar" style="display:none">
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('typesetting')" style="cursor:pointer">屬性typesetting: 設定排版；預設是"true"，產生td與label；反之則沒有td和label，只產生multiText控制項本身</legend>
				<div id="typesetting_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<td>
								title
							</td>
							<td>
								<multitext id="multiText31" name="multiText31" typesetting="false"/>
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
								<c:multitext id="multiText31" name="multiText31" typesetting="false"/>
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
					setRequestAttribute("multiText32_requestAttribute", "test");
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<multitext id="multiText32" name="multiText32" userValue="multiText32_requestAttribute"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:multitext id="multiText32" name="multiText32" userValue="multiText32_requestAttribute"/>
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
			<script src="<%=webutil.getBasePathForHTML()%>js/csiebug-form/csiebug-multiText.js"></script>
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

