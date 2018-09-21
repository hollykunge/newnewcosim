package com.casic.cloud.toolEnvironment.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import com.casic.cloud.toolEnvironment.action.CallAction;
import com.casic.cloud.toolEnvironment.bean.Cloud_tool_user_inputFile;
import com.casic.cloud.toolEnvironment.bean.Component_3Shi;
import com.casic.cloud.toolEnvironment.bean.ParasBean;



public class Component_3ShiHelper {
	public static List<Component_3Shi> components = new ArrayList();
	/**
	 * 生成部件列表 不包含整体信息
	 * @param parasBeans
	 */
	public  static void generateComponents(List<ParasBean> parasBeans){
		
		components.clear();
		int count = 0;
		//当count = 0时 countStr = ""
		String countStr = "";
		boolean isOver = false;
		String num = "",name="",minRefine = "",maxRefine = "",area="",length = "",momentPoint = "",momentLine1="",momentLine2 = "",plane1 = "",plane2 = "",plane3 = "";
		
		while(!isOver){
			if(count==0){
				countStr = "";
			}else{
				countStr = ""+count;
			}
			//编号num
			for (int i = 0;i<parasBeans.size();i++) {
			
				ParasBean parasBean = parasBeans.get(i);
				
				if(parasBean.getRealName().equals("num")&&parasBean.getChineseName().matches(".*"+countStr+":")){
					num = parasBean.getValue();
					break;
				}	
//				if(i == parasBeans.size()-1){
//					isOver = true;
//				}
				
			}
			//名称name
			for (int i = 0;i<parasBeans.size();i++) {

				ParasBean parasBean = parasBeans.get(i);
				if(parasBean.getRealName().equals("name")&&parasBean.getChineseName().matches(".*"+countStr+":")){
					name = parasBean.getValue();
					break;
				}	
//				if(i == parasBeans.size()-1){
//					isOver = true;
//				}
			}
			//最小细化数minRefine
			for (int i = 0;i<parasBeans.size();i++) {
				
				ParasBean parasBean = parasBeans.get(i);
				if(parasBean.getRealName().equals("minRefine")&&parasBean.getChineseName().matches(".*"+countStr+":")){
					minRefine = parasBean.getValue();
					break;
				}	
//				if(i == parasBeans.size()-1){
//					isOver = true;
//				}
			}
			//最大细化数maxRefine
			for (int i = 0;i<parasBeans.size();i++) {
				
				ParasBean parasBean = parasBeans.get(i);
				if(parasBean.getRealName().equals("maxRefine")&&parasBean.getChineseName().matches(".*"+countStr+":")){
					maxRefine = parasBean.getValue();
					break;
				}	
//				if(i == parasBeans.size()-1){
//					isOver = true;
//				}
			}
			//面积area
			for (int i = 0;i<parasBeans.size();i++) {
				
				ParasBean parasBean = parasBeans.get(i);
				if(parasBean.getRealName().equals("area")&&parasBean.getChineseName().matches(".*"+countStr+":")){
					area = parasBean.getValue();
					break;
				}	
//				if(i == parasBeans.size()-1){
//					isOver = true;
//				}
			}
			//长度length
			for (int i = 0;i<parasBeans.size();i++) {
				
				ParasBean parasBean = parasBeans.get(i);
				if(parasBean.getRealName().equals("length")&&parasBean.getChineseName().matches(".*"+countStr+":")){
					length = parasBean.getValue();
					break;
				}	
//				if(i == parasBeans.size()-1){
//					isOver = true;
//				}
			}
			//力矩参考点momentPoint
			for (int i = 0;i<parasBeans.size();i++) {
				
				ParasBean parasBean = parasBeans.get(i);
				if(parasBean.getRealName().equals("momentPoint")&&parasBean.getChineseName().matches(".*"+countStr+":")){
					momentPoint = parasBean.getValue();
					break;
				}	
//				if(i == parasBeans.size()-1){
//					isOver = true;
//				}
			}
			//力矩参考轴momentLine1
			for (int i = 0;i<parasBeans.size();i++) {
				
				ParasBean parasBean = parasBeans.get(i);
				if(parasBean.getRealName().equals("momentLine1")&&parasBean.getChineseName().matches(".*"+countStr+":")){
					momentLine1 = parasBean.getValue();
					break;
				}	
//				if(i == parasBeans.size()-1){
//					isOver = true;
//				}
			}
			//力矩参考轴momentLine2
			for (int i = 0;i<parasBeans.size();i++) {
				
				ParasBean parasBean = parasBeans.get(i);
				if(parasBean.getRealName().equals("momentLine2")&&parasBean.getChineseName().matches(".*"+countStr+":")){
					momentLine2 = parasBean.getValue();
					break;
				}	
//				if(i == parasBeans.size()-1){
//					isOver = true;
//				}
			}
			//切面plane1
			for (int i = 0;i<parasBeans.size();i++) {
				
				ParasBean parasBean = parasBeans.get(i);
				if(parasBean.getRealName().equals("plane1")&&parasBean.getChineseName().matches(".*"+countStr+":")){
					plane1 = parasBean.getValue();
					break;
				}	
//				if(i == parasBeans.size()-1){
//					isOver = true;
//				}
			}
			//切面plane2
			for (int i = 0;i<parasBeans.size();i++) {
				
				ParasBean parasBean = parasBeans.get(i);
				if(parasBean.getRealName().equals("plane2")&&parasBean.getChineseName().matches(".*"+countStr+":")){
					plane2 = parasBean.getValue();
					break;
				}	
//				if(i == parasBeans.size()-1){
//					isOver = true;
//				}
			}
			//切面plane3
			for (int i = 0;i<parasBeans.size();i++) {
				
				ParasBean parasBean = parasBeans.get(i);
				if(parasBean.getRealName().equals("plane3")&&parasBean.getChineseName().matches(".*"+countStr+":")){
					plane3 = parasBean.getValue();
					break;
				}	
//				if(i == parasBeans.size()-1){
//					isOver = true;
//				}
			}
			if(parasBeans.size()==0){
				isOver = true;
			}
			if(num!=""){
				Component_3Shi component = new Component_3Shi();
				component.setNum(num);
				component.setName(name);
				component.setMinRefine(minRefine);
				component.setMaxRefine(maxRefine);
				component.setArea(area);
				component.setLength(length);
				component.setMomentPoint(momentPoint);
				component.setMomentLine1(momentLine1);
				component.setMomentLine2(momentLine2);
				component.setPlane1(plane1);
				component.setPlane2(plane2);
				component.setPlane3(plane3);
				components.add(component);
				
			}else{
				isOver = true;
			}
			
			num = "";name="";minRefine = "";maxRefine = "";
			count++;
		}
//		for (int i = 0; i < components.size(); i++) {
//			System.out.println(components.get(i).getMomentPoint());
//		}
	
	}
	
