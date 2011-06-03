/**
 * Copyright (c) 2010 George Tsai. All rights reserved.
 * For more information on the George Tsai, please see http://www.pixnet.net/blog/csiebug
 */
package csiebug.util.zip;

/**
 * 印出訊息到console
 * @author George_Tsai
 * @version 2010/8/3
 */
public class ZipMonitorConsoleImpl extends ZipMonitor {
	public void printMessage() {
		System.out.println(getCurrentMessage());
		System.out.println("complete: " + getZipPercetage() + "%");
	}
}
