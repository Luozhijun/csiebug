/**
 * 
 */
package csiebug.web.html.chart.jqPlotBean;

/**
 * @author George_Tsai
 * @version 2010/5/28
 */
public class JQPlotTitle {
	public static final String RENDERER = "$.jqplot.DivTitleRenderer";
	
	private String text;	//text of the title;
	private Boolean show;	//wether or not to show the title
	private String fontFamily;	//css font-family spec for the text.
	private String fontSize;	//css font-size spec for the text.
	private String textAlign;	//css text-align spec for the text.
	private String textColor;	//css color spec for the text.
	private String renderer;	//A class for creating a DOM element for the title, see $.jqplot.DivTitleRenderer.
	private String rendererOptions;	//renderer specific options passed to the renderer.
	
	public void setText(String text) {
		this.text = text;
	}
	public String getText() {
		return text;
	}
	public void setShow(Boolean show) {
		this.show = show;
	}
	public Boolean getShow() {
		return show;
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
	public void setTextAlign(String textAlign) {
		this.textAlign = textAlign;
	}
	public String getTextAlign() {
		return textAlign;
	}
	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}
	public String getTextColor() {
		return textColor;
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
