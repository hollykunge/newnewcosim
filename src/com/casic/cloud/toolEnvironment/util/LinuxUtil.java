package com.casic.cloud.toolEnvironment.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class LinuxUtil {
	// linux集群的主节点ip
	public static String mainServerIp = "";
	// linux集群的节点ip|userName,password(不包含主节点)
	public static Map<String, String> slaveServers = new LinkedHashMap<String, String>();
	// 集群上的工具名和工具目录
	public static Map<String, String> softNameAndDirOnCluster = new HashMap<String, String>();
	public static String userName = "", password = "";

	/**
	 * 加载配置文件中的集群ip和工具目录
	 */
	public static void loadProperties() {
		// 清空原始数据
		mainServerIp = "";
		slaveServers.clear();
		softNameAndDirOnCluster.clear();
		Properties properties = new Properties();
		try {
			properties
					.load(LinuxUtil.class
							.getResourceAsStream("/com/casic/cloud/toolEnvironment/clusterAddrAndToolDir.properties"));
			Set<Object> keys = properties.keySet();
			Iterator<Object> keysIterator = keys.iterator();
			while (keysIterator.hasNext()) {
				String key = (String) keysIterator.next();
				String value = properties.getProperty((String) key);
				// 对key转码
				key = new String(((String) key).getBytes("ISO-8859-1"), "UTF-8");
				// 对value转码
				value = new String(((String) value).getBytes("ISO-8859-1"),
						"UTF-8");
				//
				if (key.equals("mainServer")) {
					mainServerIp = value;
				} else if (key.startsWith("server")) {
					String[] strs = value.split("\\|", 0);
					if (strs.length == 2) {
						slaveServers.put(strs[0], strs[1]);
					}

				} else if (key.endsWith("Dir")) {
					softNameAndDirOnCluster.put(key.replaceAll("Dir", ""),
							value);
				} else if (key.equals("userName")) {
					userName = value;
				} else if (key.equals("password")) {
					password = value;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/**
	 * 从linux端下载文件的方法
	 * 
	 * @param ip
	 *            要下载的linux端的ip
	 * @param userName
	 *            linux用户名
	 * @param password
	 *            linux密码
	 * @param sftpPort
	 *            sftp端口
	 * @throws JSchException 
	 */
	public static void downFile(String ip, String userName, String password,
			int sftpPort, String src, String des) throws Exception {
		JSch jsch = new JSch();
		Channel channel = null;
		
			Session session = jsch.getSession(userName, ip, sftpPort);
			session.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");// do not verify host
															// key
			session.setConfig(sshConfig);
			// session.setTimeout(timeout);
			session.setServerAliveInterval(92000);
			session.connect();
			// 参数sftp指明要打开的连接是sftp连接

			channel = session.openChannel("sftp");
			channel.connect();
			
			((ChannelSftp) channel).get(src, des);
			
			channel.disconnect();
			session.disconnect();
		

	}

	/**
	 * 从linux端下载文件的方法
	 * 
	 * @param ip
	 *            要下载的linux端的ip
	 * @param userName
	 *            linux用户名
	 * @param password
	 *            linux密码
	 * @param sftpPort
	 *            sftp端口
	 */
	public static void uploadFile(String ip, String userName, String password,
			int sftpPort, String src, String des) {
		JSch jsch = new JSch();
		Channel channel = null;
		try {
			Session session = jsch.getSession(userName, ip, sftpPort);
			session.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");// do not verify host
															// key
			session.setConfig(sshConfig);
			// session.setTimeout(timeout);
			session.setServerAliveInterval(92000);
			session.connect();
			// 参数sftp指明要打开的连接是sftp连接

			channel = session.openChannel("sftp");
			channel.connect();
			try {

				((ChannelSftp) channel).put(src, des);

			} catch (Exception e) {
				e.printStackTrace();
			}
			channel.disconnect();
			session.disconnect();
		} catch (JSchException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param ip
	 * @param userName
	 * @param password
	 * @param sftpPort
	 * @param command
	 *            the command to execute . commands divided by semicolon
	 */
	public static void executeShellCommand(String ip, String userName,
			String password, int sftpPort, String command) {
		JSch jsch = new JSch();
		Channel channel = null;
		try {
			Session session = jsch.getSession(userName, ip, sftpPort);
			session.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");// do not verify host
															// key
			session.setConfig(sshConfig);
			// session.setTimeout(timeout);
			session.setServerAliveInterval(92000);
			session.connect();

			channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);
			channel.connect();
			try {
				InputStream ins = channel.getInputStream();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(ins));
				String line = reader.readLine();
				while (line != null) {
					System.out.println(line);
					line = reader.readLine();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			channel.disconnect();
			session.disconnect();
		} catch (JSchException e) {
			e.printStackTrace();
		}
	}

}
