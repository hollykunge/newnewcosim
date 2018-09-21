package com.casic.cloud.toolEnvironment.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.casic.cloud.toolEnvironment.ui.MainFrame;
import com.casic.cloud.toolEnvironment.util.SoftData;

import sun.misc.BASE64Decoder;


public class Main {
	public static void main(String[] args) {
		String paras_str = "";
		for (int i = 0; i < args.length; i++) {
			paras_str = args[i];
		}

		paras_str = "cosim://toolNodeId=10000025060002&invokeParas={}&chj=&fbsws=&qtckcd=&ycqybjtjbcxzero=&wbjqyZmin=&Forcewjscjgbs=&wbjqyYmax=&jhmxzbXhtzbxdgx_id=&wbjqyXmax=&wbjqyXmin=&qtMoment_point=&wbjqyZmax=&szqjkzcsnMGlev=&gj=&ycqybjtjbcyone=&mhs=&qtMoment_line_pttwo=&dzwgs=&cckzcs=&cshfgsverts_in_X=&cshfgsverts_in_Z=&cshfgsverts_in_Y=&szqjkzcsCFL=&ycqybjtjbczzero=&ycqybjtjbczone=&qtMoment_line_ptone=&jhmxzbXhtzbxdgx=&jsbs=&ycqybjtjbcxone=&brb=&jhmxzbYhtzbxdgx=&zdwgcxb=&jhmxzbYhtzbxdgx_id=&gcm=aaa&qtckmj=&md=&wgzdxhs=&Historyscjgbs=&bxjsCPUs=&jhmxzbZhtzbxdgx_id=&jhmxzbZhtzbxdgx=&wbjqyYmin=&ycqybjtjbcyzero=&wmfjwgzxxhs=&inputFileId=10000025000002&type=1";

		try {
			paras_str = URLDecoder.decode(paras_str, "UTF-8");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (paras_str.matches("^cosim.+")) {
			SoftData softData = new SoftData(paras_str);
			// ȥ��paras_strЭ��ͷ
			softData.removeProtocolHeader();
			// ���ִ�в���List
			softData.generateInvokeParas();
			// ��������ַ�����������map
			softData.generateInputParas();
			// �ж����������������������ʾ���
			softData.judgeType();
			// �����������е�id��softData��soft_config��ֵ
			softData.loadSoft_config();
			// softData.getCloud_tool_user().setMytoolAddress("flowCartinput");
			// softData.getCloud_tool_user_inputFile().setType("气动模块(三室)");
			// softData.getCloud_tool_user_inputFile().setInputAddress("C:\\LAB\\QDCLOUD\\QDSOFT3SHI\\TrialsTest_Temp_c3d.cntl");

			if (softData.getType() == 1) {
				// ������棬��ʾ�������ֱ��ڽ�����м�����������ļ��͵��ù��߹���
				MainFrame frame = new MainFrame(softData);
				// // ���softData��inputParas�е�ӳ���ֶ��滻Ϊ�����ļ��е��ֶ�
				// softData.replaceInputParasMappingFields();
				// // ���softData�Ӿɵ������ļ��ж�ȡ�ɵ��������
				// softData.generateInputParas_old();
			} else if (softData.getType() == 2) {
				// ��������ļ�
				String text = "@start tecplot.exe "
						+ softData.getCloud_tool_user_outputFile()
								.getOutputAddress();
				File file = new File("tecplot.bat");
				try {
					FileWriter fw = new FileWriter(file);
					fw.write(text);
					fw.flush();
					fw.close();
					Runtime runtime = Runtime.getRuntime();
					Process process = runtime.exec("tecplot.bat");

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	}

}
