package csiebug.test.util;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import csiebug.util.AssertUtility;

public class AssertUtilityTest {

	@Test
	public void testNotNullObjectString() {
		String test = null;
		String message = "test message";
		
		try {
			AssertUtility.notNull(test, message);
			assertEquals(true, false);
		} catch(NullPointerException ex) {
			assertEquals(message, ex.getMessage());
		}
		
		test = "";
		
		try {
			AssertUtility.notNull(test, message);
			assertEquals(true, true);
		} catch(NullPointerException ex) {
			assertEquals(true, false);
		}
		
		test = "   ";
		
		try {
			AssertUtility.notNull(test, message);
			assertEquals(true, true);
		} catch(NullPointerException ex) {
			assertEquals(true, false);
		}
		
		test = "abc";
		
		try {
			AssertUtility.notNull(test, message);
			assertEquals(true, true);
		} catch(NullPointerException ex) {
			assertEquals(true, false);
		}
	}
	
	@Test
	public void testNotNullObject() {
		String test = null;
		
		try {
			AssertUtility.notNull(test);
			assertEquals(true, false);
		} catch(NullPointerException ex) {
			assertEquals("Object is null!!", ex.getMessage());
		}
		
		test = "";
		
		try {
			AssertUtility.notNull(test);
			assertEquals(true, true);
		} catch(NullPointerException ex) {
			assertEquals(true, false);
		}
		
		test = "   ";
		
		try {
			AssertUtility.notNull(test);
			assertEquals(true, true);
		} catch(NullPointerException ex) {
			assertEquals(true, false);
		}
		
		test = "abc";
		
		try {
			AssertUtility.notNull(test);
			assertEquals(true, true);
		} catch(NullPointerException ex) {
			assertEquals(true, false);
		}
	}
	
	@Test
	public void testNotNullAndNotSpaceObjectString() {
		String test = null;
		String message = "test message";
		
		try {
			AssertUtility.notNullAndNotSpace(test, message);
			assertEquals(true, false);
		} catch(NullPointerException ex) {
			assertEquals(message, ex.getMessage());
		}
		
		test = "";
		
		try {
			AssertUtility.notNullAndNotSpace(test, message);
			assertEquals(true, false);
		} catch(NullPointerException ex) {
			assertEquals(message, ex.getMessage());
		}
		
		test = "   ";
		
		try {
			AssertUtility.notNullAndNotSpace(test, message);
			assertEquals(true, false);
		} catch(NullPointerException ex) {
			assertEquals(message, ex.getMessage());
		}
		
		test = "abc";
		
		try {
			AssertUtility.notNullAndNotSpace(test, message);
			assertEquals(true, true);
		} catch(NullPointerException ex) {
			assertEquals(true, false);
		}
	}
	
	@Test
	public void testNotNullAndNotSpaceObject() {
		String test = null;
		
		try {
			AssertUtility.notNullAndNotSpace(test);
			assertEquals(true, false);
		} catch(NullPointerException ex) {
			assertEquals("String is null or empty!!", ex.getMessage());
		}
		
		test = "";
		
		try {
			AssertUtility.notNullAndNotSpace(test);
			assertEquals(true, false);
		} catch(NullPointerException ex) {
			assertEquals("String is null or empty!!", ex.getMessage());
		}
		
		test = "   ";
		
		try {
			AssertUtility.notNullAndNotSpace(test);
			assertEquals(true, false);
		} catch(NullPointerException ex) {
			assertEquals("String is null or empty!!", ex.getMessage());
		}
		
		test = "abc";
		
		try {
			AssertUtility.notNullAndNotSpace(test);
			assertEquals(true, true);
		} catch(NullPointerException ex) {
			assertEquals(true, false);
		}
	}
	
