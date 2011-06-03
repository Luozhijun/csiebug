package csiebug.web.html.grid;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import csiebug.util.AssertUtility;
import csiebug.util.WebUtility;
import csiebug.web.html.HtmlBuilder;
import csiebug.web.html.HtmlComponentOnlyBody;
import csiebug.web.html.HtmlRenderException;
import csiebug.web.html.form.HtmlText;


/**
 * 產生HTML Status Bar
 * @author George_Tsai
 * @version 2009/3/13
 */

public class HtmlStatusBar extends HtmlComponentOnlyBody {
	private String statusStyle;
	private List<Map<String, String>> listData;
	private int maxColumnCount;
	private int pagination;
	private String htmlId;
	private int headerRowCount;
	private HtmlToolBar htmlToolBar;
	
	private String strStartPageText;
	private String strEndPageText;
	private String strPrePageText;
	private String strNextPageText;
	private String strTotalDataStart;
	private String strTotalDataEnd;
	private String strTotalPageStart;
	private String strTotalPageEnd;
	
	//頁數太多的話,跳頁的連結會太長,所以一次show三頁
	private int defaultPageWindowSize = 3;
	
	/**
	 * Status Bar建構子
	 * @param styleType 選擇status bar種類
	 * @param list grid資料
	 * @param maxColumnCount grid最大的column數
	 * @param pagination grid所設定的分頁筆數
	 * @param id table id
	 * @param headerRowCount 此grid的標頭有幾列
	 * @author George_Tsai
	 * @version 2009/3/13
	 * @throws NamingException 
	 * @throws UnsupportedEncodingException 
	 */
	public HtmlStatusBar(String styleType, List<Map<String, String>> list, int maxColumnCount, int pagination, String id, int headerRowCount, HtmlToolBar htmlToolBar) throws UnsupportedEncodingException, NamingException {
		this.statusStyle = styleType;
		this.listData = list;
		this.maxColumnCount = maxColumnCount;
		this.pagination = pagination;
		this.htmlId = id;
		this.headerRowCount = headerRowCount;
		this.htmlToolBar = htmlToolBar;
		
		WebUtility webutil = new WebUtility();
		this.strPrePageText = webutil.getMessage("common.PrePage");
		this.strNextPageText = webutil.getMessage("common.NextPage");
		this.strStartPageText = webutil.getMessage("common.StartPage");
		this.strEndPageText = webutil.getMessage("common.EndPage");
		this.strTotalDataStart = webutil.getMessage("common.TotalDataStart");
		this.strTotalDataEnd = webutil.getMessage("common.TotalDataEnd");
		this.strTotalPageStart = webutil.getMessage("common.TotalPageStart");
		this.strTotalPageEnd = webutil.getMessage("common.TotalPageEnd");
	}
	
	/**
	 * 產生status bar
	 * @return
	 * @author George_Tsai
	 * @version 2008/12/23
	 * @throws HtmlRenderException 
	 * @throws NamingException 
	 * @throws UnsupportedEncodingException 
	 */
	public String renderBody(String content) throws HtmlRenderException {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		try {
	        if(listData != null && listData.size() > 0) {
	        	//可陸續新增客製化不同的status bar
	        	if(statusStyle.trim().equals("1")) {
	        		htmlBuilder.appendString(makeStatusBar1());
				} else if(statusStyle.startsWith("jaceju_")) {
	        		String cssName = statusStyle.substring(7, statusStyle.length());
	        		htmlBuilder.appendString(makeStatusBarJaceju(cssName));
	        	}
	        }
        } catch (UnsupportedEncodingException e) {
			throw new HtmlRenderException(e);
		} catch (NamingException e) {
			throw new HtmlRenderException(e);
		}
        
		return htmlBuilder.toString();
	}
	
