package csiebug.test.util;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import csiebug.util.DateFormatException;
import csiebug.util.DateFormatUtility;

public class DateFormatUtilityTest {

	@Test
	public void testGetDaysOfMonth() throws DateFormatException {
		int year = 2009;
		int month = 2;
		assertEquals(28, DateFormatUtility.getDaysOfMonth(year, month));
		
		year = 2008;
		month = 2;
		assertEquals(29, DateFormatUtility.getDaysOfMonth(year, month));
		
		year = 2009;
		month = 0;
		try {
			DateFormatUtility.getDaysOfMonth(year, month);
			assertEquals(true, false);
		} catch(DateFormatException dfex) {
			assertEquals(true, true);
		}
		
		year = 2009;
		month = 13;
		try {
			DateFormatUtility.getDaysOfMonth(year, month);
			assertEquals(true, false);
		} catch(DateFormatException dfex) {
			assertEquals(true, true);
		}
	}

	@Test
	public void testGetDisplayDaysOfWeek() throws DateFormatException {
		int year = 2009;
		int month = 7;
		int day = 23;
		assertEquals("Thr", DateFormatUtility.getDisplayDaysOfWeek(year, month, day, 1));
		assertEquals("星期四", DateFormatUtility.getDisplayDaysOfWeek(year, month, day, 1000));
		
		year = 2009;
		month = 7;
		day = 32;
		try {
			DateFormatUtility.getDisplayDaysOfWeek(year, month, day, 1);
			DateFormatUtility.getDisplayDaysOfWeek(year, month, day, 1000);
			assertEquals(true, false);
		} catch(DateFormatException dfex) {
			assertEquals(true, true);
		}
		
		year = 2009;
		month = 7;
		day = 0;
		try {
			DateFormatUtility.getDisplayDaysOfWeek(year, month, day, 1);
			DateFormatUtility.getDisplayDaysOfWeek(year, month, day, 1000);
			assertEquals(true, false);
		} catch(DateFormatException dfex) {
			assertEquals(true, true);
		}
		
		year = 2009;
		month = 0;
		day = 1;
		try {
			DateFormatUtility.getDisplayDaysOfWeek(year, month, day, 1);
			DateFormatUtility.getDisplayDaysOfWeek(year, month, day, 1000);
			assertEquals(true, false);
		} catch(DateFormatException dfex) {
			assertEquals(true, true);
		}
		
		year = 2009;
		month = 13;
		day = 1;
		try {
			DateFormatUtility.getDisplayDaysOfWeek(year, month, day, 1);
			DateFormatUtility.getDisplayDaysOfWeek(year, month, day, 1000);
			assertEquals(true, false);
		} catch(DateFormatException dfex) {
			assertEquals(true, true);
		}
		
		year = 2009;
		month = 0;
		day = 0;
		try {
			DateFormatUtility.getDisplayDaysOfWeek(year, month, day, 1);
			DateFormatUtility.getDisplayDaysOfWeek(year, month, day, 1000);
			assertEquals(true, false);
		} catch(DateFormatException dfex) {
			assertEquals(true, true);
		}
		
		year = 2009;
		month = 0;
		day = 32;
		try {
			DateFormatUtility.getDisplayDaysOfWeek(year, month, day, 1);
			DateFormatUtility.getDisplayDaysOfWeek(year, month, day, 1000);
			assertEquals(true, false);
		} catch(DateFormatException dfex) {
			assertEquals(true, true);
		}
		
		year = 2009;
		month = 13;
		day = 0;
		try {
			DateFormatUtility.getDisplayDaysOfWeek(year, month, day, 1);
			DateFormatUtility.getDisplayDaysOfWeek(year, month, day, 1000);
			assertEquals(true, false);
		} catch(DateFormatException dfex) {
			assertEquals(true, true);
		}
		
		year = 2009;
		month = 13;
		day = 32;
		try {
			DateFormatUtility.getDisplayDaysOfWeek(year, month, day, 1);
			DateFormatUtility.getDisplayDaysOfWeek(year, month, day, 1000);
			assertEquals(true, false);
		} catch(DateFormatException dfex) {
			assertEquals(true, true);
		}
	}

	@Test
	public void testIsBissextile() {
		int year = 2009;
		assertEquals(false, DateFormatUtility.isBissextile(year));
		
		year = 2008;
		assertEquals(true, DateFormatUtility.isBissextile(year));
		
		year = 2000;
		assertEquals(true, DateFormatUtility.isBissextile(year));
	}

	@Test
	public void testGetLocalYear() {
		int year = 2009;
		assertEquals(98, DateFormatUtility.getLocalYear(year));
	}

	@Test
	public void testGetDisplayYear() throws DateFormatException {
		int year = 2009;
		assertEquals("09", DateFormatUtility.getDisplayYear(year, 1));
		assertEquals("2009", DateFormatUtility.getDisplayYear(year, 23));
		assertEquals("98", DateFormatUtility.getDisplayYear(year, 1000));
	}

	@Test
	public void testGetDisplayMonth() throws DateFormatException {
		int month = 7;
		assertEquals("07", DateFormatUtility.getDisplayMonth(month));
		
		month = 10;
		assertEquals("10", DateFormatUtility.getDisplayMonth(month));
		
		month = 0;
		try {
			DateFormatUtility.getDisplayMonth(month);
			assertEquals(true, false);
		} catch(DateFormatException dfex) {
			assertEquals(true, true);
		}
		
		
		month = 13;
		try {
			DateFormatUtility.getDisplayMonth(month);
			assertEquals(true, false);
		} catch(DateFormatException dfex) {
			assertEquals(true, true);
		}
	}

	@Test
	public void testGetDisplayYearMonth() throws DateFormatException {
		int year = 2009;
		int month = 2;
		
		assertEquals("0902", DateFormatUtility.getDisplayYearMonth(year, month, 1));
		assertEquals("200902", DateFormatUtility.getDisplayYearMonth(year, month, 23));
		assertEquals("9802", DateFormatUtility.getDisplayYearMonth(year, month, 1000));
		
		year = 2009;
		month = 0;
		try {
			DateFormatUtility.getDisplayYearMonth(year, month, 1);
			DateFormatUtility.getDisplayYearMonth(year, month, 23);
			DateFormatUtility.getDisplayYearMonth(year, month, 1000);
			assertEquals(true, false);
		} catch(DateFormatException dfex) {
			assertEquals(true, true);
		}
		
		year = 2009;
		month = 13;
		try {
			DateFormatUtility.getDisplayYearMonth(year, month, 1);
			DateFormatUtility.getDisplayYearMonth(year, month, 23);
			DateFormatUtility.getDisplayYearMonth(year, month, 1000);
			assertEquals(true, false);
		} catch(DateFormatException dfex) {
			assertEquals(true, true);
		}
	}
	
	@Test
	public void testGetDisplayDateCalendar() throws DateFormatException {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2009);
		calendar.set(Calendar.MONTH, 6);
		calendar.set(Calendar.DAY_OF_MONTH, 23);
		
		assertEquals("07/23/09", DateFormatUtility.getDisplayDate(calendar, 1));
		assertEquals("07/23/098", DateFormatUtility.getDisplayDate(calendar, 1000));
		assertEquals("09.07.23", DateFormatUtility.getDisplayDate(calendar, 2));
		assertEquals("098.07.23", DateFormatUtility.getDisplayDate(calendar, 2000));
		assertEquals("23/07/09", DateFormatUtility.getDisplayDate(calendar, 3));
		assertEquals("23/07/098", DateFormatUtility.getDisplayDate(calendar, 3000));
		assertEquals("23.07.09", DateFormatUtility.getDisplayDate(calendar, 4));
		assertEquals("23.07.098", DateFormatUtility.getDisplayDate(calendar, 4000));
		assertEquals("23-07-09", DateFormatUtility.getDisplayDate(calendar, 5));
		assertEquals("23-07-098", DateFormatUtility.getDisplayDate(calendar, 5000));
		assertEquals("07-23-09", DateFormatUtility.getDisplayDate(calendar, 10));
		assertEquals("07-23-098", DateFormatUtility.getDisplayDate(calendar, 10000));
		assertEquals("09/07/23", DateFormatUtility.getDisplayDate(calendar, 11));
		assertEquals("098/07/23", DateFormatUtility.getDisplayDate(calendar, 11000));
		assertEquals("090723", DateFormatUtility.getDisplayDate(calendar, 12));
		assertEquals("0980723", DateFormatUtility.getDisplayDate(calendar, 12000));
		assertEquals("2009-07-23", DateFormatUtility.getDisplayDate(calendar, 23));
		assertEquals("098-07-23", DateFormatUtility.getDisplayDate(calendar, 23000));
		assertEquals("07/23/2009", DateFormatUtility.getDisplayDate(calendar, 101));
		assertEquals("07/23/098", DateFormatUtility.getDisplayDate(calendar, 101000));
		assertEquals("07/23/2009", DateFormatUtility.getDisplayDate(calendar, 101));
		assertEquals("07/23/098", DateFormatUtility.getDisplayDate(calendar, 101000));
		assertEquals("2009.07.23", DateFormatUtility.getDisplayDate(calendar, 102));
		assertEquals("098.07.23", DateFormatUtility.getDisplayDate(calendar, 102000));
		assertEquals("23/07/2009", DateFormatUtility.getDisplayDate(calendar, 103));
		assertEquals("23/07/098", DateFormatUtility.getDisplayDate(calendar, 103000));
		assertEquals("23.07.2009", DateFormatUtility.getDisplayDate(calendar, 104));
		assertEquals("23.07.098", DateFormatUtility.getDisplayDate(calendar, 104000));
		assertEquals("23-07-2009", DateFormatUtility.getDisplayDate(calendar, 105));
		assertEquals("23-07-098", DateFormatUtility.getDisplayDate(calendar, 105000));
		assertEquals("07-23-2009", DateFormatUtility.getDisplayDate(calendar, 110));
		assertEquals("07-23-098", DateFormatUtility.getDisplayDate(calendar, 110000));
		assertEquals("2009/07/23", DateFormatUtility.getDisplayDate(calendar, 111));
		assertEquals("098/07/23", DateFormatUtility.getDisplayDate(calendar, 111000));
		assertEquals("20090723", DateFormatUtility.getDisplayDate(calendar, 112));
		assertEquals("0980723", DateFormatUtility.getDisplayDate(calendar, 112000));
		
