package csiebug.web.webapp.example.taglib.chart;

import java.util.ArrayList;
import java.util.List;

import csiebug.service.ServiceException;
import csiebug.util.AssertUtility;
import csiebug.web.html.chart.jqPlotBean.JQPlotBarRenderer;
import csiebug.web.html.chart.jqPlotBean.JQPlotCandlestickPoint;
import csiebug.web.html.chart.jqPlotBean.JQPlotExtraSeriesPoint;
import csiebug.web.html.chart.jqPlotBean.JQPlotLine;
import csiebug.web.html.chart.jqPlotBean.JQPlotMarkerRenderer;
import csiebug.web.html.chart.jqPlotBean.JQPlotOHLCRenderer;
import csiebug.web.html.chart.jqPlotBean.JQPlotPieRenderer;
import csiebug.web.html.chart.jqPlotBean.JQPlotPoint;
import csiebug.web.html.chart.jqPlotBean.JQPlotPointLabels;
import csiebug.web.html.chart.jqPlotBean.JQPlotSeries;
import csiebug.web.webapp.BasicAction;

public class JQPlotChartAction extends BasicAction {
	private static final long serialVersionUID = 1L;

	public String main() throws Exception {
		//頁面控制項需要的資料
		makeControl();
		
		//以下的if else處理各樣頁面的動作,可自行增加或修改
		if(getActFlag().equalsIgnoreCase("basic")) {	//Basic Chart Example
			makeLineData1();
			makeLineData2();
			makeLineData3();
			makeLineData4();
			
			return "basic";
		} else if(getActFlag().equalsIgnoreCase("axisAutoScale")) {	//Axis Auto Scale Example
			makeLineData5();
			
			return "axisAutoScale";
		} else if(getActFlag().equalsIgnoreCase("axisLabel")) {	//Axis Label Example
			makeLineData5();
			makeLineData6();
			
			return "axisLabel";
		} else if(getActFlag().equalsIgnoreCase("pie")) {	//Pie Chart Example
			makePieData1();
			makePieData2();
			makePieData3();
			makePieData4();
			makePieData5();
			
			return "pie";
		} else if(getActFlag().equalsIgnoreCase("logAxes")) {	//Logarithmic Axes Example
			makeLineData7();
			makeLineData8();
			makeLineData9();
			
			return "logAxes";
		} else if(getActFlag().equalsIgnoreCase("dateAxes")) { //Date Axes Example
			makeLineData10();
			
			return "dateAxes";
		} else if(getActFlag().equalsIgnoreCase("dataPointHighlight")) { //Data Point Highlight Example
			makeLineData11();
			
			return "dataPointHighlight";
		} else if(getActFlag().equalsIgnoreCase("candlestick")) { //Open Hi Low Close and Candlestick Charts example
			makeLineData12();
			makeLineData13();
			makeLineData14();
			
			return "candlestick";
		} else if(getActFlag().equalsIgnoreCase("zooming")) { //Zooming Example
			makeLineData5();
			makeLineData15();
			
			return "zooming";
		} else if(getActFlag().equalsIgnoreCase("zoomingByRemoteControl")) { //Zooming By Remote Control Example
			makeLineData5();
			
			return "zoomingByRemoteControl";
		} else if(getActFlag().equalsIgnoreCase("cursorLineDataTracking")) { //Cursor Line And Data Tracking Example
			makeLineData5();
			makeLineData15();
			
			return "cursorLineDataTracking";
		} else if(getActFlag().equalsIgnoreCase("bar")) { //Bar chart example
			makeBarData1();
			makeBarData2();
			makeBarData3();
			
			return "bar";
		} else if(getActFlag().equalsIgnoreCase("fillToZero")) { //Fill to zero example
			makeLineData16();
			
			return "fillToZero";
		} else  if(getActFlag().equalsIgnoreCase("dragableAndTrendLine")) { //Dragable and trend line example
			makeLineData5();
			
			return "dragableAndTrendLine";
		} else if(getActFlag().equalsIgnoreCase("stacked")) { // Stacked example
			makeBarData4();
			makeBarData5();
			makeBarData6();
			
			return "stacked";
		} else if(getActFlag().equalsIgnoreCase("dataPointLabel")) { //Data point label example
			makeLineData7();
			makeLineData6();
			makeLineData17();
			makeLineData18();
			makeBarData7();
			
			return "dataPointLabel";
		} else if(getActFlag().equalsIgnoreCase("rotatedAxisText")) { //Rotated axis text example
			makeLineData19();
			
			return "rotatedAxisText";
		}
		//頁面動作處理結束
		
		//若沒有導到特定處理頁面,則預設都導到formLoad頁
		return FORMLOAD;
	}
	
