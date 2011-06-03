package csiebug.test.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.junit.Test;

import csiebug.util.ListException;
import csiebug.util.ListUtility;

public class ListUtilityTest {
	List<Map<String, String>> list1 = new ArrayList<Map<String,String>>();
	List<Map<String, String>> list2 = new ArrayList<Map<String,String>>();
	List<Map<String, String>> list3 = new ArrayList<Map<String,String>>();
	List<Map<String, String>> list4 = new ArrayList<Map<String,String>>();
	List<Map<String, String>> list5 = new ArrayList<Map<String,String>>();
	List<Map<String, String>> list6 = new ArrayList<Map<String,String>>();
	
	List<Map<String, String>> groupList1 = new ArrayList<Map<String,String>>();
	List<Map<String, String>> groupList2 = new ArrayList<Map<String,String>>();
	List<Map<String, String>> groupList3 = new ArrayList<Map<String,String>>();
	List<Map<String, String>> groupList4 = new ArrayList<Map<String,String>>();
	List<Map<String, String>> groupList5 = new ArrayList<Map<String,String>>();
	List<Map<String, String>> groupList6 = new ArrayList<Map<String,String>>();
	
	private void prepareData() {
		Map<String, String> map1 = new LinkedHashMap<String, String>();
		map1.put("group", "group1");
		map1.put("string", "value1");
		map1.put("number", "20000");
		map1.put("currency", "4,000");
		
		Map<String, String> map2 = new LinkedHashMap<String, String>();
		map2.put("group", "group1");
		map2.put("string", "value2");
		map2.put("number", "30000");
		map2.put("currency", "500");
		
		Map<String, String> map3 = new LinkedHashMap<String, String>();
		map3.put("group", "group3");
		map3.put("string", "value3");
		map3.put("number", "10000");
		map3.put("currency", "60");
		
		list1.add(map1);
		list1.add(map2);
		list1.add(map3);
		
		list2.add(map1);
		list2.add(map3);
		list2.add(map2);
		
		list3.add(map2);
		list3.add(map1);
		list3.add(map3);
		
		list4.add(map2);
		list4.add(map3);
		list4.add(map1);
		
		list5.add(map3);
		list5.add(map1);
		list5.add(map2);
		
		list6.add(map3);
		list6.add(map2);
		list6.add(map1);
	}
	
	private void prepareDataForInsertGroupData1() {
		Map<String, String> map1 = new LinkedHashMap<String, String>();
		map1.put("group", "group1");
		map1.put("string", "value1");
		map1.put("number", "20000");
		map1.put("currency", "4,000");
		map1.put("groupId", "group1_value1_0");
		map1.put("groupType", "data");
		
		Map<String, String> map2 = new LinkedHashMap<String, String>();
		map2.put("group", "group1");
		map2.put("string", "value2");
		map2.put("number", "30000");
		map2.put("currency", "500");
		map2.put("groupId", "group1_value2_1");
		map2.put("groupType", "data");
		
		Map<String, String> map3 = new LinkedHashMap<String, String>();
		map3.put("group", "group3");
		map3.put("string", "value3");
		map3.put("number", "10000");
		map3.put("currency", "60");
		map3.put("groupId", "group3_value3_2");
		map3.put("groupType", "data");
		
		Map<String, String> map4 = new LinkedHashMap<String, String>();
		map4.put("group", "group1");
		map4.put("string", "");
		map4.put("number", "");
		map4.put("currency", "");
		map4.put("groupId", "group1");
		map4.put("groupType", "group");
		
		Map<String, String> map5 = new LinkedHashMap<String, String>();
		map5.put("group", "group1");
		map5.put("string", "value1");
		map5.put("number", "");
		map5.put("currency", "");
		map5.put("groupId", "group1_value1");
		map5.put("groupType", "group");
		
		Map<String, String> map6 = new LinkedHashMap<String, String>();
		map6.put("group", "group1");
		map6.put("string", "value2");
		map6.put("number", "");
		map6.put("currency", "");
		map6.put("groupId", "group1_value2");
		map6.put("groupType", "group");
		
		Map<String, String> map7 = new LinkedHashMap<String, String>();
		map7.put("group", "group3");
		map7.put("string", "");
		map7.put("number", "");
		map7.put("currency", "");
		map7.put("groupId", "group3");
		map7.put("groupType", "group");
		
		Map<String, String> map8 = new LinkedHashMap<String, String>();
		map8.put("group", "group3");
		map8.put("string", "value3");
		map8.put("number", "");
		map8.put("currency", "");
		map8.put("groupId", "group3_value3");
		map8.put("groupType", "group");
		
		groupList1.add(map4);
		groupList1.add(map5);
		groupList1.add(map1);
		groupList1.add(map6);
		groupList1.add(map2);
		groupList1.add(map7);
		groupList1.add(map8);
		groupList1.add(map3);
	}
	
	private void prepareDataForInsertGroupData2() {
		Map<String, String> map1 = new LinkedHashMap<String, String>();
		map1.put("group", "group1");
		map1.put("string", "value1");
		map1.put("number", "20000");
		map1.put("currency", "4,000");
		map1.put("groupId", "group1_20000_0");
		map1.put("groupType", "data");
		
		Map<String, String> map2 = new LinkedHashMap<String, String>();
		map2.put("group", "group1");
		map2.put("string", "value2");
		map2.put("number", "30000");
		map2.put("currency", "500");
		map2.put("groupId", "group1_30000_1");
		map2.put("groupType", "data");
		
		Map<String, String> map3 = new LinkedHashMap<String, String>();
		map3.put("group", "group3");
		map3.put("string", "value3");
		map3.put("number", "10000");
		map3.put("currency", "60");
		map3.put("groupId", "group3_10000_2");
		map3.put("groupType", "data");
		
		Map<String, String> map4 = new LinkedHashMap<String, String>();
		map4.put("group", "group1");
		map4.put("string", "");
		map4.put("number", "");
		map4.put("currency", "");
		map4.put("groupId", "group1");
		map4.put("groupType", "group");
		
		Map<String, String> map5 = new LinkedHashMap<String, String>();
		map5.put("group", "group1");
		map5.put("string", "");
		map5.put("number", "20000");
		map5.put("currency", "");
		map5.put("groupId", "group1_20000");
		map5.put("groupType", "group");
		
		Map<String, String> map6 = new LinkedHashMap<String, String>();
		map6.put("group", "group1");
		map6.put("string", "");
		map6.put("number", "30000");
		map6.put("currency", "");
		map6.put("groupId", "group1_30000");
		map6.put("groupType", "group");
		
		Map<String, String> map7 = new LinkedHashMap<String, String>();
		map7.put("group", "group3");
		map7.put("string", "");
		map7.put("number", "");
		map7.put("currency", "");
		map7.put("groupId", "group3");
		map7.put("groupType", "group");
		
		Map<String, String> map8 = new LinkedHashMap<String, String>();
		map8.put("group", "group3");
		map8.put("string", "");
		map8.put("number", "10000");
		map8.put("currency", "");
		map8.put("groupId", "group3_10000");
		map8.put("groupType", "group");
		
		groupList2.add(map4);
		groupList2.add(map5);
		groupList2.add(map1);
		groupList2.add(map6);
		groupList2.add(map2);
		groupList2.add(map7);
		groupList2.add(map8);
		groupList2.add(map3);
	}
	
