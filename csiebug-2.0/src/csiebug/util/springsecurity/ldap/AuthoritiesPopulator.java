package csiebug.util.springsecurity.ldap;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.ldap.LdapAuthoritiesPopulator;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;

import csiebug.util.AssertUtility;

/**
 * ldap只做認證
 * 由這邊去取userDetailsService的角色來做授權(自己系統的db)
 * @author George_Tsai
 * @version 2009/11/19
 */
public class AuthoritiesPopulator implements LdapAuthoritiesPopulator {
	private UserDetailsService userDetailsService;
	
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	public GrantedAuthority[] getGrantedAuthorities(DirContextOperations userData, String username) {
		AssertUtility.notNullAndNotSpace(username);
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		return userDetails.getAuthorities();
	}
}