	//畫面控制項函數區
	private void makeControl() {
		//畫面控制項
		try {
			setRequestAttribute("FunctionName", getFunctionName());
		} catch(ServiceException se) {
			writeErrorLog(se);
			
			if(AssertUtility.isNotNullAndNotSpace(getFunctionId())) {
				setRequestAttribute("FunctionName", getFunctionId());
			}
		}
	}
	
	private void makeLineData1() {
		List<JQPlotLine> data = new ArrayList<JQPlotLine>();
		JQPlotLine line = new JQPlotLine();
		for (int i = 0; i < 5; i++){ 
			JQPlotPoint point = new JQPlotPoint();
			point.setX("" + i);
			point.setY("" + Math.cos(i));
			line.addPoint(point);
		} 
		data.add(line);
		
		line = new JQPlotLine();
		for (int i = 0; i < 5; i++){ 
			JQPlotPoint point = new JQPlotPoint();
			point.setX("" + i);
			point.setY("" + 2*Math.sin(i-1));
			line.addPoint(point);
		} 
		data.add(line);
		
		line = new JQPlotLine();
		for (int i = 0; i < 5; i++){ 
			JQPlotPoint point = new JQPlotPoint();
			point.setX("" + i);
			point.setY("" + (3 + Math.pow(i/4, 2)));
			line.addPoint(point);
		} 
		data.add(line);
		
		line = new JQPlotLine();
		for (int i = 0; i < 5; i++){ 
			JQPlotPoint point = new JQPlotPoint();
			point.setX("" + i);
			point.setY("" + (-3 - Math.pow(i/4, 2)));
			line.addPoint(point);
		} 
		data.add(line);
		
		setRequestAttribute("lineData1", data);
		
		List<JQPlotSeries> seriesList = new ArrayList<JQPlotSeries>();
		JQPlotSeries series = new JQPlotSeries();
		series.setLineWidth(new Float(2));
		JQPlotMarkerRenderer markerOptions = new JQPlotMarkerRenderer();
		markerOptions.setStyle(JQPlotMarkerRenderer.STYLE_DIAMOND);
		series.setMarkerOptions(markerOptions);
		seriesList.add(series);
		
		series = new JQPlotSeries();
		series.setShowLine(false);
		markerOptions = new JQPlotMarkerRenderer();
		markerOptions.setSize(new Float(7));
		markerOptions.setStyle(JQPlotMarkerRenderer.STYLE_X);
		series.setMarkerOptions(markerOptions);
		seriesList.add(series);
		
		series = new JQPlotSeries();
		markerOptions = new JQPlotMarkerRenderer();
		markerOptions.setStyle(JQPlotMarkerRenderer.STYLE_CIRCLE);
		series.setMarkerOptions(markerOptions);
		seriesList.add(series);
		
		series = new JQPlotSeries();
		series.setLineWidth(new Float(5));
		markerOptions = new JQPlotMarkerRenderer();
		markerOptions.setStyle(JQPlotMarkerRenderer.STYLE_FILLED_SQUARE);
		markerOptions.setSize(new Float(14));
		series.setMarkerOptions(markerOptions);
		seriesList.add(series);
		
		setRequestAttribute("seriesList1", seriesList);
	}
	
	private void makeLineData2() {
		List<JQPlotLine> data = new ArrayList<JQPlotLine>();
		JQPlotLine line = new JQPlotLine();
		for (int i = 0; i < 5; i++){ 
			JQPlotPoint point = new JQPlotPoint();
			point.setX("" + i);
			point.setY("" + Math.cos(i));
			line.addPoint(point);
		} 
		data.add(line);
		
		setRequestAttribute("lineData2", data);
		
		List<JQPlotSeries> seriesList = new ArrayList<JQPlotSeries>();
		JQPlotSeries series = new JQPlotSeries();
		series.setShowMarker(false);
		series.setLineWidth(new Float(5));
		series.setShadowAngle(0);
		series.setShadowOffset(new Float(1.5));
		series.setShadowAlpha(new Float(0.08));
		series.setShadowDepth(6);
		seriesList.add(series);
		
		setRequestAttribute("seriesList2", seriesList);
	}
	
