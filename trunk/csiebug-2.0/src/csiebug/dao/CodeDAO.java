package csiebug.dao;

import java.util.List;

import csiebug.domain.Code;

/**
 * 
 * @author George_Tsai
 * @version 2009/7/18
 *
 */
public interface CodeDAO extends BasicDAO {
	List<Code> search(Object obj) throws DAOException;
	List<Code> searchPagination(Object obj, int firstResult, int maxResults) throws DAOException;
}
