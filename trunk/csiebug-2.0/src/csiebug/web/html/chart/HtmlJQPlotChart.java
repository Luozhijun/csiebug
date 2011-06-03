package csiebug.web.html.chart;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import csiebug.util.AssertUtility;
import csiebug.util.StringUtility;
import csiebug.util.WebUtility;
import csiebug.web.html.HtmlBuilder;
import csiebug.web.html.HtmlComponentNoBody;
import csiebug.web.html.HtmlRenderException;
import csiebug.web.html.chart.jqPlotBean.JQPlotAxes;
import csiebug.web.html.chart.jqPlotBean.JQPlotCursor;
import csiebug.web.html.chart.jqPlotBean.JQPlotGrid;
import csiebug.web.html.chart.jqPlotBean.JQPlotHighlighter;
import csiebug.web.html.chart.jqPlotBean.JQPlotLegend;
import csiebug.web.html.chart.jqPlotBean.JQPlotLine;
import csiebug.web.html.chart.jqPlotBean.JQPlotPoint;
import csiebug.web.html.chart.jqPlotBean.JQPlotSeries;
import csiebug.web.html.chart.jqPlotBean.JQPlotTitle;

/**
 * 產生HTML jqPlot Chart
 * @author George_Tsai
 * @version 2010/5/25
 */

public class HtmlJQPlotChart extends HtmlComponentNoBody {
	private String chartId;
	
	private JQPlotLegend chartLegend;
	private JQPlotGrid chartGrid;
	private JQPlotTitle chartTitle;
	private List<JQPlotSeries> chartSeriesList;
	private JQPlotAxes chartAxes;
	private JQPlotHighlighter chartHighlighter;
	private JQPlotCursor chartCursor;
	private String style;
	private Boolean stackSeries;
	
	private List<JQPlotLine> chartData;
	
	private WebUtility webutil = new WebUtility();
	
	private String[] suffix = {"renderer", "formatter"};
	
	/**
	 * chart建構子
	 * @author George_Tsai
	 * @version 2010/3/18
	 */
	public HtmlJQPlotChart(String id, List<JQPlotLine> data, JQPlotLegend legend, JQPlotTitle title, JQPlotGrid grid, List<JQPlotSeries> seriesList, JQPlotAxes axes, JQPlotHighlighter highlighter, JQPlotCursor cursor, String style, Boolean stackSeries) {
		this.chartId = id;
		this.chartData = data;
		this.chartLegend = legend;
		this.chartGrid = grid;
		this.chartTitle = title;
		this.chartSeriesList = seriesList;
		this.chartAxes = axes;
		this.chartHighlighter = highlighter;
		this.chartCursor = cursor;
		this.style = style;
		this.stackSeries = stackSeries;
	}
	
	private void makeOnLoadScript() throws IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
		StringBuffer script = new StringBuffer();
		
		script.append("var " + chartId + " = $.jqplot('" + chartId + "', [");
		makeLineData(script);
		script.append("], {\n");
		
		boolean optionFlag = false;
		if(stackSeries != null) {
			script.append("    stackSeries:" + stackSeries);
			optionFlag = true;
		}
		optionFlag = makeProperty("legend", chartLegend, script, optionFlag, 1);
		optionFlag = makeProperty("title", chartTitle, script, optionFlag, 1);
		optionFlag = makeProperty("grid", chartGrid, script, optionFlag, 1);
		optionFlag = makePropertyArray("series", chartSeriesList, script, optionFlag, 1);
		optionFlag = makeProperty("axes", chartAxes, script, optionFlag, 1);
		optionFlag = makeProperty("highlighter", chartHighlighter, script, optionFlag, 1);
		optionFlag = makeProperty("cursor", chartCursor, script, optionFlag, 1);
		
		script.append("\n});\n");
		
