<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/csiebug-ui" %>
<%@ taglib prefix="cc" uri="/csiebug-xmlSwfChart" %>

<%@page import="csiebug.util.WebUtility"%>

<%WebUtility webutil = new WebUtility();%>


<html>
	<head>
		<%@ include file="../../../pub/common_css.jsp" %>
		<%=webutil.getImportCSSFileLink() %>
		
		<%@ include file="../../../js/xmlSwfChart/xmlSwfChart.js" %>
		<script src="<%=webutil.getBasePathForHTML()%>js/xmlSwfChart/AC_RunActiveContent.js" type="text/javascript"></script>
		
		<link rel="shortcut icon" href="<%=webutil.getBasePathForHTML()%>images/favicon.ico" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title><%=webutil.getRequestAttribute("FunctionName").toString()%></title>
	</head>
	<body>
		<form id="xmlSwfChart" name="xmlSwfChart" action="<%=webutil.getBasePathForHTML()%>example/xmlSwfChart" method="POST">
			<!-- 程式標頭 -->
			<table width="100%">
				<tr>
					<td class="PageHeader"><%=webutil.getRequestAttribute("FunctionName").toString()%></td>
				</tr>
			</table>
			<%@ include file="../../../pub/common_html.jsp" %>
			
			<!-- 以下為頁面編輯區 -->
			<h4>XmlSwfChart</h4>
			
			欲引用XmlSwfChart時，須在HTML head區塊內加入以下兩行:<br>
			<c:code>
				&lt;%@ include file="../../../js/xmlSwfChart/xmlSwfChart.js" %&gt;<br>
				<script src="<%=webutil.getBasePathForHTML()%>js/xmlSwfChart/AC_RunActiveContent.js" type="text/javascript"></script>
			</c:code>
			<br>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('chart')" style="cursor:pointer">屬性list: 從request attribute取得chart的資料</legend>
				<div id="chart_sidebar" style="display:none">
					<h5>不同的chartType資料結構不同</h5>
					<font color="red">一般</font>: 二維陣列(ArrayList)，每個Element有自己的屬性(LinkedHashMap)<br>
					<font color="red">pie</font>: 只需要一個value列，且所有數必須是正數<br>
					<font color="red">floating column/bar</font>: 每個column/bar需要兩個value(列)順序是high, low<br>
					<font color="red">candlestick</font>: 每個candle需要四個value(列)順序是max, min, open, close<br>
					<font color="red">scatter</font>: xy資料必須成對出現<br>
					<font color="red">bubble</font>: xyz資料必須成對出現<br>
					
					<h5>JAVA 程式碼</h5>
					
					<c:code>
					<!-- pseudo code
					ArrayList<ArrayList<LinkedHashMap<String, String>>> list = new ArrayList<ArrayList<LinkedHashMap<String,String>>>();
					
					ArrayList<LinkedHashMap<String, String>> list2 = new ArrayList<LinkedHashMap<String, String>>();
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("DataType", "string");
					map.put("Value", "");
					list2.add(map);
					
					map = new LinkedHashMap<String, String>();
					map.put("DataType", "string");
					map.put("Value", "2005");
					list2.add(map);
					
					map = new LinkedHashMap<String, String>();
					map.put("DataType", "string");
					map.put("Value", "2006");
					list2.add(map);
					
					list.add(list2);
					
					list2 = new ArrayList<LinkedHashMap<String, String>>();
					map = new LinkedHashMap<String, String>();
					map.put("DataType", "string");
					map.put("Value", "Region A");
					list2.add(map);
					
					map = new LinkedHashMap<String, String>();
					map.put("DataType", "number");
					map.put("Value", "10");
					list2.add(map);
					
					map = new LinkedHashMap<String, String>();
					map.put("DataType", "number");
					map.put("Value", "20");
					list2.add(map);
					
					list.add(list2);
					
					setRequestAttribute("chartData", list);
					-->
					</c:code>
				
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<XmlSwfChart chartId="chart1" list="chartData"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<cc:XmlSwfChart chartId="chart1" list="chartData"/>
					
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('axisCategoryLabels')" style="cursor:pointer">屬性axisCategoryLabels: 覆寫橫軸標題，用分號隔開；只適用在scatter和bubble chart</legend>
				<div id="axisCategoryLabels_sidebar" style="display:none">
					<h5>JAVA 程式碼</h5>
					
					<c:code>
					<!-- pseudo code
		ArrayList<ArrayList<LinkedHashMap<String, String>>> list = new ArrayList<ArrayList<LinkedHashMap<String,String>>>();
		
		ArrayList<LinkedHashMap<String, String>> list2 = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "");
		list2.add(map);
		
		for(int i = 0; i < 5; i++) {
			map = new LinkedHashMap<String, String>();
			map.put("DataType", "string");
			map.put("Value", "x");
			list2.add(map);
			
			map = new LinkedHashMap<String, String>();
			map.put("DataType", "string");
			map.put("Value", "y");
			list2.add(map);
		}
		
		list.add(list2);
		
		list2 = new ArrayList<LinkedHashMap<String, String>>();
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "region a");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", ".5");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "5");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "2");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "8");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "3");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "7");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "1.5");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "6.5");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "2.5");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "2.5");
		list2.add(map);
		
		list.add(list2);
		
		list2 = new ArrayList<LinkedHashMap<String, String>>();
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "string");
		map.put("Value", "region b");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "1");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "1");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "3");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "5");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "2");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "7");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "3.7");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "10.5");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "3.5");
		list2.add(map);
		
		map = new LinkedHashMap<String, String>();
		map.put("DataType", "number");
		map.put("Value", "2.5");
		list2.add(map);
		
		list.add(list2);
		
		setRequestAttribute("scatterChartData", list);
					-->
					</c:code>
				
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<XmlSwfChart chartId="chart2" list="scatterChartData" chartType="scatter" axisCategoryLabels="0g;1g;2g;3g;4g"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<cc:XmlSwfChart chartId="chart2" list="scatterChartData" chartType="scatter" axisCategoryLabels="0g;1g;2g;3g;4g"/>
					
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('axisValueLabels')" style="cursor:pointer">屬性axisValueLabels: 覆寫縱軸標題，用分號分隔</legend>
				<div id="axisValueLabels_sidebar" style="display:none">
					<h5>JAVA 程式碼</h5>
					
					<c:code>
					<!-- pseudo code
					ArrayList<ArrayList<LinkedHashMap<String, String>>> list = new ArrayList<ArrayList<LinkedHashMap<String,String>>>();
					
					ArrayList<LinkedHashMap<String, String>> list2 = new ArrayList<LinkedHashMap<String, String>>();
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("DataType", "string");
					map.put("Value", "");
					list2.add(map);
					
					map = new LinkedHashMap<String, String>();
					map.put("DataType", "string");
					map.put("Value", "2005");
					list2.add(map);
					
					map = new LinkedHashMap<String, String>();
					map.put("DataType", "string");
					map.put("Value", "2006");
					list2.add(map);
					
					list.add(list2);
					
					list2 = new ArrayList<LinkedHashMap<String, String>>();
					map = new LinkedHashMap<String, String>();
					map.put("DataType", "string");
					map.put("Value", "Region A");
					list2.add(map);
					
					map = new LinkedHashMap<String, String>();
					map.put("DataType", "number");
					map.put("Value", "10");
					list2.add(map);
					
					map = new LinkedHashMap<String, String>();
					map.put("DataType", "number");
					map.put("Value", "20");
					list2.add(map);
					
					list.add(list2);
					
					setRequestAttribute("chartData", list);
					-->
					</c:code>
				
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<XmlSwfChart chartId="chart3" list="chartData" axisValueLabels="0g;1g;2g;3g;4g"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<cc:XmlSwfChart chartId="chart3" list="chartData" axisValueLabels="0g;1g;2g;3g;4g"/>
					
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('chartAxisCategory')" style="cursor:pointer">屬性chartAxisCategory開頭: 橫軸標題設定</legend>
				<div id="chartAxisCategory_sidebar" style="display:none">
