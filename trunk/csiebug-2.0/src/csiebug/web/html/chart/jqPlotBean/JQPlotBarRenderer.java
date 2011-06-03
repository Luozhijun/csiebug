/**
 * Copyright (c) 2010 George Tsai. All rights reserved.
 * For more information on the George Tsai, please see http://www.pixnet.net/blog/csiebug
 */
package csiebug.web.html.chart.jqPlotBean;

/**
 * @author George_Tsai
 * @version 2010/7/16
 */
public class JQPlotBarRenderer implements JQPlotOptions {
	public static final String BAR_DIRECTION_VERTICAL = "vertical";
	public static final String BAR_DIRECTION_HORIZONTAL = "horizontal";
	
	private Integer barPadding;	//Number of pixels between adjacent bars at the same axis value.
	private Integer barMargin;	//Number of pixels between groups of bars at adjacent axis values.
	private String barDirection;	//‘vertical’ = up and down bars, ‘horizontal’ = side to side bars
	private String barWidth;	//Width of the bar in pixels (auto by devaul).
	private Integer shadowOffset;	//offset of the shadow from the slice and offset of each succesive stroke of the shadow from the last.
	private Integer shadowDepth;	//number of strokes to apply to the shadow, each stroke offset shadowOffset from the last.
	private Float shadowAlpha;	//transparency of the shadow (0 = transparent, 1 = opaque)
	private Boolean waterfall;	//true to enable waterfall plot.
	private Boolean varyBarColor;	//true to color each bar separately.
	
	public void setBarPadding(Integer barPadding) {
		this.barPadding = barPadding;
	}
	public Integer getBarPadding() {
		return barPadding;
	}
	public void setBarMargin(Integer barMargin) {
		this.barMargin = barMargin;
	}
	public Integer getBarMargin() {
		return barMargin;
	}
	public void setBarDirection(String barDirection) {
		this.barDirection = barDirection;
	}
	public String getBarDirection() {
		return barDirection;
	}
	public void setBarWidth(String barWidth) {
		this.barWidth = barWidth;
	}
	public String getBarWidth() {
		return barWidth;
	}
	public void setShadowOffset(Integer shadowOffset) {
		this.shadowOffset = shadowOffset;
	}
	public Integer getShadowOffset() {
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
	public void setWaterfall(Boolean waterfall) {
		this.waterfall = waterfall;
	}
	public Boolean getWaterfall() {
		return waterfall;
	}
	public void setVaryBarColor(Boolean varyBarColor) {
		this.varyBarColor = varyBarColor;
	}
	public Boolean getVaryBarColor() {
		return varyBarColor;
	}
}