	private void prepareDataForInsertGroupData3() {
		Map<String, String> map1 = new LinkedHashMap<String, String>();
		map1.put("group", "group1");
		map1.put("string", "value1");
		map1.put("number", "20000");
		map1.put("currency", "4,000");
		map1.put("groupId", "group1_4,000_0");
		map1.put("groupType", "data");
		
		Map<String, String> map2 = new LinkedHashMap<String, String>();
		map2.put("group", "group1");
		map2.put("string", "value2");
		map2.put("number", "30000");
		map2.put("currency", "500");
		map2.put("groupId", "group1_500_1");
		map2.put("groupType", "data");
		
		Map<String, String> map3 = new LinkedHashMap<String, String>();
		map3.put("group", "group3");
		map3.put("string", "value3");
		map3.put("number", "10000");
		map3.put("currency", "60");
		map3.put("groupId", "group3_60_2");
		map3.put("groupType", "data");
		
		Map<String, String> map4 = new LinkedHashMap<String, String>();
		map4.put("group", "group1");
		map4.put("string", "");
		map4.put("number", "");
		map4.put("currency", "");
		map4.put("groupId", "group1");
		map4.put("groupType", "group");
		
		Map<String, String> map5 = new LinkedHashMap<String, String>();
		map5.put("group", "group1");
		map5.put("string", "");
		map5.put("number", "");
		map5.put("currency", "4,000");
		map5.put("groupId", "group1_4,000");
		map5.put("groupType", "group");
		
		Map<String, String> map6 = new LinkedHashMap<String, String>();
		map6.put("group", "group1");
		map6.put("string", "");
		map6.put("number", "");
		map6.put("currency", "500");
		map6.put("groupId", "group1_500");
		map6.put("groupType", "group");
		
		Map<String, String> map7 = new LinkedHashMap<String, String>();
		map7.put("group", "group3");
		map7.put("string", "");
		map7.put("number", "");
		map7.put("currency", "");
		map7.put("groupId", "group3");
		map7.put("groupType", "group");
		
		Map<String, String> map8 = new LinkedHashMap<String, String>();
		map8.put("group", "group3");
		map8.put("string", "");
		map8.put("number", "");
		map8.put("currency", "60");
		map8.put("groupId", "group3_60");
		map8.put("groupType", "group");
		
		groupList3.add(map4);
		groupList3.add(map6);
		groupList3.add(map2);
		groupList3.add(map5);
		groupList3.add(map1);
		groupList3.add(map7);
		groupList3.add(map8);
		groupList3.add(map3);
	}
	
	private void prepareDataForInsertGroupData4() {
		Map<String, String> map1 = new LinkedHashMap<String, String>();
		map1.put("group", "group1");
		map1.put("string", "value1");
		map1.put("number", "20000");
		map1.put("currency", "4,000");
		map1.put("groupId", "group1_value1_1");
		map1.put("groupType", "data");
		
		Map<String, String> map2 = new LinkedHashMap<String, String>();
		map2.put("group", "group1");
		map2.put("string", "value2");
		map2.put("number", "30000");
		map2.put("currency", "500");
		map2.put("groupId", "group1_value2_0");
		map2.put("groupType", "data");
		
		Map<String, String> map3 = new LinkedHashMap<String, String>();
		map3.put("group", "group3");
		map3.put("string", "value3");
		map3.put("number", "10000");
		map3.put("currency", "60");
		map3.put("groupId", "group3_value3_2");
		map3.put("groupType", "data");
		
		Map<String, String> map4 = new LinkedHashMap<String, String>();
		map4.put("group", "group1");
		map4.put("string", "");
		map4.put("number", "");
		map4.put("currency", "");
		map4.put("groupId", "group1");
		map4.put("groupType", "group");
		
		Map<String, String> map5 = new LinkedHashMap<String, String>();
		map5.put("group", "group1");
		map5.put("string", "value1");
		map5.put("number", "");
		map5.put("currency", "");
		map5.put("groupId", "group1_value1");
		map5.put("groupType", "group");
		
		Map<String, String> map6 = new LinkedHashMap<String, String>();
		map6.put("group", "group1");
		map6.put("string", "value2");
		map6.put("number", "");
		map6.put("currency", "");
		map6.put("groupId", "group1_value2");
		map6.put("groupType", "group");
		
		Map<String, String> map7 = new LinkedHashMap<String, String>();
		map7.put("group", "group3");
		map7.put("string", "");
		map7.put("number", "");
		map7.put("currency", "");
		map7.put("groupId", "group3");
		map7.put("groupType", "group");
		
		Map<String, String> map8 = new LinkedHashMap<String, String>();
		map8.put("group", "group3");
		map8.put("string", "value3");
		map8.put("number", "");
		map8.put("currency", "");
		map8.put("groupId", "group3_value3");
		map8.put("groupType", "group");
		
		groupList4.add(map4);
		groupList4.add(map5);
		groupList4.add(map1);
		groupList4.add(map6);
		groupList4.add(map2);
		groupList4.add(map7);
		groupList4.add(map8);
		groupList4.add(map3);
	}
	
	private void prepareDataForInsertGroupData5() {
		Map<String, String> map1 = new LinkedHashMap<String, String>();
		map1.put("group", "group1");
		map1.put("string", "value1");
		map1.put("number", "20000");
		map1.put("currency", "4,000");
		map1.put("groupId", "group1_20000_1");
		map1.put("groupType", "data");
		
		Map<String, String> map2 = new LinkedHashMap<String, String>();
		map2.put("group", "group1");
		map2.put("string", "value2");
		map2.put("number", "30000");
		map2.put("currency", "500");
		map2.put("groupId", "group1_30000_0");
		map2.put("groupType", "data");
		
		Map<String, String> map3 = new LinkedHashMap<String, String>();
		map3.put("group", "group3");
		map3.put("string", "value3");
		map3.put("number", "10000");
		map3.put("currency", "60");
		map3.put("groupId", "group3_10000_2");
		map3.put("groupType", "data");
		
		Map<String, String> map4 = new LinkedHashMap<String, String>();
		map4.put("group", "group1");
		map4.put("string", "");
		map4.put("number", "");
		map4.put("currency", "");
		map4.put("groupId", "group1");
		map4.put("groupType", "group");
		
		Map<String, String> map5 = new LinkedHashMap<String, String>();
		map5.put("group", "group1");
		map5.put("string", "");
		map5.put("number", "20000");
		map5.put("currency", "");
		map5.put("groupId", "group1_20000");
		map5.put("groupType", "group");
		
		Map<String, String> map6 = new LinkedHashMap<String, String>();
		map6.put("group", "group1");
		map6.put("string", "");
		map6.put("number", "30000");
		map6.put("currency", "");
		map6.put("groupId", "group1_30000");
		map6.put("groupType", "group");
		
		Map<String, String> map7 = new LinkedHashMap<String, String>();
		map7.put("group", "group3");
		map7.put("string", "");
		map7.put("number", "");
		map7.put("currency", "");
		map7.put("groupId", "group3");
		map7.put("groupType", "group");
		
		Map<String, String> map8 = new LinkedHashMap<String, String>();
		map8.put("group", "group3");
		map8.put("string", "");
		map8.put("number", "10000");
		map8.put("currency", "");
		map8.put("groupId", "group3_10000");
		map8.put("groupType", "group");
		
		groupList5.add(map4);
		groupList5.add(map5);
		groupList5.add(map1);
		groupList5.add(map6);
		groupList5.add(map2);
		groupList5.add(map7);
		groupList5.add(map8);
		groupList5.add(map3);
	}
	
	private void prepareDataForInsertGroupData6() {
		Map<String, String> map1 = new LinkedHashMap<String, String>();
		map1.put("group", "group1");
		map1.put("string", "value1");
		map1.put("number", "20000");
		map1.put("currency", "4,000");
		map1.put("groupId", "group1_4,000_1");
		map1.put("groupType", "data");
		
		Map<String, String> map2 = new LinkedHashMap<String, String>();
		map2.put("group", "group1");
		map2.put("string", "value2");
		map2.put("number", "30000");
		map2.put("currency", "500");
		map2.put("groupId", "group1_500_0");
		map2.put("groupType", "data");
		
		Map<String, String> map3 = new LinkedHashMap<String, String>();
		map3.put("group", "group3");
		map3.put("string", "value3");
		map3.put("number", "10000");
		map3.put("currency", "60");
		map3.put("groupId", "group3_60_2");
		map3.put("groupType", "data");
		
		Map<String, String> map4 = new LinkedHashMap<String, String>();
		map4.put("group", "group1");
		map4.put("string", "");
		map4.put("number", "");
		map4.put("currency", "");
		map4.put("groupId", "group1");
		map4.put("groupType", "group");
		
		Map<String, String> map5 = new LinkedHashMap<String, String>();
		map5.put("group", "group1");
		map5.put("string", "");
		map5.put("number", "");
		map5.put("currency", "4,000");
		map5.put("groupId", "group1_4,000");
		map5.put("groupType", "group");
		
		Map<String, String> map6 = new LinkedHashMap<String, String>();
		map6.put("group", "group1");
		map6.put("string", "");
		map6.put("number", "");
		map6.put("currency", "500");
		map6.put("groupId", "group1_500");
		map6.put("groupType", "group");
		
		Map<String, String> map7 = new LinkedHashMap<String, String>();
		map7.put("group", "group3");
		map7.put("string", "");
		map7.put("number", "");
		map7.put("currency", "");
		map7.put("groupId", "group3");
		map7.put("groupType", "group");
		
		Map<String, String> map8 = new LinkedHashMap<String, String>();
		map8.put("group", "group3");
		map8.put("string", "");
		map8.put("number", "");
		map8.put("currency", "60");
		map8.put("groupId", "group3_60");
		map8.put("groupType", "group");
		
		groupList6.add(map4);
		groupList6.add(map6);
		groupList6.add(map2);
		groupList6.add(map5);
		groupList6.add(map1);
		groupList6.add(map7);
		groupList6.add(map8);
		groupList6.add(map3);
	}
	