<font color="red">skip</font>: If this axis holds too many labels, the skip key allows skipping (hiding) some labels. A zero value doesn't hide any labels. If the skip value is 1, then the first label is displayed, the following label is skipped, and so on. If the skip value is 2, then the first label is displayed, the following 2 are skipped, and so on. Another way to hide labels is by passing empty strings to chart_data in place of category labels. The default is zero.<br>
<br>
<font color="red">font</font>: The font used in the category-axis (see Fonts). The default is Arial.<br>
<br>
<font color="red">bold</font>: A boolean that indicates whether the font is bold or not. The default is true.<br>
<br>
<font color="red">size</font>: The font's size. The default font size is calculated based on the chart size.<br>
<br>
<font color="red">color</font>: The font's color. This is a string holding triple hexadecimal values representing the red, green, and blue components of a color. The default is "000000" (black).<br>
<br>
<font color="red">alpha</font>: This affects the labels' transparency only when the embedded font is used (see Fonts). Valid values are 0 (fully transparent) to 100 (fully opaque). The default is 90. To hide all labels in this axis, set the alpha to 0.<br>
<br>
<font color="red">orientation</font>: This affects the labels' orientation only when the embedded font is used (see Fonts). Valid values are horizontal, diagonal_up, diagonal_down, vertical_up, and vertical_down. Polar charts also accept the value circular. The default value is horizontal.<br>
<br>
<br>
<br>
<font color="red">shadow</font>: The ID of a shadow filter to apply to the category labels. This is omitted by default (no shadow).<br>
<br>
<font color="red">bevel</font>: The ID of a bevel filter to apply to the category labels. This is omitted by default (no bevel).<br>
<br>
<font color="red">glow</font>: The ID of a glow filter to apply to the category labels. This is omitted by default (no glow).<br>
<br>
<font color="red">blur</font>: The ID of a blur filter to apply to the category labels. This is omitted by default (no blur).<br>
<br>
<br>
<br>
<font color="red">margin</font>: This applies to area, stacked area, and line charts only. It is a boolean that indicates whether to leave a margin on the left and right of the graph, or bump it against the left and right chart borders. The default is true (leave a margin). In mixed charts, there's always a margin to algin area and line charts with column charts.<br>
<br>
<br>
<br>
<font color="red">min</font>: This applies to scatter and bubble charts only, when the category-axis is calculated like the value-axis. This determines the minimum value to start this axis with. The default is calculated from the chart's data.<br>
<br>
<font color="red">max</font>: This applies to scatter and bubble charts only, when the category-axis is calculated like the value-axis. This determines the maximum value to end this axis with. The default is calculated from the chart's data.<br>
<br>
<font color="red">steps</font>: This applies to scatter and bubble charts only, when the category-axis is calculated like the value-axis. This determines the number of steps between the minimum and maximum values. If the minimum value is negative, and the maximum value is positive, then 'steps' is the number of steps between zero and the larger of max and absolute min. The default is 4.<br>
<br>
<font color="red">mode</font>: This applies to scatter and bubble charts only, when the category-axis is calculated like the value-axis. This determines the way the above min and max values are adjusted. Valid values are:<br>
<br>
    * all</font>: Min and max are adjusted to show all the chart values. This is the default value.<br>
<br>
    * trim</font>: Min and max are not adjusted, even if a chart value falls outside of this range and becomes partially or totally invisible.<br>
<br>
<font color="red">prefix</font>: This applies to scatter and bubble charts only, when the category-axis is calculated like the value-axis. This determines the characters to add before the labels (example</font>: $10). The default is nothing.<br>
<br>
<font color="red">suffix</font>: This applies to scatter and bubble charts only, when the category-axis is calculated like the value-axis. This determines the characters to add after the labels (example</font>: 10%). The default is nothing.<br>
<br>
<font color="red">decimals</font>: This applies to scatter and bubble charts only, when the category-axis is calculated like the value-axis. This determines the number of decimal places to the right of the decimal point (example</font>: 10.45). The default is zero (no decimals).<br>
<br>
<font color="red">decimal_char</font>: This applies to scatter and bubble charts only, when the category-axis is calculated like the value-axis. This determines the character to use at the left of a decimal fraction (example</font>: 1.5). The default is '.' (dot or full stop).<br>
<br>
<font color="red">separator</font>: This applies to scatter and bubble charts only, when the category-axis is calculated like the value-axis. This determines the character to place between every group of thousands (example</font>: 1,00,000). The default is nothing.
					
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<XmlSwfChart chartId="chart4" list="chartData" chartAxisCategorySkip="1" chartAxisCategorySize="10" chartAxisCategoryColor="FF0000" chartAxisCategoryAlpha="75" chartAxisCategoryOrientation="diagonal_down"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<cc:XmlSwfChart chartId="chart4" list="chartData" chartAxisCategorySkip="1" chartAxisCategorySize="10" chartAxisCategoryColor="FF0000" chartAxisCategoryAlpha="75" chartAxisCategoryOrientation="diagonal_down"/>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('chartAxisTicks')" style="cursor:pointer">屬性chartAxisTicks開頭: 座標軸格線</legend>
				<div id="chartAxisTicks_sidebar" style="display:none">
