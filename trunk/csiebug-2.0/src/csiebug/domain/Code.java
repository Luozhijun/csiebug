package csiebug.domain;


/**
 * 
 * @author George_Tsai
 * @version 2009/7/17
 *
 */
public interface Code extends BasicObject {
	void setCodeId(String codeId);
	String getCodeId();
	void setCodeType(String codeType);
	String getCodeType();
	void setCodeValue(String codeValue);
	String getCodeValue();
	void setCodeDescription(String codeDescription);
	String getCodeDescription();
	void setEnabled(Boolean enabled);
	Boolean getEnabled();
}