	@Test
	public void testSortByName() {
		prepareData();
		
		assertEquals(list1, ListUtility.sortByName(list1, "group"));
		assertEquals(list1, ListUtility.sortByName(list1, "string"));
		assertEquals(list5, ListUtility.sortByName(list1, "number"));
		assertEquals(list6, ListUtility.sortByName(list1, "currency"));
		
		assertEquals(list1, ListUtility.sortByName(list2, "group"));
		assertEquals(list1, ListUtility.sortByName(list2, "string"));
		assertEquals(list5, ListUtility.sortByName(list2, "number"));
		assertEquals(list6, ListUtility.sortByName(list2, "currency"));
		
		assertEquals(list3, ListUtility.sortByName(list3, "group"));
		assertEquals(list1, ListUtility.sortByName(list3, "string"));
		assertEquals(list5, ListUtility.sortByName(list3, "number"));
		assertEquals(list6, ListUtility.sortByName(list3, "currency"));
		
		assertEquals(list3, ListUtility.sortByName(list4, "group"));
		assertEquals(list1, ListUtility.sortByName(list4, "string"));
		assertEquals(list5, ListUtility.sortByName(list4, "number"));
		assertEquals(list6, ListUtility.sortByName(list4, "currency"));
		
		assertEquals(list1, ListUtility.sortByName(list5, "group"));
		assertEquals(list1, ListUtility.sortByName(list5, "string"));
		assertEquals(list5, ListUtility.sortByName(list5, "number"));
		assertEquals(list6, ListUtility.sortByName(list5, "currency"));
		
		assertEquals(list3, ListUtility.sortByName(list6, "group"));
		assertEquals(list1, ListUtility.sortByName(list6, "string"));
		assertEquals(list5, ListUtility.sortByName(list6, "number"));
		assertEquals(list6, ListUtility.sortByName(list6, "currency"));
	}

	@Test
	public void testSortByMultipleName() {
		prepareData();
		
		String[] fieldNames = new String[]{"group", "string"};
		assertEquals(list1, ListUtility.sortByMultipleName(list1, fieldNames));
		fieldNames = new String[]{"group", "number"};
		assertEquals(list1, ListUtility.sortByMultipleName(list1, fieldNames));
		fieldNames = new String[]{"group", "currency"};
		assertEquals(list3, ListUtility.sortByMultipleName(list1, fieldNames));
		
		fieldNames = new String[]{"group", "string"};
		assertEquals(list1, ListUtility.sortByMultipleName(list2, fieldNames));
		fieldNames = new String[]{"group", "number"};
		assertEquals(list1, ListUtility.sortByMultipleName(list2, fieldNames));
		fieldNames = new String[]{"group", "currency"};
		assertEquals(list3, ListUtility.sortByMultipleName(list2, fieldNames));
		
		fieldNames = new String[]{"group", "string"};
		assertEquals(list1, ListUtility.sortByMultipleName(list3, fieldNames));
		fieldNames = new String[]{"group", "number"};
		assertEquals(list1, ListUtility.sortByMultipleName(list3, fieldNames));
		fieldNames = new String[]{"group", "currency"};
		assertEquals(list3, ListUtility.sortByMultipleName(list3, fieldNames));
		
		fieldNames = new String[]{"group", "string"};
		assertEquals(list1, ListUtility.sortByMultipleName(list4, fieldNames));
		fieldNames = new String[]{"group", "number"};
		assertEquals(list1, ListUtility.sortByMultipleName(list4, fieldNames));
		fieldNames = new String[]{"group", "currency"};
		assertEquals(list3, ListUtility.sortByMultipleName(list4, fieldNames));
		
		fieldNames = new String[]{"group", "string"};
		assertEquals(list1, ListUtility.sortByMultipleName(list5, fieldNames));
		fieldNames = new String[]{"group", "number"};
		assertEquals(list1, ListUtility.sortByMultipleName(list5, fieldNames));
		fieldNames = new String[]{"group", "currency"};
		assertEquals(list3, ListUtility.sortByMultipleName(list5, fieldNames));
		
		fieldNames = new String[]{"group", "string"};
		assertEquals(list1, ListUtility.sortByMultipleName(list6, fieldNames));
		fieldNames = new String[]{"group", "number"};
		assertEquals(list1, ListUtility.sortByMultipleName(list6, fieldNames));
		fieldNames = new String[]{"group", "currency"};
		assertEquals(list3, ListUtility.sortByMultipleName(list6, fieldNames));
	}

	@Test
	public void testSortByNameDesc() {
		prepareData();
		
		assertEquals(list6, ListUtility.sortByNameDesc(list1, "group"));
		assertEquals(list6, ListUtility.sortByNameDesc(list1, "string"));
		assertEquals(list3, ListUtility.sortByNameDesc(list1, "number"));
		assertEquals(list1, ListUtility.sortByNameDesc(list1, "currency"));
		
		assertEquals(list6, ListUtility.sortByNameDesc(list2, "group"));
		assertEquals(list6, ListUtility.sortByNameDesc(list2, "string"));
		assertEquals(list3, ListUtility.sortByNameDesc(list2, "number"));
		assertEquals(list1, ListUtility.sortByNameDesc(list2, "currency"));
		
		assertEquals(list5, ListUtility.sortByNameDesc(list3, "group"));
		assertEquals(list6, ListUtility.sortByNameDesc(list3, "string"));
		assertEquals(list3, ListUtility.sortByNameDesc(list3, "number"));
		assertEquals(list1, ListUtility.sortByNameDesc(list3, "currency"));
		
		assertEquals(list5, ListUtility.sortByNameDesc(list4, "group"));
		assertEquals(list6, ListUtility.sortByNameDesc(list4, "string"));
		assertEquals(list3, ListUtility.sortByNameDesc(list4, "number"));
		assertEquals(list1, ListUtility.sortByNameDesc(list4, "currency"));
		
		assertEquals(list6, ListUtility.sortByNameDesc(list5, "group"));
		assertEquals(list6, ListUtility.sortByNameDesc(list5, "string"));
		assertEquals(list3, ListUtility.sortByNameDesc(list5, "number"));
		assertEquals(list1, ListUtility.sortByNameDesc(list5, "currency"));
		
		assertEquals(list5, ListUtility.sortByNameDesc(list6, "group"));
		assertEquals(list6, ListUtility.sortByNameDesc(list6, "string"));
		assertEquals(list3, ListUtility.sortByNameDesc(list6, "number"));
		assertEquals(list1, ListUtility.sortByNameDesc(list6, "currency"));
	}

	@Test
	public void testSortByMultipleNameDesc() {
		prepareData();
		
		String[] fieldNames = new String[]{"group", "string"};
		assertEquals(list6, ListUtility.sortByMultipleNameDesc(list1, fieldNames));
		fieldNames = new String[]{"group", "number"};
		assertEquals(list6, ListUtility.sortByMultipleNameDesc(list1, fieldNames));
		fieldNames = new String[]{"group", "currency"};
		assertEquals(list5, ListUtility.sortByMultipleNameDesc(list1, fieldNames));
		
		fieldNames = new String[]{"group", "string"};
		assertEquals(list6, ListUtility.sortByMultipleNameDesc(list2, fieldNames));
		fieldNames = new String[]{"group", "number"};
		assertEquals(list6, ListUtility.sortByMultipleNameDesc(list2, fieldNames));
		fieldNames = new String[]{"group", "currency"};
		assertEquals(list5, ListUtility.sortByMultipleNameDesc(list2, fieldNames));
		
		fieldNames = new String[]{"group", "string"};
		assertEquals(list6, ListUtility.sortByMultipleNameDesc(list3, fieldNames));
		fieldNames = new String[]{"group", "number"};
		assertEquals(list6, ListUtility.sortByMultipleNameDesc(list3, fieldNames));
		fieldNames = new String[]{"group", "currency"};
		assertEquals(list5, ListUtility.sortByMultipleNameDesc(list3, fieldNames));
		
		fieldNames = new String[]{"group", "string"};
		assertEquals(list6, ListUtility.sortByMultipleNameDesc(list4, fieldNames));
		fieldNames = new String[]{"group", "number"};
		assertEquals(list6, ListUtility.sortByMultipleNameDesc(list4, fieldNames));
		fieldNames = new String[]{"group", "currency"};
		assertEquals(list5, ListUtility.sortByMultipleNameDesc(list4, fieldNames));
		
		fieldNames = new String[]{"group", "string"};
		assertEquals(list6, ListUtility.sortByMultipleNameDesc(list5, fieldNames));
		fieldNames = new String[]{"group", "number"};
		assertEquals(list6, ListUtility.sortByMultipleNameDesc(list5, fieldNames));
		fieldNames = new String[]{"group", "currency"};
		assertEquals(list5, ListUtility.sortByMultipleNameDesc(list5, fieldNames));
		
		fieldNames = new String[]{"group", "string"};
		assertEquals(list6, ListUtility.sortByMultipleNameDesc(list6, fieldNames));
		fieldNames = new String[]{"group", "number"};
		assertEquals(list6, ListUtility.sortByMultipleNameDesc(list6, fieldNames));
		fieldNames = new String[]{"group", "currency"};
		assertEquals(list5, ListUtility.sortByMultipleNameDesc(list6, fieldNames));
	}
	
