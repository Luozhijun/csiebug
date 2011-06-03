package csiebug.web.webapp;

import com.sun.portal.container.PortletType;
import com.sun.portal.portletcontainer.context.registry.PortletRegistryContext;
import com.sun.portal.portletcontainer.context.registry.PortletRegistryException;
import com.sun.portal.portletcontainer.driver.DesktopConstants;
import com.sun.portal.portletcontainer.driver.DriverUtil;
//import com.sun.portal.portletcontainer.driver.PortletContent;
import com.sun.portal.portletcontainer.driver.PortletWindowData;
//import com.sun.portal.portletcontainer.driver.remote.RemotePortletContent;
import com.sun.portal.portletcontainer.driver.remote.RemotePortletWindowDataImpl;
import com.sun.portal.portletcontainer.invoker.InvokerException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PortletRemoteDesktopAction extends PortletDesktopAction {
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("rawtypes")
	protected List getVisiblePortletWindows(PortletRegistryContext portletRegistryContext) throws InvokerException {
        List visiblePortletWindows = null;
        try {
            visiblePortletWindows = portletRegistryContext.getVisiblePortletWindows(PortletType.REMOTE);
        } catch (PortletRegistryException pre) {
            visiblePortletWindows = Collections.EMPTY_LIST;
            throw new InvokerException("Cannot get Portlet List", pre);
        }
        return visiblePortletWindows;
    }
    
    protected PortletContent getPortletContentObject(ServletContext context, 
            HttpServletRequest request, HttpServletResponse response) throws InvokerException{
        return new RemotePortletContent(context, request, response);
    }    
    
    protected PortletWindowData getPortletWindowDataObject(HttpServletRequest request, 
            @SuppressWarnings("rawtypes") Map portletContents, PortletRegistryContext portletRegistryContext, 
            String portletWindowName) throws PortletRegistryException{
        
        RemotePortletWindowDataImpl portletWindowData = new RemotePortletWindowDataImpl();
        @SuppressWarnings("rawtypes")
		Map portletContentMap = (Map)portletContents.get(portletWindowName);        
        portletWindowData.init(request, portletRegistryContext, portletWindowName);        
        portletWindowData.setContent((StringBuffer)portletContentMap.get(DesktopConstants.PORTLET_CONTENT));
        portletWindowData.setTitle((String)portletContentMap.get(DesktopConstants.PORTLET_TITLE));
        portletWindowData.setCurrentMode(DriverUtil.getPortletWindowModeOfPortletWindow(request, portletWindowName));
        portletWindowData.setCurrentWindowState(getCurrentPortletWindowState(request, portletWindowName));
        
        return portletWindowData;
    }     
    
   protected String getPresentationURI(){
        return "/wsrpportlet.jsp";
    }    
}
