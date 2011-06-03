/**
 * 
 */
package csiebug.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import csiebug.service.ServiceException;
import csiebug.service.ServiceStatus;
import csiebug.service.WebUtilityService;

/**
 * @author George_Tsai
 * @version 2010/2/5
 */
public class TestJob extends QuartzJobBean {
	private String testArg;
	private WebUtilityService webUtilityService;
	
	public void setTestArg(String value) {
		testArg = value;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
//		System.out.println("我有被執行耶!!" + testArg);
		
//		try {
//			lockService();
			try {
				Thread.sleep(90000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			enableService();
//			System.out.println("我結束了耶!!" + testArg);
//		} catch (ServiceException e) {
//			e.printStackTrace();
//		}
	}
	
	public void lockService() throws ServiceException {
		webUtilityService.setServiceStatus(ServiceStatus.LOCK);
	}
	
	public void enableService() throws ServiceException {
		webUtilityService.setServiceStatus(ServiceStatus.ENABLE);
	}
	
	public void setWebUtilityService(WebUtilityService service) {
		webUtilityService = service;
	}
}
