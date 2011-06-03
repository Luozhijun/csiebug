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

import csiebug.dao.UserDAO;
import csiebug.domain.Role;
import csiebug.domain.User;
import csiebug.domain.DomainObjectFactory;
import csiebug.util.ShaEncoder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springTest.xml" })
public class UserDAOTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private DomainObjectFactory domainObjectFactory;
	
	@Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }
	
	@Test
	public void testSaveUser() throws Exception {
		User user = domainObjectFactory.createUser();
		user.setId("test");
		user.setPassword(ShaEncoder.getSHA256String("testPassword"));
		user.setEnabled(true);
		user.setCreateUserId("admin");
		user.setCreateDate(Calendar.getInstance());
		
		Role role = domainObjectFactory.createRole();
		role.setId("testRole");
		role.setRoleName("測試角色");
		role.setCreateUserId("admin");
		role.setCreateDate(Calendar.getInstance());
		user.addAuthority(role);
		
		userDAO.insert(user);
		
		List<User> list = userDAO.search(user);
		assertEquals(1, list.size());
		assertEquals(user, list.get(0));
		assertEquals(1, list.get(0).getAuthorities().size());
		assertEquals(role, list.get(0).getAuthorities().iterator().next());
	}
	
	@Test
	public void testDeleteUser() throws Exception {
		prepareData();
		
		User user = domainObjectFactory.createUser();
		user.setId("test");
		userDAO.deleteMatchObjects(user);
		
		List<User> list = userDAO.search(user);
		
		assertEquals(0, list.size());
	}
	
	@Test
	public void testDeleteArray() throws Exception {
		User[] users = prepareArrayData();
		
		userDAO.delete(users);
		
		User user = domainObjectFactory.createUser();
		
		List<User> list = userDAO.search(user);
		
		assertEquals(0, list.size());
	}
	
	@Test
	public void testDeleteList() throws Exception {
		List<User> list = prepareListData();
		
		userDAO.delete(list);
		
		User user = domainObjectFactory.createUser();
		
		List<User> list2 = userDAO.search(user);
		
		assertEquals(0, list2.size());
	}
	
	@Test
	public void testDeleteSet() throws Exception {
		Set<User> set = prepareSetData();
		
		userDAO.delete(set);
		
		User user = domainObjectFactory.createUser();
		
		List<User> list = userDAO.search(user);
		
		assertEquals(0, list.size());
	}
	
	private User prepareData() throws Exception {
		User user = domainObjectFactory.createUser();
		user.setId("test");
		user.setPassword(ShaEncoder.getSHA256String("testPassword"));
		user.setEnabled(true);
		user.setCreateUserId("admin");
		user.setCreateDate(Calendar.getInstance());
		
		Role role = domainObjectFactory.createRole();
		role.setId("testRole");
		role.setRoleName("測試角色");
		role.setCreateUserId("admin");
		role.setCreateDate(Calendar.getInstance());
		user.addAuthority(role);
		
		userDAO.insert(user);
		
		return user;
	}
	
	private User[] prepareArrayData() throws Exception {
		User[] users = new User[10];
		
		for(int i = 0; i < 10; i++) {
			User user = domainObjectFactory.createUser();
			user.setId("test_" + i);
			user.setPassword(ShaEncoder.getSHA256String("testPassword_" + i));
			user.setEnabled(true);
			user.setCreateUserId("admin");
			user.setCreateDate(Calendar.getInstance());
			
			userDAO.insert(user);
			users[i] = user;
		}
		
		return users;
	}
	
	private List<User> prepareListData() throws Exception {
		List<User> list = new ArrayList<User>();
		
		for(int i = 0; i < 10; i++) {
			User user = domainObjectFactory.createUser();
			user.setId("test_" + i);
			user.setPassword(ShaEncoder.getSHA256String("testPassword_" + i));
			user.setEnabled(true);
			user.setCreateUserId("admin");
			user.setCreateDate(Calendar.getInstance());
			
			userDAO.insert(user);
			list.add(user);
		}
		
		return list;
	}
	
	private Set<User> prepareSetData() throws Exception {
		Set<User> set = new HashSet<User>();
		
		for(int i = 0; i < 10; i++) {
			User user = domainObjectFactory.createUser();
			user.setId("test_" + i);
			user.setPassword(ShaEncoder.getSHA256String("testPassword_" + i));
			user.setEnabled(true);
			user.setCreateUserId("admin");
			user.setCreateDate(Calendar.getInstance());
			
			userDAO.insert(user);
			set.add(user);
		}
		
		return set;
	}
	
	@Test
	public void testSearchUsers() throws Exception {
		User user = prepareData();
		
		User search1 = domainObjectFactory.createUser();
		search1.setId("test");
		testForSearchUsers(user, search1);
		
		User search2 = domainObjectFactory.createUser();
		search2.setPassword(ShaEncoder.getSHA256String("testPassword"));
		testForSearchUsers(user, search2);
		
//		User search3 = domainObjectFactory.getUser();
//		search3.setEnabled(true);
//		testForSearchUsers(user, search3);
		
		User search4 = domainObjectFactory.createUser();
		search4.setId("test");
		search4.setPassword(ShaEncoder.getSHA256String("testPassword"));
		testForSearchUsers(user, search4);
		
		User search5 = domainObjectFactory.createUser();
		search5.setId("test");
		search5.setEnabled(true);
		testForSearchUsers(user, search5);
		
		User search6 = domainObjectFactory.createUser();
		search6.setPassword(ShaEncoder.getSHA256String("testPassword"));
		search6.setEnabled(true);
		testForSearchUsers(user, search6);
		
		User search7 = domainObjectFactory.createUser();
		search7.setId("test");
		search7.setPassword(ShaEncoder.getSHA256String("testPassword"));
		search7.setEnabled(true);
		testForSearchUsers(user, search7);
	}
	
	private void testForSearchUsers(User user, User search) throws Exception {
		List<User> list = userDAO.search(search);
		assertEquals(1, list.size());
		assertEquals(user, list.get(0));
	}
	
	@Test
	public void testSearchPaginationUsers() throws Exception {
		List<User> list = preparePaginationData();
		User searchAll = domainObjectFactory.createUser();
		List<User> list2 = userDAO.searchPagination(searchAll, 0, 3);
		
		assertEquals(3, list2.size());
		
		for(int i = 0; i < 3; i++) {
			assertEquals(list.get(i), list2.get(i));
		}
	}
	
	@Test
	public void testSearchRowCountUsers() throws Exception {
		List<User> list = preparePaginationData();
		User searchAll = domainObjectFactory.createUser();
		
		assertEquals(list.size(), userDAO.searchRowCount(searchAll));
	}
	
	private List<User> preparePaginationData() throws Exception {
		List<User> list = new ArrayList<User>();
		
		for(int i = 0; i < 10; i++) {
			User user = domainObjectFactory.createUser();
			user.setId("test_" + i);
			user.setPassword(ShaEncoder.getSHA256String("testPassword"));
			user.setEnabled(true);
			user.setCreateUserId("admin");
			user.setCreateDate(Calendar.getInstance());
			
			userDAO.insert(user);
			list.add(user);
		}
		
		return list;
	}
}