	private void makeLineData3() {
		List<JQPlotLine> data = new ArrayList<JQPlotLine>();
		JQPlotLine line = new JQPlotLine();
		JQPlotPoint point = new JQPlotPoint();
		point.setX("1");
		point.setY("1");
		line.addPoint(point);
		point = new JQPlotPoint();
		point.setX("1.5");
		point.setY("2.25");
		line.addPoint(point);
		point = new JQPlotPoint();
		point.setX("2");
		point.setY("4");
		line.addPoint(point);
		point = new JQPlotPoint();
		point.setX("2.5");
		point.setY("6.25");
		line.addPoint(point);
		point = new JQPlotPoint();
		point.setX("3");
		point.setY("9");
		line.addPoint(point);
		point = new JQPlotPoint();
		point.setX("3.5");
		point.setY("12.25");
		line.addPoint(point);
		point = new JQPlotPoint();
		point.setX("4");
		point.setY("16");
		line.addPoint(point);
		data.add(line);
		
		line = new JQPlotLine();
		point = new JQPlotPoint();
		point.setY("25");
		line.addPoint(point);
		point = new JQPlotPoint();
		point.setY("17.5");
		line.addPoint(point);
		point = new JQPlotPoint();
		point.setY("12.25");
		line.addPoint(point);
		point = new JQPlotPoint();
		point.setY("8.6");
		line.addPoint(point);
		point = new JQPlotPoint();
		point.setY("6.0");
		line.addPoint(point);
		point = new JQPlotPoint();
		point.setY("4.2");
		line.addPoint(point);
		point = new JQPlotPoint();
		point.setY("2.9");
		line.addPoint(point);
		data.add(line);
		
		line = new JQPlotLine();
		point = new JQPlotPoint();
		point.setY("4");
		line.addPoint(point);
		point = new JQPlotPoint();
		point.setY("25");
		line.addPoint(point);
		point = new JQPlotPoint();
		point.setY("13");
		line.addPoint(point);
		point = new JQPlotPoint();
		point.setY("22");
		line.addPoint(point);
		point = new JQPlotPoint();
		point.setY("14");
		line.addPoint(point);
		point = new JQPlotPoint();
		point.setY("17");
		line.addPoint(point);
		point = new JQPlotPoint();
		point.setY("15");
		line.addPoint(point);
		data.add(line);
		
		setRequestAttribute("lineData3", data);
		
		List<JQPlotSeries> seriesList = new ArrayList<JQPlotSeries>();
		JQPlotSeries series = new JQPlotSeries();
		series.setLabel("Rising line");
		series.setShowLine(false);
		JQPlotMarkerRenderer markerOptions = new JQPlotMarkerRenderer();
		markerOptions.setStyle(JQPlotMarkerRenderer.STYLE_SQUARE);
		series.setMarkerOptions(markerOptions);
		seriesList.add(series);
		
		series = new JQPlotSeries();
		series.setLabel("Declining line");
		seriesList.add(series);
		
		series = new JQPlotSeries();
		series.setLabel("Zig Zag Line");
		series.setLineWidth(new Float(5));
		series.setShowMarker(false);
		seriesList.add(series);
		
		setRequestAttribute("seriesList3", seriesList);
	}
	
	private void makeLineData4() {
		List<JQPlotLine> data = new ArrayList<JQPlotLine>();
		JQPlotLine line = new JQPlotLine();
		for (int i = 0; i < 5; i++){ 
			JQPlotPoint point = new JQPlotPoint();
			point.setX("" + i);
			point.setY("" + Math.cos(i));
			line.addPoint(point);
		} 
		data.add(line);
		
		line = new JQPlotLine();
		for (int i = 0; i < 5; i++){ 
			JQPlotPoint point = new JQPlotPoint();
			point.setX("" + i);
			point.setY("" + 2*Math.sin(i-1));
			line.addPoint(point);
		} 
		data.add(line);
		
		setRequestAttribute("lineData4", data);
		
		List<JQPlotSeries> seriesList = new ArrayList<JQPlotSeries>();
		JQPlotSeries series = new JQPlotSeries();
		series.setLabel("Rising line");
		JQPlotMarkerRenderer markerOptions = new JQPlotMarkerRenderer();
		markerOptions.setStyle(JQPlotMarkerRenderer.STYLE_SQUARE);
		series.setMarkerOptions(markerOptions);
		seriesList.add(series);
		
		series = new JQPlotSeries();
		series.setLabel("Declining line");
		seriesList.add(series);
		
		setRequestAttribute("seriesList4", seriesList);
		
		List<List<String>> xticks = new ArrayList<List<String>>();
		List<String> point = new ArrayList<String>();
		point.add("0");
		point.add("zero");
		xticks.add(point);
		
		point = new ArrayList<String>();
		point.add("1");
		point.add("one");
		xticks.add(point);

		point = new ArrayList<String>();
		point.add("2");
		point.add("two");
		xticks.add(point);
		
		point = new ArrayList<String>();
		point.add("3");
		point.add("three");
		xticks.add(point);
		
		point = new ArrayList<String>();
		point.add("4");
		point.add("four");
		xticks.add(point);
		
		point = new ArrayList<String>();
		point.add("5");
		point.add("five");
		xticks.add(point);
		
		setRequestAttribute("xticks4", xticks);
		
		List<Integer> yticks = new ArrayList<Integer>();
		yticks.add(-5);
		yticks.add(0);
		yticks.add(5);
		yticks.add(10);
		yticks.add(15);
		yticks.add(20);
		yticks.add(25);
		yticks.add(30);
		
		setRequestAttribute("yticks4", yticks);
	}
	
