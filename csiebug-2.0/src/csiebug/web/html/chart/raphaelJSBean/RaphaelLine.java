/**
 * 
 */
package csiebug.web.html.chart.raphaelJSBean;

/**
 * @author George_Tsai
 * @version 2010/5/26
 */
public class RaphaelLine {
	private Integer height;
	private Integer width;
	private Boolean shade;
	private Boolean noStroke;
	private String axis;
	private String symbol;
	
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Integer getHeight() {
		return height;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getWidth() {
		return width;
	}
	public void setShade(Boolean shade) {
		this.shade = shade;
	}
	public Boolean getShade() {
		return shade;
	}
	public void setNoStroke(Boolean noStroke) {
		this.noStroke = noStroke;
	}
	public Boolean getNoStroke() {
		return noStroke;
	}
	public void setAxis(String axis) {
		this.axis = axis;
	}
	public String getAxis() {
		return axis;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getSymbol() {
		return symbol;
	}
}