	@Test
	public void testSortByMultipleNameWithInsertGroupData() {
		prepareData();
		prepareDataForInsertGroupData1();
		prepareDataForInsertGroupData2();
		prepareDataForInsertGroupData3();
		prepareDataForInsertGroupData4();
		prepareDataForInsertGroupData5();
		prepareDataForInsertGroupData6();
		
		String[] fieldNames = new String[]{"group", "string"};
		assertEquals(groupList1, ListUtility.sortByMultipleNameWithInsertGroupData(list1, fieldNames));
		fieldNames = new String[]{"group", "number"};
		assertEquals(groupList2, ListUtility.sortByMultipleNameWithInsertGroupData(list1, fieldNames));
		fieldNames = new String[]{"group", "currency"};
		assertEquals(groupList3, ListUtility.sortByMultipleNameWithInsertGroupData(list1, fieldNames));
		
		fieldNames = new String[]{"group", "string"};
		assertEquals(groupList1, ListUtility.sortByMultipleNameWithInsertGroupData(list2, fieldNames));
		fieldNames = new String[]{"group", "number"};
		assertEquals(groupList2, ListUtility.sortByMultipleNameWithInsertGroupData(list2, fieldNames));
		fieldNames = new String[]{"group", "currency"};
		assertEquals(groupList3, ListUtility.sortByMultipleNameWithInsertGroupData(list2, fieldNames));
		
		fieldNames = new String[]{"group", "string"};
		assertEquals(groupList4, ListUtility.sortByMultipleNameWithInsertGroupData(list3, fieldNames));
		fieldNames = new String[]{"group", "number"};
		assertEquals(groupList5, ListUtility.sortByMultipleNameWithInsertGroupData(list3, fieldNames));
		fieldNames = new String[]{"group", "currency"};
		assertEquals(groupList6, ListUtility.sortByMultipleNameWithInsertGroupData(list3, fieldNames));
		
		fieldNames = new String[]{"group", "string"};
		assertEquals(groupList4, ListUtility.sortByMultipleNameWithInsertGroupData(list4, fieldNames));
		fieldNames = new String[]{"group", "number"};
		assertEquals(groupList5, ListUtility.sortByMultipleNameWithInsertGroupData(list4, fieldNames));
		fieldNames = new String[]{"group", "currency"};
		assertEquals(groupList6, ListUtility.sortByMultipleNameWithInsertGroupData(list4, fieldNames));
		
		fieldNames = new String[]{"group", "string"};
		assertEquals(groupList1, ListUtility.sortByMultipleNameWithInsertGroupData(list5, fieldNames));
		fieldNames = new String[]{"group", "number"};
		assertEquals(groupList2, ListUtility.sortByMultipleNameWithInsertGroupData(list5, fieldNames));
		fieldNames = new String[]{"group", "currency"};
		assertEquals(groupList3, ListUtility.sortByMultipleNameWithInsertGroupData(list5, fieldNames));
		
		fieldNames = new String[]{"group", "string"};
		assertEquals(groupList4, ListUtility.sortByMultipleNameWithInsertGroupData(list6, fieldNames));
		fieldNames = new String[]{"group", "number"};
		assertEquals(groupList5, ListUtility.sortByMultipleNameWithInsertGroupData(list6, fieldNames));
		fieldNames = new String[]{"group", "currency"};
		assertEquals(groupList6, ListUtility.sortByMultipleNameWithInsertGroupData(list6, fieldNames));
	}

	@Test
	public void testReverseList() {
		prepareData();
		
		assertEquals(list6, ListUtility.reverseList(list1));
		assertEquals(list4, ListUtility.reverseList(list2));
		assertEquals(list5, ListUtility.reverseList(list3));
		assertEquals(list2, ListUtility.reverseList(list4));
		assertEquals(list3, ListUtility.reverseList(list5));
		assertEquals(list1, ListUtility.reverseList(list6));
	}

	@Test
	public void testIsCurrencyField() {
		prepareData();
		
		assertEquals(false, ListUtility.isCurrencyField(list1, "group"));
		assertEquals(false, ListUtility.isCurrencyField(list1, "string"));
		assertEquals(true, ListUtility.isCurrencyField(list1, "number"));
		assertEquals(true, ListUtility.isCurrencyField(list1, "currency"));
	}

	@Test
	public void testGetSumOfField() throws ListException {
		prepareData();
		
		try {
			ListUtility.getSumOfField(list1, "group");
			ListUtility.getSumOfField(list1, "string");
			assertEquals(true, false);
		} catch(ListException lex) {
			assertEquals(true, true);
		}
		assertEquals("60,000", ListUtility.getSumOfField(list1, "number"));
		assertEquals("4,560", ListUtility.getSumOfField(list1, "currency"));
	}

	@Test
	@Deprecated
	public void testInsertHashMaptoList() {
		prepareData();
		
		Map<String, String> map1 = new LinkedHashMap<String, String>();
		map1.put("group", "group1");
		map1.put("string", "value1");
		map1.put("number", "20000");
		map1.put("currency", "4,000");
		
		Map<String, String> map2 = new LinkedHashMap<String, String>();
		map2.put("group", "group1");
		map2.put("string", "value2");
		map2.put("number", "30000");
		map2.put("currency", "500");
		
		Map<String, String> map3 = new LinkedHashMap<String, String>();
		map3.put("group", "group3");
		map3.put("string", "value3");
		map3.put("number", "10000");
		map3.put("currency", "60");
		
		Map<String, String> insertMap = new LinkedHashMap<String, String>();
		insertMap.put("group", "group4");
		insertMap.put("string", "value4");
		insertMap.put("number", "40000");
		insertMap.put("currency", "4");
		
		List<Map<String, String>> tempList = new ArrayList<Map<String,String>>();
		tempList.add(insertMap);
		tempList.add(map1);
		tempList.add(map2);
		tempList.add(map3);
		assertEquals(tempList, ListUtility.insertHashMaptoList(list1, insertMap, 0));
		
		tempList = new ArrayList<Map<String,String>>();
		tempList.add(map1);
		tempList.add(insertMap);
		tempList.add(map2);
		tempList.add(map3);
		assertEquals(tempList, ListUtility.insertHashMaptoList(list1, insertMap, 1));
		
		tempList = new ArrayList<Map<String,String>>();
		tempList.add(map1);
		tempList.add(map2);
		tempList.add(insertMap);
		tempList.add(map3);
		assertEquals(tempList, ListUtility.insertHashMaptoList(list1, insertMap, 2));
		
		tempList = new ArrayList<Map<String,String>>();
		tempList.add(map1);
		tempList.add(map2);
		tempList.add(map3);
		tempList.add(insertMap);
		assertEquals(tempList, ListUtility.insertHashMaptoList(list1, insertMap, 3));
	}

