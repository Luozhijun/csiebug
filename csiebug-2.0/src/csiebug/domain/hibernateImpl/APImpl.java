package csiebug.domain.hibernateImpl;

import java.util.HashSet;
import java.util.Set;

import csiebug.domain.AP;
import csiebug.domain.Function;

/**
 * 
 * @author George_Tsai
 * @version 2009/7/16
 *
 */
public class APImpl extends ResourceImpl implements AP {
	private static final long serialVersionUID = 1L;
	
	private String apId;
	private String apName;
	private String apLogo;
	private String apIndexURL;
	
	private Set<Function> functions = new HashSet<Function>();
	
	public void setApId(String apId) {
		this.apId = apId;
	}
	public String getApId() {
		return apId;
	}
	public void setApName(String apName) {
		this.apName = apName;
	}
	public String getApName() {
		return apName;
	}
	public void setApLogo(String apLogo) {
		this.apLogo = apLogo;
	}
	public String getApLogo() {
		return apLogo;
	}
	public void setApIndexURL(String apIndexURL) {
		this.apIndexURL = apIndexURL;
	}
	public String getApIndexURL() {
		return apIndexURL;
	}
	public void setFunctions(Set<Function> functions) {
		this.functions = functions;
	}
	public Set<Function> getFunctions() {
		return this.functions;
	}
	public void addFunction(Function function) {
		functions.add(function);
	}
	public void removeFunction(Function function) {
		functions.remove(function);
	}
}