	private void makeLineData5() {
		List<JQPlotLine> data = new ArrayList<JQPlotLine>();
		JQPlotLine line = new JQPlotLine();
		for (int i = 0; i < 5; i++){ 
			JQPlotPoint point = new JQPlotPoint();
			point.setX("" + i);
			point.setY("" + Math.cos(i));
			line.addPoint(point);
		} 
		data.add(line);
		
		setRequestAttribute("lineData5", data);
	}
	
	private void makeLineData6() {
		List<JQPlotSeries> seriesList = new ArrayList<JQPlotSeries>();
		JQPlotSeries series = new JQPlotSeries();
		series.setShowMarker(false);
		seriesList.add(series);
		
		setRequestAttribute("seriesList6", seriesList);
	}
	
	private void makeLineData7() {
		List<JQPlotLine> data = new ArrayList<JQPlotLine>();
		JQPlotLine line = new JQPlotLine();
		JQPlotPoint point = new JQPlotPoint();
		point.setY("25");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("12.5");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("6.25");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("3.125");
		line.addPoint(point);
		 
		data.add(line);
		
		setRequestAttribute("lineData6", data);
	}
	
	private void makeLineData8() {
		List<JQPlotSeries> seriesList = new ArrayList<JQPlotSeries>();
		JQPlotSeries series = new JQPlotSeries();
		series.setLabel("Declining line");
		seriesList.add(series);
		
		setRequestAttribute("seriesList7", seriesList);
	}
	
	private void makeLineData9() {
		List<Integer> yticks = new ArrayList<Integer>();
		yticks.add(1);
		yticks.add(2);
		yticks.add(4);
		yticks.add(8);
		yticks.add(16);
		yticks.add(32);
		yticks.add(64);
		
		setRequestAttribute("yticks5", yticks);
	}
	
	private void makeLineData10() {
		List<JQPlotLine> data = new ArrayList<JQPlotLine>();
		JQPlotLine line = new JQPlotLine();
		JQPlotPoint point = new JQPlotPoint();
		point.setX("2008-09-30");
		point.setY("4");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("2008-10-30");
		point.setY("6.5");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("2008-11-30");
		point.setY("5.7");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("2008-12-30");
		point.setY("9");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("2009-01-30");
		point.setY("8.2");
		line.addPoint(point);
		 
		data.add(line);
		
		setRequestAttribute("lineData7", data);
		
		List<JQPlotSeries> seriesList = new ArrayList<JQPlotSeries>();
		JQPlotSeries series = new JQPlotSeries();
		series.setLineWidth(new Float(4));
		JQPlotMarkerRenderer markerOptions = new JQPlotMarkerRenderer();
		markerOptions.setStyle(JQPlotMarkerRenderer.STYLE_SQUARE);
		series.setMarkerOptions(markerOptions);
		seriesList.add(series);
		
		setRequestAttribute("seriesList8", seriesList);
	}
	
	private void makeLineData11() {
		List<JQPlotLine> data = new ArrayList<JQPlotLine>();
		JQPlotLine line = new JQPlotLine();
		JQPlotPoint point = new JQPlotPoint();
		point.setX("23-May-08");
		point.setY("578.55");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("20-Jun-08");
		point.setY("566.5");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("25-Jul-08");
		point.setY("480.88");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("22-Aug-08");
		point.setY("509.84");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("26-Sep-08");
		point.setY("454.13");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("24-Oct-08");
		point.setY("379.75");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("21-Nov-08");
		point.setY("303");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("26-Dec-08");
		point.setY("308.56");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("23-Jan-09");
		point.setY("299.14");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("20-Feb-09");
		point.setY("346.51");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("20-Mar-09");
		point.setY("325.99");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("24-Apr-09");
		point.setY("386.15");
		line.addPoint(point);
		 
		data.add(line);
		
		setRequestAttribute("lineData8", data);
	}
	
	private void makeLineData12() {
		List<JQPlotLine> data = new ArrayList<JQPlotLine>();
		JQPlotLine line = new JQPlotLine();
		JQPlotCandlestickPoint point = new JQPlotCandlestickPoint();
		point.setX("07/06/2009");
		point.setYOpen("138.7");
		point.setYHigh("139.68");
		point.setYLow("135.18");
		point.setYClose("135.4");
		line.addPoint(point);
		
		point = new JQPlotCandlestickPoint();
		point.setX("06/29/2009");
		point.setYOpen("143.46");
		point.setYHigh("144.66");
		point.setYLow("139.79");
		point.setYClose("140.02");
		line.addPoint(point);
		
		data.add(line);
		
		setRequestAttribute("lineData9", data);
	}
	
	private void makeLineData13() {
		List<JQPlotSeries> seriesList = new ArrayList<JQPlotSeries>();
		JQPlotSeries series = new JQPlotSeries();
		series.setRenderer("$.jqplot.OHLCRenderer");
		seriesList.add(series);
		
		setRequestAttribute("seriesList9", seriesList);
	}
	
