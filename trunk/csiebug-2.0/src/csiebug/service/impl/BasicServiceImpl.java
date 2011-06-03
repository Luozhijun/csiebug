/**
 * 
 */
package csiebug.service.impl;

import java.util.Collection;
import java.util.Iterator;

import csiebug.util.AssertUtility;
import csiebug.util.Observable;

import csiebug.service.BasicService;
import csiebug.service.ServiceException;
import csiebug.service.ServiceLockException;
import csiebug.service.ServiceStatus;

/**
 * @author George_Tsai
 * @version 2010/1/9
 */
public class BasicServiceImpl implements BasicService {
	private static final long serialVersionUID = 1L;
	
	private ServiceStatus serviceStatus;
	
	/**
	 * 檢查物件是否是null
	 * @param object
	 * @param message
	 */
	protected static void notNull(Object object, String message) throws ServiceException {
		if(object == null) {
			throw new ServiceException(new NullPointerException(message));
		}
	}
	
	/**
	 * 檢查物件是否是null
	 * @param object
	 * @param message
	 */
	protected static void notNull(Object object) throws ServiceException {
		notNull(object, "Object is null!!");
	}
	
	/**
	 * 檢查字串是否是null或是空白字串
	 * @param string
	 * @param message
	 */
	protected static void notNullAndNotSpace(String string, String message) throws ServiceException {
		if(string == null || string.trim().equals("")) {
			throw new ServiceException(new NullPointerException(message));
		}
	}
	
	/**
	 * 檢查字串是否是null或是空白字串
	 * @param string
	 * @param message
	 */
	protected static void notNullAndNotSpace(String string) throws ServiceException {
		notNullAndNotSpace(string, "String is null or empty!!");
	}
	
	/**
	 * 檢查Collection物件是否是null或是size是0
	 * @param Collection
	 * @param message
	 */
	protected static void notNullAndNotEmpty(Collection<?> c, String message) throws ServiceException {
		if(c == null || c.size() <= 0) {
			throw new ServiceException(new NullPointerException(message));
		}
	}
	
	/**
	 * 檢查Collection物件是否是null或是size是0
	 * @param Collection
	 */
	protected static void notNullAndNotEmpty(Collection<?> c) throws ServiceException {
		notNullAndNotEmpty(c, "Collection is null or empty!!");
	}
	
	/**
	 * 檢查Array是否是null或是size是0
	 * @param array
	 * @param message
	 */
	protected static void notNullAndNotEmpty(Object[] ary, String message) throws ServiceException {
		if(ary == null || ary.length <= 0) {
			throw new ServiceException(new NullPointerException(message));
		}
	}
	
	/**
	 * 檢查Array是否是null或是size是0
	 * @param array
	 */
	protected static void notNullAndNotEmpty(Object[] ary) throws ServiceException {
		notNullAndNotEmpty(ary, "Array is null or empty!!");
	}
	
	/**
	 * 檢查Collection的element是否有null
	 * @param collection
	 * @param message
	 * @throws ServiceException 
	 */
	protected static void allElementNotNull(Collection<?> c, String message) throws ServiceException {
		notNullAndNotEmpty(c, message);
		
		Iterator<?> iterator = c.iterator();
		
		while(iterator.hasNext()) {
			notNull(iterator.next(), message);
		}
	}
	
	/**
	 * 檢查Collection的element是否有null
	 * @param collection
	 * @throws ServiceException 
	 */
	protected static void allElementNotNull(Collection<?> c) throws ServiceException {
		allElementNotNull(c, "Element can not be null!!");
	}
	
	/**
	 * 檢查Array的element是否有null
	 * @param array
	 * @param message
	 * @throws ServiceException 
	 */
	protected static void allElementNotNull(Object[] ary, String message) throws ServiceException {
		notNullAndNotEmpty(ary, message);
		
		for(int i = 0; i < ary.length; i++) {
			notNull(ary[i], message);
		}
	}
	
	/**
	 * 檢查Array的element是否有null
	 * @param array
	 * @throws ServiceException 
	 */
	protected static void allElementNotNull(Object[] ary) throws ServiceException {
		allElementNotNull(ary, "Element can not be null!!");
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {
		AssertUtility.notNull(o);
		
		serviceStatus = (ServiceStatus)o;
	}
	
	public void setServiceStatus(String status) throws ServiceException {
		notNull(serviceStatus, "This service is not registered observer!!");
		
		serviceStatus.setStatus(status);
	}
	
	public String getServiceStatus() throws ServiceException {
		notNull(serviceStatus, "This service is not registered observer!!");
		
		return serviceStatus.getStatus();
	}
	
	public boolean isServiceLock() {
		try {
			return getServiceStatus().equalsIgnoreCase(ServiceStatus.LOCK);
		} catch(ServiceException se) {
			//如果沒有註冊，表示這個service永遠不用被LOCK
			return false;
		}
	}
	
	public void checkServiceStatus() throws ServiceLockException {
		if(isServiceLock()) {
			throw new ServiceLockException("Service Lock!!");
		}
	}
}
