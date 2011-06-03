package csiebug.web.html.chart;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import csiebug.util.AssertUtility;
import csiebug.util.ListUtility;
import csiebug.util.WebUtility;
import csiebug.web.html.HtmlBuilder;
import csiebug.web.html.HtmlComponentNoBody;
import csiebug.web.html.chart.raphaelJSBean.RaphaelBar;
import csiebug.web.html.chart.raphaelJSBean.RaphaelLine;
import csiebug.web.html.chart.raphaelJSBean.RaphaelPie;
import csiebug.web.html.chart.raphaelJSBean.RaphaelTitle;

/**
 * 產生HTML Raphael JS Chart
 * @author George_Tsai
 * @version 2010/5/25
 */

public class HtmlRaphaelJSChart extends HtmlComponentNoBody {
	public static final String CHART_TYPE_PIE = "pie";
	public static final String CHART_TYPE_BAR = "bar";
	public static final String CHART_TYPE_HBAR = "hbar";
	public static final String CHART_TYPE_LINE = "line";
	
	public static final String CHART_BAR_TYPE_SHARP = "sharp";
	public static final String CHART_BAR_TYPE_ROUND = "round";
	public static final String CHART_BAR_TYPE_SOFT = "soft";
	
	public static final String CHART_BAR_HOVER_TYPE_1 = "1";
	public static final String CHART_BAR_HOVER_TYPE_2 = "2";
	
	private String chartId;
	private Integer chartHeight;
	private Integer chartWidth;
	private Integer chartX;
	private Integer chartY;
	private String chartType;
	private Boolean chartInteractive;
	
	private RaphaelTitle chartTitle;
	
	private RaphaelPie chartPie;
	
	private RaphaelBar chartBar;
	
	private RaphaelLine chartLine;
	
	private List<Map<String, String>> chartData;
	
	private Map<String, List<Map<String, String>>> chartData4Line;
	
	private WebUtility webutil = new WebUtility();
	
	/**
	 * chart建構子
	 * @author George_Tsai
	 * @version 2010/5/25
	 */
	public HtmlRaphaelJSChart(String id, Integer chartHeight, Integer chartWidth, Integer chartX, Integer chartY, Boolean chartInteractive, List<Map<String, String>> data, RaphaelTitle chartTitle, RaphaelPie chartPie) {
		this.chartId = id;
		this.chartHeight = chartHeight;
		this.chartWidth = chartWidth;
		this.chartX = chartX;
		this.chartY = chartY;
		this.chartInteractive = chartInteractive;
		this.chartData = data;
		this.chartTitle = chartTitle;
		this.chartPie = chartPie;
		this.chartType = CHART_TYPE_PIE;
	}
	
	/**
	 * chart建構子
	 * @author George_Tsai
	 * @version 2010/5/25
	 */
	public HtmlRaphaelJSChart(String id, Integer chartHeight, Integer chartWidth, Integer chartX, Integer chartY, Boolean chartInteractive, List<Map<String, String>> data, RaphaelTitle chartTitle, RaphaelBar chartBar) {
		this.chartId = id;
		this.chartHeight = chartHeight;
		this.chartWidth = chartWidth;
		this.chartX = chartX;
		this.chartY = chartY;
		this.chartInteractive = chartInteractive;
		this.chartData = data;
		this.chartTitle = chartTitle;
		this.chartBar = chartBar;
		
		if(chartBar != null && chartBar.getHorizontal() != null && chartBar.getHorizontal().equals(Boolean.TRUE)) {
			this.chartType = CHART_TYPE_HBAR;
		} else {
			this.chartType = CHART_TYPE_BAR;
		}
	}
	
	/**
	 * chart建構子
	 * @author George_Tsai
	 * @version 2010/5/25
	 */
	public HtmlRaphaelJSChart(String id, Integer chartHeight, Integer chartWidth, Integer chartX, Integer chartY, Boolean chartInteractive, Map<String, List<Map<String, String>>> data, RaphaelTitle chartTitle, RaphaelLine chartLine) {
		this.chartId = id;
		this.chartHeight = chartHeight;
		this.chartWidth = chartWidth;
		this.chartX = chartX;
		this.chartY = chartY;
		this.chartInteractive = chartInteractive;
		this.chartData4Line = data;
		this.chartTitle = chartTitle;
		this.chartLine = chartLine;
		this.chartType = CHART_TYPE_LINE;
	}
	
