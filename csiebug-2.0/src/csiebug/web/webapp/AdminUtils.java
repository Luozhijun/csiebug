package csiebug.web.webapp;

import com.sun.portal.portletcontainer.admin.registry.PortletRegistryConstants;
import com.sun.portal.portletcontainer.context.registry.PortletRegistryException;
import com.sun.portal.portletcontainer.driver.admin.AdminConstants;
import com.sun.portal.portletcontainer.driver.admin.PortletAdminData;
import com.sun.portal.portletcontainer.driver.admin.PortletAdminDataFactory;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * AdminUtils is a utility class for admin UI related tasks
 */
public class AdminUtils {
    
    private static Logger logger = Logger.getLogger(AdminUtils.class.getPackage().getName(), "PCDLogMessages");
    
    protected static HttpSession getClearedSession(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.removeAttribute(AdminConstants.PORTLETS_ATTRIBUTE);
        session.removeAttribute(AdminConstants.PORTLET_APPLICATIONS_ATTRIBUTE);
        session.removeAttribute(AdminConstants.PORTLET_WINDOWS_ATTRIBUTE);
        
        session.removeAttribute(AdminConstants.SHOW_WINDOW_ATTRIBUTE);
        session.removeAttribute(AdminConstants.HIDE_WINDOW_ATTRIBUTE);
        session.removeAttribute(AdminConstants.THICK_WINDOW_ATTRIBUTE);
        session.removeAttribute(AdminConstants.THIN_WINDOW_ATTRIBUTE);
        
        session.removeAttribute(AdminConstants.DEPLOYMENT_SUCCEEDED_ATTRIBUTE);
        session.removeAttribute(AdminConstants.DEPLOYMENT_FAILED_ATTRIBUTE);
        session.removeAttribute(AdminConstants.UNDEPLOYMENT_SUCCEEDED_ATTRIBUTE);
        session.removeAttribute(AdminConstants.UNDEPLOYMENT_FAILED_ATTRIBUTE);
        session.removeAttribute(AdminConstants.CREATION_SUCCEEDED_ATTRIBUTE);
        session.removeAttribute(AdminConstants.CREATION_FAILED_ATTRIBUTE);
        session.removeAttribute(AdminConstants.MODIFY_SUCCEEDED_ATTRIBUTE);
        session.removeAttribute(AdminConstants.MODIFY_FAILED_ATTRIBUTE);
        session.removeAttribute(AdminConstants.NO_WINDOW_DATA_ATTRIBUTE);
        session.removeAttribute(AdminConstants.SELECTED_PORTLET_WINDOW_ATTRIBUTE);
        return session;
    }
    
    protected static void refreshList(HttpServletRequest request) {
        try {
            PortletAdminData portletAdminData = PortletAdminDataFactory.getPortletAdminData();
            HttpSession session = request.getSession(true);
            setAttributes(session, portletAdminData);
        } catch (PortletRegistryException pre) {
            logger.log(Level.SEVERE, "PSPCD_CSPPD0038", pre);
        }
    }
    
    protected static void setAttributes(HttpSession session, PortletAdminData portletAdminData) {
        session.removeAttribute(AdminConstants.PORTLETS_ATTRIBUTE);
        session.removeAttribute(AdminConstants.PORTLET_APPLICATIONS_ATTRIBUTE);
        session.removeAttribute(AdminConstants.PORTLET_WINDOWS_ATTRIBUTE);
        
        session.setAttribute(AdminConstants.PORTLETS_ATTRIBUTE, portletAdminData.getPortletNames());
        session.setAttribute(AdminConstants.PORTLET_APPLICATIONS_ATTRIBUTE, portletAdminData.getPortletApplicationNames());
        session.setAttribute(AdminConstants.PORTLET_WINDOWS_ATTRIBUTE, portletAdminData.getPortletWindowNames());
    }
    
    protected static void setPortletWindowAttributes(HttpSession session, PortletAdminData portletAdminData, String portletWindowName) throws Exception {
        // If portlet window name is null, get the name from the portlet window list
        if(portletWindowName == null) {
            @SuppressWarnings("rawtypes")
			List list = portletAdminData.getPortletWindowNames();
            if(list != null) {
                portletWindowName = (String)list.get(0);
            }
        }
        if(portletWindowName != null) {
            session.removeAttribute(AdminConstants.SHOW_WINDOW_ATTRIBUTE);
            session.removeAttribute(AdminConstants.HIDE_WINDOW_ATTRIBUTE);
            session.removeAttribute(AdminConstants.THICK_WINDOW_ATTRIBUTE);
            session.removeAttribute(AdminConstants.THIN_WINDOW_ATTRIBUTE);
            
            boolean visible = portletAdminData.isVisible(portletWindowName);
            String width = portletAdminData.getWidth(portletWindowName);
            if(visible) {
                session.setAttribute(AdminConstants.SHOW_WINDOW_ATTRIBUTE, "checked");
                session.setAttribute(AdminConstants.HIDE_WINDOW_ATTRIBUTE, "");
            } else {
                session.setAttribute(AdminConstants.HIDE_WINDOW_ATTRIBUTE, "checked");
                session.setAttribute(AdminConstants.SHOW_WINDOW_ATTRIBUTE, "");
            }
            if(PortletRegistryConstants.WIDTH_THICK.equals(width)) {
                session.setAttribute(AdminConstants.THICK_WINDOW_ATTRIBUTE, "selected");
                session.setAttribute(AdminConstants.THIN_WINDOW_ATTRIBUTE, "");
            } else {
                session.setAttribute(AdminConstants.THIN_WINDOW_ATTRIBUTE, "selected");
                session.setAttribute(AdminConstants.THICK_WINDOW_ATTRIBUTE, "");
            }
        }
    }
}

