/**
 * 
 */
package csiebug.util;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

import csiebug.service.BasicService;
import csiebug.service.UserService;
import csiebug.service.WebUtilityService;

/**
 * @author George_Tsai
 * @version 2010/1/19
 */
public class ServiceLockInterceptor implements MethodInterceptor {
	private String[] excludes;
	
	public void setExcludes(String[] list) {
		excludes = list;
	}
	
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		AssertUtility.notNull(methodInvocation);
		
		//每個service的method被執行前,先看看service有沒有被lock
		try {
			Object obj = methodInvocation.getThis();
			Method method = obj.getClass().getMethod("checkServiceStatus", new Class<?>[]{});
			
			if(isNeedToCheckServiceLock(methodInvocation)) {
				method.invoke(obj, new Object[]{});
			}
		} catch(NoSuchMethodException nsmex) {
			//表示這個物件沒有繼承BasicService，
			//所以它不是Service，或者它不需要被lock
			//這個exception可以忽略不管
			Logger logger = Logger.getLogger(this.getClass());
			logger.info("This object is not extends BasicService.", nsmex);
		}
		
		//執行真正要執行的method
		Object result = methodInvocation.proceed();
		
	    return result;
	}
	
	/**
	 * 檢查要被執行的method是不是BasicService提供的method
	 * 如果是的話，不檢查service lock
	 * 檢查如果這個Service是WebUtilityService的話,也不用檢查service lock
	 * 因為此Service是一些基本的service...如果被擋住,連回復的機會都沒有...
	 * @param methodInvocation
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws ClassNotFoundException 
	 */
	private boolean isNeedToCheckServiceLock(MethodInvocation methodInvocation) throws SecurityException, NoSuchMethodException, ClassNotFoundException {
		AssertUtility.notNull(methodInvocation);
		
		return !isBasicServiceMethod(methodInvocation) &&
			   !isWebUtilityService(methodInvocation) &&
			   !isUserServiceSearchUsers(methodInvocation) &&
			   !isExcludeServiceMethod(methodInvocation);
	}
	
	private boolean isBasicServiceMethod(MethodInvocation methodInvocation) {
		AssertUtility.notNull(methodInvocation);
		
		Method[] methods = BasicService.class.getMethods();
		for(int i = 0; i < methods.length; i++) {
			if(methodInvocation.getMethod().equals(methods[i])) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean isWebUtilityService(MethodInvocation methodInvocation) {
		AssertUtility.notNull(methodInvocation);
		
		Object obj = methodInvocation.getThis();
		Class<?>[] implementInterfaces = obj.getClass().getInterfaces();
		
		for(int i = 0; i < implementInterfaces.length; i++) {
			if(implementInterfaces[i].equals(WebUtilityService.class)) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean isUserServiceSearchUsers(MethodInvocation methodInvocation) throws SecurityException, NoSuchMethodException {
		AssertUtility.notNull(methodInvocation);
		
		Object obj = methodInvocation.getThis();
		Class<?>[] implementInterfaces = obj.getClass().getInterfaces();
		
		for(int i = 0; i < implementInterfaces.length; i++) {
			if(implementInterfaces[i].equals(UserService.class)) {
				Method method = UserService.class.getMethod("searchUsers", new Class<?>[]{});
				
				return methodInvocation.getMethod().equals(method);
			}
		}
		
		return false;
	}

	private boolean isExcludeServiceMethod(MethodInvocation methodInvocation) throws ClassNotFoundException, SecurityException, NoSuchMethodException {
		if(excludes != null) {
			Object obj = methodInvocation.getThis();
			Class<?>[] implementInterfaces = obj.getClass().getInterfaces();
			
			for(int i = 0; i < implementInterfaces.length; i++) {
				for(int j = 0; j < excludes.length; j++) {
					String exclude = excludes[j];
					
					//擋某個class的全部method
					if(exclude.endsWith(".*.*")) {
						String className = exclude.substring(0, exclude.length() - 4);
						
						if(implementInterfaces[i].equals(Class.forName(className))) {
							return true;
						}
					} else if(exclude.endsWith(".*")) { //擋某個class的某個method
						String[] fullPath = exclude.split("\\.");
						String methodName = fullPath[fullPath.length - 2];
						String className = exclude.replaceAll("." + methodName + ".*", "");
						
						if(implementInterfaces[i].equals(Class.forName(className))) {
							Method method = Class.forName(className).getMethod(methodName, new Class<?>[]{});
							
							if(methodInvocation.getMethod().equals(method)) {
								return true;
							}
						}
					}
				}
			}
		}
		
		return false;
	}
}
