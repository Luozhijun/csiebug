/**
 * 
 */
package csiebug.util;

import java.io.Serializable;

/**
 * @author George_Tsai
 * @version 2010/1/20
 */
public interface Observer extends Serializable {
	void update(Observable observable, Object args);
}
