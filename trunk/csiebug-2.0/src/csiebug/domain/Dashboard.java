package csiebug.domain;

import java.util.Set;

/**
 * 
 * @author George_Tsai
 * @version 2009/8/17
 *
 */
public interface Dashboard extends BasicObject {
	void setUserId(String userId);
	String getUserId();
	void setDashboardId(String dashboardId);
	String getDashboardId();
	void setDashboardName(String dashboardName);
	String getDashboardName();
	void setSortOrder(Integer sortOrder);
	Integer getSortOrder();
	void setLayout(String layout);
	String getLayout();
	void setPortlets(Set<DashboardPortlet> portlets);
	Set<DashboardPortlet> getPortlets();
	void addPortlet(DashboardPortlet portlet);
	void removePortlet(DashboardPortlet portlet);
}
