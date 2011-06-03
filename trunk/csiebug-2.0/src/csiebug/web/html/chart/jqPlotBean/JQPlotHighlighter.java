/**
 * 
 */
package csiebug.web.html.chart.jqPlotBean;

/**
 * @author George_Tsai
 * @version 2010/5/28
 */
public class JQPlotHighlighter {
	public static final String LOCATION_NW = "nw";
	public static final String LOCATION_N = "n";
	public static final String LOCATION_NE = "ne";
	public static final String LOCATION_E = "e";
	public static final String LOCATION_SE = "se";
	public static final String LOCATION_S = "s";
	public static final String LOCATION_SW = "sw";
	public static final String LOCATION_W = "w";
	public static final String SPEED_SLOW = "slow";
	public static final String SPEED_DEF = "def";
	public static final String SPEED_FAST = "fast";
	public static final String AXES_X = "x";
	public static final String AXES_Y = "y";
	public static final String AXES_BOTH = "both";
	public static final String AXES_XY = "xy";
	public static final String AXES_YX = "yx";
	
	private Boolean show;	//true to show the highlight.
	private String markerRenderer;	//Renderer used to draw the marker of the highlighted point.
	private Boolean showMarker;	//true to show the marker
	private Float lineWidthAdjust;	//Pixels to add to the lineWidth of the highlight.
	private Float sizeAdjust;	//Pixels to add to the overall size of the highlight.
	private Boolean showTooltip;	//Show a tooltip with data point values.
	private String tooltipLocation;	//Where to position tooltip, ‘n’, ‘ne’, ‘e’, ‘se’, ‘s’, ‘sw’, ‘w’, ‘nw’
	private Boolean tooltipFade;	//true = fade in/out tooltip, flase = show/hide tooltip
	private String tooltipFadeSpeed;	//‘slow’, ‘def’, ‘fast’, or number of milliseconds.
	private Integer tooltipOffset;	//Pixel offset of tooltip from the highlight.
	private String tooltipAxes;	//Which axes to display in tooltip, ‘x’, ‘y’ or ‘both’, ‘xy’ or ‘yx’ ‘both’ and ‘xy’ are equivalent, ‘yx’ reverses order of labels.
	private Boolean useAxesFormatters;	//Use the x and y axes formatters to format the text in the tooltip.
	private String tooltipFormatString;	//sprintf format string for the tooltip.
	private String formatString;	//alternative to tooltipFormatString will format the whole tooltip text, populating with x, y values as indicated by tooltipAxes option.
	private Integer yvalues;	//Number of y values to expect in the data point array. 
	
	public void setShow(Boolean show) {
		this.show = show;
	}
	public Boolean getShow() {
		return show;
	}
	public void setMarkerRenderer(String markerRenderer) {
		this.markerRenderer = markerRenderer;
	}
	public String getMarkerRenderer() {
		return markerRenderer;
	}
	public void setShowMarker(Boolean showMarker) {
		this.showMarker = showMarker;
	}
	public Boolean getShowMarker() {
		return showMarker;
	}
	public void setLineWidthAdjust(Float lineWidthAdjust) {
		this.lineWidthAdjust = lineWidthAdjust;
	}
	public Float getLineWidthAdjust() {
		return lineWidthAdjust;
	}
	public void setSizeAdjust(Float sizeAdjust) {
		this.sizeAdjust = sizeAdjust;
	}
	public Float getSizeAdjust() {
		return sizeAdjust;
	}
	public void setShowTooltip(Boolean showTooltip) {
		this.showTooltip = showTooltip;
	}
	public Boolean getShowTooltip() {
		return showTooltip;
	}
	public void setTooltipLocation(String tooltipLocation) {
		this.tooltipLocation = tooltipLocation;
	}
	public String getTooltipLocation() {
		return tooltipLocation;
	}
	public void setTooltipFade(Boolean tooltipFade) {
		this.tooltipFade = tooltipFade;
	}
	public Boolean getTooltipFade() {
		return tooltipFade;
	}
	public void setTooltipFadeSpeed(String tooltipFadeSpeed) {
		this.tooltipFadeSpeed = tooltipFadeSpeed;
	}
	public String getTooltipFadeSpeed() {
		return tooltipFadeSpeed;
	}
	public void setTooltipOffset(Integer tooltipOffset) {
		this.tooltipOffset = tooltipOffset;
	}
	public Integer getTooltipOffset() {
		return tooltipOffset;
	}
	public void setTooltipAxes(String tooltipAxes) {
		this.tooltipAxes = tooltipAxes;
	}
	public String getTooltipAxes() {
		return tooltipAxes;
	}
	public void setUseAxesFormatters(Boolean useAxesFormatters) {
		this.useAxesFormatters = useAxesFormatters;
	}
	public Boolean getUseAxesFormatters() {
		return useAxesFormatters;
	}
	public void setTooltipFormatString(String tooltipFormatString) {
		this.tooltipFormatString = tooltipFormatString;
	}
	public String getTooltipFormatString() {
		return tooltipFormatString;
	}
	public void setFormatString(String formatString) {
		this.formatString = formatString;
	}
	public String getFormatString() {
		return formatString;
	}
	public void setYvalues(Integer yvalues) {
		this.yvalues = yvalues;
	}
	public Integer getYvalues() {
		return yvalues;
	}
}
