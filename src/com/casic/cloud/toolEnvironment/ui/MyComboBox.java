package com.casic.cloud.toolEnvironment.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JComboBox;

import com.casic.cloud.toolEnvironment.bean.ComboBoxBean;

public class MyComboBox extends JComboBox implements ActionListener{
	private MainFrame frame;
	//0为操作输入 1为操作输出
	private int type = 0;
	public MyComboBox(MainFrame frame, int type){
		this.frame = frame;
		this.type = type;
		addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String address = "";
		if(getSelectedItem() instanceof ComboBoxBean){
			address = ((ComboBoxBean)getSelectedItem()).getAddress();
		}else{
			address = getSelectedItem().toString();
		}
		
		
		if(!address.equals("编辑输入文件")&&!address.equals("打开输出文件")){
			File file = new File("openFile.bat");
			try {
				FileWriter fw = new FileWriter(file);
				if(type==0){
					fw.write("start notepad.exe "+address);
				}else if(type==1){
					
					fw.write(address);
					
				}
//				setToolTipText(address);
				fw.flush();
				fw.close();
				Runtime runtime = Runtime.getRuntime();
				Process process = runtime.exec("openFile.bat");
//				// �ȴ����ִ����� ���ر�
//				BufferedReader bufferedReader = new BufferedReader(
//						new InputStreamReader(process.getInputStream()));
//				String string = null;
//				while ((string = bufferedReader.readLine()) != null) {
//					System.out.println("cmd" + string);
//				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
					
		}
		if(type==0){
			setSelectedItem("编辑输入文件");
		}else if(type==1){
			setSelectedItem("打开输出文件");
		}
		
	}
	
}
