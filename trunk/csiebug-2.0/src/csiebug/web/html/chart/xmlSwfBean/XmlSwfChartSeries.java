package csiebug.web.html.chart.xmlSwfBean;

public class XmlSwfChartSeries {
	private Integer barGap;
    private Integer setGap;
    private Boolean transfer;
    
	public void setBarGap(Integer barGap) {
		this.barGap = barGap;
	}
	public Integer getBarGap() {
		return barGap;
	}
	public void setSetGap(Integer setGap) {
		this.setGap = setGap;
	}
	public Integer getSetGap() {
		return setGap;
	}
	public void setTransfer(Boolean transfer) {
		this.transfer = transfer;
	}
	public Boolean getTransfer() {
		return transfer;
	}

}
