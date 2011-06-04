<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/csiebug-ui" %>
<%@ taglib prefix="excel" uri="/csiebug-excel" %>

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
		
		<form id="excelGrid" name="excelGrid" action="<%=webutil.getBasePathForHTML()%>example/excelGrid" method="POST">
			<!-- 程式標頭 -->
			<table width="100%">
				<tr>
					<td class="PageHeader"><%=webutil.getRequestAttribute("FunctionName").toString()%></td>
				</tr>
			</table>
			<%@ include file="../../../pub/common_form.jsp" %>
			
			<!-- 以下為頁面編輯區 -->
			<!-- 
			<div class="contextMenu" id="myMenu1">
		      <ul>
		
		        <li id="copy"><img id = "mpp_copyButton" align = "absmiddle" src = "/csiebug-2.0//images/copy-icon.png" title = "複製"></img></li>
		
		      </ul>
		
		    </div>
		    -->
		    
			<h4>Excel Grid</h4>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('forDev')" style="cursor:pointer">Developer's guide</legend>
				<div id="forDev_sidebar" style="display:none">
					大部分寫法接近Grid元件，可參閱<a href="example/table">table</a>、<a href="example/row">row</a>、<a href="example/column">column</a>、<a href="example/columns">columns</a><br></br>
					要注意的是，Excel Grid有自定義的column，會在developer指定的column前面多出三個column<br></br>
					在您要寫一些自訂script操作dom物件(grid上的內容)的時候，需要注意這點<br></br>
					第一個column是序號(上面有onclick事件可選取整個row)<br></br>
					第二個column是隱藏的，內容是存order(排序號)的hidden欄位<br></br>
					第三個column是隱藏的，內容是存uniqueID的hidden欄位<br></br>
					這代表您存放資料的List裡面也必須有uniqueID和order這些欄位值<br></br>
					若不自己指定column內容的話，預設是所有column都可編輯，會產生<a href="example/editableLabel">EditableLabel控制項</a><br></br>
					
					<hr>
					
					<h4>table欄位解說: 可參考<a href="example/table">table</a></h4>
					
					<a href="example/excelGrid?ActFlag=downloadFileName">downloadFileName</a>: 用來指定download檔案的檔名<br></br>
					formId: 指定form的id(在grid資料有異動的時候,會送出AJAX request)<br></br>
					projectId: 指定專案id<br></br>
					
					<hr>
					
					<h4>row欄位請參考<a href="example/row">row</a></h4>
					
					<hr>
					
					<h4>column欄位解說: 可參考<a href="example/column">column</a></h4>
					
					<a href="example/excelGrid?ActFlag=hideable">hideable</a>: 用來指定此column是否可隱藏，點選此column的標題列可將整個column展開或隱藏；預設是"true"<br></br>
					<a href="example/excelGrid?ActFlag=selectable">selectable</a>: 指定此column是否可選取；預設是"true"<br></br>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('forUser')" style="cursor:pointer">User's guide</legend>
				<div id="forUser_sidebar" style="display:none">
					<excel:table id="test" data="gridData" projectId="templateProject" formId="excelGrid" downloadFileName="testExcel" downloadColumns="3,4,5">
						<excel:row>
							<excel:column fieldname="field1" />
							<excel:column fieldname="field2" />
							<excel:column fieldname="field3" />
						</excel:row>
					</excel:table>
					
					<hr></hr>
					
					<h4>基本操作:</h4>
					1.選擇列: 點選第一個column，使此row focus<br></br>
					2.選擇cell: 點選cell，使此cell focus<br></br>
					3.複選: 按住ctrl鍵然後點選<br></br>
					4.連續選擇: 按住shift鍵然後點選(選擇cell時，有複製的附加功能，若選擇使用，則會用第一個cell值全部取代)<br></br>
					5.編輯: 在cell上面雙擊則可變成編輯狀態或在cell上點一下然後直接輸入文字(會直接覆蓋掉原本內容)<br></br>
					6.編輯中移動: 按住ctrl鍵再配合方向鍵，可移動到別的cell上面編輯<br></br>
					7.隱藏資訊: 點選標題列，可將整個column藏起來；游標移到標題列會有提示訊息，再點一下可回復<br></br>
					
					<h4>功能列:</h4>
					
					1.上/下移: 將資料與上/下列交換<br></br>
					2.往前新增: 先選定一個row，然後再點選此按鈕在上方增加新row<br></br>
					3.往後新增: 先選定一個row，然後再點選此按鈕在下方增加新row<br></br>
					4.刪除節點: 選定欲刪除的row(可複選)，然後再點選此按鈕刪除或按下del鍵<br></br>
					5.複製: 選定欲複製的row或cell(可複選)，然後再點選此按鈕或按下ctrl + c(之後配合貼上功能)<br></br>
					6.貼上: 選定欲貼上的cell(可複選)，然後再點選此按鈕貼上或按下ctrl + v<br></br>
					7.往前貼上: 選定欲貼上的row，然後再點選此按鈕貼上<br></br>
					8.往後貼上: 選定欲貼上的row，然後再點選此按鈕貼上或按下ctrl + v<br></br>
					9.下載Excel<br></br>
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
			<script src="<%=webutil.getBasePathForHTML()%>js/csiebug-form/csiebug-editableLabel.js"></script>
			<!-- <script src="<%=webutil.getBasePathForHTML()%>js/csiebug-excel/csiebug-excel.js"></script> -->
			<script src="<%=webutil.getBasePathForHTML()%>js/csiebug-excel/csiebug-excel-jquery.js"></script>
			<script src="<%=webutil.getBasePathForHTML()%>example/taglib/excelGrid/excelGrid.js"></script>
			<!-- <script src="<%=webutil.getBasePathForHTML()%>js/jquery-contextmenu/jquery.contextmenu.r2.packed.js"></script>-->
			
			<script type="text/javascript">
				$(document).ready(function() {
					<% if(webutil.getRequestAttribute("Script") != null) { out.print(webutil.getRequestAttribute("Script").toString()); }%>
					<% if(webutil.getRequestAttribute("Msg") != null) { out.print(webutil.getRequestAttribute("Msg").toString()); }%>
/*
					$('div[id$="_textLabel"]').contextMenu('myMenu1', {
					      bindings: {

					        'copy': function(t) {

					          //alert('Trigger was '+t.id+'\nAction was Open');
					        	copyForProject('mpp', 1);

					        },

					      }

					    });*/
				});
			</script>
			<!-- Javascript區結束 -->
			
		</form>
	</body>
</html>

