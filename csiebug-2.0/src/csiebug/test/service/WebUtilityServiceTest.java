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

import csiebug.dao.APDAO;
import csiebug.dao.DAOException;
import csiebug.dao.FunctionDAO;
import csiebug.dao.ResourceDAO;
import csiebug.dao.UserDAO;
import csiebug.domain.Dashboard;
import csiebug.domain.DashboardPortlet;
import csiebug.domain.DomainObjectFactory;
import csiebug.domain.Function;
import csiebug.domain.Portlet;
import csiebug.domain.ResourceType;
import csiebug.domain.Role;
import csiebug.domain.User;
import csiebug.domain.UserProfile;
import csiebug.domain.AP;
import csiebug.service.WebUtilityService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springTest.xml" })
public class WebUtilityServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private WebUtilityService webUtilityService;
	@Autowired
	private DomainObjectFactory domainObjectFactory;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private FunctionDAO functionDAO;
	@Autowired
	private ResourceDAO resourceDAO;
	@Autowired
	private APDAO apDAO;
	
	private UserDAO mockUserDAO;
	private FunctionDAO mockFunctionDAO;
	private ResourceDAO mockResourceDAO;
	private APDAO mockAPDAO;
	
	@Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }
	
	private void setUp() {
		mockUserDAO = EasyMock.createStrictMock(UserDAO.class);
		mockFunctionDAO = EasyMock.createStrictMock(FunctionDAO.class);
		mockResourceDAO = EasyMock.createStrictMock(ResourceDAO.class);
		mockAPDAO = EasyMock.createStrictMock(APDAO.class);
		
		webUtilityService.setUserDAO(mockUserDAO);
		webUtilityService.setFunctionDAO(mockFunctionDAO);
		webUtilityService.setResourceDAO(mockResourceDAO);
		webUtilityService.setApDAO(mockAPDAO);
	}
	
	private void tearDown() {
		webUtilityService.setUserDAO(userDAO);
		webUtilityService.setFunctionDAO(functionDAO);
		webUtilityService.setResourceDAO(resourceDAO);
		webUtilityService.setApDAO(apDAO);
	}
	
	private User prepareUserData(boolean insertFlag) throws Exception {
		User user = domainObjectFactory.createUser();
		user.setId("test");
		user.setPassword("testPassword");
		user.setEnabled(true);
		user.setCreateUserId("admin");
		user.setCreateDate(Calendar.getInstance());
		
		UserProfile profile = domainObjectFactory.createUserProfile();
		profile.setId(user.getId());
		profile.setLocale("zh_tw");
		profile.setNickname("測試員");
		profile.setCreateUserId("admin");
		profile.setCreateDate(Calendar.getInstance());
		user.setUserProfile(profile);
		
		if(insertFlag) {
			userDAO.insert(user);
		}
		
		return user;
	}
	
	private void setUpForGetUser(User voObj, User user) throws DAOException {
		setUp();
		
		List<User> expectList = new ArrayList<User>();
		expectList.add(user);
		EasyMock.expect(mockUserDAO.search(voObj)).andReturn(expectList);
		
		EasyMock.replay(mockUserDAO);
	}
	
	private void tearDownForGetUser() {
		EasyMock.verify(mockUserDAO);
		
		tearDown();
	}
	
	@Test
	public void testGetUserName() throws Exception {
		User user = prepareUserData(false);
		
		User voObj = domainObjectFactory.createUser();
		voObj.setId("test");
		
		setUpForGetUser(voObj, user);
		
		assertEquals(user.getNickname(), webUtilityService.getUserName(voObj));
		
		tearDownForGetUser();
	}
	
	@Test
	public void testGetUserLocale() throws Exception {
		User user = prepareUserData(false);
		
		User voObj = domainObjectFactory.createUser();
		voObj.setId("test");
		
		setUpForGetUser(voObj, user);
		
		assertEquals(user.getLocale(), webUtilityService.getUserLocale(voObj));
		
		tearDownForGetUser();
	}
	
	/**
	 * 有綁權限控管給某個角色,但使用者有此角色
	 * @param user
	 * @return
	 * @throws Exception
	 */
	private AP prepareAPData1(User user, boolean insertFlag) throws Exception {
		AP ap = domainObjectFactory.createAP();
		ap.setId("testResourceId");
		ap.setResourceType(ResourceType.AP);
		ap.setApId("testAPId");
		ap.setApName("測試AP");
		ap.setApIndexURL("testAction");
		ap.setCreateUserId("admin");
		ap.setCreateDate(Calendar.getInstance());
		
		Role role = domainObjectFactory.createRole();
		role.setId("testRole");
		role.setRoleName("測試角色");
		role.setCreateUserId("admin");
		role.setCreateDate(Calendar.getInstance());
		
		ap.addAuthorityResource(role);
		role.addAuthorityResource(ap);
		role.addAuthority(user);
		user.addAuthority(role);
		
		if(insertFlag) {
			userDAO.insert(ap);
		}
		
		return ap;
	}
	
	/**
	 * 沒有綁權限控管
	 * @return
	 * @throws Exception
	 */
	private AP prepareAPData2(boolean insertFlag) throws Exception {
		AP ap = domainObjectFactory.createAP();
		ap.setId("testResourceId2");
		ap.setApId("testAPId2");
		ap.setApName("測試AP2");
		ap.setApIndexURL("testAction2");
		ap.setCreateUserId("admin");
		ap.setCreateDate(Calendar.getInstance());
		
		if(insertFlag) {
			userDAO.insert(ap);
		}
		
		return ap;
	}
	
	/**
	 * 有綁權限控管給某個角色,但使用者沒有此角色
	 * @return
	 * @throws Exception
	 */
	private AP prepareAPData3(boolean insertFlag) throws Exception {
		AP ap = domainObjectFactory.createAP();
		ap.setId("testResourceId3");
		ap.setApId("testAPId3");
		ap.setApName("測試AP3");
		ap.setApIndexURL("testAction3");
		ap.setCreateUserId("admin");
		ap.setCreateDate(Calendar.getInstance());
		
		Role role = domainObjectFactory.createRole();
		role.setId("testRole3");
		role.setRoleName("測試角色3");
		role.setCreateUserId("admin");
		role.setCreateDate(Calendar.getInstance());
		ap.addAuthorityResource(role);
		
		if(insertFlag) {
			userDAO.insert(ap);
		}
		
		return ap;
	}
	
	private void setUpForCheckAPPermission(User user, AP ap, AP ap2, AP ap3) throws DAOException {
		setUp();
		
		List<User> expectList = new ArrayList<User>();
		expectList.add(user);
		EasyMock.expect(mockUserDAO.search(user)).andReturn(expectList);
		List<AP> expectList2 = new ArrayList<AP>();
		expectList2.add(ap);
		EasyMock.expect(mockAPDAO.search(ap)).andReturn(expectList2);
		
		expectList = new ArrayList<User>();
		expectList.add(user);
		EasyMock.expect(mockUserDAO.search(user)).andReturn(expectList);
		expectList2 = new ArrayList<AP>();
		expectList2.add(ap2);
		EasyMock.expect(mockAPDAO.search(ap2)).andReturn(expectList2);
		
		expectList = new ArrayList<User>();
		expectList.add(user);
		EasyMock.expect(mockUserDAO.search(user)).andReturn(expectList);
		expectList2 = new ArrayList<AP>();
		expectList2.add(ap3);
		EasyMock.expect(mockAPDAO.search(ap3)).andReturn(expectList2);
		
		EasyMock.replay(mockUserDAO);
		EasyMock.replay(mockAPDAO);
	}
	
	private void tearDownForCheckAPPermission() {
		EasyMock.verify(mockUserDAO);
		EasyMock.verify(mockAPDAO);
		
		tearDown();
	}
	
	@Test
	public void testCheckAPPermission() throws Exception {
		User user = prepareUserData(false);
		AP ap = prepareAPData1(user, false);
		AP ap2 = prepareAPData2(false);
		AP ap3 = prepareAPData3(false);
		
		setUpForCheckAPPermission(user, ap, ap2, ap3);
		
		assertEquals(true, webUtilityService.checkAPPermission(user, ap));
		assertEquals(true, webUtilityService.checkAPPermission(user, ap2));
		assertEquals(false, webUtilityService.checkAPPermission(user, ap3));
		
		tearDownForCheckAPPermission();
	}
	
	private AP prepareAPData(boolean insertFlag) throws Exception {
		AP ap = domainObjectFactory.createAP();
		ap.setId("testAPId");
		ap.setResourceType(ResourceType.AP);
		ap.setApId("testAPId");
		ap.setApName("測試AP");
		ap.setApIndexURL("testAction");
		ap.setCreateUserId("admin");
		ap.setCreateDate(Calendar.getInstance());
		
		if(insertFlag) {
			userDAO.insert(ap);
		}
		
		return ap;
	}
	
	/**
	 * 有綁權限控管給某個角色,但使用者有此角色
	 * @param user
	 * @return
	 * @throws Exception
	 */
	private Function prepareFunctionData1(User user, AP ap, boolean insertFlag) throws Exception {
		Function function = domainObjectFactory.createFunction();
		function.setId("testResourceId");
		function.setResourceType(ResourceType.FUNCTION);
		function.setApId("testAPId");
		function.setFunctionId("testFunctionId");
		function.setFunctionName("測試Function");
		function.setFunctionURL("testAction");
		function.setFunctionLogo("testlogo.gif");
		function.setCreateUserId("admin");
		function.setCreateDate(Calendar.getInstance());
		
		Role role = domainObjectFactory.createRole();
		role.setId("testRole");
		role.setRoleName("測試角色");
		role.setCreateUserId("admin");
		role.setCreateDate(Calendar.getInstance());
		
		ap.addFunction(function);
		function.setAp(ap);
		function.addAuthorityResource(role);
		role.addAuthorityResource(function);
		role.addAuthority(user);
		user.addAuthority(role);
		
		if(insertFlag) {
			userDAO.insert(function);
		}
		
		return function;
	}
	
	/**
	 * 沒有綁權限控管
	 * @return
	 * @throws Exception
	 */
	private Function prepareFunctionData2(AP ap, boolean insertFlag) throws Exception {
		Function function = domainObjectFactory.createFunction();
		function.setId("testResourceId2");
		function.setResourceType(ResourceType.FUNCTION);
		function.setApId("testAPId");
		function.setFunctionId("testFunctionId2");
		function.setFunctionName("測試Function2");
		function.setFunctionURL("testAction2");
		function.setFunctionLogo("testlogo2.gif");
		function.setCreateUserId("admin");
		function.setCreateDate(Calendar.getInstance());
		
		ap.addFunction(function);
		function.setAp(ap);
		
		if(insertFlag) {
			userDAO.insert(function);
		}
		
		return function;
	}
	
	/**
	 * 有綁權限控管給某個角色,但使用者沒有此角色
	 * @return
	 * @throws Exception
	 */
	private Function prepareFunctionData3(AP ap, boolean insertFlag) throws Exception {
		Function function = domainObjectFactory.createFunction();
		function.setId("testResourceId3");
		function.setResourceType(ResourceType.FUNCTION);
		function.setApId("testAPId");
		function.setFunctionId("testFunctionId3");
		function.setFunctionName("測試Function3");
		function.setFunctionURL("testAction3");
		function.setFunctionLogo("testlogo3.gif");
		function.setCreateUserId("admin");
		function.setCreateDate(Calendar.getInstance());
		
		ap.addFunction(function);
		function.setAp(ap);
		
		Role role = domainObjectFactory.createRole();
		role.setId("testRole3");
		role.setRoleName("測試角色3");
		role.setCreateUserId("admin");
		role.setCreateDate(Calendar.getInstance());
		function.addAuthorityResource(role);
		
		if(insertFlag) {
			userDAO.insert(function);
		}
		
		return function;
	}
	
	private void setUpForCheckFunctionPermission(User user, Function function, Function function2, Function function3) throws DAOException {
		setUp();
		
		List<User> expectList = new ArrayList<User>();
		expectList.add(user);
		EasyMock.expect(mockUserDAO.search(user)).andReturn(expectList);
		List<Function> expectList2 = new ArrayList<Function>();
		expectList2.add(function);
		EasyMock.expect(mockFunctionDAO.search(function)).andReturn(expectList2);
		
		expectList = new ArrayList<User>();
		expectList.add(user);
		EasyMock.expect(mockUserDAO.search(user)).andReturn(expectList);
		expectList2 = new ArrayList<Function>();
		expectList2.add(function2);
		EasyMock.expect(mockFunctionDAO.search(function2)).andReturn(expectList2);
		
		expectList = new ArrayList<User>();
		expectList.add(user);
		EasyMock.expect(mockUserDAO.search(user)).andReturn(expectList);
		expectList2 = new ArrayList<Function>();
		expectList2.add(function3);
		EasyMock.expect(mockFunctionDAO.search(function3)).andReturn(expectList2);
		
		EasyMock.replay(mockUserDAO);
		EasyMock.replay(mockFunctionDAO);
	}
	
	private void tearDownForCheckFunctionPermission() {
		EasyMock.verify(mockUserDAO);
		EasyMock.verify(mockFunctionDAO);
		
		tearDown();
	}
	
	@Test
	public void testCheckFunctionPermission() throws Exception {
		User user = prepareUserData(false);
		AP ap = prepareAPData(false);
		Function function = prepareFunctionData1(user, ap, false);
		Function function2 = prepareFunctionData2(ap, false);
		Function function3 = prepareFunctionData3(ap, false);
		
		setUpForCheckFunctionPermission(user, function, function2, function3);
		
		assertEquals(true, webUtilityService.checkFunctionPermission(user, function));
		assertEquals(true, webUtilityService.checkFunctionPermission(user, function2));
		assertEquals(false, webUtilityService.checkFunctionPermission(user, function3));
		
		tearDownForCheckFunctionPermission();
	}
	
	private void setUpForGetFunctionName(Function voFunction, Function function) throws DAOException {
		setUp();
		
		List<Function> expectList = new ArrayList<Function>();
		expectList.add(function);
		EasyMock.expect(mockFunctionDAO.search(voFunction)).andReturn(expectList);
		
		EasyMock.replay(mockUserDAO);
		EasyMock.replay(mockFunctionDAO);
	}
	
	private void tearDownForGetFunctionName() {
		tearDownForCheckFunctionPermission();
	}
	
	@Test
	public void testGetFunctionName() throws Exception {
		AP ap = prepareAPData(false);
		Function function2 = prepareFunctionData2(ap, false);
		Function voFunction = domainObjectFactory.createFunction();
		voFunction.setId("testResourceId2");
		voFunction.setApId("testAPId");
		voFunction.setFunctionId("testFunctionId2");
		
		setUpForGetFunctionName(voFunction, function2);
		
		assertEquals(function2.getFunctionName(), webUtilityService.getFunctionName(voFunction));
		
		tearDownForGetFunctionName();
	}
	
	private List<String> preparePortletData(User user, boolean insertFlag) throws Exception {
		List<String> list = new ArrayList<String>();
		
		Role role = domainObjectFactory.createRole();
		role.setId("testRole");
		role.setRoleName("測試角色");
		role.setCreateUserId("admin");
		role.setCreateDate(Calendar.getInstance());
		
		user.addAuthority(role);
		role.addAuthority(user);
		
		Role role2 = domainObjectFactory.createRole();
		role2.setId("testRole2");
		role2.setRoleName("測試角色2");
		role2.setCreateUserId("admin");
		role2.setCreateDate(Calendar.getInstance());
		
		Portlet portlet1 = domainObjectFactory.createPortlet();
		portlet1.setId("testPortletId1");
		portlet1.setResourceType(ResourceType.PORTLET);
		portlet1.setCreateUserId(user.getId());
		portlet1.setCreateDate(Calendar.getInstance());
		
		role2.addAuthorityResource(portlet1);
		portlet1.addAuthorityResource(role2);
		
		Portlet portlet2 = domainObjectFactory.createPortlet();
		portlet2.setId("testPortletId2");
		portlet2.setResourceType(ResourceType.PORTLET);
		portlet2.setCreateUserId(user.getId());
		portlet2.setCreateDate(Calendar.getInstance());
		
		list.add(portlet2.getId());
		
		Portlet portlet3 = domainObjectFactory.createPortlet();
		portlet3.setId("testPortletId3");
		portlet3.setResourceType(ResourceType.PORTLET);
		portlet3.setCreateUserId(user.getId());
		portlet3.setCreateDate(Calendar.getInstance());
		
		role.addAuthorityResource(portlet3);
		portlet3.addAuthorityResource(role);
		list.add(portlet3.getId());
		
		if(insertFlag) {
			userDAO.insert(role);
			userDAO.insert(role2);
			userDAO.insert(portlet2);
		}
		
		return list;
	}
	
	private List<Portlet> preparePortletDataForMock(User user) throws Exception {
		List<Portlet> list = new ArrayList<Portlet>();
		
		Role role = domainObjectFactory.createRole();
		role.setId("testRole");
		role.setRoleName("測試角色");
		role.setCreateUserId("admin");
		role.setCreateDate(Calendar.getInstance());
		
		user.addAuthority(role);
		role.addAuthority(user);
		
		Role role2 = domainObjectFactory.createRole();
		role2.setId("testRole2");
		role2.setRoleName("測試角色2");
		role2.setCreateUserId("admin");
		role2.setCreateDate(Calendar.getInstance());
		
		Portlet portlet1 = domainObjectFactory.createPortlet();
		portlet1.setId("testPortletId1");
		portlet1.setResourceType(ResourceType.PORTLET);
		portlet1.setCreateUserId(user.getId());
		portlet1.setCreateDate(Calendar.getInstance());
		
		role2.addAuthorityResource(portlet1);
		portlet1.addAuthorityResource(role2);
		
		list.add(portlet1);
		
		Portlet portlet2 = domainObjectFactory.createPortlet();
		portlet2.setId("testPortletId2");
		portlet2.setResourceType(ResourceType.PORTLET);
		portlet2.setCreateUserId(user.getId());
		portlet2.setCreateDate(Calendar.getInstance());
		
		list.add(portlet2);
		
		Portlet portlet3 = domainObjectFactory.createPortlet();
		portlet3.setId("testPortletId3");
		portlet3.setResourceType(ResourceType.PORTLET);
		portlet3.setCreateUserId(user.getId());
		portlet3.setCreateDate(Calendar.getInstance());
		
		role.addAuthorityResource(portlet3);
		portlet3.addAuthorityResource(role);
		list.add(portlet3);
		
		return list;
	}
	
	private void setUpForGetAuthorizedPortlets(User user, List<String> list) throws Exception {
		setUp();
		
		List<Portlet> listPortlet = preparePortletDataForMock(user);
		
		for(int i = 0; i < list.size(); i++) {
			List<User> expectList = new ArrayList<User>();
			expectList.add(user);
			EasyMock.expect(mockUserDAO.search(user)).andReturn(expectList);
			List<csiebug.domain.Resource> expectList2 = new ArrayList<csiebug.domain.Resource>();
			expectList2.add(listPortlet.get(i));
			EasyMock.expect(mockResourceDAO.search(listPortlet.get(i))).andReturn(expectList2);
		}
		
		EasyMock.replay(mockUserDAO);
		EasyMock.replay(mockResourceDAO);
	}
	
	private void tearDownForGetPortlets() {
		EasyMock.verify(mockUserDAO);
		EasyMock.verify(mockResourceDAO);
		
		tearDown();
	}
	
	@Test
	public void testGetAuthorizedPortlets() throws Exception {
		User user = prepareUserData(false);
		List<String> list = preparePortletData(user, false);
		List<String> listForCheck = new ArrayList<String>();
		listForCheck.add("testPortletId1");
		listForCheck.add("testPortletId2");
		listForCheck.add("testPortletId3");
		
		setUpForGetAuthorizedPortlets(user, listForCheck);
		
		assertEquals(list, webUtilityService.getAuthorizedPortlets(user, listForCheck));
		
		tearDownForGetPortlets();
	}
	
	private void preparePortletData2(User user, boolean insertFlag) throws Exception {
		Role role = domainObjectFactory.createRole();
		role.setId("testRole");
		role.setRoleName("測試角色");
		role.setCreateUserId("admin");
		role.setCreateDate(Calendar.getInstance());
		
		user.addAuthority(role);
		role.addAuthority(user);
		
		Portlet portlet1 = domainObjectFactory.createPortlet();
		portlet1.setId("testPortletId1");
		portlet1.setResourceType(ResourceType.PORTLET);
		portlet1.setCreateUserId(user.getId());
		portlet1.setCreateDate(Calendar.getInstance());
		
		role.addAuthorityResource(portlet1);
		portlet1.addAuthorityResource(role);
		
		Portlet portlet2 = domainObjectFactory.createPortlet();
		portlet2.setId("testPortletId2");
		portlet2.setResourceType(ResourceType.PORTLET);
		portlet2.setCreateUserId(user.getId());
		portlet2.setCreateDate(Calendar.getInstance());
		
		role.addAuthorityResource(portlet2);
		portlet2.addAuthorityResource(role);
		
		Portlet portlet3 = domainObjectFactory.createPortlet();
		portlet3.setId("testPortletId3");
		portlet3.setResourceType(ResourceType.PORTLET);
		portlet3.setCreateUserId(user.getId());
		portlet3.setCreateDate(Calendar.getInstance());
		
		role.addAuthorityResource(portlet3);
		portlet3.addAuthorityResource(role);
		
		Portlet portlet4 = domainObjectFactory.createPortlet();
		portlet4.setId("testPortletId4");
		portlet4.setResourceType(ResourceType.PORTLET);
		portlet4.setCreateUserId(user.getId());
		portlet4.setCreateDate(Calendar.getInstance());
		
		role.addAuthorityResource(portlet4);
		portlet4.addAuthorityResource(role);
		
		if(insertFlag) {
			userDAO.insert(role);
		}
	}
	
	private Dashboard prepareDashboardData(User user, boolean insertFlag) throws Exception {
		Dashboard dashboard = domainObjectFactory.createDashboard();
		dashboard.setUserId(user.getId());
		dashboard.setDashboardId("testDashboardId1");
		dashboard.setCreateUserId(user.getId());
		dashboard.setCreateDate(Calendar.getInstance());
		user.addDashboard(dashboard);
		
		Dashboard dashboard2 = domainObjectFactory.createDashboard();
		dashboard2.setUserId(user.getId());
		dashboard2.setDashboardId("testDashboardId2");
		dashboard2.setCreateUserId(user.getId());
		dashboard2.setCreateDate(Calendar.getInstance());
		user.addDashboard(dashboard2);
		
		if(insertFlag) {
			userDAO.insertOrUpdate(user);
		}
		
		return dashboard;
	}
	
	private List<String> prepareDashboardPortletData(User user, boolean insertFlag) throws Exception {
		List<String> list = new ArrayList<String>();
		
		DashboardPortlet portlet1 = domainObjectFactory.createDashboardPortlet();
		portlet1.setUserId(user.getId());
		portlet1.setDashboardId("testDashboardId2");
		portlet1.setPortletId("testPortletId1");
		portlet1.setVisible(false);
		portlet1.setCreateUserId(user.getId());
		portlet1.setCreateDate(Calendar.getInstance());
		portlet1.setDashboard(user.getDashboard("testDashboardId2"));
		user.getDashboard("testDashboardId2").addPortlet(portlet1);
		
		DashboardPortlet portlet2 = domainObjectFactory.createDashboardPortlet();
		portlet2.setUserId(user.getId());
		portlet2.setDashboardId("testDashboardId2");
		portlet2.setPortletId("testPortletId2");
		portlet2.setVisible(true);
		portlet2.setCreateUserId(user.getId());
		portlet2.setCreateDate(Calendar.getInstance());
		portlet2.setDashboard(user.getDashboard("testDashboardId2"));
		user.getDashboard("testDashboardId2").addPortlet(portlet2);
		
		list.add(portlet2.getPortletId());
		
		DashboardPortlet portlet3 = domainObjectFactory.createDashboardPortlet();
		portlet3.setUserId(user.getId());
		portlet3.setDashboardId("testDashboardId1");
		portlet3.setPortletId("testPortletId3");
		portlet3.setVisible(false);
		portlet3.setCreateUserId(user.getId());
		portlet3.setCreateDate(Calendar.getInstance());
		portlet3.setDashboard(user.getDashboard("testDashboardId1"));
		user.getDashboard("testDashboardId1").addPortlet(portlet3);
		
		DashboardPortlet portlet4 = domainObjectFactory.createDashboardPortlet();
		portlet4.setUserId(user.getId());
		portlet4.setDashboardId("testDashboardId1");
		portlet4.setPortletId("testPortletId4");
		portlet4.setVisible(true);
		portlet4.setCreateUserId(user.getId());
		portlet4.setCreateDate(Calendar.getInstance());
		portlet4.setDashboard(user.getDashboard("testDashboardId1"));
		user.getDashboard("testDashboardId1").addPortlet(portlet4);
		
		if(insertFlag) {
			userDAO.insertOrUpdate(user);
		}
		
		return list;
	}
	
	private List<Portlet> preparePortletData2ForMock(User user) throws Exception {
		List<Portlet> list = new ArrayList<Portlet>();
		
		Role role = domainObjectFactory.createRole();
		role.setId("testRole");
		role.setRoleName("測試角色");
		role.setCreateUserId("admin");
		role.setCreateDate(Calendar.getInstance());
		
		user.addAuthority(role);
		role.addAuthority(user);
		
		Portlet portlet1 = domainObjectFactory.createPortlet();
		portlet1.setId("testPortletId1");
		portlet1.setResourceType(ResourceType.PORTLET);
		portlet1.setCreateUserId(user.getId());
		portlet1.setCreateDate(Calendar.getInstance());
		
		role.addAuthorityResource(portlet1);
		portlet1.addAuthorityResource(role);
		
		list.add(portlet1);
		
		Portlet portlet2 = domainObjectFactory.createPortlet();
		portlet2.setId("testPortletId2");
		portlet2.setResourceType(ResourceType.PORTLET);
		portlet2.setCreateUserId(user.getId());
		portlet2.setCreateDate(Calendar.getInstance());
		
		role.addAuthorityResource(portlet2);
		portlet2.addAuthorityResource(role);
		
		list.add(portlet2);
		
		Portlet portlet3 = domainObjectFactory.createPortlet();
		portlet3.setId("testPortletId3");
		portlet3.setResourceType(ResourceType.PORTLET);
		portlet3.setCreateUserId(user.getId());
		portlet3.setCreateDate(Calendar.getInstance());
		
		role.addAuthorityResource(portlet3);
		portlet3.addAuthorityResource(role);
		
		list.add(portlet3);
		
		Portlet portlet4 = domainObjectFactory.createPortlet();
		portlet4.setId("testPortletId4");
		portlet4.setResourceType(ResourceType.PORTLET);
		portlet4.setCreateUserId(user.getId());
		portlet4.setCreateDate(Calendar.getInstance());
		
		role.addAuthorityResource(portlet4);
		portlet4.addAuthorityResource(role);
		
		list.add(portlet4);
		
		return list;
	}
	
	private void setUpForGetVisiblePortlets(User user, List<String> list) throws Exception {
		setUp();
		
		List<Portlet> listPortlet = preparePortletData2ForMock(user);
		
		for(int i = 0; i < list.size(); i++) {
			List<User> expectList = new ArrayList<User>();
			expectList.add(user);
			EasyMock.expect(mockUserDAO.search(user)).andReturn(expectList);
			List<csiebug.domain.Resource> expectList2 = new ArrayList<csiebug.domain.Resource>();
			expectList2.add(listPortlet.get(i));
			EasyMock.expect(mockResourceDAO.search(listPortlet.get(i))).andReturn(expectList2);
		}
		List<User> expectList = new ArrayList<User>();
		expectList.add(user);
		EasyMock.expect(mockUserDAO.search(user)).andReturn(expectList);
		
		EasyMock.replay(mockUserDAO);
		EasyMock.replay(mockResourceDAO);
	}
	
	@Test
	public void testGetVisiblePortlets() throws Exception {
		User user = prepareUserData(false);
		preparePortletData2(user, false);
		prepareDashboardData(user, false);
		List<String> list = prepareDashboardPortletData(user, false);
		List<String> listForCheck = new ArrayList<String>();
		listForCheck.add("testPortletId1");
		listForCheck.add("testPortletId2");
		listForCheck.add("testPortletId3");
		listForCheck.add("testPortletId4");
		
		setUpForGetVisiblePortlets(user, listForCheck);
		
		assertEquals(list, webUtilityService.getVisiblePortlets(user, "testDashboardId2", listForCheck));
		
		tearDownForGetPortlets();
	}
	
	private void preparePortletData3(User user, boolean insertFlag) throws Exception {
		Role role = domainObjectFactory.createRole();
		role.setId("testRole");
		role.setRoleName("測試角色");
		role.setCreateUserId("admin");
		role.setCreateDate(Calendar.getInstance());
		
		user.addAuthority(role);
		role.addAuthority(user);
		
		Portlet portlet1 = domainObjectFactory.createPortlet();
		portlet1.setId("testPortletId1");
		portlet1.setResourceType(ResourceType.PORTLET);
		portlet1.setCreateUserId(user.getId());
		portlet1.setCreateDate(Calendar.getInstance());
		
		role.addAuthorityResource(portlet1);
		portlet1.addAuthorityResource(role);
		
		Portlet portlet2 = domainObjectFactory.createPortlet();
		portlet2.setId("testPortletId2");
		portlet2.setResourceType(ResourceType.PORTLET);
		portlet2.setCreateUserId(user.getId());
		portlet2.setCreateDate(Calendar.getInstance());
		
		role.addAuthorityResource(portlet2);
		portlet2.addAuthorityResource(role);
		
		Portlet portlet3 = domainObjectFactory.createPortlet();
		portlet3.setId("testPortletId3");
		portlet3.setResourceType(ResourceType.PORTLET);
		portlet3.setCreateUserId(user.getId());
		portlet3.setCreateDate(Calendar.getInstance());
		
		role.addAuthorityResource(portlet3);
		portlet3.addAuthorityResource(role);
		
		Portlet portlet4 = domainObjectFactory.createPortlet();
		portlet4.setId("testPortletId4");
		portlet4.setResourceType(ResourceType.PORTLET);
		portlet4.setCreateUserId(user.getId());
		portlet4.setCreateDate(Calendar.getInstance());
		
		role.addAuthorityResource(portlet4);
		portlet4.addAuthorityResource(role);
		
		Portlet portlet5 = domainObjectFactory.createPortlet();
		portlet5.setId("testPortletId5");
		portlet5.setResourceType(ResourceType.PORTLET);
		portlet5.setCreateUserId(user.getId());
		portlet5.setCreateDate(Calendar.getInstance());
		
		role.addAuthorityResource(portlet5);
		portlet5.addAuthorityResource(role);
		
		if(insertFlag) {
			userDAO.insert(role);
		}
	}
	
	private List<String> prepareDashboardPortletData2(User user, boolean insertFlag) throws Exception {
		List<String> list = new ArrayList<String>();
		
		DashboardPortlet portlet1 = domainObjectFactory.createDashboardPortlet();
		portlet1.setUserId(user.getId());
		portlet1.setDashboardId("testDashboardId2");
		portlet1.setPortletId("testPortletId1");
		portlet1.setVisible(false);
		portlet1.setColumnName("thick");
		portlet1.setSortOrder(5);
		portlet1.setCreateUserId(user.getId());
		portlet1.setCreateDate(Calendar.getInstance());
		portlet1.setDashboard(user.getDashboard("testDashboardId2"));
		user.getDashboard("testDashboardId2").addPortlet(portlet1);
		
		DashboardPortlet portlet2 = domainObjectFactory.createDashboardPortlet();
		portlet2.setUserId(user.getId());
		portlet2.setDashboardId("testDashboardId2");
		portlet2.setPortletId("testPortletId2");
		portlet2.setVisible(true);
		portlet2.setColumnName("thin");
		portlet2.setSortOrder(4);
		portlet2.setCreateUserId(user.getId());
		portlet2.setCreateDate(Calendar.getInstance());
		portlet2.setDashboard(user.getDashboard("testDashboardId2"));
		user.getDashboard("testDashboardId2").addPortlet(portlet2);
		
		DashboardPortlet portlet3 = domainObjectFactory.createDashboardPortlet();
		portlet3.setUserId(user.getId());
		portlet3.setDashboardId("testDashboardId1");
		portlet3.setPortletId("testPortletId3");
		portlet3.setVisible(false);
		portlet3.setColumnName("thick");
		portlet3.setSortOrder(3);
		portlet3.setCreateUserId(user.getId());
		portlet3.setCreateDate(Calendar.getInstance());
		portlet3.setDashboard(user.getDashboard("testDashboardId1"));
		user.getDashboard("testDashboardId1").addPortlet(portlet3);
		
		DashboardPortlet portlet4 = domainObjectFactory.createDashboardPortlet();
		portlet4.setUserId(user.getId());
		portlet4.setDashboardId("testDashboardId1");
		portlet4.setPortletId("testPortletId4");
		portlet4.setColumnName("thick");
		portlet4.setVisible(true);
		portlet4.setSortOrder(2);
		portlet4.setCreateUserId(user.getId());
		portlet4.setCreateDate(Calendar.getInstance());
		portlet4.setDashboard(user.getDashboard("testDashboardId1"));
		user.getDashboard("testDashboardId1").addPortlet(portlet4);
		
		DashboardPortlet portlet5 = domainObjectFactory.createDashboardPortlet();
		portlet5.setUserId(user.getId());
		portlet5.setDashboardId("testDashboardId2");
		portlet5.setPortletId("testPortletId5");
		portlet5.setVisible(true);
		portlet5.setColumnName("thin");
		portlet5.setSortOrder(1);
		portlet5.setCreateUserId(user.getId());
		portlet5.setCreateDate(Calendar.getInstance());
		portlet5.setDashboard(user.getDashboard("testDashboardId2"));
		user.getDashboard("testDashboardId2").addPortlet(portlet5);
		
		list.add(portlet5.getPortletId());
		list.add(portlet2.getPortletId());
		
		if(insertFlag) {
			userDAO.insertOrUpdate(user);
		}
		
		return list;
	}
	
	private void setUpForGetSortedPortlets(User user) throws Exception {
		setUp();
		
		List<User> expectList = new ArrayList<User>();
		expectList.add(user);
		EasyMock.expect(mockUserDAO.search(user)).andReturn(expectList);
		
		EasyMock.replay(mockUserDAO);
		EasyMock.replay(mockResourceDAO);
	}
	
	@Test
	public void testGetSortedPortlets() throws Exception {
		User user = prepareUserData(false);
		preparePortletData3(user, false);
		prepareDashboardData(user, false);
		List<String> list = prepareDashboardPortletData2(user, false);
		List<String> listForCheck = new ArrayList<String>();
		listForCheck.add("testPortletId1");
		listForCheck.add("testPortletId2");
		listForCheck.add("testPortletId3");
		listForCheck.add("testPortletId4");
		listForCheck.add("testPortletId5");
		
		setUpForGetSortedPortlets(user);
		
		assertEquals(list, webUtilityService.getSortedPortlets(user, "testDashboardId2", listForCheck));
		
		tearDownForGetPortlets();
	}
}
