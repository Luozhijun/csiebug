package csiebug.web.html.chart.xmlSwfBean;

public class XmlSwfChartAxisTicks {
	private Boolean valueTicks; 
    private Boolean categoryTicks; 
    private String position; 
    private Integer majorThickness; 
    private String majorColor; 
    private Integer minorThickness; 
    private String minorColor;
    private Integer minorCount;
    
	public void setValueTicks(Boolean valueTicks) {
		this.valueTicks = valueTicks;
	}
	public Boolean getValueTicks() {
		return valueTicks;
	}
	public void setCategoryTicks(Boolean categoryTicks) {
		this.categoryTicks = categoryTicks;
	}
	public Boolean getCategoryTicks() {
		return categoryTicks;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getPosition() {
		return position;
	}
	public void setMajorThickness(Integer majorThickness) {
		this.majorThickness = majorThickness;
	}
	public Integer getMajorThickness() {
		return majorThickness;
	}
	public void setMajorColor(String majorColor) {
		this.majorColor = majorColor;
	}
	public String getMajorColor() {
		return majorColor;
	}
	public void setMinorThickness(Integer minorThickness) {
		this.minorThickness = minorThickness;
	}
	public Integer getMinorThickness() {
		return minorThickness;
	}
	public void setMinorColor(String minorColor) {
		this.minorColor = minorColor;
	}
	public String getMinorColor() {
		return minorColor;
	}
	public void setMinorCount(Integer minorCount) {
		this.minorCount = minorCount;
	}
	public Integer getMinorCount() {
		return minorCount;
	}

}
