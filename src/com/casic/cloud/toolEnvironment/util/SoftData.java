package com.casic.cloud.toolEnvironment.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Session;

import com.casic.cloud.toolEnvironment.DB.HibernateUtil;
import com.casic.cloud.toolEnvironment.bean.*;



public class SoftData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String paras_str = "";
	private String[] invokeParas ;
	private String[] paras_str_array;
	private Map<String, String> input_paras = new LinkedHashMap();
	private Cloud_tool_bpm_node cloud_tool_bpm_node;
	private Cloud_tool_user cloud_tool_user;
	private Cloud_tool cloud_tool;
	private List<Cloud_tool_user_inputFile> inputFiles = new ArrayList<Cloud_tool_user_inputFile>();
	private List<Cloud_tool_user_outputFile> outputFiles = new ArrayList<Cloud_tool_user_outputFile>();
	private List<Cloud_tool_user_parasmapper> cloud_tool_user_parasmappers;
	private Cloud_tool_user_outputFile cloud_tool_user_outputFile;
	private List<ParasBean> parasBeans;
	private Act_ru_task task;
	private Bpm_pro_run process;
	
	private byte type = 0;

	public SoftData(String paras_str) {

		// ȥ��ĩβ��/
		if (paras_str.endsWith("/")) {
			paras_str = paras_str.substring(0, paras_str.length() - 1);
		}
		this.paras_str = paras_str;
	}

	/**
	 * ȥ��pras_str��Э��ͷ
	 */
	public void removeProtocolHeader() {
		if (paras_str != null) {
			paras_str = paras_str.replaceAll("cosim.*:[/]{2,3}", "");
		}
	}

	/**
	 * ���ִ�в�������
	 */
	public void generateInvokeParas() {
		Pattern pattern = Pattern.compile("\\{.*\\}");
		Matcher matcher = pattern.matcher(paras_str);
		boolean find = matcher.find();
		if (find) {
			String invokeStr = matcher.group();
			invokeStr = invokeStr.replaceAll("\\{", "");
			invokeStr = invokeStr.replaceAll("\\}", "");
			//
			invokeParas = invokeStr.split(",", 0);
			if (invokeParas.length == 1 && (invokeParas[0].length() == 0)) {
				invokeParas = new String[0];
			}
			// ��input_str�е�invokeParas����ȥ��
			paras_str = paras_str.replaceAll("&invokeParas=\\{.*\\}", "");
		}

	}

	/**
	 * ����������map
	 */
	public void generateInputParas() {
		generatePara_str_arrayFromPara_str();
		generateParaMapFromPara_str_array();
	}

	/**
	 * ��paras_str�ָ�ɲ����ʽ����
	 */
	public void generatePara_str_arrayFromPara_str() {
		if (paras_str != null) {
			paras_str_array = paras_str.split("&", 0);
		}

	}

	/**
	 * ��paras_str_array���paras��ֵ��
	 */
	public void generateParaMapFromPara_str_array() {

		if (paras_str_array != null) {
			for (int i = 0; i < paras_str_array.length; i++) {
				String str = paras_str_array[i];
				String strs[] = str.split("=", 0);
				if (strs.length == 2) {
					input_paras.put(strs[0], strs[1]);
				}
			}
		}

	}

	/**
	 * �ж�ִ������
	 * 
	 * @return
	 */
	public byte judgeType() {

		type = new Byte(input_paras.get("type"));
		input_paras.remove("type");
		return type;
	}

	/**
	 * ��id����ݿ���װ��soft_config
	 * 
	 */
	public void loadSoft_config() {
		// ȡ��id
		String idStr = input_paras.get("toolNodeId");
		String outputFileId = input_paras.get("outputFileId");
		if (type == 1) {
			if (idStr != null) {
				Long id = new Long(idStr);
				Session session = HibernateUtil.getSessionFactory()
						.getCurrentSession();
				session.beginTransaction();
				cloud_tool_bpm_node = (Cloud_tool_bpm_node) session.get(
						Cloud_tool_bpm_node.class, id);
				cloud_tool_user = cloud_tool_bpm_node.getCloud_tool_user();
				cloud_tool = cloud_tool_user.getCloud_tool();
				cloud_tool_user_parasmappers = cloud_tool_user
						.getCloud_tool_user_parasmappers();
				if (!input_paras.containsKey("inputFileId")
						|| input_paras.get("inputFileId").equals("undefined")) {
					session.getTransaction().commit();
					return;
				}
				String inputFileIds[] = input_paras.get("inputFileId").split(",", 0);
				for (int i = 0; i < inputFileIds.length; i++) {
					Cloud_tool_user_inputFile inputFile = (Cloud_tool_user_inputFile) session
							.get(Cloud_tool_user_inputFile.class, new Long(inputFileIds[i]));
					inputFiles.add(inputFile);
				}
				
				outputFiles = cloud_tool_user.getCloud_tool_user_outputFiles();
				for(Cloud_tool_user_outputFile outputFile:outputFiles){
					
				}
				session.getTransaction().commit();

			} else {
				System.out.println("û�д˹���");

			}
		} else if (type == 2) {
			if (outputFileId != null) {
				Session session = HibernateUtil.getSessionFactory()
						.getCurrentSession();
				session.beginTransaction();
				cloud_tool_user_outputFile = (Cloud_tool_user_outputFile) session
						.get(Cloud_tool_user_outputFile.class, new Long(
								outputFileId));

				session.getTransaction().commit();
			} else {
				System.out.println("û�д�����ļ�");
			}

		}

	}

	/**
	 * ��ݴ���?������ ��ò����Ӧ��������
	 * 
	 * @param name
	 * @return
	 */
	public String getChineseName(String name) {
		String chineseName = "";

		for (int i = 0; i < cloud_tool_user_parasmappers.size(); i++) {
			Cloud_tool_user_parasmapper soft_paras_mapper = cloud_tool_user_parasmappers
					.get(i);
			if (soft_paras_mapper.getName().equals(name)
					|| soft_paras_mapper.getForm_mapper_name().equals(name)
					|| soft_paras_mapper.getChinese_mapper_name().equals(name)) {
				chineseName = soft_paras_mapper.getChinese_mapper_name();
				return chineseName;
			}
		}
		return name;
	}

	/**
	 * ��ݴ���ı?�������ö�Ӧ���ļ��еĲ�����
	 * 
	 * @param name
	 * @return
	 */
	public String getRealName(String name) {
		String realName = name;

		for (int i = 0; i < cloud_tool_user_parasmappers.size(); i++) {
			Cloud_tool_user_parasmapper soft_paras_mapper = cloud_tool_user_parasmappers
					.get(i);
			if (soft_paras_mapper.getName().equals(name)
					|| soft_paras_mapper.getForm_mapper_name().equals(name)
					|| soft_paras_mapper.getChinese_mapper_name().equals(name)) {
				realName = soft_paras_mapper.getName();

				return realName;
			}
		}
		return realName;

	}
	/**
	 * 由表单参数名得到参数类型
	 * @param name
	 * @return
	 */
	public String getType(String name){
		String type="未知类型";
		for (int i = 0; i < cloud_tool_user_parasmappers.size(); i++) {
			Cloud_tool_user_parasmapper soft_paras_mapper = cloud_tool_user_parasmappers
					.get(i);
			
			if (soft_paras_mapper.getName().equals(name)
					|| soft_paras_mapper.getForm_mapper_name().equals(name)
					|| soft_paras_mapper.getChinese_mapper_name().equals(name)) {
				type = soft_paras_mapper.getType();
				
				if(type==null){
					type = "未知类型";
				}
				return type;
			}
		}
		return type;
	}
	public String getParas_str() {
		return paras_str;
	}

	public void setParas_str(String paras_str) {
		this.paras_str = paras_str;
	}

	public String[] getInvokeParas() {
		return invokeParas;
	}

	public void setInvokeParas(String[] invokeParas) {
		this.invokeParas = invokeParas;
	}

	public String[] getParas_str_array() {
		return paras_str_array;
	}

	public void setParas_str_array(String[] paras_str_array) {
		this.paras_str_array = paras_str_array;
	}

	public Map<String, String> getInput_paras() {
		return input_paras;
	}

	public void setInput_paras(Map<String, String> input_paras) {
		this.input_paras = input_paras;
	}

	public Cloud_tool_bpm_node getCloud_tool_bpm_node() {
		return cloud_tool_bpm_node;
	}

	public void setCloud_tool_bpm_node(Cloud_tool_bpm_node cloud_tool_bpm_node) {
		this.cloud_tool_bpm_node = cloud_tool_bpm_node;
	}

	public Cloud_tool_user getCloud_tool_user() {
		return cloud_tool_user;
	}

	public void setCloud_tool_user(Cloud_tool_user cloud_tool_user) {
		this.cloud_tool_user = cloud_tool_user;
	}

	public Cloud_tool getCloud_tool() {
		return cloud_tool;
	}

	public void setCloud_tool(Cloud_tool cloud_tool) {
		this.cloud_tool = cloud_tool;
	}



	public List<Cloud_tool_user_inputFile> getInputFiles() {
		return inputFiles;
	}

	public void setInputFiles(List<Cloud_tool_user_inputFile> inputFiles) {
		this.inputFiles = inputFiles;
	}

	public List<Cloud_tool_user_parasmapper> getCloud_tool_user_parasmappers() {
		return cloud_tool_user_parasmappers;
	}

	public void setCloud_tool_user_parasmappers(
			List<Cloud_tool_user_parasmapper> cloud_tool_user_parasmappers) {
		this.cloud_tool_user_parasmappers = cloud_tool_user_parasmappers;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public Cloud_tool_user_outputFile getCloud_tool_user_outputFile() {
		return cloud_tool_user_outputFile;
	}

	public void setCloud_tool_user_outputFile(
			Cloud_tool_user_outputFile cloud_tool_user_outputFile) {
		this.cloud_tool_user_outputFile = cloud_tool_user_outputFile;
	}

	public List<Cloud_tool_user_outputFile> getOutputFiles() {
		return outputFiles;
	}

	public void setOutputFiles(List<Cloud_tool_user_outputFile> outputFiles) {
		this.outputFiles = outputFiles;
	}
	

	public List<ParasBean> getParasBeans() {
		return parasBeans;
	}

	public void setParasBean(List<ParasBean> parasBeans) {
		this.parasBeans = parasBeans;
	}

	public Act_ru_task getTask() {
		return task;
	}

	public void setTask(Act_ru_task task) {
		this.task = task;
	}

	public Bpm_pro_run getProcess() {
		return process;
	}

	public void setProcess(Bpm_pro_run process) {
		this.process = process;
	}
	
}
