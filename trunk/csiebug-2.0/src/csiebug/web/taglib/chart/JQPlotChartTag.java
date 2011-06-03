package csiebug.web.taglib.chart;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import csiebug.util.WebUtility;
import csiebug.web.html.chart.HtmlJQPlotChart;
import csiebug.web.html.chart.jqPlotBean.JQPlotAxis;
import csiebug.web.html.chart.jqPlotBean.JQPlotAxes;
import csiebug.web.html.chart.jqPlotBean.JQPlotAxisTickRenderer;
import csiebug.web.html.chart.jqPlotBean.JQPlotCanvasAxisLabelRenderer;
import csiebug.web.html.chart.jqPlotBean.JQPlotCursor;
import csiebug.web.html.chart.jqPlotBean.JQPlotGrid;
import csiebug.web.html.chart.jqPlotBean.JQPlotHighlighter;
import csiebug.web.html.chart.jqPlotBean.JQPlotLegend;
import csiebug.web.html.chart.jqPlotBean.JQPlotLine;
import csiebug.web.html.chart.jqPlotBean.JQPlotSeries;
import csiebug.web.html.chart.jqPlotBean.JQPlotTitle;

/**
 * JQPlot Chart tag
 * @author George_Tsai
 * @version 2010/05/26
 */
public class JQPlotChartTag extends BodyTagSupport implements TryCatchFinally {
	static final long serialVersionUID = 20100526;
	
	private String chartId;
	private String list;
	private String style;
    private String stackSeries;
	
	//chart legend 設定
	private String chartLegendShow;	//Wether to display the legend on the graph.
	private String chartLegendLocation;	//Placement of the legend.
	private String chartLegendXoffset;	//offset from the inside edge of the plot in the x direction in pixels.
	private String chartLegendYoffset;	//offset from the inside edge of the plot in the y direction in pixels.
	private String chartLegendBorder;	//css spec for the border around the legend box.
	private String chartLegendBackground;	//css spec for the background of the legend box.
	private String chartLegendTextColor;	//css color spec for the legend text.
	private String chartLegendFontFamily;	//css font-family spec for the legend text.
	private String chartLegendFontSize;	//css font-size spec for the legend text.
	private String chartLegendRowSpacing;	//css padding-top spec for the rows in the legend.
	private String chartLegendRendererOptions;	//renderer specific options passed to the renderer.
	private String chartLegendPredraw;	//Wether to draw the legend before the series or not.
	
	//chart title 設定
	private String chartTitleText;	//text of the title;
	private String chartTitleShow;	//wether or not to show the title
	private String chartTitleFontFamily;	//css font-family spec for the text.
	private String chartTitleFontSize;	//css font-size spec for the text.
	private String chartTitleTextAlign;	//css text-align spec for the text.
	private String chartTitleTextColor;	//css color spec for the text.
	private String chartTitleRenderer;	//A class for creating a DOM element for the title, see $.jqplot.DivTitleRenderer.
	private String chartTitleRendererOptions;	//renderer specific options passed to the renderer.
	
	//chart grid 設定
	private String chartGridDrawGridlines;	//wether to draw the gridlines on the plot.
	private String chartGridGridLineColor;	//color of the grid lines.
	private String chartGridGridLineWidth;	//width of the grid lines.
	private String chartGridBackground;	//css spec for the background color.
	private String chartGridBorderColor;	//css spec for the color of the grid border.
	private String chartGridBorderWidth;	//width of the border in pixels.
	private String chartGridShadow;	//wether to show a shadow behind the grid.
	private String chartGridShadowAngle;	//shadow angle in degrees
	private String chartGridShadowOffset;	//Offset of each shadow stroke from the border in pixels
	private String chartGridShadowWidth;	//width of the stoke for the shadow
	private String chartGridShadowDepth;	//Number of times shadow is stroked, each stroke offset shadowOffset from the last.
	private String chartGridShadowAlpha;	//Alpha channel transparency of shadow.
	private String chartGridRenderer;	//Instance of a renderer which will actually render the grid, see $.jqplot.CanvasGridRenderer.
	private String chartGridRendererOptions;	//Options to pass on to the renderer, see $.jqplot.CanvasGridRenderer.
	
	//chart series 設定
	private String chartSeriesList;
	
	//chart axes 設定
	//xaxis設定
	private String chartXaxisShow;	//Wether to display the axis on the graph.
	private String chartXaxisTickRenderer;	//A class of a rendering engine for creating the ticks labels displayed on the plot, See $.jqplot.AxisTickRenderer.
	private String chartXTickOptionsMark;	//tick mark on the axis.
	private String chartXTickOptionsShowMark;	//wether or not to show the mark on the axis.
	private String chartXTickOptionsShowGridline;	//wether or not to draw the gridline on the grid at this tick.
	private String chartXTickOptionsIsMinorTick;	//if this is a minor tick.
	private String chartXTickOptionsSize;	//Length of the tick beyond the grid in pixels.
	private String chartXTickOptionsMarkSize;	//Length of the tick marks in pixels.
	private String chartXTickOptionsShow;	//wether or not to show the tick (mark and label).
	private String chartXTickOptionsShowLabel;	//wether or not to show the label.
	private String chartXTickOptionsFormatter;	//A class of a formatter for the tick text.
	private String chartXTickOptionsFormatString;	//string passed to the formatter.
	private String chartXTickOptionsFontFamily;	//css spec for the font-family css attribute.
	private String chartXTickOptionsFontSize;	//css spec for the font-size css attribute.
	private String chartXTickOptionsTextColor;	//css spec for the color attribute.
	private String chartXTickOptionsAngle;	//angle of text, measured clockwise from x axis.
	private String chartXTickOptionsLabelPosition;	//‘auto’, ‘start’, ‘middle’ or ‘end’. 
	private String chartXTickOptionsFontWeight;	//CSS spec for fontWeight
	private String chartXTickOptionsFontStretch;	//Multiplier to condense or expand font width. 
	private String chartXTickOptionsEnableFontSupport;	//true to turn on native canvas font support in Mozilla 3.5+ and Safari 4+.
	private String chartXTickOptionsPt2px;	//Point to pixel scaling factor, used for computing height of bounding box around a label.
	private String chartXaxisLabelRenderer;	//A class of a rendering engine for creating an axis label.
	private String chartXLabelOptionsAngle;	//angle of text, measured clockwise from x axis.
	private String chartXLabelOptionsShow;	//wether or not to show the tick (mark and label).
	private String chartXLabelOptionsShowLabel;	//wether or not to show the label.
	private String chartXLabelOptionsLabel;	//label for the axis.
	private String chartXLabelOptionsFontFamily;	//CSS spec for the font-family css attribute.
	private String chartXLabelOptionsFontSize;	//CSS spec for font size.
	private String chartXLabelOptionsFontWeight;	
	private String chartXLabelOptionsFontStretch;	//Multiplier to condense or expand font width.
	private String chartXLabelOptionsTextColor;	//css spec for the color attribute.
	private String chartXLabelOptionsEnableFontSupport;	//true to turn on native canvas font support in Mozilla 3.5+ and Safari 4+.
	private String chartXLabelOptionsPt2px;	//Point to pixel scaling factor, used for computing height of bounding box around a label.
	private String chartXaxisLabel;	//Label for the axis
	private String chartXaxisShowLabel;	//true to show the axis label.
	private String chartXaxisMin;	//minimum value of the axis (in data units, not pixels).
	private String chartXaxisMax;	//maximum value of the axis (in data units, not pixels).
	private String chartXaxisAutoscale;	//Autoscale the axis min and max values to provide sensible tick spacing.
	private String chartXaxisPad;	//Padding to extend the range above and below the data bounds.
	private String chartXaxisPadMax;	//Padding to extend the range above data bounds.
	private String chartXaxisPadMin;	//Padding to extend the range below data bounds.
	private String chartXaxisTicks;	//1D [val, val, ...] or 2D [[val, label], [val, label], ...] array of ticks for the axis.
	private String chartXaxisNumberTicks;	//Desired number of ticks.
	private String chartXaxisTickInterval;	//number of units between ticks.
	private String chartXaxisRenderer;	//A class of a rendering engine that handles tick generation, scaling input data to pixel grid units and drawing the axis element.
	private String chartXaxisRendererOptions;	//renderer specific options.
	private String chartXaxisShowTicks;	//wether to show the ticks (both marks and labels) or not.
	private String chartXaxisShowTickMarks;	//wether to show the tick marks (line crossing grid) or not.
	private String chartXaxisShowMinorTicks;	//Wether or not to show minor ticks.
	private String chartXaxisUseSeriesColor;	//Use the color of the first series associated with this axis for the tick marks and line bordering this axis.
	private String chartXaxisBorderWidth;	//width of line stroked at the border of the axis.
	private String chartXaxisBorderColor;	//color of the border adjacent to the axis.
	private String chartXaxisSyncTicks;	//true to try and synchronize tick spacing across multiple axes so that ticks and grid lines line up.
	private String chartXaxisTickSpacing;	//Approximate pixel spacing between ticks on graph.
	
