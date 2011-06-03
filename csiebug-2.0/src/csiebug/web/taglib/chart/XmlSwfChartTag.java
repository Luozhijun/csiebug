package csiebug.web.taglib.chart;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import org.apache.log4j.Logger;

import csiebug.util.StringUtility;
import csiebug.util.WebUtility;
import csiebug.web.html.chart.HtmlXmlSwfChart;
import csiebug.web.html.chart.xmlSwfBean.XmlSwfChartAxisCategory;
import csiebug.web.html.chart.xmlSwfBean.XmlSwfChartAxisTicks;
import csiebug.web.html.chart.xmlSwfBean.XmlSwfChartAxisValue;
import csiebug.web.html.chart.xmlSwfBean.XmlSwfChartGuide;
import csiebug.web.html.chart.xmlSwfBean.XmlSwfChartHorizontal;
import csiebug.web.html.chart.xmlSwfBean.XmlSwfChartLabel;
import csiebug.web.html.chart.xmlSwfBean.XmlSwfChartLegend;
import csiebug.web.html.chart.xmlSwfBean.XmlSwfChartNote;
import csiebug.web.html.chart.xmlSwfBean.XmlSwfChartSeries;
import csiebug.web.html.chart.xmlSwfBean.XmlSwfChartTooltip;
import csiebug.web.html.chart.xmlSwfBean.XmlSwfChartTransition;
import csiebug.web.html.chart.xmlSwfBean.XmlSwfChartVertical;

/**
 * XML/SWF Chart tag
 * @author George_Tsai
 * @version 2009/7/2
 */
public class XmlSwfChartTag extends BodyTagSupport implements TryCatchFinally {
	static final long serialVersionUID = 20090702;
	
	private String rebuildXMLFlag = "false";
	private String chartId;
	private String chartType;
	private String chartHeight;
	private String chartWidth;
	private String xmlPath;
	private String list;
	
	//chart label 全域設定
	private String chartLabelPrefix;
	private String chartLabelSuffix;
	private String chartLabelDecimals;
	private String chartLabelDecimalChar;
	private String chartLabelSeparator;
	private String chartLabelPosition;
	private String chartLabelHideZero;
	private String chartLabelAsPercentage;
	private String chartLabelFont;
	private String chartLabelBold;
	private String chartLabelSize;
	private String chartLabelColor;
	private String chartLabelAlpha;
	private String chartLabelShadow;
	private String chartLabelBevel;
	private String chartLabelGlow;
	private String chartLabelBlur;
	
	//chart note 全域設定
	private String chartNoteType;
	private String chartNoteX;
	private String chartNoteY;
	private String chartNoteOffsetX;
	private String chartNoteOffsetY;
	private String chartNoteFont;
	private String chartNoteBold;
	private String chartNoteSize;
	private String chartNoteColor;
	private String chartNoteAlpha;
	private String chartNoteBackgroundColor;
	private String chartNoteBackgroundAlpha;
	private String chartNoteShadow;
	private String chartNoteBevel;
	private String chartNoteGlow;
	private String chartNoteBlur;
	
	//chart tooltip 全域設定
	private String chartTooltipFont;
	private String chartTooltipBold;
	private String chartTooltipSize;
	private String chartTooltipColor;
	private String chartTooltipAlpha;
	private String chartTooltipBackgroundColor;
	private String chartTooltipBackgroundAlpha;
	private String chartTooltipDuration;
	private String chartTooltipShadow;
	private String chartTooltipBevel;
	private String chartTooltipGlow;
	private String chartTooltipBlur;
	
	//水平格線設定
	private String chartHorizontalThickness;
	private String chartHorizontalColor;
	private String chartHorizontalAlpha;
	private String chartHorizontalType;
	
	//垂直格線設定
	private String chartVerticalThickness;
	private String chartVerticalColor;
	private String chartVerticalAlpha;
	private String chartVerticalType;
	
	//輔助線設定
	private String chartGuideHorizontal;
    private String chartGuideVertical;
    private String chartGuideThickness;
    private String chartGuideColor;
    private String chartGuideAlpha;
    private String chartGuideType;
    private String chartGuideSnapH;
    private String chartGuideSnapV;
    private String chartGuideConnect;
    private String chartGuideRadius;
    private String chartGuideFillColor;
    private String chartGuideFillAlpha;
    private String chartGuideLineColor;
    private String chartGuideLineAlpha;
    private String chartGuideLineThickness;
    private String chartGuideTextHAlpha;
    private String chartGuideTextVAlpha;
    private String chartGuidePrefixH;
    private String chartGuideSuffixH;
    private String chartGuidePrefixV;
    private String chartGuideSuffixV;
    private String chartGuideDecimals;
    private String chartGuideDecimalChar;
    private String chartGuideSeparator;
    private String chartGuideFont;
    private String chartGuideBold;
    private String chartGuideSize;
    private String chartGuideTextColor;
    private String chartGuideBackgroundColor;
    
    //載入特效設定
    private String chartTransitionType;
    private String chartTransitionDelay;
    private String chartTransitionDuration;
    private String chartTransitionOrder;
    
    //series設定
    private String chartSeriesBarGap;
    private String chartSeriesSetGap;
    private String chartSeriesTransfer;
    private String seriesColors;
    
    //坐標軸(橫軸)設定
    private String chartAxisCategorySkip;
    private String chartAxisCategoryFont; 
    private String chartAxisCategoryBold; 
    private String chartAxisCategorySize;
    private String chartAxisCategoryColor; 
    private String chartAxisCategoryAlpha;
    private String chartAxisCategoryOrientation;
    private String chartAxisCategoryShadow;
    private String chartAxisCategoryBevel;
    private String chartAxisCategoryGlow;
    private String chartAxisCategoryBlur;
    //area, stacked area, line charts
    private String chartAxisCategoryMargin; 
    //scatter and bubble charts
    private String chartAxisCategoryMin; 
    private String chartAxisCategoryMax;
    private String chartAxisCategorySteps;
    private String chartAxisCategoryMode;  
    private String chartAxisCategoryPrefix;
    private String chartAxisCategorySuffix; 
    private String chartAxisCategoryDecimals;
    private String chartAxisCategoryDecimalChar;  
    private String chartAxisCategorySeparator;
    private String axisCategoryLabels;
    
