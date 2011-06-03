package csiebug.web.webapp;

import com.sun.portal.portletcontainer.admin.PortletRegistryHelper;
import com.sun.portal.portletcontainer.admin.deployment.WebAppDeployerException;
import com.sun.portal.portletcontainer.admin.registry.PortletRegistryConstants;
import com.sun.portal.portletcontainer.admin.registry.PortletRegistryTags;
import com.sun.portal.portletcontainer.context.registry.PortletRegistryException;
import com.sun.portal.portletcontainer.driver.DesktopMessages;
import com.sun.portal.portletcontainer.driver.admin.AdminConstants;
import com.sun.portal.portletcontainer.driver.admin.PortletAdminData;
import com.sun.portal.portletcontainer.driver.admin.PortletAdminDataFactory;
import com.sun.portal.portletcontainer.warupdater.PortletWarUpdaterUtil;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FilenameUtils;

import csiebug.domain.Portlet;
import csiebug.domain.ResourceType;
import csiebug.service.ResourceService;
import csiebug.service.ServiceException;

public class PortletUploadAction extends PortletBasicAction {
	private static final long serialVersionUID = 1L;
	
	private HttpServletRequest request;
	
    private static Logger logger = Logger.getLogger(PortletUploadAction.class.getPackage().getName(), "PCDLogMessages");
    
    private File file;
    @SuppressWarnings("unused")
	private String contentType;
    private String filename;
    
    private ResourceService resourceService;
    
    private PortletAdminAction portletAdminAction;
    
    public void setPortletAdminAction(PortletAdminAction portletAdminAction) {
    	this.portletAdminAction = portletAdminAction;
    }
	
	public void setUpload(File file) {
       this.file = file;
    }
    
    public void setUploadContentType(String contentType) {
       this.contentType = contentType;
    }
    
    public void setUploadFileName(String filename) {
       this.filename = filename;
    }
    
