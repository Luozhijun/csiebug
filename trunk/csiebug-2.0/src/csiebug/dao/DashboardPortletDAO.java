package csiebug.dao;

import java.util.List;

import csiebug.domain.DashboardPortlet;


/**
 * 
 * @author George_Tsai
 * @version 2009/7/16
 *
 */
public interface DashboardPortletDAO extends BasicDAO {
	List<DashboardPortlet> search(Object obj) throws DAOException;
	List<DashboardPortlet> searchPagination(Object obj, int firstResult, int maxResults) throws DAOException;
}
