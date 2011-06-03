package csiebug.dao;

import java.util.List;

import csiebug.domain.Dashboard;


/**
 * 
 * @author George_Tsai
 * @version 2009/7/16
 *
 */
public interface DashboardDAO extends BasicDAO {
	List<Dashboard> search(Object obj) throws DAOException;
	List<Dashboard> searchPagination(Object obj, int firstResult, int maxResults) throws DAOException;
}
