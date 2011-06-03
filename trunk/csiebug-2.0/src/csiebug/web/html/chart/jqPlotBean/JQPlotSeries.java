/**
 * 
 */
package csiebug.web.html.chart.jqPlotBean;

/**
 * @author George_Tsai
 * @version 2010/5/29
 */
public class JQPlotSeries {
	public static final String RENDERER_LINE = "$.jqplot.LineRenderer";
	public static final String RENDERER_PIE = "$.jqplot.PieRenderer";
	public static final String RENDERER_BAR = "$.jqplot.BarRenderer";
	public static final String MARKER_RENDER = "$.jqplot.MarkerRenderer";
	public static final String XAXIS = "xaxis";
	public static final String X2AXIS = "x2axis";
	public static final String YAXIS = "yaxis";
	public static final String Y2AXIS = "y2axis";
	public static final String FILL_AXIS_X = "x";
	public static final String FILL_AXIS_Y = "y";
	
	private Boolean show;	//wether or not to draw the series.
	private String xaxis;	//which x axis to use with this series, either ‘xaxis’ or ‘x2axis’.
	private String yaxis;	//which y axis to use with this series, either ‘yaxis’ or ‘y2axis’.
	private String renderer;	//A class of a renderer which will draw the series, see $.jqplot.LineRenderer.
	private JQPlotOptions rendererOptions;	//Options to pass on to the renderer.
	private String label;	//Line label to use in the legend.
	private Boolean showLabel;	//true to show label for this series in the legend.
	private String color;	//css color spec for the series
	private Float lineWidth;	//width of the line in pixels.
	private Boolean shadow;	//wether or not to draw a shadow on the line
	private Integer shadowAngle;	//Shadow angle in degrees
	private Float shadowOffset;	//Shadow offset from line in pixels
	private Integer shadowDepth;	//Number of times shadow is stroked, each stroke offset shadowOffset from the last.
	private Float shadowAlpha;	//Alpha channel transparency of shadow.
	private Boolean breakOnNull;	//Not implemented.
	private String markerRenderer;	//A class of a renderer which will draw marker (e.g.
	private JQPlotMarkerRenderer markerOptions;	//renderer specific options to pass to the markerRenderer, see $.jqplot.MarkerRenderer.
	private Boolean showLine;	//wether to actually draw the line or not.
	private Boolean showMarker;	//wether or not to show the markers at the data points.
	private String index;	//0 based index of this series in the plot series array.
	private Boolean fill;	//true or false, wether to fill under lines or in bars.
	private String fillColor;	//CSS color spec to use for fill under line.
	private String fillAlpha;	//Alpha transparency to apply to the fill under the line.
	private Boolean fillAndStroke;	//If true will stroke the line (with color this.color) as well as fill under it.
	private Boolean disableStack;	//true to not stack this series with other series in the plot.
	private Integer neighborThreshold;	//how close or far (in pixels) the cursor must be from a point marker to detect the point.
	private Boolean fillToZero;	//true will force bar and filled series to fill toward zero on the fill Axis.
	private String fillAxis;	//Either ‘x’ or ‘y’.
	private Boolean useNegativeColors;	//true to color negative values differently in filled and bar charts.
	private JQPlotPointLabels pointLabels;
	
