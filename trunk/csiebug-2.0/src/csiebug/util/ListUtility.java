package csiebug.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
//import java.util.Set;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map.Entry;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import csiebug.util.NumberFormatUtility;


/**
 * ArrayList(由LinkedHashMap組成)相關功能
 * @author George_Tsai
 * @version 2009/4/3
 */

public class ListUtility {
	/**
	 * 傳入List與欄位名稱，取得以此欄位排序的List(由小到大)
	 * @param 欲排序的ArrayList
	 * @param 欄位名稱
	 * @return 排序完的ArrayList
	 * @author George_Tsai
	 * @version 2009/3/9
	 */
	public static List<Map<String, String>> sortByName(List<Map<String, String>> originalList, String fieldName) {
		AssertUtility.notNull(originalList);
		AssertUtility.notNullAndNotSpace(fieldName);
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		boolean flag = isCurrencyField(originalList, fieldName);
		
		for (int i = 0; i < originalList.size(); i++) {
			Map<String, String> insertMap = new LinkedHashMap<String, String>(); 
			insertMap.putAll(originalList.get(i));
			
			String strValue = insertMap.get(fieldName).toString();
			
			list = addInsertMap(list, insertMap, flag, fieldName, strValue);
		}

		return list;
	}
	
	private static List<Map<String, String>> addInsertMap(List<Map<String, String>> list, Map<String, String> insertMap, boolean flag, String fieldName, String strValue) {
		AssertUtility.notNull(list);
		AssertUtility.notNull(insertMap);
		AssertUtility.notNullAndNotSpace(fieldName);
		AssertUtility.notNull(strValue);
		
		if (list.size() == 0) {
			list.add(insertMap);
		} else {
			String compareValue = (list.get(list.size() - 1)).get(fieldName).toString();
			if (compareValue != null){
				list = addInsertMapToCorrectPosition(list, insertMap, flag, compareValue, strValue, fieldName);
			}
		}
		
		return list;
	}
	
	private static List<Map<String, String>> addInsertMapToCorrectPosition(List<Map<String, String>> list, Map<String, String> insertMap, boolean flag, String compareValue, String strValue, String fieldName) {
		AssertUtility.notNull(list);
		AssertUtility.notNull(insertMap);
		AssertUtility.notNull(compareValue);
		AssertUtility.notNull(strValue);
		AssertUtility.notNullAndNotSpace(fieldName);
		
		if(flag && !compareValue.trim().equals("") && !NumberFormatUtility.compareCurrency(compareValue, strValue)) {
			list.add(insertMap);
		} else if (!flag && strValue.compareTo(compareValue) >= 0) {
			list.add(insertMap);
		} else {
			int insertIndex = getInsertIndex(list, insertMap, fieldName, flag);
			//list = insertHashMaptoList(list, insertMap, insertIndex);
			list.add(insertIndex, insertMap);
		}
		
		return list;
	}
	
	private static List<Map<String, String>> addInsertMapWithoutCheckSpace(List<Map<String, String>> list, Map<String, String> insertMap, boolean flag, String fieldName, String strValue) {
		AssertUtility.notNull(list);
		AssertUtility.notNull(insertMap);
		AssertUtility.notNullAndNotSpace(fieldName);
		AssertUtility.notNull(strValue);
		
		if (list.size() == 0) {
			list.add(insertMap);
		} else {
			String compareValue = (list.get(list.size() - 1)).get(fieldName).toString();
			if (compareValue != null){
				list = addInsertMapToCorrectPositionWithoutCheckSpace(list, insertMap, flag, compareValue, strValue, fieldName);
			}
		}
		
		return list;
	}
	
	private static List<Map<String, String>> addInsertMapToCorrectPositionWithoutCheckSpace(List<Map<String, String>> list, Map<String, String> insertMap, boolean flag, String compareValue, String strValue, String fieldName) {
		AssertUtility.notNull(list);
		AssertUtility.notNull(insertMap);
		AssertUtility.notNull(compareValue);
		AssertUtility.notNull(strValue);
		AssertUtility.notNullAndNotSpace(fieldName);
		
		if(flag && !compareValue.trim().equals("") && (!strValue.trim().equals("") && !NumberFormatUtility.compareCurrency(compareValue, strValue))) {
			list.add(insertMap);
		} else if (!flag && strValue.compareTo(compareValue) >= 0) {
			list.add(insertMap);
		} else {
			int insertIndex = getInsertIndexWithoutCheckSpace(list, insertMap, fieldName, flag);
			//list = insertHashMaptoList(list, insertMap, insertIndex);
			list.add(insertIndex, insertMap);
		}
		
		return list;
	}
	
	/**
	 * 傳入List與欄位名稱，取得以此欄位排序的List(由小到大)
	 * @param 欲排序的ArrayList
	 * @param 依序排序的欄位名稱
	 * @return 排序完的ArrayList
	 * @author George_Tsai
	 * @version 2009/3/9
	 */
	public static List<Map<String, String>> sortByMultipleName(List<Map<String, String>> originalList, String[] fieldNames) {
		AssertUtility.notNull(originalList);
		AssertUtility.notNull(fieldNames);
		
		List<Map<String, String>> list = sortByName(originalList, fieldNames[0]);
		//先做第一層排序(群組的排序),然後再做每一個群組裡面的排序(第二層排序)
		return sortFirstLevel(list, fieldNames);
	}
	
	//先做第一層排序(群組的排序),然後再做每一個群組裡面的排序(第二層排序)
	private static List<Map<String, String>> sortFirstLevel(List<Map<String, String>> list, String[] fieldNames) {
		AssertUtility.notNull(list);
		AssertUtility.notNull(fieldNames);
		
		List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
		
		String strGroup = "";
		List<Map<String, String>> templist = new ArrayList<Map<String, String>>();
		
		for(int i = 0; i < list.size(); i++) {
			Map<String, String> insertMap = new LinkedHashMap<String, String>(); 
			insertMap.putAll(list.get(i));
			
			String strValue = (String)insertMap.get(fieldNames[0]);
			
			if(strGroup.equals("")) {
				strGroup = strValue;
			}
			
			if(strValue.equals(strGroup)) {
				templist.add(insertMap);
			} else {
				templist = sortSecondLevel(templist, fieldNames);
				
				list2.addAll(templist);
				strGroup = strValue;
				templist = new ArrayList<Map<String, String>>();
				templist.add(insertMap);
			}
		}
		
		templist = sortSecondLevel(templist, fieldNames);
		
		list2.addAll(templist);
		
		return list2;
	}
	
	private static List<Map<String, String>> sortSecondLevel(List<Map<String, String>> templist, String[] fieldNames) {
		AssertUtility.notNull(templist);
		AssertUtility.notNull(fieldNames);
		
		if(fieldNames.length > 1) {
			String[] fieldNames2 = new String[fieldNames.length - 1];
			for(int j = 0; j < fieldNames2.length; j++) {
				fieldNames2[j] = fieldNames[j + 1];
			}
			templist = sortByMultipleName(templist, fieldNames2);
		}
		
		return templist;
	}
	
	/**
	 * 傳入List與欄位名稱，取得以此欄位排序的List(由大到小)
	 * @param 欲排序的ArrayList
	 * @param 欄位名稱
	 * @return 排序完的ArrayList
	 * @author George_Tsai
	 * @version 2008/12/11
	 */
	public static List<Map<String, String>> sortByNameDesc(List<Map<String, String>> originalList, String fieldName) {
		AssertUtility.notNull(originalList);
		AssertUtility.notNullAndNotSpace(fieldName);
		
		return reverseList(sortByName(originalList, fieldName));
	}
	
