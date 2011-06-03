package csiebug.web.webapp;

import java.io.File;
import java.io.FileFilter;

public class WarFileFilter implements FileFilter {
    
    protected static final String WAR_EXTENSION = ".war";
    protected static final String WAR_DEPLOYED_EXTENSION = ".deployed";
    
    public boolean accept(File fileName) {
		String fName = fileName.getName() ;
		return ( fName.endsWith(WAR_EXTENSION) || fName.endsWith(WAR_DEPLOYED_EXTENSION));
    }

}