package csiebug.web.html.movingBox;

import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import csiebug.util.AssertUtility;
import csiebug.util.StringUtility;
import csiebug.util.WebUtility;
import csiebug.web.html.HtmlBuilder;
import csiebug.web.html.HtmlComponent;
import csiebug.web.html.HtmlRenderException;


/**
 * 產生HTML MovingBox
 * @author George_Tsai
 * @version 2010/3/25
 */

public class HtmlMovingBox extends HtmlComponent {
	private String movingBoxId;
	private String imagePath;
	
	private List<Map<String, String>> movingBoxData;
	
	private WebUtility webutil = new WebUtility();
	
	/**
	 * MovingBox建構子
	 * @author George_Tsai
	 * @version 2010/3/25
	 */
	public HtmlMovingBox(String id, String imagePath, List<Map<String, String>> data) {
		this.movingBoxId = id;
		this.imagePath = StringUtility.removeStartEndSlash(imagePath);
		this.movingBoxData = data;
	}
	
	public String renderStart() throws HtmlRenderException {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		try {
			htmlBuilder.divStart().align("center");
			if(movingBoxId != null) {
				htmlBuilder.id(movingBoxId);
			}
			htmlBuilder.tagClose();
			
			htmlBuilder.divStart().id("slider").tagClose();
			
			if(imagePath != null) {
				htmlBuilder.imgStart().className("scrollButtons left").src(webutil.getBasePathForHTML() + imagePath + "/leftarrow.png").tagClose();
			} else {
				htmlBuilder.imgStart().className("scrollButtons left").src(webutil.getBasePathForHTML() + "images/leftarrow.png").tagClose();
			}
			
			htmlBuilder.divStart().style("overflow: hidden;").className("scroll").tagClose();
			
			htmlBuilder.divStart().className("scrollContainer").tagClose();
		} catch (NamingException e) {
			throw new HtmlRenderException(e);
		}
		
		return htmlBuilder.toString();
	}
	
	public String renderBody(String content) {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		if(movingBoxData != null) {
			for(int i = 0; i < movingBoxData.size(); i++) {
				Map<String, String> imageData = movingBoxData.get(i);
				
				htmlBuilder.divStart().className("panel");
				if(movingBoxId != null) {
					htmlBuilder.id("panel_" + movingBoxId + "_" + i);
				} else {
					htmlBuilder.id("panel_" + (i + 1));
				}
				htmlBuilder.tagClose();
				
				htmlBuilder.divStart().className("inside").tagClose();
				
				htmlBuilder.imgStart();
				if(AssertUtility.isNotNullAndNotSpace(imageData.get("imageURL"))) {
					htmlBuilder.src(imageData.get("imageURL"));
				}
				if(AssertUtility.isNotNullAndNotSpace(imageData.get("title"))) {
					htmlBuilder.alt(imageData.get("title"));
				}
				htmlBuilder.tagClose();
				
				htmlBuilder.tagStart("h2");
				if(AssertUtility.isNotNullAndNotSpace(imageData.get("title"))) {
					htmlBuilder.text(imageData.get("title"));
				}
				htmlBuilder.tagClose("h2");
				
				htmlBuilder.p();
				
				if(AssertUtility.isNotNullAndNotSpace(imageData.get("description"))) {
					htmlBuilder.text(imageData.get("description"));
				}
				
				htmlBuilder.pEnd();
				
				htmlBuilder.divEnd();
				
				htmlBuilder.divEnd();
			}
		}
		
		return htmlBuilder.toString();
	}
	
	public String renderEnd() throws HtmlRenderException {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		try {
			htmlBuilder.divEnd();
			
			htmlBuilder.divStart().id("left-shadow").tagClose().divEnd();
			htmlBuilder.divStart().id("right-shadow").tagClose().divEnd();
			
			htmlBuilder.divEnd();
			
			if(imagePath != null) {
				htmlBuilder.imgStart().className("scrollButtons right").src(webutil.getBasePathForHTML() + imagePath + "/rightarrow.png").tagClose();
			} else {
				htmlBuilder.imgStart().className("scrollButtons right").src(webutil.getBasePathForHTML() + "images/rightarrow.png").tagClose();
			}
			
			htmlBuilder.divEnd();
			
			htmlBuilder.divEnd();
		} catch (NamingException e) {
			throw new HtmlRenderException(e);
		}
		
		return htmlBuilder.toString();
	}
}
