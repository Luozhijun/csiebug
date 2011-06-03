package csiebug.service.impl;

import java.util.List;

import csiebug.dao.DAOException;
import csiebug.dao.RoleDAO;
import csiebug.domain.Role;
import csiebug.service.ServiceException;
import csiebug.service.RoleService;
import csiebug.service.LazyLoadingArrayList;

public class RoleServiceImpl extends BasicServiceImpl implements RoleService {
	private static final long serialVersionUID = 1L;
	
	private RoleDAO roleDAO;
	
	public void setRoleDAO(RoleDAO dao) {
		roleDAO = dao;
	}
	
	public List<Role> searchRoles(Role voObj) throws ServiceException {
		try {
			return roleDAO.search(voObj);
		} catch (DAOException e) {
			throw new ServiceException("RoleDAO search error!", e);
		}
	}
	
	public List<Role> searchRolesPagination(Role voObj, int maxResults) throws ServiceException {
		LazyLoadingArrayList<Role> list = new LazyLoadingArrayList<Role>() {
			private static final long serialVersionUID = 1L;
		};
		list.setDao(roleDAO);
		list.setMethodName("search");
		list.setParameters(new Object[]{voObj});
		list.setRowsPerPage(maxResults);
		
		return list;
	}
	
	public void saveRole(Role obj) throws ServiceException {
		notNull(obj, "Can not save null!");
		
		try {
			roleDAO.insertOrUpdate(obj);
		} catch (DAOException e) {
			throw new ServiceException("RoleDAO insert/update error!", e);
		}
	}
	
	public void deleteRole(Role obj) throws ServiceException {
		notNull(obj, "Can not delete null!");
		
		try {
			roleDAO.delete(obj);
		} catch (DAOException e) {
			throw new ServiceException("RoleDAO delete error!", e);
		}
	}
}