	private void makeLineData14() {
		List<JQPlotSeries> seriesList = new ArrayList<JQPlotSeries>();
		JQPlotSeries series = new JQPlotSeries();
		series.setRenderer("$.jqplot.OHLCRenderer");
		JQPlotOHLCRenderer rendererOptions = new JQPlotOHLCRenderer();
		rendererOptions.setCandleStick(true);
		series.setRendererOptions(rendererOptions);
		seriesList.add(series);
		
		setRequestAttribute("seriesList10", seriesList);
	}
	
	private void makeLineData15() {
		List<JQPlotSeries> seriesList = new ArrayList<JQPlotSeries>();
		JQPlotSeries series = new JQPlotSeries();
		series.setLabel("Google, Inc.");
		series.setNeighborThreshold(-1);
		seriesList.add(series);
		
		setRequestAttribute("seriesList11", seriesList);
	}
	
	private void makeLineData16() {
		List<JQPlotLine> data = new ArrayList<JQPlotLine>();
		JQPlotLine line = new JQPlotLine();
		JQPlotPoint point = new JQPlotPoint();
		point.setY("-4");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("-7");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("9");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("16");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("3");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("5");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("-2");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("1");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("-6");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("-3");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("-2");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("8");
		line.addPoint(point);
		 
		data.add(line);
		
		setRequestAttribute("lineData10", data);
		
		List<JQPlotSeries> seriesList = new ArrayList<JQPlotSeries>();
		JQPlotSeries series = new JQPlotSeries();
		series.setFill(true);
		series.setFillToZero(true);
		series.setShowMarker(false);
		seriesList.add(series);
		
		setRequestAttribute("seriesList11", seriesList);
	}
	
	private void makeLineData17() {
		List<JQPlotLine> data = new ArrayList<JQPlotLine>();
		JQPlotLine line = new JQPlotLine();
		JQPlotExtraSeriesPoint point = new JQPlotExtraSeriesPoint();
		point.setX("-12");
		point.setY("7");
		line.addPoint(point);
		
		point = new JQPlotExtraSeriesPoint();
		point.setX("-3");
		point.setY("14");
		line.addPoint(point);
		
		point = new JQPlotExtraSeriesPoint();
		point.setX("2");
		point.setY("-1");
		point.setExtraSeriesData("(low)");
		line.addPoint(point);
		
		point = new JQPlotExtraSeriesPoint();
		point.setX("7");
		point.setY("-1");
		point.setExtraSeriesData("(low)");
		line.addPoint(point);
		
		point = new JQPlotExtraSeriesPoint();
		point.setX("11");
		point.setY("11");
		line.addPoint(point);
		
		point = new JQPlotExtraSeriesPoint();
		point.setX("13");
		point.setY("-1");
		point.setExtraSeriesData("(low)");
		line.addPoint(point);
		
		data.add(line);
		
		setRequestAttribute("lineData11", data);
		
		List<JQPlotSeries> seriesList = new ArrayList<JQPlotSeries>();
		JQPlotSeries series = new JQPlotSeries();
		series.setShowMarker(false);
		
		JQPlotPointLabels pointLabels = new JQPlotPointLabels();
		pointLabels.setLocation(JQPlotPointLabels.LOCATION_S);
		pointLabels.setYpadding(3);
		series.setPointLabels(pointLabels);
		
		seriesList.add(series);
		
		setRequestAttribute("seriesList12", seriesList);
	}
	
	private void makeLineData18() {
		List<JQPlotSeries> seriesList = new ArrayList<JQPlotSeries>();
		JQPlotSeries series = new JQPlotSeries();
		JQPlotPointLabels pointLabels = new JQPlotPointLabels();
		List<String> labels = new ArrayList<String>();
		labels.add("fourteen");
		labels.add("thirty two");
		labels.add("fourty one");
		labels.add("fourty four");
		pointLabels.setLabels(labels);
		series.setPointLabels(pointLabels);
		series.setRenderer(JQPlotSeries.RENDERER_BAR);
		seriesList.add(series);
		
		setRequestAttribute("seriesList13", seriesList);
	}
	
	private void makeLineData19() {
		List<JQPlotLine> data = new ArrayList<JQPlotLine>();
		JQPlotLine line = new JQPlotLine();
		JQPlotPoint point = new JQPlotPoint();
		point.setX("Cup Holder Pinion Bob");
		point.setY("7");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("Generic Fog Lamp");
		point.setY("9");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("HDTV Receiver");
		point.setY("15");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("8 Track Control Module");
		point.setY("12");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX(" Sludge Pump Fourier Modulator");
		point.setY("3");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("Transcender/Spice Rack");
		point.setY("6");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("Hair Spray Danger Indicator");
		point.setY("18");
		line.addPoint(point);
		
		data.add(line);
		
		setRequestAttribute("lineData12", data);
		
		List<JQPlotSeries> seriesList = new ArrayList<JQPlotSeries>();
		JQPlotSeries series = new JQPlotSeries();
		series.setRenderer(JQPlotSeries.RENDERER_BAR);
		seriesList.add(series);
		
		setRequestAttribute("seriesList13", seriesList);
	}
	
