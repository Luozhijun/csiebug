package csiebug.dao.hibernateImpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import csiebug.domain.Role;
import csiebug.domain.hibernateImpl.RoleImpl;
import csiebug.dao.DAOException;
import csiebug.dao.RoleDAO;
import csiebug.util.AssertUtility;

/**
 * 
 * @author George_Tsai
 * @version 2009/7/7
 *
 */
public class RoleDAOImpl extends BasicDAOImpl implements RoleDAO {
	private static final long serialVersionUID = 1L;

	private void addRestriction(Role role) throws DAOException {
		if(AssertUtility.isNotNullAndNotSpace(role.getId())) {
			add(Restrictions.eq("id", role.getId()));
		}
		
		if(AssertUtility.isNotNullAndNotSpace(role.getRoleName())) {
			add(Restrictions.eq("roleName", role.getRoleName()));
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Role> search(Object obj) throws DAOException {
		setTable(RoleImpl.class);
		
		if(obj != null) {
			Role role = (Role)obj;
			
			addRestriction(role);
		}
		
		return (List<Role>) query();
	}
	
	@SuppressWarnings("unchecked")
	public List<Role> searchPagination(Object obj, int firstResult, int maxResults) throws DAOException {
		setTable(RoleImpl.class);
		
		if(obj != null) {
			Role role = (Role)obj;
			
			addRestriction(role);
		}
		
		setFirstResult(firstResult);
		setMaxResults(maxResults);
		
		return (List<Role>) query();
	}
	
	public int searchRowCount(Object obj) throws DAOException {
		setTable(RoleImpl.class);
		setProjection(Projections.rowCount());
		
		if(obj != null) {
			Role role = (Role)obj;
			
			addRestriction(role);
		}
		
		return ((Integer)query().get(0)).intValue();
	}
	
	public void delete(Object[] objs) throws DAOException {
		if(AssertUtility.isAllElementNotNull(objs)) {
			String hql = "delete RoleImpl where id in (:roleIds)";
			StringBuffer roleIds = new StringBuffer();
			HashMap<String, String> parameters = new HashMap<String, String>();
			
			for(int i = 0; i < objs.length; i++) {
				if(i != 0) {
					roleIds.append(", ");
				}
				
				Role role = (Role)objs[i];
				parameters.put("roleId_" + i, role.getId());
				roleIds.append(":roleId_" + i);
			}
			
			hql = hql.replace(":roleIds", roleIds.toString());
			executeUpdate(hql, parameters);
		}
	}

	public void delete(List<?> list) throws DAOException {
		if(AssertUtility.isAllElementNotNull(list)) {
			String hql = "delete RoleImpl where id in (:roleIds)";
			StringBuffer roleIds = new StringBuffer();
			HashMap<String, String> parameters = new HashMap<String, String>();
			
			for(int i = 0; i < list.size(); i++) {
				if(i != 0) {
					roleIds.append(", ");
				}
				
				Role role = (Role)list.get(i);
				parameters.put("roleId_" + i, role.getId());
				roleIds.append(":roleId_" + i);
			}
			
			hql = hql.replace(":roleIds", roleIds.toString());
			executeUpdate(hql, parameters);
		}
	}

	public void delete(Set<?> set) throws DAOException {
		if(AssertUtility.isAllElementNotNull(set)) {
			String hql = "delete RoleImpl where id in (:roleIds)";
			StringBuffer roleIds = new StringBuffer();
			HashMap<String, String> parameters = new HashMap<String, String>();
			
			Iterator<?> iterator = set.iterator();
			
			int i = 0;
			while(iterator.hasNext()) {
				if(i != 0) {
					roleIds.append(", ");
				}
				
				Role role = (Role)iterator.next();
				parameters.put("roleId_" + i, role.getId());
				roleIds.append(":roleId_" + i);
				
				i++;
			}
			
			hql = hql.replace(":roleIds", roleIds.toString());
			executeUpdate(hql, parameters);
		}
	}
}