	/**
	 * 傳入List與欄位名稱，取得以此欄位排序的List(由小到大)
	 * 並且會增加兩個欄位groupId,groupType
	 * @param 欲排序的ArrayList
	 * @param 依序排序的欄位名稱
	 * @return 排序完的ArrayList
	 * @author George_Tsai
	 * @version 2009/3/9
	 */
	public static List<Map<String, String>> sortByMultipleNameGrouping(List<Map<String, String>> originalList, String[] fieldNames) {
		AssertUtility.notNull(originalList);
		AssertUtility.notNull(fieldNames);
		
		List<Map<String, String>> list = sortByNameGrouping(originalList, fieldNames[0]);
		//先做第一層排序(群組的排序),然後再做每一個群組裡面的排序(第二層排序)
		list = sortFirstLevelGrouping(list, fieldNames);
		
		//設定目錄或是leaf
		return setGroupType(list);
	}
	
	private static List<Map<String, String>> setGroupType(List<Map<String, String>> originalList) {
		AssertUtility.notNull(originalList);
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		for (int i = 0; i < originalList.size(); i++) {
			Map<String, String> insertMap = new LinkedHashMap<String, String>(); 
			insertMap.putAll(originalList.get(i));
			
			String groupId = insertMap.get("groupId");
			
			if(hasChild(originalList, groupId)) {
				insertMap.remove("groupType");
				insertMap.put("groupType", "group");
			} else {
				insertMap.remove("groupType");
				insertMap.put("groupType", "data");
			}
			
			list.add(insertMap);
		}

		return list;
	}
	
	private static boolean hasChild(List<Map<String, String>> originalList, String groupId) {
		AssertUtility.notNull(originalList);
		AssertUtility.notNullAndNotSpace(groupId);
		
		boolean flag = false;
		
		for (int i = 0; i < originalList.size(); i++) {
			Map<String, String> insertMap = new LinkedHashMap<String, String>(); 
			insertMap.putAll(originalList.get(i));
			
			if(insertMap.get("groupId").startsWith(groupId + "_")) {
				flag = true;
				break;
			}
		}
		
		return flag;
	}
	
	//排序但加上groupId欄位
	//(grouping在currency的sorting判定,必須忽略空字串)
	private static List<Map<String, String>> sortByNameGrouping(List<Map<String, String>> originalList, String fieldName) {
		AssertUtility.notNull(originalList);
		AssertUtility.notNullAndNotSpace(fieldName);
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		boolean flag = isCurrencyFieldWithoutCheckSpace(originalList, fieldName);
//		boolean flag = isCurrencyField(originalList, fieldName);
		
		for (int i = 0; i < originalList.size(); i++) {
			Map<String, String> insertMap = new LinkedHashMap<String, String>(); 
			insertMap.putAll(originalList.get(i));
			
			String strValue = insertMap.get(fieldName).toString();
			
			if(AssertUtility.isNotNullAndNotSpace(strValue)) {
				if(AssertUtility.isNotNullAndNotSpace(insertMap.get("groupId"))) {
					String strGroupId = insertMap.get("groupId") + "_" + strValue;
					insertMap.remove("groupId");
					insertMap.put("groupId", strGroupId);
				} else {
					insertMap.remove("groupId");
					insertMap.put("groupId", strValue);
				}
			}
			
			list = addInsertMapWithoutCheckSpace(list, insertMap, flag, fieldName, strValue);
		}

		return list;
	}
	
	//先做第一層排序(群組的排序),然後再做每一個群組裡面的排序(第二層排序)
	private static List<Map<String, String>> sortFirstLevelGrouping(List<Map<String, String>> list, String[] fieldNames) {
		AssertUtility.notNull(list);
		AssertUtility.notNull(fieldNames);
		
		List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
		
		String strGroup = "";
		List<Map<String, String>> templist = new ArrayList<Map<String, String>>();
		
		for(int i = 0; i < list.size(); i++) {
			Map<String, String> insertMap = new LinkedHashMap<String, String>(); 
			insertMap.putAll(list.get(i));
			
			String strValue = (String)insertMap.get(fieldNames[0]);
			
			if(strGroup.equals("")) {
				strGroup = strValue;
			}
			
			if(strValue.equals(strGroup)) {
				templist.add(insertMap);
			} else {
				templist = sortSecondLevelGrouping(templist, fieldNames);
				
				list2.addAll(templist);
				strGroup = strValue;
				templist = new ArrayList<Map<String, String>>();
				templist.add(insertMap);
			}
		}
		
		templist = sortSecondLevelGrouping(templist, fieldNames);
				
		list2.addAll(templist);
		
		return list2;
	}
	
	private static List<Map<String, String>> sortSecondLevelGrouping(List<Map<String, String>> templist, String[] fieldNames) {
		AssertUtility.notNull(templist);
		AssertUtility.notNull(fieldNames);
		
		if(fieldNames.length > 1) {
			String[] fieldNames2 = new String[fieldNames.length - 1];
			for(int j = 0; j < fieldNames2.length; j++) {
				fieldNames2[j] = fieldNames[j + 1];
			}
			templist = sortByMultipleNameGrouping(templist, fieldNames2);
		}
		
		return templist;
	}
	
	/**
	 * 傳入List與欄位名稱，取得以此欄位排序的List(由小到大)
	 * 中間會自動插入group資料,並且會增加兩個欄位groupId,groupType
	 * @param 欲排序的ArrayList
	 * @param 依序排序的欄位名稱
	 * @return 排序完的ArrayList
	 * @author George_Tsai
	 * @version 2010/2/12
	 */
	public static List<Map<String, String>> sortByMultipleNameWithInsertGroupData(List<Map<String, String>> originalList, String[] fieldNames) {
		AssertUtility.notNull(originalList);
		AssertUtility.notNull(fieldNames);
		
		//用來插入的groupMap所有欄位都要備妥
		Map<String, String> groupMap = new LinkedHashMap<String, String>();
		Iterator<String> iterator = originalList.get(0).keySet().iterator();
		while(iterator.hasNext()) {
			groupMap.put(iterator.next(), "");
		}
		
		List<Map<String, String>> list = sortByName(originalList, fieldNames[0]);
		
		//先做第一層排序(群組的排序),然後再做每一個群組裡面的排序(第二層排序)
		return sortFirstLevelWithInsertGroupData(list, fieldNames, groupMap, fieldNames);
	}
	
	private static List<Map<String, String>> sortByMultipleNameWithInsertGroupData(List<Map<String, String>> originalList, String[] fieldNames, Map<String, String> groupMap, String[] allFieldNames) {
		AssertUtility.notNull(originalList);
		AssertUtility.notNull(fieldNames);
		AssertUtility.notNull(groupMap);
		
		List<Map<String, String>> list = sortByName(originalList, fieldNames[0]);
		
		//先做第一層排序(群組的排序),然後再做每一個群組裡面的排序(第二層排序)
		return sortFirstLevelWithInsertGroupData(list, fieldNames, groupMap, allFieldNames);
	}
	
