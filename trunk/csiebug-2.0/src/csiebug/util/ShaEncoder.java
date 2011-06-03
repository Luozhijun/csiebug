package csiebug.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class ShaEncoder {
	private static String convertToHex(byte[] data) {
		AssertUtility.notNull(data);
		
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
        	int halfbyte = (data[i] >>> 4) & 0x0F;
        	int two_halfs = 0;
        	do {
	            if ((0 <= halfbyte) && (halfbyte <= 9))
	                buf.append((char) ('0' + halfbyte));
	            else
	            	buf.append((char) ('a' + (halfbyte - 10)));
	            halfbyte = data[i] & 0x0F;
        	} while(two_halfs++ < 1);
        }
        return buf.toString();
    }
	
	private static String getHashString(String text, String algorithm) throws NoSuchAlgorithmException, UnsupportedEncodingException  {
		AssertUtility.notNull(text);
		AssertUtility.notNullAndNotSpace(algorithm);
		
		MessageDigest md;
		md = MessageDigest.getInstance(algorithm);
		md.update(text.getBytes("UTF-8"), 0, text.length());
		byte[] hash = md.digest();
		return convertToHex(hash);
	}
	
	private static String getBase64(String text, String algorithm) throws NoSuchAlgorithmException {
		AssertUtility.notNull(text);
		AssertUtility.notNullAndNotSpace(algorithm);
		
		String base64;
		MessageDigest md = MessageDigest.getInstance(algorithm);
		md.update(text.getBytes());
		base64 = new BASE64Encoder().encode(md.digest());
		
		return base64;
	}
	
	/**
	 * fortify不鼓勵使用SHA-1演算法來加密
	 * @param text
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	@Deprecated
	public static String getSHA1String(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException  {
		AssertUtility.notNull(text);
		return getHashString(text, "SHA-1");
    }
	
	public static String getSHA256String(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException  {
		AssertUtility.notNull(text);
		return getHashString(text, "SHA-256");
	}
	
	/**
	 * fortify不鼓勵使用SHA-1演算法來加密
	 * @param text
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	@Deprecated
	public static String getSHA1Base64(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException  {
		AssertUtility.notNull(text);
		return getBase64(text, "SHA-1");
    }
	
	public static String getSHA256Base64(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException  {
		AssertUtility.notNull(text);
		return getBase64(text, "SHA-256");
	}
}
