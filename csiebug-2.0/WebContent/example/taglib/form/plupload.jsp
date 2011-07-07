<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/csiebug-ui" %>
<%@ taglib prefix="upload" uri="/csiebug-plupload" %>

<%@page import="csiebug.util.WebUtility"%>

<%WebUtility webutil = new WebUtility();%>


<html>
	<head>
		<%@ include file="../../../pub/common_css.jsp" %>
		<%=webutil.getImportCSSFileLink() %>
		<link href = "<%=webutil.getBasePathForHTML()%>js/plupload/js/jquery.plupload.queue/css/jquery.plupload.queue.css" rel = "stylesheet" type = "text/css">
		
		<link rel="shortcut icon" href="<%=webutil.getBasePathForHTML()%>images/favicon.ico" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title><%=webutil.getRequestAttribute("FunctionName").toString()%></title>
	</head>
	<body>
		<form id="plupload" name="plupload" action="<%=webutil.getBasePathForHTML()%>example/plupload" method="POST">
			<!-- 程式標頭 -->
			<table width="100%">
				<tr>
					<td class="PageHeader"><%=webutil.getRequestAttribute("FunctionName").toString()%></td>
				</tr>
			</table>
			<%@ include file="../../../pub/common_html.jsp" %>
			<%@ include file="../../../pub/common_form.jsp" %>
			
			<!-- 以下為頁面編輯區 -->
			
			<h4>PlUpload</h4>
			
			欲引用PlUpload時可引用下列檔案:<br>
			<c:code>
				<link href = "/csiebug-2.0/js/plupload/js/jquery.plupload.queue/css/jquery.plupload.queue.css" rel = "stylesheet" type = "text/css">
				<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/swfobject/2.2/swfobject.js"></script>
				<script type="text/javascript" src="http://www.google.com/jsapi"></script>
				<script type="text/javascript">
					google.load("jquery", "1.3");
				</script>
				<!-- Thirdparty intialization scripts, needed for the Google Gears and BrowserPlus runtimes -->
				<script type="text/javascript" src="/csiebug-2.0/js/plupload/js/plupload.gears.js"></script>
				<script type="text/javascript" src="http://bp.yahooapis.com/2.4.21/browserplus-min.js"></script>
				<!-- Load plupload and all it's runtimes and finally the jQuery queue widget -->
				<script type="text/javascript" src="/csiebug-2.0/js/plupload/js/plupload.full.js"></script>
				<script type="text/javascript" src="/csiebug-2.0/js/plupload/js/jquery.plupload.queue/jquery.plupload.queue.js"></script>
				<script type="text/javascript" src="/csiebug-2.0/js/plupload/js/jquery.ui.plupload/jquery.ui.plupload.js"></script>
			</c:code>
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('uploadify')" style="cursor:pointer">預設: cancelImg,checkScript,script,uploader都帶預設值</legend>
				<div id="uploadify_sidebar" style="display:none"> -->
					<h4>預設:</h4>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<plupload uploadId="uploadify1" multipartParams="multipartParams"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<upload:plupload uploadId="uploadify1" multipartParams="multipartParams"/>
			<!-- 	</div>
			</fieldset> -->
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('browseButton')">屬性browseButton: String with the ID of the browse button. Flash, HTML 5 and Silverlight requires a shim so you need to specify the id of the button that the shim will be placed above for those runtimes. This option is not required for by the queue widget.</legend>
				<div id="browseButton_sidebar" style="display:none">
				</div>
			</fieldset> -->
				<h4>browseButton:</h4>
				
				<h5>說明:</h5>
				String with the ID of the browse button. Flash, HTML 5 and Silverlight requires a shim so you need to specify the id of the button that the shim will be placed above for those runtimes. This option is not required for by the queue widget.
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('chunkSize')">屬性chunkSize: Enables you to chunk the file into smaller pieces for example if your PHP backend has a max post size of 1MB you can chunk a 10MB file into 10 requests. To disable chunking, remove this config option from your setup.</legend>
				<div id="chunkSize_sidebar" style="display:none">
				</div>
			</fieldset> -->
				<h4>chunkSize:</h4>
				
				<h5>說明:</h5>
				Enables you to chunk the file into smaller pieces for example if your PHP backend has a max post size of 1MB you can chunk a 10MB file into 10 requests. To disable chunking, remove this config option from your setup.
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('container')">屬性container: Element ID to add object elements to, this defaults to the document body element.</legend>
				<div id="container_sidebar" style="display:none">
				</div>
			</fieldset> -->
				<h4>container:</h4>
				
				<h5>說明:</h5>
				Element ID to add object elements to, this defaults to the document body element.
				
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('dragdrop')" style="cursor:pointer">屬性dragdrop:</legend>
				<div id="dragdrop_sidebar" style="display:none"> -->
					<h4>dragdrop:</h4>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<plupload uploadId="uploadify2" runtimes="html5" dragdrop="true" multipartParams="multipartParams"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<upload:plupload uploadId="uploadify2" runtimes="html5" dragdrop="true" multipartParams="multipartParams"/>
			<!-- 	</div>
			</fieldset> -->
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('dropElement')">屬性dropElement: String with the ID of the element that you want to be able to drop files into this is only used by some runtimes that support it.</legend>
				<div id="dropElement_sidebar" style="display:none">
				</div>
			</fieldset> -->
				<h4>dropElement:</h4>
				
				<h5>說明:</h5>
				String with the ID of the element that you want to be able to drop files into this is only used by some runtimes that support it.
				
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('filters')" style="cursor:pointer">屬性filters:</legend>
				<div id="filters_sidebar" style="display:none"> -->
					<h4>filters:</h4>
					
					<h5>JAVA 程式碼:</h5>
					<c:code>
					List<Map<String, String>> filters = new ArrayList<Map<String,String>>();
					Map<String, String> filterOption = new LinkedHashMap<String, String>();
					filterOption.put("title", "Image files");
					filterOption.put("extensions", "jpg,gif,png");
					filters.add(filterOption);
					setRequestAttribute("fileFilter", filters);
					</c:code>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<plupload uploadId="uploadify3" filters="fileFilter" multipartParams="multipartParams"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<upload:plupload uploadId="uploadify3" filters="fileFilter" multipartParams="multipartParams"/>
			<!-- 	</div>
			</fieldset> -->
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('flashSWFURL')" style="cursor:pointer">屬性flashSWFURL:</legend>
				<div id="flashSWFURL_sidebar" style="display:none"> -->
					<h4>flashSWFURL:</h4>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<plupload uploadId="uploadify4" runtimes="flash" multipartParams="multipartParams"/>
					<plupload uploadId="uploadify5" runtimes="flash" flashSWFURL="/csiebug-2.0/js/plupload/js/plupload.flash.swf" multipartParams="multipartParams"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<upload:plupload uploadId="uploadify4" runtimes="flash" multipartParams="multipartParams"/>
					<upload:plupload uploadId="uploadify5" runtimes="flash" flashSWFURL="/csiebug-2.0/js/plupload/js/plupload.flash.swf" multipartParams="multipartParams"/>
			<!-- 	</div>
			</fieldset> -->
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('headers')">屬性headers: Name/value object with custom headers to add to HTTP requests.</legend>
				<div id="headers_sidebar" style="display:none">
				</div>
			</fieldset> -->
				<h4>headers:</h4>
				
				<h5>說明:</h5>
				Name/value object with custom headers to add to HTTP requests.
				
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('maxFileSize')" style="cursor:pointer">屬性maxFileSize:</legend>
				<div id="maxFileSize_sidebar" style="display:none"> -->
					<h4>maxFileSize:</h4>
					
					<h5>說明:</h5>
					Maximum file size that the user can pick. This string can be in the following formats 100b, 10kb, 10mb.
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<plupload uploadId="uploadify6" runtimes="html5" maxFileSize="10kb" multipartParams="multipartParams"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<upload:plupload uploadId="uploadify6" runtimes="html5" maxFileSize="10kb" multipartParams="multipartParams"/>
			<!-- 	</div>
			</fieldset> -->
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('multipart')">屬性multipart: Boolean state if the files should be uploaded using mutlipart instead of direct binary streams. Doesn't work on WebKit using the HTML 5 runtime.</legend>
				<div id="multipart_sidebar" style="display:none">
				</div>
			</fieldset> -->
				<h4>multipart:</h4>
				
				<h5>說明:</h5>
				Boolean state if the files should be uploaded using mutlipart instead of direct binary streams. Doesn't work on WebKit using the HTML 5 runtime.
				
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('multipartParams')">屬性multipartParams: Object name/value collection with arguments to get posted together with the multipart file.</legend>
				<div id="multipartParams_sidebar" style="display:none">
				</div>
			</fieldset> -->
				<h4>multipartParams:</h4>
				
				<h5>說明:</h5>
				Object name/value collection with arguments to get posted together with the multipart file.
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('multipleQueues')">屬性multipleQueues: Boolean state if you should be able to upload multiple times or not.</legend>
				<div id="multipleQueues_sidebar" style="display:none">
				</div>
			</fieldset> -->
				<h4>multipleQueues:</h4>
				
				<h5>說明:</h5>
				Boolean state if you should be able to upload multiple times or not.
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('preinit')" style="cursor:pointer">屬性preinit:</legend>
				<div id="preinit_sidebar" style="display:none"> -->
					<h4>preinit:</h4>
					
					<h5>說明:</h5>
					Function callback that enables you to bind events before the uploader is initialized.
					
					<h5>JAVA 程式碼:</Map>
					<c:code>
					Map<String, String> preInitEvent = new LinkedHashMap<String, String>();
					preInitEvent.put(PlUploadEventEnum.Init.toString(), "function(up, info) {\n" +
				                "alert('Info:' + info + ', Features:' + up.features);\n" +
				            "}");
					preInitEvent.put(PlUploadEventEnum.UploadFile.toString(), "function(up, file) {\n" +
			                "alert('UploadFile:' + file);\n" +
			            "}");
					setRequestAttribute("preInitEvent", preInitEvent);
					</c:code>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<plupload uploadId="uploadify7" runtimes="html5" preinit="preInitEvent" multipartParams="multipartParams"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<upload:plupload uploadId="uploadify7" runtimes="html5" preinit="preInitEvent" multipartParams="multipartParams"/>
			<!-- 	</div>
			</fieldset> -->
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('rename')" style="cursor:pointer">屬性rename:</legend>
				<div id="rename_sidebar" style="display:none"> -->
					<h4>rename:</h4>
					
					<h5>說明:</h5>
					Boolean state if it should be possible to rename files before uploading them. Default is false.
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<plupload uploadId="uploadify8" runtimes="html5" rename="true" multipartParams="multipartParams"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<upload:plupload uploadId="uploadify8" runtimes="html5" rename="true" multipartParams="multipartParams"/>
			<!-- 	</div>
			</fieldset> -->
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('requiredFeatures')">屬性requiredFeatures: Comma separated list of features that each runtime must have for it to initialize.</legend>
				<div id="requiredFeatures_sidebar" style="display:none">
				</div>
			</fieldset> -->
				<h4>requiredFeatures:</h4>
				
				<h5>說明:</h5>
				Comma separated list of features that each runtime must have for it to initialize.
				
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('resize')" style="cursor:pointer">屬性resize:</legend>
				<div id="resizeHeight_sidebar" style="display:none"> -->
					<h4>resize:</h4>
					
					<h5>說明:</h5>
					Enables plupload to resize the images to clientside to the specified width, height and quality. Set this to an object with those parameters.
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<plupload uploadId="uploadify9" resizeHeight="240" resizeWidth="320" resizeQuality="90" multipartParams="multipartParams"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<upload:plupload uploadId="uploadify9" resizeHeight="240" resizeWidth="320" resizeQuality="90" multipartParams="multipartParams"/>
			<!-- 	</div>
			</fieldset> -->
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('runtimes')" style="cursor:pointer">屬性runtimes:</legend>
				<div id="runtimesHeight_sidebar" style="display:none"> -->
					<h4>runtimes:</h4>
					
					<h5>說明:</h5>
					This is a comma separated list of runtimes that you want to initialize the uploader instance with. It will try to initialize each runtime in order if one fails it will move on to the next one.
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<plupload uploadId="uploadify10" runtimes="gears" multipartParams="multipartParams"/>
					<plupload uploadId="uploadify11" runtimes="html5" multipartParams="multipartParams"/>
					<plupload uploadId="uploadify12" runtimes="flash" multipartParams="multipartParams"/>
					<plupload uploadId="uploadify13" runtimes="silverlight" multipartParams="multipartParams"/>
					<plupload uploadId="uploadify14" runtimes="browserplus" multipartParams="multipartParams"/>
					<plupload uploadId="uploadify15" runtimes="html4" multipartParams="multipartParams"/>
					<plupload uploadId="uploadify16" runtimes="gears,html5,flash,silverlight,browserplus,html4" multipartParams="multipartParams"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					<upload:plupload uploadId="uploadify10" runtimes="gears" multipartParams="multipartParams"/>
					<upload:plupload uploadId="uploadify11" runtimes="html5" multipartParams="multipartParams"/>
					<upload:plupload uploadId="uploadify12" runtimes="flash" multipartParams="multipartParams"/>
					<upload:plupload uploadId="uploadify13" runtimes="silverlight" multipartParams="multipartParams"/>
					<upload:plupload uploadId="uploadify14" runtimes="browserplus" multipartParams="multipartParams"/>
					<upload:plupload uploadId="uploadify15" runtimes="html4" multipartParams="multipartParams"/>
					<upload:plupload uploadId="uploadify16" runtimes="gears,html5,flash,silverlight,browserplus,html4" multipartParams="multipartParams"/>
			<!-- 	</div>
			</fieldset> -->
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('silverlightXAPURL')" style="cursor:pointer">屬性silverlightXAPURL:</legend>
				<div id="silverlightXAPURL_sidebar" style="display:none"> -->
					<h4>silverlightXAPURL:</h4>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<plupload uploadId="uploadify17" runtimes="silverlight" multipartParams="multipartParams"/>
					<plupload uploadId="uploadify18" runtimes="silverlight" silverlightXAPURL="/csiebug-2.0/js/plupload/js/plupload.silverlight.xap" multipartParams="multipartParams"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<upload:plupload uploadId="uploadify17" runtimes="silverlight" multipartParams="multipartParams"/>
					<upload:plupload uploadId="uploadify18" runtimes="silverlight" silverlightXAPURL="/csiebug-2.0/js/plupload/js/plupload.silverlight.xap" multipartParams="multipartParams"/>
			<!-- 	</div>
			</fieldset> -->
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('uniqueNames')">屬性uniqueNames: Generate unique filenames when uploading. This will generate unqiue filenames for the files so that they don't for example collide with existing ones on the server.</legend>
				<div id="uniqueNames_sidebar" style="display:none">
				</div>
			</fieldset> -->
				<h4>uniqueNames:</h4>
				
				<h5>說明:</h5>
				Generate unique filenames when uploading. This will generate unqiue filenames for the files so that they don't for example collide with existing ones on the server.
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('url')">屬性url: Page URL to where the files will be uploaded to.</legend>
				<div id="url_sidebar" style="display:none">
				</div>
			</fieldset> -->
				<h4>url:</h4>
				
				<h5>說明:</h5>
				Page URL to where the files will be uploaded to.
			
			<hr>
			<!-- <fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('urlstreamUpload')">屬性urlstreamUpload: Boolean state if Flash should be forced to use URLStream instead of FileReference.upload.</legend>
				<div id="urlstreamUpload_sidebar" style="display:none">
				</div>
			</fieldset> -->
				<h4>urlstreamUpload:</h4>
				
				<h5>說明:</h5>
				Boolean state if Flash should be forced to use URLStream instead of FileReference.upload.
				
			<br>
			<br>
			<br>
			<br>
			<br>
			
			<!-- 頁面編輯區結束 -->
			
			<!-- Javascript區 -->
			<%=webutil.getImportJSFileLink() %>
			<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/swfobject/2.2/swfobject.js"></script>
			<script type="text/javascript" src="http://www.google.com/jsapi"></script>
			<script type="text/javascript">
				google.load("jquery", "1.3");
			</script>
			<!-- Thirdparty intialization scripts, needed for the Google Gears and BrowserPlus runtimes -->
			<!-- <script type="text/javascript" src="<%=webutil.getBasePathForHTML()%>js/plupload/js/plupload.gears.js"></script> -->
			<script type="text/javascript" src="http://bp.yahooapis.com/2.4.21/browserplus-min.js"></script>
			<!-- Load plupload and all it's runtimes and finally the jQuery queue widget -->
			<script type="text/javascript" src="<%=webutil.getBasePathForHTML()%>js/plupload/js/plupload.full.js"></script>
			<script type="text/javascript" src="<%=webutil.getBasePathForHTML()%>js/plupload/js/jquery.plupload.queue/jquery.plupload.queue.js"></script>
			<!-- <script type="text/javascript" src="<%=webutil.getBasePathForHTML()%>js/plupload/js/jquery.ui.plupload/jquery.ui.plupload.js"></script> -->
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

