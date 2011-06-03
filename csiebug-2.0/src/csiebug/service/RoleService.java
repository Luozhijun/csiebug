package csiebug.service;

import java.util.List;

import csiebug.dao.RoleDAO;
import csiebug.domain.Role;

public interface RoleService extends BasicService {
	void setRoleDAO(RoleDAO dao);
	
	/**
	 * 搜尋符合的角色
	 * @param voObj
	 * @return
	 * @throws ServiceException
	 */
	List<Role> searchRoles(Role voObj) throws ServiceException;
	
	/**
	 * 搜尋符合的角色(pagination)
	 * @param voObj
	 * @return
	 * @throws ServiceException
	 */
	List<Role> searchRolesPagination(Role voObj, int maxResults) throws ServiceException;
	
	/**
	 * 儲存角色
	 * @param user
	 * @throws ServiceException
	 */
	void saveRole(Role user) throws ServiceException;
	
	/**
	 * 刪除角色
	 * @param obj
	 * @throws ServiceException
	 */
	void deleteRole(Role obj) throws ServiceException;
}
