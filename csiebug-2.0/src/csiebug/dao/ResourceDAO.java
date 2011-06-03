package csiebug.dao;

import java.util.List;

import csiebug.domain.Resource;

/**
 * 
 * @author George_Tsai
 * @version 2009/6/26
 *
 */
public interface ResourceDAO extends BasicDAO {
	List<Resource> search(Object obj) throws DAOException;
	List<Resource> searchPagination(Object obj, int firstResult, int maxResults) throws DAOException;
	
	/**
	 * 搜尋資源,允許有*這個萬用字元
	 * 為系統效率考量，spring securtiy使用search來做搜尋資源的工作
	 * 所以spring security不支援使用*這個萬用字元做設定
	 * @param obj
	 * @return
	 * @throws DAOException
	 */
	List<Resource> searchWithPatternMatch(Object obj) throws DAOException;
}
