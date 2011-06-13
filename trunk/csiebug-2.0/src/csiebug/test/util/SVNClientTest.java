package csiebug.test.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.DecoderException;
import org.junit.Test;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.wc.SVNRevision;

import csiebug.util.DesCoder;
import csiebug.util.FileUtility;
import csiebug.util.svn.SVNClient;

public class SVNClientTest {

	@Test
	public void testPrintRepositoryTree() throws SVNException, InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, DecoderException {
		String url = "http://localhost/svn/csiebug/csiebug-2.0/WebContent/example/svn/";
		String name = "test";
		String key = "123456789012345678901234123456789012345678901234";
		String encryptPassword = "cc4e1b57691c47fc";
		SVNClient client = new SVNClient(url, name, DesCoder.decryptCode(encryptPassword, key));
		
//		String value = "/subFolder ( author: 'csiebug'; revision: 822; date: Sun Jul 04 15:25:06 CST 2010)\n" +
//					   "/subFolder/test2.htm ( author: 'csiebug'; revision: 822; date: Sun Jul 04 15:25:06 CST 2010)\n" +
//					   "/test1.htm ( author: 'csiebug'; revision: 821; date: Fri Jul 02 17:26:26 CST 2010)\n";
//		
//		assertEquals(value, client.getRepositoryTreeFormatString(""));
		client.printRepositoryTree("");
	}
	
	@Test
	public void testIsTextFile() throws SVNException, InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, DecoderException {
		String url = "http://localhost/svn/csiebug/csiebug-2.0/";
		String name = "test";
		String key = "123456789012345678901234123456789012345678901234";
		String encryptPassword = "cc4e1b57691c47fc";
		SVNClient client = new SVNClient(url, name, DesCoder.decryptCode(encryptPassword, key));
		assertEquals(true, client.isTextFile("WebContent/example/svn/test1.htm"));
		assertEquals(false, client.isTextFile("WebContent/WEB-INF/lib/svnkit.jar"));
	}
	
	@Test
	public void testGetTextFileContent() throws SVNException, IOException, InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, DecoderException {
		String url = "http://localhost/svn/csiebug/csiebug-2.0/";
		String name = "test";
		String key = "123456789012345678901234123456789012345678901234";
		String encryptPassword = "cc4e1b57691c47fc";
		SVNClient client = new SVNClient(url, name, DesCoder.decryptCode(encryptPassword, key));
		String content = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">" + "\r\n" +
						 "<html>" + "\r\n" +
						 "<head>" + "\r\n" +
						 "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" + "\r\n" +
						 "<title>Insert title here</title>" + "\r\n" +
						 "</head>" + "\r\n" +
						 "<body>" + "\r\n" +
						 "	在此修改,用來測試history" + "\r\n" +
						 "</body>" + "\r\n" +
						 "</html>";
		
		assertEquals(content, client.getTextFileContent("WebContent/example/svn/test1.htm", "UTF-8"));
	}
	
	@Test
	public void testPrintRepositoryHistory() throws SVNException, InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, DecoderException {
		String url = "http://localhost/svn/csiebug/csiebug-2.0/";
		String name = "test";
		String key = "123456789012345678901234123456789012345678901234";
		String encryptPassword = "cc4e1b57691c47fc";
		SVNClient client = new SVNClient(url, name, DesCoder.decryptCode(encryptPassword, key));
		client.printRepositoryHistory(new String[]{"WebContent/example/svn/"}, 0, -1);
	}
	
