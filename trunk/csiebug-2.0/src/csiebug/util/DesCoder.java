package csiebug.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class DesCoder {
	public static void main(String[] args) throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, DecoderException {
		Scanner in = new Scanner(System.in);
		
		int action = -1;
		
		while(action != 1 && action != 2) {
			System.out.print("請選擇動作: 1.加密密碼 or 2.解密密碼:");
			action = Integer.parseInt(in.nextLine());
		}
		
		if(action == 1) {
			System.out.print("請輸入密碼:");
			String password = in.nextLine();
			
			int action2 = -1;
			
			while(action2 != 1 && action2 != 2) {
				System.out.print("1.自行輸入48碼加密key or 2.程式亂數產生:");
				action2 = Integer.parseInt(in.nextLine());
			}
			
			String key;
			if(action2 == 1) {
				System.out.print("請輸入加密金鑰:(請輸入48碼數字)");
				key = in.nextLine();
			} else {
				key = StringUtility.makeRandomNumberKey(48);
				System.out.println("加密金鑰:" + key);
			}
				
			System.out.print("加密密碼:" + encryptCode(password, key));
		} else if(action == 2) {
			System.out.print("請輸入加密密碼:");
			String cipherText = in.nextLine();
			
			System.out.print("請輸入解密金鑰:(請輸入48碼數字)");
			String key = in.nextLine();
			
			System.out.print("密碼:" + decryptCode(cipherText, key));
		}
		
		in.close();
	}
	
	public static String encryptCode(String plainText, String sharedKey) throws DecoderException, InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
		AssertUtility.notNull(plainText);
		AssertUtility.notNull(sharedKey);
		
		String algorithm = "DESede";
		String transformation = "DESede/CBC/PKCS5Padding";

		byte[] keyValue = Hex.decodeHex(sharedKey.toCharArray());

		DESedeKeySpec keySpec = new DESedeKeySpec(keyValue);

		/* Initialization Vector of 8 bytes set to zero. */
		IvParameterSpec iv = new IvParameterSpec(new byte[8]);

		SecretKey key = SecretKeyFactory.getInstance(algorithm).generateSecret(keySpec);
		Cipher encrypter = Cipher.getInstance(transformation);
		encrypter.init(Cipher.ENCRYPT_MODE, key, iv);

		byte[] input = plainText.getBytes("UTF-8");

		byte[] encrypted = encrypter.doFinal(input);
		
		return new String(Hex.encodeHex(encrypted));
	}
	
	public static String decryptCode(String cipherText, String sharedKey) throws DecoderException, InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		AssertUtility.notNull(cipherText);
		AssertUtility.notNull(sharedKey);
		
		String algorithm = "DESede";
		String transformation = "DESede/CBC/PKCS5Padding";

		byte[] keyValue = Hex.decodeHex(sharedKey.toCharArray());

		DESedeKeySpec keySpec = new DESedeKeySpec(keyValue);

		/* Initialization Vector of 8 bytes set to zero. */
		IvParameterSpec iv = new IvParameterSpec(new byte[8]);

		SecretKey key = SecretKeyFactory.getInstance(algorithm).generateSecret(keySpec);
		Cipher encrypter = Cipher.getInstance(transformation);
		encrypter.init(Cipher.ENCRYPT_MODE, key, iv);

		Cipher decrypter = Cipher.getInstance(transformation);
		decrypter.init(Cipher.DECRYPT_MODE, key, iv);

		byte[] decrypted = decrypter.doFinal(Hex.decodeHex(cipherText.toCharArray()));

		return new String(decrypted, "UTF-8");
	}
	
}
