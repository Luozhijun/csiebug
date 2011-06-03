/**
 * 
 */
package csiebug.web.html.chart.jqPlotBean;

import java.util.List;

/**
 * @author George_Tsai
 * @version 2010/5/30
 */
public class JQPlotPointLabels implements JQPlotOptions {
	public static final String LOCATION_NW = "nw";
	public static final String LOCATION_N = "n";
	public static final String LOCATION_NE = "ne";
	public static final String LOCATION_E = "e";
	public static final String LOCATION_SE = "se";
	public static final String LOCATION_S = "s";
	public static final String LOCATION_SW = "sw";
	public static final String LOCATION_W = "w";
	
	private String show;	//show the labels or not.
	private String location;	//compass location where to position the label around the point.
	private Boolean labelsFromSeries;	//true to use labels within data point arrays.
	private String seriesLabelIndex;	//array index for location of labels within data point arrays.
	private List<String> labels;	//array of arrays of labels, one array for each series.
	private Boolean stackedValue;	//true to display value as stacked in a stacked plot.
	private Integer ypadding;	//vertical padding in pixels between point and label
	private Integer xpadding;	//horizontal padding in pixels between point and label
	private Boolean escapeHTML;	//true to escape html entities in the labels.
	private Integer edgeTolerance;	//Number of pixels that the label must be away from an axis boundary in order to be drawn.
	private Boolean hideZeros;	//true to not show a label for a value which is 0.
	
	public void setShow(String show) {
		this.show = show;
	}
	public String getShow() {
		return show;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLocation() {
		return location;
	}
	public void setLabelsFromSeries(Boolean labelsFromSeries) {
		this.labelsFromSeries = labelsFromSeries;
	}
	public Boolean getLabelsFromSeries() {
		return labelsFromSeries;
	}
	public void setSeriesLabelIndex(String seriesLabelIndex) {
		this.seriesLabelIndex = seriesLabelIndex;
	}
	public String getSeriesLabelIndex() {
		return seriesLabelIndex;
	}
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
	public List<String> getLabels() {
		return labels;
	}
	public void setStackedValue(Boolean stackedValue) {
		this.stackedValue = stackedValue;
	}
	public Boolean getStackedValue() {
		return stackedValue;
	}
	public void setYpadding(Integer ypadding) {
		this.ypadding = ypadding;
	}
	public Integer getYpadding() {
		return ypadding;
	}
	public void setXpadding(Integer xpadding) {
		this.xpadding = xpadding;
	}
	public Integer getXpadding() {
		return xpadding;
	}
	public void setEscapeHTML(Boolean escapeHTML) {
		this.escapeHTML = escapeHTML;
	}
	public Boolean getEscapeHTML() {
		return escapeHTML;
	}
	public void setEdgeTolerance(Integer edgeTolerance) {
		this.edgeTolerance = edgeTolerance;
	}
	public Integer getEdgeTolerance() {
		return edgeTolerance;
	}
	public void setHideZeros(Boolean hideZeros) {
		this.hideZeros = hideZeros;
	}
	public Boolean getHideZeros() {
		return hideZeros;
	}
}
