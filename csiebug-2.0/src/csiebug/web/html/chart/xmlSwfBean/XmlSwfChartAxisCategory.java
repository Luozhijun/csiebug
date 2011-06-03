package csiebug.web.html.chart.xmlSwfBean;

public class XmlSwfChartAxisCategory {
	private Integer skip;
    private String font; 
    private Boolean bold; 
    private Integer size;
    private String color; 
    private Integer alpha;
    private String orientation;
    private String shadow;
    private String bevel;
    private String glow;
    private String blur;
    //area, stacked area, line charts
    private Boolean margin; 
    //scatter and bubble charts
    private Integer min; 
    private Integer max;
    private Integer steps;
    private String mode;  
    private String prefix;
    private String suffix; 
    private Integer decimals;
    private String decimalChar;  
    private String separator;
    
	public void setSkip(Integer skip) {
		this.skip = skip;
	}
	public Integer getSkip() {
		return skip;
	}
	public void setFont(String font) {
		this.font = font;
	}
	public String getFont() {
		return font;
	}
	public void setBold(Boolean bold) {
		this.bold = bold;
	}
	public Boolean getBold() {
		return bold;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Integer getSize() {
		return size;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getColor() {
		return color;
	}
	public void setAlpha(Integer alpha) {
		this.alpha = alpha;
	}
	public Integer getAlpha() {
		return alpha;
	}
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	public String getOrientation() {
		return orientation;
	}
	public void setShadow(String shadow) {
		this.shadow = shadow;
	}
	public String getShadow() {
		return shadow;
	}
	public void setBevel(String bevel) {
		this.bevel = bevel;
	}
	public String getBevel() {
		return bevel;
	}
	public void setGlow(String glow) {
		this.glow = glow;
	}
	public String getGlow() {
		return glow;
	}
	public void setBlur(String blur) {
		this.blur = blur;
	}
	public String getBlur() {
		return blur;
	}
	public void setMargin(Boolean margin) {
		this.margin = margin;
	}
	public Boolean getMargin() {
		return margin;
	}
	public void setMin(Integer min) {
		this.min = min;
	}
	public Integer getMin() {
		return min;
	}
	public void setMax(Integer max) {
		this.max = max;
	}
	public Integer getMax() {
		return max;
	}
	public void setSteps(Integer steps) {
		this.steps = steps;
	}
	public Integer getSteps() {
		return steps;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getMode() {
		return mode;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setDecimals(Integer decimals) {
		this.decimals = decimals;
	}
	public Integer getDecimals() {
		return decimals;
	}
	public void setDecimalChar(String decimalChar) {
		this.decimalChar = decimalChar;
	}
	public String getDecimalChar() {
		return decimalChar;
	}
	public void setSeparator(String separator) {
		this.separator = separator;
	}
	public String getSeparator() {
		return separator;
	}
}
