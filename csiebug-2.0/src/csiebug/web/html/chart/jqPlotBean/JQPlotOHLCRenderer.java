/**
 * 
 */
package csiebug.web.html.chart.jqPlotBean;

/**
 * @author George_Tsai
 * @version 2010/6/4
 */
public class JQPlotOHLCRenderer implements JQPlotOptions {
	private Boolean candleStick;	//true to render chart as candleStick.
	private String tickLength;	//length of the line in pixels indicating open and close price.
	private String bodyWidth;	//width of the candlestick body in pixels.
	private String openColor;	//color of the open price tick mark.
	private String closeColor;	//color of the close price tick mark.
	private String wickColor;	//color of the hi-lo line thorugh the candlestick body.
	private Boolean fillUpBody;	//true to render an “up” day (close price greater than open price) with a filled candlestick body.
	private Boolean fillDownBody;	//true to render a “down” day (close price lower than open price) with a filled candlestick body.
	private String upBodyColor;	//Color of candlestick body of an “up” day.
	private String downBodyColor;	//Color of candlestick body on a “down” day.
	private Boolean hlc;	//true if is a hi-low-close chart (no open price).
	private Float lineWidth;	//Width of the hi-low line and open/close ticks.
	
	public void setCandleStick(Boolean candleStick) {
		this.candleStick = candleStick;
	}
	public Boolean getCandleStick() {
		return candleStick;
	}
	public void setTickLength(String tickLength) {
		this.tickLength = tickLength;
	}
	public String getTickLength() {
		return tickLength;
	}
	public void setBodyWidth(String bodyWidth) {
		this.bodyWidth = bodyWidth;
	}
	public String getBodyWidth() {
		return bodyWidth;
	}
	public void setOpenColor(String openColor) {
		this.openColor = openColor;
	}
	public String getOpenColor() {
		return openColor;
	}
	public void setCloseColor(String closeColor) {
		this.closeColor = closeColor;
	}
	public String getCloseColor() {
		return closeColor;
	}
	public void setWickColor(String wickColor) {
		this.wickColor = wickColor;
	}
	public String getWickColor() {
		return wickColor;
	}
	public void setFillUpBody(Boolean fillUpBody) {
		this.fillUpBody = fillUpBody;
	}
	public Boolean getFillUpBody() {
		return fillUpBody;
	}
	public void setFillDownBody(Boolean fillDownBody) {
		this.fillDownBody = fillDownBody;
	}
	public Boolean getFillDownBody() {
		return fillDownBody;
	}
	public void setUpBodyColor(String upBodyColor) {
		this.upBodyColor = upBodyColor;
	}
	public String getUpBodyColor() {
		return upBodyColor;
	}
	public void setDownBodyColor(String downBodyColor) {
		this.downBodyColor = downBodyColor;
	}
	public String getDownBodyColor() {
		return downBodyColor;
	}
	public void setHlc(Boolean hlc) {
		this.hlc = hlc;
	}
	public Boolean getHlc() {
		return hlc;
	}
	public void setLineWidth(Float lineWidth) {
		this.lineWidth = lineWidth;
	}
	public Float getLineWidth() {
		return lineWidth;
	}
}
