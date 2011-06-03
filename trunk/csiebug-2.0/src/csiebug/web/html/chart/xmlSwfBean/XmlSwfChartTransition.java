package csiebug.web.html.chart.xmlSwfBean;

public class XmlSwfChartTransition {
	private String type;
    private Integer delay;
    private Integer duration;
    private String order;
    
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	public void setDelay(Integer delay) {
		this.delay = delay;
	}
	public Integer getDelay() {
		return delay;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getOrder() {
		return order;
	}

}