    public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}
    
    private void init() {
    	request = getRequest();
	}
    
    @SuppressWarnings("unchecked")
	public String main() throws Exception {
    	//init
    	init();
		
		// Initialize DesktopMessages' Resource Bundle
        DesktopMessages.init(request);
        
        try {
			List<String> oldList = (List<String>)getSessionAttribute("com.sun.portal.portletcontainer.driver.admin.portletWindows");
			uploadFile();
            List<String> newList = (List<String>)getSessionAttribute("com.sun.portal.portletcontainer.driver.admin.portletWindows");
            
            addDashboardPortlet(oldList, newList);
        } catch (PortletRegistryException pre) {
        	logger.log(Level.SEVERE,"PSPCD_CSPPD0029",pre);
        } catch (FileUploadException e) {
        	logger.log(Level.SEVERE,"PSPCD_CSPPD0029",e);
        }
        
        //頁面控制項需要的資料
		portletAdminAction.makeControl();
        
		//若沒有導到特定處理頁面,則預設都導到formLoad頁
		return FORMLOAD;
	}
	
	//畫面控制項函數區
	
	//畫面控制項函數區結束
	
	//邏輯函數區
	//頁面動作所對應的處理method,business logic,請歸類到下面
	private void addDashboardPortlet(List<String> oldList, List<String> newList) throws ServiceException, NamingException {
		for(int i = 0; i < newList.size(); i++) {
			String portletId = newList.get(i);
			
			if(oldList.size() == 0 || !oldList.contains(portletId)) {
				Portlet portlet = getDomainObjectFactory().createPortlet();
				portlet.setId(portletId);
				portlet.setResourceType(ResourceType.PORTLET);
				portlet.setCreateUserId(getLoginUserId());
				portlet.setCreateDate(Calendar.getInstance());
				
				boolean openPermission = getRequestValue("permissionFlag").equals("on");
				resourceService.addPortlet(portlet, openPermission);
			}
		}
	}
	
	/* This method below is for use with commons-fileupload version 1.1
     */
    private void uploadFile() throws FileUploadException, IOException, PortletRegistryException, Exception {
    	HttpSession session = AdminUtils.getClearedSession(request);
        
        String[] fileNames = new String[2];
        fileNames[0] = processFile();
        fileNames[1] = null;
		boolean hidePortletWindow = false;
		
        deployPortlet(fileNames, hidePortletWindow, session);
        // refresh portlet list
        AdminUtils.refreshList(request);
    }
    
    // First item is portlet war, second item is roles file
    private void deployPortlet(String[] fileNames, boolean hidePortletWindow, HttpSession session) throws PortletRegistryException {
    	String warFileName = fileNames[0];
        if(warFileName == null || !warFileName.endsWith(".war")) {
            session.setAttribute(AdminConstants.DEPLOYMENT_FAILED_ATTRIBUTE, 
                    DesktopMessages.getLocalizedString(AdminConstants.INVALID_PORTLET_APP));
        } else {
            PortletAdminData portletAdminData = PortletAdminDataFactory.getPortletAdminData();
            boolean success = false;
            StringBuffer messageBuffer = new StringBuffer();
            try {
                // If already deployed. Unregister it before deploying
                if(isPortletDeployed(warFileName)) {
                    try {
                        portletAdminData.undeploy(getWarName(warFileName), false);
                    } catch (Exception ex) {
                        //ignored
                    	logger.info(ex.getMessage());
                    }
                }
				Properties portletWindow = new Properties();
				if(hidePortletWindow) {
					portletWindow.setProperty(PortletRegistryTags.VISIBLE_KEY, PortletRegistryConstants.VISIBLE_FALSE);
				} else {
					portletWindow.setProperty(PortletRegistryTags.VISIBLE_KEY, PortletRegistryConstants.VISIBLE_TRUE);
				}
                success = portletAdminData.deploy(warFileName, fileNames[1], null, portletWindow, true);
                messageBuffer.append(DesktopMessages.getLocalizedString(AdminConstants.DEPLOYMENT_SUCCEEDED));
            } catch (Exception ex) {
                success = false;
                if(ex instanceof WebAppDeployerException){
                    Object[] tokens = {PortletRegistryHelper.getUpdatedAbsoluteWarFileName(warFileName)};
                    messageBuffer.append(DesktopMessages.getLocalizedString(AdminConstants.WAR_NOT_DEPLOYED, tokens));
                } else {
                    messageBuffer.append(DesktopMessages.getLocalizedString(AdminConstants.DEPLOYMENT_FAILED));
                    messageBuffer.append(".");
                    messageBuffer.append(ex.getMessage());
                    // Undeploy only when deploy fails for reasons other than war deployment
                    try {
                        portletAdminData.undeploy(getWarName(warFileName), true);
                    } catch (Exception ex1) {
                        // ignored
                    	logger.info(ex1.getMessage());
                    }
                }
            }
            if (success) {
                session.setAttribute(AdminConstants.DEPLOYMENT_SUCCEEDED_ATTRIBUTE, messageBuffer.toString());
            } else {
                session.setAttribute(AdminConstants.DEPLOYMENT_FAILED_ATTRIBUTE, messageBuffer.toString());
            }
            boolean flag = new File(warFileName).delete();
            if(!flag) {
            	logger.info("Can not delete war file '" + warFileName + "'!");
            }
        }
    }
    
    private String processFile() throws FileUploadException {
    	File fNew = null;
    	FilenameFilter filter = null;
        try {
        	filter = new FilenameFilter() {
    			public boolean accept(File dir, String name) {
    				try {
    					File currFile = new File(dir, name);
    					if (currFile.isFile() && name.endsWith(".tmp")) {
    						return (currFile.isFile() && name.endsWith(".tmp"));
    					} else {
    				    	return false;
    				    }
    				} catch(Exception ex) {
    	        		writeErrorLog(ex);
    					return false;
    				}
    			}
    		};
    		
        	String fileName = filename;
            if(fileName == null || fileName.trim().length() == 0) {
                return null;
            }
            fileName = FilenameUtils.getName(fileName);
            
            fNew = file;
            
            File finalFileName = new File(fNew.getParent() + File.separator + fileName);
            if (fNew.renameTo(finalFileName)) {
            	return finalFileName.getAbsolutePath();
            } else {
                // unable to rename, copy the contents of the file instead
                PortletWarUpdaterUtil.copyFile(fNew, finalFileName, true, false);
                return finalFileName.getAbsolutePath();
            }
        } catch (Exception e) {
            throw new FileUploadException(e.getMessage());
        } finally {
        	//刪除所有的暫存檔
        	if(fNew != null) {
        		File dir = fNew.getParentFile();
        		File[] tmpFiles = dir.listFiles(filter);
        		
        		for(int i = tmpFiles.length - 1; i >= 0; i--) {
        			boolean flag = tmpFiles[i].delete();
        			if(!flag) {
        				logger.info("Can not delete temp file '" + tmpFiles[i].getName() + "'!!");
        			}
        		}
    		}
        }
    }
    
    private String getWarName(String warFileName) {
        String warName = PortletWarUpdaterUtil.getWarName(warFileName);
        String regexp = WarFileFilter.WAR_EXTENSION + "$";
        return warName.replaceFirst(regexp, "");
    }

    private boolean isPortletDeployed(String warFileName) 
        throws PortletRegistryException{
        
        String filename = PortletRegistryHelper.getWarFileLocation() + 
                File.separator + PortletWarUpdaterUtil.getWarName(warFileName);
        return (new File(filename)).exists();
    }

	//邏輯函數區結束
}
