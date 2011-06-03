package csiebug.domain;


/**
 * 
 * @author George_Tsai
 * @version 2009/7/16
 *
 */
public interface Function extends Resource {
	void setApId(String apId);
	String getApId();
	void setFunctionId(String functionId);
	String getFunctionId();
	void setParentId(String parentId);
	String getParentId();
	void setFunctionName(String functionName);
	String getFunctionName();
	void setFunctionURL(String functionURL);
	String getFunctionURL();
	void setFunctionLogo(String functionLogo);
	String getFunctionLogo();
	void setSortOrder(Integer sortOrder);
	Integer getSortOrder();
	void setAp(AP ap);
	AP getAp();
}
