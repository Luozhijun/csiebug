package csiebug.service.impl;

import java.util.List;

import csiebug.dao.DAOException;
import csiebug.dao.ResourceDAO;
import csiebug.dao.RoleDAO;
import csiebug.domain.DomainObjectFactory;
import csiebug.domain.Portlet;
import csiebug.domain.Resource;
import csiebug.domain.Role;
import csiebug.service.ResourceService;
import csiebug.service.ServiceException;
import csiebug.service.LazyLoadingArrayList;

public class ResourceServiceImpl extends BasicServiceImpl implements ResourceService {
	private static final long serialVersionUID = 1L;
	
	private DomainObjectFactory domainObjectFactory;
	private ResourceDAO resourceDAO;
	private RoleDAO roleDAO;
	
	public void setDomainObjectFactory(DomainObjectFactory domainObjectFactory) {
		this.domainObjectFactory = domainObjectFactory;
	}
	public void setResourceDAO(ResourceDAO dao) {
		resourceDAO = dao;
	}
	public void setRoleDAO(RoleDAO dao) {
		roleDAO = dao;
	}
	
	public List<Resource> searchResources(Resource voObj) throws ServiceException {
		try {
			return resourceDAO.search(voObj);
		} catch (DAOException e) {
			throw new ServiceException("ResourceDAO search error!", e);
		}
	}
	
	public List<Resource> searchResourcesPagination(Resource voObj, int maxResults) throws ServiceException {
		LazyLoadingArrayList<Resource> list = new LazyLoadingArrayList<Resource>() {
			private static final long serialVersionUID = 1L;
		};
		list.setDao(resourceDAO);
		list.setMethodName("search");
		list.setParameters(new Object[]{voObj});
		list.setRowsPerPage(maxResults);
		
		return list;
	}

//	@SuppressWarnings("unchecked")
	public void deletePortlet(Portlet voObj) throws ServiceException {
		notNull(voObj, "Portlet is null!");
		
		List<Resource> listResource = searchResources((Resource)voObj);
		
		if(listResource.size() == 1) {
			try {
				resourceDAO.delete(listResource.get(0));
			} catch (DAOException e) {
				throw new ServiceException("ResourceDAO delete error!", e);
			}			
		} else if(listResource.size() > 1) {
			throw new ServiceException("Parameters maybe not enough!");
		}
	}
	
	public void addPortlet(Portlet portlet, boolean openPermission) throws ServiceException {
		notNull(portlet, "Portlet is null!");
		
		if(!openPermission) {
			Role voObj = domainObjectFactory.createRole();
			voObj.setId("admin");
			
			List<Role> roles;
			try {
				roles = roleDAO.search(voObj);
			} catch (DAOException e) {
				throw new ServiceException("RoleDAO search error!", e);
			}
			
			if(roles.size() == 1) {
				Role admin = roles.get(0);
				
				addPortletToRole(portlet, admin);
			} else if(roles.size() == 0) {
				throw new ServiceException("Admin role not found!");
			}
		} else {
			try {
				resourceDAO.insert(portlet);
			} catch (DAOException e) {
				throw new ServiceException("ResourceDAO insert error!", e);
			}
		}
	}
	
	public void addPortletToRole(Portlet portlet, Role role) throws ServiceException {
		notNull(portlet, "Portlet is null!");
		notNull(role, "Role is null!");
		
		portlet.addAuthorityResource(role);
		role.addAuthorityResource(portlet);
		
		try {
			roleDAO.insertOrUpdate(role);
		} catch (DAOException e) {
			throw new ServiceException("RoleDAO insert/update error!", e);
		}
	}
}
