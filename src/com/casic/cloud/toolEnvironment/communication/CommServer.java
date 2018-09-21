package com.casic.cloud.toolEnvironment.communication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.casic.cloud.toolEnvironment.util.LinuxUtil;

/**
 * 工具通信服务
 * 
 * @author ml
 * 
 */
@Component
public class CommServer {
	// 服务socket
	private static ServerSocket serverSocket;
	public static List<Client> clients = new ArrayList<Client>();
//	static {
//		// 建立服务器
//		try {
//			serverSocket = new ServerSocket(9999);
//			LinuxUtil.loadProperties();
//			bootMpd();
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		// 等待客户接入
//		waitingForClients();
//
//	}
//
//	/**
//	 * 等待客户端接入
//	 */
//	public static void waitingForClients() {
//		new Thread() {
//			public void run() {
//
//				if (serverSocket != null)
//					while (true) {
//						try {
//							Socket socket = serverSocket.accept();
//							Client client = new Client(socket);
//
//						} catch (IOException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}
//
//						try {
//							Thread.sleep(200);
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//			}
//		}.start();
//
//	}
//
//	/**
//	 * boot mpd
//	 *
//	 * @throws IOException
//	 */
//	public static void bootMpd() throws IOException {
//		// generate mpd.hosts
//		File file = new File("mpd.hosts");
//		FileWriter fw = new FileWriter(file);
//		BufferedWriter bw = new BufferedWriter(fw);
//		Set<String> slaveIps = LinuxUtil.slaveServers.keySet();
//		Iterator<String> iterator = slaveIps.iterator();
//		// mpd.hosts contains mainServer
//		bw.write(LinuxUtil.mainServerIp);
//		bw.newLine();
//		while (iterator.hasNext()) {
//			String ip = iterator.next();
//			bw.write(ip);
//			bw.newLine();
//		}
//		bw.flush();
//		bw.close();
//		fw.close();
//		// launch mpi server
//		Process process1 = Runtime.getRuntime().exec("mpdallexit");
//		BufferedReader bufferedReader = new BufferedReader(
//				new InputStreamReader(process1.getInputStream()));
//		String string = null;
//		while ((string = bufferedReader.readLine()) != null) {
//
//			try {
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			System.out.println("mpdexit:"
//					+ new String(string.getBytes("GBK"), "UTF-8"));
//		}
//		int mpdhosts = slaveIps.size() + 1;
//		Process process2 = Runtime.getRuntime().exec(
//				"mpdboot -n " + mpdhosts + " -f mpd.hosts");
//		BufferedReader bufferedReader2 = new BufferedReader(
//				new InputStreamReader(process2.getInputStream()));
//		String string2 = null;
//
//		while ((string2 = bufferedReader2.readLine()) != null) {
//			try {
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			System.out.println("mpdboot:"
//					+ new String(string2.getBytes("GBK"), "UTF-8"));
//		}
//	}
//
//	public static ServerSocket getServerSocket() {
//		return serverSocket;
//	}
//
//	public static void setServerSocket(ServerSocket serverSocket) {
//		CommServer.serverSocket = serverSocket;
//	}

}