	//yaxis設定
	private String chartYaxisShow;	//Wether to display the axis on the graph.
	private String chartYaxisTickRenderer;	//A class of a rendering engine for creating the ticks labels displayed on the plot, See $.jqplot.AxisTickRenderer.
	private String chartYTickOptionsMark;	//tick mark on the axis.
	private String chartYTickOptionsShowMark;	//wether or not to show the mark on the axis.
	private String chartYTickOptionsShowGridline;	//wether or not to draw the gridline on the grid at this tick.
	private String chartYTickOptionsIsMinorTick;	//if this is a minor tick.
	private String chartYTickOptionsSize;	//Length of the tick beyond the grid in pixels.
	private String chartYTickOptionsMarkSize;	//Length of the tick marks in pixels.
	private String chartYTickOptionsShow;	//wether or not to show the tick (mark and label).
	private String chartYTickOptionsShowLabel;	//wether or not to show the label.
	private String chartYTickOptionsFormatter;	//A class of a formatter for the tick text.
	private String chartYTickOptionsFormatString;	//string passed to the formatter.
	private String chartYTickOptionsFontFamily;	//css spec for the font-family css attribute.
	private String chartYTickOptionsFontSize;	//css spec for the font-size css attribute.
	private String chartYTickOptionsTextColor;	//css spec for the color attribute.
	private String chartYTickOptionsAngle;	//angle of text, measured clockwise from x axis.
	private String chartYTickOptionsLabelPosition;	//‘auto’, ‘start’, ‘middle’ or ‘end’. 
	private String chartYTickOptionsFontWeight;	//CSS spec for fontWeight
	private String chartYTickOptionsFontStretch;	//Multiplier to condense or expand font width. 
	private String chartYTickOptionsEnableFontSupport;	//true to turn on native canvas font support in Mozilla 3.5+ and Safari 4+.
	private String chartYTickOptionsPt2px;	//Point to pixel scaling factor, used for computing height of bounding box around a label.
	private String chartYaxisLabelRenderer;	//A class of a rendering engine for creating an axis label.
	private String chartYLabelOptionsAngle;	//angle of text, measured clockwise from x axis.
	private String chartYLabelOptionsShow;	//wether or not to show the tick (mark and label).
	private String chartYLabelOptionsShowLabel;	//wether or not to show the label.
	private String chartYLabelOptionsLabel;	//label for the axis.
	private String chartYLabelOptionsFontFamily;	//CSS spec for the font-family css attribute.
	private String chartYLabelOptionsFontSize;	//CSS spec for font size.
	private String chartYLabelOptionsFontWeight;	
	private String chartYLabelOptionsFontStretch;	//Multiplier to condense or expand font width.
	private String chartYLabelOptionsTextColor;	//css spec for the color attribute.
	private String chartYLabelOptionsEnableFontSupport;	//true to turn on native canvas font support in Mozilla 3.5+ and Safari 4+.
	private String chartYLabelOptionsPt2px;	//Point to pixel scaling factor, used for computing height of bounding box around a label.
	private String chartYaxisLabel;	//Label for the axis
	private String chartYaxisShowLabel;	//true to show the axis label.
	private String chartYaxisMin;	//minimum value of the axis (in data units, not pixels).
	private String chartYaxisMax;	//maximum value of the axis (in data units, not pixels).
	private String chartYaxisAutoscale;	//Autoscale the axis min and max values to provide sensible tick spacing.
	private String chartYaxisPad;	//Padding to extend the range above and below the data bounds.
	private String chartYaxisPadMax;	//Padding to extend the range above data bounds.
	private String chartYaxisPadMin;	//Padding to extend the range below data bounds.
	private String chartYaxisTicks;	//1D [val, val, ...] or 2D [[val, label], [val, label], ...] array of ticks for the axis.
	private String chartYaxisNumberTicks;	//Desired number of ticks.
	private String chartYaxisTickInterval;	//number of units between ticks.
	private String chartYaxisRenderer;	//A class of a rendering engine that handles tick generation, scaling input data to pixel grid units and drawing the axis element.
	private String chartYaxisRendererOptions;	//renderer specific options.
	private String chartYaxisShowTicks;	//wether to show the ticks (both marks and labels) or not.
	private String chartYaxisShowTickMarks;	//wether to show the tick marks (line crossing grid) or not.
	private String chartYaxisShowMinorTicks;	//Wether or not to show minor ticks.
	private String chartYaxisUseSeriesColor;	//Use the color of the first series associated with this axis for the tick marks and line bordering this axis.
	private String chartYaxisBorderWidth;	//width of line stroked at the border of the axis.
	private String chartYaxisBorderColor;	//color of the border adjacent to the axis.
	private String chartYaxisSyncTicks;	//true to try and synchronize tick spacing across multiple axes so that ticks and grid lines line up.
	private String chartYaxisTickSpacing;	//Approximate pixel spacing between ticks on graph.
	
	//highlighter 設定
	private String chartHighlightShow;	//true to show the highlight.
	private String chartHighlightMarkerRenderer;	//Renderer used to draw the marker of the highlighted point.
	private String chartHighlightShowMarker;	//true to show the marker
	private String chartHighlightLineWidthAdjust;	//Pixels to add to the lineWidth of the highlight.
	private String chartHighlightSizeAdjust;	//Pixels to add to the overall size of the highlight.
	private String chartHighlightShowTooltip;	//Show a tooltip with data point values.
	private String chartHighlightTooltipLocation;	//Where to position tooltip, ‘n’, ‘ne’, ‘e’, ‘se’, ‘s’, ‘sw’, ‘w’, ‘nw’
	private String chartHighlightTooltipFade;	//true = fade in/out tooltip, flase = show/hide tooltip
	private String chartHighlightTooltipFadeSpeed;	//‘slow’, ‘def’, ‘fast’, or number of milliseconds.
	private String chartHighlightTooltipOffset;	//Pixel offset of tooltip from the highlight.
	private String chartHighlightTooltipAxes;	//Which axes to display in tooltip, ‘x’, ‘y’ or ‘both’, ‘xy’ or ‘yx’ ‘both’ and ‘xy’ are equivalent, ‘yx’ reverses order of labels.
	private String chartHighlightUseAxesFormatters;	//Use the x and y axes formatters to format the text in the tooltip.
	private String chartHighlightTooltipFormatString;	//sprintf format string for the tooltip.
	private String chartHighlightFormatString;	//alternative to tooltipFormatString will format the whole tooltip text, populating with x, y values as indicated by tooltipAxes option.
	private String chartHighlightYvalues;	//Number of y values to expect in the data point array.
	
	//cursor 設定
	private String chartCursorStyle;	//CSS spec for cursor style
	private String chartCursorShow;	//wether to show the cursor or not.
	private String chartCursorShowTooltip;	//show a cursor position tooltip near the cursor
	private String chartCursorFollowMouse;	//Tooltip follows the mouse, it is not at a fixed location.
	private String chartCursorTooltipLocation;	//Where to position tooltip.
	private String chartCursorTooltipOffset;	//Pixel offset of tooltip from the grid boudaries or cursor center.
	private String chartCursorShowTooltipGridPosition;	//show the grid pixel coordinates of the mouse.
	private String chartCursorShowTooltipUnitPosition;	//show the unit (data) coordinates of the mouse.
	private String chartCursorShowTooltipDataPosition;	//Used with showVerticalLine to show intersecting data points in the tooltip.
	private String chartCursorTooltipFormatString;	//sprintf format string for the tooltip.
	private String chartCursorUseAxesFormatters;	//Use the x and y axes formatters to format the text in the tooltip.
	private String chartCursorTooltipAxisGroups;	//Show position for the specified axes.
	private String chartCursorZoom;	//Enable plot zooming.
	private String chartCursorClickReset;	//Will reset plot zoom if single click on plot without drag.
	private String chartCursorDblClickReset;	//Will reset plot zoom if double click on plot without drag.
	private String chartCursorShowVerticalLine;	//draw a vertical line across the plot which follows the cursor.
	private String chartCursorShowHorizontalLine;	//draw a horizontal line across the plot which follows the cursor.
	private String chartCursorConstrainZoomTo;	//‘none’, ‘x’ or ‘y’
	private String chartCursorIntersectionThreshold;	//pixel distance from data point or marker to consider cursor lines intersecting with point.
	private String chartCursorShowCursorLegend;	//Replace the plot legend with an enhanced legend displaying intersection information.
	private String chartCursorCursorLegendFormatString;	//Format string used in the cursor legend. 
	
