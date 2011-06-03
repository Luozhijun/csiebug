package csiebug.web.webapp;

import java.io.UnsupportedEncodingException;

import javax.naming.NamingException;

import csiebug.service.ServiceException;
import csiebug.util.AssertUtility;
import csiebug.util.NumberFormatUtility;


public class IndexAction extends BasicAction {
	private static final long serialVersionUID = 1L;
	private String redirectURL;
	
	public String getRedirectURL() {
		return redirectURL;
	}
	
	public String main() throws Exception {
		//以下的if else處理各樣頁面的動作,可自行增加或修改
		if(getActFlag().equalsIgnoreCase("nopermission")) {
			if(getSessionAttribute("PrePageURL") != null) {
				String prePageURL = (String)getSessionAttribute("PrePageURL");
				return getNoPermissionForwardPrePage(prePageURL);
			} else {
				return getNoPermissionForward();
			}
		} else if(getActFlag().equalsIgnoreCase("timeout")) {
			return getSessionTimeOutForward();
		} else if(getActFlag().equalsIgnoreCase("authenticationFailure")) {
			addPageLoadWarningDialog(null, getMessage("common.AuthenticationFailure"), "j_username", null);
		} else if(getActFlag().equalsIgnoreCase("maxSessionExceeded")) {
			addPageLoadWarningDialog(null, getMessage("common.MaxSessionExceeded"), "j_username", null);
		} else if(getActFlag().equalsIgnoreCase("logout")) {
			removeAllSessionAttribute();
		} else if(getActFlag().equalsIgnoreCase("downloadCSVView")) {
			return "downloadCSVView";
		} else if(getActFlag().equalsIgnoreCase("downloadCSV")) {
//			getRequestValue會把"\"濾掉，但是這邊已經不是使用者輸入了，所以可以補上，讓excel可以照這格式顯示
//			download(getRequestValue("DownloadFileName"), getRequestValue("GridHTML").replaceAll("<td style=\"mso-number-format:@\">", "<td style=\"mso-number-format:\\\\@\">"), "UTF-8");
			downloadCSVFromSessionData(getRequestValue("DownloadFileName"), getRequestValue("DownloadTableId"), getRequestValue("GridHTML"), "UTF-8", getRequestValue("DownloadRows"), getRequestValue("DownloadColumns"));
			return null;
		} else if(getActFlag().equalsIgnoreCase("downloadExcelView")) {
			return "downloadExcelView";
		} else if(getActFlag().equalsIgnoreCase("downloadExcel")) {
//			getRequestValue會把"\"濾掉，但是這邊已經不是使用者輸入了，所以可以補上，讓excel可以照這格式顯示
//			download(getRequestValue("DownloadFileName"), getRequestValue("GridHTML").replaceAll("<td style=\"mso-number-format:@\">", "<td style=\"mso-number-format:\\\\@\">"), "UTF-8");
//			downloadExcelUsingPOI(getRequestValue("DownloadFileName"), getRequestValue("GridHTML").replaceAll("<td style=\"mso-number-format:@\">", "<td style=\"mso-number-format:\\\\@\">"));
			downloadExcelFromSessionDataUsingPOI(getRequestValue("DownloadFileName"), getRequestValue("DownloadTableId"), getRequestValue("GridHTML"), "UTF-8", getRequestValue("DownloadRows"), getRequestValue("DownloadColumns"));
			return null;
		} else if(getActFlag().equalsIgnoreCase("downloadODSView")) {
			return "downloadODSView";
		} else if(getActFlag().equalsIgnoreCase("downloadODS")) {
//			getRequestValue會把"\"濾掉，但是這邊已經不是使用者輸入了，所以可以補上，讓excel可以照這格式顯示
//			download(getRequestValue("DownloadFileName"), getRequestValue("GridHTML").replaceAll("<td style=\"mso-number-format:@\">", "<td style=\"mso-number-format:\\\\@\">"), "UTF-8");
//			downloadODS(getRequestValue("DownloadFileName"), getRequestValue("GridHTML").replaceAll("<td style=\"mso-number-format:@\">", "<td style=\"mso-number-format:\\\\@\">"));
			downloadODSFromSessionData(getRequestValue("DownloadFileName"), getRequestValue("DownloadTableId"), getRequestValue("GridHTML"), "UTF-8", getRequestValue("DownloadRows"), getRequestValue("DownloadColumns"));
			return null;
		} else if(getActFlag().equalsIgnoreCase("downloadMPPView")) {
			return "downloadMPPView";
		} else if(getActFlag().equalsIgnoreCase("downloadMPP")) {
			downloadProject(getRequestValue("DownloadFileName"), getRequestValue("DownloadTableId"), getRequestValue("GridHTML"), "UTF-8", "mpp");
			return null;
		} else if(getActFlag().equalsIgnoreCase("downloadMSPDIView")) {
			return "downloadMSPDIView";
		} else if(getActFlag().equalsIgnoreCase("downloadMSPDI")) {
			downloadProject(getRequestValue("DownloadFileName"), getRequestValue("DownloadTableId"), getRequestValue("GridHTML"), "UTF-8", "mspdi");
			return null;
		} else if(getActFlag().equalsIgnoreCase("authenticationImage")) {
			makeAuthenticationImageFile();
			return null;
		} else if(getActFlag().equalsIgnoreCase("qrCodeImage")) {
			if(NumberFormatUtility.isValidPositiveInteger(getRequestValue("QRCodeWidth")) && NumberFormatUtility.isValidPositiveInteger(getRequestValue("QRCodeHeight"))) {
				makeQRCodeImageFile(getRequestValue("QRCodeValue"), Integer.parseInt(getRequestValue("QRCodeWidth")), Integer.parseInt(getRequestValue("QRCodeHeight")));
			} else {
				makeQRCodeImageFile(getRequestValue("QRCodeValue"));
			}
			
			return null;
		}
		//頁面動作處理結束
		
		//頁面控制項需要的資料
		makeControl();
		
		//若沒有導到特定處理頁面,則預設都導到formLoad頁
		return FORMLOAD;
	}
	
	//畫面控制項函數區
	private void makeControl() throws UnsupportedEncodingException, NamingException {
		//畫面控制項
		try {
			setRequestAttribute("FunctionName", getFunctionName());
		} catch(ServiceException se) {
			writeErrorLog(se);
			
			if(AssertUtility.isNotNullAndNotSpace(getFunctionId())) {
				setRequestAttribute("FunctionName", getFunctionId());
			} else {
				setRequestAttribute("FunctionName", getMessage("Login.FunctionName"));
			}
		}
		
		setRequestAttribute("enterSubmitScript", "submitEnter(event, 'index', '" + getMessage("common.warning") + "', '" + getMessage("common.error.required5") + "', '" + getMessage("common.ok") + "');");
	}
	
	//畫面控制項函數區結束
	
	//邏輯函數區
	//頁面動作所對應的處理method,business logic,請歸類到下面
	
	//邏輯函數區結束
}
