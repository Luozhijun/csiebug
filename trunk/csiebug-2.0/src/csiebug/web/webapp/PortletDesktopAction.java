package csiebug.web.webapp;

import com.sun.portal.container.ChannelMode;
import com.sun.portal.container.ChannelState;
import com.sun.portal.container.EntityID;
import com.sun.portal.container.PortletType;
import com.sun.portal.portletcontainer.context.registry.PortletRegistryContext;
import com.sun.portal.portletcontainer.admin.PortletRegistryCache;
import com.sun.portal.portletcontainer.admin.registry.PortletRegistryConstants;
import com.sun.portal.portletcontainer.context.ServletContextThreadLocalizer;
import com.sun.portal.portletcontainer.context.registry.PortletRegistryException;
import com.sun.portal.portletcontainer.driver.DesktopConstants;
import com.sun.portal.portletcontainer.driver.DesktopMessages;
import com.sun.portal.portletcontainer.driver.DriverUtil;
//import com.sun.portal.portletcontainer.driver.PortletContent;
import com.sun.portal.portletcontainer.driver.PortletWindowData;
import com.sun.portal.portletcontainer.driver.PortletWindowDataImpl;
import com.sun.portal.portletcontainer.driver.PropertiesContext;
import com.sun.portal.portletcontainer.invoker.InvokerException;
import com.sun.portal.portletcontainer.invoker.WindowInvokerConstants;

import com.sun.portal.portletcontainer.invoker.util.InvokerUtil;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

import csiebug.domain.Dashboard;
import csiebug.domain.DashboardPortlet;
import csiebug.domain.User;
import csiebug.service.ServiceException;
import csiebug.service.UserService;
import csiebug.util.AssertUtility;
import csiebug.util.StringUtility;
import csiebug.web.html.HtmlBuilder;

public class PortletDesktopAction extends PortletBasicAction {
	private static final long serialVersionUID = 1L;
	
	private UserService userService;
	private User loginUser;
	
	HttpServletRequest request;
	HttpServletResponse response;
	ServletContext context;
	private static Logger logger = Logger.getLogger(PortletDesktopAction.class.getPackage().getName(),
    "PCDLogMessages");
	
	private void init() throws ServiceException {
		request = getRequest();
		response = getResponse();
		context = getServletContext();
		PropertiesContext.init();
        PortletRegistryCache.init();
        userService = getUserService();
        loginUser = getLoginUser();
    }

	public String main() throws Exception {
		//init
		init();
		
		DesktopMessages.init(request);
        DriverUtil.init(request);
        response.setContentType("text/html;charset=UTF-8");
        
        // Get the list of visible portlets(sorted by the row number)
        try {
        	ServletContextThreadLocalizer.set(context);
            PortletRegistryContext portletRegistryContext = initSortWidthStatus(loginUser, DriverUtil.getPortletRegistryContext());
            PortletContent portletContent = getPortletContentObject(context, request, response);
            String portletWindowName = DriverUtil.getPortletWindowFromRequest(request);
            boolean reRenderFlag = false;
            String portletRemove = DriverUtil.getPortletRemove(request);
            if(portletRemove != null && portletWindowName != null) {
            	DashboardPortlet portlet = loginUser.getDashboardPortlet(portletWindowName);
                portlet.setVisible(false);
                portlet.setModifyUserId(getLoginUserId());
                portlet.setModifyDate(Calendar.getInstance());
                
                userService.updateDashboardPortlet(portlet);
                loginUser = getLoginUser();
                
            	portletRegistryContext.showPortletWindow(portletWindowName, false);
//                portletWindowName = null; // re-render all portlets
            	reRenderFlag = true;
            }
            @SuppressWarnings("rawtypes")
			Map portletContents = null;
//            if(portletWindowName == null) {
            if(reRenderFlag) {
                portletContents = getAllPortletContents(request, portletContent, portletRegistryContext);
            } else {
            	if(getActFlag().equalsIgnoreCase("renderPortlet")) {
            		getResponse().getWriter().print(renderPortlet(portletContent, getRequestValue("PortletWindowId")));
            		return null;
                }
            	
            	String driverAction = DriverUtil.getDriverAction(request);
            	if(driverAction == null) { //預設是render
            		driverAction = WindowInvokerConstants.RENDER;
            	}
            	
                if(WindowInvokerConstants.ACTION.equals(driverAction)) {
                	URL url = executeProcessAction(request, portletContent);
                    try {
                    	if(url != null) {
                            response.sendRedirect(url.toString());
                        } else {
                            response.sendRedirect(StringUtility.cleanCRLF(request.getRequestURL().toString()));
                        }
                    } catch (IOException ioe) {
                        throw new InvokerException("Failed during sendRedirect", ioe);
                    }
                } else if(WindowInvokerConstants.RENDER.equals(driverAction)) {
                    portletContents = getAllPortletContents(request, portletContent, portletRegistryContext);
                } else if(WindowInvokerConstants.RESOURCE.equals(driverAction)) {
                     portletContent.setPortletWindowName(portletWindowName);
                     ChannelMode portletWindowMode = getCurrentPortletWindowMode(request, portletWindowName);
                     ChannelState portletWindowState = getCurrentPortletWindowState(request, portletWindowName);
                     portletContent.setPortletWindowState(portletWindowState);
                     portletContent.setPortletWindowMode(portletWindowMode);
                     portletContent.getResources();
                }
            }
            if(portletContents != null){
            	Map<String, SortedSet<PortletWindowData>> portletWindowContents = 
                        getPortletWindowContents(request, portletContents, portletRegistryContext);
                setPortletWindowData(request, portletWindowContents);
                InvokerUtil.setResponseProperties(request, response, portletContent.getResponseProperties());
            }
        } finally {
            ServletContextThreadLocalizer.set(null);
        }
        
        //頁面控制項需要的資料
		makeControl();
		
		return FORMLOAD;
	}
	