	public static void flowCart_par(List<ParasBean> parasBeans,FileWriter fw) throws IOException{
		String text = new String(FileModel.flowCart);
		
		for (ParasBean parasBean:parasBeans) {
			
			//if(parasBean.getRealName().matches("bc[xyz][0-9]")||parasBean.getRealName().matches("iForce")||parasBean.getRealName().matches("iHist")||parasBean.getRealName().equals("Mach")||parasBean.getRealName().equals("Rho_inf")||parasBean.getRealName().equals("alpha")||parasBean.getRealName().equals("beta")||parasBean.getRealName().equals("gamma")){
			if(parasBean.getRealName().matches("bc[x,y,z]\\d")||parasBean.getRealName().equals("Mach")||parasBean.getRealName().equals("Rho_inf")||parasBean.getRealName().equals("alpha")||parasBean.getRealName().equals("beta")||parasBean.getRealName().equals("gamma")||parasBean.getRealName().equals("iForce")||parasBean.getRealName().equals("iHist")||parasBean.getRealName().equals("nOrders")||parasBean.getRealName().equals("CFL")||parasBean.getRealName().equals("nMGlev")){
				text = text.replaceAll(parasBean.getRealName()+".*\r\n", parasBean.getRealName()+" "+parasBean.getValue()+"\r\n");	
			}
				
			if(parasBean.getRealName().equals("projectName")){
				text = text.replaceAll("TrialsTest_Temp",parasBean.getValue());
			}
			
		}
		
		fw.write(text);
		
		
	}
	
