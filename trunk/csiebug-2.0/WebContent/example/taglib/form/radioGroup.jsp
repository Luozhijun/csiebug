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
		<form id="radioGroup" name="radioGroup" action="<%=webutil.getBasePathForHTML()%>example/radioGroup" method="POST">
			<!-- 程式標頭 -->
			<table width="100%">
				<tr>
					<td class="PageHeader"><%=webutil.getRequestAttribute("FunctionName").toString()%></td>
				</tr>
			</table>
			<%@ include file="../../../pub/common_html.jsp" %>
			<%@ include file="../../../pub/common_form.jsp" %>
			
			<!-- 以下為頁面編輯區 -->
			
			<h4>RadioGroup</h4>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('radioGroup')" style="cursor:pointer">屬性option: 從request attribute中抓出選項</legend>
				<div id="radioGroup_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
						LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
						map.put("1", "option1");
						map.put("2", "option2");
						map.put("3", "option3");
						setRequestAttribute("option", map);
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<radioGroup id="radioGroup1" name="radioGroup1" option="option"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<table>
						<tr>
							<c:radiogroup id="radioGroup1" name="radioGroup1" option="option"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('className')" style="cursor:pointer">屬性className: 設定選項文字所套用的css class</legend>
				<div id="className_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
						LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
						map.put("1", "option1");
						map.put("2", "option2");
						map.put("3", "option3");
						setRequestAttribute("option", map);
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<radiogroup id="radioGroup2" name="radioGroup2" option="option" className="TextReadOnly"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:radiogroup id="radioGroup2" name="radioGroup2" option="option" className="TextReadOnly"/>
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
						map.put("1", "option1");
						map.put("2", "option2");
						map.put("3", "option3");
						setRequestAttribute("option", map);
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<radiogroup id="radioGroup3" name="radioGroup3" option="option" defaultValue="2"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:radiogroup id="radioGroup3" name="radioGroup3" option="option" defaultValue="2"/>
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
						map.put("1", "option1");
						map.put("2", "option2");
						map.put("3", "option3");
						setRequestAttribute("option", map);
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<radiogroup id="radioGroup4" name="radioGroup4" option="option" footer="unit"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:radiogroup id="radioGroup4" name="radioGroup4" option="option" footer="unit"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('header')" style="cursor:pointer">屬性header/headerClass: 設定標頭文字/標頭套用的css class</legend>
				<div id="header_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
						LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
						map.put("1", "option1");
						map.put("2", "option2");
						map.put("3", "option3");
						setRequestAttribute("option", map);
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<radiogroup id="radioGroup5" name="radioGroup5" option="option" header="title"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:radiogroup id="radioGroup5" name="radioGroup5" option="option" header="title"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('isReadOnly')" style="cursor:pointer">屬性isReadOnly: 設定唯讀</legend>
				<div id="isReadOnly_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
						LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
						map.put("1", "option1");
						map.put("2", "option2");
						map.put("3", "option3");
						setRequestAttribute("option", map);
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<radiogroup id="radioGroup6" name="radioGroup6" option="option" isReadOnly="true"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:radiogroup id="radioGroup6" name="radioGroup6" option="option" isReadOnly="true"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend id="radioGroup7_legend" onClick="openSidebarFolder('isRequired')" style="cursor:pointer">屬性isRequired: 設定必填(配合js function: checkRequired()可達到檢查效果)</legend>
				<div id="isRequired_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
						LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
						map.put("1", "option1");
						map.put("2", "option2");
						map.put("3", "option3");
						setRequestAttribute("option", map);
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<script type="text/javascript">
						function radioGroup7_submit(form, warning, required, ok) {
							if(checkRequired(form, warning, required, ok)) {
								lockButton();
								document.getElementById("ActFlag").value = "radioGroup7";
								document.getElementById(form).submit();
							}
						}
					</script>
					<table>
						<tr>
							<radiogroup id="radioGroup7" name="radioGroup7" header="key" option="option" isRequired="true"/>
						</tr>
						<tr>
							<td>
								<button type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="radioGroup7_submit('radioGroup', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
							</td>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<script type="text/javascript">
						function radioGroup7_submit(form, warning, required, ok) {
							if(checkRequired(form, warning, required, ok)) {
								lockButton();
								document.getElementById("ActFlag").value = "radioGroup7";
								document.getElementById(form).submit();
							}
						}
					</script>
					<table>
						<tr>
							<c:radiogroup id="radioGroup7" name="radioGroup7" header="key" option="option" isRequired="true"/>
						</tr>
						<tr>
							<td>
								<button type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="radioGroup7_submit('radioGroup', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
							</td>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend id="radioGroup8_legend" onClick="openSidebarFolder('isReturnValue')" style="cursor:pointer">屬性isReturnValue: 設定值(form post帶值回來)；預設是true；但效力低於userValue(從request attribute取值)</legend>
				<div id="isReturnValue_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
						LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
						map.put("1", "option1");
						map.put("2", "option2");
						map.put("3", "option3");
						setRequestAttribute("option", map);
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<script type="text/javascript">
						function radioGroup8_submit(form, warning, required, ok) {
							document.getElementById("ActFlag").value = "radioGroup8";
							document.getElementById(form).submit();
						}
					</script>
					<table>
						<tr>
							<radiogroup id="radioGroup8" name="radioGroup8" option="option" isReturnValue="false"/>
						</tr>
						<tr>
							<td>
								<button type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="radioGroup8_submit('radioGroup', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
							</td>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<script type="text/javascript">
						function radioGroup8_submit(form, warning, required, ok) {
							document.getElementById("ActFlag").value = "radioGroup8";
							document.getElementById(form).submit();
						}
					</script>
					<table>
						<tr>
							<c:radiogroup id="radioGroup8" name="radioGroup8" option="option" isReturnValue="false"/>
						</tr>
						<tr>
							<td>
								<button type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="radioGroup8_submit('radioGroup', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
							</td>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend id="newLineFlag_legend" onClick="openSidebarFolder('newLineFlag')" style="cursor:pointer">屬性newLineFlag: 設定每個選項是否換行顯示，預設是"false"</legend>
				<div id="newLineFlag_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
						LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
						map.put("1", "option1");
						map.put("2", "option2");
						map.put("3", "option3");
						setRequestAttribute("option", map);
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<radiogroup id="radioGroup9" name="radioGroup9" option="option" newLineFlag="true"/>
						</tr>
						<tr>
							<td>
								<button type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="radioGroup8_submit('radioGroup', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
							</td>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:radiogroup id="radioGroup9" name="radioGroup9" option="option" newLineFlag="true"/>
						</tr>
						<tr>
							<td>
								<button type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="radioGroup8_submit('radioGroup', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
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
						map.put("1", "option1");
						map.put("2", "option2");
						map.put("3", "option3");
						setRequestAttribute("option", map);
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<radiogroup id="radioGroup10" name="radioGroup10" option="option" onChange="alert('change!');"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:radiogroup id="radioGroup10" name="radioGroup10" option="option" onChange="alert('change!');"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('onClick')" style="cursor:pointer">屬性onClick: 設定onClick事件</legend>
				<div id="onClick_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
						LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
						map.put("1", "option1");
						map.put("2", "option2");
						map.put("3", "option3");
						setRequestAttribute("option", map);
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<radiogroup id="radioGroup11" name="radioGroup11" option="option" onClick="alert('click!')"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:radiogroup id="radioGroup11" name="radioGroup11" option="option" onClick="alert('click!')"/>
						</tr>
					</table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('style')">屬性style: 設定radio控制項style</legend>
				<div id="style_sidebar" style="display:none">
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('typesetting')" style="cursor:pointer">屬性typesetting: 設定排版；預設是"true"，產生td與label；反之則沒有td和label，只產生radio控制項本身</legend>
				<div id="typesetting_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
						LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
						map.put("1", "option1");
						map.put("2", "option2");
						map.put("3", "option3");
						setRequestAttribute("option", map);
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<td>
								title
							</td>
							<td>
								<radiogroup id="radioGroup12" name="radioGroup12" option="option" typesetting="false"/>
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
								<c:radiogroup id="radioGroup12" name="radioGroup12" option="option" typesetting="false"/>
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
						map.put("1", "option1");
						map.put("2", "option2");
						map.put("3", "option3");
						setRequestAttribute("option", map);
						setRequestAttribute("radioGroup13_requestAttribute", "3");
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table>
						<tr>
							<radioGroup id="radioGroup13" name="radioGroup13" option="option" userValue="radioGroup13_requestAttribute"/>
						</tr>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<table>
						<tr>
							<c:radiogroup id="radioGroup13" name="radioGroup13" option="option" userValue="radioGroup13_requestAttribute"/>
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

