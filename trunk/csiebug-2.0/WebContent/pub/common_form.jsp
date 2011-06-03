<%@page import="csiebug.util.StringUtility"%>

<input type="hidden" id="ActFlag" name="ActFlag" value="<% if(request.getParameter("ActFlag") == null) { out.print(""); } else { out.print(StringUtility.cleanXSSPattern(request.getParameter("ActFlag"))); }%>"></input>

<!-- download excel使用欄位 -->
<input type="hidden" id="DownloadFileName" name="DownloadFileName"></input>
<input type="hidden" id="DownloadTableId" name="DownloadTableId"></input>
<input type="hidden" id="GridHTML" name="GridHTML"></input>
<input type="hidden" id="DownloadRows" name="DownloadRows"></input>
<input type="hidden" id="DownloadColumns" name="DownloadColumns"></input>