	//先做第一層排序(群組的排序),然後再做每一個群組裡面的排序(第二層排序)
	private static List<Map<String, String>> sortFirstLevelWithInsertGroupData(List<Map<String, String>> list, String[] fieldNames, Map<String, String> groupMap, String[] allFieldNames) {
		AssertUtility.notNull(list);
		AssertUtility.notNull(fieldNames);
		AssertUtility.notNull(groupMap);
		
		List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
		
		String strGroupValue = "";
		List<Map<String, String>> templist = new ArrayList<Map<String, String>>();
		
		for(int i = 0; i < list.size(); i++) {
			Map<String, String> insertMap = new LinkedHashMap<String, String>(); 
			insertMap.putAll(list.get(i));
			
			//初始的groupId都是空的,所以必須設成正確的groupId
			if(!AssertUtility.isNotNullAndNotSpace(insertMap.get("groupId"))) {
				insertMap.remove("groupId");
				StringBuffer groupId = new StringBuffer();
				for(int j = 0; j < allFieldNames.length; j++) {
					if(AssertUtility.isNotNullAndNotSpace(insertMap.get(allFieldNames[j]))) {
						if(!groupId.toString().equals("")) {
							groupId.append("_");
						}
						groupId.append(insertMap.get(allFieldNames[j]));
					}
				}
				insertMap.put("groupId", groupId.toString() + "_" + i);
				insertMap.remove("groupType");
				insertMap.put("groupType", "data");
			}
			
			String strValue = (String)insertMap.get(fieldNames[0]);
			
			if(strGroupValue.equals("")) {
				strGroupValue = strValue;
				
				//插入groupData
				groupMap.remove(fieldNames[0]);
				groupMap.put(fieldNames[0], strGroupValue);
				groupMap.remove("groupId");
				StringBuffer groupId = new StringBuffer();
				for(int j = 0; j < allFieldNames.length; j++) {
					if(AssertUtility.isNotNullAndNotSpace(groupMap.get(allFieldNames[j]))) {
						if(!groupId.toString().equals("")) {
							groupId.append("_");
						}
						groupId.append(groupMap.get(allFieldNames[j]));
					}
				}
				groupMap.put("groupId", groupId.toString());
				groupMap.remove("groupType");
				groupMap.put("groupType", "group");
				
				Map<String, String> insertGroupMap = new LinkedHashMap<String, String>();
				Iterator<Entry<String, String>> iterator = groupMap.entrySet().iterator();
				while(iterator.hasNext()) {
					Entry<String, String> entry = iterator.next();
					insertGroupMap.put(entry.getKey(), entry.getValue());
				}
				list2.add(insertGroupMap);
			}
			
			if(strValue.equals(strGroupValue)) {
				templist.add(insertMap);
			} else {
				templist = sortSecondLevelWithInsertGroupData(templist, fieldNames, groupMap, allFieldNames);
				
				list2.addAll(templist);
				strGroupValue = strValue;
				
				//插入groupData
				groupMap.remove(fieldNames[0]);
				groupMap.put(fieldNames[0], strGroupValue);
				groupMap.remove("groupId");
				StringBuffer groupId = new StringBuffer();
				for(int j = 0; j < allFieldNames.length; j++) {
					if(AssertUtility.isNotNullAndNotSpace(groupMap.get(allFieldNames[j]))) {
						if(!groupId.toString().equals("")) {
							groupId.append("_");
						}
						groupId.append(groupMap.get(allFieldNames[j]));
					}
				}
				groupMap.put("groupId", groupId.toString());
				groupMap.remove("groupType");
				groupMap.put("groupType", "group");
				
				Map<String, String> insertGroupMap = new LinkedHashMap<String, String>();
				Iterator<Entry<String, String>> iterator = groupMap.entrySet().iterator();
				while(iterator.hasNext()) {
					Entry<String, String> entry = iterator.next();
					insertGroupMap.put(entry.getKey(), entry.getValue());
				}
				list2.add(insertGroupMap);
				
				templist = new ArrayList<Map<String, String>>();
				templist.add(insertMap);
			}
		}
		
		templist = sortSecondLevelWithInsertGroupData(templist, fieldNames, groupMap, allFieldNames);
		
		list2.addAll(templist);
		
		return list2;
	}
	
	private static List<Map<String, String>> sortSecondLevelWithInsertGroupData(List<Map<String, String>> templist, String[] fieldNames, Map<String, String> groupMap, String[] allFieldNames) {
		AssertUtility.notNull(templist);
		AssertUtility.notNull(fieldNames);
		AssertUtility.notNull(groupMap);
		
		if(fieldNames.length > 1) {
			String[] fieldNames2 = new String[fieldNames.length - 1];
			for(int j = 0; j < fieldNames2.length; j++) {
				fieldNames2[j] = fieldNames[j + 1];
			}
			templist = sortByMultipleNameWithInsertGroupData(templist, fieldNames2, groupMap, allFieldNames);
			groupMap.remove(fieldNames[1]);
			groupMap.put(fieldNames[1], "");
		}
		
		return templist;
	}
	
	/**
	 * 傳入List與欄位名稱，取得以此欄位排序的List(由大到小)
	 * @param 欲排序的ArrayList
	 * @param 依序排序的欄位名稱
	 * @return 排序完的ArrayList
	 * @author George_Tsai
	 * @version 2009/3/9
	 */
	public static List<Map<String, String>> sortByMultipleNameDesc(List<Map<String, String>> originalList, String[] fieldNames) {
		AssertUtility.notNull(originalList);
		AssertUtility.notNull(fieldNames);
		
		List<Map<String, String>> list = sortByNameDesc(originalList, fieldNames[0]);
		
		//先做第一層排序(群組的排序),然後再做每一個群組裡面的排序(第二層排序)
		return sortFirstLevelDesc(list, fieldNames);
	}
	
	//先做第一層排序(群組的排序),然後再做每一個群組裡面的排序(第二層排序)
	private static List<Map<String, String>> sortFirstLevelDesc(List<Map<String, String>> list, String[] fieldNames) {
		AssertUtility.notNull(list);
		AssertUtility.notNull(fieldNames);
		
		List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
		
		String strGroup = "";
		List<Map<String, String>> templist = new ArrayList<Map<String, String>>();
		
		for(int i = 0; i < list.size(); i++) {
			Map<String, String> insertMap = new LinkedHashMap<String, String>(); 
			insertMap.putAll(list.get(i));
			
			String strValue = (String)insertMap.get(fieldNames[0]);
			
			if(strGroup.equals("")) {
				strGroup = strValue;
			}
			
			if(strValue.equals(strGroup)) {
				templist.add(insertMap);
			} else {
				templist = sortSecoundLevelDesc(templist, fieldNames);
				
				list2.addAll(templist);
				strGroup = strValue;
				templist = new ArrayList<Map<String, String>>();
				templist.add(insertMap);
			}
		}
		
		templist = sortSecoundLevelDesc(templist, fieldNames);
		
		list2.addAll(templist);
		
		return list2;
	}
	
	private static List<Map<String, String>> sortSecoundLevelDesc(List<Map<String, String>> templist, String[] fieldNames) {
		AssertUtility.notNull(templist);
		AssertUtility.notNull(fieldNames);
		
		if(fieldNames.length > 1) {
			String[] fieldNames2 = new String[fieldNames.length - 1];
			for(int j = 0; j < fieldNames2.length; j++) {
				fieldNames2[j] = fieldNames[j + 1];
			}
			templist = sortByMultipleNameDesc(templist, fieldNames2);
		}
		
		return templist;
	}
	
	/**
	 * 將原本的list倒序
	 * @param 欲排序的ArrayList
	 * @return 排序完的ArrayList
	 * @author George_Tsai
	 * @version 2008/12/11
	 */
	public static List<Map<String, String>> reverseList(List<Map<String, String>> originalList) {
		AssertUtility.notNull(originalList);
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		for(int i = 0; i < originalList.size(); i++) {
			list.add(originalList.get(originalList.size() - 1 - i));
		}

		return list;
	}
	
	/**
	 * 檢查list的某個欄位是不是currency
	 * @param 目標ArrayList
	 * @param 欲檢查的欄位名稱
	 * @author George_Tsai
	 * @version 2008/6/20
	 */
	public static boolean isCurrencyField(List<Map<String, String>> targetList, String fieldName) {
		AssertUtility.notNull(targetList);
		AssertUtility.notNullAndNotSpace(fieldName);
		
		boolean flag = true;
		
		for(int i = 0; i < targetList.size(); i++) {
			Map<String, String> map = targetList.get(i);
			
			String strValue = map.get(fieldName).toString();
			
			boolean tmpFlag = NumberFormatUtility.isValidCurrency(strValue);
			
			if(!tmpFlag) {
				flag = false;
				break;
			}
		}
		
		return flag;
	}
	
