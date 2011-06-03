package csiebug.test.util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.junit.Test;

import csiebug.util.PropertiesUtility;

public class PropertiesUtilityTest {
	String sysDB = "jdbc/csiebug";
	String sysAPURL = "http://localhost:8080/csiebug-2.0/index";
	String APID = "csiebug-2.0";
	String sysLocale = "zh_TW";
	String defaultDateFormat = "23";
	String defaultPagination = "3";
	String MAX_UPLOAD_SIZE = "1048576000";
	String cookieLifecycle = "30";
	String ldapURL = "ldap://127.0.0.1:10389";
	String ldapAdmin = "uid=admin,ou=system";
	String ldapPassword = "rum5khxm";
	
	String zh_TW = "正體中文 - 台灣";
	String en_US = "English - United States";
	String zh_CN = "简化字 - 中华人民共和国";
	String ja_JP = "日本語";
	String ko_KR = "한국어";
	
	@Test
	public void testLoad() throws IOException {
		Properties properties = PropertiesUtility.load("csiebug.properties");
		
		assertEquals(sysDB, properties.get("sysDB"));
		assertEquals(sysAPURL, properties.get("sysAPURL"));
		assertEquals(APID, properties.get("APID"));
		assertEquals(sysLocale, properties.get("sysLocale"));
		assertEquals(defaultDateFormat, properties.get("defaultDateFormat"));
		assertEquals(defaultPagination, properties.get("defaultPagination"));
		assertEquals(MAX_UPLOAD_SIZE, properties.get("MAX_UPLOAD_SIZE"));
		assertEquals(cookieLifecycle, properties.get("cookieLifecycle"));
		assertEquals(ldapURL, properties.get("ldapURL"));
		assertEquals(ldapAdmin, properties.get("ldapAdmin"));
		assertEquals(ldapPassword, properties.get("ldapPassword"));
	}
	
	@Test
	public void testGetSubProperties() throws IOException {
		Properties properties = PropertiesUtility.load("MessageResources.properties");
		Properties subProperties = PropertiesUtility.getSubProperties(properties, "common.locale.");
		
		assertEquals(5, subProperties.size());
		assertEquals(zh_TW, subProperties.get("zh_TW"));
		assertEquals(en_US, subProperties.get("en_US"));
		assertEquals(zh_CN, subProperties.get("zh_CN"));
		assertEquals(ja_JP, subProperties.get("ja_JP"));
		assertEquals(ko_KR, subProperties.get("ko_KR"));
	}
	
	@Test
	public void testToMap() throws IOException {
		Properties properties = PropertiesUtility.load("MessageResources.properties");
		Properties subProperties = PropertiesUtility.getSubProperties(properties, "common.locale.");
		
		Map<String, String> map = PropertiesUtility.toMap(subProperties);
		assertEquals(5, map.size());
		
		Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			
			assertEquals(subProperties.get(entry.getKey()), map.get(entry.getKey()));
		}
	}
}
