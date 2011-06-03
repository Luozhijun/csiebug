/**
 * 
 */
package csiebug.web.html.chart.jqPlotBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author George_Tsai
 * @version 2010/6/1
 */
public class JQPlotLine {
	private List<JQPlotPoint> points = new ArrayList<JQPlotPoint>();

	public void setPoints(List<JQPlotPoint> points) {
		this.points = points;
	}

	public List<JQPlotPoint> getPoints() {
		return points;
	}
	
	public void addPoint(JQPlotPoint point) {
		points.add(point);
	}
	public void removePoint(JQPlotPoint point) {
		points.remove(point);
	}
	
	public int length() {
		return points.size();
	}
	
	public JQPlotPoint getPoint(int index) {
		return points.get(index);
	}
}
