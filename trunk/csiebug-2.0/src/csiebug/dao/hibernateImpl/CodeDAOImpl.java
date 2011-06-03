package csiebug.dao.hibernateImpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import csiebug.domain.Code;
import csiebug.domain.hibernateImpl.CodeImpl;
import csiebug.dao.CodeDAO;
import csiebug.dao.DAOException;
import csiebug.util.AssertUtility;

/**
 * 
 * @author George_Tsai
 * @version 2009/7/18
 *
 */
public class CodeDAOImpl extends BasicDAOImpl implements CodeDAO {
	private static final long serialVersionUID = 1L;
	
	private void addRestriction(Code code) throws DAOException {
		if(AssertUtility.isNotNullAndNotSpace(code.getCodeId())) {
			add(Restrictions.eq("codeId", code.getCodeId()));
		}
		
		if(AssertUtility.isNotNullAndNotSpace(code.getCodeType())) {
			add(Restrictions.eq("codeType", code.getCodeType()));
		}
		
		if(AssertUtility.isNotNullAndNotSpace(code.getCodeValue())) {
			add(Restrictions.eq("codeValue", code.getCodeValue()));
		}
		
		if(AssertUtility.isNotNullAndNotSpace(code.getCodeDescription())) {
			add(Restrictions.like("codeDescription", "%" + code.getCodeDescription() + "%"));
		}
		
		if(code.getEnabled() != null) {
			add(Restrictions.eq("enabled", code.getEnabled()));
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Code> search(Object obj) throws DAOException {
		setTable(CodeImpl.class);
		
		if(obj != null) {
			Code code = (Code)obj;
			
			addRestriction(code);
		}
		
		return (List<Code>)query();
	}
	
	@SuppressWarnings("unchecked")
	public List<Code> searchPagination(Object obj, int firstResult, int maxResults) throws DAOException {
		setTable(CodeImpl.class);
		
		if(obj != null) {
			Code code = (Code)obj;
			
			addRestriction(code);
		}
		
		setFirstResult(firstResult);
		setMaxResults(maxResults);
		
		return (List<Code>)query();
	}
	
	public int searchRowCount(Object obj) throws DAOException {
		setTable(CodeImpl.class);
		setProjection(Projections.rowCount());
		
		if(obj != null) {
			Code code = (Code)obj;
			
			addRestriction(code);
		}
		
		return ((Integer)query().get(0)).intValue();
	}
	
	public void delete(Object[] objs) throws DAOException {
		if(AssertUtility.isAllElementNotNull(objs)) {
			String hql = "delete CodeImpl where :whereCondition";
			StringBuffer whereCondition = new StringBuffer();
			HashMap<String, String> parameters = new HashMap<String, String>();
			
			for(int i = 0; i < objs.length; i++) {
				if(i != 0) {
					whereCondition.append(" or ");
				}
				
				Code code = (Code)objs[i];
				parameters.put("codeId_" + i, code.getCodeId());
				parameters.put("codeType_" + i, code.getCodeType());
				whereCondition.append("(codeId = :codeId_" + i + " and codeType = :codeType_" + i + ")");
			}
			
			hql = hql.replace(":whereCondition", whereCondition.toString());
			executeUpdate(hql, parameters);
		}
	}

	public void delete(List<?> list) throws DAOException {
		if(AssertUtility.isAllElementNotNull(list)) {
			String hql = "delete CodeImpl where :whereCondition";
			StringBuffer whereCondition = new StringBuffer();
			HashMap<String, String> parameters = new HashMap<String, String>();
			
			for(int i = 0; i < list.size(); i++) {
				if(i != 0) {
					whereCondition.append(" or ");
				}
				
				Code code = (Code)list.get(i);
				parameters.put("codeId_" + i, code.getCodeId());
				parameters.put("codeType_" + i, code.getCodeType());
				whereCondition.append("(codeId = :codeId_" + i + " and codeType = :codeType_" + i + ")");
			}
			
			hql = hql.replace(":whereCondition", whereCondition.toString());
			executeUpdate(hql, parameters);
		}
	}

	public void delete(Set<?> set) throws DAOException {
		if(AssertUtility.isAllElementNotNull(set)) {
			String hql = "delete CodeImpl where :whereCondition";
			StringBuffer whereCondition = new StringBuffer();
			HashMap<String, String> parameters = new HashMap<String, String>();
			
			Iterator<?> iterator = set.iterator();
			
			int i = 0;
			while(iterator.hasNext()) {
				if(i != 0) {
					whereCondition.append(" or ");
				}
				
				Code code = (Code)iterator.next();
				parameters.put("codeId_" + i, code.getCodeId());
				parameters.put("codeType_" + i, code.getCodeType());
				whereCondition.append("(codeId = :codeId_" + i + " and codeType = :codeType_" + i + ")");
				
				i++;
			}
			
			hql = hql.replace(":whereCondition", whereCondition.toString());
			executeUpdate(hql, parameters);
		}
	}
}
