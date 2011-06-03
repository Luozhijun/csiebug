/**
 * 
 */
package csiebug.web.html.chart.jqPlotBean;

/**
 * @author George_Tsai
 * @version 2010/5/31
 */
public class JQPlotGrid {
	public static final String RENDERER = "$.jqplot.CanvasGridRenderer";
	
	private Boolean drawGridlines;	//wether to draw the gridlines on the plot.
	private String gridLineColor;	//color of the grid lines.
	private Float gridLineWidth;	//width of the grid lines.
	private String background;	//css spec for the background color.
	private String borderColor;	//css spec for the color of the grid border.
	private Float borderWidth;	//width of the border in pixels.
	private Boolean shadow;	//wether to show a shadow behind the grid.
	private Integer shadowAngle;	//shadow angle in degrees
	private Float shadowOffset;	//Offset of each shadow stroke from the border in pixels
	private Integer shadowWidth;	//width of the stoke for the shadow
	private Integer shadowDepth;	//Number of times shadow is stroked, each stroke offset shadowOffset from the last.
	private Float shadowAlpha;	//Alpha channel transparency of shadow.
	private String renderer;	//Instance of a renderer which will actually render the grid, see $.jqplot.CanvasGridRenderer.
	private String rendererOptions;	//Options to pass on to the renderer, see $.jqplot.CanvasGridRenderer.
	
	public void setDrawGridlines(Boolean drawGridlines) {
		this.drawGridlines = drawGridlines;
	}
	public Boolean getDrawGridlines() {
		return drawGridlines;
	}
	public void setGridLineColor(String gridLineColor) {
		this.gridLineColor = gridLineColor;
	}
	public String getGridLineColor() {
		return gridLineColor;
	}
	public void setGridLineWidth(Float gridLineWidth) {
		this.gridLineWidth = gridLineWidth;
	}
	public Float getGridLineWidth() {
		return gridLineWidth;
	}
	public void setBackground(String background) {
		this.background = background;
	}
	public String getBackground() {
		return background;
	}
	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}
	public String getBorderColor() {
		return borderColor;
	}
	public void setBorderWidth(Float borderWidth) {
		this.borderWidth = borderWidth;
	}
	public Float getBorderWidth() {
		return borderWidth;
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
	public void setShadowWidth(Integer shadowWidth) {
		this.shadowWidth = shadowWidth;
	}
	public Integer getShadowWidth() {
		return shadowWidth;
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
}
