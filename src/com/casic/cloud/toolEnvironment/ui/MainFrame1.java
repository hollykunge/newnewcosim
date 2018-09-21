package com.casic.cloud.toolEnvironment.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;

import javax.swing.JPanel;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import com.casic.cloud.toolEnvironment.util.LabelAndFiled;
import com.casic.cloud.toolEnvironment.util.SoftData;

public class MainFrame1 extends JFrame {

	private JPanel content;
	private SoftData softData;
	private JPanel inputPane;
	private List<LabelAndFiled> labelAndFileds = new ArrayList();

	/**
	 * Create the frame.
	 */
	public MainFrame1(SoftData softData) {
		this.softData = softData;
		content = new JPanel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 765, 600);
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
		initButton();
		setVisible(true);
		
		
	}

	public void initInputPane(JPanel contentPane) {
		inputPane = new JPanel();
		inputPane.setLayout(null);
		JScrollPane sp = new JScrollPane(inputPane);
		sp.setLocation(0, 0);
		sp.setSize(759, 524);
		sp.setAutoscrolls(true);
		sp.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u8BF7\u786E\u8BA4\u8F93\u5165\u53C2\u6570", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		contentPane.add(sp);
		if(softData!=null){
			loadInputPane();
		}
		
		
	}
	
	public void initButton(){
//		MyButton generInputButton = new MyButton("��������ļ�", this);
//		generInputButton.setBounds(80, 522, 120, 40);
//		content.add(generInputButton);
//		MyButton callSoftButton = new MyButton("ִ�й���", this);
//		callSoftButton.setBounds(560, 522, 120, 40);
//		content.add(callSoftButton);
		
	}

	public void loadInputPane() {
		
		Map<String, String> inputParas = softData.getInput_paras();
		
		// �����޸ĵ��������
		Set<String> sets = inputParas.keySet();
		Iterator<String> iterator = sets.iterator();
		int count = 0;
		while (iterator.hasNext()) {

			String name = iterator.next();
			String value = inputParas.get(name);
			String chineseName = softData.getChineseName(name);
			if (!name.contains("id") && value != null) {
				MyLabel label = new MyLabel(chineseName + ":");
				label.setPreferredSize(new Dimension(80, 20));
				label.setToolTipText(name);
				MyTextField field = new MyTextField(value);
				field.setPreferredSize(new Dimension(100, 20));
				JPanel panel = new JPanel();
				panel.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 10));
				LabelAndFiled labelAndFiled = new LabelAndFiled(label, field);
				labelAndFileds.add(labelAndFiled);
				panel.add(label);
				panel.add(field);
				panel.setBounds(0, count * 50, 500, 50);
				
				inputPane.add(panel);
				count++;
			}

		}
		
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

	
}
