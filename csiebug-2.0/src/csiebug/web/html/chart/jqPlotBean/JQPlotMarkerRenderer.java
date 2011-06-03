/**
 * 
 */
package csiebug.web.html.chart.jqPlotBean;

/**
 * @author George_Tsai
 * @version 2010/5/30
 */
public class JQPlotMarkerRenderer implements JQPlotOptions {
	public static final String STYLE_DIAMOND = "diamond";
	public static final String STYLE_CIRCLE = "circle";
	public static final String STYLE_SQUARE = "square";
	public static final String STYLE_X = "x";
	public static final String STYLE_PLUS = "plus";
	public static final String STYLE_DASH = "dash";
	public static final String STYLE_FILLED_DIAMOND = "filledDiamond";
	public static final String STYLE_FILLED_CIRCLE = "filledCircle";
	public static final String STYLE_FILLED_SQUARE = "filledSquare";
	public static final String SHADOW_RENDERER = "new $.jqplot.ShadowRenderer()";
	public static final String SHAPE_RENDERER = "new $.jqplot.ShapeRenderer()";
	
	private Boolean show;	//wether or not to show the marker.
	private String style;	//One of diamond, circle, square, x, plus, dash, filledDiamond, filledCircle, filledSquare
	private Integer lineWidth;	//size of the line for non-filled markers.
	private Float size;	//Size of the marker (diameter or circle, length of edge of square, etc.)
	private String color;	//color of marker.
	private String shadow;	//wether or not to draw a shadow on the line
	private Integer shadowAngle;	//Shadow angle in degrees
	private Integer shadowOffset;	//Shadow offset from line in pixels
	private Integer shadowDepth;	//Number of times shadow is stroked, each stroke offset shadowOffset from the last.
	private Float shadowAlpha;	//Alpha channel transparency of shadow.
	private String shadowRenderer;	//Renderer that will draws the shadows on the marker.
	private String shapeRenderer;	//Renderer that will draw the marker.
	
	public void setShow(Boolean show) {
		this.show = show;
	}
	public Boolean getShow() {
		return show;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getStyle() {
		return style;
	}
	public void setLineWidth(Integer lineWidth) {
		this.lineWidth = lineWidth;
	}
	public Integer getLineWidth() {
		return lineWidth;
	}
	public void setSize(Float size) {
		this.size = size;
	}
	public Float getSize() {
		return size;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getColor() {
		return color;
	}
	public void setShadow(String shadow) {
		this.shadow = shadow;
	}
	public String getShadow() {
		return shadow;
	}
	public void setShadowAngle(Integer shadowAngle) {
		this.shadowAngle = shadowAngle;
	}
	public Integer getShadowAngle() {
		return shadowAngle;
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
	public void setShadowRenderer(String shadowRenderer) {
		this.shadowRenderer = shadowRenderer;
	}
	public String getShadowRenderer() {
		return shadowRenderer;
	}
	public void setShapeRenderer(String shapeRenderer) {
		this.shapeRenderer = shapeRenderer;
	}
	public String getShapeRenderer() {
		return shapeRenderer;
	}
}