<font color="red">value_ticks</font>: A boolean that indicates whether the ticks on the value axis are visible or not. The default is false.<br>
<br>
<font color="red">category_ticks</font>: A boolean that indicates whether the ticks on the category axis are visible or not. The default is true.<br>
<br>
<font color="red">position</font>: A string that determines where on the axis to display the ticks. Valid values are outside, inside, and centered. The default is outside.<br>
<br>
<font color="red">major_thickness</font>: The thickness of major ticks. Major ticks are those that appear next to the axis labels. The default is 2 pixels.<br>
<br>
<font color="red">major_color</font>: The color of major ticks. This is a string holding triple hexadecimal values representing the red, green, and blue components of a color. The default is "000000" (black).<br>
<br>
<font color="red">minor_thickness</font>: The thickness of minor ticks. Minor ticks are those that appear between major ticks. The default is 1 pixel.<br>
<br>
<font color="red">minor_color</font>: The color of minor ticks. This is a string holding triple hexadecimal values representing the red, green, and blue components of a color. The default is "000000" (black).<br>
<br>
<font color="red">minor_count</font>: This applies to the value axis only. It sets the number of minor ticks between every 2 major ticks. The default is 4. The category axis displays minor ticks only in place of its skipped labels.
				
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<XmlSwfChart chartId="chart5" list="chartData" chartAxisTicksValueTicks="true" chartAxisTicksCategoryTicks="true" chartAxisTicksPosition="centered" chartAxisTicksMajorThickness="2" chartAxisTicksMajorColor="000000" chartAxisTicksMinorThickness="1" chartAxisTicksMinorColor="ff0000" chartAxisTicksMinorCount="3"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<cc:XmlSwfChart chartId="chart5" list="chartData" chartAxisTicksValueTicks="true" chartAxisTicksCategoryTicks="true" chartAxisTicksPosition="centered" chartAxisTicksMajorThickness="2" chartAxisTicksMajorColor="000000" chartAxisTicksMinorThickness="1" chartAxisTicksMinorColor="ff0000" chartAxisTicksMinorCount="3"/>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('chartAxisValue')" style="cursor:pointer">屬性chartAxisValue開頭: 縱軸標題設定</legend>
				<div id="chartAxisValue_sidebar" style="display:none">
<font color="red">min</font>: The minimum value to start the value-axis with. The default is zero in most charts.<br>
<br>
<font color="red">max</font>: The maximum value to end the value-axis with. The default is calculated from the chart's data.<br>
<br>
<font color="red">mode</font>: Determines the way the above min and max values are adjusted. Valid values are:<br>
<br>
    * all</font>: Min and max are adjusted to show all the chart values, including those outside of the visible scrolling area. This is the default value.<br>
<br>
    * stretch</font>: Min and max are repeatedly adjusted to show only the chart values within the visible scrolling area. This option is available to scrollable charts only.<br>
<br>
    * trim</font>: Min and max are not adjusted, even if a chart value falls outside of this range and becomes partially or totally invisible. This option is available to scrollable, scatter, and bubble charts only.<br>
<br>
<font color="red">steps</font>: The number of steps between the minimum and maximum values. If the minimum value is negative, and the maximum value is positive, then steps is the number of steps between zero and the larger of max and absolute min. The default is 4.<br>
<br>
<font color="red">prefix</font>: The characters to add before the value numbers (example</font>: $10). The default is nothing.<br>
<br>
<font color="red">suffix</font>: The characters to add after the value numbers (example</font>: 10%). The default is nothing.<br>
<br>
<font color="red">decimals</font>: The number of decimal places to the right of the decimal point (example</font>: 10.45). The default is zero (no decimals).<br>
<br>
<font color="red">decimal_char</font>: The character to use at the left of a decimal fraction (example</font>: 1.5). The default is '.' (dot or full stop).<br>
<br>
<font color="red">separator</font>: The character to place between every group of thousands (example</font>: 1,00,000). The default is nothing.<br>
<br>
<font color="red">show_min</font>: A boolean that indicates whether show or hide the first label in the value-axis. Hiding this first label might be necessary if it overlaps with the first label in the category axis. The default is true (show the first label).<br>
<br>
<font color="red">font</font>: The font used in the value-axis (see Fonts). The default is Arial.<br>
<br>
<font color="red">bold</font>: A boolean value that indicates whether the font is bold or not. The default is true.<br>
<br>
<font color="red">size</font>: The font's size. The default font size is calculated based on the chart size.<br>
<br>
<font color="red">color</font>: The font's color. This is a string holding triple hexadecimal values representing the red, green, and blue components of a color. The default is "000000" (black).<br>
<br>
<font color="red">background_color</font>: This applies to polar charts only. It determines the labels' background color to make them visible over the graphs. When omitted, axis_value labels have no background. This is a string holding triple hexadecimal values representing the red, green, and blue components of a color. The default is omitted (no background).<br>
<br>
<font color="red">alpha</font>: This affects the labels' transparency, only when the embedded font is used (see Fonts). Valid values are 0 (fully transparent) to 100 (fully opaque). The default is 90. To hide all labels in this axis, set the alpha to 0.<br>
<br>
<font color="red">orientation</font>: This affects the labels' orientation, only when the embedded font is used (see Fonts). Valid values are horizontal, diagonal_up, diagonal_down, vertical_up, and vertical_down. The default value is horizontal.<br>
<br>
<font color="red">shadow</font>: The ID of a shadow filter to apply to the value labels. This is omitted by default (no shadow).<br>
<br>
<font color="red">bevel</font>: The ID of a bevel filter to apply to the value labels. This is omitted by default (no bevel).<br>
<br>
<font color="red">glow</font>: The ID of a glow filter to apply to the value labels. This is omitted by default (no glow).<br>
<br>
<font color="red">blur</font>: The ID of a blur filter to apply to the value labels. This is omitted by default (no blur).
				
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<XmlSwfChart chartId="chart6" list="chartData" chartAxisValueMin="10" chartAxisValueMax="200" chartAxisValueSteps="5" chartAxisValuePrefix="$" chartAxisValueSuffix="" chartAxisValueDecimals="1" chartAxisValueDecimalChar="." chartAxisValueSeparator="" chartAxisValueShowMin="true" chartAxisValueFont="arial" chartAxisValueBold="true" chartAxisValueSize="12" chartAxisValueColor="FF0000" chartAxisValueAlpha="75" chartAxisValueOrientation="diagonal_up"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<cc:XmlSwfChart chartId="chart6" list="chartData" chartAxisValueMin="10" chartAxisValueMax="200" chartAxisValueSteps="5" chartAxisValuePrefix="$" chartAxisValueSuffix="" chartAxisValueDecimals="1" chartAxisValueDecimalChar="." chartAxisValueSeparator="" chartAxisValueShowMin="true" chartAxisValueFont="arial" chartAxisValueBold="true" chartAxisValueSize="12" chartAxisValueColor="FF0000" chartAxisValueAlpha="75" chartAxisValueOrientation="diagonal_up"/>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('chartGuide')" style="cursor:pointer">屬性chartGuide開頭: 輔助線設定</legend>
				<div id="chartGuide_sidebar" style="display:none">
