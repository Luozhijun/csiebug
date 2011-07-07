package csiebug.test.util;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.zip.ZipException;

import org.junit.Test;

import csiebug.util.FileUtility;
import csiebug.util.PropertiesUtility;
import csiebug.util.zip.ZipMonitorConsoleImpl;
import csiebug.util.zip.ZipUtility;

public class ZipUtilityTest {
	private String testFileSystemHome;
	
	private void init() throws IOException {
		testFileSystemHome = PropertiesUtility.load("csiebug/test/util/test.properties").getProperty("testFileSystemHome");
	}
	
	@Test
	public void testZip() throws IOException {
		init();
		
		ZipUtility zipUtility = new ZipUtility(false);
		zipUtility.setMonitor(new ZipMonitorConsoleImpl());
		
		File dir = prepareData();
		
		File zipFile = new File(testFileSystemHome + "/test.zip");
		File txtFile = new File(testFileSystemHome + "/testZip.txt");
		
		assertEquals(false, zipFile.exists());
		
		zipUtility.zip(zipFile, new File[]{dir, txtFile});
		
		assertEquals(true, zipFile.exists());
		
		//測試完成,刪除檔案
		zipFile.delete();
		txtFile.delete();
		FileUtility.deleteDirectory(dir);
	}
	
	@Test
	public void testUnzip() throws ZipException, IOException {
		init();
		
		ZipUtility zipUtility = new ZipUtility(false);
		zipUtility.setMonitor(new ZipMonitorConsoleImpl());
		
		File dir = prepareData();
		
		File zipFile = new File(testFileSystemHome + "/test.zip");
		File txtFile = new File(testFileSystemHome + "/testZip.txt");
		File dir2 = new File(testFileSystemHome + "/test2");
		
		zipUtility.zip(zipFile, new File[]{dir, txtFile});
		
		txtFile.delete();
		FileUtility.deleteDirectory(dir);
		
		zipUtility.unzip(zipFile);
		
		assertEquals(true, txtFile.exists());
		assertEquals(true, dir.exists());
		
		assertEquals(false, dir2.exists());
		
		zipUtility.unzip(zipFile, testFileSystemHome + "/test2");
		
		assertEquals(true, dir2.exists());
		File txtFile2 = new File(testFileSystemHome + "/test2/testZip.txt");
		File dir3 = new File(testFileSystemHome + "/test2/test");
		File dir4 = new File(testFileSystemHome + "/test2/test/test2");
		assertEquals(true, txtFile2.exists());
		assertEquals(true, FileUtility.isSameFile(txtFile, txtFile2));
		assertEquals(true, dir3.exists());
		assertEquals(2, dir3.list().length);
		assertEquals(true, dir4.exists());
		assertEquals(1, dir4.list().length);
		
		//測試完成,刪除檔案
		zipFile.delete();
		txtFile.delete();
		FileUtility.deleteDirectory(dir);
		FileUtility.deleteDirectory(dir2);
	}
	
	private File prepareData() throws IOException {
		Calendar today = Calendar.getInstance();
		String filePath1 = testFileSystemHome + "/testZip.txt";
		String filePath2 = testFileSystemHome + "/test/test_" + today.get(Calendar.YEAR) + (today.get(Calendar.MONTH) + 1) + today.get(Calendar.DAY_OF_MONTH) + ".txt";
		String filePath3 = testFileSystemHome + "/test/test2/test_" + today.get(Calendar.YEAR) + (today.get(Calendar.MONTH) + 1) + today.get(Calendar.DAY_OF_MONTH) + ".txt";
		String strContent = "hello world!";
		File file = null;
		File file2 = null;
		File file3 = null;
		File dir = new File(testFileSystemHome + "/test");
		ByteArrayInputStream bais = null;
		BufferedInputStream bis = null;
		
		try {
			bais = new ByteArrayInputStream(strContent.getBytes());
			
			file = new File(filePath1);
			
			if(file.exists()) {
				assertEquals(true, file.delete());
			} else {
				file.getParentFile().mkdirs();
			}
		
			FileUtility.writeFile(bais, filePath1);
			
			bais = new ByteArrayInputStream(strContent.getBytes());
			
			file2 = new File(filePath2);
			
			if(file2.exists()) {
				assertEquals(true, file2.delete());
			} else {
				file2.getParentFile().mkdirs();
			}
		
			FileUtility.writeFile(bais, filePath2);
			
			bais = new ByteArrayInputStream(strContent.getBytes());
			
			file3 = new File(filePath3);
			
			if(file3.exists()) {
				assertEquals(true, file3.delete());
			} else {
				file3.getParentFile().mkdirs();
			}
		
			FileUtility.writeFile(bais, filePath3);
		} finally {
			if(bais != null) {
				bais.close();
			}
			
			if(bis != null) {
				bis.close();
			}
		}
		
		return dir;
	}
}
