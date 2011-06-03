/**
 * 
 */
package csiebug.web.html.chart.raphaelJSBean;

/**
 * @author George_Tsai
 * @version 2010/5/26
 */
public class RaphaelBar {
	private Integer height;
	private Integer width;
	private Boolean stacked;
	private String type;
	private String hoverType;
	private Boolean horizontal;
	
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
	public void setStacked(Boolean stacked) {
		this.stacked = stacked;
	}
	public Boolean getStacked() {
		return stacked;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	public void setHoverType(String hoverType) {
		this.hoverType = hoverType;
	}
	public String getHoverType() {
		return hoverType;
	}
	public void setHorizontal(Boolean horizontal) {
		this.horizontal = horizontal;
	}
	public Boolean getHorizontal() {
		return horizontal;
	}
}
