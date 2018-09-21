package com.casic.cloud.toolEnvironment.communication;

import com.casic.cloud.toolEnvironment.action.CallAction;
import com.casic.cloud.toolEnvironment.bean.Cloud_tool_user_inputFile;
import com.casic.cloud.toolEnvironment.bean.ParasBean;
import com.casic.cloud.toolEnvironment.util.ClusterHelper;
import com.casic.cloud.toolEnvironment.util.Component_3ShiHelper;
import com.casic.cloud.toolEnvironment.util.Component_6shiHelper;
import com.casic.cloud.toolEnvironment.util.SoftData;

import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * 通信工具接入的客户端
 * 
 * @author ml
 * 
 */
public class Client extends Thread {
	private Socket socket;
	private InputStream ins;
	private OutputStream ous;
	private ObjectInputStream oins;
	private ObjectOutputStream oous;
	private SoftData softData;
	// 要上传的输入文件个数
	private int inputNumToUpload = 0;
	// 服务端运行的进程
	private Process process;

	public Client(Socket socket) {
		this.socket = socket;
		try {
			ous = socket.getOutputStream();
			ins = socket.getInputStream();
			oous = new ObjectOutputStream(ous);
			oins = new ObjectInputStream(ins);

			CommServer.clients.add(this);
		} catch (IOException e) {
			closeClient();
		}
		start();
	}

	public void run() {
		while (true) {
			// 客户端
			try {
				// 读取消息对象
				final Message message = (Message) oins.readObject();
				// 处理消息对象
				new Thread() {

					public void run() {
						try {
							handleMessage(message);
						} catch (Exception e) {
							e.printStackTrace();
							closeClient();
						}

					}

				}.start();

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				closeClient();
				break;
			}

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * 处理message
	 * 
	 * @param message
	 * @throws Exception
	 */
	public void handleMessage(Message message) throws Exception {
		int type = message.getType();
		SoftData r_softData = message.getSoftData();
		if (r_softData != null) {
			softData = r_softData;
		}
		if (type == 1) {
			// 生成输入文件
			List<ParasBean> parasBeans = softData.getParasBeans();
			List<Cloud_tool_user_inputFile> inputFiles = softData
					.getInputFiles();
			for (Cloud_tool_user_inputFile inputFile : inputFiles) {

				// if input generateStrategy is canshuhuashengcheng
				String generateStrategy = inputFile.getGenerateStrategy();
				if (generateStrategy != null
						&& generateStrategy.equals("参数化生成")) {
					String inputAddress = "";
					inputAddress = inputFile.getInputAddress();
					try {
						// judge network or cluster
						if (softData.getCloud_tool().getToolType() == 2) {
							Component_6shiHelper.generateInput(parasBeans,
									inputAddress, softData);
						} else if (softData.getCloud_tool().getToolType() == 3) {
							ClusterHelper.generateInput(parasBeans,
									inputAddress, softData);
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} else {
					// caculate inputNum
					inputNumToUpload++;

				}
			}
			// if there is no inputfile to upload finish generate input and send
			// message
			if (inputNumToUpload == 0) {
				Message s_message = new Message();
				s_message.setType(3);
				sendMessage(s_message);
			}

		} else if (type == 2) {
			List<ParasBean> parasBeans = softData.getParasBeans();
			// execute tool
			Runtime runtime = Runtime.getRuntime();
			String soft_path = softData.getCloud_tool_user().getMytoolAddress();

			if (soft_path == null || soft_path == "") {
				soft_path = softData.getCloud_tool().getToolAddress();
			}
			String command = soft_path;
			CallAction callAction = new CallAction(softData, this);
			if (soft_path.endsWith("sanshi.bat")) {
				Component_3ShiHelper.executQD(softData, callAction, runtime,
						command, soft_path);
			} else {
				//network
				if(softData.getCloud_tool().getToolType()==2){
					Component_6shiHelper.executeQD(callAction, soft_path, runtime,
							parasBeans, softData);
				}
				//cluster
				else if(softData.getCloud_tool().getToolType()==3){
					ClusterHelper.execute(callAction, soft_path, parasBeans, softData);
				}
				
			}
			process = null;
			Message s_message = new Message();
			s_message.setType(4);
			sendMessage(s_message);

		} else if (type == 5) {
			List<ParasBean> parasBeans = softData.getParasBeans();
			// 6室下载输出文件请求
			//cluster downloading from the same source...but when execute the tool we should get output from cluster continuously
			String filePath = message.getFilePath();
			
			Component_6shiHelper.downloadOutput(filePath, parasBeans, softData,
					this);

		} else if (type == 7) {
			// 上传输入文件请求

			List<ParasBean> parasBeans = softData.getParasBeans();
			if(softData.getCloud_tool().getToolType()==2){
				Component_6shiHelper.uploadInputFile(parasBeans, message, this,
						softData);
			}else if(softData.getCloud_tool().getToolType()==3){
				ClusterHelper.uploadInputFile(parasBeans, message, this, softData);
			}
			
			// 当上传的输入文件上传完成后 发送输入文件生成完成消息
			if (inputNumToUpload == 0) {
				Message s_message = new Message();
				s_message.setType(3);
				sendMessage(s_message);
			}
		} else if (type == 8) {

			// 强制停止工具请求
			if (process != null) {
				process.destroy();
				process=null;
			}
			Message s_message = new Message();
			s_message.setType(4);
			sendMessage(s_message);
		}
	}

	/**
	 * 发送消息
	 * 
	 * @param message
	 */
	public void sendMessage(Message message) {
		try {
			oous.writeObject(message);
			oous.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closeClient();
		}

	}

	/**
	 * 关闭客户端
	 */
	public void closeClient() {

		try {
			if (oins != null) {
				oins.close();
			}
			if (oous != null) {
				oous.close();
			}
			if (ins != null) {
				ins.close();
			}
			if (ous != null) {
				ous.close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		CommServer.clients.remove(this);

	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public InputStream getIns() {
		return ins;
	}

	public void setIns(InputStream ins) {
		this.ins = ins;
	}

	public OutputStream getOus() {
		return ous;
	}

	public void setOus(OutputStream ous) {
		this.ous = ous;
	}

	public ObjectInputStream getOins() {
		return oins;
	}

	public void setOins(ObjectInputStream oins) {
		this.oins = oins;
	}

	public ObjectOutputStream getOous() {
		return oous;
	}

	public void setOous(ObjectOutputStream oous) {
		this.oous = oous;
	}

	public SoftData getSoftData() {
		return softData;
	}

	public void setSoftData(SoftData softData) {
		this.softData = softData;
	}

	public int getInputNumToUpload() {
		return inputNumToUpload;
	}

	public void setInputNumToUpload(int inputNumToUpload) {
		this.inputNumToUpload = inputNumToUpload;
	}

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

}
