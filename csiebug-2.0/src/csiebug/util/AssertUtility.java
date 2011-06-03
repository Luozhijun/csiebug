/**
 * 
 */
package csiebug.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * @author George_Tsai
 * @version 2009/12/30
 */
public class AssertUtility {
	/**
	 * 檢查物件是否是null
	 * @param object
	 * @param message
	 */
	public static void notNull(Object object, String message) {
		if(object == null) {
			throw new NullPointerException(message);
		}
	}
	
	/**
	 * 檢查物件是否是null
	 * @param object
	 */
	public static void notNull(Object object) {
		notNull(object, "Object is null!!");
	}
	
	/**
	 * 檢查字串是否是null或是空白字串
	 * @param string
	 * @param message
	 */
	public static void notNullAndNotSpace(String string, String message) {
		if(string == null || string.trim().equals("")) {
			throw new NullPointerException(message);
		}
	}
	
	/**
	 * 檢查字串是否是null或是空白字串
	 * @param string
	 */
	public static void notNullAndNotSpace(String string) {
		notNullAndNotSpace(string, "String is null or empty!!");
	}
	
	/**
	 * 檢查Collection物件是否是null或是size是0
	 * @param Collection
	 * @param message
	 */
	public static void notNullAndNotEmpty(Collection<?> c, String message) {
		if(c == null || c.size() <= 0) {
			throw new NullPointerException(message);
		}
	}
	
	/**
	 * 檢查Collection物件是否是null或是size是0
	 * @param Collection
	 */
	public static void notNullAndNotEmpty(Collection<?> c) {
		notNullAndNotEmpty(c, "Collection is null or empty!!");
	}
	
	/**
	 * 檢查Array是否是null或是size是0
	 * @param array
	 * @param message
	 */
	public static void notNullAndNotEmpty(Object[] ary, String message) {
		if(ary == null || ary.length <= 0) {
			throw new NullPointerException(message);
		}
	}
	
	/**
	 * 檢查Array是否是null或是size是0
	 * @param array
	 */
	public static void notNullAndNotEmpty(Object[] ary) {
		notNullAndNotEmpty(ary, "Array is null or empty!!");
	}
	
	/**
	 * 檢查Map的element是否有null
	 * @param collection
	 * @param message
	 */
	public static void allElementNotNull(Map<?, ?> map, String message) {
		notNull(map, message);
		
		allElementNotNull(map.values(), message);
	}
	
	/**
	 * 檢查Map的element是否有null
	 * @param collection
	 */
	public static void allElementNotNull(Map<?, ?> map) {
		allElementNotNull(map, "Element can not be null!!");
	}
	
	/**
	 * 檢查Collection的element是否有null
	 * @param collection
	 * @param message
	 */
	public static void allElementNotNull(Collection<?> c, String message) {
		notNullAndNotEmpty(c, message);
		
		Iterator<?> iterator = c.iterator();
		
		while(iterator.hasNext()) {
			notNull(iterator.next(), message);
		}
	}
	
	/**
	 * 檢查Collection的element是否有null
	 * @param collection
	 */
	public static void allElementNotNull(Collection<?> c) {
		allElementNotNull(c, "Element can not be null!!");
	}
	
	/**
	 * 檢查Array的element是否有null
	 * @param array
	 * @param message
	 */
	public static void allElementNotNull(Object[] ary, String message) {
		notNullAndNotEmpty(ary, message);
		
		for(int i = 0; i < ary.length; i++) {
			notNull(ary[i], message);
		}
	}
	
	/**
	 * 檢查Array的element是否有null
	 * @param array
	 */
	public static void allElementNotNull(Object[] ary) {
		allElementNotNull(ary, "Element can not be null!!");
	}
	
	/**
	 * 檢查字串是否是null或是空白字串
	 * @param string
	 * @return
	 */
	public static boolean isNotNullAndNotSpace(String string) {
		try {
			notNullAndNotSpace(string);
		} catch(NullPointerException npex) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 檢查Collection物件是否是null或是size是0
	 * @param Collection
	 * @return
	 */
	public static boolean isNotNullAndNotEmpty(Collection<?> c) {
		try {
			notNullAndNotEmpty(c);
		} catch(NullPointerException npex) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 檢查Array是否是null或是size是0
	 * @param array
	 * @return
	 */
	public static boolean isNotNullAndNotEmpty(Object[] ary) {
		try {
			notNullAndNotEmpty(ary);
		} catch(NullPointerException npex) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 檢查Map的element是否有null
	 * @param Map
	 * @return
	 */
	public static boolean isAllElementNotNull(Map<?, ?> map) {
		try {
			allElementNotNull(map);
		} catch(NullPointerException npex) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 檢查Collection的element是否有null
	 * @param Collection
	 * @return
	 */
	public static boolean isAllElementNotNull(Collection<?> c) {
		try {
			allElementNotNull(c);
		} catch(NullPointerException npex) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 檢查Array的element是否有null
	 * @param array
	 * @return
	 */
	public static boolean isAllElementNotNull(Object[] ary) {
		try {
			allElementNotNull(ary);
		} catch(NullPointerException npex) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 檢查Map的element是否全部null
	 * @param Map
	 * @return
	 */
	public static boolean isAllElementNull(Map<?, ?> map) {
		return isAllElementNull(map.values());
	}
	
	/**
	 * 檢查Collection的element是否全部null
	 * @param Collection
	 * @return
	 */
	public static boolean isAllElementNull(Collection<?> c) {
		Iterator<?> iterator = c.iterator();
		
		while(iterator.hasNext()) {
			if(iterator.next() != null) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 檢查Array的element是否全部null
	 * @param array
	 * @return
	 */
	public static boolean isAllElementNull(Object[] ary) {
		for(int i = 0; i < ary.length; i++) {
			if(ary[i] != null) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 檢查字串是否是"true"(不分大小寫)
	 * @param value
	 * @return
	 */
	public static boolean isTrue(Object obj) {
		try {
			notNull(obj);
			
			return isTrue(obj.toString());
		} catch(NullPointerException npex) {
			return false;
		}
	}
	
	/**
	 * 檢查字串是否是"true"(不分大小寫)
	 * @param value
	 * @return
	 */
	public static boolean isTrue(String value) {
		try {
			notNullAndNotSpace(value);
			
			return value.trim().equalsIgnoreCase("true");
		} catch(NullPointerException npex) {
			return false;
		}
	}
	
	/**
	 * 檢查字串是否是"false"(不分大小寫)
	 * @param value
	 * @return
	 */
	public static boolean isFalse(Object obj) {
		try {
			notNull(obj);
			
			return isFalse(obj.toString());
		} catch(NullPointerException npex) {
			return false;
		}
	}
	
	/**
	 * 檢查字串是否是"false"(不分大小寫)
	 * @param value
	 * @return
	 */
	public static boolean isFalse(String value) {
		try {
			notNullAndNotSpace(value);
			
			return value.trim().equalsIgnoreCase("false");
		} catch(NullPointerException npex) {
			return false;
		}
	}
	
	/**
	 * 檢查字串是否可以parse成Integer
	 * @param value
	 * @return
	 */
	public static boolean isInteger(String value) {
		try {
			notNullAndNotSpace(value);
			
			Integer.parseInt(value);
			
			return true;
		} catch(NullPointerException ex) {
			return false;
		} catch(NumberFormatException ex) {
			return false;
		}
	}
}
