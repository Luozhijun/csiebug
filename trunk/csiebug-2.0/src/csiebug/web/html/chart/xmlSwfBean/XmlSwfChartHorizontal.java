package csiebug.web.html.chart.xmlSwfBean;

public class XmlSwfChartHorizontal {
	private Integer thickness;
	private String color;
	private Integer alpha;
	private String type;
	
	public void setThickness(Integer chartHorizontalThickness) {
		this.thickness = chartHorizontalThickness;
	}
	public Integer getThickness() {
		return thickness;
	}
	public void setColor(String chartHorizontalColor) {
		this.color = chartHorizontalColor;
	}
	public String getColor() {
		return color;
	}
	public void setAlpha(Integer chartHorizontalAlpha) {
		this.alpha = chartHorizontalAlpha;
	}
	public Integer getAlpha() {
		return alpha;
	}
	public void setType(String chartHorizontalType) {
		this.type = chartHorizontalType;
	}
	public String getType() {
		return type;
	}
}