	@Test
	public void testNotNullAndNotEmptyCollectionString() {
		ArrayList<String> test = null;
		String message = "test message";
		
		try {
			AssertUtility.notNullAndNotEmpty(test, message);
			assertEquals(true, false);
		} catch(NullPointerException ex) {
			assertEquals(message, ex.getMessage());
		}
		
		test = new ArrayList<String>();
		
		try {
			AssertUtility.notNullAndNotEmpty(test, message);
			assertEquals(true, false);
		} catch(NullPointerException ex) {
			assertEquals(message, ex.getMessage());
		}
		
		test.add("abc");
		
		try {
			AssertUtility.notNullAndNotEmpty(test, message);
			assertEquals(true, true);
		} catch(NullPointerException ex) {
			assertEquals(true, false);
		}
	}
	
	@Test
	public void testNotNullAndNotEmptyCollection() {
		ArrayList<String> test = null;
		
		try {
			AssertUtility.notNullAndNotEmpty(test);
			assertEquals(true, false);
		} catch(NullPointerException ex) {
			assertEquals("Collection is null or empty!!", ex.getMessage());
		}
		
		test = new ArrayList<String>();
		
		try {
			AssertUtility.notNullAndNotEmpty(test);
			assertEquals(true, false);
		} catch(NullPointerException ex) {
			assertEquals("Collection is null or empty!!", ex.getMessage());
		}
		
		test.add("abc");
		
		try {
			AssertUtility.notNullAndNotEmpty(test);
			assertEquals(true, true);
		} catch(NullPointerException ex) {
			assertEquals(true, false);
		}
	}
	
	@Test
	public void testNotNullAndNotEmptyArrayString() {
		String[] test = null;
		String message = "test message";
		
		try {
			AssertUtility.notNullAndNotEmpty(test, message);
			assertEquals(true, false);
		} catch(NullPointerException ex) {
			assertEquals(message, ex.getMessage());
		}
		
		test = new String[0];
		
		try {
			AssertUtility.notNullAndNotEmpty(test, message);
			assertEquals(true, false);
		} catch(NullPointerException ex) {
			assertEquals(message, ex.getMessage());
		}
		
		test = new String[10];
		
		try {
			AssertUtility.notNullAndNotEmpty(test, message);
			assertEquals(true, true);
		} catch(NullPointerException ex) {
			assertEquals(true, false);
		}
		
		test[0] = "abc";
		
		try {
			AssertUtility.notNullAndNotEmpty(test, message);
			assertEquals(true, true);
		} catch(NullPointerException ex) {
			assertEquals(true, false);
		}
	}
	
	@Test
	public void testNotNullAndNotEmptyArray() {
		String[] test = null;
		
		try {
			AssertUtility.notNullAndNotEmpty(test);
			assertEquals(true, false);
		} catch(NullPointerException ex) {
			assertEquals("Array is null or empty!!", ex.getMessage());
		}
		
		test = new String[0];
		
		try {
			AssertUtility.notNullAndNotEmpty(test);
			assertEquals(true, false);
		} catch(NullPointerException ex) {
			assertEquals("Array is null or empty!!", ex.getMessage());
		}
		
		test = new String[10];
		
		try {
			AssertUtility.notNullAndNotEmpty(test);
			assertEquals(true, true);
		} catch(NullPointerException ex) {
			assertEquals(true, false);
		}
		
		test[0] = "abc";
		
		try {
			AssertUtility.notNullAndNotEmpty(test);
			assertEquals(true, true);
		} catch(NullPointerException ex) {
			assertEquals(true, false);
		}
	}
	
	@Test
	public void testAllElementNotNullCollectionString() {
		ArrayList<String> test = null;
		String message = "test message";
		
		try {
			AssertUtility.allElementNotNull(test, message);
			assertEquals(true, false);
		} catch(NullPointerException ex) {
			assertEquals(message, ex.getMessage());
		}
		
		test = new ArrayList<String>();
		
		try {
			AssertUtility.allElementNotNull(test, message);
			assertEquals(true, false);
		} catch(NullPointerException ex) {
			assertEquals(message, ex.getMessage());
		}
		
		test.add("abc");
		
		try {
			AssertUtility.allElementNotNull(test, message);
			assertEquals(true, true);
		} catch(NullPointerException ex) {
			assertEquals(true, false);
		}
		
		test.add(null);
		
		try {
			AssertUtility.allElementNotNull(test, message);
			assertEquals(true, false);
		} catch(NullPointerException ex) {
			assertEquals(message, ex.getMessage());
		}
	}
	
