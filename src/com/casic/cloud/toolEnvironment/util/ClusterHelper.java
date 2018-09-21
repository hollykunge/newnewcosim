package com.casic.cloud.toolEnvironment.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import com.casic.cloud.toolEnvironment.action.CallAction;
import com.casic.cloud.toolEnvironment.bean.Cloud_tool_user_outputFile;
import com.casic.cloud.toolEnvironment.bean.ParasBean;
import com.casic.cloud.toolEnvironment.communication.Client;
import com.casic.cloud.toolEnvironment.communication.Message;

/**
 * cluster helper
 * 
 * @author Administrator
 * 
 */
public class ClusterHelper {
	/**
	 * generate input file on cluster
	 * 
	 * @param paasBeans
	 * @param inputAddress
	 * @param softData
	 * @throws Exception
	 */
	public static void generateInput(List<ParasBean> paasBeans,
			String inputAddress, SoftData softData) throws Exception {
		// generate on main server..
		Component_6shiHelper.generateInput(paasBeans, inputAddress, softData);
		// generate on slave server..
		File originFile = new File(inputAddress);
		final File templateDir = FileUtil
				.getTemplateDirStartWithTemplate(originFile);
		final File instanceDir = FileUtil.getExistDirStartWithRunId(softData
				.getProcess().getRunId().toString(), "", templateDir, true);
		final File instance = FileUtil.getExistInstanceStartWithRunId(softData
				.getProcess().getRunId().toString(), "", originFile, true);

		if (instanceDir != null && instance != null) {
			Set<String> slaveIps = LinuxUtil.slaveServers.keySet();
			Iterator<String> iterator = slaveIps.iterator();
			//
			final CyclicBarrier barrier = new CyclicBarrier(slaveIps.size() + 1);
			while (iterator.hasNext()) {
				final String slaveIp = iterator.next();
				String userNameAndPassword = LinuxUtil.slaveServers
						.get(slaveIp);
				final String userName = userNameAndPassword.split(",", 0)[0];
				final String password = userNameAndPassword.split(",", 0)[1];
				final String command = "mkdir " + instanceDir.getAbsolutePath()
						+ ";" + "cp -rf -u " + templateDir.getAbsolutePath()
						+ "/* " + instanceDir.getAbsolutePath();

				new Thread() {
					public void run() {
						// copy dirs on slave
						LinuxUtil.executeShellCommand(slaveIp, userName,
								password, 22, command);
						// upload input file
						LinuxUtil.uploadFile(slaveIp, userName, password, 22,
								instance.getAbsolutePath(),
								instance.getAbsolutePath());
						try {
							barrier.await();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (BrokenBarrierException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}.start();

			}
			barrier.await();
		}
	}

	/**
	 * upload for cluster
	 * 
	 * @param tempList
	 * @param message
	 * @param client
	 * @param softData
	 * @throws Exception
	 */
	public static void uploadInputFile(List<ParasBean> tempList,
			Message message, Client client, SoftData softData) throws Exception {
		// upload input file on main node...
		Component_6shiHelper.uploadInputFile(tempList, message, client,
				softData);
		// upload input file on slave server..
		File originFile = new File(message.getFilePath());
		final File templateDir = FileUtil
				.getTemplateDirStartWithTemplate(originFile);
		final File instanceDir = FileUtil.getExistDirStartWithRunId(softData
				.getProcess().getRunId().toString(), "", templateDir, true);
		final File instance = FileUtil.getExistInstanceStartWithRunId(softData
				.getProcess().getRunId().toString(), "", originFile, true);

		if (instanceDir != null && instance != null) {
			Set<String> slaveIps = LinuxUtil.slaveServers.keySet();
			Iterator<String> iterator = slaveIps.iterator();
			//
			final CyclicBarrier barrier = new CyclicBarrier(slaveIps.size() + 1);
			while (iterator.hasNext()) {
				final String slaveIp = iterator.next();
				String userNameAndPassword = LinuxUtil.slaveServers
						.get(slaveIp);
				final String userName = userNameAndPassword.split(",", 0)[0];
				final String password = userNameAndPassword.split(",", 0)[1];
				final String command = "mkdir " + instanceDir.getAbsolutePath()
						+ ";" + "cp -rf -u " + templateDir.getAbsolutePath()
						+ "/* " + instanceDir.getAbsolutePath();

				new Thread() {
					public void run() {
						// copy dirs on slave
						LinuxUtil.executeShellCommand(slaveIp, userName,
								password, 22, command);
						// upload input file
						LinuxUtil.uploadFile(slaveIp, userName, password, 22,
								instance.getAbsolutePath(),
								instance.getAbsolutePath());
						try {
							barrier.await();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (BrokenBarrierException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}.start();

			}
			barrier.await();
		}

	}

	/**
	 * execute tools on cluster
	 * 
	 * @param callAction
	 * @param soft_path
	 * @param runtime
	 * @param tempList
	 * @param softData
	 * @throws Exception
	 */
	public static void execute(final CallAction callAction, String soft_path,
			List<ParasBean> tempList, final SoftData softData) throws Exception {
		String command = soft_path;
		// the node number and process number per node to execute the tool on
		// cluster
		String nodeNum = "1", processNumPerNode = "1";

		String mhs = "", gj = "", chj = "";
		// 找到Mach AttachAngle SlipAngle
		for (int i = 0; i < tempList.size(); i++) {
			String realName = tempList.get(i).getRealName();
			String value = tempList.get(i).getValue();

			// 找到Mach AttachAngle和SlipAngle
			if (realName.equals("mhs")) {
				mhs = value;
			}
			if (realName.equals("gj")) {
				gj = value;
			}
			if (realName.equals("chj")) {
				chj = value;
			}
			if (realName.equals("nodeNum")) {
				nodeNum = value;
			}
			if (realName.equals("processNumPerNode")) {
				processNumPerNode = value;
			}
		}

		// 复制文件夹
		File originFile = new File(soft_path);
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd,HH-mm");

		// 获得模板文件夹
		File templateDir = FileUtil.getTemplateDirStartWithTemplate(originFile);
		// 获得实例文件夹名

		String instanceDirPath = templateDir.getAbsolutePath().replaceAll(
				"template",
				softData.getProcess().getRunId() + ","
						+ dateFormat.format(date) + "," + "Mach" + mhs + ","
						+ "AttachAngle" + gj + "," + "SlipAngle" + chj);
		// 获得实例文件夹中的实例文件
		File instance = FileUtil.getExistInstanceStartWithRunId(softData
				.getProcess().getRunId().toString(), "Mach" + mhs + ","
				+ "AttachAngle" + gj + "," + "SlipAngle" + chj, originFile,
				true);

		// 执行exe
		if (instance != null) {
			// set executable
			instance.setExecutable(true);
			// set executable on cluster
			Set<String> slaveIps = LinuxUtil.slaveServers.keySet();
			Iterator<String> iterator = slaveIps.iterator();
			//
			final CyclicBarrier barrier = new CyclicBarrier(slaveIps.size() + 1);
			File machines = new File("machines");
			FileWriter fw = new FileWriter(machines);
			BufferedWriter bw = new BufferedWriter(fw);
			int count = 0;
			while (iterator.hasNext()) {
				final String slaveIp = iterator.next();
				String userNameAndPassword = LinuxUtil.slaveServers
						.get(slaveIp);
				if (count < new Integer(nodeNum)) {
					bw.write(slaveIp + ":" + processNumPerNode);
					bw.newLine();
					count++;
				}

				final String userName = userNameAndPassword.split(",", 0)[0];
				final String password = userNameAndPassword.split(",", 0)[1];
				final String command1 =  "chmod u+x "+ instance.getAbsolutePath() ;
				new Thread() {
					public void run() {
						// set executable on slave
						LinuxUtil.executeShellCommand(slaveIp, userName,
								password, 22,command1);
						try {
							barrier.await();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (BrokenBarrierException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}.start();

			}
			bw.flush();
			bw.close();
			fw.close();
			barrier.await();
			// execute cluster
			callAction.executeCluster(machines.getAbsolutePath(), new Integer(
					nodeNum) * new Integer(processNumPerNode),
					instance.getAbsolutePath());

		}

	}

}