<font color="red">horizontal</font>: A boolean that determines whether the horizontal guide is visible or not. Valid values are true or false. The default is false (no horizontal guide).<br>
<br>
<font color="red">vertical</font>: A boolean that determines whether the vertical guide is visible or not. Valid values are true or false. The default is false (no vertical guide).<br>
<br>
<font color="red">thickness</font>: The thickness of the guide lines. Valid values are 1 and above. The default is 1.<br>
<br>
<font color="red">color</font>: The guide's line color. This is a string holding triple hexadecimal values representing the red, green, and blue components of a color. The default is "000000" (black).<br>
<br>
<font color="red">alpha</font>: The guide's line transparency value. Valid values are 0 (fully transparent) to 100 (fully opaque). The default is 20.<br>
<br>
<font color="red">type</font>: The guide's line type. Valid values are solid, dotted, and dashed. The default is solid.<br>
<br>
<font color="red">snap_h</font>: A boolean that determines whether the horizontal guide snaps to data points or not. Valid values are true or false. The default is false (no snap).<br>
<br>
<font color="red">snap_v</font>: A boolean that determines whether the vertical guide snaps to data points or not. Valid values are true or false. The default is false (no snap).<br>
<br>
<font color="red">connect</font>: By default, guides appear when the cursor is inside chart_rect. This attribute is a boolean that determines whether the guides appear when the cursor is outside of chart_rect. This can be used to connect the guides of different charts in a composite chart. Valid values are true or false. The default is false (show guides only when the cursor is inside chart_rect).<br>
<br>
<br>
<br>
<font color="red">radius</font>: Guides can optionally have a circle that highlights data points when the cursor gets close to them. The radius attribute sets the radius of this circle in pixels. The default is 4.<br>
<br>
<font color="red">fill_color</font>: The color inside the data circle. This is a string holding triple hexadecimal values representing the red, green, and blue components of a color. The default is "000000" (black).<br>
<br>
<font color="red">fill_alpha</font>: The transparency value inside the data circle. Valid values are 0 (fully transparent) to 100 (fully opaque). The default is 0. To hide the circle, set both fill_alpha and line_alpha to zero.<br>
<br>
<font color="red">line_color</font>: The data circle's border color. This is a string holding triple hexadecimal values representing the red, green, and blue components of a color. The default is "000000" (black).<br>
<br>
<font color="red">line_alpha</font>: The data circle's border transparency value. Valid values are 0 (fully transparent) to 100 (fully opaque). The default is 0. To hide the circle, set both fill_alpha and line_alpha to zero.<br>
<br>
<font color="red">line_thickness</font>: The data circle's border thickness. The default is 0 pixels (no border).<br>
<br>
<br>
<br>
<font color="red">text_h_alpha</font>: Guides can optionally have labels that display the axes values. This determines the labels' transparency for the horizontal guide. Valid values are 0 (fully transparent) to 100 (fully opaque). The default is 50. To hide the label for the horizontal guide, set this attribute to zero.<br>
<br>
<font color="red">text_v_alpha</font>: Guides can optionally have labels that display the axes values. This determines the labels' transparency for the vertical guide. Valid values are 0 (fully transparent) to 100 (fully opaque). The default is 50. To hide the label for the vertical guide, set this attribute to zero.<br>
<br>
<font color="red">prefix_h</font>: The characters to add before the guide's horizontal label (example</font>: $10). The default is nothing.<br>
<br>
<font color="red">suffix_h</font>: The characters to add after the guide's horizontal label (example</font>: 10%). The default is nothing.<br>
<br>
<font color="red">prefix_v</font>: This characters to add before the guide's vertical label (example</font>: $10). The default is nothing.<br>
<br>
<font color="red">suffix_v</font>: The characters to add after the guide's vertical label (example</font>: 10%). The default is nothing.<br>
<br>
<font color="red">decimals</font>: The number of decimal places to the right of the decimal point in the guide's number label (example</font>: 10.45). It does not affect the string label. The default is zero (no decimals).<br>
<br>
<font color="red">decimal_char</font>: The character to use at the left of a decimal fraction in the guide's number label (example</font>: 10.5). It does not affect the string label. The default is '.' (dot or full stop).<br>
<br>
<font color="red">separator</font>: The character to place between every group of thousands in the guide's number label (example</font>: 1,00,000). It does not affect the string label. The default is nothing.<br>
<br>
<font color="red">font</font>: The font used in the guide's labels (see Fonts). The default is Arial.<br>
<br>
<font color="red">bold</font>: A boolean that indicates whether the font is bold or not in the guide's labels. The default is true.<br>
<br>
<font color="red">size</font>: The font's size in the guide's labels. The default is 12.<br>
<br>
<font color="red">text_color</font>: The font's color in the guide's labels. This is a string holding triple hexadecimal values representing the red, green, and blue components of a color. The default is "000000" (black).<br>
<br>
<font color="red">background_color</font>: This determines the labels' background color to make them visible over the graph. This is a string holding triple hexadecimal values representing the red, green, and blue components of a color. When omitted, guide labels have no background. The default is omitted (no background).
				
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<XmlSwfChart chartId="chart7" list="chartData" chartGuideHorizontal="true" chartGuideVertical="false" chartGuideThickness="1" chartGuideColor="ff4400" chartGuideAlpha="75" chartGuideType="dashed" chartGuideRadius="8" chartGuideFillAlpha="0" chartGuideLineColor="ff4400" chartGuideLineAlpha="75" chartGuideLineThickness="4" chartGuideSize="10" chartGuideTextColor="ffffff" chartGuideBackgroundColor="ff4400" chartGuideTextHAlpha="90" chartGuideTextVAlpha="90"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<cc:XmlSwfChart chartId="chart7" list="chartData" chartGuideHorizontal="true" chartGuideVertical="false" chartGuideThickness="1" chartGuideColor="ff4400" chartGuideAlpha="75" chartGuideType="dashed" chartGuideRadius="8" chartGuideFillAlpha="0" chartGuideLineColor="ff4400" chartGuideLineAlpha="75" chartGuideLineThickness="4" chartGuideSize="10" chartGuideTextColor="ffffff" chartGuideBackgroundColor="ff4400" chartGuideTextHAlpha="90" chartGuideTextVAlpha="90"/>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('chartHeight')" style="cursor:pointer">屬性chartHeight: chart高度設定</legend>
				<div id="chartHeight_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<XmlSwfChart chartId="chart38" list="chartData" chartHeight="500"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<cc:XmlSwfChart chartId="chart38" list="chartData" chartHeight="500"/>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('chartHorizontal')" style="cursor:pointer">屬性chartHorizontal開頭: 水平格線設定</legend>
				<div id="chartHorizontal_sidebar" style="display:none">
