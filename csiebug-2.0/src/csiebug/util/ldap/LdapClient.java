/**
 * Copyright (c) 2010 George Tsai. All rights reserved.
 * For more information on the George Tsai, please see http://www.pixnet.net/blog/csiebug
 */
package csiebug.util.ldap;

import javax.naming.Context;
import javax.naming.ContextNotEmptyException;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.directory.BasicAttributes;

import org.apache.log4j.Logger;

import com.sun.jndi.ldap.LdapCtx;

import csiebug.util.AssertUtility;
import csiebug.util.ListUtility;
import csiebug.util.ShaEncoder;
import csiebug.util.StringUtility;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author George_Tsai
 * @version 2010/7/8
 */
public class LdapClient {
	private DirContext ctx;
	private Logger logger = Logger.getLogger(this.getClass());
	
	public LdapClient(String url, String adminDN, String password) throws NamingException {
		Hashtable<String, String> env = new Hashtable<String, String>();
	    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	    env.put(Context.PROVIDER_URL, url);
	    env.put(Context.SECURITY_PRINCIPAL, adminDN);
	    env.put(Context.SECURITY_CREDENTIALS, password);
	    
	    ctx = new InitialDirContext(env);
	}
	
	private String getOrganizationDN(String baseDN, String id) {
		String dn = "ou = " + id;
		if(!baseDN.trim().equals("")) {
			dn = dn + ", " + baseDN;
		}
		
		return dn;
	}
	
	private String getUserDN(String baseDN, String id) {
		String dn = "uid = " + id;
		if(!baseDN.trim().equals("")) {
			dn = dn + ", " + baseDN;
		}
		
		return dn;
	}
	
	private String[] parseDN(String dn) {
		String[] parse = new String[2];
		
		String[] dns = dn.split(",");
		
		StringBuffer baseDN = new StringBuffer();
		for(int i = 1; i < dns.length; i++) {
			if(!dns[i].trim().equals("")) {
				if(i != 1) {
					baseDN.append(", ");
				}
				
				baseDN.append(dns[i]);
			}
		}
		
		parse[0] = dns[0];
		parse[1] = baseDN.toString();
		
		return parse;
	}
	
	/**
	 * lookup
	 * @param dn
	 * @return
	 * @throws NamingException
	 */
	public Map<String, Object> lookup(String dn) throws NamingException {
		String[] parse = parseDN(dn);
		String baseDN = parse[1];
		String id = parse[0];
		
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		try {
			Attributes attributes = ((LdapCtx)ctx.lookup(baseDN)).getAttributes(id);
	        NamingEnumeration<String> attributeNames = attributes.getIDs();
	        
	        while(attributeNames.hasMore()) {
	        	String attributeName = attributeNames.next();
	        	map.put(attributeName, attributes.get(attributeName).get());
	        }
		} catch(NameNotFoundException nnfex) {
			logger.info("Name not found!!", nnfex);
			return null;
		}
        
		return map;
	}
	
	/**
	 * lookup organization
	 * @param baseDN
	 * @param id
	 * @return
	 * @throws NamingException
	 */
	public Map<String, Object> lookupOrganization(String baseDN, String id) throws NamingException {
		return lookup(getOrganizationDN(baseDN, id));
	}
	
	/**
	 * look user
	 * @param baseDN
	 * @param id
	 * @return
	 * @throws NamingException
	 */
	public Map<String, Object> lookupUser(String baseDN, String id) throws NamingException {
		return lookup(getUserDN(baseDN, id));
	}
	
	/**
	 * search
	 * @param baseDN
	 * @param filter
	 * @return
	 * @throws NamingException
	 */
	public List<Map<String, String>> search(String baseDN, String filter) throws NamingException {
		SearchControls controls = new SearchControls();
	    controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	    NamingEnumeration<SearchResult> results = ctx.search(baseDN, filter, controls);
	    
		return ListUtility.toList(results);
	}
	
	/**
	 * search organization(objectClass = organizationalUnit)
	 * @param baseDN
	 * @param userId
	 * @return
	 * @throws NamingException
	 */
	public List<Map<String, String>> searchOrganization(String baseDN, String id) throws NamingException {
		String filter = "";
		
		//LDAP的運算式中間不要有空白
		if(AssertUtility.isNotNullAndNotSpace(id)) {
			filter = "(ou=" + id + ")";
		}
		
		return search(baseDN, "(&(objectClass=organizationalUnit)" + filter + ")");
	}
	