	@Test
	public void testGetSubListByNameListStringString() {
		prepareData();
		
		Map<String, String> map1 = new LinkedHashMap<String, String>();
		map1.put("group", "group1");
		map1.put("string", "value1");
		map1.put("number", "20000");
		map1.put("currency", "4,000");
		
		Map<String, String> map2 = new LinkedHashMap<String, String>();
		map2.put("group", "group1");
		map2.put("string", "value2");
		map2.put("number", "30000");
		map2.put("currency", "500");
		
		Map<String, String> map3 = new LinkedHashMap<String, String>();
		map3.put("group", "group3");
		map3.put("string", "value3");
		map3.put("number", "10000");
		map3.put("currency", "60");
		
		List<Map<String, String>> tempList = new ArrayList<Map<String,String>>();
		tempList.add(map1);
		tempList.add(map2);
		assertEquals(tempList, ListUtility.getSubListByName(list1, "group", "group1"));
		
		tempList = new ArrayList<Map<String,String>>();
		tempList.add(map2);
		assertEquals(tempList, ListUtility.getSubListByName(list1, "string", "value2"));
		
		tempList = new ArrayList<Map<String,String>>();
		tempList.add(map3);
		assertEquals(tempList, ListUtility.getSubListByName(list1, "number", "10000"));
		
		tempList = new ArrayList<Map<String,String>>();
		tempList.add(map1);
		assertEquals(tempList, ListUtility.getSubListByName(list1, "currency", "4,000"));
		
		tempList = new ArrayList<Map<String,String>>();
		assertEquals(tempList, ListUtility.getSubListByName(list1, "string", "value4"));
	}

	@Test
	public void testGetSubListByNameListStringStringArray() {
		prepareData();
		
		Map<String, String> map1 = new LinkedHashMap<String, String>();
		map1.put("group", "group1");
		map1.put("string", "value1");
		map1.put("number", "20000");
		map1.put("currency", "4,000");
		
		Map<String, String> map2 = new LinkedHashMap<String, String>();
		map2.put("group", "group1");
		map2.put("string", "value2");
		map2.put("number", "30000");
		map2.put("currency", "500");
		
		Map<String, String> map3 = new LinkedHashMap<String, String>();
		map3.put("group", "group3");
		map3.put("string", "value3");
		map3.put("number", "10000");
		map3.put("currency", "60");
		
		List<Map<String, String>> tempList = new ArrayList<Map<String,String>>();
		tempList.add(map1);
		tempList.add(map2);
		tempList.add(map3);
		String[] fieldValue = new String[]{"group1", "group3"};
		assertEquals(tempList, ListUtility.getSubListByName(list1, "group", fieldValue));
		
		tempList = new ArrayList<Map<String,String>>();
		tempList.add(map2);
		tempList.add(map3);
		fieldValue = new String[]{"value2", "value3"};
		assertEquals(tempList, ListUtility.getSubListByName(list1, "string", fieldValue));
		
		tempList = new ArrayList<Map<String,String>>();
		tempList.add(map1);
		tempList.add(map3);
		fieldValue = new String[]{"10000", "20000"};
		assertEquals(tempList, ListUtility.getSubListByName(list1, "number", fieldValue));
		
		tempList = new ArrayList<Map<String,String>>();
		tempList.add(map1);
		tempList.add(map2);
		fieldValue = new String[]{"4,000", "500"};
		assertEquals(tempList, ListUtility.getSubListByName(list1, "currency", fieldValue));
		
		tempList = new ArrayList<Map<String,String>>();
		fieldValue = new String[]{"value4", "value5"};
		assertEquals(tempList, ListUtility.getSubListByName(list1, "string", fieldValue));
	}
	
	@Test
	public void testGetSubListByNameListExcludeValueStringString() {
		prepareData();
		
		Map<String, String> map1 = new LinkedHashMap<String, String>();
		map1.put("group", "group1");
		map1.put("string", "value1");
		map1.put("number", "20000");
		map1.put("currency", "4,000");
		
		Map<String, String> map2 = new LinkedHashMap<String, String>();
		map2.put("group", "group1");
		map2.put("string", "value2");
		map2.put("number", "30000");
		map2.put("currency", "500");
		
		Map<String, String> map3 = new LinkedHashMap<String, String>();
		map3.put("group", "group3");
		map3.put("string", "value3");
		map3.put("number", "10000");
		map3.put("currency", "60");
		
		List<Map<String, String>> tempList = new ArrayList<Map<String,String>>();
		tempList.add(map3);
		assertEquals(tempList, ListUtility.getSubListByNameExcludeValue(list1, "group", "group1"));
		
		tempList = new ArrayList<Map<String,String>>();
		tempList.add(map1);
		tempList.add(map3);
		assertEquals(tempList, ListUtility.getSubListByNameExcludeValue(list1, "string", "value2"));
		
		tempList = new ArrayList<Map<String,String>>();
		tempList.add(map1);
		tempList.add(map2);
		assertEquals(tempList, ListUtility.getSubListByNameExcludeValue(list1, "number", "10000"));
		
		tempList = new ArrayList<Map<String,String>>();
		tempList.add(map2);
		tempList.add(map3);
		assertEquals(tempList, ListUtility.getSubListByNameExcludeValue(list1, "currency", "4,000"));
		
		tempList = new ArrayList<Map<String,String>>();
		tempList.add(map1);
		tempList.add(map2);
		tempList.add(map3);
		assertEquals(tempList, ListUtility.getSubListByNameExcludeValue(list1, "string", "value4"));
	}

	@Test
	public void testGetSubListByNameListExcludeValueStringStringArray() {
		prepareData();
		
		Map<String, String> map1 = new LinkedHashMap<String, String>();
		map1.put("group", "group1");
		map1.put("string", "value1");
		map1.put("number", "20000");
		map1.put("currency", "4,000");
		
		Map<String, String> map2 = new LinkedHashMap<String, String>();
		map2.put("group", "group1");
		map2.put("string", "value2");
		map2.put("number", "30000");
		map2.put("currency", "500");
		
		Map<String, String> map3 = new LinkedHashMap<String, String>();
		map3.put("group", "group3");
		map3.put("string", "value3");
		map3.put("number", "10000");
		map3.put("currency", "60");
		
		List<Map<String, String>> tempList = new ArrayList<Map<String,String>>();
		String[] fieldValue = new String[]{"group1", "group3"};
		assertEquals(tempList, ListUtility.getSubListByNameExcludeValue(list1, "group", fieldValue));
		
		tempList = new ArrayList<Map<String,String>>();
		tempList.add(map1);
		fieldValue = new String[]{"value2", "value3"};
		assertEquals(tempList, ListUtility.getSubListByNameExcludeValue(list1, "string", fieldValue));
		
		tempList = new ArrayList<Map<String,String>>();
		tempList.add(map2);
		fieldValue = new String[]{"10000", "20000"};
		assertEquals(tempList, ListUtility.getSubListByNameExcludeValue(list1, "number", fieldValue));
		
		tempList = new ArrayList<Map<String,String>>();
		tempList.add(map3);
		fieldValue = new String[]{"4,000", "500"};
		assertEquals(tempList, ListUtility.getSubListByNameExcludeValue(list1, "currency", fieldValue));
		
		tempList = new ArrayList<Map<String,String>>();
		tempList.add(map1);
		tempList.add(map2);
		tempList.add(map3);
		fieldValue = new String[]{"value4", "value5"};
		assertEquals(tempList, ListUtility.getSubListByNameExcludeValue(list1, "string", fieldValue));
	}

	@Test
	public void testRenameField() {
		prepareData();
		
		Map<String, String> map1 = new LinkedHashMap<String, String>();
		map1.put("group", "group1");
		map1.put("value", "value1");
		map1.put("number", "20000");
		map1.put("currency", "4,000");
		
		Map<String, String> map2 = new LinkedHashMap<String, String>();
		map2.put("group", "group1");
		map2.put("value", "value2");
		map2.put("number", "30000");
		map2.put("currency", "500");
		
		Map<String, String> map3 = new LinkedHashMap<String, String>();
		map3.put("group", "group3");
		map3.put("value", "value3");
		map3.put("number", "10000");
		map3.put("currency", "60");
		
		List<Map<String, String>> tempList = new ArrayList<Map<String,String>>();
		tempList.add(map1);
		tempList.add(map2);
		tempList.add(map3);
		
		assertEquals(tempList, ListUtility.renameField(list1, "string", "value"));
	}

