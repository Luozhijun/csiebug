package csiebug.test.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import csiebug.dao.DAOException;
import csiebug.dao.CodeDAO;
import csiebug.domain.DomainObjectFactory;
import csiebug.domain.Code;
import csiebug.service.CodeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springTest.xml" })
public class CodeServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private CodeService codeService;
	@Autowired
	private DomainObjectFactory domainObjectFactory;
	@Autowired
	private CodeDAO codeDAO;
	
	private CodeDAO mockCodeDAO;
	
	@Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }
	
	private void setUp() {
		mockCodeDAO = EasyMock.createStrictMock(CodeDAO.class);
		codeService.setCodeDAO(mockCodeDAO);
	}
	
	private void tearDown() {
		EasyMock.verify(mockCodeDAO);
		
		codeService.setCodeDAO(codeDAO);
	}
	
	private Code prepareCodeData(boolean insertFlag) throws Exception {
		Code code = domainObjectFactory.createCode();
		code.setCodeId("test");
		code.setCodeType("testType");
		code.setCodeValue("testValue");
		code.setCodeDescription("testDescription");
		code.setEnabled(true);
		code.setCreateUserId("admin");
		code.setCreateDate(Calendar.getInstance());
		
		if(insertFlag) {
			codeDAO.insert(code);
		}
		
		return code;
	}
	
	private void setUpForSearchCodes(Code voObj, Code code) throws DAOException {
		setUp();
		
		List<Code> expectList = new ArrayList<Code>();
		expectList.add(code);
		EasyMock.expect(mockCodeDAO.search(voObj)).andReturn(expectList);
		
		EasyMock.replay(mockCodeDAO);
	}
	
	@Test
	public void testSearchCodes() throws Exception {
		Code code = prepareCodeData(false);
		
		Code voObj = domainObjectFactory.createCode();
		voObj.setCodeId("test");
		
		setUpForSearchCodes(voObj, code);
		
		List<Code> list = codeService.searchCodes(voObj);
		
		assertEquals(1, list.size());
		assertEquals(code, list.get(0));
		
		tearDown();
	}
	
	private List<Code> prepareCodePaginationData(boolean insertFlag) throws Exception {
		List<Code> list = new ArrayList<Code>();
		
		for(int i = 0; i < 10; i++) {
			Code code = domainObjectFactory.createCode();
			code.setCodeId("test_" + i);
			code.setCodeType("testType_" + i);
			code.setCodeValue("testValue_" + i);
			code.setCodeDescription("testDescription_" + i);
			code.setEnabled(true);
			code.setCreateUserId("admin");
			code.setCreateDate(Calendar.getInstance());
			
			if(insertFlag) {
				codeDAO.insert(code);
			}
			list.add(code);
		}
		
		return list;
	}
	
	private List<Code> prepareCodePaginationData(int firstResult, int maxResults) throws Exception {
		List<Code> list = new ArrayList<Code>();
		
		for(int i = firstResult; i < (firstResult + maxResults); i++) {
			Code code = domainObjectFactory.createCode();
			code.setCodeId("test_" + i);
			code.setCodeType("testType_" + i);
			code.setCodeValue("testValue_" + i);
			code.setCodeDescription("testDescription_" + i);
			code.setEnabled(true);
			code.setCreateUserId("admin");
			code.setCreateDate(Calendar.getInstance());
			
			list.add(code);
		}
		
		return list;
	}
	
	private void setUpForSearchCodesPagination(Code voObj, List<Code> list) throws Exception {
		setUp();
		
		EasyMock.expect(mockCodeDAO.searchRowCount(voObj)).andReturn(list.size());
		for(int i = 0; i < 10; i = i + 2) {
			EasyMock.expect(mockCodeDAO.searchPagination(voObj, i, 2)).andReturn(prepareCodePaginationData(i, 2));
		}
		
		EasyMock.replay(mockCodeDAO);
	}
	
	@Test
	public void testSearchCodesPagination() throws Exception {
		List<Code> list = prepareCodePaginationData(false);
		
		Code voObj = domainObjectFactory.createCode();
		
		setUpForSearchCodesPagination(voObj, list);
		
		List<Code> list2 = codeService.searchCodesPagination(voObj, 2);
		
		assertEquals(list.size(), list2.size());
		
		for(int i = 0; i < list.size(); i++) {
			assertEquals(list.get(i), list2.get(i));
		}
		
		tearDown();
	}
	
	private void setUpForSaveCode(Code code) throws DAOException {
		setUp();
		
		mockCodeDAO.insertOrUpdate(code);
		List<Code> expectList = new ArrayList<Code>();
		expectList.add(code);
		EasyMock.expect(mockCodeDAO.search(code)).andReturn(expectList);
		
		EasyMock.replay(mockCodeDAO);
	}
	
	@Test
	public void testSaveCode() throws Exception {
		Code code = domainObjectFactory.createCode();
		code.setCodeId("test");
		code.setCodeType("testType");
		code.setCodeValue("testValue");
		code.setCodeDescription("testDescription");
		code.setEnabled(true);
		code.setCreateUserId("admin");
		code.setCreateDate(Calendar.getInstance());
		
		setUpForSaveCode(code);
		
		codeService.saveCode(code);
		
		List<Code> list = codeService.searchCodes(code);
		
		assertEquals(1, list.size());
		assertEquals(code, list.get(0));
		
		tearDown();
	}
	
	private void setUpForDeleteCode(Code code) throws DAOException {
		setUp();
		
		List<Code> expectList = new ArrayList<Code>();
		expectList.add(code);
		EasyMock.expect(mockCodeDAO.search(code)).andReturn(expectList);
		mockCodeDAO.delete(code);
		expectList = new ArrayList<Code>();
		EasyMock.expect(mockCodeDAO.search(code)).andReturn(expectList);
		
		EasyMock.replay(mockCodeDAO);
	}
	
	@Test
	public void testDeleteCode() throws Exception {
		Code code = prepareCodeData(false);
		
		setUpForDeleteCode(code);
		
		List<Code> list = codeService.searchCodes(code);
		
		assertEquals(1, list.size());
		assertEquals(code, list.get(0));
		
		codeService.deleteCode(code);
		
		list = codeService.searchCodes(code);
		
		assertEquals(0, list.size());
		
		tearDown();
	}
	
	private List<Code> prepareCodeDataForGetCodeMap(boolean insertFlag) throws Exception {
		Code code = domainObjectFactory.createCode();
		code.setCodeId("test");
		code.setCodeType("testType");
		code.setCodeValue("testValue");
		code.setCodeDescription("testDescription");
		code.setEnabled(true);
		code.setCreateUserId("admin");
		code.setCreateDate(Calendar.getInstance());
		
		Code code2 = domainObjectFactory.createCode();
		code2.setCodeId("test2");
		code2.setCodeType("testType");
		code2.setCodeValue("testValue2");
		code2.setCodeDescription("testDescription2");
		code2.setEnabled(true);
		code2.setCreateUserId("admin");
		code2.setCreateDate(Calendar.getInstance());
		
		if(insertFlag) {
			codeDAO.insert(code);
			codeDAO.insert(code2);
		}
		List<Code> list = new ArrayList<Code>();
		list.add(code);
		list.add(code2);
		
		return list;
	}
	
	private void setUpForGetCodeMap(Code voObj, List<Code> list) throws DAOException {
		setUp();
		
		EasyMock.expect(mockCodeDAO.search(voObj)).andReturn(list);
		
		EasyMock.replay(mockCodeDAO);
	}
	
	@Test
	public void testGetCodeMap() throws Exception {
		List<Code> list = prepareCodeDataForGetCodeMap(false);
		
		Code voObj = domainObjectFactory.createCode();
		voObj.setCodeType("testType");
		
		setUpForGetCodeMap(voObj, list);
		
		Map<String, String> map = codeService.getCodeMap(voObj, 23);
		
		assertEquals(2, map.size());
		assertEquals("testValue", map.get("test"));
		assertEquals("testValue2", map.get("test2"));
		
		tearDown();
	}
	
	private List<Code> prepareCodeDataForGetCodeTypes(boolean insertFlag) throws Exception {
		Code code = domainObjectFactory.createCode();
		code.setCodeId("test");
		code.setCodeType("testType");
		code.setCodeValue("testValue");
		code.setCodeDescription("testDescription");
		code.setEnabled(true);
		code.setCreateUserId("admin");
		code.setCreateDate(Calendar.getInstance());
		
		Code code2 = domainObjectFactory.createCode();
		code2.setCodeId("test2");
		code2.setCodeType("CodeType");
		code2.setCodeValue("testValue2");
		code2.setCodeDescription("testDescription2");
		code2.setEnabled(true);
		code2.setCreateUserId("admin");
		code2.setCreateDate(Calendar.getInstance());
		
		if(insertFlag) {
			codeDAO.insert(code);
			codeDAO.insert(code2);
		}
		List<Code> list = new ArrayList<Code>();
		list.add(code2);
		
		return list;
	}
	
	private void setUpForGetCodeTypes(Code voObj, List<Code> list) throws DAOException {
		setUp();
		
		EasyMock.expect(mockCodeDAO.search(voObj)).andReturn(list);
		
		EasyMock.replay(mockCodeDAO);
	}
	
	@Test
	public void testGetCodeTypes() throws Exception {
		List<Code> list = prepareCodeDataForGetCodeTypes(false);
		
		Code voObj = domainObjectFactory.createCode();
		voObj.setCodeType("CodeType");
		voObj.setEnabled(true);
		
		setUpForGetCodeTypes(voObj, list);
		
		Map<String, String> map = codeService.getCodeTypes(23);
		
		assertEquals(1, map.size());
		assertEquals("testValue2", map.get("test2"));
		
		tearDown();
	}
}
