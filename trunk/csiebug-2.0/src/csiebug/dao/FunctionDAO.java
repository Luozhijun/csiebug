package csiebug.dao;

import java.util.List;

import csiebug.domain.Function;

/**
 * 
 * @author George_Tsai
 * @version 2009/6/26
 *
 */
public interface FunctionDAO extends BasicDAO {
	List<Function> search(Object obj) throws DAOException;
	List<Function> searchPagination(Object obj, int firstResult, int maxResults) throws DAOException;
}
