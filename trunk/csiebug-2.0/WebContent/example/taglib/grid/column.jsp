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
		<%@ include file="../../../pub/common_html.jsp" %>
		
		<form id="column" name="column" action="<%=webutil.getBasePathForHTML()%>example/column" method="POST">
			<!-- 程式標頭 -->
			<table width="100%">
				<tr>
					<td class="PageHeader"><%=webutil.getRequestAttribute("FunctionName").toString()%></td>
				</tr>
			</table>
			<%@ include file="../../../pub/common_form.jsp" %>
			
			<!-- 以下為頁面編輯區 -->
			<h4>Column</h4>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('className')">屬性className: 設定column所套用的css class</legend>
				<div id="className_sidebar" style="display:none">
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('colspan')" style="cursor:pointer">屬性colspan: 指定column所占欄寬，通常配合row的rowType="header"(多層式標題列)</legend>
				<div id="colspan_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					<!-- pseudo code
					ArrayList<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String,String>>();
					
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("field1", "10");
					map.put("field2", "200");
					map.put("field3", "3000");
					
					list.add(map);
					
					...
					
					setRequestAttribute("gridData", list);
					-->
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table id="table1" name="table1" data="gridData">
						<row rowType="header">
							<column title="title1" colspan="2"/>
							<column title="title2" colspan="1"/>
						</row>
						<row>
							<column fieldname="field1"/>
							<column fieldname="field2"/>
							<column fieldname="field3"/>
						</row>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:table id="table1" name="table1" data="gridData">
						<c:row rowType="header">
							<c:column title="title1" colspan="2"/>
							<c:column title="title2" colspan="1"/>
						</c:row>
						<c:row>
							<c:column fieldname="field1"/>
							<c:column fieldname="field2"/>
							<c:column fieldname="field3"/>
						</c:row>
					</c:table>
					
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('dataType')" style="cursor:pointer">屬性dataType: 設定column的資料型別；全選多選功能="selAllCheckBox"、序號="SerialNumber"、數字/金額(靠右)="number"/"currency"、預設當作一般文字處理(靠左)</legend>
				<div id="dataType_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					<!-- pseudo code
					ArrayList<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String,String>>();
					
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("field1", "10");
					map.put("field2", "200");
					map.put("field3", "3000");
					
					list.add(map);
					
					...
					
					setRequestAttribute("gridData", list);
					-->
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table id="table2" name="table2" data="gridData" toolbar="true">
						<row>
							<column dataType="selAllCheckBox" />
							<column dataType="SerialNumber" />
							<column fieldname="field1"/>
							<column fieldname="field2" dataType="number"/>
							<column fieldname="field3" dataType="currency"/>
						</row>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:table id="table2" name="table2" data="gridData" toolbar="true" downloadColumns="1,2,3,4">
						<c:row>
							<c:column dataType="selAllCheckBox" />
							<c:column dataType="SerialNumber" />
							<c:column fieldname="field1"/>
							<c:column fieldname="field2" dataType="number"/>
							<c:column fieldname="field3" dataType="currency"/>
						</c:row>
					</c:table>
					
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('fieldname')" style="cursor:pointer">屬性fieldname: 設定column取出的資料</legend>
				<div id="fieldname_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					<!-- pseudo code
					ArrayList<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String,String>>();
					
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("field1", "10");
					map.put("field2", "200");
					map.put("field3", "3000");
					
					list.add(map);
					
					...
					
					setRequestAttribute("gridData", list);
					-->
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table id="table3" name="table3" data="gridData">
						<row>
							<column />
							<column fieldname="field3" />
							<column fieldname="field1" />
						</row>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:table id="table3" name="table3" data="gridData">
						<c:row>
							<c:column />
							<c:column fieldname="field3" />
							<c:column fieldname="field1" />
						</c:row>
					</c:table>
					
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('maxLineLength')" style="cursor:pointer">屬性 maxLineLength: 用來指定資料長度，然後產生斷行，否則會用預設值來斷行(須搭配typesettingAlgorithm才有用)；若column內容有html tag，可能會造成不預期效果，請小心使用</legend>
				<div id="maxLineLength_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					<!-- pseudo code
					ArrayList<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String,String>>();
					
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("field1", "10");
					map.put("field2", "200");
					map.put("field3", "3000");
					
					list.add(map);
					
					...
					
					setRequestAttribute("gridData", list);
					-->
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table id="table12" name="table12" data="gridData">
						<row>
							<column fieldname="field1" typesettingAlgorithm="default" maxLineLength="3" />
							<column fieldname="field2" typesettingAlgorithm="default" maxLineLength="3" />
							<column fieldname="field3" typesettingAlgorithm="default" maxLineLength="3" />
						</row>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:table id="table12" name="table12" data="gridData">
						<c:row>
							<c:column fieldname="field1" typesettingAlgorithm="default" maxLineLength="3" />
							<c:column fieldname="field2" typesettingAlgorithm="default" maxLineLength="3" />
							<c:column fieldname="field3" typesettingAlgorithm="default" maxLineLength="3" />
						</c:row>
					</c:table>
					
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('onClick')" style="cursor:pointer">屬性onClick: 設定column的onClick function</legend>
				<div id="onClick_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					<!-- pseudo code
					ArrayList<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String,String>>();
					
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("field1", "10");
					map.put("field2", "200");
					map.put("field3", "3000");
					
					list.add(map);
					
					...
					
					setRequestAttribute("gridData", list);
					-->
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table id="table9" name="table9" data="gridData">
						<row>
							<column fieldname="field1" selectable="true" onClick="alert('第' + ((var.SerialNumber) - 1) + '行,第(var.ColumnIndex)列')" />
							<column fieldname="field2" selectable="true" onClick="alert('第' + ((var.SerialNumber) - 1) + '行,第(var.ColumnIndex)列')"/>
							<column fieldname="field3" selectable="true" onClick="alert('第' + ((var.SerialNumber) - 1) + '行,第(var.ColumnIndex)列')"/>
						</row>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:table id="table9" name="table9" data="gridData">
						<c:row>
							<c:column fieldname="field1" selectable="true" onClick="alert('第' + ((var.SerialNumber) - 1) + '行,第(var.ColumnIndex)列')" />
							<c:column fieldname="field2" selectable="true" onClick="alert('第' + ((var.SerialNumber) - 1) + '行,第(var.ColumnIndex)列')"/>
							<c:column fieldname="field3" selectable="true" onClick="alert('第' + ((var.SerialNumber) - 1) + '行,第(var.ColumnIndex)列')"/>
						</c:row>
					</c:table>
					
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('onDblClick')" style="cursor:pointer">屬性onDblClick: 設定column的onDblClick function</legend>
				<div id="onDblClick_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					<!-- pseudo code
					ArrayList<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String,String>>();
					
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("field1", "10");
					map.put("field2", "200");
					map.put("field3", "3000");
					
					list.add(map);
					
					...
					
					setRequestAttribute("gridData", list);
					-->
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table id="table10" name="table10" data="gridData">
						<row>
							<column fieldname="field1" selectable="true" onDblClick="alert('第' + ((var.SerialNumber) - 1) + '行,第(var.ColumnIndex)列')"/>
							<column fieldname="field2" selectable="true" onDblClick="alert('第' + ((var.SerialNumber) - 1) + '行,第(var.ColumnIndex)列')"/>
							<column fieldname="field3" selectable="true" onDblClick="alert('第' + ((var.SerialNumber) - 1) + '行,第(var.ColumnIndex)列')"/>
						</row>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:table id="table10" name="table10" data="gridData">
						<c:row>
							<c:column fieldname="field1" selectable="true" onDblClick="alert('第' + ((var.SerialNumber) - 1) + '行,第(var.ColumnIndex)列')"/>
							<c:column fieldname="field2" selectable="true" onDblClick="alert('第' + ((var.SerialNumber) - 1) + '行,第(var.ColumnIndex)列')"/>
							<c:column fieldname="field3" selectable="true" onDblClick="alert('第' + ((var.SerialNumber) - 1) + '行,第(var.ColumnIndex)列')"/>
						</c:row>
					</c:table>
					
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('selAllScript')" style="cursor:pointer">屬性selAllScript: 設定checkbox附加的script動作(配合dataType="selAllCheckBox")</legend>
				<div id="selAllScript_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					<!-- pseudo code
					ArrayList<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String,String>>();
					
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("field1", "10");
					map.put("field2", "200");
					map.put("field3", "3000");
					
					list.add(map);
					
					...
					
					setRequestAttribute("gridData", list);
					-->
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table id="table4" name="table4" data="gridData">
						<row>
							<column dataType="selAllCheckBox" selAllScript="alert('row(var.SerialNumber) click!')"/>
							<column fieldname="field1"/>
							<column fieldname="field2"/>
							<column fieldname="field3"/>
						</row>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:table id="table4" name="table4" data="gridData">
						<c:row>
							<c:column dataType="selAllCheckBox" selAllScript="alert('row(var.SerialNumber) click!')"/>
							<c:column fieldname="field1"/>
							<c:column fieldname="field2"/>
							<c:column fieldname="field3"/>
						</c:row>
					</c:table>
					
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('selectable')" style="cursor:pointer">屬性seletable: 設定column是否可選取(預設為false)；若row的seletable屬性為true，則column的選取就失效</legend>
				<div id="selectable_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					<!-- pseudo code
					ArrayList<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String,String>>();
					
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("field1", "10");
					map.put("field2", "200");
					map.put("field3", "3000");
					
					list.add(map);
					
					...
					
					setRequestAttribute("gridData", list);
					-->
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table id="table11" name="table11" data="gridData">
						<row>
							<column fieldname="field1" selectable="true"/>
							<column fieldname="field2" />
							<column fieldname="field3" selectable="true"/>
						</row>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:table id="table11" name="table11" data="gridData">
						<c:row>
							<c:column fieldname="field1" selectable="true"/>
							<c:column fieldname="field2" />
							<c:column fieldname="field3" selectable="true"/>
						</c:row>
					</c:table>
					
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('sortable')" style="cursor:pointer">屬性sortable: 設定此欄點選標頭可以排序(須配合dataType)，預設是"false"；預設當一般文字處理(照字典排序法),數字金額則依數字大小排序</legend>
				<div id="sortable_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					<!-- pseudo code
					ArrayList<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String,String>>();
					
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("field1", "10");
					map.put("field2", "200");
					map.put("field3", "3000");
					map.put("field4", new HtmlProgressBar(new Float(30)).makeProgressBarJQuery());
					
					list.add(map);
					
					...
					
					setRequestAttribute("gridData", list);
					-->
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table id="table5" name="table5" data="gridData">
						<row>
							<column fieldname="field1" sortable="true"/>
							<column fieldname="field2" dataType="number" sortable="true"/>
							<column fieldname="field3" dataType="currency" sortable="true"/>
							<column fieldname="field4" sortable="true" />
						</row>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:table id="table5" name="table5" data="gridData">
						<c:row>
							<c:column fieldname="field1" sortable="true"/>
							<c:column fieldname="field2" dataType="number" sortable="true"/>
							<c:column fieldname="field3" dataType="currency" sortable="true"/>
							<c:column fieldname="field4" sortable="true" />
						</c:row>
					</c:table>
					
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('style')" style="cursor:pointer">屬性style: 設定此欄的style</legend>
				<div id="style_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					<!-- pseudo code
					ArrayList<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String,String>>();
					
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("field1", "10");
					map.put("field2", "200");
					map.put("field3", "3000");
					
					list.add(map);
					
					...
					
					setRequestAttribute("gridData", list);
					-->
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table id="table6" name="table6" data="gridData">
						<row>
							<column fieldname="field1" />
							<column fieldname="field2" style="display:none"/>
							<column fieldname="field3" />
						</row>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:table id="table6" name="table6" data="gridData">
						<c:row>
							<c:column fieldname="field1" />
							<c:column fieldname="field2" style="display:none"/>
							<c:column fieldname="field3" />
						</c:row>
					</c:table>
					
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('title')" style="cursor:pointer">屬性title: 自定此欄的title，若沒設定，預設為fieldname</legend>
				<div id="title_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					<!-- pseudo code
					ArrayList<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String,String>>();
					
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("field1", "10");
					map.put("field2", "200");
					map.put("field3", "3000");
					
					list.add(map);
					
					...
					
					setRequestAttribute("gridData", list);
					-->
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table id="table7" name="table7" data="gridData">
						<row>
							<column fieldname="field1" />
							<column fieldname="field2" title="title2"/>
							<column fieldname="field3" />
						</row>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:table id="table7" name="table7" data="gridData">
						<c:row>
							<c:column fieldname="field1" />
							<c:column fieldname="field2" title="title2"/>
							<c:column fieldname="field3" />
						</c:row>
					</c:table>
					
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('typesettingAlgorithm')" style="cursor:pointer">屬性 typesettingAlgorithm: 用來指定換行演算法，預設是不換行(不設或設為"none")，此值可以設為"default"；若column內容有html tag，可能會造成不預期效果，請小心使用</legend>
				<div id="typesettingAlgorithm_sidebar" style="display:none">
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('advanced')" style="cursor:pointer">進階用法</legend>
				<div id="advanced_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					<!-- pseudo code
					ArrayList<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String,String>>();
					
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("field1", "10");
					map.put("field2", "200");
					map.put("field3", "3000");
					
					list.add(map);
					
					...
					
					setRequestAttribute("gridData", list);
					-->
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table id="table8" name="table8" data="gridData">
						<row>
							<column title="title1">
								static text or static HTML code => <input type="text">
							</column>
							<column title="title2">
								(var.field1) - (var.field2)
							</column>
							<column fieldname="field3" />
						</row>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:table id="table8" name="table8" data="gridData">
						<c:row>
							<c:column title="title1">
								static text or static HTML code => <input type="text">
							</c:column>
							<c:column title="title2">
								(var.field1) - (var.field2)
							</c:column>
							<c:column fieldname="field3" />
						</c:row>
					</c:table>
					
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('advanced2')" style="cursor:pointer">進階用法(可用變數)</legend>
				<div id="advanced2_sidebar" style="display:none">
					可參考onClick,onDblClick屬性的範例來運用變數<br></br>
					row tag可用的變數如下:<br></br>
					<hr></hr>
					保留字:<br></br>
					(var.ColumnIndex): ColumnIndex<br></br>
					(var.SerialNumber): 序號變數<br></br>
					(var.FormId): FormId<br></br>
					(var.TableId): TableId<br></br>
					(var.RowIndex): RowIndex<br></br>
					(var.pagination): pagination<br></br>
					(var.HeaderRowCount): Header的列數<br></br>
					<hr></hr>
					資料欄位值:<br></br>
					(var.欄位名)<br></br>
					<hr></hr>
					session值:<br></br>
					(session.欄位名)<br></br>
					<hr></hr>
					request attribute值:<br></br>
					(request.欄位名)<br></br>
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
			<script src="<%=webutil.getBasePathForHTML()%>js/csiebug-grid/csiebug-grid.js"></script>
			
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

