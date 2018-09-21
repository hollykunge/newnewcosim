package com.casic.cloud.toolEnvironment.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {
	/**
	 * 复制文件/文件夹的方法
	 * 
	 * @param src
	 * @param des
	 * @return
	 * @throws Exception 
	 */
	public static void copyFile(File src, File des) throws Exception {
		// 当目标文件夹不存在时
		if (!des.exists()) {
			// 创建des的父文件夹
			File desParent = des.getParentFile();
			desParent.mkdirs();
			if (src.isDirectory()) {
				File[] files = src.listFiles();
				for (int i = 0; i < files.length; i++) {
					File newSrc = files[i];
					File newDes = new File(des.getAbsolutePath() + "/"
							+ newSrc.getName());
					copyFile(newSrc, newDes);
				}
			} else {
				
					FileInputStream fins = new FileInputStream(src);
					FileOutputStream fous = new FileOutputStream(des);
					long fileLength = fins.available();
					int a = (int) (fileLength / (1024 * 1024 * 10));
					int b = (int) (fileLength % (1024 * 1024 * 10));

					byte buff[] = new byte[1024 * 1024 * 10];
					for (int i = 0; i < a; i++) {
						fins.read(buff);
						fous.write(buff);

					}
					buff = new byte[b];
					fins.read(buff);
					fous.write(buff);
					fous.flush();
					fins.close();
					fous.close();
				
			}
		}
	}

	/**
	 * 获取指定目录下以指定runid开头的文件夹 igoreParas 是否忽略mhs gj chj
	 * 
	 * @return
	 */
	public static File getExistDirStartWithRunId(String runId, String paras,
			File templateDir, boolean ignoreParas) {
		File instanceDir = null;
		File dirs[] = templateDir.getParentFile().listFiles();
		if (ignoreParas) {
			for (int i = 0; i < dirs.length; i++) {

				if (dirs[i].getName().contains(runId)) {
					instanceDir = dirs[i];
					break;
				}
			}
		} else {
			for (int i = 0; i < dirs.length; i++) {

				if (dirs[i].getName().contains(runId)
						&& dirs[i].getName().contains(paras)) {
					instanceDir = dirs[i];
					break;
				}
			}
		}

		return instanceDir;

	}

	/**
	 * 由流程实例号，流程参数(马赫数攻角侧滑角)，模板文件获得实例文件
	 * 
	 * @param runId
	 * @param paras
	 * @param templateFile
	 * @param ignoreParas
	 * @return
	 */
	public static File getExistInstanceStartWithRunId(String runId,
			String paras, File templateFile, boolean ignoreParas) {
		File instanceFile = null;
		File file_pos = templateFile;
		boolean isFind = false;
		while (!isFind) {
			file_pos = file_pos.getParentFile();
			if (file_pos == null) {
				break;
			}
			File files[] = file_pos.listFiles();
			
			if(files!=null)
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				if (ignoreParas) {
					if (file.getName().contains(runId)) {
						String instanceFilePath = templateFile
								.getAbsolutePath().replace("template",
										file.getName());
						instanceFile = new File(instanceFilePath);
						isFind = true;
						break;
					}
				} else {
					String instanceFilePath = templateFile.getAbsolutePath()
							.replace("template", file.getName());
					if (file.getName().contains(runId)
							&& file.getName().contains(paras)) {
						instanceFile = new File(instanceFilePath);
						isFind = true;
						break;

					}

				}

			}

		}

		return instanceFile;

	}
	/**
	 * 由原始的文件得到模板目录
	 * @param templateFile
	 * @return
	 */
	public static File getTemplateDirStartWithTemplate(File templateFile){
		File templateDir = null;
		if(templateFile!=null){
			boolean isFind = false;
			File file_pos = templateFile;
			while(!isFind){
				file_pos = file_pos.getParentFile();
				if(file_pos==null){
					break;	
				}
				if(file_pos.getName().equals("template")){
					templateDir = file_pos;
				}	
			}
		}
		return templateDir;
	}
	
	/**
	 * 从模板输入文件得到map 其中key为模板中的key value为$$key=value$$,'value'
	 * @param templateFile
	 * @return
	 */
	public static Map<String,String> getTemplateParasMapFromTemplateFile(File templateFile){
		Map<String, String> paras = new HashMap<String, String>();
		try{
			FileInputStream fins = new FileInputStream(templateFile);
			byte buff[] = new byte[(int) templateFile.length()];
			fins.read(buff);
			String text = new String(buff,"GBK");
			fins.close();
			//正则表达式提取text中的参数
			Pattern pattern = Pattern.compile("\\$\\$.*?\\$\\$");
			Matcher matcher = pattern.matcher(text);
			while(matcher.find()){
				String templateStr = matcher.group();
				String key_value[] = templateStr.replaceAll("\\$\\$", "").split("=", 0);
				if(key_value.length==2){
					String key = key_value[0];
					String value = key_value[1];
					paras.put(key,templateStr+","+value);
				}
				
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return paras;
	}
	
	/**
	 * 由输入文件得到输入文件中的文本
	 * @param templateFile
	 * @return
	 */
	public static String getTextFromFile(File templateFile){
		String text = "";
		FileInputStream fins = null;
		try{
			fins = new FileInputStream(templateFile);
			byte buff[] = new byte[(int) templateFile.length()];
			fins.read(buff);
			text = new String(buff,"gbk");
			fins.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return text;
		
	}
	
	/**
	 * get map data from error.dat.
	 * formate is line to line
	 * @param errorDataPath  error.dat's path
	 * @param stepName   nc name
	 * @param valueName  value name
	 * @return
	 */
	public static Map<String, String> generateErrorData(String errorDatPath,String stepName,String valueName){
		Map<String, String> errorDatas = new LinkedHashMap<String, String>();
		File file = new File(errorDatPath);
		if(file.exists())
		try {
			FileInputStream fins = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fins);
			BufferedReader reader = new BufferedReader(isr);
			String line = reader.readLine();
			while(line!=null){
								
				if(line.contains(stepName)&&line.contains(valueName)){
					//add space after "="
					line = line.replaceAll("=", "= ");
					String step = null,value = null;
					String[] strs = line.split("\\s{1,}", 0);
					for(int i = 0;i<strs.length;i++){
						if(strs[i].equals(stepName)){
							if(strs.length>i+1){
								step = strs[i+1];	
							}
						}else if(strs[i].equals(valueName)){
							if(strs.length>i+1){
								value = strs[i+1];	
							}
						}
						
					}
					if(step!=null&&value!=null){
						errorDatas.put(step, value);
					}
				}
				
				line = reader.readLine();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return errorDatas;
		
	}
	/**
	 * the function to delete file/dir
	 * @param path
	 * @return
	 */
	public static void delFile(String path){
		
		File file = new File(path);
		if(file!=null&&file.exists()){
			if(file.isFile()){
				file.delete();
			}else{
				File[] files = file.listFiles();
				for(File newFile:files){
					delFile(newFile.getAbsolutePath());
				}
				file.delete();
			}
			
		}
		
		
	}
}
