package csiebug.web.webapp;

//import com.sun.portal.portletcontainer.driver.PortletContent;
import com.sun.portal.portletcontainer.invoker.InvokerException;
//import com.sun.portal.portletcontainer.invoker.WindowInvoker;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * RemotePortletContent is responsible for getting the portlet content from
 * remote portlet and execting action on these portlet. It delegates the calls to
 * WSRPWindowProvider.
 */
public class RemotePortletContent extends PortletContent{

    static final long serialVersionUID = 2L;
    public RemotePortletContent(ServletContext context, HttpServletRequest request,
            HttpServletResponse response) throws InvokerException {
        super(context, request, response);
    }

    protected WindowInvoker getWindowInvoker(ServletContext context,
            HttpServletRequest request,
            HttpServletResponse response)
            throws InvokerException  {
        WindowInvoker windowInvoker = null;
        try{
            @SuppressWarnings("rawtypes")
			Class invokerClass = Class.forName("com.sun.portal.wsrp.consumer.wsrpinvoker.WSRPWindowProvider");
            if(invokerClass!=null){
                windowInvoker = (WindowInvoker)(invokerClass.newInstance());
                windowInvoker.init(context, request, response);
            }
        }catch(Exception e){
            throw new InvokerException("WSRP is not available");
        }
        return windowInvoker;
    }


}

