/**
 * 
 */
package csiebug.service;

import java.io.Serializable;
import java.util.Iterator;

import csiebug.util.AssertUtility;
import csiebug.util.Observable;
import csiebug.util.Observer;
import java.util.Vector;

/**
 * @author George_Tsai
 * @version 2010/1/19
 */
public class ServiceStatus implements Observable, Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String LOCK = "lock";
	public static final String ENABLE = "enable";
	
	private String status = ENABLE;
	
	private Vector<BasicService> observers = new Vector<BasicService>();
	
	public void setStatus(String status) {
		AssertUtility.notNull(status);
		
		this.status = status;
		notifyObservers();
	}

	public String getStatus() {
		return status;
	}

	/* (non-Javadoc)
	 * @see csiebug.util.Observable#addObserver(csiebug.util.Observer)
	 */
	public void addObserver(Observer observer) {
		AssertUtility.notNull(observer);
		
		observers.add((BasicService)observer);
		observer.update(this, null);
	}
	
	/* (non-Javadoc)
	 * @see csiebug.util.Observable#addObservers(csiebug.util.Observer[])
	 */
	public void addObservers(Observer[] observers) {
		AssertUtility.notNull(observers);
		
		for(int i = 0; i < observers.length; i++) {
			addObserver(observers[i]);
		}
	}

	/* (non-Javadoc)
	 * @see csiebug.util.Observable#notifyObservers()
	 */
	public void notifyObservers() {
		Iterator<BasicService> iterator = observers.iterator();
		
		while(iterator.hasNext()) {
			BasicService o = iterator.next();
			o.update(this, null);
		}
	}

	/* (non-Javadoc)
	 * @see csiebug.util.Observable#removeObserver(csiebug.util.Observer)
	 */
	public void removeObserver(Observer observer) {
		AssertUtility.notNull(observer);
		
		observers.remove((BasicService)observer);
	}

}
