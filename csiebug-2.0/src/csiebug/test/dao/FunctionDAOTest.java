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

import csiebug.dao.FunctionDAO;
import csiebug.domain.AP;
import csiebug.domain.Function;
import csiebug.domain.DomainObjectFactory;
import csiebug.domain.Role;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springTest.xml" })
public class FunctionDAOTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private FunctionDAO functionDAO;
	@Autowired
	private DomainObjectFactory domainObjectFactory;
	
	@Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }
	
	@Test
	public void testSaveFunction() throws Exception {
		Function function = domainObjectFactory.createFunction();
		function.setId("testResourceId");
		function.setFunctionId("testFunctionId");
		function.setFunctionName("測試Function");
		function.setFunctionURL("testAction");
		function.setSortOrder(0);
		function.setCreateUserId("admin");
		function.setCreateDate(Calendar.getInstance());
		
		AP ap = domainObjectFactory.createAP();
		ap.setId("testResourceId2");
		ap.setApId("testAPId");
		ap.setApName("測試AP");
		ap.setApIndexURL("testAction");
		ap.setCreateUserId("admin");
		ap.setCreateDate(Calendar.getInstance());
		function.setAp(ap);
		
		Role role = domainObjectFactory.createRole();
		role.setId("testRole");
		role.setRoleName("測試角色");
		role.setCreateUserId("admin");
		role.setCreateDate(Calendar.getInstance());
		function.addAuthorityResource(role);
		
		functionDAO.insert(function);
		
		List<Function> list = functionDAO.search(function);
		
		assertEquals(1, list.size());
		assertEquals(function, list.get(0));
		assertEquals(1, list.get(0).getAuthorityResources().size());
		assertEquals(role, list.get(0).getAuthorityResources().iterator().next());
		assertEquals(ap, list.get(0).getAp());
	}
	
	@Test
	public void testDeleteFunction() throws Exception {
		prepareData();
		
		Function function = domainObjectFactory.createFunction();
		function.setId("testResourceId");
		functionDAO.deleteMatchObjects(function);
		
		List<Function> list = functionDAO.search(function);
		
		assertEquals(0, list.size());
	}
	
	@Test
	public void testDeleteArray() throws Exception {
		Function[] functions = prepareArrayData();
		
		functionDAO.delete(functions);
		
		Function function = domainObjectFactory.createFunction();
		function.setFunctionName("測試Function_");
		
		List<Function> list = functionDAO.search(function);
		
		assertEquals(0, list.size());
	}
	
	@Test
	public void testDeleteList() throws Exception {
		List<Function> list = prepareListData();
		
		functionDAO.delete(list);
		
		Function function = domainObjectFactory.createFunction();
		function.setFunctionName("測試Function_");
		
		List<Function> list2 = functionDAO.search(function);
		
		assertEquals(0, list2.size());
	}
	
	@Test
	public void testDeleteSet() throws Exception {
		Set<Function> set = prepareSetData();
		
		functionDAO.delete(set);
		
		Function function = domainObjectFactory.createFunction();
		function.setFunctionName("測試Function_");
		
		List<Function> list = functionDAO.search(function);
		
		assertEquals(0, list.size());
	}
	
	private Function prepareData() throws Exception {
		Function function = domainObjectFactory.createFunction();
		function.setId("testResourceId");
		function.setFunctionId("testFunctionId");
		function.setFunctionName("測試Function");
		function.setFunctionURL("testAction");
		function.setSortOrder(0);
		function.setCreateUserId("admin");
		function.setCreateDate(Calendar.getInstance());
		
		AP ap = domainObjectFactory.createAP();
		ap.setId("testResourceId2");
		ap.setApId("testAPId");
		ap.setApName("測試AP");
		ap.setApIndexURL("testAction");
		ap.setCreateUserId("admin");
		ap.setCreateDate(Calendar.getInstance());
		function.setAp(ap);
		
		Role role = domainObjectFactory.createRole();
		role.setId("testRole");
		role.setRoleName("測試角色");
		role.setCreateUserId("admin");
		role.setCreateDate(Calendar.getInstance());
		function.addAuthorityResource(role);
		
		functionDAO.insert(function);
		
		return function;
	}
	
	private Function[] prepareArrayData() throws Exception {
		Function[] functions = new Function[10];
		
		AP ap = domainObjectFactory.createAP();
		ap.setId("testResourceId2");
		ap.setApId("testAPId");
		ap.setApName("測試AP");
		ap.setApIndexURL("testAction");
		ap.setCreateUserId("admin");
		ap.setCreateDate(Calendar.getInstance());
		
		for(int i = 0; i < 10; i++) {
			Function function = domainObjectFactory.createFunction();
			function.setId("testResourceId_" + i);
			function.setFunctionId("testFunctionId_" + i);
			function.setFunctionName("測試Function_" + i);
			function.setFunctionURL("testAction_" + i);
			function.setSortOrder(i);
			function.setCreateUserId("admin");
			function.setCreateDate(Calendar.getInstance());
			
			function.setAp(ap);
			
			functionDAO.insert(function);
			functions[i] = function;
		}
		
		return functions;
	}
	
	private List<Function> prepareListData() throws Exception {
		List<Function> list = new ArrayList<Function>();
		
		AP ap = domainObjectFactory.createAP();
		ap.setId("testResourceId2");
		ap.setApId("testAPId");
		ap.setApName("測試AP");
		ap.setApIndexURL("testAction");
		ap.setCreateUserId("admin");
		ap.setCreateDate(Calendar.getInstance());
		
		for(int i = 0; i < 10; i++) {
			Function function = domainObjectFactory.createFunction();
			function.setId("testResourceId_" + i);
			function.setFunctionId("testFunctionId_" + i);
			function.setFunctionName("測試Function_" + i);
			function.setFunctionURL("testAction_" + i);
			function.setSortOrder(i);
			function.setCreateUserId("admin");
			function.setCreateDate(Calendar.getInstance());
			
			function.setAp(ap);
			
			functionDAO.insert(function);
			list.add(function);
		}
		
		return list;
	}
	
	private Set<Function> prepareSetData() throws Exception {
		Set<Function> set = new HashSet<Function>();
		
		AP ap = domainObjectFactory.createAP();
		ap.setId("testResourceId2");
		ap.setApId("testAPId");
		ap.setApName("測試AP");
		ap.setApIndexURL("testAction");
		ap.setCreateUserId("admin");
		ap.setCreateDate(Calendar.getInstance());
		
		for(int i = 0; i < 10; i++) {
			Function function = domainObjectFactory.createFunction();
			function.setId("testResourceId_" + i);
			function.setFunctionId("testFunctionId_" + i);
			function.setFunctionName("測試Function_" + i);
			function.setFunctionURL("testAction_" + i);
			function.setSortOrder(i);
			function.setCreateUserId("admin");
			function.setCreateDate(Calendar.getInstance());
			
			function.setAp(ap);
			
			functionDAO.insert(function);
			set.add(function);
		}
		
		return set;
	}
	
	@Test
	public void testSearchFunctions() throws Exception {
		Function function = prepareData();
		
		Function search1 = domainObjectFactory.createFunction();
		search1.setId("testResourceId");
		testForSearchFunctions(function, search1);
		
		Function search2 = domainObjectFactory.createFunction();
		search2.setApId("testAPId");
		testForSearchFunctions(function, search2);
		
		Function search3 = domainObjectFactory.createFunction();
		search3.setApId("testAPId");
		search3.setFunctionName("測試F");
		testForSearchFunctions(function, search3);		
	}
	
	private void testForSearchFunctions(Function function, Function search) throws Exception {
		List<Function> list = functionDAO.search(search);
		assertEquals(1, list.size());
		assertEquals(function, list.get(0));
	}
	
	@Test
	public void testSearchPaginationFunctions() throws Exception {
		List<Function> list = preparePaginationData();
		Function searchAll = domainObjectFactory.createFunction();
		searchAll.setFunctionName("測試Function_");
		List<Function> list2 = functionDAO.searchPagination(searchAll, 0, 3);
		
		assertEquals(3, list2.size());
		
		for(int i = 0; i < 3; i++) {
			assertEquals(list.get(i), list2.get(i));
		}
	}
	
	@Test
	public void testSearchRowCountFunctions() throws Exception {
		List<Function> list = preparePaginationData();
		Function searchAll = domainObjectFactory.createFunction();
		searchAll.setFunctionName("測試Function_");
		
		assertEquals(list.size(), functionDAO.searchRowCount(searchAll));
	}
	
	private List<Function> preparePaginationData() throws Exception {
		List<Function> list = new ArrayList<Function>();
		
		AP ap = domainObjectFactory.createAP();
		ap.setId("testResourceId2");
		ap.setApId("testAPId");
		ap.setApName("測試AP");
		ap.setApIndexURL("testAction");
		ap.setCreateUserId("admin");
		ap.setCreateDate(Calendar.getInstance());
		
		for(int i = 0; i < 10; i++) {
			Function function = domainObjectFactory.createFunction();
			function.setId("testResourceId_" + i);
			function.setFunctionId("testFunctionId_" + i);
			function.setFunctionName("測試Function_" + i);
			function.setFunctionURL("testAction_" + i);
			function.setSortOrder(i);
			function.setCreateUserId("admin");
			function.setCreateDate(Calendar.getInstance());
			function.setAp(ap);
			
			functionDAO.insert(function);
			list.add(function);
		}
		
		return list;
	}
}
