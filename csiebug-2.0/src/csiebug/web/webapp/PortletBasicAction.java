package csiebug.web.webapp;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import com.sun.portal.portletcontainer.driver.DriverUtil;

import csiebug.domain.Dashboard;
import csiebug.domain.User;
import csiebug.service.ServiceException;
import csiebug.util.ListUtility;

public abstract class PortletBasicAction extends BasicAction {
	private static final long serialVersionUID = 1L;
	
	protected void makeTabs(User loginUser, boolean adminFlag) throws UnsupportedEncodingException, NamingException {
		List<Map<String, String>> tabs = new ArrayList<Map<String,String>>();
		Map<String, String> tab;
		
		//加入使用者的dashboards
		if(loginUser != null) {
			Iterator<Dashboard> iterator = loginUser.getDashboards().iterator();
			while(iterator.hasNext()) {
				Dashboard dashboard = iterator.next();
				
				tab = new LinkedHashMap<String, String>();
				tab.put("id", dashboard.getDashboardId());
				tab.put("url", DriverUtil.getPortletsURL(getRequest()) + "?dashboardId=" + dashboard.getDashboardId());
				tab.put("title", dashboard.getDashboardName());
				tab.put("sortOrder", "" + dashboard.getSortOrder());
				tabs.add(tab);
			}
			
			tabs = ListUtility.sortByName(tabs, "sortOrder");
		} else {
			tab = new LinkedHashMap<String, String>();
			tab.put("id", "dt");
			tab.put("url", DriverUtil.getPortletsURL(getRequest()));
			tab.put("title", getMessage("common.dashboard"));
			tab.put("sortOrder", "0");
			tabs.add(tab);
		}
		
		//加入主控台(主控台擺最後)
		tab = new LinkedHashMap<String, String>();
		tab.put("id", "admin");
		tab.put("url", DriverUtil.getAdminURL(getRequest()));
		tab.put("title", getMessage("common.console"));
		tab.put("sortOrder", "99");
		tabs.add(tab);
		
		if(adminFlag) {
			setRequestAttribute("selectedTab", "admin");
		} else {
			if(getRequestValue("dashboardId").trim().equals("")) {
				setRequestAttribute("selectedTab", "dt");
			} else {
				setRequestAttribute("selectedTab", getRequestValue("dashboardId"));
			}
		}
		
		setRequestAttribute("tabs", tabs);
	}
	
	@SuppressWarnings("unchecked")
	protected void makePortletOption() throws ServiceException {
		List<String> listPortletWindow = getAuthorizedPortlets((List<String>)getSessionAttribute("com.sun.portal.portletcontainer.driver.admin.portlets"));
		
		Map<String, String> map = new LinkedHashMap<String, String>();
		for(int i = 0; i < listPortletWindow.size(); i++) {
			map.put(listPortletWindow.get(i), listPortletWindow.get(i));
		}
		
		setRequestAttribute("portletOption", map);
	}
	
	@SuppressWarnings("unchecked")
	protected void makePortletWindowOption() throws ServiceException {
		List<String> listPortletWindow = getAuthorizedPortlets((List<String>)getSessionAttribute("com.sun.portal.portletcontainer.driver.admin.portletWindows"));
		
		Map<String, String> map = new LinkedHashMap<String, String>();
		for(int i = 0; i < listPortletWindow.size(); i++) {
			map.put(listPortletWindow.get(i), listPortletWindow.get(i));
		}
		
		setRequestAttribute("portletWindowOption", map);
	}
	
	protected void makeColumnNameOption() throws UnsupportedEncodingException, NamingException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		
		map.put("thick", getMessage("modify-window.thick"));
		map.put("thin", getMessage("modify-window.thin"));
		
		setRequestAttribute("columnNameOption", map);
	}
	
	protected void makeShowHideOption() throws UnsupportedEncodingException, NamingException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		
		map.put("true", getMessage("modify-window.show"));
		map.put("false", getMessage("modify-window.hide"));
		
		setRequestAttribute("visibleOption", map);
	}
	
	//畫面控制項函數區結束
	
	//邏輯函數區
	//頁面動作所對應的處理method,business logic,請歸類到下面
	
	//邏輯函數區結束
}