	@Test
	public void testRemoveByName() {
		prepareData();
		
		Map<String, String> map1 = new LinkedHashMap<String, String>();
		map1.put("group", "group1");
		map1.put("string", "value1");
		map1.put("number", "20000");
		map1.put("currency", "4,000");
		
		Map<String, String> map2 = new LinkedHashMap<String, String>();
		map2.put("group", "group1");
		map2.put("string", "value2");
		map2.put("number", "30000");
		map2.put("currency", "500");
		
		Map<String, String> map3 = new LinkedHashMap<String, String>();
		map3.put("group", "group3");
		map3.put("string", "value3");
		map3.put("number", "10000");
		map3.put("currency", "60");
		
		List<Map<String, String>> tempList = new ArrayList<Map<String,String>>();
		tempList.add(map3);
		assertEquals(tempList, ListUtility.removeByName(list1, "group", "group1"));
		
		tempList = new ArrayList<Map<String,String>>();
		tempList.add(map1);
		tempList.add(map3);
		assertEquals(tempList, ListUtility.removeByName(list1, "string", "value2"));
		
		tempList = new ArrayList<Map<String,String>>();
		tempList.add(map1);
		tempList.add(map2);
		assertEquals(tempList, ListUtility.removeByName(list1, "number", "10000"));
		
		tempList = new ArrayList<Map<String,String>>();
		tempList.add(map2);
		tempList.add(map3);
		assertEquals(tempList, ListUtility.removeByName(list1, "currency", "4,000"));
		
		tempList = new ArrayList<Map<String,String>>();
		tempList.add(map1);
		tempList.add(map2);
		tempList.add(map3);
		assertEquals(tempList, ListUtility.removeByName(list1, "string", "value4"));
	}
	
	@Test
	public void testCopyField() {
		prepareData();
		Map<String, String> map1 = new LinkedHashMap<String, String>();
		map1.put("group", "group1");
		map1.put("string", "value1");
		map1.put("number", "20000");
		map1.put("currency", "4,000");
		map1.put("copyGroup", "group1");
		
		Map<String, String> map2 = new LinkedHashMap<String, String>();
		map2.put("group", "group1");
		map2.put("string", "value2");
		map2.put("number", "30000");
		map2.put("currency", "500");
		map2.put("copyGroup", "group1");
		
		Map<String, String> map3 = new LinkedHashMap<String, String>();
		map3.put("group", "group3");
		map3.put("string", "value3");
		map3.put("number", "10000");
		map3.put("currency", "60");
		map3.put("copyGroup", "group3");
		
		List<Map<String, String>> tempList = new ArrayList<Map<String,String>>();
		tempList.add(map1);
		tempList.add(map2);
		tempList.add(map3);
		
		assertEquals(tempList, ListUtility.copyField(list1, "group", "copyGroup"));
	}

	@Test
	public void testReplaceField() {
		prepareData();
		
		Map<String, String> map1 = new LinkedHashMap<String, String>();
		map1.put("group", "group1");
		map1.put("string", "value2");
		map1.put("number", "20000");
		map1.put("currency", "4,000");
		
		Map<String, String> map2 = new LinkedHashMap<String, String>();
		map2.put("group", "group1");
		map2.put("string", "value30000");
		map2.put("number", "30000");
		map2.put("currency", "500");
		
		Map<String, String> map3 = new LinkedHashMap<String, String>();
		map3.put("group", "group3");
		map3.put("string", "value1");
		map3.put("number", "10000");
		map3.put("currency", "60");
		
		List<Map<String, String>> tempList = new ArrayList<Map<String,String>>();
		tempList.add(map1);
		tempList.add(map2);
		tempList.add(map3);
		
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("value1", "value2");
		map.put("value2", "value(var.number)");
		map.put("value3", "value1");
		
		assertEquals(tempList, ListUtility.replaceField(list1, "string", map));
	}

	@Test
	public void testTransNewLinetoBR() {
		prepareData();
		
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("value1", "value1<br>value1");
		map.put("value2", "value2value2");
		map.put("value3", "value3<br>value3");
		List<Map<String, String>> tempList = ListUtility.replaceField(list1, "string", map);
		
		map = new LinkedHashMap<String, String>();
		map.put("value1", "value1\nvalue1");
		map.put("value2", "value2value2");
		map.put("value3", "value3\nvalue3");
		
		assertEquals(tempList, ListUtility.transNewLinetoBR(ListUtility.replaceField(list1, "string", map), "string"));
	}

	@Test
	public void testTransBRtoNewLine() {
		prepareData();
		
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("value1", "value1\nvalue1");
		map.put("value2", "value2value2");
		map.put("value3", "value3\nvalue3");
		
		List<Map<String, String>> tempList = ListUtility.replaceField(list1, "string", map);
		
		map = new LinkedHashMap<String, String>();
		map.put("value1", "value1<br>value1");
		map.put("value2", "value2value2");
		map.put("value3", "value3<br>value3");
		
		assertEquals(tempList, ListUtility.transBRtoNewLine(ListUtility.replaceField(list1, "string", map), "string"));
	}

	@Test
	public void testReplaceNulltoSpace() {
		Map<String, String> map1 = new LinkedHashMap<String, String>();
		map1.put("group", "group1");
		map1.put("string", "value1");
		map1.put("number", "20000");
		map1.put("currency", "4,000");
		
		Map<String, String> map2 = new LinkedHashMap<String, String>();
		map2.put("group", "group1");
		map2.put("string", null);
		map2.put("number", "30000");
		map2.put("currency", "500");
		
		Map<String, String> map3 = new LinkedHashMap<String, String>();
		map3.put("group", "group3");
		map3.put("string", "value3");
		map3.put("number", "10000");
		map3.put("currency", "60");
		
		Map<String, String> map4 = new LinkedHashMap<String, String>();
		map4.put("group", "group1");
		map4.put("string", "");
		map4.put("number", "30000");
		map4.put("currency", "500");
		
		List<Map<String, String>> tempList = new ArrayList<Map<String,String>>();
		tempList.add(map1);
		tempList.add(map2);
		tempList.add(map3);
		
		List<Map<String, String>> tempList2 = new ArrayList<Map<String,String>>();
		tempList2.add(map1);
		tempList2.add(map4);
		tempList2.add(map3);
		
		assertEquals(tempList2, ListUtility.replaceNulltoSpace(tempList, "string"));
	}

	@Test
	public void testReplaceNulltoZero() {
		Map<String, String> map1 = new LinkedHashMap<String, String>();
		map1.put("group", "group1");
		map1.put("string", "value1");
		map1.put("number", "20000");
		map1.put("currency", "4,000");
		
		Map<String, String> map2 = new LinkedHashMap<String, String>();
		map2.put("group", "group1");
		map2.put("string", "value2");
		map2.put("number", null);
		map2.put("currency", "500");
		
		Map<String, String> map3 = new LinkedHashMap<String, String>();
		map3.put("group", "group3");
		map3.put("string", "value3");
		map3.put("number", "10000");
		map3.put("currency", "60");
		
		Map<String, String> map4 = new LinkedHashMap<String, String>();
		map4.put("group", "group1");
		map4.put("string", "value2");
		map4.put("number", "0");
		map4.put("currency", "500");
		
		List<Map<String, String>> tempList = new ArrayList<Map<String,String>>();
		tempList.add(map1);
		tempList.add(map2);
		tempList.add(map3);
		
		List<Map<String, String>> tempList2 = new ArrayList<Map<String,String>>();
		tempList2.add(map1);
		tempList2.add(map4);
		tempList2.add(map3);
		
		assertEquals(tempList2, ListUtility.replaceNulltoZero(tempList, "number"));
	}

	@Test
	public void testTransCurrency() {
		prepareData();
		
		Map<String, String> map1 = new LinkedHashMap<String, String>();
		map1.put("group", "group1");
		map1.put("string", "value1");
		map1.put("number", "20,000");
		map1.put("currency", "4,000");
		
		Map<String, String> map2 = new LinkedHashMap<String, String>();
		map2.put("group", "group1");
		map2.put("string", "value2");
		map2.put("number", "30,000");
		map2.put("currency", "500");
		
		Map<String, String> map3 = new LinkedHashMap<String, String>();
		map3.put("group", "group3");
		map3.put("string", "value3");
		map3.put("number", "10,000");
		map3.put("currency", "60");
		
		List<Map<String, String>> tempList = new ArrayList<Map<String,String>>();
		tempList.add(map1);
		tempList.add(map2);
		tempList.add(map3);
		
		assertEquals(tempList, ListUtility.transCurrency(list1, "number"));
		assertEquals(list1, ListUtility.transCurrency(list1, "currency"));
		assertEquals(list1, ListUtility.transCurrency(list1, "string"));
	}

