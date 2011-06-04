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
		<form id="select" name="select" action="<%=webutil.getBasePathForHTML()%>example/select" method="POST">
			<!-- 程式標頭 -->
			<table width="100%">
				<tr>
					<td class="PageHeader"><%=webutil.getRequestAttribute("FunctionName").toString()%></td>
				</tr>
			</table>
			<%@ include file="../../../pub/common_html.jsp" %>
			<%@ include file="../../../pub/common_form.jsp" %>
			
			<!-- 以下為頁面編輯區 -->
			
			<h4>Select</h4>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('select')" style="cursor:pointer">預設(class="Select"; 有title的td; 預設有一個空白選項)</legend>
				<div id="select_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<select id="select1" name="select1"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<table>
						<tr>
							<c:select id="select1" name="select1"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('blankOptionFlag')" style="cursor:pointer">屬性blankOptionFlag: 設定select是否有空白選項，預設是true</legend>
				<div id="blankOptionFlag_sidebar" style="display:none">
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
							<select id="select2" name="select2" option="option" blankOptionFlag="false"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<table>
						<tr>
							<c:select id="select2" name="select2" option="option" blankOptionFlag="false"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('blankOptionText')" style="cursor:pointer">屬性blankOptionText: 設定select空白選項的文字(blankOptionFlag不為false才有用)</legend>
				<div id="blankOptionText_sidebar" style="display:none">
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
							<select id="select3" name="select3" option="option" blankOptionText="blank option"/>
						</tr>
						<tr>
							<select id="select4" name="select4" option="option" blankOptionFlag="false" blankOptionText="blank option"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<table>
						<tr>
							<c:select id="select3" name="select3" option="option" blankOptionText="blank option"/>
						</tr>
						<tr>
							<c:select id="select4" name="select4" option="option" blankOptionFlag="false" blankOptionText="blank option"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('className')" style="cursor:pointer">屬性className: 設定select所套用的css class</legend>
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
							<select id="select5" name="select5" option="option" className="SelectReadOnly"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<table>
						<tr>
							<c:select id="select5" name="select5" option="option" className="SelectReadOnly"/>
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
							<select id="select6" name="select6" option="option" defaultValue="test"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:select id="select6" name="select6" option="option" defaultValue="test"/>
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
							<select id="select7" name="select7" blankOptionFlag="false" footer="kg">
								<option value="50">50</option>
								<option value="60">60</option>
								<option value="70">70</option>
								<option value="80">80</option>
								<option value="90">90</option>
							</select>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:select id="select7" name="select7" blankOptionFlag="false" footer="kg">
								<option value="50">50</option>
								<option value="60">60</option>
								<option value="70">70</option>
								<option value="80">80</option>
								<option value="90">90</option>
							</c:select>
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
							<select id="select8" name="select8" header="title"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:select id="select8" name="select8" header="title"/>
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
							<select id="select9" name="select9" isReadOnly="true"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:select id="select9" name="select9" isReadOnly="true"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend id="select10_legend" onClick="openSidebarFolder('isRequired')" style="cursor:pointer">屬性isRequired: 設定必填(配合js function: checkRequired()可達到檢查效果)</legend>
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
						function select10_submit(form, warning, required, ok) {
							if(checkRequired(form, warning, required, ok)) {
								lockButton();
								document.getElementById("ActFlag").value = "select10";
								document.getElementById(form).submit();
							}
						}
					</script>
					<table>
						<tr>
							<select id="select10" name="select10" header="key" option="option" isRequired="true"/>
						</tr>
						<tr>
							<td>
								<button type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="select10_submit('select', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
							</td>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<script type="text/javascript">
						function select10_submit(form, warning, required, ok) {
							if(checkRequired(form, warning, required, ok)) {
								lockButton();
								document.getElementById("ActFlag").value = "select10";
								document.getElementById(form).submit();
							}
						}
					</script>
					<table>
						<tr>
							<c:select id="select10" name="select10" header="key" option="option" isRequired="true"/>
						</tr>
						<tr>
							<td>
								<button type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="select10_submit('select', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
							</td>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend id="select11_legend" onClick="openSidebarFolder('isReturnValue')" style="cursor:pointer">屬性isReturnValue: 設定值(form post帶值回來)；預設是true；但效力低於userValue(從request attribute取值)</legend>
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
						function select11_submit(form, warning, required, ok) {
							document.getElementById("ActFlag").value = "select11";
							document.getElementById(form).submit();
						}
					</script>
					<table>
						<tr>
							<select id="select11" name="select11" option="option" isReturnValue="false"/>
						</tr>
						<tr>
							<td>
								<button type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="select11_submit('select', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
							</td>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<script type="text/javascript">
						function select11_submit(form, warning, required, ok) {
							document.getElementById("ActFlag").value = "select11";
							document.getElementById(form).submit();
						}
					</script>
					<table>
						<tr>
							<c:select id="select11" name="select11" option="option" isReturnValue="false"/>
						</tr>
						<tr>
							<td>
								<button type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="select11_submit('select', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
							</td>
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
							<select id="select12" name="select12" onBlur="alert('blur!');"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:select id="select12" name="select12" onBlur="alert('blur!');"/>
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
							<select id="select13" name="select13" option="option" onChange="alert('change!');"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:select id="select13" name="select13" option="option" onChange="alert('change!');"/>
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
							<select id="select14" name="select14" onClick="alert('click!')"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:select id="select14" name="select14" onClick="alert('click!')"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend id="option_legend" onClick="openSidebarFolder('option')" style="cursor:pointer">屬性option: 設定select選項(從request attribute取得選項的key/value map)</legend>
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
							<select id="select15" name="select15" option="option"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:select id="select15" name="select15" option="option"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('size')">屬性size: 設定size(select控制項顯示長度)</legend>
				<div id="size_sidebar" style="display:none">
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('style')">屬性style: 設定select控制項style</legend>
				<div id="style_sidebar" style="display:none">
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('typesetting')" style="cursor:pointer">屬性typesetting: 設定排版；預設是"true"，產生td與label；反之則沒有td和label，只產生select控制項本身</legend>
				<div id="typesetting_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<td>
								title
							</td>
							<td>
								<select id="select16" name="select16" typesetting="false"/>
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
								<c:select id="select16" name="select16" typesetting="false"/>
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
					setRequestAttribute("select17_requestAttribute", "test2");
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<select id="select17" name="select17" option="option" userValue="select17_requestAttribute"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:select id="select17" name="select17" option="option" userValue="select17_requestAttribute"/>
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