	private void makeBarData1() {
		List<JQPlotLine> data = new ArrayList<JQPlotLine>();
		JQPlotLine line = new JQPlotLine();
		JQPlotPoint point = new JQPlotPoint();
		point.setY("1");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("4");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("9");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("16");
		line.addPoint(point);
		
		data.add(line);
		
		line = new JQPlotLine();
		point = new JQPlotPoint();
		point.setY("25");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("12.5");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("6.25");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("3.125");
		line.addPoint(point);
		
		data.add(line);
		
		setRequestAttribute("barData1", data);
		
		List<JQPlotSeries> seriesList = new ArrayList<JQPlotSeries>();
		JQPlotSeries series = new JQPlotSeries();
		series.setLabel("Profits");
		series.setRenderer(JQPlotSeries.RENDERER_BAR);
		seriesList.add(series);
		
		series = new JQPlotSeries();
		series.setLabel("Expenses");
		series.setRenderer(JQPlotSeries.RENDERER_BAR);
		seriesList.add(series);
		
		setRequestAttribute("seriesList1", seriesList);
	}
	
	private void makeBarData2() {
		List<JQPlotLine> data = new ArrayList<JQPlotLine>();
		JQPlotLine line = new JQPlotLine();
		JQPlotPoint point = new JQPlotPoint();
		point.setY("1");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("4");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("9");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("16");
		line.addPoint(point);
		
		data.add(line);
		
		line = new JQPlotLine();
		point = new JQPlotPoint();
		point.setY("25");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("12.5");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("6.25");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("3.125");
		line.addPoint(point);
		
		data.add(line);
		
		line = new JQPlotLine();
		point = new JQPlotPoint();
		point.setY("2");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("7");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("15");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("30");
		line.addPoint(point);
		
		data.add(line);
		
		setRequestAttribute("barData2", data);
		
		List<JQPlotSeries> seriesList = new ArrayList<JQPlotSeries>();
		
		JQPlotBarRenderer renderOption = new JQPlotBarRenderer();
		renderOption.setBarPadding(8);
		renderOption.setBarMargin(20);
		
		JQPlotSeries series = new JQPlotSeries();
		series.setLabel("Profits");
		series.setRenderer(JQPlotSeries.RENDERER_BAR);
		series.setRendererOptions(renderOption);
		seriesList.add(series);
		
		series = new JQPlotSeries();
		series.setLabel("Expenses");
		series.setRenderer(JQPlotSeries.RENDERER_BAR);
		series.setRendererOptions(renderOption);
		seriesList.add(series);
		
		series = new JQPlotSeries();
		series.setLabel("Sales");
		series.setRenderer(JQPlotSeries.RENDERER_BAR);
		series.setRendererOptions(renderOption);
		seriesList.add(series);
		
		setRequestAttribute("seriesList2", seriesList);
		
		List<String> xticks = new ArrayList<String>();
		xticks.add("1st Qtr");
		xticks.add("2nd Qtr");
		xticks.add("3rd Qtr");
		xticks.add("4th Qtr");

		setRequestAttribute("xticks", xticks);
	}
	
	private void makeBarData3() {
		List<JQPlotLine> data = new ArrayList<JQPlotLine>();
		JQPlotLine line = new JQPlotLine();
		JQPlotPoint point = new JQPlotPoint();
		point.setX("1");
		point.setY("1");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("4");
		point.setY("2");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("9");
		point.setY("3");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("16");
		point.setY("4");
		line.addPoint(point);
		
		data.add(line);
		
		line = new JQPlotLine();
		point = new JQPlotPoint();
		point.setX("25");
		point.setY("1");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("12.5");
		point.setY("2");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("6.25");
		point.setY("3");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("3.125");
		point.setY("4");
		line.addPoint(point);
		
		data.add(line);
		
		setRequestAttribute("barData3", data);
		
		List<JQPlotSeries> seriesList = new ArrayList<JQPlotSeries>();
		
		JQPlotBarRenderer renderOption = new JQPlotBarRenderer();
		renderOption.setBarDirection(JQPlotBarRenderer.BAR_DIRECTION_HORIZONTAL);
		renderOption.setBarPadding(6);
		renderOption.setBarMargin(15);
		
		JQPlotSeries series = new JQPlotSeries();
		series.setLabel("Cats");
		series.setRenderer(JQPlotSeries.RENDERER_BAR);
		series.setRendererOptions(renderOption);
		seriesList.add(series);
		
		series = new JQPlotSeries();
		series.setLabel("Dogs");
		series.setRenderer(JQPlotSeries.RENDERER_BAR);
		series.setRendererOptions(renderOption);
		seriesList.add(series);
		
		setRequestAttribute("seriesList3", seriesList);
		
		List<String> yticks = new ArrayList<String>();
		yticks.add("Once");
		yticks.add("Twice");
		yticks.add("Three Times");
		yticks.add("More");

		setRequestAttribute("yticks", yticks);
	}
	
