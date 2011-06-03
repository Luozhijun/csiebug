package csiebug.web.html.chart;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import javax.naming.NamingException;

import csiebug.util.AssertUtility;
import csiebug.util.StringUtility;
import csiebug.util.WebUtility;
import csiebug.web.html.HtmlComponent;
import csiebug.web.html.HtmlRenderException;
import csiebug.web.html.chart.xmlSwfBean.XmlSwfChartAxisCategory;
import csiebug.web.html.chart.xmlSwfBean.XmlSwfChartAxisTicks;
import csiebug.web.html.chart.xmlSwfBean.XmlSwfChartAxisValue;
import csiebug.web.html.chart.xmlSwfBean.XmlSwfChartGuide;
import csiebug.web.html.chart.xmlSwfBean.XmlSwfChartHorizontal;
import csiebug.web.html.chart.xmlSwfBean.XmlSwfChartLabel;
import csiebug.web.html.chart.xmlSwfBean.XmlSwfChartLegend;
import csiebug.web.html.chart.xmlSwfBean.XmlSwfChartNote;
import csiebug.web.html.chart.xmlSwfBean.XmlSwfChartSeries;
import csiebug.web.html.chart.xmlSwfBean.XmlSwfChartTooltip;
import csiebug.web.html.chart.xmlSwfBean.XmlSwfChartTransition;
import csiebug.web.html.chart.xmlSwfBean.XmlSwfChartVertical;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;


/**
 * 產生HTML XML/SWF Chart
 * @author George_Tsai
 * @version 2009/7/2
 */

public class HtmlXmlSwfChart extends HtmlComponent {
	private boolean rebuildXMLFlag;
	private String chartId;
	private String chartType;
	private Integer chartHeight;
	private Integer chartWidth;
	private String xmlPath;
	private ArrayList<ArrayList<Map<String, String>>> chartData;
	
	//label global設定
	private XmlSwfChartLabel chartLabel;
	
	//note global設定
	private XmlSwfChartNote chartNote;
	
	//tooltip global設定
	private XmlSwfChartTooltip chartTooltip;
	
	//水平格線設定
	private XmlSwfChartHorizontal chartHorizontal;
	
	//垂直格線設定
	private XmlSwfChartVertical chartVertical;
	
	//輔助線設定
	private XmlSwfChartGuide chartGuide;
	
	//載入特效設定
	private XmlSwfChartTransition chartTransition;
	
	//series設定
	private XmlSwfChartSeries chartSeries;
	private String seriesColors;
	
	//坐標軸(橫軸)設定
	private XmlSwfChartAxisCategory chartAxisCategory;
	private String axisCategoryLabels;
	
	//坐標軸(縱軸)設定
	private XmlSwfChartAxisTicks chartAxisTicks;
	private XmlSwfChartAxisValue chartAxisValue;
	private String axisValueLabels;
	
	//Chart Legend 設定
	private XmlSwfChartLegend chartLegend;
	
	private WebUtility webutil = new WebUtility();
	private String sourceXMLFileName;
	FilenameFilter filter;
	