	@Test
	public void testAllElementNotNullCollection() {
		ArrayList<String> test = null;
		
		try {
			AssertUtility.allElementNotNull(test);
			assertEquals(true, false);
		} catch(NullPointerException ex) {
			assertEquals("Element can not be null!!", ex.getMessage());
		}
		
		test = new ArrayList<String>();
		
		try {
			AssertUtility.allElementNotNull(test);
			assertEquals(true, false);
		} catch(NullPointerException ex) {
			assertEquals("Element can not be null!!", ex.getMessage());
		}
		
		test.add("abc");
		
		try {
			AssertUtility.allElementNotNull(test);
			assertEquals(true, true);
		} catch(NullPointerException ex) {
			assertEquals(true, false);
		}
		
		test.add(null);
		
		try {
			AssertUtility.allElementNotNull(test);
			assertEquals(true, false);
		} catch(NullPointerException ex) {
			assertEquals("Element can not be null!!", ex.getMessage());
		}
	}
	
	@Test
	public void testAllElementNotNullArrayString() {
		String[] test = null;
		String message = "test message";
		
		try {
			AssertUtility.allElementNotNull(test, message);
			assertEquals(true, false);
		} catch(NullPointerException ex) {
			assertEquals(message, ex.getMessage());
		}
		
		test = new String[0];
		
		try {
			AssertUtility.allElementNotNull(test, message);
			assertEquals(true, false);
		} catch(NullPointerException ex) {
			assertEquals(message, ex.getMessage());
		}
		
		test = new String[2];
		
		try {
			AssertUtility.allElementNotNull(test, message);
			assertEquals(true, false);
		} catch(NullPointerException ex) {
			assertEquals(message, ex.getMessage());
		}
		
		test[0] = "abc";
		
		try {
			AssertUtility.allElementNotNull(test, message);
			assertEquals(true, false);
		} catch(NullPointerException ex) {
			assertEquals(message, ex.getMessage());
		}
		
		test[1] = "def";
		
		try {
			AssertUtility.allElementNotNull(test, message);
			assertEquals(true, true);
		} catch(NullPointerException ex) {
			assertEquals(true, false);
		}
	}
	
	@Test
	public void testAllElementNotNullArray() {
		String[] test = null;
		
		try {
			AssertUtility.allElementNotNull(test);
			assertEquals(true, false);
		} catch(NullPointerException ex) {
			assertEquals("Element can not be null!!", ex.getMessage());
		}
		
		test = new String[0];
		
		try {
			AssertUtility.allElementNotNull(test);
			assertEquals(true, false);
		} catch(NullPointerException ex) {
			assertEquals("Element can not be null!!", ex.getMessage());
		}
		
		test = new String[2];
		
		try {
			AssertUtility.allElementNotNull(test);
			assertEquals(true, false);
		} catch(NullPointerException ex) {
			assertEquals("Element can not be null!!", ex.getMessage());
		}
		
		test[0] = "abc";
		
		try {
			AssertUtility.allElementNotNull(test);
			assertEquals(true, false);
		} catch(NullPointerException ex) {
			assertEquals("Element can not be null!!", ex.getMessage());
		}
		
		test[1] = "def";
		
		try {
			AssertUtility.allElementNotNull(test);
			assertEquals(true, true);
		} catch(NullPointerException ex) {
			assertEquals(true, false);
		}
	}
	
	@Test
	public void testIsNotNullAndNotSpace() {
		String test = null;
		assertEquals(false, AssertUtility.isNotNullAndNotSpace(test));
		
		test = "";
		assertEquals(false, AssertUtility.isNotNullAndNotSpace(test));
		
		test = "   ";
		assertEquals(false, AssertUtility.isNotNullAndNotSpace(test));
		
		test = "abc";
		assertEquals(true, AssertUtility.isNotNullAndNotSpace(test));
	}
	
