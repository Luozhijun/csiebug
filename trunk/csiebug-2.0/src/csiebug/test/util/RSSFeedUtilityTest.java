package csiebug.test.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;

import csiebug.util.FileUtility;
import csiebug.util.PropertiesUtility;
import csiebug.util.rss.RSSFeedUtility;
import csiebug.util.rss.RSSItem;

public class RSSFeedUtilityTest {
	private static Logger logger = Logger.getLogger(RSSFeedUtilityTest.class.getPackage().getName());
	private String testFileSystemHome;
	
	private void init() throws IOException {
		testFileSystemHome = PropertiesUtility.load("csiebug/test/util/test.properties").getProperty("testFileSystemHome");
	}
	
	@Test
	public void testAddItem() throws IOException, DocumentException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
		init();
		
		File feedFile = new File(testFileSystemHome + "/testFeed.xml");
		
		assertEquals(false, feedFile.exists());
		
		RSSFeedUtility utility = new RSSFeedUtility(feedFile, null);
		
		assertEquals(true, feedFile.exists());
		
		Document document = DocumentHelper.parseText(FileUtility.getTextFileContent(feedFile, "UTF-8"));
		Element channelElement = document.getRootElement().element("channel");
		assertEquals(0, channelElement.elements("item").size());
		
		String title = "test";
		String description = "testDescription";
		String link = "http://test.com";
		RSSItem item = new RSSItem();
		item.setTitle(title);
		item.setDescription(description);
		item.setLink(link);
		item.setPubDate(Calendar.getInstance());
		
		utility.addItem(item);
		
		document = DocumentHelper.parseText(FileUtility.getTextFileContent(feedFile, "UTF-8"));
		channelElement = document.getRootElement().element("channel");
		assertEquals(1, channelElement.elements("item").size());
		Element itemElement = (Element)channelElement.elements("item").get(0);
		assertEquals(title, itemElement.element("title").getText());
		assertEquals(description, itemElement.element("description").getText());
		assertEquals(link, itemElement.element("link").getText());
		
		//測試完成,刪除檔案
		if(!feedFile.delete()) {
			logger.warning(feedFile.getName() + " was deleted failed!");
		}
	}
	
	@Test
	public void testAddItems() throws IOException, DocumentException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
		init();
		
		File feedFile = new File(testFileSystemHome + "/testFeed.xml");
		
		assertEquals(false, feedFile.exists());
		
		RSSFeedUtility utility = new RSSFeedUtility(feedFile, null);
		
		assertEquals(true, feedFile.exists());
		
		Document document = DocumentHelper.parseText(FileUtility.getTextFileContent(feedFile, "UTF-8"));
		Element channelElement = document.getRootElement().element("channel");
		assertEquals(0, channelElement.elements("item").size());
		
		List<RSSItem> items = new ArrayList<RSSItem>();
		String title = "test";
		String description = "testDescription";
		String link = "http://test.com";
		for(int i = 0; i < 3; i++) {
			RSSItem item = new RSSItem();
			item.setTitle(title + "_" + i);
			item.setDescription(description + "_" + i);
			item.setLink(link + "/" + i + ".htm");
			item.setPubDate(Calendar.getInstance());
			
			items.add(item);
		}
		
		utility.addItems(items);
		
		document = DocumentHelper.parseText(FileUtility.getTextFileContent(feedFile, "UTF-8"));
		channelElement = document.getRootElement().element("channel");
		assertEquals(3, channelElement.elements("item").size());
		
		for(int i = 0; i < channelElement.elements("item").size(); i++) {
			Element itemElement = (Element)channelElement.elements("item").get(i);
			assertEquals(title + "_" + i, itemElement.element("title").getText());
			assertEquals(description + "_" + i, itemElement.element("description").getText());
			assertEquals(link + "/" + i + ".htm", itemElement.element("link").getText());
		}
		
		//測試完成,刪除檔案
		if(!feedFile.delete()) {
			logger.warning(feedFile.getName() + " was deleted failed!");
		}
	}
}
