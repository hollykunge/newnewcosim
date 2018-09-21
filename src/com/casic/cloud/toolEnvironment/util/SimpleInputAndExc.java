package com.casic.cloud.toolEnvironment.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.casic.cloud.toolEnvironment.action.CallAction;
import com.casic.cloud.toolEnvironment.bean.ParasBean;
import com.casic.cloud.toolEnvironment.communication.Client;
import com.casic.cloud.toolEnvironment.communication.Message;

/**
 * simple class to generate inputfiles and exec pro for clientSide
 * 
 * @author Administrator
 * 
 */
public class SimpleInputAndExc {

	/**
	 * general function for generating input file
	 * 
	 * @param paasBeans
	 * @param inputAddress
	 * @param softData
	 * @throws Exception
	 */
	public static void generateNetworkInput(List<ParasBean> paasBeans,
			String inputAddress, SoftData softData) throws Exception {

		// 获取模板输入文件中的文本
		String text = "";
		Map<String, String> parasMap = null;
		if (inputAddress.endsWith("template")) {
			text = FileUtil.getTextFromFile(new File(inputAddress));
			// 从模板输入文件得到map
			parasMap = FileUtil.getTemplateParasMapFromTemplateFile(new File(
					inputAddress));
		} else {
			text = FileUtil
					.getTextFromFile(new File(inputAddress + ".template"));
			// 从模板输入文件得到map
			parasMap = FileUtil.getTemplateParasMapFromTemplateFile(new File(
					inputAddress + ".template"));
		}

		for (int i = 0; i < paasBeans.size(); i++) {

			String realName = paasBeans.get(i).getRealName();
			String value = paasBeans.get(i).getValue();
			// 更新map中的value值
			String templateAndValue = parasMap.get(realName);

			if (templateAndValue != null) {
				String templateAndValues[] = templateAndValue.split(",", 0);
				// 修改value初值为真实值
				if (templateAndValues.length == 2) {
					// System.out.println(realName+"::::::::"+templateAndValues[0]+"::::::"+value);
					parasMap.put(realName, templateAndValues[0] + "," + value);

				}
			}
		}

		// 遍历parasMap生成text
		text = text.replaceAll("\\$\\$", "");
		Set<String> keys = parasMap.keySet();
		Iterator<String> iterator = keys.iterator();
		while (iterator.hasNext()) {
			String templateAndValue = parasMap.get(iterator.next());
			String templateAndValues[] = templateAndValue.split(",", 0);

			if (templateAndValues.length == 2) {
				templateAndValues[0] = templateAndValues[0].replaceAll(
						"\\$\\$", "");

				text = text.replaceAll(templateAndValues[0],
						templateAndValues[1]);

			}
		}

		// 复制文件夹
		File originFile = new File(inputAddress);
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd,HH-mm");
		// 获得模板文件夹
		File templateDir = FileUtil.getTemplateDirStartWithTemplate(originFile);
		// 获得实例文件夹名
		String instanceDirPath = templateDir.getAbsolutePath().replaceAll(
				"template",
				softData.getProcess().getRunId() + ","
						+ dateFormat.format(date));
		// 获得实例文件夹中的实例文件
		File instance = FileUtil.getExistInstanceStartWithRunId(softData
				.getProcess().getRunId().toString(), "", originFile, true);
		if (instance == null) {

			// 如果实例文件为null就复制
			FileUtil.copyFile(templateDir, new File(instanceDirPath));
			instance = FileUtil.getExistInstanceStartWithRunId(softData
					.getProcess().getRunId().toString(), "", originFile, true);
		}
		if (instance != null) {
			// 生成实例文件夹下的输入文件

			FileWriter fw = new FileWriter(instance);
			fw.write(text);
			fw.flush();
			fw.close();

		}
	}

	/**
	 * general function for network tools
	 * 
	 * @param callAction
	 * @param soft_path
	 * @param runtime
	 * @param tempList
	 * @param softData
	 * @throws Exception
	 */
	public static void executeNetwork(CallAction callAction, String soft_path,
			Runtime runtime, List<ParasBean> tempList, SoftData softData)
			throws Exception {
		String command = soft_path;

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
						+ dateFormat.format(date));
		// 获得实例文件夹中的实例文件
		File instance = FileUtil.getExistInstanceStartWithRunId(softData
				.getProcess().getRunId().toString(), "", originFile, true);
		if (instance == null) {
			// 如果实例文件为null就复制
			FileUtil.copyFile(templateDir, new File(instanceDirPath));
			instance = FileUtil.getExistInstanceStartWithRunId(softData
					.getProcess().getRunId().toString(), "", originFile, true);
		}