	private void makeBarData4() {
		List<JQPlotLine> data = new ArrayList<JQPlotLine>();
		JQPlotLine line = new JQPlotLine();
		JQPlotPoint point = new JQPlotPoint();
		point.setY("4");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("2");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("9");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("16");
		line.addPoint(point);
		
		data.add(line);
		
		line = new JQPlotLine();
		point = new JQPlotPoint();
		point.setY("3");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("7");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("6.25");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("3.125");
		line.addPoint(point);
		
		data.add(line);
		
		setRequestAttribute("barData4", data);
		
		List<JQPlotSeries> seriesList = new ArrayList<JQPlotSeries>();
		
		JQPlotBarRenderer rendererOptions = new JQPlotBarRenderer();
		rendererOptions.setBarWidth("50");
		
		JQPlotSeries series = new JQPlotSeries();
		series.setLabel("1st Qtr");
		series.setRenderer(JQPlotSeries.RENDERER_BAR);
		series.setRendererOptions(rendererOptions);
		seriesList.add(series);
		
		series = new JQPlotSeries();
		series.setLabel("2nd Qtr");
		series.setRenderer(JQPlotSeries.RENDERER_BAR);
		series.setRendererOptions(rendererOptions);
		seriesList.add(series);
		
		setRequestAttribute("seriesList4", seriesList);
		
		List<String> xticks = new ArrayList<String>();
		xticks.add("Red");
		xticks.add("Blue");
		xticks.add("Green");
		xticks.add("Yellow");

		setRequestAttribute("xticks2", xticks);
	}
	
	private void makeBarData5() {
		List<JQPlotLine> data = new ArrayList<JQPlotLine>();
		JQPlotLine line = new JQPlotLine();
		JQPlotPoint point = new JQPlotPoint();
		point.setX("4");
		point.setY("1");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("2");
		point.setY("2");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("9");
		point.setY("3");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("16");
		point.setY("4");
		line.addPoint(point);
		
		data.add(line);
		
		line = new JQPlotLine();
		point = new JQPlotPoint();
		point.setX("3");
		point.setY("1");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("7");
		point.setY("2");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("6.25");
		point.setY("3");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("3.125");
		point.setY("4");
		line.addPoint(point);
		
		data.add(line);
		
		setRequestAttribute("barData5", data);
		
		List<JQPlotSeries> seriesList = new ArrayList<JQPlotSeries>();
		
		JQPlotBarRenderer rendererOptions = new JQPlotBarRenderer();
		rendererOptions.setBarDirection(JQPlotBarRenderer.BAR_DIRECTION_HORIZONTAL);
		rendererOptions.setBarWidth("40");
		
		JQPlotSeries series = new JQPlotSeries();
		series.setLabel("Noisy");
		series.setRenderer(JQPlotSeries.RENDERER_BAR);
		series.setShadowAngle(135);
		series.setRendererOptions(rendererOptions);
		seriesList.add(series);
		
		series = new JQPlotSeries();
		series.setLabel("Quiet");
		series.setRenderer(JQPlotSeries.RENDERER_BAR);
		series.setShadowAngle(135);
		series.setRendererOptions(rendererOptions);
		seriesList.add(series);
		
		setRequestAttribute("seriesList5", seriesList);
		
		List<String> yticks = new ArrayList<String>();
		yticks.add("Q1");
		yticks.add("Q2");
		yticks.add("Q3");
		yticks.add("Q4");

		setRequestAttribute("yticks2", yticks);
	}
	
	private void makeBarData6() {
		List<JQPlotLine> data = new ArrayList<JQPlotLine>();
		JQPlotLine line = new JQPlotLine();
		JQPlotPoint point = new JQPlotPoint();
		point.setX("2006");
		point.setY("4");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("2007");
		point.setY("2");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("2008");
		point.setY("9");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("2009");
		point.setY("16");
		line.addPoint(point);
		
		data.add(line);
		
		line = new JQPlotLine();
		point = new JQPlotPoint();
		point.setX("2006");
		point.setY("3");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("2007");
		point.setY("7");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("2008");
		point.setY("6.25");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("2009");
		point.setY("3.125");
		line.addPoint(point);
		
		data.add(line);
		
		setRequestAttribute("barData6", data);
		
		List<JQPlotSeries> seriesList = new ArrayList<JQPlotSeries>();
		JQPlotSeries series = new JQPlotSeries();
		series.setFill(true);
		series.setShowMarker(false);
		series.setLabel("Traps Division");
		seriesList.add(series);
		
		series = new JQPlotSeries();
		series.setFill(true);
		series.setShowMarker(false);
		series.setLabel("Decoy Division");
		seriesList.add(series);
		
		setRequestAttribute("seriesList6", seriesList);
		
		List<String> xticks = new ArrayList<String>();
		xticks.add("2006");
		xticks.add("2007");
		xticks.add("2008");
		xticks.add("2009");

		setRequestAttribute("xticks3", xticks);
	}
	
