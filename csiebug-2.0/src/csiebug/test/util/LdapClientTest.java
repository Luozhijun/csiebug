package csiebug.test.util;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.naming.ContextNotEmptyException;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;

import org.apache.commons.codec.DecoderException;
import org.junit.Test;

import csiebug.util.DesCoder;
import csiebug.util.ldap.LdapClient;

public class LdapClientTest {
	String url = "ldap://localhost:10389";
	String dn = "uid=admin,ou=system";
	String key = "123456789012345678901234123456789012345678901234";
	String encryptPassword = "cc4e1b57691c47fc";
	LdapClient client;
	
	private void init() throws NamingException, InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, DecoderException {
		client = new LdapClient(url, dn, DesCoder.decryptCode(encryptPassword, key));
	}
	
	@Test
	public void testCreateOrganization() throws NamingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, DecoderException {
		init();
		
		String baseDN = "ou = csiebug";
		String id = "testOU";
		
		//建立一個組織
		client.createOrganization(baseDN, id);
		
		Map<String, Object> map = client.lookupOrganization(baseDN, id);
		
		//看看是否真的建立
		assertEquals(id, map.get("ou"));
		
		//如果再create一次,看有沒有把Exception做處理
		try {
			client.createOrganization(baseDN, id);
			
			assertEquals(true, true);
		} catch(NameAlreadyBoundException nabex) {
			assertEquals(true, false);
		}
		
		//測試完成,把組織刪掉
		client.deleteOrganization(baseDN, id);
		
		String baseDN2 = "ou = " + id + ", " + baseDN;
		String id2 = "testOU2";
		
		//建立一個組織(parent不存在),應該產生NameNotFoundException來讓我知道
		try {
			client.createOrganization(baseDN2, id2);
			assertEquals(true, false);
		} catch(NameNotFoundException nnfex) {
			assertEquals(true, true);
		}
		
		//再用recursive的方式再建立一次
		try {
			client.createOrganization(baseDN2, id2, true);
			assertEquals(true, true);
		} catch(NameNotFoundException nnfex) {
			assertEquals(true, false);
		}
		
		//測試完成,把組織刪掉
		client.deleteOrganization(baseDN, id, true);
	}
	
	@Test
	public void testCreateOrganizationAttribute() throws NamingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, DecoderException {
		init();
		
		String baseDN = "ou = csiebug";
		String id = "testOU";
		String description = "測試組織";
		
		//建立一個組織
		Map<String, String> attributes = new LinkedHashMap<String, String>();
		attributes.put("description", description);
		client.createOrganization(baseDN, id, attributes);
		
		Map<String, Object> map = client.lookupOrganization(baseDN, id);
		
		//看看是否真的建立
		assertEquals(id, map.get("ou"));
		assertEquals(description, map.get("description"));
		
		//如果再create一次,看有沒有把Exception做處理
		try {
			client.createOrganization(baseDN, id, attributes);
			
			assertEquals(true, true);
		} catch(NameAlreadyBoundException nabex) {
			assertEquals(true, false);
		}
		
		//測試完成,把組織刪掉
		client.deleteOrganization(baseDN, id);
		
		String baseDN2 = "ou = " + id + ", " + baseDN;
		String id2 = "testOU2";
		
		//建立一個組織(parent不存在),應該產生NameNotFoundException來讓我知道
		try {
			client.createOrganization(baseDN2, id2, attributes);
			assertEquals(true, false);
		} catch(NameNotFoundException nnfex) {
			assertEquals(true, true);
		}
		
		//再用recursive的方式再建立一次
		try {
			client.createOrganization(baseDN2, id2, attributes, true);
			assertEquals(true, true);
		} catch(NameNotFoundException nnfex) {
			assertEquals(true, false);
		}
		
		//測試完成,把組織刪掉
		client.deleteOrganization(baseDN, id, true);
	}
	
