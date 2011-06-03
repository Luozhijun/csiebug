package csiebug.web.webapp;

import com.sun.portal.container.ChannelMode;
import com.sun.portal.container.ChannelURLFactory;
import com.sun.portal.container.Container;
import com.sun.portal.container.ContentException;
import com.sun.portal.container.ErrorCode;
import com.sun.portal.container.ChannelState;
import com.sun.portal.container.ContainerFactory;
import com.sun.portal.container.ContainerType;
import com.sun.portal.container.EntityID;
import com.sun.portal.container.PortletWindowContextException;
import com.sun.portal.container.WindowRequestReader;
import com.sun.portal.portletcontainer.common.PortletContainerErrorCode;
import com.sun.portal.portletcontainer.driver.portletwindow.PortletWindowInvokerUtils;
import com.sun.portal.portletcontainer.driver.portletwindow.PortletWindowRequestReader;
import com.sun.portal.portletcontainer.driver.portletwindow.PortletWindowURLFactory;
import com.sun.portal.portletcontainer.invoker.InvokerException;
import com.sun.portal.portletcontainer.invoker.WindowErrorCode;
//import com.sun.portal.portletcontainer.invoker.WindowInvoker;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;
import java.util.logging.Logger;

/**
 * PortletWindowInvoker, is dervied from the abstract base class
 * WindowInvoker.
 */

public class PortletWindowInvoker extends WindowInvoker {
    public static final String JAVAX_PORTLET_TITLE = "javax.portlet.title";
    private WindowRequestReader windowRequestReader = null;
    
    private static Logger logger = Logger.getLogger(PortletWindowInvoker.class.getPackage().getName(),
            "PCDLogMessages");
    
    static Map<PortletContainerErrorCode, WindowErrorCode> portletContainerWindowErrorCodeMap = 
		new HashMap<PortletContainerErrorCode, WindowErrorCode>();

	static {
        portletContainerWindowErrorCodeMap.put(
                PortletContainerErrorCode.MISC_ERROR, WindowErrorCode.GENERIC_ERROR);
        portletContainerWindowErrorCodeMap.put(
                PortletContainerErrorCode.PORTLET_UNAVAILABLE, WindowErrorCode.PORTLET_UNAVAILABLE);
        portletContainerWindowErrorCodeMap.put(
                PortletContainerErrorCode.UNSUPPORTED_MODE, WindowErrorCode.INVALID_MODE_CHANGE_REQUEST);
        portletContainerWindowErrorCodeMap.put(
                PortletContainerErrorCode.UNSUPPORTED_STATE, WindowErrorCode.INVALID_WINDOW_STATE_CHANGE_REQUEST);
	}
	
    public void init(ServletContext servletContext, HttpServletRequest request,
            HttpServletResponse response) throws InvokerException {
        super.init(servletContext, request, response);
        this.windowRequestReader = new PortletWindowRequestReader();
    }
    
    /**
     * Implementation of the abstract method defined in the base class.
     * Get the user profile information for the current user.
     * The implementation uses the configured
     * logical to physical user info mapping configured at
     * deployment time to calculate this.
     */
    @SuppressWarnings("rawtypes")
	public Map getUserInfoMap(HttpServletRequest request) throws InvokerException {
        return PortletWindowInvokerUtils.getUserInfoMap(getPortletWindowContext(), getPortletWindowName());
    }
    
    /**
     * Implementation of the abstract method defined in the base class.
     * Gets the container implementation configured to be used
     */
    public Container getContainer() {
    	Container c = ContainerFactory.getContainer(ContainerType.PORTLET_CONTAINER);
    	
    	if(c == null) {
    		c = new PortletContainer();
    	}
    	
    	return c;
    }
    
    /**
     * Implementation of the abstract method defined in the base class.
     * EntityID is represented as
     * <web application name>/<portlet name>/<portletWindow name>
     * <web application name>/<portlet name> is stored in the
     * display profile during
     * deployment time.
     */
    public EntityID getEntityID(HttpServletRequest request) throws InvokerException {
        return PortletWindowInvokerUtils.getEntityID(getPortletWindowContext(), getPortletWindowName());
    }
    
    /**
     * Implementation of the abstract method defined in the base class.
     */
    public ChannelURLFactory getPortletWindowURLFactory(String desktopURLPrefix, HttpServletRequest request) throws InvokerException {
    	return new PortletWindowURLFactory(desktopURLPrefix);
    }
    
    /**
     * Implementation of the abstract method defined in the base class.
     */
    
    public WindowRequestReader getWindowRequestReader() throws InvokerException {
        return windowRequestReader;
    }
    
