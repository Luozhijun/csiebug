/**
 * 
 */
package csiebug.web.html.chart.jqPlotBean;

/**
 * @author George_Tsai
 * @version 2010/6/1
 */
public class JQPlotExtraSeriesPoint extends JQPlotPoint {
	private String extraSeriesData;
	
	public void setExtraSeriesData(String extraSeriesData) {
		this.extraSeriesData = extraSeriesData;
	}
	public String getExtraSeriesData() {
		return extraSeriesData;
	}
	public String getY() {
		String yAndExtraSeriesData = super.getY() + ", ";
		
		if(extraSeriesData == null) {
			yAndExtraSeriesData = yAndExtraSeriesData + extraSeriesData;
		} else {
			yAndExtraSeriesData = yAndExtraSeriesData + "'" + extraSeriesData + "'";
		}
		
		return yAndExtraSeriesData;
	}
}
