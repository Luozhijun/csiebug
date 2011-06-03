package csiebug.web.taglib.chart;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import org.apache.log4j.Logger;

import csiebug.util.WebUtility;
import csiebug.web.html.chart.HtmlRaphaelJSChart;
import csiebug.web.html.chart.raphaelJSBean.RaphaelBar;
import csiebug.web.html.chart.raphaelJSBean.RaphaelLine;
import csiebug.web.html.chart.raphaelJSBean.RaphaelPie;
import csiebug.web.html.chart.raphaelJSBean.RaphaelTitle;

/**
 * RaphaelJS Chart tag
 * @author George_Tsai
 * @version 2010/5/26
 */
public class RaphaelJSChartTag extends BodyTagSupport implements TryCatchFinally {
	static final long serialVersionUID = 20100526;
	
	private String chartId;
	private String chartHeight;
	private String chartWidth;
	private String chartX;
	private String chartY;
	private String chartType;
	private String chartInteractive;
	private String list;
	
	//chart title 設定
	private String chartTitleText;
	private String chartTitleX;
	private String chartTitleY;
	private String chartTitleFontSize;
	
	//chart pie 設定
	private String chartPieLegendPosition;
	private String chartPieRadius;
	
	//chart bar 設定
	private String chartBarHeight;
	private String chartBarWidth;
	private String chartBarStacked;
	private String chartBarType;
	private String chartBarHoverType;
	private String chartBarHorizontal;
	
	//chart line 設定
	private String chartLineHeight;
	private String chartLineWidth;
	private String chartLineShade;
	private String chartLineNoStroke;
	private String chartLineAxis;
	private String chartLineSymbol;
	
	Logger logger = Logger.getLogger(this.getClass());
	
	public int doStartTag() throws JspException {
		try { 
            JspWriter out = pageContext.getOut();
            
            
            WebUtility webutil = new WebUtility();
            if(list != null && webutil.getRequestAttribute(list) != null) {
            	HtmlRaphaelJSChart htmlRaphaelJSChart = getChartInstance(webutil);
            	out.println(htmlRaphaelJSChart.render());
            }
        } catch(Exception e) {
        	throw new JspException(e);
        } 
 
        return SKIP_BODY; 
	}
	public void doFinally() {
		
	}
	public void doCatch(Throwable e) throws Throwable {
        throw new JspException("RaphaelJSChartTag Problem: " + e.getMessage());
    }
	
	@SuppressWarnings("unchecked")
	private HtmlRaphaelJSChart getChartInstance(WebUtility webutil) throws IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
		HtmlRaphaelJSChart htmlRaphaelJSChart = null;
		
		if(chartType != null) {
			Integer height = null;
			Integer width = null;
			Integer x = null;
			Integer y = null;
			
			try {
				height = Integer.parseInt(chartHeight);
			} catch(NumberFormatException nfex) {
				logger.info(nfex);
			}
			
			try {
				width = Integer.parseInt(chartWidth);
			} catch(NumberFormatException nfex) {
				logger.info(nfex);
			}
			
			try {
				x = Integer.parseInt(chartX);
			} catch(NumberFormatException nfex) {
				logger.info(nfex);
			}
			
			try {
				y = Integer.parseInt(chartY);
			} catch(NumberFormatException nfex) {
				logger.info(nfex);
			}
			
			if(chartType.trim().equalsIgnoreCase(HtmlRaphaelJSChart.CHART_TYPE_PIE)) {
				if(chartInteractive != null && chartInteractive.trim().equalsIgnoreCase("true")) {
					htmlRaphaelJSChart = new HtmlRaphaelJSChart(chartId, height, width, x, y, Boolean.TRUE, (List<Map<String, String>>)webutil.getRequestAttribute(list), getChartTitleInstance(), getChartPieInstance());
				} else {
					htmlRaphaelJSChart = new HtmlRaphaelJSChart(chartId, height, width, x, y, Boolean.FALSE, (List<Map<String, String>>)webutil.getRequestAttribute(list), getChartTitleInstance(), getChartPieInstance());
				}
			} else if(chartType.trim().equalsIgnoreCase(HtmlRaphaelJSChart.CHART_TYPE_BAR)) {
				if(chartInteractive != null && chartInteractive.trim().equalsIgnoreCase("true")) {
					htmlRaphaelJSChart = new HtmlRaphaelJSChart(chartId, height, width, x, y, Boolean.TRUE, (List<Map<String, String>>)webutil.getRequestAttribute(list), getChartTitleInstance(), getChartBarInstance());
				} else {
					htmlRaphaelJSChart = new HtmlRaphaelJSChart(chartId, height, width, x, y, Boolean.FALSE, (List<Map<String, String>>)webutil.getRequestAttribute(list), getChartTitleInstance(), getChartBarInstance());
				}
			} else if(chartType.trim().equalsIgnoreCase(HtmlRaphaelJSChart.CHART_TYPE_HBAR)) {
				if(chartInteractive != null && chartInteractive.trim().equalsIgnoreCase("true")) {
					htmlRaphaelJSChart = new HtmlRaphaelJSChart(chartId, height, width, x, y, Boolean.TRUE, (List<Map<String, String>>)webutil.getRequestAttribute(list), getChartTitleInstance(), getChartBarInstance());
				} else {
					htmlRaphaelJSChart = new HtmlRaphaelJSChart(chartId, height, width, x, y, Boolean.FALSE, (List<Map<String, String>>)webutil.getRequestAttribute(list), getChartTitleInstance(), getChartBarInstance());
				}
			} else if(chartType.trim().equalsIgnoreCase(HtmlRaphaelJSChart.CHART_TYPE_LINE)) {
				if(chartInteractive != null && chartInteractive.trim().equalsIgnoreCase("true")) {
					htmlRaphaelJSChart = new HtmlRaphaelJSChart(chartId, height, width, x, y, Boolean.TRUE, (Map<String, List<Map<String, String>>>)webutil.getRequestAttribute(list), getChartTitleInstance(), getChartLineInstance());
				} else {
					htmlRaphaelJSChart = new HtmlRaphaelJSChart(chartId, height, width, x, y, Boolean.FALSE, (Map<String, List<Map<String, String>>>)webutil.getRequestAttribute(list), getChartTitleInstance(), getChartLineInstance());
				}
			}
		}
		
