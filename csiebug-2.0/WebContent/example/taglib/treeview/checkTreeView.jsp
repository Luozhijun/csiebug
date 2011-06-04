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
		<form id="checkTreeView" name="checkTreeView" action="<%=webutil.getBasePathForHTML()%>example/checkTreeView" method="POST">
			<!-- 程式標頭 -->
			<table width="100%">
				<tr>
					<td class="PageHeader"><%=webutil.getRequestAttribute("FunctionName").toString()%></td>
				</tr>
			</table>
			<%@ include file="../../../pub/common_html.jsp" %>
			<%@ include file="../../../pub/common_form.jsp" %>
			
			<!-- 以下為頁面編輯區 -->
			<h4>CheckTreeView</h4>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('list')" style="cursor:pointer">屬性list: 從request attribute中取出checkTreeView資料</legend>
				<div id="list_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					<!-- pseudo code
					ArrayList<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String, String>>();
					
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("ParentId", parentId);
					map.put("Id", id);
					map.put("Name", name);
					map.put("SortOrder", sortOrder);
					map.put("ImageName", imageName);
					list.add(map);
					...
					
					setRequestAttribute("checkTreeView1", list); -->
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<checkTree id="checkTreeView1" list="checkTreeView1"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:checkTree id="checkTreeView1" list="checkTreeView1"/>
					
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('cssPath')">屬性cssPath: 自設cssPath；預設cssPath="/css"</legend>
				<div id="cssPath_sidebar" style="display:none">
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('defaultValue')" style="cursor:pointer">屬性defaultValue: 設定預設值；但效力低於isReturnValue="true"(form post帶值回來)、userValue(從request attribute取值)</legend>
				<div id="defaultValue_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					<!-- pseudo code
					ArrayList<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String, String>>();
					
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("ParentId", parentId);
					map.put("Id", id);
					map.put("Name", name);
					map.put("SortOrder", sortOrder);
					map.put("ImageName", imageName);
					list.add(map);
					...
					
					setRequestAttribute("checkTreeView2", list); -->
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<checkTree id="checkTreeView2" list="checkTreeView2" defaultValue="form2 - Form;grid2 - Grid"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:checkTree id="checkTreeView2" list="checkTreeView2" defaultValue="form2 - Form;grid2 - Grid"/>
					
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('imagePath')">屬性imagePath: 自設imagePath；預設imagePath="/images"</legend>
				<div id="imagePath_sidebar" style="display:none">
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend id="checkTreeView3_legend" onClick="openSidebarFolder('isReturnValue')" style="cursor:pointer">屬性isReturnValue: 設定值(form post帶值回來)；預設是true；但效力低於userValue(從request attribute取值)</legend>
				<div id="isReturnValue_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					<!-- pseudo code
					ArrayList<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String, String>>();
					
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("ParentId", parentId);
					map.put("Id", id);
					map.put("Name", name);
					map.put("SortOrder", sortOrder);
					map.put("ImageName", imageName);
					list.add(map);
					...
					
					setRequestAttribute("checkTreeView3", list); -->
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					<script type="text/javascript">
						function checkTreeView3_submit(form, warning, required, ok) {
							document.getElementById("ActFlag").value = "checkTreeView3";
							document.getElementById(form).submit();
						}
					</script>
					<c:code>
					<checkTree id="checkTreeView3" list="checkTreeView3" isReturnValue="false"/>
					<button id="checkTreeView3Button" type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="checkTreeView3_submit('checkTreeView', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<script type="text/javascript">
						function checkTreeView3_submit(form, warning, required, ok) {
							document.getElementById("ActFlag").value = "checkTreeView3";
							document.getElementById(form).submit();
						}
					</script>
					<c:checkTree id="checkTreeView3" list="checkTreeView3" isReturnValue="false"/>
					<button id="checkTreeView3Button" type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="checkTreeView3_submit('checkTreeView', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('jsPath')">屬性jsPath: 自設jsPath；預設jsPath="/js"</legend>
				<div id="jsPath_sidebar" style="display:none">
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('linked')" style="cursor:pointer">屬性linked: 設定父子節點的連動(配合op="multi")，預設是"true"</legend>
				<div id="linked_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					<!-- pseudo code
					ArrayList<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String, String>>();
					
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("ParentId", parentId);
					map.put("Id", id);
					map.put("Name", name);
					map.put("SortOrder", sortOrder);
					map.put("ImageName", imageName);
					list.add(map);
					...
					
					setRequestAttribute("checkTreeView4", list); -->
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<checkTree id="checkTreeView4" list="checkTreeView4" linked="false"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:checkTree id="checkTreeView4" list="checkTreeView4" linked="false"/>
					
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('op')" style="cursor:pointer">屬性op: 設定單選(radio)或多選(multi)，預設是多選(multi)</legend>
				<div id="op_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					<!-- pseudo code
					ArrayList<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String, String>>();
					
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("ParentId", parentId);
					map.put("Id", id);
					map.put("Name", name);
					map.put("SortOrder", sortOrder);
					map.put("ImageName", imageName);
					list.add(map);
					...
					
					setRequestAttribute("checkTreeView5", list); -->
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<checkTree id="checkTreeView5" list="checkTreeView5" op="radio"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:checkTree id="checkTreeView5" list="checkTreeView5" op="radio"/>
					
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend id="checkTreeView6_legend" onClick="openSidebarFolder('userValue')" style="cursor:pointer">屬性userValue: 設定值(從request attribute取值)</legend>
				<div id="userValue_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					<!-- pseudo code
					ArrayList<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String, String>>();
					
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("ParentId", parentId);
					map.put("Id", id);
					map.put("Name", name);
					map.put("SortOrder", sortOrder);
					map.put("ImageName", imageName);
					list.add(map);
					...
					
					setRequestAttribute("checkTreeView6", list);
					setRequestAttribute("checkTreeViewValue6", "tab6 - Tab;chart6 - Chart"); -->
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					<script type="text/javascript">
						function checkTreeView6_submit(form, warning, required, ok) {
							document.getElementById("ActFlag").value = "checkTreeView6";
							document.getElementById(form).submit();
						}
					</script>
					<c:code>
					<checkTree id="checkTreeView6" list="checkTreeView6" userValue="checkTreeViewValue6"/>
					<button id="checkTreeView6Button" type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="checkTreeView6_submit('checkTreeView', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<script type="text/javascript">
						function checkTreeView6_submit(form, warning, required, ok) {
							document.getElementById("ActFlag").value = "checkTreeView6";
							document.getElementById(form).submit();
						}
					</script>
					<c:checkTree id="checkTreeView6" list="checkTreeView6" userValue="checkTreeViewValue6"/>
					<button id="checkTreeView6Button" type="button" class="Button" onmouseover="className='ButtonHover'" onmouseout="className='Button'" onClick="checkTreeView6_submit('checkTreeView', '<%=webutil.getMessage("common.warning")%>', '<%=webutil.getMessage("common.error.required5")%>', '<%=webutil.getMessage("common.ok")%>');">submit</button>
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
			<script src="<%=webutil.getBasePathForHTML()%>js/csiebug-treeview/csiebug-checkTreeView.js"></script>
			
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