	private void makeOnLoadScript() {
		StringBuffer script = new StringBuffer();
		
		if(chartType != null) {
			script.append("var " + chartId + " = Raphael(\"" + chartId + "\");\n");
			script.append(chartId + ".g.txtattr.font = \"12px 'Fontin Sans', Fontin-Sans, sans-serif\";\n");
			
			makeTitle(script);
			
			if(chartType.equalsIgnoreCase(CHART_TYPE_PIE)) {
				makePie(script);
			} else if(chartType.equalsIgnoreCase(CHART_TYPE_BAR)) {
				makeBar(script, false);
			} else if(chartType.equalsIgnoreCase(CHART_TYPE_HBAR)) {
				makeBar(script, true);
			} else if(chartType.equalsIgnoreCase(CHART_TYPE_LINE)) {
				makeLine(script);
			}
			
			webutil.addPageLoadScript(script.toString());
		}
	}
	
	private void makeTitle(StringBuffer script) {
		if(chartTitle != null && chartTitle.getX() != null) {
			script.append(chartId + ".g.text(" + chartTitle.getX() + ", ");
		} else {
			script.append(chartId + ".g.text(320, ");
		}
		
		if(chartTitle != null && chartTitle.getY() != null) {
			script.append(chartTitle.getY() + ", ");
		} else {
			script.append("70, ");
		}
		
		if(chartTitle != null && chartTitle.getText() != null) {
			script.append("\"" + chartTitle.getText() + "\")");
		} else {
			script.append("\"\")");
		}
		
		if(chartTitle != null && chartTitle.getFontSize() != null) {
			script.append(".attr({\"font-size\": " + chartTitle.getFontSize() + "});\n");
		} else {
			script.append(".attr({\"font-size\": 20});\n");
		}
	}
	
	private void makePie(StringBuffer script) {
		if((chartInteractive == null || chartInteractive.equals(Boolean.TRUE)) &&
		   ((String)webutil.getRequestAttribute("Script")).indexOf("pie_fin = ") == -1) {
			script.append("var pie_fin = function () {\n");
			script.append("    this.sector.stop();\n");
			script.append("    this.sector.scale(1.1, 1.1, this.cx, this.cy);\n");
			script.append("    if (this.label) {\n");
			script.append("        this.label[0].stop();\n");
			script.append("        this.label[0].scale(1.5);\n");
			script.append("        this.label[1].attr({\"font-weight\": 800});\n");
			script.append("    }\n");
			script.append("};\n");
			script.append("var pie_fout = function () {\n");
			script.append("    this.sector.animate({scale: [1, 1, this.cx, this.cy]}, 500, \"bounce\");\n");
			script.append("    if (this.label) {\n");
			script.append("        this.label[0].animate({scale: 1}, 500, \"bounce\");\n");
			script.append("        this.label[1].attr({\"font-weight\": 400});\n");
			script.append("    }\n");
			script.append("};\n");
		}
		
		if(chartX != null) {
			script.append("var " + chartId + "_pie = " + chartId + ".g.piechart(" + chartX + ", ");
		} else {
			script.append("var " + chartId + "_pie = " + chartId + ".g.piechart(320, ");
		}
		
		if(chartY != null) {
			script.append(chartY + ", "); 
		} else {
			script.append("240, ");
		}
		
		if(chartPie != null && chartPie.getRadius() != null) {
			script.append(chartPie.getRadius() + ", ");
		} else {
			script.append("150, ");
		}
		
		chartData = ListUtility.sortByNameDesc(chartData, "amount");
		
		StringBuffer amounts = new StringBuffer();
		StringBuffer legends = new StringBuffer();
		StringBuffer hrefs = new StringBuffer();
		for(int i = 0; i < chartData.size(); i++) {
			Map<String, String> map = chartData.get(i);
			
			if(i != 0) {
				amounts.append(", ");
				legends.append(", ");
				hrefs.append(", ");
			}
			
			amounts.append(map.get("amount"));
			
			if(map.get("legend") != null) {
				legends.append("\"" + map.get("legend") + "\"");
			} else {
				legends.append("\"\"");
			}
			
			if(map.get("href") != null) {
				hrefs.append("\"" + map.get("href") + "\"");
			} else {
				hrefs.append("\"\"");
			}
		}
		
		script.append("[");
		script.append(amounts);
		script.append("], {legend: [");
		script.append(legends);
		script.append("], ");
		
		if(chartPie != null && chartPie.getLegendPosition() != null) {
			script.append("legendpos: \"" + chartPie.getLegendPosition() + "\", ");
		} else {
			script.append("legendpos: \"west\", ");
		}
		
		script.append("href: [");
		script.append(hrefs);
		script.append("]})");
		
		if(chartInteractive == null || chartInteractive.equals(Boolean.TRUE)) {
			script.append(".hover(pie_fin, pie_fout);");
		}
		
		script.append(";\n");
	}
	
