package csiebug.domain;

import java.util.List;
import java.util.Set;

/**
 * 
 * @author George_Tsai
 * @version 2009/6/22
 *
 */
public interface User extends BasicObject {
	void setId(String username);
	String getId();
	void setPassword(String password);
	String getPassword();
	void setEnabled(Boolean enabled);
	Boolean getEnabled();
	void setAuthorities(Set<Role> roles);
	Set<Role> getAuthorities();
	void setCookies(Set<Cookie> cookies);
	Set<Cookie> getCookies();
	void addCookie(Cookie cookie);
	void removeCookie(Cookie cookie);
	Cookie getCookie(String series);
	/**
	 * 取得可以使用的cookie序號
	 * @return
	 */
	String getAvailableCookieSeries();
	/**
	 * 取得過期的cookie
	 * @return
	 */
	List<Cookie> getExpiredCookie(int lifecycle);
	void setWebservicesChannels(Set<WebservicesChannel> webservicesChannels);
	Set<WebservicesChannel> getWebservicesChannels();
	void addWebservicesChannel(WebservicesChannel webservicesChannel);
	void removeWebservicesChannel(WebservicesChannel webservicesChannel);
	WebservicesChannel getWebservicesChannel(String channelId);
	/**
	 * 取得可以使用的webservicesChannelId
	 * @return
	 */
	String getAvailableWebservicesChannelId();
	/**
	 * 取得過期的webservicesChannel
	 * @return
	 */
	List<WebservicesChannel> getExpiredWebservicesChannel(int lifecycle);
	void addAuthority(Role role);
	void removeAuthority(Role role);
	void setDashboards(Set<Dashboard> dashboards);
	Set<Dashboard> getDashboards();
	void addDashboard(Dashboard dashboard);
	void removeDashboard(Dashboard dashboard);
	Dashboard getDashboard(String dashboardId);
	DashboardPortlet getDashboardPortlet(String portletId);
	
	/**
	 * 取出使用者可使用的資源
	 * @return
	 * @author George_Tsai
	 * @version 2009/8/12
	 */
	List<Resource> getResources();
	
	void setUserProfile(UserProfile userProfile);
	UserProfile getUserProfile();
	void setNickname(String nickname);
	String getNickname();
	void setLocale(String locale);
	String getLocale();
	void setUserEmails(Set<UserEmail> userEmails);
	Set<UserEmail> getUserEmails();
	void addUserEmail(UserEmail userEmail);
	void removeUserEmail(UserEmail userEmail);
	
	/**
	 * 取出使用者設定的主要email
	 * @return
	 */
	String getMajorEmail();
}
