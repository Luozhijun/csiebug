/**
 * 
 */
package csiebug.web.html.chart.raphaelJSBean;

/**
 * @author George_Tsai
 * @version 2010/5/26
 */
public class RaphaelPie {
	private String legendPosition;
	private Integer radius;
	
	public void setLegendPosition(String legendPosition) {
		this.legendPosition = legendPosition;
	}
	public String getLegendPosition() {
		return legendPosition;
	}
	public void setRadius(Integer radius) {
		this.radius = radius;
	}
	public Integer getRadius() {
		return radius;
	}
}