	/**
	 * 檢查list的某個欄位是不是currency
	 * (grouping在currency的sorting判定,必須忽略空字串)
	 * @param 目標ArrayList
	 * @param 欲檢查的欄位名稱
	 * @author George_Tsai
	 * @version 2010/9/20
	 */
	public static boolean isCurrencyFieldWithoutCheckSpace(List<Map<String, String>> targetList, String fieldName) {
		AssertUtility.notNull(targetList);
		AssertUtility.notNullAndNotSpace(fieldName);
		
		boolean flag = true;
		
		for(int i = 0; i < targetList.size(); i++) {
			Map<String, String> map = targetList.get(i);
			
			String strValue = map.get(fieldName).toString();
			
			if(!strValue.trim().equals("")) {
				boolean tmpFlag = NumberFormatUtility.isValidCurrency(strValue);
				
				if(!tmpFlag) {
					flag = false;
					break;
				}
			}
		}
		
		return flag;
	}
	
	/**
	 * 取得欄位加總
	 * @param 目標ArrayList
	 * @param 欲檢查的欄位名稱
	 * @author George_Tsai
	 * @version 2008/8/21
	 * @throws ListException 
	 */
	public static String getSumOfField(List<Map<String, String>> targetList, String fieldName) throws ListException {
		AssertUtility.notNull(targetList);
		AssertUtility.notNullAndNotSpace(fieldName);
		
		BigDecimal sum = BigDecimal.ZERO;
		
		if(isCurrencyField(targetList, fieldName)) {
			for(int i = 0; i < targetList.size(); i++) {
				Map<String, String> map = new LinkedHashMap<String, String>();
				map.putAll(targetList.get(i));
				
				if(!map.get(fieldName).toString().trim().equals("")) {
					sum = sum.add(new BigDecimal(map.get(fieldName).toString().replaceAll(",", "")));
				}
			}
		} else {
			throw new ListException("Not currency field!!");
		}
		
		return NumberFormatUtility.getCurrency(sum.toBigInteger().toString());
	}
	
	/**
	 * 取得插入到一個排序完成的list的插入位置
	 * @param 目標ArrayList
	 * @param 欲插入的LinkedHashMap
	 * @param 排序欄位名稱
	 * @param 此欄位是否是currency
	 * @return 插入位置
	 * @author George_Tsai
	 * @version 2008/6/22
	 */
	private static int getInsertIndex(List<Map<String, String>> targetList, Map<String, String> map, String fieldName, boolean isCurrency) {
		AssertUtility.notNull(targetList);
		AssertUtility.notNull(map);
		AssertUtility.notNullAndNotSpace(fieldName);
		
		int index = 0;

		String strValue = (String) map.get(fieldName);

		for (int i = targetList.size() - 1; i >= 0; i--) {
			String compareValue = (targetList.get(i)).get(fieldName);
			
			if(isCurrency && !NumberFormatUtility.compareCurrency(compareValue, strValue)) {
				index = i + 1;
				break;
			} else if (!isCurrency && strValue.compareTo(compareValue) >= 0) {
				index = i + 1;
				break;
			}
		}
		
		return index;
	}
	
	/**
	 * 取得插入到一個排序完成的list的插入位置
	 * (grouping在currency的sorting判定,必須忽略空字串)
	 * @param 目標ArrayList
	 * @param 欲插入的LinkedHashMap
	 * @param 排序欄位名稱
	 * @param 此欄位是否是currency
	 * @return 插入位置
	 * @author George_Tsai
	 * @version 2010/9/20
	 */
	private static int getInsertIndexWithoutCheckSpace(List<Map<String, String>> targetList, Map<String, String> map, String fieldName, boolean isCurrency) {
		AssertUtility.notNull(targetList);
		AssertUtility.notNull(map);
		AssertUtility.notNullAndNotSpace(fieldName);
		
		int index = 0;

		String strValue = (String) map.get(fieldName);

		for (int i = targetList.size() - 1; i >= 0; i--) {
			String compareValue = (targetList.get(i)).get(fieldName);
			
			if(isCurrency) {
				index = i + 1;
				break;
			} else if (!isCurrency && strValue.compareTo(compareValue) >= 0) {
				index = i + 1;
				break;
			}
		}
		
		return index;
	}
	
	/**
	 * 插入LinkedHashMap到ArrayList的指定位置
	 * java.util.ArrayList的add已有相同功能,所以廢棄不用
	 * @param 目標ArrayList
	 * @param 欲插入的LinkedHashMap
	 * @param 指定位置
	 * @return 插入完成的ArrayList
	 * @author George_Tsai
	 * @version 2008/6/11
	 */
	@Deprecated
	public static List<Map<String, String>> insertHashMaptoList(List<Map<String, String>> targetList, Map<String, String> insertMap, int insertIndex) {
		AssertUtility.notNull(targetList);
		AssertUtility.notNull(insertMap);
		
		List<Map<String, String>> finalList = new ArrayList<Map<String, String>>();

		if (targetList.size() == 0) {
			finalList.add(insertMap);
		} else {
			if(targetList.size() > insertIndex) {
				for (int i = 0; i < targetList.size(); i++) {
					if (i == insertIndex) {
						finalList.add(insertMap);
					}
					finalList.add(targetList.get(i));
				}
			} else {
				finalList.addAll(targetList);
				finalList.add(insertMap);
			}
		}

		return finalList;
	}
	
	/**
	 * 根據欄位名稱與值，取得此值相同的ArrayList
	 * @param 目標ArrayList
	 * @param 欄位名稱
	 * @param 欄位值
	 * @return ArrayList
	 * @author George_Tsai
	 * @version 2008/5/20
	 */
	public static List<Map<String, String>> getSubListByName(List<Map<String, String>> originalList, String fieldName, String fieldValue) {
		AssertUtility.notNull(originalList);
		AssertUtility.notNullAndNotSpace(fieldName);
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		for (int i = 0; i < originalList.size(); i++) {
			Map<String, String> insertMap = new LinkedHashMap<String, String>(); 
			insertMap.putAll(originalList.get(i));
			
			String strValue = (String) insertMap.get(fieldName);

			if(strValue.equals(fieldValue)) {
				list.add(insertMap);
			}
		}

		return list;
	}
	
	/**
	 * 根據欄位名稱與值，取得有這些值的ArrayList
	 * @param 目標ArrayList
	 * @param 欄位名稱
	 * @param 欄位值
	 * @return ArrayList
	 * @author George_Tsai
	 * @version 2008/8/20
	 */
	public static List<Map<String, String>> getSubListByName(List<Map<String, String>> originalList, String fieldName, String[] fieldValue) {
		AssertUtility.notNull(originalList);
		AssertUtility.notNullAndNotSpace(fieldName);
		AssertUtility.notNull(fieldValue);
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		for (int i = 0; i < originalList.size(); i++) {
			Map<String, String> insertMap = new LinkedHashMap<String, String>(); 
			insertMap.putAll(originalList.get(i));
			
			String strValue = (String) insertMap.get(fieldName);
			
			for(int j = 0; j < fieldValue.length; j++) {
				if(strValue.equals(fieldValue[j])) {
					list.add(insertMap);
					break;
				}
			}
		}

		return list;
	}
	
	/**
	 * 根據欄位名稱與值，取得不是此值的ArrayList
	 * @param 目標ArrayList
	 * @param 欄位名稱
	 * @param 欄位值
	 * @return ArrayList
	 * @author George_Tsai
	 * @version 2010/10/11
	 */
	public static List<Map<String, String>> getSubListByNameExcludeValue(List<Map<String, String>> originalList, String fieldName, String fieldValue) {
		AssertUtility.notNull(originalList);
		AssertUtility.notNullAndNotSpace(fieldName);
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		for (int i = 0; i < originalList.size(); i++) {
			Map<String, String> insertMap = new LinkedHashMap<String, String>(); 
			insertMap.putAll(originalList.get(i));
			
			String strValue = (String) insertMap.get(fieldName);

			if(!strValue.equals(fieldValue)) {
				list.add(insertMap);
			}
		}

		return list;
	}
	
