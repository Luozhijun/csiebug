package csiebug.web.webapp;

import com.sun.portal.container.ChannelURLType;
import com.sun.portal.container.ContainerConstants;
import com.sun.portal.container.ContainerException;
import com.sun.portal.container.ContainerRequest;
import com.sun.portal.container.ContentException;
import com.sun.portal.container.EntityID;
import com.sun.portal.container.ExecuteActionRequest;
import com.sun.portal.container.ExecuteActionResponse;
import com.sun.portal.container.ExecuteEventRequest;
import com.sun.portal.container.ExecuteEventResponse;
import com.sun.portal.container.GetMarkupRequest;
import com.sun.portal.container.GetMarkupResponse;
import com.sun.portal.container.GetResourceRequest;
import com.sun.portal.container.GetResourceResponse;
import com.sun.portal.container.PortletWindowContext;
import com.sun.portal.container.impl.AbstractContainer;
import com.sun.portal.container.service.ServiceManager;

public class PortletContainer extends AbstractContainer implements com.sun.portal.container.Container {

	@Override
	public void executeAction(ExecuteActionRequest arg0,
			ExecuteActionResponse arg1, ChannelURLType arg2)
			throws ContainerException, ContentException {
		
	}

	@Override
	public void executeEvent(ExecuteEventRequest arg0, ExecuteEventResponse arg1)
			throws ContainerException, ContentException {
		
	}

	@Override
	public void getMarkup(GetMarkupRequest arg0, GetMarkupResponse arg1)
			throws ContainerException, ContentException {
		
	}

	@Override
	public void getResources(GetResourceRequest arg0, GetResourceResponse arg1)
			throws ContainerException, ContentException {
		
	}
	
	/**
     * Returns the Service for the serviceName
     * @param serviceName the name of the service
     * @return the Service for the serviceName
     */
    protected Object getService(String serviceName) {
        return ServiceManager.getServiceManager().getService(serviceName);
    }
    
    /**
     * Determine if the entity is the target of the request.
     */
    protected boolean getIsTarget(ContainerRequest containerRequest, EntityID entityId) {
        PortletWindowContext portletWindowContext = containerRequest.getPortletWindowContext();
        String targetValue = (String) portletWindowContext.getProperty(
                ContainerConstants.ENTITY_TARGET + entityId);
        if (targetValue != null) {
            Boolean target = Boolean.valueOf(targetValue);
            return target.booleanValue();
        } else {
            return true; // Do not cache the content
        }
    }   
    
    /**
     * Mark the current entity as the target or not the target based
     * on isTarget parameter
     */
    protected void setIsTarget(ContainerRequest containerRequest, EntityID entityId, boolean isTarget) {
        PortletWindowContext portletWindowContext = containerRequest.getPortletWindowContext();
        portletWindowContext.setProperty(ContainerConstants.ENTITY_TARGET + entityId, 
                String.valueOf(isTarget));
    }  
}
