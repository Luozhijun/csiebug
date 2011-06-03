package csiebug.dao.hibernateImpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import csiebug.domain.Function;
import csiebug.domain.hibernateImpl.FunctionImpl;
import csiebug.dao.DAOException;
import csiebug.dao.FunctionDAO;
import csiebug.util.AssertUtility;

/**
 * 
 * @author George_Tsai
 * @version 2009/6/26
 *
 */
public class FunctionDAOImpl extends BasicDAOImpl implements FunctionDAO {
	private static final long serialVersionUID = 1L;

	private void addRestriction(Function function) throws DAOException {
		if(AssertUtility.isNotNullAndNotSpace(function.getId())) {
			add(Restrictions.eq("id", function.getId()));
		}
		
		if(AssertUtility.isNotNullAndNotSpace(function.getApId())) {
			add(Restrictions.eq("apId", function.getApId()));
		}
		
		if(AssertUtility.isNotNullAndNotSpace(function.getFunctionId())) {
			add(Restrictions.eq("functionId", function.getFunctionId()));
		}
		
		if(AssertUtility.isNotNullAndNotSpace(function.getParentId())) {
			add(Restrictions.eq("parentId", function.getParentId()));
		}
		
		if(AssertUtility.isNotNullAndNotSpace(function.getFunctionName())) {
			add(Restrictions.like("functionName", "%" + function.getFunctionName() + "%"));
		}
		
		if(AssertUtility.isNotNullAndNotSpace(function.getFunctionLogo())) {
			add(Restrictions.eq("functionLogo", function.getFunctionLogo()));
		}
		
		if(AssertUtility.isNotNullAndNotSpace(function.getFunctionURL())) {
			add(Restrictions.eq("functionURL", function.getFunctionURL()));
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Function> search(Object obj) throws DAOException {
		setTable(FunctionImpl.class);
		
		if(obj != null) {
			Function function = (Function)obj;
			
			addRestriction(function);
		}
		
		return (List<Function>)query();
	}
	
	@SuppressWarnings("unchecked")
	public List<Function> searchPagination(Object obj, int firstResult, int maxResults) throws DAOException {
		setTable(FunctionImpl.class);
		
		if(obj != null) {
			Function function = (Function)obj;
			
			addRestriction(function);
		}
		
		setFirstResult(firstResult);
		setMaxResults(maxResults);
		
		return (List<Function>)query();
	}
	
	public int searchRowCount(Object obj) throws DAOException {
		setTable(FunctionImpl.class);
		setProjection(Projections.rowCount());
		
		if(obj != null) {
			Function function = (Function)obj;
			
			addRestriction(function);
		}
		
		return ((Integer)query().get(0)).intValue();
	}
	
	public void delete(Object[] objs) throws DAOException {
		if(AssertUtility.isAllElementNotNull(objs)) {
			String hql = "delete ResourceImpl where id in (:resourceIds)";
			StringBuffer resourceIds = new StringBuffer();
			HashMap<String, String> parameters = new HashMap<String, String>();
			
			for(int i = 0; i < objs.length; i++) {
				if(i != 0) {
					resourceIds.append(", ");
				}
				
				Function function = (Function)objs[i];
				parameters.put("resourceId_" + i, function.getId());
				resourceIds.append(":resourceId_" + i);
			}
			
			hql = hql.replace(":resourceIds", resourceIds.toString());
			executeUpdate(hql, parameters);
		}
	}

	public void delete(List<?> list) throws DAOException {
		if(AssertUtility.isAllElementNotNull(list)) {
			String hql = "delete ResourceImpl where id in (:resourceIds)";
			StringBuffer resourceIds = new StringBuffer();
			HashMap<String, String> parameters = new HashMap<String, String>();
			
			for(int i = 0; i < list.size(); i++) {
				if(i != 0) {
					resourceIds.append(", ");
				}
				
				Function function = (Function)list.get(i);
				parameters.put("resourceId_" + i, function.getId());
				resourceIds.append(":resourceId_" + i);
			}
			
			hql = hql.replace(":resourceIds", resourceIds.toString());
			executeUpdate(hql, parameters);
		}
	}

	public void delete(Set<?> set) throws DAOException {
		if(AssertUtility.isAllElementNotNull(set)) {
			String hql = "delete ResourceImpl where id in (:resourceIds)";
			StringBuffer resourceIds = new StringBuffer();
			HashMap<String, String> parameters = new HashMap<String, String>();
			
			Iterator<?> iterator = set.iterator();
			
			int i = 0;
			while(iterator.hasNext()) {
				if(i != 0) {
					resourceIds.append(", ");
				}
				
				Function function = (Function)iterator.next();
				parameters.put("resourceId_" + i, function.getId());
				resourceIds.append(":resourceId_" + i);
				
				i++;
			}
			
			hql = hql.replace(":resourceIds", resourceIds.toString());
			executeUpdate(hql, parameters);
		}
	}
}
