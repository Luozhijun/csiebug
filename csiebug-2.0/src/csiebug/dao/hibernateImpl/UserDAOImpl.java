package csiebug.dao.hibernateImpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import csiebug.domain.Dashboard;
import csiebug.domain.DashboardPortlet;
import csiebug.domain.User;
import csiebug.domain.UserEmail;
import csiebug.domain.hibernateImpl.UserImpl;
import csiebug.dao.DAOException;
import csiebug.dao.UserDAO;
import csiebug.util.AssertUtility;

/**
 * 
 * @author George_Tsai
 * @version 2009/6/22
 *
 */
public class UserDAOImpl extends BasicDAOImpl implements UserDAO {
	private static final long serialVersionUID = 1L;

	private void addRestriction(User user) throws DAOException {
		if(AssertUtility.isNotNullAndNotSpace(user.getId())) {
			add(Restrictions.eq("id", user.getId()));
		}
		
		if(AssertUtility.isNotNullAndNotSpace(user.getPassword())) {
			add(Restrictions.eq("password", user.getPassword()));
		}
		
		if(user.getEnabled() != null) {
			add(Restrictions.eq("enabled", user.getEnabled()));
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<User> search(Object obj) throws DAOException {
		setTable(UserImpl.class);
		
		if(obj != null) {
			User user = (User)obj;
			
			addRestriction(user);
		}
		
		return (List<User>) query();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> searchPagination(Object obj, int firstResult, int maxResults) throws DAOException {
		setTable(UserImpl.class);
		
		if(obj != null) {
			User user = (User)obj;
			
			addRestriction(user);
		}
		
		setFirstResult(firstResult);
		setMaxResults(maxResults);
		
		return (List<User>) query();
	}
	
	public int searchRowCount(Object obj) throws DAOException {
		setTable(UserImpl.class);
		setProjection(Projections.rowCount());
		
		if(obj != null) {
			User user = (User)obj;
			
			addRestriction(user);
		}
		
		return ((Integer)query().get(0)).intValue();
	}
	
	public void delete(Object[] objs) throws DAOException {
		if(AssertUtility.isAllElementNotNull(objs)) {
			String hql = "delete UserImpl where id in (:userIds)";
			StringBuffer userIds = new StringBuffer();
			HashMap<String, String> parameters = new HashMap<String, String>();
			
			for(int i = 0; i < objs.length; i++) {
				if(i != 0) {
					userIds.append(", ");
				}
				
				User user = (User)objs[i];
				parameters.put("userId_" + i, user.getId());
				userIds.append(":userId_" + i);
			}
			
			hql = hql.replace(":userIds", userIds.toString());
			executeUpdate(hql, parameters);
		}
	}

	public void delete(List<?> list) throws DAOException {
		if(AssertUtility.isAllElementNotNull(list)) {
			String hql = "delete UserImpl where id in (:userIds)";
			StringBuffer userIds = new StringBuffer();
			HashMap<String, String> parameters = new HashMap<String, String>();
			
			for(int i = 0; i < list.size(); i++) {
				if(i != 0) {
					userIds.append(", ");
				}
				
				User user = (User)list.get(i);
				parameters.put("userId_" + i, user.getId());
				userIds.append(":userId_" + i);
			}
			
			hql = hql.replace(":userIds", userIds.toString());
			executeUpdate(hql, parameters);
		}
	}

	public void delete(Set<?> set) throws DAOException {
		if(AssertUtility.isAllElementNotNull(set)) {
			String hql = "delete UserImpl where id in (:userIds)";
			StringBuffer userIds = new StringBuffer();
			HashMap<String, String> parameters = new HashMap<String, String>();
			
			Iterator<?> iterator = set.iterator();
			
			int i = 0;
			while(iterator.hasNext()) {
				if(i != 0) {
					userIds.append(", ");
				}
				
				User user = (User)iterator.next();
				parameters.put("userId_" + i, user.getId());
				userIds.append(":userId_" + i);
				
				i++;
			}
			
			hql = hql.replace(":userIds", userIds.toString());
			executeUpdate(hql, parameters);
		}
	}
	
	public void deleteDashboard(Object[] objs) throws DAOException {
		if(AssertUtility.isAllElementNotNull(objs)) {
			String hql = "delete DashboardImpl where :condition";
			StringBuffer condition = new StringBuffer();
			HashMap<String, String> parameters = new HashMap<String, String>();
			
			for(int i = 0; i < objs.length; i++) {
				if(i != 0) {
					condition.append(" or ");
				}
				
				Dashboard dashboard = (Dashboard)objs[i];
				parameters.put("userId_" + i, dashboard.getUserId());
				parameters.put("dashboardId_" + i, dashboard.getDashboardId());
				condition.append("(userId = :userId_" + i + " and dashboardId = :dashboardId_" + i + ")");
			}
			
			hql = hql.replace(":condition", condition.toString());
			executeUpdate(hql, parameters);
		}
	}

	public void deleteDashboard(List<Dashboard> list) throws DAOException {
		if(AssertUtility.isAllElementNotNull(list)) {
			String hql = "delete DashboardImpl where :condition";
			StringBuffer condition = new StringBuffer();
			HashMap<String, String> parameters = new HashMap<String, String>();
			
			for(int i = 0; i < list.size(); i++) {
				if(i != 0) {
					condition.append(" or ");
				}
				
				Dashboard dashboard = (Dashboard)list.get(i);
				parameters.put("userId_" + i, dashboard.getUserId());
				parameters.put("dashboardId_" + i, dashboard.getDashboardId());
				condition.append("(userId = :userId_" + i + " and dashboardId = :dashboardId_" + i + ")");
			}
			
			hql = hql.replace(":condition", condition.toString());
			executeUpdate(hql, parameters);
		}
	}

	public void deleteDashboard(Set<Dashboard> set) throws DAOException {
		if(AssertUtility.isAllElementNotNull(set)) {
			String hql = "delete DashboardImpl where :condition";
			StringBuffer condition = new StringBuffer();
			HashMap<String, String> parameters = new HashMap<String, String>();
			
			Iterator<?> iterator = set.iterator();
			
			int i = 0;
			while(iterator.hasNext()) {
				if(i != 0) {
					condition.append(" or ");
				}
				
				Dashboard dashboard = (Dashboard)iterator.next();
				parameters.put("userId_" + i, dashboard.getUserId());
				parameters.put("dashboardId_" + i, dashboard.getDashboardId());
				condition.append("(userId = :userId_" + i + " and dashboardId = :dashboardId_" + i + ")");
				
				i++;
			}
			
			hql = hql.replace(":condition", condition.toString());
			executeUpdate(hql, parameters);
		}
	}
	
	public void deleteDashboardPortlet(Object[] objs) throws DAOException {
		if(AssertUtility.isAllElementNotNull(objs)) {
			String hql = "delete DashboardPortletImpl where :condition";
			StringBuffer condition = new StringBuffer();
			HashMap<String, String> parameters = new HashMap<String, String>();
			
			for(int i = 0; i < objs.length; i++) {
				if(i != 0) {
					condition.append(" or ");
				}
				
				DashboardPortlet portlet = (DashboardPortlet)objs[i];
				parameters.put("userId_" + i, portlet.getUserId());
				parameters.put("dashboardId_" + i, portlet.getDashboardId());
				parameters.put("portletId_" + i, portlet.getPortletId());
				condition.append("(userId = :userId_" + i + " and dashboardId = :dashboardId_" + i + " and portletId = :portletId_" + i + ")");
			}
			
			hql = hql.replace(":condition", condition.toString());
			executeUpdate(hql, parameters);
		}
	}

	public void deleteDashboardPortlet(List<DashboardPortlet> list) throws DAOException {
		if(AssertUtility.isAllElementNotNull(list)) {
			String hql = "delete DashboardPortletImpl where :condition";
			StringBuffer condition = new StringBuffer();
			HashMap<String, String> parameters = new HashMap<String, String>();
			
			for(int i = 0; i < list.size(); i++) {
				if(i != 0) {
					condition.append(" or ");
				}
				
				DashboardPortlet portlet = (DashboardPortlet)list.get(i);
				parameters.put("userId_" + i, portlet.getUserId());
				parameters.put("dashboardId_" + i, portlet.getDashboardId());
				parameters.put("portletId_" + i, portlet.getPortletId());
				condition.append("(userId = :userId_" + i + " and dashboardId = :dashboardId_" + i + " and portletId = :portletId_" + i + ")");
			}
			
			hql = hql.replace(":condition", condition.toString());
			executeUpdate(hql, parameters);
		}
	}

	public void deleteDashboardPortlet(Set<DashboardPortlet> set) throws DAOException {
		if(AssertUtility.isAllElementNotNull(set)) {
			String hql = "delete DashboardPortletImpl where :condition";
			StringBuffer condition = new StringBuffer();
			HashMap<String, String> parameters = new HashMap<String, String>();
			
			Iterator<?> iterator = set.iterator();
			
			int i = 0;
			while(iterator.hasNext()) {
				if(i != 0) {
					condition.append(" or ");
				}
				
				DashboardPortlet portlet = (DashboardPortlet)iterator.next();
				parameters.put("userId_" + i, portlet.getUserId());
				parameters.put("dashboardId_" + i, portlet.getDashboardId());
				parameters.put("portletId_" + i, portlet.getPortletId());
				condition.append("(userId = :userId_" + i + " and dashboardId = :dashboardId_" + i + " and portletId = :portletId_" + i + ")");
				
				i++;
			}
			
			hql = hql.replace(":condition", condition.toString());
			executeUpdate(hql, parameters);
		}
	}
	
	public void deleteUserEmail(Object[] objs) throws DAOException {
		if(AssertUtility.isAllElementNotNull(objs)) {
			String hql = "delete UserEmailImpl where :condition";
			StringBuffer condition = new StringBuffer();
			HashMap<String, String> parameters = new HashMap<String, String>();
			
			for(int i = 0; i < objs.length; i++) {
				if(i != 0) {
					condition.append(" or ");
				}
				
				UserEmail email = (UserEmail)objs[i];
				parameters.put("userId_" + i, email.getUserId());
				parameters.put("emailAccount_" + i, email.getEmailAccount());
				parameters.put("emailDomain_" + i, email.getEmailDomain());
				condition.append("(userId = :userId_" + i + " and emailAccount = :emailAccount_" + i + " and emailDomain = :emailDomain_" + i + ")");
			}
			
			hql = hql.replace(":condition", condition.toString());
			executeUpdate(hql, parameters);
		}
	}

	public void deleteUserEmail(List<UserEmail> list) throws DAOException {
		if(AssertUtility.isAllElementNotNull(list)) {
			String hql = "delete UserEmailImpl where :condition";
			StringBuffer condition = new StringBuffer();
			HashMap<String, String> parameters = new HashMap<String, String>();
			
			for(int i = 0; i < list.size(); i++) {
				if(i != 0) {
					condition.append(" or ");
				}
				
				UserEmail email = (UserEmail)list.get(i);
				parameters.put("userId_" + i, email.getUserId());
				parameters.put("emailAccount_" + i, email.getEmailAccount());
				parameters.put("emailDomain_" + i, email.getEmailDomain());
				condition.append("(userId = :userId_" + i + " and emailAccount = :emailAccount_" + i + " and emailDomain = :emailDomain_" + i + ")");
			}
			
			hql = hql.replace(":condition", condition.toString());
			executeUpdate(hql, parameters);
		}
	}

	public void deleteUserEmail(Set<UserEmail> set) throws DAOException {
		if(AssertUtility.isAllElementNotNull(set)) {
			String hql = "delete UserEmailImpl where :condition";
			StringBuffer condition = new StringBuffer();
			HashMap<String, String> parameters = new HashMap<String, String>();
			
			Iterator<?> iterator = set.iterator();
			
			int i = 0;
			while(iterator.hasNext()) {
				if(i != 0) {
					condition.append(" or ");
				}
				
				UserEmail email = (UserEmail)iterator.next();
				parameters.put("userId_" + i, email.getUserId());
				parameters.put("emailAccount_" + i, email.getEmailAccount());
				parameters.put("emailDomain_" + i, email.getEmailDomain());
				condition.append("(userId = :userId_" + i + " and emailAccount = :emailAccount_" + i + " and emailDomain = :emailDomain_" + i + ")");
				
				i++;
			}
			
			hql = hql.replace(":condition", condition.toString());
			executeUpdate(hql, parameters);
		}
	}
}
