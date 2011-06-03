/**
 * 
 */
package csiebug.web.html.chart.jqPlotBean;

/**
 * @author George_Tsai
 * @version 2010/5/29
 */
public class JQPlotLegend {
	public static final String LOCATION_NW = "nw";
	public static final String LOCATION_N = "n";
	public static final String LOCATION_NE = "ne";
	public static final String LOCATION_E = "e";
	public static final String LOCATION_SE = "se";
	public static final String LOCATION_S = "s";
	public static final String LOCATION_SW = "sw";
	public static final String LOCATION_W = "w";
	
	private Boolean show;	//Wether to display the legend on the graph.
	private String location;	//Placement of the legend.
	private Integer xoffset;	//offset from the inside edge of the plot in the x direction in pixels.
	private Integer yoffset;	//offset from the inside edge of the plot in the y direction in pixels.
	private String border;	//css spec for the border around the legend box.
	private String background;	//css spec for the background of the legend box.
	private String textColor;	//css color spec for the legend text.
	private String fontFamily;	//css font-family spec for the legend text.
	private String fontSize;	//css font-size spec for the legend text.
	private String rowSpacing;	//css padding-top spec for the rows in the legend.
	private String rendererOptions;	//renderer specific options passed to the renderer.
	private String predraw;	//Wether to draw the legend before the series or not.
	
	public void setShow(Boolean show) {
		this.show = show;
	}
	public Boolean getShow() {
		return show;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLocation() {
		return location;
	}
	public void setXoffset(Integer xoffset) {
		this.xoffset = xoffset;
	}
	public Integer getXoffset() {
		return xoffset;
	}
	public void setYoffset(Integer yoffset) {
		this.yoffset = yoffset;
	}
	public Integer getYoffset() {
		return yoffset;
	}
	public void setBorder(String border) {
		this.border = border;
	}
	public String getBorder() {
		return border;
	}
	public void setBackground(String background) {
		this.background = background;
	}
	public String getBackground() {
		return background;
	}
	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}
	public String getTextColor() {
		return textColor;
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
	public void setRowSpacing(String rowSpacing) {
		this.rowSpacing = rowSpacing;
	}
	public String getRowSpacing() {
		return rowSpacing;
	}
	public void setRendererOptions(String rendererOptions) {
		this.rendererOptions = rendererOptions;
	}
	public String getRendererOptions() {
		return rendererOptions;
	}
	public void setPredraw(String predraw) {
		this.predraw = predraw;
	}
	public String getPredraw() {
		return predraw;
	}
}
