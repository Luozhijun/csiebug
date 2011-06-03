package csiebug.test.util;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import csiebug.util.NumberFormatUtility;

public class NumberFormatUtilityTest {

	@Test
	public void testGetCurrencyString() {
		String numString = "20090723";
		assertEquals("20,090,723", NumberFormatUtility.getCurrency(numString));
		
		numString = "20,090,723";
		assertEquals("20,090,723", NumberFormatUtility.getCurrency(numString));
		
		numString = "200907.23";
		assertEquals("200,907.23", NumberFormatUtility.getCurrency(numString));
		
		numString = "-20090723";
		assertEquals("-20,090,723", NumberFormatUtility.getCurrency(numString));
		
		numString = "-20,090,723";
		assertEquals("-20,090,723", NumberFormatUtility.getCurrency(numString));
		
		numString = "-200907.23";
		assertEquals("-200,907.23", NumberFormatUtility.getCurrency(numString));
		
		numString = "abcdefg";
		try {
			NumberFormatUtility.getCurrency(numString);
			assertEquals(true, false);
		} catch(NumberFormatException ex) {
			assertEquals(true, true);
		}
		
		numString = "2009-0723";
		try {
			NumberFormatUtility.getCurrency(numString);
			assertEquals(true, false);
		} catch(NumberFormatException ex) {
			assertEquals(true, true);
		}
		
		numString = "2009-07-23";
		try {
			NumberFormatUtility.getCurrency(numString);
			assertEquals(true, false);
		} catch(NumberFormatException ex) {
			assertEquals(true, true);
		}
		
		numString = "2009.07.23";
		try {
			NumberFormatUtility.getCurrency(numString);
			assertEquals(true, false);
		} catch(NumberFormatException ex) {
			assertEquals(true, true);
		}
	}

	@Test
	public void testGetCurrencyInt() {
		int num = 20090723;
		
		assertEquals("20,090,723", NumberFormatUtility.getCurrency(num));
	}

	@Test
	public void testIsValidPositiveInteger() {
		String numString = "20090723";
		assertEquals(true, NumberFormatUtility.isValidPositiveInteger(numString));
		
		numString = "20,090,723";
		assertEquals(false, NumberFormatUtility.isValidPositiveInteger(numString));
		
		numString = "200907.23";
		assertEquals(false, NumberFormatUtility.isValidPositiveInteger(numString));
		
		numString = "-20090723";
		assertEquals(false, NumberFormatUtility.isValidPositiveInteger(numString));
		
		numString = "-20,090,723";
		assertEquals(false, NumberFormatUtility.isValidPositiveInteger(numString));
		
		numString = "-200907.23";
		assertEquals(false, NumberFormatUtility.isValidPositiveInteger(numString));
		
		numString = "abcdefg";
		assertEquals(false, NumberFormatUtility.isValidPositiveInteger(numString));
		
		numString = "2009-0723";
		assertEquals(false, NumberFormatUtility.isValidPositiveInteger(numString));
		
		numString = "2009-07-23";
		assertEquals(false, NumberFormatUtility.isValidPositiveInteger(numString));
		
		numString = "2009.07.23";
		assertEquals(false, NumberFormatUtility.isValidPositiveInteger(numString));
	}

	@Test
	public void testIsValidPositive() {
		String numString = "20090723";
		assertEquals(true, NumberFormatUtility.isValidPositive(numString));
		
		numString = "20,090,723";
		assertEquals(false, NumberFormatUtility.isValidPositive(numString));
		
		numString = "200907.23";
		assertEquals(true, NumberFormatUtility.isValidPositive(numString));
		
		numString = "-20090723";
		assertEquals(false, NumberFormatUtility.isValidPositive(numString));
		
		numString = "-20,090,723";
		assertEquals(false, NumberFormatUtility.isValidPositive(numString));
		
		numString = "-200907.23";
		assertEquals(false, NumberFormatUtility.isValidPositive(numString));
		
		numString = "abcdefg";
		assertEquals(false, NumberFormatUtility.isValidPositive(numString));
		
		numString = "2009-0723";
		assertEquals(false, NumberFormatUtility.isValidPositive(numString));
		
		numString = "2009-07-23";
		assertEquals(false, NumberFormatUtility.isValidPositive(numString));
		
		numString = "2009.07.23";
		assertEquals(false, NumberFormatUtility.isValidPositive(numString));
	}

