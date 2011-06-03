package csiebug.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.aopalliance.intercept.MethodInterceptor; 
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import csiebug.domain.DomainObjectFactory;

/**
 * 紀錄敏感性欄位存取的DAO與DomainObject的值
 * @author George_Tsai
 * @version 2009/6/24
 *
 */
public class DAOLogInterceptor implements MethodInterceptor {
	@Autowired
	private DomainObjectFactory domainObjectFactory;
	
   public Object invoke(MethodInvocation methodInvocation) throws Throwable {
	   AssertUtility.notNull(methodInvocation);
	   
	   WebUtility webutil = new WebUtility();
	   Logger logger = webutil.getLogger(this.getClass().getName());
	   StringBuffer message = new StringBuffer();
	   
	   try {
		   message.append("\n called DAO method: " + methodInvocation.getMethod());
	    	   
		   String method = methodInvocation.getMethod().toString();
		   String[] argsName = method.substring(method.indexOf("(") + 1, method.indexOf(")")).split(",");
		   Object[] argsObj = methodInvocation.getArguments();
	    	   
		   for(int i = 0; i < argsName.length; i++) {
			   if(!argsName[i].trim().equals("")) {
				   Object obj = argsObj[i];
				   String className = getCollectionClassName(obj);
	        		   
				   if(className == null) {
					   logDomainObject(message, argsName[i], obj, 1);
				   } else if(className.equals("java.util.List")) {
					   logListObject(message, obj);
				   } else if(className.equals("java.util.Map")) {
					   logMapObject(message, obj);
				   } else if(className.equals("java.util.Set")) {
					   logSetObject(message, obj);
				   }        		   
			   }
		   }
	           
	       Object result = methodInvocation.proceed(); 
	       return result;
	   } finally {
		   logger.info(message.toString()); 
	   }
   }
   
   private String getCollectionClassName(Object obj) throws ClassNotFoundException {
	   AssertUtility.notNull(obj);
	   
	   String className = null;
	   
	   String[] collectionClasses = new String[]{"java.util.List", "java.util.Map", "java.util.Set"};
	   
	   for(int i = 0; i < collectionClasses.length; i++) {
		   try {
			   Class<?> c = Class.forName(collectionClasses[i]);
			   c.cast(obj);
			   className = collectionClasses[i];
			   break;
		   } catch(ClassCastException ccex) {
			   continue;
		   }
	   }
	   
	   return className;
   }
   
   private String getDomainObjectClassName(Object argsObj) throws ClassNotFoundException {
	   AssertUtility.notNull(argsObj);
	   
	   String className = null;
	   
	   Class<? extends DomainObjectFactory> c = domainObjectFactory.getClass();
	   
	   for(int j = 0; j < c.getMethods().length; j++) {
		   Method argMethod = c.getMethods()[j];
		   if(argMethod.getName().startsWith("get") && !argMethod.getName().equalsIgnoreCase("getClass")) {
			   String domainObjectClassName = "csiebug.domain." + argMethod.getName().replaceFirst("get", "");
		       
			   try {
			       Class<?> c2 = Class.forName(domainObjectClassName);
			       c2.cast(argsObj);
			       className = domainObjectClassName;
			       break;
			   } catch(ClassCastException ccex) {
				   continue;
			   }
		   }
       }
	   
	   return className;
   }
   
   private void logListObject(StringBuffer message, Object argsObj) throws ClassNotFoundException, Exception {
	   AssertUtility.notNull(argsObj);
	   
	   if(message == null) {
		   message = new StringBuffer();
	   }
	   
	   List<?> list = (List<?>)argsObj;
	   message.append("\n\t Collection type: List");
	   message.append("\n\t List size: " + list.size());
	   message.append("\n\t ***** List content start *****");
	   
	   for(int i = 0; i < list.size(); i++) {
		   Object obj = list.get(i);
		   logDomainObject(message, getDomainObjectClassName(obj), obj, 2);
	   }
	   
	   message.append("\n\t ***** List content end *****");
   }
   
   @SuppressWarnings("unchecked")
   private void logMapObject(StringBuffer message, Object argsObj) throws IllegalArgumentException, SecurityException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
	   AssertUtility.notNull(argsObj);
	   
	   if(message == null) {
		   message = new StringBuffer();
	   }
	   
	   Map<String, ?> map = (Map<String, ?>)argsObj;
	   message.append("\n\t Collection type: Map");
	   message.append("\n\t Map size: " + map.size());
	   message.append("\n\t ***** Map content start *****");
	   
	   for(int i = 0; i < map.size(); i++) {
		   Object obj = map.get(map.keySet().toArray()[i]);
		   logDomainObject(message, getDomainObjectClassName(obj), obj, 2);
	   }
	   
	   message.append("\n\t ***** Map content end *****");
   }
   
   private void logSetObject(StringBuffer message, Object argsObj) throws IllegalArgumentException, SecurityException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
	   AssertUtility.notNull(argsObj);
	   
	   if(message == null) {
		   message = new StringBuffer();
	   }
	   
	   Set<?> set = (Set<?>)argsObj;
	   message.append("\n\t Collection type: Set");
	   message.append("\n\t Set size: " + set.size());
	   message.append("\n\t ***** Set content start *****");
	   
	   Iterator<?> iterator = set.iterator();
	   
	   while(iterator.hasNext()) {
		   Object obj = iterator.next();
		   logDomainObject(message, getDomainObjectClassName(obj), obj, 2);
	   }
	   
	   message.append("\n\t ***** Set content end *****");
   }
   
   private void logDomainObject(StringBuffer message, String argsName, Object argsObj, int tabCount) throws ClassNotFoundException, IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException {
	   AssertUtility.notNull(argsObj);
	   
	   if(message == null) {
		   message = new StringBuffer();
	   }
	   
	   StringBuffer tab = new StringBuffer();
	   for(int i = 0; i < tabCount; i++) {
		   tab.append("\t");
	   }
	   
	   message.append("\n" + tab.toString() + " Domain object name: " + argsName);
	   message.append("\n" + tab.toString() + " Domain object property value: ");

	   Class<?> c = Class.forName(argsName);
	   Object obj = argsObj;
	   
	   for(int j = 0; j < c.getMethods().length; j++) {
		   Method argMethod = c.getMethods()[j];
		   if(argMethod.getName().startsWith("get") && !argMethod.getName().equalsIgnoreCase("getClass")) {
		        	String key = argMethod.getName().replaceFirst("get", "");
		        	key = key.substring(0, 1).toLowerCase() + key.substring(1, key.length());
		        	message.append("\n" + tab.toString() + "\t " + key + "=" + c.getMethods()[j].invoke(obj, (Object[])null));
		   }
       }
   }

	public void setDomainObjectFactory(DomainObjectFactory domainObjectFactory) {
		this.domainObjectFactory = domainObjectFactory;
	}
	
	public DomainObjectFactory getDomainObjectFactory() {
		return domainObjectFactory;
	}
}
