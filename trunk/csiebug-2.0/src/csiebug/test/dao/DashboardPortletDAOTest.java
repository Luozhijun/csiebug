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

import csiebug.dao.DashboardPortletDAO;
import csiebug.domain.DashboardPortlet;
import csiebug.domain.DomainObjectFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springTest.xml" })
public class DashboardPortletDAOTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private DashboardPortletDAO dashboardPortletDAO;
	@Autowired
	private DomainObjectFactory domainObjectFactory;
	
	@Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }
	
	@Test
	public void testSaveDashboardPortlet() throws Exception {
		DashboardPortlet dashboardPortlet = domainObjectFactory.createDashboardPortlet();
		dashboardPortlet.setUserId("test");
		dashboardPortlet.setDashboardId("testDashboardId");
		dashboardPortlet.setPortletId("testDashboardPortletId");
		dashboardPortlet.setPortletTitle("測試DashboardPortlet");
		dashboardPortlet.setColumnName("thick");
		dashboardPortlet.setSortOrder(0);
		dashboardPortlet.setVisible(true);
		dashboardPortlet.setCreateUserId("admin");
		dashboardPortlet.setCreateDate(Calendar.getInstance());
				
		dashboardPortletDAO.insert(dashboardPortlet);
		
		List<DashboardPortlet> list = dashboardPortletDAO.search(dashboardPortlet);
		
		assertEquals(1, list.size());
		assertEquals(dashboardPortlet, list.get(0));
	}
	
	@Test
	public void testDeleteDashboardPortlet() throws Exception {
		prepareData();
		
		DashboardPortlet dashboardPortlet = domainObjectFactory.createDashboardPortlet();
		dashboardPortlet.setUserId("test");
		dashboardPortlet.setDashboardId("testDashboardId");
		dashboardPortlet.setPortletId("testDashboardPortletId");
		dashboardPortletDAO.deleteMatchObjects(dashboardPortlet);
		
		List<DashboardPortlet> list = dashboardPortletDAO.search(dashboardPortlet);
		
		assertEquals(0, list.size());
	}
	
	@Test
	public void testDeleteArray() throws Exception {
		DashboardPortlet[] portlets = prepareArrayData();
		
		dashboardPortletDAO.delete(portlets);
		
		DashboardPortlet dashboardPortlet = domainObjectFactory.createDashboardPortlet();
		dashboardPortlet.setPortletTitle("測試DashboardPortlet_");
		
		List<DashboardPortlet> list = dashboardPortletDAO.search(dashboardPortlet);
		
		assertEquals(0, list.size());
	}
	
	@Test
	public void testDeleteList() throws Exception {
		List<DashboardPortlet> list = prepareListData();
		
		dashboardPortletDAO.delete(list);
		
		DashboardPortlet dashboardPortlet = domainObjectFactory.createDashboardPortlet();
		dashboardPortlet.setPortletTitle("測試DashboardPortlet_");
		
		List<DashboardPortlet> list2 = dashboardPortletDAO.search(dashboardPortlet);
		
		assertEquals(0, list2.size());
	}
	
	@Test
	public void testDeleteSet() throws Exception {
		Set<DashboardPortlet> set = prepareSetData();
		
		dashboardPortletDAO.delete(set);
		
		DashboardPortlet dashboardPortlet = domainObjectFactory.createDashboardPortlet();
		dashboardPortlet.setPortletTitle("測試DashboardPortlet_");
		
		List<DashboardPortlet> list = dashboardPortletDAO.search(dashboardPortlet);
		
		assertEquals(0, list.size());
	}
	
	private DashboardPortlet prepareData() throws Exception {
		DashboardPortlet dashboardPortlet = domainObjectFactory.createDashboardPortlet();
		dashboardPortlet.setUserId("test");
		dashboardPortlet.setDashboardId("testDashboardId");
		dashboardPortlet.setPortletId("testDashboardPortletId");
		dashboardPortlet.setPortletTitle("測試DashboardPortlet");
		dashboardPortlet.setColumnName("thick");
		dashboardPortlet.setSortOrder(0);
		dashboardPortlet.setVisible(true);
		dashboardPortlet.setCreateUserId("admin");
		dashboardPortlet.setCreateDate(Calendar.getInstance());
				
		dashboardPortletDAO.insert(dashboardPortlet);
		
		return dashboardPortlet;
	}
	
	private DashboardPortlet[] prepareArrayData() throws Exception {
		DashboardPortlet[] portlets = new DashboardPortlet[10];
		
		for(int i = 0; i < 10; i++) {
			DashboardPortlet dashboardPortlet = domainObjectFactory.createDashboardPortlet();
			dashboardPortlet.setUserId("test_" + i);
			dashboardPortlet.setDashboardId("testDashboardId_" + i);
			dashboardPortlet.setPortletId("testDashboardPortletId_" + i);
			dashboardPortlet.setPortletTitle("測試DashboardPortlet_" + i);
			dashboardPortlet.setColumnName("thick");
			dashboardPortlet.setSortOrder(0);
			dashboardPortlet.setVisible(true);
			dashboardPortlet.setCreateUserId("admin");
			dashboardPortlet.setCreateDate(Calendar.getInstance());
					
			dashboardPortletDAO.insert(dashboardPortlet);
			portlets[i] = dashboardPortlet;
		}
		
		return portlets;
	}
	
	private List<DashboardPortlet> prepareListData() throws Exception {
		List<DashboardPortlet> list = new ArrayList<DashboardPortlet>();
		
		for(int i = 0; i < 10; i++) {
			DashboardPortlet dashboardPortlet = domainObjectFactory.createDashboardPortlet();
			dashboardPortlet.setUserId("test_" + i);
			dashboardPortlet.setDashboardId("testDashboardId_" + i);
			dashboardPortlet.setPortletId("testDashboardPortletId_" + i);
			dashboardPortlet.setPortletTitle("測試DashboardPortlet_" + i);
			dashboardPortlet.setColumnName("thick");
			dashboardPortlet.setSortOrder(0);
			dashboardPortlet.setVisible(true);
			dashboardPortlet.setCreateUserId("admin");
			dashboardPortlet.setCreateDate(Calendar.getInstance());
					
			dashboardPortletDAO.insert(dashboardPortlet);
			list.add(dashboardPortlet);
		}
		
		return list;
	}
	
	private Set<DashboardPortlet> prepareSetData() throws Exception {
		Set<DashboardPortlet> set = new HashSet<DashboardPortlet>();
		
		for(int i = 0; i < 10; i++) {
			DashboardPortlet dashboardPortlet = domainObjectFactory.createDashboardPortlet();
			dashboardPortlet.setUserId("test_" + i);
			dashboardPortlet.setDashboardId("testDashboardId_" + i);
			dashboardPortlet.setPortletId("testDashboardPortletId_" + i);
			dashboardPortlet.setPortletTitle("測試DashboardPortlet_" + i);
			dashboardPortlet.setColumnName("thick");
			dashboardPortlet.setSortOrder(0);
			dashboardPortlet.setVisible(true);
			dashboardPortlet.setCreateUserId("admin");
			dashboardPortlet.setCreateDate(Calendar.getInstance());
					
			dashboardPortletDAO.insert(dashboardPortlet);
			set.add(dashboardPortlet);
		}
		
		return set;
	}
	
	@Test
	public void testSearchDashboardPortlets() throws Exception {
		DashboardPortlet dashboardPortlet = prepareData();
		
		DashboardPortlet search1 = domainObjectFactory.createDashboardPortlet();
		search1.setUserId("test");
		testForSearchDashboardPortlets(dashboardPortlet, search1);
		
		DashboardPortlet search2 = domainObjectFactory.createDashboardPortlet();
		search2.setDashboardId("testDashboardId");
		testForSearchDashboardPortlets(dashboardPortlet, search2);
		
		DashboardPortlet search3 = domainObjectFactory.createDashboardPortlet();
		search3.setPortletId("testDashboardPortletId");
		search3.setPortletTitle("測試DashboardPortlet");
		testForSearchDashboardPortlets(dashboardPortlet, search3);		
	}
	
	private void testForSearchDashboardPortlets(DashboardPortlet dashboardPortlet, DashboardPortlet search) throws Exception {
		List<DashboardPortlet> list = dashboardPortletDAO.search(search);
		assertEquals(1, list.size());
		assertEquals(dashboardPortlet, list.get(0));
	}
	
	@Test
	public void testSearchPaginationDashboardPortlets() throws Exception {
		List<DashboardPortlet> list = preparePaginationData();
		DashboardPortlet searchAll = domainObjectFactory.createDashboardPortlet();
		searchAll.setPortletTitle("測試title_");
		List<DashboardPortlet> list2 = dashboardPortletDAO.searchPagination(searchAll, 0, 3);
		
		assertEquals(3, list2.size());
		
		for(int i = 0; i < 3; i++) {
			assertEquals(list.get(i), list2.get(i));
		}
	}
	
	@Test
	public void testSearchRowCountDashboardPortlets() throws Exception {
		List<DashboardPortlet> list = preparePaginationData();
		DashboardPortlet searchAll = domainObjectFactory.createDashboardPortlet();
		searchAll.setPortletTitle("測試title_");
		
		assertEquals(list.size(), dashboardPortletDAO.searchRowCount(searchAll));
	}
	
	private List<DashboardPortlet> preparePaginationData() throws Exception {
		List<DashboardPortlet> list = new ArrayList<DashboardPortlet>();
		
		for(int i = 0; i < 10; i++) {
			DashboardPortlet dashboardPortlet = domainObjectFactory.createDashboardPortlet();
			dashboardPortlet.setUserId("test");
			dashboardPortlet.setDashboardId("testDashboardId_" + i);
			dashboardPortlet.setPortletId("testDashboardPortletId_" + i);
			dashboardPortlet.setPortletTitle("測試title_" + i);
			dashboardPortlet.setColumnName("thick");
			dashboardPortlet.setSortOrder(i);
			dashboardPortlet.setVisible(true);
			dashboardPortlet.setCreateUserId("admin");
			dashboardPortlet.setCreateDate(Calendar.getInstance());
			
			dashboardPortletDAO.insert(dashboardPortlet);
			list.add(dashboardPortlet);
		}
		
		return list;
	}
}
