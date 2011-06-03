package csiebug.domain;

import java.io.Serializable;
import java.util.Calendar;

/**
 * 共有的欄位
 * @author George_Tsai
 * @version 2009/6/22
 */
public interface BasicObject extends Serializable {
	//Session欄位
	void setLoginUserId(String loginUserId);
	String getLoginUserId();
	
	//table共用欄位
	void setCreateUserId(String value);
	String getCreateUserId();
	void setCreateDate(Calendar value);
	Calendar getCreateDate();
	void setModifyUserId(String value);
	String getModifyUserId();
	void setModifyDate(Calendar value);
	Calendar getModifyDate();
}
