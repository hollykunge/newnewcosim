package com.casic.cloud.toolEnvironment.action;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.casic.cloud.toolEnvironment.bean.Cloud_tool_user_inputFile;
import com.casic.cloud.toolEnvironment.bean.Cloud_tool_user_outputFile;
import com.casic.cloud.toolEnvironment.communication.Client;
import com.casic.cloud.toolEnvironment.communication.Message;
import com.casic.cloud.toolEnvironment.ui.MainFrame;
import com.casic.cloud.toolEnvironment.util.Component_3ShiHelper;
import com.casic.cloud.toolEnvironment.util.FileUtil;
import com.casic.cloud.toolEnvironment.util.LabelAndFiled;
import com.casic.cloud.toolEnvironment.util.LinuxUtil;
import com.casic.cloud.toolEnvironment.util.SoftData;

public class CallAction {

	private SoftData softData;
	private Client client;

	public CallAction(SoftData softData, Client client) {
		this.softData = softData;
		this.client = client;
	}

	/**
	 * µ¥²½Ö´ÐÐ
	 */
	public void exeStep(String command, Runtime runtime) {

		for (int i = 0; i < softData.getInvokeParas().length; i++) {
			command += " " + softData.getInvokeParas()[i];
		}
		Process process = null;
		try {
			// //judge os
			// String os = System.getProperty("os.name").toUpperCase();
			// if(os.contains("WINDOWS")){
			// String disk = command.substring(0,2);
			// String splits[] = command.split("\\\\", 0);
			// String soft = splits[splits.length-1];
			// String path = command.replaceAll(soft, "");
			// File file = new File("tool.bat");
			// // FileWriter fw = new FileWriter(file);
			// FileOutputStream fw = new FileOutputStream(file);
			// BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fw,
			// "GBK"));
			// bw.write(disk);
			// bw.newLine();
			// bw.write("cd \""+path+"\"");
			// bw.newLine();
			// bw.write("\""+soft+"\"");
			// bw.newLine();
			// bw.flush();
			// bw.close();
			// file.setExecutable(true);
			// process = runtime.exec("tool.bat");
			// }else{
			// String soft = new File(command).getName();
			// String path = command.replaceAll(soft, "");
			// File file = new File("tool.bat");
			// FileOutputStream fw = new FileOutputStream(file);
			// BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
			// fw, "GBK"));
			// bw.write("cd " + path);
			// bw.newLine();
			// bw.write("./" + soft);
			// bw.newLine();
			// bw.flush();
			// bw.close();
			// file.setExecutable(true);
			// process = runtime.exec("./tool.bat");
			// }
			process = runtime.exec(command);
			client.setProcess(process);
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));
			String string = null;
			while ((string = bufferedReader.readLine()) != null) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(new String(string.getBytes("GBK"), "UTF-8"));
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}
	}

	/**
	 * execute cluster
	 * 
	 * @param command
	 *            the command to execute
	 * @param machineFile
	 *            the machinefile path
	 * @param processNum
	 *            the process to run
	 * @throws IOException
	 */
	public void executeCluster(final String machineFile, final int processNum,
			final String softPath) throws IOException {
		Process process = null;
		// judge os
		String os = System.getProperty("os.name").toUpperCase();
		if (os.contains("WINDOWS")) {
			String disk = softPath.substring(0, 2);
			String splits[] = softPath.split("\\\\", 0);
			String soft = splits[splits.length - 1];
			String path = softPath.replaceAll(soft, "");
			File file = new File("tool.bat");
			// FileWriter fw = new FileWriter(file);
			FileOutputStream fw = new FileOutputStream(file);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fw,
					"GBK"));
			bw.write(disk);
			bw.newLine();
			bw.write("cd \"" + path + "\"");
			bw.newLine();
			bw.write("mpiexec -machinefile " + machineFile + " -n "
					+ processNum + " " + "\"" + soft + "\"");
			bw.newLine();
			bw.flush();
			bw.close();
			file.setExecutable(true);
			process = Runtime.getRuntime().exec("tool.bat");
		} else {
			String soft = new File(softPath).getName();
			String path = softPath.replaceAll(soft, "");
			File file = new File("tool.bat");
			FileOutputStream fw = new FileOutputStream(file);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fw,
					"GBK"));
			bw.write("cd " + path);
			bw.newLine();
			bw.write("mpiexec -machinefile " + machineFile + " -n "
					+ processNum + " " + "./" + soft);
			bw.newLine();
			bw.flush();
			bw.close();
			file.setExecutable(true);
			process = Runtime.getRuntime().exec("./tool.bat");
		}
		// process = Runtime.getRuntime().exec(
		// "mpiexec -machinefile " + machineFile + " -n " + processNum
		// + " " + softPath);
		client.setProcess(process);

		// open thread to capture output file
		new Thread() {
			public void run() {
				String ip = "";
				String userNameAndPassword = "";
				String userName = "";
				String password = "";
				Set<String> slaveIps = LinuxUtil.slaveServers.keySet();
				Iterator<String> iterator = slaveIps.iterator();
				if (iterator.hasNext()) {
					ip = iterator.next();
					userNameAndPassword = LinuxUtil.slaveServers.get(ip);
					userName = userNameAndPassword.split(",", 0)[0];
					password = userNameAndPassword.split(",", 0)[1];
				}

				while (client.getProcess() != null) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for (Cloud_tool_user_outputFile outputFile : softData
							.getOutputFiles()) {

						File originFile = new File(
								outputFile.getOutputAddress());

						File instance = FileUtil
								.getExistInstanceStartWithRunId(softData
										.getProcess().getRunId().toString(),
										"", originFile, true);
						// System.out.println("downloading!!!!!!!"+instance.getAbsolutePath());
						try {
							LinuxUtil.downFile(ip, userName, password, 22,
									instance.getAbsolutePath(),
									instance.getAbsolutePath());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();

						}

					}
				}

			}

		}.start();

		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(process.getInputStream()));
		String string = null;
		try{
			while ((string = bufferedReader.readLine()) != null) {

				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println(new String(string.getBytes("GBK"), "UTF-8"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		

	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}