	private void makeBar(StringBuffer script, boolean horizontalFlag) {
		if(chartInteractive == null || chartInteractive.equals(Boolean.TRUE)) {
			if(chartBar != null && chartBar.getHoverType() != null && chartBar.getHoverType().trim().equals(CHART_BAR_HOVER_TYPE_2)) {
				script.append(chartId + "_fin = function () {\n");
				script.append("    var y = [], res = [];\n");
				script.append("    for (var i = this.bars.length; i--;) {\n");
				script.append("        y.push(this.bars[i].y);\n");
				script.append("        res.push(this.bars[i].value || \"0\");\n");
				script.append("    }\n");
				script.append("    this.flag = " + chartId + ".g.popup(this.bars[0].x, Math.min.apply(Math, y), res.join(\", \")).insertBefore(this);\n");
				script.append("};\n");
			} else {
				script.append(chartId + "_fin = function () {\n");
				script.append("    this.flag = " + chartId + ".g.popup(this.bar.x, this.bar.y, this.bar.value || \"0\").insertBefore(this);\n");
				script.append("};\n");
			}
			script.append(chartId + "_fout = function () {\n");
			script.append("    this.flag.animate({opacity: 0}, 300, function () {this.remove();});\n");
			script.append("};\n");
		}
		
		if(horizontalFlag) {
			script.append("var " + chartId + "_barchart = " + chartId + ".g.hbarchart(");
		} else {
			script.append("var " + chartId + "_barchart = " + chartId + ".g.barchart(");
		}
		
		if(chartX != null) {
			script.append(chartX + ", ");
		} else {
			script.append("180, ");
		}
		
		if(chartY != null) {
			script.append(chartY + ", "); 
		} else {
			script.append("100, ");
		}
		
		if(chartBar != null && chartBar.getWidth() != null) {
			script.append(chartBar.getWidth() + ", ");
		} else {
			script.append("300, ");
		}
		
		if(chartBar != null && chartBar.getHeight() != null) {
			script.append(chartBar.getHeight() + ", ");
		} else {
			script.append("220, ");
		}
		
		script.append("[");
		for(int i = 0; i < chartData.size(); i++) {
			Map<String, String> map = chartData.get(i);
			
			if(i != 0) {
				script.append(", ");
			}
			
			script.append("[");
			
			Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
			int j = 0;
			while(iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				
				if(j != 0) {
					script.append(", ");
				}
				
				script.append(entry.getValue());
				
				j++;
			}
			
			script.append("]");
		}
		script.append("], ");
		
		script.append("{");
		
		if(chartBar != null && chartBar.getStacked() != null && chartBar.getStacked().equals(Boolean.TRUE)) {
			script.append("stacked: true");
		} else {
			script.append("stacked: false");
		}
		
		if(chartBar != null && chartBar.getType() != null) {
			script.append(", ");
			script.append("type: \"" + chartBar.getType() + "\"");
		}
		
		script.append("})");
		
		if(chartInteractive == null || chartInteractive.equals(Boolean.TRUE)) {
			if(chartBar != null && chartBar.getHoverType() != null && chartBar.getHoverType().trim().equals(CHART_BAR_HOVER_TYPE_2)) {
				script.append(".hoverColumn(" + chartId + "_fin, " + chartId + "_fout)");
			} else {
				script.append(".hover(" + chartId + "_fin, " + chartId + "_fout)");
			}
		}
		
		script.append(";");
	}
	