	@Test
	public void testIsNotNullAndNotEmptyCollection() {
		ArrayList<String> test = null;
		assertEquals(false, AssertUtility.isNotNullAndNotEmpty(test));
		
		test = new ArrayList<String>();
		assertEquals(false, AssertUtility.isNotNullAndNotEmpty(test));
		
		test.add("abc");
		assertEquals(true, AssertUtility.isNotNullAndNotEmpty(test));
	}
	
	@Test
	public void testIsNotNullAndNotEmptyArray() {
		String[] test = null;
		assertEquals(false, AssertUtility.isNotNullAndNotEmpty(test));
		
		test = new String[0];
		assertEquals(false, AssertUtility.isNotNullAndNotEmpty(test));
		
		test = new String[10];
		assertEquals(true, AssertUtility.isNotNullAndNotEmpty(test));
		
		test[0] = "abc";
		assertEquals(true, AssertUtility.isNotNullAndNotEmpty(test));
	}
	
	@Test
	public void testIsAllElementNotNullCollection() {
		ArrayList<String> test = null;
		assertEquals(false, AssertUtility.isAllElementNotNull(test));
		
		test = new ArrayList<String>();
		assertEquals(false, AssertUtility.isAllElementNotNull(test));
		
		test.add("abc");
		assertEquals(true, AssertUtility.isAllElementNotNull(test));
		
		test.add(null);
		assertEquals(false, AssertUtility.isAllElementNotNull(test));
	}
	
	@Test
	public void testIsAllElementNotNullArray() {
		String[] test = null;
		assertEquals(false, AssertUtility.isAllElementNotNull(test));
		
		test = new String[0];
		assertEquals(false, AssertUtility.isAllElementNotNull(test));
		
		test = new String[2];
		assertEquals(false, AssertUtility.isAllElementNotNull(test));
		
		test[0] = "abc";
		assertEquals(false, AssertUtility.isAllElementNotNull(test));
		
		test[1] = "def";
		assertEquals(true, AssertUtility.isAllElementNotNull(test));
	}
	
	@Test
	public void testIsTrueString() {
		String test = null;
		assertEquals(false, AssertUtility.isTrue(test));
		
		test = "";
		assertEquals(false, AssertUtility.isTrue(test));
		
		test = "   ";
		assertEquals(false, AssertUtility.isTrue(test));
		
		test = "abc";
		assertEquals(false, AssertUtility.isTrue(test));
		
		test = "false";
		assertEquals(false, AssertUtility.isTrue(test));
		
		test = "False";
		assertEquals(false, AssertUtility.isTrue(test));
		
		test = "FALSE";
		assertEquals(false, AssertUtility.isTrue(test));
		
		test = "true";
		assertEquals(true, AssertUtility.isTrue(test));
		
		test = "True";
		assertEquals(true, AssertUtility.isTrue(test));
		
		test = "TRUE";
		assertEquals(true, AssertUtility.isTrue(test));
	}
	
