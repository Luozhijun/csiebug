package csiebug.domain;

import java.io.Serializable;

public interface DomainObjectFactory extends Serializable {
	AP createAP();
	Code createCode();
	Dashboard createDashboard();
	DashboardPortlet createDashboardPortlet();
	Function createFunction();
	ObjectMethod createObjectMethod();
	Portlet createPortlet();
	Resource createResource();
	Role createRole();
	URL createURL();
	User createUser();
	UserProfile createUserProfile();
	UserEmail createUserEmail();
	Cookie createCookie();
	WebservicesChannel createWebservicesChannel();
}
