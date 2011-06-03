package csiebug.domain;

/**
 * 
 * @author George_Tsai
 * @version 2009/8/13
 *
 */
public interface UserProfile extends BasicObject {
	void setId(String userId);
	String getId();
	void setNickname(String nickname);
	String getNickname();
	void setLocale(String locale);
	String getLocale();
}