	//第一種status bar
	private String makeStatusBar1() throws UnsupportedEncodingException, NamingException, HtmlRenderException {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
//    	分頁不設定且也沒設定toolbar按鈕,就不用顯示分頁列了
    	if(pagination > 0 || htmlToolBar != null) {
    		htmlBuilder.trStart();
        	htmlBuilder.className("TableStatusBar");
        	htmlBuilder.tagClose();
        	htmlBuilder.tdStart().align("right");
        	htmlBuilder.colspan("" + maxColumnCount);
        	htmlBuilder.tagClose();
        	
        	int totalPages = getTotalPages();
        	
        	//分頁不設定就沒有分頁的連結可用
        	if(pagination > 0 && totalPages > 1) {
        		//第一頁的link
	    		htmlBuilder.appendString(makeFirstPageLink(totalPages));
	    		
	    		htmlBuilder.space();
	    		
	    		//上一頁的link
	    		htmlBuilder.spanStart().id("prepage_" + htmlId).tagClose();
	    		htmlBuilder.appendString(makePrePageLink());
	    		htmlBuilder.spanEnd();
	    		
	    		//跳躍切換頁面的link
	    		for(int i = 0; i < totalPages; i++) {
	    			if(i < defaultPageWindowSize) {
	        			htmlBuilder.space();
	        			
	        			htmlBuilder.spanStart().id("page" + (i + 1) + "_" + htmlId).tagClose();
	        			if(i == 0) {
	        				htmlBuilder.appendString("[1]");
	        			} else {
	        				htmlBuilder.appendString(makePageLink(pagination, totalPages, (i + 1), headerRowCount));
	        			}
	        			htmlBuilder.spanEnd();
	    			} else {
	    				break;
	    			}
	    		}
	    		
	    		htmlBuilder.space();
	    		
	    		//下一頁的link
	    		htmlBuilder.spanStart().id("nextpage_" + htmlId).tagClose();
	    		htmlBuilder.appendString(makeNextPageLink(totalPages));
	    		htmlBuilder.spanEnd();
	    		
	    		htmlBuilder.space();
	    		
	    		//末頁的link
	    		htmlBuilder.appendString(makeLastPageLink(totalPages));
	    		
	    		//自由跳躍頁數
	    		htmlBuilder.appendString(makeFreePageControl(totalPages));
	    		
	    		//Page Data Summary
	    		htmlBuilder.tab();
	        	htmlBuilder.appendString(makePageDataSummary(totalPages));
        	}
        	
        	//toolbar按鈕
        	if(htmlToolBar != null) {
        		htmlBuilder.tab();
        		htmlBuilder.appendString(htmlToolBar.renderDownloadButton());
        	}
        	
        	htmlBuilder.tdEnd();
        	htmlBuilder.trEnd();
    	}
    	
    	return htmlBuilder.toString();
	}
	
	private String makeStatusBarJaceju(String cssClass) throws UnsupportedEncodingException, NamingException, HtmlRenderException {
		AssertUtility.notNull(cssClass);
		
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
//    	分頁不設定且也沒設定toolbar按鈕,就不用顯示分頁列了
    	if(pagination > 0 || htmlToolBar != null) {
    		htmlBuilder.trStart();
        	htmlBuilder.className("TableStatusBar");
        	htmlBuilder.tagClose();
        	htmlBuilder.tdStart().align("right");
        	htmlBuilder.colspan("" + maxColumnCount);
        	htmlBuilder.tagClose();
        	
        	int totalPages = getTotalPages();
    		
    		htmlBuilder.divStart().className("pagination " + cssClass).tagClose();
    		htmlBuilder.ul();
    		
    		//分頁不設定就沒有分頁的連結可用
        	if(pagination > 0 && totalPages > 1) {
	    		//第一頁的link
	    		htmlBuilder.li();
	    		htmlBuilder.appendString(makeFirstPageLink(totalPages));
	    		htmlBuilder.liEnd();
	    		
	    		//上一頁的link
	    		htmlBuilder.liStart().id("prepage_" + htmlId).tagClose();
	    		htmlBuilder.appendString(makePrePageLink());
	    		htmlBuilder.liEnd();
	    		
	    		//跳躍切換頁面的link
	    		for(int i = 0; i < totalPages; i++) {
	    			if(i < defaultPageWindowSize) {
	        			htmlBuilder.liStart().id("page" + (i + 1) + "_" + htmlId);
	        			if(i == 0) {
	        				htmlBuilder.className("current");
	        				htmlBuilder.tagClose();
	        				htmlBuilder.appendString("[1]");
	        			} else {
	        				htmlBuilder.tagClose();
	        				htmlBuilder.appendString(makePageLink(pagination, totalPages, (i + 1), headerRowCount));
	        			}
	        			htmlBuilder.liEnd();
	        		} else {
	    				break;
	    			}
	    		}
	    		
	    		//下一頁的link
	    		htmlBuilder.liStart().id("nextpage_" + htmlId).tagClose();
	    		htmlBuilder.appendString(makeNextPageLink(totalPages));
	    		htmlBuilder.liEnd();
	    		
	    		//末頁的link
	    		htmlBuilder.li();
	    		htmlBuilder.appendString(makeLastPageLink(totalPages));
	    		htmlBuilder.liEnd();
	    		
	    		//自由跳躍頁數
	    		htmlBuilder.appendString(makeFreePageControl(totalPages));
	    		
	    		//Page Data Summary
	    		htmlBuilder.tab();
	        	htmlBuilder.appendString(makePageDataSummary(totalPages));
        	}
        	
        	//toolbar按鈕
        	if(htmlToolBar != null) {
        		htmlBuilder.tab();
        		htmlBuilder.appendString(htmlToolBar.renderDownloadButton());
        	}
        	
        	htmlBuilder.ulEnd();
        	htmlBuilder.divEnd();
        	
        	htmlBuilder.tdEnd();
        	htmlBuilder.trEnd();
    	}
    	
    	return htmlBuilder.toString();
	}
	
