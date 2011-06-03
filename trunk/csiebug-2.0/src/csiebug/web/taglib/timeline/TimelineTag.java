package csiebug.web.taglib.timeline;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import csiebug.util.AssertUtility;
import csiebug.util.StringUtility;
import csiebug.util.WebUtility;
import csiebug.web.html.timeline.HtmlTimeline;

/**
 * Timeline tag
 * @author George_Tsai
 * @version 2010/3/18
 */
public class TimelineTag extends BodyTagSupport implements TryCatchFinally {
	static final long serialVersionUID = 20100318;
	
	private String timelineId;
	private String timelineHeight;
	private String timelineDataPath;
	private String dataSource;
	
	private String openHourBandFlag;
	private String hourBandWidthPercentage;
	private String openDayBandFlag;
	private String dayBandWidthPercentage;
	private String openMonthBandFlag;
	private String monthBandWidthPercentage;
	private String openYearBandFlag;
	private String yearBandWidthPercentage;
	
	private String list;
	
	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {
		try { 
            JspWriter out = pageContext.getOut();
            
            
            WebUtility webutil = new WebUtility();
            
            Integer th = null;
            if(AssertUtility.isInteger(timelineHeight)) {
            	th = Integer.parseInt(timelineHeight);
            }
            
            Integer hbwp = null;
            if(AssertUtility.isInteger(hourBandWidthPercentage)) {
            	hbwp = Integer.parseInt(hourBandWidthPercentage);
            }
            
            Integer dbwp = null;
            if(AssertUtility.isInteger(dayBandWidthPercentage)) {
            	dbwp = Integer.parseInt(dayBandWidthPercentage);
            }
            
            Integer mbwp = null;
            if(AssertUtility.isInteger(monthBandWidthPercentage)) {
            	mbwp = Integer.parseInt(monthBandWidthPercentage);
            }
            
            Integer ybwp = null;
            if(AssertUtility.isInteger(yearBandWidthPercentage)) {
            	ybwp = Integer.parseInt(yearBandWidthPercentage);
            }
            
            ArrayList<Map<String, String>> data = null;
            if(list != null && webutil.getRequestAttribute(list) != null) {
            	data = (ArrayList<Map<String, String>>) webutil.getRequestAttribute(list);
            }
            
            HtmlTimeline htmlTimeline = new HtmlTimeline(timelineId, th, timelineDataPath, Boolean.parseBoolean(openHourBandFlag), hbwp, Boolean.parseBoolean(openDayBandFlag), dbwp, Boolean.parseBoolean(openMonthBandFlag), mbwp, Boolean.parseBoolean(openYearBandFlag), ybwp, data, dataSource);
            
            out.println(htmlTimeline.render());
        } catch(Exception e) {
        	throw new JspException(e);
        } 
 
        return SKIP_BODY; 
	}
	public void doFinally() {
		
	}
	public void doCatch(Throwable e) throws Throwable {
        throw new JspException("TimelineTag Problem: " + e.getMessage());
    }
	
	//元件屬性區
	public void setTimelineId(String timelineId) {
		this.timelineId = timelineId;
	}
	public String getTimelineId() {
		return timelineId;
	}
	public void setTimelineHeight(String timelineHeight) {
		this.timelineHeight = timelineHeight;
	}
	public String getTimelineHeight() {
		return timelineHeight;
	}
	public void setTimelineDataPath(String xmlPath) {
		this.timelineDataPath = StringUtility.removeStartEndSlash(xmlPath);
	}
	public String getTimelineDataPath() {
		return timelineDataPath;
	}
	public void setOpenHourBandFlag(String openHourBandFlag) {
		this.openHourBandFlag = openHourBandFlag;
	}
	public String getOpenHourBandFlag() {
		return openHourBandFlag;
	}
	public void setHourBandWidthPercentage(String hourBandWidthPercentage) {
		this.hourBandWidthPercentage = hourBandWidthPercentage;
	}
	public String getHourBandWidthPercentage() {
		return hourBandWidthPercentage;
	}
	public void setOpenDayBandFlag(String openDayBandFlag) {
		this.openDayBandFlag = openDayBandFlag;
	}
	public String getOpenDayBandFlag() {
		return openDayBandFlag;
	}
	public void setDayBandWidthPercentage(String dayBandWidthPercentage) {
		this.dayBandWidthPercentage = dayBandWidthPercentage;
	}
	public String getDayBandWidthPercentage() {
		return dayBandWidthPercentage;
	}
	public void setOpenMonthBandFlag(String openMonthBandFlag) {
		this.openMonthBandFlag = openMonthBandFlag;
	}
	public String getOpenMonthBandFlag() {
		return openMonthBandFlag;
	}
	public void setMonthBandWidthPercentage(String monthBandWidthPercentage) {
		this.monthBandWidthPercentage = monthBandWidthPercentage;
	}
	public String getMonthBandWidthPercentage() {
		return monthBandWidthPercentage;
	}
	public void setOpenYearBandFlag(String openYearBandFlag) {
		this.openYearBandFlag = openYearBandFlag;
	}
	public String getOpenYearBandFlag() {
		return openYearBandFlag;
	}
	public void setYearBandWidthPercentage(String yearBandWidthPercentage) {
		this.yearBandWidthPercentage = yearBandWidthPercentage;
	}
	public String getYearBandWidthPercentage() {
		return yearBandWidthPercentage;
	}
	public void setList(String list) {
		this.list = list;
	}
	public String getList() {
		return list;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	public String getDataSource() {
		return dataSource;
	}
}