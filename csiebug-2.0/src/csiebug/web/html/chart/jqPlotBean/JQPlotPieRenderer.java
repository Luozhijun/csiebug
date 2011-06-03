/**
 * 
 */
package csiebug.web.html.chart.jqPlotBean;

/**
 * @author George_Tsai
 * @version 2010/6/4
 */
public class JQPlotPieRenderer implements JQPlotOptions {
	private String diameter;	//diameter of the pie, auto computed by default
	private Integer padding;	//padding between the pie and plot edges, legend, etc.
	private Integer sliceMargin;	//pixels spacing between pie slices.
	private Boolean fill;	//true or false, wether to fil the slices.
	private Integer shadowOffset;	//offset of the shadow from the slice and offset of each succesive stroke of the shadow from the last.
	private Float shadowAlpha;	//transparency of the shadow (0 = transparent, 1 = opaque)
	private Integer shadowDepth;	//number of strokes to apply to the shadow, each stroke offset shadowOffset from the last.
	
	public void setDiameter(String diameter) {
		this.diameter = diameter;
	}
	public String getDiameter() {
		return diameter;
	}
	public void setPadding(Integer padding) {
		this.padding = padding;
	}
	public Integer getPadding() {
		return padding;
	}
	public void setSliceMargin(Integer sliceMargin) {
		this.sliceMargin = sliceMargin;
	}
	public Integer getSliceMargin() {
		return sliceMargin;
	}
	public void setFill(Boolean fill) {
		this.fill = fill;
	}
	public Boolean getFill() {
		return fill;
	}
	public void setShadowOffset(Integer shadowOffset) {
		this.shadowOffset = shadowOffset;
	}
	public Integer getShadowOffset() {
		return shadowOffset;
	}
	public void setShadowAlpha(Float shadowAlpha) {
		this.shadowAlpha = shadowAlpha;
	}
	public Float getShadowAlpha() {
		return shadowAlpha;
	}
	public void setShadowDepth(Integer shadowDepth) {
		this.shadowDepth = shadowDepth;
	}
	public Integer getShadowDepth() {
		return shadowDepth;
	}
}
