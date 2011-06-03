package csiebug.dao.hibernateImpl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import csiebug.dao.BasicDAO;
import csiebug.dao.DAOException;
import csiebug.util.AssertUtility;

/**
 * DAO基本功能實作(使用hibernate)
 * @author George_Tsai
 * @version 2009/6/22
 */
public abstract class BasicDAOImpl extends HibernateDaoSupport implements BasicDAO {
	private static final long serialVersionUID = 1L;
	
	private Criteria criteria;
	private Class<?> targetClass;
	private Session session;
	
	/**
	 * 檢查物件是否是null
	 * @param object
	 * @param message
	 */
	protected static void notNull(Object object, String message) throws DAOException {
		if(object == null) {
			throw new DAOException(new NullPointerException(message));
		}
	}
	
	/**
	 * 檢查物件是否是null
	 * @param object
	 * @param message
	 */
	protected static void notNull(Object object) throws DAOException {
		notNull(object, "Object is null!!");
	}
	
	/**
	 * 檢查字串是否是null或是空白字串
	 * @param string
	 * @param message
	 */
	protected static void notNullAndNotSpace(String string, String message) throws DAOException {
		if(string == null || string.trim().equals("")) {
			throw new DAOException(new NullPointerException(message));
		}
	}
	
	/**
	 * 檢查字串是否是null或是空白字串
	 * @param string
	 * @param message
	 */
	protected static void notNullAndNotSpace(String string) throws DAOException {
		notNullAndNotSpace(string, "String is null or empty!!");
	}
	
	/**
	 * 檢查Collection物件是否是null或是size是0
	 * @param Collection
	 * @param message
	 */
	protected static void notNullAndNotEmpty(Collection<?> c, String message) throws DAOException {
		if(c == null || c.size() <= 0) {
			throw new DAOException(new NullPointerException(message));
		}
	}
	
	/**
	 * 檢查Collection物件是否是null或是size是0
	 * @param Collection
	 */
	protected static void notNullAndNotEmpty(Collection<?> c) throws DAOException {
		notNullAndNotEmpty(c, "Collection is null or empty!!");
	}
	
	/**
	 * 檢查Array是否是null或是size是0
	 * @param array
	 * @param message
	 */
	protected static void notNullAndNotEmpty(Object[] ary, String message) throws DAOException {
		if(ary == null || ary.length <= 0) {
			throw new DAOException(new NullPointerException(message));
		}
	}
	
	/**
	 * 檢查Array是否是null或是size是0
	 * @param array
	 */
	protected static void notNullAndNotEmpty(Object[] ary) throws DAOException {
		notNullAndNotEmpty(ary, "Array is null or empty!!");
	}
	
	/**
	 * 檢查Collection的element是否有null
	 * @param collection
	 * @param message
	 * @throws DAOException 
	 */
	protected static void allElementNotNull(Collection<?> c, String message) throws DAOException {
		notNullAndNotEmpty(c, message);
		
		Iterator<?> iterator = c.iterator();
		
		while(iterator.hasNext()) {
			notNull(iterator.next(), message);
		}
	}
	
	/**
	 * 檢查Collection的element是否有null
	 * @param collection
	 * @throws DAOException 
	 */
	protected static void allElementNotNull(Collection<?> c) throws DAOException {
		allElementNotNull(c, "Element can not be null!!");
	}
	
	/**
	 * 檢查Array的element是否有null
	 * @param array
	 * @param message
	 * @throws DAOException 
	 */
	protected static void allElementNotNull(Object[] ary, String message) throws DAOException {
		notNullAndNotEmpty(ary, message);
		
		for(int i = 0; i < ary.length; i++) {
			notNull(ary[i], message);
		}
	}
	
	/**
	 * 檢查Array的element是否有null
	 * @param array
	 * @throws DAOException 
	 */
	protected static void allElementNotNull(Object[] ary) throws DAOException {
		allElementNotNull(ary, "Element can not be null!!");
	}
	
	protected Session getCurrentSession() {
		if(session == null) {
			session = getSession();
		} else if(!session.isOpen()) {
			session = getSession();
		}
		
		return session;
	}
	
	protected void setTable(Class<?> c) throws DAOException {
		notNull(c, "Can not create criteria for null!");
		
		session = getCurrentSession();
		targetClass = c;
		criteria = session.createCriteria(targetClass);
	}
	