	/**
	 * search user(objectClass = person)
	 * @param baseDN
	 * @param userId
	 * @return
	 * @throws NamingException
	 */
	public List<Map<String, String>> searchUser(String baseDN, String id) throws NamingException {
		String filter = "";
		
		//LDAP的運算式中間不要有空白
		if(AssertUtility.isNotNullAndNotSpace(id)) {
			filter = "(uid=" + id + ")";
		}
		
		return search(baseDN, "(&(objectClass=person)" + filter + ")");
	}
	
	/**
	 * create subContext
	 * @param dn
	 * @param attributes
	 * @throws NamingException
	 */
	public void createSubcontext(String dn, Attributes attributes) throws NamingException {
		try {
			ctx.createSubcontext(dn, attributes);
		} catch(NameAlreadyBoundException nabex) {
			//表示已經存在,不當他是錯誤
			logger.info("Name already exists!!", nabex);
		}
	}
	
	/**
	 * unbind object
	 * @param dn
	 * @throws NamingException
	 */
	public void unbind(String dn) throws NamingException {
		ctx.unbind(dn);
	}
	
	/**
	 * modify attributes
	 * @param dn
	 * @param attributes
	 * @throws NamingException
	 */
	public void modifyAttributes(String dn, Attributes attributes) throws NamingException {
		ctx.modifyAttributes(dn, DirContext.REPLACE_ATTRIBUTE, attributes);
	}
	
	/**
	 * create organization(預設不recursive)
	 * @param baseDN
	 * @param id
	 * @throws NamingException
	 */
	public void createOrganization(String baseDN, String id) throws NamingException {
		createOrganization(baseDN, id, false);
	}
	
	/**
	 * create organization
	 * @param parentDN
	 * @param organizationId
	 * @throws NamingException
	 */
	public void createOrganization(String baseDN, String id, boolean recursive) throws NamingException {
		createOrganization(baseDN, id, null, recursive);
	}
	
	/**
	 * create organization(預設不recursive)
	 * @param baseDN
	 * @param id
	 * @param map
	 * @throws NamingException
	 */
	public void createOrganization(String baseDN, String id, Map<String, String> map) throws NamingException {
		createOrganization(baseDN, id, map, false);
	}
	
	/**
	 * create organization
	 * @param baseDN
	 * @param id
	 * @param map
	 * @throws NamingException
	 */
	public void createOrganization(String baseDN, String id, Map<String, String> map, boolean recursive) throws NamingException {
		Attributes attributes = new BasicAttributes();
		//必要屬性
		Attribute objectClass = new BasicAttribute("objectClass");
		objectClass.add("top");
		objectClass.add("organizationalUnit");
		attributes.put(objectClass);
		attributes.put("ou", id);
		
		//其他屬性
		if(map != null) {
			Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
			
			while(iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				
				//扣掉必要屬性
				if(!entry.getKey().equalsIgnoreCase("objectClass") &&
				   !entry.getKey().equalsIgnoreCase("ou")) {
					attributes.put(entry.getKey(), entry.getValue());
				}
			}
		}
		
		try {
			//為了效率,直接建立組織,當NameNotFoundException發生時,再去recursive建立上層組織
			createSubcontext(getOrganizationDN(baseDN, id), attributes);
		} catch(NameNotFoundException nnfex) {
			if(recursive) {
				//表示之前的parent也不存在
				//幫忙建立之前的parent
				String[] parse = parseDN(baseDN);
				String subBaseDN = parse[1];
				String subId = StringUtility.ltrim(parse[0].split("=")[1]).trim();
				createOrganization(subBaseDN, subId, recursive);
				
				//之前的都建完,再建立此組織
				createSubcontext(getOrganizationDN(baseDN, id), attributes);
			} else {
				//不要recursive的話,要把exception丟出去,讓外層有對應的處理
				throw nnfex;
			}
		}
	}
	