	@Test
	public void testModifyOrganization() throws NamingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, DecoderException {
		init();
		
		String baseDN = "ou = csiebug";
		String id = "testOU";
		String description = "測試組織";
		String description2 = "測試組織2";
		
		Map<String, String> attributes = new LinkedHashMap<String, String>();
		attributes.put("description", description);
		
		Map<String, String> attributes2 = new LinkedHashMap<String, String>();
		attributes2.put("description", description2);
		
		//直接修改不存在的組織
		try {
			client.modifyOrganization(baseDN, id, attributes);
			assertEquals(false, true);
		} catch(NameNotFoundException nnfex) {
			assertEquals(false, false);
		}
		
		//建立一個組織
		client.createOrganization(baseDN, id);
		
		Map<String, Object> map = client.lookupOrganization(baseDN, id);
		
		//看看是否真的建立
		assertEquals(id, map.get("ou"));
		assertNull(map.get("description"));
		
		//修改組織(加一個新屬性)
		client.modifyOrganization(baseDN, id, attributes);
		
		map = client.lookupOrganization(baseDN, id);
		
		//看看是否真的建立
		assertEquals(id, map.get("ou"));
		assertEquals(description, map.get("description"));
		
		//修改組織(修改屬性)
		client.modifyOrganization(baseDN, id, attributes2);
		
		map = client.lookupOrganization(baseDN, id);
		
		//看看是否真的建立
		assertEquals(id, map.get("ou"));
		assertEquals(description2, map.get("description"));
		
		//測試完成,把組織刪掉
		client.deleteOrganization(baseDN, id);
	}
	
	@Test
	public void testDeleteOrganization() throws NamingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, DecoderException {
		init();
		
		String baseDN = "ou = csiebug";
		String id = "testOU";
		
		//建立一個組織
		client.createOrganization(baseDN, id);
		
		Map<String, Object> map = client.lookupOrganization(baseDN, id);
		
		//看看是否真的建立
		assertEquals(id, map.get("ou"));
		
		//刪除組織
		client.deleteOrganization(baseDN, id);
		
		//查查看是否刪除成功
		map = client.lookupOrganization(baseDN, id);
		assertNull(map);
		
		String baseDN2 = "ou = " + id + ", " + baseDN;
		String id2 = "testOU2";
		
		//建立兩層組織
		client.createOrganization(baseDN2, id2, true);
		
		//測試刪除非leaf的組織,應該產生ContextNotEmptyException來讓我知道
		try {
			client.deleteOrganization(baseDN, id);
			assertEquals(true, false);
		} catch(ContextNotEmptyException cneex) {
			assertEquals(true, true);
		}
		
		//再測試用recursive的方式刪除非leaf的組織
		try {
			client.deleteOrganization(baseDN, id, true);
			assertEquals(true, true);
		} catch(ContextNotEmptyException cneex) {
			assertEquals(true, false);
		}
		
		//查查看是否刪除成功
		assertNull(client.lookupOrganization(baseDN, id));
		assertNull(client.lookupOrganization(baseDN2, id2));
	}
	
	@Test
	public void testLookupOrganization() throws NamingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, DecoderException {
		init();
		
		String baseDN = "ou = csiebug";
		String id = "testOU";
		String baseDN2 = "ou = " + id + ", " + baseDN;
		String id2 = "testOU2";
		
		//建立兩層組織
		client.createOrganization(baseDN2, id2, true);
		
		//從root去lookup,並不會找subtree
		Map<String, Object> map = client.lookupOrganization(baseDN, id2);
		assertNull(map);
		
		//從baseDN2去找
		map = client.lookupOrganization(baseDN2, id2);
		assertEquals(id2, map.get("ou"));
		
		//測試完成刪除組織
		client.deleteOrganization(baseDN, id, true);
	}
	
	@Test
	public void testSearchOrganization() throws NamingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, DecoderException {
		init();
		
		String baseDN = "ou = csiebug";
		String id = "testOU";
		String baseDN2 = "ou = " + id + ", " + baseDN;
		String id2 = "testOU2";
		
		//建立組織(直接建立最下層目錄)
		client.createOrganization(baseDN2, id2, true);
		
		//從baseDN去search
		List<Map<String, String>> list = client.searchOrganization(baseDN, id2);
		assertEquals(1, list.size());
		
		//測試完成刪除組織(直接刪除上層目錄)
		client.deleteOrganization(baseDN, id, true);
	}
	
	@Test
	public void testCreateUser() throws NamingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, DecoderException {
		init();
		
		String baseDN = "ou = csiebug";
		String id = "test2";
		String cn = "George Tsai";
		String sn = "Tsai";
		
		//建立一個使用者
		client.createUser(baseDN, id, cn, sn);
		
		Map<String, Object> map = client.lookupUser(baseDN, id);
		
		//看看是否真的建立
		assertEquals(id, map.get("uid"));
		assertEquals(cn, map.get("cn"));
		assertEquals(sn, map.get("sn"));
		
		//如果再create一次,看有沒有把Exception做處理
		try {
			client.createUser(baseDN, id, cn, sn);
			
			assertEquals(true, true);
		} catch(NameAlreadyBoundException nabex) {
			assertEquals(true, false);
		}
		
		//測試完成,把使用者刪掉
		client.deleteUser(baseDN, id);
		
		String baseDN2 = "ou = " + id + ", " + baseDN;
		
		//建立一個使用者(parent組織不存在),應該產生NameNotFoundException來讓我知道
		try {
			client.createUser(baseDN2, id, cn, sn);
			assertEquals(true, false);
		} catch(NameNotFoundException nnfex) {
			assertEquals(true, true);
		}
		
		//再用recursive的方式再建立一次
		try {
			client.createUser(baseDN2, id, cn, sn, true);
			assertEquals(true, true);
		} catch(NameNotFoundException nnfex) {
			assertEquals(true, false);
		}
		
		//測試完成,把組織刪掉
		client.deleteOrganization(baseDN, id, true);
	}
	