<font color="red">thickness</font>: The thickness of the horizontal grid lines. Valid values are zero and above. Zero makes the horizontal lines invisible. The default is 1.<br>
<br>
<font color="red">color</font>: The horizontal grid color. This is a string holding triple hexadecimal values representing the red, green, and blue components of a color. The default is "000000" (black).<br>
<br>
<font color="red">alpha</font>: The horizontal grid transparency value. Valid values are 0 (fully transparent) to 100 (fully opaque). The default is 20.<br>
<br>
<font color="red">type</font>: The horizontal grid line type. Valid values are solid, dotted, and dashed. The default is solid.
				
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<XmlSwfChart chartId="chart8" list="chartData" chartHorizontalThickness="2" chartHorizontalColor="FF0000" chartHorizontalAlpha="15" chartHorizontalType="dashed"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<cc:XmlSwfChart chartId="chart8" list="chartData" chartHorizontalThickness="2" chartHorizontalColor="FF0000" chartHorizontalAlpha="15" chartHorizontalType="dashed"/>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('chartLabel')" style="cursor:pointer">屬性chartLabel開頭: 資料標頭Global設定(可在每個資料覆寫設定)</legend>
				<div id="chartLabel_sidebar" style="display:none">
<font color="red">prefix</font>: The characters to add before numbers (example</font>: $10). The default is nothing.<br>
<br>
<font color="red">suffix</font>: The characters to add after numbers (example</font>: 10%). The default is nothing.<br>
<br>
<font color="red">decimals</font>: The number of decimal places to the right of the decimal point (example</font>: 10.45). The default is zero (no decimals).<br>
<br>
<font color="red">decimal_char</font>: The character to use at the left of a decimal fraction (example</font>: 1.5). The default is '.' (dot or full stop).<br>
<br>
<font color="red">separator</font>: The character to place between every group of thousands (example</font>: 1,00,000). The default is nothing.<br>
<br>
<font color="red">position</font>: The position where to place the labels. Each chart type has a different set of position values (see Label Position below).<br>
<br>
<font color="red">hide_zero</font>: This determines whether to hide value labels equal to zero. The default is false (shows zero labels).<br>
<br>
<font color="red">as_percentage</font>: This is relevant in pie charts only. It determines whether to display the values as raw data, or as percentages of the whole pie. The default is false.<br>
<br>
<font color="red">font</font>: The font used for the chart labels (see Fonts). The default is Arial.<br>
<br>
<font color="red">bold</font>: A boolean that indicates whether the font is bold or not. The default is true.<br>
<br>
<font color="red">size</font>: The font's size. The default font size is calculated based on the background size.<br>
<br>
<font color="red">color</font>: The font's color. This is a string holding triple hexadecimal values representing the red, green, and blue components of a color. The default is "000000" (black).<br>
<br>
<font color="red">alpha</font>: This affects the labels' transparency only when the embedded font is used (see Fonts). Valid values are 0 (fully transparent) to 100 (fully opaque). The default is 100.<br>
<br>
<font color="red">shadow</font>: The ID of a shadow filter to apply to chart labels. This is omitted by default (no shadow).<br>
<br>
<font color="red">bevel</font>: The ID of a bevel filter to apply to chart labels. This is omitted by default (no bevel).<br>
<br>
<font color="red">glow</font>: The ID of a glow filter to apply to chart labels. This is omitted by default (no glow).<br>
<br>
<font color="red">blur</font>: The ID of a blur filter to apply to chart labels. This is omitted by default (no blur).<br>

				
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<XmlSwfChart chartId="chart9" list="chartData" chartLabelPrefix="" chartLabelSuffix="m" chartLabelDecimals="1" chartLabelDecimalChar="." chartLabelSeparator="" chartLabelPosition="middle" chartLabelHideZero="false" chartLabelFont="arial" chartLabelBold="true" chartLabelSize="10" chartLabelColor="FFFFFF" chartLabelAlpha="90"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<cc:XmlSwfChart chartId="chart9" list="chartData" chartLabelPrefix="" chartLabelSuffix="m" chartLabelDecimals="1" chartLabelDecimalChar="." chartLabelSeparator="" chartLabelPosition="middle" chartLabelHideZero="false" chartLabelFont="arial" chartLabelBold="true" chartLabelSize="10" chartLabelColor="FFFFFF" chartLabelAlpha="90"/>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('chartLegend')" style="cursor:pointer">屬性chartLegend開頭: 圖表標頭設定</legend>
				<div id="chartLegend_sidebar" style="display:none">
