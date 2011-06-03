package csiebug.test.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import csiebug.dao.CodeDAO;
import csiebug.domain.Code;
import csiebug.domain.DomainObjectFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springTest.xml" })
public class CodeDAOTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private CodeDAO codeDAO;
	@Autowired
	private DomainObjectFactory domainObjectFactory;
	
	@Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }
	
	@Test
	public void testSaveCode() throws Exception {
		Code code = domainObjectFactory.createCode();
		code.setCodeId("testCodeId");
		code.setCodeType("testCodeType");
		code.setCodeValue("測試值");
		code.setCodeDescription("測試說明");
		code.setEnabled(true);
		code.setCreateUserId("admin");
		code.setCreateDate(Calendar.getInstance());
		
		codeDAO.insert(code);
		
		List<Code> list = codeDAO.search(code);
		
		assertEquals(1, list.size());
		assertEquals(code, list.get(0));
	}
	
	@Test
	public void testDeleteCode() throws Exception {
		prepareData();
		
		Code code = domainObjectFactory.createCode();
		code.setCodeId("testCodeId");
		code.setCodeType("testCodeType");
		codeDAO.deleteMatchObjects(code);
		
		List<Code> list = codeDAO.search(code);
		
		assertEquals(0, list.size());
	}
	
	private Code prepareData() throws Exception {
		Code code = domainObjectFactory.createCode();
		code.setCodeId("testCodeId");
		code.setCodeType("testCodeType");
		code.setCodeValue("測試值");
		code.setCodeDescription("測試說明");
		code.setEnabled(true);
		code.setCreateUserId("admin");
		code.setCreateDate(Calendar.getInstance());
		
		codeDAO.insert(code);
		
		return code;
	}
	
	@Test
	public void testSearchCodes() throws Exception {
		Code code = prepareData();
		
		Code search1 = domainObjectFactory.createCode();
		search1.setCodeId("testCodeId");
		testForSearchCodes(code, search1);
		
		Code search2 = domainObjectFactory.createCode();
		search2.setCodeType("testCodeType");
		testForSearchCodes(code, search2);
		
		Code search3 = domainObjectFactory.createCode();
		search3.setCodeId("testCodeId");
		search3.setCodeDescription("測試");
		testForSearchCodes(code, search3);		
	}
	
	private void testForSearchCodes(Code code, Code search) throws Exception {
		List<Code> list = codeDAO.search(search);
		assertEquals(1, list.size());
		assertEquals(code, list.get(0));
	}
	
	@Test
	public void testDeleteArray() throws Exception {
		Code[] codes = prepareArrayData();
		
		codeDAO.delete(codes);
		
		Code code = domainObjectFactory.createCode();
		code.setCodeDescription("測試說明_");
		
		List<Code> list = codeDAO.search(code);
		
		assertEquals(0, list.size());
	}
	
	@Test
	public void testDeleteList() throws Exception {
		List<Code> list = prepareListData();
		
		codeDAO.delete(list);
		
		Code code = domainObjectFactory.createCode();
		code.setCodeDescription("測試說明_");
		
		List<Code> list2 = codeDAO.search(code);
		
		assertEquals(0, list2.size());
	}
	
	@Test
	public void testDeleteSet() throws Exception {
		Set<Code> set = prepareSetData();
		
		codeDAO.delete(set);
		
		Code code = domainObjectFactory.createCode();
		code.setCodeDescription("測試說明_");
		
		List<Code> list = codeDAO.search(code);
		
		assertEquals(0, list.size());
	}
	
	private Code[] prepareArrayData() throws Exception {
		Code[] codes = new Code[10];
		
		for(int i = 0; i < 10; i++) {
			Code code = domainObjectFactory.createCode();
			code.setCodeId("testCodeId_" + i);
			code.setCodeType("testCodeType_" + i);
			code.setCodeValue("測試值_" + i);
			code.setCodeDescription("測試說明_" + i);
			code.setEnabled(true);
			code.setCreateUserId("admin");
			code.setCreateDate(Calendar.getInstance());
			
			codeDAO.insert(code);
			codes[i] = code;
		}
		
		return codes;
	}
	
	private List<Code> prepareListData() throws Exception {
		List<Code> list = new ArrayList<Code>();
		
		for(int i = 0; i < 10; i++) {
			Code code = domainObjectFactory.createCode();
			code.setCodeId("testCodeId_" + i);
			code.setCodeType("testCodeType_" + i);
			code.setCodeValue("測試值_" + i);
			code.setCodeDescription("測試說明_" + i);
			code.setEnabled(true);
			code.setCreateUserId("admin");
			code.setCreateDate(Calendar.getInstance());
			
			codeDAO.insert(code);
			list.add(code);
		}
		
		return list;
	}
	
	private Set<Code> prepareSetData() throws Exception {
		Set<Code> set = new HashSet<Code>();
		
		for(int i = 0; i < 10; i++) {
			Code code = domainObjectFactory.createCode();
			code.setCodeId("testCodeId_" + i);
			code.setCodeType("testCodeType_" + i);
			code.setCodeValue("測試值_" + i);
			code.setCodeDescription("測試說明_" + i);
			code.setEnabled(true);
			code.setCreateUserId("admin");
			code.setCreateDate(Calendar.getInstance());
			
			codeDAO.insert(code);
			set.add(code);
		}
		
		return set;
	}
	
	@Test
	public void testSearchPaginationCodes() throws Exception {
		List<Code> list = preparePaginationData();
		Code searchAll = domainObjectFactory.createCode();
		searchAll.setCodeDescription("測試說明_");
		List<Code> list2 = codeDAO.searchPagination(searchAll, 0, 3);
		
		assertEquals(3, list2.size());
		
		for(int i = 0; i < 3; i++) {
			assertEquals(list.get(i), list2.get(i));
		}
	}
	
	@Test
	public void testSearchRowCountCodes() throws Exception {
		List<Code> list = preparePaginationData();
		Code searchAll = domainObjectFactory.createCode();
		searchAll.setCodeDescription("測試說明_");
		
		assertEquals(list.size(), codeDAO.searchRowCount(searchAll));
	}
	
	private List<Code> preparePaginationData() throws Exception {
		List<Code> list = new ArrayList<Code>();
		
		for(int i = 0; i < 10; i++) {
			Code code = domainObjectFactory.createCode();
			code.setCodeId("testCodeId_" + i);
			code.setCodeType("testCodeType_" + i);
			code.setCodeValue("測試值_" + i);
			code.setCodeDescription("測試說明_" + i);
			code.setEnabled(true);
			code.setCreateUserId("admin");
			code.setCreateDate(Calendar.getInstance());
			
			codeDAO.insert(code);
			list.add(code);
		}
		
		return list;
	}
}
