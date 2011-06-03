package csiebug.domain;

import java.io.Serializable;
import java.util.Calendar;

/**
 * 
 * @author George_Tsai
 * @version 2009/11/13
 *
 */
public interface Cookie extends Serializable {
	void setUserId(String userId);
	String getUserId();
	void setSeries(String series);
	String getSeries();
	void setToken(String token);
	String getToken();
	void setLastUsed(Calendar lastUsed);
	Calendar getLastUsed();
}