	protected void clearCriteria() {
		session = getCurrentSession();
		criteria = session.createCriteria(targetClass);
	}
	
	protected Criteria createCriteria(String value) throws DAOException {
		notNullAndNotSpace(value, "Can not create criteria for null!");
		
		return criteria.createCriteria(value);
	}
	
	protected void add(Criterion criterion) throws DAOException {
		notNull(criterion, "Can not add null restriction!");
		
		criteria.add(criterion);
	}
	
	protected void addOrder(Order order) throws DAOException {
		notNull(order, "Can not add null order!");
		
		criteria.addOrder(order);
	}
	
	/**
	 * 清除hibernate快取
	 */
	private void flush() {
		session = getCurrentSession();
		session.flush();
		session.clear();
	}
	
	protected Criteria setFirstResult(int firstResult) {
		return criteria.setFirstResult(firstResult);
	}
	
	protected Criteria setMaxResults(int maxResults) {
		return criteria.setMaxResults(maxResults);
	}
	
	protected void setProjection(Projection projection) {
		criteria.setProjection(projection);
	}
	
	protected List<?> query(boolean flush) {
		if(flush) {
			flush();
		}
		
		return criteria.list();
	}
	
	protected List<?> query() {
		return query(false);
	}
	
	protected List<?> query(String hql, Map<String, ?> map, int firstResult, int maxResults, boolean flush) throws DAOException {
		notNullAndNotSpace(hql, "Can not create Query for null or empty HQL!");
		
		if(flush) {
			flush();
		}
		
		Query query = session.createQuery(hql);
		
		if(map != null) {
			for(int i = 0; i < map.size(); i++) {
				query.setParameter((String)map.keySet().toArray()[i], map.get(map.keySet().toArray()[i]));
			}
		}
		
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		
		return query.list();
	}
	
	protected List<?> query(String hql, Map<String, ?> map, int firstResult, int maxResults) throws DAOException {
		return query(hql, map, firstResult, maxResults, false);
	}
	
	protected List<?> query(String hql, int firstResult, int maxResults, boolean flush) throws DAOException {
		return query(hql, null, firstResult, maxResults, flush);
	}
	
	protected List<?> query(String hql, int firstResult, int maxResults) throws DAOException {
		return query(hql, firstResult, maxResults, false);
	}
	
	protected List<?> query(String hql, Map<String, ?> map, boolean flush) throws DAOException {
		notNullAndNotSpace(hql, "Can not create Query for null or empty HQL!");
		
		if(flush) {
			flush();
		}
		
		Query query = session.createQuery(hql);
		
		if(map != null) {
			for(int i = 0; i < map.size(); i++) {
				query.setParameter((String)map.keySet().toArray()[i], map.get(map.keySet().toArray()[i]));
			}
		}
		
		return query.list();
	}
	
	protected List<?> query(String hql, Map<String, ?> map) throws DAOException {
		return query(hql, map, false);
	}
	
	protected List<?> query(String hql, boolean flush) throws DAOException {
		return query(hql, null, flush);
	}
	
	protected List<?> query(String hql) throws DAOException {
		return query(hql, false);
	}
	
	public void insert(Object obj) throws DAOException {
		notNull(obj, "Can not insert null!");
		
		session = getCurrentSession();
		try {
			session.save(obj);
		} catch(NonUniqueObjectException nuoex) {
			session.merge(obj);
		}
	}
	
	public void insert(Object[] objs) throws DAOException {
		notNull(objs, "Can not insert null!");
		
		session = getCurrentSession();
		
		for(int i = 0; i < objs.length; i++) {
			try {
				session.save(objs[i]);
			} catch(NonUniqueObjectException nuoex) {
				session.merge(objs[i]);
			}
		}
	}
	
	public void insert(List<?> list) throws DAOException {
		notNull(list, "Can not insert null!");
		
		session = getCurrentSession();
		
		for(int i = 0; i < list.size(); i++) {
			try {
				session.save(list.get(i));
			} catch(NonUniqueObjectException nuoex) {
				session.merge(list.get(i));
			}
		}
	}
	
	public void insert(Set<?> set) throws DAOException {
		notNull(set, "Can not insert null!");
		
		session = getCurrentSession();
		Iterator<?> iterator = set.iterator();
		
		while(iterator.hasNext()) {
			Object obj = iterator.next();
			try {
				session.save(obj);
			} catch(NonUniqueObjectException nuoex) {
				session.merge(obj);
			}
		}
	}
	
