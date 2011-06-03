package csiebug.service;

import java.util.List;

import csiebug.dao.UserDAO;
import csiebug.domain.Cookie;
import csiebug.domain.Dashboard;
import csiebug.domain.DashboardPortlet;
import csiebug.domain.User;
import csiebug.domain.UserEmail;
import csiebug.domain.WebservicesChannel;

/**
 * User Service
 * @author George_Tsai
 *
 */
public interface UserService extends BasicService {
	void setUserDAO(UserDAO dao);
	
	/**
	 * 搜尋符合的使用者
	 * @param voObj
	 * @return
	 * @throws ServiceException
	 */
	List<User> searchUsers(User voObj) throws ServiceException;
	
	/**
	 * 搜尋符合的使用者(pagination)
	 * @param voObj
	 * @return
	 * @throws ServiceException
	 */
	List<User> searchUsersPagination(User voObj, int maxResults) throws ServiceException;
	
	/**
	 * 儲存使用者
	 * @param user
	 * @throws ServiceException
	 */
	void saveUser(User user) throws ServiceException;
	
	/**
	 * 刪除使用者
	 * @param user
	 * @throws ServiceException
	 */
	void deleteUser(User user) throws ServiceException;
	
	/**
	 * 刪除使用者的email
	 * @param email
	 * @throws ServiceException
	 */
	void deleteUserEmail(UserEmail email) throws ServiceException;
	
	/**
	 * 新增使用者的email
	 * @param email
	 * @throws ServiceException
	 */
	void addUserEmail(UserEmail email) throws ServiceException;
	
	/**
	 * 修改使用者email
	 * @param email
	 * @throws ServiceException
	 */
	void updateUserEmail(UserEmail email) throws ServiceException;
	
	/**
	 * 刪除persistent cookie的資訊
	 * @param cookie
	 * @throws ServiceException
	 */
	void deleteCookie(Cookie cookie) throws ServiceException;
	
	/**
	 * 新增persistent cookie的資訊
	 * @param cookie
	 * @throws ServiceException
	 */
	void addCookie(Cookie cookie) throws ServiceException;
	
	/**
	 * 修改persistent cookie的資訊
	 * @param cookie
	 * @throws ServiceException
	 */
	void updateCookie(Cookie cookie) throws ServiceException;
	
	/**
	 * 刪除使用者dashboard上的portlet
	 * @param portlet
	 * @throws ServiceException
	 */
	void deleteDashboardPortlet(DashboardPortlet portlet) throws ServiceException;
	
	/**
	 * 新增使用者dashboard上的portlet
	 * @param portlet
	 * @throws ServiceException
	 */
	void addDashboardPortlet(DashboardPortlet portlet) throws ServiceException;
	
	/**
	 * 修改使用者dashboard上的portlet
	 * @param portlet
	 * @throws ServiceException
	 */
	void updateDashboardPortlet(DashboardPortlet portlet) throws ServiceException;
	
	/**
	 * 刪除使用者的dashboard
	 * @param dashboard
	 * @throws ServiceException
	 */
	void deleteDashboard(Dashboard dashboard) throws ServiceException;
	
	/**
	 * 新增使用者的dashboard
	 * @param dashboard
	 * @throws ServiceException
	 */
	void addDashboard(Dashboard dashboard) throws ServiceException;
	
	/**
	 * 刪除WebservicesChannel的資訊
	 * @param channel
	 * @throws ServiceException
	 */
	void deleteWebservicesChannel(WebservicesChannel channel) throws ServiceException;
	
	/**
	 * 新增WebservicesChannel的資訊
	 * @param channel
	 * @throws ServiceException
	 */
	void addWebservicesChannel(WebservicesChannel channel) throws ServiceException;
	
	/**
	 * 修改WebservicesChannel的資訊
	 * @param channel
	 * @throws ServiceException
	 */
	void updateWebservicesChannel(WebservicesChannel channel) throws ServiceException;
}
