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
		
		<form id="table" name="table" action="<%=webutil.getBasePathForHTML()%>example/table" method="POST">
			<!-- 程式標頭 -->
			<table width="100%">
				<tr>
					<td class="PageHeader"><%=webutil.getRequestAttribute("FunctionName").toString()%></td>
				</tr>
			</table>
			<%@ include file="../../../pub/common_form.jsp" %>
			
			<!-- 以下為頁面編輯區 -->
			<h4>Table</h4>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('data')" style="cursor:pointer">屬性data: 從request attribute中取出grid資料</legend>
				<div id="data_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					<!-- pseudo code
					ArrayList<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String,String>>();
					
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("field1", "1");
					map.put("field2", "2");
					map.put("field3", "3");
					
					list.add(map);
					
					...
					
					setRequestAttribute("gridData", list);
					-->
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table id="table1" name="table1" data="gridData">
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
						<c:row>
							<c:column fieldname="field1"/>
							<c:column fieldname="field2"/>
							<c:column fieldname="field3"/>
						</c:row>
					</c:table>
					
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('className')">屬性className: 設定table所套用的css class</legend>
				<div id="className_sidebar" style="display:none">
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('downloadColumns')" style="cursor:pointer">屬性downloadColumns: 用數字(從0開始)與逗號分隔,來指定下載的column；預設是所有欄位皆下載；此屬性要先打開toolbar功能才能運作</legend>
				<div id="downloadColumns_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					<!-- pseudo code
					ArrayList<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String,String>>();
					
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("field1", "1");
					map.put("field2", "2");
					map.put("field3", "3");
					
					list.add(map);
					
					...
					
					setRequestAttribute("gridData", list);
					-->
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table id="table13" name="table13" data="gridData" toolbar="true" downloadColumns="0,2">
						<row selectable="true">
							<column fieldname="field1"/>
							<column fieldname="field2"/>
							<column fieldname="field3"/>
						</row>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:table id="table13" name="table13" data="gridData" toolbar="true" downloadColumns="0,2">
						<c:row selectable="true">
							<c:column fieldname="field1"/>
							<c:column fieldname="field2"/>
							<c:column fieldname="field3"/>
						</c:row>
					</c:table>
					
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('downloadPartial')" style="cursor:pointer">屬性downloadPartial: true/false: 下載grid上有勾選的資料/全部資料；預設是false；此屬性要先打開toolbar功能並且開啟選取功能(單選或多選)才能運作</legend>
				<div id="downloadPartial_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					<!-- pseudo code
					ArrayList<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String,String>>();
					
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("field1", "1");
					map.put("field2", "2");
					map.put("field3", "3");
					
					list.add(map);
					
					...
					
					setRequestAttribute("gridData", list);
					-->
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table id="table2" name="table2" data="gridData" toolbar="true" downloadPartial="true">
						<row selectable="true">
							<column fieldname="field1"/>
							<column fieldname="field2"/>
							<column fieldname="field3"/>
						</row>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:table id="table2" name="table2" data="gridData" toolbar="true" downloadPartial="true">
						<c:row selectable="true">
							<c:column fieldname="field1"/>
							<c:column fieldname="field2"/>
							<c:column fieldname="field3"/>
						</c:row>
					</c:table>
					
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('groupByColumns')" style="cursor:pointer">屬性groupByColumns: 指定要做grouping的欄位；使用此功能後sorting與paging功能將會取消(grouping已經是某種sorting了)</legend>
				<div id="groupByColumns_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					<!-- pseudo code
					ArrayList<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String,String>>();
					
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("uniqueID", "" + task.getUniqueID());
					map.put("name", task.getName());
					map.put("start", task.getStart().toString());
					map.put("finish", task.getFinish().toString());
					map.put("rollup", "" + task.getRollup());
					map.put("outlineNumber", task.getOutlineNumber());
					map.put("outlineLevel", "" + task.getOutlineLevel());
					map.put("id", "" + task.getID());
					map.put("recurring", "" + task.getRecurring());
					map.put("pm", "" + task.getNumber1());
					map.put("sa", "" + task.getNumber2());
					map.put("pa", "" + task.getNumber3());
					map.put("swd", "" + task.getNumber4());
					map.put("pgr", "" + task.getNumber5());
					map.put("ase", "" + task.getNumber6());
					
					list.add(map);
					
					...
					
					setRequestAttribute("gridData3", list);
					-->
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table id="table12" name="table12" data="gridData3" groupByColumns="${columns}">
						<row>
							<column fieldname="outlineNumber" title="大綱編號(outlineNumber)" />
							<column fieldname="outlineLevel" title="大綱階層(outlineLevel)" />
							<column fieldname="id" title="識別碼(id)" />
							<column fieldname="uniqueID" title="唯一識別碼(uniqueID)" />
							<column fieldname="name" title="任務名稱(name)" />
							<column fieldname="start" title="開始時間(start)" />
							<column fieldname="finish" title="完成時間(finish)" />
							<column fieldname="rollup" title="上顯型任務(rollup)" />
							<column fieldname="recurring" title="週期性(recurring)" />
							<column fieldname="pm" />
							<column fieldname="sa" />
							<column fieldname="pa" />
							<column fieldname="swd" />
							<column fieldname="pgr" />
							<column fieldname="ase" />
							<columns dynFields="level" />
						</row>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:table id="table12" name="table12" data="gridData3" groupByColumns="${columns}">
						<c:row>
							<c:column fieldname="outlineNumber" title="大綱編號(outlineNumber)" />
							<c:column fieldname="outlineLevel" title="大綱階層(outlineLevel)" />
							<c:column fieldname="id" title="識別碼(id)" />
							<c:column fieldname="uniqueID" title="唯一識別碼(uniqueID)" />
							<c:column fieldname="name" title="任務名稱(name)" />
							<c:column fieldname="start" title="開始時間(start)" />
							<c:column fieldname="finish" title="完成時間(finish)" />
							<c:column fieldname="rollup" title="上顯型任務(rollup)" />
							<c:column fieldname="recurring" title="週期性(recurring)" />
							<c:column fieldname="pm" />
							<c:column fieldname="sa" />
							<c:column fieldname="pa" />
							<c:column fieldname="swd" />
							<c:column fieldname="pgr" />
							<c:column fieldname="ase" />
							<c:columns dynFields="level" />
						</c:row>
					</c:table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('groupOp')" style="cursor:pointer">屬性groupOp: 指定grouping行為；若資料都是出現在leaf，則建議將此值設為"insertGroupData"，它將會自動塞入folder資料來做grouping</legend>
				<div id="groupOp_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					<!-- pseudo code
					ArrayList<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String,String>>();
					
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("field1", "1");
					map.put("field2", "2");
					map.put("field3", "3");
					
					list.add(map);
					
					...
					
					setRequestAttribute("gridData2", list);
					-->
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table id="table9" name="table9" data="gridData2" groupByColumns="field1,field2" groupOp="insertGroupData">
						<row>
							<column fieldname="field1"/>
							<column fieldname="field2"/>
							<column fieldname="field3"/>
						</row>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:table id="table9" name="table9" data="gridData2" groupByColumns="field1,field2" groupOp="insertGroupData">
						<c:row>
							<c:column fieldname="field1"/>
							<c:column fieldname="field2"/>
							<c:column fieldname="field3"/>
						</c:row>
					</c:table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('highlight')" style="cursor:pointer">屬性highlight: 設定table有highlight功能，預設為"false"</legend>
				<div id="highlight_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					<!-- pseudo code
					ArrayList<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String,String>>();
					
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("field1", "1");
					map.put("field2", "2");
					map.put("field3", "3");
					
					list.add(map);
					
					...
					
					setRequestAttribute("gridData", list);
					-->
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table id="table3" name="table3" data="gridData" highlight="true">
						<row selectable="true">
							<column fieldname="field1"/>
							<column fieldname="field2"/>
							<column fieldname="field3"/>
						</row>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:table id="table3" name="table3" data="gridData" highlight="true">
						<c:row selectable="true">
							<c:column fieldname="field1"/>
							<c:column fieldname="field2"/>
							<c:column fieldname="field3"/>
						</c:row>
					</c:table>
					
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('imagePath')">屬性imagePath: 自設imagePath；預設imagePath="/images"</legend>
				<div id="imagePath_sidebar" style="display:none">
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('noDataStringFlag')" style="cursor:pointer">屬性noDataStringFlag: 如果沒有資料不show空的grid，而改用文字提醒；預設為"false"</legend>
				<div id="noDataStringFlag_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					<!-- pseudo code
					ArrayList<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String,String>>();
					
					setRequestAttribute("noData", list);
					-->
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table id="table10" name="table10" data="noData" noDataStringFlag="true">
						<row>
							<column fieldname="field1"/>
							<column fieldname="field2"/>
							<column fieldname="field3"/>
						</row>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:table id="table10" name="table10" data="noData" noDataStringFlag="true">
						<c:row>
							<c:column fieldname="field1"/>
							<c:column fieldname="field2"/>
							<c:column fieldname="field3"/>
						</c:row>
					</c:table>
					
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('pagination')" style="cursor:pointer">屬性pagination: 分頁筆數；0表示不分頁，預設不分頁</legend>
				<div id="pagination_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					<!-- pseudo code
					ArrayList<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String,String>>();
					
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("field1", "1");
					map.put("field2", "2");
					map.put("field3", "3");
					
					list.add(map);
					
					...
					
					setRequestAttribute("gridData", list);
					-->
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table id="table4" name="table4" data="gridData" pagination="1">
						<row>
							<column fieldname="field1"/>
							<column fieldname="field2"/>
							<column fieldname="field3"/>
						</row>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:table id="table4" name="table4" data="gridData" pagination="1">
						<c:row>
							<c:column fieldname="field1"/>
							<c:column fieldname="field2"/>
							<c:column fieldname="field3"/>
						</c:row>
					</c:table>
					
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('statusbar')" style="cursor:pointer">屬性statusbar: 設定statusbar所套用的css class<legend>
				<div id="statusbar_sidebar" style="display:none">
					除預設以外，還有以下這些可套用:<br>
					"jaceju_digg"、"jaceju_yahoo"、"jaceju_meneame"、"jaceju_flickr"、<br>
					"jaceju_sabrosus"、"jaceju_scott"、"jaceju_quotes"、"jaceju_black"、<br>
					"jaceju_black2"、"jaceju_black-red"、"jaceju_grayr"、"jaceju_yellow"、<br>
					"jaceju_jogger"、"jaceju_starcraft2"、"jaceju_tres"、"jaceju_megas512"、<br>
					"jaceju_technorati"、"jaceju_youtube"、"jaceju_msdn"、"jaceju_badoo"、<br>
					"jaceju_manu"
					<br>
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					<!-- pseudo code
					ArrayList<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String,String>>();
					
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("field1", "1");
					map.put("field2", "2");
					map.put("field3", "3");
					
					list.add(map);
					
					...
					
					setRequestAttribute("gridData", list);
					-->
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table id="table5" name="table5" data="gridData" statusbar="jaceju_youtube" pagination="1">
						<row>
							<column fieldname="field1"/>
							<column fieldname="field2"/>
							<column fieldname="field3"/>
						</row>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:table id="table5" name="table5" data="gridData" statusbar="jaceju_youtube" pagination="1">
						<c:row>
							<c:column fieldname="field1"/>
							<c:column fieldname="field2"/>
							<c:column fieldname="field3"/>
						</c:row>
					</c:table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('statusbarPosition')" style="cursor:pointer">屬性statusBarPostion: 分頁列位置up/down；預設為down<legend>
				<div id="statusbarPosition_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					<!-- pseudo code
					ArrayList<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String,String>>();
					
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("field1", "1");
					map.put("field2", "2");
					map.put("field3", "3");
					
					list.add(map);
					
					...
					
					setRequestAttribute("gridData", list);
					-->
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table id="table6" name="table6" data="gridData" pagination="1" statusBarPosition="up">
						<row>
							<column fieldname="field1"/>
							<column fieldname="field2"/>
							<column fieldname="field3"/>
						</row>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:table id="table6" name="table6" data="gridData" pagination="1" statusBarPosition="up">
						<c:row>
							<c:column fieldname="field1"/>
							<c:column fieldname="field2"/>
							<c:column fieldname="field3"/>
						</c:row>
					</c:table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('style')" style="cursor:pointer">屬性style: 自設style<legend>
				<div id="style_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					<!-- pseudo code
					ArrayList<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String,String>>();
					
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("field1", "1");
					map.put("field2", "2");
					map.put("field3", "3");
					
					list.add(map);
					
					...
					
					setRequestAttribute("gridData", list);
					-->
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table id="table7" name="table7" data="gridData" style="width:100%">
						<row>
							<column fieldname="field1"/>
							<column fieldname="field2"/>
							<column fieldname="field3"/>
						</row>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:table id="table7" name="table7" data="gridData" style="width:100%">
						<c:row>
							<c:column fieldname="field1"/>
							<c:column fieldname="field2"/>
							<c:column fieldname="field3"/>
						</c:row>
					</c:table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('toolbar')" style="cursor:pointer">屬性toolbar: 功能列，true/false: 開啟/關閉；預設是false<legend>
				<div id="toolbar_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					<!-- pseudo code
					ArrayList<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String,String>>();
					
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("field1", "1");
					map.put("field2", "2");
					map.put("field3", "3");
					
					list.add(map);
					
					...
					
					setRequestAttribute("gridData", list);
					-->
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table id="table8" name="table8" data="gridData" toolbar="true">
						<row>
							<column fieldname="field1"/>
							<column fieldname="field2"/>
							<column fieldname="field3"/>
						</row>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:table id="table8" name="table8" data="gridData" toolbar="true">
						<c:row>
							<c:column fieldname="field1"/>
							<c:column fieldname="field2"/>
							<c:column fieldname="field3"/>
						</c:row>
					</c:table>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('xlsName')" style="cursor:pointer">屬性xlsName: 指定下載的xls檔名，toolbar必須打開才有下載功能，預設為table id</legend>
				<div id="xlsName_sidebar" style="display:none">
					<h5>JAVA 程式碼:</h5>
					
					<c:code>
					<!-- pseudo code
					ArrayList<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String,String>>();
					
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("field1", "1");
					map.put("field2", "2");
					map.put("field3", "3");
					
					list.add(map);
					
					...
					
					setRequestAttribute("gridData", list);
					-->
					</c:code>
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<table id="table11" name="table11" data="gridData" toolbar="true" xlsName="testGridXLS">
						<row selectable="true">
							<column fieldname="field1"/>
							<column fieldname="field2"/>
							<column fieldname="field3"/>
						</row>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:table id="table11" name="table11" data="gridData" toolbar="true" xlsName="testGridXLS">
						<c:row selectable="true">
							<c:column fieldname="field1"/>
							<c:column fieldname="field2"/>
							<c:column fieldname="field3"/>
						</c:row>
					</c:table>
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

