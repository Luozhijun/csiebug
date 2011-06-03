package csiebug.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import csiebug.dao.DAOException;
import csiebug.dao.CodeDAO;
import csiebug.domain.Code;
import csiebug.domain.DomainObjectFactory;
import csiebug.service.ServiceException;
import csiebug.service.CodeService;
import csiebug.service.LazyLoadingArrayList;
import csiebug.util.DateFormatException;
import csiebug.util.ListUtility;

public class CodeServiceImpl extends BasicServiceImpl implements CodeService {
	private static final long serialVersionUID = 1L;
	
	private DomainObjectFactory domainObjectFactory;
	private CodeDAO codeDAO;
	
	public void setDomainObjectFactory(DomainObjectFactory domainObjectFactory) {
		this.domainObjectFactory = domainObjectFactory;
	}
	
	public void setCodeDAO(CodeDAO dao) {
		codeDAO = dao;
	}
	
	public List<Code> searchCodes(Code voObj) throws ServiceException {
		try {
			return codeDAO.search(voObj);
		} catch (DAOException e) {
			throw new ServiceException("CodeDAO search error!", e);
		}
	}
	
	public List<Code> searchCodesPagination(Code voObj, int maxResults) throws ServiceException {
		LazyLoadingArrayList<Code> list = new LazyLoadingArrayList<Code>() {
			private static final long serialVersionUID = 1L;
		};
		list.setDao(codeDAO);
		list.setMethodName("search");
		list.setParameters(new Object[]{voObj});
		list.setRowsPerPage(maxResults);
		
		return list;
	}
	
	public void saveCode(Code obj) throws ServiceException {
		notNull(obj, "Can not save null!");
		
		try {
			codeDAO.insertOrUpdate(obj);
		} catch (DAOException e) {
			throw new ServiceException("CodeDAO insert/update error!", e);
		}
	}
	
	public void deleteCode(Code code) throws ServiceException {
		notNull(code, "Can not delete null!");
		
		try {
			codeDAO.delete(code);
		} catch (DAOException e) {
			throw new ServiceException("CodeDAO delete code error!", e);
		}
	}
	
	public Map<String, String> getCodeMap(Code voObj, int dateFormat) throws ServiceException, IllegalArgumentException, DateFormatException, IllegalAccessException, InvocationTargetException {
		try {
			return ListUtility.toMap(ListUtility.castList(ListUtility.toList(codeDAO.search(voObj), Code.class), dateFormat), "codeId", "codeValue");
		} catch (DAOException e) {
			throw new ServiceException("CodeDAO search error!", e);
		}
	}
	
	public Map<String, String> getCodeTypes(int dateFormat) throws ServiceException, IllegalArgumentException, DateFormatException, IllegalAccessException, InvocationTargetException {
		try {
			Code voObj = domainObjectFactory.createCode();
			voObj.setCodeType("CodeType");
			voObj.setEnabled(true);
			return ListUtility.toMap(ListUtility.castList(ListUtility.toList(codeDAO.search(voObj), Code.class), dateFormat), "codeId", "codeValue");
		} catch (DAOException e) {
			throw new ServiceException("CodeDAO search error!", e);
		}
	}
}
