package csiebug.domain;

/**
 * 
 * @author George_Tsai
 * @version 2010/9/29
 *
 */
public interface UserEmail extends BasicObject {
	void setUserId(String userId);
	String getUserId();
	void setEmailAccount(String emailAccount);
	String getEmailAccount();
	void setEmailDomain(String emailDomain);
	String getEmailDomain();
	void setMajorFlag(Boolean majorFlag);
	Boolean getMajorFlag();
	String toString();
}
