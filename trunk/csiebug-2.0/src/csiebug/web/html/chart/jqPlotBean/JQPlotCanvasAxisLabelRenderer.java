/**
 * 
 */
package csiebug.web.html.chart.jqPlotBean;

/**
 * @author George_Tsai
 * @version 2010/6/4
 */
public class JQPlotCanvasAxisLabelRenderer implements JQPlotOptions {
	private Integer angle;	//angle of text, measured clockwise from x axis.
	private Boolean show;	//wether or not to show the tick (mark and label).
	private Boolean showLabel;	//wether or not to show the label.
	private String label;	//label for the axis.
	private String fontFamily;	//CSS spec for the font-family css attribute.
	private String fontSize;	//CSS spec for font size.
	private String fontWeight;	
	private Float fontStretch;	//Multiplier to condense or expand font width.
	private String textColor;	//css spec for the color attribute.
	private Boolean enableFontSupport;	//true to turn on native canvas font support in Mozilla 3.5+ and Safari 4+.
	private Float pt2px;	//Point to pixel scaling factor, used for computing height of bounding box around a label. 
	
	public void setAngle(Integer angle) {
		this.angle = angle;
	}
	public Integer getAngle() {
		return angle;
	}
	public void setShow(Boolean show) {
		this.show = show;
	}
	public Boolean getShow() {
		return show;
	}
	public void setShowLabel(Boolean showLabel) {
		this.showLabel = showLabel;
	}
	public Boolean getShowLabel() {
		return showLabel;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getLabel() {
		return label;
	}
	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}
	public String getFontFamily() {
		return fontFamily;
	}
	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}
	public String getFontSize() {
		return fontSize;
	}
	public void setFontWeight(String fontWeight) {
		this.fontWeight = fontWeight;
	}
	public String getFontWeight() {
		return fontWeight;
	}
	public void setFontStretch(Float fontStretch) {
		this.fontStretch = fontStretch;
	}
	public Float getFontStretch() {
		return fontStretch;
	}
	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}
	public String getTextColor() {
		return textColor;
	}
	public void setEnableFontSupport(Boolean enableFontSupport) {
		this.enableFontSupport = enableFontSupport;
	}
	public Boolean getEnableFontSupport() {
		return enableFontSupport;
	}
	public void setPt2px(Float pt2px) {
		this.pt2px = pt2px;
	}
	public Float getPt2px() {
		return pt2px;
	}
}