	public static void project_c3d_fam(List<ParasBean> parasBeans,FileWriter fw) throws IOException{
		
		generateComponents(parasBeans);
		for(int i = 0;i<components.size();i++){
			Component_3Shi component = components.get(i);
			fw.write(component.getNum()+" "+component.getName()+" "+component.getName()+" "+component.getMinRefine()+" "+component.getMaxRefine()+"\r\n");
		}
		//为空 那么写默认值
		if(Component_3ShiHelper.components.size()==0){
			fw.write("0 BODY BODY 0 9\r\n1 FIN1 FIN1 0 10\r\n2 FIN2 FIN2 0 10\r\n3 FIN3 FIN3 0 10\r\n4 FIN4 FIN4 0 10");
		}
	}
	
	public static void project_c3d_clic_cntl(SoftData softData,List<ParasBean> parasBeans,FileWriter fw) throws IOException{
		
		generateComponents(parasBeans);
		//工程名
		String projectName = "";
		String alpha = ""; 
		String beta = ""; 
		//整体面积 长度 力矩参考点 力矩参考轴1 力矩参考轴2 切面1 2 3
		String entire_area = "",entire_length= "",entire_momentPoint= "",entire_momentLine1= "",entire_momentLine2= "",entire_plane1= "",entire_plane2= "",entire_plane3= "";
		//几何模型坐标系
		String model_X_axis = "",model_Y_axis = "",model_Z_axis = "";
		for(int i = 0;i<parasBeans.size();i++){
			if(parasBeans.get(i).getRealName().equals("projectName")){
				projectName = parasBeans.get(i).getValue();
			}
			else if(parasBeans.get(i).getRealName().equals("entire_area")){
				entire_area = parasBeans.get(i).getValue();
			}
			else if(parasBeans.get(i).getRealName().equals("entire_length")){
				entire_length = parasBeans.get(i).getValue();
			}
			else if(parasBeans.get(i).getRealName().equals("entire_momentPoint")){
				entire_momentPoint = parasBeans.get(i).getValue();
			}
			else if(parasBeans.get(i).getRealName().equals("entire_momentLine1")){
				entire_momentLine1 = parasBeans.get(i).getValue();
			}
			else if(parasBeans.get(i).getRealName().equals("entire_momentLine2")){
				entire_momentLine2 = parasBeans.get(i).getValue();
			}
			else if(parasBeans.get(i).getRealName().equals("entire_plane1")){
				entire_plane1 = parasBeans.get(i).getValue();
			}
			else if(parasBeans.get(i).getRealName().equals("entire_plane2")){
				entire_plane2 = parasBeans.get(i).getValue();
			}
			else if(parasBeans.get(i).getRealName().equals("entire_plane3")){
				entire_plane3 = parasBeans.get(i).getValue();
			}
			else if(parasBeans.get(i).getRealName().equals("model_X_axis")){
				model_X_axis = parasBeans.get(i).getValue();
			}
			else if(parasBeans.get(i).getRealName().equals("model_Y_axis")){
				model_Y_axis = parasBeans.get(i).getValue();
			}
			else if(parasBeans.get(i).getRealName().equals("model_Z_axis")){
				model_Z_axis = parasBeans.get(i).getValue();
			}
			else if(parasBeans.get(i).getRealName().equals("alpha")){
				alpha = parasBeans.get(i).getValue()+".000000";
			}
			else if(parasBeans.get(i).getRealName().equals("beta")){
				beta = parasBeans.get(i).getValue()+".000000";
			}
			
		}
		fw.write("$_ClicFileName "+projectName+"_c3d.i.triq\r\n");
		fw.write("$_Output_Directory Profiles\r\n\r\n");
		//组件
		for(int i = 0;i<components.size();i++){
			Component_3Shi component = components.get(i);
			fw.write("$_ComponentName "+component.getNum()+" "+component.getName()+"\r\n");	
		}
		fw.write("\r\n");
		//面积
		//整体面积
		if(entire_area==""){
			entire_area = "0.000314";
		}
		fw.write("$_Reference_Area "+entire_area+" entire\r\n");
		
		
		//组件面积
		for(int i = 0;i<components.size();i++){
			Component_3Shi component = components.get(i);
			fw.write("$_Reference_Area "+component.getArea()+" "+component.getName()+"\r\n");	
		}
		fw.write("\r\n");
		//长度
		//整体长度
		if(entire_length==""){
			entire_length = "0.350000";
		}
		fw.write("$_Reference_Length "+entire_length+" entire\r\n");
		//组件长度
		for(int i = 0;i<components.size();i++){
			Component_3Shi component = components.get(i);
			fw.write("$_Reference_Length "+component.getLength()+" "+component.getName()+"\r\n");	
		}
		fw.write("\r\n");
		
		if(alpha==""){
			alpha = "10.000000";
		}
		if(beta==""){
			beta = "10.000000";
		}
		fw.write("$_Incidence_Angle "+alpha+"\r\n");
		fw.write("$_Sideslip_Angle  "+beta+"\r\n\r\n");
		//几何模型坐标系和体坐标系的关系
		if(model_X_axis==""){
			model_X_axis = "-Xb";
		}
		if(model_Y_axis==""){
			model_Y_axis = "-Zb";
		}
		if(model_Z_axis==""){
			model_Z_axis = "-Yb";
		}
		fw.write("$_Model_X_axis_is "+model_X_axis+"\r\n");
		fw.write("$_Model_Y_axis_is "+model_Y_axis+"\r\n");
		fw.write("$_Model_Z_axis_is "+model_Z_axis+"\r\n\r\n");
		
		//力矩参考点 力矩参考轴
		//整体
		fw.write("$_Force entire\r\n");
		if(entire_momentPoint==""){
			entire_momentPoint = "0.000000 0.000000 0.000000";
		}
		if(entire_momentLine1==""){
			entire_momentLine1 = "0.000000 0.000000 0.000000";
		}
		if(entire_momentLine2==""){
			entire_momentLine2 = "0.000000 0.000000 0.000000";
		}
		fw.write("$_Moment_Point "+entire_momentPoint+" entire\r\n");
		fw.write("$_Moment_Line "+entire_momentLine1+" "+entire_momentLine2+" entire\r\n");
		//组件
		for (int i = 0; i < components.size(); i++) {
			fw.write("$_Force "+components.get(i).getName()+"\r\n");
			fw.write("$_Moment_Point "+components.get(i).getMomentPoint()+" "+components.get(i).getName()+"\r\n");
			fw.write("$_Moment_Line "+components.get(i).getMomentLine1()+" "+components.get(i).getMomentLine2()+" "+components.get(i).getName()+"\r\n");
			
		}
		fw.write("\r\n");
		//fw.write("$Distribution 0.000000 0.000000 0.000000 0.000000 1.000000 0.000000 0.000000 0.000000 entire\r\n");
		
	}
	
