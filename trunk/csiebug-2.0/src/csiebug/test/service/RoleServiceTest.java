package csiebug.test.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import csiebug.dao.RoleDAO;
import csiebug.domain.DomainObjectFactory;
import csiebug.domain.Role;
import csiebug.service.RoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springTest.xml" })
public class RoleServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private RoleService roleService;
	@Autowired
	private DomainObjectFactory domainObjectFactory;
	@Autowired
	private RoleDAO roleDAO;
	
	private RoleDAO mockRoleDAO;
	
	@Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }
	
	private void setUp() {
		mockRoleDAO = EasyMock.createStrictMock(RoleDAO.class);
		roleService.setRoleDAO(mockRoleDAO);
	}
	
	private void tearDown() {
		EasyMock.verify(mockRoleDAO);
		
		roleService.setRoleDAO(roleDAO);
	}
	
	private Role prepareRoleData(boolean insertFlag) throws Exception {
		Role role = domainObjectFactory.createRole();
		role.setId("test2");
		role.setCreateUserId("admin");
		role.setCreateDate(Calendar.getInstance());
		
		if(insertFlag) {
			roleDAO.insert(role);
		}
		
		return role;
	}
	
	private void setUpForSearchRoles(Role voObj, Role role) throws DAOException {
		setUp();
		
		List<Role> expectList = new ArrayList<Role>();
		expectList.add(role);
		EasyMock.expect(mockRoleDAO.search(voObj)).andReturn(expectList);
		
		EasyMock.replay(mockRoleDAO);
	}
	
	@Test
	public void testSearchRoles() throws Exception {
		Role role = prepareRoleData(false);
		
		Role voObj = domainObjectFactory.createRole();
		voObj.setId("test2");
		
		setUpForSearchRoles(voObj, role);
		
		List<Role> list = roleService.searchRoles(voObj);
		
		assertEquals(1, list.size());
		assertEquals(role, list.get(0));
		
		tearDown();
	}
	
	private List<Role> prepareRolePaginationData(boolean insertFlag) throws Exception {
		List<Role> list = new ArrayList<Role>();
		
		for(int i = 0; i < 10; i++) {
			Role role = domainObjectFactory.createRole();
			role.setId("test_" + i);
			role.setCreateUserId("admin");
			role.setCreateDate(Calendar.getInstance());
			
			if(insertFlag) {
				roleDAO.insert(role);
			}
			list.add(role);
		}
		
		return list;
	}
	
	private List<Role> prepareRolePaginationData(int firstResult, int maxResults) throws Exception {
		List<Role> list = new ArrayList<Role>();
		
		for(int i = firstResult; i < (firstResult + maxResults); i++) {
			Role role = domainObjectFactory.createRole();
			role.setId("test_" + i);
			role.setCreateUserId("admin");
			role.setCreateDate(Calendar.getInstance());
			
			list.add(role);
		}
		
		return list;
	}
	
	private void setUpForSearchRolesPagination(Role voObj, List<Role> list) throws Exception {
		setUp();
		
		EasyMock.expect(mockRoleDAO.searchRowCount(voObj)).andReturn(list.size());
		for(int i = 0; i < 10; i = i + 2) {
			EasyMock.expect(mockRoleDAO.searchPagination(voObj, i, 2)).andReturn(prepareRolePaginationData(i, 2));
		}
		
		EasyMock.replay(mockRoleDAO);
	}
	
	@Test
	public void testSearchRolesPagination() throws Exception {
		List<Role> list = prepareRolePaginationData(false);
		
		Role voObj = domainObjectFactory.createRole();
		
		setUpForSearchRolesPagination(voObj, list);
		
		List<Role> list2 = roleService.searchRolesPagination(voObj, 2);
		
		assertEquals(list.size(), list2.size());
		
		for(int i = 0; i < list.size(); i++) {
			assertEquals(list.get(i), list2.get(i));
		}
		
		tearDown();
	}
	
	private void setUpForSaveRole(Role role) throws DAOException {
		setUp();
		
		mockRoleDAO.insertOrUpdate(role);
		List<Role> expectList = new ArrayList<Role>();
		expectList.add(role);
		EasyMock.expect(mockRoleDAO.search(role)).andReturn(expectList);
		
		EasyMock.replay(mockRoleDAO);
	}
	
	@Test
	public void testSaveRole() throws Exception {
		Role role = domainObjectFactory.createRole();
		role.setId("test2");
		role.setCreateUserId("admin");
		role.setCreateDate(Calendar.getInstance());
		
		setUpForSaveRole(role);
		
		roleService.saveRole(role);
		
		List<Role> list = roleService.searchRoles(role);
		
		assertEquals(1, list.size());
		assertEquals(role, list.get(0));
		
		tearDown();
	}
}
