package csiebug.domain.hibernateImpl;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import csiebug.domain.User;
import csiebug.domain.Role;
import csiebug.domain.Resource;

public class RoleImpl extends BasicObjectImpl implements Role {
	private static final long serialVersionUID = 1L;
	
	private String roleId;
	private String roleName;
	
	private Set<User> users = new HashSet<User>();
	
	private Set<Resource> resources = new HashSet<Resource>();
	
	public void setId(String value) {
		roleId = value;
	}
	
	public String getId() {
		return roleId;
	}
	
	public void setRoleName(String value) {
		roleName = value;
	}
	
	public String getRoleName() {
		return roleName;
	}
	
	public void setAuthorities(Set<User> users) {
		this.users = users;
	}

	public Set<User> getAuthorities() {
		return users;
	}
	
	public void addAuthority(User user) {
		users.add(user);
	}
	
	public void removeAuthority(User user) {
		users.remove(user);
	}
	
	public void setAuthorityResources(Set<Resource> resource) {
		this.resources = resource;
	}

	public Set<Resource> getAuthorityResources() {
		return resources;
	}
	
	public void addAuthorityResource(Resource resource) {
		resources.add(resource);
	}
	
	public void removeAuthorityResource(Resource resource) {
		resources.remove(resource);
	}
	public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        
        if(!(obj instanceof RoleImpl)) {
            return false;
        }
        
        RoleImpl role = (RoleImpl) obj;
        
        return new EqualsBuilder().append(this.roleId, role.getId()).isEquals();
    }
    
    public int hashCode() {
        return new HashCodeBuilder().append(this.roleId).toHashCode();
    }
}
