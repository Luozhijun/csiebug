package csiebug.web.webapp;

import com.sun.portal.container.ChannelMode;
import com.sun.portal.container.ChannelState;
import com.sun.portal.portletcontainer.admin.PortletRegistryCache;
import com.sun.portal.portletcontainer.context.registry.PortletRegistryContext;
import com.sun.portal.portletcontainer.context.registry.PortletRegistryException;
import com.sun.portal.portletcontainer.driver.DriverUtil;
//import com.sun.portal.portletcontainer.driver.PortletContent;
import com.sun.portal.portletcontainer.driver.PortletWindowDataImpl;
import com.sun.portal.portletcontainer.driver.PropertiesContext;
import com.sun.portal.portletcontainer.invoker.InvokerException;
import com.sun.portal.portletcontainer.invoker.WindowInvokerConstants;

import com.sun.portal.portletcontainer.invoker.util.InvokerUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.net.URL;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

import csiebug.service.ServiceException;
import csiebug.util.AssertUtility;


public class PortletDriverAction extends BasicAction {
	private static final long serialVersionUID = 1L;
	
	HttpServletRequest request;
	HttpServletResponse response;
	ServletContext context;
	private static String PORTLET_WINDOW = "PortletWindow";
	
	private void init() throws Exception {
		request = getRequest();
		response = getResponse();
		context = getServletContext();
		PropertiesContext.init();
	}
	
	public String main() throws Exception {
		//init
		init();
		
		// Update the cache for each request
        PortletRegistryCache.init();
        // Remove driver related parameters
        DriverUtil.init(request);
        
        String portletWindowName = getPortletWindowName(request);
        PortletRegistryContext portletRegistryContext = DriverUtil.getPortletRegistryContext();
        PortletContent portletContent = getPortletContent(context, request, response, portletWindowName);
        // Show the portlet identified by the key
        StringBuffer buffer = null;
        String driverAction = DriverUtil.getDriverAction(request);
        if(driverAction == null) {
            driverAction = WindowInvokerConstants.RENDER;
        }
        String title = null;
        if(WindowInvokerConstants.ACTION.equals(driverAction)) {
            URL url = executeProcessAction(request, portletContent);
            try {
                if(url != null) {
                    response.sendRedirect(url.toString());
                } else {
                    response.sendRedirect(request.getRequestURL().toString());
                }
            } catch (IOException ioe) {
                throw new InvokerException("Failed during sendRedirect", ioe);
            }
        } else if(WindowInvokerConstants.RENDER.equals(driverAction)) {
            buffer = portletContent.getContent();
            title = portletContent.getTitle();
        } else if(WindowInvokerConstants.RESOURCE.equals(driverAction)) {
            // Invoke Resource
            portletContent.getResources();
        }
        
        ChannelMode portletWindowMode = DriverUtil.getPortletWindowModeOfPortletWindow(request, portletWindowName);
        setPortletWindowData(request, portletRegistryContext, portletWindowName, title, portletWindowMode, buffer);
        InvokerUtil.setResponseProperties(request, response, portletContent.getResponseProperties());
        RequestDispatcher rd = context.getRequestDispatcher("/single-portlet.jsp");
        rd.forward(request, response);
        InvokerUtil.clearResponseProperties(portletContent.getResponseProperties());
		
        //頁面控制項需要的資料
		makeControl();
		
		return FORMLOAD;
	}
	
	//畫面控制項函數區
	private void makeControl() {
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
		
	}
		
	//畫面控制項函數區結束
	
	//邏輯函數區
	//頁面動作所對應的處理method,business logic,請歸類到下面
	
	private URL executeProcessAction(HttpServletRequest request, PortletContent portletContent) throws InvokerException {
        String portletWindowName = DriverUtil.getPortletWindowFromRequest(request);
        ChannelMode portletWindowMode = DriverUtil.getPortletWindowModeOfPortletWindow(request, portletWindowName);
        ChannelState portletWindowState = DriverUtil.getPortletWindowStateOfPortletWindow(request, portletWindowName);
        portletContent.setPortletWindowName(portletWindowName);
        portletContent.setPortletWindowMode(portletWindowMode);
        portletContent.setPortletWindowState(portletWindowState);
        URL url = portletContent.executeAction();
        return url;
    }
	
	private PortletContent getPortletContent(ServletContext context, HttpServletRequest request, HttpServletResponse response,
            String portletWindowName) throws Exception {
        PortletContent portletContent = new PortletContent(context, request, response);
        portletContent.setPortletWindowName(portletWindowName);
        ChannelMode portletWindowMode = DriverUtil.getPortletWindowModeOfPortletWindow(request, portletWindowName);
        ChannelState portletWindowState = DriverUtil.getPortletWindowStateOfPortletWindow(request, portletWindowName);
        portletContent.setPortletWindowMode(portletWindowMode);
        portletContent.setPortletWindowState(portletWindowState);
        return portletContent;
    }
	
	private String getPortletWindowName(HttpServletRequest request) {
        String value = DriverUtil.getPortletWindowFromRequest(request);
        if(value == null) {
            value = getValue(request, WindowInvokerConstants.PORTLET_WINDOW_KEY);
        } else {
            setValue(request, WindowInvokerConstants.PORTLET_WINDOW_KEY, value);
        }
        return value;
    }
    
    private String getValue(HttpServletRequest request, String name) {
        HttpSession session = request.getSession(true);
        return (String)session.getAttribute(name);
    }
    
    private void setValue(HttpServletRequest request, String name, String value) {
        HttpSession session = request.getSession(true);
        session.setAttribute(name, value);
    }
    
    private void setPortletWindowData(HttpServletRequest request, PortletRegistryContext portletRegistryContext, String portletWindowName, String title,
            ChannelMode mode, StringBuffer content) throws PortletRegistryException {
        PortletWindowDataImpl portletWindowDataImpl = new PortletWindowDataImpl();
        portletWindowDataImpl.init(request, portletRegistryContext, portletWindowName);
        portletWindowDataImpl.setContent(content);
        portletWindowDataImpl.setTitle(title);
        portletWindowDataImpl.setCurrentMode(mode);
        portletWindowDataImpl.setCurrentWindowState(ChannelState.NORMAL);
        request.setAttribute(PORTLET_WINDOW, portletWindowDataImpl);
    }
    
  //邏輯函數區結束
}