<font color="red">transition</font>: The transition effect used to show the legend. Valid values are dissolve, drop, spin, scale, zoom, blink, slide_right, slide_left, slide_up, slide_down, and none. The default is none, which draws the legend immediately without a transition.<br>
<br>
<font color="red">delay</font>: The delay in seconds before starting the transition. The default is zero.<br>
<br>
<font color="red">duration</font>: The transition's duration in seconds. The default is 1.<br>
<br>
<font color="red">x</font>: The horizontal position of the legend's upper left corner relative to the upper left corner of the background (0, 0).<br>
<br>
<font color="red">y</font>: The vertical position of the legend's upper left corner relative to the upper left corner of the background (0, 0).<br>
<br>
<font color="red">width</font>: The legend's width. The default width is calculated to fit within the background. If the legend's rectangle is too small for its content, then it expands as necessary.<br>
<br>
<font color="red">height</font>: The legend's height. The default width is calculated to fit within the background. If the legend's rectangle is too small for its content, then it expands as necessary.<br>
<br>
<font color="red">toggle</font>: A boolean that determines whether to enable clicking on legend labels to turn on or off series graphics. Valid values are true or false. The default is true (enable this feature).<br>
<br>
<font color="red">layout</font>: This indicates if the legend is visible, and whether it is horizontal or vertical. Valid values are horizontal, vertical, and hide. The default is vertical for pie charts, and horizontal for all other charts.<br>
<br>
<font color="red">margin</font>: The space between the legend's edge and its content. The default is 5.<br>
<br>
<font color="red">bullet</font>: Determines the bullet shape. Valid values are square, circle, and line. The default is square.<br>
<br>
<font color="red">font</font>: The font used in the legend's labels (see Fonts). The default is Arial.<br>
<br>
<font color="red">bold</font>: A boolean that indicates whether the font is bold or not. Valid values are true or false. The default is true.<br>
<br>
<font color="red">size</font>: The font's size. This also affects the size of the legend's color squares. The default font size is calculated based on the background size.<br>
<br>
<font color="red">color</font>: The font's color. This is a string holding triple hexadecimal values representing the red, green, and blue components of a color. The default is "000000" (black).<br>
<br>
<font color="red">alpha</font>: This affects the labels' transparency, only when the embedded font is used (see Fonts). Valid values are 0 (fully transparent) to 100 (fully opaque). The default is 90.<br>
<br>
<font color="red">fill_color</font>: The legend's background color. This is a string holding triple hexadecimal values representing the red, green, and blue components of a color. The default is "FFFFFF" (white).<br>
<br>
<font color="red">fill_alpha</font>: The background's transparency. Valid values are 0 (fully transparent) to 100 (fully opaque). The default is 30.<br>
<br>
<font color="red">line_color</font>: The border's color. This is a string holding triple hexadecimal values representing the red, green, and blue components of a color. The default is "000000" (black).<br>
<br>
<font color="red">line_alpha</font>: The border's transparency value. Valid values are 0 (fully transparent) to 100 (fully opaque). The default is 0.<br>
<br>
<font color="red">line_thickness</font>: The border's line thickness. Valid values are 0 and above. The default is 1.<br>
<br>
<font color="red">shadow</font>: The ID of a shadow filter to apply to the legend. This is omitted by default (no shadow).<br>
<br>
<font color="red">bevel</font>: The ID of a bevel filter to apply to the legend. This is omitted by default (no bevel).<br>
<br>
<font color="red">glow</font>: The ID of a glow filter to apply to the legend. This is omitted by default (no glow).<br>
<br>
<font color="red">blur</font>: The ID of a blur filter to apply to the legend. This is omitted by default (no blur).

				
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<XmlSwfChart chartId="chart10" list="chartData" chartLegendTransition="slide_left" chartLegendDelay="2" chartLegendDuration="1"/>
					<XmlSwfChart chartId="chart11" list="chartData" chartLegendLayout="vertical" chartLegendWidth="100" chartLegendBullet="circle" chartLegendFont="arial" chartLegendBold="true" chartLegendSize="12" chartLegendColor="FF0000" chartLegendAlpha="90"/><br>
					<XmlSwfChart chartId="chart12" list="chartData" chartLegendX="5" chartLegendY="5" chartLegendWidth="390" chartLegendHeight="10" chartLegendMargin="5" chartLegendFillColor="FFFFFF" chartLegendFillAlpha="100" chartLegendLineColor="FF4400" chartLegendLineAlpha="100" chartLegendLineThickness="2"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<cc:XmlSwfChart chartId="chart10" list="chartData" chartLegendTransition="slide_left" chartLegendDelay="2" chartLegendDuration="1"/>
					<cc:XmlSwfChart chartId="chart11" list="chartData" chartLegendLayout="vertical" chartLegendWidth="100" chartLegendBullet="circle" chartLegendFont="arial" chartLegendBold="true" chartLegendSize="12" chartLegendColor="FF0000" chartLegendAlpha="90"/><br>
					<cc:XmlSwfChart chartId="chart12" list="chartData" chartLegendX="5" chartLegendY="5" chartLegendWidth="390" chartLegendHeight="10" chartLegendMargin="5" chartLegendFillColor="FFFFFF" chartLegendFillAlpha="100" chartLegendLineColor="FF4400" chartLegendLineAlpha="100" chartLegendLineThickness="2"/>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('chartNote')" style="cursor:pointer">屬性chartNote開頭: 資料註解Global設定(可在每個資料覆寫設定)</legend>
				<div id="chartNote_sidebar" style="display:none">
<font color="red">type</font>: The general look of notes. Valid values are:<br>
<br>
    * flag (default)<br>
    * balloon<br>
    * arrow<br>
    * bullet<br>
    * lance <br>
<br>
<br>
<br>
<font color="red">x</font>: The horizontal position of notes relative to the points they are attached to. A zero value centers the notes horizontally relative to their points. A positive value moves them right. A negative value moves them left. The default is 15. See Note Position below.<br>
<br>
<font color="red">y</font>: The vertical position of notes relative to the points they are attached to. A zero value centers the notes vertically relative to their points. A positive value moves them down. A negative value moves them up. The default is -30. See Note Position below.<br>
<br>
<font color="red">offset_x</font>: The horizontal distance to move the whole notes (including their origin) to avoid possible overlap with labels. A positive value moves them right. A negative value moves them left. The default is zero. See Note Position below.<br>
<br>
<font color="red">offset_y</font>: The vertical distance to move the whole notes (including their origin) to avoid possible overlap with labels. A positive value moves them down. A negative value moves them up. The default is zero. See Note Position below.<br>
<br>
<font color="red">font</font>: The font used in notes. The default is Arial. See Fonts.<br>
<br>
<font color="red">bold</font>: A boolean that indicates whether the font is bold or not. The default is true.<br>
<br>
<font color="red">size</font>: The font size. The default is 12.<br>
<br>
<font color="red">color</font>: The text color. This is a string holding triple hexadecimal values representing the red, green, and blue components of a color. The default is "FFFFFF" (white).<br>
<br>
<font color="red">alpha</font>: The text transparency value. This affects the transparency only when the embedded font is used (see Fonts). Valid values are 0 (fully transparent) to 100 (fully opaque). The default is 100.<br>
<br>
<font color="red">background_color</font>: The notes' background color. This is a string holding triple hexadecimal values representing the red, green, and blue components of a color. The default is "000000" (black).<br>
<br>
<font color="red">background_alpha</font>: The background transparency value. Valid values are 0 (fully transparent) to 100 (fully opaque). The default is 50.<br>
<br>
<font color="red">shadow</font>: The ID of a shadow filter to apply to notes. This is omitted by default (no shadow).<br>
<br>
<font color="red">bevel</font>: The ID of a bevel filter to apply to notes. This is omitted by default (no bevel).<br>
<br>
<font color="red">glow</font>: The ID of a glow filter to apply to notes. This is omitted by default (no glow).<br>
<br>
<font color="red">blur</font>: The ID of a blur filter to apply to notes. This is omitted by default (no blur).

				
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<XmlSwfChart chartId="chart13" list="chartDataWithNote" chartNoteType="lance" chartNoteSize="11" chartNoteColor="000000" chartNoteAlpha="90" chartNoteX="0" chartNoteY="-15" chartNoteOffsetY="-5" chartNoteBackgroundColor="ff4400" chartNoteBackgroundAlpha="75" chartNoteShadow="low"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<cc:XmlSwfChart chartId="chart13" list="chartDataWithNote" chartNoteType="lance" chartNoteSize="11" chartNoteColor="000000" chartNoteAlpha="90" chartNoteX="0" chartNoteY="-15" chartNoteOffsetY="-5" chartNoteBackgroundColor="ff4400" chartNoteBackgroundAlpha="75" chartNoteShadow="low"/>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('chartSeries')" style="cursor:pointer">屬性chartSeries開頭: 分項的設定</legend>
				<div id="chartSeries_sidebar" style="display:none">
