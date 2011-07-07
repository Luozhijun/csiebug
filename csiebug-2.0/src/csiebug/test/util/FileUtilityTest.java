package csiebug.test.util;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;

import org.junit.Test;

import csiebug.util.FileUtility;

public class FileUtilityTest {

	@Test
	public void testWriteFile() throws IOException {
		Calendar today = Calendar.getInstance();
		String filePath = "/home/csiebug/test_" + today.get(Calendar.YEAR) + (today.get(Calendar.MONTH) + 1) + today.get(Calendar.DAY_OF_MONTH) + ".txt";
		String strContent = "hello world!";
		File file = null;
		ByteArrayInputStream bais = null;
		BufferedInputStream bis = null;
		
		try {
			bais = new ByteArrayInputStream(strContent.getBytes());
			
			file = new File(filePath);
			
			if(file.exists()) {
				assertEquals(true, file.delete());
			}
		
			FileUtility.writeFile(bais, filePath);
			
			assertEquals(true, file.exists());
			
			byte[] data = new byte[1]; 
			bis = new BufferedInputStream(new FileInputStream(file));
			
			StringBuffer compareContent = new StringBuffer();
			while(bis.read(data) != -1) { 
				compareContent.append(new String(data));
			}
			
			assertEquals(strContent, compareContent.toString());
		} finally {
			if(bais != null) {
				bais.close();
			}
			
			if(bis != null) {
				bis.close();
			}
			
			if(file != null) {
				assertEquals(true, file.delete());
			}
		}
	}
	
	@Test
	public void testIsSameFile() throws IOException {
		Calendar today = Calendar.getInstance();
		String filePath = "/home/csiebug/test_" + today.get(Calendar.YEAR) + (today.get(Calendar.MONTH) + 1) + today.get(Calendar.DAY_OF_MONTH) + "_1.txt";
		String filePath2 = "/home/csiebug/test_" + today.get(Calendar.YEAR) + (today.get(Calendar.MONTH) + 1) + today.get(Calendar.DAY_OF_MONTH) + "_2.txt";
		String filePath3 = "/home/csiebug/test_" + today.get(Calendar.YEAR) + (today.get(Calendar.MONTH) + 1) + today.get(Calendar.DAY_OF_MONTH) + "_3.txt";
		
		String strContent = "hello world!";
		String strContent2 = "hello csiebug!";
		
		File file = makeTestTxtFile(filePath, strContent);
		File file2 = makeTestTxtFile(filePath2, strContent);
		File file3 = makeTestTxtFile(filePath3, strContent2);
		
		assertEquals(true, FileUtility.isSameFile(file, file2));
		assertEquals(false, FileUtility.isSameFile(file, file3));
		assertEquals(false, FileUtility.isSameFile(file2, file3));
		
		assertEquals(true, file.delete());
		assertEquals(true, file2.delete());
		assertEquals(true, file3.delete());
	}
	
	@Test
	public void testCopyFile() throws IOException {
		Calendar today = Calendar.getInstance();
		String filePath = "/home/csiebug/test_" + today.get(Calendar.YEAR) + (today.get(Calendar.MONTH) + 1) + today.get(Calendar.DAY_OF_MONTH) + ".txt";
		String filePath2 = "/home/csiebug/test_" + today.get(Calendar.YEAR) + (today.get(Calendar.MONTH) + 1) + today.get(Calendar.DAY_OF_MONTH) + "_copy.txt";
		
		File file = makeTestTxtFile(filePath, "hello world!");
		File file2 = new File(filePath2);
		
		FileUtility.copyFile(file, file2);
		
		assertEquals(true, FileUtility.isSameFile(file, file2));
		
		assertEquals(true, file.delete());
		assertEquals(true, file2.delete());
	}
	