	/**
	 * 根據欄位名稱與值，取得不是這些值的ArrayList
	 * @param 目標ArrayList
	 * @param 欄位名稱
	 * @param 欄位值
	 * @return ArrayList
	 * @author George_Tsai
	 * @version 2010/10/11
	 */
	public static List<Map<String, String>> getSubListByNameExcludeValue(List<Map<String, String>> originalList, String fieldName, String[] fieldValue) {
		AssertUtility.notNull(originalList);
		AssertUtility.notNullAndNotSpace(fieldName);
		AssertUtility.notNull(fieldValue);
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		for (int i = 0; i < originalList.size(); i++) {
			Map<String, String> insertMap = new LinkedHashMap<String, String>(); 
			insertMap.putAll(originalList.get(i));
			
			String strValue = (String) insertMap.get(fieldName);
			
			boolean addFlag = true;
			for(int j = 0; j < fieldValue.length; j++) {
				if(strValue.equals(fieldValue[j])) {
					addFlag = false;
					break;
				}
			}
			
			if(addFlag) {
				list.add(insertMap);
			}
		}

		return list;
	}
	
	/**
	 * 將List某個欄位名稱重新命名
	 * @param originalList
	 * @param oldName
	 * @param newName
	 * @return
	 */
	public static List<Map<String, String>> renameField(List<Map<String, String>> originalList, String oldName, String newName) {
		AssertUtility.notNull(originalList);
		AssertUtility.notNullAndNotSpace(oldName);
		AssertUtility.notNullAndNotSpace(newName);
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		for(int i = 0; i < originalList.size(); i++) {
			Map<String, String> map = new LinkedHashMap<String, String>();
			Map<String, String> tmpMap = new LinkedHashMap<String, String>();
			tmpMap.putAll(originalList.get(i));
			
			for(int j = 0; j < tmpMap.size(); j++) {
				if(tmpMap.keySet().toArray()[j].toString().equals(oldName)) {
					map.put(newName, tmpMap.get(tmpMap.keySet().toArray()[j].toString()));
				} else {
					map.put(tmpMap.keySet().toArray()[j].toString(), tmpMap.get(tmpMap.keySet().toArray()[j].toString()));
				}
			}
			
			list.add(map);
		}
		
		return list;
	}
	
	/**
	 * 根據欄位名稱與值，刪除此值相同的資料
	 * @param 目標ArrayList
	 * @param 欄位名稱
	 * @param 欄位值
	 * @return ArrayList
	 * @author George_Tsai
	 * @version 2008/8/11
	 */
	public static List<Map<String, String>> removeByName(List<Map<String, String>> originalList, String fieldName, String fieldValue) {
		AssertUtility.notNull(originalList);
		AssertUtility.notNullAndNotSpace(fieldName);
		AssertUtility.notNull(fieldValue);
		
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		list.addAll(originalList);
		
		for (int i = originalList.size() - 1; i >= 0; i--) {
			Map<String, String> removeMap = list.get(i); 
			
			String strValue = (String) removeMap.get(fieldName);
			
			if(strValue.equals(fieldValue)) {
				list.remove(i);
			}
		}

		return list;
	}
	
	/**
	 * 複製欄位值到新的欄位去
	 * @param originalList
	 * @param fieldName
	 * @param newFieldName
	 * @return
	 */
	public static List<Map<String, String>> copyField(List<Map<String, String>> originalList, String fieldName, String newFieldName) {
		AssertUtility.notNull(originalList);
		AssertUtility.notNullAndNotSpace(fieldName);
		AssertUtility.notNullAndNotSpace(newFieldName);
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		for (int i = 0; i < originalList.size(); i++) {
			Map<String, String> insertMap = new LinkedHashMap<String, String>(); 
			insertMap.putAll(originalList.get(i));
			
			String strValue = (String) insertMap.get(fieldName);
			insertMap.put(newFieldName, strValue);
			
			list.add(insertMap);
		}

		return list;
	}
	
	/**
	 * 置換某個欄位的值
	 * @param 目標ArrayList
	 * @param 欄位名稱
	 * @param 交換值的map(key:原本的值;value:替換的值)
	 * @return ArrayList
	 * @author George_Tsai
	 * @version 2008/9/4
	 */
	public static List<Map<String, String>> replaceField(List<Map<String, String>> originalList, String fieldName, Map<String, String> map) {
		AssertUtility.notNull(originalList);
		AssertUtility.notNullAndNotSpace(fieldName);
		AssertUtility.notNull(map);
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		for (int i = 0; i < originalList.size(); i++) {
			Map<String, String> insertMap = new LinkedHashMap<String, String>(); 
			insertMap.putAll(originalList.get(i));
			
			String strValue = (String) insertMap.get(fieldName);
			
			if(map.get(strValue) != null) {
//				insertMap.remove(fieldName);
				//不處理額外變數
//				insertMap.put(fieldName, map.get(strValue).toString());
				//以下是允許替換欄位可以有變數(帶入其他欄位的值)
				insertMap.put(fieldName, replaceVariable(insertMap, map.get(strValue).toString()));
			}
			
			list.add(insertMap);
		}

		return list;
	}
	
	/**
	 * 置換某個欄位的換行符號成HTML的BR
	 * @param 目標ArrayList
	 * @param 欄位名稱
	 * @return ArrayList
	 * @author George_Tsai
	 * @version 2009/3/20
	 */
	public static List<Map<String, String>> transNewLinetoBR(List<Map<String, String>> originalList, String fieldName) {
		AssertUtility.notNull(originalList);
		AssertUtility.notNullAndNotSpace(fieldName);
		
		return transString(originalList, fieldName, "\n", "<br>");
	}
	
	/**
	 * 置換某個欄位的HTML的BR成換行符號
	 * @param 目標ArrayList
	 * @param 欄位名稱
	 * @return ArrayList
	 * @author George_Tsai
	 * @version 2009/3/20
	 */
	public static List<Map<String, String>> transBRtoNewLine(List<Map<String, String>> originalList, String fieldName) {
		AssertUtility.notNull(originalList);
		AssertUtility.notNullAndNotSpace(fieldName);
		
		return transString(originalList, fieldName, "<br>", "\n");
	}
	
	/**
	 * 將NULL欄位換成空字串
	 * @param 目標ArrayList
	 * @param 欄位名稱
	 * @return ArrayList
	 * @author George_Tsai
	 * @version 2008/9/4
	 */
	public static List<Map<String, String>> replaceNulltoSpace(List<Map<String, String>> originalList, String fieldName) {
		AssertUtility.notNull(originalList);
		AssertUtility.notNullAndNotSpace(fieldName);
		
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(null, "");

		return replaceField(originalList, fieldName, map);
	}
	
	/**
	 * 將NULL欄位換成0
	 * @param 目標ArrayList
	 * @param 欄位名稱
	 * @return ArrayList
	 * @author George_Tsai
	 * @version 2008/9/4
	 */
	public static List<Map<String, String>> replaceNulltoZero(List<Map<String, String>> originalList, String fieldName) {
		AssertUtility.notNull(originalList);
		AssertUtility.notNullAndNotSpace(fieldName);
		
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(null, "0");

		return replaceField(originalList, fieldName, map);
	}
	
