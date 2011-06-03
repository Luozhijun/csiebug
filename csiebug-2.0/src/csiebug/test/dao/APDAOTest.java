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

import csiebug.dao.APDAO;
import csiebug.domain.AP;
import csiebug.domain.DomainObjectFactory;
import csiebug.domain.Function;
import csiebug.domain.Role;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springTest.xml" })
public class APDAOTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private APDAO apDAO;
	@Autowired
	private DomainObjectFactory domainObjectFactory;
	
	@Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }
	
	@Test
	public void testInsertAP() throws Exception {
		AP ap = domainObjectFactory.createAP();
		ap.setId("testResourceId");
		ap.setApId("testAPId");
		ap.setApName("測試AP");
		ap.setApIndexURL("testAction");
		ap.setCreateUserId("admin");
		ap.setCreateDate(Calendar.getInstance());
		
		Function function = domainObjectFactory.createFunction();
		function.setId("testResourceId2");
		function.setApId(ap.getApId());
		function.setFunctionId("testFunctionId");
		function.setAp(ap);
		ap.addFunction(function);
		
		Role role = domainObjectFactory.createRole();
		role.setId("testRole");
		role.setRoleName("測試角色");
		role.setCreateUserId("admin");
		role.setCreateDate(Calendar.getInstance());
		ap.addAuthorityResource(role);
		
		apDAO.insert(ap);
		
		List<AP> list = apDAO.search(ap);
		
		assertEquals(1, list.size());
		assertEquals(ap, list.get(0));
		assertEquals(1, list.get(0).getAuthorityResources().size());
		assertEquals(role, list.get(0).getAuthorityResources().iterator().next());
		assertEquals(1, list.get(0).getFunctions().size());
		assertEquals(function, list.get(0).getFunctions().iterator().next());
	}
	
	@Test
	public void testSaveAP() throws Exception {
		prepareData();
		
		AP search = domainObjectFactory.createAP();
		search.setId("testResourceId");
		search.setApId("testAPId");
		
		AP ap = (AP) apDAO.search(search).get(0);
		ap.setApName("測試AP改名");
		ap.setApIndexURL("testAction");
		ap.setModifyUserId("admin");
		ap.setModifyDate(Calendar.getInstance());
		
		apDAO.insert(ap);
		
		List<AP> list = apDAO.search(ap);
		
		assertEquals(1, list.size());
		assertEquals(ap, list.get(0));
	}
	
	@Test
	public void testDeleteAP() throws Exception {
		prepareData();
		
		AP ap = domainObjectFactory.createAP();
		ap.setId("testResourceId");
		apDAO.deleteMatchObjects(ap);
		
		List<AP> list = apDAO.search(ap);
		
		assertEquals(0, list.size());
	}
	
	@Test
	public void testDeleteArray() throws Exception {
		AP[] list = prepareArrayData();
		
		apDAO.delete(list);
		
		AP ap = domainObjectFactory.createAP();
		ap.setApName("測試AP_");
		
		List<AP> list2 = apDAO.search(ap);
		
		assertEquals(0, list2.size());
	}
	
	@Test
	public void testDeleteList() throws Exception {
		List<AP> list = prepareListData();
		
		apDAO.delete(list);
		
		AP ap = domainObjectFactory.createAP();
		ap.setApName("測試AP_");
		
		List<AP> list2 = apDAO.search(ap);
		
		assertEquals(0, list2.size());
	}
	
	@Test
	public void testDeleteSet() throws Exception {
		Set<AP> list = prepareSetData();
		
		apDAO.delete(list);
		
		AP ap = domainObjectFactory.createAP();
		ap.setApName("測試AP_");
		
		List<AP> list2 = apDAO.search(ap);
		
		assertEquals(0, list2.size());
	}
	
	private AP prepareData() throws Exception {
		AP ap = domainObjectFactory.createAP();
		ap.setId("testResourceId");
		ap.setApId("testAPId");
		ap.setApName("測試AP");
		ap.setApIndexURL("testAction");
		ap.setCreateUserId("admin");
		ap.setCreateDate(Calendar.getInstance());
		
		Function function = domainObjectFactory.createFunction();
		function.setId("testResourceId2");
		function.setApId(ap.getApId());
		function.setFunctionId("testFunctionId");
		function.setAp(ap);
		ap.addFunction(function);
		
		Role role = domainObjectFactory.createRole();
		role.setId("testRole");
		role.setRoleName("測試角色");
		role.setCreateUserId("admin");
		role.setCreateDate(Calendar.getInstance());
		ap.addAuthorityResource(role);
		
		apDAO.insert(ap);
		
		return ap;
	}
	
	private AP[] prepareArrayData() throws Exception {
		AP[] aps = new AP[10];
		
		for(int i = 0; i < 10; i++) {
			AP ap = domainObjectFactory.createAP();
			ap.setId("testResourceId_" + i);
			ap.setApId("testAPId_" + i);
			ap.setApName("測試AP_" + i);
			ap.setApIndexURL("testAction_" + i);
			ap.setCreateUserId("admin");
			ap.setCreateDate(Calendar.getInstance());
			
			apDAO.insert(ap);
			aps[i] = ap;
		}
		
		return aps;
	}
	
	private List<AP> prepareListData() throws Exception {
		List<AP> list = new ArrayList<AP>();
		
		for(int i = 0; i < 10; i++) {
			AP ap = domainObjectFactory.createAP();
			ap.setId("testResourceId_" + i);
			ap.setApId("testAPId_" + i);
			ap.setApName("測試AP_" + i);
			ap.setApIndexURL("testAction_" + i);
			ap.setCreateUserId("admin");
			ap.setCreateDate(Calendar.getInstance());
			
			apDAO.insert(ap);
			list.add(ap);
		}
		
		return list;
	}
	
	private Set<AP> prepareSetData() throws Exception {
		Set<AP> set = new HashSet<AP>();
		
		for(int i = 0; i < 10; i++) {
			AP ap = domainObjectFactory.createAP();
			ap.setId("testResourceId_" + i);
			ap.setApId("testAPId_" + i);
			ap.setApName("測試AP_" + i);
			ap.setApIndexURL("testAction_" + i);
			ap.setCreateUserId("admin");
			ap.setCreateDate(Calendar.getInstance());
			
			apDAO.insert(ap);
			set.add(ap);
		}
		
		return set;
	}
	
	@Test
	public void testSearchAPs() throws Exception {
		AP ap = prepareData();
		
		AP search1 = domainObjectFactory.createAP();
		search1.setId("testResourceId");
		testForSearchAPs(ap, search1);
		
		AP search2 = domainObjectFactory.createAP();
		search2.setApId("testAPId");
		testForSearchAPs(ap, search2);
		
		AP search3 = domainObjectFactory.createAP();
		search3.setApId("testAPId");
		search3.setApName("測試A");
		testForSearchAPs(ap, search3);
	}
	
	private void testForSearchAPs(AP ap, AP search) throws Exception {
		List<AP> list = apDAO.search(search);
		assertEquals(1, list.size());
		assertEquals(ap, list.get(0));
	}
	
	@Test
	public void testSearchPaginationAPs() throws Exception {
		List<AP> list = preparePaginationData();
		AP searchAll = domainObjectFactory.createAP();
		searchAll.setApName("測試A");
		List<AP> list2 = apDAO.searchPagination(searchAll, 0, 3);
		
		assertEquals(3, list2.size());
		
		for(int i = 0; i < 3; i++) {
			assertEquals(list.get(i), list2.get(i));
		}
	}
	
	@Test
	public void testSearchRowCountAPs() throws Exception {
		List<AP> list = preparePaginationData();
		AP searchAll = domainObjectFactory.createAP();
		searchAll.setApName("測試A");
		
		assertEquals(list.size(), apDAO.searchRowCount(searchAll));
	}
	
	private List<AP> preparePaginationData() throws Exception {
		List<AP> list = new ArrayList<AP>();
		
		for(int i = 0; i < 10; i++) {
			AP ap = domainObjectFactory.createAP();
			ap.setId("testResourceId_" + i);
			ap.setApId("testAPId_" + i);
			ap.setApName("測試AP_" + i);
			ap.setApIndexURL("testAction_" + i);
			ap.setCreateUserId("admin");
			ap.setCreateDate(Calendar.getInstance());
			
			apDAO.insert(ap);
			list.add(ap);
		}
		
		return list;
	}
	
}