	private int getTotalPages() {
		int totalPages = 1;
    	
		if(pagination > 0) {
			totalPages = (listData.size() / pagination);
			if((listData.size() % pagination) != 0) {
				totalPages++;
			}
		}
		
		return totalPages;
	}
	
	//第一頁的link
	private String makeFirstPageLink(int totalPages) {
		return makePageLink(pagination, totalPages, 1, headerRowCount, strStartPageText);
	}
	
	//上一頁的link(因為form load是第一頁,所以只有顯示文字)
	private String makePrePageLink() {
		return strPrePageText;
	}
	
	//下一頁的link
	private String makeNextPageLink(int totalPages) {
		if(getTotalPages() > 1) {
			return makePageLink(pagination, totalPages, 2, headerRowCount, strNextPageText);
		} else {
			return strNextPageText;
		}
	}
	
	//末頁的link
	private String makeLastPageLink(int totalPages) {
		return makePageLink(pagination, totalPages, totalPages, headerRowCount, strEndPageText);
	}
	
	//建立text和按鈕讓使用者自行輸入跳頁頁數
	private String makeFreePageControl(int totalPages) throws HtmlRenderException {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		HtmlText text = new HtmlText();
		text.setId(htmlId + "_freePage");
		text.setName(htmlId + "_freePage");
		text.setDataType("number");
		text.setTypesetting("false");
		text.setClassName("FreePage");
		htmlBuilder.appendString(text.render());
		
		htmlBuilder.buttonStart().type("button").onClick("goFreePage('" + htmlId + "', '" + pagination + "', '" + totalPages + "', document.getElementById('" + htmlId + "_freePage'), '" + headerRowCount + "', '" + strPrePageText + "', '" + strNextPageText + "');").tagClose().text("go").buttonEnd();
		
		return htmlBuilder.toString();
	}
	
	//產生跳頁的link
	private String makePageLink(int pagination, int totalPages, int thisPage, int headerRowCount) {
		return makePageLink(pagination, totalPages, thisPage, headerRowCount, "" + thisPage);
	}
	
	private String makePageLink(int pagination, int totalPages, int thisPage, int headerRowCount, String displayText) {
		AssertUtility.notNull(displayText);
		
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		htmlBuilder.aStart().href("JavaScript:toPage('" + htmlId + "', '" + pagination + "', '" + totalPages + "', '" + thisPage + "', '" + headerRowCount + "', '" + strPrePageText + "', '" + strNextPageText + "');").tagClose();
		htmlBuilder.appendString(displayText);
		htmlBuilder.aEnd();
		
		return htmlBuilder.toString(); 
	}
	
	private String makePageDataSummary(int totalPages) {
		return strTotalDataStart + listData.size() + strTotalDataEnd + "/" + strTotalPageStart + totalPages + strTotalPageEnd;
	}
}