<font color="red">bar_gap</font>: The gap between columns or bars in the same set. Valid values are from 0 to 100. Standard column and bar charts (not 3d) support negative bar_gap values, which makes the columns or bars overlap. The default is 0. This applies to column and bar charts only.<br>
<br>
<font color="red">set_gap</font>: The gap between sets of columns or bars. Valid values are from 0 to 100. The default is 20. This applies to column and bar charts only.<br>
<br>
<font color="red">transfer</font>: A boolean that transfers the series colors to categories. This is used if the chart has only one series (one row of data) and you want each category value to have different color. This doesn not apply to line, area, scatter, and mixed charts. The default value is false.<br>
				
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<XmlSwfChart chartId="chart14" list="chartData" chartSeriesBarGap="-50" chartSeriesSetGap="50"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<cc:XmlSwfChart chartId="chart14" list="chartData" chartSeriesBarGap="-50" chartSeriesSetGap="50"/>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('chartTooltip')" style="cursor:pointer">屬性chartTooltip開頭: 資料提示Global設定(可在每個資料覆寫設定)</legend>
				<div id="chartTooltip_sidebar" style="display:none">
<font color="red">font</font>: The font applied to the tooltip's text (see Fonts). The default is Arial.<br>
<br>
<font color="red">bold</font>: A boolean that indicates whether the font is bold or not. The default is true.<br>
<br>
<font color="red">size</font>: The text size. The default is 12.<br>
<br>
<font color="red">color</font>: The text color. This is a string holding triple hexadecimal values representing the red, green, and blue components of a color. The default is "FFFFFF" (white).<br>
<br>
<font color="red">alpha</font>: This determines the text's transparency only when the embedded font is used (see Fonts). Valid values are 0 (fully transparent) to 100 (fully opaque). The default is 100.<br>
<br>
<font color="red">background_color</font>: The tooltip's background color. This is a string holding triple hexadecimal values representing the red, green, and blue components of a color. The default is "000000" (black).<br>
<br>
<font color="red">background_alpha</font>: The tooltip's background transparency. Valid values are 0 (fully transparent) to 100 (fully opaque). The default is 50.<br>
<br>
<font color="red">duration</font>: The tooltip disappears when the mouse stops moving. This attribute determines the number of seconds to wait before removing it. The default is 5.<br>
<br>
<font color="red">shadow</font>: The ID of a shadow filter to apply to the tooltip. This is omitted by default (no shadow).<br>
<br>
<font color="red">bevel</font>: The ID of a bevel filter to apply to the tooltip. This is omitted by default (no bevel).<br>
<br>
<font color="red">glow</font>: The ID of a glow filter to apply to the tooltip. This is omitted by default (no glow).<br>
<br>
<font color="red">blur</font>: The ID of a blur filter to apply to the tooltip. This is omitted by default (no blur).
				
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<XmlSwfChart chartId="chart15" list="chartDataWithTooltip" chartTooltipColor="FFFFFF" chartTooltipAlpha="75" chartTooltipSize="11" chartTooltipBackgroundColor="8888ff" chartTooltipBackgroundAlpha="90" chartTooltipShadow="low"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<cc:XmlSwfChart chartId="chart15" list="chartDataWithTooltip" chartTooltipColor="FFFFFF" chartTooltipAlpha="75" chartTooltipSize="11" chartTooltipBackgroundColor="8888ff" chartTooltipBackgroundAlpha="90" chartTooltipShadow="low"/>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('chartTransition')" style="cursor:pointer">屬性chartTransition開頭: 動畫載入效果設定</legend>
				<div id="chartTransition_sidebar" style="display:none">
