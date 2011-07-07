<%@page import="java.util.ArrayList"%>
<%@page import="csiebug.util.FileUtility"%>
<%@page import="org.apache.struts2.dispatcher.multipart.JakartaMultiPartRequest"%>
<%@page import="org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper"%>
<%@page import="csiebug.util.AssertUtility"%>
<%@page import="java.util.UUID"%>
<%@page import="org.apache.commons.io.FilenameUtils"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Enumeration"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="java.io.File"%>

<%
String actFlag = request.getParameter("ActFlag");

Integer chunkIndex = null;
Integer chunks = null;
if(request.getParameter("chunk") != null) {
	chunkIndex = Integer.parseInt(request.getParameter("chunk"));
}
if(request.getParameter("chunks") != null) {
	chunks = Integer.parseInt(request.getParameter("chunks"));
}

if(!AssertUtility.isNotNullAndNotSpace(actFlag) || !actFlag.equalsIgnoreCase("example")) {
	String folder = request.getParameter("uploadFolder");
	if(!AssertUtility.isNotNullAndNotSpace(folder)) {
		folder = "/upload";
	}
	String path = request.getServletContext().getRealPath("/");
	if(folder.startsWith("/")) {
		path = path + folder;
	} else {
		path = path + "/" + folder;
	}
	
	File uploadDirectory = new File(path);
	
	if(!uploadDirectory.exists()) {
		uploadDirectory.mkdirs();
	}
	
	if(request instanceof MultiPartRequestWrapper)   {
		File[] files = ((MultiPartRequestWrapper)request).getFiles("file");
		
		if(chunks == null || chunks < 2) {
			for(int i = 0; i < files.length; i++) {
				File tempFile = files[i];
				File newFile = null;
				
				if(path.endsWith("/")) {
					newFile = new File(path + request.getParameter("name"));
				} else {
					newFile = new File(path + "/" + request.getParameter("name"));
				}
				
				FileUtility.copyFile(tempFile, newFile);
			}
		} else {
			//要把chunk組合起來,再拷貝
			List<File> fileList = new ArrayList<File>();
			if(session.getAttribute("currentUploadFile") != null) {
				fileList = (List<File>)session.getAttribute("currentUploadFile");
			}
			for(int i = 0; i < files.length; i++) {
				fileList.add(files[i]);
			}
			session.setAttribute("currentUploadFile", fileList);
			
			if(chunkIndex == (chunks - 1)) {
				File newFile = null;
				
				if(path.endsWith("/")) {
					newFile = new File(path + request.getParameter("name"));
				} else {
					newFile = new File(path + "/" + request.getParameter("name"));
				}
				
				FileUtility.catFile(fileList.toArray(new File[fileList.size()]), newFile);
				
				session.removeAttribute("currentUploadFile");
			}
		}
	}
} else {
	//故意讓他停三秒,可以比較容易看上傳的範例
	Thread.sleep(3000);
}

response.getWriter().print(request.getParameter("Filename"));
%>