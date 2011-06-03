package csiebug.dao.hibernateImpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import csiebug.domain.Resource;
import csiebug.domain.hibernateImpl.ResourceImpl;
import csiebug.dao.DAOException;
import csiebug.dao.ResourceDAO;
import csiebug.util.AssertUtility;

/**
 * 
 * @author George_Tsai
 * @version 2009/6/26
 *
 */
public class ResourceDAOImpl extends BasicDAOImpl implements ResourceDAO {
	private static final long serialVersionUID = 1L;
	
	private void addRestriction(Resource resource) throws DAOException {
		if(AssertUtility.isNotNullAndNotSpace(resource.getId())) {
			if(resource.getId().indexOf("*") != -1) {
				add(Restrictions.like("id", resource.getId().replaceAll("\\*", "%")));
			} else {
				add(Restrictions.eq("id", resource.getId()));
			}
		}
		
		if(resource.getResourceType() != null) {
			add(Restrictions.eq("resourceType", resource.getResourceType()));
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Resource> search(Object obj) throws DAOException {
		setTable(ResourceImpl.class);
		
		if(obj != null) {
			Resource resource = (Resource)obj;
			
			addRestriction(resource);
		}
		
		List<Resource> list = (List<Resource>)query();
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Resource> searchPagination(Object obj, int firstResult, int maxResults) throws DAOException {
		setTable(ResourceImpl.class);
		
		if(obj != null) {
			Resource resource = (Resource)obj;
			
			addRestriction(resource);
		}
		
		setFirstResult(firstResult);
		setMaxResults(maxResults);
		
		List<Resource> list = (List<Resource>)query();
		
		return list;
	}
	
	public int searchRowCount(Object obj) throws DAOException {
		setTable(ResourceImpl.class);
		setProjection(Projections.rowCount());
		
		if(obj != null) {
			Resource resource = (Resource)obj;
			
			addRestriction(resource);
		}
		
		return ((Integer)query().get(0)).intValue();
	}
	
	public List<Resource> searchWithPatternMatch(Object obj) throws DAOException {
		List<Resource> list = search(obj);
		
		if(obj != null) {
			list = addPatternMatchResource(list, obj);
		}
		
		return list;
	}
	
	private List<Resource> addPatternMatchResource(List<Resource> list, Object obj) throws DAOException {
		notNull(list);
		notNull(obj);
		
		Resource resource = (Resource)obj;
		
		if(AssertUtility.isNotNullAndNotSpace(resource.getId())) {
			List<Resource> otherMatches = getPatternMatchResource(resource);
			list.addAll(otherMatches);
		}
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	private List<Resource> getPatternMatchResource(Resource resource) throws DAOException {
		notNull(resource);
		
		clearCriteria();
		
		List<Resource> list;
		
		if(resource.getResourceType() != null) {
			add(Restrictions.eq("resourceType", resource.getResourceType()));
		}
		
		add(Restrictions.like("id", "%*%"));
		
		list = (List<Resource>)query();
		
		for(int i = list.size() - 1; i >= 0; i--) {
			Resource compareResource = list.get(i);
			String[] pattern = compareResource.getId().split("\\*");
			
			if(makeRemoveFlag(resource, pattern)) {
				list.remove(i);
			}
		}
		
		return list;
	}
	
	private boolean makeRemoveFlag(Resource resource, String[] pattern) throws DAOException {
		notNull(resource);
		allElementNotNull(pattern);
		
		boolean removeFlag = false;
		
		int preIndex = -1;
		for(int j = 0; j < pattern.length; j++) {
			int index = resource.getId().indexOf(pattern[j]);
			
			if(index == -1) {
				removeFlag = true;
			} else {
				if(preIndex < index) {
					preIndex = index;
				} else {
					removeFlag = true;
				}
			}
			
			if(removeFlag) {
				break;
			}
		}
		
		return removeFlag;
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
				
				Resource resource = (Resource)objs[i];
				parameters.put("resourceId_" + i, resource.getId());
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
				
				Resource resource = (Resource)list.get(i);
				parameters.put("resourceId_" + i, resource.getId());
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
				
				Resource resource = (Resource)iterator.next();
				parameters.put("resourceId_" + i, resource.getId());
				resourceIds.append(":resourceId_" + i);
				
				i++;
			}
			
			hql = hql.replace(":resourceIds", resourceIds.toString());
			executeUpdate(hql, parameters);
		}
	}
}
