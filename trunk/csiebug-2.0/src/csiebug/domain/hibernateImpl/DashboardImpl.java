package csiebug.domain.hibernateImpl;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import csiebug.domain.Dashboard;
import csiebug.domain.DashboardPortlet;

/**
 * 
 * @author George_Tsai
 * @version 2009/8/17
 *
 */
public class DashboardImpl extends BasicObjectImpl implements Dashboard {
	private static final long serialVersionUID = 1L;
	
	private String userId;
	private String dashboardId;
	private String dashboardName;
	private Integer sortOrder;
	private String layout;
	
	private Set<DashboardPortlet> portlets = new HashSet<DashboardPortlet>();
	
	public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        
        if(!(obj instanceof DashboardImpl)) {
            return false;
        }
        
        DashboardImpl dashboard = (DashboardImpl) obj;
        
        return new EqualsBuilder().append(this.userId, dashboard.getUserId()).append(this.dashboardId, dashboard.getDashboardId()).isEquals();
    }
    
    public int hashCode() {
        return new HashCodeBuilder().append(this.userId).append(this.dashboardId).toHashCode();
    }
    
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserId() {
		return userId;
	}
	public void setDashboardId(String dashboardId) {
		this.dashboardId = dashboardId;
	}
	public String getDashboardId() {
		return dashboardId;
	}
	public void setDashboardName(String dashboardName) {
		this.dashboardName = dashboardName;
	}
	public String getDashboardName() {
		return dashboardName;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	public Integer getSortOrder() {
		return sortOrder;
	}
	public void setLayout(String layout) {
		this.layout = layout;
	}
	public String getLayout() {
		return layout;
	}

	public void setPortlets(Set<DashboardPortlet> portlets) {
		this.portlets = portlets;
	}

	public Set<DashboardPortlet> getPortlets() {
		return portlets;
	}
	
	public void addPortlet(DashboardPortlet portlet) {
		portlets.add(portlet);
	}
	
	public void removePortlet(DashboardPortlet portlet) {
		portlets.remove(portlet);
	}
}