	@Test
	public void testCreateUserAttribute() throws NamingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, DecoderException {
		init();
		
		String baseDN = "ou = csiebug";
		String id = "test2";
		String cn = "George Tsai";
		String sn = "Tsai";
		String description = "阿毛";
		
		//建立一個使用者
		Map<String, String> attributes = new LinkedHashMap<String, String>();
		attributes.put("description", description);
		client.createUser(baseDN, id, cn, sn, attributes);
		
		Map<String, Object> map = client.lookupUser(baseDN, id);
		
		//看看是否真的建立
		assertEquals(id, map.get("uid"));
		assertEquals(cn, map.get("cn"));
		assertEquals(sn, map.get("sn"));
		assertEquals(description, map.get("description"));
		
		//如果再create一次,看有沒有把Exception做處理
		try {
			client.createUser(baseDN, id, cn, sn, attributes);
			
			assertEquals(true, true);
		} catch(NameAlreadyBoundException nabex) {
			assertEquals(true, false);
		}
		
		//測試完成,把使用者刪掉
		client.deleteUser(baseDN, id);
		
		String baseDN2 = "ou = " + id + ", " + baseDN;
		
		//建立一個使用者(parent組織不存在),應該產生NameNotFoundException來讓我知道
		try {
			client.createUser(baseDN2, id, cn, sn, attributes);
			assertEquals(true, false);
		} catch(NameNotFoundException nnfex) {
			assertEquals(true, true);
		}
		
		//再用recursive的方式再建立一次
		try {
			client.createUser(baseDN2, id, cn, sn, attributes, true);
			assertEquals(true, true);
		} catch(NameNotFoundException nnfex) {
			assertEquals(true, false);
		}
		
		//測試完成,把組織刪掉
		client.deleteOrganization(baseDN, id, true);
	}
	
	@Test
	public void testCreateUserNickNamePassword() throws NamingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, DecoderException {
		init();
		
		String baseDN = "ou = csiebug";
		String id = "test2";
		String cn = "George Tsai";
		String sn = "Tsai";
		String nickname = "阿毛";
		String password = "password";
		
		//建立一個使用者
		client.createUser(baseDN, id, cn, sn, nickname, password);
		
		Map<String, Object> map = client.lookupUser(baseDN, id);
		
		//看看是否真的建立
		assertEquals(id, map.get("uid"));
		assertEquals(cn, map.get("cn"));
		assertEquals(sn, map.get("sn"));
		assertEquals(nickname, map.get("displayname"));
		
		//如果再create一次,看有沒有把Exception做處理
		try {
			client.createUser(baseDN, id, cn, sn, nickname, password);
			
			assertEquals(true, true);
		} catch(NameAlreadyBoundException nabex) {
			assertEquals(true, false);
		}
		
		//測試完成,把使用者刪掉
		client.deleteUser(baseDN, id);
		
		String baseDN2 = "ou = " + id + ", " + baseDN;
		
		//建立一個使用者(parent組織不存在),應該產生NameNotFoundException來讓我知道
		try {
			client.createUser(baseDN2, id, cn, sn, nickname, password);
			assertEquals(true, false);
		} catch(NameNotFoundException nnfex) {
			assertEquals(true, true);
		}
		
		//再用recursive的方式再建立一次
		try {
			client.createUser(baseDN2, id, cn, sn, nickname, password, true);
			assertEquals(true, true);
		} catch(NameNotFoundException nnfex) {
			assertEquals(true, false);
		}
		
		//測試完成,把組織刪掉
		client.deleteOrganization(baseDN, id, true);
	}
	
