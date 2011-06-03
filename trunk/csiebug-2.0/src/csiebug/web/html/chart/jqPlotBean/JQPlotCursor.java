/**
 * Copyright (c) 2010 George Tsai. All rights reserved.
 * For more information on the George Tsai, please see http://www.pixnet.net/blog/csiebug
 */
package csiebug.web.html.chart.jqPlotBean;

import java.util.List;

/**
 * @author George_Tsai
 * @version 2010/6/29
 */
public class JQPlotCursor {
	public static final String LOCATION_NW = "nw";
	public static final String LOCATION_N = "n";
	public static final String LOCATION_NE = "ne";
	public static final String LOCATION_E = "e";
	public static final String LOCATION_SE = "se";
	public static final String LOCATION_S = "s";
	public static final String LOCATION_SW = "sw";
	public static final String LOCATION_W = "w";
	public static final String ZOOM_TO_NONE = "none";
	public static final String ZOOM_TO_X = "x";
	public static final String ZOOM_TO_Y = "y";
	
	private String style;	//CSS spec for cursor style
	private Boolean show;	//wether to show the cursor or not.
	private Boolean showTooltip;	//show a cursor position tooltip near the cursor
	private Boolean followMouse;	//Tooltip follows the mouse, it is not at a fixed location.
	private String tooltipLocation;	//Where to position tooltip.
	private Integer tooltipOffset;	//Pixel offset of tooltip from the grid boudaries or cursor center.
	private Boolean showTooltipGridPosition;	//show the grid pixel coordinates of the mouse.
	private Boolean showTooltipUnitPosition;	//show the unit (data) coordinates of the mouse.
	private Boolean showTooltipDataPosition;	//Used with showVerticalLine to show intersecting data points in the tooltip.
	private String tooltipFormatString;	//sprintf format string for the tooltip.
	private Boolean useAxesFormatters;	//Use the x and y axes formatters to format the text in the tooltip.
	private List<?> tooltipAxisGroups;	//Show position for the specified axes.
	private Boolean zoom;	//Enable plot zooming.
	private Boolean clickReset;	//Will reset plot zoom if single click on plot without drag.
	private Boolean dblClickReset;	//Will reset plot zoom if double click on plot without drag.
	private Boolean showVerticalLine;	//draw a vertical line across the plot which follows the cursor.
	private Boolean showHorizontalLine;	//draw a horizontal line across the plot which follows the cursor.
	private String constrainZoomTo;	//‘none’, ‘x’ or ‘y’
	private Integer intersectionThreshold;	//pixel distance from data point or marker to consider cursor lines intersecting with point.
	private Boolean showCursorLegend;	//Replace the plot legend with an enhanced legend displaying intersection information.
	private String cursorLegendFormatString;	//Format string used in the cursor legend. 
	
	public void setStyle(String style) {
		this.style = style;
	}
	public String getStyle() {
		return style;
	}
	public void setShow(Boolean show) {
		this.show = show;
	}
	public Boolean getShow() {
		return show;
	}
	public void setShowTooltip(Boolean showTooltip) {
		this.showTooltip = showTooltip;
	}
	public Boolean getShowTooltip() {
		return showTooltip;
	}
	public void setFollowMouse(Boolean followMouse) {
		this.followMouse = followMouse;
	}
	public Boolean getFollowMouse() {
		return followMouse;
	}
	public void setTooltipLocation(String tooltipLocation) {
		this.tooltipLocation = tooltipLocation;
	}
	public String getTooltipLocation() {
		return tooltipLocation;
	}
	public void setTooltipOffset(Integer tooltipOffset) {
		this.tooltipOffset = tooltipOffset;
	}
	public Integer getTooltipOffset() {
		return tooltipOffset;
	}
	public void setShowTooltipGridPosition(Boolean showTooltipGridPosition) {
		this.showTooltipGridPosition = showTooltipGridPosition;
	}
	public Boolean getShowTooltipGridPosition() {
		return showTooltipGridPosition;
	}
	public void setShowTooltipUnitPosition(Boolean showTooltipUnitPosition) {
		this.showTooltipUnitPosition = showTooltipUnitPosition;
	}
	public Boolean getShowTooltipUnitPosition() {
		return showTooltipUnitPosition;
	}
	public void setShowTooltipDataPosition(Boolean showTooltipDataPosition) {
		this.showTooltipDataPosition = showTooltipDataPosition;
	}
	public Boolean getShowTooltipDataPosition() {
		return showTooltipDataPosition;
	}
	public void setTooltipFormatString(String tooltipFormatString) {
		this.tooltipFormatString = tooltipFormatString;
	}
	public String getTooltipFormatString() {
		return tooltipFormatString;
	}
	public void setUseAxesFormatters(Boolean useAxesFormatters) {
		this.useAxesFormatters = useAxesFormatters;
	}
	public Boolean getUseAxesFormatters() {
		return useAxesFormatters;
	}
	public void setTooltipAxisGroups(List<?> tooltipAxisGroups) {
		this.tooltipAxisGroups = tooltipAxisGroups;
	}
	public List<?> getTooltipAxisGroups() {
		return tooltipAxisGroups;
	}
	public void setZoom(Boolean zoom) {
		this.zoom = zoom;
	}
	public Boolean getZoom() {
		return zoom;
	}
	public void setClickReset(Boolean clickReset) {
		this.clickReset = clickReset;
	}
	public Boolean getClickReset() {
		return clickReset;
	}
	public void setDblClickReset(Boolean dblClickReset) {
		this.dblClickReset = dblClickReset;
	}
	public Boolean getDblClickReset() {
		return dblClickReset;
	}
	public void setShowVerticalLine(Boolean showVerticalLine) {
		this.showVerticalLine = showVerticalLine;
	}
	public Boolean getShowVerticalLine() {
		return showVerticalLine;
	}
	public void setShowHorizontalLine(Boolean showHorizontalLine) {
		this.showHorizontalLine = showHorizontalLine;
	}
	public Boolean getShowHorizontalLine() {
		return showHorizontalLine;
	}
	public void setConstrainZoomTo(String constrainZoomTo) {
		this.constrainZoomTo = constrainZoomTo;
	}
	public String getConstrainZoomTo() {
		return constrainZoomTo;
	}
	public void setIntersectionThreshold(Integer intersectionThreshold) {
		this.intersectionThreshold = intersectionThreshold;
	}
	public Integer getIntersectionThreshold() {
		return intersectionThreshold;
	}
	public void setShowCursorLegend(Boolean showCursorLegend) {
		this.showCursorLegend = showCursorLegend;
	}
	public Boolean getShowCursorLegend() {
		return showCursorLegend;
	}
	public void setCursorLegendFormatString(String cursorLegendFormatString) {
		this.cursorLegendFormatString = cursorLegendFormatString;
	}
	public String getCursorLegendFormatString() {
		return cursorLegendFormatString;
	}
}
