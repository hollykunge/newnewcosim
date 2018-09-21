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

//import javax.servlet.ServletContext;
//
//import org.springframework.web.context.ContextLoader;
//import org.springframework.web.context.WebApplicationContext;





import com.casic.cloud.toolEnvironment.action.CallAction;
import com.casic.cloud.toolEnvironment.bean.ParasBean;
import com.casic.cloud.toolEnvironment.communication.Client;
import com.casic.cloud.toolEnvironment.communication.Message;

public class Component_6shiHelper {
	/**
	 * 生成六室的输入文件
	 * 
	 * @param tempList
	 * @param inputAddress
	 *            原模板输入文件的地址
	 * @throws Exception 
	 */
	public static void generateInput(List<ParasBean> paasBeans,
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

		String mhs = "", gj = "", chj = "";
//		WebApplicationContext webApplicationContext = ContextLoader
//				.getCurrentWebApplicationContext();
//		ServletContext servletContext = webApplicationContext
//				.getServletContext();

		for (int i = 0; i < paasBeans.size(); i++) {

			String realName = paasBeans.get(i).getRealName();
			String value = paasBeans.get(i).getValue();

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
//			if (realName.equals("gridPath") || realName.equals("boundaryPath")) {
//
//				value = value.replaceFirst("/.*?/", "");
//
//				value = servletContext.getRealPath("/") + value;
//
//				value = value.replaceAll("\\\\", "\\\\\\\\");
//				value = value.replaceAll("/", "\\\\\\\\");
//			}
			if (realName.equals("mic")) {
				if (value.equals("地面状态")) {
					value = "1";
				} else if (value.equals("飞行状态")) {
					value = "2";
				} else {
					value = "1";
				}

			}
			if (realName.equals("lt")) {
				if (value.equals("无粘")) {
					value = "0";
				} else if (value.equals("层流")) {
					value = "1";
				} else if (value.equals("湍流")) {
					value = "2";
				} else {
					value = "2";
				}
			}
			if (realName.equals("wmlx")) {
				if (value.equals("等温壁面")) {
					value = "1";
				} else if (value.equals("绝热壁面")) {
					value = "2";
				} else {
					value = "1";
				}
			}
			if (realName.equals("jsms")) {
				if (value.equals("定常")) {
					value = "0";
				} else if (value.equals("非定长")) {
					value = "1";
				} else {
					value = "0";
				}
			}
			if (realName.equals("jsfs")) {
				if (value.equals("开始计算")) {
					value = "0";
				} else if (value.equals("继续计算")) {
					value = "1";
				} else if (value.equals("多状态计算")) {
					value = "2";
				} else {
					value = "0";
				}
			}
			if (realName.equals("tlmx")) {
				if (value.equals("S-A")) {
					value = "1";
				} else if (value.equals("kw-SST")) {
					value = "2";
				} else {
					value = "1";
				}
			}
			if (realName.equals("coe_sst")) {
				if (value.equals("一阶")) {
					value = "0";
				} else if (value.equals("二阶")) {
					value = "1";
				} else if (value.equals("三阶")) {
					value = "2";
				} else {
					value = "0";
				}
			}
			if (realName.equals("sjtj")) {
				if (value.equals("显式")) {
					value = "0";
				} else if (value.equals("隐式")) {
					value = "1";
				} else {
					value = "0";
				}
			}
			if (realName.equals("szgs")) {
				if (value.equals("Roe")) {
					value = "4";
				} else if (value.equals("Steger_Warming")) {
					value = "5";
				} else if (value.equals("Ausmup")) {
					value = "6";
				} else {
					value = "4";
				}
			}
			if (realName.equals("nxcl")) {
				if (value.equals("Gauss")) {
					value = "3";
				} else if (value.equals("Thin")) {
					value = "4";
				} else {
					value = "3";
				}
			}
			if (realName.equals("lg-kt")) {
				if (value.equals("一阶")) {
					value = "1";
				} else if (value.equals("二阶")) {
					value = "2";
				} else if (value.equals("三阶")) {
					value = "3";
				} else {
					value = "1";
				}
			}
			if (realName.equals("les")) {
				if (value.equals("不输出")) {
					value = "0";
				} else if (value.equals("输出")) {
					value = "1";
				} else {
					value = "0";
				}
			}
			if (realName.equals("sclx")) {
				if (value.equals("3D全输出")) {
					value = "1";
				} else if (value.equals("2D局部输出")) {
					value = "2";
				} else {
					value = "1";
				}
			}
			if (realName.equals("scfx")) {
				if (value.equals("i")) {
					value = "1";
				} else if (value.equals("j")) {
					value = "2";
				} else if (value.equals("k")) {
					value = "3";
				} else {
					value = "1";
				}
			}

			if (realName.equals("pllx")) {
				if (value.equals("无喷流")) {
					value = "0";
				} else if (value.equals("有喷流")) {
					value = "1";
				} else {
					value = "0";
				}
			}
			if (realName.equals("plfx")) {
				if (value.equals("垂直壁面")) {
					value = "1";
				} else if (value.equals("指定方向")) {
					value = "2";
				} else {
					value = "1";
				}
			}

			if (realName.equals("Twall")) {
				if (value.equals("等温壁面")) {
					value = "1";
				} else if (value.equals("绝热壁面")) {
					value = "0";
				} else {
					value = "1";
				}
			}

			if (realName.equals("cont")) {
				if (value.equals("给定初场计算平均流场")) {
					value = "-1";
				} else if (value.equals("由程序自动生成初场计算平均流场")) {
					value = "0";
				} else if (value.equals("断点续算平均流场")) {
					value = "1";
				} else if (value.equals("输出平均流场")) {
					value = "2";
				} else if (value.equals("输出平均流场画图显示数据")) {
					value = "3";
				} else if (value.equals("采用AMS方法计算湍流脉动")) {
					value = "4";
				} else if (value.equals("求解g方程计算湍流脉动")) {
					value = "5";
				} else {
					value = "-1";
				}
			}

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
						+ dateFormat.format(date) + "," + "Mach" + mhs + ","
						+ "AttachAngle" + gj + "," + "SlipAngle" + chj);
		// 获得实例文件夹中的实例文件
		File instance = FileUtil.getExistInstanceStartWithRunId(softData
				.getProcess().getRunId().toString(), "Mach" + mhs + "," + "AttachAngle"
				+ gj + "," + "SlipAngle" + chj, originFile, true);
		if (instance == null) {
			
			// 如果实例文件为null就复制
			FileUtil.copyFile(templateDir, new File(instanceDirPath));
			instance = FileUtil.getExistInstanceStartWithRunId(softData
					.getProcess().getRunId().toString(), "Mach" + mhs + ","
					+ "AttachAngle" + gj + "," + "SlipAngle" + chj, originFile, true);
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
	 * 六室气动执行方法
	 * 
	 * @param callAction
	 * @param soft_path
	 * @param runtime
	 * @throws Exception 
	 */
	public static void executeQD(CallAction callAction, String soft_path,
			Runtime runtime, List<ParasBean> tempList, SoftData softData) throws Exception {
		String command = soft_path;

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
				.getProcess().getRunId().toString(), "Mach" + mhs + "," + "AttachAngle"
				+ gj + "," + "SlipAngle" + chj, originFile, true);
		if (instance == null) {
			// 如果实例文件为null就复制
			FileUtil.copyFile(templateDir, new File(instanceDirPath));
			instance = FileUtil.getExistInstanceStartWithRunId(softData
					.getProcess().getRunId().toString(), "Mach" + mhs + ","
					+ "AttachAngle" + gj + "," + "SlipAngle" + chj, originFile, true);
		}

		// 执行exe
		if (instance != null) {
			//set executeable
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
			List<ParasBean> tempList, SoftData softData, Client client) throws Exception {
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
		}

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
						+ dateFormat.format(date) + "," + "Mach" + mhs + ","
						+ "AttachAngle" + gj + "," + "SlipAngle" + chj);
		// 获得实例文件夹中的实例文件
		File instance = FileUtil.getExistInstanceStartWithRunId(softData
				.getProcess().getRunId().toString(), "Mach" + mhs + "," + "AttachAngle"
				+ gj + "," + "SlipAngle" + chj, originFile, true);
		if (instance == null) {
			// 如果实例文件为null就复制
			FileUtil.copyFile(templateDir, new File(instanceDirPath));
			instance = FileUtil.getExistInstanceStartWithRunId(softData
					.getProcess().getRunId().toString(), "Mach" + mhs + ","
					+ "AttachAngle" + gj + "," + "SlipAngle" + chj, originFile, true);
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
	public static void uploadInputFile(List<ParasBean> tempList ,
			Message message, Client client, SoftData softData) throws Exception {
		String mhs = "", gj = "", chj = "";
//		WebApplicationContext webApplicationContext = ContextLoader
//				.getCurrentWebApplicationContext();
//		ServletContext servletContext = webApplicationContext
//				.getServletContext();
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
		}
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
						+ dateFormat.format(date) + "," + "Mach" + mhs + ","
						+ "AttachAngle" + gj + "," + "SlipAngle" + chj);

		// 获得实例文件夹中的实例文件
		File instance = FileUtil.getExistInstanceStartWithRunId(softData
				.getProcess().getRunId().toString(), "Mach" + mhs + "," + "AttachAngle"
				+ gj + "," + "SlipAngle" + chj, originFile, true);
		if (instance == null) {
			// 如果实例文件为Null就复制模版文件夹从而创建实例文件夹
			FileUtil.copyFile(templateDir, new File(instanceDirPath));
			// 复制完了再获取实例文件夹中的实例文件
			instance = FileUtil.getExistInstanceStartWithRunId(softData
					.getProcess().getRunId().toString(), "Mach" + mhs + ","
					+ "AttachAngle" + gj + "," + "SlipAngle" + chj, originFile, true);
		}
		
		//开始上传
		
		byte[] fileBytes = message.getFileBytes();
		File inputFile = instance;
		try {
			FileOutputStream out = null;
			if(message.isStarted()){
				out = new FileOutputStream(inputFile, false);
			}else{
				out = new FileOutputStream(inputFile, true);
			}			
			out.write(fileBytes);
			out.flush();
			out.close();
			if (message.isEnd()) {
				client.setInputNumToUpload(client.getInputNumToUpload()-1);
				
			}

		} catch (IOException e2) {
			
			e2.printStackTrace();
		}
		
		
		

	}
}
