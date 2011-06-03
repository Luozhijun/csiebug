/**
 * 
 */
package csiebug.web.html.chart.raphaelJSBean;

/**
 * @author George_Tsai
 * @version 2010/5/26
 */
public class RaphaelTitle {
	private String text;
	private Integer x;
	private Integer y;
	private Integer fontSize;
	
	public void setText(String text) {
		this.text = text;
	}
	public String getText() {
		return text;
	}
	public void setX(Integer x) {
		this.x = x;
	}
	public Integer getX() {
		return x;
	}
	public void setY(Integer y) {
		this.y = y;
	}
	public Integer getY() {
		return y;
	}
	public void setFontSize(Integer fontSize) {
		this.fontSize = fontSize;
	}
	public Integer getFontSize() {
		return fontSize;
	}
}
