package csiebug.util;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import java.util.HashMap;
import java.util.Map;


/**
 * 監看session
 * @author George_Tsai
 * @version 2009/3/15
 */

public class WebSessionListener implements HttpSessionListener, HttpSessionAttributeListener {
	private static int activeSessionCount = 0;
	private static Map<String, String> sessions = new HashMap<String, String>();
	 
	public void sessionCreated(HttpSessionEvent event) {
		activeSessionCount++;
	}
	 
	public void sessionDestroyed(HttpSessionEvent event) {
		AssertUtility.notNull(event);
		
		if(activeSessionCount > 0) {
			activeSessionCount--;
		}
		
		//將已登入使用者登出
		String strSessionId = event.getSession().getId();
		
		synchronized(sessions){
			if(sessions.containsKey(strSessionId)){
				sessions.remove(strSessionId);
			}
		}
	}
	
	/**取得總連線數目,包括匿名連線者
	 * @author George_Tsai
	 * @version 2009/1/14
	 */
	public static int getActiveSessionCount() {
		return activeSessionCount;
	}
	
	/**取得登入的線上人數
	 * @author George_Tsai
	 * @version 2009/2/17
	 */
	public static int getOnlineUserCount() {
		return sessions.size();
	}
	
	/**取得線上所有session
	 * @author George_Tsai
	 * @version 2009/2/17
	 */
	public static Map<String, String> getOnlineSession() {
		return sessions;
	}
	
	/**
	 * 檢查此使用者是否登入系統
	 * @param strUserId
	 * @return
	 * @author George_Tsai
	 * @version 2009/2/17
	 */
	public static boolean checkLogin(String strSessionId, String strUserId) {
		AssertUtility.notNullAndNotSpace(strSessionId);
		AssertUtility.notNullAndNotSpace(strUserId);
		
		boolean flag = false;
		
		if(sessions.containsKey(strSessionId) && sessions.get(strSessionId).toString().equals(strUserId)) {
			flag = true;
		}
		
		return flag;
	}
	
	/**
	 * 檢查此session是否存在
	 * web server shutdown而不是使用者自行登出或session timeout
	 * 則存放在single sign on的table的資料還存在
	 * 所以當web server重啟後需要提供一個function可以查SSOId是否存在session中，以便清除資料庫的垃圾資料
	 * @param strUserId
	 * @return
	 * @author George_Tsai
	 * @version 2009/3/30
	 */
	public static boolean checkSSOIdExist(String strSessionId) {
		AssertUtility.notNullAndNotSpace(strSessionId);
		
		boolean flag = false;
		
		if(sessions.containsKey(strSessionId)) {
			flag = true;
		}
		
		return flag;
	}
	
	public void attributeAdded(HttpSessionBindingEvent event) {
		AssertUtility.notNull(event);
		
		if(event.getName().equals("UserId")){
			String strSessionId = event.getSession().getId();
			String strValue = (String)event.getValue();
			synchronized(sessions){
				if(sessions.containsKey(strSessionId)){
					sessions.remove(strSessionId);
				}
				sessions.put(strSessionId, strValue);
			}
		}
	}

	public void attributeRemoved(HttpSessionBindingEvent event) {
		AssertUtility.notNull(event);
		
		if(event.getName().equals("UserId")){
			String strSessionId = event.getSession().getId();
			synchronized(sessions){
				if(sessions.containsKey(strSessionId)){
					sessions.remove(strSessionId);
				}
			}
		}
	}
	
	public void attributeReplaced(HttpSessionBindingEvent event) {
		AssertUtility.notNull(event);
		
		if(event.getName().equals("UserId")){
			String strSessionId = event.getSession().getId();
			String strValue = (String)event.getValue();
			synchronized(sessions){
				if(sessions.containsKey(strSessionId)){
					sessions.remove(strSessionId);
				}
				sessions.put(strSessionId, strValue);
			}
		}
	}

}
