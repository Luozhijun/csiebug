/**
 * Copyright (c) 2010 George Tsai. All rights reserved.
 * For more information on the George Tsai, please see http://www.pixnet.net/blog/csiebug
 */
package csiebug.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import csiebug.dao.BasicDAO;

/**
 * @author George_Tsai
 * @version 2010/6/10
 */
public abstract class LazyLoadingArrayList<E> extends ArrayList<E> {
	private static final long serialVersionUID = 1L;
	
	// 當前要顯示的資料,儘量通過快取的方式減少資料庫的查詢次數
	private Map<String, E> caches = new HashMap<String, E>();
	// 當前顯示頁面的開始索引數
	private int startIndex = -1;
	// 當前顯示頁面的結束索引數
	private int endIndex = -1;
	// 每頁顯示多少條
	private int rowsPerPage ;
	
	private BasicDAO dao;
	private String methodName;
	private Object[] parameters;
	
	public E get(int index) {
		// 先檢查用戶請求的資料是否在快取中
		// 如果不在
		// 就先清除快取
		// 然後從資料庫中查詢資料
		// 這裏主要考慮速度的問題
		// 因為從快取中取得資料要比資料庫中取快得多
		if((index > this.endIndex) || (index < startIndex)) {
			caches.clear();
			startIndex = index;
			endIndex = startIndex + rowsPerPage - 1;
			int tempIndex = startIndex;
			Collection<E> collection;
			
			try {
				collection = executeDAOPaginationMethod(startIndex, rowsPerPage);
				
				for(Iterator<E> iterator = collection.iterator(); iterator.hasNext();) {
					caches.put(Integer.toString(tempIndex), iterator.next());
					tempIndex++;
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		
		return caches.get(Integer.toString(index));
	}
	
	public int size() {
		try {
			return executeDAORowCountMethod();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	private Collection<E> executeDAOPaginationMethod(int firstResult, int maxResults) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Object[] parametersWithRange;
		if(parameters != null) {
			parametersWithRange = new Object[parameters.length + 2];
			
			for(int i = 0; i < parameters.length; i++) {
				parametersWithRange[i] = parameters[i];
			}
			parametersWithRange[parametersWithRange.length - 2] = firstResult;
			parametersWithRange[parametersWithRange.length - 1] = maxResults;
		} else {
			parametersWithRange = new Object[]{firstResult, maxResults};
		}
		
		if(getMethodSuffix("Pagination") != null) {
			return (Collection<E>)getMethodSuffix("Pagination").invoke(dao, parametersWithRange);
		}
		
		return null;
	}
	
	private int executeDAORowCountMethod() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		if(getMethodSuffix("RowCount") != null) {
			return (Integer)getMethodSuffix("RowCount").invoke(dao, parameters);
		}
		
		return 0;
	}
	
	private Method getMethodSuffix(String suffix) {
		Method method = null;
		
		for(int i = 0; i < dao.getClass().getMethods().length; i++) {
			if(dao.getClass().getMethods()[i].getName().equals(methodName + suffix)) {
				method = dao.getClass().getMethods()[i];
			}
		}
		
		return method;
	}
	
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}
	public int getRowsPerPage() {
		return this.rowsPerPage;
	}
	
	public void setDao(BasicDAO dao) {
		this.dao = dao;
	}
	
	public BasicDAO getDao() {
		return this.dao;
	}
	
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	public String getMethodName() {
		return this.methodName;
	}
	
	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}
	
	public Object[] getParameters() {
		return this.parameters;
	}
}
