package csiebug.web.html.progressbar;

import csiebug.web.html.HtmlBuilder;
import csiebug.web.html.HtmlComponent;


/**
 * 產生HTML ProgressBar
 * @author George_Tsai
 * @version 2009/6/10
 */

public class HtmlProgressBar extends HtmlComponent {
	private float uncomplete;
	private float unwork;
	/**
	 * ProgressBar建構子
	 * @param uncomplete 完成進度百分比 * 100
	 * @author George_Tsai
	 * @version 2009/6/10
	 */
	public HtmlProgressBar(float uncomplete) {
		this.uncomplete = uncomplete;
		this.unwork = 100 - uncomplete;
	}
	
	public String renderStart() {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		htmlBuilder.tableStart().className("progressbar").cellspacing("0").cellpadding("0").border("0").tagClose();
		htmlBuilder.tr();
		
		return htmlBuilder.toString();
	}
	
	public String renderBody(String content) {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		if(uncomplete < 50) {
			htmlBuilder.tdStart().width(uncomplete + "%").className("progressbar_uncompleted").bgcolor("#0000FF").align("center").tagClose();
			htmlBuilder.space();
			htmlBuilder.tdEnd();
			
			htmlBuilder.tdStart().width(unwork + "%").className("progressbar_unworked").bgcolor("#C0C0C0").align("center").tagClose();
			htmlBuilder.text(uncomplete + "%");
			htmlBuilder.tdEnd();
		} else if(uncomplete < 100) {
			htmlBuilder.tdStart().width(uncomplete + "%").className("progressbar_uncompleted").bgcolor("#0000FF").align("center").tagClose();
			htmlBuilder.text(uncomplete + "%");
			htmlBuilder.tdEnd();
			
			htmlBuilder.tdStart().width(unwork + "%").className("progressbar_unworked").bgcolor("#C0C0C0").align("center").tagClose();
			htmlBuilder.space();
			htmlBuilder.tdEnd();
		} else if(uncomplete == 100) {
			htmlBuilder.tdStart().width("100%").className("progressbar_completed").bgcolor("#0000FF").align("center").tagClose();
			htmlBuilder.text("100%");
			htmlBuilder.tdEnd();
		} else {
			htmlBuilder.tdStart().width("100%").className("progressbar_exceeded").bgcolor("#0000FF").align("center").tagClose();
			htmlBuilder.text(uncomplete + "%");
			htmlBuilder.tdEnd();
		}
		
		return htmlBuilder.toString();
	}
	
	public String renderEnd() {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		htmlBuilder.trEnd();
		htmlBuilder.tableEnd();
		
		return htmlBuilder.toString();
	}
}