	@Test
	public void testCatFile() throws IOException {
		Calendar today = Calendar.getInstance();
		String filePath = "/home/csiebug/test_" + today.get(Calendar.YEAR) + (today.get(Calendar.MONTH) + 1) + today.get(Calendar.DAY_OF_MONTH) + "1.txt";
		String filePath2 = "/home/csiebug/test_" + today.get(Calendar.YEAR) + (today.get(Calendar.MONTH) + 1) + today.get(Calendar.DAY_OF_MONTH) + "2.txt";
		String filePath3 = "/home/csiebug/test_" + today.get(Calendar.YEAR) + (today.get(Calendar.MONTH) + 1) + today.get(Calendar.DAY_OF_MONTH) + "3.txt";
		String filePath4 = "/home/csiebug/test_" + today.get(Calendar.YEAR) + (today.get(Calendar.MONTH) + 1) + today.get(Calendar.DAY_OF_MONTH) + "4.txt";
		
		File file = makeTestTxtFile(filePath, "hello ");
		File file2 = makeTestTxtFile(filePath2, "george!");
		File file3 = new File(filePath3);
		File file4 = makeTestTxtFile(filePath4, "hello george!");
		File[] files = new File[2];
		files[0] = file;
		files[1] = file2;
		
		FileUtility.catFile(files, file3);
		
		assertEquals(true, FileUtility.isSameFile(file3, file4));
		
		assertEquals(true, file.delete());
		assertEquals(true, file2.delete());
		assertEquals(true, file3.delete());
		assertEquals(true, file4.delete());
	}
	
	private File makeTestTxtFile(String filePath, String strContent) throws IOException {
		File file = null;
		ByteArrayInputStream bais = null;
		
		try {
			bais = new ByteArrayInputStream(strContent.getBytes());
			
			file = new File(filePath);
			
			if(file.exists()) {
				assertEquals(true, file.delete());
			}
			
			FileUtility.writeFile(bais, filePath);
		} finally {
			if(bais != null) {
				bais.close();
			}
		}
		
		return file;
	}
	
	@Test
	public void testGetTextFileContent() throws IOException {
		Calendar today = Calendar.getInstance();
		String filePath = "/home/csiebug/test_" + today.get(Calendar.YEAR) + (today.get(Calendar.MONTH) + 1) + today.get(Calendar.DAY_OF_MONTH) + ".txt";
		String strContent = "hello world!";
		File file = null;
		ByteArrayInputStream bais = null;
		BufferedInputStream bis = null;
		
		try {
			bais = new ByteArrayInputStream(strContent.getBytes());
			
			file = new File(filePath);
			
			if(file.exists()) {
				assertEquals(true, file.delete());
			}
		
			FileUtility.writeFile(bais, filePath);
			
			assertEquals(strContent, FileUtility.getTextFileContent(file, "UTF-8"));
		} finally {
			if(bais != null) {
				bais.close();
			}
			
			if(bis != null) {
				bis.close();
			}
			
			if(file != null) {
				assertEquals(true, file.delete());
			}
		}
	}
	
	@Test
	public void testDeleteDirectory() throws IOException {
		Calendar today = Calendar.getInstance();
		String dirPath = "/home/csiebug/test";
		String filePath1 = "/home/csiebug/test/test_" + today.get(Calendar.YEAR) + (today.get(Calendar.MONTH) + 1) + today.get(Calendar.DAY_OF_MONTH) + ".txt";
		String filePath2 = "/home/csiebug/test/test2/test_" + today.get(Calendar.YEAR) + (today.get(Calendar.MONTH) + 1) + today.get(Calendar.DAY_OF_MONTH) + ".txt";
		String strContent = "hello world!";
		File file = null;
		File file2 = null;
		File dir = null;
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
			
			dir = new File(dirPath);
			
			assertEquals(true, FileUtility.deleteDirectory(dir));
			
			assertEquals(false, dir.exists());
		} finally {
			if(bais != null) {
				bais.close();
			}
			
			if(bis != null) {
				bis.close();
			}
		}
	}
}
