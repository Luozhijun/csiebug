package csiebug.test.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import csiebug.dao.ResourceDAO;
import csiebug.domain.Resource;
import csiebug.domain.DomainObjectFactory;
import csiebug.domain.ResourceType;
import csiebug.domain.Role;
import csiebug.domain.URL;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springTest.xml" })
public class ResourceDAOTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private ResourceDAO resourceDAO;
	@Autowired
	private DomainObjectFactory domainObjectFactory;
	
	@javax.annotation.Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }
	
	@Test
	public void testSaveResource() throws Exception {
		URL resource = domainObjectFactory.createURL();
		resource.setId("testAction");
		resource.setResourceType(ResourceType.URL);
		resource.setCreateUserId("admin");
		resource.setCreateDate(Calendar.getInstance());
		
		Role role = domainObjectFactory.createRole();
		role.setId("testRole");
		role.setRoleName("測試角色");
		role.setCreateUserId("admin");
		role.setCreateDate(Calendar.getInstance());
		resource.addAuthorityResource(role);
		
		resourceDAO.insert(resource);
		
		List<Resource> list = resourceDAO.search(resource);
		
		assertEquals(1, list.size());
		assertEquals(resource, list.get(0));
		assertEquals(1, list.get(0).getAuthorityResources().size());
		assertEquals(role, list.get(0).getAuthorityResources().iterator().next());
	}
	
	@Test
	public void testDeleteResource() throws Exception {
		prepareData();
		
		Resource resource = domainObjectFactory.createResource();
		resource.setId("testAction");
		resourceDAO.deleteMatchObjects(resource);
		
		List<Resource> list = resourceDAO.search(resource);
		
		assertEquals(0, list.size());
	}
	
	@Test
	public void testDeleteArray() throws Exception {
		Resource[] resources = prepareArrayData();
		
		resourceDAO.delete(resources);
		
		Resource resource = domainObjectFactory.createResource();
		
		List<Resource> list = resourceDAO.search(resource);
		
		assertEquals(0, list.size());
	}
	
	@Test
	public void testDeleteList() throws Exception {
		List<Resource> list = prepareListData();
		
		resourceDAO.delete(list);
		
		Resource resource = domainObjectFactory.createResource();
		
		List<Resource> list2 = resourceDAO.search(resource);
		
		assertEquals(0, list2.size());
	}
	
	@Test
	public void testDeleteSet() throws Exception {
		Set<Resource> set = prepareSetData();
		
		resourceDAO.delete(set);
		
		Resource resource = domainObjectFactory.createResource();
		
		List<Resource> list = resourceDAO.search(resource);
		
		assertEquals(0, list.size());
	}
	
	private Resource prepareData() throws Exception {
		URL resource = domainObjectFactory.createURL();
		resource.setId("testAction");
		resource.setCreateUserId("admin");
		resource.setCreateDate(Calendar.getInstance());
		
		Role role = domainObjectFactory.createRole();
		role.setId("testRole");
		role.setRoleName("測試角色");
		role.setCreateUserId("admin");
		role.setCreateDate(Calendar.getInstance());
		resource.addAuthorityResource(role);
		
		resourceDAO.insert(resource);
		
		return resource;
	}
	
	private Resource[] prepareArrayData() throws Exception {
		Resource[] resources = new Resource[10];
		
		for(int i = 0; i < 10; i++) {
			URL resource = domainObjectFactory.createURL();
			resource.setId("testAction");
			resource.setCreateUserId("admin");
			resource.setCreateDate(Calendar.getInstance());
			
			resourceDAO.insert(resource);
			resources[i] = resource;
		}
		
		return resources;
	}
	
	private List<Resource> prepareListData() throws Exception {
		List<Resource> list = new ArrayList<Resource>();
		
		for(int i = 0; i < 10; i++) {
			URL resource = domainObjectFactory.createURL();
			resource.setId("testAction");
			resource.setCreateUserId("admin");
			resource.setCreateDate(Calendar.getInstance());
			
			resourceDAO.insert(resource);
			list.add(resource);
		}
		
		return list;
	}
	
	private Set<Resource> prepareSetData() throws Exception {
		Set<Resource> set = new HashSet<Resource>();
		
		for(int i = 0; i < 10; i++) {
			URL resource = domainObjectFactory.createURL();
			resource.setId("testAction");
			resource.setCreateUserId("admin");
			resource.setCreateDate(Calendar.getInstance());
			
			resourceDAO.insert(resource);
			set.add(resource);
		}
		
		return set;
	}
	
	@Test
	public void testSearchResources() throws Exception {
		Resource resource = prepareData();
		
		Resource search1 = domainObjectFactory.createResource();
		search1.setId("testAction");
		testForSearchResources(resource, search1);
		
//		URL search2 = domainObjectFactory.createURL();
//		testForSearchResources(resource, search2);
		
		URL search3 = domainObjectFactory.createURL();
		search3.setId("testAction");
		search3.setResourceType(ResourceType.URL);
		testForSearchResources(resource, search3);	
		
		Resource search4 = domainObjectFactory.createResource();
		search4.setId("/login?ActFlag=login");
		search4.setResourceType(ResourceType.URL);
		List<Resource> list = resourceDAO.search(search4);
		assertEquals(0, list.size());
	}
	
	private void testForSearchResources(Resource resource, Resource search) throws Exception {
		List<Resource> list = resourceDAO.search(search);
		assertEquals(1, list.size());
		assertEquals(resource, list.get(0));
	}
	
	@Test
	public void testSearchPaginationResources() throws Exception {
		List<Resource> list = preparePaginationData();
		Resource searchAll = domainObjectFactory.createResource();
		searchAll.setId("testAction_*");
		List<Resource> list2 = resourceDAO.searchPagination(searchAll, 0, 3);
		
		assertEquals(3, list2.size());
		
		for(int i = 0; i < 3; i++) {
			assertEquals(list.get(i), list2.get(i));
		}
	}
	
	@Test
	public void testSearchRowCountResources() throws Exception {
		List<Resource> list = preparePaginationData();
		Resource searchAll = domainObjectFactory.createResource();
		searchAll.setId("testAction_*");
		
		assertEquals(list.size(), resourceDAO.searchRowCount(searchAll));
	}
	
	private List<Resource> preparePaginationData() throws Exception {
		List<Resource> list = new ArrayList<Resource>();
		
		for(int i = 0; i < 10; i++) {
			URL resource = domainObjectFactory.createURL();
			resource.setId("testAction_" + i);
			resource.setCreateUserId("admin");
			resource.setCreateDate(Calendar.getInstance());
			
			resourceDAO.insert(resource);
			list.add(resource);
		}
		
		return list;
	}
}
