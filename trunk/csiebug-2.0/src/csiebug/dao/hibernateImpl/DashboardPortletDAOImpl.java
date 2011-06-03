package csiebug.dao.hibernateImpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import csiebug.domain.DashboardPortlet;
import csiebug.domain.hibernateImpl.DashboardPortletImpl;
import csiebug.dao.DAOException;
import csiebug.dao.DashboardPortletDAO;
import csiebug.util.AssertUtility;

/**
 * 
 * @author George_Tsai
 * @version 2009/8/19
 *
 */
public class DashboardPortletDAOImpl extends BasicDAOImpl implements DashboardPortletDAO {
	private static final long serialVersionUID = 1L;
	
	private void addRestriction(DashboardPortlet portlet) throws DAOException {
		if(AssertUtility.isNotNullAndNotSpace(portlet.getUserId())) {
			add(Restrictions.eq("userId", portlet.getUserId()));
		}
		
		if(AssertUtility.isNotNullAndNotSpace(portlet.getDashboardId())) {
			add(Restrictions.eq("dashboardId", portlet.getDashboardId()));
		}
		
		if(AssertUtility.isNotNullAndNotSpace(portlet.getPortletId())) {
			add(Restrictions.eq("portletId", portlet.getPortletId()));
		}
		
		if(AssertUtility.isNotNullAndNotSpace(portlet.getPortletTitle())) {
			add(Restrictions.like("portletTitle", "%" + portlet.getPortletTitle() + "%"));
		}
		
		if(portlet.getVisible() != null) {
			add(Restrictions.eq("visible", portlet.getVisible()));
		}
		
		if(AssertUtility.isNotNullAndNotSpace(portlet.getColumnName())) {
			add(Restrictions.eq("columnName", portlet.getColumnName()));
		}
		
		if(portlet.getSortOrder() != null) {
			add(Restrictions.eq("sortOrder", portlet.getSortOrder()));
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<DashboardPortlet> search(Object obj) throws DAOException {
		setTable(DashboardPortletImpl.class);
		
		if(obj != null) {
			DashboardPortlet portlet = (DashboardPortlet)obj;
			
			addRestriction(portlet);
			addOrder(Order.asc("sortOrder"));
		}
		
		return (List<DashboardPortlet>)query();
	}
	
	@SuppressWarnings("unchecked")
	public List<DashboardPortlet> searchPagination(Object obj, int firstResult, int maxResults) throws DAOException {
		setTable(DashboardPortletImpl.class);
		
		if(obj != null) {
			DashboardPortlet portlet = (DashboardPortlet)obj;
			
			addRestriction(portlet);
			addOrder(Order.asc("sortOrder"));
		}
		
		setFirstResult(firstResult);
		setMaxResults(maxResults);
		
		return (List<DashboardPortlet>)query();
	}
	
	public int searchRowCount(Object obj) throws DAOException {
		setTable(DashboardPortletImpl.class);
		setProjection(Projections.rowCount());
		
		if(obj != null) {
			DashboardPortlet portlet = (DashboardPortlet)obj;
			
			addRestriction(portlet);
		}
		
		return ((Integer)query().get(0)).intValue();
	}
	
	public void delete(Object[] objs) throws DAOException {
		if(AssertUtility.isAllElementNotNull(objs)) {
			String hql = "delete DashboardPortletImpl where :whereCondition";
			StringBuffer whereCondition = new StringBuffer();
			HashMap<String, String> parameters = new HashMap<String, String>();
			
			for(int i = 0; i < objs.length; i++) {
				if(i != 0) {
					whereCondition.append(" or ");
				}
				
				DashboardPortlet portlet = (DashboardPortlet)objs[i];
				parameters.put("userId_" + i, portlet.getUserId());
				parameters.put("dashboardId_" + i, portlet.getDashboardId());
				parameters.put("portletId_" + i, portlet.getPortletId());
				whereCondition.append("(userId = :userId_" + i + " and dashboardId = :dashboardId_" + i + " and portletId = :portletId_" + i + ")");
			}
			
			hql = hql.replace(":whereCondition", whereCondition.toString());
			executeUpdate(hql, parameters);
		}
	}

	public void delete(List<?> list) throws DAOException {
		if(AssertUtility.isAllElementNotNull(list)) {
			String hql = "delete DashboardPortletImpl where :whereCondition";
			StringBuffer whereCondition = new StringBuffer();
			HashMap<String, String> parameters = new HashMap<String, String>();
			
			for(int i = 0; i < list.size(); i++) {
				if(i != 0) {
					whereCondition.append(" or ");
				}
				
				DashboardPortlet portlet = (DashboardPortlet)list.get(i);
				parameters.put("userId_" + i, portlet.getUserId());
				parameters.put("dashboardId_" + i, portlet.getDashboardId());
				parameters.put("portletId_" + i, portlet.getPortletId());
				whereCondition.append("(userId = :userId_" + i + " and dashboardId = :dashboardId_" + i + " and portletId = :portletId_" + i + ")");
			}
			
			hql = hql.replace(":whereCondition", whereCondition.toString());
			executeUpdate(hql, parameters);
		}
	}

	public void delete(Set<?> set) throws DAOException {
		if(AssertUtility.isAllElementNotNull(set)) {
			String hql = "delete DashboardPortletImpl where :whereCondition";
			StringBuffer whereCondition = new StringBuffer();
			HashMap<String, String> parameters = new HashMap<String, String>();
			
			Iterator<?> iterator = set.iterator();
			
			int i = 0;
			while(iterator.hasNext()) {
				if(i != 0) {
					whereCondition.append(" or ");
				}
				
				DashboardPortlet portlet = (DashboardPortlet)iterator.next();
				parameters.put("userId_" + i, portlet.getUserId());
				parameters.put("dashboardId_" + i, portlet.getDashboardId());
				parameters.put("portletId_" + i, portlet.getPortletId());
				whereCondition.append("(userId = :userId_" + i + " and dashboardId = :dashboardId_" + i + " and portletId = :portletId_" + i + ")");
				
				i++;
			}
			
			hql = hql.replace(":whereCondition", whereCondition.toString());
			executeUpdate(hql, parameters);
		}
	}
}
