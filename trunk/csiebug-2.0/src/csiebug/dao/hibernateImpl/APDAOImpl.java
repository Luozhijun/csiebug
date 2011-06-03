package csiebug.dao.hibernateImpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import csiebug.domain.AP;
import csiebug.domain.hibernateImpl.APImpl;
import csiebug.dao.APDAO;
import csiebug.dao.DAOException;
import csiebug.util.AssertUtility;

/**
 * 
 * @author George_Tsai
 * @version 2009/6/26
 *
 */
public class APDAOImpl extends BasicDAOImpl implements APDAO {
	private static final long serialVersionUID = 1L;
	
	private void addRestriction(AP ap) throws DAOException {
		if(AssertUtility.isNotNullAndNotSpace(ap.getId())) {
			add(Restrictions.eq("id", ap.getId()));
		}
		
		if(AssertUtility.isNotNullAndNotSpace(ap.getApId())) {
			add(Restrictions.eq("apId", ap.getApId()));
		}
		
		if(AssertUtility.isNotNullAndNotSpace(ap.getApName())) {
			add(Restrictions.like("apName", "%" + ap.getApName() + "%"));
		}
		
		if(AssertUtility.isNotNullAndNotSpace(ap.getApLogo())) {
			add(Restrictions.eq("apLogo", ap.getApLogo()));
		}
		
		if(AssertUtility.isNotNullAndNotSpace(ap.getApIndexURL())) {
			add(Restrictions.eq("apIndexURL", ap.getApIndexURL()));
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<AP> search(Object obj) throws DAOException {
		setTable(APImpl.class);
		
		if(obj != null) {
			AP ap = (AP)obj;
			
			addRestriction(ap);
		}
		
		return (List<AP>)query();
	}
	
	@SuppressWarnings("unchecked")
	public List<AP> searchPagination(Object obj, int firstResult, int maxResults) throws DAOException {
		setTable(APImpl.class);
		
		if(obj != null) {
			AP ap = (AP)obj;
			
			addRestriction(ap);
		}
		
		setFirstResult(firstResult);
		setMaxResults(maxResults);
		
		return (List<AP>)query();
	}
	
	public int searchRowCount(Object obj) throws DAOException {
		setTable(APImpl.class);
		setProjection(Projections.rowCount());
		
		if(obj != null) {
			AP ap = (AP)obj;
			
			addRestriction(ap);
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
				
				AP ap = (AP)objs[i];
				parameters.put("resourceId_" + i, ap.getId());
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
				
				AP ap = (AP)list.get(i);
				parameters.put("resourceId_" + i, ap.getId());
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
				
				AP ap = (AP)iterator.next();
				parameters.put("resourceId_" + i, ap.getId());
				resourceIds.append(":resourceId_" + i);
				
				i++;
			}
			
			hql = hql.replace(":resourceIds", resourceIds.toString());
			executeUpdate(hql, parameters);
		}
	}
}