	@Test
	public void testTransString() {
		prepareData();
		
		Map<String, String> map1 = new LinkedHashMap<String, String>();
		map1.put("group", "group1");
		map1.put("string", "value1");
		map1.put("number", "24,000");
		map1.put("currency", "4,000");
		
		Map<String, String> map2 = new LinkedHashMap<String, String>();
		map2.put("group", "group1");
		map2.put("string", "value2");
		map2.put("number", "3500");
		map2.put("currency", "500");
		
		Map<String, String> map3 = new LinkedHashMap<String, String>();
		map3.put("group", "group3");
		map3.put("string", "value3");
		map3.put("number", "160");
		map3.put("currency", "60");
		
		List<Map<String, String>> tempList = new ArrayList<Map<String,String>>();
		tempList.add(map1);
		tempList.add(map2);
		tempList.add(map3);
		
		assertEquals(tempList, ListUtility.transString(list1, "number", "0000", "(var.currency)"));
		assertEquals(list1, ListUtility.transString(tempList, "number", "(var.currency)", "0000"));
	}

	@Test
	public void testReplaceVariable() {
		String originalString = "hello (var.userId)!!";
		String correctString = "hello George!!";
		
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("userId", "George");
		
		assertEquals(correctString, ListUtility.replaceVariable(map, originalString));
	}
	
	@Test
	public void testJoin() throws ListException {
		prepareData();
		
		Map<String, String> map1 = new LinkedHashMap<String, String>();
		map1.put("group", "group1");
		map1.put("string", "value1");
		map1.put("currency", "4,000");
		
		Map<String, String> map2 = new LinkedHashMap<String, String>();
		map2.put("group", "group1");
		map2.put("string", "value2");
		map2.put("currency", "500");
		
		Map<String, String> map3 = new LinkedHashMap<String, String>();
		map3.put("group", "group3");
		map3.put("string", "value3");
		map3.put("currency", "60");
		
		List<Map<String, String>> tempList = new ArrayList<Map<String,String>>();
		tempList.add(map1);
		tempList.add(map2);
		tempList.add(map3);
		
		Map<String, String> map4 = new LinkedHashMap<String, String>();
		map4.put("number", "20000");
		
		Map<String, String> map5 = new LinkedHashMap<String, String>();
		map5.put("number", "30000");
		
		Map<String, String> map6 = new LinkedHashMap<String, String>();
		map6.put("number", "10000");
		
		List<Map<String, String>> tempList2 = new ArrayList<Map<String,String>>();
		tempList2.add(map4);
		tempList2.add(map5);
		tempList2.add(map6);
		
		assertEquals(list1, ListUtility.join(tempList, tempList2));
		
		Map<String, String> map7 = new LinkedHashMap<String, String>();
		map7.put("number", "40000");
		tempList2.add(map7);
		
		try {
			ListUtility.join(tempList, tempList2);
			assertEquals(true, false);
		} catch(ListException lex) {
			assertEquals(true, true);
		}
	}

	@Test
	public void testInnerJoin() throws ListException {
		prepareData();
		
		Map<String, String> map1 = new LinkedHashMap<String, String>();
		map1.put("group", "group1");
		map1.put("string", "value1");
		map1.put("currency", "4,000");
		
		Map<String, String> map2 = new LinkedHashMap<String, String>();
		map2.put("group", "group1");
		map2.put("string", "value2");
		map2.put("currency", "500");
		
		Map<String, String> map3 = new LinkedHashMap<String, String>();
		map3.put("group", "group3");
		map3.put("string", "value3");
		map3.put("currency", "60");
		
		List<Map<String, String>> tempList = new ArrayList<Map<String,String>>();
		tempList.add(map1);
		tempList.add(map2);
		tempList.add(map3);
		
		Map<String, String> map4 = new LinkedHashMap<String, String>();
		map4.put("group", "group1");
		map4.put("string", "value1");
		map4.put("number", "20000");
		
		Map<String, String> map5 = new LinkedHashMap<String, String>();
		map5.put("group", "group1");
		map5.put("string", "value2");
		map5.put("number", "30000");
		
		Map<String, String> map6 = new LinkedHashMap<String, String>();
		map6.put("group", "group3");
		map6.put("string", "value3");
		map6.put("number", "10000");
		
		List<Map<String, String>> tempList2 = new ArrayList<Map<String,String>>();
		tempList2.add(map4);
		tempList2.add(map5);
		tempList2.add(map6);
		
		String[] keys = new String[]{"group", "string"};
		
		assertEquals(list1, ListUtility.innerJoin(tempList, tempList2, keys));
		
		Map<String, String> map7 = new LinkedHashMap<String, String>();
		map7.put("group", "group4");
		map7.put("string", "value4");
		map7.put("currency", "40,000");
		
		Map<String, String> map8 = new LinkedHashMap<String, String>();
		map8.put("group", "group5");
		map8.put("string", "value5");
		map8.put("number", "50000");
		
		tempList = new ArrayList<Map<String,String>>();
		tempList.add(map1);
		tempList.add(map2);
		tempList.add(map3);
		tempList.add(map7);
		
		tempList2 = new ArrayList<Map<String,String>>();
		tempList2.add(map4);
		tempList2.add(map5);
		tempList2.add(map6);
		tempList2.add(map8);
		
		assertEquals(list1, ListUtility.innerJoin(tempList, tempList2, keys));
		
		tempList = new ArrayList<Map<String,String>>();
		tempList.add(map1);
		tempList.add(map2);
		tempList.add(map3);
		
		tempList2 = new ArrayList<Map<String,String>>();
		tempList2.add(map4);
		tempList2.add(map5);
		tempList2.add(map6);
		tempList2.add(map8);
		
		assertEquals(list1, ListUtility.innerJoin(tempList, tempList2, keys));
		
		tempList = new ArrayList<Map<String,String>>();
		tempList.add(map1);
		tempList.add(map2);
		tempList.add(map3);
		tempList.add(map7);
		
		tempList2 = new ArrayList<Map<String,String>>();
		tempList2.add(map4);
		tempList2.add(map5);
		tempList2.add(map6);
		
		assertEquals(list1, ListUtility.innerJoin(tempList, tempList2, keys));
		
		Map<String, String> map9 = new LinkedHashMap<String, String>();
		map9.put("group", "group1");
		map9.put("number", "20000");
		
		Map<String, String> map10 = new LinkedHashMap<String, String>();
		map10.put("group", "group1");
		map10.put("number", "30000");
		
		Map<String, String> map11 = new LinkedHashMap<String, String>();
		map11.put("group", "group3");
		map11.put("number", "10000");
		
		tempList2 = new ArrayList<Map<String,String>>();
		tempList2.add(map9);
		tempList2.add(map10);
		tempList2.add(map11);
		
		try {
			ListUtility.innerJoin(tempList, tempList2, keys);
			assertEquals(true, false);
		} catch(ListException lex) {
			assertEquals(true, true);
		}
		
		Map<String, String> map12 = new LinkedHashMap<String, String>();
		map12.put("group", "group5");
		map12.put("string", "value5");
		map12.put("number", "20000");
		
		Map<String, String> map13 = new LinkedHashMap<String, String>();
		map13.put("group", "group6");
		map13.put("string", "value6");
		map13.put("number", "30000");
		
		Map<String, String> map14 = new LinkedHashMap<String, String>();
		map14.put("group", "group7");
		map14.put("string", "value7");
		map14.put("number", "10000");
		
		tempList2 = new ArrayList<Map<String,String>>();
		tempList2.add(map12);
		tempList2.add(map13);
		tempList2.add(map14);
		
		assertEquals(new ArrayList<Map<String,String>>(), ListUtility.innerJoin(tempList, tempList2, keys));
	}

