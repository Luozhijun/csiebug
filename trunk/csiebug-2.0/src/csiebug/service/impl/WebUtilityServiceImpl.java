package csiebug.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import csiebug.dao.APDAO;
import csiebug.dao.DAOException;
import csiebug.dao.FunctionDAO;
import csiebug.dao.ResourceDAO;
import csiebug.dao.UserDAO;
import csiebug.domain.AP;
import csiebug.domain.Dashboard;
import csiebug.domain.DashboardPortlet;
import csiebug.domain.DomainObjectFactory;
import csiebug.domain.Function;
import csiebug.domain.Resource;
import csiebug.domain.ResourceType;
import csiebug.domain.User;
import csiebug.service.ServiceException;
import csiebug.service.WebUtilityService;
import csiebug.util.ListUtility;

public class WebUtilityServiceImpl extends BasicServiceImpl implements WebUtilityService {
	private static final long serialVersionUID = 1L;
	
	private UserDAO userDAO;
	private FunctionDAO functionDAO;
	private ResourceDAO resourceDAO;
	private APDAO apDAO;
	
	private DomainObjectFactory domainObjectFactory;
	
	public void setDomainObjectFactory(DomainObjectFactory domainObjectFactory) {
		this.domainObjectFactory = domainObjectFactory;
	}
	
	public void setUserDAO(UserDAO dao) {
		userDAO = dao;
	}
	
	public void setFunctionDAO(FunctionDAO dao) {
		functionDAO = dao;
	}
	
	public void setResourceDAO(ResourceDAO resourceDAO) {
		this.resourceDAO = resourceDAO;
	}
	
	public void setApDAO(APDAO apDAO) {
		this.apDAO = apDAO;
	}
	
	public String getUserLocale(User obj) throws ServiceException {
		notNull(obj);
		
		String strLocale = "";
		
		List<User> list;
		try {
			list = userDAO.search(obj);
		} catch (DAOException e) {
			throw new ServiceException("UserDAO search error!", e);
		}
		
		if(list.size() == 1) {
			strLocale = list.get(0).getLocale();
		} else if(list.size() == 0) {
			throw new ServiceException("User not found!");
		} else {
			throw new ServiceException("Parameters are not enough!");
		}
			
		return strLocale;
	}
	
	private boolean checkPermission(User objUser, Resource objResource) throws ServiceException {
		notNull(objUser);
		notNull(objResource);
		
		boolean flag = false;
		
		List<User> listUser;
		try {
			listUser = userDAO.search(objUser);
		} catch (DAOException e) {
			throw new ServiceException("UserDAO search error!", e);
		}
		
		List<? extends Resource> listResource;
		try {
			String className = getResourceClassName(objResource);
			
			if(className != null && className.equals("csiebug.domain.AP")) {
				listResource = apDAO.search(objResource);
			} else if(className != null && className.equals("csiebug.domain.Function")) {
				listResource = functionDAO.search(objResource);
			} else {
				listResource = resourceDAO.search(objResource);
			}
		} catch (DAOException e) {
			throw new ServiceException("ResourceDAO search error!", e);
		} catch (ClassNotFoundException e) {
			throw new ServiceException("Unknown Resource Class!");
		}
		
		if(listUser.size() == 1 && listResource.size() == 1) {
			User user = listUser.get(0);
			Resource resource = listResource.get(0);
			
			//不被管控的資源,登入後直接擁有權限
			if(resource.getAuthorityResources().size() == 0) {
				flag = true;
			} else {//有權限管控的資源,使用者必須擁有權限才可使用
				List<Resource> list = user.getResources();
				
				for(int i = 0; i < list.size(); i++) {
					Resource compareResource = list.get(i);
					
					if(compareResource.getResourceType() == resource.getResourceType() && compareResource.getId().equals(resource.getId())) {
						flag = true;
						break;
					}
				}
			}
		} else if(listUser.size() == 0) {
			throw new ServiceException("User not found!");
		} else if(listResource.size() == 0) {
			flag = true;
//			throw new ServiceException("Resource not found!");
		} else {
			throw new ServiceException("Parameters are not enough!");
		}
		
		return flag;
	}
	
	private String getResourceClassName(Object obj) throws ClassNotFoundException {
		String className = null;
		
		String[] collectionClasses = new String[]{"csiebug.domain.AP", "csiebug.domain.Function"};
		
		for(int i = 0; i < collectionClasses.length; i++) {
			try {
				Class<?> c = Class.forName(collectionClasses[i]);
				c.cast(obj);
				className = collectionClasses[i];
				break;
			} catch(ClassCastException ccex) {
				continue;
			}
		}
		
		return className;
	}
	
	public boolean checkAPPermission(User objUser, AP objAP) throws ServiceException {
		notNull(objUser);
		notNull(objAP);
		
		return checkPermission(objUser, objAP);
	}
	
	public boolean checkFunctionPermission(User objUser, Function objFunction) throws ServiceException {
		notNull(objUser);
		notNull(objFunction);
		
		return checkPermission(objUser, objFunction);
	}
	
