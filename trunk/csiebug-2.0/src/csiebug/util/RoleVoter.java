package csiebug.util;

import java.lang.reflect.Method;
//import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.aop.framework.ReflectiveMethodInvocation;
//spring security 2.x
import org.springframework.security.Authentication;
import org.springframework.security.ConfigAttribute;
import org.springframework.security.vote.AccessDecisionVoter;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.intercept.web.FilterInvocation;
//spring security3.x
//import org.springframework.security.core.Authentication;
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.web.FilterInvocation;
//import org.springframework.security.access.AccessDecisionVoter;

import csiebug.domain.Resource;
import csiebug.domain.DomainObjectFactory;
import csiebug.domain.ResourceType;
import csiebug.domain.Role;
import csiebug.dao.ResourceDAO;

/**
 * 根據角色授權
 * 此程式被呼叫是在當被保護的資源(URL或程式的method)被存取之前
 * 在spring security設定檔中被排除的資源不會被此程式檢查授權
 * @author George_Tsai
 * @version 2009/7/2
 */

public class RoleVoter implements AccessDecisionVoter {
	private String rolePrefix = "ROLE_";
	private ResourceDAO resourceDAO;
	private DomainObjectFactory domainObjectFactory;
	private HashMap<String, String> filterVariable;
	
	public boolean supports(ConfigAttribute attribute) {
		AssertUtility.notNull(attribute);
		return ((attribute.getAttribute() != null) && attribute.getAttribute().startsWith(getRolePrefix()));
	}

	public boolean supports(@SuppressWarnings("rawtypes") Class arg0) {
		return true;
	}

//	spring security 2.x
	public int vote(Authentication authentication, Object object, ConfigAttributeDefinition config) {
		AssertUtility.notNull(authentication);
		AssertUtility.notNull(object);
		
		int result = AccessDecisionVoter.ACCESS_ABSTAIN;
		
		GrantedAuthority[] authorities = authentication.getAuthorities();
        
        //如果使用者是ROLE_ANONYMOUS時,無權限存取
        if(authorities.length == 1 && authorities[0].getAuthority().equalsIgnoreCase("ROLE_ANONYMOUS")) {
        	if(object.toString().startsWith("FilterInvocation")) {
        		if(((FilterInvocation)object).getRequestUrl().startsWith("/index")) {
        			if(filterVariable != null && filterVariable.get("step") != null) {
            			if(filterVariable.get("step").equals("3")) {
    	    				filterVariable.remove("step");
    	    				filterVariable.put("step", "4");
    	    			} else {
    	    				resetFilterVariable();
    	    			}
        			}
            		
            		return AccessDecisionVoter.ACCESS_GRANTED;
        		} else if(((FilterInvocation)object).getRequestUrl().equals("/")) {
        			return AccessDecisionVoter.ACCESS_GRANTED;
        		}
        	} else {
    			if(filterVariable != null) {
    				if(object.toString().startsWith("java.lang.Object")) {
    					resetFilterVariable();
    				}
    				
    				if(filterVariable.get("step") != null) {
		    			if(filterVariable.get("step").equals("1")) {
		    				filterVariable.remove("step");
		    				filterVariable.put("step", "2");
		    			} else {
		    				resetFilterVariable();
		    			}
    				}
    			}
    			
    			return AccessDecisionVoter.ACCESS_DENIED;
    		}
        } else {
        //使用者擁有某些角色時,則需要判斷此資源是否可以被此使用者存取
        	try {
        		//查出使用者欲存取的資源,可被哪些角色存取
        		List<Resource> list = null;
        		Resource authorityResource = domainObjectFactory.createResource(); 
        		
        		if(object.toString().startsWith("FilterInvocation")) {
        			authorityResource.setId(((FilterInvocation)object).getRequestUrl());
        			authorityResource.setResourceType(ResourceType.URL);
        			
        			list = resourceDAO.searchWithPatternMatch(authorityResource);
        		} else if(object.toString().startsWith("ReflectiveMethodInvocation")) {
        			Method method = ((ReflectiveMethodInvocation)object).getMethod();
        			authorityResource.setId(method.getDeclaringClass().getName() + "." + method.getName());
        			authorityResource.setResourceType(ResourceType.OBJECT_METHOD);
        			
        			list = resourceDAO.search(authorityResource);
        		}
        		
	        	//沒有此資源,表示也不受權限控管了
	        	if(list == null || list.size() == 0) {
	        		if(filterVariable != null && filterVariable.get("step") != null) {
	        			if(filterVariable.get("step").equals("4")) {
		    				filterVariable.remove("step");
		    				filterVariable.put("step", "5");
		    			} else if(filterVariable.get("step").equals("7")) {
		    				filterVariable.remove("step");
		    				filterVariable.put("step", "8");
		    			} else {
		    				resetFilterVariable();
		    			}
	    			}
	        		
	        		return AccessDecisionVoter.ACCESS_GRANTED;
	        	} else {
	        		for(int i = 0; i < list.size(); i++) {
		        		Set<Role> roles = list.get(i).getAuthorityResources();
		        		Iterator<Role> iterator = roles.iterator();
		        		
		        		//若此資源沒有特別設定可被存取的角色,則表示只要登入系統就可使用
		        		if(roles.size() == 0) {
		        			return AccessDecisionVoter.ACCESS_GRANTED;
		        		} else {
		        		//反之,則使用者擁有的角色,至少有一個必須是可存取此資源的角色才有權限存取
		        			result = AccessDecisionVoter.ACCESS_DENIED;
			        		
			        		while(iterator.hasNext()) {
			        			Role role = iterator.next();
			        			for(int j = 0; j < authorities.length; j++) {
			        				if(authorities[j].getAuthority().equalsIgnoreCase(role.getId())) {
			        					return AccessDecisionVoter.ACCESS_GRANTED;
			        				}
			        			}
			        		}
		        		}
	        		}
	        	}
        	} catch(Exception ex) {
        		WebUtility webutil = new WebUtility();
        		webutil.writeErrorLog(ex);
        	}
        }
        
        return result;
	}
	