	//畫面控制項函數區
	private void makeControl() throws UnsupportedEncodingException, NamingException, ServiceException {
		//畫面控制項
		try {
			setRequestAttribute("FunctionName", getFunctionName());
		} catch(ServiceException se) {
			writeErrorLog(se);
			
			if(AssertUtility.isNotNullAndNotSpace(getFunctionId())) {
				setRequestAttribute("FunctionName", getFunctionId());
			} else {
				setRequestAttribute("FunctionName", "");
			}
		}
		
		makeLayout();
		makeLayoutOption();
		makeTabs(loginUser, false);
		setRequestAttribute("UserId", getLoginUserId());
	}
	
	private void makeLayout() throws ServiceException, UnsupportedEncodingException, NamingException {
    	if(loginUser != null) {
			String dashboardId = getRequestValue("dashboardId");
			
			if(dashboardId.trim().equals("")) {
				dashboardId = "dt";
				
				//如果資料庫中沒任何dt(預設dashboard)這個dashboard,要新增一筆
				//每個使用者至少都要有一個dashboard才行
				if(loginUser.getDashboard(dashboardId) == null) {
					Dashboard dashboard = getDomainObjectFactory().createDashboard();
					dashboard.setUserId(getLoginUserId());
					dashboard.setDashboardId("dt");
					dashboard.setDashboardName(getMessage("common.dashboard"));
					dashboard.setSortOrder(0);
					dashboard.setLayout("1");
					dashboard.setCreateUserId(getLoginUserId());
					dashboard.setCreateDate(Calendar.getInstance());
					userService.addDashboard(dashboard);
					
					loginUser = getLoginUser();
				}
			}
    		
    		if(!getRequestValue("layout").trim().equals("")) {
    			loginUser.getDashboard(dashboardId).setLayout(getRequestValue("layout"));
    			userService.saveUser(loginUser);
    			setRequestAttribute("layout", getRequestValue("layout"));
    		} else {
    			String layout = loginUser.getDashboard(dashboardId).getLayout();
    			if(AssertUtility.isNotNullAndNotSpace(layout)) {
    				setRequestAttribute("layout", layout);
    			} else {
    				setRequestAttribute("layout", "1");
    			}
    		}
		} else {
			if(!getRequestValue("layout").trim().equals("")) {
				setRequestAttribute("layout", getRequestValue("layout"));
			} else {
				setRequestAttribute("layout", "1");
			}
		}
	}
	
	private void makeLayoutOption() throws UnsupportedEncodingException, NamingException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("1", getMessage("desktop.ThickThin"));
		map.put("2", getMessage("desktop.ThinThick"));
		
