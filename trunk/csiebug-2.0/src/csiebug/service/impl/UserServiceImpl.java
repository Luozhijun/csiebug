package csiebug.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import csiebug.dao.DAOException;
import csiebug.dao.UserDAO;
import csiebug.domain.Cookie;
import csiebug.domain.Dashboard;
import csiebug.domain.DashboardPortlet;
import csiebug.domain.User;
import csiebug.domain.UserEmail;
import csiebug.domain.WebservicesChannel;
import csiebug.service.ServiceException;
import csiebug.service.UserService;
import csiebug.service.LazyLoadingArrayList;

public class UserServiceImpl extends BasicServiceImpl implements UserService {
	private static final long serialVersionUID = 1L;
	
	private UserDAO userDAO;
	
	public void setUserDAO(UserDAO dao) {
		userDAO = dao;
	}
	
	public List<User> searchUsers(User voObj) throws ServiceException {
		try {
			return userDAO.search(voObj);
		} catch (DAOException e) {
			throw new ServiceException("UserDAO search error!", e);
		}
	}
	
	public List<User> searchUsersPagination(User voObj, int maxResults) throws ServiceException {
		LazyLoadingArrayList<User> list = new LazyLoadingArrayList<User>() {
			private static final long serialVersionUID = 1L;
		};
		list.setDao(userDAO);
		list.setMethodName("search");
		list.setParameters(new Object[]{voObj});
		list.setRowsPerPage(maxResults);
		
		return list;
	}
	
	public void saveUser(User obj) throws ServiceException {
		notNull(obj, "Can not save null!");
		
		try {
			userDAO.insertOrUpdate(obj);
		} catch (DAOException e) {
			throw new ServiceException("UserDAO insert/update error!", e);
		}
	}
	
	public void deleteUser(User user) throws ServiceException {
		notNull(user, "Can not delete null!");
		
		try {
			List<DashboardPortlet> portlets = new ArrayList<DashboardPortlet>();
			Iterator<Dashboard> iterator = user.getDashboards().iterator();
			while(iterator.hasNext()) {
				Dashboard dashboard = iterator.next();
				portlets.addAll(dashboard.getPortlets());
			}
			userDAO.deleteDashboardPortlet(portlets);
		} catch (DAOException e) {
			throw new ServiceException("UserDAO delete portlet error!", e);
		}
		
		try {
			userDAO.deleteDashboard(user.getDashboards());
		} catch (DAOException e) {
			throw new ServiceException("UserDAO delete dashboard error!", e);
		}
		
		try {
			userDAO.deleteUserEmail(user.getUserEmails());
		} catch (DAOException e) {
			throw new ServiceException("UserDAO delete email error!", e);
		}
		
		try {
			if(user.getUserProfile() != null) {
				userDAO.delete(user.getUserProfile());
			}
			userDAO.delete(user);
		} catch (DAOException e) {
			throw new ServiceException("UserDAO delete user error!", e);
		}
	}
	
	public void deleteUserEmail(UserEmail email) throws ServiceException {
		notNull(email, "Can not delete null!");
		
		try {
			userDAO.delete(email);
		} catch (DAOException e) {
			throw new ServiceException("UserDAO delete email error!", e);
		}
	}
	
	public void addUserEmail(UserEmail email) throws ServiceException {
		notNull(email, "Can not add null!");
		
		try {
			userDAO.insert(email);
		} catch (DAOException e) {
			throw new ServiceException("UserDAO insert email error!", e);
		}
	}
	
	public void updateUserEmail(UserEmail email) throws ServiceException {
		notNull(email, "Can not update null!");
		
		try {
			userDAO.update(email);
		} catch (DAOException e) {
			throw new ServiceException("UserDAO update email error!", e);
		}
	}
	
	public void deleteCookie(Cookie cookie) throws ServiceException {
		notNull(cookie, "Can not delete null!");
		
		try {
			userDAO.delete(cookie);
		} catch (DAOException e) {
			throw new ServiceException("UserDAO delete cookie error!", e);
		}
	}
	
	public void addCookie(Cookie cookie) throws ServiceException {
		notNull(cookie, "Can not add null!");
		
		try {
			userDAO.insert(cookie);
		} catch (DAOException e) {
			throw new ServiceException("UserDAO insert cookie error!", e);
		}
	}
	
	public void updateCookie(Cookie cookie) throws ServiceException {
		notNull(cookie, "Can not update null!");
		
		try {
			userDAO.update(cookie);
		} catch (DAOException e) {
			throw new ServiceException("UserDAO update cookie error!", e);
		}
	}
	
	public void deleteDashboardPortlet(DashboardPortlet portlet) throws ServiceException {
		notNull(portlet, "Can not delete null!");
		
		try {
			userDAO.delete(portlet);
		} catch (DAOException e) {
			throw new ServiceException("UserDAO delete portlet error!", e);
		}
	}
	
	public void addDashboardPortlet(DashboardPortlet portlet) throws ServiceException {
		notNull(portlet, "Can not add null!");
		
		try {
			userDAO.insert(portlet);
		} catch (DAOException e) {
			throw new ServiceException("UserDAO insert portlet error!", e);
		}
	}
	
	public void updateDashboardPortlet(DashboardPortlet portlet) throws ServiceException {
		notNull(portlet, "Can not update null!");
		
		try {
			userDAO.update(portlet);
		} catch (DAOException e) {
			throw new ServiceException("UserDAO update portlet error!", e);
		}
	}
	
	public void deleteDashboard(Dashboard dashboard) throws ServiceException {
		notNull(dashboard, "Can not delete null!");
		
		try {
			userDAO.deleteDashboardPortlet(dashboard.getPortlets());
		} catch (DAOException e) {
			throw new ServiceException("UserDAO delete portlet error!", e);
		}
		try {
			userDAO.delete(dashboard);
		} catch (DAOException e) {
			throw new ServiceException("UserDAO delete dashboard error!", e);
		}
	}
	
	public void addDashboard(Dashboard dashboard) throws ServiceException {
		notNull(dashboard, "Can not insert null!");
		
		try {
			userDAO.insert(dashboard);
		} catch (DAOException e) {
			throw new ServiceException("UserDAO insert dashboard error!", e);
		}
	}
	
	public void deleteWebservicesChannel(WebservicesChannel channel) throws ServiceException {
		notNull(channel, "Can not delete null!");
		
		try {
			userDAO.delete(channel);
		} catch (DAOException e) {
			throw new ServiceException("UserDAO delete channel error!", e);
		}
	}
	
	public void addWebservicesChannel(WebservicesChannel channel) throws ServiceException {
		notNull(channel, "Can not add null!");
		
		try {
			userDAO.insert(channel);
		} catch (DAOException e) {
			throw new ServiceException("UserDAO insert channel error!", e);
		}
	}
	
	public void updateWebservicesChannel(WebservicesChannel channel) throws ServiceException {
		notNull(channel, "Can not update null!");
		
		try {
			userDAO.update(channel);
		} catch (DAOException e) {
			throw new ServiceException("UserDAO update channel error!", e);
		}
	}
}
