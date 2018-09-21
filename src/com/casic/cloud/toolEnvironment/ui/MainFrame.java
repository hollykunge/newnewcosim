package com.casic.cloud.toolEnvironment.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.casic.cloud.toolEnvironment.bean.Cloud_tool_user_inputFile;
import com.casic.cloud.toolEnvironment.bean.Cloud_tool_user_outputFile;
import com.casic.cloud.toolEnvironment.bean.ComboBoxBean;
import com.casic.cloud.toolEnvironment.communication.Client;
import com.casic.cloud.toolEnvironment.util.LabelAndFiled;
import com.casic.cloud.toolEnvironment.util.SoftData;
import com.casic.cloud.toolEnvironment.util.SpAndP;

public class MainFrame extends JFrame {

	private JPanel content;
	private SoftData softData;
	private JPanel inputPane;
	private List<LabelAndFiled> labelAndFileds = new ArrayList();
	private Client client;

	/**
	 * Create the frame.
	 */
	public MainFrame(SoftData softData) {
		this.softData = softData;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 765, 600);
		content = new JPanel();
		content.setLayout(null);

		content.setBorder(new EmptyBorder(5, 5, 5, 5));

		add(content);
		setResizable(false);

		// feel and look
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initInputPane(content);
		
		setVisible(true);

	}

	public void initInputPane(JPanel contentPane) {
		inputPane = new JPanel();
		inputPane.setLayout(null);
		JScrollPane sp = new JScrollPane(inputPane);
		sp.setLocation(0, 0);
		sp.setSize(759, 524);
		sp.setAutoscrolls(true);
		sp.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"),
				"\u8BF7\u786E\u8BA4\u8F93\u5165\u53C2\u6570",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		contentPane.add(sp);
		if (softData != null) {
			loadInputPane();
		}
		
	}


	public void loadInputPane() {

		Map<String, String> inputParas = softData.getInput_paras();

		// �����޸ĵ��������
		Set<String> sets = inputParas.keySet();
		Iterator<String> iterator = sets.iterator();
		int count = 0;

		MyLabel title = new MyLabel(softData.getCloud_tool().getToolName()
				+ "输入参数配置");
		title.setBounds(300, count * 50, 500, 50);
		inputPane.add(title);
		count++;
		// �ָ���
//		JSeparator separator1 = new JSeparator(JSeparator.HORIZONTAL);
//		separator1.setBounds(35, count * 60, 700, 50);
//		inputPane.add(separator1);
//		count++;
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(35, 50, 700, 420);
		
		//tabbedPane.addTab("a", new JPanel());
		inputPane.add(tabbedPane);
		List<SpAndP> saps = new ArrayList();
		while (iterator.hasNext()) {

			String formName = iterator.next();
			String value = inputParas.get(formName);
			String chineseName = softData.getChineseName(formName);
			String realName = softData.getRealName(formName);
			String type = softData.getType(formName);
			boolean findType = false;
			if (!formName.contains("id") &&!formName.contains("Id")&& value != null) {
				
				for(SpAndP sap:saps){
					
					if(((TitledBorder)(sap.getSp().getBorder())).getTitle().equals(type)){
						findType = true;
						String values[] = value.split(",", 0);
						for(int i = 0;i<values.length;i++){
							MyLabel label = null;
							if(values.length>1){
								label = new MyLabel(chineseName+i+":");
							}else{
								label = new MyLabel(chineseName+":");
							}
							label.setPreferredSize(new Dimension(300, 20));
							label.setToolTipText(realName);
							label.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
							MyTextField field = new MyTextField(values[i]);
							LabelAndFiled labelAndFiled = new LabelAndFiled(label, field);
							labelAndFileds.add(labelAndFiled);
							field.setPreferredSize(new Dimension(100, 20));
							sap.getP().add(label);
							sap.getP().add(field);
							sap.setCount(sap.getCount()+1);
							sap.getP().setPreferredSize(new Dimension(500, 40*sap.getCount()));
							
						}
						break;
					}
				}
				if(!findType){
					JPanel new_p = new JPanel();
					new_p.setLayout(new FlowLayout(FlowLayout.CENTER,15,20));
					JScrollPane new_sp = new JScrollPane(new_p);
					new_sp.setLocation(50,0);
					new_sp.setSize(650, 200);
					new_sp.setAutoscrolls(true);
					
					new_sp.setBorder(new TitledBorder(UIManager
							.getBorder("TitledBorder.border"),
							type,
							TitledBorder.LEADING, TitledBorder.TOP, null, null));
					tabbedPane.addTab(type, new_sp);
					//inputPane.add(new_sp);
					SpAndP sap = new SpAndP(new_sp, new_p);
					sap.setCount(sap.getCount()+1);
					sap.getP().setPreferredSize(new Dimension(500, 50*sap.getCount()));
					saps.add(sap);
					
					String values[] = value.split(",",0);
					for(int i = 0;i<values.length;i++){
						MyLabel label = null;
						if(values.length>1){
							label = new MyLabel(chineseName+i+":");
						}else{
							label = new MyLabel(chineseName+":");
						}
					
						label.setPreferredSize(new Dimension(300, 20));
						label.setToolTipText(realName);
						label.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
						MyTextField field = new MyTextField(values[i]);
						LabelAndFiled labelAndFiled = new LabelAndFiled(label, field);
						labelAndFileds.add(labelAndFiled);
						field.setPreferredSize(new Dimension(100, 20));
						new_p.add(label);
						new_p.add(field);
						sap.setCount(sap.getCount()+1);
						new_p.setPreferredSize(new Dimension(500, 40*sap.getCount()));
					}
					
					count++;
					
				}
				
			}

		}
		inputPane.setPreferredSize(new Dimension(759, 524));
		
	}

	public SoftData getSoftData() {
		return softData;
	}

	public void setSoftData(SoftData softData) {
		this.softData = softData;
	}

	public JPanel getInputPane() {
		return inputPane;
	}

	public void setInputPane(JPanel inputPane) {
		this.inputPane = inputPane;
	}

	public List<LabelAndFiled> getLabelAndFileds() {
		return labelAndFileds;
	}

	public void setLabelAndFileds(List<LabelAndFiled> labelAndFileds) {
		this.labelAndFileds = labelAndFileds;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	
}