	/**
	 * comref按照components写入
	 * @param parasBeans
	 * @param fw
	 * @throws IOException
	 */
	public static void project_c3d_input(List<ParasBean> parasBeans,FileWriter fw) throws IOException{
		
		generateComponents(parasBeans);
		String text = FileModel.project_c3d_input;
		String Xmin = "",Xmax ="",Ymin ="",Ymax ="",Zmin ="",Zmax="",verts_in_X="",verts_in_Y="",verts_in_Z="";
		for (int i = 0; i < parasBeans.size(); i++) {
			ParasBean parasBean = parasBeans.get(i);
			
			if(parasBean.getRealName().equals("Xmin")){
				Xmin = parasBean.getValue();
			}
			if(parasBean.getRealName().equals("Xmax")){
				Xmax = parasBean.getValue();
			}
			if(parasBean.getRealName().equals("Ymin")){
				Ymin = parasBean.getValue();
			}
			if(parasBean.getRealName().equals("Ymax")){
				Ymax = parasBean.getValue();
			}
			if(parasBean.getRealName().equals("Zmin")){
				Zmin = parasBean.getValue();
			}
			if(parasBean.getRealName().equals("Zmax")){
				Zmax = parasBean.getValue();
			}
			if(parasBean.getRealName().equals("# verts in X")){
				verts_in_X = parasBean.getValue();
			}
			if(parasBean.getRealName().equals("# verts in Y")){
				verts_in_Y = parasBean.getValue();
			}
			if(parasBean.getRealName().equals("# verts in Z")){
				verts_in_Z = parasBean.getValue();
			}	
		}
		
		for(int i = 0;i<parasBeans.size();i++){
			ParasBean parasBean = parasBeans.get(i);
			if(parasBean.getRealName().equals("projectName")){
				//修改模板工程名
				text = text.replaceAll("TrialsTest_Temp", parasBean.getValue());
			}
			else if(!parasBean.getRealName().equals("Xmin")&&!parasBean.getRealName().equals("Xmax")&&!parasBean.getRealName().equals("Ymin")&&!parasBean.getRealName().equals("Ymax")&&!parasBean.getRealName().equals("Zmin")&&!parasBean.getRealName().equals("Zmax")&&!parasBean.getRealName().equals("# verts in X")&&!parasBean.getRealName().equals("# verts in Y")&&!parasBean.getRealName().equals("# verts in Z")){
				text = text.replaceAll(parasBean.getRealName()+"\r\n+.*\r\n?",parasBean.getRealName()+":\r\n	"+parasBean.getValue()+"\r\n");
			}
		}
		//替换2. Outer Cartesian Box Specs:和3. Starting Mesh Dimensions:的内容
		text = text.replaceAll("Xmin.*\r\n?.*\r\n?", "Xmin 	Xmax 	Ymin 	Ymax 	Zmin 	Zmax\r\n	"+Xmin+" "+Xmax+" "+Ymin+" "+Ymax+" "+Zmin+" "+Zmax+"\r\n");
		text = text.replaceAll("# verts in X.*\r\n?.*\r\n?", "# verts in X    # verts in Y     # verts in Z\r\n	"+verts_in_X+"	"+verts_in_Y+"	"+verts_in_Z+"\r\n");
		
		//写部件
		String comps = "";
		for (int i = 0; i < components.size(); i++) {
			Component_3Shi component = components.get(i);
			comps+="	CompRef "+component.getNum()+"	"+component.getMinRefine()+"		\r\n";
		}
		if(!comps.equals("")){
			text = text.replaceAll("	CompRef 0.*\r\n?.*\r\n?.*\r\n?.*\r\n?.*\r\n?", comps);
		}
		fw.write(text);
	}
	
