package csiebug.domain;

/**
 * 
 * @author George_Tsai
 * @version 2009/8/17
 *
 */
public interface DashboardPortlet extends BasicObject {
	void setUserId(String userId);
	String getUserId();
	void setDashboardId(String dashboardId);
	String getDashboardId();
	void setSortOrder(Integer sortOrder);
	Integer getSortOrder();
	void setPortletId(String portletId);
	String getPortletId();
	void setPortletTitle(String portletTitle);
	String getPortletTitle();
	void setVisible(Boolean visible);
	Boolean getVisible();
	void setColumnName(String columnName);
	String getColumnName();
	void setDashboard(Dashboard dashboard);
	Dashboard getDashboard();
}
