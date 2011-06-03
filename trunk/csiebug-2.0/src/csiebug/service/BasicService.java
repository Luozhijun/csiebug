/**
 * 
 */
package csiebug.service;

import csiebug.util.Observer;

/**
 * @author George_Tsai
 * @version 2010/1/20
 */
public interface BasicService extends Observer {
	void setServiceStatus(String status) throws ServiceException;
	String getServiceStatus() throws ServiceException;
	boolean isServiceLock();
	void checkServiceStatus() throws ServiceLockException;
}