		setRequestAttribute("layoutOption", map);
	}
		
	//畫面控制項函數區結束
	
	//邏輯函數區
	//頁面動作所對應的處理method,business logic,請歸類到下面
	private StringBuffer renderPortlet(PortletContent portletContent, String portletWindowName) throws NamingException {
		portletContent.setPortletWindowName(portletWindowName);
		portletContent.setPortletWindowMode(getCurrentPortletWindowMode(request, portletWindowName));
        portletContent.setPortletWindowState(getCurrentPortletWindowState(request, portletWindowName));
        
        StringBuffer buffer;
        try {
        	buffer = portletContent.getContent();
        } catch (InvokerException ie) {
            buffer = new StringBuffer(ie.getMessage());
        }
        return buffer;
	}
	
	@SuppressWarnings("unchecked")
	private PortletRegistryContext initSortWidthStatus(User loginUser, PortletRegistryContext portletRegistryContext) throws PortletRegistryException {
		List<String> portletWindowNames = (List<String>)portletRegistryContext.getVisiblePortletWindows(PortletType.LOCAL);
		
		for(int i = 0; i < portletWindowNames.size(); i++) {
			String portletWindowName = portletWindowNames.get(i);
			DashboardPortlet portlet = loginUser.getDashboardPortlet(portletWindowName);
			
			if(portlet != null) {
				portletRegistryContext.setWidth(portletWindowName, portlet.getColumnName());
			}
		}
		
		return portletRegistryContext;
	}
	
	/**
     * Returns the PortletWindowData object for the portlet window.
     * A Set of PortletWindowData for all portlet windows is stored in the
     * Session.
     *
     * @param request the HttpServletRequest Object
     * @param portletWindowName the name of the portlet window
     *
     * @return the PortletWindowData object for the portlet window.
     */
    @SuppressWarnings("unchecked")
	private PortletWindowData getPortletWindowData(HttpServletRequest request, String portletWindowName) {
        PortletWindowData portletWindowData = null;
        Map<String, SortedSet<PortletWindowData>> portletWindowContents = getPortletWindowContentsFromSession(request);
        boolean found = false;
        if(portletWindowContents != null) {
            @SuppressWarnings("rawtypes")
			Set set = portletWindowContents.entrySet();
            @SuppressWarnings("rawtypes")
			Iterator<Map.Entry> setItr = set.iterator();
            while(setItr.hasNext()) {
                Map.Entry<String, SortedSet<PortletWindowData>> mapEntry = setItr.next();
                SortedSet<PortletWindowData> portletWindowDataSet = mapEntry.getValue();
                for(Iterator<PortletWindowData> itr = portletWindowDataSet.iterator(); itr.hasNext();) {
                    portletWindowData = itr.next();
                    if(portletWindowName.equals(portletWindowData.getPortletWindowName())) {
                        found = true;
                        break;
                    }
                }
                if(found) {
                    break;
                }
            }
        }
        if(found) {
            return portletWindowData;
        } else {
            return null;
        }
    }
    
    private void setPortletWindowData(HttpServletRequest request, 
            Map<String, SortedSet<PortletWindowData>> portletWindowContents) {
        HttpSession session = request.getSession(true);
        session.removeAttribute(DesktopConstants.PORTLET_WINDOWS);
        session.setAttribute(DesktopConstants.PORTLET_WINDOWS, portletWindowContents);
    }
    
    /**
     * Returns a Map of portlet data and title for all portlet windows.
     * In the portlet window is maximized, only the data and title for that portlet is
     * displayed.
     * For any portlet window that is minimized , only the title is shown.
     *
     * @param request the HttpServletRequest Object
     * @param portletContent the PortletContent Object
     * @param portletRegistryContext the PortletRegistryContext Object
     * 
     * @return a Map of portlet data and title for all portlet windows.
     * @throws NamingException 
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private Map getAllPortletContents(HttpServletRequest request, PortletContent portletContent,
            PortletRegistryContext portletRegistryContext) throws InvokerException, NamingException {
        String portletWindowName = DriverUtil.getPortletWindowFromRequest(request);
        ChannelState portletWindowState = getCurrentPortletWindowState(request, portletWindowName);
        
		Map portletContents;
        if(portletWindowState.equals(ChannelState.MAXIMIZED)) {
            portletContent.setPortletWindowState(ChannelState.MAXIMIZED);
            portletContents = getPortletContent(request, portletContent, portletWindowName);
        } else {
        	List visiblePortletWindows = getVisiblePortletWindows(portletRegistryContext);
            int numPortletWindows = visiblePortletWindows.size();
        	List portletList = new ArrayList();
        	List portletMinimizedList = new ArrayList();
            for(int i = 0; i < numPortletWindows; i++) {
                portletWindowName = (String)visiblePortletWindows.get(i);
                portletWindowState = getCurrentPortletWindowState(request, portletWindowName);
                if(portletWindowState.equals(ChannelState.MINIMIZED)) {
                    portletMinimizedList.add(portletWindowName);
                } else {
                    portletList.add(portletWindowName);
                }
            }
            portletContents = getPortletContents(request, portletContent, portletList, true);
            if(!portletMinimizedList.isEmpty()){
                Map portletTitles = getPortletTitles(request, portletContent, portletMinimizedList);
                portletContents.putAll(portletTitles);
            }
        }
        return portletContents;
    }
    
    /**
     * Returns a Map of portlet data and title for a portlet window.
     *
     * @param request the HttpServletRequest Object
     * @param portletContent the PortletContent Object
     * @param portletWindowName the name of the portlet window
     *
     * @return a Map of portlet data and title for a portlet window.
     * @throws NamingException 
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private Map getPortletContent(HttpServletRequest request, PortletContent portletContent,
            String portletWindowName) throws InvokerException, NamingException {
        portletContent.setPortletWindowName(portletWindowName);
        ChannelMode portletWindowMode = getCurrentPortletWindowMode(request, portletWindowName);
        portletContent.setPortletWindowMode(portletWindowMode);
        StringBuffer buffer = portletContent.getContent();
        String title = portletContent.getTitle();
        Map portletContents = new HashMap();
        portletContents.put(DesktopConstants.PORTLET_CONTENT, buffer);
        portletContents.put(DesktopConstants.PORTLET_TITLE, title);
        Map portletContentMap = new HashMap();
        portletContentMap.put(portletWindowName, portletContents);
        return portletContentMap;
    }
    
    /**
     * Returns a Map of portlet data and title for the portlet windows specified in the portletList
     *
     * @param request the HttpServletRequest Object
     * @param portletContent the PortletContent Cobject
     * @param portletList the List of portlet windows
     * 
     * @return a Map of portlet data and title for the portlet windows specified in the portletList
     * @throws NamingException 
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private Map getPortletContents(HttpServletRequest request, PortletContent portletContent,
            List portletList, boolean partialLoaded) throws InvokerException, NamingException {
        String portletWindowName;
        int numPortletWindows = portletList.size();
        Map portletContentMap = new HashMap();
        for(int i = 0; i < numPortletWindows; i++) {
            portletWindowName = (String)portletList.get(i);
            portletContent.setPortletWindowName(portletWindowName);
            ChannelMode channelMode = getCurrentPortletWindowMode(request, portletWindowName);
            portletContent.setPortletWindowMode(channelMode);
            portletContent.setPortletWindowState(getCurrentPortletWindowState(request, portletWindowName));
            
            StringBuffer buffer;
            if(!partialLoaded) {
            	//全部的portlet content都執行完才show dashboard
            	buffer = renderPortlet(portletContent, portletWindowName);
            } else {
            	//1.全部的portlet content都不處理,只丟loading圖案出來
            	//2.但是要在form load script裡面呼叫AJAX function去執行個別的portlet程式
            	HtmlBuilder htmlBuilder = new HtmlBuilder();
            	htmlBuilder.imgStart().src("images/gif_wait.gif").tagClose();
            	buffer = new StringBuffer(htmlBuilder.toString());
            	
            	String mode = "VIEW";
            	if(channelMode != null) {
            		if(channelMode.equals(ChannelMode.EDIT)) {
            			mode = "EDIT";
            		} else if(channelMode.equals(ChannelMode.HELP)) {
            			mode = "HELP";
            		} else  if(channelMode.equals(ChannelMode.CONFIG)) {
            			mode = "CONFIG";
            		}
            	}
            	addPageLoadScript("setTimeout('renderPortletContent(\"" + portletWindowName + "\", \"" + mode + "\");', " + (i + 1) + "000);");
            }
            
            String title = null;
            try{
                title = portletContent.getTitle();
            } catch (InvokerException iex) {
                // Just logging
                if(logger.isLoggable(Level.SEVERE)){
                    LogRecord logRecord = new LogRecord(Level.SEVERE,"PSPCD_CSPPD0048");
                    logRecord.setLoggerName(logger.getName());
                    logRecord.setThrown(iex);
                    logRecord.setParameters(new String[] {portletWindowName});
                    logger.log(logRecord);
                }
                title = "";
            }
            Map portletContents = new HashMap();
            portletContents.put(DesktopConstants.PORTLET_CONTENT, buffer);
            portletContents.put(DesktopConstants.PORTLET_TITLE, title);
            portletContentMap.put(portletWindowName, portletContents);
        }
        return portletContentMap;
    }
    
    /**
     * Returns a Map of portlet title for the portlet windows specified in the 
     * portletMinimizedList
     *
     * @param request the HttpServletRequest Object
     * @param portletContent the PortletContent Cobject
     * @param portletMinimizedList the List of portlet windows that are minimized
     * 
     * @return a Map of portlet title for the portlet windows that are minimized.
     * @throws NamingException 
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private Map getPortletTitles(HttpServletRequest request, PortletContent portletContent,
            List portletMinimizedList) throws InvokerException, NamingException {
        String portletWindowName;
        int numPortletWindows = portletMinimizedList.size();
        Map portletTitlesMap = new HashMap();
        for(int i = 0; i < numPortletWindows; i++) {
            portletWindowName = (String)portletMinimizedList.get(i);
            portletContent.setPortletWindowName(portletWindowName);
            portletContent.setPortletWindowMode(getCurrentPortletWindowMode(request, portletWindowName));
            portletContent.setPortletWindowState(getCurrentPortletWindowState(request, portletWindowName));
            StringBuffer buffer = null;
            String title = portletContent.getDefaultTitle();
            Map portletContents = new HashMap();
            portletContents.put(DesktopConstants.PORTLET_CONTENT, buffer);
            portletContents.put(DesktopConstants.PORTLET_TITLE, title);
            portletTitlesMap.put(portletWindowName, portletContents);
        }
        return portletTitlesMap;
    }
    
    /**
     * Returns a Map of PortletWindowData for the portlet windows for both thick
     * and think widths.
     *
     * @param request the HttpServletRequest Object
     * @param portletContents a Map of portlet data and title for the portlet windows
     * @param portletRegistryContext the PortletRegistryContext Object 
     *
     * @return a Map of PortletWindowData for the portlet windows
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
	private Map<String,SortedSet<PortletWindowData>> getPortletWindowContents(HttpServletRequest request, 
            @SuppressWarnings("rawtypes") Map portletContents, PortletRegistryContext portletRegistryContext) throws Exception {
        @SuppressWarnings("rawtypes")
		Iterator itr = portletContents.keySet().iterator();
        String portletWindowName;
        PortletComparator portletComparator = new PortletComparator(getLoginUser());
        SortedSet<PortletWindowData> portletWindowContentsThin = new TreeSet<PortletWindowData>(portletComparator);
        SortedSet<PortletWindowData> portletWindowContentsThick = new TreeSet<PortletWindowData>(portletComparator);
        int thinCount = 0;
        int thickCount = 0;
        while(itr.hasNext()) {
            portletWindowName = (String)itr.next();
            try {
                PortletWindowData portletWindowData = getPortletWindowDataObject(request, portletContents, portletRegistryContext, portletWindowName);
                
                if(portletWindowData.isThin()) {
                    portletWindowContentsThin.add(portletWindowData);
                    thinCount++;
                } else if (portletWindowData.isThick()) {
                    portletWindowContentsThick.add(portletWindowData);
                    thickCount++;
                } else {
                    throw new PortletRegistryException(portletWindowName + " is neither thick or thin!!");
                }
            } catch (PortletRegistryException pre) {
            	writeErrorLog(pre);
            }
        }
        @SuppressWarnings("rawtypes")
		Map portletWindowContents = new HashMap();
        portletWindowContents.put(PortletRegistryConstants.WIDTH_THICK, portletWindowContentsThick);
        portletWindowContents.put(PortletRegistryConstants.WIDTH_THIN, portletWindowContentsThin);
        logger.log(Level.INFO, "PSPCD_CSPPD0022", new String[] { String.valueOf(thinCount),
        String.valueOf(thickCount) });
        
        return portletWindowContents;
    }
    
    private URL executeProcessAction(HttpServletRequest request, PortletContent portletContent) throws InvokerException, Exception {
        String portletWindowName = DriverUtil.getPortletWindowFromRequest(request);
        ChannelMode portletWindowMode = DriverUtil.getPortletWindowModeOfPortletWindow(request, portletWindowName);
        ChannelState portletWindowState = DriverUtil.getPortletWindowStateOfPortletWindow(request, portletWindowName);
        portletContent.setPortletWindowName(portletWindowName);
        portletContent.setPortletWindowMode(portletWindowMode);
        portletContent.setPortletWindowState(portletWindowState);
        URL url = portletContent.executeAction();
		updatePortletModeAndState(
			getPortletWindowContentsFromSession(request), portletContent.getEventUpdatedPortlets());
		
		return url;
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map<String, SortedSet<PortletWindowData>> getPortletWindowContentsFromSession(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
        return (Map)session.getAttribute(DesktopConstants.PORTLET_WINDOWS);
	}
    
	@SuppressWarnings("unchecked")
	private void updatePortletModeAndState(
		Map<String, SortedSet<PortletWindowData>> portletWindowContents,
		List<EntityID> eventUpdatedPortlets) {
        if(portletWindowContents != null && eventUpdatedPortlets != null) {
            @SuppressWarnings("rawtypes")
			Set set = portletWindowContents.entrySet();
            @SuppressWarnings("rawtypes")
			Iterator<Map.Entry> setItr = set.iterator();
			PortletWindowData portletWindowData = null;
			List<String> portletWindowNames = new ArrayList<String>();
			for(EntityID entityID : eventUpdatedPortlets) {
				portletWindowNames.add(entityID.getPortletWindowName());
			}
            while(setItr.hasNext()) {
                Map.Entry<String, SortedSet<PortletWindowData>> mapEntry = setItr.next();
                SortedSet<PortletWindowData> portletWindowDataSet = mapEntry.getValue();
                for(Iterator<PortletWindowData> itr = portletWindowDataSet.iterator(); itr.hasNext();) {
                    portletWindowData = itr.next();
					if(portletWindowNames.contains(portletWindowData.getPortletWindowName())) {
						((PortletWindowDataImpl)portletWindowData).setCurrentMode(ChannelMode.VIEW);
						((PortletWindowDataImpl)portletWindowData).setCurrentWindowState(ChannelState.NORMAL);
					}
                }
            }
        }
	}

    /**
     * Returns the list of visible portlet windows from the portlet registry.
     *
     * @param portletRegistryContext the PortletRegistryContext Object
     * 
     * @return the list of visible portlet windows from the portlet registry.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	protected List getVisiblePortletWindows(PortletRegistryContext portletRegistryContext) throws InvokerException {
        List visiblePortletWindows = null;
        try {
        	String dashboardId = getRequestValue("dashboardId");
			
			if(dashboardId.trim().equals("")) {
				dashboardId = "dt";
			}
			
            visiblePortletWindows = getVisiblePortlets(dashboardId, getAuthorizedPortlets(portletRegistryContext.getVisiblePortletWindows(PortletType.LOCAL)));
        } catch (PortletRegistryException pre) {
            visiblePortletWindows = Collections.EMPTY_LIST;
            throw new InvokerException("Cannot get Portlet List", pre);
        } catch (Exception ex) {
        	visiblePortletWindows = Collections.EMPTY_LIST;
        	throw new InvokerException("Cannot get Portlet List", ex);
        }
        return visiblePortletWindows;
    }
    
    /**
     * Returns the current portlet window state for the portlet window.
     * First it checks in the request and then checks in the session.
     *
     * @param request the HttpServletRequest Object
     * @param portletWindowName the name of the portlet window
     *
     * @return the current portlet window state for the portlet window.
     */
    protected ChannelState getCurrentPortletWindowState(HttpServletRequest request, String portletWindowName) {
        ChannelState portletWindowState = ChannelState.NORMAL;
        if(portletWindowName != null) {
            portletWindowState = DriverUtil.getPortletWindowStateOfPortletWindow(request, portletWindowName);
            if(portletWindowState == null) {
                portletWindowState = getPortletWindowStateFromSavedData(request, portletWindowName);
            }
        }
        return portletWindowState;
    }
    
    /**
     * Returns the current portlet window mode for the portlet window.
     * First it checks in the request and then checks in the session.
     *
     * @param request the HttpServletRequest Object
     * @param portletWindowName the name of the portlet window
     *
     * @return the current portlet window mode for the portlet window.
     * @throws NamingException 
     */