		//剩下的case都是java.lang.Calendar的問題,不在這邊測試
	}
	
	@Test
	public void testGetDisplayDate() throws DateFormatException {
		int year = 2009;
		int month = 7;
		int day = 23;
		
		assertEquals("07/23/09", DateFormatUtility.getDisplayDate(year, month, day, 1));
		assertEquals("07/23/098", DateFormatUtility.getDisplayDate(year, month, day, 1000));
		assertEquals("09.07.23", DateFormatUtility.getDisplayDate(year, month, day, 2));
		assertEquals("098.07.23", DateFormatUtility.getDisplayDate(year, month, day, 2000));
		assertEquals("23/07/09", DateFormatUtility.getDisplayDate(year, month, day, 3));
		assertEquals("23/07/098", DateFormatUtility.getDisplayDate(year, month, day, 3000));
		assertEquals("23.07.09", DateFormatUtility.getDisplayDate(year, month, day, 4));
		assertEquals("23.07.098", DateFormatUtility.getDisplayDate(year, month, day, 4000));
		assertEquals("23-07-09", DateFormatUtility.getDisplayDate(year, month, day, 5));
		assertEquals("23-07-098", DateFormatUtility.getDisplayDate(year, month, day, 5000));
		assertEquals("07-23-09", DateFormatUtility.getDisplayDate(year, month, day, 10));
		assertEquals("07-23-098", DateFormatUtility.getDisplayDate(year, month, day, 10000));
		assertEquals("09/07/23", DateFormatUtility.getDisplayDate(year, month, day, 11));
		assertEquals("098/07/23", DateFormatUtility.getDisplayDate(year, month, day, 11000));
		assertEquals("090723", DateFormatUtility.getDisplayDate(year, month, day, 12));
		assertEquals("0980723", DateFormatUtility.getDisplayDate(year, month, day, 12000));
		assertEquals("2009-07-23", DateFormatUtility.getDisplayDate(year, month, day, 23));
		assertEquals("098-07-23", DateFormatUtility.getDisplayDate(year, month, day, 23000));
		assertEquals("07/23/2009", DateFormatUtility.getDisplayDate(year, month, day, 101));
		assertEquals("07/23/098", DateFormatUtility.getDisplayDate(year, month, day, 101000));
		assertEquals("07/23/2009", DateFormatUtility.getDisplayDate(year, month, day, 101));
		assertEquals("07/23/098", DateFormatUtility.getDisplayDate(year, month, day, 101000));
		assertEquals("2009.07.23", DateFormatUtility.getDisplayDate(year, month, day, 102));
		assertEquals("098.07.23", DateFormatUtility.getDisplayDate(year, month, day, 102000));
		assertEquals("23/07/2009", DateFormatUtility.getDisplayDate(year, month, day, 103));
		assertEquals("23/07/098", DateFormatUtility.getDisplayDate(year, month, day, 103000));
		assertEquals("23.07.2009", DateFormatUtility.getDisplayDate(year, month, day, 104));
		assertEquals("23.07.098", DateFormatUtility.getDisplayDate(year, month, day, 104000));
		assertEquals("23-07-2009", DateFormatUtility.getDisplayDate(year, month, day, 105));
		assertEquals("23-07-098", DateFormatUtility.getDisplayDate(year, month, day, 105000));
		assertEquals("07-23-2009", DateFormatUtility.getDisplayDate(year, month, day, 110));
		assertEquals("07-23-098", DateFormatUtility.getDisplayDate(year, month, day, 110000));
		assertEquals("2009/07/23", DateFormatUtility.getDisplayDate(year, month, day, 111));
		assertEquals("098/07/23", DateFormatUtility.getDisplayDate(year, month, day, 111000));
		assertEquals("20090723", DateFormatUtility.getDisplayDate(year, month, day, 112));
		assertEquals("0980723", DateFormatUtility.getDisplayDate(year, month, day, 112000));
		
		year = 2009;
		month = 7;
		day = 32;
		try {
			DateFormatUtility.getDisplayDate(year, month, day, 1);
			DateFormatUtility.getDisplayDate(year, month, day, 1000);
			DateFormatUtility.getDisplayDate(year, month, day, 2);
			DateFormatUtility.getDisplayDate(year, month, day, 2000);
			DateFormatUtility.getDisplayDate(year, month, day, 3);
			DateFormatUtility.getDisplayDate(year, month, day, 3000);
			DateFormatUtility.getDisplayDate(year, month, day, 4);
			DateFormatUtility.getDisplayDate(year, month, day, 4000);
			DateFormatUtility.getDisplayDate(year, month, day, 5);
			DateFormatUtility.getDisplayDate(year, month, day, 5000);
			DateFormatUtility.getDisplayDate(year, month, day, 10);
			DateFormatUtility.getDisplayDate(year, month, day, 10000);
			DateFormatUtility.getDisplayDate(year, month, day, 11);
			DateFormatUtility.getDisplayDate(year, month, day, 11000);
			DateFormatUtility.getDisplayDate(year, month, day, 12);
			DateFormatUtility.getDisplayDate(year, month, day, 12000);
			DateFormatUtility.getDisplayDate(year, month, day, 23);
			DateFormatUtility.getDisplayDate(year, month, day, 23000);
			DateFormatUtility.getDisplayDate(year, month, day, 101);
			DateFormatUtility.getDisplayDate(year, month, day, 101000);
			DateFormatUtility.getDisplayDate(year, month, day, 101);
			DateFormatUtility.getDisplayDate(year, month, day, 101000);
			DateFormatUtility.getDisplayDate(year, month, day, 102);
			DateFormatUtility.getDisplayDate(year, month, day, 102000);
			DateFormatUtility.getDisplayDate(year, month, day, 103);
			DateFormatUtility.getDisplayDate(year, month, day, 103000);
			DateFormatUtility.getDisplayDate(year, month, day, 104);
			DateFormatUtility.getDisplayDate(year, month, day, 104000);
			DateFormatUtility.getDisplayDate(year, month, day, 105);
			DateFormatUtility.getDisplayDate(year, month, day, 105000);
			DateFormatUtility.getDisplayDate(year, month, day, 110);
			DateFormatUtility.getDisplayDate(year, month, day, 110000);
			DateFormatUtility.getDisplayDate(year, month, day, 111);
			DateFormatUtility.getDisplayDate(year, month, day, 111000);
			DateFormatUtility.getDisplayDate(year, month, day, 112);
			DateFormatUtility.getDisplayDate(year, month, day, 112000);
			assertEquals(true, false);
		} catch(DateFormatException dfex) {
			assertEquals(true, true);
		}
		
		year = 2009;
		month = 7;
		day = 0;
		try {
			DateFormatUtility.getDisplayDate(year, month, day, 1);
			DateFormatUtility.getDisplayDate(year, month, day, 1000);
			DateFormatUtility.getDisplayDate(year, month, day, 2);
			DateFormatUtility.getDisplayDate(year, month, day, 2000);
			DateFormatUtility.getDisplayDate(year, month, day, 3);
			DateFormatUtility.getDisplayDate(year, month, day, 3000);
			DateFormatUtility.getDisplayDate(year, month, day, 4);
			DateFormatUtility.getDisplayDate(year, month, day, 4000);
			DateFormatUtility.getDisplayDate(year, month, day, 5);
			DateFormatUtility.getDisplayDate(year, month, day, 5000);
			DateFormatUtility.getDisplayDate(year, month, day, 10);
			DateFormatUtility.getDisplayDate(year, month, day, 10000);
			DateFormatUtility.getDisplayDate(year, month, day, 11);
			DateFormatUtility.getDisplayDate(year, month, day, 11000);
			DateFormatUtility.getDisplayDate(year, month, day, 12);
			DateFormatUtility.getDisplayDate(year, month, day, 12000);
			DateFormatUtility.getDisplayDate(year, month, day, 23);
			DateFormatUtility.getDisplayDate(year, month, day, 23000);
			DateFormatUtility.getDisplayDate(year, month, day, 101);
			DateFormatUtility.getDisplayDate(year, month, day, 101000);
			DateFormatUtility.getDisplayDate(year, month, day, 101);
			DateFormatUtility.getDisplayDate(year, month, day, 101000);
			DateFormatUtility.getDisplayDate(year, month, day, 102);
			DateFormatUtility.getDisplayDate(year, month, day, 102000);
			DateFormatUtility.getDisplayDate(year, month, day, 103);
			DateFormatUtility.getDisplayDate(year, month, day, 103000);
			DateFormatUtility.getDisplayDate(year, month, day, 104);
			DateFormatUtility.getDisplayDate(year, month, day, 104000);
			DateFormatUtility.getDisplayDate(year, month, day, 105);
			DateFormatUtility.getDisplayDate(year, month, day, 105000);
			DateFormatUtility.getDisplayDate(year, month, day, 110);
			DateFormatUtility.getDisplayDate(year, month, day, 110000);
			DateFormatUtility.getDisplayDate(year, month, day, 111);
			DateFormatUtility.getDisplayDate(year, month, day, 111000);
			DateFormatUtility.getDisplayDate(year, month, day, 112);
			DateFormatUtility.getDisplayDate(year, month, day, 112000);
			assertEquals(true, false);
		} catch(DateFormatException dfex) {
			assertEquals(true, true);
		}
		
		year = 2009;
		month = 0;
		day = 1;
		try {
			DateFormatUtility.getDisplayDate(year, month, day, 1);
			DateFormatUtility.getDisplayDate(year, month, day, 1000);
			DateFormatUtility.getDisplayDate(year, month, day, 2);
			DateFormatUtility.getDisplayDate(year, month, day, 2000);
			DateFormatUtility.getDisplayDate(year, month, day, 3);
			DateFormatUtility.getDisplayDate(year, month, day, 3000);
			DateFormatUtility.getDisplayDate(year, month, day, 4);
			DateFormatUtility.getDisplayDate(year, month, day, 4000);
			DateFormatUtility.getDisplayDate(year, month, day, 5);
			DateFormatUtility.getDisplayDate(year, month, day, 5000);
			DateFormatUtility.getDisplayDate(year, month, day, 10);
			DateFormatUtility.getDisplayDate(year, month, day, 10000);
			DateFormatUtility.getDisplayDate(year, month, day, 11);
			DateFormatUtility.getDisplayDate(year, month, day, 11000);
			DateFormatUtility.getDisplayDate(year, month, day, 12);
			DateFormatUtility.getDisplayDate(year, month, day, 12000);
			DateFormatUtility.getDisplayDate(year, month, day, 23);
			DateFormatUtility.getDisplayDate(year, month, day, 23000);
			DateFormatUtility.getDisplayDate(year, month, day, 101);
			DateFormatUtility.getDisplayDate(year, month, day, 101000);
			DateFormatUtility.getDisplayDate(year, month, day, 101);
			DateFormatUtility.getDisplayDate(year, month, day, 101000);
			DateFormatUtility.getDisplayDate(year, month, day, 102);
			DateFormatUtility.getDisplayDate(year, month, day, 102000);
			DateFormatUtility.getDisplayDate(year, month, day, 103);
			DateFormatUtility.getDisplayDate(year, month, day, 103000);
			DateFormatUtility.getDisplayDate(year, month, day, 104);
			DateFormatUtility.getDisplayDate(year, month, day, 104000);
			DateFormatUtility.getDisplayDate(year, month, day, 105);
			DateFormatUtility.getDisplayDate(year, month, day, 105000);
			DateFormatUtility.getDisplayDate(year, month, day, 110);
			DateFormatUtility.getDisplayDate(year, month, day, 110000);
			DateFormatUtility.getDisplayDate(year, month, day, 111);
			DateFormatUtility.getDisplayDate(year, month, day, 111000);
			DateFormatUtility.getDisplayDate(year, month, day, 112);
			DateFormatUtility.getDisplayDate(year, month, day, 112000);
			assertEquals(true, false);
		} catch(DateFormatException dfex) {
			assertEquals(true, true);
		}
		
		year = 2009;
		month = 13;
		day = 1;
		try {
			DateFormatUtility.getDisplayDate(year, month, day, 1);
			DateFormatUtility.getDisplayDate(year, month, day, 1000);
			DateFormatUtility.getDisplayDate(year, month, day, 2);
			DateFormatUtility.getDisplayDate(year, month, day, 2000);
			DateFormatUtility.getDisplayDate(year, month, day, 3);
			DateFormatUtility.getDisplayDate(year, month, day, 3000);
			DateFormatUtility.getDisplayDate(year, month, day, 4);
			DateFormatUtility.getDisplayDate(year, month, day, 4000);
			DateFormatUtility.getDisplayDate(year, month, day, 5);
			DateFormatUtility.getDisplayDate(year, month, day, 5000);
			DateFormatUtility.getDisplayDate(year, month, day, 10);
			DateFormatUtility.getDisplayDate(year, month, day, 10000);
			DateFormatUtility.getDisplayDate(year, month, day, 11);
			DateFormatUtility.getDisplayDate(year, month, day, 11000);
			DateFormatUtility.getDisplayDate(year, month, day, 12);
			DateFormatUtility.getDisplayDate(year, month, day, 12000);
			DateFormatUtility.getDisplayDate(year, month, day, 23);
			DateFormatUtility.getDisplayDate(year, month, day, 23000);
			DateFormatUtility.getDisplayDate(year, month, day, 101);
			DateFormatUtility.getDisplayDate(year, month, day, 101000);
			DateFormatUtility.getDisplayDate(year, month, day, 101);
			DateFormatUtility.getDisplayDate(year, month, day, 101000);
			DateFormatUtility.getDisplayDate(year, month, day, 102);
			DateFormatUtility.getDisplayDate(year, month, day, 102000);
			DateFormatUtility.getDisplayDate(year, month, day, 103);
			DateFormatUtility.getDisplayDate(year, month, day, 103000);
			DateFormatUtility.getDisplayDate(year, month, day, 104);
			DateFormatUtility.getDisplayDate(year, month, day, 104000);
			DateFormatUtility.getDisplayDate(year, month, day, 105);
			DateFormatUtility.getDisplayDate(year, month, day, 105000);
			DateFormatUtility.getDisplayDate(year, month, day, 110);
			DateFormatUtility.getDisplayDate(year, month, day, 110000);
			DateFormatUtility.getDisplayDate(year, month, day, 111);
			DateFormatUtility.getDisplayDate(year, month, day, 111000);
			DateFormatUtility.getDisplayDate(year, month, day, 112);
			DateFormatUtility.getDisplayDate(year, month, day, 112000);
			assertEquals(true, false);
		} catch(DateFormatException dfex) {
			assertEquals(true, true);
		}
		
		year = 2009;
		month = 0;
		day = 0;
		try {
			DateFormatUtility.getDisplayDate(year, month, day, 1);
			DateFormatUtility.getDisplayDate(year, month, day, 1000);
			DateFormatUtility.getDisplayDate(year, month, day, 2);
			DateFormatUtility.getDisplayDate(year, month, day, 2000);
			DateFormatUtility.getDisplayDate(year, month, day, 3);
			DateFormatUtility.getDisplayDate(year, month, day, 3000);
			DateFormatUtility.getDisplayDate(year, month, day, 4);
			DateFormatUtility.getDisplayDate(year, month, day, 4000);
			DateFormatUtility.getDisplayDate(year, month, day, 5);
			DateFormatUtility.getDisplayDate(year, month, day, 5000);
			DateFormatUtility.getDisplayDate(year, month, day, 10);
			DateFormatUtility.getDisplayDate(year, month, day, 10000);
			DateFormatUtility.getDisplayDate(year, month, day, 11);
			DateFormatUtility.getDisplayDate(year, month, day, 11000);
			DateFormatUtility.getDisplayDate(year, month, day, 12);
			DateFormatUtility.getDisplayDate(year, month, day, 12000);
			DateFormatUtility.getDisplayDate(year, month, day, 23);
			DateFormatUtility.getDisplayDate(year, month, day, 23000);
			DateFormatUtility.getDisplayDate(year, month, day, 101);
			DateFormatUtility.getDisplayDate(year, month, day, 101000);
			DateFormatUtility.getDisplayDate(year, month, day, 101);
			DateFormatUtility.getDisplayDate(year, month, day, 101000);
			DateFormatUtility.getDisplayDate(year, month, day, 102);
			DateFormatUtility.getDisplayDate(year, month, day, 102000);
			DateFormatUtility.getDisplayDate(year, month, day, 103);
			DateFormatUtility.getDisplayDate(year, month, day, 103000);
			DateFormatUtility.getDisplayDate(year, month, day, 104);
			DateFormatUtility.getDisplayDate(year, month, day, 104000);
			DateFormatUtility.getDisplayDate(year, month, day, 105);
			DateFormatUtility.getDisplayDate(year, month, day, 105000);
			DateFormatUtility.getDisplayDate(year, month, day, 110);
			DateFormatUtility.getDisplayDate(year, month, day, 110000);
			DateFormatUtility.getDisplayDate(year, month, day, 111);
			DateFormatUtility.getDisplayDate(year, month, day, 111000);
			DateFormatUtility.getDisplayDate(year, month, day, 112);
			DateFormatUtility.getDisplayDate(year, month, day, 112000);
			assertEquals(true, false);
		} catch(DateFormatException dfex) {
			assertEquals(true, true);
		}
		
		year = 2009;
		month = 0;
		day = 32;
		try {
			DateFormatUtility.getDisplayDate(year, month, day, 1);
			DateFormatUtility.getDisplayDate(year, month, day, 1000);
			DateFormatUtility.getDisplayDate(year, month, day, 2);
			DateFormatUtility.getDisplayDate(year, month, day, 2000);
			DateFormatUtility.getDisplayDate(year, month, day, 3);
			DateFormatUtility.getDisplayDate(year, month, day, 3000);
			DateFormatUtility.getDisplayDate(year, month, day, 4);
			DateFormatUtility.getDisplayDate(year, month, day, 4000);
			DateFormatUtility.getDisplayDate(year, month, day, 5);
			DateFormatUtility.getDisplayDate(year, month, day, 5000);
			DateFormatUtility.getDisplayDate(year, month, day, 10);
			DateFormatUtility.getDisplayDate(year, month, day, 10000);
			DateFormatUtility.getDisplayDate(year, month, day, 11);
			DateFormatUtility.getDisplayDate(year, month, day, 11000);
			DateFormatUtility.getDisplayDate(year, month, day, 12);
			DateFormatUtility.getDisplayDate(year, month, day, 12000);
			DateFormatUtility.getDisplayDate(year, month, day, 23);
			DateFormatUtility.getDisplayDate(year, month, day, 23000);
			DateFormatUtility.getDisplayDate(year, month, day, 101);
			DateFormatUtility.getDisplayDate(year, month, day, 101000);
			DateFormatUtility.getDisplayDate(year, month, day, 101);
			DateFormatUtility.getDisplayDate(year, month, day, 101000);
			DateFormatUtility.getDisplayDate(year, month, day, 102);
			DateFormatUtility.getDisplayDate(year, month, day, 102000);
			DateFormatUtility.getDisplayDate(year, month, day, 103);
			DateFormatUtility.getDisplayDate(year, month, day, 103000);
			DateFormatUtility.getDisplayDate(year, month, day, 104);
			DateFormatUtility.getDisplayDate(year, month, day, 104000);
			DateFormatUtility.getDisplayDate(year, month, day, 105);
			DateFormatUtility.getDisplayDate(year, month, day, 105000);
			DateFormatUtility.getDisplayDate(year, month, day, 110);
			DateFormatUtility.getDisplayDate(year, month, day, 110000);
			DateFormatUtility.getDisplayDate(year, month, day, 111);
			DateFormatUtility.getDisplayDate(year, month, day, 111000);
			DateFormatUtility.getDisplayDate(year, month, day, 112);
			DateFormatUtility.getDisplayDate(year, month, day, 112000);
			assertEquals(true, false);
		} catch(DateFormatException dfex) {
			assertEquals(true, true);
		}
		
		year = 2009;
		month = 13;
		day = 0;
		try {
			DateFormatUtility.getDisplayDate(year, month, day, 1);
			DateFormatUtility.getDisplayDate(year, month, day, 1000);
			DateFormatUtility.getDisplayDate(year, month, day, 2);
			DateFormatUtility.getDisplayDate(year, month, day, 2000);
			DateFormatUtility.getDisplayDate(year, month, day, 3);
			DateFormatUtility.getDisplayDate(year, month, day, 3000);
			DateFormatUtility.getDisplayDate(year, month, day, 4);
			DateFormatUtility.getDisplayDate(year, month, day, 4000);
			DateFormatUtility.getDisplayDate(year, month, day, 5);
			DateFormatUtility.getDisplayDate(year, month, day, 5000);
			DateFormatUtility.getDisplayDate(year, month, day, 10);
			DateFormatUtility.getDisplayDate(year, month, day, 10000);
			DateFormatUtility.getDisplayDate(year, month, day, 11);
			DateFormatUtility.getDisplayDate(year, month, day, 11000);
			DateFormatUtility.getDisplayDate(year, month, day, 12);
			DateFormatUtility.getDisplayDate(year, month, day, 12000);
			DateFormatUtility.getDisplayDate(year, month, day, 23);
			DateFormatUtility.getDisplayDate(year, month, day, 23000);
			DateFormatUtility.getDisplayDate(year, month, day, 101);
			DateFormatUtility.getDisplayDate(year, month, day, 101000);
			DateFormatUtility.getDisplayDate(year, month, day, 101);
			DateFormatUtility.getDisplayDate(year, month, day, 101000);
			DateFormatUtility.getDisplayDate(year, month, day, 102);
			DateFormatUtility.getDisplayDate(year, month, day, 102000);
			DateFormatUtility.getDisplayDate(year, month, day, 103);
			DateFormatUtility.getDisplayDate(year, month, day, 103000);
			DateFormatUtility.getDisplayDate(year, month, day, 104);
			DateFormatUtility.getDisplayDate(year, month, day, 104000);
			DateFormatUtility.getDisplayDate(year, month, day, 105);
			DateFormatUtility.getDisplayDate(year, month, day, 105000);
			DateFormatUtility.getDisplayDate(year, month, day, 110);
			DateFormatUtility.getDisplayDate(year, month, day, 110000);
			DateFormatUtility.getDisplayDate(year, month, day, 111);
			DateFormatUtility.getDisplayDate(year, month, day, 111000);
			DateFormatUtility.getDisplayDate(year, month, day, 112);
			DateFormatUtility.getDisplayDate(year, month, day, 112000);
			assertEquals(true, false);
		} catch(DateFormatException dfex) {
			assertEquals(true, true);
		}
		
		year = 2009;
		month = 13;
		day = 32;
		try {
			DateFormatUtility.getDisplayDate(year, month, day, 1);
			DateFormatUtility.getDisplayDate(year, month, day, 1000);
			DateFormatUtility.getDisplayDate(year, month, day, 2);
			DateFormatUtility.getDisplayDate(year, month, day, 2000);
			DateFormatUtility.getDisplayDate(year, month, day, 3);
			DateFormatUtility.getDisplayDate(year, month, day, 3000);
			DateFormatUtility.getDisplayDate(year, month, day, 4);
			DateFormatUtility.getDisplayDate(year, month, day, 4000);
			DateFormatUtility.getDisplayDate(year, month, day, 5);
			DateFormatUtility.getDisplayDate(year, month, day, 5000);
			DateFormatUtility.getDisplayDate(year, month, day, 10);
			DateFormatUtility.getDisplayDate(year, month, day, 10000);
			DateFormatUtility.getDisplayDate(year, month, day, 11);
			DateFormatUtility.getDisplayDate(year, month, day, 11000);
			DateFormatUtility.getDisplayDate(year, month, day, 12);
			DateFormatUtility.getDisplayDate(year, month, day, 12000);
			DateFormatUtility.getDisplayDate(year, month, day, 23);
			DateFormatUtility.getDisplayDate(year, month, day, 23000);
			DateFormatUtility.getDisplayDate(year, month, day, 101);
			DateFormatUtility.getDisplayDate(year, month, day, 101000);
			DateFormatUtility.getDisplayDate(year, month, day, 101);
			DateFormatUtility.getDisplayDate(year, month, day, 101000);
			DateFormatUtility.getDisplayDate(year, month, day, 102);
			DateFormatUtility.getDisplayDate(year, month, day, 102000);
			DateFormatUtility.getDisplayDate(year, month, day, 103);
			DateFormatUtility.getDisplayDate(year, month, day, 103000);
			DateFormatUtility.getDisplayDate(year, month, day, 104);
			DateFormatUtility.getDisplayDate(year, month, day, 104000);
			DateFormatUtility.getDisplayDate(year, month, day, 105);
			DateFormatUtility.getDisplayDate(year, month, day, 105000);
			DateFormatUtility.getDisplayDate(year, month, day, 110);
			DateFormatUtility.getDisplayDate(year, month, day, 110000);
			DateFormatUtility.getDisplayDate(year, month, day, 111);
			DateFormatUtility.getDisplayDate(year, month, day, 111000);
			DateFormatUtility.getDisplayDate(year, month, day, 112);
			DateFormatUtility.getDisplayDate(year, month, day, 112000);
			assertEquals(true, false);
		} catch(DateFormatException dfex) {
			assertEquals(true, true);
		}
	}
	
	@Test
	public void testTransDate() throws DateFormatException {
		String value = "07/23/09";
		int type = 1;
		int targetType = 23;
		assertEquals("2009-07-23", DateFormatUtility.transDate(value, type, targetType));
		
		value = "07/23/2009";
		type = 101;
		targetType = 23;
		assertEquals("2009-07-23", DateFormatUtility.transDate(value, type, targetType));
		
		value = "07/23/098";
		type = 1000;
		targetType = 23;
		assertEquals("2009-07-23", DateFormatUtility.transDate(value, type, targetType));
	}

	@Test
	public void testGetParseDate() throws DateFormatException {
		String value = "07/23/09";
		int type = 1;
		String[] date = DateFormatUtility.getParseDate(value, type);
		assertEquals("09", date[0]);
		assertEquals("07", date[1]);
		assertEquals("23", date[2]);
		assertEquals("/", date[3]);
		assertEquals("2", date[4]);
		assertEquals("2", date[5]);
		
		value = "07/23/098";
		type = 1000;
		date = DateFormatUtility.getParseDate(value, type);
		assertEquals("098", date[0]);
		assertEquals("07", date[1]);
		assertEquals("23", date[2]);
		assertEquals("/", date[3]);
		assertEquals("2", date[4]);
		assertEquals("3", date[5]);
		
		value = "09.07.23";
		type = 2;
		date = DateFormatUtility.getParseDate(value, type);
		assertEquals("09", date[0]);
		assertEquals("07", date[1]);
		assertEquals("23", date[2]);
		assertEquals(".", date[3]);
		assertEquals("1", date[4]);
		assertEquals("2", date[5]);
		
		value = "098.07.23";
		type = 2000;
		date = DateFormatUtility.getParseDate(value, type);
		assertEquals("098", date[0]);
		assertEquals("07", date[1]);
		assertEquals("23", date[2]);
		assertEquals(".", date[3]);
		assertEquals("1", date[4]);
		assertEquals("3", date[5]);
		
		value = "23/07/09";
		type = 3;
		date = DateFormatUtility.getParseDate(value, type);
		assertEquals("09", date[0]);
		assertEquals("07", date[1]);
		assertEquals("23", date[2]);
		assertEquals("/", date[3]);
		assertEquals("3", date[4]);
		assertEquals("2", date[5]);
		
		value = "23/07/098";
		type = 3000;
		date = DateFormatUtility.getParseDate(value, type);
		assertEquals("098", date[0]);
		assertEquals("07", date[1]);
		assertEquals("23", date[2]);
		assertEquals("/", date[3]);
		assertEquals("3", date[4]);
		assertEquals("3", date[5]);
		
		value = "23.07.09";
		type = 4;
		date = DateFormatUtility.getParseDate(value, type);
		assertEquals("09", date[0]);
		assertEquals("07", date[1]);
		assertEquals("23", date[2]);
		assertEquals(".", date[3]);
		assertEquals("3", date[4]);
		assertEquals("2", date[5]);
		
		value = "23.07.098";
		type = 4000;
		date = DateFormatUtility.getParseDate(value, type);
		assertEquals("098", date[0]);
		assertEquals("07", date[1]);
		assertEquals("23", date[2]);
		assertEquals(".", date[3]);
		assertEquals("3", date[4]);
		assertEquals("3", date[5]);
		
		value = "23-07-09";
		type = 5;
		date = DateFormatUtility.getParseDate(value, type);
		assertEquals("09", date[0]);
		assertEquals("07", date[1]);
		assertEquals("23", date[2]);
		assertEquals("-", date[3]);
		assertEquals("3", date[4]);
		assertEquals("2", date[5]);
		
		value = "23-07-098";
		type = 5000;
		date = DateFormatUtility.getParseDate(value, type);
		assertEquals("098", date[0]);
		assertEquals("07", date[1]);
		assertEquals("23", date[2]);
		assertEquals("-", date[3]);
		assertEquals("3", date[4]);
		assertEquals("3", date[5]);
		
		value = "07-23-09";
		type = 10;
		date = DateFormatUtility.getParseDate(value, type);
		assertEquals("09", date[0]);
		assertEquals("07", date[1]);
		assertEquals("23", date[2]);
		assertEquals("-", date[3]);
		assertEquals("2", date[4]);
		assertEquals("2", date[5]);
		
		value = "07-23-098";
		type = 10000;
		date = DateFormatUtility.getParseDate(value, type);
		assertEquals("098", date[0]);
		assertEquals("07", date[1]);
		assertEquals("23", date[2]);
		assertEquals("-", date[3]);
		assertEquals("2", date[4]);
		assertEquals("3", date[5]);
		
		value = "09/07/23";
		type = 11;
		date = DateFormatUtility.getParseDate(value, type);
		assertEquals("09", date[0]);
		assertEquals("07", date[1]);
		assertEquals("23", date[2]);
		assertEquals("/", date[3]);
		assertEquals("1", date[4]);
		assertEquals("2", date[5]);
		
		value = "098/07/23";
		type = 11000;
		date = DateFormatUtility.getParseDate(value, type);
		assertEquals("098", date[0]);
		assertEquals("07", date[1]);
		assertEquals("23", date[2]);
		assertEquals("/", date[3]);
		assertEquals("1", date[4]);
		assertEquals("3", date[5]);
		
		value = "090723";
		type = 12;
		date = DateFormatUtility.getParseDate(value, type);
		assertEquals("09", date[0]);
		assertEquals("07", date[1]);
		assertEquals("23", date[2]);
		assertEquals("", date[3]);
		assertEquals("1", date[4]);
		assertEquals("2", date[5]);
		
		value = "0980723";
		type = 12000;
		date = DateFormatUtility.getParseDate(value, type);
		assertEquals("098", date[0]);
		assertEquals("07", date[1]);
		assertEquals("23", date[2]);
		assertEquals("", date[3]);
		assertEquals("1", date[4]);
		assertEquals("3", date[5]);
		
		value = "2009-07-23";
		type = 23;
		date = DateFormatUtility.getParseDate(value, type);
		assertEquals("2009", date[0]);
		assertEquals("07", date[1]);
		assertEquals("23", date[2]);
		assertEquals("-", date[3]);
		assertEquals("1", date[4]);
		assertEquals("1", date[5]);
		
		value = "098-07-23";
		type = 23000;
		date = DateFormatUtility.getParseDate(value, type);
		assertEquals("098", date[0]);
		assertEquals("07", date[1]);
		assertEquals("23", date[2]);
		assertEquals("-", date[3]);
		assertEquals("1", date[4]);
		assertEquals("3", date[5]);
		
		value = "07/23/2009";
		type = 101;
		date = DateFormatUtility.getParseDate(value, type);
		assertEquals("2009", date[0]);
		assertEquals("07", date[1]);
		assertEquals("23", date[2]);
		assertEquals("/", date[3]);
		assertEquals("2", date[4]);
		assertEquals("1", date[5]);
		
		value = "07/23/098";
		type = 101000;
		date = DateFormatUtility.getParseDate(value, type);
		assertEquals("098", date[0]);
		assertEquals("07", date[1]);
		assertEquals("23", date[2]);
		assertEquals("/", date[3]);
		assertEquals("2", date[4]);
		assertEquals("3", date[5]);
		
		value = "2009.07.23";
		type = 102;
		date = DateFormatUtility.getParseDate(value, type);
		assertEquals("2009", date[0]);
		assertEquals("07", date[1]);
		assertEquals("23", date[2]);
		assertEquals(".", date[3]);
		assertEquals("1", date[4]);
		assertEquals("1", date[5]);
		
		value = "098.07.23";
		type = 102000;
		date = DateFormatUtility.getParseDate(value, type);
		assertEquals("098", date[0]);
		assertEquals("07", date[1]);
		assertEquals("23", date[2]);
		assertEquals(".", date[3]);
		assertEquals("1", date[4]);
		assertEquals("3", date[5]);
		
		value = "23/07/2009";
		type = 103;
		date = DateFormatUtility.getParseDate(value, type);
		assertEquals("2009", date[0]);
		assertEquals("07", date[1]);
		assertEquals("23", date[2]);
		assertEquals("/", date[3]);
		assertEquals("3", date[4]);
		assertEquals("1", date[5]);
		
		value = "23/07/098";
		type = 103000;
		date = DateFormatUtility.getParseDate(value, type);
		assertEquals("098", date[0]);
		assertEquals("07", date[1]);
		assertEquals("23", date[2]);
		assertEquals("/", date[3]);
		assertEquals("3", date[4]);
		assertEquals("3", date[5]);
		
		value = "23.07.2009";
		type = 104;
		date = DateFormatUtility.getParseDate(value, type);
		assertEquals("2009", date[0]);
		assertEquals("07", date[1]);
		assertEquals("23", date[2]);
		assertEquals(".", date[3]);
		assertEquals("3", date[4]);
		assertEquals("1", date[5]);
		
		value = "23.07.098";
		type = 104000;
		date = DateFormatUtility.getParseDate(value, type);
		assertEquals("098", date[0]);
		assertEquals("07", date[1]);
		assertEquals("23", date[2]);
		assertEquals(".", date[3]);
		assertEquals("3", date[4]);
		assertEquals("3", date[5]);
		
		value = "23-07-2009";
		type = 105;
		date = DateFormatUtility.getParseDate(value, type);
		assertEquals("2009", date[0]);
		assertEquals("07", date[1]);
		assertEquals("23", date[2]);
		assertEquals("-", date[3]);
		assertEquals("3", date[4]);
		assertEquals("1", date[5]);
		
		value = "23-07-098";
		type = 105000;
		date = DateFormatUtility.getParseDate(value, type);
		assertEquals("098", date[0]);
		assertEquals("07", date[1]);
		assertEquals("23", date[2]);
		assertEquals("-", date[3]);
		assertEquals("3", date[4]);
		assertEquals("3", date[5]);
		
		value = "07-23-2009";
		type = 110;
		date = DateFormatUtility.getParseDate(value, type);
		assertEquals("2009", date[0]);
		assertEquals("07", date[1]);
		assertEquals("23", date[2]);
		assertEquals("-", date[3]);
		assertEquals("2", date[4]);
		assertEquals("1", date[5]);
		
		value = "07-23-098";
		type = 110000;
		date = DateFormatUtility.getParseDate(value, type);
		assertEquals("098", date[0]);
		assertEquals("07", date[1]);
		assertEquals("23", date[2]);
		assertEquals("-", date[3]);
		assertEquals("2", date[4]);
		assertEquals("3", date[5]);
		
		value = "2009/07/23";
		type = 111;
		date = DateFormatUtility.getParseDate(value, type);
		assertEquals("2009", date[0]);
		assertEquals("07", date[1]);
		assertEquals("23", date[2]);
		assertEquals("/", date[3]);
		assertEquals("1", date[4]);
		assertEquals("1", date[5]);
		
		value = "098/07/23";
		type = 111000;
		date = DateFormatUtility.getParseDate(value, type);
		assertEquals("098", date[0]);
		assertEquals("07", date[1]);
		assertEquals("23", date[2]);
		assertEquals("/", date[3]);
		assertEquals("1", date[4]);
		assertEquals("3", date[5]);
		
		value = "20090723";
		type = 112;
		date = DateFormatUtility.getParseDate(value, type);
		assertEquals("2009", date[0]);
		assertEquals("07", date[1]);
		assertEquals("23", date[2]);
		assertEquals("", date[3]);
		assertEquals("1", date[4]);
		assertEquals("1", date[5]);
		
		value = "0980723";
		type = 112000;
		date = DateFormatUtility.getParseDate(value, type);
		assertEquals("098", date[0]);
		assertEquals("07", date[1]);
		assertEquals("23", date[2]);
		assertEquals("", date[3]);
		assertEquals("1", date[4]);
		assertEquals("3", date[5]);
	}
	
	@Test
	public void testIsValid12() {
		String time = "00:00";
		assertEquals(true, DateFormatUtility.isValidTime12(time));
		
		time = "1:01";
		assertEquals(true, DateFormatUtility.isValidTime12(time));
		
		time = "02:2";
		assertEquals(true, DateFormatUtility.isValidTime12(time));
		
		time = "3:3";
		assertEquals(true, DateFormatUtility.isValidTime12(time));
		
		time = "11:59";
		assertEquals(true, DateFormatUtility.isValidTime12(time));
		
		time = "12:00";
		assertEquals(false, DateFormatUtility.isValidTime12(time));
		
		time = "-1:00";
		assertEquals(false, DateFormatUtility.isValidTime12(time));
		
		time = "0:-1";
		assertEquals(false, DateFormatUtility.isValidTime12(time));
		
		time = "0:60";
		assertEquals(false, DateFormatUtility.isValidTime12(time));
	}
	
	@Test
	public void testIsValid24() {
		String time = "00:00";
		assertEquals(true, DateFormatUtility.isValidTime24(time));
		
		time = "1:01";
		assertEquals(true, DateFormatUtility.isValidTime24(time));
		
		time = "02:2";
		assertEquals(true, DateFormatUtility.isValidTime24(time));
		
		time = "3:3";
		assertEquals(true, DateFormatUtility.isValidTime24(time));
		
		time = "23:59";
		assertEquals(true, DateFormatUtility.isValidTime24(time));
		
		time = "24:00";
		assertEquals(false, DateFormatUtility.isValidTime24(time));
		
		time = "-1:00";
		assertEquals(false, DateFormatUtility.isValidTime24(time));
		
		time = "0:-1";
		assertEquals(false, DateFormatUtility.isValidTime24(time));
		
		time = "0:60";
		assertEquals(false, DateFormatUtility.isValidTime24(time));
	}
	
	@Test
	public void testIsValidMonth() {
		int month = 7;
		assertEquals(true, DateFormatUtility.isValidMonth(month));
		
		month = 0;
		assertEquals(false, DateFormatUtility.isValidMonth(month));
		
		month = 13;
		assertEquals(false, DateFormatUtility.isValidMonth(month));
	}
	
	@Test
	public void testIsValidDateStringInt() throws DateFormatException {
		String value = "2009-07-23";
		int type = 23;
		
		int targetType = 1;
		assertEquals(true, DateFormatUtility.isValidDate(DateFormatUtility.transDate(value, type, targetType), targetType));
		
		targetType = 1000;
		assertEquals(true, DateFormatUtility.isValidDate(DateFormatUtility.transDate(value, type, targetType), targetType));
		
		targetType = 2;
		assertEquals(true, DateFormatUtility.isValidDate(DateFormatUtility.transDate(value, type, targetType), targetType));
		
		targetType = 2000;
		assertEquals(true, DateFormatUtility.isValidDate(DateFormatUtility.transDate(value, type, targetType), targetType));
		
		targetType = 3;
		assertEquals(true, DateFormatUtility.isValidDate(DateFormatUtility.transDate(value, type, targetType), targetType));
		
		targetType = 3000;
		assertEquals(true, DateFormatUtility.isValidDate(DateFormatUtility.transDate(value, type, targetType), targetType));
		
		targetType = 4;
		assertEquals(true, DateFormatUtility.isValidDate(DateFormatUtility.transDate(value, type, targetType), targetType));
		
		targetType = 4000;
		assertEquals(true, DateFormatUtility.isValidDate(DateFormatUtility.transDate(value, type, targetType), targetType));
		
		targetType = 5;
		assertEquals(true, DateFormatUtility.isValidDate(DateFormatUtility.transDate(value, type, targetType), targetType));
		
		targetType = 5000;
		assertEquals(true, DateFormatUtility.isValidDate(DateFormatUtility.transDate(value, type, targetType), targetType));
		
		targetType = 10;
		assertEquals(true, DateFormatUtility.isValidDate(DateFormatUtility.transDate(value, type, targetType), targetType));
		
		targetType = 10000;
		assertEquals(true, DateFormatUtility.isValidDate(DateFormatUtility.transDate(value, type, targetType), targetType));
		
		targetType = 11;
		assertEquals(true, DateFormatUtility.isValidDate(DateFormatUtility.transDate(value, type, targetType), targetType));
		
		targetType = 11000;
		assertEquals(true, DateFormatUtility.isValidDate(DateFormatUtility.transDate(value, type, targetType), targetType));
		
		targetType = 12;
		assertEquals(true, DateFormatUtility.isValidDate(DateFormatUtility.transDate(value, type, targetType), targetType));
		
		targetType = 12000;
		assertEquals(true, DateFormatUtility.isValidDate(DateFormatUtility.transDate(value, type, targetType), targetType));

		targetType = 23;
		assertEquals(true, DateFormatUtility.isValidDate(value, targetType));
		
		targetType = 23000;
		assertEquals(true, DateFormatUtility.isValidDate(DateFormatUtility.transDate(value, type, targetType), targetType));
		
		targetType = 101;
		assertEquals(true, DateFormatUtility.isValidDate(DateFormatUtility.transDate(value, type, targetType), targetType));
		
		targetType = 101000;
		assertEquals(true, DateFormatUtility.isValidDate(DateFormatUtility.transDate(value, type, targetType), targetType));
		
		targetType = 102;
		assertEquals(true, DateFormatUtility.isValidDate(DateFormatUtility.transDate(value, type, targetType), targetType));
		
		targetType = 102000;
		assertEquals(true, DateFormatUtility.isValidDate(DateFormatUtility.transDate(value, type, targetType), targetType));
		
		targetType = 103;
		assertEquals(true, DateFormatUtility.isValidDate(DateFormatUtility.transDate(value, type, targetType), targetType));
		
		targetType = 103000;
		assertEquals(true, DateFormatUtility.isValidDate(DateFormatUtility.transDate(value, type, targetType), targetType));
		
		targetType = 104;
		assertEquals(true, DateFormatUtility.isValidDate(DateFormatUtility.transDate(value, type, targetType), targetType));
		
		targetType = 104000;
		assertEquals(true, DateFormatUtility.isValidDate(DateFormatUtility.transDate(value, type, targetType), targetType));
		
		targetType = 105;
		assertEquals(true, DateFormatUtility.isValidDate(DateFormatUtility.transDate(value, type, targetType), targetType));
		
		targetType = 105000;
		assertEquals(true, DateFormatUtility.isValidDate(DateFormatUtility.transDate(value, type, targetType), targetType));
		
		targetType = 110;
		assertEquals(true, DateFormatUtility.isValidDate(DateFormatUtility.transDate(value, type, targetType), targetType));
		
		targetType = 110000;
		assertEquals(true, DateFormatUtility.isValidDate(DateFormatUtility.transDate(value, type, targetType), targetType));
		
		targetType = 111;
		assertEquals(true, DateFormatUtility.isValidDate(DateFormatUtility.transDate(value, type, targetType), targetType));
		
		targetType = 111000;
		assertEquals(true, DateFormatUtility.isValidDate(DateFormatUtility.transDate(value, type, targetType), targetType));
		
		targetType = 112;
		assertEquals(true, DateFormatUtility.isValidDate(DateFormatUtility.transDate(value, type, targetType), targetType));
		
		targetType = 112000;
		assertEquals(true, DateFormatUtility.isValidDate(DateFormatUtility.transDate(value, type, targetType), targetType));
	}
	
	@Test
	public void testIsValidDateIntIntInt() throws DateFormatException {
		int year = 2009;
		int month = 7;
		int day = 23;
		assertEquals(true, DateFormatUtility.isValidDate(year, month, day));
		
		year = 2009;
		month = 7;
		day = 32;
		assertEquals(false, DateFormatUtility.isValidDate(year, month, day));
		
		year = 2009;
		month = 7;
		day = 0;
		assertEquals(false, DateFormatUtility.isValidDate(year, month, day));
		
		year = 2009;
		month = 0;
		day = 1;
		assertEquals(false, DateFormatUtility.isValidDate(year, month, day));
		
		year = 2009;
		month = 13;
		day = 1;
		assertEquals(false, DateFormatUtility.isValidDate(year, month, day));
		
		year = 2009;
		month = 0;
		day = 0;
		assertEquals(false, DateFormatUtility.isValidDate(year, month, day));
		
		year = 2009;
		month = 0;
		day = 32;
		assertEquals(false, DateFormatUtility.isValidDate(year, month, day));
		
		year = 2009;
		month = 13;
		day = 0;
		assertEquals(false, DateFormatUtility.isValidDate(year, month, day));
		
		year = 2009;
		month = 13;
		day = 32;
		assertEquals(false, DateFormatUtility.isValidDate(year, month, day));
	}

	@Test
	public void testIsValidDateWithYearType() throws NumberFormatException, DateFormatException {
		String year = "2009";
		String month = "7";
		String day = "23";
		assertEquals(true, DateFormatUtility.isValidDateWithYearType(year, month, day, "1"));
		
		year = "2009";
		month = "7";
		day = "32";
		assertEquals(false, DateFormatUtility.isValidDateWithYearType(year, month, day, "1"));
		
		year = "2009";
		month = "7";
		day = "0";
		assertEquals(false, DateFormatUtility.isValidDateWithYearType(year, month, day, "1"));
		
		year = "2009";
		month = "0";
		day = "1";
		assertEquals(false, DateFormatUtility.isValidDateWithYearType(year, month, day, "1"));
		
		year = "2009";
		month = "13";
		day = "1";
		assertEquals(false, DateFormatUtility.isValidDateWithYearType(year, month, day, "1"));
		
		year = "2009";
		month = "0";
		day = "0";
		assertEquals(false, DateFormatUtility.isValidDateWithYearType(year, month, day, "1"));
		
		year = "2009";
		month = "0";
		day = "32";
		assertEquals(false, DateFormatUtility.isValidDateWithYearType(year, month, day, "1"));
		
		year = "2009";
		month = "13";
		day = "0";
		assertEquals(false, DateFormatUtility.isValidDateWithYearType(year, month, day, "1"));
		
		year = "2009";
		month = "13";
		day = "32";
		assertEquals(false, DateFormatUtility.isValidDateWithYearType(year, month, day, "1"));
		
		year = "09";
		month = "7";
		day = "23";
		assertEquals(true, DateFormatUtility.isValidDateWithYearType(year, month, day, "2"));
		
		year = "09";
		month = "7";
		day = "32";
		assertEquals(false, DateFormatUtility.isValidDateWithYearType(year, month, day, "2"));
		
		year = "09";
		month = "7";
		day = "0";
		assertEquals(false, DateFormatUtility.isValidDateWithYearType(year, month, day, "2"));
		
		year = "09";
		month = "0";
		day = "1";
		assertEquals(false, DateFormatUtility.isValidDateWithYearType(year, month, day, "2"));
		
		year = "09";
		month = "13";
		day = "1";
		assertEquals(false, DateFormatUtility.isValidDateWithYearType(year, month, day, "2"));
		
		year = "09";
		month = "0";
		day = "0";
		assertEquals(false, DateFormatUtility.isValidDateWithYearType(year, month, day, "2"));
		
		year = "09";
		month = "0";
		day = "32";
		assertEquals(false, DateFormatUtility.isValidDateWithYearType(year, month, day, "2"));
		
		year = "09";
		month = "13";
		day = "0";
		assertEquals(false, DateFormatUtility.isValidDateWithYearType(year, month, day, "2"));
		
		year = "09";
		month = "13";
		day = "32";
		assertEquals(false, DateFormatUtility.isValidDateWithYearType(year, month, day, "2"));
		
		year = "098";
		month = "7";
		day = "23";
		assertEquals(true, DateFormatUtility.isValidDateWithYearType(year, month, day, "3"));
		
		year = "098";
		month = "7";
		day = "32";
		assertEquals(false, DateFormatUtility.isValidDateWithYearType(year, month, day, "3"));
		
		year = "098";
		month = "7";
		day = "0";
		assertEquals(false, DateFormatUtility.isValidDateWithYearType(year, month, day, "3"));
		
		year = "098";
		month = "0";
		day = "1";
		assertEquals(false, DateFormatUtility.isValidDateWithYearType(year, month, day, "3"));
		
		year = "098";
		month = "13";
		day = "1";
		assertEquals(false, DateFormatUtility.isValidDateWithYearType(year, month, day, "3"));
		
		year = "098";
		month = "0";
		day = "0";
		assertEquals(false, DateFormatUtility.isValidDateWithYearType(year, month, day, "3"));
		
		year = "098";
		month = "0";
		day = "32";
		assertEquals(false, DateFormatUtility.isValidDateWithYearType(year, month, day, "3"));
		
		year = "098";
		month = "13";
		day = "0";
		assertEquals(false, DateFormatUtility.isValidDateWithYearType(year, month, day, "3"));
		
		year = "098";
		month = "13";
		day = "32";
		assertEquals(false, DateFormatUtility.isValidDateWithYearType(year, month, day, "3"));
	}
	
	@Test
	public void testCompleteHourMinute() throws DateFormatException {
		assertEquals("00:00", DateFormatUtility.completeHourMinuteSecond("00:00"));
		assertEquals("01:01", DateFormatUtility.completeHourMinuteSecond("1:01"));
		assertEquals("02:02", DateFormatUtility.completeHourMinuteSecond("02:2"));
		assertEquals("03:03", DateFormatUtility.completeHourMinuteSecond("3:3"));
		assertEquals("23:59", DateFormatUtility.completeHourMinuteSecond("23:59"));
		
		try {
			DateFormatUtility.completeHourMinuteSecond("24:00");
			DateFormatUtility.completeHourMinuteSecond("-1:00");
			DateFormatUtility.completeHourMinuteSecond("0:-1");
			DateFormatUtility.completeHourMinuteSecond("0:60");
			assertEquals(true, false);
		} catch(DateFormatException dfex) {
			assertEquals(true, true);
		} 
	}

	@Test
	public void testCompleteMonthDay() throws DateFormatException {
		assertEquals("07/03/09", DateFormatUtility.completeMonthDay("7/3/09", 1));
		assertEquals("07/03/098", DateFormatUtility.completeMonthDay("7/3/098", 1000));
		assertEquals("09.07.03", DateFormatUtility.completeMonthDay("09.7.3", 2));
		assertEquals("098.07.03", DateFormatUtility.completeMonthDay("098.7.3", 2000));
		assertEquals("03/07/09", DateFormatUtility.completeMonthDay("3/7/09", 3));
		assertEquals("03/07/098", DateFormatUtility.completeMonthDay("3/7/098", 3000));
		assertEquals("03.07.09", DateFormatUtility.completeMonthDay("3.7.09", 4));
		assertEquals("03.07.098", DateFormatUtility.completeMonthDay("3.7.098", 4000));
		assertEquals("03-07-09", DateFormatUtility.completeMonthDay("3-7-09", 5));
		assertEquals("03-07-098", DateFormatUtility.completeMonthDay("3-7-098", 5000));
		assertEquals("07-03-09", DateFormatUtility.completeMonthDay("7-3-09", 10));
		assertEquals("07-03-098", DateFormatUtility.completeMonthDay("7-3-098", 10000));
		assertEquals("09/07/03", DateFormatUtility.completeMonthDay("09/7/3", 11));
		assertEquals("098/07/03", DateFormatUtility.completeMonthDay("098/7/3", 11000));
		try {
			DateFormatUtility.completeMonthDay("0973", 12);
			DateFormatUtility.completeMonthDay("09873", 12000);
			assertEquals(true, false);
		} catch(DateFormatException dfex) {
			assertEquals(true, true);
		}
		assertEquals("2009-07-03", DateFormatUtility.completeMonthDay("2009-7-3", 23));
		assertEquals("098-07-03", DateFormatUtility.completeMonthDay("098-7-3", 23000));
		assertEquals("07/03/2009", DateFormatUtility.completeMonthDay("7/3/2009", 101));
		assertEquals("07/03/098", DateFormatUtility.completeMonthDay("7/3/098", 101000));
		assertEquals("07/03/2009", DateFormatUtility.completeMonthDay("7/3/2009", 101));
		assertEquals("07/03/098", DateFormatUtility.completeMonthDay("7/3/098", 101000));
		assertEquals("2009.07.03", DateFormatUtility.completeMonthDay("2009.7.3", 102));
		assertEquals("098.07.03", DateFormatUtility.completeMonthDay("098.7.3", 102000));
		assertEquals("03/07/2009", DateFormatUtility.completeMonthDay("3/7/2009", 103));
		assertEquals("03/07/098", DateFormatUtility.completeMonthDay("3/7/098", 103000));
		assertEquals("03.07.2009", DateFormatUtility.completeMonthDay("3.7.2009", 104));
		assertEquals("03.07.098", DateFormatUtility.completeMonthDay("3.7.098", 104000));
		assertEquals("03-07-2009", DateFormatUtility.completeMonthDay("3-7-2009", 105));
		assertEquals("03-07-098", DateFormatUtility.completeMonthDay("3-7-098", 105000));
		assertEquals("07-03-2009", DateFormatUtility.completeMonthDay("7-3-2009", 110));
		assertEquals("07-03-098", DateFormatUtility.completeMonthDay("7-3-098", 110000));
		assertEquals("2009/07/03", DateFormatUtility.completeMonthDay("2009/7/3", 111));
		assertEquals("098/07/03", DateFormatUtility.completeMonthDay("098/7/3", 111000));
		try {
			DateFormatUtility.completeMonthDay("200973", 112);
			DateFormatUtility.completeMonthDay("09873", 112000);
			assertEquals(true, false);
		} catch(DateFormatException dfex) {
			assertEquals(true, true);
		}
	}

	@Test
	public void testCompleteYear() {
		String value = "1";
		String type = "1";
		assertEquals("1", DateFormatUtility.completeYear(value, type));
		
		value = "1";
		type = "2";
		assertEquals("01", DateFormatUtility.completeYear(value, type));
		
		value = "1";
		type = "3";
		assertEquals("001", DateFormatUtility.completeYear(value, type));
		
		value = "10";
		type = "1";
		assertEquals("10", DateFormatUtility.completeYear(value, type));
		
		value = "10";
		type = "2";
		assertEquals("10", DateFormatUtility.completeYear(value, type));
		
		value = "10";
		type = "3";
		assertEquals("010", DateFormatUtility.completeYear(value, type));
		
		value = "100";
		type = "1";
		assertEquals("100", DateFormatUtility.completeYear(value, type));
		
		value = "100";
		type = "2";
		assertEquals("100", DateFormatUtility.completeYear(value, type));
		
		value = "100";
		type = "3";
		assertEquals("100", DateFormatUtility.completeYear(value, type));
		
		value = "1000";
		type = "1";
		assertEquals("1000", DateFormatUtility.completeYear(value, type));
		
		value = "1000";
		type = "2";
		assertEquals("1000", DateFormatUtility.completeYear(value, type));
		
		value = "1000";
		type = "3";
		assertEquals("1000", DateFormatUtility.completeYear(value, type));
	}
	
	@Test
	public void testCompareTime12() throws DateFormatException {
		int mid1 = Calendar.AM;
		String value1 = "02:00";
		int mid2 = Calendar.AM;
		String value2 = "01:00";
		assertEquals(true, DateFormatUtility.compareTime12(mid1, value1, mid2, value2));
		
		mid1 = Calendar.AM;
		value1 = "02:00";
		mid2 = Calendar.PM;
		value2 = "01:00";
		assertEquals(false, DateFormatUtility.compareTime12(mid1, value1, mid2, value2));
		
		mid1 = Calendar.AM;
		value1 = "02:00";
		mid2 = Calendar.AM;
		value2 = "02:00";
		assertEquals(false, DateFormatUtility.compareTime12(mid1, value1, mid2, value2));
		
		mid1 = Calendar.PM;
		value1 = "02:00";
		mid2 = Calendar.AM;
		value2 = "02:00";
		assertEquals(true, DateFormatUtility.compareTime12(mid1, value1, mid2, value2));
		
		mid1 = Calendar.AM;
		value1 = "02:00";
		mid2 = Calendar.PM;
		value2 = "02:00";
		assertEquals(false, DateFormatUtility.compareTime12(mid1, value1, mid2, value2));
		
		mid1 = Calendar.AM;
		value1 = "01:00";
		mid2 = Calendar.AM;
		value2 = "02:00";
		assertEquals(false, DateFormatUtility.compareTime12(mid1, value1, mid2, value2));
		
		mid1 = Calendar.PM;
		value1 = "01:00";
		mid2 = Calendar.AM;
		value2 = "02:00";
		assertEquals(true, DateFormatUtility.compareTime12(mid1, value1, mid2, value2));
		
		mid1 = Calendar.AM;
		value1 = "2:00";
		mid2 = Calendar.AM;
		value2 = "10:00";
		assertEquals(false, DateFormatUtility.compareTime12(mid1, value1, mid2, value2));
		
		mid1 = Calendar.PM;
		value1 = "2:00";
		mid2 = Calendar.AM;
		value2 = "10:00";
		assertEquals(true, DateFormatUtility.compareTime12(mid1, value1, mid2, value2));
		
		mid1 = Calendar.AM;
		value1 = "1:2";
		mid2 = Calendar.AM;
		value2 = "01:10";
		assertEquals(false, DateFormatUtility.compareTime12(mid1, value1, mid2, value2));
		
		mid1 = Calendar.PM;
		value1 = "1:2";
		mid2 = Calendar.AM;
		value2 = "01:10";
		assertEquals(true, DateFormatUtility.compareTime12(mid1, value1, mid2, value2));
		
		try {
			mid1 = Calendar.AM;
			mid1 = Calendar.AM;
			
			value1 = "12:00";
			value2 = "00:00";
			DateFormatUtility.compareTime12(mid1, value1, mid2, value2);
			
			value1 = "00:00";
			value2 = "12:00";
			DateFormatUtility.compareTime12(mid1, value1, mid2, value2);
			
			value1 = "12:00";
			value2 = "12:00";
			DateFormatUtility.compareTime12(mid1, value1, mid2, value2);
			
			value1 = "-1:00";
			value1 = "00:00";
			DateFormatUtility.compareTime12(mid1, value1, mid2, value2);
			
			value1 = "00:00";
			value1 = "-1:00";
			DateFormatUtility.compareTime12(mid1, value1, mid2, value2);
			
			value1 = "-1:00";
			value1 = "-1:00";
			DateFormatUtility.compareTime12(mid1, value1, mid2, value2);
		
			value1 = "0:-1";
			value1 = "00:00";
			DateFormatUtility.compareTime12(mid1, value1, mid2, value2);
			
			value1 = "00:00";
			value1 = "00:-1";
			DateFormatUtility.compareTime12(mid1, value1, mid2, value2);
			
			value1 = "0:-1";
			value1 = "0:-1";
			DateFormatUtility.compareTime12(mid1, value1, mid2, value2);
		
			value1 = "0:60";
			value1 = "00:00";
			DateFormatUtility.compareTime12(mid1, value1, mid2, value2);
			
			value1 = "00:00";
			value1 = "00:60";
			DateFormatUtility.compareTime12(mid1, value1, mid2, value2);
			
			value1 = "0:60";
			value1 = "0:60";
			DateFormatUtility.compareTime12(mid1, value1, mid2, value2);
			
			value1 = "02:00";
			value2 = "01:00";
			
			mid1 = Calendar.ALL_STYLES;
			mid2 = Calendar.AM;
			DateFormatUtility.compareTime12(mid1, value1, mid2, value2);
			
			mid1 = Calendar.AM;
			mid2 = Calendar.AM_PM;
			DateFormatUtility.compareTime12(mid1, value1, mid2, value2);
			
			mid1 = Calendar.APRIL;
			mid2 = Calendar.AUGUST;
			DateFormatUtility.compareTime12(mid1, value1, mid2, value2);
			
			assertEquals(true, false);
		} catch(DateFormatException dfex) {
			assertEquals(true, true);
		} 
	}
	
	@Test
	public void testCompareTime() throws DateFormatException {
		String value1 = "02:00";
		String value2 = "01:00";
		assertEquals(true, DateFormatUtility.compareTime(value1, value2));
		
		value1 = "02:00";
		value2 = "02:00";
		assertEquals(false, DateFormatUtility.compareTime(value1, value2));
		
		value1 = "01:00";
		value2 = "02:00";
		assertEquals(false, DateFormatUtility.compareTime(value1, value2));
		
		value1 = "2:00";
		value2 = "12:00";
		assertEquals(false, DateFormatUtility.compareTime(value1, value2));
		
		value1 = "1:2";
		value2 = "01:10";
		assertEquals(false, DateFormatUtility.compareTime(value1, value2));
		
		try {
			value1 = "24:00";
			value2 = "00:00";
			DateFormatUtility.compareTime(value1, value2);
			
			value1 = "00:00";
			value2 = "24:00";
			DateFormatUtility.compareTime(value1, value2);
			
			value1 = "24:00";
			value2 = "24:00";
			DateFormatUtility.compareTime(value1, value2);
			
			value1 = "-1:00";
			value1 = "00:00";
			DateFormatUtility.compareTime(value1, value2);
			
			value1 = "00:00";
			value1 = "-1:00";
			DateFormatUtility.compareTime(value1, value2);
			
			value1 = "-1:00";
			value1 = "-1:00";
			DateFormatUtility.compareTime(value1, value2);
		
			value1 = "0:-1";
			value1 = "00:00";
			DateFormatUtility.compareTime(value1, value2);
			
			value1 = "00:00";
			value1 = "00:-1";
			DateFormatUtility.compareTime(value1, value2);
			
			value1 = "0:-1";
			value1 = "0:-1";
			DateFormatUtility.compareTime(value1, value2);
		
			value1 = "0:60";
			value1 = "00:00";
			DateFormatUtility.compareTime(value1, value2);
			
			value1 = "00:00";
			value1 = "00:60";
			DateFormatUtility.compareTime(value1, value2);
			
			value1 = "0:60";
			value1 = "0:60";
			DateFormatUtility.compareTime(value1, value2);
			
			assertEquals(true, false);
		} catch(DateFormatException dfex) {
			assertEquals(true, true);
		} 
	}

	@Test
	public void testCompareDate() throws DateFormatException {
		String value1 = "07/23/09";
		String value2 = "07/24/09";
		int type = 1;
		assertEquals(false, DateFormatUtility.compareDate(value1, value2, type));
		assertEquals(true, DateFormatUtility.compareDate(value2, value1, type));
		
		value1 = "07/23/098";
		value2 = "07/24/098";
		type = 1000;
		assertEquals(false, DateFormatUtility.compareDate(value1, value2, type));
		assertEquals(true, DateFormatUtility.compareDate(value2, value1, type));
		
		value1 = "09.07.23";
		value2 = "09.07.24";
		type = 2;
		assertEquals(false, DateFormatUtility.compareDate(value1, value2, type));
		assertEquals(true, DateFormatUtility.compareDate(value2, value1, type));
		
		value1 = "098.07.23";
		value2 = "098.07.24";
		type = 2000;
		assertEquals(false, DateFormatUtility.compareDate(value1, value2, type));
		assertEquals(true, DateFormatUtility.compareDate(value2, value1, type));
		
		value1 = "23/07/09";
		value2 = "24/07/09";
		type = 3;
		assertEquals(false, DateFormatUtility.compareDate(value1, value2, type));
		assertEquals(true, DateFormatUtility.compareDate(value2, value1, type));
		
		value1 = "23/07/098";
		value2 = "24/07/098";
		type = 3000;
		assertEquals(false, DateFormatUtility.compareDate(value1, value2, type));
		assertEquals(true, DateFormatUtility.compareDate(value2, value1, type));
		
		value1 = "23.07.09";
		value2 = "24.07.09";
		type = 4;
		assertEquals(false, DateFormatUtility.compareDate(value1, value2, type));
		assertEquals(true, DateFormatUtility.compareDate(value2, value1, type));
		
		value1 = "23.07.098";
		value2 = "24.07.098";
		type = 4000;
		assertEquals(false, DateFormatUtility.compareDate(value1, value2, type));
		assertEquals(true, DateFormatUtility.compareDate(value2, value1, type));
		
		value1 = "23-07-09";
		value2 = "24-07-09";
		type = 5;
		assertEquals(false, DateFormatUtility.compareDate(value1, value2, type));
		assertEquals(true, DateFormatUtility.compareDate(value2, value1, type));
		
		value1 = "23-07-098";
		value2 = "24-07-098";
		type = 5000;
		assertEquals(false, DateFormatUtility.compareDate(value1, value2, type));
		assertEquals(true, DateFormatUtility.compareDate(value2, value1, type));
		
		value1 = "07-23-09";
		value2 = "07-24-09";
		type = 10;
		assertEquals(false, DateFormatUtility.compareDate(value1, value2, type));
		assertEquals(true, DateFormatUtility.compareDate(value2, value1, type));
		
		value1 = "07-23-098";
		value2 = "07-24-098";
		type = 10000;
		assertEquals(false, DateFormatUtility.compareDate(value1, value2, type));
		assertEquals(true, DateFormatUtility.compareDate(value2, value1, type));
		
		value1 = "09/07/23";
		value2 = "09/07/24";
		type = 11;
		assertEquals(false, DateFormatUtility.compareDate(value1, value2, type));
		assertEquals(true, DateFormatUtility.compareDate(value2, value1, type));
		
		value1 = "098/07/23";
		value2 = "098/07/24";
		type = 11000;
		assertEquals(false, DateFormatUtility.compareDate(value1, value2, type));
		assertEquals(true, DateFormatUtility.compareDate(value2, value1, type));
		
		value1 = "090723";
		value2 = "090724";
		type = 12;
		assertEquals(false, DateFormatUtility.compareDate(value1, value2, type));
		assertEquals(true, DateFormatUtility.compareDate(value2, value1, type));
		
		value1 = "0980723";
		value2 = "0980724";
		type = 12000;
		assertEquals(false, DateFormatUtility.compareDate(value1, value2, type));
		assertEquals(true, DateFormatUtility.compareDate(value2, value1, type));
		
		value1 = "2009-07-23";
		value2 = "2009-07-24";
		type = 23;
		assertEquals(false, DateFormatUtility.compareDate(value1, value2, type));
		assertEquals(true, DateFormatUtility.compareDate(value2, value1, type));
		
		value1 = "098-07-23";
		value2 = "098-07-24";
		type = 23000;
		assertEquals(false, DateFormatUtility.compareDate(value1, value2, type));
		assertEquals(true, DateFormatUtility.compareDate(value2, value1, type));
		
		value1 = "07/23/2009";
		value2 = "07/24/2009";
		type = 101;
		assertEquals(false, DateFormatUtility.compareDate(value1, value2, type));
		assertEquals(true, DateFormatUtility.compareDate(value2, value1, type));
		
		value1 = "07/23/098";
		value2 = "07/24/098";
		type = 101000;
		assertEquals(false, DateFormatUtility.compareDate(value1, value2, type));
		assertEquals(true, DateFormatUtility.compareDate(value2, value1, type));
		
		value1 = "2009.07.23";
		value2 = "2009.07.24";
		type = 102;
		assertEquals(false, DateFormatUtility.compareDate(value1, value2, type));
		assertEquals(true, DateFormatUtility.compareDate(value2, value1, type));
		
		value1 = "098.07.23";
		value2 = "098.07.24";
		type = 102000;
		assertEquals(false, DateFormatUtility.compareDate(value1, value2, type));
		assertEquals(true, DateFormatUtility.compareDate(value2, value1, type));
		
		value1 = "23/07/2009";
		value2 = "24/07/2009";
		type = 103;
		assertEquals(false, DateFormatUtility.compareDate(value1, value2, type));
		assertEquals(true, DateFormatUtility.compareDate(value2, value1, type));
		
		value1 = "23/07/098";
		value2 = "24/07/098";
		type = 103000;
		assertEquals(false, DateFormatUtility.compareDate(value1, value2, type));
		assertEquals(true, DateFormatUtility.compareDate(value2, value1, type));
		
		value1 = "23.07.2009";
		value2 = "24.07.2009";
		type = 104;
		assertEquals(false, DateFormatUtility.compareDate(value1, value2, type));
		assertEquals(true, DateFormatUtility.compareDate(value2, value1, type));
		
		value1 = "23.07.098";
		value2 = "24.07.098";
		type = 104000;
		assertEquals(false, DateFormatUtility.compareDate(value1, value2, type));
		assertEquals(true, DateFormatUtility.compareDate(value2, value1, type));
		
		value1 = "23-07-2009";
		value2 = "24-07-2009";
		type = 105;
		assertEquals(false, DateFormatUtility.compareDate(value1, value2, type));
		assertEquals(true, DateFormatUtility.compareDate(value2, value1, type));
		
		value1 = "23-07-098";
		value2 = "24-07-098";
		type = 105000;
		assertEquals(false, DateFormatUtility.compareDate(value1, value2, type));
		assertEquals(true, DateFormatUtility.compareDate(value2, value1, type));
		
		value1 = "07-23-2009";
		value2 = "07-24-2009";
		type = 110;
		assertEquals(false, DateFormatUtility.compareDate(value1, value2, type));
		assertEquals(true, DateFormatUtility.compareDate(value2, value1, type));
		
		value1 = "07-23-098";
		value2 = "07-24-098";
		type = 110000;
		assertEquals(false, DateFormatUtility.compareDate(value1, value2, type));
		assertEquals(true, DateFormatUtility.compareDate(value2, value1, type));
		
		value1 = "2009/07/23";
		value2 = "2009/07/24";
		type = 111;
		assertEquals(false, DateFormatUtility.compareDate(value1, value2, type));
		assertEquals(true, DateFormatUtility.compareDate(value2, value1, type));
		
		value1 = "098/07/23";
		value2 = "098/07/24";
		type = 111000;
		assertEquals(false, DateFormatUtility.compareDate(value1, value2, type));
		assertEquals(true, DateFormatUtility.compareDate(value2, value1, type));
		
		value1 = "20090723";
		value2 = "20090724";
		type = 112;
		assertEquals(false, DateFormatUtility.compareDate(value1, value2, type));
		assertEquals(true, DateFormatUtility.compareDate(value2, value1, type));
		
		value1 = "0980723";
		value2 = "0980724";
		type = 112000;
		assertEquals(false, DateFormatUtility.compareDate(value1, value2, type));
		assertEquals(true, DateFormatUtility.compareDate(value2, value1, type));
	}

	@Test
	public void testToCalendarStringInt() throws DateFormatException {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2009);
		cal.set(Calendar.MONTH, 6);
		cal.set(Calendar.DAY_OF_MONTH, 23);
		
		assertEquals(cal.get(Calendar.YEAR), DateFormatUtility.toCalendar("07/23/09", 1).get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), DateFormatUtility.toCalendar("07/23/09", 1).get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), DateFormatUtility.toCalendar("07/23/09", 1).get(Calendar.DAY_OF_MONTH));
		
		assertEquals(cal.get(Calendar.YEAR), DateFormatUtility.toCalendar("07/23/098", 1000).get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), DateFormatUtility.toCalendar("07/23/098", 1000).get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), DateFormatUtility.toCalendar("07/23/098", 1000).get(Calendar.DAY_OF_MONTH));
		
		assertEquals(cal.get(Calendar.YEAR), DateFormatUtility.toCalendar("09.07.23", 2).get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), DateFormatUtility.toCalendar("09.07.23", 2).get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), DateFormatUtility.toCalendar("09.07.23", 2).get(Calendar.DAY_OF_MONTH));
		
		assertEquals(cal.get(Calendar.YEAR), DateFormatUtility.toCalendar("098.07.23", 2000).get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), DateFormatUtility.toCalendar("098.07.23", 2000).get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), DateFormatUtility.toCalendar("098.07.23", 2000).get(Calendar.DAY_OF_MONTH));
		
		assertEquals(cal.get(Calendar.YEAR), DateFormatUtility.toCalendar("23/07/09", 3).get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), DateFormatUtility.toCalendar("23/07/09", 3).get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), DateFormatUtility.toCalendar("23/07/09", 3).get(Calendar.DAY_OF_MONTH));
		
		assertEquals(cal.get(Calendar.YEAR), DateFormatUtility.toCalendar("23/07/098", 3000).get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), DateFormatUtility.toCalendar("23/07/098", 3000).get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), DateFormatUtility.toCalendar("23/07/098", 3000).get(Calendar.DAY_OF_MONTH));
		
		assertEquals(cal.get(Calendar.YEAR), DateFormatUtility.toCalendar("23.07.09", 4).get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), DateFormatUtility.toCalendar("23.07.09", 4).get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), DateFormatUtility.toCalendar("23.07.09", 4).get(Calendar.DAY_OF_MONTH));
		
		assertEquals(cal.get(Calendar.YEAR), DateFormatUtility.toCalendar("23.07.098", 4000).get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), DateFormatUtility.toCalendar("23.07.098", 4000).get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), DateFormatUtility.toCalendar("23.07.098", 4000).get(Calendar.DAY_OF_MONTH));
		
		assertEquals(cal.get(Calendar.YEAR), DateFormatUtility.toCalendar("23-07-09", 5).get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), DateFormatUtility.toCalendar("23-07-09", 5).get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), DateFormatUtility.toCalendar("23-07-09", 5).get(Calendar.DAY_OF_MONTH));
		
		assertEquals(cal.get(Calendar.YEAR), DateFormatUtility.toCalendar("23-07-098", 5000).get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), DateFormatUtility.toCalendar("23-07-098", 5000).get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), DateFormatUtility.toCalendar("23-07-098", 5000).get(Calendar.DAY_OF_MONTH));
		
		assertEquals(cal.get(Calendar.YEAR), DateFormatUtility.toCalendar("07-23-09", 10).get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), DateFormatUtility.toCalendar("07-23-09", 10).get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), DateFormatUtility.toCalendar("07-23-09", 10).get(Calendar.DAY_OF_MONTH));
		
		assertEquals(cal.get(Calendar.YEAR), DateFormatUtility.toCalendar("07-23-098", 10000).get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), DateFormatUtility.toCalendar("07-23-098", 10000).get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), DateFormatUtility.toCalendar("07-23-098", 10000).get(Calendar.DAY_OF_MONTH));
		
		assertEquals(cal.get(Calendar.YEAR), DateFormatUtility.toCalendar("09/07/23", 11).get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), DateFormatUtility.toCalendar("09/07/23", 11).get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), DateFormatUtility.toCalendar("09/07/23", 11).get(Calendar.DAY_OF_MONTH));
		
		assertEquals(cal.get(Calendar.YEAR), DateFormatUtility.toCalendar("098/07/23", 11000).get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), DateFormatUtility.toCalendar("098/07/23", 11000).get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), DateFormatUtility.toCalendar("098/07/23", 11000).get(Calendar.DAY_OF_MONTH));
		
		assertEquals(cal.get(Calendar.YEAR), DateFormatUtility.toCalendar("090723", 12).get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), DateFormatUtility.toCalendar("090723", 12).get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), DateFormatUtility.toCalendar("090723", 12).get(Calendar.DAY_OF_MONTH));
		
		assertEquals(cal.get(Calendar.YEAR), DateFormatUtility.toCalendar("0980723", 12000).get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), DateFormatUtility.toCalendar("0980723", 12000).get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), DateFormatUtility.toCalendar("0980723", 12000).get(Calendar.DAY_OF_MONTH));
		
		assertEquals(cal.get(Calendar.YEAR), DateFormatUtility.toCalendar("2009-07-23", 23).get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), DateFormatUtility.toCalendar("2009-07-23", 23).get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), DateFormatUtility.toCalendar("2009-07-23", 23).get(Calendar.DAY_OF_MONTH));
		
		assertEquals(cal.get(Calendar.YEAR), DateFormatUtility.toCalendar("098-07-23", 23000).get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), DateFormatUtility.toCalendar("098-07-23", 23000).get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), DateFormatUtility.toCalendar("098-07-23", 23000).get(Calendar.DAY_OF_MONTH));
		
		assertEquals(cal.get(Calendar.YEAR), DateFormatUtility.toCalendar("07/23/2009", 101).get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), DateFormatUtility.toCalendar("07/23/2009", 101).get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), DateFormatUtility.toCalendar("07/23/2009", 101).get(Calendar.DAY_OF_MONTH));
		
		assertEquals(cal.get(Calendar.YEAR), DateFormatUtility.toCalendar("07/23/098", 101000).get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), DateFormatUtility.toCalendar("07/23/098", 101000).get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), DateFormatUtility.toCalendar("07/23/098", 101000).get(Calendar.DAY_OF_MONTH));
		
		assertEquals(cal.get(Calendar.YEAR), DateFormatUtility.toCalendar("2009.07.23", 102).get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), DateFormatUtility.toCalendar("2009.07.23", 102).get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), DateFormatUtility.toCalendar("2009.07.23", 102).get(Calendar.DAY_OF_MONTH));
		
		assertEquals(cal.get(Calendar.YEAR), DateFormatUtility.toCalendar("098.07.23", 102000).get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), DateFormatUtility.toCalendar("098.07.23", 102000).get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), DateFormatUtility.toCalendar("098.07.23", 102000).get(Calendar.DAY_OF_MONTH));
		
		assertEquals(cal.get(Calendar.YEAR), DateFormatUtility.toCalendar("23/07/2009", 103).get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), DateFormatUtility.toCalendar("23/07/2009", 103).get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), DateFormatUtility.toCalendar("23/07/2009", 103).get(Calendar.DAY_OF_MONTH));
		
		assertEquals(cal.get(Calendar.YEAR), DateFormatUtility.toCalendar("23/07/098", 103000).get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), DateFormatUtility.toCalendar("23/07/098", 103000).get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), DateFormatUtility.toCalendar("23/07/098", 103000).get(Calendar.DAY_OF_MONTH));
		
		assertEquals(cal.get(Calendar.YEAR), DateFormatUtility.toCalendar("23.07.2009", 104).get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), DateFormatUtility.toCalendar("23.07.2009", 104).get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), DateFormatUtility.toCalendar("23.07.2009", 104).get(Calendar.DAY_OF_MONTH));
		
		assertEquals(cal.get(Calendar.YEAR), DateFormatUtility.toCalendar("23.07.098", 104000).get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), DateFormatUtility.toCalendar("23.07.098", 104000).get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), DateFormatUtility.toCalendar("23.07.098", 104000).get(Calendar.DAY_OF_MONTH));
		
		assertEquals(cal.get(Calendar.YEAR), DateFormatUtility.toCalendar("23-07-2009", 105).get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), DateFormatUtility.toCalendar("23-07-2009", 105).get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), DateFormatUtility.toCalendar("23-07-2009", 105).get(Calendar.DAY_OF_MONTH));
		
		assertEquals(cal.get(Calendar.YEAR), DateFormatUtility.toCalendar("23-07-098", 105000).get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), DateFormatUtility.toCalendar("23-07-098", 105000).get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), DateFormatUtility.toCalendar("23-07-098", 105000).get(Calendar.DAY_OF_MONTH));
		
		assertEquals(cal.get(Calendar.YEAR), DateFormatUtility.toCalendar("07-23-2009", 110).get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), DateFormatUtility.toCalendar("07-23-2009", 110).get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), DateFormatUtility.toCalendar("07-23-2009", 110).get(Calendar.DAY_OF_MONTH));
		
		assertEquals(cal.get(Calendar.YEAR), DateFormatUtility.toCalendar("07-23-098", 110000).get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), DateFormatUtility.toCalendar("07-23-098", 110000).get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), DateFormatUtility.toCalendar("07-23-098", 110000).get(Calendar.DAY_OF_MONTH));
		
		assertEquals(cal.get(Calendar.YEAR), DateFormatUtility.toCalendar("2009/07/23", 111).get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), DateFormatUtility.toCalendar("2009/07/23", 111).get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), DateFormatUtility.toCalendar("2009/07/23", 111).get(Calendar.DAY_OF_MONTH));
		
		assertEquals(cal.get(Calendar.YEAR), DateFormatUtility.toCalendar("098/07/23", 111000).get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), DateFormatUtility.toCalendar("098/07/23", 111000).get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), DateFormatUtility.toCalendar("098/07/23", 111000).get(Calendar.DAY_OF_MONTH));
		
		assertEquals(cal.get(Calendar.YEAR), DateFormatUtility.toCalendar("20090723", 112).get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), DateFormatUtility.toCalendar("20090723", 112).get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), DateFormatUtility.toCalendar("20090723", 112).get(Calendar.DAY_OF_MONTH));
		
		assertEquals(cal.get(Calendar.YEAR), DateFormatUtility.toCalendar("0980723", 112000).get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), DateFormatUtility.toCalendar("0980723", 112000).get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), DateFormatUtility.toCalendar("0980723", 112000).get(Calendar.DAY_OF_MONTH));
	}
	
	@Test
	public void testGetDateFormat() throws DateFormatException {
		assertEquals("MM/dd/yy", DateFormatUtility.getDateFormat(1));
		assertEquals("yy.MM.dd", DateFormatUtility.getDateFormat(2));
		assertEquals("dd/MM/yy", DateFormatUtility.getDateFormat(3));
		assertEquals("dd.MM.yy", DateFormatUtility.getDateFormat(4));
		assertEquals("dd-MM-yy", DateFormatUtility.getDateFormat(5));
		assertEquals("MM-dd-yy", DateFormatUtility.getDateFormat(10));
		assertEquals("yy/MM/dd", DateFormatUtility.getDateFormat(11));
		assertEquals("yyMMdd", DateFormatUtility.getDateFormat(12));
		assertEquals("yyyy-MM-dd", DateFormatUtility.getDateFormat(23));
		assertEquals("MM/dd/yyyy", DateFormatUtility.getDateFormat(101));
		assertEquals("yyyy.MM.dd", DateFormatUtility.getDateFormat(102));
		assertEquals("dd/MM/yyyy", DateFormatUtility.getDateFormat(103));
		assertEquals("dd.MM.yyyy", DateFormatUtility.getDateFormat(104));
		assertEquals("dd-MM-yyyy", DateFormatUtility.getDateFormat(105));
		assertEquals("MM-dd-yyyy", DateFormatUtility.getDateFormat(110));
		assertEquals("yyyy/MM/dd", DateFormatUtility.getDateFormat(111));
		assertEquals("yyyyMMdd", DateFormatUtility.getDateFormat(112));
		
		try {
			DateFormatUtility.getDateFormat(6);
			assertEquals(true, false);
		} catch(DateFormatException dfex) {
			assertEquals(true, true);
		}
	}
	
}
