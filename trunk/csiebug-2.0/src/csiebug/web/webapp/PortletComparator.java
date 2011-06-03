package csiebug.web.webapp;

import com.sun.portal.portletcontainer.driver.PortletWindowData;

import csiebug.domain.DashboardPortlet;
import csiebug.domain.User;

import java.io.Serializable;
import java.util.Comparator;

/**
 * dashboard排序portlet使用
 * @author George_Tsai
 * @version 2009/9/7
 *
 */
public class PortletComparator implements Comparator<PortletWindowData>, Serializable {
	private static final long serialVersionUID = 1L;
	
	private User user;
	
	public PortletComparator(User user) {
		this.user = user;
	}
	
	public int compare(PortletWindowData o1, PortletWindowData o2) {
		DashboardPortlet portlet1 = user.getDashboardPortlet(o1.getPortletWindowName());
		DashboardPortlet portlet2 = user.getDashboardPortlet(o2.getPortletWindowName());
		
		if(portlet1.getSortOrder() != null) {
			if(portlet2.getSortOrder() != null) {
				return portlet1.getSortOrder() - portlet2.getSortOrder();
			} else {
				return 1;
			}
		} else {
			if(portlet2.getSortOrder() != null) {
				return -1;
			} else {
				return 0;
			}
		}
	}
	
}
