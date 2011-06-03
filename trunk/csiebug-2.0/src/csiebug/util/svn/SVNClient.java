/**
 * Copyright (c) 2010 George Tsai. All rights reserved.
 * For more information on the George Tsai, please see http://www.pixnet.net/blog/csiebug
 */
package csiebug.util.svn;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;

import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNProperty;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

/**
 * @author George_Tsai
 * @version 2010/6/30
 */
public class SVNClient {
	private SVNRepository repository;
	private static SVNClientManager clientManager;
	
	public SVNClient(String url, String name, String password) throws SVNException {
		DAVRepositoryFactory.setup();
		repository = SVNRepositoryFactory.create(SVNURL.parseURIDecoded(url));
		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
		repository.setAuthenticationManager(authManager);
		clientManager = SVNClientManager.newInstance();
		clientManager.setAuthenticationManager(authManager);
	}
	
	/**
	 * 判斷傳入路徑是否是目錄
	 * @param filePath
	 * @return
	 * @throws SVNException
	 */
	public boolean isDirectory(String filePath) throws SVNException {
		SVNNodeKind nodeKind = repository.checkPath(filePath, -1);
		
		return (nodeKind == SVNNodeKind.DIR);
	}
	
	/**
	 * 判斷傳入路徑是否是檔案
	 * @param filePath
	 * @return
	 * @throws SVNException
	 */
	public boolean isFile(String filePath) throws SVNException {
		SVNNodeKind nodeKind = repository.checkPath(filePath, -1);
		
		return (nodeKind == SVNNodeKind.FILE);
	}
	
	/**
	 * 判斷傳入路徑是否存在
	 * @param filePath
	 * @return
	 * @throws SVNException
	 */
	public boolean exists(String filePath) throws SVNException {
		SVNNodeKind nodeKind = repository.checkPath(filePath, -1);
		
		return (nodeKind != SVNNodeKind.NONE);
	}
	
	/**
	 * 取得Repository Tree
	 * @param path
	 * @return
	 * @throws SVNException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Collection<SVNDirEntry> getRepositoryTree(String path) throws SVNException {
		return repository.getDir(path, -1, null, (Collection)null);
	}
	
	/**
	 * 將Repository Tree輸出成結構化字串
	 * (可覆寫此method,輸出自訂的結構化字串)
	 * @param path
	 * @return
	 * @throws SVNException
	 */
	public String getRepositoryTreeFormatString(String path) throws SVNException {
		StringBuffer formatString = new StringBuffer();
		
		Collection<SVNDirEntry> entries = getRepositoryTree(path);
		Iterator<SVNDirEntry> iterator = entries.iterator();
		while (iterator.hasNext()) {
			SVNDirEntry entry = iterator.next();
			formatString.append("/" + (path.equals("") ? "" : path + "/") + entry.getName() + " ( author: '" + entry.getAuthor() + "'; revision: " + entry.getRevision() + "; date: " + entry.getDate() + ")\n");
		    if (entry.getKind() == SVNNodeKind.DIR) {
		    	formatString.append(getRepositoryTreeFormatString((path.equals("")) ? entry.getName() : path + "/" + entry.getName()));
		    }
		}
		
		return formatString.toString();
	}
	
	/**
	 * 將Repository Tree的結構化字串輸出到console
	 * @param path
	 * @throws SVNException
	 */
	public void printRepositoryTree(String path) throws SVNException {
		System.out.println(getRepositoryTreeFormatString(path));
	}
	
	/**
	 * 判斷傳入路徑是否是文字檔
	 * @param filePath
	 * @return
	 * @throws SVNException
	 */
	public boolean isTextFile(String filePath) throws SVNException {
		if(isFile(filePath)) {
			SVNProperties fileProperties = new SVNProperties();
			
			repository.getFile(filePath, -1, fileProperties, null);
			
			String mimeType = fileProperties.getStringValue(SVNProperty.MIME_TYPE);
			return SVNProperty.isTextMimeType(mimeType);
		} else {
			return false;
		}
	}
	
