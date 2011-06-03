package csiebug.domain;

import java.util.Set;


/**
 * 
 * @author George_Tsai
 * @version 2009/7/16
 *
 */
public interface AP extends Resource {
	void setApId(String apId);
	String getApId();
	void setApName(String apName);
	String getApName();
	void setApLogo(String apLogo);
	String getApLogo();
	void setApIndexURL(String apIndexURL);
	String getApIndexURL();
	void setFunctions(Set<Function> functions);
	Set<Function> getFunctions();
	void addFunction(Function function);
	void removeFunction(Function function);
}
