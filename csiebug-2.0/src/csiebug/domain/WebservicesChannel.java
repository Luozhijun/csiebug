package csiebug.domain;

import java.io.Serializable;
import java.util.Calendar;

/**
 * 
 * @author George_Tsai
 * @version 2009/11/13
 *
 */
public interface WebservicesChannel extends Serializable {
	void setUserId(String userId);
	String getUserId();
	void setChannelId(String channelId);
	String getChannelId();
	void setServiceKey(String key);
	String getServiceKey();
	void setLastUsed(Calendar lastUsed);
	Calendar getLastUsed();
}