    /**
     * Implementation of the abstract method defined in the base class.
     */
    public boolean isMarkupSupported(String contentType, String locale, ChannelMode mode, ChannelState state)
    throws InvokerException {
        boolean supported = false;
        @SuppressWarnings("rawtypes")
		List supportedContentTypes;
        try {
            supportedContentTypes = getPortletWindowContext().getMarkupTypes(getPortletWindowName());
            if (ChannelMode.VIEW.equals(mode) &&
            	(supportedContentTypes.contains(contentType) || supportedContentTypes.contains("text/*") || supportedContentTypes.contains("*/*"))) {
                supported = true;
            } else if (ChannelMode.HELP.equals(mode) &&
            		   supportedContentTypes.contains(contentType)) {
                supported = true;
            } else if (ChannelMode.EDIT.equals(mode) &&
            		   supportedContentTypes.contains(contentType)) {
                supported = true;
            }
        } catch (PortletWindowContextException ex) {
            StringWriter sw = new StringWriter();
		    PrintWriter pw = new PrintWriter(sw);
		    ex.printStackTrace(pw);
		    logger.warning(sw.toString());
        }
        return supported;
    }
    
    public String getDefaultTitle() throws InvokerException {
        //First obtain title from portlet window registry
        String title = PortletWindowInvokerUtils.getPortletWindowTitle(
                getPortletWindowContext(), getPortletWindowName());
        //if resource bundle does not contain the title, then
        //obtain the default title from portlet app registry
        if(title == null) {
            logger.log(Level.INFO, "PSPCD_CSPPD0019", getPortletWindowName());
            title = PortletWindowInvokerUtils.getPortletTitle(getPortletWindowName(), getPortletWindowContext());
        }
        return title;
    }

    /**
     * Implementation of the abstract method defined in the base class.
     * Get the list of logical roles that the current user
     * belongs to. The implementation uses the configured
     * logical to physical role mapping configured at
     * deployment time to calculate this.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public List getRoleList(HttpServletRequest request) throws InvokerException {
        //
        // set logical roles only if roleMapping is not empty
        //
        List rolesList = null;
        try {
            Map roleMap = getConfiguredRoleMap();
            if (roleMap != null) {
                List<String> roles = getPortletWindowContext().getRoles();
                if (roles.isEmpty()) {
                    rolesList = Collections.EMPTY_LIST;
                } else {
                    // Before comparing roles convert the roles obtained from WebContainer
                    // and the roles obtained from registry to lowercase.
                    Map lowerCaseRoleMap = convertMapToLowerCase(roleMap);
                    List lowerCaseWebContainerRoles = convertListToLowerCase(roles);
                    rolesList = new ArrayList();
                    Iterator itr = lowerCaseWebContainerRoles.iterator();
                    while(itr.hasNext()) {
                        String webcontainerRole = (String) itr.next();
                        if (lowerCaseRoleMap.containsKey(webcontainerRole)) {
                            String logicalRole = (String) lowerCaseRoleMap.get(webcontainerRole);
                            rolesList.add(logicalRole);
                        }
                    }
                }
            } else {
                rolesList = Collections.EMPTY_LIST;
            }
        } catch (InvokerException pce) {
            throw new InvokerException("PortletWindowInvoker.getRoleList():"
                    + " couldn't check for exists on roleMap collection", pce);
        }
        return rolesList;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private List convertListToLowerCase(List list){
        List lowerCaseKeys = new ArrayList();
        Iterator itr = list.iterator();
        while(itr.hasNext()){
            String role = (String) itr.next();
            lowerCaseKeys.add(role.toLowerCase());
        }
        return lowerCaseKeys;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private Map convertMapToLowerCase(Map map) {
        Map lowerCaseMap = new HashMap();
        Iterator itr = map.entrySet().iterator();
        Map.Entry mapEntry;
        String key;
        while(itr.hasNext()){
            mapEntry = (Map.Entry)itr.next();
            key = (String)mapEntry.getKey();
            lowerCaseMap.put(key.toLowerCase(), mapEntry.getValue());
        }
        return lowerCaseMap;
    }

    @SuppressWarnings("rawtypes")
	private Map getConfiguredRoleMap() throws InvokerException {
        return PortletWindowInvokerUtils.getRoleMap(getPortletWindowContext(), getPortletWindowName());
    }
    
    @Override
    protected ErrorCode getErrorCode(ContentException ex) {
		ErrorCode errorCode = portletContainerWindowErrorCodeMap.get(ex.getErrorCode());
		if(errorCode == null) {
	        return WindowErrorCode.CONTENT_EXCEPTION;
		} else {
			return errorCode;
		}
    }
}