//    protected ChannelMode getCurrentPortletWindowMode(HttpServletRequest request, String portletWindowName) {
//        ChannelMode portletWindowMode = ChannelMode.VIEW;
//        if(portletWindowName != null) {
//        	portletWindowMode = DriverUtil.getPortletWindowModeOfPortletWindow(request, portletWindowName);
//            if(portletWindowMode == null) {
//                portletWindowMode = getPortletWindowModeFromSavedData(request, portletWindowName);
//            }
//        }
//        return portletWindowMode;
//    }
    
    protected ChannelMode getCurrentPortletWindowMode(HttpServletRequest request, String portletWindowName) throws NamingException {
        ChannelMode portletWindowMode = ChannelMode.VIEW;
        if(portletWindowName != null) {
        	if(getRequestValue("PortletWindowId").equalsIgnoreCase(portletWindowName)) {
        		String requestMode = getRequestValue("PortletWindowMode");
        		
        		if(requestMode.equals("EDIT")) {
        			portletWindowMode = ChannelMode.EDIT;
        		} else if(requestMode.equals("HELP")) {
        			portletWindowMode = ChannelMode.HELP;
        		} else if(requestMode.equals("CONFIG")) {
        			portletWindowMode = ChannelMode.CONFIG;
        		}
        	} else {
        		portletWindowMode = null;
        	}
        	
        	if(portletWindowMode == null) {
                portletWindowMode = getPortletWindowModeFromSavedData(request, portletWindowName);
            }
        }
        return portletWindowMode;
    }
    
    /**
     * Returns the portlet window state for the portlet window from the
     * PortletWindowData that is in the session.
     *
     * @param request the HttpServletRequest Object
     * @param portletWindowName the name of the portlet window
     *
     * @return the portlet window state for the portlet window from session.
     */
    protected ChannelState getPortletWindowStateFromSavedData(HttpServletRequest request, String portletWindowName) {
        PortletWindowData portletWindowContent = getPortletWindowData(request, portletWindowName);
        ChannelState portletWindowState = ChannelState.NORMAL;
        if(portletWindowContent != null) {
            String currentPortletWindowState = portletWindowContent.getCurrentWindowState();
            if(currentPortletWindowState != null) {
                portletWindowState = new ChannelState(currentPortletWindowState);
            }
        }
        return portletWindowState;
    }
    
    /**
     * Returns the portlet window mode for the portlet window from the
     * PortletWindowData that is in the session.
     *
     * @param request the HttpServletRequest Object
     * @param portletWindowName the name of the portlet window
     *
     * @return the portlet window mode for the portlet window from session.
     */
    protected ChannelMode getPortletWindowModeFromSavedData(HttpServletRequest request, String portletWindowName) {
        PortletWindowData portletWindowContent = getPortletWindowData(request, portletWindowName);
        ChannelMode portletWindowMode = ChannelMode.VIEW;
        if(portletWindowContent != null) {
            String currentPortletWindowMode = portletWindowContent.getCurrentMode();
            if(currentPortletWindowMode != null) {
                portletWindowMode = new ChannelMode(currentPortletWindowMode);
            }
        }
        return portletWindowMode;
    }
    
    protected PortletContent getPortletContentObject(ServletContext context,
            HttpServletRequest request, HttpServletResponse response) throws InvokerException{
        return new PortletContent(context, request, response);
    }
    
    protected PortletWindowData getPortletWindowDataObject(HttpServletRequest request,
            @SuppressWarnings("rawtypes") Map portletContents, PortletRegistryContext portletRegistryContext,
            String portletWindowName) throws PortletRegistryException, Exception{
        
    	DashboardPortlet portlet = getLoginUser().getDashboardPortlet(portletWindowName);
    	
    	PortletWindowDataImpl portletWindowData = new PortletWindowDataImpl();
        @SuppressWarnings("rawtypes")
		Map portletContentMap = (Map)portletContents.get(portletWindowName);
        
        portletWindowData.init(request, portletRegistryContext, portletWindowName);
        portletWindowData.setContent((StringBuffer)portletContentMap.get(DesktopConstants.PORTLET_CONTENT));
        if(portlet != null) {
        	portletWindowData.setTitle(portlet.getPortletTitle());
        } else {
        	portletWindowData.setTitle((String)portletContentMap.get(DesktopConstants.PORTLET_TITLE));
        }
        portletWindowData.setCurrentMode(getCurrentPortletWindowMode(request, portletWindowName));
        portletWindowData.setCurrentWindowState(getCurrentPortletWindowState(request, portletWindowName));
        
        return portletWindowData;
    }
    
    //邏輯函數區結束
}