	public static void project_c3d_cntl(List<ParasBean> parasBeans,FileWriter fw) throws IOException{
		
		String text = FileModel.project_c3d_cntl;
		fw.write(text);
		
		
	}
	
	public static void executQD(SoftData softData,CallAction callAction,Runtime runtime,String command,String soft_path){
		
		//侧滑角 攻角 马赫数
		List<String> betas = new ArrayList<String>();
		List<String> alphas = new ArrayList<String>();
		List<String> maches = new ArrayList<String>();
		String projectName = "",singleBeta="",singleAlpha="",singleMach="";
		String n = "",N="",ncpus ="";
		for (int i = 0; i < softData.getParasBeans().size(); i++) {
			ParasBean parasBean = softData.getParasBeans().get(i);
			if(parasBean.getRealName().equals("beta")){
				betas.add(parasBean.getValue());
			}else if(parasBean.getRealName().equals("alpha")){
				alphas.add(parasBean.getValue());
			}else if(parasBean.getRealName().equals("Mach")){
				maches.add(parasBean.getValue());
			}else if(parasBean.getRealName().equals("projectName")){
				projectName = parasBean.getValue();
			}else if(parasBean.getRealName().equals("n")){
				n = parasBean.getValue();
			}else if(parasBean.getRealName().equals("N")){
				N = parasBean.getValue();
			}else if(parasBean.getRealName().equals("ncpus")){
				ncpus = parasBean.getValue();
			}
		}
		if(betas.size()==1){
			singleBeta = betas.get(0);
			
		}
		if(alphas.size()==1){
			singleAlpha = alphas.get(0);
			
		}
		if(maches.size()==1){
			singleMach = maches.get(0);
			
		}
		
		//如果侧滑角 攻角 马赫数中有一个为多个
		if(betas.size()>1||alphas.size()>1||maches.size()>1){
			List<ParasBean> parasBeans = new ArrayList<ParasBean>();
			
			for(int i = 0;i<betas.size();i++){
				for(int j = 0;j<alphas.size();j++){
					for(int k = 0;k<maches.size();k++){
						//改掉parasBeans中的侧滑角 攻角 马赫数
						for(ParasBean parasBean:parasBeans){
							if(parasBean.getRealName().equals("beta")){
								parasBean.setValue(betas.get(i));
							}else if(parasBean.getRealName().equals("alpha")){
								parasBean.setValue(alphas.get(j));
							}else if(parasBean.getRealName().equals("Mach")){
								parasBean.setValue(maches.get(k));
							}
						}
						
//						JOptionPane.showMessageDialog(null, betas.get(i)+" "+alphas.get(j)+" "+maches.get(k));
						//生成带有侧滑角 攻角 马赫数的输入文件
						List<Cloud_tool_user_inputFile> inputFiles = softData.getInputFiles();
						for(Cloud_tool_user_inputFile inputFile:inputFiles){
							String inputAddress = "";
							
							inputAddress = inputFile.getInputAddress();
							if(inputAddress.contains("flowCart.par")||inputAddress.matches(".*_c3d.clic.cntl")){
								File file = new File(inputAddress);
								FileWriter fw = null;
								try {
									fw = new FileWriter(file,false);
								
								if(inputAddress.contains("flowCart.par")){
									
									Component_3ShiHelper.flowCart_par(parasBeans, fw);
									
								}else if(inputAddress.matches(".*_c3d.clic.cntl")){
									Component_3ShiHelper.project_c3d_clic_cntl(softData,parasBeans, fw);
								}
								fw.flush();
								fw.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							
						}	
						
					
						
						
						
						//修改bat文件中的执行参数
						File file = new File(soft_path);
						String old_text = "",text = "";
						try {
							
							old_text = FileModel.sanshi_bat;
							text = old_text;
							//替换工程名
							if(projectName!=""){
								text = text.replaceAll("TrialsTest_Temp", projectName);
							}
							//替换n N ncpus 
							if(n!=""){
								text = text.replaceAll("-n \\d*", "-n "+n);
							}
							if(N!=""){
								text = text.replaceAll("-N \\d*", "-N "+N);
							}
							if(ncpus!=""){
								text = text.replaceAll("-ncpus \\d*", "-ncpus "+ncpus);
							}
							
							
							//替换侧滑角 攻角 马赫数
							text = text.replaceAll("beta", betas.get(i));
							text = text.replaceAll("alpha", alphas.get(j));
							text = text.replaceAll("Mach", maches.get(k));
							
							FileWriter fw = new FileWriter(file);
							fw.write(text);
							fw.flush();
							fw.close();
			
							//执行工具
							callAction.exeStep(command, runtime);

							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}
			}
			
		}else{
			//修改bat文件中的执行参数
		
			File file = new File(soft_path);
			String old_text = "",text = "";
		
			//提取侧滑角攻角马赫数 工程名
			try {
				
				old_text = FileModel.sanshi_bat;
				text = old_text;
				//替换工程名
				if(projectName!=""){
					
					text = text.replaceAll("TrialsTest_Temp", projectName);
					
				}
				//替换n N ncpus 
				if(n!=""){
					text = text.replaceAll("-n \\d*", "-n "+n);
				}
				if(N!=""){
					text = text.replaceAll("-N \\d*", "-N "+N);
				}
				if(ncpus!=""){
					text = text.replaceAll("-ncpus \\d*", "-ncpus "+ncpus);
				}
				
				//替换侧滑角 攻角 马赫数
				if(singleBeta!=""){
					
					text = text.replaceAll("beta", singleBeta);
					
				}else{
					text = text.replaceAll("beta", "10");
				}
				if(singleAlpha!=""){
					text = text.replaceAll("alpha", singleAlpha);
					
				}else{
					
					text = text.replaceAll("alpha", "10");
					
				}
				if(singleMach!=""){
					text = text.replaceAll("Mach", singleMach);
					
				}else{
					text = text.replaceAll("Mach", "5");
				}
				
				
				FileWriter fw = new FileWriter(file);
				fw.write(text);
				fw.flush();
				fw.close();
				
				//执行工具
				callAction.exeStep(command, runtime);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		
		
	}

}
