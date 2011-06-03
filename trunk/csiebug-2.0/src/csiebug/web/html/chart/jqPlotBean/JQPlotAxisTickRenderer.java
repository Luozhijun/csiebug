/**
 * 
 */
package csiebug.web.html.chart.jqPlotBean;

/**
 * @author George_Tsai
 * @version 2010/6/1
 */
public class JQPlotAxisTickRenderer implements JQPlotOptions {
	public static final String MARK_INSIDE = "inside";
	public static final String MARK_OUTSIDE = "outside";
	public static final String MARK_CROSS = "cross";
	public static final String FORMATTER = "$.jqplot.DefaultTickFormatter";
	public static final String LABEL_POSITION_AUTO = "auto";
	public static final String LABEL_POSITION_START = "start";
	public static final String LABEL_POSITION_MIDDLE = "middle";
	public static final String LABEL_POSITION_END = "end";
	
	private String mark;	//tick mark on the axis.
	private Boolean showMark;	//wether or not to show the mark on the axis.
	private Boolean showGridline;	//wether or not to draw the gridline on the grid at this tick.
	private Boolean isMinorTick;	//if this is a minor tick.
	private Integer size;	//Length of the tick beyond the grid in pixels.
	private Integer markSize;	//Length of the tick marks in pixels.
	private Boolean show;	//wether or not to show the tick (mark and label).
	private Boolean showLabel;	//wether or not to show the label.
	private String formatter;	//A class of a formatter for the tick text.
	private String formatString;	//string passed to the formatter.
	private String fontFamily;	//css spec for the font-family css attribute.
	private String fontSize;	//css spec for the font-size css attribute.
	private String textColor;	//css spec for the color attribute.
	
	//CanvasAxisTickRenderer Only
	private Integer angle;	//angle of text, measured clockwise from x axis.
	private String labelPosition;	//‘auto’, ‘start’, ‘middle’ or ‘end’. 
	private String fontWeight;	//CSS spec for fontWeight
	private Float fontStretch;	//Multiplier to condense or expand font width. 
	private Boolean enableFontSupport;	//true to turn on native canvas font support in Mozilla 3.5+ and Safari 4+.
	private String pt2px;	//Point to pixel scaling factor, used for computing height of bounding box around a label. 
	
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getMark() {
		return mark;
	}
	public void setShowMark(Boolean showMark) {
		this.showMark = showMark;
	}
	public Boolean getShowMark() {
		return showMark;
	}
	public void setShowGridline(Boolean showGridline) {
		this.showGridline = showGridline;
	}
	public Boolean getShowGridline() {
		return showGridline;
	}
	public void setIsMinorTick(Boolean isMinorTick) {
		this.isMinorTick = isMinorTick;
	}
	public Boolean getIsMinorTick() {
		return isMinorTick;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Integer getSize() {
		return size;
	}
	public void setMarkSize(Integer markSize) {
		this.markSize = markSize;
	}
	public Integer getMarkSize() {
		return markSize;
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
	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}
	public String getFormatter() {
		return formatter;
	}
	public void setFormatString(String formatString) {
		this.formatString = formatString;
	}
	public String getFormatString() {
		return formatString;
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
	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}
	public String getTextColor() {
		return textColor;
	}
	public void setAngle(Integer angle) {
		this.angle = angle;
	}
	public Integer getAngle() {
		return angle;
	}
	public void setLabelPosition(String labelPosition) {
		this.labelPosition = labelPosition;
	}
	public String getLabelPosition() {
		return labelPosition;
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
	public void setEnableFontSupport(Boolean enableFontSupport) {
		this.enableFontSupport = enableFontSupport;
	}
	public Boolean getEnableFontSupport() {
		return enableFontSupport;
	}
	public void setPt2px(String pt2px) {
		this.pt2px = pt2px;
	}
	public String getPt2px() {
		return pt2px;
	}
}