	public int doStartTag() throws JspException {
		try { 
            JspWriter out = pageContext.getOut();
            
            
            WebUtility webutil = new WebUtility();
            if(list != null && webutil.getRequestAttribute(list) != null) {
            	HtmlJQPlotChart htmlJQPlotChart = getChartInstance(webutil);
            	out.println(htmlJQPlotChart.render());
            }
        } catch(Exception e) {
        	throw new JspException(e);
        } 
 
        return SKIP_BODY; 
	}
	public void doFinally() {
		
	}
	public void doCatch(Throwable e) throws Throwable {
        throw new JspException("JQPlotChartTag Problem: " + e.getMessage());
    }
	
	@SuppressWarnings("unchecked")
	private HtmlJQPlotChart getChartInstance(WebUtility webutil) throws IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
		HtmlJQPlotChart htmlJQPlotChart = null;
		
		if(chartSeriesList != null && webutil.getRequestAttribute(chartSeriesList) != null) {
			htmlJQPlotChart = new HtmlJQPlotChart(chartId, (List<JQPlotLine>)webutil.getRequestAttribute(list), getChartLegendInstance(), getChartTitleInstance(), getChartGridInstance(), (List<JQPlotSeries>)webutil.getRequestAttribute(chartSeriesList), getChartAxesInstance(), getChartHighlighterInstance(), getChartCursorInstance(), style, Boolean.parseBoolean(stackSeries));
		} else {
			htmlJQPlotChart = new HtmlJQPlotChart(chartId, (List<JQPlotLine>)webutil.getRequestAttribute(list), getChartLegendInstance(), getChartTitleInstance(), getChartGridInstance(), null, getChartAxesInstance(), getChartHighlighterInstance(), getChartCursorInstance(), style, Boolean.parseBoolean(stackSeries));
		}
		
