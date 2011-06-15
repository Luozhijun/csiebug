package csiebug.domain.hibernateImpl;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import csiebug.domain.Resource;
import csiebug.domain.ResourceType;
import csiebug.domain.Role;
import csiebug.domain.User;

/**
 * 
 * @author George_Tsai
 * @version 2009/6/26
 *
 */
public class ResourceImpl extends BasicObjectImpl implements Resource {
	private static final long serialVersionUID = 1L;
	
	private String resourceId;
//	private String resourceType;
	private ResourceType resourceType;
	
	private Set<Role> roles = new HashSet<Role>();
	private Set<User> users = new HashSet<User>();
	
	public void setId(String id) {
		this.resourceId = id;
	}
	public String getId() {
		return resourceId;
	}
	public void setResourceType(ResourceType type) {
		this.resourceType = type;
	}
	public ResourceType getResourceType() {
		return resourceType;
	}
	public void setAuthorityResources(Set<Role> roles) {
		this.roles = roles;
	}
	public Set<Role> getAuthorityResources() {
		return this.roles;
	}
	public void addAuthorityResource(Role role) {
		roles.add(role);
	}
	public void removeAuthorityResource(Role role) {
		roles.remove(role);
	}
	public void setUserResources(Set<User> users) {
		this.users = users;
	}
	public Set<User> getUserResources() {
		return this.users;
	}
	public void addUserResource(User user) {
		users.add(user);
	}
	public void removeUserResource(User user) {
		users.remove(user);
	}
	public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        
        if(!(obj instanceof ResourceImpl)) {
            return false;
        }
        
        ResourceImpl resource = (ResourceImpl) obj;
        
        return new EqualsBuilder().append(this.resourceId, resource.getId()).isEquals();
    }
    
    public int hashCode() {
        return new HashCodeBuilder().append(this.resourceId).toHashCode();
    }
}