	/**
	 * 將某個欄位的值轉換成currency格式
	 * @param 目標ArrayList
	 * @param 欄位名稱
	 * @return ArrayList
	 * @author George_Tsai
	 * @version 2008/6/12
	 */
	public static List<Map<String, String>> transCurrency(List<Map<String, String>> originalList, String fieldName) {
		AssertUtility.notNull(originalList);
		AssertUtility.notNullAndNotSpace(fieldName);
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		if(isCurrencyField(originalList, fieldName)) {
			for (int i = 0; i < originalList.size(); i++) {
				Map<String, String> insertMap = new LinkedHashMap<String, String>(); 
				insertMap.putAll(originalList.get(i));
				
				String strValue = (String) insertMap.get(fieldName);
				
				insertMap.remove(fieldName);
				insertMap.put(fieldName, NumberFormatUtility.getCurrency(strValue));
				
				list.add(insertMap);
			}
		} else {
			list.addAll(originalList);
		}

		return list;
	}
	
	/**
	 * 將某個欄位裡面的regex換成replacement
	 * @param 目標ArrayList
	 * @param 欄位名稱
	 * @param regex
	 * @param replacement
	 * @return ArrayList
	 * @author George_Tsai
	 * @version 2008/9/4
	 */
	public static List<Map<String, String>> transString(List<Map<String, String>> originalList, String fieldName, String regex, String replacement) {
		AssertUtility.notNull(originalList);
		AssertUtility.notNullAndNotSpace(fieldName);
		AssertUtility.notNull(regex);
		AssertUtility.notNull(replacement);
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		for (int i = 0; i < originalList.size(); i++) {
			Map<String, String> insertMap = new LinkedHashMap<String, String>(); 
			insertMap.putAll(originalList.get(i));
			
			String strValue = "" + insertMap.get(fieldName);
			
//			insertMap.remove(fieldName);
			//不處理額外變數
//			insertMap.put(fieldName, strValue.replaceAll(regex, replacement));
			//以下是允許替換欄位可以有變數(帶入其他欄位的值)
			insertMap.put(fieldName, strValue.replaceAll(replaceVariable(insertMap, regex), replaceVariable(insertMap, replacement)));
			
			list.add(insertMap);
		}
		
		return list;
	}
	
	//以下三個function都不採用${}的方式傳遞變數
	//因為struts2會將這種變數吃掉變成空白
	//所以都採用()的方式傳遞參數
	/**
	 * 將帶有變數的字串,置換為map中對應的值
	 * @param 含有資料的map物件
	 * @param 帶有變數的字串
	 * @author George_Tsai
	 * @return 變數置換後的字串
	 * @version 2009/1/20
	 */
	public static String replaceVariable(Map<String, String> map, String originalString) {
		AssertUtility.notNull(map);
		AssertUtility.notNull(originalString);
		
		//不使用replaceAll是因為replaceAll不吃$
		String strValue = originalString;
		String tmpValue = originalString;
		
		List<String> param = new ArrayList<String>();
		while(tmpValue.indexOf("(var.") != -1 && tmpValue.lastIndexOf(")") + 1 <= tmpValue.length()) {
			int from = tmpValue.indexOf("(var.");
			int to = tmpValue.substring(from, tmpValue.length()).indexOf(")");
			param.add(tmpValue.substring(from, from + to + 1));
			tmpValue = tmpValue.substring(from + to + 1, tmpValue.length());
		}
		
		for(int j = 0; j < param.size(); j++) {
			String regex = param.get(j).toString();
			String replacement;
			
			//map有才做替換
			if(map.get(regex.substring(5, regex.indexOf(")")).trim()) != null) {
				replacement = map.get(regex.substring(5, regex.indexOf(")")).trim()).toString();
				
				while(strValue.indexOf(regex) != -1) {
					strValue = strValue.substring(0, strValue.indexOf(regex)) + replacement + strValue.substring(strValue.indexOf(regex) + regex.length(), strValue.length());
				}
			}
		}
		
		return strValue;
	}
	
	/**
	 * 將帶有Session變數的字串,置換為session中對應的值
	 * @param 帶有變數的字串
	 * @author George_Tsai
	 * @return 變數置換後的字串
	 * @version 2009/4/3
	 */
	public static String replaceSessionVariable(String originalString) {
		AssertUtility.notNull(originalString);
		
		//不使用replaceAll是因為replaceAll不吃$
		String strValue = originalString;
		String tmpValue = originalString;
		List<String> param = new ArrayList<String>();
		while(tmpValue.indexOf("(session.") != -1 && tmpValue.lastIndexOf(")") + 1 <= tmpValue.length()) {
			int from = tmpValue.indexOf("(session.");
			int to = tmpValue.substring(from, tmpValue.length()).indexOf(")");
			param.add(tmpValue.substring(from, from + to + 1));
			tmpValue = tmpValue.substring(from + to + 1, tmpValue.length());
		}
		
		WebUtility webutil = new WebUtility();
		for(int j = 0; j < param.size(); j++) {
			String regex = param.get(j).toString();
			String replacement;
			
			//session有才做替換
			if(webutil.getSessionAttribute(regex.substring(9, regex.indexOf(")")).trim()) != null) {
				replacement = webutil.getSessionAttribute(regex.substring(9, regex.indexOf(")")).trim()).toString();
				
				while(strValue.indexOf(regex) != -1) {
					strValue = strValue.substring(0, strValue.indexOf(regex)) + replacement + strValue.substring(strValue.indexOf(regex) + regex.length(), strValue.length());
				}
			}
		}
		
		return strValue;
	}
	
	/**
	 * 將帶有Request變數的字串,置換為request中對應的值
	 * @param 帶有變數的字串
	 * @author George_Tsai
	 * @return 變數置換後的字串
	 * @version 2009/4/3
	 * @throws NamingException 
	 */
	public static String replaceRequestVariable(String originalString) throws NamingException {
		AssertUtility.notNull(originalString);
		
		//不使用replaceAll是因為replaceAll不吃$
		String strValue = originalString;
		String tmpValue = originalString;
		List<String> param = new ArrayList<String>();
		
		while(tmpValue.indexOf("(request.") != -1 && tmpValue.lastIndexOf(")") + 1 <= tmpValue.length()) {
			int from = tmpValue.indexOf("(request.");
			int to = tmpValue.substring(from, tmpValue.length()).indexOf(")");
			param.add(tmpValue.substring(from, from + to + 1));
			tmpValue = tmpValue.substring(from + to + 1, tmpValue.length());
		}
		
		WebUtility webutil = new WebUtility();
		for(int j = 0; j < param.size(); j++) {
			String regex = param.get(j).toString();
			String replacement;
			
			replacement = webutil.getRequestValue(regex.substring(9, regex.indexOf(")")).trim()).toString();
			
			while(strValue.indexOf(regex) != -1) {
				strValue = strValue.substring(0, strValue.indexOf(regex)) + replacement + strValue.substring(strValue.indexOf(regex) + regex.length(), strValue.length());
			}
		}
		
		return strValue;
	}
	
	/**
	 * 將兩個ArrayList(size一致)依順序合併
	 * @param ArrayList1(主要list)
	 * @param ArrayList2
	 * @return ArrayList(含有ArrayList1與ArrayList2的所有欄位)
	 * @author George_Tsai
	 * @version 2008/6/6
	 * @throws ListException 
	 */
	public static List<Map<String, String>> join(List<Map<String, String>> list1, List<Map<String, String>> list2) throws ListException {
		AssertUtility.notNull(list1);
		AssertUtility.notNull(list2);
		
		if(list1.size() == list2.size()) {
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			
			for(int i = 0; i < list1.size(); i++) {
				Map<String, String> map = new LinkedHashMap<String, String>();
				Map<String, String> map1 = list1.get(i);
				Map<String, String> map2 = list2.get(i);
				
				map.putAll(map1);
				
				for(int j = 0; j < map2.keySet().size(); j++) {
					if(map.get(map2.keySet().toArray()[j]) == null) {
						map.put((String)map2.keySet().toArray()[j], map2.get(map2.keySet().toArray()[j]));
					}
				}
				
				list.add(map);
			}
			
			return list;
		} else {
			throw new ListException("Can not join!! Size not match!!");
		}
	}
	
