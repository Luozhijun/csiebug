<%
out.println(session.getAttribute("timelineJSONData"));
session.removeAttribute("timelineJSONData");
%>