		// 执行exe
		if (instance != null) {
			// set executeable
			instance.setExecutable(true);
			callAction.exeStep(instance.getAbsolutePath(), runtime);

		}

	}

	/**
	 * 
	 * @param filePath
	 * @param tempList
	 * @param softData
	 * @param client
	 * @throws Exception
	 */
	public static void downloadOutput(String filePath,
			List<ParasBean> tempList, SoftData softData, Client client)
			throws Exception {
		// 下载输出文件
		// 获得输出文件实例目录
		File originFile = new File(filePath);
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd,HH-mm");

		// 获得模板文件夹
		File templateDir = FileUtil.getTemplateDirStartWithTemplate(originFile);
		// 获得实例文件夹名
		String instanceDirPath = templateDir.getAbsolutePath().replaceAll(
				"template",
				softData.getProcess().getRunId() + ","
						+ dateFormat.format(date));
		// 获得实例文件夹中的实例文件
		File instance = FileUtil.getExistInstanceStartWithRunId(softData
				.getProcess().getRunId().toString(), "", originFile, true);
		if (instance == null) {
			// 如果实例文件为null就复制
			FileUtil.copyFile(templateDir, new File(instanceDirPath));
			instance = FileUtil.getExistInstanceStartWithRunId(softData
					.getProcess().getRunId().toString(), "", originFile, true);
		}

		// 下载
		if (instance != null) {
			File instanceOutput = instance;
			FileInputStream fins;
			byte[] fileBytes = null;
			try {
				long length = instanceOutput.length();
				fins = new FileInputStream(instanceOutput);
				long a = length / (1024 * 1024 * 10);
				long b = length % (1024 * 1024 * 10);
				for (int i = 0; i < a; i++) {

					fileBytes = new byte[1024 * 1024 * 10];
					fins.read(fileBytes);
					Message s_message = new Message();
					s_message.setType(6);
					s_message.setFilePath(filePath);
					s_message.setFileBytes(fileBytes);
					if (i == 0) {
						s_message.setStarted(true);
					} else {
						s_message.setStarted(false);
					}
					s_message.setEnd(false);
					client.sendMessage(s_message);
				}
				fileBytes = new byte[(int) b];
				fins.read(fileBytes);
				Message s_message = new Message();
				s_message.setType(6);
				s_message.setFilePath(filePath);
				s_message.setFileBytes(fileBytes);
				if (a == 0) {
					s_message.setStarted(true);
				} else {
					s_message.setStarted(false);
				}
				s_message.setEnd(true);
				client.sendMessage(s_message);
				fins.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				client.closeClient();

			}

		}
	}

	/**
	 * 
	 * @param tempList
	 * @param message
	 * @param client
	 * @param softData
	 * @throws Exception
	 */
	public static void uploadInputFile(List<ParasBean> tempList,
			Message message, Client client, SoftData softData) throws Exception {

		// 复制文件夹
		File originFile = new File(message.getFilePath());
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd,HH-mm");

		// 获得模板文件夹
		File templateDir = FileUtil.getTemplateDirStartWithTemplate(originFile);
		// 获得实例文件夹名
		String instanceDirPath = templateDir.getAbsolutePath().replaceAll(
				"template",
				softData.getProcess().getRunId() + ","
						+ dateFormat.format(date));

		// 获得实例文件夹中的实例文件
		File instance = FileUtil.getExistInstanceStartWithRunId(softData
				.getProcess().getRunId().toString(), "", originFile, true);
		if (instance == null) {
			// 如果实例文件为Null就复制模版文件夹从而创建实例文件夹
			FileUtil.copyFile(templateDir, new File(instanceDirPath));
			// 复制完了再获取实例文件夹中的实例文件
			instance = FileUtil.getExistInstanceStartWithRunId(softData
					.getProcess().getRunId().toString(), "", originFile, true);
		}

		// 开始上传

		byte[] fileBytes = message.getFileBytes();
		File inputFile = instance;
		try {
			FileOutputStream out = null;
			if (message.isStarted()) {
				out = new FileOutputStream(inputFile, false);
			} else {
				out = new FileOutputStream(inputFile, true);
			}
			out.write(fileBytes);
			out.flush();
			out.close();
			if (message.isEnd()) {
				client.setInputNumToUpload(client.getInputNumToUpload() - 1);
				// 当上传的输入文件上传完成后 发送输入文件生成完成消息
				if (client.getInputNumToUpload() == 0) {
					Message s_message = new Message();
					s_message.setType(3);
					client.sendMessage(s_message);
				}
			}

		} catch (IOException e2) {

			e2.printStackTrace();
		}
	}

}
