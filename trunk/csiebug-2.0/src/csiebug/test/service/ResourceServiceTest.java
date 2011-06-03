package csiebug.test.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.easymock.EasyMock;

import csiebug.dao.DAOException;
import csiebug.dao.ResourceDAO;
import csiebug.dao.RoleDAO;
import csiebug.domain.ResourceType;
import csiebug.domain.Role;
import csiebug.domain.DomainObjectFactory;
import csiebug.domain.Portlet;
import csiebug.domain.Resource;
import csiebug.service.ResourceService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springTest.xml" })
public class ResourceServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private DomainObjectFactory domainObjectFactory;
	@Autowired
	private ResourceDAO resourceDAO;
	@Autowired
	private RoleDAO roleDAO;
	
	private ResourceDAO mockResourceDAO;
	private RoleDAO mockRoleDAO;
	
	@javax.annotation.Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }
	
	private void setUp() {
		mockResourceDAO = EasyMock.createStrictMock(ResourceDAO.class);
		mockRoleDAO = EasyMock.createStrictMock(RoleDAO.class);
		resourceService.setResourceDAO(mockResourceDAO);
		resourceService.setRoleDAO(mockRoleDAO);
	}
	
	private void tearDown() {
		resourceService.setResourceDAO(resourceDAO);
		resourceService.setRoleDAO(roleDAO);
	}
	
	private Resource prepareResourceData(boolean insertFlag) throws Exception {
		Resource resource = domainObjectFactory.createResource();
		resource.setId("testURL");
		resource.setResourceType(ResourceType.URL);
		resource.setCreateUserId("admin");
		resource.setCreateDate(Calendar.getInstance());
		
		if(insertFlag) {
			resourceDAO.insert(resource);
		}
		
		return resource;
	}
	
	private void setUpForSearchResources(Resource voObj, Resource resource) throws DAOException {
		setUp();
		
		List<Resource> expectList = new ArrayList<Resource>();
		expectList.add(resource);
		EasyMock.expect(mockResourceDAO.search(voObj)).andReturn(expectList);
		
		EasyMock.replay(mockResourceDAO);
	}
	
	private void tearDownMockResourceDAO() {
		EasyMock.verify(mockResourceDAO);
		
		tearDown();
	}
	
	@Test
	public void testSearchResources() throws Exception {
		Resource resource = prepareResourceData(false);
		
		Resource voObj = domainObjectFactory.createResource();
		voObj.setId("testURL");
		
		setUpForSearchResources(voObj, resource);
		
		List<Resource> list = resourceService.searchResources(voObj);
		
		assertEquals(1, list.size());
		assertEquals(resource, list.get(0));
		
		tearDownMockResourceDAO();
	}
	
	private List<Resource> prepareResourcePaginationData(boolean insertFlag) throws Exception {
		List<Resource> list = new ArrayList<Resource>();
		
		for(int i = 0; i < 10; i++) {
			Resource resource = domainObjectFactory.createResource();
			resource.setId("testURL_" + i);
			resource.setResourceType(ResourceType.URL);
			resource.setCreateUserId("admin");
			resource.setCreateDate(Calendar.getInstance());
			
			if(insertFlag) {
				resourceDAO.insert(resource);
			}
			list.add(resource);
		}
		
		return list;
	}
	
	private List<Resource> prepareResourcePaginationData(int firstResult, int maxResults) throws Exception {
		List<Resource> list = new ArrayList<Resource>();
		
		for(int i = firstResult; i < (firstResult + maxResults); i++) {
			Resource resource = domainObjectFactory.createResource();
			resource.setId("testURL_" + i);
			resource.setResourceType(ResourceType.URL);
			resource.setCreateUserId("admin");
			resource.setCreateDate(Calendar.getInstance());
			
			list.add(resource);
		}
		
		return list;
	}
	
	private void setUpForSearchResourcesPagination(Resource voObj, List<Resource> list) throws Exception {
		setUp();
		
		EasyMock.expect(mockResourceDAO.searchRowCount(voObj)).andReturn(list.size());
		for(int i = 0; i < 10; i = i + 2) {
			EasyMock.expect(mockResourceDAO.searchPagination(voObj, i, 2)).andReturn(prepareResourcePaginationData(i, 2));
		}
		
		EasyMock.replay(mockResourceDAO);
	}
	
	@Test
	public void testSearchResourcesPagination() throws Exception {
		List<Resource> list = prepareResourcePaginationData(false);
		
		Resource voObj = domainObjectFactory.createResource();
		voObj.setId("testURL_*");
		
		setUpForSearchResourcesPagination(voObj, list);
		
		List<Resource> list2 = resourceService.searchResourcesPagination(voObj, 2);
		
		assertEquals(list.size(), list2.size());
		
		for(int i = 0; i < list.size(); i++) {
			assertEquals(list.get(i), list2.get(i));
		}
		
		tearDownMockResourceDAO();
	}
	
	private Portlet preparePortletData(boolean insertFlag) throws Exception {
		Portlet portlet = domainObjectFactory.createPortlet();
		portlet.setId("testPortlet");
		portlet.setCreateUserId("admin");
		portlet.setCreateDate(Calendar.getInstance());
		
		if(insertFlag) {
			resourceDAO.insert(portlet);
		}
		
		return portlet;
	}
	
	private void setUpForDeletePortlet(Portlet portlet) throws DAOException {
		setUp();
		
		List<Resource> expectList = new ArrayList<Resource>();
		expectList.add(portlet);
		EasyMock.expect(mockResourceDAO.search(portlet)).andReturn(expectList);
		EasyMock.expect(mockResourceDAO.search(portlet)).andReturn(expectList);
		mockResourceDAO.delete(portlet);
		expectList = new ArrayList<Resource>();
		EasyMock.expect(mockResourceDAO.search(portlet)).andReturn(expectList);
		
		EasyMock.replay(mockResourceDAO);
	}
	
	@Test
	public void testDeletePortlet() throws Exception {
		Portlet portlet = preparePortletData(false);
		
		setUpForDeletePortlet(portlet);
		
		List<Resource> list = resourceService.searchResources(portlet);
		
		assertEquals(1, list.size());
		assertEquals(portlet, list.get(0));
		
		resourceService.deletePortlet(portlet);
		
		list = resourceService.searchResources(portlet);
		
		assertEquals(0, list.size());
		
		tearDownMockResourceDAO();
	}
	
	private Role prepareRoleData(boolean insertFlag) throws Exception {
		Role admin = domainObjectFactory.createRole();
		admin.setId("admin");
		admin.setCreateUserId("admin");
		admin.setCreateDate(Calendar.getInstance());
		
		if(insertFlag) {
			resourceDAO.insert(admin);
		}
		
		return admin;
	}
	
	private void setUpForAddPortletToRole(Role role, Portlet portlet) throws DAOException {
		setUp();
		
		mockRoleDAO.insertOrUpdate(role);
		List<Resource> expectList = new ArrayList<Resource>();
		expectList.add(portlet);
		EasyMock.expect(mockResourceDAO.search(portlet)).andReturn(expectList);
		
		EasyMock.replay(mockRoleDAO);
		EasyMock.replay(mockResourceDAO);
	}
	
	private void tearDownAllMockDAO() {
		EasyMock.verify(mockRoleDAO);
		EasyMock.verify(mockResourceDAO);
		
		tearDown();
	}
	
	@Test
	public void testAddPortletToRole() throws Exception {
		Role role = prepareRoleData(false);
		Portlet portlet = preparePortletData(false);
		
		setUpForAddPortletToRole(role, portlet);
		
		resourceService.addPortletToRole(portlet, role);
		
		List<Resource> list = resourceService.searchResources(portlet);
		
		assertEquals(1, list.size());
		assertEquals(1, role.getAuthorityResources().size());
		assertEquals(portlet, role.getAuthorityResources().iterator().next());
		assertEquals(1, portlet.getAuthorityResources().size());
		assertEquals(role, portlet.getAuthorityResources().iterator().next());
		
		tearDownAllMockDAO();
	}
	
	private void setUpForAddPortlet(Portlet portlet, Role role, Portlet portlet2) throws DAOException {
		setUp();
		
		mockResourceDAO.insert(portlet);
		List<Resource> expectList = new ArrayList<Resource>();
		expectList.add(portlet);
		EasyMock.expect(mockResourceDAO.search(portlet)).andReturn(expectList);
		
		Role voObj = domainObjectFactory.createRole();
		voObj.setId("admin");
		List<Role> expectList2 = new ArrayList<Role>();
		expectList2.add(role);
		EasyMock.expect(mockRoleDAO.search(voObj)).andReturn(expectList2);
		mockRoleDAO.insertOrUpdate(role);
		expectList = new ArrayList<Resource>();
		expectList.add(portlet2);
		EasyMock.expect(mockResourceDAO.search(portlet2)).andReturn(expectList);
		
		EasyMock.replay(mockResourceDAO);
		EasyMock.replay(mockRoleDAO);
	}
	
	@Test
	public void testAddPortlet() throws Exception {
		Role role = prepareRoleData(false);
		
		Portlet portlet = domainObjectFactory.createPortlet();
		portlet.setId("testPortlet");
		portlet.setCreateUserId("admin");
		portlet.setCreateDate(Calendar.getInstance());
		
		Portlet portlet2 = domainObjectFactory.createPortlet();
		portlet2.setId("testPortlet2");
		portlet2.setCreateUserId("admin");
		portlet2.setCreateDate(Calendar.getInstance());
		
		setUpForAddPortlet(portlet, role, portlet2);
		
		resourceService.addPortlet(portlet, true);
		
		List<Resource> list = resourceService.searchResources(portlet);
		
		assertEquals(1, list.size());
		assertEquals(portlet, list.get(0));
		assertEquals(0, role.getAuthorityResources().size());
		assertEquals(0, portlet.getAuthorityResources().size());
		
		resourceService.addPortlet(portlet2, false);
		
		list = resourceService.searchResources(portlet2);
		
		assertEquals(1, list.size());
		assertEquals(portlet2, list.get(0));
		assertEquals(1, role.getAuthorityResources().size());
		assertEquals(portlet2, role.getAuthorityResources().iterator().next());
		assertEquals(1, portlet2.getAuthorityResources().size());
		assertEquals(role, portlet2.getAuthorityResources().iterator().next());
		
		tearDownAllMockDAO();
	}
}
