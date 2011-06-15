package csiebug.web.webapp;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.portal.portletcontainer.admin.PortletRegistryHelper;
import com.sun.portal.portletcontainer.admin.deployment.WebAppDeployerException;
import com.sun.portal.portletcontainer.admin.registry.PortletRegistryConstants;
import com.sun.portal.portletcontainer.driver.DesktopMessages;
import com.sun.portal.portletcontainer.driver.PropertiesContext;
import com.sun.portal.portletcontainer.driver.admin.AdminConstants;
import com.sun.portal.portletcontainer.driver.admin.DirectoryChangedListener;
import com.sun.portal.portletcontainer.driver.admin.DirectoryWatcherTask;
import com.sun.portal.portletcontainer.driver.admin.PortletAdminData;
import com.sun.portal.portletcontainer.driver.admin.PortletAdminDataFactory;
import com.sun.portal.portletcontainer.driver.admin.PortletWar;
import com.sun.portal.portletcontainer.context.registry.PortletRegistryException;

import csiebug.domain.Dashboard;
import csiebug.domain.DashboardPortlet;
import csiebug.domain.Portlet;
import csiebug.domain.ResourceType;
import csiebug.domain.Role;
import csiebug.domain.User;
import csiebug.service.CodeService;
import csiebug.service.ResourceService;
import csiebug.service.RoleService;
import csiebug.service.ServiceException;
import csiebug.service.UserService;
import csiebug.util.AssertUtility;
import csiebug.util.DateFormatException;
import csiebug.util.ListException;
import csiebug.web.html.HtmlRenderException;

public class PortletAdminAction extends PortletBasicAction {
	private static final long serialVersionUID = 1L;
	