	@Test
	public void testIsTrueObject() {
		StringBuffer test = null;
		assertEquals(false, AssertUtility.isTrue(test));
		
		test = new StringBuffer("");
		assertEquals(false, AssertUtility.isTrue(test));
		
		test = new StringBuffer("   ");
		assertEquals(false, AssertUtility.isTrue(test));
		
		test = new StringBuffer("abc");
		assertEquals(false, AssertUtility.isTrue(test));
		
		test = new StringBuffer("false");
		assertEquals(false, AssertUtility.isTrue(test));
		
		test = new StringBuffer("False");
		assertEquals(false, AssertUtility.isTrue(test));
		
		test = new StringBuffer("FALSE");
		assertEquals(false, AssertUtility.isTrue(test));
		
		test = new StringBuffer("true");
		assertEquals(true, AssertUtility.isTrue(test));
		
		test = new StringBuffer("True");
		assertEquals(true, AssertUtility.isTrue(test));
		
		test = new StringBuffer("TRUE");
		assertEquals(true, AssertUtility.isTrue(test));
		
		Boolean test2 = null;
		assertEquals(false, AssertUtility.isTrue(test2));
		
		test2 = Boolean.FALSE;
		assertEquals(false, AssertUtility.isTrue(test2));
		
		//instantiating Boolean objects是不太好的寫法
		//PMD會掃出來,測試這邊還是要測過,然後才mark起來
//		test2 = new Boolean("false");
//		assertEquals(false, AssertUtility.isTrue(test2));
//		
//		test2 = new Boolean("False");
//		assertEquals(false, AssertUtility.isTrue(test2));
//		
//		test2 = new Boolean("FALSE");
//		assertEquals(false, AssertUtility.isTrue(test2));
//		
//		test2 = new Boolean(false);
//		assertEquals(false, AssertUtility.isTrue(test2));
//		
//		test2 = Boolean.valueOf("false");
//		assertEquals(false, AssertUtility.isTrue(test2));
//		
//		test2 = Boolean.valueOf("False");
//		assertEquals(false, AssertUtility.isTrue(test2));
//		
//		test2 = Boolean.valueOf("FALSE");
//		assertEquals(false, AssertUtility.isTrue(test2));
//		
//		test2 = Boolean.valueOf(false);
//		assertEquals(false, AssertUtility.isTrue(test2));
		
		test2 = false;
		assertEquals(false, AssertUtility.isTrue(test2));
		
		test2 = Boolean.TRUE;
		assertEquals(true, AssertUtility.isTrue(test2));
		
		//instantiating Boolean objects是不太好的寫法
		//PMD會掃出來,測試這邊還是要測過,然後才mark起來
//		test2 = new Boolean("true");
//		assertEquals(true, AssertUtility.isTrue(test2));
//		
//		test2 = new Boolean("True");
//		assertEquals(true, AssertUtility.isTrue(test2));
//		
//		test2 = new Boolean("TRUE");
//		assertEquals(true, AssertUtility.isTrue(test2));
//		
//		test2 = new Boolean(true);
//		assertEquals(true, AssertUtility.isTrue(test2));
//		
//		test2 = Boolean.valueOf("true");
//		assertEquals(true, AssertUtility.isTrue(test2));
//		
//		test2 = Boolean.valueOf("True");
//		assertEquals(true, AssertUtility.isTrue(test2));
//		
//		test2 = Boolean.valueOf("TRUE");
//		assertEquals(true, AssertUtility.isTrue(test2));
//		
//		test2 = Boolean.valueOf(true);
//		assertEquals(true, AssertUtility.isTrue(test2));
		
		test2 = true;
		assertEquals(true, AssertUtility.isTrue(test2));
	}
	
	@Test
	public void testIsFalseString() {
		String test = null;
		assertEquals(false, AssertUtility.isFalse(test));
		
		test = "";
		assertEquals(false, AssertUtility.isFalse(test));
		
		test = "   ";
		assertEquals(false, AssertUtility.isFalse(test));
		
		test = "abc";
		assertEquals(false, AssertUtility.isFalse(test));
		
		test = "false";
		assertEquals(true, AssertUtility.isFalse(test));
		
		test = "False";
		assertEquals(true, AssertUtility.isFalse(test));
		
		test = "FALSE";
		assertEquals(true, AssertUtility.isFalse(test));
		
		test = "true";
		assertEquals(false, AssertUtility.isFalse(test));
		
		test = "True";
		assertEquals(false, AssertUtility.isFalse(test));
		
		test = "TRUE";
		assertEquals(false, AssertUtility.isFalse(test));
	}
	
