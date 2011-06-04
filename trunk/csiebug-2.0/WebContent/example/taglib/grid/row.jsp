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
		<form id="row" name="row" action="<%=webutil.getBasePathForHTML()%>example/row" method="POST">
			<!-- 程式標頭 -->
			<table width="100%">
				<tr>
					<td class="PageHeader"><%=webutil.getRequestAttribute("FunctionName").toString()%></td>
				</tr>
			</table>
			<%@ include file="../../../pub/common_html.jsp" %>
			
			<!-- 以下為頁面編輯區 -->
			<h4>Row</h4>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('className')" style="cursor:pointer">屬性className: 設定row所套用的css class</legend>
				<div id="className_sidebar" style="display:none">
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
						<row className="TR_ODD">
							<column fieldname="field1"/>
							<column fieldname="field2"/>
							<column fieldname="field3"/>
						</row>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:table id="table1" name="table1" data="gridData">
						<c:row className="TR_ODD">
							<c:column fieldname="field1"/>
							<c:column fieldname="field2"/>
							<c:column fieldname="field3"/>
						</c:row>
					</c:table>
					
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('headerClassName')" style="cursor:pointer">屬性headerClassName: 設定標頭列所套用的css class，預設是"TableHeader"</legend>
				<div id="headerClassName_sidebar" style="display:none">
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
					<table id="table2" name="table2" data="gridData">
						<row headerClassName="TR_EVEN">
							<column fieldname="field1"/>
							<column fieldname="field2"/>
							<column fieldname="field3"/>
						</row>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:table id="table2" name="table2" data="gridData">
						<c:row headerClassName="TR_EVEN">
							<c:column fieldname="field1"/>
							<c:column fieldname="field2"/>
							<c:column fieldname="field3"/>
						</c:row>
					</c:table>
					
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('headerStyle')">屬性headerStyle: 設定header的style</legend>
				<div id="headerStyle_sidebar" style="display:none">
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('onClick')" style="cursor:pointer">屬性onClick: 設定row的onClick行為</legend>
				<div id="onClick_sidebar" style="display:none">
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
					//(var.SerialNumber)是保留變數,可取得當列的列數
					//(var.xxx)可取得當列某欄的值
					<table id="table3" name="table3" data="gridData">
						<row onClick="alert('[(var.SerialNumber),1]=(var.field1);[(var.SerialNumber),2]=(var.field2);[(var.SerialNumber),3]=(var.field3)');">
							<column fieldname="field1"/>
							<column fieldname="field2"/>
							<column fieldname="field3"/>
						</row>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:table id="table3" name="table3" data="gridData">
						<c:row onClick="alert('[(var.SerialNumber),1]=(var.field1);[(var.SerialNumber),2]=(var.field2);[(var.SerialNumber),3]=(var.field3)');">
							<c:column fieldname="field1"/>
							<c:column fieldname="field2"/>
							<c:column fieldname="field3"/>
						</c:row>
					</c:table>
					
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('onDblClick')" style="cursor:pointer">屬性onDblClick: 設定row的onDblClick行為</legend>
				<div id="onDblClick_sidebar" style="display:none">
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
					//(var.SerialNumber)是保留變數,可取得當列的列數
					//(var.xxx)可取得當列某欄的值
					<table id="table4" name="table4" data="gridData">
						<row onDblClick="alert('[(var.SerialNumber),1]=(var.field1);[(var.SerialNumber),2]=(var.field2);[(var.SerialNumber),3]=(var.field3)');">
							<column fieldname="field1"/>
							<column fieldname="field2"/>
							<column fieldname="field3"/>
						</row>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:table id="table4" name="table4" data="gridData">
						<c:row onDblClick="alert('[(var.SerialNumber),1]=(var.field1);[(var.SerialNumber),2]=(var.field2);[(var.SerialNumber),3]=(var.field3)');">
							<c:column fieldname="field1"/>
							<c:column fieldname="field2"/>
							<c:column fieldname="field3"/>
						</c:row>
					</c:table>
					
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('rowType')" style="cursor:pointer">屬性rowType: 設定row的type；可用來指定多層式的header</legend>
				<div id="rowType_sidebar" style="display:none">
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
					<table id="table5" name="table5" data="gridData">
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
					
					<c:table id="table5" name="table5" data="gridData">
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
				<legend onClick="openSidebarFolder('selectable')" style="cursor:pointer">屬性selectable: 設定row可以被選取(單選)，預設是"false"</legend>
				<div id="selectable_sidebar" style="display:none">
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
					<table id="table6" name="table6" data="gridData">
						<row selectable="true">
							<column fieldname="field1"/>
							<column fieldname="field2"/>
							<column fieldname="field3"/>
						</row>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:table id="table6" name="table6" data="gridData">
						<c:row selectable="true">
							<c:column fieldname="field1"/>
							<c:column fieldname="field2"/>
							<c:column fieldname="field3"/>
						</c:row>
					</c:table>
					
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('style')">屬性style: 設定row的style</legend>
				<div id="style_sidebar" style="display:none">
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('title')" style="cursor:pointer">屬性title: 設定row的提示(若column太多造成grid太長,有水平scrollbar產生,可以讓使用者有提示可知在哪個row)</legend>
				<div id="title_sidebar" style="display:none">
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
					<table id="table7" name="table7" data="gridData">
						<row title="測試(var.field1)-(var.field3)">
							<column fieldname="field1"/>
							<column fieldname="field2"/>
							<column fieldname="field3"/>
						</row>
					</table>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<c:table id="table7" name="table7" data="gridData">
						<c:row title="測試(var.field1)-(var.field3)">
							<c:column fieldname="field1"/>
							<c:column fieldname="field2"/>
							<c:column fieldname="field3"/>
						</c:row>
					</c:table>
					
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('advanced')" style="cursor:pointer">進階用法(可用變數)</legend>
				<div id="advanced_sidebar" style="display:none">
					可參考onClick,onDblClick,title屬性的範例來運用變數<br></br>
					row tag可用的變數如下:<br></br>
					<hr></hr>
					保留字:<br></br>
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

