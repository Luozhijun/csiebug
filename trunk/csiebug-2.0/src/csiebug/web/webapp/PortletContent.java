package csiebug.web.webapp;

import com.sun.portal.container.ChannelMode;
import com.sun.portal.container.ChannelState;
import com.sun.portal.container.EntityID;
import com.sun.portal.portletcontainer.invoker.InvokerException;
//import com.sun.portal.portletcontainer.driver.portletwindow.PortletWindowInvoker;
import com.sun.portal.portletcontainer.invoker.ResponseProperties;
//import com.sun.portal.portletcontainer.invoker.WindowInvoker;
import java.net.URL;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * PortletContent is responsible for getting the portlet content and execting action
 * on the portlet. It delegates the calls to PortletWindowInvoker.
 */
public class PortletContent {
    
    private HttpServletRequest request;
    private HttpServletResponse response;
    private WindowInvoker windowInvoker;
    
    public PortletContent(ServletContext context, HttpServletRequest request,
            HttpServletResponse response) throws InvokerException {
        this.request = request;
        this.response = response;
        this.windowInvoker = getWindowInvoker(context, request, response);
    }
    
    public void setPortletWindowMode(ChannelMode portletWindowMode) {
        windowInvoker.setPortletWindowMode(portletWindowMode);
    }
    
    public void setPortletWindowName(String portletWindowName) {
        windowInvoker.setPortletWindowName(portletWindowName);
    }
    
    public void setPortletWindowState(ChannelState portletWindowState) {
        windowInvoker.setPortletWindowState(portletWindowState);
    }
    
    public StringBuffer getContent() throws InvokerException {
        return windowInvoker.render(request,response);
    }
    
    public String getTitle() throws InvokerException {
        return windowInvoker.getTitle();
    }
    
    public ResponseProperties getResponseProperties() {
        return windowInvoker.getResponseProperties();
    }
    
    public String getDefaultTitle() throws InvokerException {
        return windowInvoker.getDefaultTitle();
    }
    
    public URL executeAction() throws InvokerException {
        return windowInvoker.processAction(request,response);
    }
    
    public void getResources() throws InvokerException {
       windowInvoker.getResources(request, response);
    }
    
    protected WindowInvoker getWindowInvoker(ServletContext context, 
            HttpServletRequest request,
            HttpServletResponse response) 
            throws InvokerException  {
        WindowInvoker pwInvoker = new PortletWindowInvoker();
        pwInvoker.init(context, request, response); 
        return pwInvoker;
    }

	public List<EntityID> getEventUpdatedPortlets() {
		return windowInvoker.getEventUpdatedPortlets();
	}
	
}