	public String getFunctionName(Function obj) throws ServiceException {
		notNull(obj);
		
		String strFunctionName = "";
		
		List<Function> list;
		try {
			list = functionDAO.search(obj);
		} catch (DAOException e) {
			throw new ServiceException("FunctionDAO search error!", e);
		}
		
		if(list.size() == 1) {
			strFunctionName = list.get(0).getFunctionName();
		} else if(list.size() == 0) {
			throw new ServiceException("Function not found!");
		} else {
			throw new ServiceException("Parameters are not enough!");
		}
		
		return strFunctionName;
	}
	
	public String getUserName(User obj) throws ServiceException {
		notNull(obj);
		
		String strUserName = "";
		
		List<User> list;
		try {
			list = userDAO.search(obj);
		} catch (DAOException e) {
			throw new ServiceException("UserDAO search error!", e);
		}
		
		if(list.size() == 1) {
			strUserName = list.get(0).getNickname();
		} else if(list.size() == 0) {
			throw new ServiceException("User not found!");
		} else {
			throw new ServiceException("Parameters are not enough!");
		}
		
		return strUserName;
	}

	public List<String> getAuthorizedPortlets(User objUser, List<String> portletNames) throws ServiceException {
		notNull(objUser, "User is null!");
		//Portlet container有可能餵Null，所以不對List做檢查
//		notNull(portletNames);
		
		List<String> result = new ArrayList<String>();
		
		for(int i = 0; i < portletNames.size(); i++) {
			Resource portlet = domainObjectFactory.createResource();
			portlet.setId(portletNames.get(i));
			portlet.setResourceType(ResourceType.PORTLET);
			
			if(checkPermission(objUser, portlet)) {
				result.add(portletNames.get(i));
			}
		}
		
		return result;
	}
	
	public List<String> getVisiblePortlets(User voUser, String dashboardId, List<String> portletNames) throws ServiceException {
		notNull(voUser, "User is null!");
		//Portlet container有可能餵Null，所以不對List做檢查
//		notNull(portletNames);
		notNullAndNotSpace(dashboardId, "dashboardId is null or empty!");
		
		List<String> result = new ArrayList<String>();
		
		List<String> authorizedPortlets = getAuthorizedPortlets(voUser, portletNames);
		
		List<User> users;
		try {
			users = userDAO.search(voUser);
		} catch (DAOException e) {
			throw new ServiceException("UserDAO search error!", e);
		}
		
		if(users.size() == 1) {
			User user = users.get(0);
			Dashboard dashboard = user.getDashboard(dashboardId);
			
			if(dashboard != null) {
				for(int i = 0; i < authorizedPortlets.size(); i++) {
					String portletId = authorizedPortlets.get(i);
					
					Iterator<DashboardPortlet> iterator = dashboard.getPortlets().iterator();
					while(iterator.hasNext()) {
						DashboardPortlet portlet = iterator.next();
						
						if(portletId.equalsIgnoreCase(portlet.getPortletId()) && portlet.getVisible()) {
							result.add(portletId);
							break;
						}
					}
				}
			}
		} else if(users.size() == 0) {
			throw new ServiceException("User not found!");
		} else {
			throw new ServiceException("Parameters are not enough!");
		}
		
		return result;
	}
	
	public List<String> getSortedPortlets(User voUser, String dashboardId, List<String> portletNames) throws ServiceException {
		notNull(voUser, "User is null!");
		//Portlet container有可能餵Null，所以不對List做檢查
//		notNull(portletNames);
		notNullAndNotSpace(dashboardId, "dashboardId is null or empty!");
		
		List<String> result = new ArrayList<String>();
		
		List<Map<String, String>> tmpList = new ArrayList<Map<String, String>>(); 
		
		List<User> users;
		try {
			users = userDAO.search(voUser);
		} catch (DAOException e) {
			throw new ServiceException("UserDAO search error!", e);
		}
		
		if(users.size() == 1) {
			User user = users.get(0);
			Dashboard dashboard = user.getDashboard(dashboardId);
			
			if(dashboard != null) {
				for(int i = 0; i < portletNames.size(); i++) {
					String portletId = portletNames.get(i);
					
					Iterator<DashboardPortlet> iterator = dashboard.getPortlets().iterator();
					while(iterator.hasNext()) {
						DashboardPortlet portlet = iterator.next();
						
						if(portletId.equalsIgnoreCase(portlet.getPortletId()) && portlet.getVisible()) {
							Map<String, String> map = new LinkedHashMap<String, String>();
							map.put("PortletId", portletId);
							map.put("SortOrder", "" + portlet.getSortOrder());
							map.put("ColumnName", portlet.getColumnName());
							tmpList.add(map);
							
							break;
						}
					}
				}
			}
		} else if(users.size() == 0) {
			throw new ServiceException("User not found!");
		} else {
			throw new ServiceException("Parameters are not enough!");
		}
		
		String[] filedNames = {"ColumnName", "SortOrder"};
		tmpList = ListUtility.sortByMultipleName(tmpList, filedNames);
		
		for(int i = 0; i < tmpList.size(); i++) {
			result.add(tmpList.get(i).get("PortletId"));
		}
		
		return result;
	}
}
