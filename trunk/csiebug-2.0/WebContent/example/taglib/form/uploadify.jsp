<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/csiebug-ui" %>
<%@ taglib prefix="upload" uri="/csiebug-uploadify" %>

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
		<form id="uploadify" name="uploadify" action="<%=webutil.getBasePathForHTML()%>example/uploadify" method="POST">
			<!-- 程式標頭 -->
			<table width="100%">
				<tr>
					<td class="PageHeader"><%=webutil.getRequestAttribute("FunctionName").toString()%></td>
				</tr>
			</table>
			<%@ include file="../../../pub/common_html.jsp" %>
			<%@ include file="../../../pub/common_form.jsp" %>
			
			<!-- 以下為頁面編輯區 -->
			
			<h4>Uploadify</h4>
			
			欲引用Uploadify時，除了jquery以外還須引用下列js檔:<br>
			<c:code>
				<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/swfobject/2.2/swfobject.js"></script>
				<script type="text/javascript" src="/csiebug-2.0/js/jquery.uploadify-v2.1.4/jquery.uploadify.v2.1.4.min.js"></script>
			</c:code>
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('uploadify')" style="cursor:pointer">預設: cancelImg,checkScript,script,uploader都帶預設值</legend>
				<div id="uploadify_sidebar" style="display:none"> -->
					<h4>預設: cancelImg,checkScript,script,uploader都帶預設值</h4>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<uploadify uploadId="uploadify1"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<upload:uploadify uploadId="uploadify1" scriptData="{'ActFlag':'example'}"/>
			<!-- 	</div>
			</fieldset> -->
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('auto')" style="cursor:pointer">屬性auto: 把加到queue的檔案自動上傳</legend>
				<div id="auto_sidebar" style="display:none"> -->
					<h4>屬性auto: 把加到queue的檔案自動上傳</h4>
					
					<h5>說明</h5>
					Input Format:true / false<br>
					Default:false
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<uploadify uploadId="uploadify2" auto="true"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<upload:uploadify uploadId="uploadify2" auto="true" scriptData="{'ActFlag':'example'}"/>
				<!-- </div>
			</fieldset> -->
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('buttonImg')">屬性buttonImg: 設定選取檔案按鈕的圖檔</legend>
				<div id="buttonImg_sidebar" style="display:none">
				</div>
			</fieldset> -->
				<h4>屬性buttonImg: 設定選取檔案按鈕的圖檔</h4>
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('buttonText')" style="cursor:pointer">屬性buttonText: 設定選取檔案按鈕的文字</legend>
				<div id="buttonText_sidebar" style="display:none"> -->
					<h4>屬性buttonText: 設定選取檔案按鈕的文字</h4>
					
					<h5>說明:</h5>
					露出的白色部份是flash的可點選範圍<br>
					因為自訂文字字數不確定多長,所以可能會露出白色區塊<br>
					可利用width屬性來彌補<br>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<uploadify uploadId="uploadify3" buttonText="測試文字"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<upload:uploadify uploadId="uploadify3" buttonText="測試文字" scriptData="{'ActFlag':'example'}"/>
				<!-- </div>
			</fieldset> -->
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('cancelImg')">屬性cancelImg: 設定取消選取檔案的圖檔</legend>
				<div id="cancelImg_sidebar" style="display:none">
				</div>
			</fieldset> -->
				<h4>屬性cancelImg: 設定取消選取檔案的圖檔</h4>
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('checkScript')">屬性checkScript: 設定server端程式，用來檢查是否會覆蓋檔案</legend>
				<div id="checkScript_sidebar" style="display:none">
				</div>
			</fieldset> -->
				<h4>屬性checkScript: 設定server端程式，用來檢查是否會覆蓋檔案</h4>
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('displayData')" style="cursor:pointer">屬性displayData: 設定上傳動畫顯示內容；百分比或速度</legend>
				<div id="displayData_sidebar" style="display:none"> -->
					<h4>屬性displayData: 設定上傳動畫顯示內容；百分比或速度</h4>
					
					<h5>說明:</h5>
					Input Format:'percentage' / 'speed'<br>
					Default:'percentage'
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<uploadify uploadId="uploadify4" displayData="speed"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<upload:uploadify uploadId="uploadify4" displayData="speed" scriptData="{'ActFlag':'example'}"/>
				<!-- </div>
			</fieldset> -->
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('expressInstall')">屬性expressInstall: 設定expressInstall.swf路徑</legend>
				<div id="expressInstall_sidebar" style="display:none">
				</div>
			</fieldset> -->
				<h4>屬性expressInstall: 設定expressInstall.swf路徑</h4>
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('fileExt')" style="cursor:pointer">屬性fileExt: 設定副檔名限制</legend>
				<div id="fileExt_sidebar" style="display:none"> -->
					<h4>屬性fileExt: 設定副檔名限制</h4>
					
					<h5>說明:</h5>
					Input Format:'*.ext;*.ext;*.ext;...'
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<uploadify uploadId="uploadify5" fileExt="*.jpg;*.jpeg;*.gif;*.png"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<upload:uploadify uploadId="uploadify5" fileExt="*.jpg;*.jpeg;*.gif;*.png" scriptData="{'ActFlag':'example'}"/>
				<!-- </div>
			</fieldset> -->
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('folder')">屬性folder: 設定上傳路徑</legend>
				<div id="folder_sidebar" style="display:none">
				</div>
			</fieldset> -->
				<h4>屬性folder: 設定上傳路徑</h4>
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('height')" style="cursor:pointer">屬性height: 設定選擇檔案按鈕的高度</legend>
				<div id="height_sidebar" style="display:none"> -->
					<h4>屬性height: 設定選擇檔案按鈕的高度</h4>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<uploadify uploadId="uploadify6" height="50"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<upload:uploadify uploadId="uploadify6" height="50" scriptData="{'ActFlag':'example'}"/>
				<!-- </div>
			</fieldset> -->
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('hideButton')">屬性hideButton: 將flash button藏起來,以便developer可以自行style div</legend>
				<div id="hideButton_sidebar" style="display:none">
				</div>
			</fieldset> -->
				<h4>屬性hideButton: 將flash button藏起來,以便developer可以自行style div</h4>
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('method')">屬性method: 設定submit的方法;POST/GET</legend>
				<div id="method_sidebar" style="display:none">
				</div>
			</fieldset> -->
				<h4>屬性method: 設定submit的方法;POST/GET</h4>
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('multi')" style="cursor:pointer">屬性multi: 設定多選檔案</legend>
				<div id="multi_sidebar" style="display:none"> -->
					<h4>屬性multi: 設定多選檔案</h4>
					
					<h5>說明:</h5>
					Input Format:true / false<br>
					Default:false
				
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<uploadify uploadId="uploadify7" multi="true"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<upload:uploadify uploadId="uploadify7" multi="true" scriptData="{'ActFlag':'example'}"/>
				<!-- </div>
			</fieldset> -->
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('queueID')">屬性queueID: 設定上傳檔案列表的div id</legend>
				<div id="queueID_sidebar" style="display:none">
				</div>
			</fieldset> -->
				<h4>屬性queueID: 設定上傳檔案列表的div id</h4>
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('queueSizeLimit')" style="cursor:pointer">屬性queueSizeLimit: 設定queue可放檔案的數量</legend>
				<div id="queueSizeLimit_sidebar" style="display:none"> -->
					<h4>屬性queueSizeLimit: 設定queue可放檔案的數量</h4>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<uploadify uploadId="uploadify8" multi="true" queueSizeLimit="2"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<upload:uploadify uploadId="uploadify8" multi="true" queueSizeLimit="2" scriptData="{'ActFlag':'example'}"/>
				<!-- </div>
			</fieldset> -->
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('removeCompleted')" style="cursor:pointer">屬性removeCompleted: 上傳成功直接從queue移除</legend>
				<div id="removeCompleted_sidebar" style="display:none"> -->
					<h4>屬性removeCompleted: 上傳成功直接從queue移除</h4>
					
					<h5>說明:</h5>
					Input Format:true / false<br>
					Default:true
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<uploadify uploadId="uploadify9" removeCompleted="false"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<upload:uploadify uploadId="uploadify9" removeCompleted="false" scriptData="{'ActFlag':'example'}"/>
				<!-- </div>
			</fieldset> -->
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('rollover')" style="cursor:pointer">屬性rollover: 選取檔案按鈕有rollover效果</legend>
				<div id="rollover_sidebar" style="display:none"> -->
					<h4>屬性rollover: 選取檔案按鈕有rollover效果</h4>
					
					<h5>說明:</h5>
					要配合設定buttonImg屬性<br>
					Input Format:true / false<br>
					Default:false
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<uploadify uploadId="uploadify10" rollover="true"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<upload:uploadify uploadId="uploadify10" rollover="true" scriptData="{'ActFlag':'example'}"/>
				<!-- </div>
			</fieldset> -->
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('script')">屬性script: 設定server端程式，用來上傳檔案</legend>
				<div id="script_sidebar" style="display:none">
				</div>
			</fieldset> -->
				<h4>屬性script: 設定server端程式，用來上傳檔案</h4>
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('scriptData')" style="cursor:pointer">屬性scriptData: 設定傳給server端上傳檔案程式的參數，用json格式</legend>
				<div id="scriptData_sidebar" style="display:none">
					<h5>說明:</h5>
					Input Format:{name:value,name:value,name:value,...}
				</div>
			</fieldset> -->
				<h4>屬性scriptData: 設定傳給server端上傳檔案程式的參數，用json格式</h4>
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('simUploadLimit')" style="cursor:pointer">屬性simUploadLimit: 設定同時上傳的數目</legend>
				<div id="simUploadLimit_sidebar" style="display:none"> -->
					<h4>屬性simUploadLimit: 設定同時上傳的數目</h4>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<uploadify uploadId="uploadify11" multi="true" simUploadLimit="2"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<upload:uploadify uploadId="uploadify11" multi="true" simUploadLimit="2" scriptData="{'ActFlag':'example'}"/>
				<!-- </div>
			</fieldset> -->
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('sizeLimit')" style="cursor:pointer">屬性sizeLimit: 設定上傳檔案大小限制(單位為bytes)</legend>
				<div id="sizeLimit_sidebar" style="display:none"> -->
					<h4>屬性sizeLimit: 設定上傳檔案大小限制(單位為bytes)</h4>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<uploadify uploadId="uploadify12" sizeLimit="102400"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<upload:uploadify uploadId="uploadify12" sizeLimit="102400" scriptData="{'ActFlag':'example'}"/>
				<!-- </div>
			</fieldset> -->
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('uploader')">屬性uploader: 設定uploadify.swf路徑</legend>
				<div id="uploader_sidebar" style="display:none">
				</div>
			</fieldset> -->
				<h4>屬性uploader: 設定uploadify.swf路徑</h4>
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('width')" style="cursor:pointer">屬性width: 設定選擇檔案按鈕的寬度</legend>
				<div id="width_sidebar" style="display:none"> -->
					<h4>屬性width: 設定選擇檔案按鈕的寬度</h4>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<uploadify uploadId="uploadify13" width="250"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<upload:uploadify uploadId="uploadify13" width="250" scriptData="{'ActFlag':'example'}"/>
				<!-- </div>
			</fieldset> -->
			
			<br>
			<br>
			<br>
			<br>
			<br>
			
			<!-- 頁面編輯區結束 -->
			
			<!-- Javascript區 -->
			<%=webutil.getImportJSFileLink() %>
			<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/swfobject/2.2/swfobject.js"></script>
			<script type="text/javascript" src="<%=webutil.getBasePathForHTML()%>js/jquery.uploadify-v2.1.4/jquery.uploadify.v2.1.4.min.js"></script>
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