	@Test
	public void testLeftJoin() throws ListException {
		Map<String, String> map1 = new LinkedHashMap<String, String>();
		map1.put("group", "group1");
		map1.put("string", "value1");
		map1.put("currency", "4,000");
		
		Map<String, String> map2 = new LinkedHashMap<String, String>();
		map2.put("group", "group1");
		map2.put("string", "value2");
		map2.put("currency", "500");
		
		Map<String, String> map3 = new LinkedHashMap<String, String>();
		map3.put("group", "group3");
		map3.put("string", "value3");
		map3.put("currency", "60");
		
		List<Map<String, String>> tempList = new ArrayList<Map<String,String>>();
		tempList.add(map1);
		tempList.add(map2);
		tempList.add(map3);
		
		Map<String, String> map4 = new LinkedHashMap<String, String>();
		map4.put("group", "group1");
		map4.put("string", "value1");
		map4.put("number", "20000");
		
		Map<String, String> map5 = new LinkedHashMap<String, String>();
		map5.put("group", "group1");
		map5.put("string", "value2");
		map5.put("number", "30000");
		
		List<Map<String, String>> tempList2 = new ArrayList<Map<String,String>>();
		tempList2.add(map4);
		tempList2.add(map5);
		
		Map<String, String> map6 = new LinkedHashMap<String, String>();
		map6.put("group", "group1");
		map6.put("string", "value1");
		map6.put("number", "20000");
		map6.put("currency", "4,000");
		
		Map<String, String> map7 = new LinkedHashMap<String, String>();
		map7.put("group", "group1");
		map7.put("string", "value2");
		map7.put("number", "30000");
		map7.put("currency", "500");
		
		Map<String, String> map8 = new LinkedHashMap<String, String>();
		map8.put("group", "group3");
		map8.put("string", "value3");
		map8.put("number", null);
		map8.put("currency", "60");
		
		List<Map<String, String>> tempList3 = new ArrayList<Map<String,String>>();
		tempList3.add(map6);
		tempList3.add(map7);
		tempList3.add(map8);
		
		String[] keys = new String[]{"group", "string"};
		
		assertEquals(tempList3, ListUtility.leftJoin(tempList, tempList2, keys));
	}
	
	@Test
	public void testRightJoin() throws ListException {
		Map<String, String> map1 = new LinkedHashMap<String, String>();
		map1.put("group", "group1");
		map1.put("string", "value1");
		map1.put("currency", "4,000");
		
		Map<String, String> map2 = new LinkedHashMap<String, String>();
		map2.put("group", "group1");
		map2.put("string", "value2");
		map2.put("currency", "500");
		
		List<Map<String, String>> tempList = new ArrayList<Map<String,String>>();
		tempList.add(map1);
		tempList.add(map2);
		
		Map<String, String> map3 = new LinkedHashMap<String, String>();
		map3.put("group", "group3");
		map3.put("string", "value3");
		map3.put("number", "10000");
		
		Map<String, String> map4 = new LinkedHashMap<String, String>();
		map4.put("group", "group1");
		map4.put("string", "value1");
		map4.put("number", "20000");
		
		Map<String, String> map5 = new LinkedHashMap<String, String>();
		map5.put("group", "group1");
		map5.put("string", "value2");
		map5.put("number", "30000");
		
		List<Map<String, String>> tempList2 = new ArrayList<Map<String,String>>();
		tempList2.add(map4);
		tempList2.add(map5);
		tempList2.add(map3);
		
		Map<String, String> map6 = new LinkedHashMap<String, String>();
		map6.put("group", "group1");
		map6.put("string", "value1");
		map6.put("number", "20000");
		map6.put("currency", "4,000");
		
		Map<String, String> map7 = new LinkedHashMap<String, String>();
		map7.put("group", "group1");
		map7.put("string", "value2");
		map7.put("number", "30000");
		map7.put("currency", "500");
		
		Map<String, String> map8 = new LinkedHashMap<String, String>();
		map8.put("group", "group3");
		map8.put("string", "value3");
		map8.put("number", "10000");
		map8.put("currency", null);
		
		List<Map<String, String>> tempList3 = new ArrayList<Map<String,String>>();
		tempList3.add(map6);
		tempList3.add(map7);
		tempList3.add(map8);
		
		String[] keys = new String[]{"group", "string"};
		
		assertEquals(tempList3, ListUtility.rightJoin(tempList, tempList2, keys));
	}
	
	@Test
	public void testFullOuterJoin() throws ListException {
		Map<String, String> map1 = new LinkedHashMap<String, String>();
		map1.put("group", "group1");
		map1.put("string", "value1");
		map1.put("currency", "4,000");
		
		Map<String, String> map2 = new LinkedHashMap<String, String>();
		map2.put("group", "group1");
		map2.put("string", "value2");
		map2.put("currency", "500");
		
		Map<String, String> map3 = new LinkedHashMap<String, String>();
		map3.put("group", "group3");
		map3.put("string", "value3");
		map3.put("currency", "60");
		
		List<Map<String, String>> tempList = new ArrayList<Map<String,String>>();
		tempList.add(map1);
		tempList.add(map2);
		tempList.add(map3);
		
		Map<String, String> map4 = new LinkedHashMap<String, String>();
		map4.put("group", "group1");
		map4.put("string", "value1");
		map4.put("number", "20000");
		
		Map<String, String> map5 = new LinkedHashMap<String, String>();
		map5.put("group", "group1");
		map5.put("string", "value2");
		map5.put("number", "30000");
		
		Map<String, String> map6 = new LinkedHashMap<String, String>();
		map6.put("group", "group4");
		map6.put("string", "value4");
		map6.put("number", "40000");
		
		List<Map<String, String>> tempList2 = new ArrayList<Map<String,String>>();
		tempList2.add(map4);
		tempList2.add(map5);
		tempList2.add(map6);
		
		Map<String, String> map7 = new LinkedHashMap<String, String>();
		map7.put("group", "group1");
		map7.put("string", "value1");
		map7.put("number", "20000");
		map7.put("currency", "4,000");
		
		Map<String, String> map8 = new LinkedHashMap<String, String>();
		map8.put("group", "group1");
		map8.put("string", "value2");
		map8.put("number", "30000");
		map8.put("currency", "500");
		
		Map<String, String> map9 = new LinkedHashMap<String, String>();
		map9.put("group", "group3");
		map9.put("string", "value3");
		map9.put("number", null);
		map9.put("currency", "60");
		
		Map<String, String> map10 = new LinkedHashMap<String, String>();
		map10.put("group", "group4");
		map10.put("string", "value4");
		map10.put("number", "40000");
		map10.put("currency", null);
		
		List<Map<String, String>> tempList3 = new ArrayList<Map<String,String>>();
		tempList3.add(map7);
		tempList3.add(map8);
		tempList3.add(map9);
		tempList3.add(map10);
		
		String[] keys = new String[]{"group", "string"};
		
		assertEquals(tempList3, ListUtility.fullOuterJoin(tempList, tempList2, keys));
	}

	@Test
	public void testToMap() {
		prepareData();
		
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("value1", "20000");
		map.put("value2", "30000");
		map.put("value3", "10000");
		
		assertEquals(map, ListUtility.toMap(list1, "string", "number"));
	}

	@Test
	public void testToArray() {
		prepareData();
		
		String[] values = new String[]{"value1", "value2", "value3"};
		
		assertArrayEquals(values, ListUtility.toArray(list1, "string"));
	}

	@Test
	public void testGroupByField() {
		prepareData();
		
		Map<String, String> map1 = new LinkedHashMap<String, String>();
		map1.put("group", "group1");
		map1.put("string", "value1<br>value2");
		map1.put("number", "20000<br>30000");
		map1.put("currency", "4,000<br>500");
		
		Map<String, String> map3 = new LinkedHashMap<String, String>();
		map3.put("group", "group3");
		map3.put("string", "value3");
		map3.put("number", "10000");
		map3.put("currency", "60");
		
		List<Map<String, String>> tempList = new ArrayList<Map<String,String>>();
		tempList.add(map1);
		tempList.add(map3);
		
		String[] groupByFields = new String[]{"group"};
		
		assertEquals(tempList, ListUtility.groupByField(list1, groupByFields));
	}

	@Test
	public void testToStringList() {
		prepareData();
		
		String result = "[{\"group\":\"group1\",\"string\":\"value1\",\"number\":\"20000\",\"currency\":\"4,000\"},{\"group\":\"group1\",\"string\":\"value2\",\"number\":\"30000\",\"currency\":\"500\"},{\"group\":\"group3\",\"string\":\"value3\",\"number\":\"10000\",\"currency\":\"60\"}]";
		
		assertEquals(result, ListUtility.toString(list1));
	}

	@Test
	public void testToListString() throws JSONException {
		prepareData();
		
		String formatString = "[{\"group\":\"group1\",\"string\":\"value1\",\"number\":\"20000\",\"currency\":\"4,000\"},{\"group\":\"group1\",\"string\":\"value2\",\"number\":\"30000\",\"currency\":\"500\"},{\"group\":\"group3\",\"string\":\"value3\",\"number\":\"10000\",\"currency\":\"60\"}]";
		
		assertEquals(list1, ListUtility.toList(formatString));
	}

}
