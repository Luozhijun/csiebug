/**
 * 
 */
package csiebug.web.html.chart.jqPlotBean;

import java.util.List;

/**
 * @author George_Tsai
 * @version 2010/5/31
 */
public class JQPlotAxis implements JQPlotOptions {
	public static final String TICK_RENDERER = "$.jqplot.AxisTickRenderer";
	public static final String LABEL_RENDERER = "$.jqplot.AxisLabelRenderer";
	public static final String RENDERER = "$.jqplot.LinearAxisRenderer";
	
	private Boolean show;	//Wether to display the axis on the graph.
	private String tickRenderer;	//A class of a rendering engine for creating the ticks labels displayed on the plot, See $.jqplot.AxisTickRenderer.
	private JQPlotAxisTickRenderer tickOptions;	//Options that will be passed to the tickRenderer, see $.jqplot.AxisTickRenderer options.
	private String labelRenderer;	//A class of a rendering engine for creating an axis label.
	private JQPlotCanvasAxisLabelRenderer labelOptions;	//Options passed to the label renderer.
	private String label;	//Label for the axis
	private Boolean showLabel;	//true to show the axis label.
	private String min;	//minimum value of the axis (in data units, not pixels).
	private String max;	//maximum value of the axis (in data units, not pixels).
	private Boolean autoscale;	//Autoscale the axis min and max values to provide sensible tick spacing.
	private Float pad;	//Padding to extend the range above and below the data bounds.
	private String padMax;	//Padding to extend the range above data bounds.
	private String padMin;	//Padding to extend the range below data bounds.
	private List<?> ticks;	//1D [val, val, ...] or 2D [[val, label], [val, label], ...] array of ticks for the axis.
	private String numberTicks;	//Desired number of ticks.
	private String tickInterval;	//number of units between ticks.
	private String renderer;	//A class of a rendering engine that handles tick generation, scaling input data to pixel grid units and drawing the axis element.
	private String rendererOptions;	//renderer specific options.
	private Boolean showTicks;	//wether to show the ticks (both marks and labels) or not.
	private Boolean showTickMarks;	//wether to show the tick marks (line crossing grid) or not.
	private Boolean showMinorTicks;	//Wether or not to show minor ticks.
	private Boolean useSeriesColor;	//Use the color of the first series associated with this axis for the tick marks and line bordering this axis.
	private String borderWidth;	//width of line stroked at the border of the axis.
	private String borderColor;	//color of the border adjacent to the axis.
	private String syncTicks;	//true to try and synchronize tick spacing across multiple axes so that ticks and grid lines line up.
	private Integer tickSpacing;	//Approximate pixel spacing between ticks on graph. 
	
	public void setShow(Boolean show) {
		this.show = show;
	}
	public Boolean getShow() {
		return show;
	}
	public void setTickRenderer(String tickRenderer) {
		this.tickRenderer = tickRenderer;
	}
	public String getTickRenderer() {
		return tickRenderer;
	}
	public void setTickOptions(JQPlotAxisTickRenderer tickOptions) {
		this.tickOptions = tickOptions;
	}
	public JQPlotAxisTickRenderer getTickOptions() {
		return tickOptions;
	}
	public void setLabelRenderer(String labelRenderer) {
		this.labelRenderer = labelRenderer;
	}
	public String getLabelRenderer() {
		return labelRenderer;
	}
	public void setLabelOptions(JQPlotCanvasAxisLabelRenderer labelOptions) {
		this.labelOptions = labelOptions;
	}
	public JQPlotCanvasAxisLabelRenderer getLabelOptions() {
		return labelOptions;
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
	public void setMin(String min) {
		this.min = min;
	}
	public String getMin() {
		return min;
	}
	public void setMax(String max) {
		this.max = max;
	}
	public String getMax() {
		return max;
	}
	public void setAutoscale(Boolean autoscale) {
		this.autoscale = autoscale;
	}
	public Boolean getAutoscale() {
		return autoscale;
	}
	public void setPad(Float pad) {
		this.pad = pad;
	}
	public Float getPad() {
		return pad;
	}
	public void setPadMax(String padMax) {
		this.padMax = padMax;
	}
	public String getPadMax() {
		return padMax;
	}
	public void setPadMin(String padMin) {
		this.padMin = padMin;
	}
	public String getPadMin() {
		return padMin;
	}
	public void setTicks(List<?> ticks) {
		this.ticks = ticks;
	}
	public List<?> getTicks() {
		return ticks;
	}
	public void setNumberTicks(String numberTicks) {
		this.numberTicks = numberTicks;
	}
	public String getNumberTicks() {
		return numberTicks;
	}
	public void setTickInterval(String tickInterval) {
		this.tickInterval = tickInterval;
	}
	public String getTickInterval() {
		return tickInterval;
	}
	public void setRenderer(String renderer) {
		this.renderer = renderer;
	}
	public String getRenderer() {
		return renderer;
	}
	public void setRendererOptions(String rendererOptions) {
		this.rendererOptions = rendererOptions;
	}
	public String getRendererOptions() {
		return rendererOptions;
	}
	public void setShowTicks(Boolean showTicks) {
		this.showTicks = showTicks;
	}
	public Boolean getShowTicks() {
		return showTicks;
	}
	public void setShowTickMarks(Boolean showTickMarks) {
		this.showTickMarks = showTickMarks;
	}
	public Boolean getShowTickMarks() {
		return showTickMarks;
	}
	public void setShowMinorTicks(Boolean showMinorTicks) {
		this.showMinorTicks = showMinorTicks;
	}
	public Boolean getShowMinorTicks() {
		return showMinorTicks;
	}
	public void setUseSeriesColor(Boolean useSeriesColor) {
		this.useSeriesColor = useSeriesColor;
	}
	public Boolean getUseSeriesColor() {
		return useSeriesColor;
	}
	public void setBorderWidth(String borderWidth) {
		this.borderWidth = borderWidth;
	}
	public String getBorderWidth() {
		return borderWidth;
	}
	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}
	public String getBorderColor() {
		return borderColor;
	}
	public void setSyncTicks(String syncTicks) {
		this.syncTicks = syncTicks;
	}
	public String getSyncTicks() {
		return syncTicks;
	}
	public void setTickSpacing(Integer tickSpacing) {
		this.tickSpacing = tickSpacing;
	}
	public Integer getTickSpacing() {
		return tickSpacing;
	}
}
