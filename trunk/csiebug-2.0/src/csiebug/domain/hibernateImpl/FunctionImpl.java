package csiebug.domain.hibernateImpl;

import csiebug.domain.Function;
import csiebug.domain.AP;

/**
 * 
 * @author George_Tsai
 * @version 2009/7/16
 *
 */
public class FunctionImpl extends ResourceImpl implements Function {
	private static final long serialVersionUID = 1L;
	
	private String apId;
	private String functionId;
	private String parentId;
	private String functionName;
	private String functionURL;
	private String functionLogo;
	private Integer SortOrder;
	private AP ap;
	
	public void setApId(String apId) {
		this.apId = apId;
	}
	public String getApId() {
		return apId;
	}
	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}
	public String getFunctionId() {
		return functionId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getParentId() {
		return parentId;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionURL(String functionURL) {
		this.functionURL = functionURL;
	}
	public String getFunctionURL() {
		return functionURL;
	}
	public void setFunctionLogo(String functionLogo) {
		this.functionLogo = functionLogo;
	}
	public String getFunctionLogo() {
		return functionLogo;
	}
	public void setSortOrder(Integer sortOrder) {
		SortOrder = sortOrder;
	}
	public Integer getSortOrder() {
		return SortOrder;
	}
	public void setAp(AP ap) {
		this.ap = ap;
		setApId(ap.getApId());
	}
	public AP getAp() {
		return ap;
	}
}
