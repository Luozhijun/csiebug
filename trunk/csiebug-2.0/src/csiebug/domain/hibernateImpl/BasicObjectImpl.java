package csiebug.domain.hibernateImpl;

import java.util.Calendar;

import csiebug.domain.BasicObject;

/**
 * 共有的欄位
 * @author George_Tsai
 * @version 2009/6/22
 */
public class BasicObjectImpl implements BasicObject {
	private static final long serialVersionUID = 1L;

	//Session欄位
	private String loginUserId;
	
	//table共用欄位
	private String createUserId;
	private Calendar createDate;
	private String modifyUserId;
	private Calendar modifyDate;
	
	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}

	public String getLoginUserId() {
		return loginUserId;
	}
	
	public void setCreateUserId(String value) {
		createUserId = value;
	}
	
	public String getCreateUserId() {
		return createUserId;
	}
	
	public void setCreateDate(Calendar value) {
		createDate = value;
	}
	
	public Calendar getCreateDate() {
		return createDate;
	}
	
	public void setModifyUserId(String value) {
		modifyUserId = value;
	}
	
	public String getModifyUserId() {
		return modifyUserId;
	}
	
	public void setModifyDate(Calendar value) {
		modifyDate = value;
	}
	
	public Calendar getModifyDate() {
		return modifyDate;
	}
}
