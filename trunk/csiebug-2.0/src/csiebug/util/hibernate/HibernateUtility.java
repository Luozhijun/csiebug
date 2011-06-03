/**
 * Copyright (c) 2010 George Tsai. All rights reserved.
 * For more information on the George Tsai, please see http://www.pixnet.net/blog/csiebug
 */
package csiebug.util.hibernate;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 * @author George_Tsai
 * @version 2010/10/22
 */
public class HibernateUtility {
	public static void main(String[] argc) {
		HibernateUtility.hbm2Table();
	}
	
	public static void hbm2Table() {
		Configuration config = new Configuration().configure();
        SchemaExport schemaExport = new SchemaExport(config);
        schemaExport.create(true, true);
	}
}
