package csiebug.web.webapp;

import com.sun.portal.container.service.Service;
import com.sun.portal.container.service.ServiceManager;
import com.sun.portal.container.service.coordination.ContainerEventService;
import com.sun.portal.portletcontainer.admin.PortletRegistryCache;
import com.sun.portal.portletcontainer.driver.PropertiesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;
import javax.xml.namespace.QName;

import csiebug.service.ServiceException;
import csiebug.util.AssertUtility;

public class PortletLoginAction extends BasicAction {
	private static final long serialVersionUID = 1L;
	
	HttpServletRequest request;
	HttpServletResponse response;
	ServletContext context;
    
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
		
		QName login = new QName("urn:oasis:names:tc:wsrp:v2:types", "login");
        getContainerEventService().setEvent(login, null, request, response);
        RequestDispatcher rd = request.getRequestDispatcher("/dt");
        rd.forward(request, response);
		
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
	
	private ContainerEventService getContainerEventService() {
        return (ContainerEventService)ServiceManager.getServiceManager().getService(Service.CONTAINER_EVENT_SERVICE);
    }
	
	//邏輯函數區結束
}