		webutil.addPageLoadScript(script.toString());
	}
	
	private void makeLineData(StringBuffer script) {
		for(int i = 0; i < chartData.size(); i++) {
			JQPlotLine line = chartData.get(i);
			
			if(i != 0) {
				script.append(", ");
			}
			
			script.append("[");
			
			for(int j = 0; j < line.length(); j++) {
				JQPlotPoint point = line.getPoint(j);
				
				if(j != 0) {
					script.append(", ");
				}
				
				if(point.getX() != null) {
					script.append("[" + getXYString(point.getX()) + ", " + getXYString(point.getY()) + "]");
				} else {
					script.append(getXYString(point.getY()));
				}
			}
			
			script.append("]");
		}
	}
	
	private String getXYString(String value) {
		if(isFloatString(value)) {
			return value;
		} else if(isCandlestick(value)) {
			return value;
		} else if(isExtraSeriesData(value)) {
			return value;
		} else {
			return "'" + value + "'";
		}
	}
	
	private boolean isFloatString(String value) {
		try {
			Float.parseFloat(value);
			
			return true;
		} catch(NumberFormatException nfex) {
			return false;
		}
	}
	
	private boolean isCandlestick(String value) {
		String[] values = value.split(", ");
		
		if(values.length != 4) {
			return false;
		} else {
			for(int i = 0; i < values.length; i++) {
				if(!isFloatString(values[i])) {
					return false;
				}
			}
			
			return true;
		}
	}
	
	private boolean isExtraSeriesData(String value) {
		String[] values = value.split(", ");
		
		return (values.length == 2);
	}
	
	private boolean makeProperty(String propertyId, Object chartBean, StringBuffer script, boolean optionFlag, int tabNum) throws IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
		if(chartBean != null) {
			StringBuffer tab = new StringBuffer();
			for(int i = 0; i < tabNum; i++) {
				tab.append("    ");
			}
			
			if(optionFlag) {
				script.append(",\n");
			}
			
			script.append(tab.toString() + propertyId + ":{\n");
			setProperties(script, chartBean, 2);
			script.append("\n" + tab.toString() + "}");
			
			return true;
		} else {
			return optionFlag;
		}
	}
	
	private boolean makePropertyArray(String propertyId, List<?> chartBeanList,StringBuffer script, boolean optionFlag, int tabNum) throws IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
		if(chartBeanList != null && chartBeanList.size() > 0) {
			StringBuffer tab = new StringBuffer();
			String basicTab = "    ";
			for(int i = 0; i < tabNum; i++) {
				tab.append(basicTab);
			}
			
			if(optionFlag) {
				script.append(",\n");
			}
			
			script.append(tab.toString() + propertyId + ":[ \n");
			for(int i = 0; i < chartBeanList.size(); i++) {
				if(i != 0) {
					script.append(",\n");
				}
				script.append(tab.toString() + basicTab  + "{\n");
				setProperties(script, chartBeanList.get(i), tabNum + 2);
				script.append("\n" + tab.toString() + basicTab + "}");
			}
			script.append("\n" + tab.toString() + "]");
			
			return true;
		} else {
			return optionFlag;
		}
	}
	
	@SuppressWarnings("unchecked")
	private void setProperties(StringBuffer script, Object targetObject, int tabNum) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		StringBuffer tab = new StringBuffer();
		for(int i = 0; i < tabNum; i++) {
			tab.append("    ");
		}
		
		Class<?> c = Class.forName(targetObject.getClass().getName());
		
		boolean commaFlag = false;
		for(int i = 0; i < c.getMethods().length; i++) {
        	Method method = c.getMethods()[i];
        	
        	if(method.getName().startsWith("get") && !method.getName().equalsIgnoreCase("getClass")) {
        		String key = method.getName().replaceFirst("get", "");
        		key = key.substring(0, 1).toLowerCase() + key.substring(1, key.length());
	        	
	        	Object obj = method.invoke(targetObject, (Object[])null);
	        	if(obj != null) {
	        		if(commaFlag) {
	        			script.append(",\n");
	        		}
	        		
	        		script.append(tab.toString() + key + ":");
	        		
	        		String objClass = getPropertyClass(obj);
	        		
	        		if(objClass.equals("java.lang.Boolean")) {
	        			script.append(obj);
	        		} else if(objClass.equals("java.lang.Integer")) {
	        			script.append(obj);
	        		} else if(objClass.equals("java.lang.Float")) {
	        			script.append(obj);
	        		} else if(objClass.equals("java.util.List")) {
	        			setListProperties(script, (List<Object>)obj);
	        		} else if(objClass.equals("csiebug.web.html.chart.jqPlotBean.JQPlotOptions")) {
	        			setOptionsProperties(script, obj);
	        		} else {
	        			if(StringUtility.isEndsWithInArray(key.toLowerCase(), suffix)) {
	        				script.append(obj);
	        			} else if(key.equalsIgnoreCase("rendererOptions")) {
	        				script.append("{" + obj + "}");
	        			} else {
	        				script.append("'" + obj + "'");
	        			}
	        		}
	        		
	        		commaFlag = true;
	        	}
        	}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void setOptionsProperties(StringBuffer script, Object targetObject) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
		script.append("{");
		
		Class<?> c = Class.forName(targetObject.getClass().getName());
		
		boolean commaFlag = false;
		for(int i = 0; i < c.getMethods().length; i++) {
        	Method method = c.getMethods()[i];
        	
        	if(method.getName().startsWith("get") && !method.getName().equalsIgnoreCase("getClass")) {
        		String key = method.getName().replaceFirst("get", "");
        		key = key.substring(0, 1).toLowerCase() + key.substring(1, key.length());
	        	
	        	Object obj = method.invoke(targetObject, (Object[])null);
	        	if(obj != null) {
	        		if(commaFlag) {
	        			script.append(", ");
	        		}
	        		
	        		script.append(key + ":");
	        		
	        		String objClass = getPropertyClass(obj);
	        		
	        		if(objClass.equals("java.lang.Boolean")) {
	        			script.append(obj);
	        		} else if(objClass.equals("java.lang.Integer")) {
	        			script.append(obj);
	        		} else if(objClass.equals("java.lang.Float")) {
	        			script.append(obj);
	        		} else if(objClass.equals("java.util.List")) {
	        			setListProperties(script, (List<Object>)obj);
	        		} else if(objClass.equals("csiebug.web.html.chart.jqPlotBean.JQPlotOptions")) {
	        			setOptionsProperties(script, obj);
	        		} else {
	        			if(StringUtility.isEndsWithInArray(key.toLowerCase(), suffix)) {
	        				script.append(obj);
	        			} else if(key.equalsIgnoreCase("rendererOptions")) {
	        				script.append("{" + obj + "}");
	        			} else {
	        				script.append("'" + obj + "'");
	        			}
	        		}
	        		
	        		commaFlag = true;
	        	}
        	}
		}
		
		script.append("}");
	}
	
	@SuppressWarnings("unchecked")
	private void setListProperties(StringBuffer script, List<Object> list) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		script.append("[");
		
		for(int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);
			if(i != 0) {
				script.append(", ");
			}
			
			String objClass = getPropertyClass(obj);
				
			if(objClass.equals("java.lang.Boolean")) {
    			script.append(obj);
    		} else if(objClass.equals("java.lang.Integer")) {
    			script.append(obj);
    		} else if(objClass.equals("java.lang.Float")) {
    			script.append(obj);
    		} else if(objClass.equals("java.util.List")) {
    			setListProperties(script, (List<Object>)obj);
    		} else if(objClass.equals("csiebug.web.html.chart.jqPlotBean.JQPlotOptions")) {
    			setOptionsProperties(script, obj);
    		} else {
    			script.append("'" + obj + "'");
    		}
		}
		
		script.append("]");
	}
	
	private String getPropertyClass(Object obj) throws ClassNotFoundException {
		String className = null;
		
		String[] propertyClasses = new String[]{"java.lang.String", "java.lang.Boolean", "java.lang.Integer", "java.lang.Float", "java.util.List", "csiebug.web.html.chart.jqPlotBean.JQPlotOptions"};
		
		for(int i = 0; i < propertyClasses.length; i++) {
			try {
				Class<?> c = Class.forName(propertyClasses[i]);
				c.cast(obj);
				className = propertyClasses[i];
				break;
			} catch(ClassCastException ccex) {
				continue;
			}
		}
		
		return className;
	}
	
	public String renderStart() throws HtmlRenderException {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		if(AssertUtility.isNotNullAndNotSpace(chartId)) {
			try {
				makeOnLoadScript();
			} catch (IllegalArgumentException e) {
				throw new HtmlRenderException(e);
			} catch (ClassNotFoundException e) {
				throw new HtmlRenderException(e);
			} catch (IllegalAccessException e) {
				throw new HtmlRenderException(e);
			} catch (InvocationTargetException e) {
				throw new HtmlRenderException(e);
			}
			htmlBuilder.divStart().id(chartId).className("jqplot");
			
			if(AssertUtility.isNotNullAndNotSpace(style)) {
				htmlBuilder.style(style);
			}
			
			htmlBuilder.tagClose();
		}
		
		return htmlBuilder.toString();
	}
	
	public String renderEnd() {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		if(AssertUtility.isNotNullAndNotSpace(chartId)) {
			htmlBuilder.divEnd();
		}
		
		return htmlBuilder.toString();
	}
}
