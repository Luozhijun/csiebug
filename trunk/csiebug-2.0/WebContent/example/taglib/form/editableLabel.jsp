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
		<form id="editableLabel" name="editableLabel" action="<%=webutil.getBasePathForHTML()%>example/editableLabel" method="POST">
			<!-- 程式標頭 -->
			<table width="100%">
				<tr>
					<td class="PageHeader"><%=webutil.getRequestAttribute("FunctionName").toString()%></td>
				</tr>
			</table>
			<%@ include file="../../../pub/common_html.jsp" %>
			<%@ include file="../../../pub/common_form.jsp" %>
			
			<!-- 以下為頁面編輯區 -->
			
			<h4>EditableLabel</h4>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('editableLabel')" style="cursor:pointer">預設(class="Text"; DataType="string"; 有title的td; 輸入長度和資料格式不限制)</legend>
				<div id="editableLabel_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<editableLabel id="editableLabel1" name="editableLabel1"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<table>
						<tr>
							<c:editableLabel id="editableLabel1" name="editableLabel1"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('blankText')" style="cursor:pointer">屬性blankText: 設定editableLabel空白所顯示的替代文字</legend>
				<div id="blankText_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<editableLabel id="editableLabel33" name="editableLabel33" blankText="我是替代文字"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<table>
						<tr>
							<c:editableLabel id="editableLabel33" name="editableLabel33" blankText="我是替代文字"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('className')" style="cursor:pointer">屬性className: 設定editableLabel所套用的css class</legend>
				<div id="className_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<editableLabel id="editableLabel2" name="editableLabel2" className="TextReadOnly"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<table>
						<tr>
							<c:editableLabel id="editableLabel2" name="editableLabel2" className="TextReadOnly"/>
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
						function editableLabel6_textInput_onClick() {
							document.getElementById("editableLabel6_textInput").value = 'test';
						}
					</script>
					<table>
						<tr>
							<editableLabel id="editableLabel3" name="editableLabel3" header='<%=webutil.getMessage("common.money") %>' dataType="currency"/>
						</tr>
						<tr>
							<editableLabel id="editableLabel4" name="editableLabel4" header='<%=webutil.getMessage("common.date") %>' dataType="date"/>
						</tr>
						<tr>
							<editableLabel id="editableLabel35" name="editableLabel35" header='<%=webutil.getMessage("common.time") %>' dataType="time24"/>
						</tr>
						<tr>
							<editableLabel id="editableLabel36" name="editableLabel36" header='<%=webutil.getMessage("common.datetime") %>' dataType="datetime"/>
						</tr>
						<tr>
							<editableLabel id="editableLabel5" name="editableLabel5" header='<%=webutil.getMessage("common.email") %>' dataType="email"/>
						</tr>
						<tr>
							<editableLabel id="editableLabel6" name="editableLabel6" header='<%=webutil.getMessage("common.function") %>' dataType="function"/>
						</tr>
						<tr>
							<editableLabel id="editableLabel7" name="editableLabel7" header='<%=webutil.getMessage("common.IDNO") %>' dataType="IDNO"/>
						</tr>
						<tr>
							<editableLabel id="editableLabel8" name="editableLabel8" header='<%=webutil.getMessage("common.number") %>' dataType="number"/>
						</tr>
						<tr>
							<editableLabel id="editableLabel9" name="editableLabel9" header='<%=webutil.getMessage("common.password") %>' dataType="password"/>
						</tr>
						<tr>
							<editableLabel id="editableLabel37" name="editableLabel37" header='<%=webutil.getMessage("common.color") %>' dataType="color"/>
						</tr>
						<tr>
							<editableLabel id="editableLabel41" name="editableLabel41" header='<%=webutil.getMessage("common.IP") %>' dataType="IP"/>
						</tr>
						<tr>
							<editableLabel id="editableLabel43" name="editableLabel43" header='<%=webutil.getMessage("common.IP") %>' dataType="IPv6"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<script type="text/javascript">
						function editableLabel6_textInput_onClick() {
							document.getElementById("editableLabel6_textInput").value = 'test';
						}
					</script>
					<table>
						<tr>
							<c:editableLabel id="editableLabel3" name="editableLabel3" header='<%=webutil.getMessage("common.money") %>' dataType="currency"/>
						</tr>
						<tr>
							<c:editableLabel id="editableLabel4" name="editableLabel4" header='<%=webutil.getMessage("common.date") %>' dataType="date"/>
						</tr>
						<tr>
							<c:editableLabel id="editableLabel35" name="editableLabel35" header='<%=webutil.getMessage("common.time") %>' dataType="time24"/>
						</tr>
						<tr>
							<c:editableLabel id="editableLabel36" name="editableLabel36" header='<%=webutil.getMessage("common.datetime") %>' dataType="datetime"/>
						</tr>
						<tr>
							<c:editableLabel id="editableLabel5" name="editableLabel5" header='<%=webutil.getMessage("common.email") %>' dataType="email"/>
						</tr>
						<tr>
							<c:editableLabel id="editableLabel6" name="editableLabel6" header='<%=webutil.getMessage("common.function") %>' dataType="function"/>
						</tr>
						<tr>
							<c:editableLabel id="editableLabel7" name="editableLabel7" header='<%=webutil.getMessage("common.IDNO") %>' dataType="IDNO"/>
						</tr>
						<tr>
							<c:editableLabel id="editableLabel8" name="editableLabel8" header='<%=webutil.getMessage("common.number") %>' dataType="number"/>
						</tr>
						<tr>
							<c:editableLabel id="editableLabel9" name="editableLabel9" header='<%=webutil.getMessage("common.password") %>' dataType="password"/>
						</tr>
						<tr>
							<c:editableLabel id="editableLabel37" name="editableLabel37" header='<%=webutil.getMessage("common.color") %>' dataType="color"/>
						</tr>
						<tr>
							<c:editableLabel id="editableLabel41" name="editableLabel41" header='<%=webutil.getMessage("common.IP") %>' dataType="IP"/>
						</tr>
						<tr>
							<c:editableLabel id="editableLabel43" name="editableLabel43" header='<%=webutil.getMessage("common.IP") %>' dataType="IPv6"/>
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
							<editableLabel id="editableLabel12" name="editableLabel12" defaultValue="123"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:editableLabel id="editableLabel12" name="editableLabel12" defaultValue="123"/>
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
							<editableLabel id="editableLabel13" name="editableLabel13" fixlength="5"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:editableLabel id="editableLabel13" name="editableLabel13" fixlength="5"/>
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
							<editableLabel id="editableLabel14" name="editableLabel14" dataType="currency" footer="kg"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:editableLabel id="editableLabel14" name="editableLabel14" dataType="currency" footer="kg"/>
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
							<editableLabel id="editableLabel15" name="editableLabel15" header="title"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:editableLabel id="editableLabel15" name="editableLabel15" header="title"/>
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
							<editableLabel id="editableLabel38" name="editableLabel38" header='<%=webutil.getMessage("common.date") %>' dataType="date" isMasked="true"/>
						</tr>
						<tr>
							<editableLabel id="editableLabel39" name="editableLabel39" header='<%=webutil.getMessage("common.time") %>' dataType="time24" isMasked="true"/>
						</tr>
						<tr>
							<editableLabel id="editableLabel40" name="editableLabel40" header='<%=webutil.getMessage("common.datetime") %>' dataType="datetime" isMasked="true"/>
						</tr>
						<tr>
							<editableLabel id="editableLabel42" name="editableLabel42" header='<%=webutil.getMessage("common.IP") %>' dataType="IP" isMasked="true"/>
						</tr>
						<tr>
							<editableLabel id="editableLabel44" name="editableLabel44" header='<%=webutil.getMessage("common.IP") %>' dataType="IPv6" isMasked="true"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:editableLabel id="editableLabel38" name="editableLabel38" header='<%=webutil.getMessage("common.date") %>' dataType="date" isMasked="true"/>
						</tr>
						<tr>
							<c:editableLabel id="editableLabel39" name="editableLabel39" header='<%=webutil.getMessage("common.time") %>' dataType="time24" isMasked="true"/>
						</tr>
						<tr>
							<c:editableLabel id="editableLabel40" name="editableLabel40" header='<%=webutil.getMessage("common.datetime") %>' dataType="datetime" isMasked="true"/>
						</tr>
						<tr>
							<c:editableLabel id="editableLabel42" name="editableLabel42" header='<%=webutil.getMessage("common.IP") %>' dataType="IP" isMasked="true"/>
						</tr>
						<tr>
							<c:editableLabel id="editableLabel44" name="editableLabel44" header='<%=webutil.getMessage("common.IP") %>' dataType="IPv6" isMasked="true"/>
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
							<editableLabel id="editableLabel16" name="editableLabel16" isReadOnly="true" defaultValue="123"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:editableLabel id="editableLabel16" name="editableLabel16" isReadOnly="true" defaultValue="123"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend id="editableLabel17_legend" onClick="openSidebarFolder('isRequired')" style="cursor:pointer">屬性isRequired: 設定必填(配合js function: checkRequired()可達到檢查效果)</legend>
				<div id="isRequired_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<script type="text/javascript">
						function editableLabel17_textInput_submit(form, warning, required, ok) {
							if(checkRequired(form, warning, required, ok)) {
								lockButton();
								document.getElementById("ActFlag").value = "editableLabel17";
								document.getElementById(form).submit();
							}
						}
					</script>
					<table>
						<tr>
							<editableLabel id="editableLabel17" name="editableLabel17" header="key" isRequired="true"/>
						</tr>
						<tr>
							<td>
								<button type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="editableLabel17_textInput_submit('editableLabel', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
							</td>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<script type="text/javascript">
						function editableLabel17_textInput_submit(form, warning, required, ok) {
							if(checkRequired(form, warning, required, ok)) {
								lockButton();
								document.getElementById("ActFlag").value = "editableLabel17";
								document.getElementById(form).submit();
							}
						}
					</script>
					<table>
						<tr>
							<c:editableLabel id="editableLabel17" name="editableLabel17" header="key" isRequired="true"/>
						</tr>
						<tr>
							<td>
								<button type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="editableLabel17_textInput_submit('editableLabel', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
							</td>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend id="editableLabel18_legend" onClick="openSidebarFolder('isReturnValue')" style="cursor:pointer">屬性isReturnValue: 設定值(form post帶值回來)；預設是true；但效力低於userValue(從request attribute取值)</legend>
				<div id="isReturnValue_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<script type="text/javascript">
						function editableLabel18_textInput_submit(form, warning, required, ok) {
							document.getElementById("ActFlag").value = "editableLabel18";
							document.getElementById(form).submit();
						}
					</script>
					<table>
						<tr>
							<editableLabel id="editableLabel18" name="editableLabel18" isReturnValue="false"/>
						</tr>
						<tr>
							<td>
								<button type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="editableLabel18_textInput_submit('editableLabel', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
							</td>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<script type="text/javascript">
						function editableLabel18_textInput_submit(form, warning, required, ok) {
							document.getElementById("ActFlag").value = "editableLabel18";
							document.getElementById(form).submit();
						}
					</script>
					<table>
						<tr>
							<c:editableLabel id="editableLabel18" name="editableLabel18" isReturnValue="false"/>
						</tr>
						<tr>
							<td>
								<button type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="editableLabel18_textInput_submit('editableLabel', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
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
							<editableLabel id="editableLabel19" name="editableLabel19" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxdecimal="2"/>
						</tr>
						<tr>
							<editableLabel id="editableLabel20" name="editableLabel20" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxdecimal="3"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:editableLabel id="editableLabel19" name="editableLabel19" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxdecimal="2"/>
						</tr>
						<tr>
							<c:editableLabel id="editableLabel20" name="editableLabel20" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxdecimal="3"/>
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
							<editableLabel id="editableLabel21" name="editableLabel21" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxdecimal="2" maxinteger="4"/>
						</tr>
						<tr>
							<editableLabel id="editableLabel22" name="editableLabel22" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxdecimal="3" maxinteger="5"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:editableLabel id="editableLabel21" name="editableLabel21" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxdecimal="2" maxinteger="4"/>
						</tr>
						<tr>
							<c:editableLabel id="editableLabel22" name="editableLabel22" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxdecimal="3" maxinteger="5"/>
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
							<editableLabel id="editableLabel23" name="editableLabel23" maxlength="5"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:editableLabel id="editableLabel23" name="editableLabel23" maxlength="5"/>
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
							<editableLabel id="editableLabel24" name="editableLabel24" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxvalue="1000"/>
						</tr>
						<tr>
							<editableLabel id="editableLabel25" name="editableLabel25" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxvalue="1000"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:editableLabel id="editableLabel24" name="editableLabel24" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxvalue="1000"/>
						</tr>
						<tr>
							<c:editableLabel id="editableLabel25" name="editableLabel25" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxvalue="1000"/>
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
							<editableLabel id="editableLabel26" name="editableLabel26" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxvalue="1000" minvalue="500"/>
						</tr>
						<tr>
							<editableLabel id="editableLabel27" name="editableLabel27" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxvalue="1000" minvalue="500"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:editableLabel id="editableLabel26" name="editableLabel26" header='<%=webutil.getMessage("common.money") %>' dataType="currency" maxvalue="1000" minvalue="500"/>
						</tr>
						<tr>
							<c:editableLabel id="editableLabel27" name="editableLabel27" header='<%=webutil.getMessage("common.number") %>' dataType="number" maxvalue="1000" minvalue="500"/>
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
							<editableLabel id="editableLabel29" name="editableLabel29" onChange="alert('change!');"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:editableLabel id="editableLabel29" name="editableLabel29" onChange="alert('change!');"/>
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
							<editableLabel id="editableLabel34" name="editableLabel34" onKeyDown="alert('key down!');"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:editableLabel id="editableLabel34" name="editableLabel34" onKeyDown="alert('key down!');"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('size')">屬性size: 設定size(editableLabel控制項顯示長度)</legend>
				<div id="size_sidebar" style="display:none">
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('style')">屬性style: 設定editableLabel控制項style</legend>
				<div id="style_sidebar" style="display:none">
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('typesetting')" style="cursor:pointer">屬性typesetting: 設定排版；預設是"true"，產生td與label；反之則沒有td和label，只產生editableLabel控制項本身</legend>
				<div id="typesetting_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<td>
								title
							</td>
							<td>
								<editableLabel id="editableLabel31" name="editableLabel31" typesetting="false"/>
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
								<c:editableLabel id="editableLabel31" name="editableLabel31" typesetting="false"/>
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
					setRequestAttribute("editableLabel32_requestAttribute", "test");
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<editableLabel id="editableLabel32" name="editableLabel32" userValue="editableLabel32_requestAttribute"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:editableLabel id="editableLabel32" name="editableLabel32" userValue="editableLabel32_requestAttribute"/>
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
			<script src="<%=webutil.getBasePathForHTML()%>js/csiebug-form/csiebug-editableLabel.js"></script>
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

