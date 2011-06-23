package csiebug.service;

import java.util.List;

import csiebug.dao.ResourceDAO;
import csiebug.dao.RoleDAO;
import csiebug.domain.Portlet;
import csiebug.domain.Resource;
import csiebug.domain.Role;

/**
 * Resource Service
 * @author George_Tsai
 *
 */
public interface ResourceService extends BasicService {
	void setResourceDAO(ResourceDAO dao);
	void setRoleDAO(RoleDAO dao);
	
	/**
	 * 搜尋符合的資源
	 * @param voObj
	 * @return
	 * @throws ServiceException
	 */
	List<Resource> searchResources(Resource voObj) throws ServiceException;
	
	/**
	 * 搜尋符合的資源(pagination)
	 * @param voObj
	 * @return
	 * @throws ServiceException
	 */
	List<Resource> searchResourcesPagination(Resource voObj, int maxResults) throws ServiceException;
	
	/**
	 * 刪除某個portlet(包含使用者個人資訊要一併砍除,如已經設到dashboard的資訊)
	 * @param portletId
	 * @throws ServiceException
	 */
	void deletePortlet(Portlet portlet) throws ServiceException;
	
	/**
	 * 新增portlet(deploy war檔)
	 * @param portletId
	 * @throws ServiceException
	 */
	void addPortlet(Portlet portlet, boolean openPermission) throws ServiceException;
	
	/**
	 * 將portlet的權限設給某個角色(有這個角色的使用者dashboard也可以增加這個portlet)
	 * @param portlet
	 * @param role
	 * @throws ServiceException
	 */
	void addPortletToRole(Portlet portlet, Role role) throws ServiceException;
	
	/**
	 * 儲存資源
	 * @param obj
	 * @throws ServiceException
	 */
	void saveResource(Resource obj) throws ServiceException;
}
