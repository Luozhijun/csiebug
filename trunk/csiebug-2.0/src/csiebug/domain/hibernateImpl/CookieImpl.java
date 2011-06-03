package csiebug.domain.hibernateImpl;

import java.util.Calendar;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import csiebug.domain.Cookie;

public class CookieImpl implements Cookie {
	private static final long serialVersionUID = 1L;

	private String userId;
	private String series;
	private String token;
	private Calendar lastUsed;
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserId() {
		return userId;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public String getSeries() {
		return series;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getToken() {
		return token;
	}
	public void setLastUsed(Calendar lastUsed) {
		this.lastUsed = lastUsed;
	}
	public Calendar getLastUsed() {
		return lastUsed;
	}
	
	public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        
        if(!(obj instanceof CookieImpl)) {
            return false;
        }
        
        CookieImpl cookie = (CookieImpl) obj;
        
        return new EqualsBuilder().append(this.userId, cookie.getUserId()).append(this.series, cookie.getSeries()).isEquals();
    }
    
    public int hashCode() {
        return new HashCodeBuilder().append(this.userId).append(this.series).toHashCode();
    }
	
}