	private void makeBarData7() {
		List<JQPlotLine> data = new ArrayList<JQPlotLine>();
		JQPlotLine line = new JQPlotLine();
		JQPlotPoint point = new JQPlotPoint();
		point.setY("14");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("32");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("41");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("44");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("40");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("37");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("29");
		line.addPoint(point);
		
		data.add(line);
		
		line = new JQPlotLine();
		point = new JQPlotPoint();
		point.setY("7");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("12");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("15");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("17");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("20");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("27");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setY("39");
		line.addPoint(point);
		
		data.add(line);
		
		setRequestAttribute("barData7", data);
		
		List<JQPlotSeries> seriesList = new ArrayList<JQPlotSeries>();
		
		JQPlotBarRenderer rendererOptions = new JQPlotBarRenderer();
		rendererOptions.setBarMargin(25);
		
		JQPlotPointLabels pointLabels = new JQPlotPointLabels();
		pointLabels.setStackedValue(true);
		
		JQPlotSeries series = new JQPlotSeries();
		series.setRenderer(JQPlotSeries.RENDERER_BAR);
		series.setRendererOptions(rendererOptions);
		series.setPointLabels(pointLabels);
		seriesList.add(series);
		
		series = new JQPlotSeries();
		series.setRenderer(JQPlotSeries.RENDERER_BAR);
		series.setRendererOptions(rendererOptions);
		series.setPointLabels(pointLabels);
		seriesList.add(series);
		
		setRequestAttribute("seriesList7", seriesList);
		
		List<String> yticks = new ArrayList<String>();
		yticks.add("0");
		yticks.add("20");
		yticks.add("40");
		yticks.add("60");
		yticks.add("80");

		setRequestAttribute("yticks3", yticks);
	}
	
	private void makePieData1() {
		List<JQPlotLine> data = new ArrayList<JQPlotLine>();
		JQPlotLine line = new JQPlotLine();
		JQPlotPoint point = new JQPlotPoint();
		point.setX("frogs");
		point.setY("3");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("buzzards");
		point.setY("7");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("deer");
		point.setY("2.5");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("turkeys");
		point.setY("6");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("moles");
		point.setY("5");
		line.addPoint(point);
		
		point = new JQPlotPoint();
		point.setX("ground hogs");
		point.setY("4");
		line.addPoint(point);
		
		data.add(line);
		
		setRequestAttribute("pieData1", data);
	}
	
	private void makePieData2() {
		List<JQPlotSeries> seriesList = new ArrayList<JQPlotSeries>();
		JQPlotSeries series = new JQPlotSeries();
		series.setRenderer(JQPlotSeries.RENDERER_PIE);
		seriesList.add(series);
		
		setRequestAttribute("seriesList1", seriesList);
	}
	
	private void makePieData3() {
		List<JQPlotSeries> seriesList = new ArrayList<JQPlotSeries>();
		JQPlotSeries series = new JQPlotSeries();
		series.setRenderer(JQPlotSeries.RENDERER_PIE);
		JQPlotPieRenderer rendererOptions = new JQPlotPieRenderer();
		rendererOptions.setSliceMargin(8);
		series.setRendererOptions(rendererOptions);
		seriesList.add(series);
		
		setRequestAttribute("seriesList2", seriesList);
	}
	
	private void makePieData4() {
		List<JQPlotSeries> seriesList = new ArrayList<JQPlotSeries>();
		JQPlotSeries series = new JQPlotSeries();
		series.setRenderer(JQPlotSeries.RENDERER_PIE);
		JQPlotPieRenderer rendererOptions = new JQPlotPieRenderer();
		rendererOptions.setSliceMargin(8);
		rendererOptions.setFill(false);
		series.setRendererOptions(rendererOptions);
		seriesList.add(series);
		
		setRequestAttribute("seriesList3", seriesList);
	}
	
	private void makePieData5() {
		List<JQPlotSeries> seriesList = new ArrayList<JQPlotSeries>();
		JQPlotSeries series = new JQPlotSeries();
		series.setRenderer(JQPlotSeries.RENDERER_PIE);
		JQPlotPieRenderer rendererOptions = new JQPlotPieRenderer();
		rendererOptions.setSliceMargin(8);
		rendererOptions.setFill(false);
		rendererOptions.setDiameter("100");
		series.setRendererOptions(rendererOptions);
		seriesList.add(series);
		
		setRequestAttribute("seriesList4", seriesList);
	}
	
	
	//畫面控制項函數區結束
	
	//邏輯函數區
	//頁面動作所對應的處理method,business logic,請歸類到下面
	
	//邏輯函數區結束
}
