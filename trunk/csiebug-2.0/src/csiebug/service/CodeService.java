package csiebug.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import csiebug.dao.CodeDAO;
import csiebug.domain.Code;
import csiebug.util.DateFormatException;

/**
 * Code Service
 * @author George_Tsai
 *
 */
public interface CodeService extends BasicService {
	void setCodeDAO(CodeDAO dao);
	
	/**
	 * 搜尋符合的代碼
	 * @param voObj
	 * @return
	 * @throws ServiceException
	 */
	List<Code> searchCodes(Code voObj) throws ServiceException;
	
	/**
	 * 搜尋符合的代碼(pagination)
	 * @param voObj
	 * @return
	 * @throws ServiceException
	 */
	List<Code> searchCodesPagination(Code voObj, int maxResults) throws ServiceException;
	
	/**
	 * 儲存代碼
	 * @param user
	 * @throws ServiceException
	 */
	void saveCode(Code obj) throws ServiceException;
	
	/**
	 * 刪除代碼
	 * @param codes
	 * @throws ServiceException
	 */
	void deleteCodes(List<Code> codes) throws ServiceException;
	
	/**
	 * 將符合條件的code的id,value轉成Map
	 * @param voObj
	 * @param dateFormat
	 * @return
	 * @throws ServiceException
	 * @throws IllegalArgumentException
	 * @throws DateFormatException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	Map<String, String> getCodeMap(Code voObj, int dateFormat) throws ServiceException, IllegalArgumentException, DateFormatException, IllegalAccessException, InvocationTargetException;
	
	/**
	 * 取出enabled的所有CodeType出來轉成Map
	 * @param dateFormat
	 * @return
	 * @throws ServiceException
	 */
	Map<String, String> getCodeTypes(int dateFormat) throws ServiceException, IllegalArgumentException, DateFormatException, IllegalAccessException, InvocationTargetException;
}
