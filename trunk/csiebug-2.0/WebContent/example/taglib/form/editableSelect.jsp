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
		<form id="editableSelect" name="editableSelect" action="<%=webutil.getBasePathForHTML()%>example/editableSelect" method="POST">
			<!-- 程式標頭 -->
			<table width="100%">
				<tr>
					<td class="PageHeader"><%=webutil.getRequestAttribute("FunctionName").toString()%></td>
				</tr>
			</table>
			<%@ include file="../../../pub/common_html.jsp" %>
			<%@ include file="../../../pub/common_form.jsp" %>
			
			<!-- 以下為頁面編輯區 -->
			
			<h4>EditableSelect</h4>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('editableSelect')" style="cursor:pointer">預設(class="Text"; 有title的td; op="1")</legend>
				<div id="editableSelect_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<editableselect id="editableSelect1" name="editableSelect1"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<table>
						<tr>
							<c:editableselect id="editableSelect1" name="editableSelect1"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('className')" style="cursor:pointer">屬性className: 設定editableSelect所套用的css class</legend>
				<div id="className_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
						LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
						map.put("test", "測試");
						map.put("test2", "測試2");
						setRequestAttribute("option", map);
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<editableselect id="editableSelect2" name="editableSelect2" option="option" className="TextReadOnly"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<table>
						<tr>
							<c:editableselect id="editableSelect2" name="editableSelect2" option="option" className="TextReadOnly"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('defaultValue')" style="cursor:pointer">屬性defaultValue: 設定預設值；但效力低於isReturnValue="true"(form post帶值回來)、userValue(從request attribute取值)</legend>
				<div id="defaultValue_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
						LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
						map.put("test", "測試");
						map.put("test2", "測試2");
						setRequestAttribute("option", map);
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<editableselect id="editableSelect3" name="editableSelect3" option="option" defaultValue="test"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:editableselect id="editableSelect3" name="editableSelect3" option="option" defaultValue="test"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('footer')" style="cursor:pointer">屬性footer/footerClass: 設定標尾文字/標尾文字套用的css class</legend>
				<div id="footer_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
						LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
						map.put("test", "測試");
						map.put("test2", "測試2");
						setRequestAttribute("option", map);
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<editableselect id="editableSelect4" name="editableSelect4" option="option" footer="unit" />
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:editableselect id="editableSelect4" name="editableSelect4" option="option" footer="unit" />
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
							<editableselect id="editableSelect5" name="editableSelect5" header="title"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:editableselect id="editableSelect5" name="editableSelect5" header="title"/>
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
							<editableselect id="editableSelect6" name="editableSelect6" isReadOnly="true"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:editableselect id="editableSelect6" name="editableSelect6" isReadOnly="true"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend id="editableSelect7_legend" onClick="openSidebarFolder('isRequired')" style="cursor:pointer">屬性isRequired: 設定必填(配合js function: checkRequired()可達到檢查效果)</legend>
				<div id="isRequired_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
						LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
						map.put("test", "測試");
						map.put("test2", "測試2");
						setRequestAttribute("option", map);
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<script type="text/javascript">
						function editableSelect7_submit(form, warning, required, ok) {
							if(checkRequired(form, warning, required, ok)) {
								lockButton();
								document.getElementById("ActFlag").value = "editableSelect7";
								document.getElementById(form).submit();
							}
						}
					</script>
					<table>
						<tr>
							<editableselect id="editableSelect7" name="editableSelect7" header="key" option="option" isRequired="true"/>
						</tr>
						<tr>
							<td>
								<button type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="editableSelect7_submit('editableSelect', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
							</td>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<script type="text/javascript">
						function editableSelect7_submit(form, warning, required, ok) {
							if(checkRequired(form, warning, required, ok)) {
								lockButton();
								document.getElementById("ActFlag").value = "editableSelect7";
								document.getElementById(form).submit();
							}
						}
					</script>
					<table>
						<tr>
							<c:editableselect id="editableSelect7" name="editableSelect7" header="key" option="option" isRequired="true"/>
						</tr>
						<tr>
							<td>
								<button type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="editableSelect7_submit('editableSelect', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
							</td>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend id="editableSelect8_legend" onClick="openSidebarFolder('isReturnValue')" style="cursor:pointer">屬性isReturnValue: 設定值(form post帶值回來)；預設是true；但效力低於userValue(從request attribute取值)</legend>
				<div id="isReturnValue_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
						LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
						map.put("test", "測試");
						map.put("test2", "測試2");
						setRequestAttribute("option", map);
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<script type="text/javascript">
						function editableSelect8_submit(form, warning, required, ok) {
							document.getElementById("ActFlag").value = "editableSelect8";
							document.getElementById(form).submit();
						}
					</script>
					<table>
						<tr>
							<editableselect id="editableSelect8" name="editableSelect8" option="option" isReturnValue="false"/>
						</tr>
						<tr>
							<td>
								<button type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="editableSelect8_submit('editableSelect', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
							</td>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<script type="text/javascript">
						function editableSelect8_submit(form, warning, required, ok) {
							document.getElementById("ActFlag").value = "editableSelect8";
							document.getElementById(form).submit();
						}
					</script>
					<table>
						<tr>
							<c:editableselect id="editableSelect8" name="editableSelect8" option="option" isReturnValue="false"/>
						</tr>
						<tr>
							<td>
								<button type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="editableSelect8_submit('editableSelect', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
							</td>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('onChange')" style="cursor:pointer">屬性onChange: 設定onChange事件</legend>
				<div id="onChange_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
						LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
						map.put("test", "測試");
						map.put("test2", "測試2");
						setRequestAttribute("option", map);
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<editableselect id="editableSelect9" name="editableSelect9" option="option" onChange="alert(&quotechange!&quote);"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:editableselect id="editableSelect9" name="editableSelect9" option="option" onChange="alert(&quotechange!&quote);"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('op')" style="cursor:pointer">屬性op: 設定editableSelect控制項的行為，預設為1；1: key in 資料要符合選項內的選項之一；2: 不管 key in 的值；3: 自動選取子字串符合 key in 字串的選項</legend>
				<div id="op_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
						LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
						map.put("test", "測試");
						map.put("test2", "測試2");
						setRequestAttribute("option", map);
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<editableselect id="editableSelect10" name="editableSelect10" option="option" op="1"/>
						</tr>
						<tr>
							<editableselect id="editableSelect11" name="editableSelect11" option="option" op="2"/>
						</tr>
						<tr>
							<editableselect id="editableSelect12" name="editableSelect12" option="option" op="3"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:editableselect id="editableSelect10" name="editableSelect10" option="option" op="1"/>
						</tr>
						<tr>
							<c:editableselect id="editableSelect11" name="editableSelect11" option="option" op="2"/>
						</tr>
						<tr>
							<c:editableselect id="editableSelect12" name="editableSelect12" option="option" op="3"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend id="option_legend" onClick="openSidebarFolder('option')" style="cursor:pointer">屬性option: 設定editableSelect選項(從request attribute取得選項的key/value map)</legend>
				<div id="option_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
						LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
						map.put("test", "測試");
						map.put("test2", "測試2");
						setRequestAttribute("option", map);
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<editableselect id="editableSelect13" name="editableSelect13" option="option"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:editableselect id="editableSelect13" name="editableSelect13" option="option"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('size')">屬性size: 設定size(editableSelect控制項顯示長度)</legend>
				<div id="size_sidebar" style="display:none">
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('style')">屬性style: 設定editableSelect控制項style</legend>
				<div id="style_sidebar" style="display:none">
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('typesetting')" style="cursor:pointer">屬性typesetting: 設定排版；預設是"true"，產生td與label；反之則沒有td和label，只產生editableSelect控制項本身</legend>
				<div id="typesetting_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<td>
								title
							</td>
							<td>
								<editableselect id="editableSelect14" name="editableSelect14" typesetting="false"/>
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
								<c:editableselect id="editableSelect14" name="editableSelect14" typesetting="false"/>
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
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("test", "測試");
					map.put("test2", "測試2");
					setRequestAttribute("option", map);
					setRequestAttribute("editableSelect15_requestAttribute", "test2");
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<editableselect id="editableSelect15" name="editableSelect15" option="option" userValue="editableSelect15_requestAttribute"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:editableselect id="editableSelect15" name="editableSelect15" option="option" userValue="editableSelect15_requestAttribute"/>
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
			<script src="<%=webutil.getBasePathForHTML()%>js/csiebug-form/csiebug-editableSelect.js"></script>
			
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

