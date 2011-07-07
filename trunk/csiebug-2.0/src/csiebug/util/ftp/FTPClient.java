package csiebug.util.ftp;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;

import csiebug.util.StringParseException;
import csiebug.util.StringUtility;

import sun.net.TelnetInputStream;
import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;

public class FTPClient {
	private FtpClient client;
	private File localPWD;
	
	public static final void main(String[] args) throws IOException, StringParseException {
		if(args != null && args.length == 1) {
			if(args[0].equalsIgnoreCase("gui")) {
				gui();
			} else {
				commandLine();
			}
		} else {
			commandLine();
		}
	}
	
	private static void gui() throws IOException {
		//TODO: 以後有時間再做
		JFrame frame = new JFrame("FTPClient Demo");
		
		frame.pack();
		frame.setVisible(true);
		frame.setLayout(new GridLayout(4,3));
		frame.setSize(800, 600);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	private static void commandLine() throws IOException, StringParseException {
		Scanner in = new Scanner(System.in);
		
		StringBuffer programWelcome = new StringBuffer();
		programWelcome.append("**************************\n");
		programWelcome.append("*  FTPClient Demo        *\n");
		programWelcome.append("*  @author: George_Tsai  *\n");
		programWelcome.append("**************************\n");
		System.out.println(programWelcome.toString());
		
		System.out.print("請設定本機初始路徑: ");
		String localDir = in.nextLine();
		FTPClient ftpClient = new FTPClient(localDir);
		
		String action = "init";
		while(!action.equalsIgnoreCase("exit")) {
			System.out.print("ftp:> ");
			action = in.nextLine();
			
			if(action.equalsIgnoreCase("login")) {
				System.out.print("server ip: ");
				String serverIP = in.nextLine();
				System.out.print("帳號: ");
				String id = in.nextLine();
				System.out.print("密碼: ");
				String password = in.nextLine();
				try {
					ftpClient.login(serverIP, id, password);
					System.out.println(ftpClient.welcome());
				} catch(Exception e) {
					System.out.println(e.getMessage());
					action = "init";
				}
			} else if(action.startsWith("login ")) {
				List<String> params = StringUtility.parseParameters(action, "login");
				
				if(params.size() == 3) {
					try {
						ftpClient.login(params.get(0), params.get(1), params.get(2));
						System.out.println(ftpClient.welcome());
					} catch(Exception e) {
						System.out.println(e.getMessage());
						action = "init";
					}
				} else {
					System.out.println("參數錯誤");
				}
			} else if(action.equalsIgnoreCase("pwd")) {
				if(ftpClient.isConnected()) {
					System.out.println("server端目前目錄: " + ftpClient.pwd());
				} else {
					System.out.println("尚未連接主機");
				}
			} else if(action.equalsIgnoreCase("lpwd")) {
				System.out.println("local dir: " + ftpClient.localPWD());
			} else if(action.equalsIgnoreCase("cdUp") || action.equalsIgnoreCase("cd ..")) {
				if(ftpClient.isConnected()) {
					ftpClient.cdUp();
					System.out.println("server端目前目錄: " + ftpClient.pwd());
				} else {
					System.out.println("尚未連接主機");
				}
			} else if(action.equalsIgnoreCase("lcdUp")) {
				ftpClient.localCDUp();
				System.out.println("local端目前目錄: " + ftpClient.localPWD());
			} else if(action.startsWith("cd ")) {
				if(ftpClient.isConnected()) {
					String param = StringUtility.parseParameters(action, "cd").get(0);
					try {
						ftpClient.cd(param);
						System.out.println("server端目前目錄: " + ftpClient.pwd());
					} catch(FileNotFoundException fnfex) {
						System.out.println("路徑不存在");
					}
				} else {
					System.out.println("尚未連接主機");
				}
			} else if(action.startsWith("lcd ")) {
				String param = StringUtility.parseParameters(action, "lcd").get(0);
				ftpClient.localCD(param);
				System.out.println("local端目前目錄: " + ftpClient.localPWD());
			} else if(action.equalsIgnoreCase("list") || action.equalsIgnoreCase("ls") || action.equalsIgnoreCase("dir")) {
				if(ftpClient.isConnected()) {
					System.out.print(ftpClient.listToString());
				} else {
					System.out.println("尚未連接主機");
				}
			} else if(action.equalsIgnoreCase("llist")) {
				File[] files = ftpClient.localList();
				StringBuffer dirList = new StringBuffer();
				StringBuffer fileList = new StringBuffer();
				for(int i = 0; i < files.length; i++) {
					if(files[i].isDirectory()) {
						dirList.append("[" + files[i].getName() + "]\n");
					} else {
						fileList.append(files[i].getName() + "\n");
					}
				}
				String message = dirList.toString() + fileList.toString();
				System.out.println(message.substring(0, message.length() - 1));
			} else if(action.startsWith("rename ")) {
				if(ftpClient.isConnected()) {
					List<String> params = StringUtility.parseParameters(action, "rename");
					if(params.size() == 2) {
						ftpClient.rename(params.get(0), params.get(1));
					} else {
						System.out.println("參數錯誤");
					}
				} else {
					System.out.println("尚未連接主機");
				}
			} else if(action.startsWith("download ")) {
				if(ftpClient.isConnected()) {
					List<String> params = StringUtility.parseParameters(action, "download");
					
					for(int i = 0; i < params.size(); i++) {
						ftpClient.get(params.get(i));
					}
				} else {
					System.out.println("尚未連接主機");
				}
			} else if(action.startsWith("upload ")) {
				if(ftpClient.isConnected()) {
					List<String> params = StringUtility.parseParameters(action, "upload");
					
					for(int i = 0; i < params.size(); i++) {
						ftpClient.put(params.get(i));
					}
				} else {
					System.out.println("尚未連接主機");
				}
			} else if(action.equalsIgnoreCase("help")) {
				System.out.println("cd [param]: 移動server端路徑到param目錄");
				System.out.println("cdUp: 移動server端路徑到上層");
				System.out.println("download [param]: 下載param到目前local端路徑");
				System.out.println("exit: 離開程式");
				System.out.println("lcd [param]: 移動local端路徑到param目錄");
				System.out.println("lcdUp: 移動local端路徑到上層");
				System.out.println("list: 列出server端檔案");
				System.out.println("llist: 列出local檔案");
				System.out.println("login [server IP] [id] [password]: 連接主機");
				System.out.println("logout: 登出主機");
				System.out.println("lpwd: 顯示目前local端路徑");
				System.out.println("pwd: 顯示目前server端路徑");
				System.out.println("rename [param1] [param2]: 將server端檔案param1更名為param2");
				System.out.println("upload [param]: 上傳param到目前server端路徑");
			} else if(action.equalsIgnoreCase("logout")) {
				if(ftpClient.isConnected()) {
					ftpClient.logout();
				}
				
				System.out.print("連接另外的主機? (Y/N): ");
				action = in.nextLine();
				if(action.equalsIgnoreCase("Y")) {
					action = "login";
				} else {
					break;
				}
			} else if(action.equalsIgnoreCase("gui")) {
				gui();
			} else {
				if(!action.equalsIgnoreCase("exit")) {
					System.out.println("無此指令! 您可用help指令查詢");
				}
			}
		}
		
		if(ftpClient.isConnected()) {
			ftpClient.logout();
		}
		System.out.println("bye!");
	}
	
	/**
	 * 建構client並連線
	 * @param localPath
	 * @param serverHost
	 * @param loginID
	 * @param password
	 * @throws IOException
	 */
	public FTPClient(String localPath, String serverHost, String loginID, String password) throws IOException {
		localPWD = new File(localPath);
		login(serverHost, 21, loginID, password);
	}
	
	/**
	 * 建構client並連線
	 * @param localPath
	 * @param serverHost
	 * @param port
	 * @param loginID
	 * @param password
	 * @throws IOException
	 */
	public FTPClient(String localPath, String serverHost, int port, String loginID, String password) throws IOException {
		localPWD = new File(localPath);
		login(serverHost, port, loginID, password);
	}
	
	/**
	 * 建構client
	 * @param localPath
	 */
	public FTPClient(String localPath) {
		localPWD = new File(localPath);
	}
	
	/**
	 * 連線主機
	 * @param serverHost
	 * @param port
	 * @param loginID
	 * @param password
	 * @throws IOException
	 */
	public void login(String serverHost, int port, String loginID, String password) throws IOException {
		try {
			client = new FtpClient(serverHost, port);
			client.login(loginID, password);
		} catch(IOException e) {
			client = null;
			throw e;
		}
	}
	
	/**
	 * 連線主機
	 * @param serverHost
	 * @param loginID
	 * @param password
	 * @throws IOException
	 */
	public void login(String serverHost, String loginID, String password) throws IOException {
		login(serverHost, 21, loginID, password);
	}
	
	/**
	 * 判斷是否已連線
	 * @return
	 */
	public boolean isConnected() {
		return client != null;
	}
	
	/**
	 * 登入訊息
	 * @return
	 */
	public String welcome() {
		return client.welcomeMsg;
	}
	
	/**
	 * local端目前目錄
	 * @return
	 */
	public String localPWD() {
		return localPWD.getPath();
	}
	
	/**
	 * server端目前目錄
	 * @return
	 * @throws IOException
	 */
	public String pwd() throws IOException {
		return client.pwd();
	}
	
	/**
	 * local端目錄往上層移動
	 */
	public void localCDUp() {
		localPWD = localPWD.getParentFile();
	}
	
	/**
	 * server端目錄往上層移動
	 * @throws IOException
	 */
	public void cdUp() throws IOException {
		client.cdUp();
	}
	
	/**
	 * server端目前目錄檔案列表
	 * @return
	 * @throws IOException
	 */
	public TelnetInputStream list() throws IOException {
		return client.list();
	}
	
	/**
	 * server端目前目錄檔案列表
	 * @return
	 * @throws IOException
	 */
	public String listToString() throws IOException {
		TelnetInputStream tis = list();
		
		try {
			StringBuffer list = new StringBuffer();
			
			while(true) {
				int c = tis.read();
				char ch = (char)c;
				
				if(c <= 0) {
					break;
				}
				
				list.append(ch);
			}
			return list.toString();
		} finally {
			tis.close();
		}
	}
	
	/**
	 * local端目前目錄檔案列表
	 * @return
	 */
	public File[] localList() {
		return localPWD.listFiles();
	}
	
	/**
	 * 移動local端目錄
	 * @param name
	 */
	public void localCD(String name) {
		File[] files = localPWD.listFiles();
		
		for(int i = 0; i < files.length; i++) {
			if(files[i].getName().equalsIgnoreCase(name)) {
				localPWD = files[i];
				break;
			}
		}
	}
	
	/**
	 * 移動server端目錄
	 * @param name
	 * @throws IOException
	 */
	public void cd(String name) throws IOException {
		client.cd(name);
	}
	
	/**
	 * 檔案更名
	 * @param oldName
	 * @param newName
	 * @throws IOException
	 */
	public void rename(String oldName, String newName) throws IOException {
		client.rename(oldName, newName);
	}
	
	/**
	 * 下載檔案
	 * @param filename
	 * @throws IOException
	 */
	public void get(String filename) throws IOException {
		TelnetInputStream tis = null;
		FileOutputStream fos = null;
		
		try {
			tis = client.get(filename);
			
			File destinationFile;
			if(localPWD().endsWith("\\")) {
				destinationFile = new File(localPWD + "\\" + filename);
			} else {
				destinationFile = new File(localPWD + "/" + filename);
			}
			
			fos = new FileOutputStream(destinationFile);
			
			byte[] buffer = new byte[1024];
			
			while(true) {
				int bytes = tis.read(buffer);
				
				if(bytes < 0) {
					break;
				}
				
				fos.write(buffer, 0, bytes);
			}
		} finally {
			if(fos != null) {
				fos.close();
			}
			
			if(tis != null) {
				tis.close();
			}
		}
	}
	
	/**
	 * 上傳檔案
	 * @param filename
	 * @throws IOException
	 */
	public void put(String filename) throws IOException {
		TelnetOutputStream tos = null;
		FileInputStream fis = null;
		
		try {
			tos = client.put(filename);
			
			File sourceFile;
			if(localPWD().endsWith("\\")) {
				sourceFile = new File(localPWD + "\\" + filename);
			} else {
				sourceFile = new File(localPWD + "/" + filename);
			}
			
			if(sourceFile.exists()) {
				fis = new FileInputStream(sourceFile);
				
				byte[] buffer = new byte[1024];
				
				while(true) {
					int bytes = fis.read(buffer);
					
					if(bytes < 0) {
						break;
					}
					
					tos.write(buffer, 0, bytes);
				}
			} else {
				throw new FileNotFoundException();
			}
		} finally {
			if(tos != null) {
				tos.close();
			}
			
			if(fis != null) {
				fis.close();
			}
		}
	}
	
	/**
	 * 登出主機
	 * @throws IOException
	 */
	public void logout() throws IOException {
		client.closeServer();
		client = null;
	}
}