    //坐標軸(縱軸)設定
    private String chartAxisTicksValueTicks; 
    private String chartAxisTicksCategoryTicks; 
    private String chartAxisTicksPosition; 
    private String chartAxisTicksMajorThickness; 
    private String chartAxisTicksMajorColor; 
    private String chartAxisTicksMinorThickness; 
    private String chartAxisTicksMinorColor;
    private String chartAxisTicksMinorCount;
    private String chartAxisValueMin;  
    private String chartAxisValueMax; 
    private String chartAxisValueMode;  
    private String chartAxisValueSteps;  
    private String chartAxisValuePrefix; 
    private String chartAxisValueSuffix; 
    private String chartAxisValueDecimals;
    private String chartAxisValueDecimalChar;  
    private String chartAxisValueSeparator; 
    private String chartAxisValueShowMin; 
    private String chartAxisValueFont; 
    private String chartAxisValueBold; 
    private String chartAxisValueSize; 
    private String chartAxisValueColor; 
    private String chartAxisValueBackgroundColor; 
    private String chartAxisValueAlpha;
    private String chartAxisValueOrientation; 
    private String chartAxisValueShadow;
    private String chartAxisValueBevel;
    private String chartAxisValueGlow;
    private String chartAxisValueBlur;
    private String axisValueLabels;
    
    //chart legend 設定
    private String chartLegendTransition;
    private String chartLegendDelay;
    private String chartLegendDuration; 
    private String chartLegendX;
    private String chartLegendY;
    private String chartLegendWidth; 
    private String chartLegendHeight; 
    private String chartLegendToggle;
    private String chartLegendLayout;
    private String chartLegendMargin;
    private String chartLegendBullet;
    private String chartLegendFont;
    private String chartLegendBold;
    private String chartLegendSize;
    private String chartLegendColor;
    private String chartLegendAlpha;
    private String chartLegendFillColor;
    private String chartLegendFillAlpha;
    private String chartLegendLineColor;
    private String chartLegendLineAlpha;
    private String chartLegendLineThickness;
    private String chartLegendShadow;
    private String chartLegendBevel;
    private String chartLegendGlow;
    private String chartLegendBlur;
    
    Logger logger = Logger.getLogger(this.getClass());
	
	public int doStartTag() throws JspException {
		try { 
            JspWriter out = pageContext.getOut();
            
            
            WebUtility webutil = new WebUtility();
            if(list != null && webutil.getRequestAttribute(list) != null) {
            	HtmlXmlSwfChart htmlXmlSwfChart = getChartInstance(webutil);
            	out.println(htmlXmlSwfChart.render());
            }
        } catch(Exception e) {
        	throw new JspException(e);
        } 
 
        return SKIP_BODY; 
	}
	public void doFinally() {
		
	}
	public void doCatch(Throwable e) throws Throwable {
        throw new JspException("XmlSwfChartTag Problem: " + e.getMessage());
    }
	
	@SuppressWarnings("unchecked")
	private HtmlXmlSwfChart getChartInstance(WebUtility webutil) throws IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
		HtmlXmlSwfChart htmlXmlSwfChart;
		
		Integer height = null;
		Integer width = null;
		
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
		
		if(rebuildXMLFlag.trim().equalsIgnoreCase("true")) {
    		htmlXmlSwfChart = new HtmlXmlSwfChart(Boolean.TRUE, chartId, chartType, height, width, xmlPath, (ArrayList<ArrayList<Map<String, String>>>)webutil.getRequestAttribute(list), getChartLabelInstance(), getChartNoteInstance(), getChartTooltipInstance(), getChartHorizontalInstance(), getChartVerticalInstance(), getChartGuideInstance(), getChartTransitionInstance(), getChartSeriesInstance(), seriesColors, getChartAxisCategoryInstance(), axisCategoryLabels, getChartAxisTicksInstance(), getChartAxisValueInstance(), axisValueLabels, getChartLegendInstance());
    	} else {
    		htmlXmlSwfChart = new HtmlXmlSwfChart(Boolean.FALSE, chartId, chartType, height, width, xmlPath, (ArrayList<ArrayList<Map<String, String>>>)webutil.getRequestAttribute(list), getChartLabelInstance(), getChartNoteInstance(), getChartTooltipInstance(), getChartHorizontalInstance(), getChartVerticalInstance(), getChartGuideInstance(), getChartTransitionInstance(), getChartSeriesInstance(), seriesColors, getChartAxisCategoryInstance(), axisCategoryLabels, getChartAxisTicksInstance(), getChartAxisValueInstance(), axisValueLabels, getChartLegendInstance());
    	}
		
		return htmlXmlSwfChart;
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
	
	private XmlSwfChartLabel getChartLabelInstance() throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		XmlSwfChartLabel chartLabel = null;
		
		if(!isAllAttributeNull("ChartLabel")) {
			chartLabel = new XmlSwfChartLabel();
			
			setAttribute(chartLabel, "ChartLabel");			
		}
		