	/**
	 * create user(預設不recursive)
	 * @param baseDN
	 * @param id
	 * @param commonName
	 * @param surname
	 * @throws NamingException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public void createUser(String baseDN, String id, String commonName, String surname) throws NamingException, NoSuchAlgorithmException, UnsupportedEncodingException {
		createUser(baseDN, id, commonName, surname, false);
	}
	
	/**
	 * create user
	 * @param parentDN
	 * @param userId
	 * @throws NamingException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public void createUser(String baseDN, String id, String commonName, String surname, boolean recursive) throws NamingException, NoSuchAlgorithmException, UnsupportedEncodingException {
		createUser(baseDN, id, commonName, surname, null, recursive);
	}
	
	/**
	 * create user(預設不recursive)
	 * @param baseDN
	 * @param id
	 * @param commonName
	 * @param surname
	 * @param map
	 * @throws NamingException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public void createUser(String baseDN, String id, String commonName, String surname, Map<String, String> map) throws NamingException, NoSuchAlgorithmException, UnsupportedEncodingException {
		createUser(baseDN, id, commonName, surname, map, false);
	}
	
	/**
	 * create user
	 * @param parentDN
	 * @param userId
	 * @throws NamingException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("deprecation")
	public void createUser(String baseDN, String id, String commonName, String surname, Map<String, String> map, boolean recursive) throws NamingException, NoSuchAlgorithmException, UnsupportedEncodingException {
		String dn = getUserDN(baseDN, id);
		
		Attributes attributes = new BasicAttributes(true);
		
		//必要屬性
		Attribute objectClass = new BasicAttribute("objectClass");
		objectClass.add("inetOrgPerson");
		objectClass.add("organizationalPerson");
		objectClass.add("person");
		objectClass.add("tlsKeyInfo");
		objectClass.add("top");
		attributes.put(objectClass);
		attributes.put("cn", commonName);
		attributes.put("keyalgorithm", "RSA");
		attributes.put("privatekey", ShaEncoder.getSHA256String(Calendar.getInstance().toString()));
		attributes.put("privatekeyformat", "PKCS#8");
		attributes.put("publickey", dn);
		attributes.put("publickeyformat", "X.509");
		attributes.put("sn", surname);
		attributes.put("uid", id);
		
		//其他屬性
		if(map != null) {
			Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
			
			while(iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				
				//扣掉必要屬性
				if(!entry.getKey().equalsIgnoreCase("objectClass") &&
				   !entry.getKey().equalsIgnoreCase("cn") &&
				   !entry.getKey().equalsIgnoreCase("keyalgorithm") &&
				   !entry.getKey().equalsIgnoreCase("privatekey") &&
				   !entry.getKey().equalsIgnoreCase("privatekeyformat") &&
				   !entry.getKey().equalsIgnoreCase("publickey") &&
				   !entry.getKey().equalsIgnoreCase("publickeyformat") &&
				   !entry.getKey().equalsIgnoreCase("sn") &&
				   !entry.getKey().equalsIgnoreCase("uid")) {
					//如果是userpassword屬性,必須先把它加密
					if(entry.getKey().equalsIgnoreCase("userpassword")) {
						attributes.put(entry.getKey(), "{SHA}" + ShaEncoder.getSHA1Base64(entry.getValue()));
					} else {
						attributes.put(entry.getKey(), entry.getValue());
					}
				}
			}
		}
		
		try {
			//為了效率,直接建立使用者,當NameNotFoundException發生時,再去recursive建立上層組織
			createSubcontext(dn, attributes);
		} catch(NameNotFoundException nnfex) {
			if(recursive) {
				//表示之前的parent也不存在
				//幫忙建立之前的parent
				String[] parse = parseDN(baseDN);
				String subBaseDN = parse[1];
				String subId = StringUtility.ltrim(parse[0].split("=")[1]).trim();
				createOrganization(subBaseDN, subId, recursive);
				
				//建立此使用者
				createSubcontext(dn, attributes);
			} else {
				//不要recursive的話,要把exception丟出去,讓外層有對應的處理
				throw nnfex;
			}
		}
	}
	
	/**
	 * create user(預設不recursive)
	 * @param baseDN
	 * @param id
	 * @param commonName
	 * @param surname
	 * @param nickname
	 * @param userPassword
	 * @throws NamingException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public void createUser(String baseDN, String id, String commonName, String surname, String nickname, String userPassword) throws NamingException, NoSuchAlgorithmException, UnsupportedEncodingException {
		createUser(baseDN, id, commonName, surname, nickname, userPassword, false);
	}
	
	/**
	 * create user with some attribute(nickname, password)
	 * @param parentDN
	 * @param userId
	 * @param userName
	 * @param userPassword
	 * @throws NamingException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public void createUser(String baseDN, String id, String commonName, String surname, String nickname, String userPassword, boolean recursive) throws NamingException, NoSuchAlgorithmException, UnsupportedEncodingException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("displayname", nickname);
		map.put("userpassword", userPassword);
		
		createUser(baseDN, id, commonName, surname, map, recursive);
	}
	
	private Attributes toAttributes(Map<String, String> map) {
		Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
		Attributes attributes = new BasicAttributes();
		
		while(iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			
			//除了objectClass以外都可以被修改(有沒有其他不能被修改的屬性?!)
			if(!entry.getKey().equalsIgnoreCase("objectClass")) {
				attributes.put(entry.getKey(), entry.getValue());
			}
		}
		
		return attributes;
	}
	
	/**
	 * modify organization
	 * @param parentDN
	 * @param organizationId
	 * @throws NamingException
	 */
	public void modifyOrganization(String baseDN, String id, Map<String, String> map) throws NamingException {
		modifyAttributes(getOrganizationDN(baseDN, id), toAttributes(map));
	}
	
