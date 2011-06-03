package csiebug.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;

public class FileUtility {
	/**
	 * 從InputStream寫入檔案
	 * @param is
	 * @param filePath
	 * @throws IOException 
	 * @throws Exception
	 */
	public static void writeFile(InputStream is, String filePath) throws IOException  {
		AssertUtility.notNull(is);
		AssertUtility.notNull(filePath);
		
		BufferedInputStream bis = null;
	    BufferedOutputStream bos = null;
	    try { 
	      byte[] data = new byte[1]; 

	      bis = new BufferedInputStream(is); 
	      bos = new BufferedOutputStream(new FileOutputStream(filePath));

	      while(bis.read(data) != -1) { 
	          bos.write(data); 
	      }
	      
	      // 將緩衝區中的資料全部寫出 
	      bos.flush();
	    } finally {
	      //關閉串流
	      if(bis != null)
	        bis.close();
	      if(bos != null)
	        bos.close();
	    }
	}

	  /**
	   * 拷貝檔案(NIO)
	   * @param srcFile
	   * @param desFile
	 * @throws IOException 
	   * @throws Exception
	   */
	public static void copyFile(File srcFile, File desFile) throws IOException  {
		AssertUtility.notNull(srcFile);
		AssertUtility.notNull(desFile);
		
		FileInputStream fis = new FileInputStream(srcFile);
		FileOutputStream fos = new FileOutputStream(desFile);
		
		try {
			// Create channel on the source
		    FileChannel srcChannel = fis.getChannel();
		    // Create channel on the destination
		    FileChannel dstChannel = fos.getChannel();
		    
		    // Copy file contents from source to destination
		    dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
		    
		    // Close the channels
		    srcChannel.close();
		    dstChannel.close();
	    } finally {
		    fis.close();
		    fos.close();
	    }
	}
	
	/**
	 * 檔案比對
	 * @param file1
	 * @param file2
	 * @return
	 * @throws IOException
	 */
	public static boolean isSameFile(File file1, File file2) throws IOException {
		AssertUtility.notNull(file1);
		AssertUtility.notNull(file2);
		
		boolean result = true;
		BufferedInputStream bis1 = null;
		BufferedInputStream bis2 = null;
		byte[] data1 = new byte[1];
		byte[] data2 = new byte[1];
		
		try {
			bis1 = new BufferedInputStream(new FileInputStream(file1));
			bis2 = new BufferedInputStream(new FileInputStream(file2));
			
			while(bis1.read(data1) != -1 && bis2.read(data2) != -1) { 
				result = new String(data1).equals(new String(data2));
				
				if(!result) {
					break;
				}
			}
		} finally {
			if(bis1 != null) {
				bis1.close();
			}
			
			if(bis2 != null) {
				bis2.close();
			}
		}
		
		return result;
	}
	
	/**
	 * 取得文字檔的內容
	 * @param file
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String getTextFileContent(File file, String charset) throws IOException {
		AssertUtility.notNull(file);
		AssertUtility.notNullAndNotSpace(charset);
		
		StringBuffer content = new StringBuffer();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
		try {
			String line = reader.readLine();
			while(line != null) {
				content.append(line);
				line = reader.readLine();
			}
		} finally {
			reader.close();
		}
		
		return content.toString();
	}
	
	/**
	 * 刪除非空的目錄
	 * @param dir
	 * @return
	 */
	public static boolean deleteDirectory(File dir) {
		AssertUtility.notNull(dir);
		
		boolean flag = true;
		
		File[] files = dir.listFiles();
		
		for(int i = 0; i < files.length; i++) {
			File file = files[i];
			
			if(file.isDirectory()) {
				flag = deleteDirectory(file);
			} else {
				flag = file.delete();
			}
			
			if(!flag) {
				return false;
			}
		}
		
		flag = dir.delete();
		
		return flag;
	}
}
