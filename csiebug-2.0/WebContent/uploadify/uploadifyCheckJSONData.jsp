<%@page import="csiebug.util.AssertUtility"%>
<%@page import="csiebug.util.StringUtility"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.io.File"%>

<%
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

File baseFolder = new File(path);

StringBuffer jsonData = new StringBuffer();
jsonData.append("[\n");

if(!baseFolder.exists()) {
	baseFolder.mkdirs();
} else if(baseFolder.exists() && baseFolder.isDirectory()) {
	String[] files = baseFolder.list();
	
	Enumeration<String> e = request.getParameterNames();
	boolean matchFlag = false;
	while(e.hasMoreElements()) {
		String key = e.nextElement();
		
		if(!key.equalsIgnoreCase("folder")) {
			String value = request.getParameter(key);
			if(StringUtility.isInArray(value, files)) {
				if(matchFlag) {
					jsonData.append(", ");
				} else {
					matchFlag = true;
				}
				
				jsonData.append("\"" + value + "\"");
			}
		}
	}
}
jsonData.append("]\n");
out.println(jsonData.toString());
%>