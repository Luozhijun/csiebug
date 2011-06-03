package csiebug.web.webapp;

import com.sun.portal.container.ChannelMode;
import com.sun.portal.portletcontainer.admin.PortletRegistryCache;
import com.sun.portal.portletcontainer.invoker.InvokerException;
import com.sun.portal.portletcontainer.invoker.WindowInvokerConstants;
import com.sun.portal.portletcontainer.driver.PropertiesContext;
import com.sun.portal.portletcontainer.driver.DriverUtil;
import com.sun.portal.portletcontainer.driver.remote.RemotePortletContent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpSession;

import csiebug.service.ServiceException;
import csiebug.util.AssertUtility;

public class PortletRemoteTCKAction extends BasicAction {
	private static final long serialVersionUID = 1L;
	
	HttpServletRequest request;
	HttpServletResponse response;
	private ServletContext context;
	private static String PORTLET_NAME_KEY_IN_TCK = "portletName";
	@SuppressWarnings("rawtypes")
	private List portletNames;
	
	private void init() throws Exception {
		request = getRequest();
		response = getResponse();
		context = getServletContext();
		PropertiesContext.init();
        PortletRegistryCache.init();
	}
    
    public String main() throws Exception {
    	//init
		init();
		
		DriverUtil.init(request);
        try {
            response.setContentType("text/html;charset=UTF-8");
            String portletWindowName = getPortletWindowName(request);
            StringBuffer buffer = null;
            if(portletWindowName == null){
                // Get the portletName and render it
                @SuppressWarnings("rawtypes")
				List portletNamesList = getSelectedPortletWindows(request);
                buffer = renderPortlets(context, request, response, portletNamesList);
            } else {
                String driverAction = DriverUtil.getDriverAction(request);
                if(driverAction == null) {
                    driverAction = WindowInvokerConstants.RENDER;
                }
                if(WindowInvokerConstants.ACTION.equals(driverAction)) {
                    RemotePortletContent portletContent = getPortletContent(context,
                            request, response, portletWindowName);
                    URL url = portletContent.executeAction();
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
                    // Render all portlets
                    @SuppressWarnings("rawtypes")
					List portletNamesList = getSelectedPortletWindows(request);
                    buffer = renderPortlets(context, request, response, portletNamesList);
                }
            }
            PrintWriter out = response.getWriter();
            out.println(buffer);
            out.close();
        } catch(Exception e) {
            System.out.println(e);
        }
		
		//頁面控制項需要的資料
		makeControl();
		
		//若沒有導到特定處理頁面,則預設都導到formLoad頁
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
	
	private String getPortletWindowName(HttpServletRequest request) {
        String value = DriverUtil.getPortletWindowFromRequest(request);
        if(value == null) {
            value = getValue(request, WindowInvokerConstants.PORTLET_WINDOW_KEY);
        } else {
            setValue(request, WindowInvokerConstants.PORTLET_WINDOW_KEY, value);
        }
        return value;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private List processNames(String[] portletNames) {
        // The portletName in the TCK request is of the form
        // webappname/portletname. Portlet container expects it in the form
        // webappname.portletname. Window (channel) name for remote TCK portlet
        // starts with "wsrp."
        int size = portletNames.length;
        List portletNamesList = new ArrayList();
        for(int i=0; i<size; i++) {
            portletNamesList.add("wsrp." + portletNames[i].replace('/', '.'));
        }
        System.out.println("Processed name for remote portlets ==> " + portletNamesList);
        return portletNamesList;
    }
    
    private ChannelMode getPortletWindowMode(HttpServletRequest request) {
        String value = DriverUtil.getPortletWindowFromRequest(request);
        ChannelMode portletWindowMode = DriverUtil.getPortletWindowModeOfPortletWindow(request, value);
        if(portletWindowMode == null) {
            return ChannelMode.VIEW;
        }
        return portletWindowMode;
    }
    
    private String getValue(HttpServletRequest request, String name) {
        HttpSession session = request.getSession(true);
        return (String)session.getAttribute(name);
    }
    
    private void setValue(HttpServletRequest request, String name, String value) {
        HttpSession session = request.getSession(true);
        session.setAttribute(name, value);
    }
    
    private RemotePortletContent getPortletContent(ServletContext context,
            HttpServletRequest request, HttpServletResponse response,
            String portletWindowName) throws Exception {
        RemotePortletContent portletContent = new RemotePortletContent(context,
                request, response);
        portletContent.setPortletWindowName(portletWindowName);
        ChannelMode portletWindowMode = getPortletWindowMode(request);
        portletContent.setPortletWindowMode(portletWindowMode);
        return portletContent;
    }
    
    @SuppressWarnings("rawtypes")
	public List getSelectedPortletWindows(HttpServletRequest request) {
        String[] portletWindows = request.getParameterValues(PORTLET_NAME_KEY_IN_TCK);
        List selected = null;
        if (portletWindows != null && portletWindows.length != 0) {
            selected = processNames(portletWindows);
            portletNames = selected;
        } else {
            selected = portletNames;
        }
        if (selected == null) {
            selected = Collections.EMPTY_LIST;
        }
        return selected;
    }
    
    private StringBuffer renderPortlets(ServletContext context,
            HttpServletRequest request, HttpServletResponse response,
            @SuppressWarnings("rawtypes") List portletNamesList) throws Exception {
        int numPortletWindows = portletNamesList.size();
        String portletWindowName;
        StringBuffer buffer = new StringBuffer();
        for(int i = 0; i < numPortletWindows; i++) {
            portletWindowName = (String)portletNamesList.get(i);
            RemotePortletContent portletContent = getPortletContent(context,
                    request, response, portletWindowName);
            buffer.append(portletContent.getContent());
        }
        return buffer;
    }
    
	//邏輯函數區結束
}