	public void insertOrUpdate(Object obj) throws DAOException {
		notNull(obj, "Can not insert/update null!");
		
		session = getCurrentSession();
		try {
			session.saveOrUpdate(obj);
		} catch(NonUniqueObjectException nuoex) {
			session.merge(obj);
		}
	}
	
	public void insertOrUpdate(Object[] objs) throws DAOException {
		notNull(objs, "Can not insert/update null!");
		
		session = getCurrentSession();
		
		for(int i = 0; i < objs.length; i++) {
			try {
				session.saveOrUpdate(objs[i]);
			} catch(NonUniqueObjectException nuoex) {
				session.merge(objs[i]);
			}
		}
	}
	
	public void insertOrUpdate(List<?> list) throws DAOException {
		notNull(list, "Can not insert/update null!");
		
		session = getCurrentSession();
		
		for(int i = 0; i < list.size(); i++) {
			try {
				session.saveOrUpdate(list.get(i));
			} catch(NonUniqueObjectException nuoex) {
				session.merge(list.get(i));
			}
		}
	}
	
	public void insertOrUpdate(Set<?> set) throws DAOException {
		notNull(set, "Can not insert/update null!");
		
		session = getCurrentSession();
		Iterator<?> iterator = set.iterator();
		
		while(iterator.hasNext()) {
			Object obj = iterator.next();
			try {
				session.saveOrUpdate(obj);
			} catch(NonUniqueObjectException nuoex) {
				session.merge(obj);
			}
		}
	}
	
	public void update(Object obj) throws DAOException {
		notNull(obj, "Can not update null!");
		
		session = getCurrentSession();
		try {
			session.update(obj);
		} catch(NonUniqueObjectException nuoex) {
			session.merge(obj);
		}
	}
	
	public void update(Object[] objs) throws DAOException {
		notNull(objs, "Can not update null!");
		
		session = getCurrentSession();
		
		for(int i = 0; i < objs.length; i++) {
			try {
				session.update(objs[i]);
			} catch(NonUniqueObjectException nuoex) {
				session.merge(objs[i]);
			}
		}
	}
	
	public void update(List<?> list) throws DAOException {
		notNull(list, "Can not update null!");
		
		session = getCurrentSession();
		
		for(int i = 0; i < list.size(); i++) {
			try {
				session.update(list.get(i));
			} catch(NonUniqueObjectException nuoex) {
				session.merge(list.get(i));
			}
		}
	}
	
	public void update(Set<?> set) throws DAOException {
		notNull(set, "Can not update null!");
		
		session = getCurrentSession();
		
		Iterator<?> iterator = set.iterator();
		
		while(iterator.hasNext()) {
			Object obj = iterator.next();
			try {
				session.update(obj);
			} catch(NonUniqueObjectException nuoex) {
				session.merge(obj);
			}
		}
	}
	
	public void delete(Object obj) throws DAOException {
		notNull(obj, "Can not delete null!");
		
		session = getCurrentSession();
		session.delete(obj);
	}
	
	public abstract void delete(Object[] objs) throws DAOException;
	public abstract void delete(List<?> list) throws DAOException;
	public abstract void delete(Set<?> set) throws DAOException;
	
	protected void executeUpdate(String hql, Map<String, ?> map) throws DAOException {
		AssertUtility.notNullAndNotSpace(hql, "Can not create Query for null or empty HQL!");
		
		session = getCurrentSession();
		Query query = session.createQuery(hql);
		
		if(map != null) {
			for(int i = 0; i < map.size(); i++) {
				query.setParameter((String)map.keySet().toArray()[i], map.get(map.keySet().toArray()[i]));
			}
		}
		
		query.executeUpdate();
	}
	
	protected void executeUpdate(String hql) throws DAOException {
		executeUpdate(hql, null);
	}
	
	public void deleteMatchObjects(Object obj) throws DAOException {
		delete(search(obj));
	}
	
	public abstract List<?> search(Object obj) throws DAOException;
	public abstract List<?> searchPagination(Object obj, int firstResult, int maxResults) throws DAOException;
	public abstract int searchRowCount(Object obj) throws DAOException;
}