		return htmlJQPlotChart;
	}
	
	private void setAttribute(Object targetObject, String prefix) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Class<?> tagetClass = Class.forName(targetObject.getClass().getName());
		Class<?> tagClass = Class.forName(this.getClass().getName());
		
		for(int i = 0; i < tagetClass.getMethods().length; i++) {
        	Method targetMethod = tagetClass.getMethods()[i];
        	
        	if(targetMethod.getName().startsWith("set")) {
        		for(int j = 0; j < tagClass.getMethods().length; j++) {
        			Method tagMethod = tagClass.getMethods()[j];
        			
        			if(tagMethod.getName().startsWith("get")) {
        				Class<?> parameterType = targetMethod.getParameterTypes()[0];
    	        		String getMethodName = "get" + prefix + targetMethod.getName().replaceFirst("set", "");
    	        		
        				if(tagMethod.getName().equals(getMethodName)) {
        					Object obj = tagMethod.invoke(this, (Object[])null);
        					
        					if(obj != null) {
        						if(parameterType.getName().equals("java.lang.Integer")) {
        							obj = Integer.parseInt((String)obj);
        						} else if(parameterType.getName().equals("java.lang.Float")) {
        							obj = Float.parseFloat((String)obj);
        						} else if(parameterType.getName().equals("java.util.List")) {
        							WebUtility webutil = new WebUtility();
        							obj = (List<?>)webutil.getRequestAttribute((String)obj);
        						} else if(parameterType.getName().equals("java.lang.Boolean")) {
        							obj = Boolean.parseBoolean((String)obj);
        						}
        						
        						Object[] args = new Object[]{obj};
        						targetMethod.invoke(targetObject, args);
        					}
        				}
        			}
        		}
	        }
		}
	}
	
	private boolean isAllAttributeNull(String prefix) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		boolean flag = true;
		Class<?> tagClass = Class.forName(this.getClass().getName());
		
		for(int j = 0; j < tagClass.getMethods().length; j++) {
			Method tagMethod = tagClass.getMethods()[j];
			
			if(tagMethod.getName().indexOf("get" + prefix) != -1) {
				Object obj = tagMethod.invoke(this, (Object[])null);
				
				if(obj != null) {
					flag = false;
					break;
				}
			}
		}
		
		return flag;
	}
	
	private JQPlotLegend getChartLegendInstance() throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		JQPlotLegend chartLegend = null;
		
		if(!isAllAttributeNull("ChartLegend")) {
			chartLegend = new JQPlotLegend();
			
			setAttribute(chartLegend, "ChartLegend");
		}
		
		return chartLegend;
	}
	
	private JQPlotTitle getChartTitleInstance() throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		JQPlotTitle chartTitle = null;
		
		if(!isAllAttributeNull("ChartTitle")) {
			chartTitle = new JQPlotTitle();
			
			setAttribute(chartTitle, "ChartTitle");
		}
		
		return chartTitle;
	}
	
	private JQPlotGrid getChartGridInstance() throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		JQPlotGrid chartGrid = null;
		
		if(!isAllAttributeNull("ChartGrid")) {
			chartGrid = new JQPlotGrid();
			
			setAttribute(chartGrid, "ChartGrid");
		}
		
		return chartGrid;
	}
	
	private JQPlotAxes getChartAxesInstance() throws IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
		JQPlotAxes chartAxes = null;
		
		JQPlotAxis xaxis = getChartAxisInstance("x");
		JQPlotAxis yaxis = getChartAxisInstance("y");
		
		if(xaxis != null || yaxis != null) {
			chartAxes = new JQPlotAxes();
			chartAxes.setXaxis(xaxis);
			chartAxes.setYaxis(yaxis);
		}
		
		return chartAxes;
	}
	
	private JQPlotAxis getChartAxisInstance(String type) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		JQPlotAxis chartAxis = null;
		
		if(!isAllAttributeNull("Chart" + type.toUpperCase() + "axis") ||
		   !isAllAttributeNull("Chart" + type.toUpperCase() + "TickOptions") ||
		   !isAllAttributeNull("Chart" + type.toUpperCase() + "LabelOptions")) {
			chartAxis = new JQPlotAxis();
			
			setAttribute(chartAxis, "Chart" + type.toUpperCase() + "axis");
			
			if(!isAllAttributeNull("Chart" + type.toUpperCase() + "TickOptions")) {
				JQPlotAxisTickRenderer tickOptions = new JQPlotAxisTickRenderer();
				
				setAttribute(tickOptions, "Chart" + type.toUpperCase() + "TickOptions");
				
				chartAxis.setTickOptions(tickOptions);
			}
			
			if(!isAllAttributeNull("Chart" + type.toUpperCase() + "LabelOptions")) {
				JQPlotCanvasAxisLabelRenderer labelOptions = new JQPlotCanvasAxisLabelRenderer();
				
				setAttribute(labelOptions, "Chart" + type.toUpperCase() + "LabelOptions");
				
				chartAxis.setLabelOptions(labelOptions);
			}
		}
		
		return chartAxis;
	}
	
	private JQPlotHighlighter getChartHighlighterInstance() throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		JQPlotHighlighter chartHighlighter = null;
		
		if(!isAllAttributeNull("ChartHighlight")) {
			chartHighlighter = new JQPlotHighlighter();
			
			setAttribute(chartHighlighter, "ChartHighlight");
		}
		
		return chartHighlighter;
	}
	
	private JQPlotCursor getChartCursorInstance() throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		JQPlotCursor chartCursor = null;
		
		if(!isAllAttributeNull("ChartCursor")) {
			chartCursor = new JQPlotCursor();
			
			setAttribute(chartCursor, "ChartCursor");
		}
		
		return chartCursor;
	}
	
	//元件屬性區
	public void setChartId(String chartId) {
		this.chartId = chartId;
	}
	public String getChartId() {
		return chartId;
	}
	public void setList(String list) {
		this.list = list;
	}
	public String getList() {
		return list;
	}
	public void setChartLegendShow(String chartLegendShow) {
		this.chartLegendShow = chartLegendShow;
	}
	public String getChartLegendShow() {
		return chartLegendShow;
	}
	public void setChartLegendLocation(String chartLegendLocation) {
		this.chartLegendLocation = chartLegendLocation;
	}
	public String getChartLegendLocation() {
		return chartLegendLocation;
	}
	public void setChartLegendXoffset(String chartLegendXoffset) {
		this.chartLegendXoffset = chartLegendXoffset;
	}
	public String getChartLegendXoffset() {
		return chartLegendXoffset;
	}
	public void setChartLegendYoffset(String chartLegendYoffset) {
		this.chartLegendYoffset = chartLegendYoffset;
	}
	public String getChartLegendYoffset() {
		return chartLegendYoffset;
	}
	public void setChartLegendBorder(String chartLegendBorder) {
		this.chartLegendBorder = chartLegendBorder;
	}
	public String getChartLegendBorder() {
		return chartLegendBorder;
	}
	public void setChartLegendBackground(String chartLegendBackground) {
		this.chartLegendBackground = chartLegendBackground;
	}
	public String getChartLegendBackground() {
		return chartLegendBackground;
	}
	public void setChartLegendTextColor(String chartLegendTextColor) {
		this.chartLegendTextColor = chartLegendTextColor;
	}
	public String getChartLegendTextColor() {
		return chartLegendTextColor;
	}
	public void setChartLegendFontFamily(String chartLegendFontFamily) {
		this.chartLegendFontFamily = chartLegendFontFamily;
	}
	public String getChartLegendFontFamily() {
		return chartLegendFontFamily;
	}
	public void setChartLegendFontSize(String chartLegendFontSize) {
		this.chartLegendFontSize = chartLegendFontSize;
	}
	public String getChartLegendFontSize() {
		return chartLegendFontSize;
	}
	public void setChartLegendRowSpacing(String chartLegendRowSpacing) {
		this.chartLegendRowSpacing = chartLegendRowSpacing;
	}
	public String getChartLegendRowSpacing() {
		return chartLegendRowSpacing;
	}
	public void setChartLegendRendererOptions(String chartLegendRendererOptions) {
		this.chartLegendRendererOptions = chartLegendRendererOptions;
	}
	public String getChartLegendRendererOptions() {
		return chartLegendRendererOptions;
	}
	public void setChartLegendPredraw(String chartLegendPredraw) {
		this.chartLegendPredraw = chartLegendPredraw;
	}
	public String getChartLegendPredraw() {
		return chartLegendPredraw;
	}
	public void setChartTitleText(String chartTitleText) {
		this.chartTitleText = chartTitleText;
	}
	public String getChartTitleText() {
		return chartTitleText;
	}
	public void setChartTitleShow(String chartTitleShow) {
		this.chartTitleShow = chartTitleShow;
	}
	public String getChartTitleShow() {
		return chartTitleShow;
	}
	public void setChartTitleFontFamily(String chartTitleFontFamily) {
		this.chartTitleFontFamily = chartTitleFontFamily;
	}
	public String getChartTitleFontFamily() {
		return chartTitleFontFamily;
	}
	public void setChartTitleFontSize(String chartTitleFontSize) {
		this.chartTitleFontSize = chartTitleFontSize;
	}
	public String getChartTitleFontSize() {
		return chartTitleFontSize;
	}
	public void setChartTitleTextAlign(String chartTitleTextAlign) {
		this.chartTitleTextAlign = chartTitleTextAlign;
	}
	public String getChartTitleTextAlign() {
		return chartTitleTextAlign;
	}
	public void setChartTitleTextColor(String chartTitleTextColor) {
		this.chartTitleTextColor = chartTitleTextColor;
	}
	public String getChartTitleTextColor() {
		return chartTitleTextColor;
	}
	public void setChartTitleRenderer(String chartTitleRenderer) {
		this.chartTitleRenderer = chartTitleRenderer;
	}
	public String getChartTitleRenderer() {
		return chartTitleRenderer;
	}
	public void setChartTitleRendererOptions(String chartTitleRendererOptions) {
		this.chartTitleRendererOptions = chartTitleRendererOptions;
	}
	public String getChartTitleRendererOptions() {
		return chartTitleRendererOptions;
	}
	public void setChartGridDrawGridlines(String chartGridDrawGridlines) {
		this.chartGridDrawGridlines = chartGridDrawGridlines;
	}
	public String getChartGridDrawGridlines() {
		return chartGridDrawGridlines;
	}
	public void setChartGridGridLineColor(String chartGridGridLineColor) {
		this.chartGridGridLineColor = chartGridGridLineColor;
	}
	public String getChartGridGridLineColor() {
		return chartGridGridLineColor;
	}
	public void setChartGridGridLineWidth(String chartGridGridLineWidth) {
		this.chartGridGridLineWidth = chartGridGridLineWidth;
	}
	public String getChartGridGridLineWidth() {
		return chartGridGridLineWidth;
	}
	public void setChartGridBackground(String chartGridBackground) {
		this.chartGridBackground = chartGridBackground;
	}
	public String getChartGridBackground() {
		return chartGridBackground;
	}
	public void setChartGridBorderColor(String chartGridBorderColor) {
		this.chartGridBorderColor = chartGridBorderColor;
	}
	public String getChartGridBorderColor() {
		return chartGridBorderColor;
	}
	public void setChartGridBorderWidth(String chartGridBorderWidth) {
		this.chartGridBorderWidth = chartGridBorderWidth;
	}
	public String getChartGridBorderWidth() {
		return chartGridBorderWidth;
	}
	public void setChartGridShadow(String chartGridShadow) {
		this.chartGridShadow = chartGridShadow;
	}
	public String getChartGridShadow() {
		return chartGridShadow;
	}
	public void setChartGridShadowAngle(String chartGridShadowAngle) {
		this.chartGridShadowAngle = chartGridShadowAngle;
	}
	public String getChartGridShadowAngle() {
		return chartGridShadowAngle;
	}
	public void setChartGridShadowOffset(String chartGridShadowOffset) {
		this.chartGridShadowOffset = chartGridShadowOffset;
	}
	public String getChartGridShadowOffset() {
		return chartGridShadowOffset;
	}
	public void setChartGridShadowWidth(String chartGridShadowWidth) {
		this.chartGridShadowWidth = chartGridShadowWidth;
	}
	public String getChartGridShadowWidth() {
		return chartGridShadowWidth;
	}
	public void setChartGridShadowDepth(String chartGridShadowDepth) {
		this.chartGridShadowDepth = chartGridShadowDepth;
	}
	public String getChartGridShadowDepth() {
		return chartGridShadowDepth;
	}
	public void setChartGridShadowAlpha(String chartGridShadowAlpha) {
		this.chartGridShadowAlpha = chartGridShadowAlpha;
	}
	public String getChartGridShadowAlpha() {
		return chartGridShadowAlpha;
	}
	public void setChartGridRenderer(String chartGridRenderer) {
		this.chartGridRenderer = chartGridRenderer;
	}
	public String getChartGridRenderer() {
		return chartGridRenderer;
	}
	public void setChartGridRendererOptions(String chartGridRendererOptions) {
		this.chartGridRendererOptions = chartGridRendererOptions;
	}
	public String getChartGridRendererOptions() {
		return chartGridRendererOptions;
	}
	public void setChartSeriesList(String chartSeriesList) {
		this.chartSeriesList = chartSeriesList;
	}
	public String getChartSeriesList() {
		return chartSeriesList;
	}
	public void setChartXaxisShow(String chartXaxisShow) {
		this.chartXaxisShow = chartXaxisShow;
	}
	public String getChartXaxisShow() {
		return chartXaxisShow;
	}
	public void setChartXaxisTickRenderer(String chartXaxisTickRenderer) {
		this.chartXaxisTickRenderer = chartXaxisTickRenderer;
	}
	public String getChartXaxisTickRenderer() {
		return chartXaxisTickRenderer;
	}
	public void setChartXaxisLabelRenderer(String chartXaxisLabelRenderer) {
		this.chartXaxisLabelRenderer = chartXaxisLabelRenderer;
	}
	public String getChartXaxisLabelRenderer() {
		return chartXaxisLabelRenderer;
	}
	public void setChartXaxisLabel(String chartXaxisLabel) {
		this.chartXaxisLabel = chartXaxisLabel;
	}
	public String getChartXaxisLabel() {
		return chartXaxisLabel;
	}
	public void setChartXaxisShowLabel(String chartXaxisShowLabel) {
		this.chartXaxisShowLabel = chartXaxisShowLabel;
	}
	public String getChartXaxisShowLabel() {
		return chartXaxisShowLabel;
	}
	public void setChartXaxisMin(String chartXaxisMin) {
		this.chartXaxisMin = chartXaxisMin;
	}
	public String getChartXaxisMin() {
		return chartXaxisMin;
	}
	public void setChartXaxisMax(String chartXaxisMax) {
		this.chartXaxisMax = chartXaxisMax;
	}
	public String getChartXaxisMax() {
		return chartXaxisMax;
	}
	public void setChartXaxisAutoscale(String chartXaxisAutoscale) {
		this.chartXaxisAutoscale = chartXaxisAutoscale;
	}
	public String getChartXaxisAutoscale() {
		return chartXaxisAutoscale;
	}
	public void setChartXaxisPad(String chartXaxisPad) {
		this.chartXaxisPad = chartXaxisPad;
	}
	public String getChartXaxisPad() {
		return chartXaxisPad;
	}
	public void setChartXaxisPadMax(String chartXaxisPadMax) {
		this.chartXaxisPadMax = chartXaxisPadMax;
	}
	public String getChartXaxisPadMax() {
		return chartXaxisPadMax;
	}
	public void setChartXaxisPadMin(String chartXaxisPadMin) {
		this.chartXaxisPadMin = chartXaxisPadMin;
	}
	public String getChartXaxisPadMin() {
		return chartXaxisPadMin;
	}
	public void setChartXaxisTicks(String chartXaxisTicks) {
		this.chartXaxisTicks = chartXaxisTicks;
	}
	public String getChartXaxisTicks() {
		return chartXaxisTicks;
	}
	public void setChartXaxisNumberTicks(String chartXaxisNumberTicks) {
		this.chartXaxisNumberTicks = chartXaxisNumberTicks;
	}
	public String getChartXaxisNumberTicks() {
		return chartXaxisNumberTicks;
	}
	public void setChartXaxisTickInterval(String chartXaxisTickInterval) {
		this.chartXaxisTickInterval = chartXaxisTickInterval;
	}
	public String getChartXaxisTickInterval() {
		return chartXaxisTickInterval;
	}
	public void setChartXaxisRenderer(String chartXaxisRenderer) {
		this.chartXaxisRenderer = chartXaxisRenderer;
	}
	public String getChartXaxisRenderer() {
		return chartXaxisRenderer;
	}
	public void setChartXaxisShowTicks(String chartXaxisShowTicks) {
		this.chartXaxisShowTicks = chartXaxisShowTicks;
	}
	public String getChartXaxisShowTicks() {
		return chartXaxisShowTicks;
	}
	public void setChartXaxisRendererOptions(String chartXaxisRendererOptions) {
		this.chartXaxisRendererOptions = chartXaxisRendererOptions;
	}
	public String getChartXaxisRendererOptions() {
		return chartXaxisRendererOptions;
	}
	public void setChartXaxisShowTickMarks(String chartXaxisShowTickMarks) {
		this.chartXaxisShowTickMarks = chartXaxisShowTickMarks;
	}
	public String getChartXaxisShowTickMarks() {
		return chartXaxisShowTickMarks;
	}
	public void setChartXaxisShowMinorTicks(String chartXaxisShowMinorTicks) {
		this.chartXaxisShowMinorTicks = chartXaxisShowMinorTicks;
	}
	public String getChartXaxisShowMinorTicks() {
		return chartXaxisShowMinorTicks;
	}
	public void setChartXaxisUseSeriesColor(String chartXaxisUseSeriesColor) {
		this.chartXaxisUseSeriesColor = chartXaxisUseSeriesColor;
	}
	public String getChartXaxisUseSeriesColor() {
		return chartXaxisUseSeriesColor;
	}
	public void setChartXaxisBorderWidth(String chartXaxisBorderWidth) {
		this.chartXaxisBorderWidth = chartXaxisBorderWidth;
	}
	public String getChartXaxisBorderWidth() {
		return chartXaxisBorderWidth;
	}
	public void setChartXaxisBorderColor(String chartXaxisBorderColor) {
		this.chartXaxisBorderColor = chartXaxisBorderColor;
	}
	public String getChartXaxisBorderColor() {
		return chartXaxisBorderColor;
	}
	public void setChartXaxisSyncTicks(String chartXaxisSyncTicks) {
		this.chartXaxisSyncTicks = chartXaxisSyncTicks;
	}
	public String getChartXaxisSyncTicks() {
		return chartXaxisSyncTicks;
	}
	public void setChartXaxisTickSpacing(String chartXaxisTickSpacing) {
		this.chartXaxisTickSpacing = chartXaxisTickSpacing;
	}
	public String getChartXaxisTickSpacing() {
		return chartXaxisTickSpacing;
	}
	public void setChartYaxisShow(String chartYaxisShow) {
		this.chartYaxisShow = chartYaxisShow;
	}
	public String getChartYaxisShow() {
		return chartYaxisShow;
	}
	public void setChartYaxisTickRenderer(String chartYaxisTickRenderer) {
		this.chartYaxisTickRenderer = chartYaxisTickRenderer;
	}
	public String getChartYaxisTickRenderer() {
		return chartYaxisTickRenderer;
	}
	public void setChartYaxisLabelRenderer(String chartYaxisLabelRenderer) {
		this.chartYaxisLabelRenderer = chartYaxisLabelRenderer;
	}
	public String getChartYaxisLabelRenderer() {
		return chartYaxisLabelRenderer;
	}
	public void setChartYaxisLabel(String chartYaxisLabel) {
		this.chartYaxisLabel = chartYaxisLabel;
	}
	public String getChartYaxisLabel() {
		return chartYaxisLabel;
	}
	public void setChartYaxisShowLabel(String chartYaxisShowLabel) {
		this.chartYaxisShowLabel = chartYaxisShowLabel;
	}
	public String getChartYaxisShowLabel() {
		return chartYaxisShowLabel;
	}
	public void setChartYaxisMin(String chartYaxisMin) {
		this.chartYaxisMin = chartYaxisMin;
	}
	public String getChartYaxisMin() {
		return chartYaxisMin;
	}
	public void setChartYaxisMax(String chartYaxisMax) {
		this.chartYaxisMax = chartYaxisMax;
	}
	public String getChartYaxisMax() {
		return chartYaxisMax;
	}
	public void setChartYaxisAutoscale(String chartYaxisAutoscale) {
		this.chartYaxisAutoscale = chartYaxisAutoscale;
	}
	public String getChartYaxisAutoscale() {
		return chartYaxisAutoscale;
	}
	public void setChartYaxisPad(String chartYaxisPad) {
		this.chartYaxisPad = chartYaxisPad;
	}
	public String getChartYaxisPad() {
		return chartYaxisPad;
	}
	public void setChartYaxisPadMax(String chartYaxisPadMax) {
		this.chartYaxisPadMax = chartYaxisPadMax;
	}
	public String getChartYaxisPadMax() {
		return chartYaxisPadMax;
	}
	public void setChartYaxisPadMin(String chartYaxisPadMin) {
		this.chartYaxisPadMin = chartYaxisPadMin;
	}
	public String getChartYaxisPadMin() {
		return chartYaxisPadMin;
	}
	public void setChartYaxisTicks(String chartYaxisTicks) {
		this.chartYaxisTicks = chartYaxisTicks;
	}
	public String getChartYaxisTicks() {
		return chartYaxisTicks;
	}
	public void setChartYaxisNumberTicks(String chartYaxisNumberTicks) {
		this.chartYaxisNumberTicks = chartYaxisNumberTicks;
	}
	public String getChartYaxisNumberTicks() {
		return chartYaxisNumberTicks;
	}
	public void setChartYaxisTickInterval(String chartYaxisTickInterval) {
		this.chartYaxisTickInterval = chartYaxisTickInterval;
	}
	public String getChartYaxisTickInterval() {
		return chartYaxisTickInterval;
	}
	public void setChartYaxisRenderer(String chartYaxisRenderer) {
		this.chartYaxisRenderer = chartYaxisRenderer;
	}
	public String getChartYaxisRenderer() {
		return chartYaxisRenderer;
	}
	public void setChartYaxisRendererOptions(String chartYaxisRendererOptions) {
		this.chartYaxisRendererOptions = chartYaxisRendererOptions;
	}
	public String getChartYaxisRendererOptions() {
		return chartYaxisRendererOptions;
	}
	public void setChartYaxisShowTicks(String chartYaxisShowTicks) {
		this.chartYaxisShowTicks = chartYaxisShowTicks;
	}
	public String getChartYaxisShowTicks() {
		return chartYaxisShowTicks;
	}
	public void setChartYaxisShowTickMarks(String chartYaxisShowTickMarks) {
		this.chartYaxisShowTickMarks = chartYaxisShowTickMarks;
	}
	public String getChartYaxisShowTickMarks() {
		return chartYaxisShowTickMarks;
	}
	public void setChartYaxisShowMinorTicks(String chartYaxisShowMinorTicks) {
		this.chartYaxisShowMinorTicks = chartYaxisShowMinorTicks;
	}
	public String getChartYaxisShowMinorTicks() {
		return chartYaxisShowMinorTicks;
	}
	public void setChartYaxisUseSeriesColor(String chartYaxisUseSeriesColor) {
		this.chartYaxisUseSeriesColor = chartYaxisUseSeriesColor;
	}
	public String getChartYaxisUseSeriesColor() {
		return chartYaxisUseSeriesColor;
	}
	public void setChartYaxisBorderWidth(String chartYaxisBorderWidth) {
		this.chartYaxisBorderWidth = chartYaxisBorderWidth;
	}
	public String getChartYaxisBorderWidth() {
		return chartYaxisBorderWidth;
	}
	public void setChartYaxisBorderColor(String chartYaxisBorderColor) {
		this.chartYaxisBorderColor = chartYaxisBorderColor;
	}
	public String getChartYaxisBorderColor() {
		return chartYaxisBorderColor;
	}
	public void setChartYaxisSyncTicks(String chartYaxisSyncTicks) {
		this.chartYaxisSyncTicks = chartYaxisSyncTicks;
	}
	public String getChartYaxisSyncTicks() {
		return chartYaxisSyncTicks;
	}
	public void setChartYaxisTickSpacing(String chartYaxisTickSpacing) {
		this.chartYaxisTickSpacing = chartYaxisTickSpacing;
	}
	public String getChartYaxisTickSpacing() {
		return chartYaxisTickSpacing;
	}
	public void setChartXTickOptionsMark(String chartXTickOptionsMark) {
		this.chartXTickOptionsMark = chartXTickOptionsMark;
	}
	public String getChartXTickOptionsMark() {
		return chartXTickOptionsMark;
	}
	public void setChartXTickOptionsShowMark(String chartXTickOptionsShowMark) {
		this.chartXTickOptionsShowMark = chartXTickOptionsShowMark;
	}
	public String getChartXTickOptionsShowMark() {
		return chartXTickOptionsShowMark;
	}
	public void setChartXTickOptionsShowGridline(
			String chartXTickOptionsShowGridline) {
		this.chartXTickOptionsShowGridline = chartXTickOptionsShowGridline;
	}
	public String getChartXTickOptionsShowGridline() {
		return chartXTickOptionsShowGridline;
	}
	public void setChartXTickOptionsIsMinorTick(
			String chartXTickOptionsIsMinorTick) {
		this.chartXTickOptionsIsMinorTick = chartXTickOptionsIsMinorTick;
	}
	public String getChartXTickOptionsIsMinorTick() {
		return chartXTickOptionsIsMinorTick;
	}
	public void setChartXTickOptionsSize(String chartXTickOptionsSize) {
		this.chartXTickOptionsSize = chartXTickOptionsSize;
	}
	public String getChartXTickOptionsSize() {
		return chartXTickOptionsSize;
	}
	public void setChartXTickOptionsMarkSize(String chartXTickOptionsMarkSize) {
		this.chartXTickOptionsMarkSize = chartXTickOptionsMarkSize;
	}
	public String getChartXTickOptionsMarkSize() {
		return chartXTickOptionsMarkSize;
	}
	public void setChartXTickOptionsShow(String chartXTickOptionsShow) {
		this.chartXTickOptionsShow = chartXTickOptionsShow;
	}
	public String getChartXTickOptionsShow() {
		return chartXTickOptionsShow;
	}
	public void setChartXTickOptionsShowLabel(String chartXTickOptionsShowLabel) {
		this.chartXTickOptionsShowLabel = chartXTickOptionsShowLabel;
	}
	public String getChartXTickOptionsShowLabel() {
		return chartXTickOptionsShowLabel;
	}
	public void setChartXTickOptionsFormatter(String chartXTickOptionsFormatter) {
		this.chartXTickOptionsFormatter = chartXTickOptionsFormatter;
	}
	public String getChartXTickOptionsFormatter() {
		return chartXTickOptionsFormatter;
	}
	public void setChartXTickOptionsFormatString(
			String chartXTickOptionsFormatString) {
		this.chartXTickOptionsFormatString = chartXTickOptionsFormatString;
	}
	public String getChartXTickOptionsFormatString() {
		return chartXTickOptionsFormatString;
	}
	public void setChartXTickOptionsFontFamily(
			String chartXTickOptionsFontFamily) {
		this.chartXTickOptionsFontFamily = chartXTickOptionsFontFamily;
	}
	public String getChartXTickOptionsFontFamily() {
		return chartXTickOptionsFontFamily;
	}
	public void setChartXTickOptionsFontSize(String chartXTickOptionsFontSize) {
		this.chartXTickOptionsFontSize = chartXTickOptionsFontSize;
	}
	public String getChartXTickOptionsFontSize() {
		return chartXTickOptionsFontSize;
	}
	public void setChartXTickOptionsTextColor(String chartXTickOptionsTextColor) {
		this.chartXTickOptionsTextColor = chartXTickOptionsTextColor;
	}
	public String getChartXTickOptionsTextColor() {
		return chartXTickOptionsTextColor;
	}
	public void setChartYTickOptionsMark(String chartYTickOptionsMark) {
		this.chartYTickOptionsMark = chartYTickOptionsMark;
	}
	public String getChartYTickOptionsMark() {
		return chartYTickOptionsMark;
	}
	public void setChartYTickOptionsShowMark(String chartYTickOptionsShowMark) {
		this.chartYTickOptionsShowMark = chartYTickOptionsShowMark;
	}
	public String getChartYTickOptionsShowMark() {
		return chartYTickOptionsShowMark;
	}
	public void setChartYTickOptionsShowGridline(
			String chartYTickOptionsShowGridline) {
		this.chartYTickOptionsShowGridline = chartYTickOptionsShowGridline;
	}
	public String getChartYTickOptionsShowGridline() {
		return chartYTickOptionsShowGridline;
	}
	public void setChartYTickOptionsIsMinorTick(
			String chartYTickOptionsIsMinorTick) {
		this.chartYTickOptionsIsMinorTick = chartYTickOptionsIsMinorTick;
	}
	public String getChartYTickOptionsIsMinorTick() {
		return chartYTickOptionsIsMinorTick;
	}
	public void setChartYTickOptionsSize(String chartYTickOptionsSize) {
		this.chartYTickOptionsSize = chartYTickOptionsSize;
	}
	public String getChartYTickOptionsSize() {
		return chartYTickOptionsSize;
	}
	public void setChartYTickOptionsMarkSize(String chartYTickOptionsMarkSize) {
		this.chartYTickOptionsMarkSize = chartYTickOptionsMarkSize;
	}
	public String getChartYTickOptionsMarkSize() {
		return chartYTickOptionsMarkSize;
	}
	public void setChartYTickOptionsShow(String chartYTickOptionsShow) {
		this.chartYTickOptionsShow = chartYTickOptionsShow;
	}
	public String getChartYTickOptionsShow() {
		return chartYTickOptionsShow;
	}
	public void setChartYTickOptionsShowLabel(String chartYTickOptionsShowLabel) {
		this.chartYTickOptionsShowLabel = chartYTickOptionsShowLabel;
	}
	public String getChartYTickOptionsShowLabel() {
		return chartYTickOptionsShowLabel;
	}
	public void setChartYTickOptionsFormatter(String chartYTickOptionsFormatter) {
		this.chartYTickOptionsFormatter = chartYTickOptionsFormatter;
	}
	public String getChartYTickOptionsFormatter() {
		return chartYTickOptionsFormatter;
	}
	public void setChartYTickOptionsFormatString(
			String chartYTickOptionsFormatString) {
		this.chartYTickOptionsFormatString = chartYTickOptionsFormatString;
	}
	public String getChartYTickOptionsFormatString() {
		return chartYTickOptionsFormatString;
	}
	public void setChartYTickOptionsFontFamily(
			String chartYTickOptionsFontFamily) {
		this.chartYTickOptionsFontFamily = chartYTickOptionsFontFamily;
	}
	public String getChartYTickOptionsFontFamily() {
		return chartYTickOptionsFontFamily;
	}
	public void setChartYTickOptionsFontSize(String chartYTickOptionsFontSize) {
		this.chartYTickOptionsFontSize = chartYTickOptionsFontSize;
	}
	public String getChartYTickOptionsFontSize() {
		return chartYTickOptionsFontSize;
	}
	public void setChartYTickOptionsTextColor(String chartYTickOptionsTextColor) {
		this.chartYTickOptionsTextColor = chartYTickOptionsTextColor;
	}
	public String getChartYTickOptionsTextColor() {
		return chartYTickOptionsTextColor;
	}
	public void setChartXLabelOptionsAngle(String chartXLabelOptionsAngle) {
		this.chartXLabelOptionsAngle = chartXLabelOptionsAngle;
	}
	public String getChartXLabelOptionsAngle() {
		return chartXLabelOptionsAngle;
	}
	public void setChartXLabelOptionsShow(String chartXLabelOptionsShow) {
		this.chartXLabelOptionsShow = chartXLabelOptionsShow;
	}
	public String getChartXLabelOptionsShow() {
		return chartXLabelOptionsShow;
	}
	public void setChartXLabelOptionsShowLabel(
			String chartXLabelOptionsShowLabel) {
		this.chartXLabelOptionsShowLabel = chartXLabelOptionsShowLabel;
	}
	public String getChartXLabelOptionsShowLabel() {
		return chartXLabelOptionsShowLabel;
	}
	public void setChartXLabelOptionsLabel(String chartXLabelOptionsLabel) {
		this.chartXLabelOptionsLabel = chartXLabelOptionsLabel;
	}
	public String getChartXLabelOptionsLabel() {
		return chartXLabelOptionsLabel;
	}
	public void setChartXLabelOptionsFontFamily(
			String chartXLabelOptionsFontFamily) {
		this.chartXLabelOptionsFontFamily = chartXLabelOptionsFontFamily;
	}
	public String getChartXLabelOptionsFontFamily() {
		return chartXLabelOptionsFontFamily;
	}
	public void setChartXLabelOptionsFontSize(String chartXLabelOptionsFontSize) {
		this.chartXLabelOptionsFontSize = chartXLabelOptionsFontSize;
	}
	public String getChartXLabelOptionsFontSize() {
		return chartXLabelOptionsFontSize;
	}
	public void setChartXLabelOptionsFontWeight(
			String chartXLabelOptionsFontWeight) {
		this.chartXLabelOptionsFontWeight = chartXLabelOptionsFontWeight;
	}
	public String getChartXLabelOptionsFontWeight() {
		return chartXLabelOptionsFontWeight;
	}
	public void setChartXLabelOptionsFontStretch(
			String chartXLabelOptionsFontStretch) {
		this.chartXLabelOptionsFontStretch = chartXLabelOptionsFontStretch;
	}
	public String getChartXLabelOptionsFontStretch() {
		return chartXLabelOptionsFontStretch;
	}
	public void setChartXLabelOptionsTextColor(
			String chartXLabelOptionsTextColor) {
		this.chartXLabelOptionsTextColor = chartXLabelOptionsTextColor;
	}
	public String getChartXLabelOptionsTextColor() {
		return chartXLabelOptionsTextColor;
	}
	public void setChartXLabelOptionsEnableFontSupport(
			String chartXLabelOptionsEnableFontSupport) {
		this.chartXLabelOptionsEnableFontSupport = chartXLabelOptionsEnableFontSupport;
	}
	public String getChartXLabelOptionsEnableFontSupport() {
		return chartXLabelOptionsEnableFontSupport;
	}
	public void setChartXLabelOptionsPt2px(String chartXLabelOptionsPt2px) {
		this.chartXLabelOptionsPt2px = chartXLabelOptionsPt2px;
	}
	public String getChartXLabelOptionsPt2px() {
		return chartXLabelOptionsPt2px;
	}
	public void setChartYLabelOptionsAngle(String chartYLabelOptionsAngle) {
		this.chartYLabelOptionsAngle = chartYLabelOptionsAngle;
	}
	public String getChartYLabelOptionsAngle() {
		return chartYLabelOptionsAngle;
	}
	public void setChartYLabelOptionsShow(String chartYLabelOptionsShow) {
		this.chartYLabelOptionsShow = chartYLabelOptionsShow;
	}
	public String getChartYLabelOptionsShow() {
		return chartYLabelOptionsShow;
	}
	public void setChartYLabelOptionsShowLabel(
			String chartYLabelOptionsShowLabel) {
		this.chartYLabelOptionsShowLabel = chartYLabelOptionsShowLabel;
	}
	public String getChartYLabelOptionsShowLabel() {
		return chartYLabelOptionsShowLabel;
	}
	public void setChartYLabelOptionsLabel(String chartYLabelOptionsLabel) {
		this.chartYLabelOptionsLabel = chartYLabelOptionsLabel;
	}
	public String getChartYLabelOptionsLabel() {
		return chartYLabelOptionsLabel;
	}
	public void setChartYLabelOptionsFontFamily(
			String chartYLabelOptionsFontFamily) {
		this.chartYLabelOptionsFontFamily = chartYLabelOptionsFontFamily;
	}
	public String getChartYLabelOptionsFontFamily() {
		return chartYLabelOptionsFontFamily;
	}
	public void setChartYLabelOptionsFontSize(String chartYLabelOptionsFontSize) {
		this.chartYLabelOptionsFontSize = chartYLabelOptionsFontSize;
	}
	public String getChartYLabelOptionsFontSize() {
		return chartYLabelOptionsFontSize;
	}
	public void setChartYLabelOptionsFontWeight(
			String chartYLabelOptionsFontWeight) {
		this.chartYLabelOptionsFontWeight = chartYLabelOptionsFontWeight;
	}
	public String getChartYLabelOptionsFontWeight() {
		return chartYLabelOptionsFontWeight;
	}
	public void setChartYLabelOptionsFontStretch(
			String chartYLabelOptionsFontStretch) {
		this.chartYLabelOptionsFontStretch = chartYLabelOptionsFontStretch;
	}
	public String getChartYLabelOptionsFontStretch() {
		return chartYLabelOptionsFontStretch;
	}
	public void setChartYLabelOptionsTextColor(
			String chartYLabelOptionsTextColor) {
		this.chartYLabelOptionsTextColor = chartYLabelOptionsTextColor;
	}
	public String getChartYLabelOptionsTextColor() {
		return chartYLabelOptionsTextColor;
	}
	public void setChartYLabelOptionsEnableFontSupport(
			String chartYLabelOptionsEnableFontSupport) {
		this.chartYLabelOptionsEnableFontSupport = chartYLabelOptionsEnableFontSupport;
	}
	public String getChartYLabelOptionsEnableFontSupport() {
		return chartYLabelOptionsEnableFontSupport;
	}
	public void setChartYLabelOptionsPt2px(String chartYLabelOptionsPt2px) {
		this.chartYLabelOptionsPt2px = chartYLabelOptionsPt2px;
	}
	public String getChartYLabelOptionsPt2px() {
		return chartYLabelOptionsPt2px;
	}
	public void setChartHighlightShow(String chartHighlightShow) {
		this.chartHighlightShow = chartHighlightShow;
	}
	public String getChartHighlightShow() {
		return chartHighlightShow;
	}
	public void setChartHighlightMarkerRenderer(
			String chartHighlightMarkerRenderer) {
		this.chartHighlightMarkerRenderer = chartHighlightMarkerRenderer;
	}
	public String getChartHighlightMarkerRenderer() {
		return chartHighlightMarkerRenderer;
	}
	public void setChartHighlightShowMarker(String chartHighlightShowMarker) {
		this.chartHighlightShowMarker = chartHighlightShowMarker;
	}
	public String getChartHighlightShowMarker() {
		return chartHighlightShowMarker;
	}
	public void setChartHighlightLineWidthAdjust(
			String chartHighlightLineWidthAdjust) {
		this.chartHighlightLineWidthAdjust = chartHighlightLineWidthAdjust;
	}
	public String getChartHighlightLineWidthAdjust() {
		return chartHighlightLineWidthAdjust;
	}
	public void setChartHighlightSizeAdjust(String chartHighlightSizeAdjust) {
		this.chartHighlightSizeAdjust = chartHighlightSizeAdjust;
	}
	public String getChartHighlightSizeAdjust() {
		return chartHighlightSizeAdjust;
	}
	public void setChartHighlightShowTooltip(String chartHighlightShowTooltip) {
		this.chartHighlightShowTooltip = chartHighlightShowTooltip;
	}
	public String getChartHighlightShowTooltip() {
		return chartHighlightShowTooltip;
	}
	public void setChartHighlightTooltipLocation(
			String chartHighlightTooltipLocation) {
		this.chartHighlightTooltipLocation = chartHighlightTooltipLocation;
	}
	public String getChartHighlightTooltipLocation() {
		return chartHighlightTooltipLocation;
	}
	public void setChartHighlightTooltipFade(String chartHighlightTooltipFade) {
		this.chartHighlightTooltipFade = chartHighlightTooltipFade;
	}
	public String getChartHighlightTooltipFade() {
		return chartHighlightTooltipFade;
	}
	public void setChartHighlightTooltipFadeSpeed(
			String chartHighlightTooltipFadeSpeed) {
		this.chartHighlightTooltipFadeSpeed = chartHighlightTooltipFadeSpeed;
	}
	public String getChartHighlightTooltipFadeSpeed() {
		return chartHighlightTooltipFadeSpeed;
	}
	public void setChartHighlightTooltipOffset(
			String chartHighlightTooltipOffset) {
		this.chartHighlightTooltipOffset = chartHighlightTooltipOffset;
	}
	public String getChartHighlightTooltipOffset() {
		return chartHighlightTooltipOffset;
	}
	public void setChartHighlightTooltipAxes(String chartHighlightTooltipAxes) {
		this.chartHighlightTooltipAxes = chartHighlightTooltipAxes;
	}
	public String getChartHighlightTooltipAxes() {
		return chartHighlightTooltipAxes;
	}
	public void setChartHighlightUseAxesFormatters(
			String chartHighlightUseAxesFormatters) {
		this.chartHighlightUseAxesFormatters = chartHighlightUseAxesFormatters;
	}
	public String getChartHighlightUseAxesFormatters() {
		return chartHighlightUseAxesFormatters;
	}
	public void setChartHighlightTooltipFormatString(
			String chartHighlightTooltipFormatString) {
		this.chartHighlightTooltipFormatString = chartHighlightTooltipFormatString;
	}
	public String getChartHighlightTooltipFormatString() {
		return chartHighlightTooltipFormatString;
	}
	public void setChartHighlightFormatString(String chartHighlightFormatString) {
		this.chartHighlightFormatString = chartHighlightFormatString;
	}
	public String getChartHighlightFormatString() {
		return chartHighlightFormatString;
	}
	public void setChartHighlightYvalues(String chartHighlightYvalues) {
		this.chartHighlightYvalues = chartHighlightYvalues;
	}
	public String getChartHighlightYvalues() {
		return chartHighlightYvalues;
	}
	public void setChartCursorStyle(String chartCursorStyle) {
		this.chartCursorStyle = chartCursorStyle;
	}
	public String getChartCursorStyle() {
		return chartCursorStyle;
	}
	public void setChartCursorShow(String chartCursorShow) {
		this.chartCursorShow = chartCursorShow;
	}
	public String getChartCursorShow() {
		return chartCursorShow;
	}
	public void setChartCursorShowTooltip(String chartCursorShowTooltip) {
		this.chartCursorShowTooltip = chartCursorShowTooltip;
	}
	public String getChartCursorShowTooltip() {
		return chartCursorShowTooltip;
	}
	public void setChartCursorFollowMouse(String chartCursorFollowMouse) {
		this.chartCursorFollowMouse = chartCursorFollowMouse;
	}
	public String getChartCursorFollowMouse() {
		return chartCursorFollowMouse;
	}
	public void setChartCursorTooltipLocation(String chartCursorTooltipLocation) {
		this.chartCursorTooltipLocation = chartCursorTooltipLocation;
	}
	public String getChartCursorTooltipLocation() {
		return chartCursorTooltipLocation;
	}
	public void setChartCursorTooltipOffset(String chartCursorTooltipOffset) {
		this.chartCursorTooltipOffset = chartCursorTooltipOffset;
	}
	public String getChartCursorTooltipOffset() {
		return chartCursorTooltipOffset;
	}
	public void setChartCursorShowTooltipGridPosition(
			String chartCursorShowTooltipGridPosition) {
		this.chartCursorShowTooltipGridPosition = chartCursorShowTooltipGridPosition;
	}
	public String getChartCursorShowTooltipGridPosition() {
		return chartCursorShowTooltipGridPosition;
	}
	public void setChartCursorShowTooltipUnitPosition(
			String chartCursorShowTooltipUnitPosition) {
		this.chartCursorShowTooltipUnitPosition = chartCursorShowTooltipUnitPosition;
	}
	public String getChartCursorShowTooltipUnitPosition() {
		return chartCursorShowTooltipUnitPosition;
	}
	public void setChartCursorShowTooltipDataPosition(
			String chartCursorShowTooltipDataPosition) {
		this.chartCursorShowTooltipDataPosition = chartCursorShowTooltipDataPosition;
	}
	public String getChartCursorShowTooltipDataPosition() {
		return chartCursorShowTooltipDataPosition;
	}
	public void setChartCursorTooltipFormatString(
			String chartCursorTooltipFormatString) {
		this.chartCursorTooltipFormatString = chartCursorTooltipFormatString;
	}
	public String getChartCursorTooltipFormatString() {
		return chartCursorTooltipFormatString;
	}
	public void setChartCursorUseAxesFormatters(
			String chartCursorUseAxesFormatters) {
		this.chartCursorUseAxesFormatters = chartCursorUseAxesFormatters;
	}
	public String getChartCursorUseAxesFormatters() {
		return chartCursorUseAxesFormatters;
	}
	public void setChartCursorTooltipAxisGroups(
			String chartCursorTooltipAxisGroups) {
		this.chartCursorTooltipAxisGroups = chartCursorTooltipAxisGroups;
	}
	public String getChartCursorTooltipAxisGroups() {
		return chartCursorTooltipAxisGroups;
	}
	public void setChartCursorZoom(String chartCursorZoom) {
		this.chartCursorZoom = chartCursorZoom;
	}
	public String getChartCursorZoom() {
		return chartCursorZoom;
	}
	public void setChartCursorClickReset(String chartCursorClickReset) {
		this.chartCursorClickReset = chartCursorClickReset;
	}
	public String getChartCursorClickReset() {
		return chartCursorClickReset;
	}
	public void setChartCursorDblClickReset(String chartCursorDblClickReset) {
		this.chartCursorDblClickReset = chartCursorDblClickReset;
	}
	public String getChartCursorDblClickReset() {
		return chartCursorDblClickReset;
	}
	public void setChartCursorShowVerticalLine(
			String chartCursorShowVerticalLine) {
		this.chartCursorShowVerticalLine = chartCursorShowVerticalLine;
	}
	public String getChartCursorShowVerticalLine() {
		return chartCursorShowVerticalLine;
	}
	public void setChartCursorShowHorizontalLine(
			String chartCursorShowHorizontalLine) {
		this.chartCursorShowHorizontalLine = chartCursorShowHorizontalLine;
	}
	public String getChartCursorShowHorizontalLine() {
		return chartCursorShowHorizontalLine;
	}
	public void setChartCursorConstrainZoomTo(String chartCursorConstrainZoomTo) {
		this.chartCursorConstrainZoomTo = chartCursorConstrainZoomTo;
	}
	public String getChartCursorConstrainZoomTo() {
		return chartCursorConstrainZoomTo;
	}
	public void setChartCursorIntersectionThreshold(
			String chartCursorIntersectionThreshold) {
		this.chartCursorIntersectionThreshold = chartCursorIntersectionThreshold;
	}
	public String getChartCursorIntersectionThreshold() {
		return chartCursorIntersectionThreshold;
	}
	public void setChartCursorShowCursorLegend(
			String chartCursorShowCursorLegend) {
		this.chartCursorShowCursorLegend = chartCursorShowCursorLegend;
	}
	public String getChartCursorShowCursorLegend() {
		return chartCursorShowCursorLegend;
	}
	public void setChartCursorCursorLegendFormatString(
			String chartCursorCursorLegendFormatString) {
		this.chartCursorCursorLegendFormatString = chartCursorCursorLegendFormatString;
	}
	public String getChartCursorCursorLegendFormatString() {
		return chartCursorCursorLegendFormatString;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getStyle() {
		return style;
	}
	public void setStackSeries(String stackSeries) {
		this.stackSeries = stackSeries;
	}
	public String getStackSeries() {
		return stackSeries;
	}
	public void setChartXTickOptionsAngle(String chartXTickOptionsAngle) {
		this.chartXTickOptionsAngle = chartXTickOptionsAngle;
	}
	public String getChartXTickOptionsAngle() {
		return chartXTickOptionsAngle;
	}
	public void setChartYTickOptionsAngle(String chartYTickOptionsAngle) {
		this.chartYTickOptionsAngle = chartYTickOptionsAngle;
	}
	public String getChartYTickOptionsAngle() {
		return chartYTickOptionsAngle;
	}
	public void setChartXTickOptionsLabelPosition(
			String chartXTickOptionsLabelPosition) {
		this.chartXTickOptionsLabelPosition = chartXTickOptionsLabelPosition;
	}
	public String getChartXTickOptionsLabelPosition() {
		return chartXTickOptionsLabelPosition;
	}
	public void setChartXTickOptionsFontWeight(
			String chartXTickOptionsFontWeight) {
		this.chartXTickOptionsFontWeight = chartXTickOptionsFontWeight;
	}
	public String getChartXTickOptionsFontWeight() {
		return chartXTickOptionsFontWeight;
	}
	public void setChartXTickOptionsFontStretch(
			String chartXTickOptionsFontStretch) {
		this.chartXTickOptionsFontStretch = chartXTickOptionsFontStretch;
	}
	public String getChartXTickOptionsFontStretch() {
		return chartXTickOptionsFontStretch;
	}
	public void setChartXTickOptionsEnableFontSupport(
			String chartXTickOptionsEnableFontSupport) {
		this.chartXTickOptionsEnableFontSupport = chartXTickOptionsEnableFontSupport;
	}
	public String getChartXTickOptionsEnableFontSupport() {
		return chartXTickOptionsEnableFontSupport;
	}
	public void setChartXTickOptionsPt2px(String chartXTickOptionsPt2px) {
		this.chartXTickOptionsPt2px = chartXTickOptionsPt2px;
	}
	public String getChartXTickOptionsPt2px() {
		return chartXTickOptionsPt2px;
	}
	public void setChartYTickOptionsLabelPosition(
			String chartYTickOptionsLabelPosition) {
		this.chartYTickOptionsLabelPosition = chartYTickOptionsLabelPosition;
	}
	public String getChartYTickOptionsLabelPosition() {
		return chartYTickOptionsLabelPosition;
	}
	public void setChartYTickOptionsFontWeight(
			String chartYTickOptionsFontWeight) {
		this.chartYTickOptionsFontWeight = chartYTickOptionsFontWeight;
	}
	public String getChartYTickOptionsFontWeight() {
		return chartYTickOptionsFontWeight;
	}
	public void setChartYTickOptionsFontStretch(
			String chartYTickOptionsFontStretch) {
		this.chartYTickOptionsFontStretch = chartYTickOptionsFontStretch;
	}
	public String getChartYTickOptionsFontStretch() {
		return chartYTickOptionsFontStretch;
	}
	public void setChartYTickOptionsEnableFontSupport(
			String chartYTickOptionsEnableFontSupport) {
		this.chartYTickOptionsEnableFontSupport = chartYTickOptionsEnableFontSupport;
	}
	public String getChartYTickOptionsEnableFontSupport() {
		return chartYTickOptionsEnableFontSupport;
	}
	public void setChartYTickOptionsPt2px(String chartYTickOptionsPt2px) {
		this.chartYTickOptionsPt2px = chartYTickOptionsPt2px;
	}
	public String getChartYTickOptionsPt2px() {
		return chartYTickOptionsPt2px;
	}
}