		return chartLabel;
	}
	
	private XmlSwfChartNote getChartNoteInstance() throws IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
		XmlSwfChartNote chartNote = null;
		
		if(!isAllAttributeNull("ChartNote")) {
			chartNote = new XmlSwfChartNote();
			
			setAttribute(chartNote, "ChartNote");
		}
		
		return chartNote;
	}
	
	private XmlSwfChartTooltip getChartTooltipInstance() throws IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
		XmlSwfChartTooltip chartTooltip = null;
		
		if(!isAllAttributeNull("ChartTooltip")) {
			chartTooltip = new XmlSwfChartTooltip();
			
			setAttribute(chartTooltip, "ChartTooltip");
		}
		
		return chartTooltip;
	}
	
	private XmlSwfChartHorizontal getChartHorizontalInstance() throws IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
		XmlSwfChartHorizontal chartHorizontal = null;
		
		if(!isAllAttributeNull("ChartHorizontal")) {
			chartHorizontal = new XmlSwfChartHorizontal();
			
			setAttribute(chartHorizontal, "ChartHorizontal");
		}
		
		return chartHorizontal;
	}
	
	private XmlSwfChartVertical getChartVerticalInstance() throws IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
		XmlSwfChartVertical chartVertical = null;
		
		if(!isAllAttributeNull("ChartVertical")) {
			chartVertical = new XmlSwfChartVertical();
			
			setAttribute(chartVertical, "ChartVertical");
		}
		
		return chartVertical;
	}
	
	private XmlSwfChartGuide getChartGuideInstance() throws IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
		XmlSwfChartGuide chartGuide = null;
		
		if(!isAllAttributeNull("ChartGuide")) {
			chartGuide = new XmlSwfChartGuide();
			
			setAttribute(chartGuide, "ChartGuide");
		}
		
		return chartGuide;
	}
	
	private XmlSwfChartTransition getChartTransitionInstance() throws IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
		XmlSwfChartTransition chartTransition = null;
		
		if(!isAllAttributeNull("ChartTransition")) {
			chartTransition = new XmlSwfChartTransition();
			
			setAttribute(chartTransition, "ChartTransition");
		}
		
		return chartTransition;
	}
	
	private XmlSwfChartSeries getChartSeriesInstance() throws IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
		XmlSwfChartSeries chartSeries = null;
		
		if(!isAllAttributeNull("ChartSeries")) {
			chartSeries = new XmlSwfChartSeries();
			
			setAttribute(chartSeries, "ChartSeries");
		}
		
		return chartSeries;
	}
	
	private XmlSwfChartAxisCategory getChartAxisCategoryInstance() throws IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
		XmlSwfChartAxisCategory chartAxisCategory = null;
		
		if(!isAllAttributeNull("ChartAxisCategory")) {
			chartAxisCategory = new XmlSwfChartAxisCategory();
			
			setAttribute(chartAxisCategory, "ChartAxisCategory");
		}
		
		return chartAxisCategory;
	}
	
	private XmlSwfChartAxisTicks getChartAxisTicksInstance() throws IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
		XmlSwfChartAxisTicks chartAxisTicks = null;
		
		if(!isAllAttributeNull("ChartAxisTicks")) {
			chartAxisTicks = new XmlSwfChartAxisTicks();
			
			setAttribute(chartAxisTicks, "ChartAxisTicks");
		}
		
		return chartAxisTicks;
	}
	
	private XmlSwfChartAxisValue getChartAxisValueInstance() throws IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
		XmlSwfChartAxisValue chartAxisValue = null;
		
		if(!isAllAttributeNull("ChartAxisValue")) {
			chartAxisValue = new XmlSwfChartAxisValue();
			
			setAttribute(chartAxisValue, "ChartAxisValue");
		}
		
		return chartAxisValue;
	}
	
	private XmlSwfChartLegend getChartLegendInstance() throws IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
		XmlSwfChartLegend chartLegend = null;
		
		if(!isAllAttributeNull("ChartLegend")) {
			chartLegend = new XmlSwfChartLegend();
			
			setAttribute(chartLegend, "ChartLegend");
		}
		
		return chartLegend;
	}
	
	//元件屬性區
	public void setChartId(String chartId) {
		this.chartId = chartId;
	}
	public String getChartId() {
		return chartId;
	}
	public void setXmlPath(String xmlPath) {
		this.xmlPath = StringUtility.removeStartEndSlash(xmlPath);
	}
	public String getXmlPath() {
		return xmlPath;
	}
	public void setList(String list) {
		this.list = list;
	}
	public String getList() {
		return list;
	}
	public void setChartType(String chartType) {
		this.chartType = chartType;
	}
	public String getChartType() {
		return chartType;
	}
	public void setChartLabelPrefix(String chartLabelPrefix) {
		this.chartLabelPrefix = chartLabelPrefix;
	}
	public String getChartLabelPrefix() {
		return chartLabelPrefix;
	}
	public void setChartLabelSuffix(String chartLabelSuffix) {
		this.chartLabelSuffix = chartLabelSuffix;
	}
	public String getChartLabelSuffix() {
		return chartLabelSuffix;
	}
	public void setChartLabelPosition(String chartLabelPosition) {
		this.chartLabelPosition = chartLabelPosition;
	}
	public String getChartLabelPosition() {
		return chartLabelPosition;
	}
	public void setRebuildXMLFlag(String rebuildXMLFlag) {
		this.rebuildXMLFlag = rebuildXMLFlag;
	}
	public String getRebuildXMLFlag() {
		return rebuildXMLFlag;
	}
	public void setChartLabelDecimals(String chartLabelDecimals) {
		this.chartLabelDecimals = chartLabelDecimals;
	}
	public String getChartLabelDecimals() {
		return chartLabelDecimals;
	}
	public void setChartLabelDecimalChar(String chartLabelDecimalChar) {
		this.chartLabelDecimalChar = chartLabelDecimalChar;
	}
	public String getChartLabelDecimalChar() {
		return chartLabelDecimalChar;
	}
	public void setChartLabelSeparator(String chartLabelSeparator) {
		this.chartLabelSeparator = chartLabelSeparator;
	}
	public String getChartLabelSeparator() {
		return chartLabelSeparator;
	}
	public void setChartLabelHideZero(String chartLabelHideZero) {
		this.chartLabelHideZero = chartLabelHideZero;
	}
	public String getChartLabelHideZero() {
		return chartLabelHideZero;
	}
	public void setChartLabelAsPercentage(String chartLabelAsPercentage) {
		this.chartLabelAsPercentage = chartLabelAsPercentage;
	}
	public String getChartLabelAsPercentage() {
		return chartLabelAsPercentage;
	}
	public void setChartLabelFont(String chartLabelFont) {
		this.chartLabelFont = chartLabelFont;
	}
	public String getChartLabelFont() {
		return chartLabelFont;
	}
	public void setChartLabelBold(String chartLabelBold) {
		this.chartLabelBold = chartLabelBold;
	}
	public String getChartLabelBold() {
		return chartLabelBold;
	}
	public void setChartLabelSize(String chartLabelSize) {
		this.chartLabelSize = chartLabelSize;
	}
	public String getChartLabelSize() {
		return chartLabelSize;
	}
	public void setChartLabelColor(String chartLabelColor) {
		this.chartLabelColor = chartLabelColor;
	}
	public String getChartLabelColor() {
		return chartLabelColor;
	}
	public void setChartLabelAlpha(String chartLabelAlpha) {
		this.chartLabelAlpha = chartLabelAlpha;
	}
	public String getChartLabelAlpha() {
		return chartLabelAlpha;
	}
	public void setChartLabelShadow(String chartLabelShadow) {
		this.chartLabelShadow = chartLabelShadow;
	}
	public String getChartLabelShadow() {
		return chartLabelShadow;
	}
	public void setChartLabelBevel(String chartLabelBevel) {
		this.chartLabelBevel = chartLabelBevel;
	}
	public String getChartLabelBevel() {
		return chartLabelBevel;
	}
	public void setChartLabelGlow(String chartLabelGlow) {
		this.chartLabelGlow = chartLabelGlow;
	}
	public String getChartLabelGlow() {
		return chartLabelGlow;
	}
	public void setChartLabelBlur(String chartLabelBlur) {
		this.chartLabelBlur = chartLabelBlur;
	}
	public String getChartLabelBlur() {
		return chartLabelBlur;
	}
	public void setChartNoteType(String chartNoteType) {
		this.chartNoteType = chartNoteType;
	}
	public String getChartNoteType() {
		return chartNoteType;
	}
	public void setChartNoteX(String chartNoteX) {
		this.chartNoteX = chartNoteX;
	}
	public String getChartNoteX() {
		return chartNoteX;
	}
	public void setChartNoteY(String chartNoteY) {
		this.chartNoteY = chartNoteY;
	}
	public String getChartNoteY() {
		return chartNoteY;
	}
	public void setChartNoteOffsetX(String chartNoteOffsetX) {
		this.chartNoteOffsetX = chartNoteOffsetX;
	}
	public String getChartNoteOffsetX() {
		return chartNoteOffsetX;
	}
	public void setChartNoteOffsetY(String chartNoteOffsetY) {
		this.chartNoteOffsetY = chartNoteOffsetY;
	}
	public String getChartNoteOffsetY() {
		return chartNoteOffsetY;
	}
	public void setChartNoteFont(String chartNoteFont) {
		this.chartNoteFont = chartNoteFont;
	}
	public String getChartNoteFont() {
		return chartNoteFont;
	}
	public void setChartNoteBold(String chartNoteBold) {
		this.chartNoteBold = chartNoteBold;
	}
	public String getChartNoteBold() {
		return chartNoteBold;
	}
	public void setChartNoteSize(String chartNoteSize) {
		this.chartNoteSize = chartNoteSize;
	}
	public String getChartNoteSize() {
		return chartNoteSize;
	}
	public void setChartNoteColor(String chartNoteColor) {
		this.chartNoteColor = chartNoteColor;
	}
	public String getChartNoteColor() {
		return chartNoteColor;
	}
	public void setChartNoteAlpha(String chartNoteAlpha) {
		this.chartNoteAlpha = chartNoteAlpha;
	}
	public String getChartNoteAlpha() {
		return chartNoteAlpha;
	}
	public void setChartNoteBackgroundColor(String chartNoteBackgroundColor) {
		this.chartNoteBackgroundColor = chartNoteBackgroundColor;
	}
	public String getChartNoteBackgroundColor() {
		return chartNoteBackgroundColor;
	}
	public void setChartNoteBackgroundAlpha(String chartNoteBackgroundAlpha) {
		this.chartNoteBackgroundAlpha = chartNoteBackgroundAlpha;
	}
	public String getChartNoteBackgroundAlpha() {
		return chartNoteBackgroundAlpha;
	}
	public void setChartNoteShadow(String chartNoteShadow) {
		this.chartNoteShadow = chartNoteShadow;
	}
	public String getChartNoteShadow() {
		return chartNoteShadow;
	}
	public void setChartNoteBevel(String chartNoteBevel) {
		this.chartNoteBevel = chartNoteBevel;
	}
	public String getChartNoteBevel() {
		return chartNoteBevel;
	}
	public void setChartNoteGlow(String chartNoteGlow) {
		this.chartNoteGlow = chartNoteGlow;
	}
	public String getChartNoteGlow() {
		return chartNoteGlow;
	}
	public void setChartNoteBlur(String chartNoteBlur) {
		this.chartNoteBlur = chartNoteBlur;
	}
	public String getChartNoteBlur() {
		return chartNoteBlur;
	}
	public void setChartTooltipFont(String chartTooltipFont) {
		this.chartTooltipFont = chartTooltipFont;
	}
	public String getChartTooltipFont() {
		return chartTooltipFont;
	}
	public void setChartTooltipBold(String chartTooltipBold) {
		this.chartTooltipBold = chartTooltipBold;
	}
	public String getChartTooltipBold() {
		return chartTooltipBold;
	}
	public void setChartTooltipSize(String chartTooltipSize) {
		this.chartTooltipSize = chartTooltipSize;
	}
	public String getChartTooltipSize() {
		return chartTooltipSize;
	}
	public void setChartTooltipColor(String chartTooltipColor) {
		this.chartTooltipColor = chartTooltipColor;
	}
	public String getChartTooltipColor() {
		return chartTooltipColor;
	}
	public void setChartTooltipAlpha(String chartTooltipAlpha) {
		this.chartTooltipAlpha = chartTooltipAlpha;
	}
	public String getChartTooltipAlpha() {
		return chartTooltipAlpha;
	}
	public void setChartTooltipBackgroundColor(
			String chartTooltipBackgroundColor) {
		this.chartTooltipBackgroundColor = chartTooltipBackgroundColor;
	}
	public String getChartTooltipBackgroundColor() {
		return chartTooltipBackgroundColor;
	}
	public void setChartTooltipBackgroundAlpha(
			String chartTooltipBackgroundAlpha) {
		this.chartTooltipBackgroundAlpha = chartTooltipBackgroundAlpha;
	}
	public String getChartTooltipBackgroundAlpha() {
		return chartTooltipBackgroundAlpha;
	}
	public void setChartTooltipDuration(String chartTooltipDuration) {
		this.chartTooltipDuration = chartTooltipDuration;
	}
	public String getChartTooltipDuration() {
		return chartTooltipDuration;
	}
	public void setChartTooltipShadow(String chartTooltipShadow) {
		this.chartTooltipShadow = chartTooltipShadow;
	}
	public String getChartTooltipShadow() {
		return chartTooltipShadow;
	}
	public void setChartTooltipBevel(String chartTooltipBevel) {
		this.chartTooltipBevel = chartTooltipBevel;
	}
	public String getChartTooltipBevel() {
		return chartTooltipBevel;
	}
	public void setChartTooltipGlow(String chartTooltipGlow) {
		this.chartTooltipGlow = chartTooltipGlow;
	}
	public String getChartTooltipGlow() {
		return chartTooltipGlow;
	}
	public void setChartTooltipBlur(String chartTooltipBlur) {
		this.chartTooltipBlur = chartTooltipBlur;
	}
	public String getChartTooltipBlur() {
		return chartTooltipBlur;
	}
	public void setChartHorizontalThickness(String chartHorizontalThickness) {
		this.chartHorizontalThickness = chartHorizontalThickness;
	}
	public String getChartHorizontalThickness() {
		return chartHorizontalThickness;
	}
	public void setChartHorizontalColor(String chartHorizontalColor) {
		this.chartHorizontalColor = chartHorizontalColor;
	}
	public String getChartHorizontalColor() {
		return chartHorizontalColor;
	}
	public void setChartHorizontalAlpha(String chartHorizontalAlpha) {
		this.chartHorizontalAlpha = chartHorizontalAlpha;
	}
	public String getChartHorizontalAlpha() {
		return chartHorizontalAlpha;
	}
	public void setChartHorizontalType(String chartHorizontalType) {
		this.chartHorizontalType = chartHorizontalType;
	}
	public String getChartHorizontalType() {
		return chartHorizontalType;
	}
	public void setChartVerticalThickness(String chartVerticalThickness) {
		this.chartVerticalThickness = chartVerticalThickness;
	}
	public String getChartVerticalThickness() {
		return chartVerticalThickness;
	}
	public void setChartVerticalColor(String chartVerticalColor) {
		this.chartVerticalColor = chartVerticalColor;
	}
	public String getChartVerticalColor() {
		return chartVerticalColor;
	}
	public void setChartVerticalAlpha(String chartVerticalAlpha) {
		this.chartVerticalAlpha = chartVerticalAlpha;
	}
	public String getChartVerticalAlpha() {
		return chartVerticalAlpha;
	}
	public void setChartVerticalType(String chartVerticalType) {
		this.chartVerticalType = chartVerticalType;
	}
	public String getChartVerticalType() {
		return chartVerticalType;
	}
	public void setChartGuideHorizontal(String chartGuideHorizontal) {
		this.chartGuideHorizontal = chartGuideHorizontal;
	}
	public String getChartGuideHorizontal() {
		return chartGuideHorizontal;
	}
	public void setChartGuideVertical(String chartGuideVertical) {
		this.chartGuideVertical = chartGuideVertical;
	}
	public String getChartGuideVertical() {
		return chartGuideVertical;
	}
	public void setChartGuideThickness(String chartGuideThickness) {
		this.chartGuideThickness = chartGuideThickness;
	}
	public String getChartGuideThickness() {
		return chartGuideThickness;
	}
	public void setChartGuideColor(String chartGuideColor) {
		this.chartGuideColor = chartGuideColor;
	}
	public String getChartGuideColor() {
		return chartGuideColor;
	}
	public void setChartGuideAlpha(String chartGuideAlpha) {
		this.chartGuideAlpha = chartGuideAlpha;
	}
	public String getChartGuideAlpha() {
		return chartGuideAlpha;
	}
	public void setChartGuideType(String chartGuideType) {
		this.chartGuideType = chartGuideType;
	}
	public String getChartGuideType() {
		return chartGuideType;
	}
	public void setChartGuideSnapH(String chartGuideSnapH) {
		this.chartGuideSnapH = chartGuideSnapH;
	}
	public String getChartGuideSnapH() {
		return chartGuideSnapH;
	}
	public void setChartGuideSnapV(String chartGuideSnapV) {
		this.chartGuideSnapV = chartGuideSnapV;
	}
	public String getChartGuideSnapV() {
		return chartGuideSnapV;
	}
	public void setChartGuideConnect(String chartGuideConnect) {
		this.chartGuideConnect = chartGuideConnect;
	}
	public String getChartGuideConnect() {
		return chartGuideConnect;
	}
	public void setChartGuideRadius(String chartGuideRadius) {
		this.chartGuideRadius = chartGuideRadius;
	}
	public String getChartGuideRadius() {
		return chartGuideRadius;
	}
	public void setChartGuideFillColor(String chartGuideFillColor) {
		this.chartGuideFillColor = chartGuideFillColor;
	}
	public String getChartGuideFillColor() {
		return chartGuideFillColor;
	}
	public void setChartGuideFillAlpha(String chartGuideFillAlpha) {
		this.chartGuideFillAlpha = chartGuideFillAlpha;
	}
	public String getChartGuideFillAlpha() {
		return chartGuideFillAlpha;
	}
	public void setChartGuideLineColor(String chartGuideLineColor) {
		this.chartGuideLineColor = chartGuideLineColor;
	}
	public String getChartGuideLineColor() {
		return chartGuideLineColor;
	}
	public void setChartGuideLineAlpha(String chartGuideLineAlpha) {
		this.chartGuideLineAlpha = chartGuideLineAlpha;
	}
	public String getChartGuideLineAlpha() {
		return chartGuideLineAlpha;
	}
	public void setChartGuideLineThickness(String chartGuideLineThickness) {
		this.chartGuideLineThickness = chartGuideLineThickness;
	}
	public String getChartGuideLineThickness() {
		return chartGuideLineThickness;
	}
	public void setChartGuideTextHAlpha(String chartGuideTextHAlpha) {
		this.chartGuideTextHAlpha = chartGuideTextHAlpha;
	}
	public String getChartGuideTextHAlpha() {
		return chartGuideTextHAlpha;
	}
	public void setChartGuideTextVAlpha(String chartGuideTextVAlpha) {
		this.chartGuideTextVAlpha = chartGuideTextVAlpha;
	}
	public String getChartGuideTextVAlpha() {
		return chartGuideTextVAlpha;
	}
	public void setChartGuidePrefixH(String chartGuidePrefixH) {
		this.chartGuidePrefixH = chartGuidePrefixH;
	}
	public String getChartGuidePrefixH() {
		return chartGuidePrefixH;
	}
	public void setChartGuideSuffixH(String chartGuideSuffixH) {
		this.chartGuideSuffixH = chartGuideSuffixH;
	}
	public String getChartGuideSuffixH() {
		return chartGuideSuffixH;
	}
	public void setChartGuidePrefixV(String chartGuidePrefixV) {
		this.chartGuidePrefixV = chartGuidePrefixV;
	}
	public String getChartGuidePrefixV() {
		return chartGuidePrefixV;
	}
	public void setChartGuideSuffixV(String chartGuideSuffixV) {
		this.chartGuideSuffixV = chartGuideSuffixV;
	}
	public String getChartGuideSuffixV() {
		return chartGuideSuffixV;
	}
	public void setChartGuideDecimals(String chartGuideDecimals) {
		this.chartGuideDecimals = chartGuideDecimals;
	}
	public String getChartGuideDecimals() {
		return chartGuideDecimals;
	}
	public void setChartGuideDecimalChar(String chartGuideDecimalChar) {
		this.chartGuideDecimalChar = chartGuideDecimalChar;
	}
	public String getChartGuideDecimalChar() {
		return chartGuideDecimalChar;
	}
	public void setChartGuideSeparator(String chartGuideSeparator) {
		this.chartGuideSeparator = chartGuideSeparator;
	}
	public String getChartGuideSeparator() {
		return chartGuideSeparator;
	}
	public void setChartGuideFont(String chartGuideFont) {
		this.chartGuideFont = chartGuideFont;
	}
	public String getChartGuideFont() {
		return chartGuideFont;
	}
	public void setChartGuideBold(String chartGuideBold) {
		this.chartGuideBold = chartGuideBold;
	}
	public String getChartGuideBold() {
		return chartGuideBold;
	}
	public void setChartGuideSize(String chartGuideSize) {
		this.chartGuideSize = chartGuideSize;
	}
	public String getChartGuideSize() {
		return chartGuideSize;
	}
	public void setChartGuideTextColor(String chartGuideTextColor) {
		this.chartGuideTextColor = chartGuideTextColor;
	}
	public String getChartGuideTextColor() {
		return chartGuideTextColor;
	}
	public void setChartGuideBackgroundColor(String chartGuideBackgroundColor) {
		this.chartGuideBackgroundColor = chartGuideBackgroundColor;
	}
	public String getChartGuideBackgroundColor() {
		return chartGuideBackgroundColor;
	}
	public void setChartTransitionType(String chartTransitionType) {
		this.chartTransitionType = chartTransitionType;
	}
	public String getChartTransitionType() {
		return chartTransitionType;
	}
	public void setChartTransitionDelay(String chartTransitionDelay) {
		this.chartTransitionDelay = chartTransitionDelay;
	}
	public String getChartTransitionDelay() {
		return chartTransitionDelay;
	}
	public void setChartTransitionDuration(String chartTransitionDuration) {
		this.chartTransitionDuration = chartTransitionDuration;
	}
	public String getChartTransitionDuration() {
		return chartTransitionDuration;
	}
	public void setChartTransitionOrder(String chartTransitionOrder) {
		this.chartTransitionOrder = chartTransitionOrder;
	}
	public String getChartTransitionOrder() {
		return chartTransitionOrder;
	}
	public void setChartSeriesBarGap(String chartSeriesBarGap) {
		this.chartSeriesBarGap = chartSeriesBarGap;
	}
	public String getChartSeriesBarGap() {
		return chartSeriesBarGap;
	}
	public void setChartSeriesSetGap(String chartSeriesSetGap) {
		this.chartSeriesSetGap = chartSeriesSetGap;
	}
	public String getChartSeriesSetGap() {
		return chartSeriesSetGap;
	}
	public void setChartSeriesTransfer(String chartSeriesTransfer) {
		this.chartSeriesTransfer = chartSeriesTransfer;
	}
	public String getChartSeriesTransfer() {
		return chartSeriesTransfer;
	}
	public void setChartAxisCategorySkip(String chartAxisCategorySkip) {
		this.chartAxisCategorySkip = chartAxisCategorySkip;
	}
	public String getChartAxisCategorySkip() {
		return chartAxisCategorySkip;
	}
	public void setChartAxisCategoryFont(String chartAxisCategoryFont) {
		this.chartAxisCategoryFont = chartAxisCategoryFont;
	}
	public String getChartAxisCategoryFont() {
		return chartAxisCategoryFont;
	}
	public void setChartAxisCategoryBold(String chartAxisCategoryBold) {
		this.chartAxisCategoryBold = chartAxisCategoryBold;
	}
	public String getChartAxisCategoryBold() {
		return chartAxisCategoryBold;
	}
	public void setChartAxisCategorySize(String chartAxisCategorySize) {
		this.chartAxisCategorySize = chartAxisCategorySize;
	}
	public String getChartAxisCategorySize() {
		return chartAxisCategorySize;
	}
	public void setChartAxisCategoryColor(String chartAxisCategoryColor) {
		this.chartAxisCategoryColor = chartAxisCategoryColor;
	}
	public String getChartAxisCategoryColor() {
		return chartAxisCategoryColor;
	}
	public void setChartAxisCategoryAlpha(String chartAxisCategoryAlpha) {
		this.chartAxisCategoryAlpha = chartAxisCategoryAlpha;
	}
	public String getChartAxisCategoryAlpha() {
		return chartAxisCategoryAlpha;
	}
	public void setChartAxisCategoryOrientation(
			String chartAxisCategoryOrientation) {
		this.chartAxisCategoryOrientation = chartAxisCategoryOrientation;
	}
	public String getChartAxisCategoryOrientation() {
		return chartAxisCategoryOrientation;
	}
	public void setChartAxisCategoryShadow(String chartAxisCategoryShadow) {
		this.chartAxisCategoryShadow = chartAxisCategoryShadow;
	}
	public String getChartAxisCategoryShadow() {
		return chartAxisCategoryShadow;
	}
	public void setChartAxisCategoryBevel(String chartAxisCategoryBevel) {
		this.chartAxisCategoryBevel = chartAxisCategoryBevel;
	}
	public String getChartAxisCategoryBevel() {
		return chartAxisCategoryBevel;
	}
	public void setChartAxisCategoryGlow(String chartAxisCategoryGlow) {
		this.chartAxisCategoryGlow = chartAxisCategoryGlow;
	}
	public String getChartAxisCategoryGlow() {
		return chartAxisCategoryGlow;
	}
	public void setChartAxisCategoryBlur(String chartAxisCategoryBlur) {
		this.chartAxisCategoryBlur = chartAxisCategoryBlur;
	}
	public String getChartAxisCategoryBlur() {
		return chartAxisCategoryBlur;
	}
	public void setChartAxisCategoryMargin(String chartAxisCategoryMargin) {
		this.chartAxisCategoryMargin = chartAxisCategoryMargin;
	}
	public String getChartAxisCategoryMargin() {
		return chartAxisCategoryMargin;
	}
	public void setChartAxisCategoryMin(String chartAxisCategoryMin) {
		this.chartAxisCategoryMin = chartAxisCategoryMin;
	}
	public String getChartAxisCategoryMin() {
		return chartAxisCategoryMin;
	}
	public void setChartAxisCategoryMax(String chartAxisCategoryMax) {
		this.chartAxisCategoryMax = chartAxisCategoryMax;
	}
	public String getChartAxisCategoryMax() {
		return chartAxisCategoryMax;
	}
	public void setChartAxisCategorySteps(String chartAxisCategorySteps) {
		this.chartAxisCategorySteps = chartAxisCategorySteps;
	}
	public String getChartAxisCategorySteps() {
		return chartAxisCategorySteps;
	}
	public void setChartAxisCategoryMode(String chartAxisCategoryMode) {
		this.chartAxisCategoryMode = chartAxisCategoryMode;
	}
	public String getChartAxisCategoryMode() {
		return chartAxisCategoryMode;
	}
	public void setChartAxisCategoryPrefix(String chartAxisCategoryPrefix) {
		this.chartAxisCategoryPrefix = chartAxisCategoryPrefix;
	}
	public String getChartAxisCategoryPrefix() {
		return chartAxisCategoryPrefix;
	}
	public void setChartAxisCategorySuffix(String chartAxisCategorySuffix) {
		this.chartAxisCategorySuffix = chartAxisCategorySuffix;
	}
	public String getChartAxisCategorySuffix() {
		return chartAxisCategorySuffix;
	}
	public void setChartAxisCategoryDecimals(String chartAxisCategoryDecimals) {
		this.chartAxisCategoryDecimals = chartAxisCategoryDecimals;
	}
	public String getChartAxisCategoryDecimals() {
		return chartAxisCategoryDecimals;
	}
	public void setChartAxisCategoryDecimalChar(
			String chartAxisCategoryDecimalChar) {
		this.chartAxisCategoryDecimalChar = chartAxisCategoryDecimalChar;
	}
	public String getChartAxisCategoryDecimalChar() {
		return chartAxisCategoryDecimalChar;
	}
	public void setChartAxisCategorySeparator(String chartAxisCategorySeparator) {
		this.chartAxisCategorySeparator = chartAxisCategorySeparator;
	}
	public String getChartAxisCategorySeparator() {
		return chartAxisCategorySeparator;
	}
	public void setAxisCategoryLabels(String axisCategoryLabels) {
		this.axisCategoryLabels = axisCategoryLabels;
	}
	public String getAxisCategoryLabels() {
		return axisCategoryLabels;
	}
	public void setChartAxisTicksValueTicks(String chartAxisTicksValueTicks) {
		this.chartAxisTicksValueTicks = chartAxisTicksValueTicks;
	}
	public String getChartAxisTicksValueTicks() {
		return chartAxisTicksValueTicks;
	}
	public void setChartAxisTicksCategoryTicks(
			String chartAxisTicksCategoryTicks) {
		this.chartAxisTicksCategoryTicks = chartAxisTicksCategoryTicks;
	}
	public String getChartAxisTicksCategoryTicks() {
		return chartAxisTicksCategoryTicks;
	}
	public void setChartAxisTicksPosition(String chartAxisTicksPosition) {
		this.chartAxisTicksPosition = chartAxisTicksPosition;
	}
	public String getChartAxisTicksPosition() {
		return chartAxisTicksPosition;
	}
	public void setChartAxisTicksMajorThickness(
			String chartAxisTicksMajorThickness) {
		this.chartAxisTicksMajorThickness = chartAxisTicksMajorThickness;
	}
	public String getChartAxisTicksMajorThickness() {
		return chartAxisTicksMajorThickness;
	}
	public void setChartAxisTicksMajorColor(String chartAxisTicksMajorColor) {
		this.chartAxisTicksMajorColor = chartAxisTicksMajorColor;
	}
	public String getChartAxisTicksMajorColor() {
		return chartAxisTicksMajorColor;
	}
	public void setChartAxisTicksMinorThickness(
			String chartAxisTicksMinorThickness) {
		this.chartAxisTicksMinorThickness = chartAxisTicksMinorThickness;
	}
	public String getChartAxisTicksMinorThickness() {
		return chartAxisTicksMinorThickness;
	}
	public void setChartAxisTicksMinorColor(String chartAxisTicksMinorColor) {
		this.chartAxisTicksMinorColor = chartAxisTicksMinorColor;
	}
	public String getChartAxisTicksMinorColor() {
		return chartAxisTicksMinorColor;
	}
	public void setChartAxisTicksMinorCount(String chartAxisTicksMinorCount) {
		this.chartAxisTicksMinorCount = chartAxisTicksMinorCount;
	}
	public String getChartAxisTicksMinorCount() {
		return chartAxisTicksMinorCount;
	}
	public void setChartAxisValueMin(String chartAxisValueMin) {
		this.chartAxisValueMin = chartAxisValueMin;
	}
	public String getChartAxisValueMin() {
		return chartAxisValueMin;
	}
	public void setChartAxisValueMax(String chartAxisValueMax) {
		this.chartAxisValueMax = chartAxisValueMax;
	}
	public String getChartAxisValueMax() {
		return chartAxisValueMax;
	}
	public void setChartAxisValueMode(String chartAxisValueMode) {
		this.chartAxisValueMode = chartAxisValueMode;
	}
	public String getChartAxisValueMode() {
		return chartAxisValueMode;
	}
	public void setChartAxisValueSteps(String chartAxisValueSteps) {
		this.chartAxisValueSteps = chartAxisValueSteps;
	}
	public String getChartAxisValueSteps() {
		return chartAxisValueSteps;
	}
	public void setChartAxisValuePrefix(String chartAxisValuePrefix) {
		this.chartAxisValuePrefix = chartAxisValuePrefix;
	}
	public String getChartAxisValuePrefix() {
		return chartAxisValuePrefix;
	}
	public void setChartAxisValueSuffix(String chartAxisValueSuffix) {
		this.chartAxisValueSuffix = chartAxisValueSuffix;
	}
	public String getChartAxisValueSuffix() {
		return chartAxisValueSuffix;
	}
	public void setChartAxisValueDecimals(String chartAxisValueDecimals) {
		this.chartAxisValueDecimals = chartAxisValueDecimals;
	}
	public String getChartAxisValueDecimals() {
		return chartAxisValueDecimals;
	}
	public void setChartAxisValueDecimalChar(String chartAxisValueDecimalChar) {
		this.chartAxisValueDecimalChar = chartAxisValueDecimalChar;
	}
	public String getChartAxisValueDecimalChar() {
		return chartAxisValueDecimalChar;
	}
	public void setChartAxisValueSeparator(String chartAxisValueSeparator) {
		this.chartAxisValueSeparator = chartAxisValueSeparator;
	}
	public String getChartAxisValueSeparator() {
		return chartAxisValueSeparator;
	}
	public void setChartAxisValueShowMin(String chartAxisValueShowMin) {
		this.chartAxisValueShowMin = chartAxisValueShowMin;
	}
	public String getChartAxisValueShowMin() {
		return chartAxisValueShowMin;
	}
	public void setChartAxisValueFont(String chartAxisValueFont) {
		this.chartAxisValueFont = chartAxisValueFont;
	}
	public String getChartAxisValueFont() {
		return chartAxisValueFont;
	}
	public void setChartAxisValueBold(String chartAxisValueBold) {
		this.chartAxisValueBold = chartAxisValueBold;
	}
	public String getChartAxisValueBold() {
		return chartAxisValueBold;
	}
	public void setChartAxisValueSize(String chartAxisValueSize) {
		this.chartAxisValueSize = chartAxisValueSize;
	}
	public String getChartAxisValueSize() {
		return chartAxisValueSize;
	}
	public void setChartAxisValueColor(String chartAxisValueColor) {
		this.chartAxisValueColor = chartAxisValueColor;
	}
	public String getChartAxisValueColor() {
		return chartAxisValueColor;
	}
	public void setChartAxisValueBackgroundColor(
			String chartAxisValueBackgroundColor) {
		this.chartAxisValueBackgroundColor = chartAxisValueBackgroundColor;
	}
	public String getChartAxisValueBackgroundColor() {
		return chartAxisValueBackgroundColor;
	}
	public void setChartAxisValueAlpha(String chartAxisValueAlpha) {
		this.chartAxisValueAlpha = chartAxisValueAlpha;
	}
	public String getChartAxisValueAlpha() {
		return chartAxisValueAlpha;
	}
	public void setChartAxisValueOrientation(String chartAxisValueOrientation) {
		this.chartAxisValueOrientation = chartAxisValueOrientation;
	}
	public String getChartAxisValueOrientation() {
		return chartAxisValueOrientation;
	}
	public void setChartAxisValueShadow(String chartAxisValueShadow) {
		this.chartAxisValueShadow = chartAxisValueShadow;
	}
	public String getChartAxisValueShadow() {
		return chartAxisValueShadow;
	}
	public void setChartAxisValueBevel(String chartAxisValueBevel) {
		this.chartAxisValueBevel = chartAxisValueBevel;
	}
	public String getChartAxisValueBevel() {
		return chartAxisValueBevel;
	}
	public void setChartAxisValueGlow(String chartAxisValueGlow) {
		this.chartAxisValueGlow = chartAxisValueGlow;
	}
	public String getChartAxisValueGlow() {
		return chartAxisValueGlow;
	}
	public void setChartAxisValueBlur(String chartAxisValueBlur) {
		this.chartAxisValueBlur = chartAxisValueBlur;
	}
	public String getChartAxisValueBlur() {
		return chartAxisValueBlur;
	}
	public void setAxisValueLabels(String axisValueLabels) {
		this.axisValueLabels = axisValueLabels;
	}
	public String getAxisValueLabels() {
		return axisValueLabels;
	}
	public void setChartLegendTransition(String chartLegendTransition) {
		this.chartLegendTransition = chartLegendTransition;
	}
	public String getChartLegendTransition() {
		return chartLegendTransition;
	}
	public void setChartLegendDelay(String chartLegendDelay) {
		this.chartLegendDelay = chartLegendDelay;
	}
	public String getChartLegendDelay() {
		return chartLegendDelay;
	}
	public void setChartLegendDuration(String chartLegendDuration) {
		this.chartLegendDuration = chartLegendDuration;
	}
	public String getChartLegendDuration() {
		return chartLegendDuration;
	}
	public void setChartLegendX(String chartLegendX) {
		this.chartLegendX = chartLegendX;
	}
	public String getChartLegendX() {
		return chartLegendX;
	}
	public void setChartLegendY(String chartLegendY) {
		this.chartLegendY = chartLegendY;
	}
	public String getChartLegendY() {
		return chartLegendY;
	}
	public void setChartLegendWidth(String chartLegendWidth) {
		this.chartLegendWidth = chartLegendWidth;
	}
	public String getChartLegendWidth() {
		return chartLegendWidth;
	}
	public void setChartLegendHeight(String chartLegendHeight) {
		this.chartLegendHeight = chartLegendHeight;
	}
	public String getChartLegendHeight() {
		return chartLegendHeight;
	}
	public void setChartLegendToggle(String chartLegendToggle) {
		this.chartLegendToggle = chartLegendToggle;
	}
	public String getChartLegendToggle() {
		return chartLegendToggle;
	}
	public void setChartLegendLayout(String chartLegendLayout) {
		this.chartLegendLayout = chartLegendLayout;
	}
	public String getChartLegendLayout() {
		return chartLegendLayout;
	}
	public void setChartLegendMargin(String chartLegendMargin) {
		this.chartLegendMargin = chartLegendMargin;
	}
	public String getChartLegendMargin() {
		return chartLegendMargin;
	}
	public void setChartLegendBullet(String chartLegendBullet) {
		this.chartLegendBullet = chartLegendBullet;
	}
	public String getChartLegendBullet() {
		return chartLegendBullet;
	}
	public void setChartLegendFont(String chartLegendFont) {
		this.chartLegendFont = chartLegendFont;
	}
	public String getChartLegendFont() {
		return chartLegendFont;
	}
	public void setChartLegendBold(String chartLegendBold) {
		this.chartLegendBold = chartLegendBold;
	}
	public String getChartLegendBold() {
		return chartLegendBold;
	}
	public void setChartLegendSize(String chartLegendSize) {
		this.chartLegendSize = chartLegendSize;
	}
	public String getChartLegendSize() {
		return chartLegendSize;
	}
	public void setChartLegendColor(String chartLegendColor) {
		this.chartLegendColor = chartLegendColor;
	}
	public String getChartLegendColor() {
		return chartLegendColor;
	}
	public void setChartLegendAlpha(String chartLegendAlpha) {
		this.chartLegendAlpha = chartLegendAlpha;
	}
	public String getChartLegendAlpha() {
		return chartLegendAlpha;
	}
	public void setChartLegendFillColor(String chartLegendFillColor) {
		this.chartLegendFillColor = chartLegendFillColor;
	}
	public String getChartLegendFillColor() {
		return chartLegendFillColor;
	}
	public void setChartLegendFillAlpha(String chartLegendFillAlpha) {
		this.chartLegendFillAlpha = chartLegendFillAlpha;
	}
	public String getChartLegendFillAlpha() {
		return chartLegendFillAlpha;
	}
	public void setChartLegendLineColor(String chartLegendLineColor) {
		this.chartLegendLineColor = chartLegendLineColor;
	}
	public String getChartLegendLineColor() {
		return chartLegendLineColor;
	}
	public void setChartLegendLineAlpha(String chartLegendLineAlpha) {
		this.chartLegendLineAlpha = chartLegendLineAlpha;
	}
	public String getChartLegendLineAlpha() {
		return chartLegendLineAlpha;
	}
	public void setChartLegendLineThickness(String chartLegendLineThickness) {
		this.chartLegendLineThickness = chartLegendLineThickness;
	}
	public String getChartLegendLineThickness() {
		return chartLegendLineThickness;
	}
	public void setChartLegendShadow(String chartLegendShadow) {
		this.chartLegendShadow = chartLegendShadow;
	}
	public String getChartLegendShadow() {
		return chartLegendShadow;
	}
	public void setChartLegendBevel(String chartLegendBevel) {
		this.chartLegendBevel = chartLegendBevel;
	}
	public String getChartLegendBevel() {
		return chartLegendBevel;
	}
	public void setChartLegendGlow(String chartLegendGlow) {
		this.chartLegendGlow = chartLegendGlow;
	}
	public String getChartLegendGlow() {
		return chartLegendGlow;
	}
	public void setChartLegendBlur(String chartLegendBlur) {
		this.chartLegendBlur = chartLegendBlur;
	}
	public String getChartLegendBlur() {
		return chartLegendBlur;
	}
	public void setSeriesColors(String seriesColors) {
		this.seriesColors = seriesColors;
	}
	public String getSeriesColors() {
		return seriesColors;
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
}