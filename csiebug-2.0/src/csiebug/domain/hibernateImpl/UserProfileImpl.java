package csiebug.domain.hibernateImpl;

import java.util.Calendar;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import csiebug.domain.UserProfile;

/**
 * 
 * @author George_Tsai
 * @version 2009/8/13
 *
 */
public class UserProfileImpl extends BasicObjectImpl implements UserProfile {
	private static final long serialVersionUID = 1L;
	
	private String userId;
	private String nickname;
	private String locale;
	private Calendar birthday;
	
	public void setId(String userId) {
		this.userId = userId;
	}
	public String getId() {
		return userId;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getNickname() {
		return nickname;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getLocale() {
		return locale;
	}
	public void setBirthday(Calendar birthday) {
		this.birthday = birthday;
	}
	public Calendar getBirthday() {
		return birthday;
	}
	
	public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        
        if(!(obj instanceof UserProfileImpl)) {
            return false;
        }
        
        UserProfileImpl userProfile = (UserProfileImpl) obj;
        
        return new EqualsBuilder().append(this.userId, userProfile.getId()).isEquals();
    }
    
    public int hashCode() {
        return new HashCodeBuilder().append(this.userId).toHashCode();
    }
}
