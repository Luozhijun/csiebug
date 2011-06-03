package csiebug.dao;

import java.util.List;

import csiebug.domain.Role;

/**
 * 
 * @author George_Tsai
 * @version 2009/7/7
 *
 */
public interface RoleDAO extends BasicDAO {
	List<Role> search(Object obj) throws DAOException;
	List<Role> searchPagination(Object obj, int firstResult, int maxResults) throws DAOException;
}