	public void setRolePrefix(String rolePrefix) {
		this.rolePrefix = rolePrefix;
	}

	public String getRolePrefix() {
		return rolePrefix;
	}

	public void setResourceDAO(ResourceDAO resourceDAO) {
		this.resourceDAO = resourceDAO;
	}

	public void setDomainObjectFactory(DomainObjectFactory domainObjectFactory) {
		this.domainObjectFactory = domainObjectFactory;
	}
	
	public void setFilterVariable(HashMap<String, String> filterVariable) {
		this.filterVariable = filterVariable;
	}
	
	private void resetFilterVariable() {
		filterVariable.remove("URLForRemeberMe");
		filterVariable.remove("step");
	}
	
//	spring security 2.x
//	@SuppressWarnings("unchecked")
//	public int vote(Authentication authentication, Object object,
//			Collection<ConfigAttribute> arg2) {
//		int result = AccessDecisionVoter.ACCESS_ABSTAIN;
//		Collection<GrantedAuthority> authorities = authentication.getAuthorities();
//        
//        //如果使用者是ROLE_ANONYMOUS時,無權限存取
//        if(authorities.size() == 1 && authorities.iterator().next().getAuthority().equalsIgnoreCase("ROLE_ANONYMOUS")) {
//        	return AccessDecisionVoter.ACCESS_DENIED;
//        } else {
//        //使用者擁有某些角色時,則需要判斷此資源是否可以被此使用者存取
//        	try {
//        		//查出使用者欲存取的資源,可被哪些角色存取
//				List<Resource> list = null;
//        		Resource authorityResource = domainObjectFactory.getResource(); 
//        		
//        		if(object.toString().startsWith("FilterInvocation")) {
//        			authorityResource.setId(((FilterInvocation)object).getRequestUrl());
//        			authorityResource.setResourceType("URL");
//					list = resourceDAO.searchWithPatternMatch(authorityResource);
//        		} else if(object.toString().startsWith("ReflectiveMethodInvocation")) {
//        			Method method = ((ReflectiveMethodInvocation)object).getMethod();
//        			authorityResource.setId(method.getDeclaringClass().getName() + "." + method.getName());
//        			authorityResource.setResourceType("ObjectMethod");
//					list = resourceDAO.search(authorityResource);
//        		}
//        		
//	        	//沒有此資源,表示也不受權限控管了
//	        	if(list == null || list.size() == 0) {
//	        		return AccessDecisionVoter.ACCESS_GRANTED;
//	        	} else {
//	        		for(int i = 0; i < list.size(); i++) {
//		        		Set<Role> roles = list.get(i).getAuthorityResources();
//		        		Iterator<Role> iterator = roles.iterator();
//		        		
//		        		//若此資源沒有特別設定可被存取的角色,則表示只要登入系統就可使用
//		        		if(roles.size() == 0) {
//		        			return AccessDecisionVoter.ACCESS_GRANTED;
//		        		} else {
//		        		//反之,則使用者擁有的角色,至少有一個必須是可存取此資源的角色才有權限存取
//		        			result = AccessDecisionVoter.ACCESS_DENIED;
//			        		
//			        		while(iterator.hasNext()) {
//			        			Role role = iterator.next();
//			        			Iterator<GrantedAuthority> iterator2 = authorities.iterator();
//			        			while(iterator2.hasNext()) {
//			        				GrantedAuthority authority = iterator2.next();
//			        				if(authority.getAuthority().equalsIgnoreCase(role.getId())) {
//			        					return AccessDecisionVoter.ACCESS_GRANTED;
//			        				}
//			        			}
//			        		}
//		        		}
//	        		}
//	        	}
//        	} catch(Exception ex) {
//        		WebUtility webutil = new WebUtility();
//        		webutil.writeErrorLog(ex);
//        	}
//        }
//        
//        return result;
//	}

}
