package csiebug.web.webapp;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import javax.naming.NamingException;

import csiebug.domain.User;
import csiebug.service.ServiceException;
import csiebug.util.AssertUtility;


public class LoginAction extends BasicAction {
	private static final long serialVersionUID = 1L;
	
	public String main() throws Exception {
		//以下的if else處理各樣頁面的動作,可自行增加或修改
		if(getActFlag().equalsIgnoreCase("login")) {
			return login();
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
		
	}
	
	//畫面控制項函數區結束
	 
	//邏輯函數區
	//頁面動作所對應的處理method,business logic,請歸類到下面
	
	private String login() throws Exception {
		setLoginUserId((String)getSessionAttribute("SPRING_SECURITY_LAST_USERNAME"));
		
		User loginUser = getLoginUser();
		loginUser.setModifyUserId(loginUser.getId());
		loginUser.setModifyDate(Calendar.getInstance());
		getUserService().saveUser(loginUser);
		
		setLoginUserLocale(loginUser.getLocale());
		
		setLoginAPId(getAPId());
		
		return SUCCESS;
	}
	
	
	
	
	//邏輯函數區結束
}