	/**
	 * XML/SWF Chart建構子
	 * @author George_Tsai
	 * @version 2009/7/2
	 */
	public HtmlXmlSwfChart(boolean rebuildXML, String id, String type, Integer height, Integer width, String path, ArrayList<ArrayList<Map<String, String>>> list, XmlSwfChartLabel chartLabel, XmlSwfChartNote chartNote, XmlSwfChartTooltip chartTooltip, XmlSwfChartHorizontal chartHorizontal, XmlSwfChartVertical chartVertical, XmlSwfChartGuide chartGuide, XmlSwfChartTransition chartTransition, XmlSwfChartSeries chartSeries, String seriesColors, XmlSwfChartAxisCategory chartAxisCategory, String axisCategoryLabels, XmlSwfChartAxisTicks chartAxisTicks, XmlSwfChartAxisValue chartAxisValue, String axisValueLabels, XmlSwfChartLegend chartLegend) {
		this.rebuildXMLFlag = rebuildXML;
		this.chartId = id;
		this.chartType = type;
		this.chartHeight = height;
		this.chartWidth = width;
		this.xmlPath = StringUtility.removeStartEndSlash(path);
		this.chartData = list;
		this.chartLabel = chartLabel;
		this.chartNote = chartNote;
		this.chartTooltip = chartTooltip;
		this.chartHorizontal = chartHorizontal;
		this.chartVertical = chartVertical;
		this.chartGuide = chartGuide;
		this.chartTransition = chartTransition;
		this.chartSeries = chartSeries;
		this.seriesColors = seriesColors;
		this.chartAxisCategory = chartAxisCategory;
		this.axisCategoryLabels = axisCategoryLabels;
		this.chartAxisTicks = chartAxisTicks;
		this.chartAxisValue = chartAxisValue;
		this.axisValueLabels = axisValueLabels;
		this.chartLegend = chartLegend;
		
		//用登入者和session id就可以判斷檔案的有效期限
		sourceXMLFileName = chartId + "-" + webutil.getLoginUserId() + "-" + webutil.getSession().getId() + ".xml";
		
		//FilenameFilter
		filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				try {
					WebUtility webutil = new WebUtility();
					File currFile = new File(dir, name);
					if (currFile.isFile() && name.indexOf(chartId + "-" + webutil.getLoginUserId() + "-") != -1) {
						return (name.indexOf(sourceXMLFileName) == -1);
					} else {
				    	return false;
				    }
				} catch(Exception ex) {
	        		webutil.writeErrorLog(ex);
					return false;
				}
			}
		};
	}
	
	/**
	 * 刪除過期的XML檔案
	 * @throws Exception
	 */
	private void deleteExpireXMLFile() {
		File xmlDir = getXmlDirInstance();
		
		if(xmlDir.exists() && xmlDir.isDirectory()) {
			File[] xmlFiles = xmlDir.listFiles(filter);
			
			for(int i = xmlFiles.length - 1; i >= 0; i--) {
				if(!xmlFiles[i].delete()) {
					webutil.getLogger(this.getClass().getName()).warn(xmlFiles[i].getName() + " was deleted failed!");
				}
			}
		}
	}
	
	private File getXmlDirInstance() {
		File xmlDir;
		
		if(xmlPath != null) {
			xmlDir = new File(webutil.getServletContext().getRealPath("/") + "/" + xmlPath);
		} else {
			xmlDir = new File(webutil.getServletContext().getRealPath("/") + "/charts/tempXML");
		}
		
		return xmlDir;
	}
	
	/**
	 * 製作本次所需要的XML資料檔案
	 * @throws IOException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalArgumentException 
	 * @throws Exception
	 */
	private void makeXMLFile() throws IOException, IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
		File xmlFile;
		if(xmlPath != null) {
			xmlFile = new File(webutil.getServletContext().getRealPath("/") + "/" + xmlPath + "/" + sourceXMLFileName);
		} else {
			xmlFile = new File(webutil.getServletContext().getRealPath("/") + "/charts/tempXML/" + sourceXMLFileName);
		}
		
		if(!xmlFile.exists() || rebuildXMLFlag) {
			Document document = DocumentHelper.createDocument();
			Element rootElement = document.addElement("chart");
			
			if(chartType != null) {
				addChartType(rootElement);
			}
			
			if(chartLabel != null) {
				addChartLabel(rootElement);
			}
			
			if(chartNote != null) {
				addChartNote(rootElement);
			}
			
			if(chartTooltip != null) {
				addChartTooltip(rootElement);
			}
			
			if(chartHorizontal != null) {
				addChartHorizontal(rootElement);
			}
			
			if(chartVertical != null) {
				addChartVertical(rootElement);
			}
			
			if(chartGuide != null) {
				addChartGuide(rootElement);
			}
			
			if(chartTransition != null) {
				addChartTransition(rootElement);
			}
			
			if(chartSeries != null) {
				addChartSeries(rootElement);
			}
			
			if(AssertUtility.isNotNullAndNotSpace(seriesColors)) {
				addSeriesColors(rootElement);
			}
			
			if(chartAxisCategory != null) {
				addChartAxisCategory(rootElement);
			}
			
			if(AssertUtility.isNotNullAndNotSpace(axisCategoryLabels)) {
				addAxisCategoryLabels(rootElement);
			}
			
			if(chartAxisTicks != null) {
				addChartAxisTicks(rootElement);
			}
			
			if(chartAxisValue != null) {
				addChartAxisValue(rootElement);
			}
			
			if(AssertUtility.isNotNullAndNotSpace(axisValueLabels)) {
				addAxisValueLabels(rootElement);
			}
			
			if(chartLegend != null) {
				addChartLegend(rootElement);
			}
			
			addChartData(rootElement);
			
			OutputFormat format = OutputFormat.createPrettyPrint();
			XMLWriter writer = new XMLWriter(new FileWriter(xmlFile), format);
			
			writer.write(document);
			writer.close();
		}
	}
	
	private void addChartType(Element rootElement) {
		AssertUtility.notNull(rootElement);
		
		Element chartTypeElement = rootElement.addElement("chart_type");
		chartTypeElement.addText(chartType);
	}
	
	private void addNodeAttribute(Element element, Object targetObject) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		AssertUtility.notNull(element);
		AssertUtility.notNull(targetObject);
		
		Class<?> c = Class.forName(targetObject.getClass().getName());
		
		for(int i = 0; i < c.getMethods().length; i++) {
        	Method method = c.getMethods()[i];
        	
        	if(method.getName().startsWith("get") && !method.getName().equalsIgnoreCase("getClass")) {
        		String key = method.getName().replaceFirst("get", "");
	        	key = StringUtility.camelNamingtoUderlineNaming(key);
	        	
	        	Object obj = method.invoke(targetObject, (Object[])null);
	        	if(obj != null) {
	        		element.addAttribute(key, obj.toString());
	        	}
        	}
		}
	}
	
	private void addChartLabel(Element rootElement) throws IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
		AssertUtility.notNull(rootElement);
		
		Element chartLabelElement = rootElement.addElement("chart_label");
		
		addNodeAttribute(chartLabelElement, chartLabel);
	}
	
	private void addChartNote(Element rootElement) throws IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
		AssertUtility.notNull(rootElement);
		
		Element chartNoteElement = rootElement.addElement("chart_note");
		
		addNodeAttribute(chartNoteElement, chartNote);
	}
	
	private void addChartTooltip(Element rootElement) throws IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
		AssertUtility.notNull(rootElement);
		
		Element chartTooltipElement = rootElement.addElement("tooltip");
		
		addNodeAttribute(chartTooltipElement, chartTooltip);
	}
	
	private void addChartHorizontal(Element rootElement) throws IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
		AssertUtility.notNull(rootElement);
		
		Element chartHorizontalElement = rootElement.addElement("chart_grid_h");
		
		addNodeAttribute(chartHorizontalElement, chartHorizontal);
	}
	
	private void addChartVertical(Element rootElement) throws IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
		AssertUtility.notNull(rootElement);
		
		Element chartVerticalElement = rootElement.addElement("chart_grid_v");
		
		addNodeAttribute(chartVerticalElement, chartVertical);
	}
	
	private void addChartGuide(Element rootElement) throws IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
		AssertUtility.notNull(rootElement);
		
		Element chartGuideElement = rootElement.addElement("chart_guide");
		
		addNodeAttribute(chartGuideElement, chartGuide);
	}
	
	private void addChartTransition(Element rootElement) throws IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
		AssertUtility.notNull(rootElement);
		
		Element chartTransitionElement = rootElement.addElement("chart_transition");
		
		addNodeAttribute(chartTransitionElement, chartTransition);
	}
	
	private void addChartSeries(Element rootElement) throws IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
		AssertUtility.notNull(rootElement);
		
		Element chartSeriesElement = rootElement.addElement("series");
		
		addNodeAttribute(chartSeriesElement, chartSeries);
	}
	
	private void addSeriesColors(Element rootElement) {
		AssertUtility.notNull(rootElement);
		
		Element seriesColorElement = rootElement.addElement("series_color");
		
		String[] colors = seriesColors.split(";");
			
		for(int i = 0; i < colors.length; i++) {
			seriesColorElement.addElement("color").addText(colors[i]);
		}
	}
	
	private void addChartAxisCategory(Element rootElement) throws IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
		AssertUtility.notNull(rootElement);
		
		Element chartAxisCategoryElement = rootElement.addElement("axis_category");
		
		addNodeAttribute(chartAxisCategoryElement, chartAxisCategory);
	}
	
	private void addAxisCategoryLabels(Element rootElement) {
		AssertUtility.notNull(rootElement);
		
		Element xisCategoryLabelElement = rootElement.addElement("axis_category_label");
		
		String[] labels = axisCategoryLabels.split(";");
			
		for(int i = 0; i < labels.length; i++) {
			xisCategoryLabelElement.addElement("string").addText(labels[i]);
		}
	}
	
	private void addChartAxisTicks(Element rootElement) throws IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
		AssertUtility.notNull(rootElement);
		
		Element chartAxisTicksElement = rootElement.addElement("axis_ticks");
		
		addNodeAttribute(chartAxisTicksElement, chartAxisTicks);
	}
	
	private void addChartAxisValue(Element rootElement) throws IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
		AssertUtility.notNull(rootElement);
		
		Element chartAxisValueElement = rootElement.addElement("axis_value");
		
		addNodeAttribute(chartAxisValueElement, chartAxisValue);
	}
	
	private void addAxisValueLabels(Element rootElement) {
		AssertUtility.notNull(rootElement);
		
		Element xisValueLabelElement = rootElement.addElement("axis_value_label");
		
		String[] labels = axisValueLabels.split(";");
			
		for(int i = 0; i < labels.length; i++) {
			xisValueLabelElement.addElement("string").addText(labels[i]);
		}
	}
	
	private void addChartLegend(Element rootElement) throws IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
		AssertUtility.notNull(rootElement);
		
		Element chartLegendElement = rootElement.addElement("legend");
		
		addNodeAttribute(chartLegendElement, chartLegend);
	}
	
	private void addChartData(Element rootElement) {
		AssertUtility.notNull(rootElement);
		
		Element chartDataElement = rootElement.addElement("chart_data");
		
		if(chartData != null) {
			for(int i = 0; i < chartData.size(); i++) {
				Element rowElement = chartDataElement.addElement("row");
				
				ArrayList<Map<String, String>> rowData = chartData.get(i);
				
				addDataType(rowElement, rowData);
			}
		}
	}
	
	private void addDataType(Element rowElement, ArrayList<Map<String, String>> rowData) {
		AssertUtility.notNull(rowElement);
		AssertUtility.notNull(rowData);
		
		for(int j = 0; j < rowData.size(); j++) {
			Map<String, String> map = rowData.get(j);
			
			addDataTypeElement(rowElement, map);
		}
	}
	
	private void addDataTypeElement(Element rowElement, Map<String, String> map) {
		AssertUtility.notNull(rowElement);
		AssertUtility.notNull(map);
		
		Element node = null;
		if(map.get("DataType") != null && map.get("DataType").equalsIgnoreCase("String")) {
			if(AssertUtility.isNotNullAndNotSpace(map.get("Value"))) {
				node = rowElement.addElement("null");
			} else {
				node = rowElement.addElement("string").addText(map.get("Value"));
			}
		} else if(map.get("DataType") != null && map.get("DataType").equalsIgnoreCase("number")) {
			node = rowElement.addElement("number").addText(map.get("Value"));
		}
		
		addIndividualNote(node, map);
		addIndividualTooltip(node, map);
	}
	
	private void addIndividualNote(Element node, Map<String, String> map) {
		AssertUtility.notNull(node);
		AssertUtility.notNull(map);
		
		addIndividualNodeAttribute("note", node, map);
		
		/**
		 * 有以下幾種note type:
		 *     * flag (default)
		 *     * balloon
		 *     * arrow
		 *     * bullet
		 *     * lance 
		 */
		addIndividualNodeAttribute("note_type", node, map);
		addIndividualNodeAttribute("note_x", node, map);
		addIndividualNodeAttribute("note_y", node, map);
		addIndividualNodeAttribute("note_offset_x", node, map);
		addIndividualNodeAttribute("note_offset_y", node, map);
		addIndividualNodeAttribute("note_size", node, map);
	}
	
	private void addIndividualTooltip(Element node, Map<String, String> map) {
		AssertUtility.notNull(node);
		AssertUtility.notNull(map);
		
		addIndividualNodeAttribute("tooltip", node, map);
	}
	
	private void addIndividualNodeAttribute(String name, Element node, Map<String, String> map) {
		AssertUtility.notNullAndNotSpace(name);
		AssertUtility.notNull(node);
		AssertUtility.notNull(map);
		
		if(map.get(name) != null) {
			node.addAttribute(name, (String)map.get(name));
		}
	}
	
	public String renderStart() throws HtmlRenderException {
		deleteExpireXMLFile();
		
		StringBuffer script = new StringBuffer();
		try {
			makeXMLFile();
			
			script.append("<script language=\"JavaScript\" type=\"text/javascript\">\n");
		} catch (IllegalArgumentException e) {
			throw new HtmlRenderException(e);
		} catch (IOException e) {
			throw new HtmlRenderException(e);
		} catch (ClassNotFoundException e) {
			throw new HtmlRenderException(e);
		} catch (IllegalAccessException e) {
			throw new HtmlRenderException(e);
		} catch (InvocationTargetException e) {
			throw new HtmlRenderException(e);
		}
		
		return script.toString();
	}
	
	public String renderBody(String content) throws HtmlRenderException {
		StringBuffer script = new StringBuffer();
		try {
			script.append("<!--\n");
			script.append("if (AC_FL_RunContent == 0 || DetectFlashVer == 0) {\n");
			script.append("	alert(\"This page requires AC_RunActiveContent.js.\");\n");
			script.append("} else {\n");
			script.append("	var hasRightVersion = DetectFlashVer(requiredMajorVersion, requiredMinorVersion, requiredRevision);\n");
			script.append("	if(hasRightVersion) { \n");
			script.append("		AC_FL_RunContent(\n");
			script.append("			'codebase', 'http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=9,0,45,0',\n");
			if(chartWidth != null) {
				script.append("			'width', '" + chartWidth + "',\n");
			} else {
				script.append("			'width', '400',\n");
			}
			if(chartHeight != null) {
				script.append("			'height', '" + chartHeight + "',\n");
			} else {
				script.append("			'height', '250',\n");
			}
			script.append("			'scale', 'noscale',\n");
			script.append("			'salign', 'TL',\n");
			script.append("			'bgcolor', '#777788',\n");
			script.append("			'wmode', 'opaque',\n");
			script.append("			'movie', 'charts',\n");
			script.append("			'src', '" + webutil.getBasePathForHTML() + "charts',\n");
			//因為瀏覽器會cache住flash,即使XML更改
			//所以使用unique_id讓每次url都不同以達到更新效果
			if(xmlPath != null) {
				script.append("			'FlashVars', 'library_path=" + webutil.getBasePathForHTML() + "charts/charts_library&xml_source=" + webutil.getBasePathForHTML() + xmlPath + "/" + sourceXMLFileName + "?unique_id=" + Calendar.getInstance().getTimeInMillis() + "', \n");
			} else {
				script.append("			'FlashVars', 'library_path=" + webutil.getBasePathForHTML() + "charts/charts_library&xml_source=" + webutil.getBasePathForHTML() + "charts/tempXML/" + sourceXMLFileName + "?unique_id=" + Calendar.getInstance().getTimeInMillis() + "', \n");
			}
			script.append("			'id', '" + chartId + "',\n");
			script.append("			'name', '" + chartId + "',\n");
			script.append("			'menu', 'true',\n");
			script.append("			'allowFullScreen', 'true',\n");
			script.append("			'allowScriptAccess','sameDomain',\n");
			script.append("			'quality', 'high',\n");
			script.append("			'align', 'middle',\n");
			script.append("			'pluginspage', 'http://www.macromedia.com/go/getflashplayer',\n");
			script.append("			'play', 'true',\n");
			script.append("			'devicefont', 'false'\n");
			script.append("			); \n");
			script.append("	} else { \n");
			script.append("		var alternateContent = 'This content requires the Adobe Flash Player. '\n");
			script.append("		+ '<u><a href=http://www.macromedia.com/go/getflash/>Get Flash</a></u>.';\n");
			script.append("		document.write(alternateContent); \n");
			script.append("	}\n");
			script.append("}\n");
			script.append("// -->\n");
		} catch (NamingException e) {
			throw new HtmlRenderException(e);
		}
		
		return script.toString();
	}

	public String renderEnd() {
		StringBuffer script = new StringBuffer();
		
		script.append("</script>\n");
		script.append("<noscript>\n");
		script.append("	<P>This content requires JavaScript.</P>\n");
		script.append("</noscript>\n");
		
		return script.toString();
	}
}
