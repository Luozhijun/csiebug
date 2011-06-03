package csiebug.domain;

import java.util.Set;

public interface Role extends BasicObject {
	void setId(String value);
	String getId();
	void setRoleName(String value);
	String getRoleName();
	void setAuthorities(Set<User> users);
	Set<User> getAuthorities();
	void addAuthority(User user);
	void removeAuthority(User user);
	void setAuthorityResources(Set<Resource> resource);
	Set<Resource> getAuthorityResources();
	void addAuthorityResource(Resource resource);
	void removeAuthorityResource(Resource resource);
}