	@Test
	public void testIsValidNegative() {
		String numString = "20090723";
		assertEquals(false, NumberFormatUtility.isValidNegative(numString));
		
		numString = "20,090,723";
		assertEquals(false, NumberFormatUtility.isValidNegative(numString));
		
		numString = "200907.23";
		assertEquals(false, NumberFormatUtility.isValidNegative(numString));
		
		numString = "-20090723";
		assertEquals(true, NumberFormatUtility.isValidNegative(numString));
		
		numString = "-20,090,723";
		assertEquals(false, NumberFormatUtility.isValidNegative(numString));
		
		numString = "-200907.23";
		assertEquals(true, NumberFormatUtility.isValidNegative(numString));
		
		numString = "abcdefg";
		assertEquals(false, NumberFormatUtility.isValidNegative(numString));
		
		numString = "2009-0723";
		assertEquals(false, NumberFormatUtility.isValidNegative(numString));
		
		numString = "2009-07-23";
		assertEquals(false, NumberFormatUtility.isValidNegative(numString));
		
		numString = "2009.07.23";
		assertEquals(false, NumberFormatUtility.isValidNegative(numString));
	}

	@Test
	public void testIsValid() {
		String numString = "20090723";
		assertEquals(true, NumberFormatUtility.isValid(numString));
		
		numString = "20,090,723";
		assertEquals(false, NumberFormatUtility.isValid(numString));
		
		numString = "200907.23";
		assertEquals(true, NumberFormatUtility.isValid(numString));
		
		numString = "-20090723";
		assertEquals(true, NumberFormatUtility.isValid(numString));
		
		numString = "-20,090,723";
		assertEquals(false, NumberFormatUtility.isValid(numString));
		
		numString = "-200907.23";
		assertEquals(true, NumberFormatUtility.isValid(numString));
		
		numString = "abcdefg";
		assertEquals(false, NumberFormatUtility.isValid(numString));
		
		numString = "2009-0723";
		assertEquals(false, NumberFormatUtility.isValid(numString));
		
		numString = "2009-07-23";
		assertEquals(false, NumberFormatUtility.isValid(numString));
		
		numString = "2009.07.23";
		assertEquals(false, NumberFormatUtility.isValid(numString));
	}

	@Test
	public void testIsValidCurrency() {
		String numString = "20090723";
		assertEquals(true, NumberFormatUtility.isValidCurrency(numString));
		
		numString = "20,090,723";
		assertEquals(true, NumberFormatUtility.isValidCurrency(numString));
		
		numString = "200907.23";
		assertEquals(true, NumberFormatUtility.isValidCurrency(numString));
		
		numString = "-20090723";
		assertEquals(true, NumberFormatUtility.isValidCurrency(numString));
		
		numString = "-20,090,723";
		assertEquals(true, NumberFormatUtility.isValidCurrency(numString));
		
		numString = "-200907.23";
		assertEquals(true, NumberFormatUtility.isValidCurrency(numString));
		
		numString = "abcdefg";
		assertEquals(false, NumberFormatUtility.isValidCurrency(numString));
		
		numString = "2009-0723";
		assertEquals(false, NumberFormatUtility.isValidCurrency(numString));
		
		numString = "2009-07-23";
		assertEquals(false, NumberFormatUtility.isValidCurrency(numString));
		
		numString = "2009.07.23";
		assertEquals(false, NumberFormatUtility.isValidCurrency(numString));
	}