	/**
	 * 取得文字檔內容
	 * @param filePath
	 * @param charsetName
	 * @return
	 * @throws SVNException
	 * @throws IOException
	 */
	public String getTextFileContent(String filePath, String charsetName) throws SVNException, IOException {
		if(isFile(filePath) && isTextFile(filePath)) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				repository.getFile(filePath, -1, null, baos);
				return baos.toString(charsetName);
			} finally {
				baos.close();
			}
		} else {
			return null;
		}
	}
	
	/**
	 * 取得Repository History
	 * @param targetPaths
	 * @param startRevision
	 * @param endRevision
	 * @return
	 * @throws SVNException
	 */
	@SuppressWarnings("unchecked")
	public Collection<SVNLogEntry> getRepositoryHistory(String[] targetPaths, long startRevision, long endRevision) throws SVNException {
		return repository.log(targetPaths, null, startRevision, endRevision, true, true);
	}
	
	/**
	 * 將Repository History輸出成結構化字串
	 * (可覆寫此method,輸出自訂的結構化字串)
	 * @param logEntries
	 * @return
	 * @throws SVNException
	 */
	@SuppressWarnings("unchecked")
	public String getRepositoryHistoryFormatString(Collection<SVNLogEntry> logEntries) throws SVNException {
		StringBuffer formatString = new StringBuffer();
		
		for(Iterator<SVNLogEntry> entries = logEntries.iterator(); entries.hasNext();) {
			SVNLogEntry logEntry = (SVNLogEntry)entries.next();
			formatString.append("---------------------------------------------\n");
			formatString.append("revision: " + logEntry.getRevision() + "\n");
			formatString.append("author: " + logEntry.getAuthor() + "\n");
			formatString.append("date: " + logEntry.getDate() + "\n");
			formatString.append("log message: " + logEntry.getMessage() + "\n");
			
			if(logEntry.getChangedPaths().size() > 0) {
				formatString.append("\n");
				formatString.append("changed paths:\n");
				Iterator<Entry<String, SVNLogEntryPath>> iterator = logEntry.getChangedPaths().entrySet().iterator();
				
				while(iterator.hasNext()) {
					SVNLogEntryPath entryPath = iterator.next().getValue();
					formatString.append(" " + entryPath.getType() + " " + entryPath.getPath() +
									   ((entryPath.getCopyPath() != null) ? " (from " + entryPath.getCopyPath() + " revision " + entryPath.getCopyRevision() + ")" : "") + "\n");
				}
			}
		}
		
		return formatString.toString();
	}
	
	/**
	 * 將Repository History的結構化字串輸出到console
	 * @param targetPaths
	 * @param startRevision
	 * @param endRevision
	 * @throws SVNException
	 */
	public void printRepositoryHistory(String[] targetPaths, long startRevision, long endRevision) throws SVNException {
		Collection<SVNLogEntry> logEntries = getRepositoryHistory(targetPaths, startRevision, endRevision);
		printRepositoryHistory(logEntries);
	}
	
	/**
	 * 將Repository Tree的結構化字串輸出到console
	 * @param logEntries
	 * @throws SVNException
	 */
	public void printRepositoryHistory(Collection<SVNLogEntry> logEntries) throws SVNException {
		System.out.println(getRepositoryHistoryFormatString(logEntries));
	}
	
	/**
	 * 從SVN checkout
	 * @param url
	 * @param destPath
	 * @param isRecursive
	 * @return
	 * @throws SVNException
	 */
	public long checkout(String url, String destPath, boolean isRecursive) throws SVNException {
		return checkout(SVNURL.parseURIDecoded(url), new File(destPath), isRecursive);
	}
	
	/**
	 * 從SVN checkout
	 * @param url
	 * @param destPath
	 * @param isRecursive
	 * @return
	 * @throws SVNException
	 */
	public long checkout(SVNURL url, File destPath, boolean isRecursive) throws SVNException {
		return checkout(url, SVNRevision.create(repository.getLatestRevision()), destPath, isRecursive);
	}
	
	/**
	 * 從SVN checkout
	 * @param url
	 * @param revision
	 * @param destPath
	 * @param isRecursive
	 * @return
	 * @throws SVNException
	 */
	public long checkout(SVNURL url, SVNRevision revision, File destPath, boolean isRecursive) throws SVNException {
		SVNUpdateClient updateClient = clientManager.getUpdateClient();
		/*
		 * sets externals not to be ignored during the checkout
		 */
		updateClient.setIgnoreExternals(false);
		/*
		 * returns the number of the revision at which the working copy is 
		 */
		if(isRecursive) {
			return updateClient.doCheckout(url, destPath, revision, revision, SVNDepth.INFINITY, false);
		} else {
			return updateClient.doCheckout(url, destPath, revision, revision, SVNDepth.FILES, false);
		}
	}
	
	/**
	 * 從SVN update
	 * @param wcPath
	 * @param isRecursive
	 * @return
	 * @throws SVNException
	 */
	public long update(String wcPath, boolean isRecursive) throws SVNException {
		return update(new File(wcPath), isRecursive);
	}
	
	/**
	 * 從SVN update
	 * @param wcPath
	 * @param isRecursive
	 * @return
	 * @throws SVNException
	 */
	public long update(File wcPath, boolean isRecursive) throws SVNException {
		return update(wcPath, SVNRevision.create(repository.getLatestRevision()), isRecursive);
	}
	
	/**
	 * 從SVN update
	 * @param wcPath
	 * @param updateToRevision
	 * @param isRecursive
	 * @return
	 * @throws SVNException
	 */
	public long update(File wcPath, SVNRevision updateToRevision, boolean isRecursive) throws SVNException {
		SVNUpdateClient updateClient = clientManager.getUpdateClient();
		/*
		 * sets externals not to be ignored during the update
		 */
		updateClient.setIgnoreExternals(false);
		/*
		 * returns the number of the revision wcPath was updated to
		 */
		if(isRecursive) {
			return updateClient.doUpdate(wcPath, updateToRevision, SVNDepth.INFINITY, false, true);
		} else {
			return updateClient.doUpdate(wcPath, updateToRevision, SVNDepth.FILES, false, true);
		}
	}
}