		return htmlRaphaelJSChart;
	}
	
	private void setAttribute(Object targetObject, String prefix) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Class<?> tagetClass = Class.forName(targetObject.getClass().getName());
		Class<?> tagClass = Class.forName(this.getClass().getName());
		
		for(int i = 0; i < tagetClass.getMethods().length; i++) {
        	Method targetMethod = tagetClass.getMethods()[i];
        	
        	if(targetMethod.getName().startsWith("set")) {
        		for(int j = 0; j < tagClass.getMethods().length; j++) {
        			Method tagMethod = tagClass.getMethods()[j];
        			
        			if(tagMethod.getName().startsWith("get")) {
        				Class<?> parameterType = targetMethod.getParameterTypes()[0];
    	        		String getMethodName = "get" + prefix + targetMethod.getName().replaceFirst("set", "");
    	        		
        				if(tagMethod.getName().equals(getMethodName)) {
        					Object obj = tagMethod.invoke(this, (Object[])null);
        					
        					if(obj != null) {
        						if(parameterType.getName().equals("java.lang.Integer")) {
        							obj = Integer.parseInt((String)obj);
        						} else if(parameterType.getName().equals("java.lang.Boolean")) {
        							obj = Boolean.parseBoolean((String)obj);
        						}
        						
        						Object[] args = new Object[]{obj};
        						targetMethod.invoke(targetObject, args);
        					}
        				}
        			}
        		}
	        }
		}
	}
	
	private boolean isAllAttributeNull(String prefix) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		boolean flag = true;
		Class<?> tagClass = Class.forName(this.getClass().getName());
		
		for(int j = 0; j < tagClass.getMethods().length; j++) {
			Method tagMethod = tagClass.getMethods()[j];
			
			if(tagMethod.getName().indexOf("get" + prefix) != -1) {
				Object obj = tagMethod.invoke(this, (Object[])null);
				
				if(obj != null) {
					flag = false;
					break;
				}
			}
		}
		
		return flag;
	}
	
	private RaphaelTitle getChartTitleInstance() throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		RaphaelTitle chartTitle = null;
		
		if(!isAllAttributeNull("ChartTitle")) {
			chartTitle = new RaphaelTitle();
			
			setAttribute(chartTitle, "ChartTitle");
		}
		
		return chartTitle;
	}
	
	private RaphaelPie getChartPieInstance() throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		RaphaelPie chartPie = null;
		
		if(!isAllAttributeNull("ChartPie")) {
			chartPie = new RaphaelPie();
			
			setAttribute(chartPie, "ChartPie");		
		}
		
		return chartPie;
	}
	
	private RaphaelBar getChartBarInstance() throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		RaphaelBar chartBar = null;
		
		if(!isAllAttributeNull("ChartBar")) {
			chartBar = new RaphaelBar();
			
			setAttribute(chartBar, "ChartBar");			
		}
		
		return chartBar;
	}
	
	private RaphaelLine getChartLineInstance() throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		RaphaelLine chartLine = null;
		
		if(!isAllAttributeNull("ChartLine")) {
			chartLine = new RaphaelLine();
			
			setAttribute(chartLine, "ChartLine");			
		}
		
		return chartLine;
	}
	
	//元件屬性區
	public void setChartId(String chartId) {
		this.chartId = chartId;
	}
	public String getChartId() {
		return chartId;
	}
	public void setChartHeight(String chartHeight) {
		this.chartHeight = chartHeight;
	}
	public String getChartHeight() {
		return chartHeight;
	}
	public void setChartWidth(String chartWidth) {
		this.chartWidth = chartWidth;
	}
	public String getChartWidth() {
		return chartWidth;
	}
	public void setChartX(String chartX) {
		this.chartX = chartX;
	}
	public String getChartX() {
		return chartX;
	}
	public void setChartY(String chartY) {
		this.chartY = chartY;
	}
	public String getChartY() {
		return chartY;
	}
	public void setChartType(String chartType) {
		this.chartType = chartType;
	}
	public String getChartType() {
		return chartType;
	}
	public void setChartInteractive(String chartInteractive) {
		this.chartInteractive = chartInteractive;
	}
	public String getChartInteractive() {
		return chartInteractive;
	}
	public void setChartTitleText(String chartTitleText) {
		this.chartTitleText = chartTitleText;
	}
	public String getChartTitleText() {
		return chartTitleText;
	}
	public void setChartTitleX(String chartTitleX) {
		this.chartTitleX = chartTitleX;
	}
	public String getChartTitleX() {
		return chartTitleX;
	}
	public void setChartTitleY(String chartTitleY) {
		this.chartTitleY = chartTitleY;
	}
	public String getChartTitleY() {
		return chartTitleY;
	}
	public void setChartTitleFontSize(String chartTitleFontSize) {
		this.chartTitleFontSize = chartTitleFontSize;
	}
	public String getChartTitleFontSize() {
		return chartTitleFontSize;
	}
	public void setChartPieLegendPosition(String chartPieLegendPosition) {
		this.chartPieLegendPosition = chartPieLegendPosition;
	}
	public String getChartPieLegendPosition() {
		return chartPieLegendPosition;
	}
	public void setChartPieRadius(String chartPieRadius) {
		this.chartPieRadius = chartPieRadius;
	}
	public String getChartPieRadius() {
		return chartPieRadius;
	}
	public void setChartBarHeight(String chartBarHeight) {
		this.chartBarHeight = chartBarHeight;
	}
	public String getChartBarHeight() {
		return chartBarHeight;
	}
	public void setChartBarWidth(String chartBarWidth) {
		this.chartBarWidth = chartBarWidth;
	}
	public String getChartBarWidth() {
		return chartBarWidth;
	}
	public void setChartBarStacked(String chartBarStacked) {
		this.chartBarStacked = chartBarStacked;
	}
	public String getChartBarStacked() {
		return chartBarStacked;
	}
	public void setChartBarType(String chartBarType) {
		this.chartBarType = chartBarType;
	}
	public String getChartBarType() {
		return chartBarType;
	}
	public void setChartBarHoverType(String chartBarHoverType) {
		this.chartBarHoverType = chartBarHoverType;
	}
	public String getChartBarHoverType() {
		return chartBarHoverType;
	}
	public void setChartBarHorizontal(String chartBarHorizontal) {
		this.chartBarHorizontal = chartBarHorizontal;
	}
	public String getChartBarHorizontal() {
		return chartBarHorizontal;
	}
	public void setChartLineHeight(String chartLineHeight) {
		this.chartLineHeight = chartLineHeight;
	}
	public String getChartLineHeight() {
		return chartLineHeight;
	}
	public void setChartLineWidth(String chartLineWidth) {
		this.chartLineWidth = chartLineWidth;
	}
	public String getChartLineWidth() {
		return chartLineWidth;
	}
	public void setChartLineShade(String chartLineShade) {
		this.chartLineShade = chartLineShade;
	}
	public String getChartLineShade() {
		return chartLineShade;
	}
	public void setChartLineNoStroke(String chartLineNoStroke) {
		this.chartLineNoStroke = chartLineNoStroke;
	}
	public String getChartLineNoStroke() {
		return chartLineNoStroke;
	}
	public void setChartLineAxis(String chartLineAxis) {
		this.chartLineAxis = chartLineAxis;
	}
	public String getChartLineAxis() {
		return chartLineAxis;
	}
	public void setChartLineSymbol(String chartLineSymbol) {
		this.chartLineSymbol = chartLineSymbol;
	}
	public String getChartLineSymbol() {
		return chartLineSymbol;
	}
	public void setList(String list) {
		this.list = list;
	}
	public String getList() {
		return list;
	}
}