	/**
	 * modify user
	 * @param parentDN
	 * @param userId
	 * @throws NamingException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public void modifyUser(String baseDN, String id, Map<String, String> map) throws NamingException, NoSuchAlgorithmException, UnsupportedEncodingException {
		modifyAttributes(getUserDN(baseDN, id), toAttributes(map));
	}
	
	/**
	 * delete organization(預設不recursive)
	 * @param baseDN
	 * @param id
	 * @throws NamingException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public void deleteOrganization(String baseDN, String id) throws NamingException, NoSuchAlgorithmException, UnsupportedEncodingException {
		deleteOrganization(baseDN, id, false);
	}
	
	/**
	 * delete organization
	 * @param parentDN
	 * @param organizationId
	 * @throws NamingException
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	public void deleteOrganization(String baseDN, String id, boolean recursive) throws NamingException, NoSuchAlgorithmException, UnsupportedEncodingException {
		try {
			//為了效率,直接刪除自己,當有ContextNotEmptyException的時候再去recursive刪除
			unbind(getOrganizationDN(baseDN, id));
		} catch(ContextNotEmptyException cneex) {
			if(recursive) {
				String organizationDN = getOrganizationDN(baseDN, id);
				
				//刪除組織下的user
				deleteUsers(organizationDN);
				//刪除組織下的子組織
				deleteOrganizations(organizationDN, recursive);
				
				//刪除自己
				unbind(getOrganizationDN(baseDN, id));
			} else {
				//不要recursive的話,要把exception丟出去,讓外層有對應的處理
				throw cneex;
			}
		}
	}
	
	/**
	 * 刪除baseDN這層下的所有使用者
	 * @param baseDN
	 * @throws NamingException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public void deleteUsers(String baseDN) throws NamingException, NoSuchAlgorithmException, UnsupportedEncodingException {
		List<Map<String, String>> users = searchUser(baseDN, "");
		
		for(int i = 0; i < users.size(); i++) {
			deleteUser(baseDN, users.get(i).get("uid"));
		}
	}
	
	/**
	 * 刪除baseDN這層下的所有組織(預設不recursive)
	 * @param baseDN
	 * @throws NamingException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public void deleteOrganizations(String baseDN) throws NamingException, NoSuchAlgorithmException, UnsupportedEncodingException {
		deleteOrganizations(baseDN, false);
	}
	
	/**
	 * 刪除baseDN這層下的所有組織
	 * @param baseDN
	 * @throws NamingException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public void deleteOrganizations(String baseDN, boolean recursive) throws NamingException, NoSuchAlgorithmException, UnsupportedEncodingException {
		List<Map<String, String>> organizations = searchOrganization(baseDN, "");
		
		for(int i = 0; i < organizations.size(); i++) {
			deleteOrganization(baseDN, organizations.get(i).get("ou"), recursive);
		}
	}
	
	/**
	 * delete user
	 * @param parentDN
	 * @param userId
	 * @throws NamingException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public void deleteUser(String baseDN, String id) throws NamingException, NoSuchAlgorithmException, UnsupportedEncodingException {
		unbind(getUserDN(baseDN, id));
	}
}
