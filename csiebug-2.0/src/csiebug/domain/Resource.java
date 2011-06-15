package csiebug.domain;

import java.util.Set;


/**
 * 
 * @author George_Tsai
 * @version 2009/6/26
 *
 */
public interface Resource extends BasicObject {
	void setId(String id);
	String getId();
	void setResourceType(ResourceType type);
	ResourceType getResourceType();
	void setAuthorityResources(Set<Role> roles);
	Set<Role> getAuthorityResources();
	void addAuthorityResource(Role role);
	void removeAuthorityResource(Role role);
	void setUserResources(Set<User> users);
	Set<User> getUserResources();
	void addUserResource(User user);
	void removeUserResource(User user);
}
