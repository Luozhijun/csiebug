package csiebug.test.util;

import static org.junit.Assert.*;

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

import csiebug.util.DesCoder;

public class DesCoderTest {
	@Test
	public void testEncryptCode() throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, DecoderException {
		String text = "password";
		//key長度48
		String key = "123456789012345678901234123456789012345678901234";
		assertEquals("c878b8d1815a703c1c39726039fa9806", DesCoder.encryptCode(text, key));
	}
	
	@Test
	public void testDecryptCode() throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, DecoderException {
		String encryptCode = "c878b8d1815a703c1c39726039fa9806";
		//key長度48
		String key = "123456789012345678901234123456789012345678901234";
		assertEquals("password", DesCoder.decryptCode(encryptCode, key));
	}
}