<font color="red">type</font>: The type of the transition. Valid values are dissolve, drop, spin, scale, zoom, blink, slide_right, slide_left, slide_up, slide_down, and none. The default is none, which draws the chart immediately without a transition.<br>
<br>
<font color="red">delay</font>: The delay in seconds before starting the transition. The default is zero.<br>
<br>
<font color="red">duration</font>: The transition's duration in seconds. The default is 1.<br>
<br>
<font color="red">order</font>: The order in which to transition the chart's parts. Valid values are series, category, and all. The default is all.
				
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<XmlSwfChart chartId="chart16" list="chartData" chartTransitionType="drop" chartTransitionDelay="1" chartTransitionDuration="2" chartTransitionOrder="series"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<cc:XmlSwfChart chartId="chart16" list="chartData" chartTransitionType="drop" chartTransitionDelay="1" chartTransitionDuration="2" chartTransitionOrder="series"/>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('chartType')" style="cursor:pointer">屬性chartType: 指定圖表的類型</legend>
				<div id="chartType_sidebar" style="display:none">
					<h5>有以下這些類型:</h5>
    * line<br>
    * column (default)<br>
    * stacked column<br>
    * floating column<br>
    * 3d column<br>
    * image column<br>
    * stacked 3d column<br>
    * parallel 3d column<br>
    * pie<br>
    * 3d pie<br>
    * image pie<br>
    * donut<br>
    * bar<br>
    * stacked bar<br>
    * floating bar<br>
    * area<br>
    * stacked area<br>
    * 3d area<br>
    * stacked 3d area<br>
    * candlestick<br>
    * scatter<br>
    * polar<br>
    * bubble<br>
				
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<XmlSwfChart chartId="chart17" list="chartData" chartType="line" />
					<XmlSwfChart chartId="chart18" list="chartData" chartType="stacked column" /><br>
					<XmlSwfChart chartId="chart19" list="floatingChartData" chartType="floating column" />
					<XmlSwfChart chartId="chart20" list="chartData" chartType="3d column" /><br>
					<XmlSwfChart chartId="chart21" list="chartData" chartType="stacked 3d column" />
					<XmlSwfChart chartId="chart22" list="chartData" chartType="parallel 3d column" /><br>
					<XmlSwfChart chartId="chart23" list="pieChartData" chartType="pie" />
					<XmlSwfChart chartId="chart24" list="pieChartData" chartType="3d pie" /><br>
					<XmlSwfChart chartId="chart25" list="chartData" chartType="donut" />
					<XmlSwfChart chartId="chart26" list="chartData" chartType="bar" /><br>
					<XmlSwfChart chartId="chart27" list="chartData" chartType="stacked bar" />
					<XmlSwfChart chartId="chart28" list="floatingChartData" chartType="floating bar" /><br>
					<XmlSwfChart chartId="chart29" list="chartData" chartType="area" />
					<XmlSwfChart chartId="chart30" list="chartData" chartType="stacked area" /><br>
					<XmlSwfChart chartId="chart31" list="chartData" chartType="3d area" />
					<XmlSwfChart chartId="chart32" list="chartData" chartType="stacked 3d area" /><br>
					<XmlSwfChart chartId="chart33" list="candlestickChartData" chartType="candlestick" />
					<XmlSwfChart chartId="chart34" list="scatterChartData" chartType="scatter" /><br>
					<XmlSwfChart chartId="chart35" list="chartData" chartType="polar" />
					<XmlSwfChart chartId="chart36" list="bubbleChartData" chartType="bubble" /><br>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<cc:XmlSwfChart chartId="chart17" list="chartData" chartType="line" />
					<cc:XmlSwfChart chartId="chart18" list="chartData" chartType="stacked column" /><br>
					<cc:XmlSwfChart chartId="chart19" list="floatingChartData" chartType="floating column" />
					<cc:XmlSwfChart chartId="chart20" list="chartData" chartType="3d column" /><br>
					<cc:XmlSwfChart chartId="chart21" list="chartData" chartType="stacked 3d column" />
					<cc:XmlSwfChart chartId="chart22" list="chartData" chartType="parallel 3d column" /><br>
					<cc:XmlSwfChart chartId="chart23" list="pieChartData" chartType="pie" />
					<cc:XmlSwfChart chartId="chart24" list="pieChartData" chartType="3d pie" /><br>
					<cc:XmlSwfChart chartId="chart25" list="chartData" chartType="donut" />
					<cc:XmlSwfChart chartId="chart26" list="chartData" chartType="bar" /><br>
					<cc:XmlSwfChart chartId="chart27" list="chartData" chartType="stacked bar" />
					<cc:XmlSwfChart chartId="chart28" list="floatingChartData" chartType="floating bar" /><br>
					<cc:XmlSwfChart chartId="chart29" list="chartData" chartType="area" />
					<cc:XmlSwfChart chartId="chart30" list="chartData" chartType="stacked area" /><br>
					<cc:XmlSwfChart chartId="chart31" list="chartData" chartType="3d area" />
					<cc:XmlSwfChart chartId="chart32" list="chartData" chartType="stacked 3d area" /><br>
					<cc:XmlSwfChart chartId="chart33" list="candlestickChartData" chartType="candlestick" />
					<cc:XmlSwfChart chartId="chart34" list="scatterChartData" chartType="scatter" /><br>
					<cc:XmlSwfChart chartId="chart35" list="chartData" chartType="polar" />
					<cc:XmlSwfChart chartId="chart36" list="bubbleChartData" chartType="bubble" /><br>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('chartVertical')" style="cursor:pointer">屬性chartVertical開頭: 垂直格線設定</legend>
				<div id="chartVertical_sidebar" style="display:none">
<font color="red">thickness</font>: The thickness of the vertical grid lines. Valid values are zero and above. Zero makes the vertical lines invisible. The default is zero.<br>
<br>
<font color="red">color</font>: The vertical grid color. This is a string holding triple hexadecimal values representing the red, green, and blue components of a color. The default is "000000" (black).<br>
<br>
<font color="red">alpha</font>: The vertical grid transparency value. Valid values are 0 (fully transparent) to 100 (fully opaque). The default is 20.<br>
<br>
<font color="red">type</font>: The vertical grid line type. Valid values are solid, dotted, and dashed. The default is solid.
				
					<hr>
					
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<XmlSwfChart chartId="chart37" list="chartData" chartVerticalThickness="2" chartVerticalColor="FF0000" chartVerticalAlpha="15" chartVerticalType="dashed"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<cc:XmlSwfChart chartId="chart37" list="chartData" chartVerticalThickness="2" chartVerticalColor="FF0000" chartVerticalAlpha="15" chartVerticalType="dashed"/>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('chartWidth')" style="cursor:pointer">屬性chartWidth: chart寬度設定</legend>
				<div id="chartWidth_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<XmlSwfChart chartId="chart39" list="chartData" chartWidth="500"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<cc:XmlSwfChart chartId="chart39" list="chartData" chartWidth="500"/>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('rebuildXMLFlag')">屬性rebuildXMLFlag: 每次request都重新製作XML檔案，預設是"false"</legend>
				<div id="rebuildXMLFlag_sidebar" style="display:none">
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('seriesColors')" style="cursor:pointer">屬性seriesColors: 分項顏色設定；用分號分隔</legend>
				<div id="seriesColors_sidebar" style="display:none">
					<h5>JSP 程式碼:</h5>
					
					<c:code>
					<XmlSwfChart chartId="chart40" list="chartData" seriesColors="ff0000;00ff00;0000ff"/>
					</c:code>
					
					<hr>
					
					<h5>頁面結果:</h5>
					
					<cc:XmlSwfChart chartId="chart40" list="chartData" seriesColors="ff0000;00ff00;0000ff"/>
				</div>
			</fieldset>
			
			<fieldset style="background:lightblue">
				<legend onClick="openSidebarFolder('xmlPath')">屬性xmlPath: 指定存放XML的路徑</legend>
				<div id="xmlPath_sidebar" style="display:none">
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

