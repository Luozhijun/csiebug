package csiebug.dao;

import java.util.List;

import csiebug.domain.AP;


/**
 * 
 * @author George_Tsai
 * @version 2009/7/16
 *
 */
public interface APDAO extends BasicDAO {
	List<AP> search(Object obj) throws DAOException;
	List<AP> searchPagination(Object obj, int firstResult, int maxResults) throws DAOException;
}
