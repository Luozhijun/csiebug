package csiebug.domain.hibernateImpl;

import csiebug.domain.*;

public class HibernateDomainObjectFactory implements DomainObjectFactory {
	private static final long serialVersionUID = 1L;

	public Resource createResource() {
		return new ResourceImpl();
	}

	public User createUser() {
		return new UserImpl();
	}
	
	public Role createRole() {
		return new RoleImpl();
	}
	
	public AP createAP() {
		return new APImpl();
	}
	
	public Function createFunction() {
		return new FunctionImpl();
	}
	
	public ObjectMethod createObjectMethod() {
		return new ObjectMethodImpl();
	}
	
	public URL createURL() {
		return new URLImpl();
	}
	
	public Code createCode() {
		return new CodeImpl();
	}
	
	public Dashboard createDashboard() {
		return new DashboardImpl();
	}

	public Portlet createPortlet() {
		return new PortletImpl();
	}

	public UserProfile createUserProfile() {
		return new UserProfileImpl();
	}
	
	public UserEmail createUserEmail() {
		return new UserEmailImpl();
	}

	public DashboardPortlet createDashboardPortlet() {
		return new DashboardPortletImpl();
	}
	
	public Cookie createCookie() {
		return new CookieImpl();
	}
	
	public WebservicesChannel createWebservicesChannel() {
		return new WebservicesChannelImpl();
	}
}