	@Test
	public void testCheckout() throws SVNException, InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, DecoderException {
		String url = "http://localhost/svn/csiebug/csiebug-2.0/";
		String name = "test";
		String key = "123456789012345678901234123456789012345678901234";
		String encryptPassword = "cc4e1b57691c47fc";
		SVNClient client = new SVNClient(url, name, DesCoder.decryptCode(encryptPassword, key));
		
		String dirPath = "/home/csiebug/test/svn/";
		File dir = new File(dirPath);
		String svnPath = "http://localhost/svn/csiebug/csiebug-2.0/WebContent/example/svn/";
		
		client.checkout(svnPath, dirPath, false);
		
		File[] files = dir.listFiles();
		
		assertEquals(2, files.length);
		
		for(int i = 0; i < files.length; i++) {
			File file = files[i];
			
			if(file.isFile()) {
				assertEquals("test1.htm", file.getName());
			} else if(file.getName().equalsIgnoreCase(".svn")) {
				//svn目錄不在此測試了
			} else {
				//如果有子目錄出現,是不對的
				assertEquals(false, true);
			}
		}
		
		client.checkout(svnPath, dirPath, true);
		
		files = dir.listFiles();
		
		assertEquals(3, files.length);
		
		for(int i = 0; i < files.length; i++) {
			File file = files[i];
			
			if(file.isFile()) {
				assertEquals("test1.htm", file.getName());
			} else if(file.getName().equalsIgnoreCase(".svn")) {
				//svn目錄不在此測試了
			} else {
				//測試子目錄內容
				assertEquals("subFolder", file.getName());
				
				File[] files2 = file.listFiles();
				
				assertEquals(2, files2.length);
				
				for(int j = 0; j < files2.length; j++) {
					File file2 = files2[j];
					
					if(file2.isFile()) {
						assertEquals("test2.htm", file2.getName());
					} else if(file2.getName().equalsIgnoreCase(".svn")) {
						//svn目錄可以不用管
					} else {
						assertEquals(false, true);
					}
				}
			}
		}
		
		//測試完成,刪除checkout下來的目錄
		FileUtility.deleteDirectory(dir);
	}
	
//	@Test
	public void testUpdate() throws SVNException, IOException, InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, DecoderException {
		String url = "http://localhost/svn/csiebug/csiebug-2.0/";
		String name = "test";
		String key = "123456789012345678901234123456789012345678901234";
		String encryptPassword = "cc4e1b57691c47fc";
		SVNClient client = new SVNClient(url, name, DesCoder.decryptCode(encryptPassword, key));
		
		//先checkout下來
		String dirPath = "/home/csiebug/test/svn/";
		File dir = new File(dirPath);
		String svnPath = "http://localhost/svn/csiebug/csiebug-2.0/WebContent/example/svn/";
		
		//checkout 最新版本
		client.checkout(svnPath, dirPath, true);
		
		String filePath = "/home/csiebug/csiebug-2.0/test1.htm";
		File test1 = new File(filePath);
		String keyWord = "在此修改,用來測試history";
		
		//最新的檔案裡面應該有keyWord這串文字
		assertEquals(true, FileUtility.getTextFileContent(test1, "UTF-8").indexOf(keyWord) != -1);
		
		//將revision 817 update下來後是沒有keyWord這串文字的
		client.update(test1, SVNRevision.create(817), false);
		
		assertEquals(false, FileUtility.getTextFileContent(test1, "UTF-8").indexOf(keyWord) != -1);
		
		//checkout 最新版本
		client.checkout(svnPath, dirPath, true);
		
		filePath = "/home/csiebug/csiebug-2.0/subFolder/test2.htm";
		File test2 = new File(filePath);
		String keyWord2 = "在此修改,用來測試svn";
		
		//最新的檔案裡面應該有keyWord2這串文字
		assertEquals(true, FileUtility.getTextFileContent(test2, "UTF-8").indexOf(keyWord2) != -1);
		
		//從根目錄update revision 817下來(非recursive)
		client.update(dir, SVNRevision.create(817), false);
		
		//子目錄應該不存在
		assertEquals(false, test2.exists());
		
		//從根目錄update revision 817下來(recursive)
		client.update(dir, SVNRevision.create(817), true);
		
		//檔案裡面應該沒有keyWord2這串文字(是revision 817)
		assertEquals(false, FileUtility.getTextFileContent(test2, "UTF-8").indexOf(keyWord2) != -1);
		
		//測試完成,再把檔案更新為最新
		client.update(dir, true);
		
		//測試完成,刪除checkout下來的目錄
		FileUtility.deleteDirectory(dir);
	}
}