	@Test
	public void testCompareCurrency() {
		String value1 = "20,090,723";
		String value2 = "2,010";
		assertEquals(true, NumberFormatUtility.compareCurrency(value1, value2));
		assertEquals(false, NumberFormatUtility.compareCurrency(value2, value1));
	}
	
	@Test
	public void testRoundFloat() {
		String value1 = "123.45678";
		String value2 = "123.46";
		float value3 = new Float(value1);
		assertEquals(value2, NumberFormatUtility.round(value3, 2));
		
		value1 = "123.455";
		value2 = "123.46";
		value3 = new Float(value1);
		assertEquals(value2, NumberFormatUtility.round(value3, 2));
		
		value1 = "123.055";
		value2 = "123.06";
		value3 = new Float(value1);
		assertEquals(value2, NumberFormatUtility.round(value3, 2));
		
		value1 = "123.45478";
		value2 = "123.45";
		value3 = new Float(value1);
		assertEquals(value2, NumberFormatUtility.round(value3, 2));
		
		value1 = "123.99578";
		value2 = "124.00";
		value3 = new Float(value1);
		assertEquals(value2, NumberFormatUtility.round(value3, 2));
		
		value1 = "123.4";
		value2 = "123.40";
		value3 = new Float(value1);
		assertEquals(value2, NumberFormatUtility.round(value3, 2));
		
		value1 = "123";
		value2 = "123.00";
		value3 = new Float(value1);
		assertEquals(value2, NumberFormatUtility.round(value3, 2));
	}
	
	@Test
	public void testRoundDouble() {
		String value1 = "123.45678";
		String value2 = "123.46";
		double value3 = new Double(value1);
		assertEquals(value2, NumberFormatUtility.round(value3, 2));
		
		value1 = "123.455";
		value2 = "123.46";
		value3 = new Double(value1);
		assertEquals(value2, NumberFormatUtility.round(value3, 2));
		
		value1 = "123.055";
		value2 = "123.06";
		value3 = new Double(value1);
		assertEquals(value2, NumberFormatUtility.round(value3, 2));
		
		value1 = "123.45478";
		value2 = "123.45";
		value3 = new Double(value1);
		assertEquals(value2, NumberFormatUtility.round(value3, 2));
		
		value1 = "123.99578";
		value2 = "124.00";
		value3 = new Double(value1);
		assertEquals(value2, NumberFormatUtility.round(value3, 2));
		
		value1 = "123.4";
		value2 = "123.40";
		value3 = new Double(value1);
		assertEquals(value2, NumberFormatUtility.round(value3, 2));
		
		value1 = "123";
		value2 = "123.00";
		value3 = new Double(value1);
		assertEquals(value2, NumberFormatUtility.round(value3, 2));
	}
	
	@Test
	public void testRoundBigDecimal() {
		String value1 = "123.45678";
		String value2 = "123.46";
		BigDecimal value3 = new BigDecimal(value1);
		assertEquals(value2, NumberFormatUtility.round(value3, 2));
		
		value1 = "123.455";
		value2 = "123.46";
		value3 = new BigDecimal(value1);
		assertEquals(value2, NumberFormatUtility.round(value3, 2));
		
		value1 = "123.055";
		value2 = "123.06";
		value3 = new BigDecimal(value1);
		assertEquals(value2, NumberFormatUtility.round(value3, 2));
		
		value1 = "123.45478";
		value2 = "123.45";
		value3 = new BigDecimal(value1);
		assertEquals(value2, NumberFormatUtility.round(value3, 2));
		
		value1 = "123.99578";
		value2 = "124.00";
		value3 = new BigDecimal(value1);
		assertEquals(value2, NumberFormatUtility.round(value3, 2));
		
		value1 = "123.4";
		value2 = "123.40";
		value3 = new BigDecimal(value1);
		assertEquals(value2, NumberFormatUtility.round(value3, 2));
		
		value1 = "123";
		value2 = "123.00";
		value3 = new BigDecimal(value1);
		assertEquals(value2, NumberFormatUtility.round(value3, 2));
	}
}
