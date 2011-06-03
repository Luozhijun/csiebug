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

import csiebug.dao.RoleDAO;
import csiebug.domain.Resource;
import csiebug.domain.ResourceType;
import csiebug.domain.Role;
import csiebug.domain.DomainObjectFactory;
import csiebug.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springTest.xml" })
public class RoleDAOTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private RoleDAO roleDAO;
	@Autowired
	private DomainObjectFactory domainObjectFactory;
	
	@javax.annotation.Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }
	
	@Test
	public void testSaveRole() throws Exception {
		Role role = domainObjectFactory.createRole();
		role.setId("testRole");
		role.setRoleName("測試角色");
		role.setCreateUserId("admin");
		role.setCreateDate(Calendar.getInstance());
		
		User user = domainObjectFactory.createUser();
		user.setId("test");
		user.setPassword("testPassword");
		user.setEnabled(true);
		user.setCreateUserId("admin");
		user.setCreateDate(Calendar.getInstance());
		role.addAuthority(user);
		
		Resource resource = domainObjectFactory.createResource();
		resource.setId("testAction");
		resource.setResourceType(ResourceType.URL);
		resource.setCreateUserId("admin");
		resource.setCreateDate(Calendar.getInstance());
		role.addAuthorityResource(resource);
		
		roleDAO.insert(role);
		
		List<Role> list = roleDAO.search(role);
		
		assertEquals(1, list.size());
		assertEquals(role, list.get(0));
		assertEquals(1, list.get(0).getAuthorities().size());
		assertEquals(user, list.get(0).getAuthorities().iterator().next());
		assertEquals(1, list.get(0).getAuthorityResources().size());
		assertEquals(resource, list.get(0).getAuthorityResources().iterator().next());
	}
	
	@Test
	public void testDeleteRole() throws Exception {
		prepareData();
		
		Role role = domainObjectFactory.createRole();
		role.setId("testRole");
		roleDAO.deleteMatchObjects(role);
		
		List<Role> list = roleDAO.search(role);
		
		assertEquals(0, list.size());
	}
	
	@Test
	public void testDeleteArray() throws Exception {
		Role[] roles = prepareArrayData();
		
		roleDAO.delete(roles);
		
		Role role = domainObjectFactory.createRole();
		
		List<Role> list = roleDAO.search(role);
		
		assertEquals(0, list.size());
	}
	
	@Test
	public void testDeleteList() throws Exception {
		List<Role> list = prepareListData();
		
		roleDAO.delete(list);
		
		Role role = domainObjectFactory.createRole();
		
		List<Role> list2 = roleDAO.search(role);
		
		assertEquals(0, list2.size());
	}
	
	@Test
	public void testDeleteSet() throws Exception {
		Set<Role> set = prepareSetData();
		
		roleDAO.delete(set);
		
		Role role = domainObjectFactory.createRole();
		
		List<Role> list = roleDAO.search(role);
		
		assertEquals(0, list.size());
	}
	
	private Role prepareData() throws Exception {
		Role role = domainObjectFactory.createRole();
		role.setId("testRole");
		role.setRoleName("測試角色");
		role.setCreateUserId("admin");
		role.setCreateDate(Calendar.getInstance());
		
		User user = domainObjectFactory.createUser();
		user.setId("test");
		user.setPassword("testPassword");
		user.setEnabled(true);
		user.setCreateUserId("admin");
		user.setCreateDate(Calendar.getInstance());
		role.addAuthority(user);
		
		Resource resource = domainObjectFactory.createResource();
		resource.setId("testAction");
		resource.setResourceType(ResourceType.URL);
		resource.setCreateUserId("admin");
		resource.setCreateDate(Calendar.getInstance());
		role.addAuthorityResource(resource);
		
		roleDAO.insert(role);
		
		return role;
	}
	
	private Role[] prepareArrayData() throws Exception {
		Role[] roles = new Role[10];
		
		for(int i = 0; i < 10; i++) {
			Role role = domainObjectFactory.createRole();
			role.setId("testRole_" + i);
			role.setRoleName("測試角色_" + i);
			role.setCreateUserId("admin");
			role.setCreateDate(Calendar.getInstance());
			
			roleDAO.insert(role);
			roles[i] = role;
		}
		
		return roles;
	}
	
	private List<Role> prepareListData() throws Exception {
		List<Role> list = new ArrayList<Role>();
		
		for(int i = 0; i < 10; i++) {
			Role role = domainObjectFactory.createRole();
			role.setId("testRole_" + i);
			role.setRoleName("測試角色_" + i);
			role.setCreateUserId("admin");
			role.setCreateDate(Calendar.getInstance());
			
			roleDAO.insert(role);
			list.add(role);
		}
		
		return list;
	}
	
	private Set<Role> prepareSetData() throws Exception {
		Set<Role> set = new HashSet<Role>();
		
		for(int i = 0; i < 10; i++) {
			Role role = domainObjectFactory.createRole();
			role.setId("testRole_" + i);
			role.setRoleName("測試角色_" + i);
			role.setCreateUserId("admin");
			role.setCreateDate(Calendar.getInstance());
			
			roleDAO.insert(role);
			set.add(role);
		}
		
		return set;
	}
	
	@Test
	public void testSearchRoles() throws Exception {
		Role role = prepareData();
		
		Role search1 = domainObjectFactory.createRole();
		search1.setId("testRole");
		testForSearchRoles(role, search1);
		
		Role search2 = domainObjectFactory.createRole();
		search2.setRoleName("測試角色");
		testForSearchRoles(role, search2);
		
		Role search3 = domainObjectFactory.createRole();
		search3.setId("testRole");
		search3.setRoleName("測試角色");
		testForSearchRoles(role, search3);		
	}
	
	private void testForSearchRoles(Role role, Role search) throws Exception {
		List<Role> list = roleDAO.search(search);
		assertEquals(1, list.size());
		assertEquals(role, list.get(0));
	}
	
	@Test
	public void testSearchPaginationRoles() throws Exception {
		List<Role> list = preparePaginationData();
		Role searchAll = domainObjectFactory.createRole();
		List<Role> list2 = roleDAO.searchPagination(searchAll, 0, 3);
		
		assertEquals(3, list2.size());
		
		for(int i = 0; i < 3; i++) {
			assertEquals(list.get(i), list2.get(i));
		}
	}
	
	@Test
	public void testSearchRowCountRoles() throws Exception {
		List<Role> list = preparePaginationData();
		Role searchAll = domainObjectFactory.createRole();
		
		assertEquals(list.size(), roleDAO.searchRowCount(searchAll));
	}
	
	private List<Role> preparePaginationData() throws Exception {
		List<Role> list = new ArrayList<Role>();
		
		for(int i = 0; i < 10; i++) {
			Role role = domainObjectFactory.createRole();
			role.setId("testRole_" + i);
			role.setRoleName("測試角色_" + i);
			role.setCreateUserId("admin");
			role.setCreateDate(Calendar.getInstance());
			
			roleDAO.insert(role);
			list.add(role);
		}
		
		return list;
	}
}