	public void setShow(Boolean show) {
		this.show = show;
	}
	public Boolean getShow() {
		return show;
	}
	public void setXaxis(String xaxis) {
		this.xaxis = xaxis;
	}
	public String getXaxis() {
		return xaxis;
	}
	public void setYaxis(String yaxis) {
		this.yaxis = yaxis;
	}
	public String getYaxis() {
		return yaxis;
	}
	public void setRenderer(String renderer) {
		this.renderer = renderer;
	}
	public String getRenderer() {
		return renderer;
	}
	public void setRendererOptions(JQPlotOptions rendererOptions) {
		this.rendererOptions = rendererOptions;
	}
	public JQPlotOptions getRendererOptions() {
		return rendererOptions;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getLabel() {
		return label;
	}
	public void setShowLabel(Boolean showLabel) {
		this.showLabel = showLabel;
	}
	public Boolean getShowLabel() {
		return showLabel;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getColor() {
		return color;
	}
	public void setLineWidth(Float lineWidth) {
		this.lineWidth = lineWidth;
	}
	public Float getLineWidth() {
		return lineWidth;
	}
	public void setShadow(Boolean shadow) {
		this.shadow = shadow;
	}
	public Boolean getShadow() {
		return shadow;
	}
	public void setShadowAngle(Integer shadowAngle) {
		this.shadowAngle = shadowAngle;
	}
	public Integer getShadowAngle() {
		return shadowAngle;
	}
	public void setShadowOffset(Float shadowOffset) {
		this.shadowOffset = shadowOffset;
	}
	public Float getShadowOffset() {
		return shadowOffset;
	}
	public void setShadowDepth(Integer shadowDepth) {
		this.shadowDepth = shadowDepth;
	}
	public Integer getShadowDepth() {
		return shadowDepth;
	}
	public void setShadowAlpha(Float shadowAlpha) {
		this.shadowAlpha = shadowAlpha;
	}
	public Float getShadowAlpha() {
		return shadowAlpha;
	}
	public void setBreakOnNull(Boolean breakOnNull) {
		this.breakOnNull = breakOnNull;
	}
	public Boolean getBreakOnNull() {
		return breakOnNull;
	}
	public void setMarkerRenderer(String markerRenderer) {
		this.markerRenderer = markerRenderer;
	}
	public String getMarkerRenderer() {
		return markerRenderer;
	}
	public void setMarkerOptions(JQPlotMarkerRenderer markerOptions) {
		this.markerOptions = markerOptions;
	}
	public JQPlotMarkerRenderer getMarkerOptions() {
		return markerOptions;
	}
	public void setShowLine(Boolean showLine) {
		this.showLine = showLine;
	}
	public Boolean getShowLine() {
		return showLine;
	}
	public void setShowMarker(Boolean showMarker) {
		this.showMarker = showMarker;
	}
	public Boolean getShowMarker() {
		return showMarker;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getIndex() {
		return index;
	}
	public void setFill(Boolean fill) {
		this.fill = fill;
	}
	public Boolean getFill() {
		return fill;
	}
	public void setFillColor(String fillColor) {
		this.fillColor = fillColor;
	}
	public String getFillColor() {
		return fillColor;
	}
	public void setFillAlpha(String fillAlpha) {
		this.fillAlpha = fillAlpha;
	}
	public String getFillAlpha() {
		return fillAlpha;
	}
	public void setFillAndStroke(Boolean fillAndStroke) {
		this.fillAndStroke = fillAndStroke;
	}
	public Boolean getFillAndStroke() {
		return fillAndStroke;
	}
	public void setDisableStack(Boolean disableStack) {
		this.disableStack = disableStack;
	}
	public Boolean getDisableStack() {
		return disableStack;
	}
	public void setNeighborThreshold(Integer neighborThreshold) {
		this.neighborThreshold = neighborThreshold;
	}
	public Integer getNeighborThreshold() {
		return neighborThreshold;
	}
	public void setFillToZero(Boolean fillToZero) {
		this.fillToZero = fillToZero;
	}
	public Boolean getFillToZero() {
		return fillToZero;
	}
	public void setFillAxis(String fillAxis) {
		this.fillAxis = fillAxis;
	}
	public String getFillAxis() {
		return fillAxis;
	}
	public void setUseNegativeColors(Boolean useNegativeColors) {
		this.useNegativeColors = useNegativeColors;
	}
	public Boolean getUseNegativeColors() {
		return useNegativeColors;
	}
	public void setPointLabels(JQPlotPointLabels pointLabels) {
		this.pointLabels = pointLabels;
	}
	public JQPlotPointLabels getPointLabels() {
		return pointLabels;
	}
}