	/**
	 * 將兩個ArrayList合併(類似sql的inner join)
	 * @param ArrayList1
	 * @param ArrayList2
	 * @param keys(欲join所要比對的key欄位)
	 * @return ArrayList(含有ArrayList1與ArrayList2的所有欄位)
	 * @author George_Tsai
	 * @version 2008/8/12
	 * @throws ListException 
	 */
	public static List<Map<String, String>> innerJoin(List<Map<String, String>> list1, List<Map<String, String>> list2, String[] keys) throws ListException {
		AssertUtility.notNull(list1);
		AssertUtility.notNull(list2);
		AssertUtility.notNull(keys);
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		for(int i = 0; i < list1.size(); i++) {
			Map<String, String> map = new LinkedHashMap<String, String>(); 
			map.putAll(list1.get(i));
			
			Map<String, String> map2 = new LinkedHashMap<String, String>();
			
			boolean addFlag = true;
			
			for(int j = 0; j < list2.size(); j++) {
				map2.putAll(list2.get(j));
				addFlag = true;
				
				for(int k = 0; k < keys.length; k++) {
					if(map.get(keys[k]) != null && map2.get(keys[k]) != null) {
						if(!map.get(keys[k]).toString().equals(map2.get(keys[k]).toString())) {
							addFlag = false;
							break;
						}
					} else {
						throw new ListException("Can not join!! Keys are not match!!");
					}
				}
				
				if(addFlag) {
					for(int k = 0; k < map2.keySet().size(); k++) {
						if(map.get("" + map2.keySet().toArray()[k]) == null) {
							map.put("" + map2.keySet().toArray()[k], map2.get("" + map2.keySet().toArray()[k]));
						}
					}
						
					list.add(map);
				}
			}
		}
			
		return list;
	}
	
	/**
	 * 將兩個ArrayList合併(類似sql的left join)
	 * 若合併失敗則傳回ArrayList1
	 * @param ArrayList1
	 * @param ArrayList2
	 * @param keys(欲join所要比對的key欄位)
	 * @return ArrayList(含有ArrayList1與ArrayList2的所有欄位)
	 * @author George_Tsai
	 * @version 2009/3/9
	 * @throws ListException 
	 */
	public static List<Map<String, String>> leftJoin(List<Map<String, String>> list1, List<Map<String, String>> list2, String[] keys) throws ListException {
		AssertUtility.notNull(list1);
		AssertUtility.notNull(list2);
		AssertUtility.notNull(keys);
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		for(int i = 0; i < list1.size(); i++) {
			Map<String, String> map = new LinkedHashMap<String, String>(); 
			map.putAll(list1.get(i));
			
			Map<String, String> map2 = new LinkedHashMap<String, String>();
			
			boolean addFlag = true;
			
			for(int j = 0; j < list2.size(); j++) {
				map2.putAll(list2.get(j));
				addFlag = true;
				
				for(int k = 0; k < keys.length; k++) {
					if(map.get(keys[k]) != null && map2.get(keys[k]) != null) {
						if(!map.get(keys[k]).toString().equals(map2.get(keys[k]).toString())) {
							addFlag = false;
							break;
						}
					} else {
						throw new ListException("Can not join!! Keys are not match!!");
					}
				}
				
				if(addFlag) {
					break;
				}
			}
			
			if(addFlag) {
				for(int k = 0; k < map2.keySet().size(); k++) {
					if(map.get("" + map2.keySet().toArray()[k]) == null) {
						map.put("" + map2.keySet().toArray()[k], map2.get("" + map2.keySet().toArray()[k]));
					}
				}
				
				list.add(map);
			} else {
				for(int k = 0; k < map2.keySet().size(); k++) {
					if(map.get("" + map2.keySet().toArray()[k]) == null) {
						map.put("" + map2.keySet().toArray()[k], null);
					}
				}
				
				list.add(map);
			}
		}
			
		return list;
	}
	
	/**
	 * 將兩個ArrayList合併(類似sql的right join)
	 * 若合併失敗則傳回ArrayList1
	 * @param ArrayList1
	 * @param ArrayList2
	 * @param keys(欲join所要比對的key欄位)
	 * @return ArrayList(含有ArrayList1與ArrayList2的所有欄位)
	 * @author George_Tsai
	 * @version 2009/9/19
	 * @throws ListException 
	 */
	public static List<Map<String, String>> rightJoin(List<Map<String, String>> list1, List<Map<String, String>> list2, String[] keys) throws ListException {
		AssertUtility.notNull(list1);
		AssertUtility.notNull(list2);
		AssertUtility.notNull(keys);
		
		return leftJoin(list2, list1, keys);
	}
	
	/**
	 * 將兩個ArrayList合併(類似sql的full outer join)(left join與right join的聯集)
	 * @param ArrayList1
	 * @param ArrayList2
	 * @param keys(欲join所要比對的key欄位)
	 * @return ArrayList(含有ArrayList1與ArrayList2的所有欄位)
	 * @author George_Tsai
	 * @version 2009/9/19
	 * @throws ListException 
	 */
	public static List<Map<String, String>> fullOuterJoin(List<Map<String, String>> list1, List<Map<String, String>> list2, String[] keys) throws ListException {
		AssertUtility.notNull(list1);
		AssertUtility.notNull(list2);
		AssertUtility.notNull(keys);
		
		List<Map<String, String>> list = leftJoin(list1, list2, keys);
		List<Map<String, String>> rightJoinList = rightJoin(list1, list2, keys);
		
		for(int i = 0; i < rightJoinList.size(); i++) {
			if(!list.contains(rightJoinList.get(i))) {
				list.add(rightJoinList.get(i));
			}
		}
		
		return list;
	}
	
	/**
	 * 將ArrayList某兩個欄位拿出來轉成一個LinkedHashMap物件
	 * @param ArrayList
	 * @param key的欄位名稱
	 * @param value的欄位名稱
	 * @return LinkedHashMap
	 * @author George_Tsai
	 * @version 2008/7/11
	 */
	public static Map<String, String> toMap(List<Map<String, String>> list, String keyName, String valueName) {
		AssertUtility.notNull(list);
		AssertUtility.notNullAndNotSpace(keyName);
		AssertUtility.notNullAndNotSpace(valueName);
		
		Map<String, String> map = new LinkedHashMap<String, String>();
		
		for(int i = 0; i < list.size(); i++) {
			Map<String, String> tmpMap = list.get(i);
			
			map.put(tmpMap.get(keyName).toString(), tmpMap.get(valueName).toString());
		}
		
		return map;
	}
	
	/**
	 * 將某欄整個轉出為String[]
	 * @param list
	 * @param fieldName
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/2/7
	 */
	public static String[] toArray(List<Map<String, String>> list, String fieldName) {
		AssertUtility.notNull(list);
		AssertUtility.notNullAndNotSpace(fieldName);
		
		String[] aryString = new String[list.size()];
		
		for(int i = 0; i < list.size(); i++) {
			if((list.get(i)).get(fieldName) == null) {
				aryString[i] = "";
			} else {
				aryString[i] = (list.get(i)).get(fieldName).toString();
			}
		}
		
		return aryString;
	}
	