	@Test
	public void testModifyUser() throws NamingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, DecoderException {
		init();
		
		String baseDN = "ou = csiebug";
		String id = "test2";
		String cn = "George Tsai";
		String sn = "Tsai";
		String description = "阿毛";
		String description2 = "阿毛2";
		
		Map<String, String> attributes = new LinkedHashMap<String, String>();
		attributes.put("description", description);
		
		Map<String, String> attributes2 = new LinkedHashMap<String, String>();
		attributes2.put("description", description2);
		
		//直接修改不存在的使用者
		try {
			client.modifyUser(baseDN, id, attributes);
			assertEquals(false, true);
		} catch(NameNotFoundException nnfex) {
			assertEquals(false, false);
		}
		
		//建立一個使用者
		client.createUser(baseDN, id, cn, sn);
		
		Map<String, Object> map = client.lookupUser(baseDN, id);
		
		//看看是否真的建立
		assertEquals(id, map.get("uid"));
		assertEquals(cn, map.get("cn"));
		assertEquals(sn, map.get("sn"));
		assertNull(map.get("description"));
		
		//修改使用者(加一個新屬性)
		client.modifyUser(baseDN, id, attributes);
		
		map = client.lookupUser(baseDN, id);
		
		//看看是否真的建立
		assertEquals(id, map.get("uid"));
		assertEquals(cn, map.get("cn"));
		assertEquals(sn, map.get("sn"));
		assertEquals(description, map.get("description"));
		
		//修改使用者(修改屬性)
		client.modifyUser(baseDN, id, attributes2);
		
		map = client.lookupUser(baseDN, id);
		
		//看看是否真的建立
		assertEquals(id, map.get("uid"));
		assertEquals(cn, map.get("cn"));
		assertEquals(sn, map.get("sn"));
		assertEquals(description2, map.get("description"));
		
		//測試完成,把使用者刪掉
		client.deleteUser(baseDN, id);
	}
	
	@Test
	public void testDeleteUser() throws NamingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, DecoderException {
		init();
		
		String baseDN = "ou = csiebug";
		String id = "test2";
		String cn = "George Tsai";
		String sn = "Tsai";
		
		//建立一個使用者
		client.createUser(baseDN, id, cn, sn);
		
		Map<String, Object> map = client.lookupUser(baseDN, id);
		
		//看看是否真的建立
		assertEquals(id, map.get("uid"));
		assertEquals(cn, map.get("cn"));
		assertEquals(sn, map.get("sn"));
		
		//刪除使用者
		client.deleteUser(baseDN, id);
		
		//查查看是否刪除成功
		map = client.lookupUser(baseDN, id);
		
		assertNull(map);
	}
	
	@Test
	public void testLookupUser() throws NamingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, DecoderException {
		init();
		
		String baseDN = "ou = csiebug";
		String ouId = "testOU";
		String baseDN2 = "ou = " + ouId + ", " + baseDN;
		String id = "test2";
		String cn = "George Tsai";
		String sn = "Tsai";
		
		//建立一個使用者
		client.createUser(baseDN2, id, cn, sn, true);
		
		//從baseDN去lookup,並不會找subtree
		Map<String, Object> map = client.lookupUser(baseDN, id);
		assertNull(map);
		
		//從baseDN2去找
		map = client.lookupUser(baseDN2, id);
		assertEquals(id, map.get("uid"));
		assertEquals(cn, map.get("cn"));
		assertEquals(sn, map.get("sn"));
		
		//測試完成刪除組織
		client.deleteOrganization(baseDN, ouId, true);
	}
	
	@Test
	public void testSearchUser() throws NamingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, DecoderException {
		init();
		
		String baseDN = "ou = csiebug";
		String ouId = "testOU";
		String baseDN2 = "ou = " + ouId + ", " + baseDN;
		String id = "test2";
		String cn = "George Tsai";
		String sn = "Tsai";
		
		//建立一個使用者
		client.createUser(baseDN2, id, cn, sn, true);
		
		//從baseDN去search
		List<Map<String, String>> list = client.searchUser(baseDN, id);
		assertEquals(1, list.size());
		
		//測試完成刪除組織
		client.deleteOrganization(baseDN, ouId, true);
	}
	
//	@Test
//	public void testSearchForTK() throws NamingException {
//		client = new LdapClient("ldap://172.16.7.71:389/", "edward_chiang2@hyperkube.com", "ebp.955.twn");
//		
//		List<Map<String, String>> list = client.search("OU=teamkube.com,DC=hyperkube,DC=com", "(objectClass=user)");
//		
//		for(int i = 0; i < list.size(); i++) {
//			Map<String, String> map = list.get(i);
//			
//			Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
//			
//			while(iterator.hasNext()) {
//				Entry<String, String> entry = iterator.next();
//				
//				if(entry.getKey().equalsIgnoreCase("sAMAccountName") || entry.getKey().startsWith("userPrincipalName")) {
//					System.out.println(entry.getKey() + ":" + entry.getValue());
//				}
//			}
//			
//			System.out.println("*************");
//		}
//	}
}
