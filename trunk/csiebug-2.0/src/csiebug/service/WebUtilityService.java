package csiebug.service;

import java.util.List;

import csiebug.dao.APDAO;
import csiebug.dao.FunctionDAO;
import csiebug.dao.ResourceDAO;
import csiebug.dao.UserDAO;
import csiebug.domain.AP;
import csiebug.domain.Function;
import csiebug.domain.User;

/**
 * WebUtility會使用的service
 * @author George_Tsai
 * @version 2009/2/6
 *
 */
public interface WebUtilityService extends BasicService {
	void setUserDAO(UserDAO dao);
	void setFunctionDAO(FunctionDAO dao);
	void setResourceDAO(ResourceDAO resourceDAO);
	void setApDAO(APDAO apDAO);
	
	/**
	 * 取得使用者使用語系
	 * @param userId
	 * @return
	 * @throws ServiceException 
	 */
	String getUserLocale(User user) throws ServiceException;
	
	/**
	 * 檢查是否有此系統的登入權
	 * @param strAPId
	 * @return
	 * @throws ServiceException 
	 */
	boolean checkAPPermission(User objUser, AP objAP) throws ServiceException;
	
	/**
	 * 檢查是否有此功能的權限
	 * @return
	 * @throws ServiceException 
	 */
	boolean checkFunctionPermission(User objUser, Function objFunction) throws ServiceException;
	
	/**
	 * 取得function名稱
	 * @return
	 * @throws ServiceException 
	 */
	String getFunctionName(Function obj) throws ServiceException;
	
	/**
	 * 取得使用者暱稱
	 * @param strUserId
	 * @return
	 * @throws ServiceException 
	 */
	String getUserName(User obj) throws ServiceException;
	
	/**
	 * 過濾取得有權限使用的Portlet名稱
	 * @param Student
	 * @param portletNames
	 * @return
	 * @throws ServiceException 
	 */
	List<String> getAuthorizedPortlets(User obj, List<String> portletNames) throws ServiceException;
	
	/**
	 * 過濾取得有權限使用且在此dashboard設定顯示的Portlet名稱
	 * @param Student
	 * @param dashboardId
	 * @param portletNames
	 * @return
	 * @throws ServiceException 
	 */
	List<String> getVisiblePortlets(User obj, String dashboardId, List<String> portletNames) throws ServiceException;
	
	/**
	 * 將此dashboard的portlet名稱排序後傳出
	 * @param voUser
	 * @param dashboardId
	 * @param portletNames
	 * @return
	 * @throws ServiceException 
	 */
	List<String> getSortedPortlets(User voUser, String dashboardId, List<String> portletNames) throws ServiceException;
}