	/**
	 * Group by 多個欄位
	 * @param list
	 * @param groupByFields
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/2/8
	 */
	public static List<Map<String, String>> groupByField(List<Map<String, String>> list, String[] groupByFields) {
		AssertUtility.notNull(list);
		AssertUtility.notNull(groupByFields);
		
		List<Map<String, String>> tempList = new ArrayList<Map<String, String>>();
		
		if(list.size() > 0) {
			String strGroupByField = "";
			Object[] keys = (list.get(0)).keySet().toArray();
			
			StringBuffer[] strTargetField = new StringBuffer[keys.length];
			for(int i = 0; i < strTargetField.length; i++) {
				strTargetField[i] = new StringBuffer();
			}
			
			for(int i = 0; i < list.size(); i++) {
				Map<String, String> tempMap = list.get(i);
				
				if(strGroupByField.equals(appendGroupByFieldValue(tempMap, groupByFields))) {
					for(int j = 0; j < strTargetField.length; j++) {
						if(!StringUtility.isInArray(keys[j].toString(), groupByFields)) {
							strTargetField[j].append("<br>" + tempMap.get(keys[j].toString()).toString());
						}
					}
				} else {
					if(i != 0) {
						Map<String, String> map = new LinkedHashMap<String, String>();
						for(int j = 0; j < groupByFields.length; j++) {
							map.put(groupByFields[j], strGroupByField.split(",")[j]);
						}
						for(int j = 0; j < strTargetField.length; j++) {
							if(!StringUtility.isInArray(keys[j].toString(), groupByFields)) {
								map.put(keys[j].toString(), strTargetField[j].toString());
							}
						}
						tempList.add(map);
					}
					
					strGroupByField = appendGroupByFieldValue(tempMap, groupByFields);
					for(int j = 0; j < strTargetField.length; j++) {
						if(!StringUtility.isInArray(keys[j].toString(), groupByFields)) {
							strTargetField[j] = new StringBuffer().append(tempMap.get(keys[j].toString()).toString());
						}
					}
				}
			}
			Map<String, String> map = new LinkedHashMap<String, String>();
			for(int j = 0; j < groupByFields.length; j++) {
				map.put(groupByFields[j], strGroupByField.split(",")[j]);
			}
			for(int j = 0; j < strTargetField.length; j++) {
				if(!StringUtility.isInArray(keys[j].toString(), groupByFields)) {
					map.put(keys[j].toString(), strTargetField[j].toString());
				}
			}
			tempList.add(map);
		}
		
		return tempList;
	}
	
	//做多個欄位的group by,把多個欄位的值串接做判斷,就可簡化演算法為做一個欄位group by一樣
	private static String appendGroupByFieldValue(Map<String, String> map, String[] fields) {
		AssertUtility.notNull(map);
		AssertUtility.notNull(fields);
		
		StringBuffer strValue = new StringBuffer();
		
		for(int i = 0; i < fields.length; i++) {
			if(!strValue.toString().equals("")) {
				strValue.append(",");
			}
			strValue.append(map.get(fields[i]).toString());
		}
		
		return strValue.toString();
	}
	
	
	
	/**
	 * 將ArrayList轉成使用符號隔開的格式字串(利用JSONArray)
	 * 為了讓WebServices可以傳送ArrayList資料所設計
	 * @param list
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2011/5/6
	 */
	public static String toString(List<Map<String, String>> list) {
		AssertUtility.notNull(list);
		
		JSONArray array = new JSONArray(list);
		return array.toString();
	}
	
	/**
	 * 將使用符號隔開的格式字串(JSONArray)轉換成ArrayList物件
	 * 為了讓WebServices可以傳送ArrayList資料所設計
	 * @param formatString
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2011/5/6
	 * @throws JSONException 
	 */
	public static List<Map<String, String>> toList(String formatString) throws JSONException {
		AssertUtility.notNull(formatString);
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		if(!formatString.trim().equals("")) {
			JSONArray array = new JSONArray(formatString);
			for(int i = 0; i < array.length(); i++) {
				JSONObject obj = new JSONObject(array.get(i).toString());
				Map<String, String> map = new LinkedHashMap<String, String>();
				
				for(int j = 0; j < obj.names().length(); j++) {
					String key = obj.names().getString(j);
					map.put(key, obj.getString(key));
				}
				
				list.add(map);
			}
		}
		
		return list;
	}
	
	/**
	 * 將使用hibernate抓出的List物件轉成ArrayList物件
	 * @param List
	 * @param userClass(Domain Object)
	 * @return
	 * @throws Exception
	 * @author George_Tsai
	 * @version 2009/6/10
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static List<Map<String, Object>> toList(List<?> list, Class<?> userClass) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException  {
		AssertUtility.notNull(list);
		AssertUtility.notNull(userClass);
		
		List<Map<String, Object>> finalList = new ArrayList<Map<String, Object>>();
		
		Iterator<?> iterator = list.iterator();
		
        while (iterator.hasNext()) {
        	Map<String, Object> map = new LinkedHashMap<String, Object>();
        	
        	Object obj = iterator.next();
	        
	        for(int i = 0; i < userClass.getMethods().length; i++) {
	        	Method method = userClass.getMethods()[i];
	        	if(method.getName().startsWith("get") && !method.getName().equalsIgnoreCase("getClass")) {
	        		String key = method.getName().replaceFirst("get", "");
		        	key = key.substring(0, 1).toLowerCase() + key.substring(1, key.length());
		        	
		        	//需要傳參數的get method先濾掉
		        	if(method.getGenericParameterTypes() == null || method.getGenericParameterTypes().length == 0) {
		        		//要不要濾掉collection物件,可以再考慮看看
//		        		if(!method.getReturnType().equals(List.class) &&
//		        		   !method.getReturnType().equals(Set.class) &&
//				           !method.getReturnType().equals(Map.class)) {
		        			map.put(key, method.invoke(obj, (Object[])null));
//				        }
		        	}
	        	}
	        }
	        
	        finalList.add(map);
        }
		
		return finalList;
	}
	
	/**
	 * 將ArrayList<LinkedHashMap<String, Object>>,轉型成為ArrayList<LinkedHashMap<String, String>>
	 * 將使用hibernate抓出的List物件轉成ArrayList物件後,需要透過此method轉型
	 * 才有辦法使用其它的method來協力完成工作
	 * @param originalList
	 * @param dateType
	 * @return
	 * @author George_Tsai
	 * @throws DateFormatException 
	 */
	public static List<Map<String, String>> castList(List<Map<String, Object>> originalList, int dateType) throws DateFormatException {
		AssertUtility.notNull(originalList);
		
		List<Map<String, String>> resultList = new ArrayList<Map<String,String>>();
		
		for(int i = 0; i < originalList.size(); i++) {
			Map<String, String> resultMap = new LinkedHashMap<String, String>();
			
			Map<String, Object> originalMap = originalList.get(i);
			Iterator<Entry<String, Object>> iterator = originalMap.entrySet().iterator();
			while(iterator.hasNext()) {
				Entry<String, Object> entry = iterator.next();
				if(entry.getValue() != null) {
					if(Calendar.class.isInstance(entry.getValue())) {
						resultMap.put(entry.getKey(), DateFormatUtility.getDisplayDate((Calendar)entry.getValue(), dateType));
					} else {
						resultMap.put(entry.getKey(), entry.getValue().toString());
					}
				} else {
					resultMap.put(entry.getKey(), null);
				}
			}
			
			resultList.add(resultMap);
		}
		
		return resultList;
	}
	
	/**
	 * 將NamingEnumeration<SearchResult>轉成List<Map<String, String>>
	 * @param results
	 * @return
	 * @throws NamingException
	 */
	public static List<Map<String, String>> toList(NamingEnumeration<SearchResult> results) throws NamingException {
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		
		while (results.hasMore()) {
			Map<String, String> map = new LinkedHashMap<String, String>();
			SearchResult searchResult = results.next();
            Attributes attributes = searchResult.getAttributes();
            NamingEnumeration<String> attributeNames = attributes.getIDs();
            
            while(attributeNames.hasMore()) {
            	String attributeName = attributeNames.next();
            	map.put(attributeName, "" + attributes.get(attributeName).get());
            }
            
            list.add(map);
		}
		
		return list;
	}
}