	@Test
	public void testIsFalseObject() {
		StringBuffer test = null;
		assertEquals(false, AssertUtility.isFalse(test));
		
		test = new StringBuffer("");
		assertEquals(false, AssertUtility.isFalse(test));
		
		test = new StringBuffer("   ");
		assertEquals(false, AssertUtility.isFalse(test));
		
		test = new StringBuffer("abc");
		assertEquals(false, AssertUtility.isFalse(test));
		
		test = new StringBuffer("false");
		assertEquals(true, AssertUtility.isFalse(test));
		
		test = new StringBuffer("False");
		assertEquals(true, AssertUtility.isFalse(test));
		
		test = new StringBuffer("FALSE");
		assertEquals(true, AssertUtility.isFalse(test));
		
		test = new StringBuffer("true");
		assertEquals(false, AssertUtility.isFalse(test));
		
		test = new StringBuffer("True");
		assertEquals(false, AssertUtility.isFalse(test));
		
		test = new StringBuffer("TRUE");
		assertEquals(false, AssertUtility.isFalse(test));
		
		Boolean test2 = null;
		assertEquals(false, AssertUtility.isFalse(test2));
		
		test2 = Boolean.FALSE;
		assertEquals(true, AssertUtility.isFalse(test2));
		
		//instantiating Boolean objects是不太好的寫法
		//PMD會掃出來,測試這邊還是要測過,然後才mark起來
//		test2 = new Boolean("false");
//		assertEquals(true, AssertUtility.isFalse(test2));
//		
//		test2 = new Boolean("False");
//		assertEquals(true, AssertUtility.isFalse(test2));
//		
//		test2 = new Boolean("FALSE");
//		assertEquals(true, AssertUtility.isFalse(test2));
//		
//		test2 = new Boolean(false);
//		assertEquals(true, AssertUtility.isFalse(test2));
//		
//		test2 = Boolean.valueOf("false");
//		assertEquals(true, AssertUtility.isFalse(test2));
//		
//		test2 = Boolean.valueOf("False");
//		assertEquals(true, AssertUtility.isFalse(test2));
//		
//		test2 = Boolean.valueOf("FALSE");
//		assertEquals(true, AssertUtility.isFalse(test2));
//		
//		test2 = Boolean.valueOf(false);
//		assertEquals(true, AssertUtility.isFalse(test2));
		
		test2 = false;
		assertEquals(true, AssertUtility.isFalse(test2));
		
		test2 = Boolean.TRUE;
		assertEquals(false, AssertUtility.isFalse(test2));
		
		//instantiating Boolean objects是不太好的寫法
		//PMD會掃出來,測試這邊還是要測過,然後才mark起來
//		test2 = new Boolean("true");
//		assertEquals(false, AssertUtility.isFalse(test2));
//		
//		test2 = new Boolean("True");
//		assertEquals(false, AssertUtility.isFalse(test2));
//		
//		test2 = new Boolean("TRUE");
//		assertEquals(false, AssertUtility.isFalse(test2));
//		
//		test2 = new Boolean(true);
//		assertEquals(false, AssertUtility.isFalse(test2));
//		
//		test2 = Boolean.valueOf("true");
//		assertEquals(false, AssertUtility.isFalse(test2));
//		
//		test2 = Boolean.valueOf("True");
//		assertEquals(false, AssertUtility.isFalse(test2));
//		
//		test2 = Boolean.valueOf("TRUE");
//		assertEquals(false, AssertUtility.isFalse(test2));
//		
//		test2 = Boolean.valueOf(true);
//		assertEquals(false, AssertUtility.isFalse(test2));
		
		test2 = true;
		assertEquals(false, AssertUtility.isFalse(test2));
	}
	
	@Test
	public void testIsInteger() {
		String test = null;
		assertEquals(false, AssertUtility.isInteger(test));
		
		test = "";
		assertEquals(false, AssertUtility.isInteger(test));
		
		test = "   ";
		assertEquals(false, AssertUtility.isInteger(test));
		
		test = "abc";
		assertEquals(false, AssertUtility.isInteger(test));
		
		test = "1111.11";
		assertEquals(false, AssertUtility.isInteger(test));
		
		test = "1111.00";
		assertEquals(false, AssertUtility.isInteger(test));
		
		test = "-1111";
		assertEquals(true, AssertUtility.isInteger(test));
		
		test = "1,111";
		assertEquals(false, AssertUtility.isInteger(test));
		
		test = "1111";
		assertEquals(true, AssertUtility.isInteger(test));
		
		test = "2147483647";
		assertEquals(true, AssertUtility.isInteger(test));
		
		test = "2147483648";
		assertEquals(false, AssertUtility.isInteger(test));
		
		test = "-2147483648";
		assertEquals(true, AssertUtility.isInteger(test));
		
		test = "-2147483649";
		assertEquals(false, AssertUtility.isInteger(test));
	}
}