	private UserService userService;
	private ResourceService resourceService;
	private CodeService codeService;
	private RoleService roleService;
	
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}
	
	public ResourceService getResourceService() {
		return resourceService;
	}
	
	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}
	
	public CodeService getCodeService() {
		return codeService;
	}
	
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	public RoleService getRoleService() {
		return roleService;
	}
	
	HttpServletRequest request;
	HttpServletResponse response;
	private static final String PORTLET_DRIVER_AUTODEPLOY_DIR = PortletRegistryHelper.getAutoDeployLocation();
	private static Logger logger = Logger.getLogger(PortletAdminAction.class.getPackage().getName(),
    "PCDLogMessages");
	
	private DirectoryChangedListener directoryChangedListener = new DirectoryChangedListener() {
        public void fileAdded(File file) {
            if ( file.getName().endsWith(WarFileFilter.WAR_EXTENSION)) {
                PortletWar portlet = new PortletWar(file);
                checkAndDeploy(portlet);
            } else if (file.getName().endsWith(WarFileFilter.WAR_DEPLOYED_EXTENSION)) {
                String markerFileName = file.getAbsolutePath();
                String portletWarFileName = markerFileName.replaceFirst(WarFileFilter.WAR_DEPLOYED_EXTENSION+"$", "");
                PortletWar portlet = new PortletWar(portletWarFileName);
                
                if ( !portlet.warFileExists() ) {
                    try {
                        portlet.undeploy();
                    } catch (Exception e) {
                        if(logger.isLoggable(Level.INFO)) {
                            logger.log(Level.INFO, "PSPCD_CSPPD0031", portlet.getWarName());
                        }
                    }
                }
            }
        }
        
        private void checkAndDeploy(PortletWar portlet) {
            if (!portlet.isDeployed())
                portlet.deploy();
            else if (portlet.needsRedeploy()) {
                try {
                    portlet.redeploy();
                } catch (Exception e) {
                    if(logger.isLoggable(Level.INFO)) {
                        logger.log(Level.INFO, "PSPCD_CSPPD0031", portlet.getWarName());
                    }
                }
            }
        }
    };
	
	private void init() {
		request = getRequest();
		response = getResponse();
		userService = getUserService();
		
		// Do not invoke autodeploy is not enabled
        if(PropertiesContext.enableAutodeploy()) {
            DirectoryWatcherTask watcher = new DirectoryWatcherTask(PORTLET_DRIVER_AUTODEPLOY_DIR, new WarFileFilter(), directoryChangedListener);
            
            Timer timer = new Timer();
            long watchInterval = PropertiesContext.getAutodeployDirWatchInterval();
            timer.schedule(watcher, watchInterval, watchInterval);
        }
	}
    
    public String main() throws Exception {
    	//init
		init();
		
		DesktopMessages.init(request);
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = AdminUtils.getClearedSession(request);
        PortletAdminData portletAdminData = null;
        try {
            portletAdminData = PortletAdminDataFactory.getPortletAdminData();
        } catch (PortletRegistryException pre) {
            throw new IOException(pre.getMessage());
        }
        AdminUtils.setAttributes(session, portletAdminData);
        
        if(isParameterPresent(request, AdminConstants.CREATE_PORTLET_WINDOW_SUBMIT)) {
        	createPortletSubmit(session, portletAdminData);
        } else if (isParameterPresent(request, AdminConstants.UNDEPLOY_PORTLET_SUBMIT)) {
            undeploySubmit(session, portletAdminData);
        } else if(isParameterPresent(request, AdminConstants.MODIFY_PORTLET_WINDOW_SUBMIT) && modifyPortletSubmit(session, portletAdminData) == null) {
        	return null;
        } else if(isParameterPresent(request, AdminConstants.PORTLET_WINDOW_LIST)) {
            portletList(session, portletAdminData);
        } else if(getRequestValue("ModifyDashboardSubmit").equalsIgnoreCase("modifyDashboardSubmit") && modifyDashboardSubmit() == null) {
        	return null;
        } else if(getActFlag().equalsIgnoreCase("saveWindowOrder")) {
        	String dashboardId = getRequestValue("dashboardId");
        	if(dashboardId.trim().equals("")) {
        		dashboardId = "dt";
        	}
        	String[] windowsName = null;
        	String width = null;
        	
        	if(!getRequestValue("thick").trim().equals("")) {
        		windowsName = getRequestValue("thick").split(",");
        		width = "thick";
        	} else if(!getRequestValue("thin").trim().equals("")) {
        		windowsName = getRequestValue("thin").split(",");
        		width = "thin";
        	}
        	
        	if(windowsName != null && width != null) {
	        	for(int i = 0; i < windowsName.length; i++) {
	        		saveModifyPortletWindow(windowsName[i], dashboardId, width, i);
	    		}
        	}
        } else if(AssertUtility.isNotNullAndNotSpace(getRequestValue("systemAdminActFlag"))) {
        	SystemAdminAction action = new SystemAdminAction(this);
        	action.main();
        } else if(AssertUtility.isNotNullAndNotSpace(getRequestValue("personalSettingActFlag"))) {
        	PersonalSettingAction action = new PersonalSettingAction(this);
        	action.main();
        } else {
        	loadPortletProperties(session, portletAdminData);
        }
        
        //頁面控制項需要的資料
		makeControl();
        
        //若沒有導到特定處理頁面,則預設都導到formLoad頁
		return FORMLOAD;
	}
	
	//畫面控制項函數區
	public void makeControl() throws UnsupportedEncodingException, NamingException, ServiceException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, DateFormatException, ListException, HtmlRenderException {
		//畫面控制項
		try {
			setRequestAttribute("FunctionName", getFunctionName());
		} catch(Exception e) {
			writeErrorLog(e);
			
			if(AssertUtility.isNotNullAndNotSpace(getFunctionId())) {
				setRequestAttribute("FunctionName", getFunctionId());
			} else {
				setRequestAttribute("FunctionName", "");
			}
		}
		
		User loginUser = getLoginUser();
		
		makeTabs(loginUser, true);
		makePortletOption();
		makePortletWindowOption();
		makePortletProperties(loginUser);
		makeColumnNameOption();
		makeShowHideOption();
		
		Iterator<Role> iterator = loginUser.getAuthorities().iterator();
		boolean adminFlag = false;
		while(iterator.hasNext()) {
			Role role = iterator.next();
			if(role.getId().equals("admin")) {
				adminFlag = true;
				break;
			}
		}
		
		if(adminFlag) {
			SystemAdminAction action = new SystemAdminAction(this);
			action.makeControl();
		}
		
		PersonalSettingAction action = new PersonalSettingAction(this);
		action.makeControl();
	}
	
	private void makePortletProperties(User loginUser) throws NamingException {
		Map<String, String> allDashboard = new LinkedHashMap<String, String>();
		Map<String, String> allDashboardWithoutDt = new LinkedHashMap<String, String>();
		
		//加入使用者的dashboards
		if(loginUser != null) {
			Iterator<Dashboard> iterator = loginUser.getDashboards().iterator();
			while(iterator.hasNext()) {
				Dashboard dashboard = iterator.next();
				String dashboardName = dashboard.getDashboardName();
				
				if(!dashboard.getDashboardId().equals("dt")) {
					if(AssertUtility.isNotNullAndNotSpace(dashboardName)) {
						allDashboard.put(dashboard.getDashboardId(), dashboardName);
						allDashboardWithoutDt.put(dashboard.getDashboardId(), dashboardName);
					} else {
						allDashboard.put(dashboard.getDashboardId(), dashboard.getDashboardId());
						allDashboardWithoutDt.put(dashboard.getDashboardId(), dashboard.getDashboardId());
					}
				} else {
					if(AssertUtility.isNotNullAndNotSpace(dashboardName)) {
						allDashboard.put(dashboard.getDashboardId(), dashboardName);
					} else {
						allDashboard.put(dashboard.getDashboardId(), dashboard.getDashboardId());
					}
				}
				
				if(!getRequestValue("dashboard").trim().equals("") && getRequestValue("dashboard").trim().equalsIgnoreCase(dashboard.getDashboardId())) {
					setRequestAttribute("dashboardSortOrder", dashboard.getSortOrder());
				} else if(getRequestValue("dashboard").trim().equals("")) { 
					removeRequestAttribute("dashboardSortOrder");
				}
				
				if(!getRequestValue("portletWindowList").trim().equals("") && getRequestAttribute("dashboardId") == null) {
					Iterator<DashboardPortlet> iterator2 = dashboard.getPortlets().iterator();
					while(iterator2.hasNext()) {
						DashboardPortlet portlet = iterator2.next();
						
						if(portlet.getPortletId().equalsIgnoreCase(getRequestValue("portletWindowList"))) {
							setRequestAttribute("dashboardId", portlet.getDashboardId());
							setRequestAttribute("portlet.title", portlet.getPortletTitle());
							setRequestAttribute("columnName", portlet.getColumnName());
							setRequestAttribute("portletSortOrder", portlet.getSortOrder());
							setRequestAttribute("visible", portlet.getVisible());
							break;
						}
					}
				} else if(getRequestValue("portletWindowList").trim().equals("")) {
					setRequestAttribute("portlet.title", "");
					setRequestAttribute("portletSortOrder", "");
				}
			}
		}
		
		setRequestAttribute("dashboardOption", allDashboard);
		setRequestAttribute("modifyDashboardOption", allDashboardWithoutDt);
	}
	
	//畫面控制項函數區結束
	
	//邏輯函數區
	//頁面動作所對應的處理method,business logic,請歸類到下面
	private void createPortletSubmit(HttpSession session, PortletAdminData portletAdminData) throws ServiceException, NamingException {
		String portletWindowName = request.getParameter(AdminConstants.PORTLET_WINDOW_NAME + "_create");
        String portletName = request.getParameter(AdminConstants.PORTLET_LIST);
        String title = request.getParameter(AdminConstants.PORTLET_WINDOW_TITLE + "_create");
        if(portletName == null){
            String message = DesktopMessages.getLocalizedString(AdminConstants.NO_BASE_PORTLET);
            session.setAttribute(AdminConstants.CREATION_FAILED_ATTRIBUTE, message);
        } else {
            boolean isValid = validateString(portletWindowName, false);
            boolean isDuplicate = false;
            if(isValid) {
                // Check if a portlet window already exists with the same name.
                List<String> portletWindowNames = portletAdminData.getPortletWindowNames();
                if(portletWindowNames != null) {
                    for(String tempPortletWindowName: portletWindowNames){
                        if(portletWindowName.equals(tempPortletWindowName)) {
                            String message = DesktopMessages.getLocalizedString(AdminConstants.PORTLET_WINDOW_NAME_ALREADY_EXISTS, 
                                    new String[]{portletWindowName});
                            session.setAttribute(AdminConstants.CREATION_FAILED_ATTRIBUTE, message);
                            isDuplicate = true;
                            break;
                        }
                    }
                }
            }
            
            if(!isDuplicate) {
                if(isValid) {
                    isValid = validateString(title, true);
                }
                StringBuffer messageBuffer = new StringBuffer(DesktopMessages.getLocalizedString(AdminConstants.CREATION_FAILED));
                if(isValid) {
                    boolean success = false;
                    try {
                        success = portletAdminData.createPortletWindow(portletName, portletWindowName, title);
                    } catch (Exception ex) {
                        messageBuffer.append(".");
                        messageBuffer.append(ex.getMessage());
                    }
                    if(success) {
                    	Portlet portlet = getDomainObjectFactory().createPortlet();
                    	portlet.setId(portletWindowName);
                    	portlet.setCreateUserId(getLoginUserId());
                    	portlet.setCreateDate(Calendar.getInstance());
                    	boolean openPermission = getRequestValue("createWindowPermissionFlag").equals("on");
        				resourceService.addPortlet(portlet, openPermission);
                    	
                        String message = DesktopMessages.getLocalizedString(AdminConstants.CREATION_SUCCEEDED);
                        session.setAttribute(AdminConstants.CREATION_SUCCEEDED_ATTRIBUTE, message);
                        AdminUtils.refreshList(request);
                    } else {
                        session.setAttribute(AdminConstants.CREATION_FAILED_ATTRIBUTE, messageBuffer.toString());
                    }
                } else {
                    String message = DesktopMessages.getLocalizedString(AdminConstants.INVALID_CHARACTERS);
                    session.setAttribute(AdminConstants.CREATION_FAILED_ATTRIBUTE, message);
                }
            }
        }
	}
	
	@SuppressWarnings("unchecked")
	private void undeploySubmit(HttpSession session, PortletAdminData portletAdminData) throws ServiceException {
		String[] portletsToUndeploy = request.getParameterValues(AdminConstants.PORTLETS_TO_UNDEPLOY);
        if(portletsToUndeploy == null){
            String message = DesktopMessages.getLocalizedString(AdminConstants.NO_PORTLET_APP);
            session.setAttribute(AdminConstants.UNDEPLOYMENT_FAILED_ATTRIBUTE, message);
        } else {
        	List<String> oldList = (List<String>)getSessionAttribute("com.sun.portal.portletcontainer.driver.admin.portletWindows");
        	
            StringBuffer messageBuffer = new StringBuffer();
            boolean success = false;
            for (int i = 0; i < portletsToUndeploy.length; i++) {
                String warName = portletsToUndeploy[i];
                try {
                    success = portletAdminData.undeploy(warName, true);
                } catch (Exception ex) {
                    success = false;
                    if(ex instanceof WebAppDeployerException){
                        Object[] tokens = {warName+".war"};
                        messageBuffer.append(DesktopMessages.getLocalizedString(AdminConstants.WAR_NOT_UNDEPLOYED, tokens));
                    } else {
                        messageBuffer.append(DesktopMessages.getLocalizedString(AdminConstants.UNDEPLOYMENT_FAILED));
                        messageBuffer.append(".");
                        messageBuffer.append(ex.getMessage());
                    }
                    // If undeploy throws exception, stop undeploying remaining portlets
                    break;
                }
            }
            
            if (success) {
                messageBuffer.append(DesktopMessages.getLocalizedString(AdminConstants.UNDEPLOYMENT_SUCCEEDED));
                session.setAttribute(AdminConstants.UNDEPLOYMENT_SUCCEEDED_ATTRIBUTE, messageBuffer.toString());
                // refresh portlet list
                AdminUtils.refreshList(request);
                
                List<String> newList = (List<String>)getSessionAttribute("com.sun.portal.portletcontainer.driver.admin.portletWindows");
                
                //undeploy時,刪掉這個war檔所有的portlet
                //因為一個war檔,有可能有好幾個portlet
                deleteDashboardPortlet(oldList, newList);
                
                //UI reload
                removeRequestAttribute("portletOption");
                makePortletOption();
                removeRequestAttribute("portletWindowOption");
                makePortletWindowOption();
            } else {
                session.setAttribute(AdminConstants.UNDEPLOYMENT_FAILED_ATTRIBUTE, messageBuffer.toString());
            }
        }
	}
	
	private String modifyPortletSubmit(HttpSession session, PortletAdminData portletAdminData) throws IOException, ServiceException, NamingException {
		if(getActFlag().equalsIgnoreCase("loadPortletProperties")) {
			User loginUser = getLoginUser();
			
			makePortletProperties(loginUser);
			
			String[] properties = new String[5];
			for(int i = 0; i < 5; i++) {
				properties[i] = "";
			}
			
			if(getRequestAttribute("dashboardId") != null) {
				properties[0] = (String)getRequestAttribute("dashboardId");
			}
			
			if(getRequestAttribute("portlet.title") != null) {
				properties[1] = (String)getRequestAttribute("portlet.title");
			}
			
			if(getRequestAttribute("columnName") != null) {
				properties[2] = (String)getRequestAttribute("columnName");
			}
			
			if(getRequestAttribute("portletSortOrder") != null) {
				properties[3] = "" + getRequestAttribute("portletSortOrder");
			}
			
			if(getRequestAttribute("visible") != null) {
				properties[4] = "" + getRequestAttribute("visible");
			}
			
			StringBuffer resultString = new StringBuffer();
			for(int i = 0; i < 5; i++) {
				if(i != 0) {
					resultString.append(";");
				}
				resultString.append(properties[i]);
			}
			
			getResponse().getWriter().print(resultString.toString());
    		
    		return null;
		} else {
    		modifyPortlet(session, portletAdminData);
    	}
		
		return "";
	}
	
	private void loadPortletProperties(HttpSession session, PortletAdminData portletAdminData) {
		try {
        	AdminUtils.setPortletWindowAttributes(session, portletAdminData, null);
        } catch(Exception ex) {
            StringBuffer messageBuffer = new StringBuffer(DesktopMessages.getLocalizedString(AdminConstants.NO_WINDOW_DATA));
            messageBuffer.append(".");
            messageBuffer.append(ex.getMessage());
            session.setAttribute(AdminConstants.NO_WINDOW_DATA_ATTRIBUTE, messageBuffer.toString());
        }
	}
	
	private void modifyPortlet(HttpSession session, PortletAdminData portletAdminData) throws NamingException {
		String portletWindowName = request.getParameter("portletWindowList");
        setSelectedPortletWindow(session, portletWindowName);
        String dashboardId = getRequestValue("dashboardId");
        String portletSortOrder = getRequestValue("portletSortOrder");
        String title = request.getParameter(AdminConstants.PORTLET_WINDOW_TITLE);
        String width = request.getParameter(AdminConstants.WIDTH_LIST);
        String visibleValue = request.getParameter(AdminConstants.VISIBLE_LIST);
        boolean visible;
        if(PortletRegistryConstants.VISIBLE_TRUE.equals(visibleValue)){
            visible = true;
        } else {
            visible = false;
        }
        if(portletWindowName == null){
            String message = DesktopMessages.getLocalizedString(AdminConstants.NO_BASE_PORTLET_WINDOW);
            session.setAttribute(AdminConstants.MODIFY_FAILED_ATTRIBUTE, message);
        } else {
            StringBuffer messageBuffer = new StringBuffer(DesktopMessages.getLocalizedString(AdminConstants.MODIFY_FAILED));
            boolean success = false;
            try {
            	saveModifyPortletWindow(portletWindowName, dashboardId, title, width, visible, portletSortOrder);
            	
                success = portletAdminData.modifyPortletWindow(portletWindowName, width, visible);
                AdminUtils.setPortletWindowAttributes(session, portletAdminData, portletWindowName);
            } catch (Exception ex) {
            	messageBuffer.append(".");
                messageBuffer.append(ex.getMessage());
            }
            if(success) {
                String message = DesktopMessages.getLocalizedString(AdminConstants.MODIFY_SUCCEEDED);
                session.setAttribute(AdminConstants.MODIFY_SUCCEEDED_ATTRIBUTE, message);
            } else {
                session.setAttribute(AdminConstants.MODIFY_FAILED_ATTRIBUTE, messageBuffer.toString());
            }
        }
	}
	
	private void portletList(HttpSession session, PortletAdminData portletAdminData) {
		String portletWindowName = request.getParameter(AdminConstants.PORTLET_WINDOW_LIST);
        setSelectedPortletWindow(session, portletWindowName);
        if(portletWindowName == null){
            String message = DesktopMessages.getLocalizedString(AdminConstants.NO_BASE_PORTLET_WINDOW);
            session.setAttribute(AdminConstants.NO_WINDOW_DATA_ATTRIBUTE, message);
        } else {
            StringBuffer messageBuffer = new StringBuffer(DesktopMessages.getLocalizedString(AdminConstants.NO_WINDOW_DATA));
            // Set the attribues for show/hide and thick/thin
            boolean success = false;
            try {
            	AdminUtils.setPortletWindowAttributes(session, portletAdminData, portletWindowName);
                success = true;
            } catch (Exception ex) {
                messageBuffer.append(".");
                messageBuffer.append(ex.getMessage());
            }
            if(!success) {
                session.setAttribute(AdminConstants.NO_WINDOW_DATA_ATTRIBUTE, messageBuffer.toString());
            }
        }
	}
	
	private String modifyDashboardSubmit() throws IOException, ServiceException, NamingException {
		User loginUser = getLoginUser();
    	Dashboard dashboard = loginUser.getDashboard(getRequestValue("dashboard"));
    	String actFlag = getRequestValue("modifyDashboardActFlag");
    	
    	if(actFlag.equalsIgnoreCase("save")) {
    		dashboardSave(loginUser, dashboard);
    	} else if(actFlag.equalsIgnoreCase("delete")) {
    		dashboardDelete(loginUser, dashboard);
    	} else if(actFlag.equalsIgnoreCase("loadDashboardProperties")) {
    		if(getRequestValue("dashboard").trim().equals("")) {
    			//沒有輸入dashboard,就把SortOrder清空
    			getResponse().getWriter().print("");
    		} else {
	    		if(dashboard != null) {
		    		if(dashboard.getSortOrder() == null) { //沒有設定SortOrder的情況
		    			getResponse().getWriter().print("1");
		    		} else {
		    			getResponse().getWriter().print(dashboard.getSortOrder());
		    		}
		    	} else { //資料庫無資料，要取一個最大SortOrder+1給它
	    			Iterator<Dashboard> iterator = loginUser.getDashboards().iterator();
	    			
	    			int maxSortOrder = 0;
	    			while(iterator.hasNext()) {
	    				Dashboard d = iterator.next();
	    				if(d.getSortOrder() > maxSortOrder) {
	    					maxSortOrder = d.getSortOrder();
	    				}
	    			}
	    			
	    			getResponse().getWriter().print(maxSortOrder + 1);
	    		}
    		}
    		
    		return null;
    	}
    	
    	return "";
	}
	
	private void dashboardSave(User loginUser, Dashboard dashboard) throws ServiceException, NamingException {
		if(dashboard != null) {
			dashboard.setDashboardName(getRequestValue("dashboard_valueDisplay"));
			if(getRequestValue("dashboardSortOrder").trim().equals("")) {
				dashboard.setSortOrder(getMaxDashboardSortOrder(loginUser));
			} else {
				dashboard.setSortOrder(Integer.parseInt(getRequestValue("dashboardSortOrder")));
			}
			dashboard.setModifyUserId(getLoginUserId());
			dashboard.setModifyDate(Calendar.getInstance());
		} else {
			dashboard = getDomainObjectFactory().createDashboard();
			dashboard.setUserId(getLoginUserId());
			dashboard.setDashboardId(getRequestValue("dashboard"));
			dashboard.setDashboardName(getRequestValue("dashboard_valueDisplay"));
			if(getRequestValue("dashboardSortOrder").trim().equals("")) {
				dashboard.setSortOrder(getMaxDashboardSortOrder(loginUser));
			} else {
				dashboard.setSortOrder(Integer.parseInt(getRequestValue("dashboardSortOrder")));
			}
			dashboard.setCreateUserId(getLoginUserId());
			dashboard.setCreateDate(Calendar.getInstance());
			
			loginUser.addDashboard(dashboard);
		}
		
		userService.saveUser(loginUser);
	}
	
	private void dashboardDelete(User loginUser, Dashboard dashboard) throws ServiceException {
		if(dashboard != null) {
			loginUser.removeDashboard(dashboard);
			
			userService.deleteDashboard(dashboard);
		}
	}
	
	private int getMaxDashboardSortOrder(User loginUser) {
		int result = 1;
		
		Iterator<Dashboard> iterator = loginUser.getDashboards().iterator();
		while(iterator.hasNext()) {
			Dashboard dashboard = iterator.next();
			
			if(dashboard.getSortOrder() >= result) {
				result = dashboard.getSortOrder() + 1;
			}
		}
		
		return result;
	}
	
	private int getMaxPortletSortOrder(User loginUser, String dashboardId, String width) {
		int result = 0;
		
		Dashboard dashboard = loginUser.getDashboard(dashboardId);
		Iterator<DashboardPortlet> iterator = dashboard.getPortlets().iterator();
		while(iterator.hasNext()) {
			DashboardPortlet portlet = iterator.next();
			
			if(portlet.getColumnName().trim().equalsIgnoreCase(width) && portlet.getSortOrder() >= result) {
				result = portlet.getSortOrder() + 1;
			}
		}
		
		
		return result;
	}
	
	private void deleteDashboardPortlet(List<String> oldList, List<String> newList) throws ServiceException {
		for(int i = 0; i < oldList.size(); i++) {
			String portletId = oldList.get(i);
			
			if(!newList.contains(portletId)) {
				//刪除使用者做出來的portlet視窗副本
				User loginUser = getLoginUser();
				DashboardPortlet portlet = loginUser.getDashboardPortlet(portletId);
				if(portlet != null) { //如果使用者有把他加到某個dashboard才需要刪
					Dashboard dashboard = loginUser.getDashboard(portlet.getDashboardId());
					dashboard.removePortlet(portlet);
					userService.deleteDashboardPortlet(portlet);
				}
				
				//刪除portlet
				Portlet voObj = getDomainObjectFactory().createPortlet();
				voObj.setId(portletId);
				voObj.setResourceType(ResourceType.PORTLET);
				
				resourceService.deletePortlet(voObj);
			}
		}
	}
	
	private void saveModifyPortletWindow(String portletWindowName, String dashboardId, String title, String width, boolean visible, String portletSortOrder) throws ServiceException {
		User loginUser = getLoginUser();
    	
    	Iterator<Dashboard> iterator = loginUser.getDashboards().iterator();
    	
    	//刪除,因為有可能改動歸屬的dashboard
    	boolean flag = false;
    	String createUserId = null;
    	Calendar createDate = null;
    	while(iterator.hasNext()) {
    		Dashboard dashboard = iterator.next();
    		
    		Iterator<DashboardPortlet> iterator2 = dashboard.getPortlets().iterator();
    		
    		while(iterator2.hasNext()) {
    			DashboardPortlet targetPortlet = iterator2.next();
    			
    			if(targetPortlet.getPortletId().equals(portletWindowName)) {
    				createUserId = targetPortlet.getCreateUserId();
    				createDate = targetPortlet.getCreateDate();
    				dashboard.removePortlet(targetPortlet);
    				userService.deleteDashboardPortlet(targetPortlet);
    				
    				flag = true;
    				
    				if(flag) {
    					break;
    				}
    			}
    		}
    		
    		if(flag) {
				break;
			}
    	}
    	
    	//再新增
    	DashboardPortlet portlet = getDomainObjectFactory().createDashboardPortlet();
    	portlet.setUserId(getLoginUserId());
    	portlet.setDashboardId(dashboardId);
    	portlet.setPortletId(portletWindowName);
    	portlet.setPortletTitle(title);
    	portlet.setVisible(visible);
    	if(portletSortOrder.trim().equals("")) {
    		portlet.setSortOrder(getMaxPortletSortOrder(loginUser, dashboardId, width));
    	} else {
    		portlet.setSortOrder(Integer.parseInt(portletSortOrder));
    	}
    	portlet.setColumnName(width);
    	
    	if(flag) {
    		portlet.setCreateUserId(createUserId);
    		portlet.setCreateDate(createDate);
    		portlet.setModifyUserId(getLoginUserId());
    		portlet.setModifyDate(Calendar.getInstance());
    	} else {
        	portlet.setCreateUserId(getLoginUserId());
        	portlet.setCreateDate(Calendar.getInstance());
    	}
    	
    	userService.addDashboardPortlet(portlet);
    	
    	Dashboard dashboard = loginUser.getDashboard(dashboardId);
    	dashboard.addPortlet(portlet);
    	
    	//修改存檔後,控制項要帶回新的值
    	removeRequestAttribute("dashboardId");
    	setRequestAttribute("dashboardId", dashboardId);
    	
    	removeRequestAttribute("portlet.title");
    	setRequestAttribute("portlet.title", title);
    	
    	removeRequestAttribute("portletSortOrder");
		setRequestAttribute("portletSortOrder", portletSortOrder);
		
		removeRequestAttribute("visible");
		setRequestAttribute("visible", visible);
	}
	
	private void saveModifyPortletWindow(String portletWindowName, String dashboardId, String width, int portletSortOrder) throws ServiceException {
		User loginUser = getLoginUser();
    	
		Dashboard dashboard = loginUser.getDashboard(dashboardId);
		
		Iterator<DashboardPortlet> iterator = dashboard.getPortlets().iterator();
		
		while(iterator.hasNext()) {
			DashboardPortlet targetPortlet = iterator.next();
			
			if(targetPortlet.getPortletId().equals(portletWindowName)) {
				targetPortlet.setColumnName(width);
				targetPortlet.setSortOrder(portletSortOrder);
				targetPortlet.setModifyUserId(getLoginUserId());
				targetPortlet.setModifyDate(Calendar.getInstance());
				
				userService.addDashboardPortlet(targetPortlet);
				
				break;
			}
		}
    }
	
	private boolean validateString(String name, boolean allowSpaces) {
        if(name == null || name.trim().length() == 0){
            return false;
        }
        String value = name.trim();
        for(int i=0; i<value.length(); i++) {
            char c = value.charAt(i);
            if(!Character.isLetterOrDigit(c) && !((c == '_') || (allowSpaces && c == ' '))){
                return false;
            }
        }
        return true;
    }
    
    private boolean isParameterPresent(HttpServletRequest request, String parameter) {
        String name = request.getParameter(parameter);
        return (name == null ? false : true);
    }
    
    private void setSelectedPortletWindow(HttpSession session, String portletWindowName) {
        session.removeAttribute(AdminConstants.SELECTED_PORTLET_WINDOW_ATTRIBUTE);
        session.setAttribute(AdminConstants.SELECTED_PORTLET_WINDOW_ATTRIBUTE, portletWindowName);
    }
    
	//邏輯函數區結束
}
