package csiebug.web.taglib.movingBox;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import csiebug.util.StringUtility;
import csiebug.util.WebUtility;
import csiebug.web.html.movingBox.HtmlMovingBox;

/**
 * MovingBox tag
 * @author George_Tsai
 * @version 2010/3/25
 */
public class MovingBoxTag extends BodyTagSupport implements TryCatchFinally {
	static final long serialVersionUID = 20100325;
	
	private String movingBoxId;
	private String imagePath;
	
	private String list;
	
	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {
		try { 
            JspWriter out = pageContext.getOut();
            
            
            WebUtility webutil = new WebUtility();
            
            ArrayList<Map<String, String>> data = null;
            if(list != null && webutil.getRequestAttribute(list) != null) {
            	data = (ArrayList<Map<String, String>>) webutil.getRequestAttribute(list);
            }
            
            HtmlMovingBox htmlMovingBox = new HtmlMovingBox(movingBoxId, imagePath, data);
            
            out.println(htmlMovingBox.render());
        } catch(Exception e) {
        	throw new JspException(e);
        } 
 
        return SKIP_BODY; 
	}
	public void doFinally() {
		
	}
	public void doCatch(Throwable e) throws Throwable {
        throw new JspException("MovingBoxTag Problem: " + e.getMessage());
    }
	
	//元件屬性區
	public void setMovingBoxId(String movingBoxId) {
		this.movingBoxId = movingBoxId;
	}
	public String getMovingBoxId() {
		return movingBoxId;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = StringUtility.removeStartEndSlash(imagePath);
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setList(String list) {
		this.list = list;
	}
	public String getList() {
		return list;
	}
}