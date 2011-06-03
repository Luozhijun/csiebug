package csiebug.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * DAO基本功能
 * @author George_Tsai
 * @version 2009/9/8
 *
 */
public interface BasicDAO extends Serializable {
	/**
	 * insert 一個 domain object
	 * @param obj
	 * @throws DAOException
	 */
	void insert(Object obj) throws DAOException;
	
	/**
	 * insert 多個 domain object
	 * @param objs
	 * @throws DAOException
	 */
	void insert(Object[] objs) throws DAOException;
	
	/**
	 * insert 多個 domain object
	 * @param list
	 * @throws DAOException
	 */
	void insert(List<?> list) throws DAOException;
	
	/**
	 * insert 多個 domain object
	 * @param set
	 * @throws DAOException
	 */
	void insert(Set<?> set) throws DAOException;
	
	/**
	 * insert or update 一個 domain object
	 * @param obj
	 * @throws DAOException
	 */
	void insertOrUpdate(Object obj) throws DAOException;
	
	/**
	 * insert or update 多個 domain object
	 * @param obj
	 * @throws DAOException
	 */
	void insertOrUpdate(Object[] obj) throws DAOException;
	
	/**
	 * insert or update 多個 domain object
	 * @param list
	 * @throws DAOException
	 */
	void insertOrUpdate(List<?> list) throws DAOException;
	
	/**
	 * insert or update 多個 domain object
	 * @param set
	 * @throws DAOException
	 */
	void insertOrUpdate(Set<?> set) throws DAOException;
	
	/**
	 * update 一個 domain object
	 * @param obj
	 * @throws DAOException
	 */
	void update(Object obj) throws DAOException;
	
	/**
	 * update 多個 domain object
	 * @param obj
	 * @throws DAOException
	 */
	void update(Object[] obj) throws DAOException;
	
	/**
	 * update 多個 domain object
	 * @param obj
	 * @throws DAOException
	 */
	void update(List<?> list) throws DAOException;
	
	/**
	 * update 多個 domain object
	 * @param obj
	 * @throws DAOException
	 */
	void update(Set<?> set) throws DAOException;
	
	/**
	 * delete 一個 domain object
	 * @param obj
	 * @throws DAOException
	 */
	void delete(Object obj) throws DAOException;
	
	/**
	 * delete 多個 domain object
	 * @param objs
	 * @throws DAOException
	 */
	void delete(Object[] objs) throws DAOException;
	
	/**
	 * delete 多個 domain object
	 * @param objs
	 * @throws DAOException
	 */
	void delete(List<?> list) throws DAOException;
	
	/**
	 * delete 多個 domain object
	 * @param objs
	 * @throws DAOException
	 */
	void delete(Set<?> set) throws DAOException;
	
	/**
	 * delete 符合此 value object 的所有物件
	 * 須實作search與delete(List<?> list)
	 * @param search
	 * @throws DAOException
	 */
	void deleteMatchObjects(Object search) throws DAOException;
	
	/**
	 * 搜尋符合此 value object 的物件
	 * @param obj
	 * @return
	 * @throws DAOException
	 */
	List<?> search(Object obj) throws DAOException;
	
	/**
	 * 搜尋符合此 value object 的物件(pagination)
	 * @param obj
	 * @return
	 * @throws DAOException
	 */
	List<?> searchPagination(Object obj, int firstResult, int maxResults) throws DAOException;
	
	/**
	 * 搜尋符合此 value object 的筆數
	 * @param obj
	 * @return
	 * @throws DAOException
	 */
	int searchRowCount(Object obj) throws DAOException;
}
