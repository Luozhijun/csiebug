package csiebug.test.util;

import static org.junit.Assert.*;

import org.junit.Test;

import csiebug.util.ShaEncoder;

public class ShaEncoderTest {

	@Test
	@Deprecated
	public void testGetSHA1String() {
		String text = "password";
		
		try {
			assertEquals("5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", ShaEncoder.getSHA1String(text));
		} catch(Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	public void testGetSHA256String() {
		String text = "password";
		
		try {
			assertEquals("5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8", ShaEncoder.getSHA256String(text));
		} catch(Exception ex) {
			fail(ex.getMessage());
		}
	}
}