	private void makeLine(StringBuffer script) {
		if(chartInteractive == null || chartInteractive.equals(Boolean.TRUE)) {
			script.append(chartId + "_fin = function () {\n");
			script.append("    this.tags = " + chartId + ".set();\n");
			script.append("    for (var i = 0, ii = this.y.length; i < ii; i++) {\n");
			script.append("        this.tags.push(" + chartId + ".g.tag(this.x, this.y[i], this.values[i], 160, 10).insertBefore(this).attr([{fill: \"#fff\"}, {fill: this.symbols[i].attr(\"fill\")}]));\n");
			script.append("    }\n");
			script.append("};\n");
			script.append(chartId + "_fout = function () {\n");
			script.append("    this.tags && this.tags.remove();\n");
			script.append("};\n");
		}
		
		script.append("var " + chartId + "_linechart = " + chartId + ".g.linechart(");
		if(chartX != null) {
			script.append(chartX + ", ");
		} else {
			script.append("180, ");
		}
		
		if(chartY != null) {
			script.append(chartY + ", "); 
		} else {
			script.append("100, ");
		}
		
		if(chartLine != null && chartLine.getWidth() != null) {
			script.append(chartLine.getWidth() + ", ");
		} else {
			script.append("300, ");
		}
		
		if(chartLine != null && chartLine.getHeight() != null) {
			script.append(chartLine.getHeight() + ", ");
		} else {
			script.append("220, ");
		}
		
		script.append("[");
		if(chartData4Line.get("x") != null) {
			List<Map<String, String>> list = chartData4Line.get("x");
			for(int i = 0; i < list.size(); i++) {
				Map<String, String> map = list.get(i);
				
				if(i != 0) {
					script.append(", ");
				}
				
				script.append("[");
				
				Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
				int j = 0;
				while(iterator.hasNext()) {
					Entry<String, String> entry = iterator.next();
					
					if(j != 0) {
						script.append(", ");
					}
					
					script.append(entry.getValue());
					
					j++;
				}
				
				script.append("]");
			}
		}
		script.append("], ");
		
		script.append("[");
		if(chartData4Line.get("y") != null) {
			List<Map<String, String>> list = chartData4Line.get("y");
			for(int i = 0; i < list.size(); i++) {
				Map<String, String> map = list.get(i);
				
				if(i != 0) {
					script.append(", ");
				}
				
				script.append("[");
				
				Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
				int j = 0;
				while(iterator.hasNext()) {
					Entry<String, String> entry = iterator.next();
					
					if(j != 0) {
						script.append(", ");
					}
					
					script.append(entry.getValue());
					
					j++;
				}
				
				script.append("]");
			}
		}
		script.append("], ");
		
		script.append("{");
		
		if(chartLine != null && chartLine.getShade() != null && chartLine.getShade().equals(Boolean.TRUE)) {
			script.append("shade: true");
		} else {
			script.append("shade: false");
		}
		
		if(chartLine != null && chartLine.getNoStroke() != null && chartLine.getNoStroke().equals(Boolean.TRUE)) {
			script.append(",nostroke: true");
		} else {
			script.append(",nostroke: false");
		}
		
		if(chartLine != null && chartLine.getAxis() != null) {
			script.append(", ");
			script.append("axis: \"" + chartLine.getAxis() + "\"");
		}
		
		if(chartLine != null && chartLine.getSymbol() != null) {
			script.append(", ");
			script.append("symbol: \"" + chartLine.getSymbol() + "\"");
		} else if(chartInteractive == null || chartInteractive.equals(Boolean.TRUE)) {
			script.append(", ");
			script.append("symbol: \"o\"");
		}
		
		script.append("})");
		
		if(chartInteractive == null || chartInteractive.equals(Boolean.TRUE)) {
			script.append(".hoverColumn(" + chartId + "_fin, " + chartId + "_fout)");
		}
		
		script.append(";");
	}
	
	public String renderStart() {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		if(AssertUtility.isNotNullAndNotSpace(chartId)) {
			makeOnLoadScript();
			
			htmlBuilder.divStart().id(chartId);
			
			if(chartHeight != null) {
				if(chartWidth != null) {
					htmlBuilder.style("height: " + chartHeight + "px; width: " + chartWidth + "px");
				} else {
					htmlBuilder.style("height: " + chartHeight + "px; width: 500px");
				}
			} else {
				if(chartWidth != null) {
					htmlBuilder.style("height: 500px; width: " + chartWidth + "px");
				} else {
					htmlBuilder.style("height: 500px; width: 500px");
				}
			}
			
			htmlBuilder.tagClose();
		}
		
		return htmlBuilder.toString();
	}
	
	public String renderEnd() {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		if(AssertUtility.isNotNullAndNotSpace(chartId)) {
			htmlBuilder.divEnd();
		}
		
		return htmlBuilder.toString();
	}
}
