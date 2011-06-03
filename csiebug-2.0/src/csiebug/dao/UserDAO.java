package csiebug.dao;

import java.util.List;
import java.util.Set;

import csiebug.domain.Dashboard;
import csiebug.domain.DashboardPortlet;
import csiebug.domain.User;
import csiebug.domain.UserEmail;

/**
 * 
 * @author George_Tsai
 * @version 2009/6/22
 *
 */
public interface UserDAO extends BasicDAO {
	List<User> search(Object obj) throws DAOException;
	List<User> searchPagination(Object obj, int firstResult, int maxResults) throws DAOException;
	void deleteDashboard(Object[] objs) throws DAOException;
	void deleteDashboard(List<Dashboard> list) throws DAOException;
	void deleteDashboard(Set<Dashboard> set) throws DAOException;
	void deleteDashboardPortlet(Object[] objs) throws DAOException;
	void deleteDashboardPortlet(List<DashboardPortlet> list) throws DAOException;
	void deleteDashboardPortlet(Set<DashboardPortlet> set) throws DAOException;
	void deleteUserEmail(Object[] objs) throws DAOException;
	void deleteUserEmail(List<UserEmail> list) throws DAOException;
	void deleteUserEmail(Set<UserEmail> set) throws DAOException;
}
